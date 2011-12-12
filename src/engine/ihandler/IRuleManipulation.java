package engine.ihandler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.RuleNet;

public interface IRuleManipulation {

	/**
	 * Creates an Arc
	 * 
	 * @param id ID of the Rule
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 */
	public void createArc(int id, INode from, INode to);
	
	/**
	 * Creates a Place
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Place will be created
	 */
	public void createPlace(int id,RuleNet net, Point2D coordinate);
	
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
	 */
	public void createTransition(int id,RuleNet net, Point2D coordinate);
	
	/**
	 * Deletes an Arc
	 * 
	 * @param id ID of the Rule
	 * @param arc which will be deleted
	 */
	public void deleteArc(int id,RuleNet net, Arc arc); // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * Deletes a Place
	 * 
	 * @param id ID of the Rule
	 * @param place which will be deleted
	 */
	public void deletePlace(int id,RuleNet net, INode place);
	
	/**
	 * Deletes a Transition
	 * 
	 * @param id ID of the Rule
	 * @param transition which will be deleted
	 */
	public void deleteTransition(int id,RuleNet net, INode transition);
	
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
	public PlaceAttribute getPlaceAttribute(int id, INode place);
	
	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id ID of the Rule
	 * @param transition which attributes are wanted
	 * @return TransitionAttribute
	 */
	public TransitionAttribute getTransitionAttribute(int id, INode transition);
	
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
	 */
	public void moveNode(int id, INode node, Point2D relativePosition);
	
	/**
	 * Saves a Rule.
	 * 
	 * @param id ID of the Rule
	 * @param path where to save the Rule
	 * @param filename name for the Rule
	 * @param format which the Rule should be saved. (PNML the only option till now)
	 */
	public void save(int id, String path, String filename, String format); // TODO: String format zu => Format format
	
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 */
	public void setMarking(int id, INode place, int marking);
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the PName
	 * @param pname PName
	 */
	public void setPname(int id, INode place, String pname);
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 */
	public void setTlb(int id, INode transition, String tlb);
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the TName
	 * @param tname TName
	 */
	public void setTname(int id, INode transition, String tname);
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Rule
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 */
	public void setWeight(int id, Arc arc, int weight);
	
}
