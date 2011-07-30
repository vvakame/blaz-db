package net.vvakame.blaz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.vvakame.blaz.util.KeyUtil;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link Key} のテストケース
 * @author vvakame
 */
public class KeyTest {

	/**
	 * {@link #compareTo()} の動作確認.
	 * @author vvakame
	 */
	@Test
	public void compareTo() {
		List<Key> keyList = new ArrayList<Key>();

		keyList.add(KeyUtil.createKey("a", 1));
		keyList.add(KeyUtil.createKey("c", 1));
		keyList.add(KeyUtil.createKey("b", 1));

		keyList.add(KeyUtil.createKey("d", 1));
		keyList.add(KeyUtil.createKey("d", "str1"));
		keyList.add(KeyUtil.createKey("d", 2));
		keyList.add(KeyUtil.createKey("d", "str2"));

		keyList.add(KeyUtil.createKey("e", "あ"));
		keyList.add(KeyUtil.createKey("e", "い"));

		Collections.sort(keyList);

		int i = 0;
		assertThat(keyList.get(i++), is(KeyUtil.createKey("a", 1)));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("b", 1)));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("c", 1)));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("d", 1)));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("d", 2)));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("d", "str1")));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("d", "str2")));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("e", "あ")));
		assertThat(keyList.get(i++), is(KeyUtil.createKey("e", "い")));
	}
}
