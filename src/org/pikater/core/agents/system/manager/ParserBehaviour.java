package org.pikater.core.agents.system.manager;

import jade.content.Concept;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import org.pikater.core.agents.system.Agent_Manager;
import org.pikater.core.agents.system.data.DataManagerService;
import org.pikater.core.agents.system.manager.graph.ComputationGraph;
import org.pikater.core.agents.system.manager.graph.events.LoggerObserver;
import org.pikater.core.agents.system.manager.parser.Parser;
import org.pikater.core.ontology.subtrees.batch.Batch;
import org.pikater.core.ontology.subtrees.batch.ExecuteBatchDebug;
import org.pikater.core.ontology.subtrees.batch.NewBatch;
import org.pikater.core.ontology.subtrees.batchdescription.ComputationDescription;
import org.pikater.shared.database.jpa.status.JPABatchStatus;

/**
 * The type Parser behaviour.
 */
public class ParserBehaviour extends AchieveREResponder {

	private static final long serialVersionUID = 4754473043512463873L;

	private Agent_Manager agent;

	/**
	 * Instantiates a new Parser behaviour.
	 * 
	 * @param agent_Manager
	 *            the agent _ manager
	 */
	public ParserBehaviour(Agent_Manager agent_Manager) {
		super(agent_Manager, MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST));

		this.agent = agent_Manager;

	}

	@Override
	protected ACLMessage handleRequest(final ACLMessage request)
			throws NotUnderstoodException, RefuseException {

		Concept concept = null;

		try {
			concept = ((Action) (agent.getContentManager()
					.extractContent(request))).getAction();
		} catch (CodecException | OntologyException e) {
			agent.logException(e.getMessage(), e);
		}

		/*
		 * ExecuteBatchDebug is deprecated
		 */
		if (concept instanceof ExecuteBatchDebug) {

			ExecuteBatchDebug executeBatch = (ExecuteBatchDebug) concept;
			ComputationDescription comDescription = executeBatch
					.getDescription();
			int batchID = executeBatch.getBatchID();
			int userID = executeBatch.getUserID();

			return respondToNewBatch(comDescription, batchID, userID, request);
		}

		/*
		 * NewBatch inform
		 */
		if (concept instanceof NewBatch) {

			NewBatch newBatch = (NewBatch) concept;
			int userID = newBatch.getUserId();

			Batch batch = DataManagerService.loadBatch(agent,
					newBatch.getBatchId());

			ComputationDescription comDescription = batch.getDescription();
			int batchID = batch.getId();

			return respondToNewBatch(comDescription, batchID, userID, request);

		}

		ACLMessage failure = request.createReply();
		failure.setPerformative(ACLMessage.FAILURE);
		agent.logSevere("Failure responding to request: "
				+ request.getContent());

		return failure;
	}

	/**
	 * Handles new batches - parses them and instantiates computatio graph
	 * 
	 * @param comDescription
	 *            Ontology description of te new batch
	 * @param batchID
	 *            Batch Id
	 * @param userID
	 *            Batch owner Id
	 * @param request
	 *            Original request
	 * @return Response to request
	 */
	private ACLMessage respondToNewBatch(ComputationDescription comDescription,
			int batchID, int userID, ACLMessage request) {

		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		reply.setContent("OK");
		this.agent.send(reply);

		JPABatchStatus status;
		try {
			Parser parser = new Parser(agent);
			parser.parseRoots(comDescription, batchID, userID);

			ComputationGraph computationGraph = parser.getComputationGraph();
			computationGraph.setBatchID(batchID);
			computationGraph.addObserver(new LoggerObserver(agent));
			ComputationCollectionItem item = new ComputationCollectionItem(
					computationGraph, request, batchID);
			agent.addComputation(item);
			computationGraph.startBatchComputation();
			status = JPABatchStatus.COMPUTING;
		} catch (Exception e) {
			agent.logException("Error in processing the batch", e);
			status = JPABatchStatus.FAILED;
		}
		// change status to computing and log to database
		DataManagerService.updateBatchStatus(agent, batchID, status.name());

		return null;
	}

}