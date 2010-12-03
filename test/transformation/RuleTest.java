/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RuleTest {

    public RuleTest() {
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
     * Test of finalize method, of class Rule.
     */
    @Test
    public void testFinalize() throws Exception {
        try {
            System.out.println("finalize");
            Rule instance = new Rule();
            instance.finalize();
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (Throwable ex) {
            Logger.getLogger(RuleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of K method, of class Rule.
     */
    @Test
    public void testK() {
        System.out.println("K");
        Rule instance = new Rule();
        IPetrinet expResult = null;
        IPetrinet result = instance.K();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of L method, of class Rule.
     */
    @Test
    public void testL() {
        System.out.println("L");
        Rule instance = new Rule();
        IPetrinet expResult = null;
        IPetrinet result = instance.L();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of R method, of class Rule.
     */
    @Test
    public void testR() {
        System.out.println("R");
        Rule instance = new Rule();
        IPetrinet expResult = null;
        IPetrinet result = instance.R();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoL method, of class Rule.
     */
    @Test
    public void testFromKtoL_INode() {
        System.out.println("fromKtoL");
        INode node = null;
        Rule instance = new Rule();
        INode expResult = null;
        INode result = instance.fromKtoL(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoL method, of class Rule.
     */
    @Test
    public void testFromKtoL_IArc() {
        System.out.println("fromKtoL");
        IArc edge = null;
        Rule instance = new Rule();
        IArc expResult = null;
        IArc result = instance.fromKtoL(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoR method, of class Rule.
     */
    @Test
    public void testFromKtoR_INode() {
        System.out.println("fromKtoR");
        INode node = null;
        Rule instance = new Rule();
        INode expResult = null;
        INode result = instance.fromKtoR(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromKtoR method, of class Rule.
     */
    @Test
    public void testFromKtoR_IArc() {
        System.out.println("fromKtoR");
        IArc edge = null;
        Rule instance = new Rule();
        IArc expResult = null;
        IArc result = instance.fromKtoR(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromLtoK method, of class Rule.
     */
    @Test
    public void testFromLtoK_INode() {
        System.out.println("fromLtoK");
        INode node = null;
        Rule instance = new Rule();
        INode expResult = null;
        INode result = instance.fromLtoK(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromLtoK method, of class Rule.
     */
    @Test
    public void testFromLtoK_IArc() {
        System.out.println("fromLtoK");
        IArc edge = null;
        Rule instance = new Rule();
        IArc expResult = null;
        IArc result = instance.fromLtoK(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromRtoK method, of class Rule.
     */
    @Test
    public void testFromRtoK_INode() {
        System.out.println("fromRtoK");
        INode node = null;
        Rule instance = new Rule();
        INode expResult = null;
        INode result = instance.fromRtoK(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromRtoK method, of class Rule.
     */
    @Test
    public void testFromRtoK_IArc() {
        System.out.println("fromRtoK");
        IArc edge = null;
        Rule instance = new Rule();
        IArc expResult = null;
        IArc result = instance.fromRtoK(edge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}