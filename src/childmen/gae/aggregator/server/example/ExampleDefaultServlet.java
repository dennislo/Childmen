package childmen.gae.aggregator.server.example;

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
 * 
 * NOTE: To run this example servlet add the following to web.xml
 * 
 * 	<!-- DEFAULT servlet mapping -->
 *	<servlet>
 *		<servlet-name>Default servlet</servlet-name>
 *		<servlet-class>childmen.gae.aggregator.server.example.ExampleDefaultServlet</servlet-class>
 *	</servlet>
 *	<servlet-mapping>
 *		<servlet-name>Default servlet</servlet-name>
 *		<url-pattern>/</url-pattern>
 *	</servlet-mapping>
 * 
 * 
 * 
 * This Default Servlet is the entry point into our Server 
 * This servlet mapped with the URL Pattern of / within web.xml
 * @author Dennis
 */
@SuppressWarnings("serial")
public class ExampleDefaultServlet extends HttpServlet {

	private RequestDispatcher _exampleQueryParametersDispatcher;
	
	private ServletMatcher _matcher;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config); //so you can call getInitParameter(...); in other methods

		//1. Initialise request dispatches
		_exampleQueryParametersDispatcher = this.getServletContext().getRequestDispatcher("/examplequeryparametersservlet");
		//_servletXDispatcher = this.getServletContext().getRequestDispatcher("/examplepostdata");
		
		
		//2. Put all URL to Servlet mappings below
		Map<String, RequestDispatcher> rdMap = new HashMap<String, RequestDispatcher>(); //map key is regex of url 
		
		rdMap.put("queryparameters.*", _exampleQueryParametersDispatcher); //handled by ExampleQueryParameters servlet
		//rdMap.put(".*/entities.*", _exampleQueryParametersDispatcher);
		//rdMap.put(".*/media.*", _exampleQueryParametersDispatcher);
		//rdMap.put(".*/actions.*", _exampleQueryParametersDispatcher);				
		//rdMap.put("popular/*", _exampleQueryParametersDispatcher);
		//rdMap.put("media/.*", _exampleQueryParametersDispatcher);

		_matcher = new ServletMatcher();

		_matcher.load(rdMap);
				
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		requestHandler(req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		requestHandler(req,resp);
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		requestHandler(req,resp);
	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		requestHandler(req,resp);
	}
	
	private void requestHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String uriPattern = req.getRequestURI(); 
		RequestDispatcher requestDispatcher = _matcher.match(uriPattern);

		try {						
			//requestDispatcher.include(req, resp);			
			requestDispatcher.forward(req, resp); //forward requests with headers, query strings			
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
