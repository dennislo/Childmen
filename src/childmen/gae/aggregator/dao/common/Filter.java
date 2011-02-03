package childmen.gae.aggregator.dao.common;

import java.io.Serializable;

public class Filter implements Serializable {

	private static final long serialVersionUID = -7165134355494456946L;

	private String filterProperty;
	private Serializable filterValue;
	
	private Filter() {
	}
	
	public Filter(String filterProperty, Serializable filterValue) {
		super();
		this.filterProperty = filterProperty;
		this.filterValue = filterValue;
	}

	/**
	 * @return the filterProperty
	 */
	public String getFilterProperty() {
		return filterProperty;
	}

	/**
	 * @param filterProperty the filterProperty to set
	 */
	public void setFilterProperty(String filterProperty) {
		this.filterProperty = filterProperty;
	}

	/**
	 * @return the filterValue
	 */
	public Serializable getFilterValue() {
		return filterValue;
	}

	/**
	 * @param filterValue the filterValue to set
	 */
	public void setFilterValue(Serializable filterValue) {
		this.filterValue = filterValue;
	}

	



}