package engine.handler.rule;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import engine.handler.RuleNet;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;

/**
 * This class implements {@link IRulePersistence}. Its the interface for the gui
 * component
 * 
 * It can be used for saving and loading rules.
 * 
 * Its functionalities are delegated to ruleManipulationBackend
 * 
 */
public class RulePersistence implements IRulePersistence {

	/** Singleton instance */
	private static RulePersistence rulePersistence;
	
	/** Object with actual logic to delegate to */
	private RuleHandler ruleManipulationBackend;

	private RulePersistence() {
		this.ruleManipulationBackend = RuleHandler.getInstance();
	}

	/** Returns the singleton instance */
	public static RulePersistence getInstance() {
		if (rulePersistence == null) {
			rulePersistence = new RulePersistence();
		}

		return rulePersistence;
	}

	@Override
	public PreArc createPreArc(int id, RuleNet net, Place place, Transition transition)
			throws EngineException {

		return ruleManipulationBackend.createPreArc(id, net, place, transition);
	}

	@Override
	public PostArc createPostArc(int id, RuleNet net, Transition transition, Place place)
			throws EngineException {

		return ruleManipulationBackend.createPostArc(id, net, transition, place);
	}

	@Override
	public Place createPlace(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		return ruleManipulationBackend.createPlace(id, net, coordinate);
	}

	@Override
	public int createRule() {
		return ruleManipulationBackend.createRule();
	}

	@Override
	public Transition createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		return ruleManipulationBackend.createTransition(id, net, coordinate);
	}

	@Override
	public void setMarking(int id, Place place, int marking)
			throws EngineException {

		ruleManipulationBackend.setMarking(id, place, marking);
	}

	@Override
	public void setPname(int id, Place place, String pname)
			throws EngineException {

		ruleManipulationBackend.setPname(id, place, pname);
	}

	@Override
	public void setTlb(int id, Transition transition, String tlb)
			throws EngineException {

		ruleManipulationBackend.setTlb(id, transition, tlb);
	}

	@Override
	public void setTname(int id, Transition transition, String tname)
			throws EngineException {

		ruleManipulationBackend.setTname(id, transition, tname);
	}

	@Override
	public void setWeight(int id, PreArc preArc, int weight) throws EngineException {
		ruleManipulationBackend.setWeight(id, preArc, weight);
	}

	@Override
	public void setWeight(int id, PostArc postArc, int weight) throws EngineException {
		ruleManipulationBackend.setWeight(id, postArc, weight);
	}

	@Override
	public void setRnw(int id, Transition transition, IRenew renews)
			throws EngineException {

		ruleManipulationBackend.setRnw(id, transition, renews);
	}

	@Override
	public void setPlaceColor(int id, Place place, Color color)
			throws EngineException {

		ruleManipulationBackend.setPlaceColor(id, place, color);
	}

	@Override
	public void setNodeSize(int id, double nodeSize) throws EngineException {
		ruleManipulationBackend.setNodeSize(id, nodeSize);
	}
}
