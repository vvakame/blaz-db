package net.vvakame.blaz.util;

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
}
