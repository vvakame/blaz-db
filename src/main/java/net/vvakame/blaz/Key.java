package net.vvakame.blaz;

import java.io.Serializable;

/**
 * KVS の Key
 * @author vvakame
 */
public class Key implements Serializable, Comparable<Key> {

	private static final long serialVersionUID = 1L;

	String kind;

	String name;

	long id;


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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Key)) {
			return false;
		}
		Key otherKey = (Key) obj;
		if (!kind.equals(otherKey.kind)) {
			return false;
		} else if (name != null && !name.equals(otherKey.name)) {
			return false;
		} else if (id != otherKey.id) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + ((kind == null) ? 0 : kind.hashCode());
		result = 31 * result + ((name == null) ? 0 : name.hashCode());
		result = 31 * result + (int) (id ^ id >>> 32);
		return result;
	}

	@Override
	public int compareTo(Key other) {
		if (this == other) {
			return 0;
		}

		return compareToSub(this, other);
	}

	private int compareToSub(Key thisKey, Key otherKey) {
		if (thisKey == otherKey) {
			return 0;
		}
		int result;
		result = thisKey.getKind().compareTo(otherKey.getKind());
		if (result != 0) {
			return result;
		}
		result = thisKey.getName().compareTo(otherKey.getName());
		if (result != 0) {
			return result;
		}
		result = Long.valueOf(thisKey.getId()).compareTo(Long.valueOf(otherKey.getId()));
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(kind).append("(");
		if (name != null) {
			builder.append("\"" + name + "\"");
		} else if (id == 0L) {
			builder.append("no id yet.");
		} else {
			builder.append(id);
		}
		builder.append(")");
		return builder.toString();
	}
}
