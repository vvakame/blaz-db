package net.vvakame.blaz.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;

/**
 * モデルと {@link Entity} のヒモ付や、検索条件、ソート条件の指定を行うためのクラス.
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
		DatastoreUtil.modelMetaCache.put(getModelClass().getCanonicalName(), this);

		if (meta == null || meta.classHierarchyList == null) {
			classHierarchyList = Collections.unmodifiableList(new ArrayList<String>());
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
	 * @return モデルの {@link Class}
	 * @author vvakame
	 */
	public abstract Class<M> getModelClass();

	/**
	 * モデルを {@link Entity} として保存した時のKindを取得する.
	 * @return Kind名
	 * @author vvakame
	 */
	public abstract String getKind();

	/**
	 * モデルに対して指定された {@link Key} をセットする.
	 * @param model {@link Key} をセットされるモデル
	 * @param key セットする {@link Key}
	 * @author vvakame
	 */
	public abstract void setKey(M model, Key key);

	/**
	 * モデルに対して指定された {@link Key} を取得する.
	 * @param model {@link Key} を取得するモデル
	 * @return 取得した {@link Key}
	 * @author vvakame
	 */
	public abstract Key getKey(M model);

	/**
	 * モデルから {@link Entity} への変換を行う.<br>
	 * もし、子孫クラスのモデルの場合、適切な {@link ModelMeta} に処理を委譲する.
	 * @param model 変換元モデル
	 * @return 変換後 {@link Entity}
	 * @author vvakame
	 */
	public abstract Entity modelToEntity(M model);

	/**
	 * {@link Entity} からモデルへの変換を行う.<br>
	 * もし、子孫クラスのモデルの場合、適切な {@link ModelMeta} に処理を委譲する.
	 * @param entity 変換元 {@link Entity}
	 * @return 変換後モデル
	 * @author vvakame
	 */
	public abstract M entityToModel(Entity entity);
}
