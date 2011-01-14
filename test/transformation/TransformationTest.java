/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.RenewCount;

/**
 *
 * @author Niklas
 */
public class TransformationTest {

    // private Rule rule1;

    public TransformationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        /*
        rule1 = new Rule();
        //L von r1
        IPlace p1 = rule1.K().createPlace("Wecker ein");
        rule1.fromKtoL(p1);
        IPlace p2 = rule1.K().createPlace("Wecker ein");
        p2.setMark(1);
        rule1.fromKtoL(p2);
        rule1.fromKtoR(p2);
        IPlace p3 = rule1.K().createPlace("");
        rule1.fromKtoL(p3);
        rule1.fromKtoR(p3);
        IPlace p4 = rule1.K().createPlace("");
        rule1.fromKtoL(p4);
        rule1.fromKtoR(p4);
        IPlace p5 = rule1.K().createPlace("Wecker aus");
        rule1.fromKtoL(p5);
        rule1.fromKtoR(p5);
        IPlace p6 = rule1.K().createPlace("Wecker aus");
        rule1.fromKtoL(p6);
        ITransition t1 = rule1.K().createTransition("", Renews.COUNT);
        rule1.fromKtoL(t1);
        IArc arc = rule1.K().createArc("");
        arc.setStart(p1);
        arc.setEnd(t1);
        rule1.fromKtoL(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t1);
        arc.setEnd(p4);
        rule1.fromKtoL(arc);
        ITransition t2 = rule1.K().createTransition("", Renews.COUNT);
        rule1.fromKtoL(t2);
        arc = rule1.K().createArc("");
        arc.setStart(p3);
        arc.setEnd(t2);
        rule1.fromKtoL(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t2);
        arc.setEnd(p6);
        rule1.fromKtoL(arc);
        ITransition t3 = rule1.K().createTransition("", Renews.COUNT);
        rule1.fromKtoR(t3);
        arc = rule1.K().createArc("");
        arc.setStart(p2);
        arc.setEnd(t3);
        rule1.fromKtoR(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t3);
        arc.setEnd(p4);
        rule1.fromKtoR(arc);
        ITransition t4 = rule1.K().createTransition("", Renews.COUNT);
        rule1.fromKtoR(t4);
        arc = rule1.K().createArc("");
        arc.setStart(p3);
        arc.setEnd(t4);
        rule1.fromKtoR(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t4);
        arc.setEnd(p5);
        rule1.fromKtoR(arc);
        */
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRule(){
        System.out.println("Test Rule");
    }

    /**
     * Test of N method, of class Transformation.
     */
    @Test
    public void testN() {
        System.out.println("N");
        Transformation instance = null;
        IPetrinet expResult = null;
        IPetrinet result = instance.N();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morphism method, of class Transformation.
     */
    @Test
    public void testMorphism() {
        System.out.println("morphism");
        Transformation instance = null;
        IMorphism expResult = null;
        IMorphism result = instance.morphism();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of transform method, of class Transformation.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        Transformation instance = null;
        instance.transform();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}