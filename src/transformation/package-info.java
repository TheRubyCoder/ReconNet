/**																			  #
 * <p>
 * The transformation component bundles logical functionalities for rules
 * and transformations. It is responsible for creating and editing rules as 
 * well as finding morphisms and applying rules to petrinets. Therefore it
 * directly accesses {@link petrinet.model.Petrinet petrinets} from the petrinet
 * component.
 * </p>
 * <p>
 * The most important class in this component is the 
 * {@link transformation.Rule Rule} which is composed of three petrinets and
 * explicit morphisms among their nodes or to be more precise between L<->K 
 * and K<->R.
 * A {@link transformation.Morphism Morphism} is just an information holding 
 * class without any high level logic. It just combines the source and target
 * petrinets as well as mappings between their elements into one handy object.
 * It is used in the {@link transformation.Transformation Transformation} to
 * actually apply a rule to a petrinet. The modified Ullmann algorithm is 
 * extracted into the utility class
 * {@link transformation.MorphismFactory MorphismFactory} 
 * </p>
 */
package transformation;
