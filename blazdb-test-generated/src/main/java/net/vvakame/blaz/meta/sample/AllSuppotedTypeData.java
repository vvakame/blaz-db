package net.vvakame.blaz.meta.sample;

import java.util.Date;
import java.util.List;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.PropertyConverter;
import net.vvakame.blaz.annotation.Attribute;
import net.vvakame.blaz.annotation.BlazAttribute;
import net.vvakame.blaz.annotation.Model;

/**
 * A class with fields for all the primitive types.
 * @author vvakame
 */
@Model(kind = "AllTypes", schemaVersion = 1, schemaVersionName = "sv")
public class AllSuppotedTypeData {

	@Attribute(primaryKey = true)
	Key key;

	Key k;

	boolean bool;

	byte b;

	short s;

	int i;

	long l;

	float f;

	double d;

	Boolean boolW;

	Byte bW;

	Short sW;

	Integer iW;

	Long lW;

	Float fW;

	Double dW;

	String str;

	byte[] bytes;

	List<Key> keyList;

	List<Boolean> boolWList;

	List<Byte> bWList;

	List<Short> sWList;

	List<Integer> iWList;

	List<Long> lWList;

	List<Float> fWList;

	List<Double> dWList;

	List<String> strList;

	List<byte[]> bytesList;

	@BlazAttribute(converter = DateConverter.class)
	Date date;

	@BlazAttribute(converter = DateConverter.class)
	List<Date> dateList;


	static class DateConverter extends PropertyConverter<Date, Long> {

		public static DateConverter getInstance() {
			return new DateConverter();
		}

		@Override
		public Long serialize(Date value) {
			return value.getTime();
		}

		@Override
		public Date deserialize(Long value) {
			return new Date(value);
		}
	}


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
	 * @return the k
	 * @category accessor
	 */
	public Key getK() {
		return k;
	}

	/**
	 * @param k the k to set
	 * @category accessor
	 */
	public void setK(Key k) {
		this.k = k;
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

	/**
	 * @return the boolW
	 * @category accessor
	 */
	public Boolean getBoolW() {
		return boolW;
	}

	/**
	 * @param boolW the boolW to set
	 * @category accessor
	 */
	public void setBoolW(Boolean boolW) {
		this.boolW = boolW;
	}

	/**
	 * @return the bW
	 * @category accessor
	 */
	public Byte getbW() {
		return bW;
	}

	/**
	 * @param bW the bW to set
	 * @category accessor
	 */
	public void setbW(Byte bW) {
		this.bW = bW;
	}

	/**
	 * @return the sW
	 * @category accessor
	 */
	public Short getsW() {
		return sW;
	}

	/**
	 * @param sW the sW to set
	 * @category accessor
	 */
	public void setsW(Short sW) {
		this.sW = sW;
	}

	/**
	 * @return the iW
	 * @category accessor
	 */
	public Integer getiW() {
		return iW;
	}

	/**
	 * @param iW the iW to set
	 * @category accessor
	 */
	public void setiW(Integer iW) {
		this.iW = iW;
	}

	/**
	 * @return the lW
	 * @category accessor
	 */
	public Long getlW() {
		return lW;
	}

	/**
	 * @param lW the lW to set
	 * @category accessor
	 */
	public void setlW(Long lW) {
		this.lW = lW;
	}

	/**
	 * @return the fW
	 * @category accessor
	 */
	public Float getfW() {
		return fW;
	}

	/**
	 * @param fW the fW to set
	 * @category accessor
	 */
	public void setfW(Float fW) {
		this.fW = fW;
	}

	/**
	 * @return the dW
	 * @category accessor
	 */
	public Double getdW() {
		return dW;
	}

	/**
	 * @param dW the dW to set
	 * @category accessor
	 */
	public void setdW(Double dW) {
		this.dW = dW;
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

	/**
	 * @return the keyList
	 * @category accessor
	 */
	public List<Key> getKeyList() {
		return keyList;
	}

	/**
	 * @param keyList the keyList to set
	 * @category accessor
	 */
	public void setKeyList(List<Key> keyList) {
		this.keyList = keyList;
	}

	/**
	 * @return the boolWList
	 * @category accessor
	 */
	public List<Boolean> getBoolWList() {
		return boolWList;
	}

	/**
	 * @param boolWList the boolWList to set
	 * @category accessor
	 */
	public void setBoolWList(List<Boolean> boolWList) {
		this.boolWList = boolWList;
	}

	/**
	 * @return the bWList
	 * @category accessor
	 */
	public List<Byte> getbWList() {
		return bWList;
	}

	/**
	 * @param bWList the bWList to set
	 * @category accessor
	 */
	public void setbWList(List<Byte> bWList) {
		this.bWList = bWList;
	}

	/**
	 * @return the sWList
	 * @category accessor
	 */
	public List<Short> getsWList() {
		return sWList;
	}

	/**
	 * @param sWList the sWList to set
	 * @category accessor
	 */
	public void setsWList(List<Short> sWList) {
		this.sWList = sWList;
	}

	/**
	 * @return the iWList
	 * @category accessor
	 */
	public List<Integer> getiWList() {
		return iWList;
	}

	/**
	 * @param iWList the iWList to set
	 * @category accessor
	 */
	public void setiWList(List<Integer> iWList) {
		this.iWList = iWList;
	}

	/**
	 * @return the lWList
	 * @category accessor
	 */
	public List<Long> getlWList() {
		return lWList;
	}

	/**
	 * @param lWList the lWList to set
	 * @category accessor
	 */
	public void setlWList(List<Long> lWList) {
		this.lWList = lWList;
	}

	/**
	 * @return the fWList
	 * @category accessor
	 */
	public List<Float> getfWList() {
		return fWList;
	}

	/**
	 * @param fWList the fWList to set
	 * @category accessor
	 */
	public void setfWList(List<Float> fWList) {
		this.fWList = fWList;
	}

	/**
	 * @return the dWList
	 * @category accessor
	 */
	public List<Double> getdWList() {
		return dWList;
	}

	/**
	 * @param dWList the dWList to set
	 * @category accessor
	 */
	public void setdWList(List<Double> dWList) {
		this.dWList = dWList;
	}

	/**
	 * @return the strList
	 * @category accessor
	 */
	public List<String> getStrList() {
		return strList;
	}

	/**
	 * @param strList the strList to set
	 * @category accessor
	 */
	public void setStrList(List<String> strList) {
		this.strList = strList;
	}

	/**
	 * @return the bytesList
	 * @category accessor
	 */
	public List<byte[]> getBytesList() {
		return bytesList;
	}

	/**
	 * @param bytesList the bytesList to set
	 * @category accessor
	 */
	public void setBytesList(List<byte[]> bytesList) {
		this.bytesList = bytesList;
	}

	/**
	 * @return the date
	 * @category accessor
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 * @category accessor
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the dateList
	 * @category accessor
	 */
	public List<Date> getDateList() {
		return dateList;
	}

	/**
	 * @param dateList the dateList to set
	 * @category accessor
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}
}
