package net.vvakame.blaz.meta.sample;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.sqlite.SQLiteKVS;
import net.vvakame.blaz.util.KeyUtil;

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
		final AllSuppotedTypeDataMeta meta = AllSuppotedTypeDataMeta.get();

		Key key;
		{
			AllSuppotedTypeData data = new AllSuppotedTypeData();
			data.setK(KeyUtil.createKey("hoge", "key-dayo"));
			data.setBool(true);
			data.setB((byte) 1);
			data.setS((short) 2);
			data.setI(3);
			data.setL(4);
			data.setF(1.125f);
			data.setD(2.5);
			data.setBoolW(false);
			data.setbW((byte) 11);
			data.setsW((short) 22);
			data.setiW(33);
			data.setlW(44L);
			data.setfW(521.125f);
			data.setdW(52.5);
			data.setStr("ヾ(*´∀｀*)ﾉｷｬｯｷｬ");
			data.setBytes("Hello world!".getBytes());

			Datastore.put(data);
			key = data.getKey();
		}
		{
			AllSuppotedTypeData data = Datastore.get(meta, key);
			assertThat(data.getKey(), is(key));
		}
		{
			AllSuppotedTypeData data = new AllSuppotedTypeData();
			data.setK(null);
			data.setBool(true);
			data.setB((byte) 111);
			data.setS((short) 222);
			data.setI(333);
			data.setL(444);
			data.setF(0.999f);
			data.setD(0.001);
			data.setBoolW(null);
			data.setbW(null);
			data.setsW(null);
			data.setiW(null);
			data.setlW(null);
			data.setfW(null);
			data.setdW(null);
			data.setStr(null);
			data.setBytes(null);

			Datastore.put(data);
			key = data.getKey();
		}
		{
			AllSuppotedTypeData data = Datastore.get(meta, key);
			assertThat(data.getKey(), is(key));
		}
	}

	/**
	 * Test case.
	 * @author vvakame
	 */
	@Test
	public void put_primitive() {
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
