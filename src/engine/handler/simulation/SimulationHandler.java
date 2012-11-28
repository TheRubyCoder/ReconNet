package engine.handler.simulation;

import static exceptions.Exceptions.exception;
import static exceptions.Exceptions.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.ITransformation;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.ihandler.ISimulation;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.ShowAsInfoException;

public class SimulationHandler implements ISimulation {

	/** Session manager holding session data */
	private final SessionManager sessionManager;

	/** The transformation component (singleton instance) */
	private final ITransformation transformationComponent;

	/** Singleton instance of this class */
	private static SimulationHandler simulation;

	/**
	 * Random number generator to choose a random rule to be applied or to
	 * choose whether to fire or to transform
	 */
	private Random random = new Random();

	/** The distance for nodes that are added through application of a rule */
	public static final int DISTANCE_WHEN_ADDED = 100;

	private SimulationHandler() {
		sessionManager = SessionManager.getInstance();
		transformationComponent = TransformationComponent.getTransformation();
	}

	/** Returns the singleton instance */
	public static SimulationHandler getInstance() {
		if (simulation == null)
			simulation = new SimulationHandler();

		return simulation;
	}

	@Override
	public int createSimulationSession(int id) {
		return 0;
	}

	@Override
	public void fire(int id, int n) throws EngineException {

		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("SimulationHandler - id of the Petrinet is wrong");
			return;
		} 
		
		Petrinet petrinet = petrinetData.getPetrinet();
		for (int i = 0; i < n; i++) {

			// if there are no more activated transitions the
			// petrinet.fire()method will throw a IllegalState Exception
			// this method catches the IllegalState Exception and throws a
			// EngineException so the GUI only will get EngineExceptions
			try {
				petrinet.fire();
			} catch (IllegalStateException e) {
				exception(e.getMessage());
			}
		}
	}

	@Override
	public void save(int id, String path, String filename, String format)
			throws EngineException {
		throw new UnsupportedOperationException(
				"Simulation data are not implemented");
	}

	@Override
	public void transform(int id, Collection<Integer> ruleIDs, int n)
			throws EngineException {

		if (ruleIDs.isEmpty()) {
			info("Es sind keine Regeln ausgewählt");
		}

		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("SimulationHandler - id of the Petrinet is wrong");
			return;
		} 
	
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();
		List<Rule> sortedRules = new ArrayList<Rule>();
		for (Integer ruleId : ruleIDs) {
			sortedRules.add(sessionManager.getRuleData(ruleId).getRule());
		}
		sortedRules = Collections.unmodifiableList(sortedRules);

		for (int i = 0; i < n; i++) {
			// Make sure a random rule is selected each time
			List<Rule> shuffledRules = new ArrayList<Rule>(sortedRules);
			Collections.shuffle(shuffledRules);
			// Find matching rule and apply it
			Transformation transformation = findMatchingRule(shuffledRules,
					petrinet);
			// Remove deleted elements from display
			jungData.deleteDataOfMissingElements(petrinet);
			// Add new elements to display
			fillJungDataWithNewElements(jungData,
					transformation.getAddedNodes(),
					transformation.getAddedArcs());
		}
	}

	/**
	 * After a rule has been applied on a petrinet, the added nodes are not part
	 * of the display (<code>jungData</code>). They must be added.
	 * 
	 * @param jungData
	 * @param addedNodes
	 * @param addedArcs
	 */
	private void fillJungDataWithNewElements(JungData jungData,
			Set<INode> addedNodes, Set<IArc> addedArcs) {
		//Add nodes at "random" position
		for (INode node : addedNodes) {
			jungData.createPlaceOrTransition(node);
		}
		
		//Add arcs
		for (IArc arc : addedArcs) {
			//Find out which method to call
			if (arc.getSource() instanceof Place) {
				jungData.createArc(arc, (Place)arc.getSource(), (Transition)arc.getTarget());
			}else{
				jungData.createArc(arc, (Transition)arc.getSource(), (Place)arc.getTarget());
			}
		}

	}

	/**
	 * Finds a rule out of <code>shuffledRules</code> that can be applied on
	 * <code>petrinet</code>
	 * 
	 * @param shuffledRules
	 * @param petrinet
	 * @return Transformation that was used for altering the petrinet
	 * @throws ShowAsInfoException
	 *             if none of the rules matches with the petrinet (user friendly
	 *             message)
	 */
	private Transformation findMatchingRule(List<Rule> shuffledRules,
			Petrinet petrinet) {
		Iterator<Rule> ruleIterator = shuffledRules.iterator();
		while (ruleIterator.hasNext()) {
			Rule rule = (Rule) ruleIterator.next();
			Transformation transformation = transformationComponent.transform(
					petrinet, rule);
			if (transformation == null) {
				// go on with iteration
			} else {
				return transformation;
			}
		}
		info("Keine der Regeln passt auf das Petrinetz");
		return null;
	}

	@Override
	public void fireOrTransform(int id, Collection<Integer> ruleIDs, int n)
			throws EngineException {
		for (int i = 0; i < n; i++) {
			//try fire first? 50 : 50 chance
			if (random.nextFloat() < 0.5d) {
				try {
					fire(id, 1);
				} catch (EngineException ex) {
					//fire failed? OK, try transform
					transform(id, ruleIDs, 1);
				}
			} else {
				//try transform first? 50 : 50 chance
				try {
					transform(id, ruleIDs, 1);
				} catch (EngineException ex) {
					// transform failed? OK, try fire
					fire(id, 1);
				}
			}
		}
	}

}
