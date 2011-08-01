package net.vvakame.blaz.bare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.Sorter.Order;
import net.vvakame.blaz.sorter.AbstractKeySorter;
import net.vvakame.blaz.sorter.AbstractPropertySorter;

class BareSortUtil {

	private BareSortUtil() {
	}

	public static void sort(List<Entity> entities, Sorter[] sorters) {
		List<Sorter> sorterList = reverse(sorters);
		for (Sorter sorter : sorterList) {
			if (sorter instanceof AbstractPropertySorter) {
				Collections.sort(entities,
						new PropertyComparator(sorter.getName(), sorter.getOrder()));
			} else if (sorter instanceof AbstractKeySorter) {
				Collections.sort(entities, new KeyComparator(sorter.getOrder()));
			} else {
				throw new IllegalArgumentException("unknown sorter "
						+ sorter.getClass().getCanonicalName());
			}
		}
	}

	static List<Sorter> reverse(Sorter[] sorters) {
		final int len = sorters.length;
		List<Sorter> resultList = new ArrayList<Sorter>(len);
		for (int i = len - 1; 0 <= i; i--) {
			resultList.add(sorters[i]);
		}
		return resultList;
	}


	private static class KeyComparator implements Comparator<Entity> {

		Order order;


		private KeyComparator(Order order) {
			this.order = order;
		}

		@Override
		public int compare(Entity e1, Entity e2) {

			Key key1 = e1.getKey();
			Key key2 = e2.getKey();

			if (order == Order.ASC) {
				return key1.compareTo(key2);
			} else if (order == Order.DESC) {
				return key2.compareTo(key1);
			} else {
				throw new IllegalArgumentException("order is required.");
			}
		}

	}

	private static class PropertyComparator implements Comparator<Entity> {

		String name;

		final int small;

		final int big;


		private PropertyComparator(String name, Order order) {
			this.name = name;

			if (order == Order.ASC) {
				small = -1;
				big = 1;
			} else if (order == Order.DESC) {
				small = 1;
				big = -1;
			} else {
				throw new IllegalArgumentException();
			}
		}

		@Override
		public int compare(Entity e1, Entity e2) {
			if (e1 == null || e2 == null) {
				throw new NullPointerException("object is null.");
			}

			Object v1 = e1.getProperty(name);
			if (v1 instanceof Collection<?>) {
				v1 = getSmallestValue((Collection<?>) v1);
			}
			Object v2 = e2.getProperty(name);
			if (v2 instanceof Collection<?>) {
				v2 = getSmallestValue((Collection<?>) v2);
			}

			return compareValue(v1, v2);
		}

		@SuppressWarnings("unchecked")
		private int compareValue(Object v1, Object v2) {
			if (v1 == null && v2 == null) {
				return 0;
			}
			if (v1 == null) {
				return small;
			}
			if (v2 == null) {
				return big;
			}
			if (!(v1 instanceof Comparable)) {
				throw new IllegalStateException("property " + name + " is not comparable.");
			}
			final int compareTo = ((Comparable<Object>) v1).compareTo(v2);
			if (compareTo == 0) {
				return 0;
			} else if (compareTo < 0) {
				return small;
			} else if (compareTo > 0) {
				return big;
			}
			return 0;
		}

		private static Object getSmallestValue(Collection<?> collection) {
			if (collection.size() == 0) {
				return null;
			}
			if (collection.size() == 1) {
				return collection.iterator().next();
			}
			Object[] array = collection.toArray();
			Arrays.sort(array);
			return array[0];
		}
	}
}
