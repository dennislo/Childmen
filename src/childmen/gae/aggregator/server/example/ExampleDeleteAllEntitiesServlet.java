package childmen.gae.aggregator.server.example;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Utility;

@SuppressWarnings("serial")
public class ExampleDeleteAllEntitiesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Initialise.DeleteLocationsForTesting();
		Initialise.DeletePlatformsForTesting();
		Initialise.DeleteReviewsForTesting();
		Initialise.DeleteGamesForTesting();		
		Initialise.DeleteCriticsForTesting();
				
		resp.getWriter().println("All data from data store was deleted at: " + Utility.GetSydneyTimeString());
				
	}
}

