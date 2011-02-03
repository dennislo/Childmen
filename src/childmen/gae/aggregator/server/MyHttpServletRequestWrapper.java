package childmen.gae.aggregator.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Customer HttpServletRequestWrapper
 * Purpose: currently only used to remove the "outputpretty" query parameter from HttpServletRequest obj 
 *			before it is passed to the other servlets
 * @author Dennis
 */
public class MyHttpServletRequestWrapper  extends HttpServletRequestWrapper
{
	private Map<String, String[]> modifiableParameters = null;
   
    
    /**
     * Create a new request wrapper that will merge additional parameters into
     * the request object without prematurely reading parameters from the
     * original request.
     * 
     * @param request
     * @param additionalParams
     */
    public MyHttpServletRequestWrapper(HttpServletRequest request) {    	        
        super(request); //init with HttpServletRequestWrapper constructor
	}

    @Override
    public Map<String, String[]> getParameterMap()
    {
        if(modifiableParameters == null)
        {
        	modifiableParameters = new HashMap<String, String[]>(super.getParameterMap()); //create a copy of the original query params map
        	if(modifiableParameters.containsKey("outputpretty"))
        	{
        		modifiableParameters.remove("outputpretty");
        	}
        }
        //Return an unmodifiable collection because we need to uphold the interface contract.
        return Collections.unmodifiableMap(modifiableParameters);        
    }


    


}
