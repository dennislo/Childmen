package childmen.gae.aggregator.server;

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
import childmen.gae.aggregator.server.example.Initialise;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class CriticsServlet extends HttpServlet {

	//Create request handler
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Critics Servlet doPost is not implemented yet");
	}	
		
	//Retrieve request handler
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Critics Servlet doGet is not implemented yet");
	}
	
	//Update request handler
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{					
		resp.setContentType("text/plain");
		resp.getWriter().println("Critics Servlet doPut is not implemented yet");
	}
	
	//Delete request handler	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Critics Servlet doDelete is not implemented yet");
	}
}
