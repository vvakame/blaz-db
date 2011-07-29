package net.vvakame.blaz;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public interface Filter {

	/**
	 * 検索対象.
	 * @author vvakame
	 */
	public static enum FilterTarget {
		/** Key */
		KEY,
		/** カインド */
		KIND,
		/** プロパティ */
		PROPERTY
	}

	/**
	 * 検索オプション.
	 * @author vvakame
	 */
	public static enum FilterOption {
		/** 等しい */
		EQ,
		/** より大きい */
		GT,
		/** より小さい */
		LT,
		/** より大きいか、等しい */
		GT_EQ,
		/** より小さいか、等しい */
		LT_EQ,
	}


	/**
	 * @return the target
	 * @category accessor
	 */
	public FilterTarget getTarget();

	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName();

	/**
	 * @return the option
	 * @category accessor
	 */
	public FilterOption getOption();

	/**
	 * @return the value
	 * @category accessor
	 */
	public Object getValue();
}
