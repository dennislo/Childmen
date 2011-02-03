package childmen.gae.aggregator.server;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Constants;
import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.GameDAO;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Response;
import childmen.gae.aggregator.model.Review;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class GameDetailsServlet extends BaseServlet<Game> {
	
	//Retrieve request handler
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{		
		resp.setContentType("text/plain");		
		
		String requestURI = (String) req.getSession().getAttribute("uriPattern");	
		String[] uriParts = requestURI.split("/");		
		
		Long gameId = Long.parseLong(uriParts[2]);
				
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		GameDAO ofyGameDAO = objectifyFactory.getGameDAO();
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		CriticDAO ofyCriticDAO = objectifyFactory.getCriticDAO();
				
		//Retrieve game using game id		
		Game game = ofyGameDAO.getByProperty("id", gameId);
		
		//Retrieve reviews of game
		List<Review> reviews = null;
		if(game != null)
		{
			reviews = (List<Review>) ofyReviewDAO.listReviewsByGame(game);
		}
		else 
		{
			String error = Constants.Error.NoResults + " at " + requestURI; //print error 
			WriteResponse(error, req, resp);			
		}
						
		
		//Retrieve critics of reviews 
		Map<Review, Critic> critics = new LinkedHashMap<Review, Critic>(); //predictable iteration order
		
		for (Review r : reviews) {
			Critic c = null;
			try {
				c = ofyCriticDAO.get(r.getCritic());
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}

			critics.put(r, c);
		}
		
		Response r = new Response();
		Response.GameDetails gameDetails = r.new GameDetails(game, reviews, critics);
		
		WriteResponse(gameDetails, req, resp);
				
	}	
}
