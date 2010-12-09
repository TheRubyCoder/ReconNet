/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import engine.impl.mock.Place;
import java.util.Set;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import petrinetze.IArc;

import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.RenewCount;

/**
 *
 * @author Niklas, Philipp K�hn
 */
public class RuleTest {

    public static IRule rule1;

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
        rule1 = new Rule();
        //L von r1
        IPlace p1 = rule1.K().createPlace("Wecker ein");
        rule1.fromKtoL(p1);
        IPlace p2 = rule1.K().createPlace("Wecker ein");
        p2.setMark(1);
        rule1.fromKtoL(p2);
        rule1.fromKtoR(p2);
        IPlace p3 = rule1.K().createPlace("");
        rule1.fromKtoL(p3);
        rule1.fromKtoR(p3);
        IPlace p4 = rule1.K().createPlace("");
        rule1.fromKtoL(p4);
        rule1.fromKtoR(p4);
        IPlace p5 = rule1.K().createPlace("Wecker aus");
        rule1.fromKtoL(p5);
        rule1.fromKtoR(p5);
        IPlace p6 = rule1.K().createPlace("Wecker aus");
        rule1.fromKtoL(p6);
        ITransition t1 = rule1.K().createTransition("", new RenewCount());
        rule1.fromKtoL(t1);
        IArc arc = rule1.K().createArc("");
        arc.setStart(p1);
        arc.setEnd(t1);
        rule1.fromKtoL(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t1);
        arc.setEnd(p4);
        rule1.fromKtoL(arc);
        ITransition t2 = rule1.K().createTransition("", new RenewCount());
        rule1.fromKtoL(t2);
        arc = rule1.K().createArc("");
        arc.setStart(p3);
        arc.setEnd(t2);
        rule1.fromKtoL(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t2);
        arc.setEnd(p6);
        rule1.fromKtoL(arc);
        ITransition t3 = rule1.K().createTransition("", new RenewCount());
        rule1.fromKtoR(t3);
        arc = rule1.K().createArc("");
        arc.setStart(p2);
        arc.setEnd(t3);
        rule1.fromKtoR(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t3);
        arc.setEnd(p4);
        rule1.fromKtoR(arc);
        ITransition t4 = rule1.K().createTransition("", new RenewCount());
        rule1.fromKtoR(t4);
        arc = rule1.K().createArc("");
        arc.setStart(p3);
        arc.setEnd(t4);
        rule1.fromKtoR(arc);
        arc = rule1.K().createArc("");
        arc.setStart(t4);
        arc.setEnd(p5);
        rule1.fromKtoR(arc);
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

    @Test
    public void testPlaces(){
        System.out.println("Places");
        Set<IPlace> k = rule1.K().getAllPlaces();
        Set<IPlace> l = rule1.L().getAllPlaces();
        Set<IPlace> r = rule1.R().getAllPlaces();
        for(IPlace place : r){
            assertTrue(k.contains(place));
        }
        for(IPlace place : l){
            assertTrue(k.contains(place));
        }
    }

    @Test
    public void testArcs(){
        System.out.println("Arcs");
        Set<IArc> k = rule1.K().getAllArcs();
        Set<IArc> l = rule1.L().getAllArcs();
        Set<IArc> r = rule1.R().getAllArcs();
        for(IArc place : r){
            assertTrue(k.contains(place));
        }
        for(IArc place : l){
            assertTrue(k.contains(place));
        }
    }

    @Test
    public void testTransitions(){
        System.out.println("Transitions");
        Set<ITransition> k = rule1.K().getAllTransitions();
        Set<ITransition> l = rule1.L().getAllTransitions();
        Set<ITransition> r = rule1.R().getAllTransitions();
        for(ITransition place : r){
            assertTrue(k.contains(place));
        }
        for(ITransition place : l){
            assertTrue(k.contains(place));
        }
    }
}