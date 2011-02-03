package childmen.gae.aggregator.model;

import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Model of a critic e.g. IGN or Gamespot  
 * @author Dennis
 */
public class Critic {

	@Id private Long id;
	private String name;
	private String homepagelink; //e.g. www.ign.com
	private String imagelink; //i.e. link to critic logo OR we might just store it as a blob?

	private Key<Location> location; //one to one relationship back to location 
	
	private Critic() {}
	
	public Critic(String name, String homepagelink, String imagelink) {	
		this.name = name;
		this.homepagelink = homepagelink;
		this.imagelink = imagelink;
	}
	
	/*	
	 * Setting relationships
	 
	//Add one review to this Critic	
	public void addReview(Review review) {
		review.setCritic(this);
		Objectify service = ObjectifyService.begin();	
		service.put(review);
	}

	
	//Add multiple reviews to this Critic	
	public void addReviews(List<Review> reviews) {
		for (Review review : reviews) {
			review.setCritic(this);
		}
		Objectify service = ObjectifyService.begin();
		service.put(reviews);
	}
	*/
	
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}	
	/**
	 * @return the homepagelink
	 */
	public String getHomepagelink() {
		return homepagelink;
	}

	/**
	 * @param homepagelink the homepagelink to set
	 */
	public void setHomepagelink(String homepagelink) {
		this.homepagelink = homepagelink;
	}

	/**
	 * @return the imagelink
	 */
	public String getImagelink() {
		return imagelink;
	}

	/**
	 * @param imagelink the imagelink to set
	 */
	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	/**
	 * @return the location
	 */
	public Key<Location> getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = new Key<Location>(Location.class, location.getId());
	}
	
}
