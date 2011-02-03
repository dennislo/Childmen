package childmen.gae.aggregator.dao;

import childmen.gae.aggregator.dao.impl.ObjectifyDAOFactory;

/**  
 * Use this DAO Factory for two purposes: 1. Add different datastores supported. 2. Add different DAO exposed
 *  
 * @author Dennis 
 */
public abstract class DAOFactory {

	// List of datastores types supported by the factory
	public static final int OBJECTIFY = 1;
	public static final int MYSQL = 2;
	public static final int POSTGRESQL = 3;

	// There will be a method for each DAO that can be
	// created. The concrete factories will have to
	// implement these methods.	
	//grouping 1
	public abstract GameDAO getGameDAO();
	public abstract ReviewDAO getReviewDAO();
	public abstract UserRatingDAO getUserRatingDAO();
	
	//grouping 2
	public abstract CriticDAO getCriticDAO();
	public abstract LocationDAO getLocationDAO();
	
	//grouping 3 - includes GameDAO
	public abstract PlatformDAO getPlatformDAO();
	
	private static DAOFactory factory;
	public static DAOFactory getDAOFactory(int whichFactory) {

		switch (whichFactory) {
		case OBJECTIFY:
			if(factory == null) 
			{
				factory = new ObjectifyDAOFactory();
				return factory;
			}
			else 
			{
				return factory;
			}
		/*case MYSQL:
			return new MySqlDAOFactory();
		case POSTGRESQL:
			return new PostgreSqlDAOFactory();*/
		default:
			return null;
		}
	}

}
