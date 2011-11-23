/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;


import java.util.Set;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.*;


/**
 *
 * @author Niklas, Philipp K�hn
 */
public class RuleTest {

    public Rule rule1;

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
        Place p1 = rule1.getL().createPlace("Wecker ein");
        Place p2 = rule1.getK().createPlace("Wecker ein");
        p2.setMark(1);
        Place p3 = rule1.getK().createPlace("");
        Place p4 = rule1.getK().createPlace("");
        Place p5 = rule1.getK().createPlace("Wecker aus");
        Place p6 = rule1.getL().createPlace("Wecker aus");
        Transition t1 = rule1.getL().createTransition("", Renews.COUNT);
        rule1.getL().createArc("", p1, t1);
        rule1.getL().createArc("", t1, rule1.fromKtoL(p4));
        Transition t2 = rule1.getL().createTransition("", Renews.COUNT);
        rule1.getL().createArc("", rule1.fromKtoL(p3), t2);
        rule1.getL().createArc("", t2, p6);
        Transition t3 = rule1.getR().createTransition("", Renews.COUNT);
        rule1.getR().createArc("", rule1.fromKtoR(p2), t3);
        rule1.getR().createArc("", t3, rule1.fromKtoR(p4));
        Transition t4 = rule1.getR().createTransition("", Renews.COUNT);
        rule1.getR().createArc("", rule1.fromKtoR(p3), t4);
        rule1.getR().createArc("", t4, rule1.fromKtoR(p5));
  
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fromLtoK method, of class Rule.
     */
    @Test
    public void testFromLtoK_INode() {
        System.out.println("fromLtoK");
        Rule instance = new Rule();
        INode node = instance.getL().createPlace("a");
        assertEquals(node.getName(), instance.fromLtoK(node).getName());
    	assertEquals(node.getName(), instance.fromKtoL(instance.fromLtoK(node)).getName());
    }

    /**
     * Test of fromLtoK method, of class Rule.
     */
    @Test
    public void testFromLtoK_Arc() {
        System.out.println("fromLtoK");
        Rule instance = new Rule();
        Transition node = instance.getL().createTransition("a", Renews.COUNT);
        assertEquals(node.getName(), instance.fromLtoK(node).getName());
        assertEquals(node.getRnw(), ((Transition)instance.fromLtoK(node)).getRnw());
    	assertEquals(node.getName(), instance.fromKtoL(instance.fromLtoK(node)).getName());
    	assertEquals(node.getRnw(), ((Transition)instance.fromKtoL(instance.fromLtoK(node))).getRnw());
    }

    /**
     * Test of fromRtoK method, of class Rule.
     */
    @Test
    public void testFromRtoK_INode() {
        System.out.println("fromRtoK");
        Rule instance = new Rule();
        INode node = instance.getR().createPlace("a");
        assertEquals(node.getName(), instance.fromRtoK(node).getName());
    	assertEquals(node.getName(), instance.fromKtoR(instance.fromRtoK(node)).getName());
    }

    /**
     * Test of fromRtoK method, of class Rule.
     */
    @Test
    public void testFromRtoK_Arc() {
        System.out.println("fromRtoK");
        Rule instance = TransformationComponent.getTransformation().createRule();
        Transition node = instance.getR().createTransition("a", Renews.COUNT);
        assertEquals(node.getName(), instance.fromRtoK(node).getName());
        assertEquals(node.getRnw(), ((Transition)instance.fromRtoK(node)).getRnw());
    	assertEquals(node.getName(), instance.fromKtoR(instance.fromRtoK(node)).getName());
    	assertEquals(node.getRnw(), ((Transition)instance.fromKtoR(instance.fromRtoK(node))).getRnw());
    }

    @Test
    public void testPlaces(){
        System.out.println("Places");
        Set<Place> k = rule1.getK().getAllPlaces();
        Set<Place> l = rule1.getL().getAllPlaces();
        Set<Place> r = rule1.getR().getAllPlaces();
        for(Place place : r){
            assertTrue(k.contains(rule1.fromRtoK(place)));
        }
        for(Place place : l){
            assertTrue(k.contains(rule1.fromLtoK(place)));
        }
    }

    @Test
    public void testArcs(){
        System.out.println("Arcs");
        Set<Arc> k = rule1.getK().getAllArcs();
        Set<Arc> l = rule1.getL().getAllArcs();
        Set<Arc> r = rule1.getR().getAllArcs();
        for(Arc place : r){
            assertTrue(k.contains(rule1.fromRtoK(place)));
        }
        for(Arc place : l){
            assertTrue(k.contains(rule1.fromLtoK(place)));
        }
    }

    @Test
    public void testTransitions(){
        System.out.println("Transitions");
        Set<Transition> k = rule1.getK().getAllTransitions();
        Set<Transition> l = rule1.getL().getAllTransitions();
        Set<Transition> r = rule1.getR().getAllTransitions();
        for(Transition place : r){
            assertTrue(k.contains(rule1.fromRtoK(place)));
        }
        for(Transition place : l){
            assertTrue(k.contains(rule1.fromLtoK(place)));
        }
    }
}