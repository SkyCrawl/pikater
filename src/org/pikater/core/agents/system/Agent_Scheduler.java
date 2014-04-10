package org.pikater.core.agents.system;

import java.io.IOException;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.util.leap.List;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import org.pikater.core.agents.system.management.ManagerAgentCommunicator;
import org.pikater.core.agents.PikaterAgent;
import org.pikater.core.ontology.description.ComputationDescription;
import org.pikater.core.ontology.description.ComputingAgent;
import org.pikater.core.ontology.description.DataSourceDescription;
import org.pikater.core.ontology.description.DescriptionOntology;
import org.pikater.core.ontology.description.FileDataProvider;
import org.pikater.core.ontology.description.FileVisualizer;
import org.pikater.core.ontology.description.IComputationElement;
import org.pikater.core.ontology.description.Method;
import org.pikater.core.ontology.messages.ExecuteExperiment;
import org.pikater.core.ontology.messages.MessagesOntology;
import org.pikater.core.ontology.messages.SendEmail;
import org.pikater.core.ontology.messages.Solve;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;


public class Agent_Scheduler extends PikaterAgent {
	
	private static final long serialVersionUID = 7226837600070711675L;

	
	@Override
	protected void setup() {

	  	System.out.println("Agent: " +getLocalName() + " starts.");

		initDefault();
		registerWithDF("Scheduler");


		RecieveExperiment recieveExp =
			new RecieveExperiment(this, this.getCodec(), this.getOntology());
		addBehaviour(recieveExp);

/*********REMOVE DOWN********/
        FileDataProvider fileDataProvider = new FileDataProvider();
        fileDataProvider.setFileURI("weather.arff");

        DataSourceDescription fileDataSource = new DataSourceDescription();
        fileDataSource.setDataProvider(fileDataProvider);

		ComputingAgent comAgent = new ComputingAgent();
		comAgent.setTrainingData(fileDataSource);
		comAgent.setModelClass(new Method("pikater.agents.computing.Agent_WekaCA") );

		DataSourceDescription computingDataSource = new DataSourceDescription();
		computingDataSource.setDataProvider(comAgent);

        FileVisualizer visualizer = new FileVisualizer();
        visualizer.setDataSource(computingDataSource);

        ComputationDescription comDescription = new ComputationDescription();
		comDescription.setRootElement(visualizer);


		ExecuteExperiment executeExpAction = new ExecuteExperiment(comDescription);

		try {
			Thread.sleep(9000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        AID receiver = new AID("ComputingManager", false);		

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(receiver);
        msg.setLanguage(getCodec().getName());
        msg.setOntology(getOntology().getName());
        try {
			getContentManager().fillContent(msg, new Action(receiver, executeExpAction));
			this.addBehaviour(new SendProblemToCompManager(this, msg) );
			
			//ACLMessage reply = FIPAService.doFipaRequestClient(this, msg, 10000);
			//System.out.println("Reply: " + reply.getContent());
			
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*********REMOVE UP********/

	  	// Make this agent terminate
	  	//doDelete();
	}


	@Override
	protected String getAgentType(){
		return "Scheduler";
	}

	@Override
    public Ontology getOntology() {
        return DescriptionOntology.getInstance();
    }

}


class RecieveExperiment extends CyclicBehaviour {

	private Agent agent = null;
	private Codec codec = null;
	private Ontology ontology = null;
	
	public RecieveExperiment(Agent agent, Codec codec, Ontology ontology) {
		this.agent = agent;
		this.codec = codec;
		this.ontology = ontology;
	}

	public void action() {

		ACLMessage request= this.agent.receive();
		
		if (request != null) {
			
			ContentElement ce = null;
			try {
				ce = agent.getContentManager().extractContent(request);
			} catch (CodecException | OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Action act = (Action)ce;
			
			if (!(act.getAction() instanceof ExecuteExperiment)) {
				return;
			}
           
			ExecuteExperiment exeExperiment = (ExecuteExperiment) act.getAction();
            ComputationDescription compDescription = exeExperiment.getDescription();
            
            ACLMessage reply = request.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent("OK");
            agent.send(reply);



            AID receiver = new AID("ComputingManager", false);		

            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(receiver);
            msg.setLanguage(codec.getName());
            msg.setOntology(ontology.getName());
            try {
    			agent.getContentManager().fillContent(msg, new Action(receiver, exeExperiment));
    			agent.addBehaviour(new SendProblemToCompManager(agent, msg) );
    			
    			//ACLMessage reply = FIPAService.doFipaRequestClient(this, msg, 10000);
    			//System.out.println("Reply: " + reply.getContent());
    			
    		} catch (CodecException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (OntologyException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

		}
	}
}



class SendProblemToCompManager extends AchieveREInitiator {

	private static final long serialVersionUID = 8923548223375000884L;

	String gui_id;
	PikaterAgent agent;
	
	public SendProblemToCompManager(Agent agent, ACLMessage msg) {
		super(agent, msg);
		this.agent = (PikaterAgent) agent;
	}

	protected void handleAgree(ACLMessage agree) {
		System.out.println(agent.getLocalName() + ": Agent "
				+ agree.getSender().getName() + " agreed.");
	}

	protected void handleInform(ACLMessage inform) {
		System.out.println(agent.getLocalName() + ": Agent "
				+ inform.getSender().getName() + " replied.");
	}

	protected void handleRefuse(ACLMessage refuse) {
		System.out.println(agent.getLocalName() + ": Agent "
				+ refuse.getSender().getName()
				+ " refused to perform the requested action");
	}

	protected void handleFailure(ACLMessage failure) {
		if (failure.getSender().equals(myAgent.getAMS())) {
			// FAILURE notification from the JADE runtime: the receiver
			// does not exist
			System.out.println(agent.getLocalName() + ": Responder does not exist");
		} else {
			System.out.println(agent.getLocalName() + ": Agent " + failure.getSender().getName()
					+ " failed to perform the requested action");
		}
	}

}
