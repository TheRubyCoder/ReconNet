package petrinetze.impl;

import java.util.ArrayList;
import java.util.List;

import petrinet.model.ActionType;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IPetrinetListener;
import petrinet.model.Petrinet;


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

	List<IArc> AddedEdges   = new ArrayList<IArc>();
	List<IArc> ChangedEdges = new ArrayList<IArc>();
	List<IArc> DeletedEdges = new ArrayList<IArc>();
	
	@Override
	public void changed(Petrinet petrinet, INode element,
			ActionType actionType) 
	{
		if(actionType == ActionType.ADDED)
			AddedNodes.add(element);
		else if(actionType == ActionType.CHANGED)
			ChangedNodes.add(element);
		else if(actionType == ActionType.REMOVED)
			DeletedNodes.add(element);
		
	}

	@Override
	public void changed(Petrinet petrinet, IArc element,
			ActionType actionType) 
	{
			if(actionType == ActionType.ADDED)
				AddedEdges.add(element);
			else if(actionType == ActionType.CHANGED)
				ChangedEdges.add(element);
			else if(actionType == ActionType.REMOVED)
				DeletedEdges.add(element);
	}
}
	