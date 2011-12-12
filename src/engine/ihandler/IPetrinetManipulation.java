package engine.ihandler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import exceptions.EngineException;

public interface IPetrinetManipulation {

	/**
	 * Creates an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean createArc(int id, INode from, INode to) throws EngineException;
	
	/**
	 * Creates a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param coordinate Point where the Place will be created
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean createPlace(int id, Point2D coordinate) throws EngineException;
	
	/**
	 * Creates a Petrinet
	 * 
	 * @return ID of the created Petrinet
	 */
	public int createPetrinet();
	
	/**
	 * Creates a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param coordinate Point where the Transition will be created
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean createTransition(int id, Point2D coordinate) throws EngineException;
	
	/**
	 * Deletes an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which will be deleted
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean deleteArc(int id, Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * Deletes a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param place which will be deleted
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean deletePlace(int id, INode place);
	
	/**
	 * Deletes a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param transition which will be deleted
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean deleteTransition(int id, INode transition);
	
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
	 */
	public PlaceAttribute getPlaceAttribute(int id, INode place);
	
	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id ID of the Petrinet
	 * @param transition which attributes are wanted
	 * @return TransitionAttribute
	 */
	public TransitionAttribute getTransitionAttribute(int id, INode transition);
	
	/**
	 * Moves a Graph.
	 * 
	 * @param id ID of the Petrinet
	 * @param relativePosition relative movement of the Graph
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean moveGraph(int id, Point2D relativePosition) throws EngineException;
	
	/**
	 * Moves a node.
	 * 
	 * @param id ID of the Petrinet
	 * @param node to move
	 * @param relativePosition relative movement of the node
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean moveNode(int id, INode node, Point2D relativePosition) throws EngineException;
	
	/**
	 * Saves a Petrinet.
	 * 
	 * @param id ID of the Petrinet
	 * @param path where to save the Petrinet
	 * @param filename name for the Petrinet
	 * @param format which the Petrinet should be saved. (PNML the only option till now)
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean save(int id, String path, String filename, String format); // TODO: String format zu => Format format
	
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean setMarking(int id, INode place, int marking) throws EngineException;
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the PName
	 * @param pname PName
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean setPname(int id, INode place, String pname) throws EngineException;
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean setTlb(int id, INode transition, String tlb) throws EngineException;
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the TName
	 * @param tname TName
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean setTname(int id, INode transition, String tname) throws EngineException;
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Petrinet
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 * @throws EngineException 
	 */
	public boolean setWeight(int id, Arc arc, int weight) throws EngineException;
	
	/**
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 */
	public Enum getNodeType(INode node);
}
