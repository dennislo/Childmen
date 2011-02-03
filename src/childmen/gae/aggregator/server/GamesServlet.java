package childmen.gae.aggregator.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Constants;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.dao.common.QueryParamsUtility;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Platform;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;

@SuppressWarnings("serial")
public class GamesServlet extends BaseServlet<Game> {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{								
		//Initialise.DeleteGamesForTesting();
		//Initialise.CreateGamesForTesting();
		
		resp.setContentType("text/plain");

		//Make mutable copy of qp's to avoid:
		//"java.lang.UnsupportedOperationException java.util.Collections$UnmodifiableMap.remove" exception
		Map<String, String[]> paramsMap = new HashMap<String, String[]>(req.getParameterMap());		

		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();		
		
		List<Game> games = new ArrayList<Game>();
		
		if(paramsMap.containsKey(Constants.QueryParams.PLATFORM)) //ugly hax!
		{			
			games = GetGamesByPlatform(paramsMap);											
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
	}	
	
	public List<Game> GetGamesByPlatform(Map<String, String[]> paramsMap)
	{		
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();		
					
		List<Game> gamesMatchingPlatform = new ArrayList<Game>();
		
		//Get list of games by platform
		String filterValue = QueryParamsUtility.GetStringFilterValue(paramsMap.get(Constants.QueryParams.PLATFORM));		 	
		List<Key<Game>> gameKeys = GetPlatformGameKeys(filterValue);
					
		for (Key<Game> key: gameKeys) {
			try {
				gamesMatchingPlatform.add(ofyGameDAO.get(key));
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		paramsMap.remove(Constants.QueryParams.PLATFORM); //remove from qp's

		//Get list of games by filters
		//List<Game> gamesMatchingFilters = ofyGameDAO.getGamesByQueryParams(paramsMap);
		//List<Game> list = retainAll(gamesMatchingFilters, gamesMatchingPlatform); //transforms s1 into the intersection of s1 and s2. 
		
		return gamesMatchingPlatform;
	}
	
	public List<Key<Game>> GetPlatformGameKeys(String filterValue)
	{
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);		
		PlatformDAO ofyPlatformDAO = objectifyFactory.getPlatformDAO();
		
		List<Key<Game>> gameKeys = new ArrayList<Key<Game>>();
		if(filterValue.toLowerCase().equals("all"))
		{
			List<Platform> platforms = ofyPlatformDAO.listByClass();
			for (Platform platform : platforms) {
				List<Key<Game>> keys = platform.getGamekeys();
				gameKeys.addAll(keys);
			}
		}
		else
		{
			Platform platform = ofyPlatformDAO.getByProperty("name", filterValue);
			List<Key<Game>> keys = platform.getGamekeys();
			gameKeys.addAll(keys);
		}
		return gameKeys;
	}
	
	/*
	public List<Game> retainAll (List<Game> s1, List<Game> s2)
	{
		List<Game> list = new ArrayList<Game>();
		
		for (Game game : s2) 
		{			
			if(contains(s1, game)) //if s1 contains game
			{
				list.add(game);	//add to list
			}
		}
		
		return list;
	}
	
	public boolean contains(List<Game> set, Game item)
	{
		for (Game game : set) 				
		{
			Long id = game.getId();
			if( id.equals( item.getId() ) ) 
			{
				return true;
			}
		}
		return false;
	}
	*/
}