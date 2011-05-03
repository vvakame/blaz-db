package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.FilterOption;
import net.vvakame.blaz.IKeyValueStore;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLiteによるKVSの実装
 * @author vvakame
 */
public class SQLiteKVS implements IKeyValueStore {

	Context mContext;

	SQLiteDatabase mDb = null;


	/**
	 * the constructor.
	 * @param context
	 * @category constructor
	 */
	public SQLiteKVS(Context context) {
		mContext = context.getApplicationContext();
		mDb = new KvsOpenHelper(mContext, "blaz.db", 1).getWritableDatabase();
	}

	@Override
	public void put(Entity entity) {
		{
			ContentValues values = convKeyToValues(entity.getKey());
			mDb.insert("KEYS", null, values);
		}
		List<ContentValues> list = convPropertiesToValues(entity.getKey(), entity.getProperties());
		for (ContentValues values : list) {
			mDb.insert("VALUES", null, values);
		}
	}

	@Override
	public Entity get(Key key) {
		Cursor c;
		c = mDb.query("KEYS", null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		if (!c.moveToFirst()) {
			// FIXME EntityNotFoundException作る
			throw new IllegalArgumentException();
		}
		Key newKey = new Key();
		newKey.setId(c.getLong(c.getColumnIndex("ID")));
		newKey.setName(c.getString(c.getColumnIndex("NAME")));

		c = mDb.query("VALUES", null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		Entity entity = new Entity();
		entity.setKey(newKey);
		int nameIdx = c.getColumnIndex("NAME");
		int typeIdx = c.getColumnIndex("TYPE");
		int valStrIdx = c.getColumnIndex("VAL_STR");
		int valIntIdx = c.getColumnIndex("VAL_INT");
		int valRealIdx = c.getColumnIndex("VAL_REAL");
		int valBlobIdx = c.getColumnIndex("VAL_BLOB");
		if (!c.moveToFirst()) {
			return entity;
		}
		do {
			String name = c.getString(nameIdx);
			String type = c.getString(typeIdx);
			Object value;
			if ("String".equals(type)) {
				value = c.getString(valStrIdx);
			} else {
				throw new UnsupportedOperationException();
			}
			entity.setProperty(name, value);
		} while (c.moveToNext());

		return entity;
	}

	static List<Entity> find(FilterOption options) {
		return null;
	}

	static ContentValues convKeyToValues(Key key) {
		ContentValues values = new ContentValues();

		if (key.getName() == null) {
			values.put("ID", key.getId());
		} else {
			values.put("NAME", key.getName());
		}
		values.put("KIND", key.getKind());
		values.put("KEY_STR", KeyUtil.keyToString(key));
		values.put("PARENT_KEY", KeyUtil.keyToString(key.getParent()));

		return values;
	}

	static List<ContentValues> convPropertiesToValues(Key key, Map<String, Object> properties) {
		// TODO ValuesがListの場合
		// FIXME 今はStringがValueの場合のみ考える
		List<ContentValues> values = new ArrayList<ContentValues>(properties.keySet().size());
		for (String keyStr : properties.keySet()) {
			Object obj = properties.get(keyStr);
			if (obj instanceof String) {
				ContentValues value = new ContentValues();
				value.put("KEY_STR", KeyUtil.keyToString(key));
				value.put("KIND", key.getKind());
				value.put("NAME", keyStr);
				value.put("TYPE", "String");
				// TODO 文字コードを指定したほうがいい
				value.put("VAL_STR", (String) obj);
				values.add(value);
			} else {
				throw new UnsupportedOperationException();
			}
		}

		return values;
	}
}
