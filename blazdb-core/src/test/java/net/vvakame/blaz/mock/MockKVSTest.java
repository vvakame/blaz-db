package net.vvakame.blaz.mock;

import net.vvakame.blaz.RawDatastoreTestBase;

import org.junit.Before;

/**
 * {@link MockKVS} のテストケース.
 * @author vvakame
 */
public class MockKVSTest extends RawDatastoreTestBase {

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Override
	@Before
	public void before() {
		kvs = new MockKVS();
	}
}
