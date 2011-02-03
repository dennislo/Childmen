package childmen.gae.aggregator.server.backups;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Arrays;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.LocationDAO;
import childmen.gae.aggregator.model.Location;

@SuppressWarnings("serial")
public class LocationsServlet extends HttpServlet {
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);			
		LocationDAO ofyLocationDAO = objectifyFactory.getLocationDAO();
		
		/*Delete all countries - DEBUG only
		List<Key<Location>> locationKeys = ofyLocationDAO.listKeys();
		ofyLocationDAO.deleteKeys(locationKeys);
		*/
		
		//Store all countries names and abbreviation 		
		for(int i = 0; i < Arrays.CountryNames.length; i++)
		{
			String countryName = Arrays.CountryNames[i];
			String countryAbbreviation = Arrays.CountryAbbreviations[i];
			Location location = new Location(countryName, countryAbbreviation);
			
			ofyLocationDAO.put(location);
		}
		
		/*Print all countries - DEBUG only
		List<Location> retrievedLocations = ofyLocationDAO.listByClass();
		for(Location l : retrievedLocations) 
		{
			System.out.println("location = " + l.getCountryName());
		}*/
		
	}
}
