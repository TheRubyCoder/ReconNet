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
import transformation.RuleTest;

/**
 *
 * @author Niklas
 */
public class MorphismTest {

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