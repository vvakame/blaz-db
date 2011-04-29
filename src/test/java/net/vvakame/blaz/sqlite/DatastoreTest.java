package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.database.Cursor;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class DatastoreTest {

	ShadowApplication application = Robolectric.getShadowApplication();


	@Test
	public void put() {
		Entity entity = new Entity();
		entity.setKey(KeyUtil.createKey("hoge", "piyo"));
		entity.setProperty("key1", "value1");
		entity.setProperty("key2", "value2");
		entity.setProperty("key3", "value3");
		entity.setProperty("key4", "value4");

		Datastore.put(entity);

		Cursor c;
		c = Datastore.sDb.query("KEYS", null, null, null, null, null, null);
		assertThat(c.getCount(), is(1));
		c = Datastore.sDb.query("VALUES", null, null, null, null, null, null);
		assertThat(c.getCount(), is(4));
	}

	@Test
	public void get() {
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

	@Before
	public void before() {
		Datastore.init(application.getApplicationContext());
	}
}
