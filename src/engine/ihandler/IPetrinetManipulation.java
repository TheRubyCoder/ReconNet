package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Renews;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import exceptions.EngineException;

/**
 * 
 * This is GUI-Interface for all actions with Petrinet-functions.
 * 
 * Implementation: engine.handler.petrinet.PetrinetManipulation
 * 
 * Some functions are:
 *  - create[Petrinet|Arc|Place|Transition](..)
 * 	- delete[Arc|Place|Transition](..)
 *  - get[Arc|Place|Transition]Attribute(..)
 *  - getJungLayout(..)
 *  - move[Graph|Node](..)
 *  - save(..)
 *  - set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)
 * 
 * @author alex (aas772)
 *
 */

public interface IPetrinetManipulation {

	/**
	 * Creates an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 * @throws EngineException 
	 */
	public void createArc(int id, INode from, INode to) throws EngineException;
	
	/**
	 * 
	 * Creates a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param coordinate Point where the Place will be created
	 * @return 
	 * @throws EngineException 
	 * 
	 */
	public void createPlace(int id, Point2D coordinate) throws EngineException;
	
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
	public void createTransition(int id, Point2D coordinate) throws EngineException;
	
	/**
	 * Deletes an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which will be deleted
	 * @throws EngineException 
	 */
	public void deleteArc(int id, Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * Deletes a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param place which will be deleted
	 */
	public void deletePlace(int id, INode place) throws EngineException;
	
	/**
	 * Deletes a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param transition which will be deleted
	 */
	public void deleteTransition(int id, INode transition) throws EngineException;
	
	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(int id, Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
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
	 * Moves a Graph.
	 * 
	 * @param id ID of the Petrinet
	 * @param relativePosition relative movement of the Graph
	 * @throws EngineException 
	 */
	public void moveGraph(int id, Point2D relativePosition) throws EngineException;
	
	/**
	 * Moves a node.
	 * 
	 * @param id ID of the Petrinet
	 * @param node to move
	 * @param relativePosition relative movement of the node
	 * @throws EngineException 
	 */
	public void moveNode(int id, INode node, Point2D relativePosition) throws EngineException;
	
	/**
	 * Saves a Petrinet.
	 * 
	 * @param id ID of the Petrinet
	 * @param path where to save the Petrinet
	 * @param filename name for the Petrinet
	 * @param format which the Petrinet should be saved. (PNML the only option till now)
	 */
	public void save(int id, String path, String filename, String format) throws EngineException; // TODO: String format zu => Format format
	
	/**
	 * 
	 * Load a Petrinet.
	 * 
	 * @param path where is this Petrinet
	 * @param filename name for the Petrinet
	 * @return the id of the Petrinet
	 * 
	 */
	public int load(String path, String filename);
	
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
	 * 
	 * This methods clean the Tool from this Petrinet.
	 * 
	 * @param id ID of the Petrinet
	 * @throws EngineException
	 * 
	 */
	public void closePetrinet(int id) throws EngineException;
	
	/**
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 * @throws EngineException 
	 */
	public NodeTypeEnum getNodeType(INode node) throws EngineException;
	
}
