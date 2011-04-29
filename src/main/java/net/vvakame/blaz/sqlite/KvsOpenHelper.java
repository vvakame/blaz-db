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
		// TODO KEYにUniq制約が必要
		db.execSQL("CREATE TABLE KEYS (ID INTEGER, NAME TEXT, KIND TEXT,KEY_STR TEXT, PARENT_KEY TEXT)");
		db.execSQL("CREATE TABLE VALUES (KEY TEXT, KIND TEXT, TYPE TEXT, VALUE BLOB)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
