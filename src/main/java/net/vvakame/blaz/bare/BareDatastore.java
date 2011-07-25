package net.vvakame.blaz.bare;

import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.exception.EntityNotFoundException;

/**
 * KVSのラッパ.
 * @author vvakame
 */
public abstract class BareDatastore {

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * Entityが取得できなかった場合、例外が発生する.
	 * @param key
	 * @return {@link Entity}
	 * @throws EntityNotFoundException Entityが取得できなかった場合
	 * @author vvakame
	 */
	public abstract Entity get(Key key) throws EntityNotFoundException;

	/**
	 * {@link Key} を元に {@link Entity} を取得する.<br>
	 * 1つでもEntityが取得できなかった場合、例外が発生する.
	 * @param key
	 * @return {@link Entity}
	 * @throws EntityNotFoundException Entityが取得できなかった場合
	 * @author vvakame
	 */
	public abstract List<Entity> get(Key... key) throws EntityNotFoundException;

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
	public abstract Map<Key, Entity> getAsMap(Iterable<Key> keys);

	/**
	 * {@link Entity} を保存する.
	 * @param entity
	 * @throws NullPointerException 引数にnullを与えると発生する
	 * @author vvakame
	 */
	public abstract void put(Entity entity) throws NullPointerException;

	/**
	 * Entityを削除する
	 * @param key
	 * @author vvakame
	 */
	public abstract void delete(Key key);

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
}
