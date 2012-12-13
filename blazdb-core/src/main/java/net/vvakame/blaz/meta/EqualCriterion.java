package net.vvakame.blaz.meta;

import net.vvakame.blaz.Filter.FilterOption;

class EqualCriterion<T> extends FilterCriterionBase {

	public EqualCriterion(CoreAttributeMeta<T> coreAttr, T value) {
		super(coreAttr.getType(), coreAttr.getName(), FilterOption.EQ, value);
	}
}
