package net.vvakame.blaz.util;

import java.util.Date;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Filter.FilterTarget;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.filter.KeyEqFilter;
import net.vvakame.blaz.filter.KeyGtEqFilter;
import net.vvakame.blaz.filter.KeyGtFilter;
import net.vvakame.blaz.filter.KeyInFilter;
import net.vvakame.blaz.filter.KeyLtEqFilter;
import net.vvakame.blaz.filter.KeyLtFilter;
import net.vvakame.blaz.filter.KindEqFilter;
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

/**
 * {@link Filter} に関するユーティリティ.
 * @author vvakame
 */
public class FilterUtil {

	/**
	 * 渡されたパラメタを元に {@link Filter} を生成して返す.
	 * @param target
	 * @param name
	 * @param option
	 * @param value
	 * @return {@link Filter}
	 * @author vvakame
	 */
	public static Filter getFilter(FilterTarget target, String name, FilterOption option,
			Object value) {
		if (value instanceof Object[]) {
			return getFilterInner(target, name, option, (Object[]) value);
		} else {
			return getFilterInner(target, name, option, value);
		}
	}

	static Filter getFilterInner(FilterTarget target, String name, FilterOption option, Object value) {
		switch (target) {
			case KIND:
				return getKindFilter(option, name);
			case KEY:
				return getKeyFilter(option, (Key) value);
			case PROPERTY:
				return getPropertyFilter(name, option, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	static Filter getFilterInner(FilterTarget target, String name, FilterOption option,
			Object... value) {
		switch (target) {
			case KEY:
				return getKeyFilterIn(option, (Key[]) value);
			case PROPERTY:
				return getPropertyFilterIn(name, option, value);
			default:
				throw new IllegalArgumentException();
		}
	}

	static Filter getKindFilter(FilterOption option, String name) {
		switch (option) {
			case EQ:
				return new KindEqFilter(name);
			default:
				throw new IllegalArgumentException();
		}
	}

	static Filter getKeyFilter(FilterOption option, Key value) {
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

	static Filter getKeyFilterIn(FilterOption option, Key... value) {
		return new KeyInFilter(value);
	}

	static Filter getPropertyFilter(String name, FilterOption option, Object value) {

		if (value instanceof Byte || value instanceof Short || value instanceof Integer
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

		} else if (value == null) {

			return getPropertyNullFilter(name, option);

		}

		throw new IllegalArgumentException();
	}

	static Filter getPropertyFilterIn(String name, FilterOption option, Object... value) {

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

	static Filter getPropertyIntegerFilter(String name, FilterOption option, long value) {
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

	static Filter getPropertyRealFilter(String name, FilterOption option, double value) {
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

	static Filter getPropertyBooleanFilter(String name, FilterOption option, boolean value) {
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

	static Filter getPropertyStringFilter(String name, FilterOption option, String value) {
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

	static Filter getPropertyKeyFilter(String name, FilterOption option, Key value) {
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

	static Filter getPropertyNullFilter(String name, FilterOption option) {
		switch (option) {
			case EQ:
				return new PropertyNullEqFilter(name);
			default:
				throw new IllegalArgumentException();
		}
	}

}
