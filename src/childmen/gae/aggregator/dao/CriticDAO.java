package childmen.gae.aggregator.dao;

import childmen.gae.aggregator.dao.impl.ObjectifyGenericDAO;
import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Review;

public abstract class CriticDAO extends ObjectifyGenericDAO<Critic>{
	
	protected CriticDAO() {
		super(Critic.class);				
	}
		
	/*Add Critic specific DAO methods below that are non generic*/
	public abstract void addReview(Review review, Critic critic);
}
