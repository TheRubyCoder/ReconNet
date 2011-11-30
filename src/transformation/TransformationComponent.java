package transformation;

import petrinet.Petrinet;
import exceptions.GeneralPetrinetException;

/**
 * Singleton that represents the transformation component<br/>
 * Other components refer to this object to delegate to the transformation component instead of directly refering to the classes within the component
 */
public class TransformationComponent implements ITransformation{
	
	//#################### singleton ##################
	private static TransformationComponent instance;
	
	private TransformationComponent() { }
	
	static {
		instance = new TransformationComponent();
	}
	
	public static ITransformation getTransformation() {
		return instance;
	}
	//#################################################
	
	//### instance methods  (UML-Interface) ###########
	@Override
	public Rule createRule() {
		return new Rule();
	}
	
	/**
	 * Joins both petrinets, using the given transformation.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param transformation the transformation to use.
	 */
	@Override
	public void join(Petrinet left, Petrinet right, Transformation transformation)
	{
		left.addNet(right);
		transformation.transform();
	}

	/**
	 * Joins both petrinets, using the given morphism and rule.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param morphism the morphism to use.
	 * @param rule the rule to use.
	 */
	@Override
	public void join(Petrinet left, Petrinet right, Morphism morphism, Rule rule)
	{
		left.addNet(right);
		Transformation.createTransformation(left, morphism, rule).transform();
//		new Transformation(left, morphism, rule).transform();
	}

	/**
	 * Will join both petrinets, using the given rule and a random morphism.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param rule the rule to use.
	 */
	@Override
	public void join(Petrinet left, Petrinet right, Rule rule)
	{
		left.addNet(right);
		Transformation.createTransformationWithAnyMorphism(left, rule).transform();
	}
	
	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @throws GeneralPetrinetException When no default morphism found
	 * @return the transformation that was used for transforming (containing rule, nNet and morphism)
	 */
	@Override
	public  Transformation transform(Petrinet net, Rule rule)
	{
		System.out.println(rule.getR());
		return Transformation.createTransformationWithAnyMorphism(net, rule).transform();
	}

	@Override
	public ChangedPetrinetElements setMark(Rule rule, int placeId, int mark) {
		return rule.setMark(placeId, mark);
	}

}
