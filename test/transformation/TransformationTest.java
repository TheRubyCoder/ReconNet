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
import petrinetze.IPetrinet;

/**
 *
 * @author Niklas
 */
public class TransformationTest {

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
    }

    @After
    public void tearDown() {
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
     * Test of rule method, of class Transformation.
     */
    @Test
    public void testRule() {
        System.out.println("rule");
        Transformation instance = null;
        IRule expResult = null;
        IRule result = instance.rule();
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