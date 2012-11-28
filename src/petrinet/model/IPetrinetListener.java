package petrinet.model;

/**
 * A PetrinetListener can be attacked to a petrinet 
 * ({@link Petrinet#addPetrinetListener(IPetrinetListener)}) to perform some
 * action on the changing of nodes or arcs. The action is defined by the
 * concrete class that implements {@link IPetrinetListener} 
 */
public interface IPetrinetListener {
	void changed(Petrinet petrinet, INode element, ActionType actionType);
    void changed(Petrinet petrinet, IArc element, ActionType actionType);
}