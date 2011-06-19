package pikater.ontology.messages;

import jade.content.Concept;

public class Computation implements Concept {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2908172397391249327L;
	private String _problem_id;
	private String _id;
	private Agent _agent;
	private Data _data;
	private int _timeout; // miliseconds
	private Method _method;
	private String _get_results;
	private String _gui_agent;
	private boolean _save_results;
	
	// Methods required to use this class to represent the TASK role
	public void setAgent(Agent agent) {
		_agent = agent;
	}

	public Agent getAgent() {
		return _agent;
	}

	public void setData(Data data) {
		_data = data;
	}

	public Data getData() {
		return _data;
	}

	public void setProblem_id(String problem_id) {
		_problem_id = problem_id;
	}

	public String getProblem_id() {
		return _problem_id;
	}

	public void setId(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	public void setTimeout(int timeout) {
		_timeout = timeout;
	}

	public int getTimeout() {
		return _timeout;
	}

	public Method getMethod() {
		return _method;
	}

	public void setMethod(Method method) {
		_method = method;
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
}
