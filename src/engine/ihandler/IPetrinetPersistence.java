package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Renews;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
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
	 * Creates an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 * @throws EngineException 
	 */
	public Arc createArc(int id, INode from, INode to) throws EngineException;
	
	/**
	 * 
	 * Creates a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param coordinate Point where the Place will be created
	 * @throws EngineException 
	 * 
	 */
	public INode createPlace(int id, Point2D coordinate) throws EngineException;
	
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
	 * @param id ID of the Petrinet
	 * @param coordinate Point where the Transition will be created
	 * @return 
	 * @throws EngineException 
	 */
	public INode createTransition(int id, Point2D coordinate) throws EngineException;
	
	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(int id, Arc arc); // TODO IArc gibt es nicht?
	
	/**
	 * Gets the JungLayout from the Petrinet
	 * 
	 * @param id ID of the Petrinet
	 * @return AbstractLayout
	 * @throws EngineException 
	 */
	
	// TODO
	public AbstractLayout<INode, Arc> getJungLayout(int id) throws EngineException; // TODO: AbstractLayout<INode, Arc> richtig?
	
	/**
	 * Gets the Attributes from a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param place which attributes are wanted
	 * @return PlaceAtrribute
	 * @throws EngineException 
	 */
	public PlaceAttribute getPlaceAttribute(int id, INode place) throws EngineException;
	
	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param transition which attributes are wanted
	 * @return TransitionAttribute
	 * @throws EngineException 
	 */
	public TransitionAttribute getTransitionAttribute(int id, INode transition) throws EngineException;
	
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @throws EngineException 
	 */
	public void setMarking(int id, INode place, int marking) throws EngineException;
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the PName
	 * @param pname PName
	 * @throws EngineException 
	 */
	public void setPname(int id, INode place, String pname) throws EngineException;
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @throws EngineException 
	 */
	public void setTlb(int id, INode transition, String tlb) throws EngineException;
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the TName
	 * @param tname TName
	 * @throws EngineException 
	 */
	public void setTname(int id, INode transition, String tname) throws EngineException;
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Petrinet
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(int id, Arc arc, int weight) throws EngineException;
	
	/**
	 * 
	 * Sets a Strings as RNW.
	 * 
	 * @param id ID of the Petrinet
	 * @param rnw String as RNW
	 * @throws EngineException 
	 * 
	 */
	public void setRnw(int id, INode transition, Renews renews) throws EngineException;
	
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
	public void setPlaceColor(int id, INode place, Color color) throws EngineException;
	
	/**
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 * @throws EngineException 
	 */
	public Enum getNodeType(INode node) throws EngineException;
	
}
