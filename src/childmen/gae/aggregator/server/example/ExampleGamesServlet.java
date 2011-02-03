package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.model.Game;

import com.google.appengine.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class ExampleGamesServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Create required DAO Factory and get DAO
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();
		
		//Create and store game to Datastore
		String testImageLink = "http://img1.gamespotcdn.com/metacritic/public/www/images/products/games/9/e1b4a2672ef423c38a7c8e81383f2e87-98.jpg";				
		Game g0 = new Game("MW2", "FPS", "COD in present times", 10, testImageLink);
		Game g1 = new Game("black ops", "FPS", "COD in Vietnam", 9, testImageLink);
		Game g2 = new Game("need for speed - hot persuit", "Racing", "Car - Cops vs Criminals", 8, testImageLink);
		ofyGameDAO.put(g0);
		ofyGameDAO.put(g1);
		ofyGameDAO.put(g2);
			
		//Retrieve game from Datastore		
		List<Game> retrievedGames = ofyGameDAO.listByClass();
 				
		// Create JSON response
		try {
			JSONObject responseObj = new JSONObject();

			List<JSONObject> gameObjects = new LinkedList<JSONObject>();

			for (Game g : retrievedGames) {
				JSONObject gameObj = new JSONObject();

				gameObj.put("Id", g.getId());
				gameObj.put("Name", g.getName());
				gameObj.put("Genre", g.getGenre());				

				gameObjects.add(gameObj);
			}
			
			responseObj.put("games", gameObjects);									
			
			//Write JSON object to response
			PrintWriter writer = resp.getWriter();
			writer.write(responseObj.toString());
			writer.flush();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* Query Parameters with Switch 
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Map<String, String[]> paramsMap = (Map<String, String[]>) req
				.getParameterMap();

		// Iterate through map
		Iterator<Map.Entry<String, String[]>> it = paramsMap.entrySet()
				.iterator();
		while (it.hasNext()) {

			Map.Entry<String, String[]> pairs = it.next();

			String paramName = pairs.getKey();
			String[] paramValues = pairs.getValue();

			String paramValue = "";
			for (String value : paramValues) {
				if (paramValue.length() > 1) {
					paramValue += " " + value;
				} else {
					paramValue += value;
				}
			}

			List<Game> games = GetGamesByQueryParams(paramsMap);

			QueryParameters.Game qp;
			try {
				qp = QueryParameters.Game.valueOf(paramName);
			} catch (IllegalArgumentException e) {
				qp = null;
			}

			if (qp != null) {
				switch (qp) {
				case name:
					System.out.println(paramName);
					System.out.println(paramValue);

					DAOFactory objectifyFactory = DAOFactory
							.getDAOFactory(DAOFactory.OBJECTIFY);
					GameDAO ofyGameDAO = objectifyFactory.getGameDAO();

					break;
				case genre:
					System.out.println(paramName);
					System.out.println(paramValue);
					break;
				}
			} else {
				System.out.println("Invalid query parameter.");
			}

		}
	}*/

}
