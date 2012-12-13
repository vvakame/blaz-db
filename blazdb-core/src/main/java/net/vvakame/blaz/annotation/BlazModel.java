package net.vvakame.blaz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Model. model nearly equals to Entity schema.
 * 
 * @author vvakame
 */
@Retention(RetentionPolicy.CLASS)
@Target({
	ElementType.TYPE
})
public @interface BlazModel {

	/**
	 * The kind of entity. default Class name.
	 * @return Kind
	 * @author vvakame
	 */
	String kind() default "";

	/**
	 * Entity schema versions.
	 * @return schema version
	 * @author vvakame
	 */
	int schemaVersion() default 0;

	/**
	 * The property name of Entity schema version.
	 * @return schemaVersionName
	 * @author vvakame
	 */
	String schemaVersionName() default "blazdb.schemaVersion";
}
