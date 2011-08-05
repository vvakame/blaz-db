package net.vvakame.blaz.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Filter.FilterOption;
import net.vvakame.blaz.Key;
import net.vvakame.blaz.Transaction;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.filter.KeyFilter;
import net.vvakame.blaz.filter.KindFilter;
import net.vvakame.blaz.filter.PropertyFilter;
import net.vvakame.blaz.util.FilterChecker;
import net.vvakame.blaz.util.KeyUtil;

/**
 * {@link BareDatastore} のモック実装.<br>
 * {@link Serializable} を活用すれば永続化可能.
 * @author vvakame
 */
public class MockKVS extends BareDatastore {

	Map<String, Map<Key, Entity>> db = new HashMap<String, Map<Key, Entity>>();


	Map<Key, Entity> getKindMap(String kind) {
		return getKindMap(db, kind);
	}

	Map<Key, Entity> getKindMap(Key key) {
		return getKindMap(db, key.getKind());
	}

	Map<Key, Entity> getKindMap(Entity entity) {
		return getKindMap(db, entity.getKind());
	}

	Map<Key, Entity> getKindMap(Map<String, Map<Key, Entity>> db, String kind) {
		Map<Key, Entity> resultMap = db.get(kind);
		if (resultMap == null) {
			resultMap = new HashMap<Key, Entity>();
			db.put(kind, resultMap);
		}
		return resultMap;
	}

	Map<Key, Entity> getKindMap(Map<String, Map<Key, Entity>> db, Key key) {
		return getKindMap(db, key.getKind());
	}

	Map<Key, Entity> getKindMap(Map<String, Map<Key, Entity>> db, Entity entity) {
		return getKindMap(db, entity.getKind());
	}

	long getKindMaxId(String kind) {
		Map<Key, Entity> kindMap = getKindMap(kind);
		long max = 0;
		for (Key key : kindMap.keySet()) {
			max = Math.max(max, key.getId());
		}
		return max;
	}

	@Override
	public Entity getOrNull(Key key) {
		return getKindMap(key).get(key);
	}

	@Override
	public void put(Entity entity) throws NullPointerException {
		// primitive convert
		// whole number -> long
		// real number -> double
		for (String name : entity.getProperties().keySet()) {
			Object value = entity.getProperty(name);
			if (value instanceof Byte) {
				value = (long) (Byte) value;
			} else if (value instanceof Short) {
				value = (long) (Short) value;
			} else if (value instanceof Integer) {
				value = (long) (Integer) value;
			} else if (value instanceof Float) {
				value = (double) (Float) value;
			} else if (value instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
				for (int i = 0; i < list.size(); i++) {
					Object listValue = list.get(i);
					if (listValue instanceof Byte) {
						listValue = (long) (Byte) listValue;
					} else if (listValue instanceof Short) {
						listValue = (long) (Short) listValue;
					} else if (listValue instanceof Integer) {
						listValue = (long) (Integer) listValue;
					} else if (listValue instanceof Float) {
						listValue = (double) (Float) listValue;
					}
					list.set(i, listValue);
				}
			}
			entity.setProperty(name, value);
		}

		if (entity.getKey() == null || entity.getKey().getName() == null
				&& entity.getKey().getId() == 0) {

			String kind = entity.getKind();
			long id = getKindMaxId(kind) + 1;
			Key key = KeyUtil.createKey(kind, id);
			entity.setKey(key);
		}

		getKindMap(entity).put(entity.getKey(), entity);
	}

	@Override
	public void delete(Key key) {
		getKindMap(key).remove(key);
	}

	@Override
	public List<Key> findAsKey(Filter... filters) {
		if (checkFilter && !FilterChecker.check(this, filters)) {
			throw new IllegalArgumentException("invalid filter combination.");
		}

		Map<String, Map<Key, Entity>> workingMap = db;
		for (Filter filter : filters) {
			if (filter instanceof KindFilter) {
				KindFilter kindFilter = (KindFilter) filter;
				workingMap = getDbByKind(workingMap, kindFilter.getName());
			} else if (filter instanceof KeyFilter) {
				KeyFilter keyFilter = (KeyFilter) filter;

				Key key = (Key) keyFilter.getValue();
				CompareBlock cmp = getCompareBlock(keyFilter.getOption());

				workingMap = getKindMapByKey(workingMap, cmp, key);
			} else if (filter instanceof PropertyFilter) {
				PropertyFilter propertyFilter = (PropertyFilter) filter;

				String name = propertyFilter.getName();
				Object value = propertyFilter.getValue();
				CompareBlock cmp = getCompareBlock(propertyFilter.getOption());

				workingMap = getKindMapByProperty(workingMap, cmp, name, value);
			}
		}

		return dbToKeyList(workingMap);
	}

	CompareBlock getCompareBlock(FilterOption option) {
		CompareBlock cmp;
		switch (option) {
			case EQ:
				cmp = CMP_EQ;
				break;
			case GT:
				cmp = CMP_GT;
				break;
			case GT_EQ:
				cmp = CMP_GT_EQ;
				break;
			case LT:
				cmp = CMP_LT;
				break;
			case LT_EQ:
				cmp = CMP_LT_EQ;
				break;
			default:
				throw new IllegalArgumentException();
		}
		return cmp;
	}

	Map<String, Map<Key, Entity>> getDbByKind(Map<String, Map<Key, Entity>> workingMap, String kind) {
		Map<String, Map<Key, Entity>> tmpMap = new HashMap<String, Map<Key, Entity>>();
		tmpMap.put(kind, workingMap.get(kind));
		return tmpMap;
	}

	Map<String, Map<Key, Entity>> getKindMapByKey(Map<String, Map<Key, Entity>> workingMap,
			CompareBlock cmp, Key key) {

		Map<Key, Entity> kindMap = workingMap.get(key.getKind());

		Map<Key, Entity> resultMap = new HashMap<Key, Entity>();
		for (Key cmpKey : kindMap.keySet()) {
			if (key.getName() == null && cmpKey.getName() != null) {
				continue;
			} else if (key.getName() != null && cmpKey.getName() == null) {
				continue;
			} else if (cmp.isComparePassage(key, cmpKey)) {
				resultMap.put(cmpKey, kindMap.get(cmpKey));
			}
		}

		Map<String, Map<Key, Entity>> newWorkingMap = new HashMap<String, Map<Key, Entity>>();
		newWorkingMap.put(key.getKind(), resultMap);
		return newWorkingMap;
	}

	@SuppressWarnings("unchecked")
	Map<String, Map<Key, Entity>> getKindMapByProperty(Map<String, Map<Key, Entity>> workingMap,
			CompareBlock cmp, String name, Object value) {

		Map<String, Map<Key, Entity>> newWorkingMap = new HashMap<String, Map<Key, Entity>>();

		for (String kind : workingMap.keySet()) {
			Map<Key, Entity> kindMap = getKindMap(workingMap, kind);

			Map<Key, Entity> resultMap = new HashMap<Key, Entity>();

			for (Key key : kindMap.keySet()) {
				Entity entity = kindMap.get(key);
				Object tmpValue = entity.getProperty(name);
				if (tmpValue instanceof Collection) {
					for (Object tmpValue2 : (Collection<Object>) tmpValue) {
						if (cmp.isComparePassage(value, tmpValue2)) {
							resultMap.put(key, entity);
						}
					}
				} else {
					if (cmp.isComparePassage(value, tmpValue)) {
						resultMap.put(key, entity);
					}
				}
			}
			newWorkingMap.put(kind, resultMap);
		}

		return newWorkingMap;
	}

	List<Key> dbToKeyList(Map<String, Map<Key, Entity>> workingMap) {
		List<Key> keyList = new ArrayList<Key>();
		for (Map<Key, Entity> kindMap : workingMap.values()) {
			for (Key key : kindMap.keySet()) {
				keyList.add(key);
			}
		}
		return keyList;
	}

	@Override
	public Transaction beginTransaction() {
		return new MockTransaction(this);
	}

	@Override
	public boolean checkFilter(Filter... filters) {
		return true;
	}


	interface CompareBlock {

		public boolean isComparePassage(Object obj1, Object obj2);
	}


	final CompareBlock CMP_EQ = new CompareBlock() {

		@SuppressWarnings({
			"cast",
			"unchecked",
			"rawtypes"
		})
		@Override
		public boolean isComparePassage(Object obj1, Object obj2) {
			return ((Comparable) obj1).compareTo((Comparable) obj2) == 0;
		}
	};

	final CompareBlock CMP_GT = new CompareBlock() {

		@SuppressWarnings({
			"cast",
			"unchecked",
			"rawtypes"
		})
		@Override
		public boolean isComparePassage(Object obj1, Object obj2) {
			return ((Comparable) obj1).compareTo((Comparable) obj2) < 0;
		}
	};

	final CompareBlock CMP_GT_EQ = new CompareBlock() {

		@SuppressWarnings({
			"cast",
			"unchecked",
			"rawtypes"
		})
		@Override
		public boolean isComparePassage(Object obj1, Object obj2) {
			return ((Comparable) obj1).compareTo((Comparable) obj2) <= 0;
		}
	};

	final CompareBlock CMP_LT = new CompareBlock() {

		@SuppressWarnings({
			"cast",
			"unchecked",
			"rawtypes"
		})
		@Override
		public boolean isComparePassage(Object obj1, Object obj2) {
			return ((Comparable) obj1).compareTo((Comparable) obj2) > 0;
		}
	};

	final CompareBlock CMP_LT_EQ = new CompareBlock() {

		@SuppressWarnings({
			"cast",
			"unchecked",
			"rawtypes"
		})
		@Override
		public boolean isComparePassage(Object obj1, Object obj2) {
			return ((Comparable) obj1).compareTo((Comparable) obj2) >= 0;
		}
	};
}
