package net.vvakame.blaz.common;

import net.vvakame.blaz.IFilter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KeyFilter implements IFilter {

	final static FilterTarget target = FilterTarget.KEY;

	FilterOption option;

	Object value;


	/**
	 * the constructor.
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public KeyFilter(FilterOption option, String value) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (value == null) {
			throw new IllegalArgumentException("value is required.");
		}

		this.option = option;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public KeyFilter(FilterOption option, long value) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (value == 1) {
			throw new IllegalArgumentException("value must not be 1.");
		}

		this.option = option;
		this.value = value;
	}

	@Override
	public FilterTarget getTarget() {
		return target;
	}

	@Override
	public String getName() {
		throw new IllegalStateException("not supported!");
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Object getValue() {
		return value;
	}
}
