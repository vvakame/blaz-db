package net.vvakame.blaz.sorter;

/**
 * EntityのKeyを元に降順ソートするためのソータの抽象
 * @author vvakame
 */
public class KeyDescSorter extends AbstractKeySorter {

	final Order order = Order.DESC;


	@Override
	public Order getOrder() {
		return order;
	}
}
