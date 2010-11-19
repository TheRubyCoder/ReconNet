package transformation;

import petrinetze.IPetrinet;

public class Transformation implements ITransformation {
	
	private final IPetrinet N;
	private final IMorphism morphism;
	private final IRule rule;
	
	/**
	 * Constructor for the class Transformation
	 */	
	public Transformation(IPetrinet n,IMorphism morph,IRule r){
		N = n; morphism = morph; rule = r;
		
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
		// TODO Auto-generated method stub, Put code in ! 

	}

}
