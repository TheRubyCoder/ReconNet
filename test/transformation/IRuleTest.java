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

    /**
     * Test of fromLtoK method, of class IRule.
     */
    @Test
    public void testFromLtoK_INode() {
        System.out.println("fromLtoK");
        INode node = null;
        IRule instance = new IRuleImpl();
        INode expResult = null;
        INode result = instance.fromLtoK(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromLtoK method, of class IRule.
     */
    @Test
    public void testFromLtoK_IArc() {
        System.out.println("fromLtoK");
        IArc edge = null;
        IRule instance = new IRuleImpl();
        IArc expResult = null;
        IArc result = instance.fromLtoK(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromRtoK method, of class IRule.
     */
    @Test
    public void testFromRtoK_INode() {
        System.out.println("fromRtoK");
        INode node = null;
        IRule instance = new IRuleImpl();
        INode expResult = null;
        INode result = instance.fromRtoK(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromRtoK method, of class IRule.
     */
    @Test
    public void testFromRtoK_IArc() {
        System.out.println("fromRtoK");
        IArc edge = null;
        IRule instance = new IRuleImpl();
        IArc expResult = null;
        IArc result = instance.fromRtoK(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoL method, of class IRule.
     */
    @Test
    public void testFromKtoL_INode() {
        System.out.println("fromKtoL");
        INode node = null;
        IRule instance = new IRuleImpl();
        INode expResult = null;
        INode result = instance.fromKtoL(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoL method, of class IRule.
     */
    @Test
    public void testFromKtoL_IArc() {
        System.out.println("fromKtoL");
        IArc edge = null;
        IRule instance = new IRuleImpl();
        IArc expResult = null;
        IArc result = instance.fromKtoL(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoR method, of class IRule.
     */
    @Test
    public void testFromKtoR_INode() {
        System.out.println("fromKtoR");
        INode node = null;
        IRule instance = new IRuleImpl();
        INode expResult = null;
        INode result = instance.fromKtoR(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoR method, of class IRule.
     */
    @Test
    public void testFromKtoR_IArc() {
        System.out.println("fromKtoR");
        IArc edge = null;
        IRule instance = new IRuleImpl();
        IArc expResult = null;
        IArc result = instance.fromKtoR(edge);
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

        public INode fromLtoK(INode node) {
            return null;
        }

        public IArc fromLtoK(IArc edge) {
            return null;
        }

        public INode fromRtoK(INode node) {
            return null;
        }

        public IArc fromRtoK(IArc edge) {
            return null;
        }

        public INode fromKtoL(INode node) {
            return null;
        }

        public IArc fromKtoL(IArc edge) {
            return null;
        }

        public INode fromKtoR(INode node) {
            return null;
        }

        public IArc fromKtoR(IArc edge) {
            return null;
        }
    }

}