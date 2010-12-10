/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.Petrinet;
import transformation.RuleTest;

/**
 *
 * @author Niklas
 */
public class MorphismTest {

    public static IPetrinet petrinet;

    public MorphismTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        petrinet = new Petrinet();
        IPlace p1 = petrinet.createPlace("Wecker Ein");
        IPlace p2 = petrinet.createPlace("Wecker");
        IPlace p3 = petrinet.createPlace("");
        IPlace p4 = petrinet.createPlace("Wecker Aus");
        IPlace p5 = petrinet.createPlace("Aufstehen");
        p5.setMark(1);
        IPlace p6 = petrinet.createPlace("Wecker Ein");
        IPlace p7 = petrinet.createPlace("Wecker Aus");
        IPlace p8 = petrinet.createPlace("");
        IPlace p9 = petrinet.createPlace("Badezimmer");
        IPlace p10 = petrinet.createPlace("Küche");
        ITransition t1 = petrinet.createTransition("", null);
        IArc arc = petrinet.createArc("");
        arc.setStart(p1);
        arc.setEnd(t1);
        arc = petrinet.createArc("");
        arc.setStart(t1);
        arc.setEnd(p2);
        ITransition t2 = petrinet.createTransition("snooze", null);
        arc = petrinet.createArc("");
        arc.setStart(p3);
        arc.setEnd(t2);
        arc = petrinet.createArc("");
        arc.setStart(t2);
        arc.setEnd(p2);
        ITransition t3 = petrinet.createTransition("klingelt", null);
        arc = petrinet.createArc("");
        arc.setStart(p2);
        arc.setEnd(t3);
        arc = petrinet.createArc("");
        arc.setStart(t3);
        arc.setEnd(p3);
        ITransition t4 = petrinet.createTransition("aus", null);
        arc = petrinet.createArc("");
        arc.setStart(p3);
        arc.setEnd(t4);
        arc = petrinet.createArc("");
        arc.setStart(t4);
        arc.setEnd(p4);
        ITransition t5 = petrinet.createTransition("Mit Wecker", null);
        arc = petrinet.createArc("");
        arc.setStart(p5);
        arc.setEnd(t5);
        arc = petrinet.createArc("");
        arc.setStart(t5);
        arc.setEnd(p6);
        ITransition t6 = petrinet.createTransition("Von Alleine", null);
        arc = petrinet.createArc("");
        arc.setStart(p5);
        arc.setEnd(t6);
        arc = petrinet.createArc("");
        arc.setStart(t6);
        arc.setEnd(p8);
        ITransition t7 = petrinet.createTransition("", null);
        arc = petrinet.createArc("");
        arc.setStart(p7);
        arc.setEnd(t7);
        arc = petrinet.createArc("");
        arc.setStart(t7);
        arc.setEnd(p8);
        ITransition t8 = petrinet.createTransition("", null);
        arc = petrinet.createArc("");
        arc.setStart(p8);
        arc.setEnd(t8);
        arc = petrinet.createArc("");
        arc.setStart(t8);
        arc.setEnd(p9);
        ITransition t9 = petrinet.createTransition("", null);
        arc = petrinet.createArc("");
        arc.setStart(p8);
        arc.setEnd(t9);
        arc = petrinet.createArc("");
        arc.setStart(t9);
        arc.setEnd(p10);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of IsValid method, of class Morphism.
     */
    @Test
    public void testIsValid() {
        System.out.println("IsValid");
        Morphism instance = null;
        boolean expResult = false;
        boolean result = instance.IsValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edges method, of class Morphism.
     */
    @Test
    public void testEdges() {
        System.out.println("edges");
        Morphism instance = null;
        Map expResult = null;
        Map result = instance.edges();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morph method, of class Morphism.
     */
    @Test
    public void testMorph_ITransition() {
        System.out.println("morph");
        ITransition transition = null;
        Morphism instance = null;
        ITransition expResult = null;
        ITransition result = instance.morph(transition);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morph method, of class Morphism.
     */
    @Test
    public void testMorph_IPlace() {
        System.out.println("morph");
        IPlace place = null;
        Morphism instance = null;
        IPlace expResult = null;
        IPlace result = instance.morph(place);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morph method, of class Morphism.
     */
    @Test
    public void testMorph_IArc() {
        System.out.println("morph");
        IArc arc = null;
        Morphism instance = null;
        IArc expResult = null;
        IArc result = instance.morph(arc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of places method, of class Morphism.
     */
    @Test
    public void testPlaces() {
        System.out.println("places");
        Morphism instance = null;
        Map expResult = null;
        Map result = instance.places();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transitions method, of class Morphism.
     */
    @Test
    public void testTransitions() {
        System.out.println("transitions");
        Morphism instance = null;
        Map expResult = null;
        Map result = instance.transitions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of From method, of class Morphism.
     */
    @Test
    public void testFrom() {
        System.out.println("From");
        Morphism instance = null;
        IPetrinet expResult = null;
        IPetrinet result = instance.From();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of To method, of class Morphism.
     */
    @Test
    public void testTo() {
        System.out.println("To");
        Morphism instance = null;
        IPetrinet expResult = null;
        IPetrinet result = instance.To();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}