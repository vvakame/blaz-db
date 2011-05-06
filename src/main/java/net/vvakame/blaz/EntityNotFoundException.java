package net.vvakame.blaz;

/**
 * 指定のEntityが発見できなかった場合に投げられる例外.
 * @author vvakame
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	/**
	 * the constructor.
	 * @category constructor
	 */
	public EntityNotFoundException() {
		super();
	}

	/**
	 * the constructor.
	 * @param message
	 * @param cause
	 * @category constructor
	 */
	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * the constructor.
	 * @param message
	 * @category constructor
	 */
	public EntityNotFoundException(String message) {
		super(message);
	}

	/**
	 * the constructor.
	 * @param cause
	 * @category constructor
	 */
	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}
}
