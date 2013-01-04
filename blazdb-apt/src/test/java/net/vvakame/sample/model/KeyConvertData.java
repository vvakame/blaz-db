package net.vvakame.sample.model;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.PropertyConverter;
import net.vvakame.blaz.annotation.BlazAttribute;
import net.vvakame.blaz.annotation.BlazModel;
import net.vvakame.blaz.util.KeyUtil;

@BlazModel
public class KeyConvertData {
	@BlazAttribute(primaryKey = true, converter = LongToKeyConverter.class)
	Long key;

	String data;

	static class LongToKeyConverter extends PropertyConverter<Long, Key> {
		public static LongToKeyConverter getInstance() {
			return new LongToKeyConverter();
		}

		@Override
		public Key serialize(Long value) {
			return KeyUtil.createKey("sample", value);
		}

		@Override
		public Long deserialize(Key value) {
			return value.getId();
		}
	}

	/**
	 * @return the key
	 * @category accessor
	 */
	public Long getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 * @category accessor
	 */
	public void setKey(Long key) {
		this.key = key;
	}

	/**
	 * @return the data
	 * @category accessor
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 * @category accessor
	 */
	public void setData(String data) {
		this.data = data;
	}
}
