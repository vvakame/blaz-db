package net.vvakame.blaz.bare;

import java.util.List;

import net.vvakame.blaz.Datastore;
import net.vvakame.blaz.meta.ModelMeta;

/**
 * Hook point for {@link BareDatastore}.
 * @author vvakame
 */
public interface DatastoreHook {

	/**
	 * If {@link BareDatastore} post to {@link Datastore#setupDatastore(BareDatastore)}, do setup.
	 * @param metas All of meta classes
	 * @author vvakame
	 */
	public void onSetup(List<ModelMeta<?>> metas);
}
