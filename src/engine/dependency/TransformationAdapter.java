package engine.dependency;

import petrinet.Petrinet;
import exceptions.GeneralPetrinetException;
import transformation.ChangedPetrinetElements;
import transformation.Morphism;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;


public class TransformationAdapter {
	
	private TransformationAdapter(){};
	
	public static Rule createRule() {
		return TransformationComponent.getTransformation().createRule();
	}
	
	/**
	 * Joins both petrinets, using the given transformation.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param transformation the transformation to use.
	 */
	public static void join(Petrinet left, Petrinet right, Transformation transformation)
	{
		TransformationComponent.getTransformation().join(left, right, transformation);
	}

	/**
	 * Joins both petrinets, using the given morphism and rule.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param morphism the morphism to use.
	 * @param rule the rule to use.
	 */
	public static void join(Petrinet left, Petrinet right, Morphism morphism, Rule rule)
	{
		TransformationComponent.getTransformation().join(left, right, morphism, rule);
	}

	/**
	 * Will join both petrinets, using the given rule and a random morphism.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param rule the rule to use.
	 * @throws Exception 
	 */
	public static void join(Petrinet left, Petrinet right, Rule rule) throws GeneralPetrinetException
	{
		TransformationComponent.getTransformation().join(left, right, rule);
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
	public static ChangedPetrinetElements setMark(Rule rule, int placeId, int mark){
		return TransformationComponent.getTransformation().setMark(rule, placeId, mark);
	}
}
