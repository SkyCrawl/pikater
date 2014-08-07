package org.pikater.shared.experiment.webformat.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pikater.shared.experiment.webformat.IExperimentGraph;
import org.pikater.shared.experiment.webformat.client.ExperimentGraphClient;
import org.pikater.shared.util.SimpleIDGenerator;

public class ExperimentGraphServer implements IExperimentGraph<Integer, BoxInfoServer>
{
	/**
	 * ID generator for boxes. 
	 */
	public SimpleIDGenerator boxIDGenerator;

	/**
	 * The 1:1 map containing all the boxes.
	 */
	public Map<Integer, BoxInfoServer> leafBoxes;
	
	/**
	 * Collection of oriented edges between boxes, sorted by the "from end point".
	 */
	public Map<Integer, Set<Integer>> edges;
	
	// ------------------------------------------------------------------
	// CONSTRUCTOR

	/** PUBLIC DEFAULT CONSTRUCTOR keeps Vaadin happy. */
	public ExperimentGraphServer()
	{
		this.boxIDGenerator = new SimpleIDGenerator();
		this.leafBoxes = new HashMap<Integer, BoxInfoServer>();
		this.edges = new HashMap<Integer, Set<Integer>>();
	}
	
	// ------------------------------------------------------------------
	// INHERITED INTERFACE
	
	@Override
	public void clear()
	{
		leafBoxes.clear();
		edges.clear();
		boxIDGenerator.reset();
	}
	
	@Override
	public boolean containsBox(Integer boxID)
	{
		return leafBoxes.containsKey(boxID);
	}
	
	@Override
	public BoxInfoServer getBox(Integer boxID)
	{
		return leafBoxes.get(boxID);
	}
	
	@Override
	public BoxInfoServer addBox(BoxInfoServer box)
	{
		box.setID(boxIDGenerator.getAndIncrement());
		leafBoxes.put(box.getID(), box);
		return box;
	}
	
	@Override
	public boolean edgesDefinedFor(Integer boxID)
	{
		return (edges.get(boxID) != null) && !edges.get(boxID).isEmpty(); 
	}
	
	/*
	public Integer addWrapperBoxAndReturnID(UniversalGui guiInfo, AbstractBox... childBoxes) 
	{
		// TODO: problems with overlapping of LeafBoxes?
		
		WrapperBox newBox = new WrapperBox(idGenerator.getAndIncrement(), guiInfo, childBoxes);
		allBoxes.put(newBox.id, newBox);
		return newBox.id;
	}
	*/
	
	@Override
	public void connect(Integer fromBoxKey, Integer toBoxKey)
	{
		interboxConnectionAction(fromBoxKey, toBoxKey, true);
	}
	
	@Override
	public void disconnect(Integer fromBoxKey, Integer toBoxKey)
	{
		interboxConnectionAction(fromBoxKey, toBoxKey, false);
	}
	
	// ------------------------------------------------------------------
	// PUBLIC INTERFACE
	
	public boolean isValid()
	{
		for(Entry<Integer, Set<Integer>> entry : edges.entrySet())
		{
			BoxInfoServer boxFrom = leafBoxes.get(entry.getKey());
			for(Integer boxToID : entry.getValue())
			{
				BoxInfoServer boxTo = leafBoxes.get(boxToID);
				if(boxFrom.isRegistered() != boxTo.isRegistered())
				{
					return false;
				}
				// TODO: should we delete the edge if both endpoints are not registered?
			}
		}
		for(BoxInfoServer webBox : leafBoxes.values())
		{
			if(!webBox.isValid())
			{
				return false;
			}
		}
		return true;
	}
	
	public ExperimentGraphClient toClientFormat()
	{
		ExperimentGraphClient result = new ExperimentGraphClient();
		for(BoxInfoServer box : leafBoxes.values())
		{
			result.addBox(box.toClientFormat());
		}
		result.edges = new HashMap<Integer, Set<Integer>>(edges); // simply copy, all IDs are kept intact
		return result;
	}
	
	// ------------------------------------------------------------------
	// PRIVATE INTERFACE
	
	private void interboxConnectionAction(Integer fromBoxKey, Integer toBoxKey, boolean connect)
	{
		/*
		 * Let this method have a transaction-like manner and only alter the data when
		 * everything has been checked and approved.
		 */
		
		// first, all kinds of checks before actually doing anything significant
		if((fromBoxKey == null) || (toBoxKey == null)) // boxes have not been added to the structure
		{
			throw new IllegalArgumentException("Cannot add this edge because at least one of the boxes was not added to the structure. "
					+ "Call the 'addBox()' method first and try again.");
		}
		if(!leafBoxes.containsKey(fromBoxKey) || !leafBoxes.containsKey(toBoxKey))
		{
			throw new IllegalArgumentException("One of the supplied box keys represents a wrapper box. Cannot add edges to wrapper boxes.");
		}
		if(connect) // we want to connect the boxes
		{
			if((edges.get(fromBoxKey) != null) && edges.get(fromBoxKey).contains(toBoxKey)) // an edge already exists
			{
				throw new IllegalStateException("Cannot add an edge from box '" + String.valueOf(fromBoxKey) + "' to box '" +
						String.valueOf(toBoxKey) + "': they are already connected.");
			}
		}
		else // we want to disconnect the boxes
		{
			if((edges.get(fromBoxKey) == null) || !edges.get(fromBoxKey).contains(toBoxKey)) // the edge doesn't exist
			{
				throw new IllegalStateException("Cannot remove the edge from box '" + String.valueOf(fromBoxKey) + "' to box '" +
						String.valueOf(toBoxKey) + "': they are not connected.");
			}
		}

		// and finally, let's add or remove the edge
		if(connect)
		{
			// add edge
			if(!edges.containsKey(fromBoxKey))
			{
				edges.put(fromBoxKey, new HashSet<Integer>());
			}
			edges.get(fromBoxKey).add(toBoxKey);
		}
		else
		{
			// remove edge
			edges.get(fromBoxKey).remove(toBoxKey);
		}
	}
}