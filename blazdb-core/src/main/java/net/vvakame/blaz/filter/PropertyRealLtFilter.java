package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyRealLtFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.LT;

	double value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyRealLtFilter(String name, double value) {
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
