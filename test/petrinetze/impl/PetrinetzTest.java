package petrinetze.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

import petrinetze.*;

/**
 *
 * @author Philipp Kuehn
 *
 */
public class PetrinetzTest {

    private IPetrinet p;

    private IPlace place1;

    private IPlace place2;

    private ITransition transition;

	private SimpleListener listener;

	@Before
	public void setUp() throws Exception
	{
		p = new Petrinet();
		listener = new SimpleListener();
		p.addPetrinetListener(listener);

        place1 = p.createPlace("place1");
        place2 = p.createPlace("place2");

        transition = p.createTransition("transition", Renews.COUNT);

        p.createArc("p1t", place1, transition);
        p.createArc("tp2", transition, place2);
	}
	
	@Test
	public void createPlace()
	{
		assertEquals(place1.getName(), "place1");
		assertTrue(p.getAllPlaces().contains(place1));
		assertTrue(p.getAllGraphElement().getAllNodes().contains(place1));
		assertTrue(listener.AddedNodes.contains(place1));

		assertEquals(place2.getName(), "place2");
		assertTrue(p.getAllPlaces().contains(place2));
		assertTrue(p.getAllGraphElement().getAllNodes().contains(place2));
		assertTrue(listener.AddedNodes.contains(place2));
	}
	
	@Test
	public void createTransition()
	{
		assertEquals(transition.getName(), "transition");
		assertTrue(p.getAllTransitions().contains(transition));
		assertTrue(p.getAllGraphElement().getAllNodes().contains(transition));
		assertEquals(Renews.COUNT, transition.getRnw());
		assertTrue(listener.AddedNodes.contains(transition));
	}
	
	@Test
	public void createArc()
	{
        ITransition transition = p.createTransition("t");
		IArc edge1 = p.createArc("edge1", place1, transition);
		assertEquals("edge1", edge1.getName());
		assertTrue(p.getAllArcs().contains(edge1));
		assertTrue(p.getAllGraphElement().getAllArcs().contains(edge1));
		assertEquals(place1, edge1.getStart());
		assertEquals(transition, edge1.getEnd());
		assertEquals(1, edge1.getMark());
		assertTrue(listener.AddedEdges.contains(edge1));

		IArc edge2 = p.createArc("edge2", transition, place2);
		assertEquals("edge2", edge2.getName());
		assertTrue(p.getAllArcs().contains(edge2));
		assertTrue(p.getAllGraphElement().getAllArcs().contains(edge2));
		assertEquals(transition, edge2.getStart());
		assertEquals(place2, edge2.getEnd());
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
        final IPetrinet petrinet = new Petrinet();

		IPlace p = petrinet.createPlace("p");
        p.setMark(1);
		ITransition a = petrinet.createTransition("a", Renews.COUNT);
		ITransition b = petrinet.createTransition("b", Renews.COUNT);
		petrinet.createArc("pa", p, a);
		petrinet.createArc("pb", p, b);
		petrinet.createArc("ap", a, p);
		petrinet.createArc("bp", b, p);

		int times = 100000000;
		for(int i = 0; i < times; i++)
			petrinet.fire();
		
		int distance = Math.abs(Integer.parseInt(a.getTlb()) - Integer.parseInt(b.getTlb()));
		System.out.println(a.getTlb());
		System.out.println(b.getTlb());
		assertTrue("Variation must not be greater than 10%", distance < times / 10);
	}
}


