package net.vvakame.blaz.bare;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
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
		entities.add(makeEntity("a", "a"));
		entities.add(makeEntity("a", "b"));
		entities.add(makeEntity("a", "c"));
		entities.add(makeEntity("a", 1));
		entities.add(makeEntity("a", 2));
		entities.add(makeEntity("b", "a"));
		entities.add(makeEntity("b", "b"));
		entities.add(makeEntity("b", "c"));
		entities.add(makeEntity("b", 1));
		entities.add(makeEntity("b", 2));

		BareSortUtil.sort(entities, new Sorter[] {
			new KeyDescSorter()
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "c")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "a")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", 2)));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", 1)));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", 2)));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", 1)));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new KeyAscSorter()
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", 1)));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", 2)));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "a")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "b")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "c")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", 1)));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", 2)));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "a")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "b")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "c")));
		}
	}

	/**
	 * Entityの昇順・降順ソートのテスト.
	 * @author vvakame
	 */
	@Test
	public void testEntitySort_normal() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(makeEntity("a", "k1", "a", null));
		entities.add(makeEntity("a", "k2", "a", "a"));
		entities.add(makeEntity("a", "k3", "a", "b"));
		entities.add(makeEntity("b", "k1", "a", null));
		entities.add(makeEntity("b", "k2", "a", "a"));
		entities.add(makeEntity("b", "k3", "a", "b"));

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyDescSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k1")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k1")));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyAscSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k1")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k1")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k3")));
		}
	}

	/**
	 * Entityの昇順・降順ソートのテスト.
	 * @author vvakame
	 */
	@Test
	public void testEntitySort_string() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(makeEntity("a", "k3", "a", "2"));
		entities.add(makeEntity("a", "k8", "a", "c"));
		entities.add(makeEntity("a", "k4", "a", "3"));
		entities.add(makeEntity("a", "k6", "a", "b"));
		entities.add(makeEntity("b", "k7", "a", "b"));
		entities.add(makeEntity("a", "k1", "a", null));
		entities.add(makeEntity("a", "k2", "a", "1"));
		entities.add(makeEntity("a", "k5", "a", "a"));
		entities.add(makeEntity("a", "k9", "a", "d"));

		// stable sort...

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyDescSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k9")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k8")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k6")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k7")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k5")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k4")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k1")));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyAscSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k1")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k4")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k5")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k6")));
			assertThat(entities.get(i++).getKey(), is(makeKey("b", "k7")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k8")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k9")));
		}
	}

	/**
	 * Entityの昇順・降順ソートのテスト.
	 * @author vvakame
	 */
	@Test
	public void testEntitySort_any_types() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(makeEntity("a", "k1", "a", null));
		entities.add(makeEntity("a", "k2", "a", false));
		entities.add(makeEntity("a", "k3", "a", true));
		entities.add(makeEntity("a", "k4", "a", (byte) 1));
		entities.add(makeEntity("a", "k5", "a", (short) 2));
		entities.add(makeEntity("a", "k6", "a", 3));
		List<Object> list = new ArrayList<Object>();
		list.add(100);
		list.add(4);
		entities.add(makeEntity("a", "k7", "a", list));
		entities.add(makeEntity("a", "k8", "a", (long) 5));
		entities.add(makeEntity("a", "k9", "a", 6.6f));
		entities.add(makeEntity("a", "kA", "a", 7.7));
		entities.add(makeEntity("a", "kB", "a", "str"));
		entities.add(makeEntity("a", "kC", "a", KeyUtil.createKey("z", "a")));
		entities.add(makeEntity("a", "kD", "a", KeyUtil.createKey("z", "b")));

		// stable sort...

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyDescSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kD")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kC")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kB")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kA")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k9")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k8")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k7")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k6")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k5")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k4")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k1")));
		}

		BareSortUtil.sort(entities, new Sorter[] {
			new PropertyAscSorter("a")
		});
		{
			int i = 0;
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k1")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k2")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k3")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k4")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k5")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k6")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k7")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k8")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "k9")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kA")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kB")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kC")));
			assertThat(entities.get(i++).getKey(), is(makeKey("a", "kD")));
		}
	}

	Key makeKey(String kind, String name) {
		return KeyUtil.createKey(kind, name);
	}

	Key makeKey(String kind, long id) {
		return KeyUtil.createKey(kind, id);
	}

	Entity makeEntity(String kind, String keyName) {
		Entity entity = new Entity(kind, keyName);
		return entity;
	}

	Entity makeEntity(String kind, long keyId) {
		Entity entity = new Entity(kind);
		entity.setKey(KeyUtil.createKey(kind, keyId));
		return entity;
	}

	Entity makeEntity(String kind, String keyName, String propertyName, Object value) {
		Key key = KeyUtil.createKey(kind, keyName);
		Entity entity = new Entity(key);
		entity.setProperty(propertyName, value);
		return entity;
	}
}
