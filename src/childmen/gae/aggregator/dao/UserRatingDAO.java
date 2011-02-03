package childmen.gae.aggregator.dao;

import childmen.gae.aggregator.dao.impl.ObjectifyGenericDAO;
import childmen.gae.aggregator.model.UserRating;

public class UserRatingDAO extends ObjectifyGenericDAO<UserRating>
{		
			
	public UserRatingDAO()
	{
		super(UserRating.class);			
	}
	
	/*Add User Rating specific DAO methods below that are non generic*/

}
