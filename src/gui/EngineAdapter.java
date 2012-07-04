package gui;

import engine.handler.petrinet.PetrinetManipulation;
import engine.handler.petrinet.PetrinetPersistence;
import engine.handler.rule.RuleManipulation;
import engine.handler.simulation.SimulationHandler;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.ISimulation;

/**
 * Adapter to the engine component. This encapsulates the access of "handler"
 * objects and just returns the interface
 */
public class EngineAdapter {

	private EngineAdapter() {
	}

	public static ISimulation getSimulation() {
		return SimulationHandler.getInstance();
	}

	public static IPetrinetManipulation getPetrinetManipulation() {
		return PetrinetManipulation.getInstance();
	}

	public static IRuleManipulation getRuleManipulation() {
		return RuleManipulation.getInstance();
	}

	public static IPetrinetPersistence getPetrinetPersistence() {
		return PetrinetPersistence.getInstance();
	}

}
