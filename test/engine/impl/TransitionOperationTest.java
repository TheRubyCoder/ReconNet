package engine.impl;

import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;

import petrinet.Arc;
import petrinet.INode;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Transition;
import petrinet.Petrinet;
import petrinet.Renews;
import petrinetze.impl.SimpleListener;

public class TransitionOperationTest extends TestCase {

	/**
	 * Das gebaute Netz
	 * 
	 * A --(y1)-- t1 --(x1)-- B
	 * |					  |
	 * ----(y2)-- t2 --(x2)----
	 * |					  |
	 * ----(y3)-- t3 --(x3)----
	 * |					  |
	 * ----(y4)-- t4 --(x4)---- 
	 */
	private Petrinet p;
	
	/**
	 * Das zweite Netz
	 * A --(y1)-- t1 --(x1)-- B --(y3)-- t3 --(x3)-- C
	 * |					|   |                    |
	 * ----(y2)-- t2 --(x2)--   --(y4)-- t4 --(x4)----
	 */
	private Petrinet p2;
	
    private Place place1;
    private Place place2;
    private Place place3;    
    private Place place4;
    private Place place5;
    
    private Transition transition1;
    private Transition transition2;    
    private Transition transition3;    
    private Transition transition4;    
    private Transition transition12;
    private Transition transition22;    
    private Transition transition32;    
    private Transition transition42;
    
    private Arc arc11;
    private Arc arc12;
    private Arc arc21;
    private Arc arc22;
    private Arc arc31;
    private Arc arc32;
    private Arc arc41;
    private Arc arc42;
    private Arc arc112;
    private Arc arc122;
    private Arc arc212;
    private Arc arc222;
    private Arc arc312;
    private Arc arc322;
    private Arc arc412;
    private Arc arc422;
    
    private SimpleListener listener;
	
	/**
	 * Erstellen eines Netzes
	 */
	@Before
	public void setUp() throws Exception {		
		p = PetrinetComponent.getPetrinet().createPetrinet();
		listener = new SimpleListener();
		p.addPetrinetListener(listener);
		
		p2 = PetrinetComponent.getPetrinet().createPetrinet();
		p2.addPetrinetListener(listener);

        place1 = p.createPlace("A");
        place2 = p.createPlace("B");
        place3 = p2.createPlace("A");
        place4 = p2.createPlace("B");
        place5 = p2.createPlace("C");

        transition1 = p.createTransition("t1", Renews.COUNT);
        transition2 = p.createTransition("t2", Renews.COUNT);
        transition3 = p.createTransition("t3", Renews.COUNT);
        transition4 = p.createTransition("t4", Renews.COUNT);
        
        transition12 = p2.createTransition("t1", Renews.COUNT);
        transition22 = p2.createTransition("t2", Renews.COUNT);
        transition32 = p2.createTransition("t3", Renews.COUNT);
        transition42 = p2.createTransition("t4", Renews.COUNT);

        arc11 = p.createArc("y1", place1, transition1);
        arc21 = p.createArc("y2", place1, transition2);
        arc31 = p.createArc("y3", place1, transition3);
        arc41 = p.createArc("y4", place1, transition4);
        
        arc112 = p2.createArc("y1", place3, transition12);
        arc212 = p2.createArc("y2", place3, transition22);
        arc312 = p2.createArc("y3", place4, transition32);
        arc412 = p2.createArc("y4", place4, transition42);
        
        arc12 = p.createArc("x1", transition1, place2);
        arc22 = p.createArc("x2", transition2, place2);
        arc32 = p.createArc("x3", transition3, place2);
        arc42 = p.createArc("x4", transition4, place2);

        arc122 = p2.createArc("x1", transition12, place4);
        arc222 = p2.createArc("x2", transition22, place4);
        arc322 = p2.createArc("x3", transition32, place5);
        arc422 = p2.createArc("x4", transition42, place5);
        
        place1.setMark(1);
	}
	
	/**
	 *  eine Transitionen nichtdeterministisch schalten
	 */
	public void testNotDeterministTransition(){
		try {
			this.setUp();
		} catch (Exception e) {
			System.out.println("bin raus: testNotDeterministTransition\n" + e);
		}
		
		Set<INode> nodeSet1 = p.fire();
		assertEquals(place2.getMark(), 1);
		
		/**
		 * TODO..: wenn kein mark gesetzt ist!!! knallt es einfach
		 */
	}
	
	/**
	 *  eine angegebene Transition schalten, falls moglich
	 */
	public void testSomeSelectedTransition(){
		try {
			this.setUp();
		} catch (Exception e) {
			System.out.println("bin raus: testSomeSelectedTransition\n" + e);
		}
		
		place1.setMark(1);
		Set<INode> nodeSet2 = p.fire(transition1.getId());
		assertEquals(place2.getMark(), 1);
		
//		erneutes schalten geht nicht weil die transitionen aktiv sein müssen!! TODO
		Set<INode> nodeSet3 = p.fire(transition1.getId());
		assertEquals(place2.getMark(), 1);
	}
	
	/**
	 *  mehrere Transitionen sequentiell nichtdeterministisch schalten
	 */
	public void testSomeSequentialTransition(){
		try {
			this.setUp();
		} catch (Exception e) {
			System.out.println("bin raus: testSomeSequentialTransition\n" + e);
		}
		
		System.out.println(p2.toString());
		
		place3.setMark(3);
		Set<INode> nodeSet3 = p2.fire();
		System.out.println("node: " + nodeSet3.toString());

		assertEquals(place3.getMark(), 2);
		assertEquals(place4.getMark(), 1);
		
		Set<INode> nodeSet4 = p2.fire();
		System.out.println("node: " + nodeSet4.toString());

		assertEquals(place5.getMark(), 1);
		assertEquals(place4.getMark(), 0);
	}
	
}
