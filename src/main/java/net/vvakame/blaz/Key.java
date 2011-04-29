package net.vvakame.blaz;

/**
 * KVS の Key
 * @author vvakame
 */
public class Key {

	Key parent;

	String kind;

	String name;

	long id;


	/**
	 * @return the parent
	 * @category accessor
	 */
	public Key getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 * @category accessor
	 */
	public void setParent(Key parent) {
		this.parent = parent;
	}

	/**
	 * @return the kind
	 * @category accessor
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 * @category accessor
	 */
	public void setKind(String kind) {
		this.kind = kind;
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
	 * @return the id
	 * @category accessor
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @category accessor
	 */
	public void setId(long id) {
		this.id = id;
	}
}
