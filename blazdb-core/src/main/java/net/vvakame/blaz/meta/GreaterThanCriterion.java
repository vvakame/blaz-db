package net.vvakame.blaz.meta;

import net.vvakame.blaz.Filter.FilterOption;

class GreaterThanCriterion<T> extends FilterCriterionBase {

	public GreaterThanCriterion(CoreAttributeMeta<T> coreAttr, T value) {
		super(coreAttr.getType(), coreAttr.getName(), FilterOption.GT, value);
	}
}
