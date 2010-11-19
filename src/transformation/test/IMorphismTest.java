/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation.test;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import petrinetze.IArc;
import petrinetze.IPlace;
import petrinetze.ITransition;
import transformation.IMorphism;

/**
 *
 * @author Niklas
 */
public class IMorphismTest {

    public IMorphismTest() {
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
     * Test of transitions method, of class IMorphism.
     */
    @Test
    public void testTransitions() {
        System.out.println("transitions");
        IMorphism instance = new IMorphismImpl();
        Map expResult = null;
        Map result = instance.transitions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of places method, of class IMorphism.
     */
    @Test
    public void testPlaces() {
        System.out.println("places");
        IMorphism instance = new IMorphismImpl();
        Map expResult = null;
        Map result = instance.places();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edges method, of class IMorphism.
     */
    @Test
    public void testEdges() {
        System.out.println("edges");
        IMorphism instance = new IMorphismImpl();
        Map expResult = null;
        Map result = instance.edges();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morph method, of class IMorphism.
     */
    @Test
    public void testMorph_ITransition() {
        System.out.println("morph");
        ITransition transition = null;
        IMorphism instance = new IMorphismImpl();
        ITransition expResult = null;
        ITransition result = instance.morph(transition);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morph method, of class IMorphism.
     */
    @Test
    public void testMorph_IPlace() {
        System.out.println("morph");
        IPlace place = null;
        IMorphism instance = new IMorphismImpl();
        IPlace expResult = null;
        IPlace result = instance.morph(place);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of morph method, of class IMorphism.
     */
    @Test
    public void testMorph_IArc() {
        System.out.println("morph");
        IArc arc = null;
        IMorphism instance = new IMorphismImpl();
        IArc expResult = null;
        IArc result = instance.morph(arc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsValid method, of class IMorphism.
     */
    @Test
    public void testIsValid() {
        System.out.println("IsValid");
        IMorphism instance = new IMorphismImpl();
        boolean expResult = false;
        boolean result = instance.IsValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IMorphismImpl implements IMorphism {

        public Map<ITransition, ITransition> transitions() {
            return null;
        }

        public Map<IPlace, IPlace> places() {
            return null;
        }

        public Map<IArc, IArc> edges() {
            return null;
        }

        public ITransition morph(ITransition transition) {
            return null;
        }

        public IPlace morph(IPlace place) {
            return null;
        }

        public IArc morph(IArc arc) {
            return null;
        }

        public boolean IsValid() {
            return false;
        }
    }

}