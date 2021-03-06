package org.pikater.core.agents.gateway.batch;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

import org.pikater.core.CoreAgents;
import org.pikater.core.agents.gateway.Agent_PikaterGateway;
import org.pikater.core.agents.gateway.Initiator;
import org.pikater.core.agents.gateway.PikaterGateway_General;
import org.pikater.core.agents.gateway.exception.PikaterGatewayException;
import org.pikater.core.ontology.BatchOntology;
import org.pikater.core.ontology.subtrees.batch.NewBatch;

public class PikaterGateway_NewBatch {

	/**
	 * Constructor
	 */
	private PikaterGateway_NewBatch() {
	}
	
	/**
	 * New Batch Inform
	 * @param newBatchID
	 * @param userID
	 * @throws PikaterGatewayException
	 */
	public static void newBatch(int newBatchID, int userID
			) throws PikaterGatewayException {

		try {
	        
	        NewBatch newBatch = new NewBatch();
	        newBatch.setUserId(userID);
	        newBatch.setBatchId(newBatchID);

			Ontology batchOntology = BatchOntology.getInstance();

			ACLMessage msg = Agent_PikaterGateway.makeActionRequest(
					CoreAgents.MANAGER.getName(), batchOntology,
					newBatch);

			Initiator initiator = new Initiator(msg);
			PikaterGateway_General.generalRequest(initiator);
			initiator.getOkResponse();

		} catch (ControllerException e1) {
			throw new PikaterGatewayException("Failed to make request");
		} catch (CodecException e1) {
			throw new PikaterGatewayException("Failed to make request");
		} catch (OntologyException e1) {
			throw new PikaterGatewayException("Failed to make request");
		}

	}

}
