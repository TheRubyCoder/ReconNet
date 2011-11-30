package transformation;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;

import java.util.HashMap;
import java.util.Set;

import exceptions.GeneralPetrinetException;
/**
 * An Transformation on a Petrinet<br/>
 * The Transformation applies a rule on an petrinet under a certain morphism
 */
public class Transformation{
	
	private final Petrinet petrinet;
	private final Morphism morphism;
	private final Rule rule; 
	
	/**	
	 * Constructor for the class Transformation
	 * @param net,the petrinet to transform
	 * @param morph, the morphism to use
	 * @param r, the rule that should apply
	 */	
	private Transformation(Petrinet petrinet,Morphism morphism,Rule rule){
		this.petrinet = petrinet; 
		this.morphism = morphism; 
		this.rule = rule;
		
	}
	
	/**
	 * Creates a new Transformation with given parameters
	 * @param petrinet Petrinet to transform
	 * @param morphism Morphism to use the rule under
	 * @param rule Rule to apply to petrinet
	 * @return the transformation
	 */
	static Transformation createTransformation(Petrinet petrinet,
			Morphism morphism,
			Rule rule){
		return new Transformation(petrinet, morphism, rule);
	}
	
	/**
	 * Creates a new Transformation with given parameters
	 * @param petrinet Petrinet to transform
	 * @param morphism Morphism to use the rule under
	 * @param rule Rule to apply to petrinet
	 * @return the transformation<br/><tt>null</tt>if no Morphism found
	 */
	static Transformation createTransformationWithAnyMorphism(Petrinet petrinet,
			Rule rule){
		
		Morphism tempMorphism = MorphismFactory.createMorphism(rule.getL(),petrinet);
		//Morphism found?
		if(tempMorphism != null){
			return new Transformation(petrinet, tempMorphism, rule);
		}else{
			return null;
		}
	}
	
	/**
	 * Returns the Petrinet of this transformation.
	 * This net will be changed when transform() is called.
	 * @return the Rule of this transformation.
	 */
	public Petrinet getPetrinet() {
		return petrinet;
	}
	
	/**
	 * Returns the Morphism of this transformation.
	 * @return the Morphism of this transformation.
	 */
	public Morphism getMorphism() {
		return morphism;
	}
	
	/**
	 * Returns the Rule of this transformation.
	 * @return the Rule of this transformation.
	 */	
	public Rule getRule() {
		return rule;
	}	
	
	/**
	 * This will transform the petrinet
	 * using the Rule returned by getRule() and
	 * the Morphism returned by getMorphism().
	 * @return the Transformation that was used (<tt>this</tt>)
	 */
	Transformation transform() {
		Petrinet K = rule.getK();
		Set<INode> KNode = K.getAllGraphElement().getAllNodes();
		Set<Arc> KArc = K.getAllArcs();		
		for (INode i : KNode)  // Add K - L Places
		{
			if(rule.fromKtoL(i) == null) { // If K not in L do,.....
				if(i instanceof Place){   
					Place n = petrinet.createPlace(i.getName());
					morphism.getPlacesMorphism().put((Place)i, n);
				}
				else{
					IRenew rnw = ((Transition) i).getRnw();
					Transition n = petrinet.createTransition(i.getName(),rnw);
					morphism.getTransitionsMorphism().put((Transition)i, n);
				}
			}
			else
			{
				// just add the nodes to create d
				if(i instanceof Place){   
					morphism.getPlacesMorphism().put((Place)i, morphism.getPlaceMorphism((Place)rule.fromKtoL(i)));
				}
				else{
					morphism.getTransitionsMorphism().put((Transition)i, morphism.getTransitionMorphism((Transition)rule.fromKtoL(i)));
				}
			}
		}
		for (Arc a : KArc){ //Add K - L Arcs
			if(rule.fromKtoL(a) == null) {
				if(a.getStart() instanceof Place){
					Arc n = petrinet.createArc(a.getName(),morphism.getPlaceMorphism((Place)a.getStart()),morphism.getTransitionMorphism((Transition)a.getEnd()));
					morphism.getEdgesMorphism().put(a, n);
				}
				else{
					Arc n = petrinet.createArc(a.getName(),morphism.getTransitionMorphism((Transition)a.getStart()),morphism.getPlaceMorphism((Place)a.getEnd()));
					morphism.getEdgesMorphism().put(a, n);
				}	
			}
			else
			{
				// just add the edges to create d
				morphism.getEdgesMorphism().put(a, morphism.getArcMorphism(rule.fromKtoL(a)));
			}
		}
		for (INode i : KNode) // Delete K - R Places
		{
			if (rule.fromKtoR(i) == null) { 
				if(i instanceof Place){
					petrinet.deletePlaceById(morphism.getPlaceMorphism((Place)i).getId());
				}
				else {
					petrinet.deleteTransitionByID(morphism.getTransitionMorphism((Transition)i).getId());
				}
			}
		}
		for (Arc i : KArc){ // Delete K - R Arcs
			if (rule.fromKtoR(i) == null) {
				petrinet.deleteArcByID(morphism.getArcMorphism(i).getId());
			}			
		}
		return this;
	}

}