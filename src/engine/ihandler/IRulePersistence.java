package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import exceptions.EngineException;

/**
 * 
 * This is a Interface for the Persistence-Component.
 * 
 * Implementation: engine.handler.rule.RulePersistence
 * 
 * @author alex (aas772)
 *
 */

public interface IRulePersistence {

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
	public Arc createArc(@NotNull int id,RuleNet net, @NotNull INode from, @NotNull INode to) throws EngineException ;
	
	/**
	 * 
	 * Creates a Place
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Place will be created
	 * @return the new Place
	 * 
	 */
	public INode createPlace(@NotNull int id, @NotNull RuleNet net, @NotNull Point2D coordinate) throws EngineException ;
	
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
	public INode createTransition(@NotNull int id, @NotNull RuleNet net, @NotNull Point2D coordinate) throws EngineException;
	
	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id ID of the Rule
	 * @param arc which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(@NotNull int id, @NotNull Arc arc) throws EngineException ; // TODO IArc gibt es nicht?
	
	/**
	 * Gets the JungLayout from the Rule
	 * 
	 * @param id ID of the Rule
	 * @return AbstractLayout
	 */
	public AbstractLayout<INode, Arc> getJungLayout(@NotNull int id, @NotNull RuleNet net) throws EngineException ; // TODO: AbstractLayout<INode, Arc> richtig?
	
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
	public void setRnw(int id, INode transition, IRenew renews) throws EngineException;

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
	 * Returns the type of the Object.
	 * @param node to check
	 * @return Enum composed of Place, Transition
	 */
	public NodeTypeEnum getNodeType(@NotNull INode node);
	
	/**
	 * Sets the nodeSize for the JungData of the petrinets of the rule with <code>id</code>
	 * @see {@link JungData#setNodeSize(double)}
	 * @param id
	 * @param nodeSize
	 */
	public void setNodeSize(int id, double nodeSize);
	
}
