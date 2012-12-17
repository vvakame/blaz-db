package net.vvakame.blaz.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB操作のためのユーティリティクラス。
 * @author vvakame
 */
class DbUtil {

	/**
	 * テーブルが存在するかを確認する。
	 * @param conn
	 * @param tableName
	 * @return テーブルが存在するか否か
	 * @throws SQLException
	 * @author vvakame
	 */
	public static boolean checkTableExsists(Connection conn, String tableName) throws SQLException {
		if (isSQLiteConnection(conn)) {
			return checkTableExsistsForSQLite(conn, tableName);
		} else if (isH2DbConnection(conn)) {
			return checkTableExsistsForH2Db(conn, tableName);
		} else {
			throw new IllegalStateException("unknown JDBC " + conn.getClass().getCanonicalName());
		}
	}

	static boolean checkTableExsistsForSQLite(Connection connection, String tableName)
			throws SQLException {
		PreparedStatement ps =
				connection
					.prepareStatement("SELECT count(*) as count FROM sqlite_master WHERE type='table' AND name=?;");

		ps.setString(1, tableName);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			throw new IllegalStateException();
		}

		return rs.getInt("count") == 1;
	}

	static boolean checkTableExsistsForH2Db(Connection connection, String tableName)
			throws SQLException {
		PreparedStatement ps =
				connection
					.prepareStatement("select count(*) as count from information_schema.tables where table_type = 'TABLE' and table_name = ?");

		ps.setString(1, tableName);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			throw new IllegalStateException();
		}

		return rs.getInt("count") == 1;
	}

	/**
	 * ビューが存在するかを確認する。
	 * @param conn
	 * @param viewName
	 * @return ビューが存在するか否か
	 * @throws SQLException
	 * @author vvakame
	 */
	public static boolean checkViewExsists(Connection conn, String viewName) throws SQLException {
		if (isSQLiteConnection(conn)) {
			return checkViewExsistsForSQLite(conn, viewName);
		} else {
			throw new IllegalStateException("unknown JDBC " + conn.getClass().getCanonicalName());
		}
	}

	static boolean checkViewExsistsForSQLite(Connection connection, String viewName)
			throws SQLException {
		PreparedStatement ps =
				connection
					.prepareStatement("SELECT count(*) as count FROM sqlite_master WHERE type='view' AND name=?;");

		ps.setString(1, viewName);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			throw new IllegalStateException();
		}

		return rs.getInt("count") == 1;
	}

	/**
	 * インデックスが存在するかを確認する。
	 * @param conn
	 * @param indexName
	 * @return テーブルが存在するか否か
	 * @throws SQLException
	 * @author vvakame
	 */
	public static boolean checkIndexExsists(Connection conn, String indexName) throws SQLException {
		if (isSQLiteConnection(conn)) {
			return checkIndexExsistsForSQLite(conn, indexName);
		} else {
			throw new IllegalStateException("unknown JDBC " + conn.getClass().getCanonicalName());
		}
	}

	static boolean checkIndexExsistsForSQLite(Connection connection, String indexName)
			throws SQLException {
		PreparedStatement ps =
				connection
					.prepareStatement("SELECT count(*) as count FROM sqlite_master WHERE type='index' AND name=?;");

		ps.setString(1, indexName);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			throw new IllegalStateException();
		}

		return rs.getInt("count") == 1;
	}

	static boolean isSQLiteConnection(Connection conn) throws SQLException {
		final String url = conn.getMetaData().getURL();
		return url.startsWith("jdbc:sqlite:");
	}

	static boolean isH2DbConnection(Connection conn) throws SQLException {
		final String url = conn.getMetaData().getURL();
		return url.startsWith("jdbc:h2:");
	}
}
