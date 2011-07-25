package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.exception.EntityNotFoundException;
import net.vvakame.blaz.util.KeyUtil;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLiteによるKVSの実装
 * @author vvakame
 */
public class SQLiteKVS extends BareDatastore implements SqlTransaction.ActionCallback {

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
		KeysDao.insert(mDb, entity.getKey());
		ValuesDao.insert(mDb, entity);
	}

	@Override
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("key is required.");
		}
		KeysDao.delete(mDb, key);
		ValuesDao.delete(mDb, key);
	}

	@Override
	public Entity get(Key key) {
		Entity entity = getOrNull(key);
		if (entity == null) {
			throw new EntityNotFoundException("key=" + key.toString() + " is not found.");
		}

		return entity;
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

	@Override
	public Entity getOrNull(Key key) {
		if (!KeysDao.isExists(mDb, key)) {
			return null;
		}
		Entity entity = ValuesDao.cursorToEntityAsSingle(key, ValuesDao.query(mDb, key));

		return entity;
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

		{ // for propertyless entity...
			Cursor c = KeysDao.query(mDb, keyList.toArray(new Key[] {}));
			if (!c.moveToFirst()) {
				return resultMap;
			}
			List<Key> tmpKeyList = KeysDao.cursorToKeys(c);
			for (Key key : tmpKeyList) {
				Entity entity = new Entity();
				entity.setKey(key);
				resultMap.put(key, entity);
			}
		}

		Cursor c = ValuesDao.query(mDb, keyList.toArray(new Key[] {}));
		resultMap.putAll(ValuesDao.cursorToEntities(c));

		return resultMap;
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

		return KeysDao.cursorToKeys(c);
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
}
