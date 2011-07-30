package net.vvakame.blaz.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;

/**
 * {@link Transaction} のモック実装
 * @author vvakame
 */
public class MockTransaction implements Transaction {

	boolean active = true;

	MockKVS kvs;

	Map<String, Map<Key, Entity>> backupDb;


	/**
	 * the constructor.
	 * @param kvs
	 * @category constructor
	 */
	@SuppressWarnings("unchecked")
	public MockTransaction(MockKVS kvs) {
		if (kvs == null) {
			throw new IllegalArgumentException("kvs is required.");
		}

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(kvs.db);

			byte[] bytes = baos.toByteArray();

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);

			backupDb = (Map<String, Map<Key, Entity>>) ois.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		this.kvs = kvs;
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
		active = false;
		return true;
	}

	@Override
	public synchronized boolean rollback() {
		if (!active) {
			return false;
		}
		kvs.db = backupDb;
		active = false;
		return true;
	}
}
