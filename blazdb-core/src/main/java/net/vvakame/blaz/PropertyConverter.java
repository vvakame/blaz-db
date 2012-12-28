package net.vvakame.blaz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PropertyConverter<P, R> {

	public abstract R serialize(P value);

	public abstract P deserialize(R value);

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
			Collection<P> collection) {
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

	public static class DummyConverter extends PropertyConverter<Void, Void> {
		@Override
		public Void serialize(Void value) {
			return null;
		}

		@Override
		public Void deserialize(Void value) {
			return null;
		}
	}
}
