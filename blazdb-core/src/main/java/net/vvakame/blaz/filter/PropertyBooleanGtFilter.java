package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyBooleanGtFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.GT;

	boolean value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyBooleanGtFilter(String name, boolean value) {
		super(name);
		this.value = value;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Boolean getValue() {
		return value;
	}
}
