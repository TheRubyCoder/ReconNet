package transformation;

import petrinet.IPetrinet;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;

/**
 * Singleton that represents the transformation component<br/>
 * Other components refer to this object to delegate to the transformation component instead of directly refering to the classes within the component
 */
public class TransformationComponent{
	
	private static TransformationComponent instance;
	
	private TransformationComponent() { }
	
	static {
		instance = new TransformationComponent();
	}

}
