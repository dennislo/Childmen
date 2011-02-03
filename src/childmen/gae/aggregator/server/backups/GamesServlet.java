package childmen.gae.aggregator.server.backups;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Constants;
import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.dao.common.QueryParamsUtility;
import childmen.gae.aggregator.dao.common.ScrapeHandler;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Platform;
import childmen.gae.aggregator.model.Review;
import childmen.gae.aggregator.server.BaseServlet;
import childmen.gae.aggregator.server.example.Initialise;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;

@SuppressWarnings("serial")
public class GamesServlet extends BaseServlet<Game> {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{								
		//Initialise.DeleteGamesForTesting(); //TODO: remove
		//Initialise.CreateGamesForTesting(); //TODO: remove
		
		resp.setContentType("text/plain");

		//Need to make a copy, so it is mutable	to avoid: 
		//"java.lang.UnsupportedOperationException java.util.Collections$UnmodifiableMap.remove" exception
		Map<String, String[]> paramsMap = new HashMap<String, String[]>(req.getParameterMap());		

		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();
		PlatformDAO ofyPlatformDAO = objectifyFactory.getPlatformDAO();
		
		List<Game> games;
		
		if(paramsMap.containsKey(Constants.QueryParams.PLATFORM)) //TODO: handle platform, ugly!
		{			
			String filterValue = QueryParamsUtility.GetStringFilterValue(paramsMap.get(Constants.QueryParams.PLATFORM));
			Platform platform = ofyPlatformDAO.getByProperty("name", filterValue); //TODO: game (one to many) platforms not working?
			Key<Platform> platformForeignKey = ofyPlatformDAO.getKeyByProperty("id", platform.getId());
			
			games = ofyGameDAO.listByProperty("platform", platformForeignKey);						
		}
		else //process as normal
		{					
			games = ofyGameDAO.getGamesByQueryParams(paramsMap);
		}				
		
		WriteResponse(games, req, resp);			
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
		
		/*
		 * Android POST test below: 
		 */		
		/*
		// Get JSON payload
		String payload = "";
		if (req.getParameterMap().containsKey("payload")) {
			payload = req.getParameter("payload");
		}
		
		// Convert to object list using CreateObjectFromCSVString()
		
		// Convert to object list
		Type listType = new TypeToken<List<Game>>() {}.getType();
		List<Game> games = ConvertToObjectList(payload, listType);
		
		// Create required DAO Factory and get DAO
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();
		
		List<Response> responseMessages = new ArrayList<Response>();
		for (Game g : games) {
			ofyGameDAO.put(g); // store object in data store
		
			String msg = WriteInfoToLog(g.getClass().getName(), g.getId());
		
			Response r = new Response(msg);
			responseMessages.add(r);
		}
		
		// Create response and send it
		WriteResponse(responseMessages, req, resp);
		*/
	}	
	
	private void CreateAndStoreGamesTest(HttpServletRequest req, HttpServletResponse resp) throws IOException, EntityNotFoundException
	{
		resp.setContentType("text/plain");
					
		Initialise.DeleteLocationsForTesting();
		Initialise.CreateLocationsForTesting();
		Initialise.DeleteCriticsForTesting();
		
		Initialise.DeletePlatformsForTesting();	//TODO: replace with real method
		Initialise.CreatePlatformsForTesting(); //TODO: replace with real method
		Initialise.DeleteGamesForTesting(); //TODO: remove
							
		Initialise.DeleteReviewsForTesting();
		
		String scrapefile = "data/games.txt";
		ScrapeHandler sh = new ScrapeHandler();
		sh.HandleInitialScrape(scrapefile); //loop through CSV file and store in data store
		
		//update platforms, reviews, critics with another scrape
		String scrapefile_ign_au = "data/games2.txt";
		sh.HandleInitialScrape(scrapefile_ign_au);
		
		//debug(req, resp);
	
	}

	/*
	private List<Game> GetGamesByQueryParams(Map<String, String[]> paramsMap) {
		List<Game> list = new ArrayList<Game>();

		ObjectifyService.register(Game.class);

		Objectify service = ObjectifyService.begin();

		Query<Game> q = service.query(Game.class);
		// Query<Game> q = service.query(Game.class).filter("metarating >", 7).filter("genre", "FPS");
		// Query<Game> q = service.query(Game.class).order("metarating"); //asc order

		QueryParamsUtility.HandleDoubleQueryParams(q, paramsMap);

		// Iterate through map
		Iterator<Map.Entry<String, String[]>> it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<String, String[]> pairs = it.next();

			String paramName = pairs.getKey();
			if (paramName.equals("outputpretty")) {
				continue; // ignore outputpretty
			}

			Collection<String> paramValues = (Collection<String>) Utility.toCollection(pairs.getValue());
			String paramValue = Utility.join(paramValues, " ");

			q = q.filter(paramName, paramValue);
		}

		list = q.list();

		return list;
	}*/
	
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
			WriteResponse(rcMap, req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}