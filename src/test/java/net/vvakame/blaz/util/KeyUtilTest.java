package net.vvakame.blaz.util;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.util.KeyUtil;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link KeyUtil} のテストケース.
 * @author vvakame
 */
public class KeyUtilTest {

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Test
	public void keyToString() {
		Key key1 = KeyUtil.createKey("hoge", "piyo");
		String str = KeyUtil.keyToString(key1);
		Key key2 = KeyUtil.stringToKey(str);

		assertThat(key1, equalTo(key2));
	}
}
