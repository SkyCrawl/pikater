package org.pikater.core.agents.experiment.dataprocessing;

import jade.content.Concept;
import jade.content.ContentException;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.domain.FIPAException;
import jade.domain.FIPAService;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pikater.core.ontology.AgentInfoOntology;
import org.pikater.core.ontology.DataOntology;
import org.pikater.core.ontology.ExperimentOntology;
import org.pikater.core.ontology.TaskOntology;
import org.pikater.core.ontology.subtrees.agentInfo.AgentInfo;
import org.pikater.core.ontology.subtrees.agentInfo.Slot;
import org.pikater.core.ontology.subtrees.agentInfo.slotTypes.SlotTypes;
import org.pikater.core.ontology.subtrees.batchDescription.DataProcessing;
import org.pikater.core.ontology.subtrees.data.Data;
import org.pikater.core.ontology.subtrees.dataInstance.DataInstances;
import org.pikater.core.ontology.subtrees.task.ExecuteTask;
import org.pikater.core.ontology.subtrees.task.Task;
import org.pikater.core.ontology.subtrees.task.TaskOutput;
import org.pikater.core.ontology.subtrees.task.Task.InOutType;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Agent_WeatherSplitter extends Agent_DataProcessing {

	private static final long serialVersionUID = 4679962419249103511L;

	@Override
	public List<Ontology> getOntologies() {
		List<Ontology> ontologies = new ArrayList<>();
		ontologies.add(TaskOntology.getInstance());
		ontologies.add(AgentInfoOntology.getInstance());
		ontologies.add(ExperimentOntology.getInstance());
		ontologies.add(DataOntology.getInstance());
		return ontologies;
	}
	
	@Override
	protected AgentInfo getAgentInfo() {
		AgentInfo agentInfo = new AgentInfo();
		agentInfo.importAgentClass(this.getClass());
		agentInfo.importOntologyClass(DataProcessing.class);
	
		agentInfo.setName("WeatherSplitter");
		agentInfo.setDescription("Splits weather data by prediction.");

		Slot i1 = new Slot();
		i1.setSlotType(SlotTypes.DATA_TYPE);
		i1.setDataType("firstInput");
		i1.setDescription("First weather input.");
		Slot i2 = new Slot();
		i2.setSlotType(SlotTypes.DATA_TYPE);
		i2.setDataType("secondInput");
		i2.setDescription("Second weather input.");
		
		agentInfo.setInputSlots(Arrays.asList(i1, i2));

		Slot o_sunny = new Slot();
		o_sunny.setSlotType(SlotTypes.DATA_TYPE);
		o_sunny.setDataType("sunnyOutput");
		o_sunny.setDescription("Sunny output.");
		Slot o_overcast = new Slot();
		o_overcast.setSlotType(SlotTypes.DATA_TYPE);
		o_overcast.setDataType("overcastOutput");
		o_overcast.setDescription("Overcast output.");
		Slot o_rainy = new Slot();
		o_rainy.setSlotType(SlotTypes.DATA_TYPE);
		o_rainy.setDataType("rainyOutput");
		o_rainy.setDescription("Rainy output.");

		agentInfo.setOutputSlots(Arrays.asList(o_sunny, o_overcast, o_rainy));

		return agentInfo;
	}

	@Override
	protected void setup() {
		super.setup();

		Ontology ontology = TaskOntology.getInstance();
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchOntology(ontology.getName()),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

		addBehaviour(new AchieveREResponder(this, template) {
			private static final long serialVersionUID = 746138569142900592L;

			@Override
			protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
				try {
					Concept action = ((Action) (getContentManager().extractContent(request))).getAction();
					if (action instanceof ExecuteTask)
						return respondToExecute(request);
					else
						throw new RefuseException("Invalid action requested");
				} catch (CodecException e) {
					throw new FailureException("Unknown codec: " + e.getMessage());
				} catch (OntologyException e) {
					throw new FailureException("Unknown ontology: " + e.getMessage());
				} catch (ContentException e) {
					throw new FailureException("Content problem: " + e.getMessage());
				} catch (FIPAException e) {
					logError("FIPA exception when performing task", e);
					throw new FailureException("Failed because of another agent " + e.getMessage());
				}
			}
		});
		
	}

	private ACLMessage respondToExecute(ACLMessage request) throws ContentException, FIPAException {
		log("got execute");
		final ACLMessage reply = request.createReply();
		final ExecuteTask content;
		Task performed;
		try {
			content = (ExecuteTask) ((Action)getContentManager().extractContent(request)).getAction();
			performed = performTask(content.getTask());
		} catch (CodecException e) {
			logError("Failed to extract task", e);
			throw e;
		} catch (OntologyException e) {
			logError("Failed to extract task", e);
			throw e;
		}

		Result result = new Result(content, performed);
		getContentManager().fillContent(reply, result);
		reply.setPerformative(ACLMessage.INFORM);

		log("responding with finished task");
		return reply;
	}
	
	private List<DataInstances> getDataForTask(Task t) throws FIPAException {
		List<DataInstances> res = new ArrayList<>();
		for (Data dataI : t.getDatas().getDatas()) {
			String fname = dataI.getInternalFileName();
			ACLMessage request = makeGetDataRequest(fname);
			log("sending getData for "+fname);
			ACLMessage reply = FIPAService.doFipaRequestClient(this, request);
			res.add(processGetDataResponse(reply));
			log("got data for "+fname);
		}
		return res;
	}

	private Task performTask(Task t) throws FIPAException {
		log("getting data");

		List<DataInstances> weatherData = getDataForTask(t);
		log("processing data");
		ArrayList<TaskOutput> outputs = processData(weatherData);
		log("returning outputs");
		t.setOutput(outputs);
		return t;
	}
	
	private Instances mergeInputs(List<DataInstances> weatherData) {
		Instances res = new Instances(weatherData.get(0).toWekaInstances());
		Instances second = weatherData.get(1).toWekaInstances();
		for (int i = 0; i < second.numInstances(); ++i) {
			res.add(second.instance(i));
		}
		return res;
	}

	private ArrayList<TaskOutput> processData(List<DataInstances> weatherData) {
		ArrayList<TaskOutput> res = new ArrayList<>();
		Instances input = mergeInputs(weatherData);
		
		Instances sunny = new Instances(input, 0);
		Instances overcast = new Instances(input, 0);
		Instances rainy = new Instances(input, 0);
		
		Attribute forecast = input.attribute(0);
		
		for (int i = 0; i < input.numInstances(); ++i) {
			Instance instance = input.instance(i);
			String value = instance.stringValue(forecast);
			switch (value) {
			case "rainy":
				rainy.add(instance);
				break;
			case "overcast":
				overcast.add(instance);
				break;
			case "sunny":
				sunny.add(instance);
				break;
			default:
				throw new IllegalArgumentException("Unknown weather data: " + value);
			}
		}
		
		res.add(makeOutput(sunny));
		res.add(makeOutput(overcast));
		res.add(makeOutput(rainy));
		return res;
	}
	
	private TaskOutput makeOutput(Instances instances) {
		TaskOutput res = new TaskOutput();
		res.setName(saveArff(instances));
		res.setType(InOutType.DATA);
		return res;
	}
	
}
