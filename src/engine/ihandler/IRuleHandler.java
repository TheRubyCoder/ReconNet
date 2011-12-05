package engine.ihandler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.RuleNet;

public interface IRuleHandler {

	/**
	 * Creates an Arc
	 * 
	 * @param id ID of the Rule
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 * @return <code> true </code> if creation was successful, <code> false </code> otherwise
	 */
	public boolean createArc(int id, INode from, INode to);
	
	/**
	 * Creates a Place
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Place will be created
	 * @return <code> true </code> if creation was successful, <code> false </code> otherwise
	 */
	public boolean createPlace(int id,RuleNet net, Point2D coordinate);
	
	/**
	 * Creates a Rule
	 * 
	 * @return ID of the created Rule
	 */
	public int createRule();
	
	/**
	 * Creates a Transition
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Transition will be created
	 * @return <code> true </code> if creation was successful, <code> false </code> otherwise
	 */
	public boolean createTransition(int id,RuleNet net, Point2D coordinate);
	
	/**
	 * Deletes an Arc
	 * 
	 * @param id ID of the Rule
	 * @param arc which will be deleted
	 * @return <code> true </code> if deleting was successful, <code> false </code> otherwise
	 */
	public boolean deleteArc(int id,RuleNet net, Arc arc); // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * Deletes a Place
	 * 
	 * @param id ID of the Rule
	 * @param place which will be deleted
	 * @return <code> true </code> if deleting was successful, <code> false </code> otherwise
	 */
	public boolean deletePlace(int id,RuleNet net, Place place);
	
	/**
	 * Deletes a Transition
	 * 
	 * @param id ID of the Rule
	 * @param transition which will be deleted
	 * @return <code> true </code> if deleting was successful, <code> false </code> otherwise
	 */
	public boolean deleteTransition(int id,RuleNet net, Transition transition);
	
	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id ID of the Rule
	 * @param arc which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(int id, Arc arc); // TODO IArc gibt es nicht?
	
	/**
	 * Gets the JungLayout from the Rule
	 * 
	 * @param id ID of the Rule
	 * @return AbstractLayout
	 */
	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net); // TODO: AbstractLayout<INode, Arc> richtig?
	
	/**
	 * Gets the Attributes from a Place
	 * 
	 * @param id ID of the Rule
	 * @param place which attributes are wanted
	 * @return PlaceAtrribute
	 */
	public PlaceAttribute getPlaceAttribute(int id, Place place);
	
	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id ID of the Rule
	 * @param transition which attributes are wanted
	 * @return TransitionAttribute
	 */
	public TransitionAttribute getTransitionAttribute(int id, Transition transition);
	
	/**
	 * Gets the Attributes from a Rule
	 * 
	 * @param id ID of the Rule
	 * @return RuleAttribute
	 */
	public RuleAttribute getRuleAttribute(int id);
	
	/**
	 * Moves a node.
	 * 
	 * @param id ID of the Rule
	 * @param node to move
	 * @param relativePosition relative movement of the node
	 * @return <code> true </code> if moving was successful, <code> false </code> otherwise
	 */
	public boolean moveNode(int id, INode node, Point2D relativePosition);
	
	/**
	 * Saves a Rule.
	 * 
	 * @param id ID of the Rule
	 * @param path where to save the Rule
	 * @param filename name for the Rule
	 * @param format which the Rule should be saved. (PNML the only option till now)
	 * @return <code> true </code> if saving was successful, <code> false </code> otherwise
	 */
	public boolean save(int id, String path, String filename, String format); // TODO: String format zu => Format format
	
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @return <code> true </code> if setting was successful, <code> false </code> otherwise
	 */
	public boolean setMarking(int id, Place place, int marking);
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the PName
	 * @param pname PName
	 * @return <code> true </code> if setting was successful, <code> false </code> otherwise
	 */
	public boolean setPname(int id, Place place, String pname);
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @return <code> true </code> if setting was successful, <code> false </code> otherwise
	 */
	public boolean setTlb(int id, Transition transition, String tlb);
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the TName
	 * @param tname TName
	 * @return <code> true </code> if setting was successful, <code> false </code> otherwise
	 */
	public boolean setTname(int id, Transition transition, String tname);
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Rule
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 * @return <code> true </code> if setting was successful, <code> false </code> otherwise
	 */
	public boolean setWeight(int id, Arc arc, int weight);
	
}
