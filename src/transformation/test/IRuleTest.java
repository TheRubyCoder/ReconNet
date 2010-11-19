/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import petrinetze.IPetrinet;
import transformation.IRule;

/**
 *
 * @author Niklas
 */
public class IRuleTest {

    public IRuleTest() {
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
     * Test of L method, of class IRule.
     */
    @Test
    public void testL() {
        System.out.println("L");
        IRule instance = new IRuleImpl();
        IPetrinet expResult = null;
        IPetrinet result = instance.L();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of K method, of class IRule.
     */
    @Test
    public void testK() {
        System.out.println("K");
        IRule instance = new IRuleImpl();
        IPetrinet expResult = null;
        IPetrinet result = instance.K();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of R method, of class IRule.
     */
    @Test
    public void testR() {
        System.out.println("R");
        IRule instance = new IRuleImpl();
        IPetrinet expResult = null;
        IPetrinet result = instance.R();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IRuleImpl implements IRule {

        public IPetrinet L() {
            return null;
        }

        public IPetrinet K() {
            return null;
        }

        public IPetrinet R() {
            return null;
        }
    }

}