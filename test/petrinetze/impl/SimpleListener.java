package petrinetze.impl;

import java.util.ArrayList;
import java.util.List;

import petrinetze.ActionType;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPetrinetListener;


/**
 * 
 * Simple Listener
 * @author Philipp Kühn
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

	List<IArc> AddedEdges = new ArrayList<IArc>();
	List<IArc> ChangedEdges = new ArrayList<IArc>();
	List<IArc> DeletedEdges = new ArrayList<IArc>();
	
	@Override
	public void changed(IPetrinet petrinet, INode element,
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
	public void changed(IPetrinet petrinet, IArc element,
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
	