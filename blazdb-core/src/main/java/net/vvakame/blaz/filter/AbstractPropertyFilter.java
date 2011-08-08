package net.vvakame.blaz.filter;

import net.vvakame.blaz.Filter;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public abstract class AbstractPropertyFilter implements Filter {

	final static FilterTarget target = FilterTarget.PROPERTY;

	String name;


	/**
	 * the constructor.
	 * @param name 
	 * @category constructor
	 */
	public AbstractPropertyFilter(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.name = name;
	}

	@Override
	public final FilterTarget getTarget() {
		return target;
	}

	@Override
	public final String getName() {
		return name;
	}
}
