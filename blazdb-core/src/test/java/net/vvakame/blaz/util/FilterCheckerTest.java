package net.vvakame.blaz.util;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.filter.KeyEqFilter;
import net.vvakame.blaz.filter.KindEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanEqFilter;
import net.vvakame.blaz.mock.MockKVS;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link FilterChecker} のテストケース.
 * @author vvakame
 */
public class FilterCheckerTest {

	BareDatastore kvs;


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
		Filter keyFilter = new KeyEqFilter(key);
		Filter kindFilter1 = new KindEqFilter("hoge");
		Filter kindFilter2 = new KindEqFilter("fuga");
		Filter propertyFilter = new PropertyBooleanEqFilter("name", true);

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
		Filter keyFilter = new KeyEqFilter(key);
		Filter kindFilter = new KindEqFilter("hoge");
		Filter propertyFilter = new PropertyBooleanEqFilter("name", true);

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
			Filter filter = new KindEqFilter("hoge");
			assertThat(FilterChecker.hasKindFilter(filter), is(true));
		}
		{
			Filter filter1 = new KindEqFilter("hoge");
			Filter filter2 = new PropertyBooleanEqFilter("name", true);
			assertThat(FilterChecker.hasKindFilter(filter1, filter2), is(true));
		}
		{
			Filter filter1 = new PropertyBooleanEqFilter("name", true);
			Filter filter2 = new PropertyBooleanEqFilter("name", true);
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
			Filter filter = new KeyEqFilter(KeyUtil.createKey("hoge", 1));
			assertThat(FilterChecker.hasKeyFilter(filter), is(true));
		}
		{
			Filter filter1 = new KeyEqFilter(KeyUtil.createKey("hoge", 1));
			Filter filter2 = new PropertyBooleanEqFilter("name", true);
			assertThat(FilterChecker.hasKeyFilter(filter1, filter2), is(true));
		}
		{
			Filter filter1 = new PropertyBooleanEqFilter("name", true);
			Filter filter2 = new PropertyBooleanEqFilter("name", true);
			assertThat(FilterChecker.hasKeyFilter(filter1, filter2), is(false));
		}
	}

	/**
	 * {@link BareDatastore} のセットアップ
	 * @author vvakame
	 */
	@Before
	public void setUp() {
		kvs = new MockKVS();
	}
}
