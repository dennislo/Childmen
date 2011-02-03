package childmen.gae.aggregator.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

/**
 * Model of a User's rating for a game e.g. COD Black Ops
 * @author Dennis
 *
 */
public class UserRating {

	@Id private Long id;
	private double avgerageUserRating; //e.g. 7.5;
	private Key<Game> game; //one to many relationship back to Game
	
	private UserRating() {}
	
	public UserRating(double averageUserRating)
	{
		this.avgerageUserRating = averageUserRating;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the avgerageUserRating
	 */
	public double getAvgerageUserRating() {
		return avgerageUserRating;
	}

	/**
	 * @param avgerageUserRating the avgerageUserRating to set
	 */
	public void setAvgerageUserRating(double avgerageUserRating) {
		this.avgerageUserRating = avgerageUserRating;
	}

	/**
	 * @return the game
	 */
	public Key<Game> getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = new Key<Game>(Game.class, game.getId());
	}


}
