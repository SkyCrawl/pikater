package org.pikater.web.experiment.server;

import java.util.LinkedHashSet;
import java.util.Set;

import org.pikater.core.CoreConstants;
import org.pikater.core.ontology.subtrees.agentInfo.Slot;
import org.pikater.core.ontology.subtrees.newOption.base.NewOption;
import org.pikater.web.experiment.server.BoxSlot.SlotType;

public class ExperimentGraphValidator
{
	private final ExperimentGraphServer experimentGraph;
	private final Set<String> problems;
	
	public ExperimentGraphValidator(ExperimentGraphServer experimentGraph)
	{
		this.experimentGraph = experimentGraph;
		this.problems = new LinkedHashSet<String>();
	}
	
	//-----------------------------------------------------------
	// BASIC PUBLIC INTERFACE
	
	public boolean problemsFound()
	{
		return !this.problems.isEmpty();
	}
	
	public Set<String> getProblems()
	{
		return problems;
	}
	
	public void clear()
	{
		this.problems.clear();
	}
	
	//-----------------------------------------------------------
	// BASIC PRIVATE INTERFACE
	
	private void registerValidationProblem(String problem)
	{
		this.problems.add(problem);
	}
	
	// ------------------------------------------------------------------
	// VALIDATION INTERFACE
	
	public void validate()
	{
		// clean the instance just in case so that old problems are not effective anymore
		clear();
		
		// first check boxes
		boolean inputBoxIncluded = false;
		for(BoxInfoServer box : experimentGraph)
		{
			if(box.isRegistered()) // only inspect registered boxes
			{
				/*
				 * A check whether the box is approved is needed. Currently, this is done
				 * in core system and only approved agents are sent to web.
				 */
				if(!experimentGraph.getSlotConnections().hasBoxSlotConnections(box))
				{
					// the box is not isolated but has no valid slot connections defined
					registerValidationProblem(String.format("At least one of '%s' boxes has no slot connections defined. Isolated boxes are not allowed.", 
							box.getAssociatedAgent().getName()));
				}
				else
				{
					// this is only basic validation, specific option validation may be done in the switch below
					for(NewOption option : box.getAssociatedAgent().getOptions())
					{
						if(!option.isValid(true))
						{
							// the box is not isolated but has no valid slot connections defined
							registerValidationProblem(String.format("At least one of '%s' boxes has invalid value in the '%s' option.", 
									box.getAssociatedAgent().getName(),
									option.getName()
							));
						}
					}
					switch(box.getBoxType())
					{
						case INPUT:
							inputBoxIncluded = true;
							if(box.getAssociatedAgent().getName().equals("FileInput"))
							{
								NewOption optionToCheck = box.getAssociatedAgent().getOptions().fetchOptionByName("fileURI");
								String value = (String) optionToCheck.toSingleValue().getCurrentValue().hackValue();
								if(value.equals("inputFile.ARFF"))
								{
									registerValidationProblem("At least one of 'FileInput' boxes has the invalid default value set in the 'fileURI' option.");
								}
							}
							validateBoxSlot(box, SlotType.OUTPUT, CoreConstants.SLOT_FILE_DATA); // potentially duplicate check but it is needed
							break;
						case CHOOSE:
							break;
						case COMPOSITE:
							break;
						case COMPUTE:
							validateBoxSlot(box, SlotType.INPUT, CoreConstants.SLOT_TRAINING_DATA); // potentially duplicate check but it is needed
							validateBoxSlot(box, SlotType.INPUT, CoreConstants.SLOT_TESTING_DATA); // potentially duplicate check but it is needed
							break;
						case MISC:
							break;
						case OPTION:
							break;
						case OUTPUT:
							validateBoxSlot(box, SlotType.INPUT, CoreConstants.SLOT_FILE_DATA); // potentially duplicate check but it is needed
							break;
						case PROCESS_DATA:
							break;
						case SEARCH:
							break;
						default:
							throw new IllegalStateException("Unknown state: " + box.getBoxType().name());
					}
				}
			}
		}
		
		if(!inputBoxIncluded)
		{
			registerValidationProblem("The experiment has no registered input boxes. This error may be the result of previous errors.");
		}
		
		// then check edges
		/*
		for(BoxInfoServer boxFrom : experimentGraph)
		{
			for(BoxInfoServer boxTo : experimentGraph.getFromNeighbours(boxFrom.getID()))
			{
				if(boxFrom.isRegistered() != boxTo.isRegistered())
				{
					registerValidationProblem(String.format("At least one of '%s' boxes doesn't have the '%s' slot connected."));
				}
			}
		}
		*/
	}
	
	private void validateBoxSlot(BoxInfoServer box, SlotType type, String slotDataType)
	{
		Slot slotToCheck;
		if(type == SlotType.INPUT)
		{
			slotToCheck = box.getAssociatedAgent().fetchInputSlotByDataType(slotDataType);
		}
		else
		{
			slotToCheck = box.getAssociatedAgent().fetchOutputSlotByDataType(slotDataType);	
		}
		if(!experimentGraph.getSlotConnections().isSlotConnectedToAValidEndpoint(slotToCheck))
		{
			registerValidationProblem(String.format("At least one of '%s' boxes doesn't have the '%s' slot connected.", 
					box.getAssociatedAgent().getName(), slotDataType));
		}
	}
}