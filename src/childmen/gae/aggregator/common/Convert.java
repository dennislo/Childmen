package childmen.gae.aggregator.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.gson.Gson;

public class Convert {
	
	public static String DateToString(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");				
		String convertedDate = df.format(date);						
		return convertedDate;
	}
	
	public static String DateTimeToString(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");				
		String convertedDate = df.format(date);						
		return convertedDate;
	}
	
	public static Date StringToDateTime(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd"); //e.g. input: 20101225 
		Date convertedDate = null;
        try {
        	convertedDate  = df.parse(dateString);                        
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return convertedDate; //e.g. output: Sat Dec 25 00:00:00 EST 2010
	}
	
	public static Date StringToDateWithInputFormat(String dateString, String format)
	{
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy"); //e.g. input: "Jan 23, 2011"
		Date convertedDate = null;
        try {
        	convertedDate  = df.parse(dateString);                        
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return convertedDate; //e.g. output: Sun Jan 23 00:00:00 EST 2011
	}
	
	
	/**
	 * Takes a map of query parameters and converts it into a JSON object
	 * @param paramsMap - query parameters from the HttpServlet request object
	 * @return JSONObject - JSONObject
	 * @throws JSONException
	 */
	public static String QueryParamsPostDataToJSON(Map<String, String[]> paramsMap) throws JSONException
	{		
		/*JSONObject responseObj = new JSONObject();
		List<JSONObject> queryParametersObjects = new LinkedList<JSONObject>();

		try {
			// Iterate through map to create JSON output
			Iterator<Map.Entry<String, String[]>> it = paramsMap.entrySet().iterator();
			while (it.hasNext()) {

				JSONObject qpObj = new JSONObject();

				Map.Entry<String, String[]> pairs = it.next();

				String paramName = pairs.getKey();
				String[] paramValues = pairs.getValue();

				String paramValue = "";
				for (String value : paramValues) {
					paramValue += value;
				}

				qpObj.put("ParameterName", paramName);
				qpObj.put("ParameterValue", paramValue);

				queryParametersObjects.add(qpObj);
			}

			responseObj.put("QueryParameters", queryParametersObjects);
			
			return responseObj;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return null;*/
		
		Gson gson = new Gson();
		String json = gson.toJson(paramsMap);
		return json;
	}
}
