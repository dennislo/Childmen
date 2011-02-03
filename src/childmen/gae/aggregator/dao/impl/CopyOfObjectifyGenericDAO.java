package childmen.gae.aggregator.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import childmen.gae.aggregator.model.Critic;
import childmen.gae.aggregator.model.Game;
import childmen.gae.aggregator.model.Location;
import childmen.gae.aggregator.model.Platform;
import childmen.gae.aggregator.model.Review;
import childmen.gae.aggregator.model.UserRating;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

/**
 * Generic DAO with common CRUD operations specific to Objectify
 * 
 * @see source
 *      http://turbomanage.wordpress.com/2010/01/28/simplify-with-objectify/
 * @see source 
 *      http://turbomanage.wordpress.com/2010/02/09/generic-dao-for-objectify-2/
 * @author Dennis
 * @param <T>
 */
public class CopyOfObjectifyGenericDAO<T> extends DAOBase {

	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC
			| Modifier.TRANSIENT;

	static {
		/*
		 * Register all classes
		 */
		ObjectifyService.register(Review.class);
		ObjectifyService.register(Game.class);
		ObjectifyService.register(Critic.class);
		ObjectifyService.register(UserRating.class);
		ObjectifyService.register(Location.class);
		ObjectifyService.register(Platform.class);
	}

	protected Class<T> clazz;

	// public ObjectifyGenericDAO() //Hack for ObjectifyDAOFactory
	// {
	// // initialisation
	// service = ObjectifyService.begin();
	// };

	/**
	 * We've got to get the associated domain class somehow
	 * 
	 * @param clazz
	 */
	protected CopyOfObjectifyGenericDAO(Class<T> clazz) {
		// association
		this.clazz = clazz;
	}

	/* Generic CRUD */
	public Key<T> put(T entity) {
		return ofy().put(entity);
	}

	public Map<Key<T>, T> putAll(Iterable<T> entities) {
		return ofy().put(entities);
	}

	public void delete(T entity) {
		ofy().delete(entity);
	}

	public void deleteKey(Key<T> entityKey) {
		ofy().delete(entityKey);
	}

	public void deleteAll(Iterable<T> entities) {
		ofy().delete(entities);
	}

	public void deleteKeys(Iterable<Key<T>> keys) {
		ofy().delete(keys);
	}

	public T get(Long id) throws EntityNotFoundException {
		return ofy().get(this.clazz, id);
	}

	public T get(Key<T> key) throws EntityNotFoundException {
		return ofy().get(key);
	}

	/**
	 * Convenience method to get first object matching a single property.
	 * 
	 * @param propName
	 * @param propValue
	 *            this is CaSe SeNsItIvE
	 * @return T matching Object
	 */
	public T getByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.get();
	}

	/*
	 * public List<T> listByProperty(String propName, Object propValue) {
	 * Query<T> q = ofy().query(clazz); q.filter(propName, propValue); return
	 * asList(q); }
	 */

	/**
	 * Convenience method to get all entities of this class by property
	 * 
	 * @param propName
	 *            - Property in class
	 * @param propValue
	 *            - Value of Property
	 * @return list of entities
	 * @author Dennis
	 */
	public List<T> listByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		return q.filter(propName, propValue).list();
	}

	/**
	 * Convenience method to get all entities of this class
	 * 
	 * @return list of entities
	 * @author Dennis
	 */
	public List<T> listByClass() {
		Query<T> q = ofy().query(clazz);
		return asList(q);
	}

	/**
	 * Convenience method to get entity key by property NOTE: requires entities
	 * to be have an @id of type Long
	 * 
	 * @param propName
	 *            - Property in class
	 * @param propValue
	 *            - Value of Property
	 * @return key of entity
	 * @author Dennis
	 * @usage ofyGameDAO.getKeyByProperty("id", game.getId());
	 */
	public Key<T> getKeyByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		Key<T> key = q.filter(propName, propValue).getKey();
		return key;
	}

	/**
	 * Convenience method to get all entity keys of this class
	 * 
	 * @return list of keys
	 * @author Dennis
	 */
	public List<Key<T>> listKeys() {
		// Query<T> q = ofy().query(clazz);
		// return asKeyList(q.fetchKeys());
		return ofy().query(clazz).listKeys();
	}

	public List<Key<T>> listKeysByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return asKeyList(q.fetchKeys());
	}

	public T getByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		QueryResultIterator<T> iterator = queryByExample.iterator(); // returns
																		// cursor
		T obj = iterator.next();
		if (iterator.hasNext())
			throw new RuntimeException("Too many results");
		return obj;
	}

	public List<T> listByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return asList(queryByExample);
	}

	private List<T> asList(Query<T> q) {
		List<T> list = new ArrayList<T>();
		for (T t : q) {
			list.add(t);
		}
		return list;
	}

	private List<Key<T>> asKeyList(Iterable<Key<T>> iterableKeys) {
		ArrayList<Key<T>> keys = new ArrayList<Key<T>>();
		for (Key<T> key : iterableKeys) {
			keys.add(key);
		}
		return keys;
	}

	private Query<T> buildQueryByExample(T exampleObj) {
		Query<T> q = ofy().query(clazz);

		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields()) {
			// Ignore transient, embedded, array, and collection properties
			if (field.isAnnotationPresent(Transient.class)
					|| (field.isAnnotationPresent(Embedded.class))
					|| (field.getType().isArray())
					|| (Collection.class.isAssignableFrom(field.getType()))
					|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
				continue;

			field.setAccessible(true);

			Object value;
			try {
				value = field.get(exampleObj);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			if (value != null) {
				q.filter(field.getName(), value);
			}
		}

		return q;
	}

	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	
	
	
	
	/*
	 * Convenience methods for updating multiple properties of an entity support
	 * embedded properties update (multi-level).
	 */

	public T updateProperties(T entity, List properties) throws Exception {
		List eps = escapeProperties(properties);
		for (Property p : eps) {
			entity = updateProperty(entity, p);
		}
		return entity;
	}

	public T updateProperty(T entity, Property property) throws Exception {
		String[] fieldNames = property.getPropertyName().split("\\.");
		int len = fieldNames.length;
		Object entityField = entity;
		for (int i = 0; i < len - 1; i++) {
			entityField = getField(entityField, fieldNames[i]);
		}
		setField(entityField, fieldNames[len - 1], property.getPropertyValue());
		return entity;
	}

	public List escapeProperties(Iterable properties) {
		/*
		 * TODO: Security and performances issues should be treated here. Entity
		 * classes should override this method
		 */
		ArrayList eps = new ArrayList();
		for (Property p : properties) {
			eps.add(p);
		}
		return eps;
	}

	public Object getField(Object obj, String fieldName) throws Exception {
		String methName = "get" + fieldName.toUpperCase().charAt(0)
				+ fieldName.substring(1);
		Method meth = obj.getClass().getMethod(methName);
		return meth.invoke(obj);
	}

	public void setField(Object obj, String fieldName, Object value)
			throws Exception {
		String methName = "set" + fieldName.toUpperCase().charAt(0)
				+ fieldName.substring(1);
		Class parametersTypes[] = new Class[1];
		parametersTypes[0] = value.getClass();
		Method meth = obj.getClass().getMethod(methName, parametersTypes);
		Object argList[] = new Object[1];
		argList[0] = value;
		meth.invoke(obj, argList);
	}

	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Convenience method to build all possible get Queries
	 * 
	 * @param filters
	 *            :query filters
	 * @param sortOrders
	 *            : query sort orders
	 * @param limit
	 *            : number of maximum fetched results
	 * @return Query
	 */

	public Query buildQuery(Iterable filters, Iterable sortOrders, int limit) {
		Query q = ofy().query(clazz);

		List escapedFilters = escapeFilters(filters);
		List escapedSortOrders = escapeSortOrders(sortOrders);
		int escapedLimit = escapeFetchLimit(limit);

		for (Filter f : escapedFilters) {
			q.filter(f.getFilterCondition(), f.getFilterValue());
		}
		for (String so : escapedSortOrders) {
			q.order(so);
		}
		q.limit(escapedLimit);
		return q;
	}

	public List escapeFilters(Iterable filters) {
		/*
		 * TODO: Security and performances issues should be treated here Entity
		 * classes should override this method
		 */
		ArrayList efs = new ArrayList();
		for (Filter f : filters) {
			efs.add(f);
		}
		return efs;
	}

	public List escapeSortOrders(Iterable sortOrders) {
		/*
		 * TODO: Security and performances issues should be treated here Entity
		 * classes should override this method
		 */
		ArrayList esos = new ArrayList();
		for (String so : sortOrders) {
			esos.add(so);
		}

		return esos;
	}

	public int escapeFetchLimit(int limit) {
		/*
		 * TODO: Security and performances issues should be treated here Entity
		 * classes should override this method example:
		 */
		int classSpecificLimit = 51;
		if (limit >= classSpecificLimit) {
			return classSpecificLimit;
		}
		return limit;
	}
	
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Convenience method to get all objects for all possible get Queries
	 * 
	 * @param filters
	 *            :query filters
	 * @param sortOrders
	 *            : query sort orders
	 * @param limit
	 *            : number of maximum fetched objects
	 * @return List
	 */

	public List listByQuery(Iterable filters, Iterable sortOrders, int limit) {
		Query q = buildQuery(filters, sortOrders, limit);
		return q.list();
	}



	/**
	 * Convenience method to get all object keys for all possible get Queries
	 * 
	 * @param filters
	 *            :query filters
	 * @param sortOrders
	 *            : query sort orders
	 * @param limit
	 *            : number of maximum fetched keys
	 * @return List<Key>
	 */

	public List<Key> listKeysByQuery(Iterable filters, Iterable sortOrders,
			int limit) {
		Query q = buildQuery(filters, sortOrders, limit);
		return asKeyList(q.fetchKeys());
	}

	private List<Key> asKeyList(Iterable<Key> iterableKeys) {
		ArrayList<Key> keys = new ArrayList<Key>();
		for (Key key : iterableKeys) {
			keys.add(key);
		}
		return keys;
	}
}