package org.pikater.core.ontology.subtrees.batch;

import org.pikater.core.ontology.subtrees.batchdescription.ComputationDescription;

import jade.content.Concept;

public class Batch implements Concept {

	private static final long serialVersionUID = -7028457864866356063L;

	private int id;

	private String name;
	private String note;

	private String status;
	
	private long xmlOID;
	private int ownerID;

	private int priority;
	
	private ComputationDescription description;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public long getXmlOID() {
		return xmlOID;
	}
	public void setXmlOID(long xmlOID) {
		this.xmlOID = xmlOID;
	}

	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ComputationDescription getDescription() {
		return description;
	}
	public void setDescription(ComputationDescription description) {
		this.description = description;
	}

}