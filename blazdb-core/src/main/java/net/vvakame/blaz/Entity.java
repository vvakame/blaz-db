package net.vvakame.blaz;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.vvakame.blaz.util.KeyUtil;

/**
 * KVS の Value
 * @author vvakame
 */
public class Entity {

	private String kind;

	private Key key;

	private Map<String, Object> propertyMap = new HashMap<String, Object>();


	/**
	 * 指定された Kind のEntityを新規作成する.
	 * @param kind
	 * @category constructor
	 */
	public Entity(String kind) {
		if (kind == null) {
			throw new NullPointerException("kind is required");
		}
		this.kind = kind;
	}

	/**
	 * 指定された Kind と名前のEntityを新規作成する.
	 * @param kind
	 * @param name
	 * @category constructor
	 */
	public Entity(String kind, String name) {
		if (kind == null) {
			throw new NullPointerException("kind is required");
		}
		this.kind = kind;
		key = KeyUtil.createKey(kind, name);
	}

	/**
	 * 指定された key のEntityを新規作成する.
	 * @param key 
	 * @category constructor
	 */
	public Entity(Key key) {
		if (key == null) {
			throw new NullPointerException("key is required");
		}
		this.kind = key.getKind();
		this.key = key;
	}

	/**
	 * EntityのKindを取得します.
	 * @return Kind
	 * @author vvakame
	 */
	public String getKind() {
		if (key != null) {
			return key.getKind();
		} else {
			return kind;
		}
	}

	/**
	 * EntityのKeyを設定する.
	 * @param key
	 * @author vvakame
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * EntityのKeyを取得します.
	 * @return {@link Key}
	 * @author vvakame
	 */
	public Key getKey() {
		return this.key;
	}

	/**
	 * Entityが保持するプロパティを返します.
	 * @param <T> 
	 * @param name
	 * @return プロパティの値
	 * @author vvakame
	 */
	@SuppressWarnings("unchecked")
	public <T>T getProperty(String name) {
		return (T) propertyMap.get(name);
	}

	/**
	 * Entityが保持するプロパティのMapを返します.<br>
	 * 防御的コピーは行われていないため、返されたMapを変更するとEntityにも影響が及びます.
	 * @return Entityが保持するプロパティのMap
	 * @author vvakame
	 */
	public Map<String, Object> getProperties() {
		return propertyMap;
	}

	/**
	 * Entityが指定のプロパティを保持しているかをチェックします.
	 * @param name
	 * @return 指定のプロパティを保持しているか否か
	 * @author vvakame
	 */
	public boolean hasProperty(String name) {
		return propertyMap.containsKey(name);
	}

	/**
	 * Entityが保持している指定のプロパティを削除します.
	 * @param name
	 * @author vvakame
	 */
	public void removeProperty(String name) {
		propertyMap.remove(name);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	public void setProperty(String name, Object value) {
		if (value == null) {
			propertyMap.put(name, value);
		} else if (value instanceof String) {
			setProperty(name, (String) value);
		} else if (value instanceof Key) {
			setProperty(name, (Key) value);
		} else if (value instanceof Byte) {
			setProperty(name, (long) (Byte) value);
		} else if (value instanceof Short) {
			setProperty(name, (long) (Short) value);
		} else if (value instanceof Integer) {
			setProperty(name, (long) (Integer) value);
		} else if (value instanceof Long) {
			setProperty(name, (long) (Long) value);
		} else if (value instanceof Float) {
			setProperty(name, (double) (Float) value);
		} else if (value instanceof Double) {
			setProperty(name, (double) (Double) value);
		} else if (value instanceof Boolean) {
			setProperty(name, (boolean) (Boolean) value);
		} else if (value instanceof Collection<?>) {
			setProperty(name, (Collection<?>) value);
		} else if (value instanceof byte[]) {
			setProperty(name, (byte[]) value);
		} else {
			throw new IllegalArgumentException("unsupported type="
					+ value.getClass().getCanonicalName());
		}
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, long value) {
		propertyMap.put(name, value);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, double value) {
		propertyMap.put(name, value);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, boolean value) {
		propertyMap.put(name, value);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, Collection<?> value) {
		propertyMap.put(name, value);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, byte[] value) {
		propertyMap.put(name, value);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, String value) {
		propertyMap.put(name, value);
	}

	/**
	 * Entityのプロパティに値をセットします.
	 * @param name
	 * @param value
	 * @author vvakame
	 */
	private void setProperty(String name, Key value) {
		propertyMap.put(name, value);
	}
}
