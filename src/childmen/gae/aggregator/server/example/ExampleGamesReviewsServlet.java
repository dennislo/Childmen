package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Convert;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Review;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;

@SuppressWarnings("serial")
public class ExampleGamesReviewsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {		
		
		// Create required DAOs
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO(); //ofyGameDAO is really an ObjectifyGameDAO
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		
		// Create games
		String testImageLink = "http://img1.gamespotcdn.com/metacritic/public/www/images/products/games/9/e1b4a2672ef423c38a7c8e81383f2e87-98.jpg";		
		Game g0 = new Game("MW2", "FPS", "COD in present times", 10, testImageLink);
		Game g1 = new Game("black ops", "FPS", "COD in Vietnam", 9, testImageLink);
		Game g2 = new Game("need for speed - hot persuit", "Racing", "Car - Cops vs Criminals", 8, testImageLink);

		// Store game in datastore
		ofyGameDAO.put(g0);
		Key<Game> g1Key = ofyGameDAO.put(g1);
		ofyGameDAO.put(g2);			
		
		// Create reviews
		String testReviewLink = "http://au.xbox360.ign.com/objects/143/14349501.html";
		
		//Create review published dates				 
		Calendar cal = Calendar.getInstance();
		
		Date publishedDate = cal.getTime(); //set publishedDate as Today
				
		cal.add(Calendar.DATE, 1);	//Adding 1 day to current date
		Date publishedDatePlusOne = cal.getTime();
		
		cal.add(Calendar.DATE, 2);	//Adding 2 days to current date
		Date publishedDatePlusTwo = cal.getTime();
				
		Review r1 = new Review(testReviewLink, publishedDate, 1);
		Review r2 = new Review(testReviewLink, publishedDatePlusOne, 2);
		Review r3 = new Review(testReviewLink, publishedDatePlusTwo, 3);						
				
		//Link game to reviews. Also commits in Datastore
		ofyGameDAO.addReview(r1, g1);
		ofyGameDAO.addReview(r2, g1);
		ofyGameDAO.addReview(r3, g1);
		
		//Retrieve game and reviews from Datastore
		Game retrievedGame = null;
		try {
			retrievedGame = ofyGameDAO.get(g1Key);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}	
		List<Review> retrievedReviews = null;
		if(retrievedGame != null)
		{
			retrievedReviews = (List<Review>) ofyReviewDAO.listReviewsByGame(retrievedGame);
		}
		
		// Create JSON response
		try {
			JSONObject responseObj = new JSONObject();

			List<JSONObject> reviewObjects = new LinkedList<JSONObject>();

			for (Review r : retrievedReviews) {
				JSONObject reviewObj = new JSONObject();

				reviewObj.put("Id", r.getId());
				reviewObj.put("publishedDate", Convert.DateToString(r.getPublisheddate()));
				reviewObj.put("reviewLink", r.getReviewlink());
				reviewObj.put("game name", retrievedGame.getName());

				reviewObjects.add(reviewObj);
			}
			
			responseObj.put("reviews", reviewObjects);			
			
			//Write JSON object to response
			PrintWriter writer = resp.getWriter();
			writer.write(responseObj.toString());
			writer.flush();
			// writer.println(responseObj.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
}