package petrinetze.impl;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPetrinetMgr;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.Petrinet;
import petrinetze.impl.PetrinetMgrImpl;

public class TestPetriNet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		//Generiere das 1. Netz
//		IPetrinetMgr mgr = new PetrinetMgrImpl();
		IPetrinet net = new Petrinet();
		//Baue die 1. Stelle
		IPlace place = net.createPlace("Erste Stelle");
		place.setMark(2);
		//Baue die 1. Transition
		ITransition transition = net.createTransition("Erste Transition");
		IPlace place2 = net.createPlace("Zweite Stelle");
		place2.setMark(1);
		//Verbinde die Stelle mit Transition
		IArc arc = net.createArc("Erste Kante");
		arc.setStart(place);
		arc.setEnd(transition);
		arc.setMark(2);
		IArc arc2 = net.createArc("Zweite Kante");
		arc2.setStart(transition);
		arc2.setEnd(place2);
		arc2.setMark(1);
		
		System.out.println(net);
	}

}
