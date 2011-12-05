package engine.jungmodification;

import java.awt.geom.Point2D;
import java.util.Collection;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;
import engine.data.JungData;

public class JungModification {

	/**
	 * 
	 * @param jung
	 * @param fromPlace
	 * @param toTransition
	 */
	public void createArc(JungData jung, Place fromPlace, Transition toTransition){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param toTransition
	 * @param fromPlace
	 */
	public void createArc(JungData jung, Transition toTransition, Place fromPlace){
		// TODO: JungData		
	}
	
	/**
	 * 
	 * @param jung
	 * @param place
	 * @param coordinate
	 */
	public void createPlace(JungData jung, Place place, Point2D coordinate){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param transition
	 * @param coordinate
	 */
	public void createTransition(JungData jung, Transition transition, Point2D coordinate){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param arcs
	 * @param nodes
	 */
	public void delete(JungData jung, Collection<Arc> arcs, Collection<INode> nodes){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param node
	 * @param coordinate
	 */
	public void moveNode(JungData jung, INode node, Point2D coordinate){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param arc
	 */
	public void updateArc(JungData jung, Arc arc){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param place
	 */
	public void updatePlace(JungData jung, Place place){
		// TODO: JungData
	}
	
	/**
	 * 
	 * @param jung
	 * @param transition
	 */
	public void updateTransition(JungData jung, Transition transition){
		// TODO: JungData
	}
	
}
