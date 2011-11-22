package petrinet;


/**
 * Interface for accessing petrinet component so other components do not need to directly access classes within the component 
 */
public interface IPetrinet {
	
	public Petrinet createPetrinet();
	
	public Petrinet getPetrinetById(int id);
	
//	public Transition createTransition(Petrinet petrinet, IRenew renew);
//	
//	public Transition getTransitionById(int id);

}
