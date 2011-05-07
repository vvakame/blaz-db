package net.vvakame.blaz;

import java.util.HashMap;
import java.util.Map;

/**
 * KVS の Value
 * @author vvakame
 */
public class Entity {

	private Key key;

	private Map<String, Object> propertyMap = new HashMap<String, Object>();


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
	 * EntityのKindを取得します.
	 * @return Kind
	 * @author vvakame
	 */
	public String getKind() {
		return this.key.getKind();
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
		propertyMap.put(name, value);
	}
}
