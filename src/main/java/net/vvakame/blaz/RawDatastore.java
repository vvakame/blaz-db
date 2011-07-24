package net.vvakame.blaz;

import java.util.List;

/**
 * {@link IKeyValueStore} 実装を利用したDatastore.
 * @author vvakame
 */
public class RawDatastore {

	static IKeyValueStore sKvs;


	/**
	 * 初期化
	 * @param kvs
	 * @author vvakame
	 */
	public static void init(IKeyValueStore kvs) {
		sKvs = kvs;
	}

	/**
	 * Entityを保存する.
	 * @param entity
	 * @author vvakame
	 * @see #init(IKeyValueStore)
	 */
	public static void put(Entity entity) {
		sKvs.put(entity);
	}

	/**
	 * Entityを取得する.<br>指定のKeyに対応するEntityが存在しない場合例外が発生する.
	 * @param key
	 * @return keyに対応するEntity
	 * @throws EntityNotFoundException Keyに体操するEntityが存在しなかった場合発生
	 * @author vvakame
	 */
	public static Entity get(Key key) throws EntityNotFoundException {
		return sKvs.get(key);
	}

	/**
	 * Entityを削除する
	 * @param key
	 * @author vvakame
	 */
	public static void delete(Key key) {
		sKvs.delete(key);
	}

	/**
	 * 指定の条件に合致する {@link Entity} を探して返す
	 * @param filters
	 * @return 見つかった {@link Entity}
	 * @author vvakame
	 */
	static List<Entity> find(Filter... filters) {
		return sKvs.find(filters);
	}

	/**
	 * データ操作に対するトランザクションを開始する.<br>
	 * トランザクション操作が提供されるか、どういう機能かは各バックエンドに依存する.
	 * @return トランザクション
	 * @author vvakame
	 */
	public static Transaction beginTransaction() {
		return sKvs.beginTransaction();
	}
}
