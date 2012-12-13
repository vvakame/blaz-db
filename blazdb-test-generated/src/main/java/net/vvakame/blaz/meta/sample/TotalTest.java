package net.vvakame.blaz.meta.sample;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.sqlite.SQLiteKVS;

import org.junit.Before;

/**
 * {@link Datastore} の全体的なテスト.
 * @author vvakame
 */
public class TotalTest {

	@SuppressWarnings("unused")
	private static final RootDataMeta META = RootDataMeta.get();

	@SuppressWarnings("unused")
	private static final ExtendedDataMeta EXT_META = ExtendedDataMeta.get();

	BareDatastore kvs;


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
