package net.vvakame.blaz.filter;


/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public class PropertyNullEqFilter extends AbstractPropertyFilter {

	final FilterOption option = FilterOption.EQ;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @category constructor
	 */
	public PropertyNullEqFilter(String name) {
		super(name);
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Object getValue() {
		return null;
	}
}
