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
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;


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
        Place p1 = rule1.getL().addPlace("Wecker ein");
        Place p2 = rule1.getK().addPlace("Wecker ein");
        p2.setMark(1);
        Place p3 = rule1.getK().addPlace("");
        Place p4 = rule1.getK().addPlace("");
        Place p5 = rule1.getK().addPlace("Wecker aus");
        Place p6 = rule1.getL().addPlace("Wecker aus");
        Transition t1 = rule1.getL().addTransition("", Renews.COUNT);
        rule1.getL().addPreArc("", p1, t1);
        rule1.getL().addPostArc("", t1, (Place) rule1.fromKtoL(p4));
        Transition t2 = rule1.getL().addTransition("", Renews.COUNT);
        rule1.getL().addPreArc("", (Place) rule1.fromKtoL(p3), t2);
        rule1.getL().addPostArc("", t2, p6);
        Transition t3 = rule1.getR().addTransition("", Renews.COUNT);
        rule1.getR().addPreArc("", (Place) rule1.fromKtoR(p2), t3);
        rule1.getR().addPostArc("", t3, (Place) rule1.fromKtoR(p4));
        Transition t4 = rule1.getR().addTransition("", Renews.COUNT);
        rule1.getR().addPreArc("", (Place) rule1.fromKtoR(p3), t4);
        rule1.getR().addPostArc("", t4, (Place) rule1.fromKtoR(p5));
  
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
        INode node = instance.getL().addPlace("a");
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
        Transition node = instance.getL().addTransition("a", Renews.COUNT);
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
        INode node = instance.getR().addPlace("a");
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
        Transition node = instance.getR().addTransition("a", Renews.COUNT);
        assertEquals(node.getName(), instance.fromRtoK(node).getName());
        assertEquals(node.getRnw(), ((Transition)instance.fromRtoK(node)).getRnw());
    	assertEquals(node.getName(), instance.fromKtoR(instance.fromRtoK(node)).getName());
    	assertEquals(node.getRnw(), ((Transition)instance.fromKtoR(instance.fromRtoK(node))).getRnw());
    }

    @Test
    public void testPlaces(){
        System.out.println("Places");
        Set<Place> k = rule1.getK().getPlaces();
        Set<Place> l = rule1.getL().getPlaces();
        Set<Place> r = rule1.getR().getPlaces();
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
        Set<IArc> k = rule1.getK().getArcs();
        Set<IArc> l = rule1.getL().getArcs();
        Set<IArc> r = rule1.getR().getArcs();
        for(IArc place : r){
            assertTrue(k.contains(rule1.fromRtoK(place)));
        }
        for(IArc place : l){
            assertTrue(k.contains(rule1.fromLtoK(place)));
        }
    }

    @Test
    public void testTransitions(){
        System.out.println("Transitions");
        Set<Transition> k = rule1.getK().getTransitions();
        Set<Transition> l = rule1.getL().getTransitions();
        Set<Transition> r = rule1.getR().getTransitions();
        for(Transition place : r){
            assertTrue(k.contains(rule1.fromRtoK(place)));
        }
        for(Transition place : l){
            assertTrue(k.contains(rule1.fromLtoK(place)));
        }
    }
}