package engine.handler.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import petrinet.Petrinet;
import transformation.ITransformation;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.ihandler.ISimulation;
import engine.session.SessionManager;
import exceptions.EngineException;

public class Simulation implements ISimulation {

	private final SessionManager sessionManager;
	private final ITransformation transformationComponent;
	private static Simulation simulation;

	private Simulation() {
		sessionManager = SessionManager.getInstance();
		transformationComponent = TransformationComponent.getTransformation();
	}

	public static Simulation getInstance() {
		if (simulation == null)
			simulation = new Simulation();

		return simulation;
	}

	@Override
	public int createSimulationSession(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fire(int id, int n) throws EngineException {

		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("Simulation - id of the Petrinet is wrong");

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
		// TODO Auto-generated method stub
		// waiting for Persistence Team

	}

	private Random random = new Random();

	@Override
	public void transform(int id, Collection<Integer> ruleIDs, int n)
			throws EngineException {

		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("Simulation - id of the Petrinet is wrong");

		} else {
			Petrinet petrinet = petrinetData.getPetrinet();
			List<Integer> pickedRules = new ArrayList<Integer>();
			pickedRules.addAll(ruleIDs);

			for (int i = 0; i < n; i++) {
				// Getting a random Rule from pickedRules
				int randomRule = random.nextInt(pickedRules.size());

				RuleData ruleData = sessionManager.getRuleData(pickedRules
						.get(randomRule));
				Rule rule = ruleData.getRule();

				Transformation transformation = transformationComponent
						.transform(petrinet, rule);

				// if result of transformation != null the petrinet changed so
				// we have to do the next transform step with the changed
				// petrinet
				if (transformation != null) {
					petrinet = transformation.getPetrinet();
				}

			}
		}
	}

	@Override
	public void fireOrTransform(int id, Collection<Integer> ruleIDs, int n)
			throws EngineException {
		// TODO Auto-generated method stub
	}

	private void exception(String value) throws EngineException {
		throw new EngineException(value);
	}

}
