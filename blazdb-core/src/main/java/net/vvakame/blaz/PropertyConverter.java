package net.vvakame.blaz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PropertyConverter<P, R> {

	public R serialize(P value) {
		throw new UnsupportedOperationException(
				"please override this method or set method.");
	}

	public P deserialize(R value) {
		throw new UnsupportedOperationException(
				"please override this method or get method.");
	}

	public void set(Entity entity, String propertyName, P value) {
		R converted;
		if (value != null) {
			converted = serialize(value);
		} else {
			converted = null;
		}
		entity.setProperty(propertyName, converted);
	}

	public void setCollection(Entity entity, String propertyName,
			Collection<? extends P> collection) {
		if (collection == null) {
			entity.setProperty(propertyName, null);
			return;
		}
		List<R> list = new ArrayList<R>(collection.size());
		for (P value : collection) {
			if (value != null) {
				list.add(serialize(value));
			} else {
				list.add(null);
			}
		}
		entity.setProperty(propertyName, list);
	}

	public void setAny(Entity entity, String propertyName, Object value) {
		throw new UnsupportedOperationException(
				"please override this method or fix attribute's type. propertyName="
						+ propertyName);
	}

	public P get(Entity entity, String propertyName) {
		R value = entity.getProperty(propertyName);
		if (value != null) {
			return deserialize(value);
		} else {
			return null;
		}
	}

	public List<P> getList(Entity entity, String propertyName) {
		List<R> list = entity.getProperty(propertyName);
		if (list == null) {
			return null;
		}

		List<P> convertedList = new ArrayList<P>();
		for (R value : list) {
			if (value != null) {
				convertedList.add(deserialize(value));
			} else {
				convertedList.add(null);
			}
		}
		return convertedList;
	}

	public <U> U getAny(Entity entity, String propertyName) {
		throw new UnsupportedOperationException(
				"please override this method or fix attribute's type. propertyName="
						+ propertyName);
	}

	public static class DummyConverter extends PropertyConverter<Void, Void> {
	}
}
