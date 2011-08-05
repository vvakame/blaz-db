package net.vvakame.sample;

import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Sorter.Order;
import net.vvakame.sample.CoreAttributeMeta.Type;

class AscSorterCriterion extends SorterCriterionBase {

	public AscSorterCriterion(Type type) {
		super(type, null, Order.ASC);
	}

	public AscSorterCriterion(Type type, String name) {
		super(type, name, Order.ASC);
	}

	@Override
	public Sorter[] getSorters() {
		return sorters;
	}
}
