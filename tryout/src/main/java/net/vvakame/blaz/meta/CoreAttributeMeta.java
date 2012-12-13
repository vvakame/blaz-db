package net.vvakame.blaz.meta;

/**
 * {@link ModelMeta} が保持するなんらかの値を表す抽象クラス.
 * @author vvakame
 * @param <T>
 */
public abstract class CoreAttributeMeta<T> {

	enum Type {
		KIND, KEY, PROPERTY
	}


	/** {@link ModelQuery#sort(SortCriterion...)} に渡す昇順ソート指示子 */
	public final SortCriterion asc;

	/** {@link ModelQuery#sort(SortCriterion...)} に渡す降順ソート指示子 */
	public final SortCriterion desc;


	/**
	 * the constructor.
	 * @param asc {@link SortCriterion} for {@link #asc}
	 * @param desc {@link SortCriterion} for {@link #desc}
	 * @category constructor
	 */
	public CoreAttributeMeta(AscSorterCriterion asc, DescSorterCriterion desc) {
		this.asc = asc;
		this.desc = desc;
	}

	protected abstract String getName();

	abstract Type getType();

	/**
	 * {@link ModelQuery#filter(FilterCriterion...)} に渡す == 検索指示子の組み立て.
	 * @param value 検索に利用する値
	 * @return {@link FilterCriterion}
	 * @author vvakame
	 */
	public abstract FilterCriterion equal(T value);

	/**
	 * {@link ModelQuery#filter(FilterCriterion...)} に渡す <= 検索指示子の組み立て.
	 * @param value 検索に利用する値
	 * @return {@link FilterCriterion}
	 * @author vvakame
	 */
	public abstract FilterCriterion lessThan(T value);

	/**
	 * {@link ModelQuery#filter(FilterCriterion...)} に渡す < 検索指示子の組み立て.
	 * @param value 検索に利用する値
	 * @return {@link FilterCriterion}
	 * @author vvakame
	 */
	public abstract FilterCriterion lessThanOrEqual(T value);

	/**
	 * {@link ModelQuery#filter(FilterCriterion...)} に渡す >= 検索指示子の組み立て.
	 * @param value 検索に利用する値
	 * @return {@link FilterCriterion}
	 * @author vvakame
	 */
	public abstract FilterCriterion greaterThan(T value);

	/**
	 * {@link ModelQuery#filter(FilterCriterion...)} に渡す > 検索指示子の組み立て.
	 * @param value 検索に利用する値
	 * @return {@link FilterCriterion}
	 * @author vvakame
	 */
	public abstract FilterCriterion greaterThanOrEqual(T value);

	/**
	 * {@link ModelQuery#filter(FilterCriterion...)} に渡す IN 検索指示子の組み立て.
	 * @param values 検索に利用する値
	 * @return {@link FilterCriterion}
	 * @author vvakame
	 */
	public abstract FilterCriterion in(T... values);
}
