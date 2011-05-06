package net.vvakame.blaz;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.sqlite.SQLiteKVS;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link Datastore} のテスト
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class DatastoreTest {

	/**
	 * {@link Datastore#put(Entity)} に対して対応しているはずの全ての型を突っ込む.
	 * @author vvakame
	 */
	@Test
	public void put() {
		Entity entity = new Entity();
		entity.setKey(KeyUtil.createKey("hoge", "piyo"));
		entity.setProperty("null", null);
		entity.setProperty("String", "str");
		entity.setProperty("Byte", (byte) 1);
		entity.setProperty("Short", (short) 2);
		entity.setProperty("Integer", 3);
		entity.setProperty("Long", 4L);
		entity.setProperty("BooleanT", true);
		entity.setProperty("BooleanF", false);
		entity.setProperty("Float", 1.1f);
		entity.setProperty("Double", 2.2);
		entity.setProperty("Key", KeyUtil.createKey("hoge", "puyo"));
		entity.setProperty("byte[]", new byte[] {
			1,
			2,
			3
		});
		List<Object> list = new ArrayList<Object>();
		list.add(null);
		list.add("str");
		list.add((byte) 1);
		list.add((short) 2);
		list.add(3);
		list.add(4L);
		list.add(true);
		list.add(false);
		list.add(1.1f);
		list.add(2.2);
		list.add(KeyUtil.createKey("hoge", "payo"));
		list.add(new byte[] {
			1,
			2,
			3
		});
		entity.setProperty("List", list);
		Datastore.put(entity);
	}

	/**
	 * {@link Datastore#put(Entity)} と {@link Datastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void put_get() {
		Key key1;
		{
			Entity entity = new Entity();
			key1 = KeyUtil.createKey("hoge", "piyo");
			entity.setKey(key1);
			entity.setProperty("key1", "value1");
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			Datastore.put(entity);
		}
		Key key2;
		{
			Entity entity = new Entity();
			key2 = KeyUtil.createKey("hoge", "puyo");
			entity.setKey(key2);
			entity.setProperty("key1", "value1");
			Datastore.put(entity);
		}

		Entity entity;

		entity = Datastore.get(key1);
		assertThat(entity.getProperties().size(), is(4));

		entity = Datastore.get(key2);
		assertThat(entity.getProperties().size(), is(1));
	}

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Before
	public void before() {
		ShadowApplication application = Robolectric.getShadowApplication();
		IKeyValueStore kvs = new SQLiteKVS(application.getApplicationContext());
		Datastore.init(kvs);
	}
}
