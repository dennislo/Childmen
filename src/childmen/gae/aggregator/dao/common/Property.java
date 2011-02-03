package childmen.gae.aggregator.dao.common;

import java.io.Serializable;

public class Property implements Serializable {

	private static final long serialVersionUID = -7165134355494456946L;

	private String propertyName;
	private Serializable propertyValue;

	private Property() {
	}
	
	public Property(String propertyName, Serializable propertyValue) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the propertyValue
	 */
	public Serializable getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(Serializable propertyValue) {
		this.propertyValue = propertyValue;
	}

}