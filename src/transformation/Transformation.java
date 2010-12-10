package transformation;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.ITransition;
import petrinetze.impl.*;


import java.util.Iterator;
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
		morphism = new Morphism(N,r.L());
		
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
		// TODO Auto-generated method stub
		return morphism;
	}
	
	/**
	 * Returns the IRule of this transformation.
	 * @return the IRule of this transformation.
	 */	
	@Override
	public IRule rule() {
		// TODO Auto-generated method stub
		return rule;
	}		
	/**
	 * Method for starting the transform. 
	 */
	@Override
	public void transform() {
		IPetrinet K = rule.K();
		IPetrinet L = rule.L();	
		//Places of K - Places of L
		Set<INode> KNode = K.getAllGraphElement().getAllNodes();
		Set<IArc> KArcs = K.getAllArcs();
		//rule.fromKtoL();
		for (INode i : KNode)
		{
			if(!(rule.fromKtoL(i).equals(null))){
				if(i instanceof IPlace){   // Wenn I ein Place
					IPlace place = N.createPlace(i.getName());
				}
				else{
					IRenew rnw = ((ITransition) i).getRnw();
					N.createTransition(i.getName(),rnw);
				}
			}				  
		}
		
			
		
		
		// TODO Check if L is in N
		// ADD K-L to the transformationPut code in ! 

	}

}
