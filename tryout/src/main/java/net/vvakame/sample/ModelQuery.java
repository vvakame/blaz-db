package net.vvakame.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.vvakame.blaz.Entity;
import net.vvakame.blaz.Filter;
import net.vvakame.blaz.Sorter;
import net.vvakame.blaz.bare.BareDatastore;
import net.vvakame.blaz.filter.KindFilter;

/**
 * <M> についてのクエリビルダ
 * @author vvakame
 * @param <M>
 */
public class ModelQuery<M> {

	ModelMeta<M> meta;

	List<Filter> filters = new ArrayList<Filter>();

	List<Sorter> sorters = new ArrayList<Sorter>();


	/**
	 * the constructor.
	 * @param meta 処理対象の {@link ModelMeta}
	 * @category constructor
	 */
	public ModelQuery(ModelMeta<M> meta) {
		this.meta = meta;
		filters.add(new KindFilter(meta.getKind()));
	}

	/**
	 * 検索条件の指定
	 * @param criterion
	 * @return this
	 * @author vvakame
	 */
	public ModelQuery<M> filter(FilterCriterion... criterion) {
		for (FilterCriterion criteria : criterion) {
			if (criteria == null) {
				// TODO ちゃんとしたメッセージ入れたい
				throw new NullPointerException();
			}
			filters.addAll(Arrays.asList(criteria.getFilters()));
		}
		return this;
	}

	/**
	 * ソート条件の指定
	 * @param criterion
	 * @return this
	 * @author vvakame
	 */
	public ModelQuery<M> sort(SortCriterion... criterion) {
		for (SortCriterion criteria : criterion) {
			if (criteria == null) {
				// TODO ちゃんとしたメッセージ入れたい
				throw new NullPointerException();
			}
			sorters.addAll(Arrays.asList(criteria.getSorters()));
		}
		return this;
	}

	/**
	 * 設定済みの条件に基づき検索を行い、モデルに組み立てて返す.
	 * @return 検索結果
	 * @author vvakame
	 */
	public List<M> asList() {
		final BareDatastore kvs = Datastore.kvs;
		List<Entity> entities =
				kvs.find(filters.toArray(new Filter[] {}), sorters.toArray(new Sorter[] {}));
		List<M> modelList = new ArrayList<M>();
		for (Entity entity : entities) {
			M model = meta.entityTomodel(entity);
			modelList.add(model);
		}

		return modelList;
	}
}
