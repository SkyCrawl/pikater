package pikater;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.proto.SubscriptionInitiator;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import pikater.ontology.messages.Data;
import pikater.ontology.messages.Execute;
import pikater.ontology.messages.GetData;
import pikater.ontology.messages.GetOptions;
import pikater.ontology.messages.Interval;
import pikater.ontology.messages.MessagesOntology;
import pikater.ontology.messages.Metadata;
import pikater.ontology.messages.Method;
import pikater.ontology.messages.Option;
import pikater.ontology.messages.Problem;
import pikater.ontology.messages.Results;
import pikater.ontology.messages.Solve;

public abstract class Agent_GUI extends GuiAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991685806920963921L;

	private String path = System.getProperty("user.dir")
			+ System.getProperty("file.separator");

	protected Codec codec = new SLCodec();
	protected Ontology ontology = MessagesOntology.getInstance();

	private int default_timeout = 30000; // 30s

	protected Vector<Problem> problems = new Vector<Problem>();

	private int problem_id = 0;
	private int agent_id = 0;
	private int data_id = 0;

	private long timeout = 10000;

	
	private HashMap<String, String> agentTypes;
	private HashMap<String, Object[]> agentOptions;
	private int default_number_of_values_to_try = 10;
	private float default_error_rate = (float) 0.3;
	protected String default_method = "Random";
	private int default_maximum_tries = 10;
	private String default_get_results = "after_each_computation";

	private String myAgentName;
	/*
	 * should use the following methods: refreshOptions(ontology.messages.Agent
	 * agent) should be called after user changes options of an agent
	 * sendProblem(); should be called after the Problem is ready to be sent to
	 * the manager
	 * 
	 * can use the following method: protected String[] getComputingAgents()
	 */

	protected abstract String getAgentType();

	/* returns the string with agent type */

	// protected abstract void displayOptions(Problem problem, int performative);

	/*
	 * method should be used to display agent options, it is called
	 * automatically after receiving the message from a computing agent
	 */

	protected abstract void displayResult(ACLMessage inform);

	/*
	 * method should be used to display the result, it is called automatically
	 * after receiving the message from a manager
	 */

	protected abstract void displayResurrectedResult(ACLMessage inform);

        protected abstract void displayFileImportProgress(int completed, int all);
	
	protected abstract void mySetup();

	/*
	 * it should call ... int createNewProblem() - returns the _problem_id ...
	 * addAgentToProblem(int _problem_id, String name, String type) - either
	 * name or type is set, the other parameter should be null - throws
	 * FailureExeption, if the agent could not be found / created ...
	 * addAgentToProblemWekaStyle(int _problem_id, String agentName, String
	 * agentType, String [] agentParams) - similar to addAgentToProblem, but it
	 * adds also agent options - throws FailureExeption, if the agent could not
	 * be found / created ... addOptionToAgent(int _problem_id, String
	 * agent_name, String option_name, String option_value ) ...
	 * addFileToProblem(int _problem_id, String _fileName) ...
	 * addMethodToProblem(int problem_id, String name, String errorRate) -
	 * name...{ChooseXValue, Random}
	 * 
	 * ... getAgentOptions(String agentName) to receive the options from each
	 * computing agent
	 */

	protected abstract void allOptionsReceived(int problem_id);

	/* automatically called after all replies from computing agents are received */

	protected abstract void displayPartialResult(ACLMessage inform);
	protected abstract void displayTaskResult(ACLMessage inform);
	/*
	 * Process the partial results received from computing agents maybe only the
	 * content would be better as a parameter
	 */

	protected abstract void DisplayWrongOption(int problemGuiId,
			String agentName, String optionName, String errorMessage);

	/* This method should handle missing value of the agent option */

	protected void setDefault_number_of_values_to_try(int number) {
		/*
		 * default_number_of_values_to_try - when ChooseXValues method is
		 * selected; should be set in GUI agent setup
		 */
		default_number_of_values_to_try = number;
	}

	protected void setDefault_error_rate(double value) {
		default_error_rate = (float) value;
	}

	protected String[] getComputingAgents() {
		// returns the array of all computing agents' local names

		String type = "ComputingAgent";

		// The list of known computing agents
		String[] ComputingAgents = null;

		// Make the list
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription CAsd = new ServiceDescription();
		CAsd.setType(type);
		template.addServices(CAsd);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			System.out.println("Found the following agents:");
			ComputingAgents = new String[result.length];

			for (int i = 0; i < result.length; ++i) {
				ComputingAgents[i] = result[i].getName().getLocalName();
				System.out.println(ComputingAgents[i]);
			}

		} catch (FIPAException fe) {
			fe.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("No " + type + " found.");
		}
		return ComputingAgents;

	} // end getComputingAgents

	protected List getOptions(String agentType) throws
			CodecException, OntologyException, FIPAException {
		
		long _timeout = System.currentTimeMillis() + 2000; 
		AID aid = null;
		String newName = null;
		
		while (aid == null && System.currentTimeMillis() < _timeout) {
			// try until you find agent of the given type or you manage to
			// create it

			aid = getAgentByType(agentType);
			if (aid == null) {
				// agent of given type doesn't exist
				newName = generateName(agentType);
				
				if (agentTypes == null) {
					createAgentTypesHashMap();
				}
				
				System.out.println("Creating agent " + newName + ", type: "+ agentType);

				aid = createAgent(agentTypes.get(agentType), newName, agentOptions.get(agentType));
				doWait(100);
			}
		}
		if (aid == null) {
			throw new FailureException("Agent of the " + agentType
					+ " type could not be found or created.");
		}
		
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(aid);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		msg.setConversationId("options only");
		// We want to receive a reply in 5 secs
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 2000));

		// Prepare the content.
		GetOptions get = new GetOptions();
		Action a = new Action();
		a.setAction(get);
		a.setActor(this.getAID());

		// Let JADE convert from Java objects to string
		getContentManager().fillContent(msg, a);

		ACLMessage reply = FIPAService.doFipaRequestClient(this, msg);

		List options = null;
		ContentElement content = getContentManager().extractContent(reply);
		if (content instanceof Result) {
			Result result = (Result) content;
			if (result.getValue() instanceof pikater.ontology.messages.Agent) {
				pikater.ontology.messages.Agent agent = 
					(pikater.ontology.messages.Agent) result.getValue();
				options = agent.getOptions();
			}
		}
		
		return options;
	
	} // end getAgentOptions 
			
	protected void getAgentOptions(String receiver) {
		// returns the ontology class Agent (containing agent options) for an
		// agent "receiver", specified by its localName

		// get available Options from selected agent:

		// create a request message with GetOptions content

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

		msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));

		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());

		// We want to receive a reply in 30 secs
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 30000));

		// Prepare the content.
		GetOptions get = new GetOptions();
		Action a = new Action();
		a.setAction(get);
		a.setActor(this.getAID());

		try {
			// Let JADE convert from Java objects to string
			getContentManager().fillContent(msg, a);

		} catch (CodecException ce) {
			ce.printStackTrace();
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}

		AchieveREInitiator behav = new AchieveREInitiator(this, msg) {

			protected void handleInform(ACLMessage inform) {
				System.out.println(getLocalName() + ": Agent "
						+ inform.getSender().getName() + " replied.");
				// we've just received the Options in an inform message

				System.out.println("Conversation id:" + inform.getConversationId());
				
				ContentElement content;
				try {
					content = getContentManager().extractContent(inform);
					// System.out.println(getLocalName()+": Action: "+((Result)content).getAction());
					if (content instanceof Result) {
						Result result = (Result) content;

						if (result.getValue() instanceof pikater.ontology.messages.Agent) {

							pikater.ontology.messages.Agent agent = (pikater.ontology.messages.Agent) result
									.getValue();

							refreshOptions(agent, inform.getPerformative());
							checkProblems();
						}
					}

				} catch (UngroundedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CodecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			protected void handleRefuse(ACLMessage refuse) {
				pikater.ontology.messages.Agent agent = new pikater.ontology.messages.Agent();
				agent.setName(refuse.getSender().getName());

				System.out.println(getLocalName() + ": Agent "
						+ refuse.getSender().getName()
						+ " refused to perform the requested action");

				refreshOptions(agent, refuse.getPerformative());
				checkProblems();
				displayResult(refuse);

			}

			protected void handleFailure(ACLMessage failure) {

				String requestKey = (String) REQUEST_KEY;
				ACLMessage request = (ACLMessage) getDataStore()
						.get(requestKey);
				Iterator receivers = request.getAllIntendedReceiver();
				String agentName = ((AID) receivers.next()).getLocalName();

				pikater.ontology.messages.Agent agent = new pikater.ontology.messages.Agent();
				agent.setName(agentName);

				if (failure.getSender().equals(myAgent.getAMS())) {
					// FAILURE notification from the JADE runtime: the receiver
					// does not exist
					System.out.println("Agent " + myAgent.getLocalName()
							+ "Responder " + agentName + " does not exist.");
				} else {
					System.out.println("Agent " + myAgent.getLocalName()
							+ ": Agent " + agentName
							+ " failed to perform the requested action");
				}

				// refreshOptions(agent, failure.getPerformative());
				checkProblems();
				displayResult(failure);
				
			}

		};

		addBehaviour(behav);

	} // end getAgentOptions

	protected void sendProblem(int _problem_id) {
		// find the problem according to a _problem_id
		Problem problem = null;

		// TODO what if the problem could not be found
		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (Integer.parseInt(next_problem.getGui_id()) == _problem_id
					&& !next_problem.getSent()) {
				problem = next_problem;
			}
		}

		if (problem == null) { // TODO exception
			return;
		}
		
		problem.setStart(getDateTime());
		
		// create a request message with SendProblem content
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("manager", AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());

		// We want to receive a reply in 30 secs
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 30000));

		msg.setConversationId(problem.getGui_id() + getLocalName());
		// Prepare the content.
		Solve solve = new Solve();
		solve.setProblem(problem);

		Action a = new Action();
		a.setAction(solve);
		a.setActor(this.getAID());

		try {
			// Let JADE convert from Java objects to string
			getContentManager().fillContent(msg, a);

		} catch (CodecException ce) {
			ce.printStackTrace();
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}

		AchieveREInitiator send_problem = new AchieveREInitiator(this, msg) {
			// send a problem

			protected void handleAgree(ACLMessage agree) {
				System.out.println(getLocalName() + ": Agent "
						+ agree.getSender().getName() + " agreed.");
				updateProblemId(agree.getContent());
			}

			protected void handleInform(ACLMessage inform) {
				System.out.println(getLocalName() + ": Agent "
						+ inform.getSender().getName() + " replied.");

				// remove problem from problems vector
				// problems.remove(problem);

			}

			protected void handleRefuse(ACLMessage refuse) {
				System.out.println(getLocalName() + ": Agent "
						+ refuse.getSender().getName()
						+ " refused to perform the requested action");
				displayResult(refuse);
			}

			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// FAILURE notification from the JADE runtime: the receiver
					// does not exist
					System.out.println("Responder does not exist");
				} else {
					System.out.println("Agent " + failure.getSender().getName()
							+ " failed to perform the requested action");
				}
				displayResult(failure);
			}

		};

		addBehaviour(send_problem);

		problem.setSent(true);

		ACLMessage subscrmsg = new ACLMessage(ACLMessage.SUBSCRIBE);
		subscrmsg.addReceiver(new AID("manager", AID.ISLOCALNAME)); // TODO find
																	// manager
																	// in yellow
																	// pages
		subscrmsg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		subscrmsg.setConversationId("subscription" + msg.getConversationId());
		subscrmsg.setLanguage(codec.getName());
		subscrmsg.setOntology(ontology.getName());

		SubscriptionInitiator receive_results = new SubscriptionInitiator(this,
				subscrmsg) {
			// receive the sequence of replies

			protected void handleInform(ACLMessage inform) {
				System.out.println(getLocalName() + ": Agent "
						+ inform.getSender().getName() + " replied.");
				displayResult(inform);

			}

			protected void handleRefuse(ACLMessage refuse) {
				System.out.println(getLocalName() + ": Agent "
						+ refuse.getSender().getName()
						+ " refused to perform the requested action");
				displayResult(refuse);
			}

			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// FAILURE notification from the JADE runtime: the receiver
					// does not exist
					System.out.println("Responder does not exist");
				} else {
					System.out.println("Agent " + failure.getSender().getName()
							+ " failed to perform the requested action");
				}
				displayResult(failure);
			}

		};
		
		if (problem.getGet_results().equals("after_each_computation")){
			addBehaviour(receive_results);
		}

	}

	protected int createNewProblem(String timeout, String get_results) {
		int _timeout;
		String _get_results;
		Problem problem = new Problem();
		problem.setGui_id(Integer.toString(problem_id)); // agent manager
															// changes the id
															// afterwards
		if (timeout == null) {
			_timeout = default_timeout;
		} else {
			_timeout = Integer.parseInt(timeout);
		}

		if (get_results == null) {
			_get_results = default_get_results;
		} else {
			_get_results = get_results;
		}

		Method method = new Method();
		method.setName(default_method);
		method.setError_rate(default_error_rate);
		method.setMaximum_tries(default_maximum_tries);
		problem.setMethod(method);

		problem.setTimeout(_timeout);
		problem.setAgents(new ArrayList());
		problem.setData(new ArrayList());
		problem.setSent(false);
		problem.setGet_results(_get_results);
		problem.setGui_agent(myAgentName);
		problems.add(problem);

		return problem_id++;
	}

	/*
	 * protected void addAgentToProblemWekaStyle(int _problem_id, String
	 * agentName, String agentType, String agentParams) throws FailureException{
	 * 
	 * addAgentToProblem(_problem_id, agentName, agentType, agentParams); }
	 */

	protected int addAgentToProblem(int _problem_id, String name, String type,
			String optString) throws FailureException {
		AID aid = null;
		String newName = null;

		if (type != null) {
			if (type.contains("?")) {
				addAgent(_problem_id, agent_id, name, type, optString);
				checkProblems();
				return agent_id++;
			}
		}

		long _timeout = timeout + System.currentTimeMillis();
		if (type != null) {
			// create an agent of a given type to get its options
			while (aid == null && System.currentTimeMillis() < _timeout) {
				// try until you find agent of the given type or you manage to
				// create it

				aid = getAgentByType(type);
				if (aid == null) {
					// agent of given type doesn't exist
					newName = generateName(type);
					aid = createAgent(agentTypes.get(type), newName, agentOptions.get(type));
					doWait(100);
				}
			}
			if (aid == null) {
				throw new FailureException("Agent of the " + type
						+ " type could not be found or created.");
			}
			newName = aid.getLocalName();
		}

		if (name != null) {
			// check if the agent exists
			if (!exists(newName)) {
				throw new FailureException("Agent " + name
						+ " could not be found.");
			} else {
				newName = name;
			}
		}

		addAgent(_problem_id, agent_id, name, type, optString);

		getAgentOptions(newName);

		return agent_id++;
	}

	protected boolean exists(String name) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName(name);
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			if (result.length > 0) {
				return true;
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return false;
	}

	protected void removeAgentFromAllProblems(int _agent_id) {

		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (!next_problem.getSent()) {
				// find the given agent
				Iterator itr = next_problem.getAgents().iterator();
				while (itr.hasNext()) {
					pikater.ontology.messages.Agent next_agent = (pikater.ontology.messages.Agent) itr
							.next();
					if (Integer.parseInt(next_agent.getGui_id()) == _agent_id) {
						next_problem.getAgents().remove(next_agent);
					}
				}
			}
		}
	}

	private String generateName(String agentType) {
		int number = 0;
		String name = agentType + number;
		boolean success = false;
		while (!success) {
			// try to find an agent with "name"
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setName(name);
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(this, template);
				// if the agent with this name already exists, increase number
				if (result.length > 0) {
					number++;
					name = agentType + number;
				} else {
					success = true;
					return name;
				}
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
		return null;
	}

	private void addAgent(int _problem_id, int _agent_id, String name,
			String type, String optString) {

		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (!next_problem.getSent()) {
				if (Integer.parseInt(next_problem.getGui_id()) == _problem_id) {
					pikater.ontology.messages.Agent agent = new pikater.ontology.messages.Agent();
					agent.setName(name);
					agent.setType(type);
					agent.setGui_id(Integer.toString(_agent_id));
					if (optString == null) {
						agent.setOptions(new ArrayList());
					} else {
						List options = agent.stringToOptions(optString);
						Iterator it = options.iterator();

						while (it.hasNext()) {
							Option opt = (Option) it.next();
							opt
									.setNumber_of_values_to_try(default_number_of_values_to_try);
						}

						agent.setOptions(options);

					}
					List agents = next_problem.getAgents();
					agents.add(agent);
					next_problem.setAgents(agents);
				}
			}
		}
	}

	protected void addOptionToAgent(int _problem_id, int _agent_id,
			String option_name, String option_value, String lower,
			String upper, String number_of_values_to_try, String set) {
		// TODO add interval ...
		System.err.println("Add option to agent");
		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (!next_problem.getSent()) {

				if (Integer.parseInt(next_problem.getGui_id()) == _problem_id) {
					Iterator itr = next_problem.getAgents().iterator();
					while (itr.hasNext()) {
						pikater.ontology.messages.Agent next_agent =
							(pikater.ontology.messages.Agent) itr.next();
						// find the right agent
						if (Integer.parseInt(next_agent.getGui_id()) == _agent_id) {

							Option option = new Option();
							option.setName(option_name);

							if (option_value == null) {
								option_value = "True";
							}

							if (option_value.indexOf("?") > -1) {
								// if (option_value.equals("?")){
								option.setMutable(true);
								option.setUser_value(option_value);
								if (lower != null && upper != null) {
									Interval interval = new Interval();
									interval.setMin(Float.valueOf(lower));
									interval.setMax(Float.valueOf(upper));
									option.setRange(interval);
									option.setIs_a_set(false);
								}
								if (set != null) {
									String[] set_array = (set.replace(" ", ""))
											.split(",");
									List set_list = new ArrayList();
									for (int i = 0; i < set_array.length; i++) {
										set_list.add(set_array[i]);
									}
									option.setSet(set_list);
									option.setIs_a_set(true);
								}
							}
							option.setValue(option_value);

							if (next_problem.getMethod().getName().equals(
									"ChooseXValues")) {
								if (number_of_values_to_try == null) {
									option
											.setNumber_of_values_to_try(default_number_of_values_to_try);
								} else {
									option.setNumber_of_values_to_try(Integer
											.parseInt(number_of_values_to_try));
								}
							}

							List options = next_agent.getOptions();
							options.add(option);
							next_agent.setOptions(options);
						}
					}
				}
			}
		}

	}

	protected int addDatasetToProblem(int _problem_id, String _train,
			String _test, String _label, String _output, String _mode) {
		// get the problem
		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (!next_problem.getSent()) {
				if (Integer.parseInt(next_problem.getGui_id()) == _problem_id) {
					List data = next_problem.getData();
					Data d = new Data();
					d.setExternal_test_file_name(_test);
					d.setExternal_train_file_name(_train);
					d.setTrain_file_name("data"
							+ System.getProperty("file.separator")
							+ "files"
							+ System.getProperty("file.separator")
							+ DataManagerService.translateFilename(this, 1,
									_train, null));
					d.setTest_file_name("data"
							+ System.getProperty("file.separator")
							+ "files"
							+ System.getProperty("file.separator")
							+ DataManagerService.translateFilename(this, 1,
									_test, null));
					if (_label != null){  // if there is a file to label
						d.setLabel_file_name("data"
								+ System.getProperty("file.separator")
								+ "files"
								+ System.getProperty("file.separator")
								+ DataManagerService.translateFilename(this, 1,
										_label, null));
					}
					if (_output != null) {
						d.setOutput(_output);
					}
					if (_mode != null) {
						d.setMode(_mode);
					}
					data.add(d);
					next_problem.setData(data);
				}
			}
		}
		return data_id++;
	}

	protected void addMetadataToDataset(int d_id, String file_name,
			String missing_values, String number_of_attributes,
			String number_of_instances, String attribute_type,
			String default_task) {

		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();

			Iterator itr = next_problem.getData().iterator();
			while (itr.hasNext()) {
				Data next_data = (Data) itr.next();
				if (next_data.getGui_id() == d_id) {
					Metadata m = new Metadata();
					m.setAttribute_type(attribute_type);
					m.setDefault_task(default_task);
					m.setExternal_name(file_name);
					if (missing_values != null) {
						if (missing_values.equals("True")) {
							m.setMissing_values(true);
						} else {
							m.setMissing_values(false);
						}
					}
					m.setNumber_of_attributes(Integer
							.parseInt(number_of_attributes));
					m.setNumber_of_instances(Integer
							.parseInt(number_of_instances));

					next_data.setMetadata(m);
				}
			}
		}
	}

	protected void addMethodToProblem(int problem_id, String name,
			String errorRate, String maximumTries) {
		// get the problem
		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (Integer.parseInt(next_problem.getGui_id()) == problem_id
					&& !next_problem.getSent()) {

				Method method = new Method();
				method.setName(name);

				if (name.equals("Random")) {
					if (errorRate == null) {
						method.setError_rate(default_error_rate);
					} else {
						method.setError_rate(Float.parseFloat(errorRate));
					}
					if (maximumTries == null) {
						method.setMaximum_tries(default_maximum_tries);
					} else {
						method.setMaximum_tries(Integer.parseInt(maximumTries));
					}
				}
				next_problem.setMethod(method);
			}
		}
	}

	private void checkProblems() {
		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (!next_problem.getSent()) {
				boolean done = true;
				Iterator aitr = next_problem.getAgents().iterator();
				while (aitr.hasNext()) {
					pikater.ontology.messages.Agent next_agent = (pikater.ontology.messages.Agent) aitr
							.next();

					String type = "";
					if (next_agent.getType() != null) {
						type = next_agent.getType();
					}

					if (!(type.contains("?") && next_agent.getName() == null)) {
						// if we will recomend the type of the agent, we don't
						// wait for the options to be received
						if (next_agent.getOptions() == null) {
							next_agent.setOptions(new ArrayList());
						}
						if (next_agent.getOptions().size() > 0) { // if there is
																	// at least
																	// one
																	// option
							// if data_type is set it means that the options
							// from a computing agent have
							// been received already
							// it's enough to test the first option
							if (((Option) (next_agent.getOptions().iterator()
									.next())).getData_type() == null) {
								done = false;
							}
						}
					}
				}
				if (done) {
					allOptionsReceived(Integer.parseInt(next_problem
							.getGui_id()));
				}
			}
		}
	}

	private void refreshOptions(pikater.ontology.messages.Agent agent,
			int performative) {
		// refresh options in all problems, where the agent is involved

		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (!next_problem.getSent()) {
				if (performative == ACLMessage.INFORM) {

					Iterator aitr = next_problem.getAgents().iterator();
					while (aitr.hasNext()) {
						pikater.ontology.messages.Agent next_agent = (pikater.ontology.messages.Agent) aitr
								.next();

						// all problems where the agent (input parameter)
						// figures
						if (next_agent.getName() != null) {
							if (next_agent.getName().equals(agent.getName())) {
								next_agent.setType(agent.getType());
								next_agent.setOptions(_refreshOptions(
										next_agent, agent, next_problem));
								// System.out.println("DT "+((Option)(next_agent.getOptions().iterator().next())).getData_type());
							}
						} // end if getName != null
						if (next_agent.getType() != null) {
							// System.out.println("type1 "+next_agent.getType()+" type2 "+agent.getType());
							if (next_agent.getType().equals(agent.getType())) {
								next_agent.setOptions(_refreshOptions(
										next_agent, agent, next_problem));
							}
						} // end if getType != null
					} // end while - iterate over agents
				} // end if performative = inform

				else {
					// TODO remove the agent from the problem and let the user
					// know
					removeAgentFromAllProblems(Integer.parseInt(agent
							.getGui_id()));
				}
			} // end if ! sent
		}
	} // end refreshOptions

	private List _refreshOptions(pikater.ontology.messages.Agent next_agent,
			pikater.ontology.messages.Agent agent, Problem next_problem) {
		List newOptions = null;

		if (agent.getOptions() != null) {
			// update the options (merge them)

			// copy agent's options
			java.util.List mergedOptions = new java.util.ArrayList();
			Iterator oitr = agent.getOptions().iterator();
			while (oitr.hasNext()) {
				Option next_option = (Option) oitr.next();
				// next_option.setValue(next_option.getDefault_value());
				Option o = new Option();
				o.setData_type(next_option.getData_type());
				o.setDefault_value(next_option.getDefault_value());
				// o.setIs_a_set(next_option.getIs_a_set());
				o.setName(next_option.getName());
				o.setNumber_of_args(next_option.getNumber_of_args());
				o.setRange(next_option.getRange());
				o.setSet(next_option.getSet());
				o.setValue(next_option.getDefault_value());
				// mergedOptions.add(next_option);
				mergedOptions.add(o);
			}

			// go through the options set in the problem
			// and replace the options send by an computing agent
			Iterator opitr = next_agent.getOptions().iterator();
			while (opitr.hasNext()) {
				Option next_problem_option = (Option) opitr.next();
				ListIterator ocaitr = mergedOptions.listIterator();
				while (ocaitr.hasNext()) {
					Option next_merged_option = (Option) ocaitr.next();
					if (next_problem_option.getName().equals(
							next_merged_option.getName())
					// && (next_problem_option.getValue() != null
					// || next_problem_option.getUser_value() != null )
					) {
						// copy all the parameters (problem -> merged)
						if (next_problem_option.getMutable()) {
							next_merged_option.setMutable(true);
							next_merged_option.setUser_value(
									next_problem_option.getValue());
							if (next_problem_option.getIs_a_set()){
								next_merged_option.setIs_a_set(true);
							}

							if (next_problem_option.getRange() != null) {
								next_merged_option.getRange().setMin(
									next_problem_option.getRange().getMin());
								next_merged_option.getRange().setMax(
									next_problem_option.getRange().getMax());
								next_merged_option.setIs_a_set(false);
							}							
							next_merged_option
									.setNumber_of_values_to_try(next_problem_option
											.getNumber_of_values_to_try());
						}
						// check the value
						if (!next_merged_option.getData_type()
								.equals("BOOLEAN")
								&& next_problem_option.getValue()
										.equals("True")) {
							DisplayWrongOption(Integer.parseInt(next_problem
									.getGui_id()), next_agent.getName(),
									next_problem_option.getName(),
									next_problem_option.getName()
											+ " is not a BOOLEAN type option.");
						} else {
							next_merged_option.setValue(next_problem_option
									.getValue());
						}

						if (next_problem_option.getSet() != null) {
							next_merged_option.setSet(next_problem_option
									.getSet());
						}

						if (next_problem_option.getNumber_of_args() != null) {
							next_merged_option
									.setNumber_of_args(next_problem_option
											.getNumber_of_args());
						}

						ocaitr.set(next_merged_option);
					}
				}
			} // end while - iterate over options
			// create jade.util.leap.ArrayList again
			ArrayList mergedOptionsArrayList = new ArrayList();
			mergedOptionsArrayList.fromList(mergedOptions);
			// next_agent.setOptions(mergedOptionsArrayList);
			newOptions = mergedOptionsArrayList;
		} // end if (empty option list)
		return newOptions;
	} // end function _refreshOption

	protected void createAgentTypesHashMap(){
		// read agent types from file

		// Sets up a file reader to read the agent_types file
		
		agentTypes = new HashMap<String, String>();
			agentOptions = new HashMap<String, Object[]>();
		FileReader input;
		try {
			input = new FileReader(path + "agent_types");
			// Filter FileReader through a Buffered read to read a line at a
			// time
			BufferedReader bufRead = new BufferedReader(input);
			String line = bufRead.readLine();

			// Read through file one line at time
			while (line != null) {
					String[] agentClass = line.split(":");
					agentTypes.put(agentClass[0], agentClass[1]);
					if(agentClass.length>2){
						Object[] opts = new Object[agentClass.length-2];
						for(int i = 0; i < opts.length; i++)
							opts[i] = agentClass[i+2];
						this.agentOptions.put(agentClass[0], opts);
					}
				line = bufRead.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	protected Vector<String> offerAgentTypes() {
		if (agentTypes == null) {
			createAgentTypesHashMap();
		}
		
		Vector<String> agents = new Vector<String>();
		agents.addAll(agentTypes.keySet());
		return agents;
	}

	protected AID createAgent(String type, String name, Object[] options) {
		// get a container controller for creating new agents
		PlatformController container = getContainerController();

		try {
			AgentController agent = container.createNewAgent(name, type,
					options);
			agent.start();
			return new AID((String) name, AID.ISLOCALNAME);
		} catch (ControllerException e) {
			// System.err.println( "Exception while adding agent: " + e );
			// e.printStackTrace();
			return null;
		}
	}

	protected AID getAgentByType(String agentType) {
		AID[] Agents;

		// Make the list of agents of given type
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(agentType);
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			System.out.println("Found the following " + agentType + " agents:");
			Agents = new AID[result.length];

			for (int i = 0; i < result.length; ++i) {
				Agents[i] = result[i].getName();
				System.out.println(Agents[i].getName());
			}

			if (Agents.length > 0) {
				// choose one
				Random generator = new Random();
				int rnd = generator.nextInt(Agents.length);
				return Agents[rnd];
			} else {
				return null;
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
			return null;
		}
	}

	protected void setup() {
		myAgentName = this.getLocalName();
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		// Partial results handler
		addBehaviour(new CompAgentResultsServer(this));

		// register with DF
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(getAgentType());
		sd.setName(getName());
		dfd.setName(getAID());
		dfd.addServices(sd);

		// Name of general GUI service:
		sd = new ServiceDescription();
		sd.setType("GUIAgent");
		sd.setName(getName());
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.err.println(getLocalName()
					+ " registration with DF unsucceeded. Reason: "
					+ e.getMessage());
			doDelete();
		}

		String incomingFilesPath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "incoming"
				+ System.getProperty("file.separator");
		File incomingFiles = new File(incomingFilesPath);

                int incomingFilesNumber = incomingFiles.list().length;
                int currenInFile = 0;
               for (String fileName : incomingFiles.list()) {

                displayFileImportProgress(currenInFile, incomingFilesNumber);
                currenInFile++;
                DataManagerService.importFile(this, 1, fileName, null);

                String internalFilename = DataManagerService.translateFilename(this, 1, (String) fileName, null);
                internalFilename = "data" + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + internalFilename;

                sd = new ServiceDescription();
                sd.setType("ARFFReader");

                dfd = new DFAgentDescription();
                dfd.addServices(sd);

                try {
                    DFAgentDescription readers[] = DFService.search(this, dfd);

                    if (readers.length == 0) {
                        System.err.println("No readers found");
                        break;
                    }

                    AID reader = readers[0].getName();

                    GetData gd = new GetData();
                    gd.setFile_name(internalFilename);
                    gd.setSaveMetadata(true);

                    Action a = new Action();
                    a.setAction(gd);
                    a.setActor(this.getAID());

                    ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
                    req.addReceiver(reader);
                    req.setLanguage(codec.getName());
                    req.setOntology(ontology.getName());
                    req.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

                    getContentManager().fillContent(req, a);

                    ACLMessage response = FIPAService.doFipaRequestClient(this, req);

                    if (response.getPerformative() != ACLMessage.INFORM) {
                        System.err.println("Error reading file");
                    }

                } catch (CodecException ce) {
                    ce.printStackTrace();
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                } catch (OntologyException oe) {
                    oe.printStackTrace();
                }


            }
               displayFileImportProgress(incomingFilesNumber, incomingFilesNumber);

		System.out.println("GUI agent " + getLocalName()
				+ " is alive and waiting...");

		mySetup();

	} // end setup

	/* This behavior captures partial results from computating agents and results from ressurected agents */
	protected class CompAgentResultsServer extends CyclicBehaviour {
		/**
		 * 
		 */
		
		private static final long serialVersionUID = -8456018173216610239L;
		private MessageTemplate partialMsgTemplate = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("partial-results"));

		private MessageTemplate resurrectedMsgTemplate = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("resurrected-results"));

		private MessageTemplate afterTaskMsgTemplate = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("result_after_task"));

		public CompAgentResultsServer(Agent agent) {
			super(agent);
		}

		@Override
		public void action() {
			
			ACLMessage res = receive(resurrectedMsgTemplate);					
			ACLMessage par = receive(partialMsgTemplate);
			ACLMessage aft = receive(afterTaskMsgTemplate);

			if (res != null) {
				displayResurrectedResult(res);
				return;
			}
			else {
				if (par != null) {
					displayPartialResult(par);
					return;
				}
				else{
					if (aft != null) {
						displayTaskResult(aft);
						return;
					}				
					else{
						block();
					}
				}				
			}
		}
	}

	protected void getProblemsFromXMLFile(String fileName)
			throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build("file:"+System.getProperty("file.separator")+System.getProperty("file.separator")+ System.getProperty("user.dir")+ System.getProperty("file.separator") + fileName);
		System.out.println("file:\\" + System.getProperty("user.dir")+ System.getProperty("file.separator") + fileName);
		Element root_element = doc.getRootElement();

		java.util.List _problems = root_element.getChildren("experiment"); // return
																			// all
																			// children
																			// by
																			// name
		java.util.Iterator p_itr = _problems.iterator();
		while (p_itr.hasNext()) {
			Element next_problem = (Element) p_itr.next();

			int p_id = createNewProblem(next_problem.getAttributeValue("timeout"),
					next_problem.getAttributeValue("get_results"));

			java.util.List method = next_problem.getChildren("method");
			java.util.Iterator m_itr = method.iterator();
			if (method.size() == 0) {
				// TODO select default
			}
			if (method.size() > 1) {
				// TODO error
			}
			while (m_itr.hasNext()) {
				Element next_method = (Element) m_itr.next();
				addMethodToProblem(p_id, next_method.getAttributeValue("name"),
						next_method.getAttributeValue("error_rate"),
						next_method.getAttributeValue("maximum_tries"));
			}

			java.util.List dataset = next_problem.getChildren("dataset");
			java.util.Iterator ds_itr = dataset.iterator();
			while (ds_itr.hasNext()) {
				Element next_dataset = (Element) ds_itr.next();
				int d_id = addDatasetToProblem(p_id, 
						next_dataset.getAttributeValue("train"),
						next_dataset.getAttributeValue("test"),
						next_dataset.getAttributeValue("label"),
						next_dataset.getAttributeValue("output"),
						next_dataset.getAttributeValue("mode"));

				java.util.List metadata = next_dataset.getChildren("metadata");
				if (metadata.size() > 0) {
					java.util.Iterator md_itr = metadata.iterator();
					Element next_metadata = (Element) md_itr.next();

					addMetadataToDataset(d_id, next_dataset
							.getAttributeValue("train"), next_metadata
							.getAttributeValue("missing_values"), next_metadata
							.getAttributeValue("number_of_attributes"),
							next_metadata
									.getAttributeValue("number_of_instances"),
							next_metadata.getAttributeValue("attribute_type"),
							next_metadata.getAttributeValue("default_task"));
				}
			}

			java.util.List _agents = next_problem.getChildren("agent");
			java.util.Iterator a_itr = _agents.iterator();
			while (a_itr.hasNext()) {
				Element next_agent = (Element) a_itr.next();

				String agent_name = next_agent.getAttributeValue("name");
				String agent_type = next_agent.getAttributeValue("type");
				int a_id = -1;
				try {
					a_id = addAgentToProblem(p_id, agent_name, agent_type, null);
				} catch (FailureException e) {
					System.err.println(e.getLocalizedMessage());
					// e.printStackTrace();
				}

				java.util.List _options = next_agent.getChildren("parameter");
				java.util.Iterator o_itr = _options.iterator();
				while (o_itr.hasNext()) {
					Element next_option = (Element) o_itr.next();
					addOptionToAgent(p_id, a_id, 
							next_option.getAttributeValue("name"),
							next_option.getAttributeValue("value"),
							next_option.getAttributeValue("lower"),
							next_option.getAttributeValue("upper"),
							next_option.getAttributeValue("number_of_values_to_try"),
							next_option.getAttributeValue("set"));
				}
			}
		}
	} // end _test_getProblemsFromXMLFile

	private void updateProblemId(String ids) {
		String[] ID = ids.split(" ");
		String guiId = ID[0];
		String id = ID[1];
		// find problem with gui_id
		for (Enumeration pe = problems.elements(); pe.hasMoreElements();) {
			Problem next_problem = (Problem) pe.nextElement();
			if (next_problem.getGui_id().equals(guiId)) {
				next_problem.setId(id);
			}
		}
	}
		
	protected void loadAgent(String _filename, Execute action, byte [] object) throws FIPAException {
		// protected void loadAgent(String _name, int _userID, String _timestamp) {
		pikater.ontology.messages.LoadAgent _loadAgent = new pikater.ontology.messages.LoadAgent();
		
		_loadAgent.setFilename(_filename);
		_loadAgent.setFirst_action(action);
		_loadAgent.setObject(object);
		// _loadAgent.setName(_name);
		// _loadAgent.setUserID(_userID);
		// _loadAgent.setTimestamp(_timestamp);
		
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("agentManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setConversationId("resurrected-results");
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(this.getAID());
		a.setAction(_loadAgent);
		
		try {
			getContentManager().fillContent(request, a);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FIPAService.doFipaRequestClient(this, request);
	}
	
/*
	protected List getSavedAgents(int userID) {
		pikater.ontology.messages.GetSavedAgents gsa = new pikater.ontology.messages.GetSavedAgents();
		
		gsa.setUserID(1);
		
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("agentManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(this.getAID());
		a.setAction(gsa);
		
		try {
			getContentManager().fillContent(request, a);
			
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List listOfResults = null; 
		try {
			ACLMessage result = FIPAService.doFipaRequestClient(this, request);			
			
			ContentElement content = getContentManager().extractContent(result);			
			if (content instanceof Result) {				
				Result _result = (Result) content;
				listOfResults = (List) _result.getValue();				
			}
		}catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listOfResults;
	}
	*/
	
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

}