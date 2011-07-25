package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.EntityNotFoundException;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.IKeyValueStore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.UnsupportedPropertyException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static net.vvakame.blaz.sqlite.KvsOpenHelper.*;

/**
 * SQLiteによるKVSの実装
 * @author vvakame
 */
public class SQLiteKVS implements IKeyValueStore, SqlTransaction.ActionCallback {

	static final String DB_NAME = "blaz.db";

	static final int DB_VERSION = 1;

	Context mContext;

	SQLiteDatabase mDb = null;


	/**
	 * the constructor.
	 * @param context
	 * @category constructor
	 */
	public SQLiteKVS(Context context) {
		mContext = context.getApplicationContext();
		mDb = new KvsOpenHelper(mContext, DB_NAME, DB_VERSION).getWritableDatabase();
	}

	@Override
	public void put(Entity entity) {
		delete(entity.getKey());
		{
			ContentValues values = convKeyToValues(entity.getKey());
			mDb.insert(TABLE_KEYS, null, values);
		}
		List<ContentValues> list = makeValues(entity.getKey(), entity.getProperties());
		for (ContentValues values : list) {
			mDb.insert(TABLE_VALUES, null, values);
		}
	}

	@Override
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("key is required.");
		}
		mDb.delete(TABLE_KEYS, COL_KEY_STRING + " = ?", new String[] {
			KeyUtil.keyToString(key)
		});
		mDb.delete(TABLE_VALUES, COL_KEY_STRING + " = ?", new String[] {
			KeyUtil.keyToString(key)
		});
	}

	static ContentValues convKeyToValues(Key key) {
		ContentValues values = new ContentValues();

		if (key.getName() == null) {
			values.put(COL_ID, key.getId());
		} else {
			values.put(COL_NAME, key.getName());
		}
		values.put(COL_KIND, key.getKind());
		values.put(COL_KEY_STRING, KeyUtil.keyToString(key));

		return values;
	}

	static List<ContentValues> makeValues(Key key, Map<String, Object> properties) {
		List<ContentValues> values = new ArrayList<ContentValues>(properties.keySet().size());
		for (String name : properties.keySet()) {
			Object obj = properties.get(name);
			if (obj instanceof Collection) {
				convListToValue(key, values, name, (Collection<?>) obj);
			} else {
				convObjToValue(key, values, name, obj, false);
			}
		}

		return values;
	}

	static void convObjToValue(Key key, List<ContentValues> values, String name, Object obj,
			boolean isList) {
		ContentValues value = new ContentValues();
		value.put(COL_KEY_STRING, KeyUtil.keyToString(key));
		value.put(COL_KIND, key.getKind());
		value.put(COL_NAME, name);

		if (obj == null) {
			value.put(COL_TYPE, isList ? T_L_NULL : T_NULL);
		} else if (obj instanceof String) {
			value.put(COL_TYPE, isList ? T_L_STRING : T_STRING);
			value.put(COL_VALUE_STRING, (String) obj);
		} else if (obj instanceof Byte) {
			value.put(COL_TYPE, isList ? T_L_LONG : T_LONG);
			value.put(COL_VALUE_INTEGER, (Byte) obj);
		} else if (obj instanceof Short) {
			value.put(COL_TYPE, isList ? T_L_LONG : T_LONG);
			value.put(COL_VALUE_INTEGER, (Short) obj);
		} else if (obj instanceof Integer) {
			value.put(COL_TYPE, isList ? T_L_LONG : T_LONG);
			value.put(COL_VALUE_INTEGER, (Integer) obj);
		} else if (obj instanceof Long) {
			value.put(COL_TYPE, isList ? T_L_LONG : T_LONG);
			value.put(COL_VALUE_INTEGER, (Long) obj);
		} else if (obj instanceof Boolean) {
			value.put(COL_TYPE, isList ? T_L_BOOLEAN : T_BOOLEAN);
			if ((Boolean) obj) {
				value.put(COL_VALUE_STRING, T_V_BOOLEAN_TRUE);
			} else {
				value.put(COL_VALUE_STRING, T_V_BOOLEAN_FALSE);
			}
		} else if (obj instanceof Float) {
			value.put(COL_TYPE, isList ? T_L_DOUBLE : T_DOUBLE);
			value.put(COL_VALUE_REAL, (Float) obj);
		} else if (obj instanceof Double) {
			value.put(COL_TYPE, isList ? T_L_DOUBLE : T_DOUBLE);
			value.put(COL_VALUE_REAL, (Double) obj);
		} else if (obj instanceof Key) {
			value.put(COL_TYPE, isList ? T_L_KEY : T_KEY);
			value.put(COL_VALUE_STRING, KeyUtil.keyToString((Key) obj));
		} else if (obj instanceof byte[]) {
			value.put(COL_TYPE, isList ? T_L_BYTES : T_BYTES);
			value.put(COL_VALUE_BLOB, (byte[]) obj);
		} else {
			throw new UnsupportedPropertyException("property name=" + name
					+ " is not suppored type. type=" + obj.getClass().getCanonicalName());
		}
		values.add(value);
	}

	static void convListToValue(Key key, List<ContentValues> values, String name,
			Collection<?> collection) {
		Object[] list = collection.toArray();
		if (collection.size() == 0) {
			ContentValues value = new ContentValues();
			value.put(COL_KEY_STRING, KeyUtil.keyToString(key));
			value.put(COL_KIND, key.getKind());
			value.put(COL_NAME, name);
			value.put(COL_SEQ, 0);

			value.put(COL_TYPE, T_L_BLANK);
			values.add(value);

			return;
		} else {
			for (int i = 0; i < list.length; i++) {
				Object obj = list[i];
				convObjToValue(key, values, name, obj, true);
			}
			return;
		}
	}

	@Override
	public Entity get(Key key) {
		Entity entity = getOrNull(key);
		if (entity == null) {
			throw new EntityNotFoundException("key=" + key.toString() + " is not found.");
		}

		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity getOrNull(Key key) {
		Cursor c;
		c = mDb.query(TABLE_KEYS, null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		if (!c.moveToFirst()) {
			return null;
		}
		Key newKey = cursorToKey(c);

		c = mDb.query(TABLE_VALUES, null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, COL_NAME + "," + COL_SEQ);

		Entity entity = new Entity();
		entity.setKey(newKey);
		final int nameIdx = c.getColumnIndex(COL_NAME);
		final int typeIdx = c.getColumnIndex(COL_TYPE);
		final int valStrIdx = c.getColumnIndex(COL_VALUE_STRING);
		final int valIntIdx = c.getColumnIndex(COL_VALUE_INTEGER);
		final int valRealIdx = c.getColumnIndex(COL_VALUE_REAL);
		final int valBlobIdx = c.getColumnIndex(COL_VALUE_BLOB);
		if (!c.moveToFirst()) {
			return entity;
		}
		do {
			final String name = c.getString(nameIdx);
			String type = c.getString(typeIdx);
			Object value;
			if (T_NULL.equals(type)) {
				value = null;
			} else if (T_STRING.equals(type)) {
				value = c.getString(valStrIdx);
			} else if (T_BOOLEAN.equals(type)) {
				value = c.getString(valStrIdx).equals(T_V_BOOLEAN_TRUE) ? true : false;
			} else if (T_LONG.equals(type)) {
				value = c.getLong(valIntIdx);
			} else if (T_DOUBLE.equals(type)) {
				value = c.getDouble(valRealIdx);
			} else if (T_KEY.equals(type)) {
				value = KeyUtil.stringToKey(c.getString(valStrIdx));
			} else if (T_BYTES.equals(type)) {
				value = c.getBlob(valBlobIdx);
			} else if (T_L_BLANK.equals(type)) {
				value = new ArrayList<Object>();
			} else if (type.startsWith(T_L_PREFIX)) {
				List<Object> list;
				if (entity.getProperties().containsKey(name)) {
					list = (List<Object>) entity.getProperty(name);
				} else {
					list = new ArrayList<Object>();
				}
				if (T_L_NULL.equals(type)) {
					list.add(null);
				} else if (T_L_STRING.equals(type)) {
					list.add(c.getString(valStrIdx));
				} else if (T_L_BOOLEAN.equals(type)) {
					boolean b = c.getString(valStrIdx).equals(T_V_BOOLEAN_TRUE) ? true : false;
					list.add(b);
				} else if (T_L_LONG.equals(type)) {
					list.add(c.getLong(valIntIdx));
				} else if (T_L_DOUBLE.equals(type)) {
					list.add(c.getDouble(valRealIdx));
				} else if (T_L_KEY.equals(type)) {
					list.add(KeyUtil.stringToKey(c.getString(valStrIdx)));
				} else if (T_L_BYTES.equals(type)) {
					list.add(c.getBlob(valBlobIdx));
				} else {
					throw new UnsupportedPropertyException("property name=" + name
							+ " is not suppored type. type=" + type);
				}
				value = list;
			} else {
				throw new UnsupportedPropertyException("property name=" + name
						+ " is not suppored type. type=" + type);
			}
			entity.setProperty(name, value);
		} while (c.moveToNext());

		return entity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Key, Entity> getAsMap(Iterable<Key> keys) {
		if (keys == null) {
			return new HashMap<Key, Entity>();
		}

		List<Key> keyList;
		if (keys instanceof List) {
			keyList = (List<Key>) keys;
		} else {
			keyList = new ArrayList<Key>();
			for (Key key : keys) {
				keyList.add(key);
			}
		}

		if (keyList.size() == 0) {
			return new HashMap<Key, Entity>();
		}

		StringBuilder builder = new StringBuilder();
		List<String> args = new ArrayList<String>();
		builder.append(COL_KEY_STRING).append(" IN (");
		for (int i = 0; i < keyList.size(); i++) {
			builder.append("?");
			if (i != keyList.size() - 1) {
				builder.append(",");
			}
			args.add(KeyUtil.keyToString(keyList.get(i)));
		}
		builder.append(")");

		Map<Key, Entity> resultMap = new HashMap<Key, Entity>();
		{ // for propertyless entity...
			Cursor c =
					mDb.query(TABLE_KEYS, null, builder.toString(), args.toArray(new String[] {}),
							null, null, null);
			if (!c.moveToFirst()) {
				return resultMap;
			}
			List<Key> tmpKeyList = cursorToKeys(c);
			for (Key key : tmpKeyList) {
				Entity entity = new Entity();
				entity.setKey(key);
				resultMap.put(key, entity);
			}
		}

		Cursor c =
				mDb.query(TABLE_VALUES, null, builder.toString(), args.toArray(new String[] {}),
						null, null, COL_KEY_STRING + "," + COL_NAME + "," + COL_SEQ);

		if (!c.moveToFirst()) {
			return resultMap;
		}

		final int keyIdx = c.getColumnIndex(COL_KEY_STRING);
		final int nameIdx = c.getColumnIndex(COL_NAME);
		final int typeIdx = c.getColumnIndex(COL_TYPE);
		final int valStrIdx = c.getColumnIndex(COL_VALUE_STRING);
		final int valIntIdx = c.getColumnIndex(COL_VALUE_INTEGER);
		final int valRealIdx = c.getColumnIndex(COL_VALUE_REAL);
		final int valBlobIdx = c.getColumnIndex(COL_VALUE_BLOB);

		String oldKeyStr = c.getString(keyIdx);
		String keyStr = c.getString(keyIdx);
		Entity entity = new Entity();
		entity.setKey(KeyUtil.stringToKey(keyStr));
		do {
			keyStr = c.getString(keyIdx);
			if (!keyStr.equals(oldKeyStr)) {
				resultMap.put(entity.getKey(), entity);
				oldKeyStr = keyStr;
				entity = new Entity();
				entity.setKey(KeyUtil.stringToKey(keyStr));
			}
			final String name = c.getString(nameIdx);
			String type = c.getString(typeIdx);
			Object value;
			if (T_NULL.equals(type)) {
				value = null;
			} else if (T_STRING.equals(type)) {
				value = c.getString(valStrIdx);
			} else if (T_BOOLEAN.equals(type)) {
				value = c.getString(valStrIdx).equals(T_V_BOOLEAN_TRUE) ? true : false;
			} else if (T_LONG.equals(type)) {
				value = c.getLong(valIntIdx);
			} else if (T_DOUBLE.equals(type)) {
				value = c.getDouble(valRealIdx);
			} else if (T_KEY.equals(type)) {
				value = KeyUtil.stringToKey(c.getString(valStrIdx));
			} else if (T_BYTES.equals(type)) {
				value = c.getBlob(valBlobIdx);
			} else if (T_L_BLANK.equals(type)) {
				value = new ArrayList<Object>();
			} else if (type.startsWith(T_L_PREFIX)) {
				List<Object> list;
				if (entity.getProperties().containsKey(name)) {
					list = (List<Object>) entity.getProperty(name);
				} else {
					list = new ArrayList<Object>();
				}
				if (T_L_NULL.equals(type)) {
					list.add(null);
				} else if (T_L_STRING.equals(type)) {
					list.add(c.getString(valStrIdx));
				} else if (T_L_BOOLEAN.equals(type)) {
					boolean b = c.getString(valStrIdx).equals(T_V_BOOLEAN_TRUE) ? true : false;
					list.add(b);
				} else if (T_L_LONG.equals(type)) {
					list.add(c.getLong(valIntIdx));
				} else if (T_L_DOUBLE.equals(type)) {
					list.add(c.getDouble(valRealIdx));
				} else if (T_L_KEY.equals(type)) {
					list.add(KeyUtil.stringToKey(c.getString(valStrIdx)));
				} else if (T_L_BYTES.equals(type)) {
					list.add(c.getBlob(valBlobIdx));
				} else {
					throw new UnsupportedPropertyException("property name=" + name
							+ " is not suppored type. type=" + type);
				}
				value = list;
			} else {
				throw new UnsupportedPropertyException("property name=" + name
						+ " is not suppored type. type=" + type);
			}
			entity.setProperty(name, value);
		} while (c.moveToNext());
		resultMap.put(entity.getKey(), entity);

		return resultMap;
	}

	Key cursorToKey(Cursor c) {
		Key newKey = new Key();
		newKey.setKind(c.getString(c.getColumnIndex(COL_KIND)));
		newKey.setId(c.getLong(c.getColumnIndex(COL_ID)));
		newKey.setName(c.getString(c.getColumnIndex(COL_NAME)));
		return newKey;
	}

	List<Key> cursorToKeys(Cursor c) {
		List<Key> keys = new ArrayList<Key>();

		final int idx = c.getColumnIndex(COL_KEY_STRING);

		do {
			String keyStr = c.getString(idx);
			keys.add(KeyUtil.stringToKey(keyStr));
		} while (c.moveToNext());

		return keys;
	}

	@Override
	public List<Key> findAsKey(Filter... filters) {
		for (Filter filter : filters) {
			if (filter == null) {
				throw new IllegalArgumentException("null argment is not allowed.");
			}
		}
		StringBuilder builder = new StringBuilder();
		List<String> args = new ArrayList<String>();
		if (filters.length == 0) {
			QueryBuilder.makeGetAllQuery(builder, args);
		} else if (filters.length == 1) {
			Filter filter = filters[0];
			QueryBuilder.makeQuery(filter, builder, args);
		} else {
			for (int i = 0; i < filters.length; i++) {
				Filter filter = filters[i];
				builder.append(" (");
				QueryBuilder.makeQuery(filter, builder, args);
				builder.append(") ");
				if (i != filters.length - 1) {
					builder.append("INTERSECT");
				}
			}
		}

		String query = builder.toString();
		Cursor c = mDb.rawQuery(query, args.toArray(new String[] {}));
		if (!c.moveToFirst()) {
			return new ArrayList<Key>();
		}

		return cursorToKeys(c);
	}

	@Override
	public List<Entity> find(Filter... filters) {
		List<Key> keys = findAsKey(filters);
		if (keys == null || keys.size() == 0) {
			return new ArrayList<Entity>();
		}

		Map<Key, Entity> entities = getAsMap(keys);
		List<Entity> resultList = new ArrayList<Entity>(entities.values());
		if (keys.size() == entities.size()) {
			return resultList;
		}

		for (Key key : keys) {
			if (!entities.containsKey(key)) {
				Entity entity = new Entity();
				entity.setKey(key);
				resultList.add(entity);
			}
		}

		return resultList;
	}

	/**
	 * データ操作に対するトランザクションを開始する.<br>
	 * トランザクションの仕様は {@link SQLiteDatabase#beginTransaction()} に準じる。
	 * @return トランザクション
	 * @author vvakame
	 */
	@Override
	public Transaction beginTransaction() {
		mDb.beginTransaction();
		return new SqlTransaction(this);
	}

	@Override
	public boolean onCommit() {
		mDb.setTransactionSuccessful();
		mDb.endTransaction();
		return true;
	}

	@Override
	public boolean onRollback() {
		mDb.endTransaction();
		return true;
	}

	@Override
	public List<Entity> get(Key... keys) throws EntityNotFoundException {
		List<Key> keyList = Arrays.asList(keys);
		Map<Key, Entity> entities = getAsMap(keyList);
		List<Entity> resultList = new ArrayList<Entity>(entities.values());
		if (keyList.size() == entities.size()) {
			return resultList;
		}
		throw new EntityNotFoundException("size expected=" + keyList.size() + ", but got="
				+ entities.size());
	}
}
