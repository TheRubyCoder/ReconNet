package gui2;

import engine.ihandler.IPetrinetPersistence;
import engine.handler.petrinet.PetrinetManipulation;
import engine.handler.petrinet.PetrinetPersistence;
import engine.handler.rule.RuleManipulation;
import engine.handler.simulation.Simulation;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.ISimulation;

public class EngineAdapter {
	
	private EngineAdapter() {}
	
	public static ISimulation getSimulation(){
		return Simulation.getInstance();
	}
	
	public static IPetrinetManipulation getPetrinetManipulation(){
		return PetrinetManipulation.getInstance();
	}
	
	public static IRuleManipulation getRuleManipulation(){
		return RuleManipulation.getInstance();
	}
	
	public static IPetrinetPersistence getPetrinetPersistence(){
		return PetrinetPersistence.getInstance(); 
	}

}
