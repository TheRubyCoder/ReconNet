package gui2;

import engine.handler.simulation.Simulation;
import engine.ihandler.ISimulation;

public class EngineAdapter {
	
	private EngineAdapter() {}
	
	public static ISimulation getSimulation(){
		return Simulation.getInstance();
	}

}
