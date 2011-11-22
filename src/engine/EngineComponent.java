package engine;

import engine.dependency.PetrinetAdapter;
import engine.dependency.TransformationAdapter;
import petrinet.Petrinet;
import transformation.Rule;

/**
 * Singleton that represents the engine component<br/>
 * Other components refer to this object to delegate to the engine component
 * instead of directly reffering to the classes within the component
 */
public class EngineComponent implements IPetrinetManipulation {

	private static EngineComponent instance;

	private EngineComponent() {	}

	static {
		instance = new EngineComponent();
	}
	
	public static IPetrinetManipulation getEngine(){
		return instance;
	}
	
	
	public Petrinet createPetrinet() {
		return PetrinetAdapter.createPetrinet();
	}

	public Petrinet getPetrinetById(int id) {
		return PetrinetAdapter.getPetrinetById(id);
	}


	@Override
	public Rule createRule() {
		return TransformationAdapter.createRule();
	}

//	@Override
//	public Transition createTransition(Petrinet petrinet, IRenew renew) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Transition getTransitionById(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
