package net.vvakame.blaz.bare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.exception.EntityNotFoundException;
import net.vvakame.blaz.util.FilterChecker;

/**
 * KVSのラッパ.
 * 
 * @author vvakame
 */
public abstract class BareDatastore {

	protected boolean checkFilter = false;

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、例外が発生する.
	 * 
	 * @param key
	 *            {@link Entity} のKey
	 * @return {@link Entity}
	 * @throws EntityNotFoundException
	 *             Entityが取得できなかった場合
	 * @author vvakame
	 */
	public Entity get(Key key) throws EntityNotFoundException {
		Entity entity = getOrNull(key);
		if (entity == null) {
			throw new EntityNotFoundException("key=" + key.toString()
					+ " is not found.");
		}

		return entity;
	}

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * 1つでもEntityが取得できなかった場合、例外が発生する.
	 * 
	 * @param keys
	 *            {@link Entity} のKeyのリスト
	 * @return {@link Entity}
	 * @throws EntityNotFoundException
	 *             Entityが取得できなかった場合
	 * @author vvakame
	 */
	public List<Entity> get(Key... keys) throws EntityNotFoundException {
		List<Key> keyList = Arrays.asList(keys);
		Map<Key, Entity> entities = getAsMap(keyList);
		List<Entity> resultList = new ArrayList<Entity>(entities.values());
		if (keyList.size() == entities.size()) {
			return resultList;
		}
		throw new EntityNotFoundException("size expected=" + keyList.size()
				+ ", but got=" + entities.size());
	}

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、nullを返す.
	 * 
	 * @param key
	 * @return {@link Entity}
	 * @author vvakame
	 */
	public abstract Entity getOrNull(Key key);

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、結果のMapには含まれない.
	 * 
	 * @param keys
	 * @return {@link Key} と対応する {@link Entity} の {@link Map}
	 * @author vvakame
	 */
	public Map<Key, Entity> getAsMap(Iterable<Key> keys) {
		Map<Key, Entity> entityMap = new HashMap<Key, Entity>();
		for (Key key : keys) {
			Entity entity = getOrNull(key);
			if (entity != null) {
				entityMap.put(key, entity);
			}
		}
		return entityMap;
	}

	/**
	 * {@link Entity} を保存する.
	 * 
	 * @param entity
	 * @throws NullPointerException
	 *             引数にnullを与えると発生する
	 * @author vvakame
	 */
	public abstract void put(Entity entity) throws NullPointerException;

	/**
	 * {@link Entity} を保存する.
	 * 
	 * @param entities
	 * @throws NullPointerException
	 *             引数にnullを与えると発生する
	 * @author vvakame
	 */
	public void put(Entity... entities) throws NullPointerException {
		for (Entity entity : entities) {
			put(entity);
		}
	}

	/**
	 * Entityを削除する
	 * 
	 * @param key
	 * @author vvakame
	 */
	public abstract void delete(Key key);

	/**
	 * Entityを全て削除する
	 * 
	 * @param keys
	 * @author vvakame
	 */
	public void delete(Key... keys) {
		for (Key key : keys) {
			delete(key);
		}
	}

	/**
	 * 指定の条件に合致する {@link Entity} を探して返す。
	 * 
	 * @param filters
	 * @return 見つかった {@link Entity}
	 * @author vvakame
	 */
	public List<Entity> find(Filter... filters) {
		return find(filters, null);
	}

	/**
	 * 指定の条件に合致する {@link Entity} を探した後、ソートして返す。<br>
	 * 同一プロパティに複数の型がある場合、異なる型の間のソート可否や、ソート順は保証されない。<br>
	 * 
	 * @param filters
	 * @param sorters
	 * @return 見つかった {@link Entity}
	 * @author vvakame
	 */
	public List<Entity> find(Filter[] filters, Sorter[] sorters) {
		// find または findAsKey は独自の実装を用意すること

		List<Key> keys = findAsKey(filters, sorters);
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
				Entity entity = new Entity(key);
				resultList.add(entity);
			}
		}

		BareSortUtil.sort(resultList, sorters);

		return resultList;
	}

	/**
	 * 指定の条件に合致する {@link Entity} の {@link Key} を探して返す。
	 * 
	 * @param filters
	 * @return 見つかった {@link Entity} の {@link Key}
	 * @author vvakame
	 */
	public List<Key> findAsKey(Filter... filters) {
		return findAsKey(filters, null);
	}

	/**
	 * 指定の条件に合致する {@link Entity} の {@link Key} を探して返す。
	 * 同一プロパティに複数の型がある場合、異なる型の間のソート可否や、ソート順は保証されない。<br>
	 * 
	 * @param filters
	 * @param sorters
	 * @return 見つかった {@link Entity} の {@link Key}
	 * @author vvakame
	 */
	public List<Key> findAsKey(Filter[] filters, Sorter[] sorters) {
		// find または findAsKey は独自の実装を用意すること

		List<Entity> entities = find(filters, sorters);
		if (entities == null || entities.size() == 0) {
			return new ArrayList<Key>();
		}

		List<Key> keys = new ArrayList<Key>();
		for (Entity entity : entities) {
			keys.add(entity.getKey());
		}
		return keys;
	}

	/**
	 * データ操作に対するトランザクションを開始する.<br>
	 * トランザクション操作が提供されるか、どういう機能かは各バックエンドに依存する.
	 * 
	 * @return トランザクション
	 * @author vvakame
	 */
	public abstract Transaction beginTransaction();

	/**
	 * 渡されたFilterのリストをクエリとして発行可能かチェックする.<br>
	 * 基本的なチェックは {@link FilterChecker#check(BareDatastore, Filter...)} にて行われる.
	 * 
	 * @param filters
	 * @return クエリ発行可否
	 * @author vvakame
	 */
	public boolean checkFilter(Filter... filters) {
		return checkFilter(filters, null);
	}

	/**
	 * 渡されたFilterのリストをクエリとして発行可能かチェックする.<br>
	 * 基本的なチェックは {@link FilterChecker#check(BareDatastore, Filter...)} にて行われる.
	 * 
	 * @param filters
	 * @param sorters
	 * @return クエリ発行可否
	 * @author vvakame
	 */
	public abstract boolean checkFilter(Filter[] filters, Sorter[] sorters);

	/**
	 * 渡されたEntityのリストを指定のSorterに基づきソートする。
	 * 
	 * @param entities
	 * @param sorters
	 * @author vvakame
	 */
	public void sort(List<Entity> entities, Sorter[] sorters) {
		BareSortUtil.sort(entities, sorters);
	}

	/**
	 * フィルタの組み合わせチェックを行うかを設定する.
	 * 
	 * @param check
	 * @author vvakame
	 */
	public void setCheckFilter(boolean check) {
		this.checkFilter = check;
	}

	/**
	 * フィルタの組み合わせチェックの設定を取得する.
	 * 
	 * @return チェックを行うか否か
	 * @author vvakame
	 */
	public boolean getCheckFilter() {
		return this.checkFilter;
	}
}
