package net.vvakame.blaz.meta.sample;

import java.util.Date;

import net.vvakame.blaz.Key;
import net.vvakame.blaz.annotation.Model;

/**
 * テスト用クラス
 * @author vvakame
 */
@Model
public class RootData {

	Key key;

	String str;

	int integer;

	Date date;


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
	 * @return the integer
	 * @category accessor
	 */
	public int getInteger() {
		return integer;
	}

	/**
	 * @param integer the integer to set
	 * @category accessor
	 */
	public void setInteger(int integer) {
		this.integer = integer;
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
}
