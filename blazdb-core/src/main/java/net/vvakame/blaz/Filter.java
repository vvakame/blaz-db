package net.vvakame.blaz;

/**
 * Entityを検索するためのフィルタ
 * 
 * @author vvakame
 */
public abstract class Filter {

	/**
	 * 検索対象.
	 * 
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
	 * 
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
		/** 指定された何かに当てはまる */
		IN,
	}

	/**
	 * @return the target
	 * @category accessor
	 */
	public abstract FilterTarget getTarget();

	/**
	 * @return the name
	 * @category accessor
	 */
	public abstract String getName();

	/**
	 * @return the option
	 * @category accessor
	 */
	public abstract FilterOption getOption();

	/**
	 * @return the value
	 * @category accessor
	 */
	public abstract Object getValue();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName()).append(" ");
		builder.append(getTarget()).append(" ");
		builder.append(getOption()).append(" ");
		builder.append(getName()).append(" ");
		try {
			builder.append(getValue()).append(" ");
		} catch (UnsupportedOperationException e) {
		}
		builder.setLength(builder.length() - 1);

		return builder.toString();
	}
}
