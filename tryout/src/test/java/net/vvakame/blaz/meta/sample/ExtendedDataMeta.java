package net.vvakame.blaz.meta.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.meta.DatastoreUtil;
import net.vvakame.blaz.meta.KeyAttributeMeta;
import net.vvakame.blaz.meta.ModelMeta;
import net.vvakame.blaz.meta.PropertyAttributeMeta;

/**
 * {@link ExtendedData} の {@link ModelMeta} 表現.<br>
 * {@link Datastore} の各種メソッドに対して渡して利用する.
 * @author vvakame
 */
public class ExtendedDataMeta extends ModelMeta<ExtendedData> {

	static final String KIND = "RootData";

	static final ExtendedDataMeta singleton = new ExtendedDataMeta();

	final KeyAttributeMeta key = new KeyAttributeMeta(KIND);

	final PropertyAttributeMeta<String> str =
			new PropertyAttributeMeta<String>("str", String.class);

	final PropertyAttributeMeta<Integer> integer = new PropertyAttributeMeta<Integer>("integer",
			Integer.class);

	final PropertyAttributeMeta<Date> date = new PropertyAttributeMeta<Date>("date", Date.class);


	protected ExtendedDataMeta() {
		super(RootDataMeta.get());
	}

	/**
	 * {@link RootDataMeta} のインスタンスを取得する.
	 * @return {@link RootDataMeta} のインスタンスを取得する.
	 * @author vvakame
	 */
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
		entity.setProperty("booleanData", model.isBooleanData());
		entity.setProperty("byteData", model.getByteData());
		entity.setProperty("shortData", model.getShortData());
		entity.setProperty("longData", model.getLongData());
		entity.setProperty("floatData", model.getFloatData());
		entity.setProperty("doubleData", model.getDoubleData());
		entity.setProperty("list", model.getList());

		return entity;
	}

	@Override
	public ExtendedData entityToModel(Entity entity) {
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
			return (ExtendedData) meta.entityToModel(entity);
		} else if (classHierarchyList.size() > classHierarchy.size()) {
			// if entity is upper model. can't convert to model...
			throw new IllegalArgumentException(
					"entity is not convert to model. check class hierarchy.");
		}

		Key key = entity.getKey();
		ExtendedData model = new ExtendedData();
		setKey(model, key);

		if (entity.hasProperty("str")) {
			model.setStr(entity.<String> getProperty("str"));
		}
		if (entity.hasProperty("integer")) {
			Long val = entity.<Long> getProperty("integer");
			model.setInteger(val.intValue());
		}
		if (entity.hasProperty("date")) {
			Long val = entity.<Long> getProperty("date");
			if (val != null) {
				model.setDate(new Date(val));
			} else {
				model.setDate(null);
			}
		}
		if (entity.hasProperty("booleanData")) {
			Boolean val = entity.<Boolean> getProperty("booleanData");
			model.setBooleanData(val);
		}
		if (entity.hasProperty("byteData")) {
			Long val = entity.<Long> getProperty("byteData");
			model.setByteData(val.byteValue());
		}
		if (entity.hasProperty("shortData")) {
			Long val = entity.<Long> getProperty("shortData");
			model.setShortData(val.shortValue());
		}
		if (entity.hasProperty("longData")) {
			Long val = entity.<Long> getProperty("longData");
			model.setLongData(val);
		}
		if (entity.hasProperty("floatData")) {
			Double val = entity.<Double> getProperty("floatData");
			model.setFloatData(val.floatValue());
		}
		if (entity.hasProperty("doubleData")) {
			Double val = entity.<Double> getProperty("doubleData");
			model.setDoubleData(val);
		}
		if (entity.hasProperty("list")) {
			Collection<String> val = entity.<Collection<String>> getProperty("list");
			List<String> list = new ArrayList<String>(val);
			model.setList(list);
		}

		return model;
	}
}