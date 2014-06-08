package org.pikater.core.agents.system.computationDescriptionParser.dependencyGraph;

import jade.core.Agent;

/**
 * User: Kuba
 * Date: 10.5.2014
 * Time: 14:12
 */
public interface StartComputationStrategy {	
    public void execute(ComputationNode computation);
}