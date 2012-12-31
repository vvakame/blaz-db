package net.vvakame.blaz.meta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.PropertyConverter;

/**
 * {@link ModelMeta} が保持するプロパティを表すクラス.
 * 
 * @author vvakame
 * @param <T>
 * @param <P>
 */
public class ConverterAttributeMeta<T, R> extends CoreAttributeMeta<T> {

	final String name;

	final Class<T> fromClazz;

	final Class<R> toClazz;

	final PropertyConverter<T, R> converter;

	/**
	 * the constructor.
	 * 
	 * @param name
	 * @param fromClazz
	 * @param toClazz
	 * @param converter
	 * @category constructor
	 */
	@SuppressWarnings("unchecked")
	public ConverterAttributeMeta(String name, Class<?> fromClazz,
			Class<?> toClazz, PropertyConverter<T, R> converter) {
		super(new AscSorterCriterion(Type.PROPERTY, name),
				new DescSorterCriterion(Type.PROPERTY, name));
		this.name = name;
		this.fromClazz = (Class<T>) fromClazz;
		this.toClazz = (Class<R>) toClazz;
		this.converter = converter;
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
	@SuppressWarnings("unchecked")
	public FilterCriterion equal(T value) {
		return new EqualCriterion<R>((CoreAttributeMeta<R>) this,
				converter.serialize(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public FilterCriterion lessThan(T value) {
		return new LessThanCriterion<R>((CoreAttributeMeta<R>) this,
				converter.serialize(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public FilterCriterion lessThanOrEqual(T value) {
		return new LessThanOrEqualCriterion<R>((CoreAttributeMeta<R>) this,
				converter.serialize(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public FilterCriterion greaterThan(T value) {
		return new GreaterThanCriterion<R>((CoreAttributeMeta<R>) this,
				converter.serialize(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public FilterCriterion greaterThanOrEqual(T value) {
		return new GreaterThanOrEqualCriterion<R>((CoreAttributeMeta<R>) this,
				converter.serialize(value));
	}

	@Override
	@SuppressWarnings("unchecked")
	public FilterCriterion in(T... values) {
		List<R> list = new ArrayList<R>(values.length);
		for (T value : values) {
			list.add(converter.serialize(value));
		}
		R[] array = (R[]) Array.newInstance(toClazz, values.length);
		return new InCriterion<R>((CoreAttributeMeta<R>) this,
				list.toArray(array));
	}
}
