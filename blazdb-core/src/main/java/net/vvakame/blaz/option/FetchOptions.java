package net.vvakame.blaz.option;

public class FetchOptions {

	Integer limit;

	Integer offset;

	/**
	 * @param limit
	 *            the limit to set
	 * @category accessor
	 */
	public void limit(int limit) {
		this.limit = limit;
	}

	/**
	 * @param offset
	 *            the offset to set
	 * @category accessor
	 */
	public void offset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the limit
	 * @category accessor
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * @return the offset
	 * @category accessor
	 */
	public Integer getOffset() {
		return offset;
	}
}
