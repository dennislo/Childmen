package childmen.gae.aggregator.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Model of a platform i.e. PC, PS3, XBOX360, WII, DS, PSP, IPHONE, IPAD    
 * @author Dennis
 */
public class Platform {

	@Id private Long id;
	private String name;
	private String imagename;
	private List<Key<Game>> gamekeys = new ArrayList<Key<Game>>();

	private Platform() {}

	public Platform(String name, String imagename) {	
		this.name = name;
		this.imagename = imagename;
	}
	
	/*	
	 * Setting relationships
	 */ 
	//Add one game to this Platform	
	public void addGame(Game game) {
		game.setPlatform(this);
		Objectify service = ObjectifyService.begin();	
		service.put(game);
	}

	
	//Add multiple games to this Platform	
	public void addGames(List<Game> games) {
		for (Game game : games) {
			game.setPlatform(this);
		}
		Objectify service = ObjectifyService.begin();
		service.put(games);
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
	 * @return the imagename
	 */
	public String getImagename() {
		return imagename;
	}

	/**
	 * @param imagename the imagename to set
	 */
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	/**
	 * @return the gamekeys
	 */
	public List<Key<Game>> getGamekeys() {
		return gamekeys;
	}

	/**
	 * @param gamekeys the gamekeys to set
	 */
	public void setGamekeys(List<Key<Game>> gamekeys) {
		this.gamekeys = gamekeys;
	}	
}
