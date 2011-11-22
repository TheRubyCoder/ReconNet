package transformation;

import java.util.HashMap;

import petrinet.IPetrinet;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Transition;

/**
 * Singleton that represents the transformation component<br/>
 * Other components refer to this object to delegate to the transformation component instead of directly refering to the classes within the component
 */
public class TransformationComponent implements ITransformation{
	
	private static TransformationComponent instance;
	
	private TransformationComponent() { }
	
	static {
		instance = new TransformationComponent();
	}
	
	public static ITransformation getPetrinet() {
		return instance;
	}

	@Override
	public Rule createRule() {
		return new Rule();
	}

}
