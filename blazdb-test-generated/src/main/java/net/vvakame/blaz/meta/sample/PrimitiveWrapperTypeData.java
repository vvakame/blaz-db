package net.vvakame.blaz.meta.sample;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.annotation.Model;

/**
 * A class with fields for all the primitive types.
 * @author vvakame
 */
@Model
public class PrimitiveWrapperTypeData {

	Key key;

	Boolean bool;

	Byte b;

	Short s;

	Integer i;

	Long l;

	Float f;

	Double d;


	/**
	 * @return the key
	 * @category accessor
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 * @category accessor
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the bool
	 * @category accessor
	 */
	public Boolean getBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 * @category accessor
	 */
	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	/**
	 * @return the b
	 * @category accessor
	 */
	public Byte getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(Byte b) {
		this.b = b;
	}

	/**
	 * @return the s
	 * @category accessor
	 */
	public Short getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 * @category accessor
	 */
	public void setS(Short s) {
		this.s = s;
	}

	/**
	 * @return the i
	 * @category accessor
	 */
	public Integer getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 * @category accessor
	 */
	public void setI(Integer i) {
		this.i = i;
	}

	/**
	 * @return the l
	 * @category accessor
	 */
	public Long getL() {
		return l;
	}

	/**
	 * @param l the l to set
	 * @category accessor
	 */
	public void setL(Long l) {
		this.l = l;
	}

	/**
	 * @return the f
	 * @category accessor
	 */
	public Float getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 * @category accessor
	 */
	public void setF(Float f) {
		this.f = f;
	}

	/**
	 * @return the d
	 * @category accessor
	 */
	public Double getD() {
		return d;
	}

	/**
	 * @param d the d to set
	 * @category accessor
	 */
	public void setD(Double d) {
		this.d = d;
	}
}
