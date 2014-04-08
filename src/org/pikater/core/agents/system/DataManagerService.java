package org.pikater.core.agents.system;

import java.util.Date;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAService;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import org.pikater.core.ontology.messages.DeleteTempFiles;
import org.pikater.core.ontology.messages.GetAllMetadata;
import org.pikater.core.ontology.messages.GetFileInfo;
import org.pikater.core.ontology.messages.GetFiles;
import org.pikater.core.ontology.messages.GetMetadata;
import org.pikater.core.ontology.messages.GetTheBestAgent;
import org.pikater.core.ontology.messages.ImportFile;
import org.pikater.core.ontology.messages.MessagesOntology;
import org.pikater.core.ontology.messages.Metadata;
import org.pikater.core.ontology.messages.SaveMetadata;
import org.pikater.core.ontology.messages.SaveResults;
import org.pikater.core.ontology.messages.ShutdownDatabase;
import org.pikater.core.ontology.messages.Task;
import org.pikater.core.ontology.messages.TranslateFilename;
import org.pikater.core.ontology.messages.UpdateMetadata;

public class DataManagerService extends FIPAService {

	static final Codec codec = new SLCodec();

       
        public static String importFile(Agent agent, int userID, String path, String content, boolean temp) {            

		ImportFile im = new ImportFile();
		im.setUserID(userID);
		im.setExternalFilename(path);
		im.setFileContent(content);
                im.setTempFile(temp);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(im);

		try {
			agent.getContentManager().fillContent(request, a);
		} catch (CodecException e1) {
			e1.printStackTrace();
		} catch (OntologyException e1) {
			e1.printStackTrace();
		}

		try {
			return FIPAService.doFipaRequestClient(agent, request).getContent();
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		return null;
        }


	public static String importFile(Agent agent, int userID, String path, String content) {
             return importFile(agent, userID, path, content, false);
	}

	public static String translateFilename(Agent agent, int user,
			String externalFilename, String internalFilename) {

		TranslateFilename tf = new TranslateFilename();
		tf.setUserID(user);
		tf.setExternalFilename(externalFilename);
		tf.setInternalFilename(internalFilename);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(tf);

		try {
			agent.getContentManager().fillContent(request, a);

			ACLMessage inform = FIPAService.doFipaRequestClient(agent, request);

			if (inform == null) {
				return null;
			}

			Result r = (Result) agent.getContentManager()
					.extractContent(inform);

			return (String) r.getValue();
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void saveResult(Agent agent, Task t) {

		SaveResults sr = new SaveResults();
		sr.setTask(t);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(sr);

		try {
			agent.getContentManager().fillContent(request, a);

			FIPAService.doFipaRequestClient(agent, request);

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}

	}

	public static void saveMetadata(Agent agent, Metadata m) {
		SaveMetadata saveMetadata = new SaveMetadata();
		saveMetadata.setMetadata(m);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(saveMetadata);

		try {
			agent.getContentManager().fillContent(request, a);

			FIPAService.doFipaRequestClient(agent, request);

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	public static List getAllMetadata(Agent agent, GetAllMetadata gm) {

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(gm);
		
		try {
			agent.getContentManager().fillContent(request, a);
			ACLMessage inform = FIPAService.doFipaRequestClient(agent, request);

			Result r = (Result) agent.getContentManager()
					.extractContent(inform);
			List allMetadata = (List) r.getValue();
			return allMetadata;

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Metadata getMetadata(Agent agent, GetMetadata gm) {

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(gm);
		
		try {
			agent.getContentManager().fillContent(request, a);
			ACLMessage inform = FIPAService.doFipaRequestClient(agent, request);

			Result r = (Result) agent.getContentManager().extractContent(inform);
			Metadata metadata = (Metadata)r.getValue();
			return metadata;

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return null;
	}
	
        public static org.pikater.core.ontology.messages.Agent getTheBestAgent(Agent agent, String fileName) {
            return (org.pikater.core.ontology.messages.Agent) getTheBestAgents(agent, fileName, 1).get(0);
        }
        
	public static List getTheBestAgents(Agent agent, String fileName, int number) {
		GetTheBestAgent g = new GetTheBestAgent();
		g.setNearest_file_name(fileName);
                g.setNumberOfAgents(number);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(g);

		try {
			agent.getContentManager().fillContent(request, a);
			ACLMessage inform = FIPAService.doFipaRequestClient(agent, request);

			if (inform.getPerformative() == ACLMessage.FAILURE) {
				return null;
			}

			Result r = (Result) agent.getContentManager().extractContent(inform);
			List bestAgents = (List) r.getValue();
			return bestAgents;

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList getFilesInfo(Agent agent, GetFileInfo gfi) {

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(gfi);

		try {
			agent.getContentManager().fillContent(request, a);

			ACLMessage inform = FIPAService.doFipaRequestClient(agent, request);

			if (inform == null) {
				return new ArrayList();
			}

			Result r = (Result) agent.getContentManager()
					.extractContent(inform);

			return (ArrayList) r.getValue();
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void updateMetadata(Agent agent, Metadata m) {
		UpdateMetadata updateMetadata = new UpdateMetadata();
		updateMetadata.setMetadata(m);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(updateMetadata);

		try {
			agent.getContentManager().fillContent(request, a);

			FIPAService.doFipaRequestClient(agent, request);

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

        public static void deleteTempFiles(Agent agent) {

            DeleteTempFiles dtf = new DeleteTempFiles();

            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(new AID("dataManager", false));
            request.setOntology(MessagesOntology.getInstance().getName());
            request.setLanguage(codec.getName());
            request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

            Action a = new Action();
            a.setActor(agent.getAID());
            a.setAction(dtf);

            try {
                agent.getContentManager().fillContent(request, a);

                FIPAService.doFipaRequestClient(agent, request);

            } catch (CodecException e) {
                e.printStackTrace();
            } catch (OntologyException e) {
                e.printStackTrace();
            } catch (FIPAException e) {
                e.printStackTrace();
            }

        }

	public static ArrayList getFiles(Agent agent, int userID) {
		GetFiles gfi = new GetFiles();
		gfi.setUserID(userID);

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(gfi);

		try {
			agent.getContentManager().fillContent(request, a);

			ACLMessage inform = FIPAService.doFipaRequestClient(agent, request);

			if (inform == null) {
				return null;
			}

			Result r = (Result) agent.getContentManager()
					.extractContent(inform);

			return (ArrayList) r.getValue();
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public static boolean shutdownDatabase(Agent agent) {

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.addReceiver(new AID("dataManager", false));
		request.setOntology(MessagesOntology.getInstance().getName());
		request.setLanguage(codec.getName());
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.setReplyByDate(new Date(System.currentTimeMillis() + 1000));

		ShutdownDatabase sd = new ShutdownDatabase();
		
		Action a = new Action();
		a.setActor(agent.getAID());
		a.setAction(sd);

		ACLMessage inform = null;
		try {
			agent.getContentManager().fillContent(request, a);

			inform = FIPAService.doFipaRequestClient(agent, request);

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		if (inform == null){
			return false;		
		}
		else {
			return true;			
		}

	}
}