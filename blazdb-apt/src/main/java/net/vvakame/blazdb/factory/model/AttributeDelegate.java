package net.vvakame.blazdb.factory.model;

import java.lang.annotation.Annotation;

import net.vvakame.blaz.annotation.Attribute;
import net.vvakame.blaz.annotation.BlazAttribute;

/**
 * Delegate interface for {@link Attribute} or {@link BlazAttribute}.
 * @author vvakame
 */
public interface AttributeDelegate {

	/**
	 * @return primaryKey
	 * @author vvakame
	 */
	public boolean primaryKey();

	/**
	 * @return name
	 * @author vvakame
	 */
	public String name();

	/**
	 * @return persistent
	 * @author vvakame
	 */
	public boolean persistent();

	/**
	 * @return annotationType
	 * @author vvakame
	 */
	public Class<? extends Annotation> annotationType();
}
