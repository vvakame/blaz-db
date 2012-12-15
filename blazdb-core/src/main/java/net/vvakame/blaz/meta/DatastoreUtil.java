package net.vvakame.blaz.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Entity;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.bare.DatastoreHook;

/**
 * {@link Datastore} のためのユーティリティクラス.
 * @author vvakame
 */
public class DatastoreUtil {

	static Map<String, ModelMeta<?>> modelMetaCache = new HashMap<String, ModelMeta<?>>();


	/**
	 * 存在する {@link ModelMeta} のキャッシュを作成する.<br>
	 * JVM初期化時に利用される {@link ModelMeta} は必ず本メソッドを経由し登録されねばならない.
	 * @param clazzString クラスのFQN, または固有の名前
	 * @param meta 保持する {@link ModelMeta}
	 * @author vvakame
	 */
	public static void addMetaCache(String clazzString, ModelMeta<?> meta) {
		modelMetaCache.put(clazzString, meta);
	}

	/**
	 * model を {@link Entity} に変換し返す.
	 * @param model
	 * @return 変換後の {@link Entity}
	 * @author vvakame
	 */
	@SuppressWarnings({
		"unchecked",
		"rawtypes"
	})
	public static Entity modelToEntity(Object model) {
		ModelMeta modelMeta = getModelMeta(model.getClass());
		return modelMeta.modelToEntity(model);
	}

	/**
	 * modelClass を元に {@link ModelMeta} を取得し、返す.<br>
	 * 本メソッドで {@link ModelMeta} を取得するには、事前に {@link #addMetaCache(String, ModelMeta)} で登録されている必要がある.
	 * @param modelClass
	 * @return 取得した {@link ModelMeta}
	 * @author vvakame
	 */
	@SuppressWarnings("unchecked")
	public static <M>ModelMeta<M> getModelMeta(Class<M> modelClass) {
		return (ModelMeta<M>) modelMetaCache.get(modelClass.getCanonicalName());
	}

	/**
	 * modelClassName を元に {@link ModelMeta} を取得し、返す.<br>
	 * 本メソッドで {@link ModelMeta} を取得するには、事前に {@link #addMetaCache(String, ModelMeta)} で登録されている必要がある.
	 * @param modelClassName
	 * @return 取得した {@link ModelMeta}
	 * @author vvakame
	 */
	@SuppressWarnings("unchecked")
	public static <M>ModelMeta<M> getModelMeta(String modelClassName) {
		return (ModelMeta<M>) modelMetaCache.get(modelClassName);
	}

	/**
	 * Setup BareDatatore.
	 * @param kvs
	 * @author vvakame
	 */
	public static void setUp(BareDatastore kvs) {
		if (kvs instanceof DatastoreHook) {
			ArrayList<ModelMeta<?>> list = new ArrayList<ModelMeta<?>>(modelMetaCache.values());
			((DatastoreHook) kvs).onSetup(list);
		}
	}
}
