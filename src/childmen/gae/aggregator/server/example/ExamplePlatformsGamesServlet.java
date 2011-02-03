package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Arrays;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Platform;

import com.googlecode.objectify.Key;

/**
 * Example: Get Critics from Review Method: get() with Key<T>
 */
@SuppressWarnings("serial")
public class ExamplePlatformsGamesServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		//Create required DAO's
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		PlatformDAO ofyPlatformDAO = objectifyFactory.getPlatformDAO();
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();		
		
		//Create platforms
		for(String platformName : Arrays.Platforms)
		{
			String name = platformName;
			String imageName = platformName+"_image.png";
			Platform newPlatform = new Platform(name, imageName);
			
			ofyPlatformDAO.put(newPlatform);
		}			
		
		//Create games
		Initialise.CreateGamesForTesting();			
		List<Game> gamesInDS = ofyGameDAO.listByClass();
		
		//Get XBOX360 platform (for example)
		Platform xbox360Platform = ofyPlatformDAO.getByProperty("name", "XBOX360");

		//then associate XBOX360 platform with games		
		xbox360Platform.addGame(gamesInDS.get(0));
		xbox360Platform.addGame(gamesInDS.get(1));
		xbox360Platform.addGame(gamesInDS.get(2));		

		//Finally, get XBOX360 platform foreign key back. Alternatively, you could get from the return value of put()
		Key<Platform> xbox360PlatformForeignKey = ofyPlatformDAO.getKeyByProperty("id", xbox360Platform.getId()); //g1.getPlatform();
		
		//Now retrieve games using XBOX360 platform foreign key 
		List<Game> retrievedXbox360Games = ofyGameDAO.listByProperty("platform", xbox360PlatformForeignKey);
		
		//Print to console for debug
		for(Game g : retrievedXbox360Games)
		{
			String output = "Platform: " + xbox360Platform.getName() + " /// Game Name: " + g.getName();
			System.out.println(output);
		}
		
		//Construct JSON response below...
		
	}
	
}