package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.RawDatastore;
import net.vvakame.blaz.RawDatastoreTestBase;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

/**
 * {@link SQLiteKVS} のテストケース.
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class SQLiteKVSTest extends RawDatastoreTestBase {

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Override
	@Before
	public void before() {
		ShadowApplication application = Robolectric.getShadowApplication();
		SQLiteKVS kvs = new SQLiteKVS(application.getApplicationContext());
		RawDatastore.init(kvs);
	}
}
