package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KeyFilter implements Filter {

	final static FilterTarget target = FilterTarget.KEY;

	FilterOption option;

	Key key;

	Key[] keys;


	/**
	 * the constructor.
	 * @param option
	 * @param key
	 * @category constructor
	 */
	public KeyFilter(FilterOption option, Key key) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (key == null) {
			throw new IllegalArgumentException("value is required.");
		}

		this.option = option;
		this.key = key;
	}

	/**
	 * the constructor.
	 * @param option
	 * @param keys
	 * @category constructor
	 */
	public KeyFilter(FilterOption option, Key... keys) {
		if (option != FilterOption.IN) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (keys == null || keys.length == 0) {
			throw new IllegalArgumentException("value is required.");
		}

		this.option = option;
		this.keys = keys;
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
		return key != null ? key : keys;
	}
}
