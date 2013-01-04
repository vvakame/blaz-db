package net.vvakame.blaz.meta;

import java.util.Collection;

public interface CollectionAttributeMetaInterface<C extends Collection<T>, T> {

	/**
	 * @return the collectionClass
	 * @category accessor
	 */
	public Class<C> getCollectionClass();

	/**
	 * @return the typeParameterClass
	 * @category accessor
	 */
	public Class<?> getTypeParameterClass();

	public String getName();
}
