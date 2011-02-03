package childmen.gae.aggregator.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;

public class ServletMatcher {

	Map<Pattern, RequestDispatcher> _patternsMatchServlets;

	public ServletMatcher() {
		_patternsMatchServlets = new HashMap<Pattern, RequestDispatcher>();
	}

	/**
	 * Create key/value pairs i.e. URL patterns/Servlet request dispatchers
	 * @param servletMap - Map with URL patterns/Servlet request dispatcher pairs
	 * @author Dennis
	 */
	public void load(Map<String, RequestDispatcher> servletMap) {

		Set<String> keys = servletMap.keySet();

		for (String key : keys) {

			_patternsMatchServlets.put(Pattern.compile(key), servletMap.get(key));
		}
	}

	/**
	 * Returns the Servlet Request dispatcher of corresponding URL pattern
	 * @param uriPattern - URL pattern of the Servlet request dispatcher
	 * @return Servlet request dispatcher 
	 */
	public RequestDispatcher match(String uriPattern) {
		RequestDispatcher servletDispatcher = null;
		boolean matches = false;
		Set<Pattern> patterns = _patternsMatchServlets.keySet();

		for (Pattern pattern : patterns) {
			
			Matcher match = pattern.matcher(uriPattern);

			matches = match.find();
			if (matches == true) {
				servletDispatcher = (RequestDispatcher) _patternsMatchServlets.get(pattern);
				break;
			}

		}

		return servletDispatcher;
	}
}
