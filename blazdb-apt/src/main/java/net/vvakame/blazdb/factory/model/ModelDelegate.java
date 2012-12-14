package net.vvakame.blazdb.factory.model;

import java.lang.annotation.Annotation;

import net.vvakame.blaz.annotation.BlazModel;
import net.vvakame.blaz.annotation.Model;

/**
 * Delegate interface for {@link Model} or {@link BlazModel}.
 * @author vvakame
 */
public interface ModelDelegate {

	/**
	 * @return kind
	 * @author vvakame
	 */
	public String kind();

	/**
	 * @return schemaVersion
	 * @author vvakame
	 */
	public int schemaVersion();

	/**
	 * @return schemaVersionName
	 * @author vvakame
	 */
	public String schemaVersionName();

	/**
	 * @return annotationType
	 * @author vvakame
	 */
	public Class<? extends Annotation> annotationType();
}
