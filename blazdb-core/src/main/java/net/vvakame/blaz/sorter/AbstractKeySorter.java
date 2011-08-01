package net.vvakame.blaz.sorter;

import net.vvakame.blaz.Sorter;

/**
 * EntityのKeyを元にソートするためのソータの抽象
 * @author vvakame
 */
public abstract class AbstractKeySorter implements Sorter {

	String name;


	@Override
	public String getName() {
		return name;
	}
}
