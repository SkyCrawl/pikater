package ontology.messages;

import jade.content.Concept;
import jade.util.leap.List;

public class Task implements Concept {
	private String _options;
	private String _id;
	private String _computation_id;
	private String _problem_id;
	private Evaluation _result;
	private Agent _agent;
	private Data _data;
	
	
	public void setAgent(Agent agent) {
		_agent=agent;
	}
	public Agent getAgent() {
		return _agent;
	}
	public void setData(Data data) {
		_data=data;
	}
	public Data getData() {
		return _data;
	}
	
	public void setOptions(String options) {
		_options=options;
	}
	public String getOptions() {
		return _options;
	}
	public void setId(String id) {
		_id=id;
	}
	public String getId() {
		return _id;
	}
	public void setComputation_id(String computation_id) {
		_computation_id=computation_id;
	}
	public String getComputation_id() {
		return _computation_id;
	}
	public void setProblem_id(String problem_id) {
		_problem_id=problem_id;
	}
	public String getProblem_id() {
		return _problem_id;
	}
	public void setResult(Evaluation result) {
		_result = result;
	}
	public Evaluation getResult() {
		return _result;
	}
}
