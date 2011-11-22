package petrinet;

import java.util.HashMap;
import java.util.Map;


/**
 * Singleton that represents the petrinet component<br/>
 * Other components refer to this object to delegate to the petrinet component instead of directly refering to the classes within the component
 */
public class PetrinetComponent implements IPetrinet{
	
	private static PetrinetComponent instance;
	
	private Map<Integer, Petrinet> petrinets;
	
	private Map<Integer, Transition> transitions;
	
	private PetrinetComponent() { }
	
	static { 
		instance = new PetrinetComponent();
		instance.petrinets = new HashMap<Integer, Petrinet>();
		instance.transitions = new HashMap<Integer, Transition>();
	}
	
	public static IPetrinet getPetrinet() {
		return instance;
	}

	@Override
	public Petrinet createPetrinet() {
		Petrinet petrinet = new Petrinet();
		petrinets.put(petrinet.getId(),petrinet);
		return petrinet;
	}

	@Override
	public Petrinet getPetrinetById(int id) {
		return petrinets.get(id);
	}

//	@Override
//	public Transition createTransition(Petrinet petrinet, IRenew renew) {
//		int id = UUID.gettID();
//		Transition transition = new Transition(id, renew, petrinet);
//		transitions.put(id, transition);
//		return transition;
//	}
//
//	@Override
//	public Transition getTransitionById(int id) {
//		return transitions.get(id);
//	}
	
}
