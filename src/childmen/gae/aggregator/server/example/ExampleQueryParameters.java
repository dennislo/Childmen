package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.model.TestModel;
import childmen.gae.aggregator.server.BaseServlet;

/**
 * Example processing URL query parameters
 * 
 * @author Dennis
 */
@SuppressWarnings("serial")
public class ExampleQueryParameters extends BaseServlet<TestModel> {
	
	public ExampleQueryParameters() {
		//super(TestModel.class);
	}

	public void service(HttpServletRequest req, HttpServletResponse resp)
	{			
		super.service(req, resp);	
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {	       		
		
		resp.setContentType("text/plain"); //required for pretty print		
			
		/*********************************************/
				
		//Get URI information
		
		String pathInfo = req.getPathInfo();
		String pathTrans = req.getPathTranslated();
		String uri = req.getRequestURI();
		StringBuffer rqsb = req.getRequestURL();
		String rq = rqsb.toString();
		String servletPath = req.getServletPath();
		String rp = req.getRealPath(servletPath);
		String ref = req.getHeader("referer");
		
		System.out.println("pathInfo:  " + pathInfo);
		System.out.println("pathTrans: " + pathTrans);
		System.out.println("uri:       " + uri);
		System.out.println("RequestURL:" + rq);
		System.out.println("ServletPath:" + servletPath);
		System.out.println("RealPath:" + rp);
		System.out.println("Referer:" + ref);
		
		/*********************************************/
				
		/*
		String outformat = "";
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = req.getParameter(paramName);

			if (paramName.equals("outformat")) {
				outformat = paramValue;
			}
		}*/

		//Get query parameters as a Map
		Map<String, String[]> paramsMap = (Map<String, String[]>) req.getParameterMap();
		
		//Determine output format
		if(paramsMap.containsKey("outformat"))
		{
			String value = req.getParameter("outformat");
			if (value.equals("JSON")) {
				outputJSON(paramsMap, req, resp);				
			} else {
				OutputToConsole(paramsMap);				
			}	
		}
		
	}

	private void outputJSON(Map<String, String[]> paramsMap, HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{	
		WriteResponse(paramsMap, req, resp);
	}


	public static void OutputToConsole(Map<String, String[]> paramsMap) 
	{
		// Iterate through map and output to console
		Iterator<Map.Entry<String, String[]>> it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<String, String[]> pairs = it.next();

			String paramName = pairs.getKey();
			String[] paramValues = pairs.getValue();

			String paramValue = "";
			for (String value : paramValues) {
				paramValue += value;
			}

			String output = "paramName: " + paramName + " //// paramValue: " + paramValue;
			System.out.println(output);

		}
	}

}
