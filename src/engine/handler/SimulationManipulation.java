package engine.handler;

import java.util.Collection;

import engine.ihandler.ISimulation;

public class SimulationManipulation implements ISimulation {

	@Override
	public int createSimulationSession(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fire(int id, int n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(int id, String path, String filename, String format) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transform(int id, Collection<Integer> ruleIDs, int n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void fireOrTransform(int id, Collection<Integer> ruleIDs, int n) {
		// TODO Auto-generated method stub
	}

}
