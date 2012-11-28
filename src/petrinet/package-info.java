/**
 * <p>
 * The petrinet component bundles all basic functionalities for petrinets.
 * This means there are only purely logical functions such as creating and
 * editing petrinets, checking whether a transition is active, firing a single
 * transition, Renews, Labels etc.
 * </p>
 * <p>
 * Obviously the most important class is the {@link petrinet.model.Petrinet Petrinet}
 * as it has most of the functionalities described above. It is composed of
 * {@link petrinet.model.Place places}, {@link petrinet.model.Transition transitions} and
 * {@link petrinet.model.Arc arcs} which also reference one another.
 * The {@link petrinet.model.Place Place} does not have information about its
 * position or color as it is mapped in the engine.
 * {@link petrinet.model.IRenew Renews} are implemented as an object that has the
 * {@link petrinet.model.IRenew#renew(String) renew method} to transform an input
 * String to an output String. So all Labels need to be Strings. Also there
 * are only a few implementations of Renews that are actually used: 
 * {@link petrinet.model.rnw.Count count},
 * {@link petrinet.model.rnw.Toggle toggle} and
 * {@link petrinet.model.rnw.Identity id}
 * </p>
 */
package petrinet;

