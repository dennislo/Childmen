package childmen.gae.aggregator.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * Model of a review e.g. COD Black Ops review at http://au.pc.ign.com/articles/113/1133458p1.html   
 * @author Dennis
 *
 */
@Cached
public class Review {

	@Id private Long Id;	
	@Unindexed private String reviewlink;	
	private Date publisheddate;
	private double rating;  //TODO: convert to entity? 

	private Key<Game> game; //one to many relationship back to Game / one to one relationship back to Game
							//Read: http://www.ibm.com/developerworks/java/library/j-javadev2-13/index.html
							//Only one hard connection needed
	
	private Key<Critic> critic; //one to one relationship back to Critic

	//For objectify only
	private Review() {}
	

	public Review(String reviewlink, Date publisheddate, double rating) {
		this.reviewlink = reviewlink;
		this.publisheddate = publisheddate;
		this.rating = rating;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}

	/**
	 * @return the reviewlink
	 */
	public String getReviewlink() {
		return reviewlink;
	}

	/**
	 * @param reviewlink the reviewlink to set
	 */
	public void setReviewlink(String reviewlink) {
		this.reviewlink = reviewlink;
	}

	/**
	 * @return the publisheddate
	 */
	public Date getPublisheddate() {
		return publisheddate;
	}

	/**
	 * @param publisheddate the publisheddate to set
	 */
	public void setPublisheddate(Date publisheddate) {
		this.publisheddate = publisheddate;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
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
		this.game = new Key<Game>(Game.class, game.getId()); //associate as an objectify key with game id 

	}
	
	/**
	 * @return the critic
	 */
	public Key<Critic> getCritic() {
		return critic;
	}

	/**
	 * @param critic the critic to set
	 */
	public void setCritic(Critic critic) {
		this.critic = new Key<Critic>(Critic.class, critic.getId());
	}
}
