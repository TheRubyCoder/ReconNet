package petrinetze;




public interface IPetrinetListener {

//	void fired(Petrinet petrinet, ITransition transition);
	void changed(Petrinet petrinet, INode element, ActionType actionType);

    void changed(Petrinet petrinet, Arc element, ActionType actionType);


}
