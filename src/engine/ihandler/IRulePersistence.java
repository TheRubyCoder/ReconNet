package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

import com.sun.istack.NotNull;

import engine.data.JungData;
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
	 * Creates an PreArc
	 * 
	 * @param id ID of the Rule
	 * @param place Place of the Arc
	 * @param transition Transition of the Arc
	 * @return the new PreArc
	 * 
	 */
	public PreArc createPreArc(@NotNull int id, RuleNet net, @NotNull Place place, @NotNull Transition transition) throws EngineException;

	/**
	 * Creates an PostArc
	 * 
	 * @param id ID of the Rule
	 * @param transition Transition of the Arc
	 * @param  place Place of the Arc
	 * @return the new PostArc
	 * 
	 */
	public PostArc createPostArc(@NotNull int id, RuleNet net, @NotNull Transition transition, @NotNull Place place) throws EngineException;
	
	/**
	 * 
	 * Creates a Place
	 * 
	 * @param id ID of the Rule
	 * @param coordinate Point where the Place will be created
	 * @return the new Place
	 * 
	 */
	public Place createPlace(@NotNull int id, @NotNull RuleNet net, @NotNull Point2D coordinate) throws EngineException ;
	
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
	public Transition createTransition(@NotNull int id, @NotNull RuleNet net, @NotNull Point2D coordinate) throws EngineException;

	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the Mark
	 * @param marking amount of mark 
	 * @throws EngineException 
	 */
	public void setMarking(@NotNull int id, @NotNull Place place, @NotNull int marking) throws EngineException;
	
	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place where to set the PName
	 * @param pname PName
	 * @throws EngineException 
	 */
	public void setPname(@NotNull int id, @NotNull Place place, @NotNull String pname) throws EngineException;
	
	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the tlb
	 * @param tlb TransitionLabel
	 * @throws EngineException 
	 */
	public void setTlb(@NotNull int id, @NotNull Transition transition, @NotNull String tlb) throws EngineException;
	
	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id ID of the Rule
	 * @param transition where to set the TName
	 * @param tname TName
	 * @throws EngineException 
	 */
	public void setTname(@NotNull int id, @NotNull Transition transition, @NotNull String tname) throws EngineException;
	
	/**
	 * Sets the Weight of a PreArc.
	 * 
	 * @param  id ID of the Rule
	 * @param  preArc where to set the weight
	 * @param  weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(@NotNull int id, @NotNull PreArc preArc, @NotNull int weight) throws EngineException;

	/**
	 * Sets the Weight of a PostArc.
	 * 
	 * @param  id ID of the Rule
	 * @param  postArc where to set the weight
	 * @param  weight weight of the arc
	 * @throws EngineException 
	 */
	public void setWeight(@NotNull int id, @NotNull PostArc postArc, @NotNull int weight) throws EngineException;
	
	/**
	 * 
	 * Sets a Strings as RNW.
	 * 
	 * @param id ID of the Rule
	 * @param transition 
	 * @param rnw String as RNW
	 * 
	 */
	public void setRnw(int id, Transition transition, IRenew renews) throws EngineException;

	/**
	 * 
	 * Set Color of a Place.
	 * 
	 * @param id ID of the Rule
	 * @param place which should modify
	 * @param color new Color
	 * 
	 */
	public void setPlaceColor(int id, Place place, Color color) throws EngineException;
	
	/**
	 * Sets the nodeSize for the JungData of the petrinets of the rule with <code>id</code>
	 * @see {@link JungData#setNodeSize(double)}
	 * @param id
	 * @param nodeSize
	 * @throws EngineException 
	 */
	public void setNodeSize(int id, double nodeSize) throws EngineException;	
}
