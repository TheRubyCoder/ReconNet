package petrinetze;



public interface PetrinetListener {

//	void fired(IPetrinet petrinet, ITransition transition);
	void changed(IPetrinet petrinet, INode element, ActionType actionType);
	void changed(IPetrinet petrinet, IArc element, ActionType actionType);

}