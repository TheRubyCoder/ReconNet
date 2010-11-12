package haw.wp.rcpn.test;

import haw.wp.rcpn.IArc;
import haw.wp.rcpn.IPetrinet;
import haw.wp.rcpn.IPlace;
import haw.wp.rcpn.ITransition;
import haw.wp.rcpn.impl.Session;

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
