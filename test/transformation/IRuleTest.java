/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import petrinetze.INode;
import petrinetze.ITransition;
import petrinetze.impl.RenewCount;

/**
 *
 * @author Niklas, Philipp Kühn
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
     * Test of fromLtoK method, of class IRule.
     */
    @Test
    public void testFromLtoK_INode() {
        System.out.println("fromLtoK");
        IRule instance = new Rule();
        INode node = instance.L().createPlace("a");
        assertEquals(node.getName(), instance.fromLtoK(node).getName());
    	assertEquals(node.getName(), instance.fromKtoL(instance.fromLtoK(node)).getName());
    }

    /**
     * Test of fromLtoK method, of class IRule.
     */
    @Test
    public void testFromLtoK_IArc() {
        System.out.println("fromLtoK");
        IRule instance = new Rule();
        ITransition node = instance.L().createTransition("a", new RenewCount());
        assertEquals(node.getName(), instance.fromLtoK(node).getName());
        assertEquals(node.getRnw(), ((ITransition)instance.fromLtoK(node)).getRnw());
    	assertEquals(node.getName(), instance.fromKtoL(instance.fromLtoK(node)).getName());
    	assertEquals(node.getRnw(), ((ITransition)instance.fromKtoL(instance.fromLtoK(node))).getRnw());
    }

    /**
     * Test of fromRtoK method, of class IRule.
     */
    @Test
    public void testFromRtoK_INode() {
        System.out.println("fromRtoK");
        IRule instance = new Rule();
        INode node = instance.R().createPlace("a");
        assertEquals(node.getName(), instance.fromRtoK(node).getName());
    	assertEquals(node.getName(), instance.fromKtoR(instance.fromRtoK(node)).getName());
    }

    /**
     * Test of fromRtoK method, of class IRule.
     */
    @Test
    public void testFromRtoK_IArc() {
        System.out.println("fromRtoK");
        IRule instance = new Rule();
        ITransition node = instance.R().createTransition("a", new RenewCount());
        assertEquals(node.getName(), instance.fromRtoK(node).getName());
        assertEquals(node.getRnw(), ((ITransition)instance.fromRtoK(node)).getRnw());
    	assertEquals(node.getName(), instance.fromKtoR(instance.fromRtoK(node)).getName());
    	assertEquals(node.getRnw(), ((ITransition)instance.fromKtoR(instance.fromRtoK(node))).getRnw());
    }
}