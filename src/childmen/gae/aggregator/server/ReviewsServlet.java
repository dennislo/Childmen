package childmen.gae.aggregator.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.dao.DAOFactory;
import childmen.gae.aggregator.dao.ReviewDAO;
import childmen.gae.aggregator.model.Review;

@SuppressWarnings("serial")
public class ReviewsServlet extends BaseServlet<Review> {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {				

		resp.setContentType("text/plain");
		
		Map<String, String[]> paramsMap = new HashMap<String, String[]>(req.getParameterMap()); //Make mutable copy of qp's
		
		//Initialise.DeleteReviewsForTesting();		
		//Initialise.CreateReviewsForTesting();
		
		DAOFactory objectifyFactory = DAOFactory.getDAOFactory(DAOFactory.OBJECTIFY);
		ReviewDAO ofyReviewDAO = objectifyFactory.getReviewDAO();
		
		List<Review> reviews = ofyReviewDAO.getReviewsByQueryParams(paramsMap);
		
		WriteResponse(reviews, req, resp);

	}
	
	//Create request handler
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{					
		resp.setContentType("text/plain");
		resp.getWriter().println("Reviews Servlet doPut is not implemented yet");
	}	
	
	//Update request handler
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Reviews Servlet doPost is not implemented yet");
	}
	
	//Delete request handler	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Reviews Servlet doDelete is not implemented yet");
	}
	
}