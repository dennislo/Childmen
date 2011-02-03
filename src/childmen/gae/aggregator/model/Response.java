package childmen.gae.aggregator.model;

import java.util.List;
import java.util.Map;

/**
 * Response object for server clients
 * 
 * @author Dennis
 */
public class Response {
	
	public class GameDetails
	{
		Game game;
		List<Review> reviews;
		Map<Review, Critic> criticsMap;
		
		
		public GameDetails(Game game, List<Review> reviews, Map<Review, Critic> criticsMap) {
			this.game = game;
			this.reviews = reviews;
			this.criticsMap = criticsMap;
		}
		
		/**
		 * @return the game
		 */
		public Game getGame() {
			return game;
		}
		/**
		 * @param game the game to set
		 */
		public void setGame(Game game) {
			this.game = game;
		}
		/**
		 * @return the reviews
		 */
		public List<Review> getReviews() {
			return reviews;
		}
		/**
		 * @param reviews the reviews to set
		 */
		public void setReviews(List<Review> reviews) {
			this.reviews = reviews;
		}
		/**
		 * @return the criticsMap
		 */
		public Map<Review, Critic> getCriticsMap() {
			return criticsMap;
		}
		/**
		 * @param criticsMap the criticsMap to set
		 */
		public void setCriticsMap(Map<Review, Critic> criticsMap) {
			this.criticsMap = criticsMap;
		}		
	}
	
		
	private String message;

	public Response() {

	}

	public Response(String message)
	{
		this.message= message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
