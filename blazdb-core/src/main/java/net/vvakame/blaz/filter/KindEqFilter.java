package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KindEqFilter implements Filter {

	final static FilterTarget target = FilterTarget.KIND;

	FilterOption option = FilterOption.EQ;

	String name;


	/**
	 * the constructor.
	 * @param kind
	 * @category constructor
	 */
	public KindEqFilter(String kind) {
		if (kind == null) {
			throw new IllegalArgumentException("kind is required.");
		}

		this.name = kind;
	}

	@Override
	public FilterTarget getTarget() {
		return target;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	@Deprecated
	public Object getValue() {
		throw new IllegalStateException("not supported!");
	}
}
