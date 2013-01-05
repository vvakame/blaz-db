package net.vvakame.blaz.filter;


/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public class PropertyIntegerInFilter extends AbstractPropertyFilter {

	final FilterOption option = FilterOption.IN;

	Long[] values;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @param values
	 * @category constructor
	 */
	public PropertyIntegerInFilter(String name, Long... values) {
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
	public Long[] getValue() {
		return values;
	}
}
