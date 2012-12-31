package net.vvakame.blazdb.factory.model;

import net.vvakame.blaz.annotation.Attribute;
import net.vvakame.blazdb.factory.ConverterType;
import net.vvakame.blazdb.factory.Kind;

/**
 * Model for {@link Attribute}.
 * 
 * @author vvakame
 */
public class AttributeModel {

	String typeNameFQNWithGenerics;

	String typeNameFQN;

	/**
	 * if typeNameFQN was primitive or primitive wrapper class, this field are
	 * class name use for type cast
	 */
	String castTo;

	Kind kind;

	Kind subKind;

	String subTypeNameFQN;

	String getter;

	String setter;

	ConverterType converterType;

	// info from annotation

	boolean primaryKey;

	String name;

	boolean persistent;

	String converterFQN;

	/**
	 * Accessor for enum value.
	 * 
	 * @return kind as String
	 * @author vvakame
	 */
	public String kind() {
		return kind != null ? kind.name() : null;
	}

	/**
	 * Accessor for enum value.
	 * 
	 * @return subKind as String
	 * @author vvakame
	 */
	public String subKind() {
		return subKind != null ? subKind.name() : null;
	}

	/**
	 * Accessor for enum value.
	 * 
	 * @return converterType as String
	 * @author vvakame
	 */
	public String converterType() {
		return converterType != null ? converterType.name() : null;
	}

	/**
	 * Check this attribute is number primitive?
	 * 
	 * @return number primitive or not
	 * @author vvakame
	 */
	public boolean isNumberPrimitive() {
		switch (kind) {
		case BYTE:
		case SHORT:
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check this attribute is number primitive wrapper?
	 * 
	 * @return number primitive wrapper or not
	 * @author vvakame
	 */
	public boolean isNumberPrimitiveWrapper() {
		switch (kind) {
		case BYTE_WRAPPER:
		case SHORT_WRAPPER:
		case INT_WRAPPER:
		case LONG_WRAPPER:
		case FLOAT_WRAPPER:
		case DOUBLE_WRAPPER:
			return true;
		default:
			return false;
		}
	}

	// ↑ for mvel template

	/**
	 * @return the typeNameFQNWithGenerics
	 * @category accessor
	 */
	public String getTypeNameFQNWithGenerics() {
		return typeNameFQNWithGenerics;
	}

	/**
	 * @return the typeNameFQN
	 * @category accessor
	 */
	public String getTypeNameFQN() {
		return typeNameFQN;
	}

	/**
	 * @param typeNameFQN
	 *            the typeNameFQN to set
	 * @category accessor
	 */
	public void setTypeNameFQN(String typeNameFQN) {
		this.typeNameFQN = typeNameFQN;
	}

	/**
	 * @return the castTo
	 * @category accessor
	 */
	public String getCastTo() {
		return castTo;
	}

	/**
	 * @param castTo
	 *            the castTo to set
	 * @category accessor
	 */
	public void setCastTo(String castTo) {
		this.castTo = castTo;
	}

	/**
	 * @return the kind
	 * @category accessor
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 * @category accessor
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the subKind
	 * @category accessor
	 */
	public Kind getSubKind() {
		return subKind;
	}

	/**
	 * @param subKind
	 *            the subKind to set
	 * @category accessor
	 */
	public void setSubKind(Kind subKind) {
		this.subKind = subKind;
	}

	/**
	 * @return the subTypeNameFQN
	 * @category accessor
	 */
	public String getSubTypeNameFQN() {
		return subTypeNameFQN;
	}

	/**
	 * @param subTypeNameFQN
	 *            the subTypeNameFQN to set
	 * @category accessor
	 */
	public void setSubTypeNameFQN(String subTypeNameFQN) {
		this.subTypeNameFQN = subTypeNameFQN;
	}

	/**
	 * @return the getter
	 * @category accessor
	 */
	public String getGetter() {
		return getter;
	}

	/**
	 * @param getter
	 *            the getter to set
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
	 * @param setter
	 *            the setter to set
	 * @category accessor
	 */
	public void setSetter(String setter) {
		this.setter = setter;
	}

	/**
	 * @return the converterType
	 * @category accessor
	 */
	public ConverterType getConverterType() {
		return converterType;
	}

	/**
	 * @param converterType
	 *            the converterType to set
	 * @category accessor
	 */
	public void setConverterType(ConverterType converterType) {
		this.converterType = converterType;
	}

	/**
	 * @return the primaryKey
	 * @category accessor
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey
	 *            the primaryKey to set
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
	 * @param name
	 *            the name to set
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
	 * @param persistent
	 *            the persistent to set
	 * @category accessor
	 */
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	/**
	 * @return the converterFQN
	 * @category accessor
	 */
	public String getConverterFQN() {
		return converterFQN;
	}

	/**
	 * @param converterFQN
	 *            the converterFQN to set
	 * @category accessor
	 */
	public void setConverterFQN(String converterFQN) {
		this.converterFQN = converterFQN;
	}

	/**
	 * @param typeNameFQNWithGenerics
	 *            the typeNameFQNWithGenerics to set
	 * @category accessor
	 */
	public void setTypeNameFQNWithGenerics(String typeNameFQNWithGenerics) {
		this.typeNameFQNWithGenerics = typeNameFQNWithGenerics;
	}
}
