package net.vvakame.blaz.meta;

import java.util.Date;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.filter.AbstractKeyFilter;
import net.vvakame.blaz.filter.AbstractPropertyFilter;
import net.vvakame.blaz.filter.KeyEqFilter;
import net.vvakame.blaz.filter.KeyGtEqFilter;
import net.vvakame.blaz.filter.KeyGtFilter;
import net.vvakame.blaz.filter.KeyInFilter;
import net.vvakame.blaz.filter.KeyLtEqFilter;
import net.vvakame.blaz.filter.KeyLtFilter;
import net.vvakame.blaz.filter.PropertyBooleanEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanGtEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanGtFilter;
import net.vvakame.blaz.filter.PropertyBooleanInFilter;
import net.vvakame.blaz.filter.PropertyBooleanLtEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanLtFilter;
import net.vvakame.blaz.filter.PropertyIntegerEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerGtEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerGtFilter;
import net.vvakame.blaz.filter.PropertyIntegerInFilter;
import net.vvakame.blaz.filter.PropertyIntegerLtEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerLtFilter;
import net.vvakame.blaz.filter.PropertyKeyEqFilter;
import net.vvakame.blaz.filter.PropertyKeyGtEqFilter;
import net.vvakame.blaz.filter.PropertyKeyGtFilter;
import net.vvakame.blaz.filter.PropertyKeyInFilter;
import net.vvakame.blaz.filter.PropertyKeyLtEqFilter;
import net.vvakame.blaz.filter.PropertyKeyLtFilter;
import net.vvakame.blaz.filter.PropertyNullEqFilter;
import net.vvakame.blaz.filter.PropertyRealEqFilter;
import net.vvakame.blaz.filter.PropertyRealGtEqFilter;
import net.vvakame.blaz.filter.PropertyRealGtFilter;
import net.vvakame.blaz.filter.PropertyRealInFilter;
import net.vvakame.blaz.filter.PropertyRealLtEqFilter;
import net.vvakame.blaz.filter.PropertyRealLtFilter;
import net.vvakame.blaz.filter.PropertyStringEqFilter;
import net.vvakame.blaz.filter.PropertyStringGtEqFilter;
import net.vvakame.blaz.filter.PropertyStringGtFilter;
import net.vvakame.blaz.filter.PropertyStringInFilter;
import net.vvakame.blaz.filter.PropertyStringLtEqFilter;
import net.vvakame.blaz.filter.PropertyStringLtFilter;
import net.vvakame.blaz.meta.CoreAttributeMeta.Type;

abstract class FilterCriterionBase implements FilterCriterion {

	protected Filter[] filters;


	protected FilterCriterionBase(Type type, String name, FilterOption option, Object value) {
		switch (type) {
			case KEY:
				filters = new Filter[] {
					getKeyFilter(option, (Key) value)
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

	protected FilterCriterionBase(Type type, String name, FilterOption option, Object... value) {
		switch (type) {
			case KEY:
				filters = new Filter[] {
					getKeyFilterIn(option, (Key[]) value)
				};
				break;
			case PROPERTY:
				filters = new Filter[] {
					getPropertyFilterIn(name, option, value)
				};
				break;

			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractKeyFilter getKeyFilter(FilterOption option, Key value) {
		switch (option) {
			case EQ:
				return new KeyEqFilter(value);
			case GT:
				return new KeyGtFilter(value);
			case GT_EQ:
				return new KeyGtEqFilter(value);
			case LT:
				return new KeyLtFilter(value);
			case LT_EQ:
				return new KeyLtEqFilter(value);
			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractKeyFilter getKeyFilterIn(FilterOption option, Key... value) {
		return new KeyInFilter(value);
	}

	AbstractPropertyFilter getPropertyFilter(String name, FilterOption option, Object value) {

		if (value == null) {

			return getPropertyNullFilter(name, option);

		} else if (value instanceof Byte || value instanceof Short || value instanceof Integer
				|| value instanceof Long) {

			return getPropertyIntegerFilter(name, option, ((Number) value).longValue());

		} else if (value instanceof Float || value instanceof Double) {

			return getPropertyRealFilter(name, option, ((Number) value).doubleValue());

		} else if (value instanceof Boolean) {

			return getPropertyBooleanFilter(name, option, (Boolean) value);

		} else if (value instanceof String) {

			return getPropertyStringFilter(name, option, (String) value);

		} else if (value instanceof Key) {

			return getPropertyKeyFilter(name, option, (Key) value);

		} else if (value instanceof Date) {

			return getPropertyIntegerFilter(name, option, ((Date) value).getTime());

		}

		throw new IllegalArgumentException();
	}

	AbstractPropertyFilter getPropertyFilterIn(String name, FilterOption option, Object... value) {

		if (value instanceof Byte[] || value instanceof Short[] || value instanceof Integer[]) {

			Number[] numberValues = (Number[]) value;
			Long[] longValues = new Long[numberValues.length];
			for (int i = 0; i < longValues.length; i++) {
				longValues[i] = numberValues[i].longValue();
			}

			return new PropertyIntegerInFilter(name, longValues);

		} else if (value instanceof Long[]) {

			return new PropertyIntegerInFilter(name, (Long[]) value);

		} else if (value instanceof Float[]) {

			Number[] numberValues = (Number[]) value;
			Double[] doubleValues = new Double[numberValues.length];
			for (int i = 0; i < doubleValues.length; i++) {
				doubleValues[i] = numberValues[i].doubleValue();
			}

			return new PropertyRealInFilter(name, doubleValues);

		} else if (value instanceof Double[]) {

			return new PropertyRealInFilter(name, (Double[]) value);

		} else if (value instanceof Boolean[]) {

			return new PropertyBooleanInFilter(name, (Boolean[]) value);

		} else if (value instanceof String[]) {

			return new PropertyStringInFilter(name, (String[]) value);

		} else if (value instanceof Key[]) {

			return new PropertyKeyInFilter(name, (Key[]) value);

		} else if (value instanceof Date[]) {

			Date[] dateValues = (Date[]) value;
			Long[] longValues = new Long[dateValues.length];
			for (int i = 0; i < longValues.length; i++) {
				longValues[i] = dateValues[i].getTime();
			}

			return new PropertyIntegerInFilter(name, longValues);

		}

		throw new IllegalArgumentException();
	}

	AbstractPropertyFilter getPropertyIntegerFilter(String name, FilterOption option, long value) {
		switch (option) {
			case EQ:
				return new PropertyIntegerEqFilter(name, value);
			case GT:
				return new PropertyIntegerGtFilter(name, value);
			case GT_EQ:
				return new PropertyIntegerGtEqFilter(name, value);
			case LT:
				return new PropertyIntegerLtFilter(name, value);
			case LT_EQ:
				return new PropertyIntegerLtEqFilter(name, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractPropertyFilter getPropertyRealFilter(String name, FilterOption option, double value) {
		switch (option) {
			case EQ:
				return new PropertyRealEqFilter(name, value);
			case GT:
				return new PropertyRealGtFilter(name, value);
			case GT_EQ:
				return new PropertyRealGtEqFilter(name, value);
			case LT:
				return new PropertyRealLtFilter(name, value);
			case LT_EQ:
				return new PropertyRealLtEqFilter(name, value);
			case IN:
				return new PropertyRealInFilter(name, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractPropertyFilter getPropertyBooleanFilter(String name, FilterOption option, boolean value) {
		switch (option) {
			case EQ:
				return new PropertyBooleanEqFilter(name, value);
			case GT:
				return new PropertyBooleanGtFilter(name, value);
			case GT_EQ:
				return new PropertyBooleanGtEqFilter(name, value);
			case LT:
				return new PropertyBooleanLtFilter(name, value);
			case LT_EQ:
				return new PropertyBooleanLtEqFilter(name, value);
			case IN:
				return new PropertyBooleanInFilter(name, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractPropertyFilter getPropertyStringFilter(String name, FilterOption option, String value) {
		switch (option) {
			case EQ:
				return new PropertyStringEqFilter(name, value);
			case GT:
				return new PropertyStringGtFilter(name, value);
			case GT_EQ:
				return new PropertyStringGtEqFilter(name, value);
			case LT:
				return new PropertyStringLtFilter(name, value);
			case LT_EQ:
				return new PropertyStringLtEqFilter(name, value);
			case IN:
				return new PropertyStringInFilter(name, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractPropertyFilter getPropertyNullFilter(String name, FilterOption option) {
		switch (option) {
			case EQ:
				return new PropertyNullEqFilter(name);
			default:
				throw new IllegalArgumentException();
		}
	}

	AbstractPropertyFilter getPropertyKeyFilter(String name, FilterOption option, Key value) {
		switch (option) {
			case EQ:
				return new PropertyKeyEqFilter(name, value);
			case GT:
				return new PropertyKeyGtFilter(name, value);
			case GT_EQ:
				return new PropertyKeyGtEqFilter(name, value);
			case LT:
				return new PropertyKeyLtFilter(name, value);
			case LT_EQ:
				return new PropertyKeyLtEqFilter(name, value);
			case IN:
				return new PropertyKeyInFilter(name, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override
	public Filter[] getFilters() {
		return filters;
	}
}
