package net.vvakame.blaz.meta.sample;

import java.util.ArrayList;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.sqlite.JdbcKVS;
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

	JdbcKVS kvs;


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
			data.setKeyList(new ArrayList<Key>());
			data.getKeyList().add(KeyUtil.createKey("fuga", "hoge"));
			data.getKeyList().add(null);
			data.setBoolWList(new ArrayList<Boolean>());
			data.getBoolWList().add(true);
			data.getBoolWList().add(false);
			data.getBoolWList().add(null);
			data.setbWList(new ArrayList<Byte>());
			data.getbWList().add((byte) 1);
			data.getbWList().add(null);
			data.setsWList(new ArrayList<Short>());
			data.getsWList().add((short) 2);
			data.getsWList().add(null);
			data.setiWList(new ArrayList<Integer>());
			data.getiWList().add(3);
			data.getiWList().add(null);
			data.setlWList(new ArrayList<Long>());
			data.getlWList().add(4L);
			data.getlWList().add(null);
			data.setfWList(new ArrayList<Float>());
			data.getfWList().add(1.25f);
			data.getfWList().add(null);
			data.setdWList(new ArrayList<Double>());
			data.getdWList().add(2.5);
			data.getdWList().add(null);
			data.setStrList(new ArrayList<String>());
			data.getStrList().add("foo");
			data.getStrList().add(null);
			data.setBytesList(new ArrayList<byte[]>());
			data.getBytesList().add(new byte[] {
				1,
				2,
				3
			});
			data.getBytesList().add(null);

			Datastore.put(data);
			key = data.getKey();
		}
		{
			AllSuppotedTypeData data = Datastore.get(meta, key);
			assertThat(data.getKey(), is(key));

			assertThat(data.getKeyList().size(), is(2));
			assertThat(data.getKeyList().get(0), is(KeyUtil.createKey("fuga", "hoge")));
			assertThat(data.getKeyList().get(1), nullValue());

			assertThat(data.getBoolWList().size(), is(3));
			assertThat(data.getBoolWList().get(0), is(true));
			assertThat(data.getBoolWList().get(1), is(false));
			assertThat(data.getBoolWList().get(2), nullValue());

			assertThat(data.getbWList().size(), is(2));
			assertThat(data.getbWList().get(0), is((byte) 1));
			assertThat(data.getbWList().get(1), nullValue());

			assertThat(data.getsWList().size(), is(2));
			assertThat(data.getsWList().get(0), is((short) 2));
			assertThat(data.getsWList().get(1), nullValue());

			assertThat(data.getiWList().size(), is(2));
			assertThat(data.getiWList().get(0), is(3));
			assertThat(data.getiWList().get(1), nullValue());

			assertThat(data.getlWList().size(), is(2));
			assertThat(data.getlWList().get(0), is(4L));
			assertThat(data.getlWList().get(1), nullValue());

			assertThat(data.getfWList().size(), is(2));
			assertThat(data.getfWList().get(0), is(1.25f));
			assertThat(data.getfWList().get(1), nullValue());

			assertThat(data.getdWList().size(), is(2));
			assertThat(data.getdWList().get(0), is(2.5));
			assertThat(data.getdWList().get(1), nullValue());

			assertThat(data.getStrList().size(), is(2));
			assertThat(data.getStrList().get(0), is("foo"));
			assertThat(data.getStrList().get(1), nullValue());

			assertThat(data.getBytesList().size(), is(2));
			assertThat(data.getBytesList().get(0).length, is(3));
			assertThat(data.getBytesList().get(0)[0], is((byte) 1));
			assertThat(data.getBytesList().get(0)[1], is((byte) 2));
			assertThat(data.getBytesList().get(0)[2], is((byte) 3));
			assertThat(data.getBytesList().get(1), nullValue());
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
		kvs = JdbcKVS.createSQLiteInstance(":memory:");
		kvs.createView(PrimitiveTypeDataMeta.get());
		kvs.createView(AllSuppotedTypeDataMeta.get());
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
