package engine.jungmodification;

import java.awt.geom.Point2D;
import java.util.Collection;

import edu.uci.ics.jung.visualization.layout.PersistentLayout.Point;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;

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
	
	public void createArc(JungData jung, Transition toTransition, Place fromPlace){
		// TODO: JungData		
	}
	
	public void createPlace(JungData jung, Place place, Point2D coordinate){
		// TODO: JungData
	}
	
	public void createTransition(JungData jung, Transition transition, Point2D coordinate){
		// TODO: JungData
	}
	
	public void delete(JungData jung, Collection<Arc> arcs, Collection<INode> nodes){
		// TODO: JungData
	}
	
	public void moveNode(JungData jung, INode node, Point2D coordinate){
		// TODO: JungData
	}
	
	public void updateArc(JungData jung, Arc arc){
		// TODO: JungData
	}
	
	public void updatePlace(JungData jung, Place place){
		// TODO: JungData
	}
	
	public void updateTransition(JungData jung, Transition transition){
		// TODO: JungData
	}
	
}
