package net.vvakame.blaz;

import net.vvakame.repackage.android.util.Base64;

/**
 * {@link Key} についてのユーティリティ.
 * @author vvakame
 */
public class KeyUtil {

	static final String SEPARATOR = "#";


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
	 * 指定のKindとidを表す {@link Key} を生成し返す.
	 * @param kind
	 * @param id
	 * @return 生成したKey
	 * @author vvakame
	 */
	public static Key createKey(String kind, long id) {
		Key key = new Key();
		key.setKind(kind);
		key.setId(id);

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
		builder.append(key.getKind()).append(SEPARATOR);
		if (key.getName() != null) {
			builder.append("N").append(SEPARATOR).append(key.getName());
		} else {
			builder.append("I").append(SEPARATOR).append(String.valueOf(key.getId()));
		}
		return Base64.encodeToString(builder.toString().getBytes(), Base64.NO_PADDING
				+ Base64.URL_SAFE);
	}

	/**
	 * {@link Key} の文字列表現から {@link Key} を復元する.
	 * @param str
	 * @return 復元された {@link Key}
	 * @author vvakame
	 */
	public static Key stringToKey(String str) {
		String keyStr = new String(Base64.decode(str, Base64.NO_PADDING + Base64.URL_SAFE));
		String[] split = keyStr.split(SEPARATOR);
		if ("N".equals(split[1])) {
			return createKey(split[0], split[2]);
		} else if ("I".equals(split[1])) {
			return createKey(split[0], Long.parseLong(split[2]));
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
