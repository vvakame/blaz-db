package net.vvakame.blaz.filter;

import java.util.Arrays;
import java.util.List;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Key;

/**
 * Entityを検索するためのフィルタ
 * @author vvakame
 */
public class PropertyFilter implements Filter {

	final static FilterTarget target = FilterTarget.PROPERTY;

	String name;

	FilterOption option;

	Object value;


	/**
	 * the constructor.
	 * @param name 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, String value) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		} else if (value == null) {
			throw new IllegalArgumentException("value is required.");
		}

		this.option = option;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, boolean value) {
		if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = FilterOption.EQ;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, boolean value) {
		if (option != FilterOption.EQ) {
			throw new IllegalArgumentException("FilterOption is must be EQ.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = option;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, long value) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = option;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, double value) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = option;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, Key value) {
		if (option == null) {
			throw new IllegalArgumentException("FilterOption is required.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = option;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 
	 * @param option
	 * @param value
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, List<Object> value) {
		if (option != FilterOption.IN) {
			throw new IllegalArgumentException("FilterOption is must be IN.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = option;
		this.name = name;
		this.value = value;
	}

	/**
	 * the constructor.
	 * @param name 	
	 * @param option
	 * @param values
	 * @category constructor
	 */
	public PropertyFilter(String name, FilterOption option, Object... values) {
		if (option != FilterOption.IN) {
			throw new IllegalArgumentException("FilterOption is must be IN.");
		} else if (name == null) {
			throw new IllegalArgumentException("name is required.");
		}

		this.option = option;
		this.name = name;
		this.value = Arrays.asList(values);
	}

	@Override
	public FilterTarget getTarget() {
		return target;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FilterOption getOption() {
		return option;
	}

	@Override
	public Object getValue() {
		return value;
	}
}
