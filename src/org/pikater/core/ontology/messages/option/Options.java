package org.pikater.core.ontology.messages.option;

import java.util.List;

import jade.content.Concept;

public class Options implements Concept{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8578686409784032991L;
	private List<Option> list;
	
	public void setList(List<Option> list) {
		this.list = list;
	}
	public List<Option> getList() {
		return list;
	}
	
	public Options(List<Option> list){
		setList(list);
	}
	public Options(){
	}
}
