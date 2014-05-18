package org.pikater.core.ontology.subtrees.oldPikaterMessages;

import org.pikater.core.ontology.subtrees.management.Agent;
import org.pikater.core.ontology.subtrees.task.EvaluationMethod;
import org.pikater.core.ontology.subtrees.task.Id;

import jade.content.Concept;
import jade.util.leap.List;

public class Problem implements Concept {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 7185046750972524624L;
	private Id _id;
	private String _gui_id;
	private String _status;  // status in GUI agent: new, sent, finished, failed, refused
	private List _agents;
	private List _data;
	private int _timeout;
	private Agent _method;
	private String _start;
	private String _get_results; // after_each_computation, after_each_task
	private String _gui_agent;
	private boolean _save_results;
	private String _name;
	private EvaluationMethod _evaluation_method;
	private Agent Recommender;
	
	public void setAgents(List agents) {
		_agents = agents;
	}

	public List getAgents() {
		return _agents;
	}

	public void setData(List data) {
		_data = data;
	}

	public List getData() {
		return _data;
	}

	public void setId(Id id) {
		_id = id;
	}

	public Id getId() {
		return _id;
	}

	public void setGui_id(String gui_id) {
		_gui_id = gui_id;
	}

	public String getGui_id() {
		return _gui_id;
	}

	public void setTimeout(int timeout) {
		_timeout = timeout;
	}

	public int getTimeout() {
		return _timeout;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public Agent getMethod() {
		return _method;
	}

	public void setMethod(Agent method) {
		_method = method;
	}

	public void setStart(String _start) {
		this._start = _start;
	}

	public String getStart() {
		return _start;
	}

	public void setGet_results(String _get_results) {
		this._get_results = _get_results;
	}

	public String getGet_results() {
		return _get_results;
	}

	public void setGui_agent(String _gui_agent) {
		this._gui_agent = _gui_agent;
	}

	public String getGui_agent() {
		return _gui_agent;
	}

	public void setSave_results(boolean _save_results) {
		this._save_results = _save_results;
	}

	public boolean getSave_results() {
		return _save_results;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getName() {
		return _name;
	}
	
	public void setEvaluation_method(EvaluationMethod _evaluation_method) {
		this._evaluation_method = _evaluation_method;
	}
	
	public EvaluationMethod getEvaluation_method() {
		return _evaluation_method;
	}

	public Agent getRecommender() {
		return Recommender;
	}

	public void setRecommender(Agent recommender) {
		Recommender = recommender;
	}	
}