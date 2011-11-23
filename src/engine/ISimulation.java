package engine;

import petrinet.Petrinet;

public interface ISimulation {
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB 
	 * @param petrinet
	 * @return
	 */
	boolean simulateOneStep(Petrinet petrinet);
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB 
	 * @param petrinet
	 * @param k
	 * @return
	 */
	boolean simulateKSteps(Petrinet petrinet, int k);
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB 
	 * @param petrinet
	 * @return
	 */
	boolean startSimulation(Petrinet petrinet);
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB 
	 * @param petrinet
	 * @param switchVektor
	 * @return
	 */
	boolean simulateStep(Petrinet petrinet, Integer[] switchVektor);

}
