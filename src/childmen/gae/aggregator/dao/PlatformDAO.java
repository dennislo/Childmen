package childmen.gae.aggregator.dao;

import childmen.gae.aggregator.dao.impl.ObjectifyGenericDAO;
import childmen.gae.aggregator.model.Platform;

public class PlatformDAO extends ObjectifyGenericDAO<Platform> {

	protected PlatformDAO() {
		super(Platform.class);
	}

	/*Add Platform specific DAO methods below that are non generic*/
}
