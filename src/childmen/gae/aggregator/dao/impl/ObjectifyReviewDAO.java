package childmen.gae.aggregator.dao.impl;

import java.util.List;
import java.util.Map;

import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Review;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Review DAO implementation for Objectify 
 * @author Dennis
 */
public class ObjectifyReviewDAO extends ReviewDAO {

	private Objectify service;
	
	public ObjectifyReviewDAO() {
		//initialisation
		if(service == null) 
		{
			service = ObjectifyService.begin();
		}
	}
	
	/*Add Game specific DAO methods below that are non generic*/
	public List<Review> listReviewsByGame(Game g)
	{
		List<Review> retrievedReviews = (List<Review>) service.query(Review.class).filter("game", g).list();
		return retrievedReviews;
	}

	//TODO: remove
	@Override
	public List<Review> listReviewsByGameName(String name) {
		List<Review> retrievedReviews = (List<Review>) service.query(Review.class).filter("name", name).list(); 
		return retrievedReviews;
	}

	@Override
	public List<Review> getReviewsByQueryParams(Map<String, String[]> paramsMap) {		
		List<Review> list = listByQueryParams(paramsMap);
		return list;
		/*
		List<Review> list = null;
		
		int limit;
		List<Filter> filters = new ArrayList<Filter>();
		List<String> sortOrders = new ArrayList<String>();
						
		//Get filters and sortOrders from query params
		QueryParamsUtility.HandleDoubleQueryParamFilters(filters, paramsMap); 
		QueryParamsUtility.HandleDoubleQueryParamSortOrders(sortOrders, paramsMap);
		
		limit = QueryParamsUtility.GetLimit(paramsMap);
		
		QueryParamsUtility.GetFiltersFromQueryParams(filters, paramsMap); 				
		//Utility.GetSortOrdersFromQueryParams(sortOrders, paramsMap); 
				
		Query<Review> q = buildQuery(filters, sortOrders, limit); //calling generic method
		
		list = q.list();
		
		return list;
		*/
	}	
}

