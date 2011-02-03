package childmen.gae.aggregator.server.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import childmen.gae.aggregator.common.Convert;

import com.google.appengine.repackaged.org.json.JSONException;


/**
 * Example processing URL query parameters
 * @author Dennis
 *
 */
@SuppressWarnings("serial")
public class ExamplePostData extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {		
		
		Map<String, String[]> paramsMap = (Map<String, String[]>)req.getParameterMap();
		/*		
		JSONObject responseObj = new JSONObject();
		try {
			responseObj = Convert.QueryParamsPostDataToJSON(paramsMap); //use helper to create JSON response
		} catch (JSONException e) {

			e.printStackTrace();
		} */
		
		// Write JSON object to response
		PrintWriter writer = resp.getWriter();
		try {
			writer.write(Convert.QueryParamsPostDataToJSON(paramsMap));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writer.flush();
	}

}