package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.FilterOption;
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

	@SuppressWarnings("unchecked")
	@Override
	public Entity get(Key key) {
		Cursor c;
		c = mDb.query(TABLE_KEYS, null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		if (!c.moveToFirst()) {
			// FIXME EntityNotFoundException作る
			throw new IllegalArgumentException();
		}
		Key newKey = new Key();
		newKey.setId(c.getLong(c.getColumnIndex(COL_ID)));
		newKey.setName(c.getString(c.getColumnIndex(COL_NAME)));

		c = mDb.query(TABLE_VALUES, null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);

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
				value = "UNKNOWN"; // FIXME
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
					list.add("UNKNOWN"); // FIXME
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

	static List<Entity> find(FilterOption options) {
		return null;
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
		values.put(COL_PARENT_KEY, KeyUtil.keyToString(key.getParent()));

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
}
