/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Niklas
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({transformation.test.TransformationsTest.class,transformation.test.IMorphismTest.class,transformation.test.ITransformationTest.class,transformation.test.TransformationsTest.class,transformation.test.IRuleTest.class})
public class TransformationSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}