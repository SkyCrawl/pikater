package org.pikater.core.agents.system.manager.parser;

import org.pikater.core.agents.system.manager.graph.ComputationNode;

import java.util.LinkedList;

/**
 * Standard queue
 * User: Kuba
 * Date: 10.5.2014
 * Time: 12:07
 */
public class StandardBuffer<E> extends AbstractComputationBuffer<E> {
    private LinkedList<E> buffer=new LinkedList<E>();
    private boolean data; 
    private String targetInput;

    /**
     *
     * @param source Source node
     * @param target Target node
     */
    public StandardBuffer(ComputationNode source, ComputationNode target) {
           super(source,target);
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty();
    }

    @Override
    public void insert(E element) {
         buffer.addLast(element);
    }

    @Override
    public E getNext() {
        return buffer.pollFirst();
    }

    @Override
    public int size() {
        return buffer.size();
    }

	@Override
	public boolean isData() {
		return data;
	}

	@Override
	public void setData(boolean data) {
		this.data = data;
	}

    @Override
	public String getTargetInput() {
		return targetInput;
	}
}
