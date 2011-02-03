package childmen.gae.aggregator.dao.impl;

import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.LocationDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.dao.UserRatingDAO;

/**  
 * This is the Objectify concrete DAO Factory implementation
 * @author Dennis 
 */
public class ObjectifyDAOFactory extends DAOFactory {
	
	@Override
	public GameDAO getGameDAO() {
		return new ObjectifyGameDAO();
	}

	@Override
	public ReviewDAO getReviewDAO() {	
		return new ObjectifyReviewDAO();
	}

	@Override
	public UserRatingDAO getUserRatingDAO() {
		return new ObjectifyUserRatingDAO();
	}

	@Override
	public CriticDAO getCriticDAO() {
		return new ObjectifyCriticDAO();
	}

	@Override
	public LocationDAO getLocationDAO() {
		return new ObjectifyLocationDAO();
	}

	@Override
	public PlatformDAO getPlatformDAO() {
		return new ObjectifyPlatformDAO();
	}
	
}
