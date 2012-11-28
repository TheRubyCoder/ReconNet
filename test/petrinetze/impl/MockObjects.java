package petrinetze.impl;

import java.util.HashMap;

import petrinet.PetrinetComponent;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import petrinet.model.rnw.Identity;
import petrinet.model.rnw.Mapping;
/**
 * @image html Isomorphism_placesneu_small.png so macht man ein bild d^-^b
 **/
public class MockObjects {
	
	//graphs from Isomorphism_transitions_neu.png TODO: change this comment!
	public static final Mapping NEXT=new Mapping(new HashMap<String,String>(){{
		put("yellow", "green");
		put("green", "yellow");
	}});
	
	public static final Identity ID=new Identity();
	
	public static Petrinet getPetrinet01(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.addPlace("P1");
		Place p2=petri.addPlace("P2");
		Place p3=petri.addPlace("P3");
		
		
		
		Transition a=petri.addTransition("A",ID);
		petri.addPreArc("p1-a",p1,a);
		petri.addPostArc("a-p2",a,p2);
		petri.addPostArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	public static Petrinet getPetrinet02(){
		Petrinet petri=getPetrinet01();
		
		Place p4 = petri.addPlace("P4");
		//works only with ONE TRANSITION at Petri01
		petri.addPostArc("a-p4", petri.getTransitions().iterator().next(), p4);
		
		return petri;
	}
	
	public static Petrinet getPetrinet03(){
		Petrinet petri=getPetrinet01();
		
		Place p4 = petri.addPlace("P4");
		//works only with ONE TRANSITION at Petri01
		petri.addPreArc("p4-a", p4, petri.getTransitions().iterator().next());
		
		return petri;
	}

	public static Petrinet getPetrinet04(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.addPlace("P1");
		Place p2=petri.addPlace("P2");
		Place p3=petri.addPlace("P3");
		
		
		
		Transition a=petri.addTransition("A",NEXT);
		a.setTlb("yellow");
		petri.addPreArc("p1-a",p1,a);
		petri.addPostArc("a-p2",a,p2);
		petri.addPostArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	
	public static Petrinet getPetrinet05(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.addPlace("P1");
		Place p2=petri.addPlace("P2");
		Place p3=petri.addPlace("P3");
		
		
		
		Transition a=petri.addTransition("A",NEXT);
		a.setTlb("green");
		petri.addPreArc("p1-a",p1,a);
		petri.addPostArc("a-p2",a,p2);
		petri.addPostArc("a-p3",a,p3);
		
		
		return petri;
	}
	

	public static Petrinet getPetrinet06(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.addPlace("P1");
		Place p2=petri.addPlace("P2");
		Place p3=petri.addPlace("P3");
		
		
		
		Transition a=petri.addTransition("B",ID);
		a.setTlb("yellow");
		petri.addPreArc("p1-a",p1,a);
		petri.addPostArc("a-p2",a,p2);
		petri.addPostArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	public static Petrinet getPetrinet07(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.addPlace("P1");
		Place p2=petri.addPlace("P2");

		
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		petri.addPreArc("p1-a",p1,a);
		petri.addPostArc("a-p2",a,p2);
		
		
		return petri;
	}
	
	public static Petrinet getPetrinet08(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p2=petri.addPlace("P2");
		Place p3=petri.addPlace("P3");
		
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		petri.addPostArc("a-p2",a,p2);
		petri.addPostArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	
	//graphs from Isomorphism_places_neu.png TODO: change this comment!
	
	public static Petrinet getPetrinet10(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		petri.addPostArc("a-p1",a,p1);
		petri.addPostArc("b-p1",b,p1);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);
		petri.addPreArc("p1-e",p1,e);
		
		return petri;
	}
	
	
	
	public static Petrinet getPetrinet11(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(1);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		petri.addPostArc("a-p1",a,p1);
		petri.addPostArc("b-p1",b,p1);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);
		petri.addPreArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet11a(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(3);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		petri.addPostArc("a-p1",a,p1);
		petri.addPostArc("b-p1",b,p1);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);
		petri.addPreArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet12(){
		Petrinet petri = PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		petri.addPostArc("a-p1",a,p1);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);
		petri.addPreArc("p1-e",p1,e);
		
		return petri;
	}
	
	
	public static Petrinet getPetrinet13(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");

		
		petri.addPostArc("a-p1",a,p1);
		petri.addPostArc("b-p1",b,p1);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);

		
		return petri;
	}
	
	public static Petrinet getPetrinet14(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition ba=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		petri.addPostArc("a-p1",a,p1);
		petri.addPostArc("b-p1",b,p1);
		petri.addPostArc("ba-p1",ba,p1);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);
		petri.addPreArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet15(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.addPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition ba=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.addTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.addTransition("A",ID);
		a.setTlb("yellow");
		
		petri.addPostArc("a-p1",a,p1);
		petri.addPostArc("b-p1",b,p1);
		petri.addPreArc("p1-ba",p1,ba);
		petri.addPreArc("p1-c",p1,c);
		petri.addPreArc("p1-d",p1,d);
		petri.addPreArc("p1-e",p1,e);
		
		return petri;
	}
}

