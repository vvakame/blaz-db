package net.vvakame.blaz.sorter;

import net.vvakame.blaz.Sorter;

/**
 * EntityのPropertyを元にソートするためのソータの抽象
 * 
 * @author vvakame
 */
public abstract class AbstractPropertySorter extends Sorter {

	String name;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @category constructor
	 */
	public AbstractPropertySorter(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
