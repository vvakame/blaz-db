package net.vvakame.blaz.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;

/**
 * モデルと {@link Entity} のヒモ付や、検索条件、ソート条件の指定を行うためのクラス.
 * 
 * @author vvakame
 * @param <M>
 */
public abstract class ModelMeta<M> {

	/** {@link Key} を保持するプロパティ名. */
	public static final String PROPERTY_KEY = "__key__";

	/** モデルの継承階層を保持するプロパティ名. */
	public static final String PROPERTY_CLASS_HIERARCHY = "__class_hierarchy__";

	/** モデルのスキーマリビジョンを保持するプロパティ名. */
	public static final String PROPERTY_SCHEMA = "__schema__";

	protected List<String> classHierarchyList;

	protected ModelMeta() {
		init(null);
	}

	protected ModelMeta(ModelMeta<?> meta) {
		init(meta);
	}

	private void init(ModelMeta<?> meta) {
		DatastoreUtil.modelMetaCache.put(getModelClass().getCanonicalName(),
				this);

		if (meta == null || meta.classHierarchyList == null) {
			classHierarchyList = Collections
					.unmodifiableList(new ArrayList<String>());
		} else {
			List<String> list = new ArrayList<String>();
			list.addAll(meta.classHierarchyList);
			list.add(getModelName());
			classHierarchyList = Collections.unmodifiableList(list);
		}
	}

	protected String getModelName() {
		return getModelClass().getCanonicalName();
	}

	/**
	 * モデルの {@link Class} を取得する.
	 * 
	 * @return モデルの {@link Class}
	 * @author vvakame
	 */
	public abstract Class<M> getModelClass();

	/**
	 * モデルを {@link Entity} として保存した時のKindを取得する.
	 * 
	 * @return Kind名
	 * @author vvakame
	 */
	public abstract String getKind();

	/**
	 * モデルに対して指定された {@link Key} をセットする.
	 * 
	 * @param model
	 *            {@link Key} をセットされるモデル
	 * @param key
	 *            セットする {@link Key}
	 * @author vvakame
	 */
	public abstract void setKey(M model, Key key);

	/**
	 * モデルに対して指定された {@link Key} を取得する.
	 * 
	 * @param model
	 *            {@link Key} を取得するモデル
	 * @return 取得した {@link Key}
	 * @author vvakame
	 */
	public abstract Key getKey(M model);

	/**
	 * この {@link ModelMeta} が持つ全てのプロパティの一覧を返す。
	 * 
	 * @return プロパティの一覧
	 * @author vvakame
	 */
	public abstract List<CoreAttributeMeta<?>> getProperties();

	/**
	 * モデルから {@link Entity} への変換を行う.<br>
	 * もし、子孫クラスのモデルの場合、適切な {@link ModelMeta} に処理を委譲する.
	 * 
	 * @param model
	 *            変換元モデル
	 * @return 変換後 {@link Entity}
	 * @author vvakame
	 */
	public abstract Entity modelToEntity(M model);

	/**
	 * {@link Entity} からモデルへの変換を行う.<br>
	 * もし、子孫クラスのモデルの場合、適切な {@link ModelMeta} に処理を委譲する.
	 * 
	 * @param entity
	 *            変換元 {@link Entity}
	 * @return 変換後モデル
	 * @author vvakame
	 */
	public abstract M entityToModel(Entity entity);

	/**
	 * Entityの指定されたPropertyをListとして取り出す。
	 * 
	 * @param clazz
	 * @param entity
	 * @param propertyName
	 * @return {@link List}
	 * @author vvakame
	 */
	public <T> List<T> toList(Class<T> clazz, Entity entity, String propertyName) {
		Object property = entity.getProperty(propertyName);
		if (property == null) {
			return null;
		}

		if (property instanceof Collection == false) {
			throw new IllegalArgumentException(propertyName
					+ " is not Collection");
		}

		@SuppressWarnings("unchecked")
		Collection<T> originals = (Collection<T>) property;
		return new ArrayList<T>(originals);
	}

	/**
	 * Entityの指定されたPropertyをListとして取り出す。
	 * 
	 * @param entity
	 * @param propertyName
	 * @return {@link List}
	 * @author vvakame
	 */
	public List<Byte> toByteList(Entity entity, String propertyName) {
		Object property = entity.getProperty(propertyName);
		if (property == null) {
			return null;
		}

		if (property instanceof Collection == false) {
			throw new IllegalArgumentException(propertyName
					+ " is not Collection");
		}

		@SuppressWarnings("unchecked")
		Collection<Long> originals = (Collection<Long>) property;
		List<Byte> resultList = new ArrayList<Byte>(originals.size());

		for (Long value : originals) {
			resultList.add(value != null ? value.byteValue() : null);
		}

		return resultList;
	}

	/**
	 * Entityの指定されたPropertyをListとして取り出す。
	 * 
	 * @param entity
	 * @param propertyName
	 * @return {@link List}
	 * @author vvakame
	 */
	public List<Short> toShortList(Entity entity, String propertyName) {
		Object property = entity.getProperty(propertyName);
		if (property == null) {
			return null;
		}

		if (property instanceof Collection == false) {
			throw new IllegalArgumentException(propertyName
					+ " is not Collection");
		}

		@SuppressWarnings("unchecked")
		Collection<Long> originals = (Collection<Long>) property;
		List<Short> resultList = new ArrayList<Short>(originals.size());

		for (Long value : originals) {
			resultList.add(value != null ? value.shortValue() : null);
		}

		return resultList;
	}

	/**
	 * Entityの指定されたPropertyをListとして取り出す。
	 * 
	 * @param entity
	 * @param propertyName
	 * @return {@link List}
	 * @author vvakame
	 */
	public List<Integer> toIntegerList(Entity entity, String propertyName) {
		Object property = entity.getProperty(propertyName);
		if (property == null) {
			return null;
		}

		if (property instanceof Collection == false) {
			throw new IllegalArgumentException(propertyName
					+ " is not Collection");
		}

		@SuppressWarnings("unchecked")
		Collection<Long> originals = (Collection<Long>) property;
		List<Integer> resultList = new ArrayList<Integer>(originals.size());

		for (Long value : originals) {
			resultList.add(value != null ? value.intValue() : null);
		}

		return resultList;
	}

	/**
	 * Entityの指定されたPropertyをListとして取り出す。
	 * 
	 * @param entity
	 * @param propertyName
	 * @return {@link List}
	 * @author vvakame
	 */
	public List<Float> toFloatList(Entity entity, String propertyName) {
		Object property = entity.getProperty(propertyName);
		if (property == null) {
			return null;
		}

		if (property instanceof Collection == false) {
			throw new IllegalArgumentException(propertyName
					+ " is not Collection");
		}

		@SuppressWarnings("unchecked")
		Collection<Double> originals = (Collection<Double>) property;
		List<Float> resultList = new ArrayList<Float>(originals.size());

		for (Double value : originals) {
			resultList.add(value != null ? value.floatValue() : null);
		}

		return resultList;
	}
}
