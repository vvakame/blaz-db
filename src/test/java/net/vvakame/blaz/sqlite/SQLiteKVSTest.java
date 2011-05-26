package net.vvakame.blaz.sqlite;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Filter.FilterTarget;
import net.vvakame.blaz.KeyUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

/**
 * {@link SQLiteKVS} のテストケース.
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class SQLiteKVSTest {

	SQLiteKVS mKvs;


	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void find_Key_Single() {
		Entity entity;

		entity = new Entity();
		entity.setKey(KeyUtil.createKey("hoge", "piyo"));
		mKvs.put(entity);

		entity = new Entity();
		entity.setKey(KeyUtil.createKey("hoge", "puyo"));
		mKvs.put(entity);

		mKvs.find(new Filter(FilterTarget.KEY, FilterOption.EQ, KeyUtil.createKey("hoge", "puyo")));
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
