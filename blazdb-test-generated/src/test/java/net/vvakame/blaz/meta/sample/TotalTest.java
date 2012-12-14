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

	BareDatastore kvs;


	/**
	 * Test case.
	 * @author vvakame
	 */
	@Test
	public void put() {
		final PrimitiveTypeDataMeta meta = PrimitiveTypeDataMeta.get();

		Key key = KeyUtil.createKey(meta.getKind(), 1);
		{
			PrimitiveTypeData data = new PrimitiveTypeData();
			data.setKey(key);
			data.setBool(true);
			data.setF(1.125f);

			Datastore.put(data);
			key = data.getKey();
		}
		{
			List<PrimitiveTypeData> list =
					Datastore.query(meta).filter(meta.key.equal(key)).asList();
			assertThat(list.size(), is(1));
		}
	}

	/**
	 * 動作確認
	 * @author vvakame
	 */
	@Before
	public void setUp() {
		kvs = new SQLiteKVS(":memory:");
		Datastore.setupDatastore(kvs);
	}
}
