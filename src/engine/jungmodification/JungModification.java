package engine.jungmodification;

import java.awt.geom.Point2D;
import java.util.Collection;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;
import engine.data.JungData;

public class JungModification {

	private static JungModification jungModification;
	
	private JungModification(){}
	
	public static JungModification getInstance(){
		if(jungModification == null){
			jungModification = new JungModification();
		}
		
		return jungModification;
	}
	
	/**
	 * Creates an Arc in the JungRepresentation of the petrinet from a Place to a Transition.
	 * 
	 * @param jung JungRepresentation where arc will be created
	 * @param fromPlace Source of the arc
	 * @param toTransition Target of the arc
	 */
	public void createArc(JungData jung, Place fromPlace, Transition toTransition){
		// TODO: JungData
	}
	
	/**
	 * Creates an Arc in the JungRepresentation of the petrinet from a Transition to a Place.
	 * 
	 * @param jung JungRepresentation where arc will be created
	 * @param fromPlace Source of the arc
	 * @param toTransition Target of the arc
	 */
	public void createArc(JungData jung, Transition toTransition, Place fromPlace){
		// TODO: JungData		
	}
	
	/**
	 * Creates a Place in the JungRepresentation of the petrinet.
	 * 
	 * @param jung JungRepresentation where place will be created
	 * @param place Place to create
	 * @param coordinate Position of the Place
	 */
	public void createPlace(JungData jung, Place place, Point2D coordinate){
		// TODO: JungData
	}
	
	/**
	 * Creates a Transition in the JungRepresentation of the petrinet.
	 * 
	 * @param jung JungRepresentation where place will be created
	 * @param transition Transition to create
	 * @param coordinate Position of the Transition
	 */
	public void createTransition(JungData jung, Transition transition, Point2D coordinate){
		// TODO: JungData
	}
	
	/**
	 * Deletes Arcs and INodes from the JungRepresentation.
	 * 
	 * @param jung JungRepresentation where Arcs/INodes will be deleted
	 * @param arcs Collection of Arcs to be deleted
	 * @param nodes Collection of INodes to be deleted
	 */
	public void delete(JungData jung, Collection<Arc> arcs, Collection<INode> nodes){
		// TODO: JungData
	}
	
	/**
	 * Moves a INode.
	 * 
	 * @param jung JungRepresentation where INode will be moved
	 * @param node Node to move
	 * @param coordinate new Position of the INode
	 */
	public void moveNode(JungData jung, INode node, Point2D coordinate){
		// TODO: JungData
	}
	
	/**
	 * Updates an Arc.
	 * 
	 * @param jung JungRepresentation where Arc will be updated
	 * @param arc Arc to update
	 */
	public void updateArc(JungData jung, Arc arc){
		// TODO: JungData
	}
	
	/**
	 * Updates a Place.
	 * 
	 * @param jung JungRepresentation where Place will be updated
	 * @param place Place to Update
	 */
	public void updatePlace(JungData jung, Place place){
		// TODO: JungData
	}
	
	/**
	 * Updates a Transition
	 * 
	 * @param jung JungRepresentation where Transition will be updated
	 * @param transition Transition to update
	 */
	public void updateTransition(JungData jung, Transition transition){
		// TODO: JungData
	}
	
}
