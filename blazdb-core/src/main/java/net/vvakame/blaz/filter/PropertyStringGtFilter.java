package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyStringGtFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.GT;

	String value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyStringGtFilter(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public String getValue() {
		return value;
	}
}
