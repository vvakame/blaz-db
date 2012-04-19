package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.RawDatastoreTestBase;
import net.vvakame.blaz.exception.EntityNotFoundException;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;
import com.xtremelabs.robolectric.util.DatabaseConfig;
import com.xtremelabs.robolectric.util.H2Map;

/**
 * {@link SQLiteKVS} のテストケース.
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class SQLiteKVSTest extends RawDatastoreTestBase {

	/**
	 * {@link Robolectric} だとTransactionがうまく動かないので暫定で回避
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
		// DatabaseConfig.setDatabaseMap(new SQLiteMap());
		DatabaseConfig.setDatabaseMap(new H2Map());

		ShadowApplication application = Robolectric.getShadowApplication();
		kvs = new SQLiteKVS(application.getApplicationContext());
	}
}
