package net.vvakame.blaz.bare;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.sorter.KeyAscSorter;
import net.vvakame.blaz.sorter.KeyDescSorter;
import net.vvakame.blaz.util.KeyUtil;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link BareSortUtil} のテストケース.
 * @author vvakame
 */
public class BareSortUtilTest {

	/**
	 * Keyの昇順・降順ソートのテスト.
	 * @author vvakame
	 */
	@Test
	public void testKeySort() {
		List<Entity> entities = new ArrayList<Entity>();
		{
			Entity entity = new Entity("a", "a");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("a", "b");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("a", "c");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("a");
			entity.setKey(KeyUtil.createKey("a", 1));
			entities.add(entity);
		}
		{
			Entity entity = new Entity("a");
			entity.setKey(KeyUtil.createKey("a", 2));
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b", "a");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b", "b");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b", "c");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b");
			entity.setKey(KeyUtil.createKey("b", 1));
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b");
			entity.setKey(KeyUtil.createKey("b", 2));
			entities.add(entity);
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new KeyDescSorter()
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", 2)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", 1)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", 2)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", 1)));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new KeyAscSorter()
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", 1)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", 2)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", 1)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", 2)));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "c")));
		}
	}
}
