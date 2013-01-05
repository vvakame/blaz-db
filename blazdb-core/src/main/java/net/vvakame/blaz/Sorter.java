package net.vvakame.blaz;

/**
 * Entity検索結果をソートするためのソータ
 * 
 * @author vvakame
 */
public abstract class Sorter {

	/**
	 * ソート順
	 * 
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
	public abstract Order getOrder();

	/**
	 * @return the name
	 * @category accessor
	 */
	public abstract String getName();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName()).append(" ");
		builder.append(getName()).append(" ");
		builder.append(getOrder()).append(" ");
		builder.setLength(builder.length() - 1);

		return builder.toString();
	}
}
