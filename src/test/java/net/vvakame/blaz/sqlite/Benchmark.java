package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.util.KeyUtil;

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

	BareDatastore kvs;


	/**
	 * 暖機
	 * @author vvakame
	 */
	@Test
	public void warmup() {
		List<Entity> entities = new ArrayList<Entity>(100);
		for (int i = 0; i < 200; i++) {
			entities.add(genEntity(i));
		}

		{
			System.gc();
			long begin = System.currentTimeMillis();
			Transaction tx = kvs.beginTransaction();
			for (Entity entity : entities) {
				kvs.put(entity);
			}
			tx.commit();
			long end = System.currentTimeMillis();

			Log.d(TAG, "warmup uses " + (end - begin) + " ms.");
			System.out.println("warmup uses " + (end - begin) + " ms.");
		}
		{
			System.gc();
			long begin = System.currentTimeMillis();
			Transaction tx = kvs.beginTransaction();
			for (Entity entity : entities) {
				kvs.get(entity.getKey());
			}
			tx.commit();
			long end = System.currentTimeMillis();

			Log.d(TAG, "warmup uses " + (end - begin) + " ms.");
			System.out.println("warmup uses " + (end - begin) + " ms.");
		}
	}

	/**
	 * {@link BareDatastore#put(Entity)} と {@link BareDatastore#get(Key)} の動作確認
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
			kvs.put(entity);
		}
		long end = System.currentTimeMillis();

		Log.d(TAG, "put100 uses " + (end - begin) + " ms.");
		System.out.println("put100 uses " + (end - begin) + " ms.");
	}

	/**
	 * {@link BareDatastore#put(Entity)} と {@link BareDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void get300件() {
		List<Entity> entities = new ArrayList<Entity>(300);
		for (int i = 0; i < 300; i++) {
			Entity entity = genEntity(i);
			entities.add(entity);
			kvs.put(entity);
		}

		System.gc();
		long begin = System.currentTimeMillis();
		for (Entity entity : entities) {
			kvs.get(entity.getKey());
		}
		long end = System.currentTimeMillis();

		Log.d(TAG, "get300 uses " + (end - begin) + " ms.");
		System.out.println("get300 uses " + (end - begin) + " ms.");
	}

	/**
	 * {@link BareDatastore#put(Entity)} と {@link BareDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void put100件_withTx() {
		List<Entity> entities = new ArrayList<Entity>(100);
		for (int i = 0; i < 100; i++) {
			entities.add(genEntity(i));
		}

		System.gc();
		long begin = System.currentTimeMillis();
		Transaction tx = kvs.beginTransaction();
		for (Entity entity : entities) {
			kvs.put(entity);
		}
		tx.commit();
		long end = System.currentTimeMillis();

		Log.d(TAG, "put100 with Tx uses " + (end - begin) + " ms.");
		System.out.println("put100 with Tx uses " + (end - begin) + " ms.");
	}

	/**
	 * {@link BareDatastore#put(Entity)} と {@link BareDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void get300件_withTx() {
		List<Entity> entities = new ArrayList<Entity>(300);
		for (int i = 0; i < 300; i++) {
			Entity entity = genEntity(i);
			entities.add(entity);
			kvs.put(entity);
		}

		System.gc();
		long begin = System.currentTimeMillis();
		Transaction tx = kvs.beginTransaction();
		for (Entity entity : entities) {
			kvs.get(entity.getKey());
		}
		tx.commit();
		long end = System.currentTimeMillis();

		Log.d(TAG, "get300 with Tx uses " + (end - begin) + " ms.");
		System.out.println("get300 with Tx uses " + (end - begin) + " ms.");
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
	}
}
