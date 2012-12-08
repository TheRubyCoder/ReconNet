package engine.dependency;

import petrinet.model.Petrinet;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;


public class TransformationAdapter {
	
	private TransformationAdapter(){};
	
	public static Rule createRule() {
		return TransformationComponent.getTransformation().createRule();
	}
	
	
	/**
	 * Transformations the petrinet like defined in rule with random match
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @return the transformation that was used for transforming (containing rule, nNet and match)
	 */
	public static Transformation transform(Petrinet net, Rule rule)
	{
		return TransformationComponent.getTransformation().transform(net, rule);
	}
}
