package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyIntegerGtFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.GT;

	long value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyIntegerGtFilter(String name, long value) {
		super(name);
		this.value = value;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Long getValue() {
		return value;
	}
}
