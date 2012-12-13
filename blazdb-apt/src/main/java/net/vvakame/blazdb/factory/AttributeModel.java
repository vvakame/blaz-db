package net.vvakame.blazdb.factory;

import net.vvakame.blaz.annotation.Attribute;

/**
 * Model for {@link Attribute}.
 * @author vvakame
 */
public class AttributeModel {

	String typeNameFQN;

	/** if typeNameFQN was premitive, this field handle wrapper class name for type cast */
	String primitiveWrapper;

	String getter;

	String setter;

	// info from annotation

	boolean primaryKey;

	String name;

	boolean persistent;


	/**
	 * @return the typeNameFQN
	 * @category accessor
	 */
	public String getTypeNameFQN() {
		return typeNameFQN;
	}

	/**
	 * @param typeNameFQN the typeNameFQN to set
	 * @category accessor
	 */
	public void setTypeNameFQN(String typeNameFQN) {
		this.typeNameFQN = typeNameFQN;
	}

	/**
	 * @return the primitiveWrapper
	 * @category accessor
	 */
	public String getPrimitiveWrapper() {
		return primitiveWrapper;
	}

	/**
	 * @param primitiveWrapper the primitiveWrapper to set
	 * @category accessor
	 */
	public void setPrimitiveWrapper(String primitiveWrapper) {
		this.primitiveWrapper = primitiveWrapper;
	}

	/**
	 * @return the getter
	 * @category accessor
	 */
	public String getGetter() {
		return getter;
	}

	/**
	 * @param getter the getter to set
	 * @category accessor
	 */
	public void setGetter(String getter) {
		this.getter = getter;
	}

	/**
	 * @return the setter
	 * @category accessor
	 */
	public String getSetter() {
		return setter;
	}

	/**
	 * @param setter the setter to set
	 * @category accessor
	 */
	public void setSetter(String setter) {
		this.setter = setter;
	}

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
