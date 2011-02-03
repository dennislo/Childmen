package childmen.gae.aggregator.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Constants;
import childmen.gae.aggregator.common.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This Base Servlet should be inherited by all other servlets in this application 
 * @author Dennis
 */
@SuppressWarnings("serial")
public abstract class BaseServlet<T> extends HttpServlet {
	
	public final Logger log = Logger.getLogger("Aggregator log:");
	
	/**
	 * Boolean variable to determine if output should be pretty printed or compact printed
	 */
	public static boolean outputpretty;

	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
	{
		//Set pretty print attribute
		if (req.getParameterMap().containsKey(Constants.QueryParams.OUTPUTPRETTY))
		{
			String value = req.getParameter(Constants.QueryParams.OUTPUTPRETTY);			
			outputpretty = (value.toLowerCase().equals("true"))? true : false;				
		}
		else
		{
			outputpretty = false;
		}
		
		try {
			super.service(req, resp); //call HttpServlet's service(). This redirect to correct doXXX() method in the child servlet.
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public List<T> ConvertToObjectList(String json, Type listType)
	{
		Gson gson = new Gson();
		//Type listType = new TypeToken<List<T>>() {}.getType(); 
		List<T> list  = gson.fromJson(json,listType); //No-args constructor for class required
		return list;	
	}
		
	public static void WriteResponse(Object object, HttpServletRequest req, HttpServletResponse resp) throws IOException
	{						
		PrintWriter writer;	
		writer = resp.getWriter();
			
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		/*
		if(outputpretty)
		{									
			gson = new GsonBuilder().setPrettyPrinting().create();			
		}
		else
		{			 
			gson = new Gson();		
		}*/
						
		String output = gson.toJson(object); 	
		if(!output.equals("[]")) 
		{
			writer.write(output);
		}
		else
		{
			writer.write("Sorry, no results returned at " + req.getRequestURI() + " " + req.getParameterMap());
		}
		
		writer.flush();	
	}	
	
	public String WriteInfoToLog(String type, Long objectId)
	{
		String today = Utility.GetSydneyTimeString();
		String output = "object of type " + type + " with id " +  + objectId + " was created at " + today;
		log.info(output); //need to ensure logging.properties contains .level = INFO
		return output;
	}
}
