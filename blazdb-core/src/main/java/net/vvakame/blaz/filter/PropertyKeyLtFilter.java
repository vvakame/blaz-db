package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyKeyLtFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.LT;

	Key value;


	/**
	 * the constructor.
	 * @param name 
	 * @param value 
	 * @category constructor
	 */
	public PropertyKeyLtFilter(String name, Key value) {
		super(name);
		if (value == null) {
			throw new IllegalArgumentException("value is required.");
		}
		this.value = value;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Key getValue() {
		return value;
	}
}
