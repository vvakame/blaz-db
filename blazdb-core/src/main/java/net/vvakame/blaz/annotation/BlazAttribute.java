package net.vvakame.blaz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attribute. attribute nearly equals to Entity's property.<br>
 * This class was alternative of {@link Attribute}. same class exsits in Slim3.
 * @author vvakame
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
	ElementType.FIELD
})
public @interface BlazAttribute {

	/**
	 * This property is a primary key.
	 * @return primary key or not
	 * @author vvakame
	 */
	boolean primaryKey() default false;

	/**
	 * The property name. default value is a field name.
	 * @return property name
	 * @author vvakame
	 */
	String name() default "";

	/**
	 * This property is persisted.
	 * @return persisted or not
	 * @author vvakame
	 */
	boolean persistent() default true;
}
