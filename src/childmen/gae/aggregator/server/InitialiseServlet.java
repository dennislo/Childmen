package childmen.gae.aggregator.server;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;

import childmen.gae.aggregator.common.Arrays;
import childmen.gae.aggregator.common.Utility;
import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.LocationDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.dao.common.ScrapeHandler;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Location;
import childmen.gae.aggregator.model.Platform;
import childmen.gae.aggregator.model.Review;
import childmen.gae.aggregator.server.example.Initialise;

/**
 * Normally executed by a GAE cron job
 * Purpose: reset location and platform entities in the data store
 * @author Dennis
 *
 */
@SuppressWarnings("serial")
public class InitialiseServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException 
	{	
		DeleteLocations();
		DeletePlatforms();
		CreateLocations();
		CreatePlatforms();		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException 
	{	
		resp.setContentType("text/plain");
		
		try {
			CreateAndStoreGamesTest(req, resp);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		resp.getWriter().println("Data store was initialised at: " + Utility.GetSydneyTimeString());
	}
	
	
	private void CreateAndStoreGamesTest(HttpServletRequest req, HttpServletResponse resp) throws IOException, EntityNotFoundException
	{
		resp.setContentType("text/plain");
					
		Initialise.DeleteLocationsForTesting(); //TODO: replace with real method
		Initialise.CreateLocationsForTesting();
		
		Initialise.DeletePlatformsForTesting();	
		Initialise.CreatePlatformsForTesting(); 
		
		Initialise.DeleteCriticsForTesting();
		Initialise.DeleteGamesForTesting(); 						
		Initialise.DeleteReviewsForTesting();
		
		String scrapefile = "data/games.txt";
		ScrapeHandler sh = new ScrapeHandler();
		sh.HandleInitialScrape(scrapefile); //loop through CSV file and store in data store
		
		//update platforms, reviews, critics with another scrape
		String scrapefile_ign_au = "data/games2.txt";
		sh.HandleInitialScrape(scrapefile_ign_au);
		
		//debug(req, resp);
	
	}

	
	private static DAOFactory objectifyFactory;
	private static LocationDAO ofyLocationDAO;
	private static PlatformDAO ofyPlatformDAO;
	
	static	//Create required DAOs
	{
		objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		ofyLocationDAO = objectifyFactory.getLocationDAO();
		ofyPlatformDAO = objectifyFactory.getPlatformDAO();
	}
	
	
	public static void CreateLocations()
	{
		for(int i = 0; i < Arrays.CountryNames.length; i++)
		{
			String name = Arrays.CountryNames[i];
			String abbreviation = Arrays.CountryAbbreviations[i];
			Location location = new Location(name, abbreviation);
			
			ofyLocationDAO.put(location);
		}		
	}
	
	public static void DeleteLocations()
	{
		List<Key<Location>> locationKeys = ofyLocationDAO.listKeys();
		ofyLocationDAO.deleteByKeys(locationKeys);
	}
	
	public static void CreatePlatforms()
	{		
		for(String platformName : Arrays.Platforms)
		{
			String name = platformName;
			String imageName = platformName+"_image.png"; //TODO: assign real images
			Platform newPlatform = new Platform(name, imageName);
			
			ofyPlatformDAO.put(newPlatform);
		}
	}
	
	public static void DeletePlatforms()
	{
		List<Key<Platform>> platformKeys = ofyPlatformDAO.listKeys();
		ofyPlatformDAO.deleteByKeys(platformKeys);
	}
	
	
	////////////////////////////////DEBUG////////////////////////////////
	
	private void debug(HttpServletRequest req, HttpServletResponse resp)
	{
		//DEBUG: now list all critics of United States
		/*
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		LocationDAO ofyLocationDAO = objectifyFactory.getLocationDAO();
		CriticDAO ofyCriticDAO = objectifyFactory.getCriticDAO();

		List<Critic> output = new ArrayList<Critic>();
		List<Location> locations = ofyLocationDAO.listByClass();
		for (Location location : locations) {
			List<Key<Critic>> criticKeys = location.getCritickeys();
			for (Key<Critic> key : criticKeys) {
				output.add(ofyCriticDAO.get(key));
			}
		}
		WriteResponse(output, req, resp);
		*/
		
		//DEBUG: now list all games of all platforms
		/*
		try {
			DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
			PlatformDAO ofyPlatformDAO = objectifyFactory.getPlatformDAO();
			GameDAO ofyGameDAO = objectifyFactory.getGameDAO();

			List<Game> output = new ArrayList<Game>();
			List<Platform> platforms = ofyPlatformDAO.listByClass();
			for (Platform platform : platforms) {
				List<Key<Game>> gameKeys = platform.getGamekeys();
				for (Key<Game> key : gameKeys) {
					output.add(ofyGameDAO.get(key));
				}
			}
			WriteResponse(output, req, resp);
			
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		*/
		/*		
		//DEBUG: Retrieve game and it's reviews	
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();
		Game retrievedGame = null;
		retrievedGame =  ofyGameDAO.getByProperty("name", "Dead Space 2");
		List<Review> retrievedReviews = null;
		if(retrievedGame != null)
		{
			retrievedReviews = (List<Review>) ofyReviewDAO.listReviewsByGame(retrievedGame);
		}
		WriteResponse(retrievedReviews, req, resp);
		*/
		
		//DEBUG: Retrive critic of reviews - for game details page
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		CriticDAO ofyCriticDAO = objectifyFactory.getCriticDAO();
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		
		List<Review> retrievedReviews = ofyReviewDAO.listByClass();
		Map<Review, Critic> rcMap = new LinkedHashMap<Review, Critic>(); //predictable iteration order

		for (Review r : retrievedReviews) {
			Critic c = null;
			try {
				c = ofyCriticDAO.get(r.getCritic());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

			rcMap.put(r, c);
		}
		
		try {
			BaseServlet.WriteResponse(rcMap, req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
