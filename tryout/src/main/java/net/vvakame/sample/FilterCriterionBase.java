package net.vvakame.sample;

import java.util.Date;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.filter.KeyFilter;
import net.vvakame.blaz.filter.PropertyFilter;
import net.vvakame.sample.CoreAttributeMeta.Type;

abstract class FilterCriterionBase implements FilterCriterion {

	protected Filter[] filters;


	protected FilterCriterionBase(Type type, String name, FilterOption option, Object value) {
		switch (type) {
			case KEY:
				filters = new Filter[] {
					new KeyFilter(option, (Key) value)
				};
				break;
			case PROPERTY:
				filters = new Filter[] {
					getPropertyFilter(name, option, value)
				};
				break;

			default:
				throw new IllegalArgumentException();
		}
	}

	protected PropertyFilter getPropertyFilter(String name, FilterOption option, Object value) {
		if (value instanceof Byte) {
			return new PropertyFilter(name, option, (Byte) value);
		} else if (value instanceof Short) {
			return new PropertyFilter(name, option, (Short) value);
		} else if (value instanceof Integer) {
			return new PropertyFilter(name, option, (Integer) value);
		} else if (value instanceof Long) {
			return new PropertyFilter(name, option, (Long) value);
		} else if (value instanceof Float) {
			return new PropertyFilter(name, option, (Float) value);
		} else if (value instanceof Double) {
			return new PropertyFilter(name, option, (Double) value);
		} else if (value instanceof Boolean) {
			return new PropertyFilter(name, option, (Boolean) value);
		} else if (value instanceof String) {
			return new PropertyFilter(name, option, (String) value);
		} else if (value instanceof Key) {
			return new PropertyFilter(name, option, (Key) value);
		} else if (value instanceof Date) {
			return new PropertyFilter(name, option, ((Date) value).getTime());
		}

		throw new IllegalArgumentException();
	}

	@Override
	public Filter[] getFilters() {
		return filters;
	}
}
