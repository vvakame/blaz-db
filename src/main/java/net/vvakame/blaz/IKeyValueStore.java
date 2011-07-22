package net.vvakame.blaz;

import java.util.List;

/**
 * KVSのラッパ.
 * @author vvakame
 */
public interface IKeyValueStore {

	/**
	 * {@link Key} を元に {@link Entity} を取得する.
	 * @param key
	 * @return {@link Entity}
	 * @author vvakame
	 */
	public Entity get(Key key);

	/**
	 * {@link Entity} を保存する.
	 * @param entity
	 * @author vvakame
	 */
	public void put(Entity entity);

	/**
	 * 指定の条件に合致する {@link Entity} を探して返す
	 * @param filters
	 * @return 見つかった {@link Entity}
	 * @author vvakame
	 */
	public List<Entity> find(IFilter... filters);

	/**
	 * 指定の条件に合致する {@link Entity} の {@link Key} を探して返す
	 * @param filters
	 * @return 見つかった {@link Entity} の {@link Key}
	 * @author vvakame
	 */
	public List<Key> findAsKey(IFilter... filters);
}
