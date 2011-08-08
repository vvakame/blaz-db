package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyRealInFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.IN;

	Double[] values;


	/**
	 * the constructor.
	 * @param name 
	 * @param values
	 * @category constructor
	 */
	public PropertyRealInFilter(String name, Double... values) {
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
	public Double[] getValue() {
		return values;
	}
}
