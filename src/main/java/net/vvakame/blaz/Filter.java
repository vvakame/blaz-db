package net.vvakame.blaz;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class Filter {

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
	 * the constructor.
	 * @param target 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public Filter(FilterTarget target, FilterOption option, Object value) {
		if (target != FilterTarget.KEY) {
			throw new IllegalArgumentException();
		}

		this.target = target;
		this.option = option;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param target 
	 * @param name
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public Filter(FilterTarget target, String name, FilterOption option, Object value) {
		if (target == FilterTarget.KEY && name != null) {
			throw new IllegalArgumentException();
		}

		this.target = target;
		this.name = name;
		this.option = option;
		this.value = value;
	}


	FilterTarget target;

	String name;

	FilterOption option;

	Object value;


	/**
	 * @return the target
	 * @category accessor
	 */
	public FilterTarget getTarget() {
		return target;
	}

	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the option
	 * @category accessor
	 */
	public FilterOption getOption() {
		return option;
	}

	/**
	 * @return the value
	 * @category accessor
	 */
	public Object getValue() {
		return value;
	}
}
