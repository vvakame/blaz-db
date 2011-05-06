package net.vvakame.blaz;

/**
 * {@link Key} についてのユーティリティ.
 * @author vvakame
 */
public class KeyUtil {

	/**
	 * 指定のKindとnameを表す {@link Key} を生成し返す.
	 * @param kind
	 * @param name
	 * @return 生成したKey
	 * @author vvakame
	 */
	public static Key createKey(String kind, String name) {
		Key key = new Key();
		key.setKind(kind);
		key.setName(name);

		return key;
	}

	/**
	 * {@link Key} を文字列表現に変換する.
	 * @param key
	 * @return keyの文字列表現
	 * @author vvakame
	 */
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
