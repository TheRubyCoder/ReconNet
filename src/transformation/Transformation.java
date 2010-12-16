package transformation;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.ITransition;
import java.util.Set;

public class Transformation implements ITransformation {
	
	private final IPetrinet N;
	private final IMorphism morphism;
	private final IRule rule; 
	
	/**
	 * Constructor for the class Transformation
	 * @param Petrinet N, Morphism morph, Rule R
	 */	
	public Transformation(IPetrinet n,IMorphism morph,IRule r){
		N = n; morphism = morph; rule = r;
		
	}
	/**
	 * Constructor for the class Transformation
	 * @param Petrinet N,Rule R
	 */	
	public Transformation(IPetrinet n,IRule r){
		N = n; rule = r;
		morphism = MorphismFactory.createMorphism(N,r.L());
		
	}		
	/**
	 * Returns the IPetrinet of this transformation.
	 * This net will be changed when transform() is called.
	 * @return the IRule of this transformation.
	 */
	@Override
	public IPetrinet N() {
		return N;
	}
	
	/**
	 * Returns the IMorphism of this transformation.
	 * @return the IMorphism of this transformation.
	 */
	@Override
	public IMorphism morphism() {
		return morphism;
	}
	
	/**
	 * Returns the IRule of this transformation.
	 * @return the IRule of this transformation.
	 */	
	@Override
	public IRule rule() {
		return rule;
	}		
	/**
	 * Method for starting the transform. 
	 */
	@Override
	public void transform() {
		IPetrinet K = rule.K();
		Set<INode> KNode = K.getAllGraphElement().getAllNodes();
		Set<IArc> KArc = K.getAllArcs();		
		for (INode i : KNode)  // Add K - L Places
		{
			if(!(rule.fromKtoL(i).equals(null))){
				if(i instanceof IPlace){   
					N.createPlace(i.getName());
					
				}
				else{
					IRenew rnw = ((ITransition) i).getRnw();
					N.createTransition(i.getName(),rnw);
				}
			}				  
		}
		for (IArc a : KArc){ //Add K - L Arcs
			if(!(rule.fromKtoL(a)).equals(null)){
				if(a.getStart() instanceof IPlace){
					N.createArc(a.getName(),morphism.morph((IPlace)a.getStart()),morphism.morph((ITransition)a.getEnd()));
				}
				else{
					N.createArc(a.getName(),morphism.morph((ITransition)a.getStart()),morphism.morph((IPlace)a.getEnd()));
				}	
			}
		}
		for (INode i : KNode) // Delete K - R Places
		{
			if(!(rule.fromKtoR(i).equals(null))){
				if(i instanceof IPlace){
					N.deletePlaceById(morphism.morph((IPlace)i).getId());
				}
				else {
					N.deleteTransitionByID(morphism.morph((ITransition)i).getId());
				}
			}
		}
		for (IArc i : KArc){ // Delete K - R Arc´s
			if(!(rule.fromKtoR(i).equals(null))){
				N.deleteArcByID(morphism.morph(i).getId());
			}
			
		}
		

	}

}