package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;
import net.vvakame.blaz.UnsupportedPropertyException;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static net.vvakame.blaz.sqlite.KvsOpenHelper.*;

class ValuesDao {

	private ValuesDao() {
	}

	@SuppressWarnings("unchecked")
	public static Entity cursorToEntityAsSingle(Key key, Cursor c) {

		Entity entity = new Entity();
		entity.setKey(key);
		if (!c.moveToFirst()) {
			return entity;
		}

		final int nameIdx = c.getColumnIndex(COL_NAME);
		final int typeIdx = c.getColumnIndex(COL_TYPE);
		final int valStrIdx = c.getColumnIndex(COL_VALUE_STRING);
		final int valIntIdx = c.getColumnIndex(COL_VALUE_INTEGER);
		final int valRealIdx = c.getColumnIndex(COL_VALUE_REAL);
		final int valBlobIdx = c.getColumnIndex(COL_VALUE_BLOB);
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

	@SuppressWarnings("unchecked")
	public static Map<Key, Entity> cursorToEntities(Cursor c) {
		if (!c.moveToFirst()) {
			return new HashMap<Key, Entity>();
		}

		final int keyIdx = c.getColumnIndex(COL_KEY_STRING);
		final int nameIdx = c.getColumnIndex(COL_NAME);
		final int typeIdx = c.getColumnIndex(COL_TYPE);
		final int valStrIdx = c.getColumnIndex(COL_VALUE_STRING);
		final int valIntIdx = c.getColumnIndex(COL_VALUE_INTEGER);
		final int valRealIdx = c.getColumnIndex(COL_VALUE_REAL);
		final int valBlobIdx = c.getColumnIndex(COL_VALUE_BLOB);

		Map<Key, Entity> resultMap = new HashMap<Key, Entity>();

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

	public static void insert(SQLiteDatabase db, Entity entity) {
		List<ContentValues> list = makeValues(entity.getKey(), entity.getProperties());
		for (ContentValues values : list) {
			db.insert(TABLE_VALUES, null, values);
		}
	}

	public static void delete(SQLiteDatabase db, Key key) {
		db.delete(TABLE_VALUES, COL_KEY_STRING + " = ?", new String[] {
			KeyUtil.keyToString(key)
		});
	}

	public static Cursor query(SQLiteDatabase db, Key... keys) {
		StringBuilder builder = new StringBuilder();
		List<String> args = new ArrayList<String>();
		builder.append(COL_KEY_STRING).append(" IN (");
		for (int i = 0; i < keys.length; i++) {
			builder.append("?");
			if (i != keys.length - 1) {
				builder.append(",");
			}
			args.add(KeyUtil.keyToString(keys[i]));
		}
		builder.append(")");

		Cursor c =
				db.query(TABLE_VALUES, null, builder.toString(), args.toArray(new String[] {}),
						null, null, COL_KEY_STRING + "," + COL_NAME + "," + COL_SEQ);
		return c;
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
}
