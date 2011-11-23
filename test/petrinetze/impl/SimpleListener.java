package petrinetze.impl;

import java.util.ArrayList;
import java.util.List;

import petrinet.ActionType;
import petrinet.Arc;
import petrinet.INode;
import petrinet.IPetrinetListener;
import petrinet.Petrinet;


/**
 * 
 * Simple Listener
 * @author Philipp Kuehn
 *
 */
public class SimpleListener implements IPetrinetListener
{
	public SimpleListener()
	{
	
	}
	List<INode> AddedNodes = new ArrayList<INode>();
	List<INode> ChangedNodes = new ArrayList<INode>();
	List<INode> DeletedNodes = new ArrayList<INode>();

	List<Arc> AddedEdges = new ArrayList<Arc>();
	List<Arc> ChangedEdges = new ArrayList<Arc>();
	List<Arc> DeletedEdges = new ArrayList<Arc>();
	
	@Override
	public void changed(Petrinet petrinet, INode element,
			ActionType actionType) 
	{
		if(actionType == ActionType.added)
			AddedNodes.add(element);
		else if(actionType == ActionType.changed)
			ChangedNodes.add(element);
		else if(actionType == ActionType.deleted)
			DeletedNodes.add(element);
		
	}

	@Override
	public void changed(Petrinet petrinet, Arc element,
			ActionType actionType) 
	{
			if(actionType == ActionType.added)
				AddedEdges.add(element);
			else if(actionType == ActionType.changed)
				ChangedEdges.add(element);
			else if(actionType == ActionType.deleted)
				DeletedEdges.add(element);
	}
}
	