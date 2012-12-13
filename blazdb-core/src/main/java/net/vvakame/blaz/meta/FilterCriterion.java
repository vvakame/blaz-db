package net.vvakame.blaz.meta;

import net.vvakame.blaz.Filter;

/**
 * 検索条件
 * @author vvakame
 */
public interface FilterCriterion {

	/**
	 * 検索条件の取得
	 * @return 検索条件
	 * @author vvakame
	 */
	public Filter[] getFilters();
}
