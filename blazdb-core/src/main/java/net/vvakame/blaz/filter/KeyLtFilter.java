package net.vvakame.blaz.filter;

import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KeyLtFilter extends AbstractKeyFilter {

	final FilterOption option = FilterOption.LT;


	/**
	 * the constructor.
	 * @param key
	 * @category constructor
	 */
	public KeyLtFilter(Key key) {
		super(key);
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Key getValue() {
		return key;
	}
}
