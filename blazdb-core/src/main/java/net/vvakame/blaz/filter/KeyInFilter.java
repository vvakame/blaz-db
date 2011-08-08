package net.vvakame.blaz.filter;

import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KeyInFilter extends AbstractKeyFilter {

	final static FilterTarget target = FilterTarget.KEY;

	final FilterOption option = FilterOption.IN;

	Key[] keys;


	/**
	 * the constructor.
	 * @param keys
	 * @category constructor
	 */
	public KeyInFilter(Key... keys) {
		if (keys.length == 0) {
			throw new IllegalArgumentException("keys are required.");
		}
		this.keys = keys;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public final Key[] getValue() {
		return keys;
	}
}
