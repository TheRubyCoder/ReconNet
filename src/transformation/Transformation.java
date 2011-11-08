package transformation;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.ITransition;
import java.util.Set;

import exceptions.GeneralPetrinetException;

public class Transformation implements ITransformation {
	
	private final IPetrinet petrinet;
	private final IMorphism morphism;
	private final IRule rule; 
	
	/**	
	 * Constructor for the class Transformation
	 * @param net,the petrinet to transform
	 * @param morph, the morphism to use
	 * @param r, the rule that should apply
	 */	
	private Transformation(IPetrinet petrinet,IMorphism morphism,IRule rule){
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
	public static ITransformation createTransformation(IPetrinet petrinet,
			IMorphism morphism,
			IRule rule){
		return new Transformation(petrinet, morphism, rule);
	}
	
	/**
	 * Creates a new Transformation with given parameters
	 * @param petrinet Petrinet to transform
	 * @param morphism Morphism to use the rule under
	 * @param rule Rule to apply to petrinet
	 * @return the transformation
	 * @throws GeneralPetrinetException When no default morphism found
	 */
	public static ITransformation createTransformationWithAnyMorphism(IPetrinet petrinet,
			IRule rule) throws GeneralPetrinetException{
		IMorphism tempMorphism = MorphismFactory.createMorphism(rule.getL(),petrinet);
		if(tempMorphism == null){
			throw new GeneralPetrinetException("No Morphism detected");
		}
		return new Transformation(petrinet, tempMorphism, rule);
	}
	
	/**
	 * Returns the IPetrinet of this transformation.
	 * This net will be changed when transform() is called.
	 * @return the IRule of this transformation.
	 */
	@Override
	public IPetrinet getPetrinet() {
		return petrinet;
	}
	
	/**
	 * Returns the IMorphism of this transformation.
	 * @return the IMorphism of this transformation.
	 */
	@Override
	public IMorphism getMorphism() {
		return morphism;
	}
	
	/**
	 * Returns the IRule of this transformation.
	 * @return the IRule of this transformation.
	 */	
	@Override
	public IRule getRule() {
		return rule;
	}	
	
	/**
	 * Method for starting the transform. 
	 */
	@Override
	public void transform() {
		IPetrinet K = rule.getK();
		Set<INode> KNode = K.getAllGraphElement().getAllNodes();
		Set<IArc> KArc = K.getAllArcs();		
		for (INode i : KNode)  // Add K - L Places
		{
			if(rule.fromKtoL(i) == null) { // If K not in L do,.....
				if(i instanceof IPlace){   
					IPlace n = petrinet.createPlace(i.getName());
					morphism.getPlacesMorphism().put((IPlace)i, n);
				}
				else{
					IRenew rnw = ((ITransition) i).getRnw();
					ITransition n = petrinet.createTransition(i.getName(),rnw);
					morphism.getTransitionsMorphism().put((ITransition)i, n);
				}
			}
			else
			{
				// just add the nodes to create d
				if(i instanceof IPlace){   
					morphism.getPlacesMorphism().put((IPlace)i, morphism.getPlaceMorphism((IPlace)rule.fromKtoL(i)));
				}
				else{
					morphism.getTransitionsMorphism().put((ITransition)i, morphism.getTransitionMorphism((ITransition)rule.fromKtoL(i)));
				}
			}
		}
		for (IArc a : KArc){ //Add K - L Arcs
			if(rule.fromKtoL(a) == null) {
				if(a.getStart() instanceof IPlace){
					IArc n = petrinet.createArc(a.getName(),morphism.getPlaceMorphism((IPlace)a.getStart()),morphism.getTransitionMorphism((ITransition)a.getEnd()));
					morphism.getEdgesMorphism().put(a, n);
				}
				else{
					IArc n = petrinet.createArc(a.getName(),morphism.getTransitionMorphism((ITransition)a.getStart()),morphism.getPlaceMorphism((IPlace)a.getEnd()));
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
				if(i instanceof IPlace){
					petrinet.deletePlaceById(morphism.getPlaceMorphism((IPlace)i).getId());
				}
				else {
					petrinet.deleteTransitionByID(morphism.getTransitionMorphism((ITransition)i).getId());
				}
			}
		}
		for (IArc i : KArc){ // Delete K - R Arcs
			if (rule.fromKtoR(i) == null) {
				petrinet.deleteArcByID(morphism.getArcMorphism(i).getId());
			}			
		}
	}

}