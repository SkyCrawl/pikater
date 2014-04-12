package org.pikater.core.options.xmlGenerators;

import org.pikater.core.agents.experiment.computing.Agent_WekaCA;
import org.pikater.core.ontology.description.Method;
import org.pikater.core.options.LogicalBoxDescription;

public class ZeroR_MethodBox extends LogicalBoxDescription {
	public ZeroR_MethodBox(){
		super("ZeroR",Method.class,"Zero R Method");
		this.setAgentName(Agent_WekaCA.class);
		this.setPicture("picture3.jpg");
	}
}