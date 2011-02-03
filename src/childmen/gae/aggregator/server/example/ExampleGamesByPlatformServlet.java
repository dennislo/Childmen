package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Platform;
import childmen.gae.aggregator.server.BaseServlet;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;


/**
 * Example servlet to handle
 * GET /games?outputpretty=true&platform=XBOX360 
 * GET GET /games?outputpretty=true&platform=ALL
 * @author Dennis
 */
@SuppressWarnings("serial")
public class ExampleGamesByPlatformServlet extends BaseServlet<Platform> {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		resp.setContentType("text/plain");
		
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		PlatformDAO ofyPlatformDAO = objectifyFactory.getPlatformDAO();
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();
		
		Initialise.DeletePlatformsForTesting();
		Initialise.DeleteGamesForTesting();
		Initialise.CreatePlatformsForTesting();
		Initialise.CreateGamesForTesting();
		
		int numberOfPlatforms = ofyPlatformDAO.listKeys().size();
		System.out.println(numberOfPlatforms);
		
		//retrieve game keys and platforms
		//List<Game> games = ofyGameDAO.listByClass(); 		
		List<Key<Game>> gameKeys = ofyGameDAO.listKeys();
		Platform xbox360 = ofyPlatformDAO.getByProperty("name", "XBOX360");
		Platform ps3 = ofyPlatformDAO.getByProperty("name", "PS3");
		Platform pc = ofyPlatformDAO.getByProperty("name", "PC"); 			
						
		
		//use platform dao to associate game with platform
		List<Key<Game>> xbox360GameKeys = xbox360.getGamekeys(); //empty initially
		List<Key<Game>> ps3GameKeys = ps3.getGamekeys();
		List<Key<Game>> pcGameKeys = pc.getGamekeys();		
		
		Key<Game> g0Key = gameKeys.get(0);
		Key<Game> g1Key = gameKeys.get(1);
		Key<Game> g2Key = gameKeys.get(2);
		Key<Game> g3Key = gameKeys.get(3);
		Key<Game> g4Key = gameKeys.get(4);
		
		//assign 3 games to xbox360, 1 game to ps3, 1 game to pc
		xbox360GameKeys.add(g0Key);
		xbox360GameKeys.add(g1Key);
		xbox360GameKeys.add(g2Key);
		ps3GameKeys.add(g3Key);
		pcGameKeys.add(g4Key);
		
		//update entity in datastore
		Key<Platform> xbox360Key = ofyPlatformDAO.put(xbox360);
		Key<Platform> ps3Key = ofyPlatformDAO.put(ps3);
		Key<Platform> pcKey = ofyPlatformDAO.put(pc);
				
		//now get all games on XBOX360, PS3 and PC
		try {
			Platform xbox360Fetched = ofyPlatformDAO.getByProperty("name", "XBOX360");
			Platform ps3Fetched = ofyPlatformDAO.get(ps3Key);
			Platform pcFetched = ofyPlatformDAO.get(pcKey);
			
			List<Key<Game>> xbox360GameKeysFetched = xbox360Fetched.getGamekeys(); //now full
			List<Key<Game>> ps3GameKeysFetched = ps3Fetched.getGamekeys();
			List<Key<Game>> pcGameKeysFetched = pcFetched.getGamekeys();
			
			//create response
			List<Game> xbox360Games = new ArrayList<Game>();
			for (Key<Game> key: xbox360GameKeysFetched) {
				xbox360Games.add(ofyGameDAO.get(key));
			} 			
			WriteResponse(xbox360Games, req, resp);
			
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}

		
		
		
		
		
	}
	
	
}
