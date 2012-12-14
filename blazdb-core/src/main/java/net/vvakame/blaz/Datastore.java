package net.vvakame.blaz;

import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.exception.EntityNotFoundException;
import net.vvakame.blaz.meta.DatastoreUtil;
import net.vvakame.blaz.meta.ModelMeta;
import net.vvakame.blaz.meta.ModelQuery;

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
	 * 現在利用している {@link BareDatastore} を返す
	 * @return {@link BareDatastore}
	 * @author vvakame
	 */
	public static BareDatastore getBareDatastore() {
		return kvs;
	}

	/**
	 * モデルの保存.
	 * @param model
	 * @author vvakame
	 */
	@SuppressWarnings("unchecked")
	public static void put(Object model) {
		Entity entity = DatastoreUtil.modelToEntity(model);
		kvs.put(entity);
		@SuppressWarnings("rawtypes")
		ModelMeta meta = DatastoreUtil.getModelMeta(model.getClass());
		meta.setKey(model, entity.getKey());
	}

	/**
	 * モデルの取得.
	 * @param meta
	 * @param key
	 * @return モデル
	 * @throws EntityNotFoundException モデルが取得できなかった時 
	 * @author vvakame
	 */
	public static <M>M get(ModelMeta<M> meta, Key key) throws EntityNotFoundException {
		Entity entity = kvs.get(key);
		return meta.entityToModel(entity);
	}

	/**
	 * モデルの取得.
	 * @param meta
	 * @param key
	 * @return モデル or null
	 * @author vvakame
	 */
	public static <M>M getOrNull(ModelMeta<M> meta, Key key) {
		Entity entity = kvs.getOrNull(key);
		return meta.entityToModel(entity);
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
