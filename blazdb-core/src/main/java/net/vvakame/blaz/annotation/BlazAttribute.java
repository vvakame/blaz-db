package net.vvakame.blaz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.vvakame.blaz.PropertyConverter;
import net.vvakame.blaz.PropertyConverter.DummyConverter;

/**
 * Attribute. attribute nearly equals to Entity's property.<br>
 * This class was alternative of {@link Attribute}. same class exsits in Slim3.
 * 
 * @author vvakame
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD })
public @interface BlazAttribute {

	/**
	 * This property is a primary key.
	 * 
	 * @return primary key or not
	 * @author vvakame
	 */
	boolean primaryKey() default false;

	/**
	 * The property name. default value is a field name.
	 * 
	 * @return property name
	 * @author vvakame
	 */
	String name() default "";

	/**
	 * This property is persisted.
	 * 
	 * @return persisted or not
	 * @author vvakame
	 */
	boolean persistent() default true;

	/**
	 * Thire property processed by appointed {@link PropertyConverter}.
	 * 
	 * @return {@link PropertyConverter} class
	 * @author vvakame
	 */
	Class<? extends PropertyConverter<?, ?>> converter() default DummyConverter.class;
}
