package net.vvakame.blaz.filter;

import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KeyEqFilter extends AbstractKeyFilter {

	final FilterOption option = FilterOption.EQ;


	/**
	 * the constructor.
	 * @param key
	 * @category constructor
	 */
	public KeyEqFilter(Key key) {
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
