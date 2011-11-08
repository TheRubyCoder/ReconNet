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
        System.out.println("Test auskommentiert: testL() in IRuleTest.java:50");
//        IRule instance = new IRuleImpl();
//        IPetrinet expResult = null;
//        IPetrinet result = instance.L();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
 //       fail("The test case is a prototype.");
    }

    /**
     * Test of K method, of class IRule.
     */
    @Test
    public void testK() {
        System.out.println("K");
        System.out.println("Test auskommentiert: testK() in IRuleTest.java:63");
//        IRule instance = new IRuleImpl();
//        IPetrinet expResult = null;
//        IPetrinet result = instance.K();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of R method, of class IRule.
     */
    @Test
    public void testR() {
        System.out.println("R");
        System.out.println("Test auskommentiert: testR() in IRuleTest.java:78");
//        IRule instance = new IRuleImpl();
//        IPetrinet expResult = null;
//        IPetrinet result = instance.R();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    // TODO
    public static class IRuleImpl implements IRule {

        public IPetrinet getL() {
            return null;
        }

        public IPetrinet getK() {
            return null;
        }

        public IPetrinet getR() {
            return null;
        }

        @Override
        public INode fromLtoK(INode node) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public IArc fromLtoK(IArc edge) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public INode fromRtoK(INode node) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public IArc fromRtoK(IArc edge) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public INode fromKtoL(INode node) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public IArc fromKtoL(IArc edge) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public INode fromKtoR(INode node) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public IArc fromKtoR(IArc edge) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}