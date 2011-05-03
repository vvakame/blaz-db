package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.Datastore;
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

/**
 * {@link SQLiteKVS} のテストケース.
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class SQLiteKVSTest {

	SQLiteKVS mKvs;


	/**
	 * {@link Datastore#put(Entity)} と {@link Datastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void sampleQuery() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo"));
			entity.setProperty("key1", "value1");
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("rnd", "rnd1");
			Datastore.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "puyo"));
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			entity.setProperty("rnd", "rnd2");
			Datastore.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "payo"));
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			entity.setProperty("key5", "value5");
			entity.setProperty("rnd", "rnd3");
			Datastore.put(entity);
		}

		Cursor c;

		c = mKvs.mDb.rawQuery("SELECT KEY_STR FROM VALUES WHERE NAME = ?", new String[] {
			"key3"
		});
		assertThat(c.moveToFirst(), is(true));
		assertThat(c.getCount(), is(3));

		c =
				mKvs.mDb
					.rawQuery(
							"(SELECT KEY_STR FROM VALUES WHERE NAME = ?) INTERSECT (SELECT KEY_STR FROM VALUES WHERE NAME = ?)",
							new String[] {
								"key2",
								"key3"
							});
		assertThat(c.moveToFirst(), is(true));
		assertThat(c.getCount(), is(2));

		c =
				mKvs.mDb
					.rawQuery(
							"(SELECT KEY_STR FROM VALUES WHERE NAME = ?) UNION (SELECT KEY_STR FROM VALUES WHERE NAME = ?)",
							new String[] {
								"key2",
								"key3"
							});
		assertThat(c.moveToFirst(), is(true));
		assertThat(c.getCount(), is(3));

		c = mKvs.mDb.rawQuery("SELECT KEY_STR FROM VALUES WHERE VAL_STR = ?", new String[] {
			"rnd3"
		});
		assertThat(c.moveToFirst(), is(true));
		assertThat(c.getCount(), is(1));
	}

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Before
	public void before() {
		ShadowApplication application = Robolectric.getShadowApplication();
		mKvs = new SQLiteKVS(application.getApplicationContext());
		Datastore.init(mKvs);
	}
}
