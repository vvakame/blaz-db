package net.vvakame.sample;

import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Sorter.Order;
import net.vvakame.sample.CoreAttributeMeta.Type;

class DescSorterCriterion extends SorterCriterionBase {

	public DescSorterCriterion(Type type) {
		super(type, null, Order.DESC);
	}

	public DescSorterCriterion(Type type, String name) {
		super(type, name, Order.DESC);
	}

	@Override
	public Sorter[] getSorters() {
		return sorters;
	}
}
