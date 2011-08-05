package net.vvakame.sample;

import net.vvakame.blaz.Filter.FilterOption;

class LessThanCriterion<T> extends FilterCriterionBase {

	public LessThanCriterion(CoreAttributeMeta<T> coreAttr, T value) {
		super(coreAttr.getType(), coreAttr.getName(), FilterOption.LT, value);
	}
}
