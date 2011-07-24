package net.vvakame.blaz;

/**
 * KVS の Transaction
 * @author vvakame
 */
public interface Transaction {

	/**
	 * 本トランザクションが有効かどうか
	 * @return 有効か否か
	 * @author vvakame
	 */
	public boolean isActive();

	/**
	 * トランザクションを確定する.
	 * @return 成否
	 * @author vvakame
	 */
	public boolean commit();

	/**
	 * トランザクションを巻き戻す.
	 * @return 成否
	 * @author vvakame
	 */
	public boolean rollback();
}
