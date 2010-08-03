package ontology.messages;

import jade.content.AgentAction;

public class ImportFile implements AgentAction {

	private static final long serialVersionUID = 2105110111862994842L;
	
	private int userID;
	private String externalFilename;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getExternalFilename() {
		return externalFilename;
	}
	public void setExternalFilename(String externalFilename) {
		this.externalFilename = externalFilename;
	}
	
}
