package childmen.gae.aggregator.dao.impl;

import childmen.gae.aggregator.dao.CriticDAO;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Review;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Critic DAO implementation for Objectify 
 * @author Dennis
 */
public class ObjectifyCriticDAO extends CriticDAO {

	private Objectify service;
	
	public ObjectifyCriticDAO() {
		// initialisation
		if(service == null) 
		{
			service = ObjectifyService.begin();
		}
	}
		
	/*Add Critic specific DAO methods below that are non generic*/
	
	/*
	 * Setting relationships
	 */	
	/**
	 * Add review to this Critic
	 * @param	
	 */
	@Override
	public void addReview(Review review, Critic critic) {
		review.setCritic(critic);		
		service.put(review);
	}

}
