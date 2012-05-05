package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Renews;

import com.sun.istack.NotNull;

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
	public void createArc(@NotNull int id,@NotNull INode from,@NotNull INode to) throws EngineException;
	
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
	public void createPlace(@NotNull int id,@NotNull Point2D coordinate) throws EngineException;
	
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
	public void createTransition(@NotNull int id, @NotNull Point2D coordinate) throws EngineException;
	
	/**
	 * Deletes an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which will be deleted
	 * @throws EngineException 
	 */
	public void deleteArc(@NotNull int id, @NotNull Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * Deletes a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param place which will be deleted
	 */
	public void deletePlace(@NotNull int id, @NotNull INode place) throws EngineException;
	
	/**
	 * Deletes a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param transition which will be deleted
	 */
	public void deleteTransition(@NotNull int id, @NotNull INode transition) throws EngineException;
	
	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(@NotNull int id, @NotNull Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
	/**
	 * Gets the JungLayout from the Petrinet
	 * 
	 * @param id ID of the Petrinet
	 * @return AbstractLayout
	 * @throws EngineException 
	 */
	
	// TODO
	public AbstractLayout<INode, Arc> getJungLayout(@NotNull int id) throws EngineException; // TODO: AbstractLayout<INode, Arc> richtig?
	
	/**
	 * Gets the Attributes from a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param place which attributes are wanted
	 * @return PlaceAtrribute
	 * @throws EngineException 
	 */
	public PlaceAttribute getPlaceAttribute(@NotNull int id, @NotNull INode place) throws EngineException;
	
	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param transition which attributes are wanted
	 * @return TransitionAttribute
	 * @throws EngineException 
	 */
	public TransitionAttribute getTransitionAttribute(@NotNull int id, @NotNull INode transition) throws EngineException;
	
	/**
	 * Moves a Graph.
	 * 
	 * @param id ID of the Petrinet
	 * @param relativePosition relative movement of the Graph
	 * @throws EngineException 
	 */
	public void moveGraph(@NotNull int id, @NotNull Point2D relativePosition) throws EngineException;
	
	/**
	 * Moves a node.
	 * 
	 * @param id ID of the Petrinet
	 * @param node to move
	 * @param relativePosition relative movement of the node
	 * @throws EngineException 
	 */
	public void moveNode(@NotNull int id, @NotNull INode node, @NotNull Point2D relativePosition) throws EngineException;
	
	/**
	 * Saves a Petrinet.
	 * 
	 * @param id ID of the Petrinet
	 * @param path where to save the Petrinet
	 * @param filename name for the Petrinet
	 * @param format which the Petrinet should be saved. (PNML the only option till now)
	 */
	public void save(@NotNull int id, @NotNull String path, @NotNull String filename, @NotNull String format) throws EngineException; // TODO: String format zu => Format format
	
	/**
	 * 
	 * Load a Petrinet.
	 * 
	 * @param path where is this Petrinet
	 * @param filename name for the Petrinet
	 * @return the id of the Petrinet
	 * 
	 */
	public int load(@NotNull String path, @NotNull String filename);
	
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @throws EngineException 
	 */
	public void setMarking(@NotNull int id, @NotNull INode place, @NotNull int marking) throws EngineException;
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the PName
	 * @param pname PName
	 * @throws EngineException 
	 */
	public void setPname(@NotNull int id, @NotNull INode place, @NotNull String pname) throws EngineException;
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @throws EngineException 
	 */
	public void setTlb(@NotNull int id, @NotNull INode transition, @NotNull String tlb) throws EngineException;
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the TName
	 * @param tname TName
	 * @throws EngineException 
	 */
	public void setTname(@NotNull int id, @NotNull INode transition, @NotNull String tname) throws EngineException;
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Petrinet
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(@NotNull int id, @NotNull Arc arc, @NotNull int weight) throws EngineException;
	
	/**
	 * 
	 * Sets a Strings as RNW.
	 * 
	 * @param id ID of the Petrinet
	 * @param rnw String as RNW
	 * @throws EngineException 
	 * 
	 */
	public void setRnw(@NotNull int id, @NotNull INode transition, @NotNull Renews renews) throws EngineException;
	
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
	public void setPlaceColor(@NotNull int id, @NotNull INode place, @NotNull Color color) throws EngineException;
	
	/**
	 * 
	 * This methods clean the Tool from this Petrinet.
	 * 
	 * @param id ID of the Petrinet
	 * @throws EngineException
	 * 
	 */
	public void closePetrinet(@NotNull int id) throws EngineException;
	
	/**
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 * @throws EngineException 
	 */
	public NodeTypeEnum getNodeType(@NotNull INode node) throws EngineException;
	
	/**
	 * Prints the petrinet with <tt>id</tt> in the console
	 *  */
	public void printPetrinet(@NotNull int id);
	
}
