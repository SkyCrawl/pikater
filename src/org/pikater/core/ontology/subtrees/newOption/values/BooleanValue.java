package org.pikater.core.ontology.subtrees.newOption.values;

public class BooleanValue implements ITypedValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 486219518827018753L;

	private boolean value;
	
	/*
	 * Default constructor is needed.
	 */
	public BooleanValue() {}
	public BooleanValue(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public ITypedValue clone() {
		
		BooleanValue valueNew = new BooleanValue();
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
		return "Boolean";
	}
}