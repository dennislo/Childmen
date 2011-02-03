package childmen.gae.aggregator.server.backups;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Review;
import childmen.gae.aggregator.server.BaseServlet;
import childmen.gae.aggregator.server.example.Initialise;

@SuppressWarnings("serial")
public class ReviewsServlet extends BaseServlet<Review> {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {				

		resp.setContentType("text/plain");
		
		Map<String, String[]> paramsMap = new HashMap<String, String[]>(req.getParameterMap()); //make mutable copy of qp's
		
		Initialise.DeleteReviewsForTesting();		
		Initialise.CreateReviewsForTesting();
		
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		@SuppressWarnings("unused")
		List<Review> reviewsInDS = ofyReviewDAO.listByClass();
		
		List<Review> reviews = ofyReviewDAO.getReviewsByQueryParams(paramsMap);
		
		WriteResponse(reviews, req, resp);

	}	
	
}