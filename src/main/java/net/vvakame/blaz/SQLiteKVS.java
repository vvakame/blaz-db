package net.vvakame.blaz;

import net.vvakame.blaz.sqlite.KvsOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteKVS implements IKeyValueStore {

	SQLiteDatabase mDb;

	static final String KEYS = "KYES";

	static final String VALS = "VALS";


	public SQLiteKVS(Context context, String dbname, int version) {
		KvsOpenHelper helper = new KvsOpenHelper(context, dbname, version);
		mDb = helper.getWritableDatabase();
	}

	@Override
	public Entity get(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Key key, Entity entity) {
		mDb.insert(KEYS, null, conv(key));

		ContentValues val = new ContentValues();
		mDb.insert(VALS, null, val);
	}

	@Override
	public <T>ModelQuery<T> query(ModelMeta m) {
		// TODO Auto-generated method stub
		return null;
	}

	ContentValues conv(Key key) {
		if (key == null) {
			return null;
		}
		ContentValues values = new ContentValues();
		if (key.getKind() != null) {
			throw new IllegalArgumentException("key's kind is required.");
		}
		values.put("KIND", key.getKind());
		values.put("PAREND_KEY", KeyUtil.keyToString(key.getParent()));
		values.put("KEY", KeyUtil.keyToString(key));
		return values;
	}
}
