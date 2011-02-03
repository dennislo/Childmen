package childmen.gae.aggregator.dao;

import java.util.List;
import java.util.Map;

import childmen.gae.aggregator.dao.impl.ObjectifyGenericDAO;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Review;

/**
 * This is an adapter layer. Change this so it extends MySQLGenericDAO<Game> if we decide to use MySQL 
 * @author Dennis 
 */
public abstract class GameDAO extends ObjectifyGenericDAO<Game>
{		
	protected GameDAO() {
		super(Game.class);
	}		
	
	/*Add Game specific DAO methods below that are non generic*/	
	public abstract Long putGame(Game g); //TODO: remove
	public abstract Game getGameByName(String name); //TODO: example only
	public abstract List<Game> getGamesByQueryParams(Map<String, String[]> paramsMap);
	
	public abstract List<Game> getGamesByPlatform(Map<String, String[]> paramsMap);
	
	/*Setting relationships*/
	public abstract void addReview(Review review, Game game);
	public abstract void addReviews(List<Review> reviews, Game game);	
	
}
