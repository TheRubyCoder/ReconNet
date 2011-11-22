package engine;

import engine.dependency.PetrinetAdapter;
import petrinet.IPetrinet;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;

/**
 * Singleton that represents the engine component<br/>
 * Other components refer to this object to delegate to the engine component
 * instead of directly reffering to the classes within the component
 */
public class EngineComponent implements IPetrinet {

	private static EngineComponent instance;

	private EngineComponent() {	}

	static {
		instance = new EngineComponent();
	}
	
	public static IPetrinet getPetrinet(){
		return instance;
	}
	
	public Petrinet createPetrinet() {
		return PetrinetAdapter.createPetrinet();
	}

	public Petrinet getPetrinetById(int id) {
		return PetrinetAdapter.getPetrinetById(id);
	}

}
