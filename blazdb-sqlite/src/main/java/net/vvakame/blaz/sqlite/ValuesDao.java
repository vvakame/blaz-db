package net.vvakame.blaz.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.exception.UnsupportedPropertyException;
import net.vvakame.blaz.util.KeyUtil;
import static net.vvakame.blaz.sqlite.Constants.*;

class ValuesDao {

	private ValuesDao() {
	}

	@SuppressWarnings("unchecked")
	public static Entity resultSetToEntityAsSingle(Key key, ResultSet rs)
			throws SQLException {

		Entity entity = new Entity(key);
		if (!rs.next()) {
			return entity;
		}

		do {
			final String name = rs.getString(COL_NAME);
			String type = rs.getString(COL_TYPE);
			Object value;
			if (T_NULL.equals(type)) {
				value = null;
			} else if (T_STRING.equals(type)) {
				value = rs.getString(COL_VALUE_STRING);
			} else if (T_BOOLEAN.equals(type)) {
				value = rs.getString(COL_VALUE_STRING).equals(T_V_BOOLEAN_TRUE) ? true
						: false;
			} else if (T_LONG.equals(type)) {
				value = rs.getLong(COL_VALUE_INTEGER);
			} else if (T_DOUBLE.equals(type)) {
				value = rs.getDouble(COL_VALUE_REAL);
			} else if (T_KEY.equals(type)) {
				value = KeyUtil.stringToKey(rs.getString(COL_VALUE_STRING));
			} else if (T_BYTES.equals(type)) {
				value = rs.getBytes(COL_VALUE_BLOB);
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
					list.add(rs.getString(COL_VALUE_STRING));
				} else if (T_L_BOOLEAN.equals(type)) {
					boolean b = rs.getString(COL_VALUE_STRING).equals(
							T_V_BOOLEAN_TRUE) ? true : false;
					list.add(b);
				} else if (T_L_LONG.equals(type)) {
					list.add(rs.getLong(COL_VALUE_INTEGER));
				} else if (T_L_DOUBLE.equals(type)) {
					list.add(rs.getDouble(COL_VALUE_REAL));
				} else if (T_L_KEY.equals(type)) {
					list.add(KeyUtil.stringToKey(rs.getString(COL_VALUE_STRING)));
				} else if (T_L_BYTES.equals(type)) {
					list.add(rs.getBytes(COL_VALUE_BLOB));
				} else {
					throw new UnsupportedPropertyException("property name="
							+ name + " is not suppored type. type=" + type);
				}
				value = list;
			} else {
				throw new UnsupportedPropertyException("property name=" + name
						+ " is not suppored type. type=" + type);
			}
			entity.setProperty(name, value);
		} while (rs.next());

		return entity;
	}

	@SuppressWarnings("unchecked")
	public static Map<Key, Entity> resultSetToEntities(ResultSet rs)
			throws SQLException {
		if (!rs.next()) {
			return new HashMap<Key, Entity>();
		}

		Map<Key, Entity> resultMap = new HashMap<Key, Entity>();

		String oldKeyStr = rs.getString(COL_KEY_STRING);
		String keyStr = rs.getString(COL_KEY_STRING);
		Entity entity = new Entity(KeyUtil.stringToKey(keyStr));
		do {
			keyStr = rs.getString(COL_KEY_STRING);
			if (!keyStr.equals(oldKeyStr)) {
				resultMap.put(entity.getKey(), entity);
				oldKeyStr = keyStr;
				entity = new Entity(KeyUtil.stringToKey(keyStr));
			}
			final String name = rs.getString(COL_NAME);
			String type = rs.getString(COL_TYPE);
			Object value;
			if (T_NULL.equals(type)) {
				value = null;
			} else if (T_STRING.equals(type)) {
				value = rs.getString(COL_VALUE_STRING);
			} else if (T_BOOLEAN.equals(type)) {
				value = rs.getString(COL_VALUE_STRING).equals(T_V_BOOLEAN_TRUE) ? true
						: false;
			} else if (T_LONG.equals(type)) {
				value = rs.getLong(COL_VALUE_INTEGER);
			} else if (T_DOUBLE.equals(type)) {
				value = rs.getDouble(COL_VALUE_REAL);
			} else if (T_KEY.equals(type)) {
				value = KeyUtil.stringToKey(rs.getString(COL_VALUE_STRING));
			} else if (T_BYTES.equals(type)) {
				value = rs.getBytes(COL_VALUE_BLOB);
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
					list.add(rs.getString(COL_VALUE_STRING));
				} else if (T_L_BOOLEAN.equals(type)) {
					boolean b = rs.getString(COL_VALUE_STRING).equals(
							T_V_BOOLEAN_TRUE) ? true : false;
					list.add(b);
				} else if (T_L_LONG.equals(type)) {
					list.add(rs.getLong(COL_VALUE_INTEGER));
				} else if (T_L_DOUBLE.equals(type)) {
					list.add(rs.getDouble(COL_VALUE_REAL));
				} else if (T_L_KEY.equals(type)) {
					list.add(KeyUtil.stringToKey(rs.getString(COL_VALUE_STRING)));
				} else if (T_L_BYTES.equals(type)) {
					list.add(rs.getBytes(COL_VALUE_BLOB));
				} else {
					throw new UnsupportedPropertyException("property name="
							+ name + " is not suppored type. type=" + type);
				}
				value = list;
			} else {
				throw new UnsupportedPropertyException("property name=" + name
						+ " is not suppored type. type=" + type);
			}
			entity.setProperty(name, value);
		} while (rs.next());
		resultMap.put(entity.getKey(), entity);

		return resultMap;
	}

	public static void insert(Connection conn, Entity entity)
			throws SQLException {
		makeValues(conn, entity.getKey(), entity.getProperties());
	}

	public static void delete(Connection conn, Key key) throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = conn
					.prepareStatement("DELETE FROM VALUE_TABLE WHERE KEY_STR = ?");
			pre.setString(1, KeyUtil.keyToString(key));
			pre.executeUpdate();
			pre.close();
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	public static Map<Key, Entity> query(Connection conn, Key... keys)
			throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM VALUE_TABLE WHERE ")
				.append(COL_KEY_STRING).append(" IN (");
		for (int i = 0; i < keys.length; i++) {
			builder.append("'").append(KeyUtil.keyToString(keys[i]))
					.append("'");
			if (i != keys.length - 1) {
				builder.append(",");
			}
		}
		builder.append(")");

		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement(builder.toString());

			ResultSet rs = pre.executeQuery();
			return resultSetToEntities(rs);
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	static void makeValues(Connection conn, Key key,
			Map<String, Object> properties) throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = conn
					.prepareStatement("INSERT INTO VALUE_TABLE (KEY_STR, KIND, NAME, TYPE, SEQ, VAL_STR, VAL_INT, VAL_REAL, VAL_BYTES) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			for (String name : properties.keySet()) {
				Object obj = properties.get(name);
				if (obj instanceof Collection) {
					convListToValue(pre, key, name, (Collection<?>) obj);
				} else {
					convObjToValue(pre, key, name, obj, false);
				}
			}
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	static void convObjToValue(PreparedStatement pre, Key key, String name,
			Object obj, boolean isList) throws SQLException {
		pre.setString(1, KeyUtil.keyToString(key));
		pre.setString(2, key.getKind());
		pre.setString(3, name);

		if (obj == null) {
			pre.setString(4, isList ? T_L_NULL : T_NULL);
		} else if (obj instanceof String) {
			pre.setString(4, isList ? T_L_STRING : T_STRING);
			pre.setString(6, (String) obj);
		} else if (obj instanceof Byte) {
			pre.setString(4, isList ? T_L_LONG : T_LONG);
			pre.setByte(7, (Byte) obj);
		} else if (obj instanceof Short) {
			pre.setString(4, isList ? T_L_LONG : T_LONG);
			pre.setShort(7, (Short) obj);
		} else if (obj instanceof Integer) {
			pre.setString(4, isList ? T_L_LONG : T_LONG);
			pre.setInt(7, (Integer) obj);
		} else if (obj instanceof Long) {
			pre.setString(4, isList ? T_L_LONG : T_LONG);
			pre.setLong(7, (Long) obj);
		} else if (obj instanceof Boolean) {
			pre.setString(4, isList ? T_L_BOOLEAN : T_BOOLEAN);
			if ((Boolean) obj) {
				pre.setString(6, T_V_BOOLEAN_TRUE);
			} else {
				pre.setString(6, T_V_BOOLEAN_FALSE);
			}
		} else if (obj instanceof Float) {
			pre.setString(4, isList ? T_L_DOUBLE : T_DOUBLE);
			pre.setFloat(8, (Float) obj);
		} else if (obj instanceof Double) {
			pre.setString(4, isList ? T_L_DOUBLE : T_DOUBLE);
			pre.setDouble(8, (Double) obj);
		} else if (obj instanceof Key) {
			pre.setString(4, isList ? T_L_KEY : T_KEY);
			pre.setString(6, KeyUtil.keyToString((Key) obj));
		} else if (obj instanceof byte[]) {
			pre.setString(4, isList ? T_L_BYTES : T_BYTES);
			pre.setBytes(9, (byte[]) obj);
		} else {
			throw new UnsupportedPropertyException("property name=" + name
					+ " is not suppored type. type="
					+ obj.getClass().getCanonicalName());
		}

		pre.executeUpdate();
		pre.clearParameters();
	}

	static void convListToValue(PreparedStatement pre, Key key, String name,
			Collection<?> collection) throws SQLException {
		Object[] list = collection.toArray();
		if (collection.size() == 0) {
			pre.setString(1, KeyUtil.keyToString(key));
			pre.setString(2, key.getKind());
			pre.setString(3, name);
			pre.setString(4, T_L_BLANK);
			pre.setLong(5, 0);

			pre.executeUpdate();
			pre.clearParameters();

			return;
		} else {
			for (int i = 0; i < list.length; i++) {
				Object obj = list[i];
				convObjToValue(pre, key, name, obj, true);
			}
			return;
		}
	}
}
