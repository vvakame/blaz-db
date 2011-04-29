package net.vvakame.blaz;

public class KeyUtil {

	public static Key createKey(String kind, String name) {
		Key key = new Key();
		key.setKind(kind);
		key.setName(name);

		return key;
	}

	public static String keyToString(Key key) {
		if (key == null) {
			return null;
		}
		if (key.getKind() == null) {
			throw new IllegalArgumentException("key's kind is required.");
		}
		StringBuilder builder = new StringBuilder();
		builder.append(key.getKind()).append("#");
		if (key.getName() != null) {
			builder.append("N#").append(key.getName());
		} else {
			builder.append("I#").append(String.valueOf(key.getId()));
		}
		return builder.toString();
	}
}
