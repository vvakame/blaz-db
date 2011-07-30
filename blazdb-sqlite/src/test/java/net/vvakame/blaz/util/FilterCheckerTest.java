package net.vvakame.blaz.util;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.filter.KeyFilter;
import net.vvakame.blaz.filter.KindFilter;
import net.vvakame.blaz.filter.PropertyFilter;
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
 * {@link FilterChecker} のテストケース.
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class FilterCheckerTest {

	SQLiteKVS kvs;


	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void check_empty() {
		assertThat(FilterChecker.check(kvs), is(true));
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void check_SingleKind() {
		Key key = KeyUtil.createKey("hoge", 1);
		Filter keyFilter = new KeyFilter(FilterOption.EQ, key);
		Filter kindFilter1 = new KindFilter("hoge");
		Filter kindFilter2 = new KindFilter("fuga");
		Filter propertyFilter = new PropertyFilter("name", true);

		assertThat(FilterChecker.check(kvs, kindFilter1), is(true));
		assertThat(FilterChecker.check(kvs, kindFilter1, propertyFilter), is(true));
		assertThat(FilterChecker.check(kvs, kindFilter2, kindFilter2), is(false));
		assertThat(FilterChecker.check(kvs, kindFilter1, keyFilter, propertyFilter), is(false));
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void check_KeyOnly() {
		Key key = KeyUtil.createKey("hoge", 1);
		Filter keyFilter = new KeyFilter(FilterOption.EQ, key);
		Filter kindFilter = new KindFilter("hoge");
		Filter propertyFilter = new PropertyFilter("name", true);

		assertThat(FilterChecker.check(kvs, keyFilter), is(true));
		assertThat(FilterChecker.check(kvs, keyFilter, kindFilter), is(false));
		assertThat(FilterChecker.check(kvs, keyFilter, propertyFilter), is(false));
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void hasKindFilter() {
		{
			Filter filter = new KindFilter("hoge");
			assertThat(FilterChecker.hasKindFilter(filter), is(true));
		}
		{
			Filter filter1 = new KindFilter("hoge");
			Filter filter2 = new PropertyFilter("name", true);
			assertThat(FilterChecker.hasKindFilter(filter1, filter2), is(true));
		}
		{
			Filter filter1 = new PropertyFilter("name", true);
			Filter filter2 = new PropertyFilter("name", true);
			assertThat(FilterChecker.hasKindFilter(filter1, filter2), is(false));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void hasKeyFilter() {
		{
			Filter filter = new KeyFilter(FilterOption.EQ, KeyUtil.createKey("hoge", 1));
			assertThat(FilterChecker.hasKeyFilter(filter), is(true));
		}
		{
			Filter filter1 = new KeyFilter(FilterOption.EQ, KeyUtil.createKey("hoge", 1));
			Filter filter2 = new PropertyFilter("name", true);
			assertThat(FilterChecker.hasKeyFilter(filter1, filter2), is(true));
		}
		{
			Filter filter1 = new PropertyFilter("name", true);
			Filter filter2 = new PropertyFilter("name", true);
			assertThat(FilterChecker.hasKeyFilter(filter1, filter2), is(false));
		}
	}

	/**
	 * {@link BareDatastore} のセットアップ
	 * @author vvakame
	 */
	@Before
	public void setUp() {
		ShadowApplication application = Robolectric.getShadowApplication();
		kvs = new SQLiteKVS(application.getApplicationContext());
	}
}
