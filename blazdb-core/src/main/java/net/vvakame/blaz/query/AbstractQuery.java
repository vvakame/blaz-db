package net.vvakame.blaz.query;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.option.FetchOptions;

public abstract class AbstractQuery<SUB> {

	protected List<Filter> filters = new ArrayList<Filter>();

	protected List<Sorter> sorters = new ArrayList<Sorter>();

	protected FetchOptions options = new FetchOptions();

	@SuppressWarnings("unchecked")
	public SUB limit(int limit) {
		options.limit(limit);
		return (SUB) this;
	}

	@SuppressWarnings("unchecked")
	public SUB offset(int offset) {
		options.offset(offset);
		return (SUB) this;
	}
}
