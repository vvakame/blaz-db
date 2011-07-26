package net.vvakame.blaz.util;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.filter.KeyFilter;
import net.vvakame.blaz.filter.KindFilter;
import net.vvakame.blaz.filter.PropertyFilter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link FilterChecker} のテストケース.
 * @author vvakame
 */
public class FilterCheckerTest {

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void check_empty() {
		assertThat(FilterChecker.check(), is(true));
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

		assertThat(FilterChecker.check(keyFilter), is(true));
		assertThat(FilterChecker.check(keyFilter, kindFilter), is(false));
		assertThat(FilterChecker.check(keyFilter, propertyFilter), is(false));
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
}
