package org.pikater.core.ontology.subtrees.newOption.values;

public class DoubleValue implements ITypedValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1276470189024492227L;

	private double value;
	
	/*
	 * Default constructor is needed.
	 */
	public DoubleValue() {}
	public DoubleValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public ITypedValue clone() {
		
		DoubleValue valueNew = new DoubleValue();
		valueNew.setValue(value);
		
		return valueNew;
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
}
