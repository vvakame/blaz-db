package net.vvakame.sample;

import net.vvakame.blaz.Sorter;

/**
 * ソート条件
 * @author vvakame
 */
public interface SortCriterion {

	/**
	 * ソート条件の取得
	 * @return ソート条件
	 * @author vvakame
	 */
	public Sorter[] getSorters();
}
