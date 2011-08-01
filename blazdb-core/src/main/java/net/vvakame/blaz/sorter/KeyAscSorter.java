package net.vvakame.blaz.sorter;

/**
 * EntityのKeyを元に昇順ソートするためのソータの抽象
 * @author vvakame
 */
public class KeyAscSorter extends AbstractKeySorter {

	final Order order = Order.ASC;


	@Override
	public Order getOrder() {
		return order;
	}
}
