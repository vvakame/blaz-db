package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyBooleanInFilter extends AbstractPropertyFilter implements Filter {

	final FilterOption option = FilterOption.IN;

	Boolean[] values;


	/**
	 * the constructor.
	 * @param name 
	 * @param values
	 * @category constructor
	 */
	public PropertyBooleanInFilter(String name, Boolean... values) {
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
	public Boolean[] getValue() {
		return values;
	}
}
