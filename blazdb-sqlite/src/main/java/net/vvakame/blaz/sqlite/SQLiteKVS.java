package net.vvakame.blaz.sqlite;

import java.io.Closeable;
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
import net.vvakame.blaz.bare.DatastoreHook;
import net.vvakame.blaz.meta.ModelMeta;
import net.vvakame.blaz.meta.PropertyAttributeMeta;
import net.vvakame.blaz.util.FilterChecker;
import net.vvakame.blaz.util.KeyUtil;

/**
 * SQLiteによるKVSの実装
 * @author vvakame
 */
public class SQLiteKVS extends BareDatastore implements SqlTransaction.ActionCallback, Closeable,
		DatastoreHook {

	static final String DB_NAME = "blaz.sqlite";

	// static final String DB_NAME = ":memory:";

	Connection conn;


	/**
	 * the constructor.
	 * @category constructor
	 */
	public SQLiteKVS() {
		this(DB_NAME);
	}

	/**
	 * the constructor.
	 * @param dbName Database file name
	 * @category constructor
	 */
	public SQLiteKVS(String dbName) {
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);

			conn.setAutoCommit(true);

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

	@Override
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO
			throw new RuntimeException(e);
		}
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
			if (!DbUtil.checkIndexExsists(stmt, "VALUE_KEY_STR")) {
				stmt.executeUpdate("CREATE INDEX VALUE_KEY_STR ON VALUE_TABLE(KEY_STR)");
			}
			if (!DbUtil.checkIndexExsists(stmt, "VALUE_KIND_NAME")) {
				stmt.executeUpdate("CREATE INDEX VALUE_KIND_NAME ON VALUE_TABLE(KIND, NAME)");
			}
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

	@Override
	public void onSetup(List<ModelMeta<?>> metas) {
		for (ModelMeta<?> meta : metas) {
			StringBuilder select = new StringBuilder();
			StringBuilder from = new StringBuilder();
			StringBuilder where1 = new StringBuilder();
			StringBuilder where2 = new StringBuilder();

			final String kind = meta.getKind();
			String firstName = null;
			List<PropertyAttributeMeta<?>> properties = meta.getProperties();
			for (PropertyAttributeMeta<?> p : properties) {
				final String name = p.getName();
				final Class<?> clazz = p.getPropertyClass();

				// *name*.key_str as key_str
				if (firstName == null) {
					select.append(name).append(".key_str as key_str,");
				}

				// *name*.val as *name*,
				select.append(name).append(".val as ").append(name).append(",");

				// (select VAL_??? as val, kind, key_str from VALUE_TABLE where name = '*name*') *name*,
				from.append("(select VAL_");
				if (Long.class.equals(clazz) || Integer.class.equals(clazz)
						|| Short.class.equals(clazz) || Byte.class.equals(clazz)) {
					from.append("INT");
				} else if (Double.class.equals(clazz) || Float.class.equals(clazz)) {
					from.append("REAL");
				} else if (String.class.equals(clazz) || Boolean.class.equals(clazz)) {
					from.append("STR");
				} else if (byte[].class.equals(clazz)) {
					from.append("BYTES");
				} else if (Key.class.equals(clazz)) {
					from.append("STR");
				} else {
					throw new IllegalStateException("unknown class = " + clazz.getCanonicalName());
				}

				from.append(" as val,  kind, key_str from VALUE_TABLE where name = '").append(name)
					.append("') ").append(name).append(",");

				// and *name*.kind = '*kind*'
				if (firstName != null) {
					where1.append("and ");
				}
				where1.append(name).append(".kind = '").append(kind).append("' ");

				// and firstName.key_str = name.key_str
				if (firstName != null) {
					where2.append("and ").append(firstName).append(".key_str = ").append(name)
						.append(".key_str ");
				}

				if (firstName == null) {
					firstName = name;
				}
			}
			// cut last ','
			select.setLength(select.length() - 1);
			from.setLength(from.length() - 1);

			StringBuilder sql = new StringBuilder();
			sql.append("CREATE VIEW ").append(kind).append(" as ");
			sql.append("select ").append(select).append(" ");
			sql.append("from ").append(from).append(" ");
			sql.append("where ").append(where1).append(where2);

			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				if (!DbUtil.checkViewExsists(stmt, kind)) {
					stmt.close();
					stmt = conn.createStatement();
					stmt.executeUpdate(sql.toString());
				}
			} catch (SQLException e) {
				// TODO
				throw new RuntimeException(e);
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						// TODO
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
}
