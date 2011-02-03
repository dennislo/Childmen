package childmen.gae.aggregator.dao.common;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import childmen.gae.aggregator.common.Constants;
import childmen.gae.aggregator.common.Convert;
import childmen.gae.aggregator.common.Utility;

public class QueryParamsUtility {
	
	public static void HandleDoubleQueryParamFilters(List<Filter> filters, Map<String, String[]> paramsMap)
	{
		String filterProperty;		
		if(paramsMap.containsKey(Constants.QueryParams.REVIEWSTARTDATE) && paramsMap.containsKey(Constants.QueryParams.REVIEWENDDATE))
		{	
			Date filterValue;
			
			//Add publisheddate >= reviewstartdate filter
			filterProperty = Constants.Review.PUBLISHEDDATE + " >="; 			
			filterValue = GetDateFilterValue(paramsMap.get(Constants.QueryParams.REVIEWSTARTDATE));			
			Filter reviewStartDateFilter = new Filter(filterProperty, filterValue);
			filters.add(reviewStartDateFilter);

			//Add publisheddate <= reviewenddate filter
			filterProperty = Constants.Review.PUBLISHEDDATE + " <=";; 			
			filterValue = GetDateFilterValue(paramsMap.get(Constants.QueryParams.REVIEWENDDATE));			
			Filter reviewEndDateFilter = new Filter(filterProperty, filterValue);
			filters.add(reviewEndDateFilter);
			
			//Remove from qp's since it's been handled
			paramsMap.remove(Constants.QueryParams.REVIEWSTARTDATE);
			paramsMap.remove(Constants.QueryParams.REVIEWENDDATE);	
		}
		else if(paramsMap.containsKey(Constants.QueryParams.METARATINGSTART) && paramsMap.containsKey(Constants.QueryParams.METARATINGEND))
		{
			double filterValue;
			
			//Add metarating >= metaratingstart filter
			filterProperty = Constants.Game.METARATING + " >="; 			
			filterValue = GetDoubleFilterValue(paramsMap.get(Constants.QueryParams.METARATINGSTART));			
			Filter metaratingStartFilter = new Filter(filterProperty, filterValue);
			filters.add(metaratingStartFilter);
				
			//Add metarating <= metaratingend filter
			filterProperty = Constants.Game.METARATING + " <="; 			
			filterValue = GetDoubleFilterValue(paramsMap.get(Constants.QueryParams.METARATINGEND));			
			Filter metaratingEndFilter = new Filter(filterProperty, filterValue);
			filters.add(metaratingEndFilter);
			
			//Remove from qp's since it's been handled
			paramsMap.remove(Constants.QueryParams.METARATINGSTART);
			paramsMap.remove(Constants.QueryParams.METARATINGEND);	
		}
	}
	
	public static Date GetDateFilterValue(String[] ParamValues)
	{		
		Collection<String> dateCollection = (Collection<String>)Utility.toCollection(ParamValues);		
		String dateString = Utility.join(dateCollection, " "); 		
		
		Date value = Convert.StringToDateTime(dateString);
		return value;
	}
	
	public static double GetDoubleFilterValue(String[] ParamValues)
	{		
		Collection<String> doubleCollection = (Collection<String>)Utility.toCollection(ParamValues);		
		String doubleString = Utility.join(doubleCollection, " "); 		
		
		Double value = Double.parseDouble(doubleString); 
		return value;
	}
	
	public static String GetStringFilterValue(String[] ParamValues)
	{
		Collection<String> stringCollection = (Collection<String>)Utility.toCollection(ParamValues);		
		String value = Utility.join(stringCollection, " "); 		
				
		return value;
	}
	
	public static int GetIntFilterValue(String[] ParamValues)
	{
		Collection<String> stringCollection = (Collection<String>)Utility.toCollection(ParamValues);		
		String intString = Utility.join(stringCollection, " "); 		

		int value = Integer.parseInt(intString);
		return value;
	}
	/*
	public static void HandleDoubleQueryParamFilters(List<Filter> filters, Map<String, String[]> paramsMap)
	{
		if(paramsMap.containsKey(Constants.QueryParams.REVIEWSTARTDATE) && paramsMap.containsKey(Constants.QueryParams.REVIEWENDDATE))
		{
			Collection<String> reviewStartDates = (Collection<String>)Utility.toCollection(paramsMap.get(Constants.QueryParams.REVIEWSTARTDATE));
			Collection<String> reviewEndDates = (Collection<String>)Utility.toCollection(paramsMap.get(Constants.QueryParams.REVIEWENDDATE));
			String reviewStartDate = Utility.join(reviewStartDates, " "); 
			String reviewEndDate = Utility.join(reviewEndDates, " ");
			
			Date rStartDate = Convert.StringToDateTime(reviewStartDate);
			Date rEndDate = Convert.StringToDateTime(reviewEndDate);
			
			String filterProperty;
			Date filterValue;
			
			//Add publisheddate >= reviewstartdate filter
			filterProperty = Constants.Review.PUBLISHEDDATE + " >=";
			filterValue = rStartDate;
			Filter reviewStartDateFilter = new Filter(filterProperty, filterValue);
			filters.add(reviewStartDateFilter);
			
			
			//Add publisheddate <= reviewenddate filter
			filterProperty = Constants.Review.PUBLISHEDDATE + " <=";
			filterValue = rEndDate;
			Filter reviewEndDateFilter = new Filter(filterProperty, filterValue);
			filters.add(reviewEndDateFilter);
			
			//remove as iterator doesn't need to process it
			paramsMap.remove(Constants.QueryParams.REVIEWSTARTDATE);
			paramsMap.remove(Constants.QueryParams.REVIEWENDDATE);	
					
		}
	}*/
	
	public static void GetFiltersFromQueryParams(List<Filter> filters, Map<String, String[]> paramsMap)
	{
		if(paramsMap.containsKey(Constants.QueryParams.OUTPUTPRETTY))
		{
			paramsMap.remove(Constants.QueryParams.OUTPUTPRETTY); //ignore outputpretty by removal
		}		
						
		// Iterate through map to extract filters 
		Iterator<Map.Entry<String, String[]>> it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<String, String[]> pairs = it.next();

			String paramName = pairs.getKey();
		
			//Collection<String> paramValues = (Collection<String>)Utility.toCollection(pairs.getValue());
			//String paramValue = Utility.join(paramValues," ");
			String paramValue = GetStringFilterValue(pairs.getValue());
				
			String filterProperty = paramName;
			try {
				Double filterValue = Double.parseDouble(paramValue);
				Filter newFilter = new Filter(filterProperty, filterValue);
				filters.add(newFilter);
			}
			catch(NumberFormatException e)
			{
				String filterValue = paramValue;
				Filter newFilter = new Filter(filterProperty, filterValue);
				filters.add(newFilter);
			}			
		}	
	}
		
	public static void HandleDoubleQueryParamSortOrders(List<String> sortOrders, Map<String, String[]> paramsMap)
	{
		if(paramsMap.containsKey(Constants.QueryParams.SORTON) && paramsMap.containsKey(Constants.QueryParams.SORTORDER))
		{
			Collection<String> sortOns = (Collection<String>)Utility.toCollection(paramsMap.get(Constants.QueryParams.SORTON));
			Collection<String> _sortOrders = (Collection<String>)Utility.toCollection(paramsMap.get(Constants.QueryParams.SORTORDER));			
			
			String sortOn = Utility.join(sortOns, " "); 
			String sortOrder = Utility.join(_sortOrders, " ");
			
			String orderProperty = "";			
			if(sortOrder.equals("desc"))
			{
				orderProperty = "-" + sortOn; //descending order				
			}
			else if (sortOrder.equals("asc"))
			{
				orderProperty = sortOn; //ascending order
			}
			else  
			{
				orderProperty = "-" + sortOn; //default to descending order
			}
			
			sortOrders.add(orderProperty);
			
			//Remove from qp's since it's been handled
			paramsMap.remove(Constants.QueryParams.SORTON);
			paramsMap.remove(Constants.QueryParams.SORTORDER);
					
		}
	}
	
	public static int GetLimit(Map<String, String[]> paramsMap)
	{
		int limit = 0;
		if(paramsMap.containsKey(Constants.QueryParams.LIMIT))
		{
			Collection<String> limits = (Collection<String>) Utility.toCollection(paramsMap.get(Constants.QueryParams.LIMIT));
			String limitString = Utility.join(limits, " ");			
			limit = Integer.parseInt(limitString);
		}
		return limit;		
	}
}
