package data;

import java.util.HashSet;
import java.util.Set;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.ITransition;
import petrinetze.Petrinet;
import petrinetze.RenewCount;
import petrinetze.RenewId;


/**
 * This Class contains the test data for morphism test. 
 * DONT CHANGE THE ORDER IN WICH PLACES OR TRANSITIONS ARE CREATED! (as Ids are used in the tests)
 * (better dont change anything at all until you know the test very well) 
 * 
 */
public class MorphismData {
	

	public static int getIdMatchesInRule2(){
		return idOfThird;
	}
	
	public static int getIdFromTransitions() {
		return idFromTransitions;
	}

	public static int getIdFromPlaces() {
		return idFromPlaces;
	}

	public static int getIdMatchedTransition() {
		return idMatchedTransition;
	}

	public static Set<Integer> getIdsMatchedPlaces() {
		return idsMatchedPlaces;
	}

	private static int idFromTransitions;

	private static int idFromPlaces;
	
	private static int idMatchedTransition;
	
	private static Set<Integer> idsMatchedPlaces = new HashSet<Integer>();
	
	private static int idOfThird;
	
	
	private MorphismData(){}
	
	/**
	 * 
	 * @return the "from" Petrinet that is specified in... 
 	 */
	public static IPetrinet getPetrinetIsomorphismPlacesFrom(){
		IPetrinet result = new Petrinet();
		IRenew renewId = new RenewId();
		
		IPlace p1 = result.createPlace("P1");
		
		idFromPlaces = p1.getId();
		
		ITransition t1 = result.createTransition("A", renewId);
		ITransition t2 = result.createTransition("A", renewId);
		ITransition t3 = result.createTransition("A", renewId);
		ITransition t4 = result.createTransition("A", renewId);
		ITransition t5 = result.createTransition("A", renewId);
		
		// pre
		result.createArc("", t1, p1);
		result.createArc("", t2, p1);
		
		//post
		result.createArc("", p1, t3);
		result.createArc("", p1, t4);
		result.createArc("", p1, t5);
		
		//mark
		p1.setMark(2);
		
		return result;
	}
	
	
	/**
	 * 
	 * @return the "to" Petrinet that is specified in... 
 	 */
	public static IPetrinet getPetrinetIsomorphismPlacesTo(){
		// The matching subnet P1 and T1...
		IPetrinet result = new Petrinet();
		IRenew renewId = new RenewId();
		
		IPlace p1 = result.createPlace("P1");
		
		idsMatchedPlaces.add(p1.getId());
		
		ITransition t11 = result.createTransition("A", renewId);
		ITransition t12 = result.createTransition("A", renewId);
		ITransition t13 = result.createTransition("A", renewId);
		ITransition t14 = result.createTransition("A", renewId);
		ITransition t15 = result.createTransition("A", renewId);
		
		// pre
		result.createArc("", t11, p1);
		result.createArc("", t12, p1);
		
		//post
		result.createArc("", p1, t13);
		result.createArc("", p1, t14);
		result.createArc("", p1, t15);
		
		//mark
		p1.setMark(2);
		
		
		
		// The not matching subnet mark not enough  
		// with following int 2
		IPlace p2 = result.createPlace("P1");
				
		ITransition t21 = result.createTransition("A", renewId);
		ITransition t22 = result.createTransition("A", renewId);
		ITransition t23 = result.createTransition("A", renewId);
		ITransition t24 = result.createTransition("A", renewId);
		ITransition t25 = result.createTransition("A", renewId);
				
		// pre
		result.createArc("", t21, p2);
		result.createArc("", t22, p2);
				
		//post
		result.createArc("", p2, t23);
		result.createArc("", p2, t24);
		result.createArc("", p2, t25);
				
		//mark
		p2.setMark(1);
		
		
		// The matching subnet mark is 1 more  
		// with following int 3
		IPlace p3 = result.createPlace("P1");
		idsMatchedPlaces.add(p3.getId());
		idOfThird = p3.getId();

				
		ITransition t31 = result.createTransition("A", renewId);
		ITransition t32 = result.createTransition("A", renewId);
		ITransition t33 = result.createTransition("A", renewId);
		ITransition t34 = result.createTransition("A", renewId);
		ITransition t35 = result.createTransition("A", renewId);
				
		// pre
		result.createArc("", t31, p3);
		result.createArc("", t32, p3);
				
		//post
		result.createArc("", p3, t33);
		result.createArc("", p3, t34);
		result.createArc("", p3, t35);
				
		//mark
		p3.setMark(3);
		
		
		// The not matching subnet pre is not enough  
		// with following int 4
		IPlace p4 = result.createPlace("P1");
						
		ITransition t41 = result.createTransition("A", renewId);
		ITransition t43 = result.createTransition("A", renewId);
		ITransition t44 = result.createTransition("A", renewId);
		ITransition t45 = result.createTransition("A", renewId);
						
		// pre
		result.createArc("", t41, p4);
						
		//post
		result.createArc("", p4, t43);
		result.createArc("", p4, t44);
		result.createArc("", p4, t45);
						
		//mark
		p4.setMark(2);
		
		
		// The not matching subnet post is not enough  
		// with following int 5
		IPlace p5 = result.createPlace("P1");
				
		ITransition t51 = result.createTransition("A", renewId);
		ITransition t52 = result.createTransition("A", renewId);
		ITransition t54 = result.createTransition("A", renewId);
		ITransition t55 = result.createTransition("A", renewId);
				
		// pre
		result.createArc("", t51, p5);
		result.createArc("", t52, p5);
				
		//post
		result.createArc("", p5, t54);
		result.createArc("", p5, t55);
				
		//mark
		p5.setMark(2);
		
		
		// The matching subnet pre is to many  
		// with following int 6
		IPlace p6 = result.createPlace("P1");
		idsMatchedPlaces.add(p6.getId());

						
		ITransition t61 = result.createTransition("A", renewId);
		ITransition t62 = result.createTransition("A", renewId);
		ITransition t63 = result.createTransition("A", renewId);
		ITransition t64 = result.createTransition("A", renewId);
		ITransition t65 = result.createTransition("A", renewId);
		ITransition t66 = result.createTransition("A", renewId);
						
		// pre
		result.createArc("", t61, p6);
		result.createArc("", t62, p6);
		result.createArc("", t63, p6);
						
		//post
		result.createArc("", p6, t64);
		result.createArc("", p6, t65);
		result.createArc("", p6, t66);
						
		//mark
		p6.setMark(2);
		
		
		// The matching subnet post is to many  
		// with following int 7
		IPlace p7 = result.createPlace("P1");
		idsMatchedPlaces.add(p7.getId());

								
		ITransition t71 = result.createTransition("A", renewId);
		ITransition t72 = result.createTransition("A", renewId);
		ITransition t73 = result.createTransition("A", renewId);
		ITransition t74 = result.createTransition("A", renewId);
		ITransition t75 = result.createTransition("A", renewId);
		ITransition t76 = result.createTransition("A", renewId);
								
		// pre
		result.createArc("", t71, p7);
		result.createArc("", t72, p7);
								
		//post
		result.createArc("", p7, t73);
		result.createArc("", p7, t74);
		result.createArc("", p7, t75);
		result.createArc("", p7, t76);
								
		//mark
		p7.setMark(2);
		
		
		return result;
	}
	
	
	/**
	 * Returns the "from" petrinet specified in '../additional/images/Isomorphism_transitions.png'
	 */
	public static IPetrinet getPetrinetIsomorphismTransitionsFrom(){
		IPetrinet result = new Petrinet();
		
		IRenew rnwId = new RenewId();
		
		idFromTransitions = addSubnetToPetrinetLikeInMorphismTransition(result, "P1".split(" "), "P2 P3".split(" "), "A", rnwId, "1");
		
		return result;
	}

	
	/**
	 * Returns the "to" petrinet specified in '../additional/images/Isomorphism_transitions.png'
	 */
	public static IPetrinet getPetrinetIsomorphismTransitionsTo(){
		IPetrinet result = new Petrinet();
		
		IRenew rnwId = new RenewId();
		IRenew rnwCount = new RenewCount();
		
		String[] pre1 = {"P1"};
		String[] pre2 = {"P1"};
		String[] pre3 = {"P1"};
		String[] pre4 = {"P1"};
		String[] pre5 = {"P1"};
		String[] pre6 = {"P1", "P2"};
		String[] pre7 = {"P1"};
		String[] pre8 = {};
		
		
		String[] post1 = {"P2", "P3"};
		String[] post2 = {"P2", "P3"};
		String[] post3 = {"P2", "P3"};
		String[] post4 = {"P2", "P3"};
		String[] post5 = {"P2", "P3", "P4"};
		String[] post6 = {"P2", "P3"};
		String[] post7 = {"P2"};
		String[] post8 = {"P2", "P3"};
		
		String[] name = {"A", "A", "A", "B", "A", "A", "A", "A"};
		String[] tlb =  {"1", "1", "2", "1", "1", "1", "1", "1"};
		
		idMatchedTransition = addSubnetToPetrinetLikeInMorphismTransition(result,pre1,post1,name[0],rnwId,tlb[0]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre2,post2,name[1],rnwCount,tlb[1]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre3,post3,name[2],rnwId,tlb[2]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre4,post4,name[3],rnwId,tlb[3]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre5,post5,name[4],rnwId,tlb[4]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre6,post6,name[5],rnwId,tlb[5]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre7,post7,name[6],rnwId,tlb[6]);
		addSubnetToPetrinetLikeInMorphismTransition(result,pre8,post8,name[7],rnwId,tlb[7]);
		return result;
	}
	
	
	private static int addSubnetToPetrinetLikeInMorphismTransition(IPetrinet petrinet, 
			String[] pre, 
			String[] post, 
			String name, 
			IRenew renew, 
			String tlb){
		ITransition transition = petrinet.createTransition(name,renew);
		transition.setTlb(tlb);
		for (String string: pre) {
			IPlace place = petrinet.createPlace(string);
			petrinet.createArc("", place, transition);
		}
		for (String string : post) {
			IPlace place = petrinet.createPlace(string);
			petrinet.createArc("", transition, place);
		}
		return transition.getId();
	}
	
	
//	public static void main(String[] args){
//		IPetrinet from = MorphismData.getPetrinetIsomorphismPlacesFrom();
//		System.out.println(from.toString());
		
//		IPetrinet to = MorphismData.getPetrinetIsomorphismPlacesTo();
//		System.out.println(to.toString());
		
//	}
	

}
