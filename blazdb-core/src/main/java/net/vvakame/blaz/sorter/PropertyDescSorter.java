package net.vvakame.blaz.sorter;

/**
 * EntityのPeropertyを元に降順ソートするためのソータの抽象
 * @author vvakame
 */
public class PropertyDescSorter extends AbstractPropertySorter {

	final Order order = Order.DESC;


	/**
	 * the constructor.
	 * @param name
	 * @category constructor
	 */
	public PropertyDescSorter(String name) {
		super(name);
	}

	@Override
	public Order getOrder() {
		return order;
	}
}
