package org.pikater.core.agents.system.manager.graph.strategies;

import org.pikater.core.agents.system.manager.graph.ComputationNode;
import org.pikater.core.agents.system.manager.graph.StartComputationStrategy;

/**
 * Strategy that do nothing
 * User: Kuba
 * Date: 8.6.2014
 * Time: 16:28
 */
public class DummyComputationStrategy implements StartComputationStrategy {
    /**
     *
     * @param computation Computation node with this strategy
     */
    @Override
    public void execute(ComputationNode computation) {

    }
}
