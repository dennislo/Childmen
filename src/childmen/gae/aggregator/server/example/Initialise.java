package childmen.gae.aggregator.server.example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import childmen.gae.aggregator.common.Arrays;
import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.LocationDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Location;
import childmen.gae.aggregator.model.Platform;
import childmen.gae.aggregator.model.Review;

import com.googlecode.objectify.Key;

public class Initialise {
	
	private static DAOFactory objectifyFactory;
	private static GameDAO ofyGameDAO;
	private static ReviewDAO ofyReviewDAO;
	private static PlatformDAO ofyPlatformDAO;
	private static CriticDAO ofyCriticDAO;
	private static LocationDAO ofyLocationDAO;
	
	static	//Create required DAOs
	{
		objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		ofyReviewDAO = objectifyFactory.getReviewDAO();
		ofyGameDAO = objectifyFactory.getGameDAO();
		ofyPlatformDAO = objectifyFactory.getPlatformDAO();
		ofyCriticDAO = objectifyFactory.getCriticDAO();
		ofyLocationDAO = objectifyFactory.getLocationDAO();
	}
	
	public static void CreateLocationsForTesting()
	{
		for(int i = 0; i < Arrays.CountryNames.length; i++)
		{
			String name = Arrays.CountryNames[i];
			String abbreviation = Arrays.CountryAbbreviations[i];
			Location location = new Location(name, abbreviation);
			
			ofyLocationDAO.put(location);
		}
	}
	
	public static void DeleteLocationsForTesting()
	{
		List<Key<Location>> locationKeys = ofyLocationDAO.listKeys();
		ofyLocationDAO.deleteByKeys(locationKeys);
	}
	
	public static void CreatePlatformsForTesting()
	{		
		for(String platformName : Arrays.Platforms)
		{
			String name = platformName;
			String imageName = platformName+"_image.png"; //TODO: assign real images
			Platform newPlatform = new Platform(name, imageName);
			
			ofyPlatformDAO.put(newPlatform);
		}
	}
	
	public static void DeletePlatformsForTesting()
	{
		List<Key<Platform>> platformKeys = ofyPlatformDAO.listKeys();
		ofyPlatformDAO.deleteByKeys(platformKeys);
	}

	public static void CreateGamesForTesting() {		
		String testImageLink = "http://img1.gamespotcdn.com/metacritic/public/www/images/products/games/9/e1b4a2672ef423c38a7c8e81383f2e87-98.jpg";
		Game g0 = new Game("MW2", "FPS", "COD in present times", 10, testImageLink);
		Game g1 = new Game("black ops", "FPS", "COD in Vietnam", 9.0, testImageLink);
		Game g2 = new Game("need for speed - hot persuit", "Racing","Car - Cops vs Criminals", 6.5, testImageLink);
		Game g3 = new Game("Star Craft 2", "RTS","Protoss vs Zerg vs Human orgy", 8.5, testImageLink);
		Game g4 = new Game("Red Alert 2", "RTS","Live action cut scenes", 7.2, testImageLink);
		ofyGameDAO.put(g0);
		ofyGameDAO.put(g1);
		ofyGameDAO.put(g2);
		ofyGameDAO.put(g3);
		ofyGameDAO.put(g4);
				
		//TODO: Associate platforms with games using TAB seperated file
	}
	
	public static void DeleteGamesForTesting() {		
		List<Key<Game>> gameKeys = ofyGameDAO.listKeys();
		ofyGameDAO.deleteByKeys(gameKeys);
	}
	
	public static void CreateReviewsForTesting() {				
		//Create review published dates				 
		Calendar cal = Calendar.getInstance();
		
		Date publishedDate = cal.getTime(); //set publishedDate as Today
				
		cal.add(Calendar.DATE, -10);	//subtracting 10 dates from current date
		Date publishedDatePlusOne = cal.getTime();
		
		cal.add(Calendar.DATE, -20);	//subtracting 20 dates from current date
		Date publishedDatePlusTwo = cal.getTime();
				
		Review r1 = new Review("http://au.xbox360.ign.com/objects/143/14349501.html", publishedDate, 1.0);
		Review r2 = new Review("http://gamespot.com/123.html", publishedDatePlusOne, 1.0);
		Review r3 = new Review("http://1up.com/456.html", publishedDatePlusTwo, 2.0);	
		
		ofyReviewDAO.put(r1);
		ofyReviewDAO.put(r2);
		ofyReviewDAO.put(r3);
	}

	public static void DeleteReviewsForTesting() {
		List<Key<Review>> reviewKeys = ofyReviewDAO.listKeys();
		ofyReviewDAO.deleteByKeys(reviewKeys);
	}
	
	public static void CreateCriticsForTesting()
	{		
		Critic c1 = new Critic("IGN AU", "www.ign.com.au", "www.ign.com.au/logo");
		Critic c2 = new Critic("Gamespot", "www.gamespot.com", "www.gamespot.com/logo");
		Critic c3 = new Critic("1up", "www.1up.com", "www.1up.com/logo");			
		
		ofyCriticDAO.put(c1);
		ofyCriticDAO.put(c2);
		ofyCriticDAO.put(c3);		
	}
	
	public static void DeleteCriticsForTesting()
	{
		List<Key<Critic>> criticKeys = ofyCriticDAO.listKeys();
		ofyCriticDAO.deleteByKeys(criticKeys);
	}
}
