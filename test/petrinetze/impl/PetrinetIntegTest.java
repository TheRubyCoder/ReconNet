package petrinetze.impl;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IPost;
import petrinetze.IPre;
import petrinetze.ITransition;
import petrinetze.Petrinet;

public class PetrinetIntegTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPetrinet net = new Petrinet();
		IPlace p1 = net.createPlace("AAA");
		p1.setMark(3);
		IPlace p2 = net.createPlace("BBB");
		IPlace p3 = net.createPlace("CCC");
		IPlace p4 = net.createPlace("DDD");
		
		p2.setMark(6);
		
		ITransition t1 = net.createTransition("ttt");
		ITransition t2 = net.createTransition("uuu");
		IArc a4 = net.createArc("dt", p2, t2);
		a4.setMark(5);
		
		IArc a1 = net.createArc("at", p1, t1);
		a1.setMark(2);
		
		IArc a5 = net.createArc("at", t2, p4);
		a1.setMark(2);
		
		IArc a2 = net.createArc("bt", t1, p2);
		IArc a3 = net.createArc("ct", t1, p3);
		a3.setMark(3);
		
		for (IArc a : net.getAllArcs()) {
			System.out.println("Arc ID: " + a.getId() + " Name: " + a.getName());
		}
		
		IPre pre = net.getPre();
		System.out.println(pre);
		
		IPost post = net.getPost();
		System.out.println(post);
		
//		net.deleteTransitionByID(2);
//		net.deletePlaceById(2);
		
		IPre pre1 = net.getPre();
		System.out.println(pre1);
		
		net.fire();
		
		IPost post1 = net.getPost();
		System.out.println(post1);
		for (IArc a : net.getAllArcs()) {
			System.out.println("Arc ID: " + a.getId() + " Name: " + a.getName());
		}
		
		System.out.println(net.getActivatedTransitions());
	}

}
