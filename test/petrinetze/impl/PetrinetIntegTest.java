package petrinetze.impl;

import petrinet.Arc;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Post;
import petrinet.Pre;
import petrinet.Transition;
import petrinet.Petrinet;

public class PetrinetIntegTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Petrinet net = PetrinetComponent.getPetrinet().createPetrinet();
		Place p1 = net.createPlace("AAA");
		p1.setMark(3);
		Place p2 = net.createPlace("BBB");
		Place p3 = net.createPlace("CCC");
		Place p4 = net.createPlace("DDD");
		
		p2.setMark(6);
		
		Transition t1 = net.createTransition("ttt");
		Transition t2 = net.createTransition("uuu");
		Arc a4 = net.createArc("dt", p2, t2);
		a4.setMark(5);
		
		Arc a1 = net.createArc("at", p1, t1);
		a1.setMark(2);
		
		Arc a5 = net.createArc("at", t2, p4);
		a1.setMark(2);
		
		Arc a2 = net.createArc("bt", t1, p2);
		Arc a3 = net.createArc("ct", t1, p3);
		a3.setMark(3);
		
		for (Arc a : net.getAllArcs()) {
			System.out.println("Arc ID: " + a.getId() + " Name: " + a.getName());
		}
		
		Pre pre = net.getPre();
		System.out.println(pre);
		
		Post post = net.getPost();
		System.out.println(post);
		
//		net.deleteTransitionByID(2);
//		net.deletePlaceById(2);
		
		Pre pre1 = net.getPre();
		System.out.println(pre1);
		
		net.fire();
		
		Post post1 = net.getPost();
		System.out.println(post1);
		for (Arc a : net.getAllArcs()) {
			System.out.println("Arc ID: " + a.getId() + " Name: " + a.getName());
		}
		
		System.out.println(net.getActivatedTransitions());
	}

}
