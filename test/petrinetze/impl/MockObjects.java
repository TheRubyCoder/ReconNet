/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

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

