package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.Transition;
import petrinet.model.PreArc;
import petrinet.model.PostArc;
import engine.data.JungData;
import exceptions.EngineException;

/**
 * 
 * This is a Interface for Persistence Component.
 * 
 * Implementation: engine.handler.petrinet.PetrinetPersistence
 * 
 * @author alex (aas772)
 *
 */

public interface IPetrinetPersistence {
	/**
	 * Creates an PreArc
	 * 
	 * @param  id   		ID of the Petrinet
	 * @param  place        Place of the Arc
	 * @param  transition   Transition of the Arc
	 * @throws EngineException 
	 */
	public PreArc createPreArc(int id, Place place, Transition transition) throws EngineException;

	/**
	 * Creates an PostArc
	 * 
	 * @param  id   		ID of the Petrinet
	 * @param  transition 	Transition of the Arc
	 * @param  place   		Place of the Arc
	 * @throws EngineException 
	 */
	public PostArc createPostArc(int id, Transition transition, Place place) throws EngineException;
	
	/**
	 * 
	 * Creates a Place
	 * 
	 * @param  id ID of the Petrinet
	 * @param  coordinate Point where the Place will be created
	 * @throws EngineException 
	 * 
	 */
	public Place createPlace(int id, Point2D coordinate) throws EngineException;
	
	/**
	 * 
	 * Creates a Petrinet
	 * 
	 * @return ID of the created Petrinet
	 * 
	 */
	public int createPetrinet();
	
	/**
	 * Creates a Transition
	 * 
	 * @param  id ID of the Petrinet
	 * @param  coordinate Point where the Transition will be created
	 * @return 
	 * @throws EngineException 
	 */
	public Transition createTransition(int id, Point2D coordinate) throws EngineException;
				
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @throws EngineException 
	 */
	public void setMarking(int id, Place place, int marking) throws EngineException;
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the PName
	 * @param pname PName
	 * @throws EngineException 
	 */
	public void setPname(int id, Place place, String pname) throws EngineException;
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @throws EngineException 
	 */
	public void setTlb(int id, Transition transition, String tlb) throws EngineException;
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the TName
	 * @param tname TName
	 * @throws EngineException 
	 */
	public void setTname(int id, Transition transition, String tname) throws EngineException;
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param  id ID of the Petrinet
	 * @param  preArc where to set the weight
	 * @param  weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(int id, PreArc preArc, int weight) throws EngineException;
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param  id ID of the Petrinet
	 * @param  postArc where to set the weight
	 * @param  weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(int id, PostArc postArc, int weight) throws EngineException;
	
	/**
	 * 
	 * Sets a Strings as RNW.
	 * 
	 * @param id ID of the Petrinet
	 * @param rnw String as RNW
	 * @throws EngineException 
	 * 
	 */
	public void setRnw(int id, Transition transition, IRenew renews) throws EngineException;
	
	/**
	 * 
	 * Set Color of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place which should modify
	 * @param color new Color
	 * @throws EngineException 
	 * 
	 */
	public void setPlaceColor(int id, Place place, Color color) throws EngineException;
		
	/**
	 * Sets the nodeSize for the JungData of petrinet with <code>id</code>
	 * 
	 * @see {@link JungData#setNodeSize(double)}
	 * @param id
	 * @param nodeSize
	 */
	public void setNodeSize(int id, double nodeSize);	
}
