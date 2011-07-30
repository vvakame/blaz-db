package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.Transaction;

/**
 * {@link SQLiteKVS} に対する {@link Transaction} の実装.
 * @author vvakame
 */
public class SqlTransaction implements Transaction {

	static interface ActionCallback {

		public boolean onCommit();

		public boolean onRollback();
	}


	boolean active = true;

	ActionCallback callback;


	/**
	 * the constructor.
	 * @param callback
	 * @category constructor
	 */
	public SqlTransaction(ActionCallback callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback is required.");
		}
		this.callback = callback;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public synchronized boolean commit() {
		if (!active) {
			return false;
		}
		boolean result = callback.onCommit();
		if (result) {
			active = false;
		}
		return result;
	}

	@Override
	public synchronized boolean rollback() {
		if (!active) {
			return false;
		}
		boolean result = callback.onRollback();
		if (result) {
			active = false;
		}
		return result;
	}
}
