package net.vvakame.sample.obj;

import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.sqlite.SQLiteKVS;
import net.vvakame.blaz.util.KeyUtil;
import net.vvakame.sample.Datastore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class RootDataTest {

	private static final RootDataMeta META = RootDataMeta.get();

	BareDatastore kvs;


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
			Datastore.put(model);
		}
		{
			RootData model = new RootData();
			model.setKey(KeyUtil.createKey(META.getKind(), "c"));
			model.setInteger(1);
			model.setDate(at);
			Datastore.put(model);
		}

		List<RootData> list;

		list = Datastore.query(META).asList();
		assertThat(list.size(), is(3));

		list = Datastore.query(META).filter(META.integer.equal(2)).asList();
		assertThat(list.size(), is(1));

		list = Datastore.query(META).filter(META.integer.equal(1)).asList();
		assertThat(list.size(), is(2));

		list = Datastore.query(META).filter(META.integer.equal(1), META.date.equal(at)).asList();
		assertThat(list.size(), is(1));
	}

	@Test
	public void testKeyAttributeMeta() {
		Date at = new Date();
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
	}

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

	@Before
	public void setUp() {
		ShadowApplication application = Robolectric.getShadowApplication();
		kvs = new SQLiteKVS(application.getApplicationContext());
		Datastore.setupDatastore(kvs);
	}
}
