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
import transformation.IMorphism;
import transformation.IRule;
import transformation.ITransformation;

/**
 *
 * @author Niklas
 */
public class ITransformationTest {

    public ITransformationTest() {
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
     * Test of rule method, of class ITransformation.
     */
    @Test
    public void testRule() {
        System.out.println("rule");
        System.out.println("Test auskommentiert: testRule() in ITransformationsTest.java:40");
        
        
//        ITransformation instance = new ITransformationImpl();
//        IRule expResult = null;
//        IRule result = instance.rule();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of morphism method, of class ITransformation.
     */
    @Test
    public void testMorphism() {
        System.out.println("morphism");
        System.out.println("Test auskommentiert: testMorphism() in ITransformationsTest.java:67");
        
//        ITransformation instance = new ITransformationImpl();
//        IMorphism expResult = null;
//        IMorphism result = instance.morphism();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of N method, of class ITransformation.
     */
    @Test
    public void testN() {
        System.out.println("N");
        System.out.println("Test auskommentiert: testN() in ITransformationsTest.java:80");
//        ITransformation instance = new ITransformationImpl();
//        IPetrinet expResult = null;
//        IPetrinet result = instance.N();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class ITransformation.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        System.out.println("Test auskommentiert: testTransform() in ITransformationsTest.java:95");
        
        
//        ITransformation instance = new ITransformationImpl();
//        instance.transform();
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    public static class ITransformationImpl implements ITransformation {

        public IRule getRule() {
            return null;
        }

        public IMorphism getMorphism() {
            return null;
        }

        public IPetrinet getPetrinet() {
            return null;
        }

        public void transform() {
        }
    }

}