package childmen.gae.aggregator.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Model of a Location e.g. Australia    
 * @author Dennis
 */
public class Location {

	@Id private Long id;
	private String name;
	private String abbreviation;
    List<Key<Critic>> critickeys = new ArrayList<Key<Critic>>();
	
	private Location() {};

	public Location(String name, String abbreviation) {
		this.name = name;
		this.abbreviation = abbreviation;
	}

	/*	
	 * Setting relationships
	 */
	//Associate location to this critic	[one location to one critic]
	public void addCritic(Critic critic) {
		critic.setLocation(this);
		Objectify service = ObjectifyService.begin();	
		service.put(critic);
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
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * @return the critickeys
	 */
	public List<Key<Critic>> getCritickeys() {
		return critickeys;
	}

	/**
	 * @param critickeys the critickeys to set
	 */
	public void setCritickeys(List<Key<Critic>> critickeys) {
		this.critickeys = critickeys;
	}
	
}
