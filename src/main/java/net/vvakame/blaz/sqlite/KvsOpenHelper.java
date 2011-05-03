package net.vvakame.blaz.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 動作確認
 * @author vvakame
 */
public class KvsOpenHelper extends SQLiteOpenHelper {

	/**
	 * the constructor.
	 * @param c
	 * @param dbname
	 * @param version
	 * @category constructor
	 */
	public KvsOpenHelper(Context c, String dbname, int version) {
		super(c, dbname, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// FIXME KEYにUniq制約が必要
		db.execSQL("CREATE TABLE KEYS (ID INTEGER, NAME TEXT, KIND TEXT,KEY_STR TEXT, PARENT_KEY TEXT)");
		db.execSQL("CREATE TABLE VALUES (KEY_STR TEXT, KIND TEXT, NAME TEXT, TYPE TEXT, VAL_STR TEXT, VAL_INT INTEGER, VAL_REAL REAL, VAL_BYTES BLOB)");
		db.execSQL("CREATE INDEX VALUES_KEY_STR ON VALUES(KEY_STR)");
		db.execSQL("CREATE INDEX VALUES_KIND_NAME ON VALUES(KIND, NAME)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
