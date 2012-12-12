package net.vvakame.blaz.sqlite;

import java.util.List;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.filter.AbstractKeyFilter;
import net.vvakame.blaz.filter.AbstractPropertyFilter;
import net.vvakame.blaz.filter.KeyEqFilter;
import net.vvakame.blaz.filter.KeyGtEqFilter;
import net.vvakame.blaz.filter.KeyGtFilter;
import net.vvakame.blaz.filter.KeyInFilter;
import net.vvakame.blaz.filter.KeyLtEqFilter;
import net.vvakame.blaz.filter.KeyLtFilter;
import net.vvakame.blaz.filter.KindEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanGtEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanGtFilter;
import net.vvakame.blaz.filter.PropertyBooleanInFilter;
import net.vvakame.blaz.filter.PropertyBooleanLtEqFilter;
import net.vvakame.blaz.filter.PropertyBooleanLtFilter;
import net.vvakame.blaz.filter.PropertyIntegerEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerGtEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerGtFilter;
import net.vvakame.blaz.filter.PropertyIntegerInFilter;
import net.vvakame.blaz.filter.PropertyIntegerLtEqFilter;
import net.vvakame.blaz.filter.PropertyIntegerLtFilter;
import net.vvakame.blaz.filter.PropertyKeyEqFilter;
import net.vvakame.blaz.filter.PropertyKeyGtEqFilter;
import net.vvakame.blaz.filter.PropertyKeyGtFilter;
import net.vvakame.blaz.filter.PropertyKeyInFilter;
import net.vvakame.blaz.filter.PropertyKeyLtEqFilter;
import net.vvakame.blaz.filter.PropertyKeyLtFilter;
import net.vvakame.blaz.filter.PropertyNullEqFilter;
import net.vvakame.blaz.filter.PropertyRealEqFilter;
import net.vvakame.blaz.filter.PropertyRealGtEqFilter;
import net.vvakame.blaz.filter.PropertyRealGtFilter;
import net.vvakame.blaz.filter.PropertyRealInFilter;
import net.vvakame.blaz.filter.PropertyRealLtEqFilter;
import net.vvakame.blaz.filter.PropertyRealLtFilter;
import net.vvakame.blaz.filter.PropertyStringEqFilter;
import net.vvakame.blaz.filter.PropertyStringGtEqFilter;
import net.vvakame.blaz.filter.PropertyStringGtFilter;
import net.vvakame.blaz.filter.PropertyStringInFilter;
import net.vvakame.blaz.filter.PropertyStringLtEqFilter;
import net.vvakame.blaz.filter.PropertyStringLtFilter;
import net.vvakame.blaz.util.KeyUtil;
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

	static final String SQL_KEY_IN = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS + " WHERE "
			+ COL_KEY_STRING + " IN (";

	static final String SQL_KIND = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_KEYS + " WHERE "
			+ COL_KIND + " = ?";

	static final String SQL_PROPERTY_STR_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " = ?";

	static final String SQL_PROPERTY_STR_GT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " > ?";

	static final String SQL_PROPERTY_STR_GT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ? AND "
			+ COL_VALUE_STRING + " >= ?";

	static final String SQL_PROPERTY_STR_LT = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " < ?";

	static final String SQL_PROPERTY_STR_LT_EQ = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ? AND "
			+ COL_VALUE_STRING + " <= ?";

	static final String SQL_PROPERTY_STR_IN = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ? AND " + COL_VALUE_STRING
			+ " IN (";

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

	static final String SQL_PROPERTY_INTEGER_IN = "SELECT " + COL_KEY_STRING + " FROM "
			+ TABLE_VALUES + " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_INTEGER + " IN (";

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

	static final String SQL_PROPERTY_REAL_IN = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_NAME + " = ? AND " + COL_VALUE_REAL + " IN (";

	static final String SQL_PROPERTY_NULL_EQ = "SELECT " + COL_KEY_STRING + " FROM " + TABLE_VALUES
			+ " WHERE " + COL_TYPE + " IN (?, ?) AND " + COL_NAME + " = ?";


	public static void makeGetAllQuery(StringBuilder builder, List<String> args) {
		builder.append(SQL_ALL);
	}

	public static void makeQuery(Filter filter, StringBuilder builder, List<String> args) {
		if (filter instanceof KindEqFilter) {
			makeQueryKindEq(filter, builder, args);

		} else if (filter instanceof AbstractKeyFilter) {
			if (filter instanceof KeyEqFilter) {
				makeQueryKeyEq(filter, builder, args);

			} else if (filter instanceof KeyGtFilter) {
				makeQueryKeyGt(filter, builder, args);

			} else if (filter instanceof KeyGtEqFilter) {
				makeQueryKeyGtEq(filter, builder, args);

			} else if (filter instanceof KeyLtFilter) {
				makeQueryKeyLt(filter, builder, args);

			} else if (filter instanceof KeyLtEqFilter) {
				makeQueryKeyLtEq(filter, builder, args);

			} else if (filter instanceof KeyInFilter) {
				makeQueryKeyIn(filter, builder, args);

			} else {
				throw new IllegalArgumentException("unknown filter");
			}
		} else if (filter instanceof AbstractPropertyFilter) {
			if (filter instanceof PropertyBooleanEqFilter) {
				makeQueryPropertyBooleanEq(filter, builder, args);

			} else if (filter instanceof PropertyBooleanGtFilter) {
				makeQueryPropertyBooleanGt(filter, builder, args);

			} else if (filter instanceof PropertyBooleanGtEqFilter) {
				makeQueryPropertyBooleanGtEq(filter, builder, args);

			} else if (filter instanceof PropertyBooleanLtFilter) {
				makeQueryPropertyBooleanLt(filter, builder, args);

			} else if (filter instanceof PropertyBooleanLtEqFilter) {
				makeQueryPropertyBooleanLtEq(filter, builder, args);

			} else if (filter instanceof PropertyBooleanInFilter) {
				makeQueryPropertyBooleanIn(filter, builder, args);

			} else if (filter instanceof PropertyIntegerEqFilter) {
				makeQueryPropertyIntegerEq(filter, builder, args);

			} else if (filter instanceof PropertyIntegerGtFilter) {
				makeQueryPropertyIntegerGt(filter, builder, args);

			} else if (filter instanceof PropertyIntegerGtEqFilter) {
				makeQueryPropertyIntegerGtEq(filter, builder, args);

			} else if (filter instanceof PropertyIntegerLtFilter) {
				makeQueryPropertyIntegerLt(filter, builder, args);

			} else if (filter instanceof PropertyIntegerLtEqFilter) {
				makeQueryPropertyIntegerLtEq(filter, builder, args);

			} else if (filter instanceof PropertyIntegerInFilter) {
				makeQueryPropertyIntegerIn(filter, builder, args);

			} else if (filter instanceof PropertyRealEqFilter) {
				makeQueryPropertyRealEq(filter, builder, args);

			} else if (filter instanceof PropertyRealGtFilter) {
				makeQueryPropertyRealGt(filter, builder, args);

			} else if (filter instanceof PropertyRealGtEqFilter) {
				makeQueryPropertyRealGtEq(filter, builder, args);

			} else if (filter instanceof PropertyRealLtFilter) {
				makeQueryPropertyRealLt(filter, builder, args);

			} else if (filter instanceof PropertyRealLtEqFilter) {
				makeQueryPropertyRealLtEq(filter, builder, args);

			} else if (filter instanceof PropertyRealInFilter) {
				makeQueryPropertyRealIn(filter, builder, args);

			} else if (filter instanceof PropertyStringEqFilter) {
				makeQueryPropertyStringEq(filter, builder, args);

			} else if (filter instanceof PropertyStringGtFilter) {
				makeQueryPropertyStringGt(filter, builder, args);

			} else if (filter instanceof PropertyStringGtEqFilter) {
				makeQueryPropertyStringGtEq(filter, builder, args);

			} else if (filter instanceof PropertyStringLtFilter) {
				makeQueryPropertyStringLt(filter, builder, args);

			} else if (filter instanceof PropertyStringLtEqFilter) {
				makeQueryPropertyStringLtEq(filter, builder, args);

			} else if (filter instanceof PropertyStringInFilter) {
				makeQueryPropertyStringIn(filter, builder, args);

			} else if (filter instanceof PropertyKeyEqFilter) {
				makeQueryPropertyKeyEq(filter, builder, args);

			} else if (filter instanceof PropertyKeyGtFilter) {
				makeQueryPropertyKeyGt(filter, builder, args);

			} else if (filter instanceof PropertyKeyGtEqFilter) {
				makeQueryPropertyKeyGtEq(filter, builder, args);

			} else if (filter instanceof PropertyKeyLtFilter) {
				makeQueryPropertyKeyLt(filter, builder, args);

			} else if (filter instanceof PropertyKeyLtEqFilter) {
				makeQueryPropertyKeyLtEq(filter, builder, args);

			} else if (filter instanceof PropertyKeyInFilter) {
				makeQueryPropertyKeyIn(filter, builder, args);

			} else if (filter instanceof PropertyNullEqFilter) {
				makeQueryPropertyNullEq(filter, builder, args);

			} else {
				throw new IllegalArgumentException("unknown filter");
			}
		} else {
			throw new IllegalArgumentException("unknown filter");
		}
	}

	static void makeQueryKindEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_KIND);
		args.add(filter.getName());
	}

	static void makeQueryKeyGtEq(Filter filter, StringBuilder builder, List<String> args) {
		Key key = ((KeyGtEqFilter) filter).getValue();
		args.add(key.getKind());
		if (key.getName() == null) {
			builder.append(SQL_KEY_ID_GT_EQ);
			args.add(String.valueOf(key.getId()));
		} else {
			builder.append(SQL_KEY_NAME_GT_EQ);
			args.add(key.getName());
		}
	}

	static void makeQueryKeyGt(Filter filter, StringBuilder builder, List<String> args) {
		Key key = ((KeyGtFilter) filter).getValue();
		args.add(key.getKind());
		if (key.getName() == null) {
			builder.append(SQL_KEY_ID_GT);
			args.add(String.valueOf(key.getId()));
		} else {
			builder.append(SQL_KEY_NAME_GT);
			args.add(key.getName());
		}
	}

	static void makeQueryKeyLt(Filter filter, StringBuilder builder, List<String> args) {
		Key key = ((KeyLtFilter) filter).getValue();
		args.add(key.getKind());
		if (key.getName() == null) {
			builder.append(SQL_KEY_ID_LT);
			args.add(String.valueOf(key.getId()));
		} else {
			builder.append(SQL_KEY_NAME_LT);
			args.add(key.getName());
		}
	}

	static void makeQueryKeyLtEq(Filter filter, StringBuilder builder, List<String> args) {
		Key key = ((KeyLtEqFilter) filter).getValue();
		args.add(key.getKind());
		if (key.getName() == null) {
			builder.append(SQL_KEY_ID_LT_EQ);
			args.add(String.valueOf(key.getId()));
		} else {
			builder.append(SQL_KEY_NAME_LT_EQ);
			args.add(key.getName());
		}
	}

	static void makeQueryKeyIn(Filter filter, StringBuilder builder, List<String> args) {
		Key[] keys = ((KeyInFilter) filter).getValue();

		builder.append(SQL_KEY_IN);
		addInSql(builder, keys.length);

		for (Key key : keys) {
			args.add(KeyUtil.keyToString(key));
		}
	}

	static void makeQueryKeyEq(Filter filter, StringBuilder builder, List<String> args) {
		Key key = ((KeyEqFilter) filter).getValue();
		args.add(key.getKind());
		if (key.getName() == null) {
			builder.append(SQL_KEY_ID_EQ);
			args.add(String.valueOf(key.getId()));
		} else {
			builder.append(SQL_KEY_NAME_EQ);
			args.add(key.getName());
		}
	}

	static void makeQueryPropertyBooleanEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_EQ);

		args.add(T_BOOLEAN);
		args.add(T_L_BOOLEAN);
		args.add(filter.getName());
		if (((PropertyBooleanEqFilter) filter).getValue()) {
			args.add(T_V_BOOLEAN_TRUE);
		} else {
			args.add(T_V_BOOLEAN_FALSE);
		}
	}

	static void makeQueryPropertyBooleanGt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_GT);

		args.add(T_BOOLEAN);
		args.add(T_L_BOOLEAN);
		args.add(filter.getName());
		if (((PropertyBooleanGtFilter) filter).getValue()) {
			args.add(T_V_BOOLEAN_TRUE);
		} else {
			args.add(T_V_BOOLEAN_FALSE);
		}
	}

	static void makeQueryPropertyBooleanGtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_GT_EQ);

		args.add(T_BOOLEAN);
		args.add(T_L_BOOLEAN);
		args.add(filter.getName());
		if (((PropertyBooleanGtEqFilter) filter).getValue()) {
			args.add(T_V_BOOLEAN_TRUE);
		} else {
			args.add(T_V_BOOLEAN_FALSE);
		}
	}

	static void makeQueryPropertyBooleanLt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_LT);

		args.add(T_BOOLEAN);
		args.add(T_L_BOOLEAN);
		args.add(filter.getName());
		if (((PropertyBooleanLtFilter) filter).getValue()) {
			args.add(T_V_BOOLEAN_TRUE);
		} else {
			args.add(T_V_BOOLEAN_FALSE);
		}
	}

	static void makeQueryPropertyBooleanLtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_LT_EQ);

		args.add(T_BOOLEAN);
		args.add(T_L_BOOLEAN);
		args.add(filter.getName());
		if (((PropertyBooleanLtEqFilter) filter).getValue()) {
			args.add(T_V_BOOLEAN_TRUE);
		} else {
			args.add(T_V_BOOLEAN_FALSE);
		}
	}

	static void makeQueryPropertyBooleanIn(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_IN);

		args.add(T_BOOLEAN);
		args.add(T_L_BOOLEAN);
		args.add(filter.getName());

		Boolean[] values = ((PropertyBooleanInFilter) filter).getValue();

		for (boolean value : values) {
			if (value) {
				args.add(T_V_BOOLEAN_TRUE);
			} else {
				args.add(T_V_BOOLEAN_FALSE);
			}
		}
		addInSql(builder, values.length);
	}

	static void makeQueryPropertyIntegerEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_INTEGER_EQ);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyIntegerEqFilter) filter).getValue()));
	}

	static void makeQueryPropertyIntegerGt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_INTEGER_GT);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyIntegerGtFilter) filter).getValue()));
	}

	static void makeQueryPropertyIntegerGtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_INTEGER_GT_EQ);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyIntegerGtEqFilter) filter).getValue()));
	}

	static void makeQueryPropertyIntegerLt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_INTEGER_LT);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyIntegerLtFilter) filter).getValue()));
	}

	static void makeQueryPropertyIntegerLtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_INTEGER_LT_EQ);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyIntegerLtEqFilter) filter).getValue()));
	}

	static void makeQueryPropertyIntegerIn(Filter filter, StringBuilder builder, List<String> args) {
		Long[] values = ((PropertyIntegerInFilter) filter).getValue();

		builder.append(SQL_PROPERTY_INTEGER_IN);
		addInSql(builder, values.length);

		args.add(filter.getName());
		for (Long value : values) {
			args.add(String.valueOf(value));
		}
	}

	static void makeQueryPropertyRealEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_REAL_EQ);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyRealEqFilter) filter).getValue()));
	}

	static void makeQueryPropertyRealGt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_REAL_GT);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyRealGtFilter) filter).getValue()));
	}

	static void makeQueryPropertyRealGtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_REAL_GT_EQ);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyRealGtEqFilter) filter).getValue()));
	}

	static void makeQueryPropertyRealLt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_REAL_LT);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyRealLtFilter) filter).getValue()));
	}

	static void makeQueryPropertyRealLtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_REAL_LT_EQ);
		args.add(filter.getName());
		args.add(String.valueOf(((PropertyRealLtEqFilter) filter).getValue()));
	}

	static void makeQueryPropertyRealIn(Filter filter, StringBuilder builder, List<String> args) {
		Double[] values = ((PropertyRealInFilter) filter).getValue();

		builder.append(SQL_PROPERTY_REAL_IN);
		addInSql(builder, values.length);

		args.add(filter.getName());
		for (Double value : values) {
			args.add(String.valueOf(value));
		}
	}

	static void makeQueryPropertyStringEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_EQ);
		args.add(T_STRING);
		args.add(T_L_STRING);
		args.add(filter.getName());
		args.add(((PropertyStringEqFilter) filter).getValue());
	}

	static void makeQueryPropertyStringGt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_GT);
		args.add(T_STRING);
		args.add(T_L_STRING);
		args.add(filter.getName());
		args.add(((PropertyStringGtFilter) filter).getValue());
	}

	static void makeQueryPropertyStringGtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_GT_EQ);
		args.add(T_STRING);
		args.add(T_L_STRING);
		args.add(filter.getName());
		args.add(((PropertyStringGtEqFilter) filter).getValue());
	}

	static void makeQueryPropertyStringLt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_LT);
		args.add(T_STRING);
		args.add(T_L_STRING);
		args.add(filter.getName());
		args.add(((PropertyStringLtFilter) filter).getValue());
	}

	static void makeQueryPropertyStringLtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_LT_EQ);
		args.add(T_STRING);
		args.add(T_L_STRING);
		args.add(filter.getName());
		args.add(((PropertyStringLtEqFilter) filter).getValue());
	}

	static void makeQueryPropertyStringIn(Filter filter, StringBuilder builder, List<String> args) {
		String[] values = ((PropertyStringInFilter) filter).getValue();

		builder.append(SQL_PROPERTY_STR_IN);
		addInSql(builder, values.length);

		args.add(T_STRING);
		args.add(T_L_STRING);
		args.add(filter.getName());

		for (String value : values) {
			args.add(value);
		}
	}

	static void makeQueryPropertyKeyEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_EQ);
		args.add(T_KEY);
		args.add(T_L_KEY);
		Key key = ((PropertyKeyEqFilter) filter).getValue();
		args.add(filter.getName());
		args.add(KeyUtil.keyToString(key));
	}

	static void makeQueryPropertyKeyGt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_GT);
		args.add(T_KEY);
		args.add(T_L_KEY);
		Key key = ((PropertyKeyGtFilter) filter).getValue();
		args.add(filter.getName());
		args.add(KeyUtil.keyToString(key));
	}

	static void makeQueryPropertyKeyGtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_GT_EQ);
		args.add(T_KEY);
		args.add(T_L_KEY);
		Key key = ((PropertyKeyGtEqFilter) filter).getValue();
		args.add(filter.getName());
		args.add(KeyUtil.keyToString(key));
	}

	static void makeQueryPropertyKeyLt(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_LT);
		args.add(T_KEY);
		args.add(T_L_KEY);
		Key key = ((PropertyKeyLtFilter) filter).getValue();
		args.add(filter.getName());
		args.add(KeyUtil.keyToString(key));
	}

	static void makeQueryPropertyKeyLtEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_STR_LT_EQ);
		args.add(T_KEY);
		args.add(T_L_KEY);
		Key key = ((PropertyKeyLtEqFilter) filter).getValue();
		args.add(filter.getName());
		args.add(KeyUtil.keyToString(key));
	}

	static void makeQueryPropertyKeyIn(Filter filter, StringBuilder builder, List<String> args) {
		Key[] values = ((PropertyKeyInFilter) filter).getValue();

		builder.append(SQL_PROPERTY_STR_IN);
		addInSql(builder, values.length);

		args.add(T_KEY);
		args.add(T_L_KEY);
		args.add(filter.getName());

		for (Key value : values) {
			args.add(KeyUtil.keyToString(value));
		}
	}

	static void makeQueryPropertyNullEq(Filter filter, StringBuilder builder, List<String> args) {
		builder.append(SQL_PROPERTY_NULL_EQ);
		args.add(T_NULL);
		args.add(T_L_NULL);
		args.add(filter.getName());
	}

	static void addInSql(StringBuilder builder, int itr) {
		for (int i = 0; i < itr; i++) {
			builder.append("?, ");
		}
		builder.setLength(builder.length() - 2);
		builder.append(")");
	}
}
