package transformation;

import static transformation.dependency.PetrinetAdapter.createPetrinet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import petrinet.model.ActionType;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IPetrinetListener;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;

/**
 * An Interface for Rules<br\>
 * Rules define how a petrinet can be reconfigured<br\>
 * Where L must be found in the petrinet, K is the context of all involved nodes
 * and R is the resulting part-graph
 */

public class Rule {

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
	private final Map<IArc, IArc> lkSameEdges;
	/** Mapping of edges between R and K */
	private final Map<IArc, IArc> rKSameEdges;

	/**
	 * Indicates that R has initially been changed to prevent cycle.<br\>
	 * Used in the Listener
	 */
	private boolean rChanging = false;
	/**
	 * Indicates that K has initially been changed to prevent cycle.<br\>
	 * Used in the Listener
	 */
	private boolean kChanging = false;
	/**
	 * Indicates that L has initially been changed to prevent cycle.<br\>
	 * Used in the Listener
	 */
	private boolean lChanging = false;

	/**
	 * Creates an empty rule
	 */
	Rule() {
		k = createPetrinet();
		l = createPetrinet();
		r = createPetrinet();

		lKSameNodes = new HashMap<INode, INode>();
		rKSameNodes = new HashMap<INode, INode>();
		lkSameEdges = new HashMap<IArc, IArc>();
		rKSameEdges = new HashMap<IArc, IArc>();

		kListener = new Listener(Net.K, l, k, r);
		lListener = new Listener(Net.L, l, k, r);
		rListener = new Listener(Net.R, l, k, r);
	}

	/**
	 * Returns the gluing Petrinet of this rule.
	 * 
	 * @return the gluing Petrinet of this rule.
	 */
	public Petrinet getK() {
		return k;
	}

	/**
	 * Returns the left Petrinet of this rule.
	 * 
	 * @return the left Petrinet of this rule.
	 */
	public Petrinet getL() {
		return l;
	}

	/**
	 * Returns the right Petrinet of this rule.
	 * 
	 * @return the right Petrinet of this rule.
	 */
	public Petrinet getR() {
		return r;
	}

	public void removeNodeOrArc(INode nodeOrArc) {
		if (nodeOrArc instanceof IArc) {
			List<IArc> mappings = getMappings((IArc) nodeOrArc);
			if (mappings.get(0) != null && getL().containsArc(mappings.get(0).getId())) {
				getL().removeArc(mappings.get(0).getId());
			}
			
			if (mappings.get(1) != null && getK().containsArc(mappings.get(1).getId())) {
				getK().removeArc(mappings.get(1).getId());
			}
			if (mappings.get(2) != null && getR().containsArc(mappings.get(2).getId())) {
				getR().removeArc(mappings.get(2).getId());
			}
		} else {
			List<INode> mappings = getMappings(nodeOrArc);
			if (mappings.get(0) != null) {
				getL().removeElement(mappings.get(0).getId());
			}
			if (mappings.get(1) != null) {
				getK().removeElement(mappings.get(1).getId());
			}
			if (mappings.get(2) != null) {
				getR().removeElement(mappings.get(2).getId());
			}
		}
	}

	/**
	 * Gets the key from a given value in a map. <br/>
	 * Does not check for multiple keys
	 * 
	 * @param map
	 *            The map to look at
	 * @param value
	 *            The value to find the key to
	 * @return First key that is found<br/>
	 *         <tt>null</tt> if no key found
	 */
	private <K, V> K getKeyFromValue(Map<K, V> map, V value) {
		for (Entry<K, V> e : map.entrySet())
			if (e.getValue().equals(value))
				return e.getKey();
		return null;
	}

	/**
	 * Returns the corresponding node in L.
	 * 
	 * @param node
	 *            a node in K.
	 * @return the corresponding node in L.
	 */
	public INode fromKtoL(INode node) {
		return getKeyFromValue(lKSameNodes, node);
	}

	/**
	 * Returns the corresponding edge in L.
	 * 
	 * @param edge
	 *            a edge in K.
	 * @return the corresponding edge in L.
	 */
	public IArc fromKtoL(IArc edge) {
		return getKeyFromValue(lkSameEdges, edge);
	}

	/**
	 * Returns the corresponding node in R.
	 * 
	 * @param node
	 *            a node in K.
	 * @return the corresponding node in R.
	 */
	public INode fromKtoR(INode node) {
		return getKeyFromValue(rKSameNodes, node);
	}

	/**
	 * Returns the corresponding edge in R.
	 * 
	 * @param edge
	 *            a edge in K.
	 * @return the corresponding edge in R.
	 */
	public IArc fromKtoR(IArc edge) {
		return getKeyFromValue(rKSameEdges, edge);
	}

	/**
	 * Returns the corresponding node in K.
	 * 
	 * @param node
	 *            a node in L.
	 * @return the corresponding node in K.
	 */
	public INode fromLtoK(INode node) {
		if (lKSameNodes.containsKey(node))
			return lKSameNodes.get(node);
		return null;
	}

	/**
	 * Returns the corresponding edge in K.
	 * 
	 * @param edge
	 *            a edge in L.
	 * @return the corresponding edge in K.
	 */
	public IArc fromLtoK(IArc edge) {
		if (lkSameEdges.containsKey(edge))
			return lkSameEdges.get(edge);
		return null;
	}

	/**
	 * Returns the corresponding node in K.
	 * 
	 * @param node
	 *            a node in R.
	 * @return the corresponding node in K.
	 */
	public INode fromRtoK(INode node) {
		if (rKSameNodes.containsKey(node))
			return rKSameNodes.get(node);
		return null;
	}

	/**
	 * Returns the corresponding edge in K.
	 * 
	 * @param edge
	 *            a edge in R.
	 * @return the corresponding edge in K.
	 */
	public IArc fromRtoK(IArc edge) {
		if (rKSameEdges.containsKey(edge))
			return rKSameEdges.get(edge);
		return null;
	}

	/** Enum for Listener so they know what they are listening to */
	private enum Net {
		L, K, R
	}

	/**
	 * 
	 * This Listener is used to listen for events on the L K and R nets. When
	 * anything is added to K, it will get added to R and L. When anything is
	 * added to L or R, it will get added to K. The same goes for deletion of
	 * nodes.
	 * 
	 */
	private class Listener implements IPetrinetListener {
		private final Net net;
		private final Petrinet k;
		private final Petrinet l;
		private final Petrinet r;

		Listener(Net net, Petrinet l, Petrinet k, Petrinet r) {
			this.net = net;
			this.k = k;
			this.l = l;
			this.r = r;
			if (net == Net.L)
				l.addPetrinetListener(this);
			else if (net == Net.K)
				k.addPetrinetListener(this);
			else if (net == Net.R)
				r.addPetrinetListener(this);

		}

		@Override
		public void changed(Petrinet petrinet, INode element,
				ActionType actionType) {
			if (net == Net.L) {
				if (!kChanging && !rChanging) {
					lChanging = true;
					lNodeChanged(element, actionType);
					lChanging = false;
				}
			} else if (net == Net.K) {
				if (!lChanging && !rChanging) {
					kChanging = true;
					kNodeChanged(element, actionType);
					kChanging = false;
				}
			} else if (net == Net.R) {
				if (!lChanging && !kChanging) {
					rChanging = true;
					rNodeChanged(element, actionType);
					rChanging = false;
				}
			}
		}

		@Override
		public void changed(Petrinet petrinet, IArc element,
				ActionType actionType) {
			if (net == Net.L) {
				if (!kChanging && !rChanging) {
					lChanging = true;
					lEdgeChanged(element, actionType);
					lChanging = false;
				}
			} else if (net == Net.K) {
				if (!lChanging && !rChanging) {
					kChanging = true;
					kEdgeChanged(element, actionType);
					kChanging = false;
				}
			} else if (net == Net.R) {
				if (!lChanging && !kChanging) {
					rChanging = true;
					rEdgeChanged(element, actionType);
					rChanging = false;
				}
			}
		}

		private void lNodeChanged(INode element, ActionType actionType) {
			if (actionType == ActionType.ADDED) {
				if (element instanceof Place
						&& !lKSameNodes.containsKey(element)) {
					INode node = k.addPlace(element.getName());
					lKSameNodes.put(element, node);
				} else if (element instanceof Transition
						&& !lKSameNodes.containsKey(element)) {
					INode node = k.addTransition(element.getName(),
							((Transition) element).getRnw());
					lKSameNodes.put(element, node);
				}
			} else if (actionType == ActionType.REMOVED) {
				if (element instanceof Place
						&& !r.getPlaces().contains(element)
						&& lKSameNodes.containsKey(element)) {
					INode node = lKSameNodes.get(element);
					lKSameNodes.remove(element);

					if (k.containsPlace(node.getId())) {
						k.removePlace(node.getId());
					}
				} else if (element instanceof Transition
						&& !r.getTransitions().contains(element)
						&& lKSameNodes.containsKey(element)) {
					INode node = lKSameNodes.get(element);
					lKSameNodes.remove(element);
					
					if (k.containsTransition(node.getId())) {
						k.removeTransition(node.getId());
					}
				}
			}
		}

		private void kNodeChanged(INode element, ActionType actionType) {
			if (actionType == ActionType.ADDED) {
				if (element instanceof Place) {
					if (!lKSameNodes.containsValue(element)) {
						INode node = l.addPlace(element.getName());
						lKSameNodes.put(node, element);
					}
					if (!rKSameNodes.containsValue(element)) {
						INode node = r.addPlace(element.getName());
						rKSameNodes.put(node, element);
					}
				} else if (element instanceof Transition) {
					if (!lKSameNodes.containsValue(element)) {
						INode node = l.addTransition(element.getName(),
								((Transition) element).getRnw());
						lKSameNodes.put(node, element);
					}
					if (!rKSameNodes.containsValue(element)) {
						INode node = r.addTransition(element.getName(),
								((Transition) element).getRnw());
						rKSameNodes.put(node, element);
					}
				}
			} else if (actionType == ActionType.REMOVED) {
				if (element instanceof Place) {
					if (lKSameNodes.containsValue(element)) {
						INode node = getKeyFromValue(lKSameNodes, element);
						lKSameNodes.remove(element);
                        
                        if (l.containsPlace(node.getId())) {
                            l.removePlace(node.getId());
                        }
					}
					if (rKSameNodes.containsValue(element)) {
						INode node = getKeyFromValue(rKSameNodes, element);
						rKSameNodes.remove(element);
                        
                        if (r.containsPlace(node.getId())) {
                            r.removePlace(node.getId());
                        }
					}
				} else if (element instanceof Transition
						&& !r.getTransitions().contains(element)
						&& lKSameNodes.containsKey(element)) {
					if (lKSameNodes.containsValue(element)) {
						INode node = getKeyFromValue(lKSameNodes, element);
						lKSameNodes.remove(element);
                        
                        if (l.containsTransition(node.getId())) {
                            l.removeTransition(node.getId());
                        }
					}
					if (rKSameNodes.containsValue(element)) {
						INode node = getKeyFromValue(rKSameNodes, element);
						rKSameNodes.remove(element);
                        
                        if (r.containsTransition(node.getId())) {
                            r.removeTransition(node.getId());
                        }
					}
				}
			}
		}

		private void rNodeChanged(INode element, ActionType actionType) {
			if (actionType == ActionType.ADDED) {
				if (element instanceof Place
						&& !rKSameNodes.containsKey(element)) {
					INode node = k.addPlace(element.getName());
					rKSameNodes.put(element, node);
				} else if (element instanceof Transition
						&& !rKSameNodes.containsKey(element)) {
					INode node = k.addTransition(element.getName(),
							((Transition) element).getRnw());
					rKSameNodes.put(element, node);
				}
			} else if (actionType == ActionType.REMOVED) {
				if (element instanceof Place
						&& !r.getPlaces().contains(element)
						&& rKSameNodes.containsKey(element)) {
					INode node = rKSameNodes.get(element);
					rKSameNodes.remove(element);
					
                    if (k.containsPlace(node.getId())) {
                        k.removePlace(node.getId());
                    }
				} else if (element instanceof Transition
						&& !r.getTransitions().contains(element)
						&& rKSameNodes.containsKey(element)) {
					INode node = rKSameNodes.get(element);
					rKSameNodes.remove(element);
                            
                    if (k.containsTransition(node.getId())) {
                        k.removeTransition(node.getId());
                    }
				}
			}
		}

		private void lEdgeChanged(IArc element, ActionType actionType) {
			if (actionType == ActionType.ADDED) {
				if (!lkSameEdges.containsKey(element)) {
					INode start = lKSameNodes.get(element.getSource());
					INode end   = lKSameNodes.get(element.getTarget());

					IArc edge  = null;
					
					if (start instanceof Place && end instanceof Transition) {
						edge  = k.addPreArc(element.getName(), (Place) start, (Transition) end);
						
					} else if (start instanceof Transition && end instanceof Place) {
						edge  = k.addPostArc(element.getName(), (Transition) start, (Place) end);						
					}

					lkSameEdges.put(element, edge);
				}
			} else if (actionType == ActionType.REMOVED) {
				if (lkSameEdges.containsKey(element)) {
					IArc edge = lkSameEdges.get(element);
					lkSameEdges.remove(element);
                    
                    if (k.containsArc(edge.getId())) {
                        k.removeArc(edge.getId());
                    }
				}
			}
		}

		private void kEdgeChanged(IArc element, ActionType actionType) {
			if (actionType == ActionType.ADDED) {
				if (!lkSameEdges.containsValue(element)) {
					INode start = getKeyFromValue(lKSameNodes,
							element.getSource());
					INode end = getKeyFromValue(lKSameNodes, element.getTarget());

					IArc edge  = null;
					
					if (start instanceof Place && end instanceof Transition) {
						edge  = l.addPreArc(element.getName(), (Place) start, (Transition) end);
						
					} else if (start instanceof Transition && end instanceof Place) {
						edge  = l.addPostArc(element.getName(), (Transition) start, (Place) end);						
					}

					lkSameEdges.put(edge, element);
				}
				if (!rKSameEdges.containsValue(element)) {
					INode start = getKeyFromValue(rKSameNodes,
							element.getSource());
					INode end = getKeyFromValue(rKSameNodes, element.getTarget());

					IArc edge  = null;
					
					if (start instanceof Place && end instanceof Transition) {
						edge  = r.addPreArc(element.getName(), (Place) start, (Transition) end);
						
					} else if (start instanceof Transition && end instanceof Place) {
						edge  = r.addPostArc(element.getName(), (Transition) start, (Place) end);						
					}
					
					rKSameEdges.put(edge, element);
				}
			} else if (actionType == ActionType.REMOVED) {
				if (lkSameEdges.containsValue(element)) {
					IArc edge = getKeyFromValue(lkSameEdges, element);
					lkSameEdges.remove(element);
                    
                    if (l.containsArc(edge.getId())) {
                        l.removeArc(edge.getId());
                    }
				}
				if (rKSameEdges.containsValue(element)) {
					IArc edge = getKeyFromValue(rKSameEdges, element);
					rKSameEdges.remove(element);
                    
                    if (r.containsArc(edge.getId())) {
                        r.removeArc(edge.getId());
                    }
				}
			}
		}

		private void rEdgeChanged(IArc element, ActionType actionType) {
			if (actionType == ActionType.ADDED) {
				if (!rKSameEdges.containsKey(element)) {
					INode start = rKSameNodes.get(element.getSource());
					INode end = rKSameNodes.get(element.getTarget());
					
					IArc edge  = null;
					
					if (start instanceof Place && end instanceof Transition) {
						edge  = k.addPreArc(element.getName(), (Place) start, (Transition) end);
						
					} else if (start instanceof Transition && end instanceof Place) {
						edge  = k.addPostArc(element.getName(), (Transition) start, (Place) end);						
					}
					
					rKSameEdges.put(element, edge);
				}
			} else if (actionType == ActionType.REMOVED) {
				if (rKSameEdges.containsKey(element)) {
					IArc edge = rKSameEdges.get(element);
					rKSameEdges.remove(element);
                    
                    if (k.containsArc(edge.getId())) {
                        k.removeArc(edge.getId());
                    }
				}
			}
		}
	}

	/**
	 * @see ITransformation#setName(Rule, int, String)
	 */
	public void setName(int nodeId, String name) {
		Petrinet rulePart = getPetrinetOfNode(nodeId);
		INode node = rulePart.getNode(nodeId);
		for (INode correspondingNode : getMappings(node)) {
			if (correspondingNode != null) {
				correspondingNode.setName(name);
			}
		}

	}

	/**
	 * Sets the mark of the place that is specified by <code>placeId</code> and
	 * its mappings.
	 * 
	 * @param placeId
	 * @param mark
	 */
	void setMark(int placeId, int mark) {
		Petrinet rulePart = getPetrinetOfNode(placeId);
		if (rulePart != null) {
			// if place was in k...
			if (rulePart.getId() == k.getId()) {
				setMarkInK(k.getPlace(placeId), mark);
			}
			// if place was in l
			else if (rulePart.getId() == l.getId()) {
				setMarkInL(l.getPlace(placeId), mark);
			}
			// if place was in r
			else {
				setMarkInR(r.getPlace(placeId), mark);
			}
		}
	}

	/**
	 * Sets the mark for the place and its mappings, that is only in L and K
	 * 
	 * @param place
	 * @param mark
	 */
	private void setMarkInL(Place place, int mark) {
		place.setMark(mark);
		((Place) fromLtoK(place)).setMark(mark);
	}

	/**
	 * Sets the mark for the place and its mappings, thats in L, K and R
	 * 
	 * @param place
	 * @param mark
	 */
	private void setMarkInR(Place place, int mark) {
		place.setMark(mark);
		((Place) fromRtoK(place)).setMark(mark);
	}

	/**
	 * Sets the mark for the place and its mappings, thats only in K and R
	 * 
	 * @param place
	 * @param mark
	 */
	private void setMarkInK(Place place, int mark) {
		place.setMark(mark);
		((Place) fromKtoR(place)).setMark(mark);
		((Place) fromKtoL(place)).setMark(mark);
	}

	/**
	 * Finds the part of the rule in which a node is included
	 * 
	 * @param nodeId
	 * @return <tt> null </tt> if place is not in rule
	 */
	Petrinet getPetrinetOfNode(int nodeId) {
		for (Place place : k.getPlaces()) {
			if (place.getId() == nodeId)
				return k;
		}
		for (Transition transition: k.getTransitions()) {
			if (transition.getId() == nodeId)
				return k;
		}
		
		for (Place place : l.getPlaces()) {
			if (place.getId() == nodeId)
				return l;
		}
		for (Transition transition: l.getTransitions()) {
			if (transition.getId() == nodeId)
				return l;
		}
		
		for (Place place : r.getPlaces()) {
			if (place.getId() == nodeId)
				return r;
		}
		for (Transition transition: r.getTransitions()) {
			if (transition.getId() == nodeId)
				return r;
		}
		return null;
	}

	/**
	 * Similar to {@link ITransformation#getMappings(Rule, INode)} but with
	 * {@link INode} instead of {@link IArc}
	 */
	public List<INode> getMappings(INode node) {
		List<INode> mappings = new LinkedList<INode>();
		INode inL = null;
		INode inK = null;
		INode inR = null;
		if (node != null) {
			if (getL().getPlaces().contains(node)
					|| getL().getTransitions().contains(node)) {
				inL = node;
				inK = fromLtoK(inL);
				inR = fromKtoR(inK);
			} else if (getK().getPlaces().contains(node)
					|| getK().getTransitions().contains(node)) {
				inK = node;
				inL = fromKtoL(inK);
				inR = fromKtoR(inK);
			} else if (getR().getPlaces().contains(node)
					|| getR().getTransitions().contains(node)) {
				inR = node;
				inK = fromRtoK(inR);
				inL = fromKtoL(inK);
			} else {
				return null;
			}
		}
		mappings.add(inL);
		mappings.add(inK);
		mappings.add(inR);
		return mappings;
	}

	/**
	 * {@link ITransformation#getMappings(Rule, IArc)}
	 */
	public List<IArc> getMappings(IArc arc) {
		List<IArc> mappings = new LinkedList<IArc>();
		IArc inL = null;
		IArc inK = null;
		IArc inR = null;
		fromKtoR(null);
		if (getL().getArcs().contains(arc)) {
			inL = arc;
			inK = fromLtoK(inL);
			inR = fromKtoR(inK);
		} else if (getK().getArcs().contains(arc)) {
			inK = arc;
			inL = fromKtoL(inK);
			inR = fromKtoR(inK);
		} else if (getR().getArcs().contains(arc)) {
			inR = arc;
			inK = fromRtoK(inR);
			inL = fromLtoK(inL);
		} else {
			return null;
		}
		mappings.add(inL);
		mappings.add(inK);
		mappings.add(inR);
		return mappings;
	}

	/**
	 * Returns the nodes (in L) that will be deleted on applying the rule.
	 * 
	 * @return
	 */
	public List<INode> getNodesToDelete() {
		List<INode> toDelete = new ArrayList<INode>();
		toDelete.addAll(getL().getPlaces());
		toDelete.addAll(getL().getTransitions());
		Iterator<INode> iterator = toDelete.iterator();
		while (iterator.hasNext()) {
			INode toDeleteNode = (INode) iterator.next();
			if (fromKtoR(fromLtoK(toDeleteNode)) == null) {
				// if L->K->R == null, then stay in toDelete
			} else {
				iterator.remove();
			}
		}
		return toDelete;
	}
}
