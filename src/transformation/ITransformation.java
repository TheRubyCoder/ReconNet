package transformation;

import petrinet.Petrinet;
import exceptions.GeneralPetrinetException;


/**
 * Interface for accessing transformation component so other components do not need to directly access classes within the component 
 */
public interface ITransformation {

	public Rule createRule();
	
	/**
	 * Joins both petrinets, using the given transformation.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param transformation the transformation to use.
	 */
	void join(Petrinet left, Petrinet right, Transformation transformation);

	/**
	 * Joins both petrinets, using the given morphism and rule.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param morphism the morphism to use.
	 * @param rule the rule to use.
	 */
	void join(Petrinet left, Petrinet right, Morphism morphism, Rule rule);

	/**
	 * Will join both petrinets, using the given rule and a random morphism.
	 * The right petrinet will be modified and will be the resulting net.
	 * @param left the left petrinet.
	 * @param right the right petrinet.
	 * @param rule the rule to use.
	 * @throws Exception 
	 */
	void join(Petrinet left, Petrinet right, Rule rule) throws GeneralPetrinetException;
	
	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @throws GeneralPetrinetException When no default morphism found
	 * @return the transformation that was used for transforming (containing rule, nNet and morphism)
	 */
	Transformation transform(Petrinet net, Rule rule);
	
}
