/**																			  
 * <p>
 * The engine component joines all the other components together by 
 * transforming data and controling the program flow. It also adds colors and 
 * positions to nodes and runs simulations on nets.
 * </p>
 * <p> The most important classes for the engine are the handlers 
 * {@link engine.handler.petrinet.PetrinetHandler petrinet handler},
 * {@link engine.handler.rule.RuleHandler rule handler} and 
 * {@link engine.handler.simulation.SimulationHandler simulation handler} which
 * implement their respective Interfaces
 * {@link engine.ihandler.IRuleManipulation IRuleManipulation} and 
 * {@link engine.ihandler.IPetrinetPersistence petrinet persistence} etc.
 * They do not actually use the keyword <code>implements</code> to do so, as
 * the handlers are called from special classes (e.g.
 * {@link engine.handler.petrinet.PetrinetManipulation PetrinetManipulation})
 * that implement only one of those interfaces.
 * Also important is the {@link engine.data data package} as it contains
 * the classes {@link engine.data.JungData JungData}, 
 * {@link engine.data.PetrinetData PetrinetData} and
 * {@link engine.data.RuleData RuleData} which are returned by many methods
 * to give information about rules, petrinets and nodes.
 * <b>Petrinets and Rules should not be accessed by the gui directly but only 
 * through the engine.<b> 
 * </p>
 * 
 */
package engine;