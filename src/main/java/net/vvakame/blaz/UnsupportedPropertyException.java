package net.vvakame.blaz;

/**
 * Entityに保存できない要素を保存しようとしたときに投げられる例外.
 * @author vvakame
 */
public class UnsupportedPropertyException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	/**
	 * the constructor.
	 * @category constructor
	 */
	public UnsupportedPropertyException() {
		super();
	}

	/**
	 * the constructor.
	 * @param message
	 * @param cause
	 * @category constructor
	 */
	public UnsupportedPropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * the constructor.
	 * @param message
	 * @category constructor
	 */
	public UnsupportedPropertyException(String message) {
		super(message);
	}

	/**
	 * the constructor.
	 * @param cause
	 * @category constructor
	 */
	public UnsupportedPropertyException(Throwable cause) {
		super(cause);
	}
}
