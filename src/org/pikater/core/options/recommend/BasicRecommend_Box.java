package org.pikater.core.options.recommend;

import org.pikater.core.agents.experiment.recommend.Agent_Basic;
import org.pikater.core.ontology.subtrees.agentinfo.AgentInfo;
import org.pikater.core.ontology.subtrees.batchdescription.Recommend;
import org.pikater.core.options.SlotsHelper;

public class BasicRecommend_Box {

	public static AgentInfo get() {

		AgentInfo agentInfo = new AgentInfo();
		agentInfo.importAgentClass(Agent_Basic.class);
		agentInfo.importOntologyClass(Recommend.class);
	
		agentInfo.setName("Basic");
		agentInfo.setDescription("Basic Recommend");
		
		agentInfo.setInputSlots(SlotsHelper.getInputSlots_Recommend());
		agentInfo.setOutputSlots(SlotsHelper.getOutputSlots_Recommend());
		
		return agentInfo;
	}

}
