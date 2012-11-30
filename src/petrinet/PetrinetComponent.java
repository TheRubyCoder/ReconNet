package petrinet;

import java.util.HashMap;
import java.util.Map;

import petrinet.model.Petrinet;

/**
 * Singleton that represents the petrinet component<br/>
 * Other components refer to this object to delegate to the petrinet component
 * instead of directly refering to the classes within the component
 */
public class PetrinetComponent implements IPetrinet {

	private static PetrinetComponent instance;

	private Map<Integer, Petrinet> petrinets;

	private PetrinetComponent() {
	}

	static {
		instance = new PetrinetComponent();
		instance.petrinets = new HashMap<Integer, Petrinet>();
	}

	public static IPetrinet getPetrinet() {
		return instance;
	}

	@Override
	public Petrinet createPetrinet() {
		Petrinet petrinet = new Petrinet();
		petrinets.put(petrinet.getId(), petrinet);
		return petrinet;
	}

	@Override
	public Petrinet getPetrinet(int id) {
		return petrinets.get(id);
	}
}
