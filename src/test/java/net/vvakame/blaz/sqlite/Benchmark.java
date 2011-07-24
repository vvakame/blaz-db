package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.vvakame.blaz.RawDatastore;
import net.vvakame.blaz.Entity;
import net.vvakame.blaz.IKeyValueStore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.util.Log;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

/**
 * ベンチマークテスト
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class Benchmark {

	static final String TAG = "SQLiteBench";

	IKeyValueStore kvs;


	/**
	 * {@link RawDatastore#put(Entity)} と {@link RawDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void put100件() {
		List<Entity> entities = new ArrayList<Entity>(100);
		for (int i = 0; i < 100; i++) {
			entities.add(genEntity(i));
		}

		System.gc();
		long begin = System.currentTimeMillis();
		for (Entity entity : entities) {
			RawDatastore.put(entity);
		}
		long end = System.currentTimeMillis();

		Log.d(TAG, "put100 uses " + (end - begin) + " ms.");
		System.out.println("put100 uses " + (end - begin) + " ms.");
	}

	/**
	 * {@link RawDatastore#put(Entity)} と {@link RawDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void get300件() {
		List<Entity> entities = new ArrayList<Entity>(300);
		for (int i = 0; i < 300; i++) {
			Entity entity = genEntity(i);
			entities.add(entity);
			RawDatastore.put(entity);
		}

		System.gc();
		long begin = System.currentTimeMillis();
		for (Entity entity : entities) {
			RawDatastore.get(entity.getKey());
		}
		long end = System.currentTimeMillis();

		Log.d(TAG, "get300 uses " + (end - begin) + " ms.");
		System.out.println("get300 uses " + (end - begin) + " ms.");
	}

	static Entity genEntity(int seed) {
		Random rnd = new Random(seed);

		Entity entity = new Entity();
		Key key = KeyUtil.createKey("hoge" + rnd.nextInt(), "piyopiyo" + seed);
		entity.setKey(key);
		int max = rnd.nextInt() % 100;
		for (int i = 0; i < max; i++) {
			entity.setProperty("key" + i, "value" + i);
		}

		return entity;
	}

	/**
	 * 前準備
	 * @author vvakame
	 */
	@Before
	public void before() {
		ShadowApplication application = Robolectric.getShadowApplication();
		kvs = new SQLiteKVS(application.getApplicationContext());
		RawDatastore.init(kvs);
	}
}
