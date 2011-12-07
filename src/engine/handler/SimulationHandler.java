package engine.handler;

import java.util.Collection;

import engine.ihandler.ISimulation;

public class SimulationHandler implements ISimulation {

	@Override
	public int createSimulationSession(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean fire(int id, int n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void save(int id, String path, String filename, String format) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean transform(int id, Collection<Integer> ruleIDs, int n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fireOrTransform(int id, Collection<Integer> ruleIDs, int n) {
		// TODO Auto-generated method stub
		return false;
	}

}
