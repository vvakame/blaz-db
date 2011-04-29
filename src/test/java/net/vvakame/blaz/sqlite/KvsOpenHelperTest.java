package net.vvakame.blaz.sqlite;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * テスト
 * @author vvakame
 */
@RunWith(RobolectricTestRunner.class)
public class KvsOpenHelperTest {

	/**
	 * 開始
	 * @author vvakame
	 */
	@Test
	public void run() {
		ShadowApplication application = Robolectric.getShadowApplication();
		KvsOpenHelper helper = new KvsOpenHelper(application.getApplicationContext(), "test.db", 1);
		SQLiteDatabase database = helper.getWritableDatabase();
		assertThat(database, notNullValue());

		database.beginTransaction();
		database.execSQL("INSERT INTO KEYS (KEY) VALUES (1)");

		Cursor c = database.query("KEYS", null, null, null, null, null, null);
		assertThat(c.getCount(), is(1));
	}
}
