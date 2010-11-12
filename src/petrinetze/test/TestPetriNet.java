package petrinetze.test;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.Session;

public class TestPetriNet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Generiere die Session
		Session session = new Session();
		//Generiere das 1. Netz
		IPetrinet net = session.createLabeldPetrinet();
		//Baue die 1. Stelle
		IPlace place = net.createPlace("TesPlacet");
		//Baue die 1. Transition
		ITransition transition = net.createTransition("TestTransition");
		//Verbinde die Stelle mit Transition
		IArc arc = net.createArc();
		arc.setStart(place);
		arc.setEnd(transition);
		session.setRunning(true);
	}

}
