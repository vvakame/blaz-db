package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.util.KeyUtil;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static net.vvakame.blaz.sqlite.KvsOpenHelper.*;

class KeysDao {

	private KeysDao() {
	}

	public static Key cursorToKeyAsSingle(Cursor c) {
		if (!c.moveToFirst()) {
			return null;
		}
		Key newKey = new Key();
		newKey.setKind(c.getString(c.getColumnIndex(COL_KIND)));
		newKey.setId(c.getLong(c.getColumnIndex(COL_ID)));
		newKey.setName(c.getString(c.getColumnIndex(COL_NAME)));

		c.close();

		return newKey;
	}

	public static List<Key> cursorToKeys(Cursor c) {
		List<Key> keyList = new ArrayList<Key>();
		if (!c.moveToFirst()) {
			return keyList;
		}

		final int idx = c.getColumnIndex(COL_KEY_STRING);

		do {
			String keyStr = c.getString(idx);
			keyList.add(KeyUtil.stringToKey(keyStr));
		} while (c.moveToNext());

		c.close();

		return keyList;
	}

	public static void insert(SQLiteDatabase db, Key key) {
		ContentValues values = convKeyToValues(key);
		db.insert(TABLE_KEYS, null, values);
	}

	public static void delete(SQLiteDatabase db, Key key) {
		db.delete(TABLE_KEYS, COL_KEY_STRING + " = ?", new String[] {
			KeyUtil.keyToString(key)
		});
	}

	public static Cursor query(SQLiteDatabase db, Key... keys) {
		StringBuilder builder = new StringBuilder();
		List<String> args = new ArrayList<String>();
		builder.append(COL_KEY_STRING).append(" IN (");
		for (int i = 0; i < keys.length; i++) {
			builder.append("?");
			if (i != keys.length - 1) {
				builder.append(",");
			}
			args.add(KeyUtil.keyToString(keys[i]));
		}
		builder.append(")");

		Cursor c =
				db.query(TABLE_KEYS, null, builder.toString(), args.toArray(new String[] {}), null,
						null, null);

		return c;
	}

	public static boolean isExists(SQLiteDatabase db, Key key) {
		Cursor c =
				db.rawQuery("SELECT count(*) as cnt FROM " + TABLE_KEYS + " WHERE "
						+ COL_KEY_STRING + " = ?", new String[] {
					KeyUtil.keyToString(key)
				});
		c.moveToFirst();
		return c.getInt(0) != 0;
	}

	static ContentValues convKeyToValues(Key key) {
		ContentValues values = new ContentValues();

		if (key.getName() == null) {
			values.put(COL_ID, key.getId());
		} else {
			values.put(COL_NAME, key.getName());
		}
		values.put(COL_KIND, key.getKind());
		values.put(COL_KEY_STRING, KeyUtil.keyToString(key));

		return values;
	}
}
