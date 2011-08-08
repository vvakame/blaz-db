package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyRealGtFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.GT;

	double value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyRealGtFilter(String name, double value) {
		super(name);
		this.value = value;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Double getValue() {
		return value;
	}
}
