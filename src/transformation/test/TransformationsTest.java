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
public class TransformationsTest {

    public TransformationsTest() {
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
     * Test of join method, of class Transformations.
     */
    @Test
    public void testJoin_3args_1() {
        System.out.println("join");
        IPetrinet left = null;
        IPetrinet right = null;
        ITransformation transformation = null;
        Transformations.join(left, right, transformation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of join method, of class Transformations.
     */
    @Test
    public void testJoin_4args() {
        System.out.println("join");
        IPetrinet left = null;
        IPetrinet right = null;
        IMorphism morphism = null;
        IRule rule = null;
        Transformations.join(left, right, morphism, rule);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of join method, of class Transformations.
     */
    @Test
    public void testJoin_3args_2() {
        System.out.println("join");
        IPetrinet left = null;
        IPetrinet right = null;
        IRule rule = null;
        Transformations.join(left, right, rule);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class Transformations.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        IPetrinet net = null;
        IRule rule = null;
        Transformations instance = null;
        instance.transform(net, rule);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}