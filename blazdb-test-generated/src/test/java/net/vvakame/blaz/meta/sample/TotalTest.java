package net.vvakame.blaz.meta.sample;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.sqlite.SQLiteKVS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * {@link Datastore} の全体的なテスト.
 * @author vvakame
 */
public class TotalTest {

	SQLiteKVS kvs;


	/**
	 * Test case.
	 * @author vvakame
	 */
	@Test
	public void put() {
		final PrimitiveTypeDataMeta meta = PrimitiveTypeDataMeta.get();

		Key key;
		{
			PrimitiveTypeData data = new PrimitiveTypeData();
			data.setBool(true);
			data.setF(1.125f);
			data.setD(2.5);
			data.setB((byte) 1);
			data.setS((short) 2);
			data.setI(3);
			data.setL(4);

			Datastore.put(data);
			key = data.getKey();
		}
		{
			PrimitiveTypeData data = Datastore.get(meta, key);
			assertThat(data.getKey(), is(key));
			assertThat(data.isBool(), is(true));
			assertThat(data.getF(), is(1.125f));
		}
	}

	/**
	 * 下準備.
	 * @author vvakame
	 */
	@Before
	public void setUp() {
		kvs = new SQLiteKVS(":memory:");
		Datastore.setupDatastore(kvs);
	}

	/**
	 * 後始末.
	 * @author vvakame
	 */
	@After
	public void tearDown() {
		kvs.close();
	}
}
