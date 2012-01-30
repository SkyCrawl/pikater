package pikater;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAService;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.StaleProxyException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pikater.ontology.messages.MessagesOntology;

public class Agent_Initiator extends Agent {
	/**
	 * 
	 */
	private Codec codec = new SLCodec();
	private Ontology ontology = MessagesOntology.getInstance();
	
	private static final long serialVersionUID = -3908734088006529947L;
	private String path = System.getProperty("user.dir")
			+ System.getProperty("file.separator");

	@Override
	protected void setup() {
		//Register the SL content language
		getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		
		// read agents from file
		try {
			/* Sets up a file reader to read the init file */
			FileReader input = new FileReader(path + "init");
			/*
			 * Filter FileReader through a Buffered read to read a line at a
			 * time
			 */
			BufferedReader bufRead = new BufferedReader(input);

			String line; // String that holds current file line
			int count = 0; // Line number of count
			// Read first line
			line = bufRead.readLine();
			count++;

			// Read through file one line at time. Print line # and line
			while (line != null) {
				System.out.println(count + ": " + line);

				// parse the line
				String delims = "[ ]+";
				String[] params = line.split(delims);
				if (params[0].equals("$a")) {

					String[] rest_of_the_array;

					rest_of_the_array = new String[params.length - 3];
					for (int i = 3; i < params.length; i++) {
						rest_of_the_array[i - 3] = params[i];
					}

					CreateAgent(params[1], params[2], rest_of_the_array);

				}
				if (params[0].equals("$l")) {
					String[] p = { "load" };
					CreateAgent(params[1], params[2], p);

				}

				line = bufRead.readLine();

				count++;
			}

			bufRead.close();
			
			// loadAgent("rbf3", 1);

		} catch (ArrayIndexOutOfBoundsException e) {
			/*
			 * If no file was passed on the command line, this exception is
			 * generated. A message indicating how to the class should be called
			 * is displayed
			 */
			System.out.println("Usage: java ReadFile filename\n");

		} catch (IOException e) {
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}
		
		addBehaviour(new TickerBehaviour(this, 10000) {
			
		  Calendar cal;
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  
		  protected void onTick() {
			  cal = Calendar.getInstance();
			  System.out.println("Agent "+myAgent.getLocalName()+": tick="+getTickCount()+" time="+sdf.format(cal.getTime()));
		  } 
		  
		});

	}

	public int CreateAgent(String type, String name, Object[] args) {
		// get a container controller for creating new agents
		PlatformController container = getContainerController();

		try {
			AgentController agent = container.createNewAgent(name, type, args);
			agent.start();
			// provide agent time to register with DF etc.
			doWait(300);
		} catch (ControllerException e) {
			System.err.println("Exception while adding agent: " + e);
			e.printStackTrace();
			return 0;
		} 

		return 1;
	}
}