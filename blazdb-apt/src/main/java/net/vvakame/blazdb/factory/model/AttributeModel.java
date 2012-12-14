package net.vvakame.blazdb.factory.model;

import net.vvakame.blaz.annotation.Attribute;

/**
 * Model for {@link Attribute}.
 * @author vvakame
 */
public class AttributeModel {

	String typeNameFQN;

	boolean numberPrimitive;

	boolean numberPrimitiveWrapper;

	/** if typeNameFQN was primitive or primitive wrapper class, this field are class name use for type cast */
	String castTo;

	boolean notForSearch;

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
	 * @return the numberPrimitive
	 * @category accessor
	 */
	public boolean isNumberPrimitive() {
		return numberPrimitive;
	}

	/**
	 * @param numberPrimitive the numberPrimitive to set
	 * @category accessor
	 */
	public void setNumberPrimitive(boolean numberPrimitive) {
		this.numberPrimitive = numberPrimitive;
	}

	/**
	 * @return the numberPrimitiveWrapper
	 * @category accessor
	 */
	public boolean isNumberPrimitiveWrapper() {
		return numberPrimitiveWrapper;
	}

	/**
	 * @param numberPrimitiveWrapper the numberPrimitiveWrapper to set
	 * @category accessor
	 */
	public void setNumberPrimitiveWrapper(boolean numberPrimitiveWrapper) {
		this.numberPrimitiveWrapper = numberPrimitiveWrapper;
	}

	/**
	 * @return the castTo
	 * @category accessor
	 */
	public String getCastTo() {
		return castTo;
	}

	/**
	 * @param castTo the castTo to set
	 * @category accessor
	 */
	public void setCastTo(String castTo) {
		this.castTo = castTo;
	}

	/**
	 * @return the notForSearch
	 * @category accessor
	 */
	public boolean isNotForSearch() {
		return notForSearch;
	}

	/**
	 * @param notForSearch the notForSearch to set
	 * @category accessor
	 */
	public void setNotForSearch(boolean notForSearch) {
		this.notForSearch = notForSearch;
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
