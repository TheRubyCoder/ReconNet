package transformation;

import exceptions.GeneralPetrinetException;
import petrinetze.IPetrinet;

/**
 * @author Philipp Kuehn
 * @author Marvin Ede
 * @author Oliver Willhoeft
 *
 */
public final class Transformations 
{
	private Transformations()
	{
		
	}
	
	/**
	 * Joins both petrinets, using the given transformation.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param transformation the transformation to use.
	 */
	public static void join(IPetrinet left, IPetrinet right, ITransformation transformation)
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
	public static void join(IPetrinet left, IPetrinet right, IMorphism morphism, IRule rule)
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
	 * @throws Exception 
	 */
	public static void join(IPetrinet left, IPetrinet right, IRule rule) throws GeneralPetrinetException
	{
		left.addNet(right);
		Transformation.createTransformationWithAnyMorphism(left, rule).transform();
//		new Transformation(left, rule).transform();
	}
	
	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @throws GeneralPetrinetException When no default morphism found
	 * @return the transformation that was used for transforming (containing rule, nNet and morphism)
	 */
	public static ITransformation transform(IPetrinet net, IRule rule) throws GeneralPetrinetException
	{
		return Transformation.createTransformationWithAnyMorphism(net, rule).transform();
//		new Transformation(net, rule).transform();
//		System.out.println(net);
	}

}
