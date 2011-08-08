package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyKeyInFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.IN;

	Key[] values;


	/**
	 * the constructor.
	 * @param name 
	 * @param values
	 * @category constructor
	 */
	public PropertyKeyInFilter(String name, Key... values) {
		super(name);
		if (values.length == 0) {
			throw new IllegalArgumentException("values are required.");
		}
		this.values = values;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Key[] getValue() {
		return values;
	}
}
