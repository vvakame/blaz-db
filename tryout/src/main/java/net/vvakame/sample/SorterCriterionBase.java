package net.vvakame.sample;

import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Sorter.Order;
import net.vvakame.blaz.sorter.KeyAscSorter;
import net.vvakame.blaz.sorter.KeyDescSorter;
import net.vvakame.blaz.sorter.PropertyAscSorter;
import net.vvakame.blaz.sorter.PropertyDescSorter;
import net.vvakame.sample.CoreAttributeMeta.Type;

abstract class SorterCriterionBase implements SortCriterion {

	protected Sorter[] sorters;


	SorterCriterionBase(Type type, String name, Order order) {
		switch (type) {
			case KEY:
				switch (order) {
					case ASC:
						sorters = new Sorter[] {
							new KeyAscSorter()
						};
						break;
					case DESC:
						sorters = new Sorter[] {
							new KeyDescSorter()
						};
						break;
					default:
						throw new IllegalArgumentException();
				}
				break;
			case PROPERTY:
				switch (order) {
					case ASC:
						sorters = new Sorter[] {
							new PropertyAscSorter(name)
						};
						break;
					case DESC:
						sorters = new Sorter[] {
							new PropertyDescSorter(name)
						};
						break;
					default:
						throw new IllegalArgumentException();
				}
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
}
