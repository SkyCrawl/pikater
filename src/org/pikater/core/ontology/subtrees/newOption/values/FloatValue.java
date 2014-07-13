package org.pikater.core.ontology.subtrees.newOption.values;

public class FloatValue implements ITypedValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6154392916809467193L;
	
	private float value;
	
	/*
	 * Default constructor is needed.
	 */
	public FloatValue() {}
	public FloatValue(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	@Override
	public ITypedValue clone() {
		
		FloatValue valueNew = new FloatValue();
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
		return "Float";
	}
}
