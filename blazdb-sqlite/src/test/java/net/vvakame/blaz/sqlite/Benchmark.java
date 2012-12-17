package net.vvakame.blaz.sqlite;

import org.junit.Before;

/**
 * ベンチマークテスト
 * @author vvakame
 */
public class Benchmark extends net.vvakame.blaz.compat.Benchmark {

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Before
	public void before() {
		kvs = JdbcKVS.createSQLiteInstance(":memory:");
	}
}
