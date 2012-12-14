package net.vvakame.blaz.meta.sample;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.annotation.BlazModel;

/**
 * A class with fields for all the not primitive and not primitive wrapper types.
 * @author vvakame
 */
@BlazModel
public class NotPrimitiveTypeData {

	Key key;

	String str;

	byte[] bytes;


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
	 * @return the str
	 * @category accessor
	 */
	public String getStr() {
		return str;
	}

	/**
	 * @param str the str to set
	 * @category accessor
	 */
	public void setStr(String str) {
		this.str = str;
	}

	/**
	 * @return the bytes
	 * @category accessor
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 * @category accessor
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
