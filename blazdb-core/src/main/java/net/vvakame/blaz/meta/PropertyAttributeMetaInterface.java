package net.vvakame.blaz.meta;

public interface PropertyAttributeMetaInterface<T> {

	/**
	 * Get property type class.
	 * 
	 * @return propety type
	 * @author vvakame
	 */
	public Class<?> getPropertyClass();

	public String getName();
}
