package net.vvakame.blaz.meta.sample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.sqlite.SQLiteKVS;
import net.vvakame.blaz.util.KeyUtil;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

/**
 * {@link Datastore} の全体的なテスト.
 * @author vvakame
 */
public class TotalTest {

	private static final RootDataMeta META = RootDataMeta.get();

	private static final ExtendedDataMeta EXT_META = ExtendedDataMeta.get();

	BareDatastore kvs;


	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void test_extended() {
		Key key = KeyUtil.createKey(META.getKind(), "a");
		Date at = new Date();
		{
			ExtendedData model = new ExtendedData();
			model.setKey(key);
			model.setStr("str");
			model.setDate(at);
			model.setBooleanData(true);
			model.setByteData((byte) 1);
			model.setShortData((short) 2);
			model.setInteger(3);
			model.setLongData(4);
			model.setFloatData(5.5f);
			model.setDoubleData(6.75);
			List<String> list = new ArrayList<String>();
			list.add("hoge");
			list.add("fuga");
			list.add("piyo");
			model.setList(list);
			Datastore.put(model);
		}
		{
			List<ExtendedData> list = Datastore.query(EXT_META).asList();
			assertThat(list.size(), is(1));
			ExtendedData model = list.get(0);
			assertThat(model.getKey(), is(key));
			assertThat(model.getStr(), is("str"));
			assertThat(model.getDate(), is(at));
			assertThat(model.isBooleanData(), is(true));
			assertThat(model.getByteData(), is((byte) 1));
			assertThat(model.getShortData(), is((short) 2));
			assertThat(model.getInteger(), is(3));
			assertThat(model.getLongData(), is(4L));
			assertThat(model.getFloatData(), is(5.5f));
			assertThat(model.getDoubleData(), is(6.75));
			assertThat(model.getList().get(0), is("hoge"));
			assertThat(model.getList().get(1), is("fuga"));
			assertThat(model.getList().get(2), is("piyo"));
		}
		{
			List<RootData> list = Datastore.query(META).asList();
			assertThat(list.size(), is(1));
			RootData model = list.get(0);
			assertThat(model, instanceOf(ExtendedData.class));
			assertThat(model.getKey(), is(key));
			assertThat(model.getStr(), is("str"));
			assertThat(model.getDate(), is(at));
			assertThat(model.getInteger(), is(3));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void testEqualCriteria() {
		Date at = new Date();
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "a"));
			model.setInteger(1);
			model.setDate(new Date(at.getTime() - 1));
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "b"));
			model.setInteger(2);
			model.setDate(null);
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "c"));
			model.setInteger(1);
			model.setDate(at);
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "d"));
			model.setInteger(1);
			model.setDate(null);
			Datastore.put(model);
		}

		List<RootData> list;

		list = Datastore.query(META).asList();
		assertThat(list.size(), is(4));

		list = Datastore.query(META).filter(META.integer.equal(2)).asList();
		assertThat(list.size(), is(1));

		list = Datastore.query(META).filter(META.integer.equal(1)).asList();
		assertThat(list.size(), is(3));

		list = Datastore.query(META).filter(META.date.equal(null)).asList();
		assertThat(list.size(), is(2));
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void testKeyAttributeMeta() {
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "a"));
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "b"));
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "c"));
			Datastore.put(model);
		}

		List<RootData> list;

		list = Datastore.query(META).asList();
		assertThat(list.size(), is(3));

		Key key = KeyUtil.createKey(META.getKind(), "b");
		{
			list = Datastore.query(META).filter(META.key.equal(key)).asList();
			assertThat(list.size(), is(1));
			assertThat(list.get(0).getKey(), is(key));
		}
		{
			Key targetKey = KeyUtil.createKey(META.getKind(), "c");
			list = Datastore.query(META).filter(META.key.greaterThan(key)).asList();
			assertThat(list.size(), is(1));
			assertThat(list.get(0).getKey(), is(targetKey));
		}
		{
			Key targetKey = KeyUtil.createKey(META.getKind(), "a");
			list = Datastore.query(META).filter(META.key.lessThan(key)).asList();
			assertThat(list.size(), is(1));
			assertThat(list.get(0).getKey(), is(targetKey));
		}
		{
			Key targetKey = KeyUtil.createKey(META.getKind(), "a");
			list = Datastore.query(META).filter(META.key.lessThan(key)).asList();
			assertThat(list.size(), is(1));
			assertThat(list.get(0).getKey(), is(targetKey));
		}
		{
			Key targetKey = KeyUtil.createKey(META.getKind(), "a");
			list = Datastore.query(META).filter(META.key.lessThan(key)).asList();
			assertThat(list.size(), is(1));
			assertThat(list.get(0).getKey(), is(targetKey));
		}
		{
			Key targetKey1 = KeyUtil.createKey(META.getKind(), "b");
			Key targetKey2 = KeyUtil.createKey(META.getKind(), "c");
			list = Datastore.query(META).filter(META.key.greaterThanOrEqual(key)).asList();
			assertThat(list.size(), is(2));
			assertThat(list.get(0).getKey(), isOneOf(targetKey1, targetKey2));
			assertThat(list.get(1).getKey(), isOneOf(targetKey1, targetKey2));
		}
		{
			Key targetKey1 = KeyUtil.createKey(META.getKind(), "a");
			Key targetKey2 = KeyUtil.createKey(META.getKind(), "b");
			list = Datastore.query(META).filter(META.key.lessThanOrEqual(key)).asList();
			assertThat(list.size(), is(2));
			assertThat(list.get(0).getKey(), isOneOf(targetKey1, targetKey2));
			assertThat(list.get(1).getKey(), isOneOf(targetKey1, targetKey2));
		}
		{
			Key targetKey1 = KeyUtil.createKey(META.getKind(), "a");
			Key targetKey2 = KeyUtil.createKey(META.getKind(), "c");
			list = Datastore.query(META).filter(META.key.in(targetKey1, targetKey2)).asList();
			assertThat(list.size(), is(2));
			assertThat(list.get(0).getKey(), isOneOf(targetKey1, targetKey2));
			assertThat(list.get(1).getKey(), isOneOf(targetKey1, targetKey2));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void testPropertySorter() {
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "a"));
			model.setInteger(2);
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "b"));
			model.setInteger(1);
			model.setStr("b");
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "c"));
			model.setInteger(3);
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "d"));
			model.setInteger(1);
			model.setStr("a");
			Datastore.put(model);
		}

		{
			List<RootData> list =
					Datastore.query(META).sort(META.integer.asc, META.str.asc).asList();
			assertThat(list.size(), is(4));
			int i = 0;
			assertThat(list.get(i++).getKey().getName(), is("d"));
			assertThat(list.get(i++).getKey().getName(), is("b"));
			assertThat(list.get(i++).getKey().getName(), is("a"));
			assertThat(list.get(i++).getKey().getName(), is("c"));
		}
		{
			List<RootData> list =
					Datastore.query(META).sort(META.integer.desc, META.str.desc).asList();
			assertThat(list.size(), is(4));
			int i = 0;
			assertThat(list.get(i++).getKey().getName(), is("c"));
			assertThat(list.get(i++).getKey().getName(), is("a"));
			assertThat(list.get(i++).getKey().getName(), is("b"));
			assertThat(list.get(i++).getKey().getName(), is("d"));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void testKeySorter() {
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "a"));
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "b"));
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "c"));
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "d"));
			Datastore.put(model);
		}

		{
			List<RootData> list = Datastore.query(META).sort(META.key.asc).asList();
			assertThat(list.size(), is(4));
			int i = 0;
			assertThat(list.get(i++).getKey().getName(), is("a"));
			assertThat(list.get(i++).getKey().getName(), is("b"));
			assertThat(list.get(i++).getKey().getName(), is("c"));
			assertThat(list.get(i++).getKey().getName(), is("d"));
		}
		{
			List<RootData> list = Datastore.query(META).sort(META.key.desc).asList();
			assertThat(list.size(), is(4));
			int i = 0;
			assertThat(list.get(i++).getKey().getName(), is("d"));
			assertThat(list.get(i++).getKey().getName(), is("c"));
			assertThat(list.get(i++).getKey().getName(), is("b"));
			assertThat(list.get(i++).getKey().getName(), is("a"));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Before
	public void setUp() {
		kvs = new SQLiteKVS();
		Datastore.setupDatastore(kvs);
	}
}
