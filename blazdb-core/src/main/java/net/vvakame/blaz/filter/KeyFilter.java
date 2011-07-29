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
		return key;
	}
}
