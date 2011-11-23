package petrinetze.impl;

import java.util.HashMap;

import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Transition;
import petrinet.Petrinet;
import petrinet.RenewId;
import petrinet.RenewMap;
/**
 * @image html Isomorphism_placesneu_small.png so macht man ein bild d^-^b
 **/
public class MockObjects {
	
	//graphs from Isomorphism_transitions_neu.png TODO: change this comment!
	public static final RenewMap NEXT=new RenewMap(new HashMap<String,String>(){{
		put("yellow", "green");
		put("green", "yellow");
	}});
	
	public static final RenewId ID=new RenewId();
	
	public static Petrinet getPetrinet01(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.createPlace("P1");
		Place p2=petri.createPlace("P2");
		Place p3=petri.createPlace("P3");
		
		
		
		Transition a=petri.createTransition("A",ID);
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	public static Petrinet getPetrinet02(){
		Petrinet petri=getPetrinet01();
		
		Place p4 = petri.createPlace("P4");
		//works only with ONE TRANSITION at Petri01
		petri.createArc("a-p4", petri.getAllTransitions().iterator().next(), p4);
		
		return petri;
	}
	
	public static Petrinet getPetrinet03(){
		Petrinet petri=getPetrinet01();
		
		Place p4 = petri.createPlace("P4");
		//works only with ONE TRANSITION at Petri01
		petri.createArc("p4-a", p4, petri.getAllTransitions().iterator().next());
		
		return petri;
	}

	public static Petrinet getPetrinet04(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.createPlace("P1");
		Place p2=petri.createPlace("P2");
		Place p3=petri.createPlace("P3");
		
		
		
		Transition a=petri.createTransition("A",NEXT);
		a.setTlb("yellow");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	
	public static Petrinet getPetrinet05(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.createPlace("P1");
		Place p2=petri.createPlace("P2");
		Place p3=petri.createPlace("P3");
		
		
		
		Transition a=petri.createTransition("A",NEXT);
		a.setTlb("green");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	

	public static Petrinet getPetrinet06(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.createPlace("P1");
		Place p2=petri.createPlace("P2");
		Place p3=petri.createPlace("P3");
		
		
		
		Transition a=petri.createTransition("B",ID);
		a.setTlb("yellow");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	public static Petrinet getPetrinet07(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();
		Place p1=petri.createPlace("P1");
		Place p2=petri.createPlace("P2");

		
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		
		
		return petri;
	}
	
	public static Petrinet getPetrinet08(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p2=petri.createPlace("P2");
		Place p3=petri.createPlace("P3");
		
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	
	//graphs from Isomorphism_places_neu.png TODO: change this comment!
	
	public static Petrinet getPetrinet10(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	
	
	public static Petrinet getPetrinet11(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(1);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet11a(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(3);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet12(){
		Petrinet petri = PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	
	public static Petrinet getPetrinet13(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");

		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);

		
		return petri;
	}
	
	public static Petrinet getPetrinet14(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition ba=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("ba-p1",ba,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet15(){
		Petrinet petri =PetrinetComponent.getPetrinet().createPetrinet();

		Place p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		Transition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition ba=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		Transition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-ba",p1,ba);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
}

