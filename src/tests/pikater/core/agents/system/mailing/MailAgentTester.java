package tests.pikater.core.agents.system.mailing;

import java.util.ArrayList;
import java.util.List;

import org.pikater.core.AgentNames;
import org.pikater.core.agents.PikaterAgent;
import org.pikater.core.agents.system.Agent_Mailing;
import org.pikater.core.ontology.MailingOntology;
import org.pikater.core.ontology.subtrees.mailing.SendEmail;

import jade.content.AgentAction;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.domain.FIPAException;
import jade.domain.FIPAService;
import jade.lang.acl.ACLMessage;

/** Tento agent slouzi k vyzkouseni MailAgenta.  Posle mu request na zaslani testovaciho mailu na pevne zadanou adresu a umre. */
public class MailAgentTester extends PikaterAgent {
    private static final long serialVersionUID = -8946304470396671885L;
    
    private static final String DESTINATION_ADDRESS = "j.krajicek@atlas.cz";

	@Override
	public List<Ontology> getOntologies() {
		List<Ontology> ontologies = new ArrayList<Ontology>();
		ontologies.add(MailingOntology.getInstance());
		return ontologies;
	}
	
    @Override
    protected void setup() {
        initDefault();

        // dal by se taky najit v DF, kdybych nevedel, jak se jmenuje
        AID receiver = new AID(AgentNames.MAILING, false);

        // pozadavek, ktery primeje MailAgenta poslat testovaci e-mail
        SendEmail action = new SendEmail(Agent_Mailing.EmailType.TEST, DESTINATION_ADDRESS);

        try {
            ACLMessage request = makeActionRequest(receiver, action);
            log("Sending test request to mailAgent");
            ACLMessage reply = FIPAService.doFipaRequestClient(this, request, 10000);
            if (reply == null)
                logError("Reply not received.");
            else
                log("Reply received: "+ACLMessage.getPerformative(reply.getPerformative())+" "+reply.getContent());
        } catch (CodecException | OntologyException e) {
            logError("Ontology/codec error occurred: "+e.getMessage(), e);
            e.printStackTrace();
        } catch (FIPAException e) {
            logError("FIPA error occurred: "+e.getMessage(), e);
            e.printStackTrace();
        }

        log("MailAgentTester ending");
        doDelete();
    }
    
    /** Naplni pozadavek na konkretni akci pro jednoho ciloveho agenta */
    private ACLMessage makeActionRequest(AID target, AgentAction action) throws CodecException, OntologyException {
    	Ontology ontology = MailingOntology.getInstance();
    	
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(target);
        msg.setLanguage(getCodec().getName());
        msg.setOntology(ontology.getName());
        getContentManager().fillContent(msg, new Action(target, action));
        return msg;
    }

}