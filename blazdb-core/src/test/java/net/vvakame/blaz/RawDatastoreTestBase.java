package net.vvakame.blaz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.exception.EntityNotFoundException;
import net.vvakame.blaz.filter.KeyEqFilter;
import net.vvakame.blaz.filter.KeyGtEqFilter;
import net.vvakame.blaz.filter.KeyGtFilter;
import net.vvakame.blaz.filter.KeyInFilter;
import net.vvakame.blaz.filter.KeyLtEqFilter;
import net.vvakame.blaz.filter.KeyLtFilter;
import net.vvakame.blaz.filter.KindEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerGtEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerGtFilter;
import net.vvakame.blaz.filter.PropertyIntegerInFilter;
import net.vvakame.blaz.filter.PropertyIntegerLtEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerLtFilter;
import net.vvakame.blaz.filter.PropertyKeyEqFilter;
import net.vvakame.blaz.filter.PropertyKeyGtEqFilter;
import net.vvakame.blaz.filter.PropertyKeyGtFilter;
import net.vvakame.blaz.filter.PropertyKeyInFilter;
import net.vvakame.blaz.filter.PropertyKeyLtEqFilter;
import net.vvakame.blaz.filter.PropertyKeyLtFilter;
import net.vvakame.blaz.filter.PropertyRealEqFilter;
import net.vvakame.blaz.filter.PropertyRealGtEqFilter;
import net.vvakame.blaz.filter.PropertyRealGtFilter;
import net.vvakame.blaz.filter.PropertyRealInFilter;
import net.vvakame.blaz.filter.PropertyRealLtEqFilter;
import net.vvakame.blaz.filter.PropertyRealLtFilter;
import net.vvakame.blaz.filter.PropertyStringEqFilter;
import net.vvakame.blaz.filter.PropertyStringGtEqFilter;
import net.vvakame.blaz.filter.PropertyStringGtFilter;
import net.vvakame.blaz.filter.PropertyStringInFilter;
import net.vvakame.blaz.filter.PropertyStringLtEqFilter;
import net.vvakame.blaz.filter.PropertyStringLtFilter;
import net.vvakame.blaz.sorter.KeyAscSorter;
import net.vvakame.blaz.sorter.KeyDescSorter;
import net.vvakame.blaz.sorter.PropertyAscSorter;
import net.vvakame.blaz.sorter.PropertyDescSorter;
import net.vvakame.blaz.util.KeyUtil;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

/**
 * {@link BareDatastore} のテスト用クラス.<br>
 * 各 {@link BareDatastore} 実装は本クラスを継承し、 {@link #before()} のみ実装すること.
 * @author vvakame
 */
public abstract class RawDatastoreTestBase {

	protected BareDatastore kvs;

	protected boolean supportTransaction = true;


	/**
	 * {@link BareDatastore#put(Entity)} に対して対応しているはずの全ての型を突っ込む.
	 * @author vvakame
	 */
	@Test
	public void put_get_single() {
		Key key = KeyUtil.createKey("hoge", "piyo");

		{
			Entity entity = new Entity(key);
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
	 * {@link BareDatastore#put(Entity)} に対して対応しているはずの全ての型を突っ込む.
	 * @author vvakame
	 */
	@Test
	public void put_get_empty() {
		Key key = KeyUtil.createKey("hoge", "piyo");

		{
			Entity entity = new Entity(key);
			kvs.put(entity);
		}
		{
			Entity entity = kvs.get(key);
			assertThat(entity.getKey(), is(key));
			assertThat(entity.getProperties().size(), is(0));
		}
	}

	/**
	 * {@link BareDatastore#put(Entity)} に対してnullを突っ込む
	 * @author vvakame
	 */
	@Test(expected = NullPointerException.class)
	public void put_null() {
		kvs.put((Entity) null);
	}

	/**
	 * {@link BareDatastore#put(Entity)} に対してname, idなしを突っ込む
	 * @author vvakame
	 */
	@Test
	public void put_nameLess() {
		List<Entity> entities = new ArrayList<Entity>();
		{
			Entity entity = new Entity("hoge");
			entity.setProperty("value", 1);
			kvs.put(entity);
			entities.add(entity);
		}
		{
			Entity entity = new Entity("hoge");
			entity.setProperty("value", 2);
			kvs.put(entity);
			entities.add(entity);
		}
		{
			Entity entity = new Entity("hoge");
			entity.setProperty("value", 3);
			kvs.put(entity);
			entities.add(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 100));
			kvs.put(entity);
			entities.add(entity);
		}
		{
			Entity entity = new Entity("hoge");
			kvs.put(entity);
			entities.add(entity);
		}

		List<Entity> resultEntities = kvs.find();
		assertThat(resultEntities.size(), is(5));

		root: for (Entity entity : resultEntities) {
			for (Entity base : entities) {
				if (entity.getKey().equals(base.getKey())) {
					continue root;
				}
			}
			fail("key is not matched");
		}
	}

	/**
	 * {@link BareDatastore#put(Entity)} と {@link BareDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void put_get_pickup_1() {
		Key key1;
		{
			key1 = KeyUtil.createKey("hoge", "piyo");
			Entity entity = new Entity(key1);
			entity.setProperty("key1", "value1");
			entity.setProperty("key2", "value2");
			entity.setProperty("key3", "value3");
			entity.setProperty("key4", "value4");
			kvs.put(entity);
		}
		Key key2;
		{
			key2 = KeyUtil.createKey("hoge", "puyo");
			Entity entity = new Entity(key2);
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
	 * {@link BareDatastore#put(Entity)} の上書きの動作確認
	 * @author vvakame
	 */
	@Test
	public void put_update() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		{
			Entity entity = new Entity(key);
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
			Entity entity = new Entity(key);
			entity.setProperty("keyA", "valueA");
			kvs.put(entity);
		}
		{
			Entity entity = kvs.get(key);
			assertThat(entity.getProperties().size(), is(1));
		}
	}

	/**
	 * {@link BareDatastore#put(Entity)} の上書きの動作確認
	 * @author vvakame
	 */
	@Test
	public void put_multi() {
		Entity entity1;
		{
			entity1 = new Entity("hoge", "piyo1");
			entity1.setProperty("key1", "value1");
			entity1.setProperty("key2", "value2");
			entity1.setProperty("key3", "value3");
			entity1.setProperty("key4", "value4");
			kvs.put(entity1);
		}
		Entity entity2;
		{
			entity2 = new Entity("hoge", "piyo2");
			entity2.setProperty("keyA", "valueA");
		}
		kvs.put(entity1, entity2);
		List<Entity> entities = kvs.find();
		assertThat(entities.size(), is(2));
	}

	/**
	 * {@link BareDatastore#get(Key)} の動作確認
	 * @author vvakame
	 */
	@Test(expected = EntityNotFoundException.class)
	public void get_not_exists() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		kvs.get(key);
	}

	/**
	 * {@link BareDatastore#get(Key...)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void get_multi() {
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		{
			Entity entity = new Entity(key1);
			kvs.put(entity);
		}
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		{
			Entity entity = new Entity(key2);
			kvs.put(entity);
		}
		List<Entity> entities = kvs.get(key1, key2);
		assertThat(entities.size(), is(2));
	}

	/**
	 * {@link BareDatastore#get(Key...)} の動作確認
	 * @author vvakame
	 */
	@Test(expected = EntityNotFoundException.class)
	public void get_multi_not_exists() {
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		{
			Entity entity = new Entity(key1);
			kvs.put(entity);
		}
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		kvs.get(key1, key2);
	}

	/**
	 * {@link BareDatastore#delete(Key)} の動作確認
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
	 * {@link BareDatastore#get(Key...)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void getAsMap() {
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		{
			Entity entity = new Entity(key1);
			kvs.put(entity);
		}
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		{
			Entity entity = new Entity(key2);
			entity.setProperty("value", 1);
			kvs.put(entity);
		}
		Key key3 = KeyUtil.createKey("hoge", "piyo3");

		List<Key> keyList = new ArrayList<Key>();
		keyList.add(key1);
		keyList.add(key2);
		keyList.add(key3);
		Map<Key, Entity> entities = kvs.getAsMap(keyList);
		assertThat(entities.size(), is(2));
	}

	/**
	 * {@link BareDatastore#delete(Key)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void delete_single() {
		Key key = KeyUtil.createKey("hoge", "piyo");
		{
			Entity entity = new Entity(key);
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
			assertThat(kvs.find().size(), is(0));
		}
	}

	/**
	 * {@link BareDatastore#delete(Key...)} の動作確認
	 * @author vvakame
	 */
	@Test
	public void delete_multi() {
		Key key1 = KeyUtil.createKey("hoge", "piyo1");
		{
			Entity entity = new Entity(key1);
			entity.setProperty("key1", "value1");
			kvs.put(entity);
		}
		Key key2 = KeyUtil.createKey("hoge", "piyo2");
		{
			Entity entity = new Entity(key2);
			kvs.put(entity);
		}
		Key key3 = KeyUtil.createKey("hoge", "piyo3");
		{
			Entity entity = new Entity(key3);
			entity.setProperty("key1", "value1");
			kvs.put(entity);
		}
		{
			kvs.delete(key1, key2);
		}
		{
			assertThat(kvs.find().size(), is(1));
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_KIND_EQ_filter() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo3");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new KindEqFilter("hoge"));
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
			Entity entity = new Entity("hoge", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyEqFilter(key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(key));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyEqFilter(key));
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
			Entity entity = new Entity("hoge", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		key = KeyUtil.createKey("hoge", "1");
		list = kvs.find(new KeyGtFilter(key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(KeyUtil.createKey("hoge", "2")));

		key = KeyUtil.createKey("hoge", 1);
		list = kvs.find(new KeyGtFilter(key));
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
			Entity entity = new Entity("hoge", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "3");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 3));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		Key key1;
		Key key2;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyGtEqFilter(key));

		assertThat(list.size(), is(2));
		key1 = KeyUtil.createKey("hoge", "2");
		key2 = KeyUtil.createKey("hoge", "3");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyGtEqFilter(key));

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
			Entity entity = new Entity("hoge", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("fuga", 1));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyLtFilter(key));
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(KeyUtil.createKey("hoge", "1")));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyLtFilter(key));
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
			Entity entity = new Entity("hoge", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "3");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("hoge", 3));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity(KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		Key key;
		List<Entity> list;

		Key key1;
		Key key2;

		key = KeyUtil.createKey("hoge", "2");
		list = kvs.find(new KeyLtEqFilter(key));

		assertThat(list.size(), is(2));
		key1 = KeyUtil.createKey("hoge", "1");
		key2 = KeyUtil.createKey("hoge", "2");
		assertThat(list.get(0).getKey(), isOneOf(key1, key2));
		assertThat(list.get(1).getKey(), isOneOf(key1, key2));

		key = KeyUtil.createKey("hoge", 2);
		list = kvs.find(new KeyLtEqFilter(key));

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
	public void find_Key_IN_filter() {
		{
			Entity entity = new Entity("hoge", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "3");
			kvs.put(entity);
		}

		List<Entity> list;

		Key key1;
		Key key2;

		key1 = KeyUtil.createKey("hoge", "1");
		key2 = KeyUtil.createKey("hoge", "2");

		list = kvs.find(new KeyInFilter(key1));

		assertThat(list.size(), is(1));
		assertThat(list.get(0).getKey(), is(key1));

		list = kvs.find(new KeyInFilter(key1, key2));
		assertThat(list.size(), is(2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringEqFilter("key", "value2"));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("name1", "value1");
			entity.setProperty("name2", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("name1", "value2");
			entity.setProperty("name2", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("name1", "value2");
			entity.setProperty("name2", "value1");
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyStringEqFilter("name1", "value2"), new PropertyStringEqFilter(
						"name2", "value1"));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", true);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", true);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", false);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyBooleanEqFilter("key", true));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("name1", true);
			entity.setProperty("name2", true);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("name1", true);
			entity.setProperty("name2", false);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("name1", false);
			entity.setProperty("name2", false);
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyBooleanEqFilter("name1", true), new PropertyBooleanEqFilter(
						"name2", false));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyIntegerEqFilter("key", 1));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("name1", 1);
			entity.setProperty("name2", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("name1", 1);
			entity.setProperty("name2", 3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("name1", 2);
			entity.setProperty("name2", 3);
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyIntegerEqFilter("name1", 1), new PropertyIntegerEqFilter(
						"name2", 3));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyRealEqFilter("key", 1.3));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("name1", 1.1);
			entity.setProperty("name2", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("name1", 1.1);
			entity.setProperty("name2", 1.3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("name1", 1.2);
			entity.setProperty("name2", 1.3);
			kvs.put(entity);
		}
		List<Entity> list =
				kvs.find(new PropertyRealEqFilter("name1", 1.1), new PropertyRealEqFilter("name2",
						1.3));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", KeyUtil.createKey("a", "A"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", KeyUtil.createKey("a", "B"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", KeyUtil.createKey("a", "B"));
			kvs.put(entity);
		}
		Key key = KeyUtil.createKey("a", "B");
		List<Entity> list = kvs.find(new PropertyKeyEqFilter("key", key));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key1", KeyUtil.createKey("a", "A"));
			entity.setProperty("key2", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key1", KeyUtil.createKey("a", "B"));
			entity.setProperty("key2", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key1", KeyUtil.createKey("a", "B"));
			entity.setProperty("key2", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		// same cond
		List<Entity> list =
				kvs.find(new PropertyKeyEqFilter("key1", KeyUtil.createKey("a", "B")),
						new PropertyKeyEqFilter("key2", KeyUtil.createKey("a", 2)));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringGtFilter("key", "value2"));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyIntegerGtFilter("key", 2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyRealGtFilter("key", 1.2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyKeyGtFilter("key", KeyUtil.createKey("a", 2)));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringGtEqFilter("key", "value2"));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyIntegerGtEqFilter("key", 2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyRealGtEqFilter("key", 1.2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyKeyGtEqFilter("key", KeyUtil.createKey("a", 2)));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringLtFilter("key", "value2"));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyIntegerLtFilter("key", 2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyRealLtFilter("key", 1.2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyKeyLtFilter("key", KeyUtil.createKey("a", 2)));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringLtEqFilter("key", "value2"));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyIntegerLtEqFilter("key", 2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1.2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyRealLtEqFilter("key", 1.2));
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", KeyUtil.createKey("a", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", KeyUtil.createKey("a", 2));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", KeyUtil.createKey("a", 3));
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyKeyLtEqFilter("key", KeyUtil.createKey("a", 2)));
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
	public void find_string_PROPERTY_IN_single_filter() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", "value1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", "value2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", "value3");
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringInFilter("key", "value2", "value3"));
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
	public void find_integer_PROPERTY_IN_single_filter() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 3);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyIntegerInFilter("key", 1L, 2L));
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
	public void find_real_PROPERTY_IN_single_filter() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", 1.3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", 1.5);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyRealInFilter("key", 1.3, 1.5));
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
	public void find_key_PROPERTY_IN_single_filter() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("key", KeyUtil.createKey("a", "A"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("key", KeyUtil.createKey("a", "B"));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("key", KeyUtil.createKey("a", "C"));
			kvs.put(entity);
		}
		Key keyA = KeyUtil.createKey("a", "B");
		Key keyB = KeyUtil.createKey("a", "C");
		List<Entity> list = kvs.find(new PropertyKeyInFilter("key", keyA, keyB));
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
	public void find_from_list() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			List<String> list = new ArrayList<String>();
			list.add("abc");
			list.add("cde");
			list.add("efg");
			entity.setProperty("a", list);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			List<String> list = new ArrayList<String>();
			list.add("cde");
			list.add("efg");
			list.add("ghi");
			entity.setProperty("a", list);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			List<String> list = new ArrayList<String>();
			list.add("efg");
			list.add("ghi");
			list.add("ijk");
			entity.setProperty("a", list);
			kvs.put(entity);
		}
		List<Entity> list = kvs.find(new PropertyStringEqFilter("a", "ghi"));
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
	public void find_all() {
		{
			Entity entity = new Entity("hoge", "piyo1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo2");
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
			Entity entity = new Entity("hoge", "piyo1");
			entity.setProperty("real", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo2");
			entity.setProperty("real", 1.2);
			entity.setProperty("int", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "piyo3");
			entity.setProperty("real", 1.3);
			entity.setProperty("int", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo1");
			entity.setProperty("real", 1.1);
			entity.setProperty("int", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo2");
			entity.setProperty("real", 1.2);
			entity.setProperty("int", 2);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo3");
			entity.setProperty("real", 1.3);
			entity.setProperty("int", 3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("fuga", "piyo4");
			entity.setProperty("real", 1.2);
			entity.setProperty("int", 4);
			kvs.put(entity);
		}

		List<Entity> list =
				kvs.find(new KindEqFilter("fuga"), new PropertyRealEqFilter("real", 1.2),
						new PropertyIntegerEqFilter("int", 2));
		assertThat(list.size(), is(1));
		Key key1 = KeyUtil.createKey("fuga", "piyo2");
		assertThat(list.get(0).getKey(), is(key1));
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_sort_by_key() {
		{
			Entity entity = new Entity("hoge", "C");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "A");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "B");
			kvs.put(entity);
		}

		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new KeyAscSorter()
			});
			assertThat(entities.get(0).getKey().getName(), is("A"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("C"));
		}
		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new KeyDescSorter()
			});
			assertThat(entities.get(0).getKey().getName(), is("C"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("A"));
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_sort_by_property_string() {
		{
			Entity entity = new Entity("hoge", "C");
			entity.setProperty("a", "3");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "A");
			entity.setProperty("a", "1");
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "B");
			entity.setProperty("a", "2");
			kvs.put(entity);
		}

		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyAscSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("A"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("C"));
		}
		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyDescSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("C"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("A"));
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_sort_by_property_integer() {
		{
			Entity entity = new Entity("hoge", "C");
			entity.setProperty("a", 3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "A");
			entity.setProperty("a", 1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "B");
			entity.setProperty("a", 2);
			kvs.put(entity);
		}

		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyAscSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("A"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("C"));
		}
		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyDescSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("C"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("A"));
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_sort_by_property_real() {
		{
			Entity entity = new Entity("hoge", "C");
			entity.setProperty("a", 3.3);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "A");
			entity.setProperty("a", 1.1);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "B");
			entity.setProperty("a", 2.2);
			kvs.put(entity);
		}

		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyAscSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("A"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("C"));
		}
		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyDescSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("C"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("A"));
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_sort_by_property_boolean() {
		{
			Entity entity = new Entity("hoge", "A");
			entity.setProperty("a", false);
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "B");
			entity.setProperty("a", true);
			kvs.put(entity);
		}

		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyAscSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("A"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
		}
		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyDescSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("B"));
			assertThat(entities.get(1).getKey().getName(), is("A"));
		}
	}

	/**
	 * 動作確認.
	 * @author vvakame
	 */
	@Test
	public void find_sort_by_property_key() {
		{
			Entity entity = new Entity("hoge", "C");
			entity.setProperty("a", KeyUtil.createKey("fuga", 3));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "A");
			entity.setProperty("a", KeyUtil.createKey("fuga", 1));
			kvs.put(entity);
		}
		{
			Entity entity = new Entity("hoge", "B");
			entity.setProperty("a", KeyUtil.createKey("fuga", 2));
			kvs.put(entity);
		}

		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyAscSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("A"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("C"));
		}
		{
			List<Entity> entities = kvs.find(new Filter[] {}, new Sorter[] {
				new PropertyDescSorter("a")
			});
			assertThat(entities.get(0).getKey().getName(), is("C"));
			assertThat(entities.get(1).getKey().getName(), is("B"));
			assertThat(entities.get(2).getKey().getName(), is("A"));
		}
	}

	/**
	 * {@link BareDatastore#beginTransaction()} のテスト.
	 * @author vvakame
	 */
	@Test
	public void transation_success() {
		if (!supportTransaction) {
			return;
		}

		Transaction tx = kvs.beginTransaction();
		try {
			Entity entity = new Entity("hoge", "piyo1");
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
	 * {@link BareDatastore#beginTransaction()} のテスト.
	 * @author vvakame
	 */
	@Test(expected = EntityNotFoundException.class)
	public void transation_rollback() {
		if (!supportTransaction) {
			return;
		}

		Transaction tx = kvs.beginTransaction();
		try {
			Entity entity = new Entity("hoge", "piyo1");
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
	 * クエリの組み合わせのチェックのテスト.
	 * @author vvakame
	 */
	@Test
	public void checkFilter_ok() {
		kvs.setCheckFilter(true);
		kvs.find(new KindEqFilter("hoge"), new PropertyBooleanEqFilter("fuga", true));
	}

	/**
	 * クエリの組み合わせのチェックのテスト.
	 * @author vvakame
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkFilter_ng() {
		kvs.setCheckFilter(true);
		kvs.find(new KindEqFilter("hoge"), new KindEqFilter("fuga"));
	}

	/**
	 * kvsを呼出し可能なようにセットアップすること.
	 * @author vvakame
	 */
	public abstract void before();
}
