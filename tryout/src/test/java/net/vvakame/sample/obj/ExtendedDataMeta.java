package net.vvakame.sample.obj;

import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.sample.DatastoreUtil;
import net.vvakame.sample.KeyAttributeMeta;
import net.vvakame.sample.ModelMeta;
import net.vvakame.sample.PropertyAttributeMeta;

public class ExtendedDataMeta extends ModelMeta<ExtendedData> {

	static final String KIND = "TestData";

	static final ExtendedDataMeta singleton = new ExtendedDataMeta();

	final KeyAttributeMeta key = new KeyAttributeMeta(KIND);

	final PropertyAttributeMeta<String> str =
			new PropertyAttributeMeta<String>("str", String.class);

	final PropertyAttributeMeta<Integer> integer = new PropertyAttributeMeta<Integer>("integer",
			Integer.class);

	final PropertyAttributeMeta<Date> date = new PropertyAttributeMeta<Date>("date", Date.class);


	protected ExtendedDataMeta() {
		super();
	}

	public static ExtendedDataMeta get() {
		return singleton;
	}

	@Override
	public void setKey(ExtendedData model, Key key) {
		model.setKey(key);
	}

	@Override
	public Key getKey(ExtendedData model) {
		return model.getKey();
	}

	@Override
	public String getKind() {
		return KIND;
	}

	@Override
	public Class<ExtendedData> getModelClass() {
		return ExtendedData.class;
	}

	@Override
	public Entity modelToEntity(ExtendedData model) {
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

		entity.setProperty(PROPERTY_CLASS_HIERARCHY, classHierarchyList);

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
	public ExtendedData entityTomodel(Entity entity) {
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
			return (ExtendedData) meta.entityTomodel(entity);
		} else if (classHierarchyList.size() > classHierarchy.size()) {
			// if entity is upper model. can't convert to model...
			throw new IllegalArgumentException(
					"entity is not convert to model. check class hierarchy.");
		}

		Key key = entity.getKey();
		ExtendedData model = new ExtendedData();
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
