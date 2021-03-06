package org.pikater.core.options.virtual;

import org.pikater.core.agents.experiment.virtual.Agent_VirtualCARecSearchComplexBoxProvider;
import org.pikater.core.ontology.subtrees.agentinfo.AgentInfo;
import org.pikater.core.ontology.subtrees.batchdescription.CARecSearchComplex;
import org.pikater.core.options.SlotsHelper;

public class CARecSearchComplex_Box {

	public static AgentInfo get() {

		AgentInfo agentInfo = new AgentInfo();
		agentInfo
				.importAgentClass(Agent_VirtualCARecSearchComplexBoxProvider.class);
		agentInfo.importOntologyClass(CARecSearchComplex.class);

		agentInfo.setName("Complex");
		agentInfo.setDescription("Complex Box");

		agentInfo.setInputSlots(SlotsHelper.getIntputSlots_CARecSearchComplex());

		agentInfo.setOutputSlots(SlotsHelper.getOutputSlots_CARecSearchComplex());

		return agentInfo;
	}

}
