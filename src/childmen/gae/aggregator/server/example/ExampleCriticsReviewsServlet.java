package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Review;
import childmen.gae.aggregator.server.BaseServlet;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * Example: Get Critics from Review Method: get() with Key<T>
 */
@SuppressWarnings("serial")
public class ExampleCriticsReviewsServlet extends HttpServlet {

	//TODO: Fix this example
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		

		//Create required DAO Factory and get DAO
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);

		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		CriticDAO ofyCriticDAO = objectifyFactory.getCriticDAO();

		//Create and store critics and reviews		
		Initialise.DeleteCriticsForTesting();
		Initialise.CreateCriticsForTesting();
		Initialise.DeleteReviewsForTesting();
		Initialise.CreateReviewsForTesting();				

		List<Critic> critics = ofyCriticDAO.listByClass();
		
		//Associate review with critic
		List<Review> retrievedReviews = ofyReviewDAO.listByClass(); // ofyReviewDAO.listReviewsByGameName(gameName);		
		
		for(int i=0; i < retrievedReviews.size(); i++) {
			Review r = retrievedReviews.get(i);			 
			Critic c = critics.get(i);
			
			//c.addReview(r); //link critic with review
			ofyCriticDAO.addReview(r, c);
		}

		//Retrive critic of review
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

		//Iterate through map 
	    Iterator<Map.Entry<Review, Critic>> it = rcMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Review, Critic> pairs = it.next();
	        
	        Review r = pairs.getKey();
	        Critic c = pairs.getValue();
	        
	        Long reviewId = r.getId();
	        String reviewLink = r.getReviewlink();
	        
	        String criticName = c.getName();
	        
	        String output = "reviewId = " + reviewId + " reviewLink = " + reviewLink + " criticName = " + criticName;	        	
	        System.out.println(output);	        
	    }

	    BaseServlet.WriteResponse(rcMap, req, resp);
	}
}
