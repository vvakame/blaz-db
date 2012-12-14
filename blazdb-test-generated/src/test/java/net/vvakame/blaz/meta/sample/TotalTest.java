package net.vvakame.blaz.meta.sample;

import java.util.List;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.sqlite.SQLiteKVS;
import net.vvakame.blaz.util.KeyUtil;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * {@link Datastore} の全体的なテスト.
 * @author vvakame
 */
public class TotalTest {

	private static final RootDataMeta META = RootDataMeta.get();

	BareDatastore kvs;


	@Test
	public void put() {
		Key key = KeyUtil.createKey(META.getKind(), 1);
		{
			RootData data = new RootData();
			data.setKey(key);
			data.setInteger(1);
			data.setStr("2");

			Datastore.put(data);
			key = data.getKey();
		}
		{
			List<RootData> list = Datastore.query(META).filter(META.key.equal(key)).asList();
			assertThat(list.size(), is(1));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Before
	public void setUp() {
		kvs = new SQLiteKVS();
		Datastore.setupDatastore(kvs);
	}
}
