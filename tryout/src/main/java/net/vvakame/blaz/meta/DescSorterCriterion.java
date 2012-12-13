package net.vvakame.blaz.meta;

import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Sorter.Order;
import net.vvakame.blaz.meta.CoreAttributeMeta.Type;

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
