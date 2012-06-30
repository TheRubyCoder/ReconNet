package engine.handler.simulation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import transformation.ITransformation;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import engine.attribute.NodeLayoutAttribute;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.ihandler.ISimulation;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.ShowAsInfoException;
import exceptions.ShowAsWarningException;

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

		} else {
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

	}

	@Override
	public void save(int id, String path, String filename, String format)
			throws EngineException {
		throw new UnsupportedOperationException("Simulation data are not implemented");
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

		} else {
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			for (int i = 0; i < n; i++) {
				// Everytime one Rule matched, all other Rules will be checked
				// again
				List<Integer> pickedRules = new ArrayList<Integer>();
				pickedRules.addAll(ruleIDs);
				Transformation transformation = null;
				// While the random picked rule is not matching, the temporaly
				// picked one is removed from the List of Rules and another Rule
				// from this List is picked
				while (transformation == null) {
					// Getting a random Rule from pickedRules
					int randomRule = random.nextInt(pickedRules.size());

					RuleData ruleData = sessionManager.getRuleData(pickedRules
							.get(randomRule));
					Rule rule = ruleData.getRule();

					transformation = transformationComponent.transform(
							petrinet, rule);
					// if result of transformation != null the petrinet changed
					// so
					// we have to do the next transform step with the changed
					// petrinet
					if (transformation != null) {
						try {

							petrinet = transformation.getPetrinet();
							Set<INode> addedNodes = transformation
									.getAddedNodes();
							Set<INode> deletedNodes = transformation
									.getDeletedNodes();
							Set<Arc> addedArcs = transformation.getAddedArcs();
							Set<Arc> deletedArcs = transformation
									.getDeletedArcs();

							// step 1 alles durchgehen größtes x suchen
							Map<INode, NodeLayoutAttribute> layoutMap = jungData
									.getNodeLayoutAttributes();

							double biggestX = 0;

							Collection<NodeLayoutAttribute> entrySet = layoutMap
									.values();

							for (NodeLayoutAttribute bla : entrySet) {
								double x = bla.getCoordinate().getX();

								if (x > biggestX)
									biggestX = x;

							}

							jungData.delete(deletedArcs, deletedNodes);
							// TODO eventuell bessere Werte für Punkte suchen
							// X Werte des Punktes werden alle biggestX +
							// DISTANCE_WHEN_ADDED
							// gesetzt
							// Y Werte werden in DISTANCE_WHEN_ADDED Schritten nach oben
							// gesetzt
							int count = 0;
							for (INode elem : addedNodes) {
								count++;
								if (elem instanceof Place) {
									jungData.createPlace((Place) elem,
											new Point2D.Double(biggestX
													+ DISTANCE_WHEN_ADDED, count
													* DISTANCE_WHEN_ADDED));
								} else {
									jungData.createTransition(
											(Transition) elem,
											new Point2D.Double(biggestX
													+ DISTANCE_WHEN_ADDED, count
													* DISTANCE_WHEN_ADDED));
								}
							}
							for (Arc arc : addedArcs) {
								INode start = arc.getStart();
								INode end = arc.getEnd();
								if (start instanceof Place
										&& end instanceof Transition) {
									jungData.createArc(arc, (Place) start,
											(Transition) end);
								} else {
									jungData.createArc(arc, (Transition) start,
											(Place) end);
								}
							}
							jungData.deleteDataOfMissingElements(petrinet);
						} catch (IllegalArgumentException ex) { // thrown when
																// "not all incident arcs are given"
							pickedRules.remove(randomRule);
							if (pickedRules.size() == 0) {
								exception("No Rule is matching");
							}
						}
					} else {
						// This Rule did not match so it's removed from the
						// rules
						pickedRules.remove(randomRule);
						if (pickedRules.size() == 0) {
							exception("No Rule is matching");
						}
					}
				}
			}
		}
	}

	@Override
	public void fireOrTransform(int id, Collection<Integer> ruleIDs, int n)
			throws EngineException {
		for (int i = 0; i < n; i++) {
			if (random.nextFloat() < 0.5d) {
				try {
					fire(id, 1);
				} catch (EngineException ex) {
					transform(id, ruleIDs, 1);
				}
			} else {
				try {
					transform(id, ruleIDs, 1);
				} catch (EngineException ex) {
					fire(id, 1);
				}
			}
		}
	}

	private void exception(String value) throws EngineException {
		throw new EngineException(value);
	}

	@SuppressWarnings("unused")
	private void warning(String message) throws ShowAsWarningException {
		throw new ShowAsWarningException(message);
	}

	private void info(String message) throws ShowAsInfoException {
		throw new ShowAsInfoException(message);
	}

}
