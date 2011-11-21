package transformation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import petrinetze.ActionType;
import petrinetze.Arc;
import petrinetze.INode;
import petrinetze.Petrinet;
import petrinetze.IPetrinetListener;
import petrinetze.Place;
import petrinetze.Transition;
import petrinetze.Petrinet;

/**
 * 
 * @author Philipp Kuehn
 * @author Marvin Ede
 * @author Oliver Willhoeft
 *
 */

public class Rule implements IRule
{
	
	/** K part of the rule */
	private final Petrinet k;
	/** L part of the rule */
	private final Petrinet l;
	/** R part of the rule */
	private final Petrinet r;
	/** Listener that listens to changes in K */
	private final Listener kListener;
	/** Listener that listens to changes in L */
	private final Listener lListener;
	/** Listener that listens to changes in R */
	private final Listener rListener;
	/** Mapping of nodes between L and K */
	private final Map<INode, INode> lKSameNodes;
	/** Mapping of nodes between R and K */
	private final Map<INode, INode> rKSameNodes;
	/** Mapping of edges between L and K */
	private final Map<Arc, Arc> lkSameEdges;
	/** Mapping of edges between R and K */
	private final Map<Arc, Arc> rKSameEdges;
	

	/** Indicates that R has initially been changed to prevent cycle.<br\> Used in the Listener */ 
	private boolean rChanging = false;
	/** Indicates that K has initially been changed to prevent cycle.<br\> Used in the Listener */ 
	private boolean kChanging = false;
	/** Indicates that L has initially been changed to prevent cycle.<br\> Used in the Listener */ 
	private boolean lChanging = false;
	
	/**
	 * Creates an empty rule
	 */
	public Rule() {
		k = new Petrinet();
		l = new Petrinet();
		r = new Petrinet();
		
		lKSameNodes = new HashMap<INode, INode>();
		rKSameNodes = new HashMap<INode, INode>();
		lkSameEdges = new HashMap<Arc, Arc>();
		rKSameEdges = new HashMap<Arc, Arc>();
		
		kListener = new Listener(Net.K, l, k, r);
		lListener = new Listener(Net.L, l, k, r);
		rListener = new Listener(Net.R, l, k, r);
	}
	
	/*
	 * Make sure garbage collector also kills the listeners
	 * (Not quite sure?)
	 */
	@Override
	protected void finalize() throws Throwable 
	{
		super.finalize();
		k.removePetrinetListener(kListener);
		l.removePetrinetListener(lListener);
		r.removePetrinetListener(rListener);
	}

	/**
	 * @see IRule#getK()
	 */
	@Override
	public Petrinet getK() {
		return k;
	}

	/**
	 * @see IRule#getL()
	 */
	@Override
	public Petrinet getL() {
		return l;
	}

	/**
	 * @see IRule#getR()
	 */
	@Override
	public Petrinet getR() {
		return r;
	}

	/**
	 * Gets the key from a given value in a map. <br/> Does not check for multiple keys
	 * @param map The map to look at
	 * @param value The value to find the key to
	 * @return First key that is found<br/><tt>null</tt> if no key found
	 */
	private <K, V> K getKeyFromValue(Map<K, V> map, V value)
	{
		for(Entry<K, V> e : map.entrySet())
			if(e.getValue().equals(value))
				return e.getKey();
		return null;
	}

	/**
	 * @see IRule#fromKtoL(INode)
	 */
	@Override
	public INode fromKtoL(INode node) {
		return getKeyFromValue(lKSameNodes, node);
	}

	/**
	 * @see IRule#fromKtoL(Arc)
	 */
	@Override
	public Arc fromKtoL(Arc edge) {
		return getKeyFromValue(lkSameEdges, edge);
	}

	/**
	 * @see IRule#fromKtoR(INode)
	 */
	@Override
	public INode fromKtoR(INode node) {
		return getKeyFromValue(rKSameNodes, node);
	}

	/**
	 * @see IRule#fromKtoR(Arc)
	 */
	@Override
	public Arc fromKtoR(Arc edge) {
		return getKeyFromValue(rKSameEdges, edge);
	}

	/**
	 * @see IRule#fromLtoK(INode)
	 */
	@Override
	public INode fromLtoK(INode node) {
		if(lKSameNodes.containsKey(node))
			return lKSameNodes.get(node);
		return null;
	}

	/**
	 * @see IRule#fromLtoK(Arc)
	 */
	@Override
	public Arc fromLtoK(Arc edge) {
		if(lkSameEdges.containsKey(edge))
			return lkSameEdges.get(edge);
		return null;
	}

	/**
	 * @see IRule#fromRtoK(INode)
	 */
	@Override
	public INode fromRtoK(INode node) {
		if(rKSameNodes.containsKey(node))
			return rKSameNodes.get(node);
		return null;
	}

	/**
	 * @see IRule#fromRtoK(Arc)
	 */
	@Override
	public Arc fromRtoK(Arc edge) {
		if(rKSameEdges.containsKey(edge))
			return rKSameEdges.get(edge);
		return null;
	}
	
	/** Enum for Listener so they know what they are listening to */
	private enum Net { L,K,R }
	

	/**
	 * 
	 * This Listener is used to listen for events on the L K and R nets.
	 * When anything is added to K, it will get added to R and L.
	 * When anything is added to L or R, it will get added to K.
	 *
	 */
	private class Listener implements IPetrinetListener
	{
		private final Net net;
		private final Petrinet k;
		private final Petrinet l;
		private final Petrinet r;
		
		Listener(Net net, Petrinet l, Petrinet k, Petrinet r)
		{
			this.net = net;
			this.k = k;
			this.l = l;
			this.r = r;
			if(net == Net.L)
				l.addPetrinetListener(this);
			else if(net == Net.K)
				k.addPetrinetListener(this);
			else if(net == Net.R)
				r.addPetrinetListener(this);
			
		}

		@Override
		public void changed(Petrinet petrinet, INode element, ActionType actionType) 
		{
			if(net == Net.L)
			{
				if(!kChanging && !rChanging)
				{
					lChanging = true;
					lNodeChanged(element, actionType);
					lChanging = false;
				}
			}
			else if(net == Net.K)
			{
				if(!lChanging && !rChanging)
				{
					kChanging = true;
					kNodeChanged(element, actionType);
					kChanging = false;
				}
			}
			else if(net == Net.R)
			{
				if(!lChanging && !kChanging)
				{
					rChanging = true;
					rNodeChanged(element, actionType);
					rChanging = false;
				}
			}
		}

		@Override
		public void changed(Petrinet petrinet, Arc element, ActionType actionType) 
		{
			if(net == Net.L)
			{
				if(!kChanging && !rChanging)
				{
					lChanging = true;
					lEdgeChanged(element, actionType);
					lChanging = false;
				}
			}
			else if(net == Net.K)
			{
				if(!lChanging && !rChanging)
				{
					kChanging = true;
					kEdgeChanged(element, actionType);
					kChanging = false;
				}
			}
			else if(net == Net.R)
			{
				if(!lChanging && !kChanging)
				{
					rChanging = true;
					rEdgeChanged(element, actionType);
					rChanging = false;
				}
			}
		}
		
		private void lNodeChanged(INode element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(element instanceof Place && !lKSameNodes.containsKey(element))
				{
					INode node = k.createPlace(element.getName());
					lKSameNodes.put(element, node);
				}
				else if(element instanceof Transition && !lKSameNodes.containsKey(element))
				{
					INode node = k.createTransition(element.getName(), ((Transition)element).getRnw());
					lKSameNodes.put(element, node);
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(element instanceof Place && !r.getAllPlaces().contains(element) && lKSameNodes.containsKey(element))
				{
					INode node = lKSameNodes.get(element);
					lKSameNodes.remove(element);
					k.deletePlaceById(node.getId());
				}
				else if(element instanceof Transition && !r.getAllTransitions().contains(element) && lKSameNodes.containsKey(element))
				{
					INode node = lKSameNodes.get(element);
					lKSameNodes.remove(element);
					k.deleteTransitionByID(node.getId());
				}
			}
		}
		private void kNodeChanged(INode element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(element instanceof Place)
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
				else if(element instanceof Transition)
				{
					if(!lKSameNodes.containsValue(element))
					{
						INode node = l.createTransition(element.getName(), ((Transition)element).getRnw());
						lKSameNodes.put(node, element);
					}
					if(!rKSameNodes.containsValue(element))
					{
						INode node = r.createTransition(element.getName(), ((Transition)element).getRnw());
						rKSameNodes.put(node, element);
					}
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(element instanceof Place)
				{
					if(lKSameNodes.containsValue(element))
					{
						INode node = getKeyFromValue(lKSameNodes, element);
						lKSameNodes.remove(element);
						l.deletePlaceById(node.getId());
					}
					if(rKSameNodes.containsValue(element))
					{
						INode node = getKeyFromValue(rKSameNodes, element);
						rKSameNodes.remove(element);
						r.deletePlaceById(node.getId());
					}
				}
				else if(element instanceof Transition && !r.getAllTransitions().contains(element) && lKSameNodes.containsKey(element))
				{
					if(lKSameNodes.containsValue(element))
					{
						INode node = getKeyFromValue(lKSameNodes, element);
						lKSameNodes.remove(element);
						l.deletePlaceById(node.getId());
					}
					if(rKSameNodes.containsValue(element))
					{
						INode node = getKeyFromValue(rKSameNodes, element);
						rKSameNodes.remove(element);
						r.deletePlaceById(node.getId());
					}
				}
			}
		}
		
		private void rNodeChanged(INode element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(element instanceof Place && !rKSameNodes.containsKey(element))
				{
					INode node = k.createPlace(element.getName());
					rKSameNodes.put(element, node);
				}
				else if(element instanceof Transition && !rKSameNodes.containsKey(element))
				{
					INode node = k.createTransition(element.getName(), ((Transition)element).getRnw());
					rKSameNodes.put(element, node);
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(element instanceof Place && !r.getAllPlaces().contains(element) && rKSameNodes.containsKey(element))
				{
					INode node = rKSameNodes.get(element);
					rKSameNodes.remove(element);
					k.deletePlaceById(node.getId());
				}
				else if(element instanceof Transition && !r.getAllTransitions().contains(element) && rKSameNodes.containsKey(element))
				{
					INode node = rKSameNodes.get(element);
					rKSameNodes.remove(element);
					k.deleteTransitionByID(node.getId());
				}
			}
		}
		
		private void lEdgeChanged(Arc element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(!lkSameEdges.containsKey(element))
				{
                    INode start = lKSameNodes.get(element.getStart());
                    INode end = lKSameNodes.get(element.getEnd());
					Arc edge = k.createArc(element.getName(), start, end);

					lkSameEdges.put(element, edge);
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(lkSameEdges.containsKey(element))
				{
					Arc edge = lkSameEdges.get(element);
					lkSameEdges.remove(element);
					k.deleteArcByID(edge.getId());
				}
			}
		}
		private void kEdgeChanged(Arc element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(!lkSameEdges.containsValue(element))
				{
                    INode start = getKeyFromValue(lKSameNodes, element.getStart());
                    INode end = getKeyFromValue(lKSameNodes, element.getEnd());

                    Arc edge = l.createArc(element.getName(), start, end);


                    lkSameEdges.put(edge, element);
				}
				if(!rKSameEdges.containsValue(element))
				{
                    INode start = getKeyFromValue(rKSameNodes, element.getStart());
                    INode end = getKeyFromValue(rKSameNodes, element.getEnd());
                    Arc edge = r.createArc(element.getName(), start, end);
                    rKSameEdges.put(edge, element);
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(lkSameEdges.containsValue(element))
				{
					Arc edge = getKeyFromValue(lkSameEdges, element);
					lkSameEdges.remove(element);
					l.deleteArcByID(edge.getId());
				}
				if(rKSameEdges.containsValue(element))
				{
					Arc edge = getKeyFromValue(rKSameEdges, element);
					rKSameEdges.remove(element);
					r.deleteArcByID(edge.getId());
				}
			}
		}
		
		private void rEdgeChanged(Arc element, ActionType actionType)
		{
			if(actionType == ActionType.added)
			{
				if(!rKSameEdges.containsKey(element))
				{
                    INode start = (rKSameNodes.get(element.getStart()));
                    INode end = rKSameNodes.get(element.getEnd());
                    Arc edge = k.createArc(element.getName(), start, end);
					rKSameEdges.put(element, edge);
				}
			}
			else if(actionType == ActionType.deleted)
			{
				if(rKSameEdges.containsKey(element))
				{
					Arc edge = rKSameEdges.get(element);
					rKSameEdges.remove(element);
					k.deleteArcByID(edge.getId());
				}
			}
		}	
	}


}
