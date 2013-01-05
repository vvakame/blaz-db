package net.vvakame.blaz.query;

public abstract class AbstractQuery<M> {
	int limit;

	@SuppressWarnings("unchecked")
	public M limit(int limit) {
		this.limit = limit;
		return (M) this;
	}
}
