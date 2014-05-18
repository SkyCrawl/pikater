package org.pikater.core.options;

import org.pikater.core.agents.experiment.computing.Agent_WekaNBTreeCA;
import org.pikater.core.ontology.subtrees.agentInfo.AgentInfo;
import org.pikater.core.ontology.subtrees.description.ComputingAgent;

public class NBTree_CABox {
	
	public static AgentInfo get() {

		AgentInfo agentInfo = new AgentInfo();
		agentInfo.setAgentClass(Agent_WekaNBTreeCA.class.getName());
		agentInfo.setOntologyClass(ComputingAgent.class.getName());
	
		agentInfo.setName("NBTree");
		agentInfo.setPicture("picture3.jpg");
		agentInfo.setDescription("NBTree Method");


		// Slots Definition
		agentInfo.setInputSlots(AAA_SlotHelper.getCAInputSlots());
		agentInfo.setOutputSlots(AAA_SlotHelper.getCAOutputSlots());

		return agentInfo;
	}
}
