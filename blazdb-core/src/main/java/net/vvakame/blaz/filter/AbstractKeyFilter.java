package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public abstract class AbstractKeyFilter extends Filter {

	final static FilterTarget target = FilterTarget.KEY;

	Key key;

	protected AbstractKeyFilter() {
	}

	protected AbstractKeyFilter(Key key) {
		this.key = key;
	}

	@Override
	public final FilterTarget getTarget() {
		return target;
	}

	@Override
	@Deprecated
	public final String getName() {
		throw new UnsupportedOperationException("not supported!");
	}
}
