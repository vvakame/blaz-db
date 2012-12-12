package net.vvakame.blaz.sqlite;

/**
 * 動作確認
 * @author vvakame
 */
class Constants {

	private Constants() {
	}


	static final String TABLE_KEYS = "KEY_TABLE";

	static final String TABLE_VALUES = "VALUE_TABLE";

	static final String COL_ID = "ID";

	static final String COL_NAME = "NAME";

	static final String COL_KEY_STRING = "KEY_STR";

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
}
