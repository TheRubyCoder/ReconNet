package engine.ihandler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;

public interface IPetrinetManipulation {

	/**
	 * Creates an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean createArc(int id, INode from, INode to);
	
	/**
	 * Creates a Place
	 * 
	 * @param id ID of the Petrinet
	 * @param coordinate Point where the Place will be created
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean createPlace(int id, Point2D coordinate);
	
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
	 */
	public boolean createTransition(int id, Point2D coordinate);
	
	/**
	 * Deletes an Arc
	 * 
	 * @param id ID of the Petrinet
	 * @param arc which will be deleted
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean deleteArc(int id, Arc arc); // TODO IArc gibt es nicht?
	
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
	 */
	
	// TODO
	public AbstractLayout<INode, Arc> getJungLayout(int id); // TODO: AbstractLayout<INode, Arc> richtig?
	
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
	 */
	public boolean moveGraph(int id, Point2D relativePosition);
	
	/**
	 * Moves a node.
	 * 
	 * @param id ID of the Petrinet
	 * @param node to move
	 * @param relativePosition relative movement of the node
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean moveNode(int id, INode node, Point2D relativePosition);
	
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
	 */
	public boolean setMarking(int id, INode place, int marking);
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Petrinet
	 * @param place where to set the PName
	 * @param pname PName
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean setPname(int id, INode place, String pname);
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean setTlb(int id, INode transition, String tlb);
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Petrinet
	 * @param transition where to set the TName
	 * @param tname TName
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean setTname(int id, INode transition, String tname);
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Petrinet
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 * @return <code> true </code> if something changed, so the GUI has to refresh <code> false </code> otherwise
	 */
	public boolean setWeight(int id, Arc arc, int weight);
	
	/**
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 */
	public Enum getNodeType(INode node);
}
