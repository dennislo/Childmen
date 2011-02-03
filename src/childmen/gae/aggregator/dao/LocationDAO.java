package childmen.gae.aggregator.dao;

import childmen.gae.aggregator.dao.impl.ObjectifyGenericDAO;
import childmen.gae.aggregator.model.Location;

public class LocationDAO extends ObjectifyGenericDAO<Location> {

	protected LocationDAO() {
		super(Location.class);
	}

	/*Add Location specific DAO methods below that are non generic*/
}
