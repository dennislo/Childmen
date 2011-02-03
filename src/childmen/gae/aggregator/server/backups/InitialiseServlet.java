package childmen.gae.aggregator.server.backups;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

import childmen.gae.aggregator.common.Arrays;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.LocationDAO;
import childmen.gae.aggregator.dao.PlatformDAO;
import childmen.gae.aggregator.model.Location;
import childmen.gae.aggregator.model.Platform;

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
	
	
	
}
