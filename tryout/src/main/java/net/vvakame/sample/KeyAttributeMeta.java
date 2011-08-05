package net.vvakame.sample;

import net.vvakame.blaz.Key;

/**
 * {@link ModelMeta} が保持する {@link Key} を表すクラス.
 * @author vvakame
 */
public class KeyAttributeMeta extends CoreAttributeMeta<Key> {

	final String kind;


	/**
	 * the constructor.
	 * @param kind
	 * @category constructor
	 */
	public KeyAttributeMeta(String kind) {
		super(new AscSorterCriterion(Type.KEY), new DescSorterCriterion(Type.KEY));
		this.kind = kind;
	}

	@Override
	protected String getName() {
		return null;
	}

	@Override
	public Type getType() {
		return Type.KEY;
	}

	@Override
	public FilterCriterion equal(Key value) {
		return new EqualCriterion<Key>(this, value);
	}

	@Override
	public FilterCriterion lessThan(Key value) {
		return new LessThanCriterion<Key>(this, value);
	}

	@Override
	public FilterCriterion lessThanOrEqual(Key value) {
		return new LessThanOrEqualCriterion<Key>(this, value);
	}

	@Override
	public FilterCriterion greaterThan(Key value) {
		return new GreaterThanCriterion<Key>(this, value);
	}

	@Override
	public FilterCriterion greaterThanOrEqual(Key value) {
		return new GreaterThanOrEqualCriterion<Key>(this, value);
	}
}
