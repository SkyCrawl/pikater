package org.pikater.core.ontology.subtrees.task;

import jade.content.Concept;

public class Eval implements Concept {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3186933368082724288L;

	private float maxValue = Float.MAX_VALUE;

	private String name;
	private Float value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		if (Float.isNaN(value)) {
			this.value = maxValue;
		} else {
			this.value = value;
		}
	}

}
