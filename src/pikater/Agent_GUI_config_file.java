package pikater;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;
import jade.util.leap.List;

import java.io.IOException;

import org.jdom.JDOMException;

import pikater.ontology.messages.Agent;
import pikater.ontology.messages.Data;
import pikater.ontology.messages.DataInstances;
import pikater.ontology.messages.Eval;
import pikater.ontology.messages.Evaluation;
import pikater.ontology.messages.Execute;
import pikater.ontology.messages.Problem;
import pikater.ontology.messages.Results;
import pikater.ontology.messages.Task;

public class Agent_GUI_config_file extends Agent_GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -709390383325209787L;
	private String path = System.getProperty("user.dir")
			+ System.getProperty("file.separator");
	private String configFileName;

    private final String CONFIG = "config";

	
	@Override
	protected void displayResult(ACLMessage inform) {
		ContentElement content;
		try {
			content = getContentManager().extractContent(inform);
			if (content instanceof Result) {
				Result result = (Result) content;
				if (result.getValue() instanceof Results) {
					List tasks = ((Results) result.getValue()).getResults();
					if (tasks != null) {
						Iterator itr = tasks.iterator();
						while (itr.hasNext()) {
							Task task = (Task) itr.next();
							
							Float error_rate = null;
							Iterator ev_itr = task.getResult().getEvaluations().iterator();							
							while (ev_itr.hasNext()) {
								Eval next_eval = (Eval) ev_itr.next();
								if (next_eval.getName().equals("error_rate")){ 
									error_rate = next_eval.getValue();
								}
							}
							System.out.println(getLocalName()
									+ ": options for agent "
									+ task.getAgent().getName() + " were "
									+ task.getAgent().optionsToString()
									+ " error_rate: "
									+ error_rate);
						}
					} else {
						System.out.println(getLocalName()
								+ ": there were no tasks in this computation.");
					}
				}
			}

		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			System.out.println(getLocalName() + ": "
					+ inform.getContent());
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	protected void displayResurrectedResult(ACLMessage inform) {
		ContentElement content;
		try {
			content = getContentManager().extractContent(inform);
			if (content instanceof Result) {
				Result result = (Result) content;
				
				if (result.getValue() instanceof Evaluation) {					
					Evaluation eval = ((Evaluation) result.getValue());
				
					Float error_rate = null;
					Iterator ev_itr = eval.getEvaluations().iterator();							
					while (ev_itr.hasNext()) {
						Eval next_eval = (Eval) ev_itr.next();
						if (next_eval.getName().equals("error_rate")){ 
							error_rate = next_eval.getValue();
						}
					}
					
					System.out.println(getLocalName()
									+ ": "
									+ " error_rate: "
									+ error_rate);
					
					/* List dataList = eval.getLabeled_data();
					Iterator itr = dataList.iterator();
					while (itr.hasNext()) {
						System.out.println("Instances: "+((DataInstances)itr.next()).getInstances());
					}
					*/
				}
				
			}
			else {
				System.out.println(getLocalName()
							+ ": there were no tasks in this computation.");
			}
		
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			System.out.println(getLocalName() + ": "
					+ inform.getContent());
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void displayTaskResult(ACLMessage inform) {
		
		ContentElement content;
		try {
			content = getContentManager().extractContent(inform);
			
			// System.out.println(content);
			
			if (content instanceof Result) {
				
				Result result = (Result) content;
				if (result.getValue() instanceof Task) {					
					Task task = ((Task) result.getValue());
				
					Float error_rate = null;
					Iterator ev_itr = task.getResult().getEvaluations().iterator();							
					while (ev_itr.hasNext()) {
						Eval next_eval = (Eval) ev_itr.next();
						if (next_eval.getName().equals("error_rate")){ 
							error_rate = next_eval.getValue();
						}
					}
					
					System.out.println(getLocalName()
							+ ": options for agent "
							+ task.getAgent().getName() + " were "
							+ task.getAgent().optionsToString()
							+ " error_rate: "+ error_rate
							+ ", dataset: "
							+ task.getData().getExternal_train_file_name());
				}
				
			}
			else {
				System.out.println(getLocalName()
							+ ": there were no tasks in this computation.");
			}
		
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			System.out.println(getLocalName() + ": "
					+ inform.getContent());
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void DisplayWrongOption(int problemGuiId, String agentName,
			String optionName, String errorMessage) {
		System.out.println("Agent :" + getName() + " " + problemGuiId + " "
				+ agentName + " " + optionName + " " + errorMessage);
	}

	@Override
	protected void allOptionsReceived(int problem_id) {
		try {
			sendProblem(problem_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getAgentType() {
		return "GUI config file";
	}

	@Override
	protected void mySetup() {
		setDefault_error_rate(0.01);

		doWait(10000);
		/* test of getOptions method
		/*try {
			System.out.println("J48 options: "+getOptions("J48"));
		} catch (CodecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OntologyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FIPAException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		/*
		 * // test: int newId = createNewProblem("1000"); try {
		 * //addAgentToProblem(newId, null, "MultilayerPerceptron",
		 * "-L 0.4 -D -M ? -H ?,?"); // addAgentToProblem(newId, null,
		 * "RBFNetwork", "-B 4"); addAgentToProblem(newId, null, "?", null);
		 * 
		 * } catch (FailureException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } addDatasetToProblem(newId, "iris.arff",
		 * "iris.arff", null, null); // getAgentOptions("mp1"); //
		 */

		// System.out.println("Agent types: " + offerAgentTypes());

		configFileName = getConfigFileName();
		try {
			 System.out.println(getLocalName() + ": xml file name " + 
					//"file:"+System.getProperty("file.separator")+System.getProperty("file.separator")+
					System.getProperty("user.dir")+ System.getProperty("file.separator") + configFileName);
			getProblemsFromXMLFile(configFileName);
		}
		// indicates a well-formedness error
		catch (JDOMException e) {
			System.out.println(getLocalName() + ": " + configFileName + " is not well-formed. "
					+ e.getMessage());
		} catch (IOException e) {
			System.out.print(getLocalName() + ": Could not check " + configFileName);
			System.out.println(" because " + e.getMessage());
		}
		// */

		String agentName = "1_RBFNetwork1_2011-05-22_23-17-33.112";
	// test of loading an agent
	/*
		Agent a = new Agent();
		a.setName(agentName);
		a.setGui_id("pokusny oziveny agent");
		
		Data d = new Data();
		d.setMode("test_only");
		d.setTest_file_name("data/files/772c551b8486b932aed784a582b9c1b1");
		d.setTrain_file_name("data/files/772c551b8486b932aed784a582b9c1b1"); // weather
		
		// d.setTest_file_name("data/files/25d7d5d689042a3816aa1598d5fd56ef");
		// d.setTrain_file_name("data/files/25d7d5d689042a3816aa1598d5fd56ef"); // iris
		d.setExternal_test_file_name("weather.arff");
		d.setExternal_train_file_name("weather.arff");
		d.setOutput("predictions");
		
		Task t = new Task();		
		t.setAgent(a);
		t.setData(d);
 
		t.setId("pokusny task pro pokusneho oziveneho agenta");
		t.setComputation_id("neni soucasti zadne computation");
		t.setProblem_id("neni soucasti zadneho problemu");
		
		Execute ex = new Execute();
		ex.setTask(t);
		
		try {
			loadAgent(agentName, ex, null);
		} catch (FIPAException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/* */
	// end test of loading

	// test of loading Karlik
	/*
		Agent a = new Agent();
		a.setName("1_RBFNetwork0_2011-05-17_09-01-32.263");
		a.setGui_id("pokusny oziveny agent");
				
		try {
			loadAgent("1_RBFNetwork0_2011-05-17_09-01-32.263", null);
		} catch (FIPAException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/* */
	// end test of loading
		
		
		
	} // end mySetup

	@Override
	protected void displayPartialResult(ACLMessage inform) {
		System.out.println(getLocalName() + ": Partial results");
	}

	private String getConfigFileName() {
		// return (String) getArguments()[0];
		return GetArgumentValue(CONFIG);
	}

    @Override
    protected void displayFileImportProgress(int completed, int all) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

}
