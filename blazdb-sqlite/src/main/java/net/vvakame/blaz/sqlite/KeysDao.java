package net.vvakame.blaz.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.util.KeyUtil;
import static net.vvakame.blaz.sqlite.Constants.*;

class KeysDao {

	private KeysDao() {
	}

	public static Key resultSetToKeyAsSingle(ResultSet rs) throws SQLException {
		if (!rs.first()) {
			return null;
		}
		Key newKey = new Key();
		newKey.setKind(rs.getString(COL_KIND));
		newKey.setId(rs.getLong(COL_ID));
		newKey.setName(rs.getString(COL_NAME));

		return newKey;
	}

	public static List<Key> resultSetToKeys(ResultSet rs) throws SQLException {
		List<Key> keyList = new ArrayList<Key>();
		if (!rs.next()) {
			return keyList;
		}

		do {
			String keyStr = rs.getString(COL_KEY_STRING);
			keyList.add(KeyUtil.stringToKey(keyStr));
		} while (rs.next());

		return keyList;
	}

	public static void insert(Connection conn, Key key) throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement("INSERT INTO " + TABLE_KEYS + " ("
					+ COL_ID + ", " + COL_NAME + ", " + COL_KIND + ", "
					+ COL_KEY_STRING + ") values (?, ?, ?, ?)");

			if (key.getName() == null) {
				pre.setLong(1, key.getId());
				pre.setNull(2, Types.VARCHAR);
			} else {
				pre.setNull(1, Types.INTEGER);
				pre.setString(2, key.getName());
			}
			pre.setString(3, key.getKind());
			pre.setString(4, KeyUtil.keyToString(key));

			pre.executeUpdate();
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	public static void delete(Connection conn, Key key) throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement("DELETE FROM " + TABLE_KEYS + " WHERE "
					+ COL_KEY_STRING + " = ?");

			pre.setString(1, KeyUtil.keyToString(key));

			pre.executeUpdate();
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	public static List<Key> query(Connection conn, Key... keys)
			throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM " + TABLE_KEYS + " WHERE ")
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

			return resultSetToKeys(pre.executeQuery());
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	public static long getLatestId(Connection conn, String kind)
			throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement("SELECT max(" + COL_ID
					+ ") as maxValue FROM " + TABLE_KEYS + " WHERE " + COL_KIND
					+ " = ?");

			pre.setString(1, kind);

			ResultSet resultSet = pre.executeQuery();
			resultSet.next();
			return resultSet.getLong(1);
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}

	public static boolean isExists(Connection conn, Key key)
			throws SQLException {
		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement("SELECT count(*) as cnt FROM "
					+ TABLE_KEYS + " WHERE " + COL_KEY_STRING + " = ?");

			pre.setString(1, KeyUtil.keyToString(key));

			ResultSet resultSet = pre.executeQuery();
			resultSet.next();
			return resultSet.getLong(1) != 0;
		} finally {
			if (pre != null) {
				pre.close();
			}
		}
	}
}
