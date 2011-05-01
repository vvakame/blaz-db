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
	 * {@link Entity} を保存する.
	 * @param entity
	 * @author vvakame
	 */
	public void put(Entity entity);

	// EQ_MATCH
	// LT_MATCH
	// GT_MATCH
	// LEQ_MATCH
	// GEQ_MATCH
}
