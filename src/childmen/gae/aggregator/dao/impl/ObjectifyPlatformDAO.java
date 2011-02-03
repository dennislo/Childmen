package childmen.gae.aggregator.dao.impl;

import childmen.gae.aggregator.dao.PlatformDAO;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyPlatformDAO extends PlatformDAO {
	
	private Objectify service;
	
	public ObjectifyPlatformDAO() {
		// initialisation
		if(service == null) 
		{
			service = ObjectifyService.begin();
		}
				
	}
	
	/*Add Platform specific DAO methods below that are non generic*/

}
