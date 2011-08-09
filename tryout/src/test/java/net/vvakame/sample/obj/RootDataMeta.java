package net.vvakame.sample.obj;

import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.sample.Datastore;
import net.vvakame.sample.DatastoreUtil;
import net.vvakame.sample.KeyAttributeMeta;
import net.vvakame.sample.ModelMeta;
import net.vvakame.sample.PropertyAttributeMeta;

/**
 * {@link RootData} の {@link ModelMeta} 表現.<br>
 * {@link Datastore} の各種メソッドに対して渡して利用する.
 * @author vvakame
 */
public class RootDataMeta extends ModelMeta<RootData> {

	static final String KIND = "RootData";

	static final RootDataMeta singleton = new RootDataMeta();

	final KeyAttributeMeta key = new KeyAttributeMeta(KIND);

	final PropertyAttributeMeta<String> str =
			new PropertyAttributeMeta<String>("str", String.class);

	final PropertyAttributeMeta<Integer> integer = new PropertyAttributeMeta<Integer>("integer",
			Integer.class);

	final PropertyAttributeMeta<Date> date = new PropertyAttributeMeta<Date>("date", Date.class);


	protected RootDataMeta() {
		super(null);
	}

	/**
	 * {@link RootDataMeta} のインスタンスを取得する.
	 * @return {@link RootDataMeta} のインスタンスを取得する.
	 * @author vvakame
	 */
	public static RootDataMeta get() {
		return singleton;
	}

	@Override
	public void setKey(RootData model, Key key) {
		model.setKey(key);
	}

	@Override
	public Key getKey(RootData model) {
		return model.getKey();
	}

	@Override
	public String getKind() {
		return KIND;
	}

	@Override
	public Class<RootData> getModelClass() {
		return RootData.class;
	}

	@Override
	public Entity modelToEntity(RootData model) {
		if (model == null) {
			throw new NullPointerException("model is required");
		}

		Key key = getKey(model);
		Entity entity;
		if (key != null) {
			entity = new Entity(key);
		} else {
			entity = new Entity(KIND);
		}

		entity.setProperty("str", model.getStr());
		entity.setProperty("integer", model.getInteger());
		Date tmp = model.getDate();
		if (tmp == null) {
			entity.setProperty("date", null);
		} else {
			entity.setProperty("date", tmp.getTime());
		}

		return entity;
	}

	@Override
	public RootData entityTomodel(Entity entity) {
		if (entity == null) {
			throw new NullPointerException("entity is required");
		} else if (!KIND.equals(entity.getKind())) {
			throw new IllegalArgumentException("entity kind=" + entity.getKind()
					+ ", but expected=" + KIND);
		}

		List<String> classHierarchy = entity.getProperty(PROPERTY_CLASS_HIERARCHY);
		if (classHierarchy == null) {
			// ok. only root node.
		} else if (classHierarchyList.size() < classHierarchy.size()) {
			// if entity is lower model...
			String className = classHierarchy.get(classHierarchy.size() - 1);
			ModelMeta<Object> meta = DatastoreUtil.getModelMeta(className);
			if (!meta.getKind().equals(entity.getKind())) {
				throw new IllegalArgumentException("kind=" + entity.getKind() + " is not expected.");
			}
			return (RootData) meta.entityTomodel(entity);
		} else if (classHierarchyList.size() > classHierarchy.size()) {
			// if entity is upper model. can't convert to model...
			throw new IllegalArgumentException(
					"entity is not convert to model. check class hierarchy.");
		}

		Key key = entity.getKey();
		RootData model = new RootData();
		setKey(model, key);
		model.setStr((String) entity.getProperty("str"));
		model.setInteger((int) (long) (Long) entity.getProperty("integer"));
		Long tmp = (Long) entity.getProperty("date");
		if (tmp != null) {
			model.setDate(new Date(tmp));
		}

		return model;
	}
}
