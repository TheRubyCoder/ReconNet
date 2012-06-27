package engine.dependency;

import petrinet.Petrinet;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import exceptions.GeneralPetrinetException;


public class TransformationAdapter {
	
	private TransformationAdapter(){};
	
	public static Rule createRule() {
		return TransformationComponent.getTransformation().createRule();
	}
	
	
	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @throws GeneralPetrinetException When no default morphism found
	 * @return the transformation that was used for transforming (containing rule, nNet and morphism)
	 */
	public static Transformation transform(Petrinet net, Rule rule)
	{
		return TransformationComponent.getTransformation().transform(net, rule);
	}
	
	/**
	 * Sets the mark of a node in a rule and modifies other parts of rule accordingly
	 * @param rule Rule in wich the node is included
	 * @param placeId id of place
	 * @param mark new value for mark
	 * @return needs to be defined by engine group
	 */
	public static void setMark(Rule rule, int placeId, int mark){
		TransformationComponent.getTransformation().setMark(rule, placeId, mark);
	}
}
