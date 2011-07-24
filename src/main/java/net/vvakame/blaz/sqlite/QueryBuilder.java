package net.vvakame.blaz.sqlite;

import java.util.List;

import net.vvakame.blaz.IFilter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.KeyUtil;
import static net.vvakame.blaz.sqlite.KvsOpenHelper.*;

class QueryBuilder {

	static final String SQL_ALL = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS;

	static final String SQL_KEY_ID_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_ID + " = ?";

	static final String SQL_KEY_ID_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_ID + " > ?";

	static final String SQL_KEY_ID_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_ID + " >= ?";

	static final String SQL_KEY_ID_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_ID + " < ?";

	static final String SQL_KEY_ID_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_ID + " <= ?";

	static final String SQL_KEY_NAME_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_NAME + " = ?";

	static final String SQL_KEY_NAME_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_NAME + " > ?";

	static final String SQL_KEY_NAME_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_NAME + " >= ?";

	static final String SQL_KEY_NAME_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_NAME + " < ?";

	static final String SQL_KEY_NAME_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS
			+ " WHERE " + COL_KIND + " = ? AND " + COL_NAME + " <= ?";

	static final String SQL_KIND = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS + " WHERE "
			+ COL_KIND + " = ?";

	static final String SQL_PROPERTY_STR_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " = ?";

	static final String SQL_PROPERTY_STR_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " > ?";

	static final String SQL_PROPERTY_STR_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND "
			+ COL_VALUE_STRING + " >= ?";

	static final String SQL_PROPERTY_STR_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " < ?";

	static final String SQL_PROPERTY_STR_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_TYPE + " = ? AND " + COL_NAME + " = ? AND "
			+ COL_VALUE_STRING + " <= ?";

	static final String SQL_PROPERTY_INTEGER_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " = ?";

	static final String SQL_PROPERTY_INTEGER_GT = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " > ?";

	static final String SQL_PROPERTY_INTEGER_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " >= ?";

	static final String SQL_PROPERTY_INTEGER_LT = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " < ?";

	static final String SQL_PROPERTY_INTEGER_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " <= ?";

	static final String SQL_PROPERTY_REAL_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " = ?";

	static final String SQL_PROPERTY_REAL_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " > ?";

	static final String SQL_PROPERTY_REAL_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " >= ?";

	static final String SQL_PROPERTY_REAL_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " < ?";

	static final String SQL_PROPERTY_REAL_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " <= ?";


	// FIXME SQL_PROPERTY_KEY_EQ 的な何かが必要

	static void makeGetAllQuery(StringBuilder builder, List<String> args) {
		builder.append(SQL_ALL);
	}

	static void makeQuery(IFilter filter, StringBuilder builder, List<String> args) {
		switch (filter.getTarget()) {
			case KEY:
				makeQueryKey(filter, builder, args);
				break;

			case KIND:
				makeQueryKind(filter, builder, args);
				break;

			case PROPERTY:
				makeQueryProperty(filter, builder, args);
				break;

			default:
				throw new IllegalArgumentException();
		}
	}

	static void makeQueryKey(IFilter filter, StringBuilder builder, List<String> args) {
		Key key = (Key) filter.getValue();
		args.add(key.getKind());

		if (key.getName() == null) {
			switch (filter.getOption()) {
				case EQ:
					builder.append(SQL_KEY_ID_EQ);
					break;

				case GT:
					builder.append(SQL_KEY_ID_GT);
					break;

				case GT_EQ:
					builder.append(SQL_KEY_ID_GT_EQ);
					break;

				case LT:
					builder.append(SQL_KEY_ID_LT);
					break;

				case LT_EQ:
					builder.append(SQL_KEY_ID_LT_EQ);
					break;

				default:
					break;
			}
			args.add(String.valueOf(key.getId()));
		} else {
			switch (filter.getOption()) {
				case EQ:
					builder.append(SQL_KEY_NAME_EQ);
					break;

				case GT:
					builder.append(SQL_KEY_NAME_GT);
					break;

				case GT_EQ:
					builder.append(SQL_KEY_NAME_GT_EQ);
					break;

				case LT:
					builder.append(SQL_KEY_NAME_LT);
					break;

				case LT_EQ:
					builder.append(SQL_KEY_NAME_LT_EQ);
					break;

				default:
					break;
			}
			args.add(key.getName());
		}
	}

	static void makeQueryKind(IFilter filter, StringBuilder builder, List<String> args) {
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_KIND);
				break;
			default:
				break;
		}
		args.add(filter.getName());
	}

	static void makeQueryProperty(IFilter filter, StringBuilder builder, List<String> args) {
		Object obj = filter.getValue();
		if (obj instanceof Key) {
			makeQueryPropertyKey(filter, builder, args);
		} else if (obj instanceof String) {
			makeQueryPropertyString(filter, builder, args);
		} else if (obj instanceof Boolean) {
			makeQueryPropertyBoolean(filter, builder, args);
		} else if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long) {
			makeQueryPropertyInteger(filter, builder, args);
		} else if (obj instanceof Float || obj instanceof Double) {
			makeQueryPropertyReal(filter, builder, args);
		} else {
			throw new IllegalArgumentException();
		}
	}

	static void makeQueryPropertyKey(IFilter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_STR_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_STR_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_STR_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_STR_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_STR_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(T_KEY);
		args.add(name);
		args.add(KeyUtil.keyToString((Key) obj));
	}

	static void makeQueryPropertyString(IFilter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_STR_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_STR_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_STR_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_STR_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_STR_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(T_STRING);
		args.add(name);
		args.add((String) obj);
	}

	static void makeQueryPropertyBoolean(IFilter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_STR_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_STR_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_STR_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_STR_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_STR_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(T_BOOLEAN);
		args.add(name);
		if ((Boolean) obj) {
			args.add(T_V_BOOLEAN_TRUE);
		} else {
			args.add(T_V_BOOLEAN_FALSE);
		}
	}

	static void makeQueryPropertyInteger(IFilter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_INTEGER_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_INTEGER_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_INTEGER_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_INTEGER_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_INTEGER_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(name);
		args.add(String.valueOf(obj));
	}

	static void makeQueryPropertyReal(IFilter filter, StringBuilder builder, List<String> args) {
		String name = filter.getName();
		Object obj = filter.getValue();
		switch (filter.getOption()) {
			case EQ:
				builder.append(SQL_PROPERTY_REAL_EQ);
				break;
			case GT:
				builder.append(SQL_PROPERTY_REAL_GT);
				break;
			case GT_EQ:
				builder.append(SQL_PROPERTY_REAL_GT_EQ);
				break;
			case LT:
				builder.append(SQL_PROPERTY_REAL_LT);
				break;
			case LT_EQ:
				builder.append(SQL_PROPERTY_REAL_LT_EQ);
				break;
			default:
				throw new IllegalArgumentException();
		}
		args.add(name);
		args.add(String.valueOf(obj));
	}
}
