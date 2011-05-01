package net.vvakame.blaz.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Datastore {

	static Context sContext;

	static SQLiteDatabase sDb = null;


	public static void init(Context context) {
		sContext = context.getApplicationContext();
		sDb = new KvsOpenHelper(sContext, "blaz.db", 1).getWritableDatabase();
	}

	// EQ_MATCH
	// LT_MATCH
	// GT_MATCH
	// LEQ_MATCH
	// GEQ_MATCH

	public static void put(Entity entity) {
		{
			ContentValues values = convKeyToValues(entity.getKey());
			sDb.insert("KEYS", null, values);
		}
		List<ContentValues> list = convPropertiesToValues(entity.getKey(), entity.getProperties());
		for (ContentValues values : list) {
			sDb.insert("VALUES", null, values);
		}
	}

	public static Entity get(Key key) {
		Cursor c;
		c = sDb.query("KEYS", null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		Key newKey = new Key();
		newKey.setId(c.getLong(c.getColumnIndex("ID")));
		newKey.setName(c.getString(c.getColumnIndex("NAME")));

		c = sDb.query("VALUES", null, "KEY_STR = ?", new String[] {
			KeyUtil.keyToString(key)
		}, null, null, null);
		Entity entity = new Entity();
		int nameIdx = c.getColumnIndex("NAME");
		int kindIdx = c.getColumnIndex("KIND");
		int typeIdx = c.getColumnIndex("TYPE");
		int valueIdx = c.getColumnIndex("VALUE");
		c.moveToFirst();
		do {
			String name = c.getString(nameIdx);
			String type = c.getString(typeIdx);
			byte[] blob = c.getBlob(valueIdx);
			Object value;
			if ("String".equals(type)) {
				value = new String(blob);
			} else {
				throw new UnsupportedOperationException();
			}
			entity.setProperty(name, value);
		} while (c.moveToNext());

		return entity;
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
				value.put("VALUE", ((String) obj).getBytes());
				values.add(value);
			} else {
				throw new UnsupportedOperationException();
			}
		}

		return values;
	}
}