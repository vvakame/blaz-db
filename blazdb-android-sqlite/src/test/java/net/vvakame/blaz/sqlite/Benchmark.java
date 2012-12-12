package net.vvakame.blaz.sqlite;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

/**
 * ベンチマークテスト
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class Benchmark extends net.vvakame.blaz.compat.Benchmark {

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Before
	public void before() {
		ShadowApplication application = Robolectric.getShadowApplication();
		kvs = new SQLiteKVS(application.getApplicationContext());
	}
}
