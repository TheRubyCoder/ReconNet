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
        ITransformation instance = new ITransformationImpl();
        IRule expResult = null;
        IRule result = instance.rule();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of morphism method, of class ITransformation.
     */
    @Test
    public void testMorphism() {
        System.out.println("morphism");
        ITransformation instance = new ITransformationImpl();
        IMorphism expResult = null;
        IMorphism result = instance.morphism();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of N method, of class ITransformation.
     */
    @Test
    public void testN() {
        System.out.println("N");
        ITransformation instance = new ITransformationImpl();
        IPetrinet expResult = null;
        IPetrinet result = instance.N();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of transform method, of class ITransformation.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        ITransformation instance = new ITransformationImpl();
        instance.transform();
        // TODO review the generated test code and remove the default call to fail.
         
    }

    public class ITransformationImpl implements ITransformation {

        public IRule rule() {
            return null;
        }

        public IMorphism morphism() {
            return null;
        }

        public IPetrinet N() {
            return null;
        }

        public void transform() {
        }
    }

}