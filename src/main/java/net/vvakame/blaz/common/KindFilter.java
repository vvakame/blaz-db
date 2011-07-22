package net.vvakame.blaz.common;

import net.vvakame.blaz.IFilter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class KindFilter implements IFilter {

	final static FilterTarget target = FilterTarget.KIND;

	FilterOption option = FilterOption.EQ;

	String name;


	/**
	 * the constructor.
	 * @param kind
	 * @category constructor
	 */
	public KindFilter(String kind) {
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
	public Object getValue() {
		throw new IllegalStateException("not supported!");
	}
}
