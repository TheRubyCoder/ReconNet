package engine.dependency;

import transformation.Rule;
import transformation.TransformationComponent;


public class TransformationAdapter {
	
	private TransformationAdapter(){};
	
	public static Rule createRule() {
		return TransformationComponent.getPetrinet().createRule();
	}

}
