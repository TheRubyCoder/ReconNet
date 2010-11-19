package transformation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import petrinetze.ActionType;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPetrinetListener;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.Petrinet;

public class Rule implements IRule
{
	
	private final IPetrinet k;
	private final IPetrinet l;
	private final IPetrinet r;
	private final Listener kListener;
	private final Listener lListener;
	private final Listener rListener;
	public Rule()
	{
		k = new Petrinet();
		l = new Petrinet();
		r = new Petrinet();
		
		kListener = new Listener(Net.k, l, k, r);
		lListener = new Listener(Net.l, l, k, r);
		rListener = new Listener(Net.r, l, k, r);
	}
	
	@Override
	protected void finalize() throws Throwable 
	{
		super.finalize();
		k.removePetrinetListener(kListener);
		l.removePetrinetListener(lListener);
		r.removePetrinetListener(rListener);
	}

	@Override
	public IPetrinet K() {
		return k;
	}

	@Override
	public IPetrinet L() {
		return l;
	}

	@Override
	public IPetrinet R() {
		return r;
	}
	
	private enum Net
	{
		l,
		k,
		r
	}
	
	private class Listener implements IPetrinetListener
	{
		private final Net net;
		private final IPetrinet k;
		private final IPetrinet l;
		private final IPetrinet r;
		private final Map<INode, INode> lKSameNodes;
		private final Map<INode, INode> rKSameNodes;
		private final Map<IArc, IArc> lkSameEdges;
		private final Map<IArc, IArc> rKSameEdges;
		
		Listener(Net net, IPetrinet l, IPetrinet k, IPetrinet r)
		{
			this.net = net;
			this.k = k;
			this.l = l;
			this.r = r;
			if(net == Net.l)
				l.addPetrinetListener(this);
			else if(net == Net.k)
				k.addPetrinetListener(this);
			else if(net == Net.r)
				r.addPetrinetListener(this);
			
			lKSameNodes = new HashMap<INode, INode>();
			rKSameNodes = new HashMap<INode, INode>();
			lkSameEdges = new HashMap<IArc, IArc>();
			rKSameEdges = new HashMap<IArc, IArc>();
		}

		@Override
		public void changed(IPetrinet petrinet, INode element, ActionType actionType) 
		{
			if(net == Net.l)
			{
				lNodeChanged(element, actionType);
			}
			else if(net == Net.k)
			{
				kNodeChanged(element, actionType);
			}
			else if(net == Net.r)
			{
				rNodeChanged(element, actionType);
			}
		}

		@Override
		public void changed(IPetrinet petrinet, IArc element, ActionType actionType) 
		{
			if(net == Net.l)
			{
				lEdgeChanged(element, actionType);
			}
			else if(net == Net.k)
			{
				kEdgeChanged(element, actionType);
			}
			else if(net == Net.r)
			{
				rEdgeChanged(element, actionType);
			}
		}
		
		private void lNodeChanged(INode element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(element instanceof IPlace && !lKSameNodes.containsKey(element))
				{
					INode node = k.createPlace(element.getName());
					lKSameNodes.put(element, node);
				}
				else if(element instanceof ITransition && !lKSameNodes.containsKey(element))
				{
					INode node = k.createTransition(element.getName());
					lKSameNodes.put(element, node);
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(element instanceof IPlace && !r.getAllPlaces().contains(element) && lKSameNodes.containsKey(element))
				{
					k.deletePlaceById(lKSameNodes.get(element).getId());
					lKSameNodes.remove(element);
				}
				else if(element instanceof ITransition && !r.getAllTransitions().contains(element) && lKSameNodes.containsKey(element))
				{
					k.deleteTransitionByID(lKSameNodes.get(element).getId());
					lKSameNodes.remove(element);
				}
			}
		}
		private void kNodeChanged(INode element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(element instanceof IPlace)
				{
					if(!lKSameNodes.containsValue(element))
					{
						INode node = l.createPlace(element.getName());
						lKSameNodes.put(node, element);
					}
					if(!rKSameNodes.containsValue(element))
					{
						INode node = r.createPlace(element.getName());
						rKSameNodes.put(node, element);
					}
				}
				else if(element instanceof ITransition)
				{
					if(!lKSameNodes.containsValue(element))
					{
						INode node = l.createTransition(element.getName());
						lKSameNodes.put(node, element);
					}
					if(!rKSameNodes.containsValue(element))
					{
						INode node = r.createTransition(element.getName());
						rKSameNodes.put(node, element);
					}
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(element instanceof IPlace)
				{
					if(lKSameNodes.containsValue(element))
					{
						l.deletePlaceById(getKeyFromValue(lKSameNodes, element).getId());
						lKSameNodes.remove(element);
					}
					if(rKSameNodes.containsValue(element))
					{
						r.deletePlaceById(getKeyFromValue(rKSameNodes, element).getId());
						rKSameNodes.remove(element);
					}
				}
				//TODO hier weitermachen
				else if(element instanceof ITransition && !r.getAllTransitions().contains(element) && lKSameNodes.containsKey(element))
				{
					k.deleteTransitionByID(lKSameNodes.get(element).getId());
					lKSameNodes.remove(element);
				}
			}
		}
		
		private void rNodeChanged(INode element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				
			}
			else if(actionType == ActionType.deleted)
			{
				
			}
		}
		
		private void lEdgeChanged(IArc element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				
			}
			else if(actionType == ActionType.deleted)
			{
				
			}
		}
		private void kEdgeChanged(IArc element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				
			}
			else if(actionType == ActionType.deleted)
			{
				
			}
		}
		
		private void rEdgeChanged(IArc element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				
			}
			else if(actionType == ActionType.deleted)
			{
				
			}
		}
		
		private <K, V> K getKeyFromValue(Map<K, V> map, V value)
		{
			for(Entry<K, V> e : map.entrySet())
				if(e.getValue().equals(value))
					return e.getKey();
			return null;
		}
		
	}


}
