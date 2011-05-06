package net.vvakame.blaz.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 動作確認
 * @author vvakame
 */
public class KvsOpenHelper extends SQLiteOpenHelper {

	static final String TABLE_KEYS = "KEYS";

	static final String TABLE_VALUES = "VALUES";

	static final String COL_ID = "ID";

	static final String COL_NAME = "NAME";

	static final String COL_KEY_STRING = "KEY_STR";

	static final String COL_PARENT_KEY = "PARENT_KEY";

	static final String COL_KIND = "KIND";

	static final String COL_TYPE = "TYPE";

	static final String COL_VALUE_STRING = "VAL_STR";

	static final String COL_VALUE_INTEGER = "VAL_INT";

	static final String COL_VALUE_REAL = "VAL_REAL";

	static final String COL_VALUE_BLOB = "VAL_BYTES";

	static final String COL_SEQ = "SEQ";

	static final String T_NULL = "NULL";

	static final String T_STRING = "STR";

	static final String T_BOOLEAN = "BOOL";

	static final String T_LONG = "LONG";

	static final String T_DOUBLE = "DOUBLE";

	static final String T_KEY = "KEY";

	static final String T_BYTES = "BLOB";

	static final String T_L_PREFIX = "L#";

	static final String T_L_BLANK = T_L_PREFIX + "BLANK";

	static final String T_L_NULL = T_L_PREFIX + T_NULL;

	static final String T_L_STRING = T_L_PREFIX + T_STRING;

	static final String T_L_BOOLEAN = T_L_PREFIX + T_BOOLEAN;

	static final String T_L_LONG = T_L_PREFIX + T_LONG;

	static final String T_L_DOUBLE = T_L_PREFIX + T_DOUBLE;

	static final String T_L_KEY = T_L_PREFIX + T_KEY;

	static final String T_L_BYTES = T_L_PREFIX + T_BYTES;

	static final String T_V_BOOLEAN_TRUE = "T";

	static final String T_V_BOOLEAN_FALSE = "F";


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
		db.execSQL("CREATE TABLE VALUES (KEY_STR TEXT, KIND TEXT, NAME TEXT, TYPE TEXT, SEQ INTEGER, VAL_STR TEXT, VAL_INT INTEGER, VAL_REAL REAL, VAL_BYTES BLOB)");
		db.execSQL("CREATE INDEX VALUES_KEY_STR ON VALUES(KEY_STR)");
		db.execSQL("CREATE INDEX VALUES_KIND_NAME ON VALUES(KIND, NAME)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
