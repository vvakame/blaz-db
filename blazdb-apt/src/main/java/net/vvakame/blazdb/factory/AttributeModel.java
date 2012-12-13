package net.vvakame.blazdb.factory;

import net.vvakame.blaz.annotation.Attribute;

/**
 * Model for {@link Attribute}.
 * @author vvakame
 */
public class AttributeModel {

	boolean primaryKey;

	String name;

	boolean persistent;


	/**
	 * @return the primaryKey
	 * @category accessor
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey the primaryKey to set
	 * @category accessor
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @category accessor
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the persistent
	 * @category accessor
	 */
	public boolean isPersistent() {
		return persistent;
	}

	/**
	 * @param persistent the persistent to set
	 * @category accessor
	 */
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
}
