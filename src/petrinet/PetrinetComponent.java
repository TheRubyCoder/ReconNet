package petrinet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	public Petrinet getPetrinetById(int id) {
		return petrinets.get(id);
	}

	@Override
	public Collection<Integer> deleteElementInPetrinet(int petrinetId,
			int elementId) {
		return getPetrinetById(petrinetId).deleteElementById(elementId);
	}

	@Override
	public ElementType getNodeType(int petrinetId, int nodeId) {
		return getPetrinetById(petrinetId).getNodeType(nodeId);
	}
}
