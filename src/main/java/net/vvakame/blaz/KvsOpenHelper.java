package net.vvakame.blaz;

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
		db.execSQL("CREATE TABLE TEST (KEY INTEGER PRIMARY KEY AUTOINCREMENT, VAL TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
