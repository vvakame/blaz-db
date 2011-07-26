package net.vvakame.blaz.bare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.exception.EntityNotFoundException;
import net.vvakame.blaz.util.FilterChecker;

/**
 * KVSのラッパ.
 * @author vvakame
 */
public abstract class BareDatastore {

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、例外が発生する.
	 * @param key {@link Entity} のKey
	 * @return {@link Entity}
	 * @throws EntityNotFoundException Entityが取得できなかった場合
	 * @author vvakame
	 */
	public abstract Entity get(Key key) throws EntityNotFoundException;

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * 1つでもEntityが取得できなかった場合、例外が発生する.
	 * @param keys {@link Entity} のKeyのリスト
	 * @return {@link Entity}
	 * @throws EntityNotFoundException Entityが取得できなかった場合
	 * @author vvakame
	 */
	public List<Entity> get(Key... keys) throws EntityNotFoundException {
		List<Entity> entities = new ArrayList<Entity>();
		for (Key key : keys) {
			entities.add(get(key));
		}
		return entities;
	}

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、nullを返す.
	 * @param key
	 * @return {@link Entity}
	 * @author vvakame
	 */
	public abstract Entity getOrNull(Key key);

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、結果のMapには含まれない.
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
	 * @param entity
	 * @throws NullPointerException 引数にnullを与えると発生する
	 * @author vvakame
	 */
	public abstract void put(Entity entity) throws NullPointerException;

	/**
	 * {@link Entity} を保存する.
	 * @param entities
	 * @throws NullPointerException 引数にnullを与えると発生する
	 * @author vvakame
	 */
	public void put(Entity... entities) throws NullPointerException {
		for (Entity entity : entities) {
			put(entity);
		}
	}

	/**
	 * Entityを削除する
	 * @param key
	 * @author vvakame
	 */
	public abstract void delete(Key key);

	/**
	 * Entityを全て削除する
	 * @param keys
	 * @author vvakame
	 */
	public void delete(Key... keys) {
		for (Key key : keys) {
			delete(key);
		}
	}

	/**
	 * 指定の条件に合致する {@link Entity} を探して返す
	 * @param filters
	 * @return 見つかった {@link Entity}
	 * @author vvakame
	 */
	public abstract List<Entity> find(Filter... filters);

	/**
	 * 指定の条件に合致する {@link Entity} の {@link Key} を探して返す
	 * @param filters
	 * @return 見つかった {@link Entity} の {@link Key}
	 * @author vvakame
	 */
	public abstract List<Key> findAsKey(Filter... filters);

	/**
	 * データ操作に対するトランザクションを開始する.<br>
	 * トランザクション操作が提供されるか、どういう機能かは各バックエンドに依存する.
	 * @return トランザクション
	 * @author vvakame
	 */
	public abstract Transaction beginTransaction();

	/**
	 * 渡されたFilterのリストをクエリとして発行可能かチェックする.<br>
	 * 基本的なチェックは {@link FilterChecker#check(BareDatastore, Filter...)} にて行われる.
	 * @param filters
	 * @return クエリ発行可否
	 * @author vvakame
	 */
	public abstract boolean checkFilter(Filter... filters);
}
