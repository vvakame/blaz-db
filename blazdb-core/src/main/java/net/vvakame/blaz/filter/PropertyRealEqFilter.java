package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyRealEqFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.EQ;

	double value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyRealEqFilter(String name, double value) {
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
