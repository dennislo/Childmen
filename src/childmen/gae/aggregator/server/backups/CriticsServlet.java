package childmen.gae.aggregator.server.backups;

import java.io.IOException;
import java.util.HashMap;
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
import childmen.gae.aggregator.server.example.Initialise;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class CriticsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Initialise.DeleteGamesForTesting();
		Initialise.CreateGamesForTesting();
		Initialise.DeleteCriticsForTesting();
		Initialise.CreateCriticsForTesting();
		
		String gameName = "black ops"; //get from request parameter					
		
		//Test to get all reviews and it's critic 
		//Create required DAO Factory and get DAO
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		CriticDAO ofyCriticDAO = objectifyFactory.getCriticDAO();
		
		
		List<Review> retrievedReviews = ofyReviewDAO.listReviewsByGameName(gameName);
		Map<Review, Critic> rcMap = new HashMap<Review, Critic>();
		
		for(Review r : retrievedReviews)
		{		
			Critic c = null;
			try {
				c = ofyCriticDAO.get(r.getCritic());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
			
			rcMap.put(r, c);
		}
		
		BaseServlet.WriteResponse(rcMap, req, resp);
	}
}
