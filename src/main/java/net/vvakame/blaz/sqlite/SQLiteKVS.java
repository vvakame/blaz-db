package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.EntityNotFoundException;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.IKeyValueStore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;
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
public class SQLiteKVS implements IKeyValueStore {

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
		{
			ContentValues values = convKeyToValues(entity.getKey());
			mDb.insert(TABLE_KEYS, null, values);
		}
		List<ContentValues> list = makeValues(entity.getKey(), entity.getProperties());
		for (ContentValues values : list) {
			mDb.insert(TABLE_VALUES, null, values);
		}
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
				makeValueList(key, values, name, (Collection<?>) obj);
			} else {
				makeValue(key, values, name, obj);
			}
		}

		return values;
	}

	static void makeValue(Key key, List<ContentValues> values, String name, Object obj) {
		ContentValues value = new ContentValues();
		value.put(COL_KEY_STRING, KeyUtil.keyToString(key));
		value.put(COL_KIND, key.getKind());
		value.put(COL_NAME, name);

		if (obj == null) {
			value.put(COL_TYPE, T_NULL);
		} else if (obj instanceof String) {
			value.put(COL_TYPE, T_STRING);
			value.put(COL_VALUE_STRING, (String) obj);
		} else if (obj instanceof Byte) {
			value.put(COL_TYPE, T_LONG);
			value.put(COL_VALUE_INTEGER, (Byte) obj);
		} else if (obj instanceof Short) {
			value.put(COL_TYPE, T_LONG);
			value.put(COL_VALUE_INTEGER, (Short) obj);
		} else if (obj instanceof Integer) {
			value.put(COL_TYPE, T_LONG);
			value.put(COL_VALUE_INTEGER, (Integer) obj);
		} else if (obj instanceof Long) {
			value.put(COL_TYPE, T_LONG);
			value.put(COL_VALUE_INTEGER, (Long) obj);
		} else if (obj instanceof Boolean) {
			value.put(COL_TYPE, T_BOOLEAN);
			if ((Boolean) obj) {
				value.put(COL_VALUE_STRING, T_V_BOOLEAN_TRUE);
			} else {
				value.put(COL_VALUE_STRING, T_V_BOOLEAN_FALSE);
			}
		} else if (obj instanceof Float) {
			value.put(COL_TYPE, T_DOUBLE);
			value.put(COL_VALUE_REAL, (Float) obj);
		} else if (obj instanceof Double) {
			value.put(COL_TYPE, T_DOUBLE);
			value.put(COL_VALUE_REAL, (Double) obj);
		} else if (obj instanceof Key) {
			value.put(COL_TYPE, T_KEY);
			value.put(COL_VALUE_STRING, KeyUtil.keyToString((Key) obj));
		} else if (obj instanceof byte[]) {
			value.put(COL_TYPE, T_BYTES);
			value.put(COL_VALUE_BLOB, (byte[]) obj);
		} else {
			throw new UnsupportedPropertyException("property name=" + name
					+ " is not suppored type. type=" + obj.getClass().getCanonicalName());
		}
		values.add(value);
	}

	static void makeValueList(Key key, List<ContentValues> values, String name,
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
				ContentValues value = new ContentValues();
				value.put(COL_KEY_STRING, KeyUtil.keyToString(key));
				value.put(COL_KIND, key.getKind());
				value.put(COL_NAME, name);
				value.put(COL_SEQ, i);

				if (obj == null) {
					value.put(COL_TYPE, T_L_NULL);
				} else if (obj instanceof String) {
					value.put(COL_TYPE, T_L_STRING);
					value.put(COL_VALUE_STRING, (String) obj);
				} else if (obj instanceof Byte) {
					value.put(COL_TYPE, T_L_LONG);
					value.put(COL_VALUE_INTEGER, (Byte) obj);
				} else if (obj instanceof Short) {
					value.put(COL_TYPE, T_L_LONG);
					value.put(COL_VALUE_INTEGER, (Short) obj);
				} else if (obj instanceof Integer) {
					value.put(COL_TYPE, T_L_LONG);
					value.put(COL_VALUE_INTEGER, (Integer) obj);
				} else if (obj instanceof Long) {
					value.put(COL_TYPE, T_L_LONG);
					value.put(COL_VALUE_INTEGER, (Long) obj);
				} else if (obj instanceof Boolean) {
					value.put(COL_TYPE, T_L_BOOLEAN);
					if ((Boolean) obj) {
						value.put(COL_VALUE_STRING, T_V_BOOLEAN_TRUE);
					} else {
						value.put(COL_VALUE_STRING, T_V_BOOLEAN_FALSE);
					}
				} else if (obj instanceof Float) {
					value.put(COL_TYPE, T_L_DOUBLE);
					value.put(COL_VALUE_REAL, (Float) obj);
				} else if (obj instanceof Double) {
					value.put(COL_TYPE, T_L_DOUBLE);
					value.put(COL_VALUE_REAL, (Double) obj);
				} else if (obj instanceof Key) {
					value.put(COL_TYPE, T_L_KEY);
					value.put(COL_VALUE_STRING, KeyUtil.keyToString((Key) obj));
				} else if (obj instanceof byte[]) {
					value.put(COL_TYPE, T_L_BYTES);
					value.put(COL_VALUE_BLOB, (byte[]) obj);
				} else {
					throw new UnsupportedPropertyException("property name=" + name + ", index = "
							+ i + " is not suppored type. type="
							+ obj.getClass().getCanonicalName());
				}
				values.add(value);

			}
			return;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity get(Key key) {
		Cursor c;
		c = mDb.query(TABLE_KEYS, null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		if (!c.moveToFirst()) {
			throw new EntityNotFoundException("key=" + key.toString() + " is not found.");
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


	static final String SQL_KEY_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_KEY_STRING + " = ?";

	static final String SQL_KEY_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_KEY_STRING + " < ?";

	static final String SQL_KEY_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_KEY_STRING + " <= ?";

	static final String SQL_KEY_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_KEY_STRING + " > ?";

	static final String SQL_KEY_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_KEY_STRING + " >= ?";

	static final String SQL_KIND = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES + " WHERE "
			+ COL_KIND + " = ?";

	static final String SQL_PROPERTY_STR_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " = ?";

	static final String SQL_PROPERTY_STR_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " < ?";

	static final String SQL_PROPERTY_STR_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND "
			+ COL_VALUE_STRING + " <= ?";

	static final String SQL_PROPERTY_STR_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " > ?";

	static final String SQL_PROPERTY_STR_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND "
			+ COL_VALUE_STRING + " >= ?";

	static final String SQL_PROPERTY_INTEGER_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " = ?";

	static final String SQL_PROPERTY_INTEGER_GT = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " < ?";

	static final String SQL_PROPERTY_INTEGER_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " <= ?";

	static final String SQL_PROPERTY_INTEGER_LT = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " > ?";

	static final String SQL_PROPERTY_INTEGER_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " >= ?";

	static final String SQL_PROPERTY_REAL_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " = ?";

	static final String SQL_PROPERTY_REAL_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " < ?";

	static final String SQL_PROPERTY_REAL_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " <= ?";

	static final String SQL_PROPERTY_REAL_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " > ?";

	static final String SQL_PROPERTY_REAL_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " >= ?";


	@Override
	public List<Key> findAsKey(Filter... filters) {
		if (filters.length == 0) {
			throw new IllegalArgumentException("must need 1 filter option required.");
		}
		for (Filter filter : filters) {
			if (filter == null) {
				throw new IllegalArgumentException("null argment is not allowed.");
			}
		}
		StringBuilder builder = new StringBuilder();
		List<String> args = new ArrayList<String>();
		Filter filter;
		if (filters.length == 1) {
			filter = filters[0];
			makeQuery(filter, builder, args);
		} else {
			for (int i = 0; i < filters.length; i++) {
				filter = filters[i];
				builder.append(" (");
				makeQuery(filter, builder, args);
				builder.append(") ");
				if (i - 1 != filters.length) {
					builder.append("INTERSECT");
				}
			}
		}

		Cursor c = mDb.rawQuery(builder.toString(), args.toArray(new String[] {}));
		if (!c.moveToFirst()) {
			return new ArrayList<Key>();
		}

		return cursorToKeys(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> find(Filter... filters) {
		List<Key> keys = findAsKey(filters);
		if (keys == null || keys.size() == 0) {
			return new ArrayList<Entity>();
		}

		StringBuilder builder = new StringBuilder();
		List<String> args = new ArrayList<String>();
		builder.append(COL_KEY_STRING).append(" IN (");
		for (int i = 0; i < keys.size(); i++) {
			builder.append("?");
			if (i != keys.size() - 1) {
				builder.append(",");
			}
			args.add(KeyUtil.keyToString(keys.get(i)));
		}
		builder.append(")");

		Cursor c =
				mDb.query(TABLE_VALUES, null, builder.toString(), args.toArray(new String[] {}),
						null, null, COL_KEY_STRING + "," + COL_NAME + "," + COL_SEQ);

		if (!c.moveToFirst()) {
			return new ArrayList<Entity>();
		}

		final int keyIdx = c.getColumnIndex(COL_KEY_STRING);
		final int nameIdx = c.getColumnIndex(COL_NAME);
		final int typeIdx = c.getColumnIndex(COL_TYPE);
		final int valStrIdx = c.getColumnIndex(COL_VALUE_STRING);
		final int valIntIdx = c.getColumnIndex(COL_VALUE_INTEGER);
		final int valRealIdx = c.getColumnIndex(COL_VALUE_REAL);
		final int valBlobIdx = c.getColumnIndex(COL_VALUE_BLOB);

		List<Entity> resultList = new ArrayList<Entity>();
		String oldKeyStr = c.getString(keyIdx);
		String keyStr = c.getString(keyIdx);
		Entity entity = new Entity();
		entity.setKey(KeyUtil.stringToKey(keyStr));
		do {
			keyStr = c.getString(keyIdx);
			if (!keyStr.equals(oldKeyStr)) {
				resultList.add(entity);
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
		resultList.add(entity);

		return resultList;
	}

	static void makeQuery(Filter filter, StringBuilder builder, List<String> args) {
		switch (filter.getTarget()) {
			case KEY:
				makeQueryKey(filter, builder, args);
				break;

			case KIND:
				makeQueryKind(filter, builder, args);
				break;

			case PROPERTY:
				makeQueryProperty(filter, builder, args);
				break;

			default:
				throw new IllegalArgumentException();
		}
	}

	static void makeQueryKey(Filter filter, StringBuilder builder, List<String> args) {
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_KEY_EQ);
				break;

			case GT:
				builder.append(SQL_KEY_GT);
				break;

			case GT_EQ:
				builder.append(SQL_KEY_GT_EQ);
				break;

			case LT:
				builder.append(SQL_KEY_LT);
				break;

			case LT_EQ:
				builder.append(SQL_KEY_LT_EQ);
				break;

			default:
				break;
		}
		args.add(KeyUtil.keyToString((Key) filter.getValue()));
	}

	static void makeQueryKind(Filter filter, StringBuilder builder, List<String> args) {
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_KIND);
				break;
			default:
				break;
		}
		args.add(filter.getName());
	}

	static void makeQueryProperty(Filter filter, StringBuilder builder, List<String> args) {
		Object obj = filter.getValue();
		if (obj instanceof Key) {
			makeQueryPropertyKey(filter, builder, args);
		} else if (obj instanceof String) {
			makeQueryPropertyString(filter, builder, args);
		} else if (obj instanceof Boolean) {
			makeQueryPropertyBoolean(filter, builder, args);
		} else if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long) {
			makeQueryPropertyInteger(filter, builder, args);
		} else if (obj instanceof Float || obj instanceof Double) {
			makeQueryPropertyReal(filter, builder, args);
		} else {
			throw new IllegalArgumentException();
		}
	}

	static void makeQueryPropertyKey(Filter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_STR_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_STR_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_STR_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_STR_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_STR_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(T_KEY);
		args.add(name);
		args.add(KeyUtil.keyToString((Key) obj));
	}

	static void makeQueryPropertyString(Filter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_STR_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_STR_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_STR_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_STR_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_STR_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(T_STRING);
		args.add(name);
		args.add((String) obj);
	}

	static void makeQueryPropertyBoolean(Filter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_STR_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_STR_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_STR_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_STR_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_STR_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(T_BOOLEAN);
		args.add(name);
		args.add(String.valueOf(obj));
	}

	static void makeQueryPropertyInteger(Filter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_INTEGER_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_INTEGER_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_INTEGER_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_INTEGER_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_INTEGER_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(name);
		args.add(String.valueOf(obj));
	}

	static void makeQueryPropertyReal(Filter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_REAL_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_REAL_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_REAL_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_REAL_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_REAL_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(name);
		args.add(String.valueOf(obj));
	}
}
