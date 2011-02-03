package childmen.gae.aggregator.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.ServletMatcher;

/**
 * This Controller Servlet is the entry point into our Server. It redirects requests to your specified servlet 
 * @author Dennis
 */
@SuppressWarnings("serial")
public class ControllerServlet extends HttpServlet {

	private RequestDispatcher exampleqpServletDispatcher;
	
	//private RequestDispatcher initialiseServletDispatcher;
	//private RequestDispatcher reviewServletDispatcher;	
	//private RequestDispatcher gameServletDispatcher;	
	private RequestDispatcher gameDetailsServletDispatcher;
	
	private ServletMatcher _matcher;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		//1. Initialise request dispatches with web.xml URL patterns
		exampleqpServletDispatcher = this.getServletContext().getRequestDispatcher("/examplequeryparametersservlet");
		//initialiseServletDispatcher = this.getServletContext().getRequestDispatcher("/initialise");	
		//reviewServletDispatcher = this.getServletContext().getRequestDispatcher("/reviews");	
		//gameServletDispatcher = this.getServletContext().getRequestDispatcher("/games");
		gameDetailsServletDispatcher = this.getServletContext().getRequestDispatcher("/gamedetails");
		
		
		//2. Put custom URL patterns to Servlet mappings below 
		Map<String, RequestDispatcher> rdMap = new HashMap<String, RequestDispatcher>();
		
		
		//rdMap.put("/initialise", initialiseServletDispatcher);
		
		//rdMap.put("/games\\?.*", gameServletDispatcher);
		
		rdMap.put("/game/\\d+", gameDetailsServletDispatcher);
		
		//rdMap.put("/reviews.*", reviewServletDispatcher);
		
		rdMap.put("/qp.*", exampleqpServletDispatcher);
		//rdMap.put(".*/entities.*", _servletXDispatcher);
		//rdMap.put(".*/media.*", _servletXDispatcher);
		//rdMap.put(".*/actions.*", _servletXDispatcher);				
		//rdMap.put("popular/*", _servletXDispatcher);
		//rdMap.put("media/.*", _servletXDispatcher);

		_matcher = new ServletMatcher();
		_matcher.load(rdMap);				
		
	}

	
	//Create request handler
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		requestHandler(req,resp);
	}
	
	//Retrieve request handler
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		requestHandler(req,resp);
	}
		
	//Update request handler
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{					
		requestHandler(req,resp);
	}
	
	//Delete request handler	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		requestHandler(req,resp);
	}
	
	private void requestHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		//Handle request
		String uriPattern = req.getRequestURI(); 		
		RequestDispatcher requestDispatcher = _matcher.match(uriPattern);
		if(requestDispatcher != null)
		{
			try {
				
				System.out.println("uriPattern: " + uriPattern);
				
				req.getSession().setAttribute("uriPattern",uriPattern); //session variable with original request uri								
				
				requestDispatcher.forward(req, resp); //forward requests with headers, query strings			
			} catch (ServletException e) {
				e.printStackTrace();
			}			
		}
		else
		{
			requestDispatcher = this.getServletContext().getNamedDispatcher("/index.html"); //redirect to servlet listing
		}

	}	
}