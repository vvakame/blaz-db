package net.vvakame.blaz.sorter;

/**
 * EntityのPeropertyを元に昇順ソートするためのソータの抽象
 * @author vvakame
 */
public class PropertyAscSorter extends AbstractPropertySorter {

	final Order order = Order.ASC;


	/**
	 * the constructor.
	 * @param name
	 * @category constructor
	 */
	public PropertyAscSorter(String name) {
		super(name);
	}

	@Override
	public Order getOrder() {
		return order;
	}
}
