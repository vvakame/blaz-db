package net.vvakame.blaz;

/**
 * Entity検索結果をソートするためのソータ
 * @author vvakame
 */
public interface Sorter {

	/**
	 * ソート順
	 * @author vvakame
	 */
	public static enum Order {
		/** 昇順 */
		ASC,
		/** 降順 */
		DESC
	}


	/**
	 * @return the order
	 * @category accessor
	 */
	public Order getOrder();

	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName();
}
