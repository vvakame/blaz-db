package net.vvakame.blaz;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * KVS の Key
 * @author vvakame
 */
public class Key implements Serializable, Comparable<Key> {

	private static final long serialVersionUID = 1L;

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
		} else if (!name.equals(otherKey.name)) {
			return false;
		} else if (id != otherKey.id) {
			return false;
		} else if (parent == null && otherKey.parent == null) {
			return true;
		} else if (parent == null && otherKey.parent != null) {
			return false;
		} else {
			return parent.equals(otherKey.parent);
		}
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + ((parent == null) ? 0 : parent.hashCode());
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

		Key tmpKey;
		LinkedList<Key> thisKeys = new LinkedList<Key>();
		tmpKey = this;
		do {
			thisKeys.addFirst(tmpKey);
		} while ((tmpKey = tmpKey.getParent()) != null);
		LinkedList<Key> otherKeys = new LinkedList<Key>();
		tmpKey = other;
		do {
			otherKeys.addFirst(tmpKey);
		} while ((tmpKey = tmpKey.getParent()) != null);

		for (int i = 0; i < thisKeys.size(); i++) {
			Key thisKey = thisKeys.get(i);
			Key otherKey = i < otherKeys.size() ? otherKeys.get(i) : null;
			if (otherKey == null) {
				return 1;
			}
			int result = compareToSub(thisKey, otherKey);
			if (result != 0) {
				return result;
			}
		}

		return thisKeys.size() < otherKeys.size() ? -1 : 0;
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
		if (parent != null) {
			builder.append(parent.toString());
		}
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
