package childmen.gae.aggregator.dao.impl;

import java.util.List;
import java.util.Map;

import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Review;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Game DAO implementation for Objectify 
 * @author Dennis
 */
public class ObjectifyGameDAO extends GameDAO {
	Objectify service;
	
	public ObjectifyGameDAO() {
		// initialisation
		if(service == null) 
		{
			service = ObjectifyService.begin();
		}
	}
	
	/*Add Game specific DAO methods below that are non generic*/
	
	@Override
	public Long putGame(Game g)
	{
		//Store game
		service.put(g);
		
		//Fetch stored game back
		Game storedGame = null;
		storedGame = service.get(Game.class, g.getId());				
		
		return (storedGame != null)? storedGame.getId() : null;
	}
	
	@Override
	public Game getGameByName(String name)
	{
		Game g = null;
		g =   service.query(Game.class).filter("name", name).get();
		return g;
	}
	
	@Override
	public List<Game> getGamesByQueryParams(Map<String, String[]> paramsMap) {	
		List<Game> list = listByQueryParams(paramsMap);
		return list;	
		/*
		List<Game> list = null;
		
		int limit;	
		List<Filter> filters = new ArrayList<Filter>();
		List<String> sortOrders = new ArrayList<String>();
						
		//Get filters and sortOrders from query params
		QueryParamsUtility.HandleDoubleQueryParamFilters(filters, paramsMap); 
		QueryParamsUtility.HandleDoubleQueryParamSortOrders(sortOrders, paramsMap);
		
		limit = QueryParamsUtility.GetLimit(paramsMap);
		
		QueryParamsUtility.GetFiltersFromQueryParams(filters, paramsMap); 				
		//Utility.GetSortOrdersFromQueryParams(sortOrders, paramsMap); 
				
		Query<Game> q = buildQuery(filters, sortOrders, limit); //calling generic method
		
		list = q.list();
		
		return list;*/
	}
	
	@Override
	public List<Game> getGamesByPlatform(Map<String, String[]> paramsMap) {
		List<Game> list = listByQueryParams(paramsMap);
		return list;			
	}
	
	/*
	 * Setting relationships
	 */	
	/**
	 * Add one review to this Game
	 * @param	
	 */
	@Override
	public void addReview(Review review, Game game) {
		review.setGame(game);		
		service.put(review);
	}
	
	/**
	 * Add multiple reviews to this Game	
	 * @param reviews
	 * @param game
	 */
	@Override
	public void addReviews(List<Review> reviews, Game game) {
		for (Review review : reviews) {
			review.setGame(game);
		}
		service.put(reviews);
	}

	
	/*
	public List<Game> getGamesByQueryParamsBackup(Map<String, String[]> paramsMap) {
		
		int limit; //TODO: define qp in spreadsheet		
		List<Filter> filters = new ArrayList<Filter>();
		List<String> sortOrders = new ArrayList<String>();
						
		//Get filters and sortOrders from query params
		QueryParamsUtility.HandleDoubleQueryParamFilters(filters, paramsMap); 
		QueryParamsUtility.HandleDoubleQueryParamSortOrders(sortOrders, paramsMap);
		
		//Get limit from query params
		limit = QueryParamsUtility.GetLimit(paramsMap);
		
		QueryParamsUtility.GetFiltersFromQueryParams(filters, paramsMap); 				
		//Utility.GetSortOrdersFromQueryParams(sortOrders, paramsMap); 
				
		Query<Game> q = buildQuery(filters, sortOrders, limit); //calling generic method
		
		List<Game> list = q.list();
				
		return list;
	}*/

	
	/*@Override
	public List<Game> listGamesByPlatform(Platform platform) {
		List<Game> retrievedGames = null;
		retrievedGames = service.query(Game.class).filter("platform", name).get();
		return retrievedGames;
	}*/
}


