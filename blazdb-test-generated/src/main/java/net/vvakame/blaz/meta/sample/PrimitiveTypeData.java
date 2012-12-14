package net.vvakame.blaz.meta.sample;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.annotation.Model;

/**
 * A class with fields for all the primitive types.
 * @author vvakame
 */
@Model
public class PrimitiveTypeData {

	Key key;

	boolean bool;

	byte b;

	short s;

	int i;

	long l;

	float f;

	double d;


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
	public boolean isBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 * @category accessor
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @return the b
	 * @category accessor
	 */
	public byte getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(byte b) {
		this.b = b;
	}

	/**
	 * @return the s
	 * @category accessor
	 */
	public short getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 * @category accessor
	 */
	public void setS(short s) {
		this.s = s;
	}

	/**
	 * @return the i
	 * @category accessor
	 */
	public int getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 * @category accessor
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * @return the l
	 * @category accessor
	 */
	public long getL() {
		return l;
	}

	/**
	 * @param l the l to set
	 * @category accessor
	 */
	public void setL(long l) {
		this.l = l;
	}

	/**
	 * @return the f
	 * @category accessor
	 */
	public float getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 * @category accessor
	 */
	public void setF(float f) {
		this.f = f;
	}

	/**
	 * @return the d
	 * @category accessor
	 */
	public double getD() {
		return d;
	}

	/**
	 * @param d the d to set
	 * @category accessor
	 */
	public void setD(double d) {
		this.d = d;
	}
}
