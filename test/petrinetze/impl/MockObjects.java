package petrinetze.impl;

import java.util.HashMap;

import petrinetze.IPlace;
import petrinetze.ITransition;

public class MockObjects {
	
	//graphs from Isomorphism_transitions_neu.png TODO: change this comment!
	public static final RenewMap NEXT=new RenewMap(new HashMap<String,String>(){{
		put("yellow", "green");
		put("green", "yellow");
	}});
	
	public static final RenewId ID=new RenewId();
	
	public static Petrinet getPetrinet01(){
		Petrinet petri =new Petrinet();
		IPlace p1=petri.createPlace("P1");
		IPlace p2=petri.createPlace("P2");
		IPlace p3=petri.createPlace("P3");
		
		
		
		ITransition a=petri.createTransition("A",ID);
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	public static Petrinet getPetrinet02(){
		Petrinet petri=getPetrinet01();
		
		IPlace p4 = petri.createPlace("P4");
		//works only with ONE TRANSITION at Petri01
		petri.createArc("a-p4", petri.getAllTransitions().iterator().next(), p4);
		
		return petri;
	}
	
	public static Petrinet getPetrinet03(){
		Petrinet petri=getPetrinet01();
		
		IPlace p4 = petri.createPlace("P4");
		//works only with ONE TRANSITION at Petri01
		petri.createArc("p4-a", p4, petri.getAllTransitions().iterator().next());
		
		return petri;
	}

	public static Petrinet getPetrinet04(){
		Petrinet petri =new Petrinet();
		IPlace p1=petri.createPlace("P1");
		IPlace p2=petri.createPlace("P2");
		IPlace p3=petri.createPlace("P3");
		
		
		
		ITransition a=petri.createTransition("A",NEXT);
		a.setTlb("yellow");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	
	public static Petrinet getPetrinet05(){
		Petrinet petri =new Petrinet();
		IPlace p1=petri.createPlace("P1");
		IPlace p2=petri.createPlace("P2");
		IPlace p3=petri.createPlace("P3");
		
		
		
		ITransition a=petri.createTransition("A",NEXT);
		a.setTlb("green");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	

	public static Petrinet getPetrinet06(){
		Petrinet petri =new Petrinet();
		IPlace p1=petri.createPlace("P1");
		IPlace p2=petri.createPlace("P2");
		IPlace p3=petri.createPlace("P3");
		
		
		
		ITransition a=petri.createTransition("B",ID);
		a.setTlb("yellow");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	public static Petrinet getPetrinet07(){
		Petrinet petri =new Petrinet();
		IPlace p1=petri.createPlace("P1");
		IPlace p2=petri.createPlace("P2");

		
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		petri.createArc("p1-a",p1,a);
		petri.createArc("a-p2",a,p2);
		
		
		return petri;
	}
	
	public static Petrinet getPetrinet08(){
		Petrinet petri =new Petrinet();

		IPlace p2=petri.createPlace("P2");
		IPlace p3=petri.createPlace("P3");
		
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		petri.createArc("a-p2",a,p2);
		petri.createArc("a-p3",a,p3);
		
		
		return petri;
	}
	
	
	
	//graphs from Isomorphism_places_neu.png TODO: change this comment!
	
	public static Petrinet getPetrinet10(){
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	
	
	public static Petrinet getPetrinet11(){
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(1);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet11a(){
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(3);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	public static Petrinet getPetrinet12(){
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition e=petri.createTransition("A",ID);
		a.setTlb("yellow");
		
		petri.createArc("a-p1",a,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);
		petri.createArc("p1-e",p1,e);
		
		return petri;
	}
	
	
	public static Petrinet getPetrinet13(){
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");

		
		petri.createArc("a-p1",a,p1);
		petri.createArc("b-p1",b,p1);
		petri.createArc("p1-c",p1,c);
		petri.createArc("p1-d",p1,d);

		
		return petri;
	}
	
	public static Petrinet getPetrinet14(){
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition ba=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition e=petri.createTransition("A",ID);
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
		Petrinet petri =new Petrinet();

		IPlace p1=petri.createPlace("P1");
		p1.setMark(2);
		
		
		ITransition a=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition b=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition ba=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition c=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition d=petri.createTransition("A",ID);
		a.setTlb("yellow");
		ITransition e=petri.createTransition("A",ID);
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

