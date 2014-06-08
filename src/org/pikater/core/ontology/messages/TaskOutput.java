package org.pikater.core.ontology.messages;

import jade.content.Concept;
import org.pikater.core.ontology.subtrees.task.Task.InOutType;

public class TaskOutput implements Concept{
	
	private static final long serialVersionUID = 2323906394322843296L;
	
	private InOutType type;
	private String name;	// name on the disc
	private Object value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InOutType getType() {
		return type;
	}
	public void setType(InOutType type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}	
	
}
