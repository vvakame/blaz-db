package net.vvakame.blaz.meta;

import net.vvakame.blaz.Filter.FilterOption;

class InCriterion<T> extends FilterCriterionBase {

	public InCriterion(CoreAttributeMeta<T> coreAttr, T... values) {
		super(coreAttr.getType(), coreAttr.getName(), FilterOption.IN, values);
	}
}
