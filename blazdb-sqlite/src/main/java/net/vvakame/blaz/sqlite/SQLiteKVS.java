package net.vvakame.blaz.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.util.FilterChecker;
import net.vvakame.blaz.util.KeyUtil;

/**
 * SQLiteによるKVSの実装
 * @author vvakame
 */
public class SQLiteKVS extends BareDatastore implements SqlTransaction.ActionCallback {

	// static final String DB_NAME = "blaz.db";
	static final String DB_NAME = ":memory:";

	Connection conn;


	/**
	 * the constructor.
	 * @category constructor
	 */
	public SQLiteKVS() {
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

			conn.setAutoCommit(false);

			onCreate();

		} catch (ClassNotFoundException e) {
			// TODO
			throw new RuntimeException(e);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}

		// TODO connection.close();
	}

	void onCreate() throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();

			// FIXME KEYにUniq制約が必要
			if (!DbUtil.checkTableExsists(stmt, Constants.TABLE_KEYS)) {
				stmt.executeUpdate("CREATE TABLE KEY_TABLE (ID INTEGER, NAME TEXT, KIND TEXT,KEY_STR TEXT)");
			}
			if (!DbUtil.checkTableExsists(stmt, Constants.TABLE_VALUES)) {
				stmt.executeUpdate("CREATE TABLE VALUE_TABLE (KEY_STR TEXT, KIND TEXT, NAME TEXT, TYPE TEXT, SEQ INTEGER, VAL_STR TEXT, VAL_INT INTEGER, VAL_REAL REAL, VAL_BYTES BLOB)");
			}
			stmt.executeUpdate("DROP INDEX IF EXISTS VALUE_KEY_STR");
			stmt.executeUpdate("CREATE INDEX VALUE_KEY_STR ON VALUE_TABLE(KEY_STR)");

			stmt.executeUpdate("DROP INDEX IF EXISTS VALUE_KIND_NAME");
			stmt.executeUpdate("CREATE INDEX VALUE_KIND_NAME ON VALUE_TABLE(KIND, NAME)");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	long getLatestId(String kind) {
		try {
			return KeysDao.getLatestId(conn, kind);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
	}

	@Override
	public void put(Entity entity) {
		if (entity == null) {
			throw new NullPointerException("entity is null.");
		}
		if (entity.getKey() != null) {
			delete(entity.getKey());
		} else {
			String kind = entity.getKind();
			long id = getLatestId(kind) + 1;
			Key key = KeyUtil.createKey(kind, id);
			entity.setKey(key);
		}
		try {
			KeysDao.insert(conn, entity.getKey());
			ValuesDao.insert(conn, entity);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("key is required.");
		}
		try {
			KeysDao.delete(conn, key);
			ValuesDao.delete(conn, key);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
	}

	@Override
	public Entity getOrNull(Key key) {
		try {
			if (!KeysDao.isExists(conn, key)) {
				return null;
			}
			Map<Key, Entity> map = ValuesDao.query(conn, key);
			if (map.containsKey(key)) {
				return map.get(key);
			} else {
				Entity entity = new Entity(key);
				return entity;
			}
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<Key, Entity> getAsMap(Iterable<Key> keys) {
		if (keys == null) {
			return new HashMap<Key, Entity>();
		}

		List<Key> keyList = KeyUtil.conv(keys);
		if (keyList.size() == 0) {
			return new HashMap<Key, Entity>();
		}

		Map<Key, Entity> resultMap = new HashMap<Key, Entity>();
		try {
			{ // for propertyless entity...
				List<Key> tmpKeyList = KeysDao.query(conn, keyList.toArray(new Key[] {}));
				if (tmpKeyList.size() == 0) {
					return resultMap;
				}
				for (Key key : tmpKeyList) {
					Entity entity = new Entity(key);
					resultMap.put(key, entity);
				}
			}

			resultMap.putAll(ValuesDao.query(conn, keyList.toArray(new Key[] {})));
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}

		return resultMap;
	}

	@Override
	public List<Key> findAsKey(Filter... filters) {
		if (checkFilter && !FilterChecker.check(this, filters)) {
			throw new IllegalArgumentException("invalid filter combination.");
		}

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
				builder.append(" ");
				QueryBuilder.makeQuery(filter, builder, args);
				builder.append(" ");
				if (i != filters.length - 1) {
					builder.append("INTERSECT");
				}
			}
		}

		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement(builder.toString());

			for (int i = 1; i <= args.size(); i++) {
				pre.setString(i, args.get(i - 1));
			}

			ResultSet c = pre.executeQuery();
			return KeysDao.resultSetToKeys(c);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		} finally {
			if (pre != null) {
				try {
					pre.close();
				} catch (SQLException e) {
					// TODO
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * データ操作に対するトランザクションを開始する.
	 * @return トランザクション
	 * @author vvakame
	 */
	@Override
	public Transaction beginTransaction() {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
		return new SqlTransaction(this);
	}

	@Override
	public boolean onCommit() {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public boolean onRollback() {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public boolean checkFilter(Filter... filters) {
		return true;
	}
}
