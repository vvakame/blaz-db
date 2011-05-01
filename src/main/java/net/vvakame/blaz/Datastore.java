package net.vvakame.blaz;

/**
 * {@link IKeyValueStore} 実装を利用したDatastore.
 * @author vvakame
 */
public class Datastore {

	static IKeyValueStore sKvs;


	/**
	 * 初期化
	 * @param kvs
	 * @author vvakame
	 */
	public static void init(IKeyValueStore kvs) {
		sKvs = kvs;
	}

	public static void put(Entity entity) {
		sKvs.put(entity);
	}

	public static Entity get(Key key) {
		return sKvs.get(key);
	}
}
