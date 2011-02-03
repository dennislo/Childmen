package childmen.gae.aggregator.dao.impl;

import childmen.gae.aggregator.dao.LocationDAO;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyLocationDAO extends LocationDAO {

	private Objectify service;
	
	public ObjectifyLocationDAO() {
		// initialisation
		if(service == null) 
		{
			service = ObjectifyService.begin();
		}
	}
	
	/*Add Location specific DAO methods below that are non generic*/
}
