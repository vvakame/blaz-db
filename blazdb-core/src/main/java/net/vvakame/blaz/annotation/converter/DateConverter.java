package net.vvakame.blaz.annotation.converter;

import java.util.Date;

import net.vvakame.blaz.PropertyConverter;

/**
 * {@link PropertyConverter} for {@link Date}.
 * 
 * @author vvakame
 */
public class DateConverter extends PropertyConverter<Date, Long> {

	static DateConverter converter;

	private DateConverter() {
	}

	/**
	 * Get instance.
	 * 
	 * @return instance
	 * @author vvakame
	 */
	public static DateConverter getInstance() {
		if (converter == null) {
			converter = new DateConverter();
		}
		return converter;
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
