package childmen.gae.aggregator.common;

public class QueryParameters {
		
	public enum Game
	{
		name,
		genre,
		blurb,
		metarating
	}

	
	public enum Review
	{
		publishdate
	}
	
	public enum Platform
	{
		name
	}
	
	public enum Filters
	{
		limit,
		time //7 days, 
	}
}
