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
public abstract class ReviewDAO extends ObjectifyGenericDAO<Review>
{			
	protected ReviewDAO() {
		super(Review.class);
	}
	
	/*Add Review specific DAO methods below that are non generic*/
	public abstract List<Review> listReviewsByGame(Game g);
	public abstract List<Review> listReviewsByGameName(String name);

	public abstract List<Review> getReviewsByQueryParams(Map<String, String[]> paramsMap);
}
