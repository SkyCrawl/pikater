package org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.kineticcomponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.pikater.core.ontology.subtrees.agentInfo.AgentInfo;
import org.pikater.shared.database.jpa.JPABatch;
import org.pikater.shared.experiment.universalformat.UniversalComputationDescription;
import org.pikater.shared.experiment.universalformat.UniversalConnector;
import org.pikater.shared.experiment.universalformat.UniversalElement;
import org.pikater.shared.experiment.universalformat.UniversalGui;
import org.pikater.shared.experiment.universalformat.UniversalOntology;
import org.pikater.shared.experiment.webformat.BoxInfo;
import org.pikater.shared.experiment.webformat.BoxType;
import org.pikater.shared.experiment.webformat.ExperimentGraph;
import org.pikater.shared.logging.PikaterLogger;
import org.pikater.shared.util.SimpleIDGenerator;
import org.pikater.web.config.AgentInfoCollection;
import org.pikater.web.config.ServerConfigurationInterface;
import org.pikater.web.vaadin.gui.client.kineticcomponent.KineticComponentClientRpc;
import org.pikater.web.vaadin.gui.client.kineticcomponent.KineticComponentServerRpc;
import org.pikater.web.vaadin.gui.client.kineticcomponent.KineticComponentState;
import org.pikater.web.vaadin.gui.server.components.popups.MyNotifications;
import org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.CustomTabSheetTabComponent;
import org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.ExpEditor;
import org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.ExpEditor.ExpEditorToolbox;
import org.pikater.web.vaadin.gui.server.ui_expeditor.expeditor.toolboxes.BoxOptionsToolbox;
import org.pikater.web.vaadin.gui.shared.kineticcomponent.ClickMode;
import org.pikater.web.vaadin.gui.shared.kineticcomponent.graphitems.AbstractGraphItemShared.RegistrationOperation;
import org.pikater.web.vaadin.gui.shared.kineticcomponent.graphitems.BoxGraphItemShared;
import org.pikater.web.vaadin.gui.shared.kineticcomponent.graphitems.EdgeGraphItemShared;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractComponent;

@JavaScript(value = "kinetic-v4.7.3-dev.js")
public class KineticComponent extends AbstractComponent
{
	private static final long serialVersionUID = -539901377528727478L;
	
	//---------------------------------------------------------------
	// GUI COMPONENTS TO KEEP TRACK OF
	
	/**
	 * Constant reference to the parent editor component.
	 */
	private final ExpEditor parentEditor;
	
	/**
	 * Reference to the tab containing this component.
	 */
	private CustomTabSheetTabComponent parentTab;
	
	//---------------------------------------------------------------
	// EXPERIMENT RELATED FIELDS
	
	/**
	 * ID generator for boxes.
	 * This generator never falls back and thus it is ensured that no two
	 * boxes will have the same ID.
	 */
	private final SimpleIDGenerator boxIDGenerator;
	
	/**
	 * The dynamic mapping between boxes and agent information. Only a portion of agent information
	 * is sent to the client (+ some added value), wrapped in BoxInfo instance.
	 * This field is the base for all format conversions and some other commands.
	 */
	private final Map<String, AgentInfo> boxIDToAgentInfo;
	
	/**
	 * Reference to experiment last used in the {@link #importExperiment(UniversalComputationDescription)}
	 * method.
	 */
	private Integer previouslyLoadedExperimentID;
	
	/**
	 * Callback for exported experiments.
	 * @see {@link IOnExperimentReceivedFromClient}   
	 */
	private IOnExperimentReceivedFromClient exportedExperimentCallback;
	
	//---------------------------------------------------------------
	// OTHER PROGRAMMATIC FIELDS
	
	private boolean bindOptionsManagerWithSelectionChanges;
	
	/*
	 * Dynamic information from the client side - absolute left corner position of the Kinetic stage.
	 */
	private int absoluteLeft;
	private int absoluteTop;
	
	//---------------------------------------------------------------
	// CONSTRUCTOR
	
	public KineticComponent(final ExpEditor parentEditor)
	{
		super();
		setSizeFull();
		
		/*
		 * Init.
		 */
		
		this.parentEditor = parentEditor;
		this.parentTab = null;
		
		this.boxIDGenerator = new SimpleIDGenerator();
		this.boxIDToAgentInfo = new HashMap<String, AgentInfo>();
		this.previouslyLoadedExperimentID = null;
		this.exportedExperimentCallback = null;
		
		this.bindOptionsManagerWithSelectionChanges = areSelectionChangesBoundWithOptionsManagerByDefault();
		this.absoluteLeft = 0;
		this.absoluteTop = 0;
		
		/*
		 * Register handlers for client commands.
		 */
		
		registerRpc(new KineticComponentServerRpc()
		{
			private static final long serialVersionUID = -2769231541745495584L;
			
			/**
			 * Currently unsupported.
			 */
			@Deprecated
			@Override
			public void command_setExperimentModified(boolean modified)
			{
				/*
				getState().serverThinksThatSchemaIsModified = modified;
				parentTab.setTabContentModified(modified);
				parentEditor.getExtension().setKineticContentModified(KineticComponent.this, modified);
				
				MyNotifications.showInfo("Modification note", String.valueOf(modified));
				*/
			}

			@Override
			public void command_onLoadCallback(int absoluteX, int absoluteY)
			{
				KineticComponent.this.absoluteLeft = absoluteX;
				KineticComponent.this.absoluteTop = absoluteY;
			}
			
			@Override
			public void command_alterClickMode(ClickMode newClickMode)
			{
				getState().clickMode = newClickMode;
				KineticComponent.this.parentEditor.getToolbar().onClickModeAlteredOnClient(newClickMode);
			}
			
			@Override
			public void command_boxSetChange(RegistrationOperation opKind, BoxGraphItemShared[] boxes)
			{
				// TODO Auto-generated method stub
				// MyNotifications.showInfo(null, "Box set changed");
			}

			@Override
			public void command_edgeSetChange(RegistrationOperation opKind, EdgeGraphItemShared[] edges)
			{
				// TODO Auto-generated method stub
				// MyNotifications.showInfo(null, "Edge set changed");
			}
			
			@Override
			public void command_selectionChange(String[] selectedBoxesIDs)
			{
				if(bindOptionsManagerWithSelectionChanges)
				{
					// convert to agent information array
					AgentInfo[] selectedBoxesInformation = new AgentInfo[selectedBoxesIDs.length];
					for(int i = 0; i < selectedBoxesIDs.length; i++)
					{
						if(boxIDToAgentInfo.containsKey(selectedBoxesIDs[i]))
						{
							selectedBoxesInformation[i] = boxIDToAgentInfo.get(selectedBoxesIDs[i]);
						}
						else
						{
							throw new IllegalStateException(String.format("Kinetic state out of sync. "
									+ "No agent info was found for box ID '%s'.", selectedBoxesIDs[i]));
						}
					}
					
					// get the toolbox
					BoxOptionsToolbox toolbox = (BoxOptionsToolbox) parentEditor.getToolbox(ExpEditorToolbox.METHOD_OPTION_MANAGER);
					
					// set the new content to it and display the toolbox if needed
					if(toolbox.setContentFromSelectedBoxes(selectedBoxesInformation))
					{
						parentEditor.openToolbox(ExpEditorToolbox.METHOD_OPTION_MANAGER);
					}
				}
			}

			@Override
			public void response_sendExperimentToSave(ExperimentGraph experiment)
			{
				UniversalComputationDescription result = null;
				try
				{
					result = webToUni(experiment);
				}
				catch (ConversionException e)
				{
					PikaterLogger.logThrowable("Could not convert to universal format because of the error below.", e);
				}
				
				try
				{
					if(result != null)
					{
						exportedExperimentCallback.handleExperiment(result, new IOnExperimentSaved()
						{
							@Override
							public void experimentSaved(JPABatch newExperimentEntity)
							{
								if(newExperimentEntity.getId() == 0)
								{
									throw new IllegalStateException("The given new experiment has not been saved yet.");
								}
								else
								{
									previouslyLoadedExperimentID = newExperimentEntity.getId();
									parentTab.setCaption(newExperimentEntity.getName());
								}
							}
						});
					}
				}
				finally
				{
					exportedExperimentCallback = null;
				}
			}
		});
	}
	
	//---------------------------------------------------------------
	// INHERITED INTERFACE
	
	@Override
	public KineticComponentState getState()
	{
		return (KineticComponentState) super.getState();
	}
	
	//---------------------------------------------------------------
	// EXPERIMENT IMPORT/EXPORT RELATED ROUTINES/TYPES
	
	/**
	 * Used BEFORE saving experiments.</br>
	 * The server has to issue an asynchronous command to the client and wait for it to
	 * send response. The experiment from response is converted into universal format
	 * and passed to the {@link IOnExperimentReceivedFromClient#handleExperiment(UniversalComputationDescription)
	 * handleExperiment(UniversalComputationDescription)} method.
	 */
	public static interface IOnExperimentReceivedFromClient
	{
		/**
		 * Handle the exported experiment in this method.
		 * @param exportedExperiment
		 * @param experimentSavedCallback provide a callback for when the experiment is successfully saved
		 */
		void handleExperiment(UniversalComputationDescription exportedExperiment, IOnExperimentSaved experimentSavedCallback);
	}
	
	/**
	 * Used AFTER saving experiments to keep this component's inner state in sync.
	 */
	public static interface IOnExperimentSaved
	{
		/**
		 * Call this method when your experiment has successfully been saved.
		 * @param newBatchID the ID of the newly saved experiment
		 */
		void experimentSaved(JPABatch newExperimentEntity);
	}
	
	public void importExperiment(JPABatch experiment)
	{
		try
		{
			UniversalComputationDescription uniFormat = UniversalComputationDescription.fromXML(experiment.getXML());
			
			resetEnvironment(); // calls another client command which must precede this one:
			getClientRPC().command_receiveExperimentToLoad(uniToWeb(uniFormat));
			
			previouslyLoadedExperimentID = experiment.getId();
			parentTab.setCaption(experiment.getName());
		}
		catch (ConversionException e)
		{
			PikaterLogger.logThrowable("", e);
			MyNotifications.showError("Could not import experiment", "Please, contact the administrators.");
		}
	}
	
	public synchronized void exportExperiment(IOnExperimentReceivedFromClient callback)
	{
		if(exportedExperimentCallback != null)
		{
			MyNotifications.showWarning("Export ignored", "Another call pending...");
		}
		else
		{
			// register callback
			exportedExperimentCallback = callback;
			
			// send command to the client
			getClientRPC().request_sendExperimentToSave();
		}
	}
	
	//---------------------------------------------------------------
	// MISCELLANEOUS PUBLIC INTERFACE
	
	public void setParentTab(CustomTabSheetTabComponent parentTab)
	{
		this.parentTab = parentTab;
	}
	
	public void createBox(AgentInfo info, int absX, int absY)
	{
		getClientRPC().command_createBox(createBoxInfo(info, absX - absoluteLeft, absY - absoluteTop));
	}
	
	public void reloadVisualStyle()
	{
		getClientRPC().request_reloadVisualStyle();
	}
	
	public boolean areSelectionChangesBoundWithOptionsManager()
	{
		return bindOptionsManagerWithSelectionChanges;
	}
	
	public static boolean areSelectionChangesBoundWithOptionsManagerByDefault()
	{
		return true;
	}

	public void setBindOptionsManagerWithSelectionChanges(boolean bindOptionsManagerWithSelectionChanges)
	{
		this.bindOptionsManagerWithSelectionChanges = bindOptionsManagerWithSelectionChanges;
	}
	
	public boolean isContentModified()
	{
		return true; // TODO: until problems with the "modified" status are resolved, always return true
		// return getState().serverThinksThatSchemaIsModified;
	}
	
	public Integer getPreviouslyLoadedExperimentID()
	{
		return previouslyLoadedExperimentID;
	}
	
	//---------------------------------------------------------------
	// MISCELLANEOUS PRIVATE INTERFACE
	
	private KineticComponentClientRpc getClientRPC()
	{
		return getRpcProxy(KineticComponentClientRpc.class);
	}
	
	private BoxInfo createBoxInfo(AgentInfo info, int relX, int relY)
	{
		BoxType type = BoxType.fromAgentInfo(info);
		String newBoxID = String.valueOf(boxIDGenerator.getAndIncrement());
		boxIDToAgentInfo.put(newBoxID, info);
		return new BoxInfo(
				newBoxID,
				type.name(),
				info.getName(),
				relX,
				relY,
				type.toPictureURL()
		);
	}
	
	private void resetEnvironment()
	{
		getClientRPC().command_resetKineticEnvironment();
		boxIDToAgentInfo.clear();
	}
	
	//---------------------------------------------------------------
	// FORMAT CONVERSIONS
	
	private UniversalComputationDescription webToUni(ExperimentGraph webFormat) throws ConversionException
	{
		// first some checks
		AgentInfoCollection agentInfoProvider = ServerConfigurationInterface.getKnownAgents();
		if(webFormat == null)
		{
			throw new ConversionException(new NullPointerException("The argument web format is null."));
		}
		else if(agentInfoProvider == null)
		{
			throw new ConversionException(new NullPointerException("Agent information has not yet been received from pikater."));
		}
		
		UniversalComputationDescription result = new UniversalComputationDescription();
		for(Entry<String, BoxInfo> entry : webFormat.leafBoxes.entrySet())
		{
			/*
			 * Determine basic information and references.
			 */
			String webBoxID = entry.getKey();
			BoxInfo webBox = entry.getValue();
			AgentInfo agentInfo = boxIDToAgentInfo.get(webBoxID);
			if(agentInfo == null)
			{
				throw new ConversionException(new IllegalStateException(String.format(
						"No agent info was found for box '%s@%s'.", webBox.boxTypeName, webBox.displayName)));
			}
			
			/*
			 * Transform box into its uni-format counterpart.
			 */
			
			// create the master element
			UniversalElement uniBox = new UniversalElement();
			
			// create FIRST of the 2 child objects
			UniversalOntology uniBoxInfo = new UniversalOntology();
			try
			{
				uniBoxInfo.setType(Class.forName(agentInfo.getOntologyClassName()));
			}
			catch (ClassNotFoundException e)
			{
				throw new ConversionException(new IllegalStateException(String.format(
						"Could not convert '%s' to a class instance. Have changes been made to AgentInfo recently?",
						agentInfo.getOntologyClassName()), e));
			}
			uniBoxInfo.setOptions(agentInfo.getOptions().getOptions());
			for(String edgeToBoxWithID : webFormat.edges.get(webBoxID)) // transform edges
			{
				UniversalConnector connector = new UniversalConnector();
				// TODO: connector.s
				// ontologyInfo.addInputSlot();
			}
			
			// create SECOND of the 2 child objects
			UniversalGui uniBoxPositionInfo = new UniversalGui(webBox.initialX, webBox.initialY);
			
			// glue it all together and register in the result uni-format
			uniBox.setGUIInfo(uniBoxPositionInfo);
			uniBox.setOntologyInfo(uniBoxInfo);
			result.addElement(uniBox);
		}
		return result;
	}
	
	private ExperimentGraph uniToWeb(UniversalComputationDescription uniFormat) throws ConversionException
	{
		// first some checks
		AgentInfoCollection agentInfoProvider = ServerConfigurationInterface.getKnownAgents();
		if(uniFormat == null)
		{
			throw new ConversionException(new NullPointerException("The argument universal format is null."));
		}
		else if(agentInfoProvider == null)
		{
			throw new ConversionException(new NullPointerException("Agent information has not yet been received from pikater."));
		}
		
		// and then onto the conversion
		if(uniFormat.isGUICompatible())
		{
			ExperimentGraph webFormat = new ExperimentGraph();

			// first convert all boxes
			Map<UniversalElement, String> uniBoxToWebBoxID = new HashMap<UniversalElement, String>();
			for(UniversalElement element : uniFormat.getAllElements())
			{
				AgentInfo agentInfo =  agentInfoProvider.getByOntologyClass(element.getOntologyInfo().getType());
				if(agentInfo == null)
				{
					throw new ConversionException(new IllegalStateException(String.format(
							"No agent info instance was found for ontology '%s'.", element.getOntologyInfo().getType().getName())));
				}
				else
				{
					BoxInfo info = createBoxInfo(agentInfo, element.getGUIInfo().x, element.getGUIInfo().y);
					String convertedBoxID = webFormat.addLeafBoxAndReturnID(info);
					uniBoxToWebBoxID.put(element, convertedBoxID);
				}
			}
			
			// then convert all edges
			for(UniversalElement element : uniFormat.getAllElements())
			{
				for(UniversalConnector edge : element.getOntologyInfo().getInputSlots())
				{
					webFormat.connect(
							uniBoxToWebBoxID.get(edge.getFromElement()),
							uniBoxToWebBoxID.get(element)
					);
				}
			}
			
			// TODO: wrapper boxes, options & stuff
			
			return webFormat;
		}
		else
		{
			throw new ConversionException(new IllegalArgumentException(String.format(
					"The universal format below is not fully compatible with the GUI (web) format.\n%s", uniFormat.toXML())));
		}
	}
}