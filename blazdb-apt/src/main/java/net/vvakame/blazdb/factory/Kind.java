package net.vvakame.blazdb.factory;

import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.PropertyConverter;
import net.vvakame.blazdb.factory.model.AttributeModel;

/**
 * The type {@link AttributeModel} represents.
 * 
 * @author vvakame
 */
public enum Kind {
	/** 未指定 */
	UNKNOWN,
	/** {@link String} */
	STRING,
	/** {@code boolean} */
	BOOLEAN,
	/** {@code double} */
	DOUBLE,
	/** {@code long} */
	LONG,
	/** {@code byte} */
	BYTE,
	/** {@code char} */
	CHAR,
	/** {@code float} */
	FLOAT,
	/** {@code int} */
	INT,
	/** {@code short} */
	SHORT,
	/** {@link Date} */
	DATE,
	/** {@link List} */
	LIST,
	/** {@link Enum} */
	ENUM,
	/** {@link Key} */
	KEY,
	/** byte[] */
	BYTE_ARRAY,
	/** {@link Boolean} */
	BOOLEAN_WRAPPER,
	/** {@link Double} */
	DOUBLE_WRAPPER,
	/** {@link Long} */
	LONG_WRAPPER,
	/** {@link Byte} */
	BYTE_WRAPPER,
	/** {@link Character} */
	CHAR_WRAPPER,
	/** {@link Float} */
	FLOAT_WRAPPER,
	/** {@link Integer} */
	INT_WRAPPER,
	/** {@link Short} */
	SHORT_WRAPPER,
	/** {@link PropertyConverter} */
	CONVERTER
}
