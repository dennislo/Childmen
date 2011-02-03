package childmen.gae.aggregator.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import childmen.gae.aggregator.dao.UserRatingDAO;

/**
 * User Rating DAO implementation for Objectify 
 * @author Dennis
 */
public class ObjectifyUserRatingDAO extends UserRatingDAO {

	private Objectify service;
	
	public ObjectifyUserRatingDAO() {
		// initialisation
		if(service == null) 
		{
			service = ObjectifyService.begin();
		}
	}
	
	/*Add User Rating specific DAO methods below that are non generic*/
}
