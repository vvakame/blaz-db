package net.vvakame.sample;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.bare.BareDatastore;

/**
 * DB.<br>
 * 利用する前に {@link #setupDatastore(BareDatastore)} を呼出し、適切な {@link BareDatastore} をセットアップする必要がある.
 * @author vvakame
 */
public class Datastore {

	static BareDatastore kvs = null;


	/**
	 * 利用する {@link BareDatastore} の設定
	 * @param kvs 利用する {@link BareDatastore}
	 * @author vvakame
	 */
	public static void setupDatastore(BareDatastore kvs) {
		Datastore.kvs = kvs;
	}

	/**
	 * モデルの保存.
	 * @param model
	 * @author vvakame
	 */
	public static void put(Object model) {
		Entity entity = DatastoreUtil.modelToEntity(model);
		kvs.put(entity);
	}

	/**
	 * DBに対するクエリ発行の起点
	 * @param meta
	 * @return クエリビルダー
	 * @author vvakame
	 */
	public static <M>ModelQuery<M> query(ModelMeta<M> meta) {
		return new ModelQuery<M>(meta);
	}
}
