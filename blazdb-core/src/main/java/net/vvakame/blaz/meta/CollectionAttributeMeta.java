package net.vvakame.blaz.meta;

import java.util.Collection;
import java.util.List;

import net.vvakame.blaz.Key;

/**
 * {@link ModelMeta} が保持するプロパティを表すクラス.
 * 
 * @author vvakame
 * @param <T>
 * @param <P>
 */
public class CollectionAttributeMeta<C extends Collection<T>, T> extends
		CoreAttributeMeta<T> {

	final String name;

	final Class<C> collectionClass;

	final Class<T> typeParameterClass;

	static {
		new CollectionAttributeMeta<List<Key>, Key>("", List.class, Key.class);
	}

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @param collectionClass
	 * @param typeParameterClass
	 * @category constructor
	 */
	@SuppressWarnings("unchecked")
	public CollectionAttributeMeta(String name, Class<?> collectionClass,
			Class<T> typeParameterClass) {
		super(new AscSorterCriterion(Type.PROPERTY, name),
				new DescSorterCriterion(Type.PROPERTY, name));
		this.name = name;
		this.collectionClass = (Class<C>) collectionClass;
		this.typeParameterClass = typeParameterClass;
	}

	/**
	 * @return the collectionClass
	 * @category accessor
	 */
	public Class<C> getCollectionClass() {
		return collectionClass;
	}

	/**
	 * @return the typeParameterClass
	 * @category accessor
	 */
	public Class<T> getTypeParameterClass() {
		return typeParameterClass;
	}

	@Override
	public Type getType() {
		return Type.PROPERTY;
	}

	@Override
	public String getName() {
		return name;
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
