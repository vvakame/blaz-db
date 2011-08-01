package net.vvakame.blaz.bare;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.sorter.KeyAscSorter;
import net.vvakame.blaz.sorter.KeyDescSorter;
import net.vvakame.blaz.sorter.PropertyAscSorter;
import net.vvakame.blaz.sorter.PropertyDescSorter;
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

	/**
	 * Entityの昇順・降順ソートのテスト.
	 * @author vvakame
	 */
	@Test
	public void testEntitySort_normal() {
		List<Entity> entities = new ArrayList<Entity>();
		{
			Entity entity = new Entity("a", "a");
			entity.setProperty("a", null);
			entities.add(entity);
		}
		{
			Entity entity = new Entity("a", "b");
			entity.setProperty("a", "a");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("a", "c");
			entity.setProperty("a", "b");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b", "a");
			entity.setProperty("a", null);
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b", "b");
			entity.setProperty("a", "a");
			entities.add(entity);
		}
		{
			Entity entity = new Entity("b", "c");
			entity.setProperty("a", "b");
			entities.add(entity);
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyDescSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "a")));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyAscSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "c")));
		}
	}

	/**
	 * Entityの昇順・降順ソートのテスト.
	 * @author vvakame
	 */
	@Test
	public void testEntitySort_string() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(makeEntity("a", "a", "2"));
		entities.add(makeEntity("a", "a", "c"));
		entities.add(makeEntity("a", "a", "3"));
		entities.add(makeEntity("a", "a", "b"));
		entities.add(makeEntity("b", "a", "b"));
		entities.add(makeEntity("a", "a", null));
		entities.add(makeEntity("a", "a", "1"));
		entities.add(makeEntity("a", "a", "a"));
		entities.add(makeEntity("a", "a", "d"));

		// stable sort...

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyDescSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "d")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "3")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "2")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "1")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "null")));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyAscSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "null")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "1")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "2")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "3")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(KeyUtil.createKey("a", "d")));
		}
	}

	Entity makeEntity(String kind, String name, Object value) {
		Entity entity = new Entity(kind, String.valueOf(value));
		entity.setProperty(name, value);
		return entity;
	}
}
