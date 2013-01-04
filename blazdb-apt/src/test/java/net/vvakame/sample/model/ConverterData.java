package net.vvakame.sample.model;

import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.PropertyConverter;
import net.vvakame.blaz.annotation.Attribute;
import net.vvakame.blaz.annotation.BlazAttribute;
import net.vvakame.blaz.annotation.BlazModel;

@BlazModel
public class ConverterData {

	Key key;

	@BlazAttribute(converter = DateConverter.class)
	Date at;

	@BlazAttribute(converter = DateConverter.class)
	List<Date> atList;

	@Attribute(converter = NestedConverter.class)
	List<List<String>> nestedConverter;

	public static class DateConverter extends PropertyConverter<Date, Long> {
		public static DateConverter getInstance() {
			return new DateConverter();
		}

		@Override
		public Long serialize(Date value) {
			return value.getTime();
		}

		@Override
		public Date deserialize(Long value) {
			return new Date(value);
		}
	}

	public static class VoidConverter extends PropertyConverter<Void, Void> {
		public static VoidConverter getInstance() {
			return new VoidConverter();
		}
	}

	public static class Raw1Converter<P> extends PropertyConverter<P, String> {
		@SuppressWarnings("rawtypes")
		public static Raw1Converter getInstance() {
			return new Raw1Converter();
		}
	}

	public static class Raw2Converter extends Raw1Converter<String> {
		public static Raw2Converter getInstance() {
			return new Raw2Converter();
		}
	}

	public static class NestedConverter extends
			PropertyConverter<List<List<String>>, String> {
		public static NestedConverter getInstance() {
			return new NestedConverter();
		}
	}

	/**
	 * @return the key
	 * @category accessor
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 * @category accessor
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the at
	 * @category accessor
	 */
	public Date getAt() {
		return at;
	}

	/**
	 * @param at
	 *            the at to set
	 * @category accessor
	 */
	public void setAt(Date at) {
		this.at = at;
	}

	/**
	 * @return the atList
	 * @category accessor
	 */
	public List<Date> getAtList() {
		return atList;
	}

	/**
	 * @param atList
	 *            the atList to set
	 * @category accessor
	 */
	public void setAtList(List<Date> atList) {
		this.atList = atList;
	}

	/**
	 * @return the nestedConverter
	 * @category accessor
	 */
	public List<List<String>> getNestedConverter() {
		return nestedConverter;
	}

	/**
	 * @param nestedConverter
	 *            the nestedConverter to set
	 * @category accessor
	 */
	public void setNestedConverter(List<List<String>> nestedConverter) {
		this.nestedConverter = nestedConverter;
	}
}
