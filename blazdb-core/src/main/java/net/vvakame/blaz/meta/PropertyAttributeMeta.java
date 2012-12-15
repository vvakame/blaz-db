package net.vvakame.blaz.meta;

/**
 * {@link ModelMeta} が保持するプロパティを表すクラス.
 * @author vvakame
 * @param <T>
 */
public class PropertyAttributeMeta<T> extends CoreAttributeMeta<T> {

	final String name;

	final Class<T> propertyClass;


	/**
	 * the constructor.
	 * @param name
	 * @param clazz
	 * @category constructor
	 */
	public PropertyAttributeMeta(String name, Class<T> clazz) {
		// TODO clazz are for type generic

		super(new AscSorterCriterion(Type.PROPERTY, name), new DescSorterCriterion(Type.PROPERTY,
				name));
		this.name = name;
		this.propertyClass = clazz;
	}

	@Override
	public Type getType() {
		return Type.PROPERTY;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Get property type class.
	 * @return propety type
	 * @author vvakame
	 */
	public Class<T> getPropertyClass() {
		return propertyClass;
	}

	@Override
	public FilterCriterion equal(T value) {
		return new EqualCriterion<T>(this, value);
	}

	@Override
	public FilterCriterion lessThan(T value) {
		return new LessThanCriterion<T>(this, value);
	}

	@Override
	public FilterCriterion lessThanOrEqual(T value) {
		return new LessThanOrEqualCriterion<T>(this, value);
	}

	@Override
	public FilterCriterion greaterThan(T value) {
		return new GreaterThanCriterion<T>(this, value);
	}

	@Override
	public FilterCriterion greaterThanOrEqual(T value) {
		return new GreaterThanOrEqualCriterion<T>(this, value);
	}

	@Override
	public FilterCriterion in(T... values) {
		return new InCriterion<T>(this, values);
	}
}
