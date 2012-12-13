package net.vvakame.blaz.meta;

import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Sorter.Order;
import net.vvakame.blaz.meta.CoreAttributeMeta.Type;

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
