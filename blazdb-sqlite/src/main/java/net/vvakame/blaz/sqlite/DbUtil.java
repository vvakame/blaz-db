package net.vvakame.blaz.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB操作のためのユーティリティクラス。
 * @author vvakame
 */
class DbUtil {

	/**
	 * テーブルが存在するかを確認する。
	 * @param statement
	 * @param tableName
	 * @return テーブルが存在するか否か
	 * @throws SQLException
	 * @author vvakame
	 */
	public static boolean checkTableExsists(Statement statement, String tableName)
			throws SQLException {
		Connection connection = statement.getConnection();
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

	/**
	 * ビューが存在するかを確認する。
	 * @param statement
	 * @param viewName
	 * @return ビューが存在するか否か
	 * @throws SQLException
	 * @author vvakame
	 */
	public static boolean checkViewExsists(Statement statement, String viewName)
			throws SQLException {
		Connection connection = statement.getConnection();
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
	 * @param statement
	 * @param indexName
	 * @return テーブルが存在するか否か
	 * @throws SQLException
	 * @author vvakame
	 */
	public static boolean checkIndexExsists(Statement statement, String indexName)
			throws SQLException {
		Connection connection = statement.getConnection();
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
}
