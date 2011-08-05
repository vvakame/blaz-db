package net.vvakame.sample;

import net.vvakame.blaz.Filter.FilterOption;

class GreaterThanOrEqualCriterion<T> extends FilterCriterionBase {

	public GreaterThanOrEqualCriterion(CoreAttributeMeta<T> coreAttr, T value) {
		super(coreAttr.getType(), coreAttr.getName(), FilterOption.GT_EQ, value);
	}
}
