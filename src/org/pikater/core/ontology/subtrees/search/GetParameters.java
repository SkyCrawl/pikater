package org.pikater.core.ontology.subtrees.search;

import java.util.List;

import org.pikater.core.ontology.subtrees.newOption.NewOption;
import org.pikater.core.ontology.subtrees.search.searchItems.SearchItem;

import jade.content.AgentAction;

public class GetParameters implements AgentAction{

	private static final long serialVersionUID = -4554163588726699351L;
	
	private List<SearchItem> schema; // List of Options
	private List<NewOption> search_options;
	
	public List<SearchItem> getSchema() {
		return schema;
	}
	public void setSchema(List<SearchItem> schema) {
		this.schema = schema;
	}
	public List<NewOption> getSearch_options() {
		return search_options;
	}
	public void setSearch_options(List<NewOption> search_options) {
		this.search_options = search_options;
	}

}
