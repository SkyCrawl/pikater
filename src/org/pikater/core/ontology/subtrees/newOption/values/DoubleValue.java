package org.pikater.core.ontology.subtrees.newOption.values;

import org.pikater.core.ontology.subtrees.newOption.values.interfaces.IComparableValueData;
import org.pikater.core.ontology.subtrees.newOption.values.interfaces.IValueData;

public class DoubleValue implements IComparableValueData
{
	private static final long serialVersionUID = 1276470189024492227L;

	private Double value;
	
	/**
	 * Should only be used by JADE.
	 */
	@Deprecated
	public DoubleValue() {}
	public DoubleValue(double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public IValueData clone()
	{
		return new DoubleValue(value);
	}

	@Override
	public String exportToWeka() {
		
		return String.valueOf(value);
	}
	
	@Override
	public String toDisplayName()
	{
		return "Double";
	}
	
	@Override
	public int compareTo(IComparableValueData o)
	{
		return value.compareTo((Double) o.getValue());
	}
}
