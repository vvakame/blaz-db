package net.vvakame.blaz.mock;

import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.exception.EntityNotFoundException;

/**
 * {@link BareDatastore} のモック実装.<br>
 * フル機能は提供しない.
 * @author vvakame
 */
public class MockKVS extends BareDatastore {

	@Override
	public Entity get(Key key) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getOrNull(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Entity entity) throws NullPointerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Key key) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Entity> find(Filter... filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Key> findAsKey(Filter... filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction beginTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkFilter(Filter... filters) {
		// TODO Auto-generated method stub
		return false;
	}
}
