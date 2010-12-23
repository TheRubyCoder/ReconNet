package petrinetze.impl;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IPost;
import petrinetze.IPre;
import petrinetze.ITransition;

public class PetrinetIntegTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPetrinet net = new Petrinet();
		IPlace p1 = net.createPlace("AAA");
		IPlace p2 = net.createPlace("BBB");
		IPlace p3 = net.createPlace("CCC");
		
		ITransition t1 = net.createTransition("ttt");
		ITransition t2 = net.createTransition("uuu");
		IArc a4 = net.createArc("dt", p2, t2);
		a4.setMark(5);
		
		IArc a1 = net.createArc("at", p1, t1);
		a1.setMark(2);
		
		IArc a2 = net.createArc("bt", t1, p2);
		IArc a3 = net.createArc("ct", t1, p3);
		a3.setMark(3);
		
		IPre pre = net.getPre();
		System.out.println(pre);
		
		IPost post = net.getPost();
		System.out.println(post);
		
	}

}
