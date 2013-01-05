package net.vvakame.blaz.filter;

import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public class PropertyKeyEqFilter extends AbstractPropertyFilter {

	final FilterOption option = FilterOption.EQ;

	Key value;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @param value
	 * @category constructor
	 */
	public PropertyKeyEqFilter(String name, Key value) {
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
