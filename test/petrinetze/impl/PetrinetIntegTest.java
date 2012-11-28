package petrinetze.impl;

import petrinet.PetrinetComponent;
import petrinet.model.IArc;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Post;
import petrinet.model.Pre;
import petrinet.model.Transition;

public class PetrinetIntegTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Petrinet net = PetrinetComponent.getPetrinet().createPetrinet();
		Place p1 = net.addPlace("AAA");
		p1.setMark(3);
		Place p2 = net.addPlace("BBB");
		Place p3 = net.addPlace("CCC");
		Place p4 = net.addPlace("DDD");
		
		p2.setMark(6);
		
		Transition t1 = net.addTransition("ttt");
		Transition t2 = net.addTransition("uuu");
		IArc a4 = net.addPreArc("dt", p2, t2);
		a4.setMark(5);
		
		IArc a1 = net.addPreArc("at", p1, t1);
		a1.setMark(2);
		
		IArc a5 = net.addPostArc("at", t2, p4);
		a1.setMark(2);
		
		IArc a2 = net.addPostArc("bt", t1, p2);
		IArc a3 = net.addPostArc("ct", t1, p3);
		a3.setMark(3);
		
		for (IArc a : net.getArcs()) {
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
		for (IArc a : net.getArcs()) {
			System.out.println("Arc ID: " + a.getId() + " Name: " + a.getName());
		}
		
		System.out.println(net.getActivatedTransitions());
	}

}
