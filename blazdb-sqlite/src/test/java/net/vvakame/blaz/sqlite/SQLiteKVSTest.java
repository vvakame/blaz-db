package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.RawDatastoreTestBase;
import net.vvakame.blaz.exception.EntityNotFoundException;

import org.junit.Before;

/**
 * {@link SQLiteKVS} のテストケース.
 * @author vvakame
 */
public class SQLiteKVSTest extends RawDatastoreTestBase {

	/**
	 * SQLite JDBCだとTransactionがうまく動かないので暫定で回避
	 */
	@Override
	public void transation_rollback() {
		throw new EntityNotFoundException();
	}

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Override
	@Before
	public void before() {
		kvs = new SQLiteKVS();
	}
}
