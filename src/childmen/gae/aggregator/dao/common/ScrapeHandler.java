package childmen.gae.aggregator.dao.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import childmen.gae.aggregator.common.Convert;
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

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;

public class ScrapeHandler {

	private static DAOFactory objectifyFactory;	
	public static CriticDAO ofyCriticDAO;
	public static GameDAO ofyGameDAO;	
	public static PlatformDAO ofyPlatformDAO;
	public static LocationDAO ofyLocationDAO;
	public static ReviewDAO ofyReviewDAO;
		
	private final static int GAME_NAME_INDEX = 0;
	private final static int GAME_IMAGELINK_INDEX = 2;
	private final static int GAME_METARATING_INDEX = 5; //TODO: aggregated
	private final static int GAME_BLURB_INDEX = 6;
	private final static int GAME_GENRE_INDEX = 8;
	
	private final static int PLATFORMS_INDEX = 9;
	
	private final static int REVIEW_REVIEWLINK_INDEX = 3;
	private final static int REVIEW_PUBLISHEDDATE_INDEX = 4;
	private final static int REVIEW_RATING_INDEX = 5;
	
	private final static int CRITIC_NAME_INDEX = 1;
	private final static int CRITIC_HOMEPAGE_INDEX = 2;
	private final static int CRITIC_IMAGELINK_INDEX = 3;

	private final static int LOCATION_NAME_INDEX = 4;
		
	static	//Create required DAOs
	{
		objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);	
		ofyCriticDAO = objectifyFactory.getCriticDAO();
		ofyGameDAO = objectifyFactory.getGameDAO();
		ofyPlatformDAO = objectifyFactory.getPlatformDAO();
		ofyLocationDAO = objectifyFactory.getLocationDAO();
		ofyReviewDAO = objectifyFactory.getReviewDAO();
	}
	
	/**
	 * Purpose:	Create games, create review, associate platform with game, associate review with game
	 * @param scrapefile : relative path to name of scrape file e.g.
	 * So a file located in /projectfolder/war/data/anothertest.xml would be accessed using 
	 * FileInputStream("data/anothertest.xml").  
	 * @throws EntityNotFoundException 
	 */
	public void HandleInitialScrape(String scrapefile) throws EntityNotFoundException
	{	
		
		//List<Game> list = new ArrayList<Game>();
		
		File f = null;
		FileReader fr = null;
		BufferedReader br = null;

		f = new File(scrapefile);

		Key<Critic> criticKey = null;
		
		try {
			fr = new FileReader(f);
			
			//BufferedReader is added for fast reading
			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				
				if(line.startsWith("#") || line.isEmpty())
					continue;		//skip
					
				String[] fields = line.split("\t");
							
				if(line.startsWith("$"))
				{													
					criticKey = GetCriticKey(fields[CRITIC_NAME_INDEX]);
					if(criticKey == null) //then create critic and associate with location
					{
						criticKey = CreateCritic(fields);												
					}															
					AssociateCriticWithLocations(criticKey, fields);
					
					continue; //move to next line
				}
						
				Key<Game> gameKey = GetGameKey(fields[GAME_NAME_INDEX]);								
				if(gameKey == null)
				{
					gameKey = CreateGame(fields); 
				}																				
				AssociateGameWithPlatforms(gameKey, fields);
			
				Key<Review> reviewKey = CreateReview(fields);				
				//AssociateGameWithReview(gameKey, reviewKey, fields);
				
				if(criticKey != null)
				{
					//AssociateCriticWithReview(criticKey, reviewKey);  //link critic with new review
				}
				
				//list.add(game);
			}

			// dispose all the resources after using them
			fr.close();
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public Key<Critic> GetCriticKey(String criticName)	
	{
		Key<Critic> ck = ofyCriticDAO.getKeyByProperty("name", criticName);			
		return ck;
	}
	
	public Key<Game> GetGameKey(String gameName)
	{		
		Key<Game> gk = ofyGameDAO.getKeyByProperty("name", gameName);		
		return gk;
	}
	
	public Key<Critic> CreateCritic(String[] fields)
	{
		String name = fields[CRITIC_NAME_INDEX];
		String homepagelink = fields[CRITIC_HOMEPAGE_INDEX];
		String imagelink = fields[CRITIC_IMAGELINK_INDEX];
	
		Critic critic = new Critic(name, homepagelink, imagelink);
		Key<Critic> ck = ofyCriticDAO.put(critic);
		
		return ck;
	}
	
	public void AssociateCriticWithLocations(Key<Critic> criticKey, String[] fields)
	{
		Map<String, Location> locationsMap = asLocationMap(ofyLocationDAO.listByClass()); //TODO: put in context initializer or baseservlet
		
		String locationName = fields[LOCATION_NAME_INDEX]; //extract location from csv file
		
		//associate location with critic								
		Location location = locationsMap.get(locationName);
		List<Key<Critic>> criticKeys = location.getCritickeys();
		criticKeys.add(criticKey);
		
		ofyLocationDAO.put(location); // update data store							
	}
	private Map<String, Location> asLocationMap(List<Location> locations) //put in generic dao?
	{
		Map<String, Location> locationMap = new HashMap<String, Location>();
		for(Location l : locations)
		{
			locationMap.put(l.getName(), l);
		}
		return locationMap;
	}
	
	public Key<Game> CreateGame(String[] fields)
	{
		String name = fields[GAME_NAME_INDEX];		
		String imagelink = fields[GAME_IMAGELINK_INDEX];		
		double metarating;
		try
		{
			metarating = Double.parseDouble(fields[GAME_METARATING_INDEX]);
		}
		catch(NumberFormatException e)
		{
			metarating = 0.0;
		}
		String blurb = fields[GAME_BLURB_INDEX];		
		String genre = fields[GAME_GENRE_INDEX];
			
		Game game = new Game(name, genre, blurb, metarating, imagelink);
		Key<Game> gk = ofyGameDAO.put(game);
		
		return gk;
	}
	
	public void AssociateGameWithPlatforms(Key<Game> gameKey, String[] fields)
	{						
		Map<String, Platform> platformsMap = asPlatformMap(ofyPlatformDAO.listByClass());
		
		String[] platforms = fields[PLATFORMS_INDEX].split(","); //extract platforms from csv file
		
		//associate platforms with game
		for (String platformName : platforms) 
		{
			Platform platform = platformsMap.get(platformName);
		
			List<Key<Game>> gameKeys = platform.getGamekeys();			
			gameKeys.add(gameKey); 
			
			ofyPlatformDAO.put(platform); // update data store					
		}
	}
	private Map<String, Platform> asPlatformMap(List<Platform> platforms) //put in generic dao?
	{
		Map<String, Platform> platformMap = new HashMap<String, Platform>();
		for(Platform p : platforms)
		{
			platformMap.put(p.getName(), p);
		}
		return platformMap;
	}
	
	public Key<Review> CreateReview(String[] fields)
	{
		String reviewlink = fields[REVIEW_REVIEWLINK_INDEX];
		String format = "MMM dd, yyyy";
		Date publisheddate = Convert.StringToDateWithInputFormat(fields[REVIEW_PUBLISHEDDATE_INDEX], format);
		double rating;
		try
		{
			rating = Double.parseDouble(fields[REVIEW_RATING_INDEX]);
		}
		catch(NumberFormatException e)
		{
			rating = 0.0;
		}
		
		Review review = new Review(reviewlink, publisheddate, rating);
		Key<Review> rk = ofyReviewDAO.put(review);
		return rk;
	}
	
	public void AssociateGameWithReview(Key<Game> gameKey, Key<Review> reviewKey, String[] fields) throws EntityNotFoundException
	{
		Game game = ofyGameDAO.get(gameKey);
		Review review = ofyReviewDAO.get(reviewKey);
		
		//Link game to review. Commits to data store
		ofyGameDAO.addReview(review, game);				
	}
	
	public void AssociateCriticWithReview(Key<Critic> criticKey, Key<Review> reviewKey) throws EntityNotFoundException
	{
		Critic critic = ofyCriticDAO.get(criticKey);
		Review review = ofyReviewDAO.get(reviewKey);
		
		//Link critic to review. Commits to data store
		ofyCriticDAO.addReview(review, critic);
	}
}
