package net.vvakame.blaz.meta;

import net.vvakame.blaz.Filter.FilterOption;

class LessThanOrEqualCriterion<T> extends FilterCriterionBase {

	public LessThanOrEqualCriterion(CoreAttributeMeta<T> coreAttr, T value) {
		super(coreAttr.getType(), coreAttr.getName(), FilterOption.LT_EQ, value);
	}
}
