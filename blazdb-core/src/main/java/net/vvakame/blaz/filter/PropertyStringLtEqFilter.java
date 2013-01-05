package net.vvakame.blaz.filter;


/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public class PropertyStringLtEqFilter extends AbstractPropertyFilter {

	final FilterOption option = FilterOption.LT_EQ;

	String value;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @param value
	 * @category constructor
	 */
	public PropertyStringLtEqFilter(String name, String value) {
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
