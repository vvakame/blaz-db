package net.vvakame.blaz;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.common.KeyFilter;
import net.vvakame.blaz.common.KindFilter;
import net.vvakame.blaz.common.PropertyFilter;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

/**
 * {@link IKeyValueStore} のテスト用クラス.<br>
 * 各 {@link IKeyValueStore} 実装は本クラスを継承し、 {@link #before()} のみ実装すること.
 * @author vvakame
 */
public abstract class RawDatastoreTestBase {

	protected IKeyValueStore kvs;

	protected boolean supportTransaction = true;


	/**
	 * {@link IKeyValueStore#put(Entity)} に対して対応しているはずの全ての型を突っ込む.
	 * @author vvakame
	 */
	@Test
	public void put_get_single() {
		Key key = KeyUtil.createKey("hoge", "piyo");

		{
			Entity entity = new Entity();
			entity.setKey(key);
			entity.setProperty("null", null);
			entity.setProperty("String", "str");
			entity.setProperty("Byte", (byte) 1);
			entity.setProperty("Short", (short) 2);
			entity.setProperty("Integer", 3);
			entity.setProperty("Long", 4L);
			entity.setProperty("BooleanT", true);
			entity.setProperty("BooleanF", false);
			entity.setProperty("Float", 1.125f);
			entity.setProperty("Double", 2.5);
			entity.setProperty("Key", KeyUtil.createKey("hoge", "puyo"));
			entity.setProperty("byte[]", new byte[] {
				1,
				2,
				3
			});
			entity.setProperty("ListEmpty", new ArrayList<Object>());
			List<Object> list = new ArrayList<Object>();
			list.add(null);
			list.add("str");
			list.add((byte) 1);
			list.add((short) 2);
			list.add(3);
			list.add(4L);
			list.add(true);
			list.add(false);
			list.add(1.125f);
			list.add(2.25);
			list.add(KeyUtil.createKey("hoge", "payo"));
			list.add(new byte[] {
				1,
				2,
				3
			});
			entity.setProperty("List", list);
			kvs.put(entity);
		}
		{
			Entity entity = kvs.get(key);
			assertThat(entity.getProperties().size(), is(14));
			assertThat(entity.getProperty("null"), nullValue());
			assertThat(entity.getProperty("String"), is((Object) "str"));
			assertThat(entity.getProperty("Byte"), is((Object) 1L));
			assertThat(entity.getProperty("Short"), is((Object) 2L));
			assertThat(entity.getProperty("Integer"), is((Object) 3L));
			assertThat(entity.getProperty("Long"), is((Object) 4L));
			assertThat(entity.getProperty("BooleanT"), is((Object) true));
			assertThat(entity.getProperty("BooleanF"), is((Object) false));
			assertThat(entity.getProperty("Float"), is((Object) 1.125));
			assertThat(entity.getProperty("Double"), is((Object) 2.5));
			assertThat(entity.getProperty("Key"), is((Object) KeyUtil.createKey("hoge", "puyo")));
			assertThat(entity.getProperty("byte[]"), is((Object) new byte[] {
				1,
				2,
				3
			}));
			List<Object> list;
			list = entity.getProperty("ListEmpty");
			assertThat(list.size(), is(0));
			list = entity.getProperty("List");
			assertThat(list.size(), is(12));
			assertThat(list.get(0), nullValue());
			assertThat(list.get(1), is((Object) "str"));
			assertThat(list.get(2), is((Object) 1L));
			assertThat(list.get(3), is((Object) 2L));
			assertThat(list.get(4), is((Object) 3L));
			assertThat(list.get(5), is((Object) 4L));
			assertThat(list.get(6), is((Object) true));
			assertThat(list.get(7), is((Object) false));
			assertThat(list.get(8), is((Object) 1.125));
			assertThat(list.get(9), is((Object) 2.25));
			assertThat(list.get(10), is((Object) KeyUtil.createKey("hoge", "payo")));
			assertThat(list.get(11), is((Object) new byte[] {
				1,
				2,
				3
			}));
		}
	}

	/**
	 * {@link IKeyValueStore#put(Entity)} に対して対応しているはずの全ての型を突っ込む.
	 * @author vvakame
	 */
	@Test
	public void put_get_empty() {
		Key key = KeyUtil.createKey("hoge", "piyo");

		{
			Entity entity = new Entity();
			entity.setKey(key);
			kvs.put(entity);
		}
		{
			Entity entity = kvs.get(key);
			assertThat(entity.getKey(), is(key));
			assertThat(entity.getProperties().size(), is(0));
		}
	}

	/**
	 * {@link IKeyValueStore#put(Entity)} と {@link IKeyValueStore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void put_get_pickup_1() {
		Key key1;
		{
			Entity entity = new Entity();
			key1 = KeyUtil.createKey("hoge", "piyo");
			entity.setKey(key1);
			entity.setProperty("key1", "value1");
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			kvs.put(entity);
		}
		Key key2;
		{
			Entity entity = new Entity();
			key2 = KeyUtil.createKey("hoge", "puyo");
			entity.setKey(key2);
			entity.setProperty("key1", "value1");
			kvs.put(entity);
		}

		Entity entity;

		entity = kvs.get(key1);
		assertThat(entity.getProperties().size(), is(4));

		entity = kvs.get(key2);
		assertThat(entity.getProperties().size(), is(1));
	}

	/**
	 * {@link IKeyValueStore#put(Entity)} の上書きの動作確認
	 * @author vvakame
	 */
	@Test
	public void put_update() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		{
			Entity entity = new Entity();
			entity.setKey(key);
			entity.setProperty("key1", "value1");
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			kvs.put(entity);
		}
		{
			Entity entity = kvs.get(key);
			assertThat(entity.getProperties().size(), is(4));
		}
		{
			Entity entity = new Entity();
			entity.setKey(key);
			entity.setProperty("keyA", "valueA");
			kvs.put(entity);
		}
		{
			Entity entity = kvs.get(key);
			assertThat(entity.getProperties().size(), is(1));
		}
	}

	/**
	 * {@link IKeyValueStore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test(expected = EntityNotFoundException.class)
	public void get_not_exists() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		kvs.get(key);
	}

	/**
	 * {@link IKeyValueStore#delete(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void getOrNull() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		try {
			kvs.get(key);
			fail("ENFE expected!");
		} catch (EntityNotFoundException e) {
		}

		kvs.getOrNull(key);
	}

	/**
	 * {@link IKeyValueStore#put(Entity)} と {@link IKeyValueStore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test(expected = EntityNotFoundException.class)
	public void delete() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		{
			Entity entity = new Entity();
			entity.setKey(key);
			entity.setProperty("key1", "value1");
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			kvs.put(entity);
		}
		{
			kvs.delete(key);
		}
		{
			kvs.get(key);
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_KIND_EQ_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo3"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new KindFilter("hoge"));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_Key_EQ_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyFilter(FilterOption.EQ, key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(key));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyFilter(FilterOption.EQ, key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(key));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_Key_GT_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		key = KeyUtil.createKey("hoge", "1");
		list = kvs.find(new KeyFilter(FilterOption.GT, key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(KeyUtil.createKey("hoge", "2")));

		key = KeyUtil.createKey("hoge", 1);
		list = kvs.find(new KeyFilter(FilterOption.GT, key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(KeyUtil.createKey("hoge", 2)));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_Key_GT_EQ_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "3"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 3));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		Key key1;
		Key key2;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyFilter(FilterOption.GT_EQ, key));

		assertThat(list.size(), is(2));
		key1 = KeyUtil.createKey("hoge", "2");
		key2 = KeyUtil.createKey("hoge", "3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyFilter(FilterOption.GT_EQ, key));

		assertThat(list.size(), is(2));
		key1 = KeyUtil.createKey("hoge", 2);
		key2 = KeyUtil.createKey("hoge", 3);
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_Key_LT_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", 1));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyFilter(FilterOption.LT, key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(KeyUtil.createKey("hoge", "1")));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyFilter(FilterOption.LT, key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(KeyUtil.createKey("hoge", 1)));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_Key_LT_EQ_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "3"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", 3));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		Key key1;
		Key key2;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyFilter(FilterOption.LT_EQ, key));

		assertThat(list.size(), is(2));
		key1 = KeyUtil.createKey("hoge", "1");
		key2 = KeyUtil.createKey("hoge", "2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyFilter(FilterOption.LT_EQ, key));

		assertThat(list.size(), is(2));
		key1 = KeyUtil.createKey("hoge", 1);
		key2 = KeyUtil.createKey("hoge", 2);
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_string_PROPERTY_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.EQ, "value2"));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_string_PROPERTY_EQ_multi_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("name1", "value1");
			entity.setProperty("name2", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("name1", "value2");
			entity.setProperty("name2", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("name1", "value2");
			entity.setProperty("name2", "value1");
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("name1", FilterOption.EQ, "value2"),
						new PropertyFilter("name2", FilterOption.EQ, "value1"));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_boolean_PROPERTY_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", true);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", true);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", false);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.EQ, true));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_boolean_PROPERTY_EQ_multi_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("name1", true);
			entity.setProperty("name2", true);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("name1", true);
			entity.setProperty("name2", false);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("name1", false);
			entity.setProperty("name2", false);
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("name1", FilterOption.EQ, true), new PropertyFilter(
						"name2", FilterOption.EQ, false));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_integer_PROPERTY_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.EQ, 1));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_integer_PROPERTY_EQ_multi_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("name1", 1);
			entity.setProperty("name2", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("name1", 1);
			entity.setProperty("name2", 3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("name1", 2);
			entity.setProperty("name2", 3);
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("name1", FilterOption.EQ, 1), new PropertyFilter(
						"name2", FilterOption.EQ, 3));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_real_PROPERTY_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.EQ, 1.3));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_real_PROPERTY_EQ_multi_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("name1", 1.1);
			entity.setProperty("name2", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("name1", 1.1);
			entity.setProperty("name2", 1.3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("name1", 1.2);
			entity.setProperty("name2", 1.3);
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("name1", FilterOption.EQ, 1.1), new PropertyFilter(
						"name2", FilterOption.EQ, 1.3));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_key_PROPERTY_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", KeyUtil.createKey("a", "A"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", KeyUtil.createKey("a", "B"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", KeyUtil.createKey("a", "B"));
			kvs.put(entity);
		}
		Key key = KeyUtil.createKey("a", "B");
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.EQ, key));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_key_PROPERTY_EQ_multi_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key1", KeyUtil.createKey("a", "A"));
			entity.setProperty("key2", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key1", KeyUtil.createKey("a", "B"));
			entity.setProperty("key2", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key1", KeyUtil.createKey("a", "B"));
			entity.setProperty("key2", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		// same cond
		List<Entity> list =
				kvs.find(new PropertyFilter("key1", FilterOption.EQ, KeyUtil.createKey("a", "B")),
						new PropertyFilter("key2", FilterOption.EQ, KeyUtil.createKey("a", 2)));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_string_PROPERTY_GT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.GT, "value2"));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_integer_PROPERTY_GT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.GT, 2));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_real_PROPERTY_GT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.GT, 1.2));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_key_PROPERTY_GT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("key", FilterOption.GT, KeyUtil.createKey("a", 2)));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_string_PROPERTY_GT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.GT_EQ, "value2"));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_integer_PROPERTY_GT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.GT_EQ, 2));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_real_PROPERTY_GT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.GT_EQ, 1.2));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_key_PROPERTY_GT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("key", FilterOption.GT_EQ, KeyUtil.createKey("a", 2)));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo2");
		Key key2 = KeyUtil.createKey("hoge", "piyo3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_string_PROPERTY_LT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.LT, "value2"));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_integer_PROPERTY_LT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.LT, 2));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_real_PROPERTY_LT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.LT, 1.2));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_key_PROPERTY_LT_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("key", FilterOption.LT, KeyUtil.createKey("a", 2)));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_string_PROPERTY_LT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.LT_EQ, "value2"));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_integer_PROPERTY_LT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.LT_EQ, 2));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_real_PROPERTY_LT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyFilter("key", FilterOption.LT_EQ, 1.2));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_key_PROPERTY_LT_EQ_single_filter() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyFilter("key", FilterOption.LT_EQ, KeyUtil.createKey("a", 2)));
		assertThat(list.size(), is(2));
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_all() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo1"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo2"));
			kvs.put(entity);
		}

		List<Entity> list = kvs.find();
		assertThat(list.size(), is(5));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_complex_query() {
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("real", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo2"));
			entity.setProperty("real", 1.2);
			entity.setProperty("int", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo3"));
			entity.setProperty("real", 1.3);
			entity.setProperty("int", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo1"));
			entity.setProperty("real", 1.1);
			entity.setProperty("int", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo2"));
			entity.setProperty("real", 1.2);
			entity.setProperty("int", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo3"));
			entity.setProperty("real", 1.3);
			entity.setProperty("int", 3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("fuga", "piyo4"));
			entity.setProperty("real", 1.2);
			entity.setProperty("int", 4);
			kvs.put(entity);
		}

		List<Entity> list =
				kvs.find(new KindFilter("fuga"), new PropertyFilter("real", FilterOption.EQ, 1.2),
						new PropertyFilter("int", FilterOption.EQ, 2));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("fuga", "piyo2");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * {@link IKeyValueStore#beginTransaction()} のテスト.
	 * @author vvakame
	 */
	@Test
	public void transation_success() {
		if (!supportTransaction) {
			return;
		}

		Transaction tx = kvs.beginTransaction();
		try {
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("value", 111);
			kvs.put(entity);

			tx.commit();

		} finally {
			if (tx.isActive()) {
				tx.rollback();
				fail("commit is not successfull.");
			}
		}

		kvs.get(KeyUtil.createKey("hoge", "piyo1"));
	}

	/**
	 * {@link IKeyValueStore#beginTransaction()} のテスト.
	 * @author vvakame
	 */
	@Test(expected = EntityNotFoundException.class)
	public void transation_rollback() {
		if (!supportTransaction) {
			return;
		}

		Transaction tx = kvs.beginTransaction();
		try {
			Entity entity = new Entity();
			entity.setKey(KeyUtil.createKey("hoge", "piyo1"));
			entity.setProperty("value", 111);
			kvs.put(entity);

			// tx.commit();

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			} else {
				fail("committed.");
			}
		}

		// rollbacked. raise ENFE.
		kvs.get(KeyUtil.createKey("hoge", "piyo1"));
	}

	/**
	 * kvsを呼出し可能なようにセットアップすること.
	 * @author vvakame
	 */
	public abstract void before();
}
