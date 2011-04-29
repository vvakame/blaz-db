package net.vvakame.blaz;

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
	 * {@link Key} に対応する {@link Entity} を上書き保存する.
	 * @param key
	 * @param entity
	 * @author vvakame
	 */
	public void put(Key key, Entity entity);

	/**
	 * {@link Filter} を適用した
	 * @param m 
	 * @param <T> 
	 * @return Query
	 * @author vvakame
	 */
	public <T>ModelQuery<T> query(ModelMeta m);
}
