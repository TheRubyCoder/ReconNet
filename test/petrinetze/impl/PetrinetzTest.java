package petrinetze.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.*;
import petrinet.model.IArc;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;

public class PetrinetzTest {

    private Petrinet p;

    private Place place1;

    private Place place2;

    private Transition transition;

	private SimpleListener listener;

	@Before
	public void setUp() throws Exception
	{
		p = PetrinetComponent.getPetrinet().createPetrinet();
		listener = new SimpleListener();
		p.addPetrinetListener(listener);

        place1 = p.addPlace("place1");
        place2 = p.addPlace("place2");

        transition = p.addTransition("transition", Renews.COUNT);

        p.addPreArc("p1t", place1, transition);
        p.addPostArc("tp2", transition, place2);
	}
	
	@Test
	public void createPlace()
	{
		assertEquals(place1.getName(), "place1");
		assertTrue(p.getPlaces().contains(place1));
		assertTrue(p.getAllGraphElement().getAllNodes().contains(place1));
		assertTrue(listener.AddedNodes.contains(place1));

		assertEquals(place2.getName(), "place2");
		assertTrue(p.getPlaces().contains(place2));
		assertTrue(p.getAllGraphElement().getAllNodes().contains(place2));
		assertTrue(listener.AddedNodes.contains(place2));
	}
	
	@Test
	public void createTransition()
	{
		assertEquals(transition.getName(), "transition");
		assertTrue(p.getTransitions().contains(transition));
		assertTrue(p.getAllGraphElement().getAllNodes().contains(transition));
		assertEquals(Renews.COUNT, transition.getRnw());
		assertTrue(listener.AddedNodes.contains(transition));
	}
	
	@Test
	public void createArc()
	{
        Transition transition = p.addTransition("t");
		IArc edge1 = p.addPreArc("edge1", place1, transition);
		assertEquals("edge1", edge1.getName());
		assertTrue(p.getArcs().contains(edge1));
		assertTrue(p.getAllGraphElement().getAllArcs().contains(edge1));
		assertEquals(place1, edge1.getSource());
		assertEquals(transition, edge1.getTarget());
		assertEquals(1, edge1.getMark());
		assertTrue(listener.AddedEdges.contains(edge1));

		IArc edge2 = p.addPostArc("edge2", transition, place2);
		assertEquals("edge2", edge2.getName());
		assertTrue(p.getArcs().contains(edge2));
		assertTrue(p.getAllGraphElement().getAllArcs().contains(edge2));
		assertEquals(transition, edge2.getSource());
		assertEquals(place2, edge2.getTarget());
		assertEquals(1, edge2.getMark());
		assertTrue(listener.AddedEdges.contains(edge2));
	}
	
	@Test
	public void tokenFire()
	{
		place1.setMark(1);
		assertEquals(1, place1.getMark());
		assertEquals(0, place2.getMark());
		p.fire(transition.getId());
		assertEquals(0, place1.getMark());
		assertEquals(1, place2.getMark());
	}
	
	@Test
	public void randomTokenFire()
	{
        final Petrinet petrinet = PetrinetComponent.getPetrinet().createPetrinet();

		Place p = petrinet.addPlace("p");
        p.setMark(1);
		Transition a = petrinet.addTransition("a", Renews.COUNT);
		Transition b = petrinet.addTransition("b", Renews.COUNT);
		petrinet.addPreArc("pa", p, a);
		petrinet.addPreArc("pb", p, b);
		petrinet.addPostArc("ap", a, p);
		petrinet.addPostArc("bp", b, p);

//		int times = 100000000; // for serious testing
		int times = 1000; // for every day testing so jenkins does not take 5 minutes per build
		for(int i = 0; i < times; i++)
			petrinet.fire();
		
		int distance = Math.abs(Integer.parseInt(a.getTlb()) - Integer.parseInt(b.getTlb()));
		System.out.println(a.getTlb());
		System.out.println(b.getTlb());
		assertTrue("Variation must not be greater than 10%", distance < times / 10);
	}
}


