package net.vvakame.blaz.filter;


/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public class PropertyStringInFilter extends AbstractPropertyFilter {

	final FilterOption option = FilterOption.IN;

	String[] values;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @param values
	 * @category constructor
	 */
	public PropertyStringInFilter(String name, String... values) {
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
	public String[] getValue() {
		return values;
	}
}
