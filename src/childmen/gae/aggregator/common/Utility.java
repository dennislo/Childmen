package childmen.gae.aggregator.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Contains a random collection of reusable methods
 * @author Dennis
 */
public class Utility {

	public static String GetSydneyTimeString() {
		Calendar sydneyTime = Calendar.getInstance(TimeZone
				.getTimeZone("Australia/Sydney"));
		Date today = sydneyTime.getTime();
		String output = Convert.DateTimeToString(today);
		return output;
	}

	public static String join(Collection<?> s, String delimiter) {
		StringBuilder builder = new StringBuilder();
		Iterator<?> iter = s.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (!iter.hasNext()) {
				break;
			}
			builder.append(delimiter);
		}
		return builder.toString();
	}

	public static Collection<?> toCollection(Object[] objectArray) {
		Collection c = new ArrayList();
		for (Object object : objectArray) {
			c.add(object);
		}
		return c;
	}

	/**
	 * Checks whether the specified class contains a field matching the
	 * specified name.
	 * 
	 * @param clazz
	 *            The class to check.
	 * @param fieldName
	 *            The field name.
	 * 
	 * @return Returns <code>true</code> if the cass contains a field for the
	 *         specified name, <code>
	 *         false</code> otherwise.
	 */
	public static boolean containsField(Class<?> clazz, String fieldName) {
		try {
			clazz.getDeclaredField(fieldName);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}


}
