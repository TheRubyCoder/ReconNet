/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.Arc;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Transition;

/**
 *
 * @author Niklas
 */
public class MorphismTest {

	private static Petrinet fromPn;
    private static Petrinet toPn;
    
    private static Place[] from_p, to_p;
    private static Transition[] from_t, to_t;
    private static Arc[] from_a, to_a;
    
    private Morphism testObject;
    
    private static Map<Place, Place> expectedPlaceMap;
    private static Map<Transition, Transition> expectedTransitionMap;
    private static Map<Arc, Arc> expectedArcMap;

    
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    	setupPetrinetFrom();
    	setupPetrinetTo();
    	setupExpectedResults();
    	
    	testObject = MorphismFactory.createMorphism(fromPn, toPn);
    }

    @After
    public void tearDown() {
    }

    

    /**
     * Test of places method, of class Morphism.
     */
    @Test
    public void testPlaces() {
        assertEquals(expectedPlaceMap, testObject.getPlacesMorphism());
    }

    /**
     * Test of transitions method, of class Morphism.
     */
    @Test
    public void testTransitions() {
        assertEquals(expectedTransitionMap, testObject.getTransitionsMorphism());
    }

    /**
     * Test of edges method, of class Morphism.
     */
    @Test
    public void testEdges() {
        assertEquals(expectedArcMap, testObject.getEdgesMorphism());
    }

    /**
     * Test of morph method, of class Morphism.
     */
    @Test
    public void testMorph_Transition() {
    	for (Map.Entry<Transition, Transition> entry : expectedTransitionMap.entrySet()) {
    		assertEquals(entry.getValue(), testObject.getTransitionMorphism(entry.getKey()));
    	}
    }

    /**
     * Test of morph method, of class Morphism.
     */
    @Test
    public void testMorph_Place() {
    	for (Map.Entry<Place, Place> entry : expectedPlaceMap.entrySet()) {
    		assertEquals(entry.getValue(), testObject.getPlaceMorphism(entry.getKey()));
    	}
    }

    /**
     * Test of morph method, of class Morphism.
     */
    @Test
    public void testMorph_Arc() {
    	for (Map.Entry<Arc, Arc> entry : expectedArcMap.entrySet()) {
    		assertEquals(entry.getValue(), testObject.getArcMorphism(entry.getKey()));
    	}
    }

    /**
     * Test of From method, of class Morphism.
     */
    @Test
    public void testFrom() {
    	assertEquals(fromPn, testObject.getFrom());
    }

    /**
     * Test of To method, of class Morphism.
     */
    @Test
    public void testTo() {
        assertEquals(toPn, testObject.getTo());
    }
    
    
    private void setupPetrinetFrom() {
    	fromPn = PetrinetComponent.getPetrinet().createPetrinet();

    	from_p = new Place[6];
    	from_t = new Transition[2];
    	from_a = new Arc[4];

    	from_p[0] = fromPn.createPlace("Wecker Ein");
    	from_p[1] = fromPn.createPlace("");
    	from_p[2] = fromPn.createPlace("Wecker Ein");
    	from_p[2].setMark(1);
    	from_p[3] = fromPn.createPlace("Wecker Aus");
    	from_p[4] = fromPn.createPlace("");
    	from_p[5] = fromPn.createPlace("Wecker Aus");
    	
    	from_t[0] = fromPn.createTransition("");
    	from_t[1] = fromPn.createTransition("");
    	
    	from_a[0] = fromPn.createArc("", from_p[0], from_t[0]);
    	from_a[1] = fromPn.createArc("", from_t[0], from_p[1]);
    	from_a[2] = fromPn.createArc("",from_p[4], from_t[1]);
    	from_a[3] = fromPn.createArc("", from_t[1], from_p[5]);
    }

    
    private void setupPetrinetTo() {
    	toPn = PetrinetComponent.getPetrinet().createPetrinet();
    	
    	to_p = new Place[10];
    	to_t = new Transition[9];
    	to_a = new Arc[18];
    	
    	to_p[0] = toPn.createPlace("Wecker Ein");
    	to_p[1] = toPn.createPlace("Aufstehen");
    	to_p[1].setMark(1);
    	to_p[2] = toPn.createPlace("");
    	to_p[3] = toPn.createPlace("Wecker Ein");
    	to_p[3].setMark(1);
    	to_p[4] = toPn.createPlace("Wecker Aus");
    	to_p[5] = toPn.createPlace("");
    	to_p[6] = toPn.createPlace("Wecker Aus");
    	to_p[7] = toPn.createPlace("Badezimmer");
    	to_p[8] = toPn.createPlace("Küche");
    	to_p[9] = toPn.createPlace("");
    	
    	to_t[0] = toPn.createTransition("");
    	to_t[1] = toPn.createTransition("Mit Wecker");
    	to_t[2] = toPn.createTransition("Von Allein");
    	to_t[3] = toPn.createTransition("snooze");
    	to_t[4] = toPn.createTransition("klingelt");
    	to_t[5] = toPn.createTransition("");
    	to_t[6] = toPn.createTransition("");
    	to_t[7] = toPn.createTransition("");
    	to_t[8] = toPn.createTransition("");

    	to_a[0] = toPn.createArc("", to_p[0], to_t[0]);
    	to_a[1] = toPn.createArc("", to_t[0], to_p[2]);
    	to_a[2] = toPn.createArc("", to_p[2], to_t[4]);
    	to_a[3] = toPn.createArc("", to_t[4], to_p[5]);
    	to_a[4] = toPn.createArc("", to_p[5], to_t[3]);
    	to_a[5] = toPn.createArc("", to_t[3], to_p[2]);
    	to_a[6] = toPn.createArc("", to_p[5], to_t[5]);
    	to_a[7] = toPn.createArc("", to_t[5], to_p[6]);
    	to_a[8] = toPn.createArc("", to_p[1], to_t[1]);
    	to_a[9] = toPn.createArc("", to_t[1], to_p[3]);
    	to_a[10] = toPn.createArc("", to_p[1], to_t[2]);
    	to_a[11] = toPn.createArc("", to_t[2], to_p[9]);
    	to_a[12] = toPn.createArc("", to_p[4], to_t[8]);
    	to_a[13] = toPn.createArc("", to_t[8], to_p[9]);
    	to_a[14] = toPn.createArc("", to_p[9], to_t[6]);
    	to_a[15] = toPn.createArc("", to_t[6], to_p[7]);
    	to_a[16] = toPn.createArc("", to_p[9], to_t[7]);
    	to_a[17] = toPn.createArc("", to_t[7], to_p[8]);
    }
    
    
    private void setupExpectedResults() {
    	expectedPlaceMap = new HashMap<Place, Place>();
    	expectedTransitionMap = new HashMap<Transition, Transition>();
    	expectedArcMap = new HashMap<Arc, Arc>();
    	
    	expectedPlaceMap.put(from_p[0], to_p[0]);
    	expectedPlaceMap.put(from_p[1], to_p[2]);
    	expectedPlaceMap.put(from_p[2], to_p[3]);
    	expectedPlaceMap.put(from_p[3], to_p[4]);
    	expectedPlaceMap.put(from_p[4], to_p[5]);
    	expectedPlaceMap.put(from_p[5], to_p[6]);
    	
    	expectedTransitionMap.put(from_t[0], to_t[0]);
    	expectedTransitionMap.put(from_t[1], to_t[5]);
    	
    	expectedArcMap.put(from_a[0], to_a[0]);
    	expectedArcMap.put(from_a[1], to_a[1]);
    	expectedArcMap.put(from_a[2], to_a[6]);
    	expectedArcMap.put(from_a[3], to_a[7]);
    }
    
}