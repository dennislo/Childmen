package childmen.gae.aggregator.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LocationsServlet extends HttpServlet {
		
	//Create request handler
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Locations Servlet is not implemented yet");
	}
	
	//Retrieve request handler
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Locations Servlet is not implemented yet");
	}
	
	//Update request handler	
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{					
		resp.setContentType("text/plain");
		resp.getWriter().println("Locations Servlet is not implemented yet");
	}
	
	//Delete request handler	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Locations Servlet is not implemented yet");
	}
}
