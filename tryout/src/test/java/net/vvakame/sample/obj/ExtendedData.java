package net.vvakame.sample.obj;

import java.util.ArrayList;
import java.util.List;

public class ExtendedData extends RootData {

	byte byteData;

	short shortData;

	long longData;

	float floatData;

	double doubleData;

	boolean booleanData;

	List<String> list = new ArrayList<String>();


	/**
	 * @return the byteData
	 * @category accessor
	 */
	public byte getByteData() {
		return byteData;
	}

	/**
	 * @param byteData the byteData to set
	 * @category accessor
	 */
	public void setByteData(byte byteData) {
		this.byteData = byteData;
	}

	/**
	 * @return the shortData
	 * @category accessor
	 */
	public short getShortData() {
		return shortData;
	}

	/**
	 * @param shortData the shortData to set
	 * @category accessor
	 */
	public void setShortData(short shortData) {
		this.shortData = shortData;
	}

	/**
	 * @return the longData
	 * @category accessor
	 */
	public long getLongData() {
		return longData;
	}

	/**
	 * @param longData the longData to set
	 * @category accessor
	 */
	public void setLongData(long longData) {
		this.longData = longData;
	}

	/**
	 * @return the floatData
	 * @category accessor
	 */
	public float getFloatData() {
		return floatData;
	}

	/**
	 * @param floatData the floatData to set
	 * @category accessor
	 */
	public void setFloatData(float floatData) {
		this.floatData = floatData;
	}

	/**
	 * @return the doubleData
	 * @category accessor
	 */
	public double getDoubleData() {
		return doubleData;
	}

	/**
	 * @param doubleData the doubleData to set
	 * @category accessor
	 */
	public void setDoubleData(double doubleData) {
		this.doubleData = doubleData;
	}

	/**
	 * @return the booleanData
	 * @category accessor
	 */
	public boolean isBooleanData() {
		return booleanData;
	}

	/**
	 * @param booleanData the booleanData to set
	 * @category accessor
	 */
	public void setBooleanData(boolean booleanData) {
		this.booleanData = booleanData;
	}

	/**
	 * @return the list
	 * @category accessor
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 * @category accessor
	 */
	public void setList(List<String> list) {
		this.list = list;
	}
}
