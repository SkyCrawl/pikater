package pikater.utility.experiment.boxes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pikater.utility.experiment.parameters.AbstractParameter;
import pikater.utility.experiment.parameters.info.ParamInfo;
import pikater.utility.experiment.slots.AbstractSlot;

public abstract class LeafBox extends AbstractBox
{
	public enum LeafBoxCategory
	{
		INPUT,
		SEARCHER,
		COMPUTING,
		VISUALIZER
	};
	
	// -----------------------------------------------------------
	// EDITOR RELATED FIELDS SUITABLE TO BE DISPLAYED TO THE USER
	
	/**
	 * The collection of parameters that ARE NOT taken from input slots and ARE editable by the user.
	 * The map uses the "id" field of type @ParamInfo as key. See @ParamInfo for details.
	 */
	private final Map<ParamInfo, AbstractParameter> editableNonInputParameters;
	
	// -----------------------------------------------------------
	// INTERNAL FIELDS NOT BEING DISPLAYED TO THE USER
	
	/**
	 * The collection of parameters that MAY OR MAY NOT be taken from input slots and ARE NOT editable by the user. This
	 * feature is useful for box inheritance and changing a select few parameters to ensure a different behaviour.
	 * The map uses the "id" field of type @ParamInfo as key. See @ParamInfo for details.
	 */
	private final Map<ParamInfo, AbstractParameter> internalParameters;
	
	/**
	 * Input slots defined by this box. By default, all underlying data is required to be provided by connected boxes (incoming edges).
	 */
	private final Set<AbstractSlot> inputSlots;
	
	/**
	 * Output slots defined by this box. By default, all underlying data is automatically served to connected boxes (outgoing edges).
	 */
	private final Set<AbstractSlot> outputSlots;
	
	/**
	 * Special internal variables for the sake of conversion to computation ontologies.
	 */
	public final String agentClassName;
	public final String ontologyClassName;
	
	// -----------------------------------------------------------
	// CONSTRUCTOR
	
	public LeafBox(String displayName, String picture, String description, String agentClassName, String ontologyClassName)
	{
		super(displayName, picture, description);
		
		this.editableNonInputParameters = new HashMap<ParamInfo, AbstractParameter>();
		this.internalParameters = new HashMap<ParamInfo, AbstractParameter>();
		this.inputSlots = new HashSet<AbstractSlot>();
		this.outputSlots = new HashSet<AbstractSlot>();
		
		this.agentClassName = agentClassName;
		this.ontologyClassName = ontologyClassName;
	}
	
	// -----------------------------------------------------------
	// INHERITED METHODS
	
	@Override
	public boolean isLeaf()
	{
		return true;
	}
	
	// -----------------------------------------------------------
	// THIS CLASS'S ABSTRACT METHODS
	
	public abstract Set<LeafBoxCategory> getCategories();

	// ---------------------------------------------------------------------------
	// PUBLIC GETTERS
	
	public AbstractParameter getParameter(ParamInfo paramInfo)
	{
		if(editableNonInputParameters.containsKey(paramInfo))
		{
			return editableNonInputParameters.get(paramInfo);
		}
		else
		{
			return internalParameters.get(paramInfo);
		}
	}

	public Map<ParamInfo, AbstractParameter> getEditableParameters()
	{
		return new HashMap<ParamInfo, AbstractParameter>(editableNonInputParameters);
	}
	
	public Map<ParamInfo, AbstractParameter> getInternalParameters()
	{
		return new HashMap<ParamInfo, AbstractParameter>(internalParameters);
	}
	
	public Collection<AbstractSlot> getInputSlots()
	{
		return new ArrayList<AbstractSlot>(inputSlots);
	}

	public Collection<AbstractSlot> getOutputSlots()
	{
		return new ArrayList<AbstractSlot>(outputSlots);
	}
	
	// ---------------------------------------------------------------------------
	// OTHER PUBLIC METHODS

	public void addEditableParameter(ParamInfo paramInfo, AbstractParameter param)
	{
		this.editableNonInputParameters.put(paramInfo, param);
	}
	
	public void addInternalParameter(ParamInfo paramInfo, AbstractParameter param)
	{
		this.internalParameters.put(paramInfo, param);
	}
	
	public void addInputSlot(AbstractSlot slot)
	{
		this.inputSlots.add(slot);
	}
	
	public void addOutputSlot(AbstractSlot slot)
	{
		this.outputSlots.add(slot);
	}
	
	public boolean isOnlyConsumer()
	{
		return !inputSlots.isEmpty() && outputSlots.isEmpty();
	}
	
	public boolean isOnlyProvider()
	{
		return inputSlots.isEmpty() && !outputSlots.isEmpty();
	}
}
