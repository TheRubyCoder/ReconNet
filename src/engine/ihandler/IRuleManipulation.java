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
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import exceptions.EngineException;

/**
 * 
 * This is a Interface for a RuleManipulation from the GUI-Component.
 * 
 * Implementation: engine.handler.rule.RuleManipulation
 * 
 * @author alex (aas772)
 *
 */

public interface IRuleManipulation {

	/**
	 * 
	 * Creates an Arc
	 * 
	 * @param id ID of the Rule
	 * @param from Source of the Arc
	 * @param to Target of the Arc
	 * @return the new Arc
	 * 
	 */
	public void createArc(@NotNull int id,RuleNet net, @NotNull INode from, @NotNull INode to) throws EngineException;
	
	/**
	 * 
	 * Creates a Place
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Place will be created
	 * @return the new Place
	 * 
	 */
	public void createPlace(@NotNull int id, @NotNull RuleNet net, @NotNull Point2D coordinate) throws EngineException;
	
	/**
	 * 
	 * Creates a Rule
	 * 
	 * @return ID of the created Rule
	 * 
	 */
	public int createRule();
	
	/**
	 * 
	 * Creates a Transition
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Transition will be created
	 * @throws EngineException
	 * @return the new Transition 
	 * 
	 */
	public void createTransition(@NotNull int id, @NotNull RuleNet net, @NotNull Point2D coordinate) throws EngineException;
	
	/**
	 * 
	 * Deletes an Arc
	 * 
	 * @param id ID of the Rule
	 * @param arc which will be deleted
	 * 
	 */
	public void deleteArc(@NotNull int id, @NotNull RuleNet net, @NotNull Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * Deletes a Place
	 * 
	 * @param id ID of the Rule
	 * @param place which will be deleted
	 */
	public void deletePlace(@NotNull int id, @NotNull RuleNet net, @NotNull INode place) throws EngineException;
	
	/**
	 * Deletes a Transition
	 * 
	 * @param id ID of the Rule
	 * @param transition which will be deleted
	 */
	public void deleteTransition(@NotNull int id, @NotNull RuleNet net, @NotNull INode transition) throws EngineException;
	
	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id ID of the Rule
	 * @param arc which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(@NotNull int id, @NotNull Arc arc) throws EngineException; // TODO IArc gibt es nicht?
	
	/**
	 * Gets the JungLayout from the Rule
	 * 
	 * @param id ID of the Rule
	 * @return AbstractLayout
	 */
	public AbstractLayout<INode, Arc> getJungLayout(@NotNull int id, @NotNull RuleNet net) throws EngineException; // TODO: AbstractLayout<INode, Arc> richtig?
	
	/**
	 * Gets the Attributes from a Place
	 * 
	 * @param id ID of the Rule
	 * @param place which attributes are wanted
	 * @return PlaceAtrribute
	 * @throws EngineException 
	 */
	public PlaceAttribute getPlaceAttribute(@NotNull int id, @NotNull INode place) throws EngineException;
	
	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id ID of the Rule
	 * @param transition which attributes are wanted
	 * @return TransitionAttribute 
	 * @throws EngineException 
	 */
	public TransitionAttribute getTransitionAttribute(@NotNull int id, @NotNull INode transition) throws EngineException;
	
	/**
	 * Gets the Attributes from a Rule
	 * 
	 * @param id ID of the Rule
	 * @return RuleAttribute or it throws a EngineException
	 * @throws EngineException 
	 */
	public RuleAttribute getRuleAttribute(@NotNull int id) throws EngineException;
	
	/**
	 * Moves a node.
	 * 
	 * @param id ID of the Rule
	 * @param node to move
	 * @param relativePosition relative movement of the node
	 * @throws EngineException 
	 */
	public void moveNode(@NotNull int id, @NotNull INode node, @NotNull Point2D relativePosition) throws EngineException;
	
	/**
	 * Saves a Rule.
	 * 
	 * @param id ID of the Rule
	 * @param path where to save the Rule
	 * @param filename name for the Rule
	 * @param format which the Rule should be saved. (PNML the only option till now)
	 */
	public void save(@NotNull int id, @NotNull String path, @NotNull String filename, @NotNull String format) throws EngineException; // TODO: String format zu => Format format
	
	/**
	 * 
	 * Load a Rule.
	 * 
	 * @param path where can we find the Rule
	 * @param filename name of the Rule
	 * @return the Id of the Rule
	 * 
	 */
	public int load(@NotNull String path, @NotNull String filename); 
	
	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @throws EngineException 
	 */
	public void setMarking(@NotNull int id, @NotNull INode place, @NotNull int marking) throws EngineException;
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the PName
	 * @param pname PName
	 * @throws EngineException 
	 */
	public void setPname(@NotNull int id, @NotNull INode place, @NotNull String pname) throws EngineException;
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @throws EngineException 
	 */
	public void setTlb(@NotNull int id, @NotNull INode transition, @NotNull String tlb) throws EngineException;
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the TName
	 * @param tname TName
	 * @throws EngineException 
	 */
	public void setTname(@NotNull int id, @NotNull INode transition, @NotNull String tname) throws EngineException;
	
	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id ID of the Rule
	 * @param arc where to set the weight
	 * @param weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(@NotNull int id, @NotNull Arc arc, @NotNull int weight) throws EngineException;

	/**
	 * 
	 * Sets a Strings as RNW.
	 * 
	 * @param id ID of the Rule
	 * @param rnw String as RNW
	 * 
	 */
	public void setRnw(int id, INode transition, Renews renews) throws EngineException;

	/**
	 * 
	 * Set Color of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place which should modify
	 * @param color new Color
	 * 
	 */
	public void setPlaceColor(int id, INode place, Color color) throws EngineException;
	
	/**
	 * 
	 * This methods clean the Tool from this Rule.
	 * 
	 * @param id ID of the Rule
	 * @throws EngineException
	 * 
	 */
	public void closeRule(int id) throws EngineException;
	
	/**
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 */
	public NodeTypeEnum getNodeType(@NotNull INode node);
	
}
