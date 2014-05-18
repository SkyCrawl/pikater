package org.pikater.core.ontology.subtrees.messages;

import org.pikater.core.ontology.subtrees.task.Task;

import jade.content.AgentAction;

public class SaveResults implements AgentAction {

	private static final long serialVersionUID = -7028457864866356063L;

	private Task task;

	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}

}