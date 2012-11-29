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
import petrinet.model.PostArc;
import petrinet.model.PreArc;
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
        Place p1 = rule1.addPlaceToL("Wecker ein");
        Place p2 = rule1.addPlaceToK("Wecker ein");
        rule1.setMarkInK(p2, 1);
        Place p3 = rule1.addPlaceToK("");
        Place p4 = rule1.addPlaceToK("");
        Place p5 = rule1.addPlaceToK("Wecker aus");
        Place p6 = rule1.addPlaceToL("Wecker aus");
        
        Transition t1 = rule1.addTransitionToL("", Renews.COUNT);
        
        rule1.addPreArcToL("", p1, t1);
        rule1.addPostArcToL("", t1, (Place) rule1.fromKtoL(p4));
        
        Transition t2 = rule1.addTransitionToL("", Renews.COUNT);
        
        rule1.addPreArcToL("", (Place) rule1.fromKtoL(p3), t2);
        rule1.addPostArcToL("", t2, p6);
        
        Transition t3 = rule1.addTransitionToR("", Renews.COUNT);
        
        rule1.addPreArcToR("", (Place) rule1.fromKtoR(p2), t3);
        rule1.addPostArcToR("", t3, (Place) rule1.fromKtoR(p4));
        
        Transition t4 = rule1.addTransitionToR("", Renews.COUNT);
        
        rule1.addPreArcToR("", (Place) rule1.fromKtoR(p3), t4);
        rule1.addPostArcToR("", t4, (Place) rule1.fromKtoR(p5));
  
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
        Place place = instance.addPlaceToL("a");

    	assertEquals(place.getName(), instance.fromLtoK(place).getName());
		assertEquals(place.getName(), instance.fromKtoL(instance.fromLtoK(place)).getName());
		
    	assertEquals(place.getMark(), instance.fromLtoK(place).getMark());
		assertEquals(place.getMark(), instance.fromKtoL(instance.fromLtoK(place)).getMark());
		
		assertNull(instance.fromKtoR(instance.fromLtoK(place)));
		assertNull(instance.fromLtoR(place));
    }
    
    /**
     * Test of fromLtoK method, of class Rule.
     */
    @Test
    public void testFromLtoK_Arc() {
        System.out.println("fromLtoK");
        Rule instance = new Rule();
        Transition node = instance.addTransitionToL("a", Renews.COUNT);
        
        assertEquals(node.getName(), instance.fromLtoK(node).getName());
    	assertEquals(node.getName(), instance.fromKtoL(instance.fromLtoK(node)).getName());
    	
        assertEquals(node.getRnw(), ((Transition)instance.fromLtoK(node)).getRnw());
    	assertEquals(node.getRnw(), ((Transition)instance.fromKtoL(instance.fromLtoK(node))).getRnw());
    	
    	assertEquals(node.getTlb(), instance.fromKtoL(instance.fromLtoK(node)).getTlb());
    	assertEquals(node.getTlb(), ((Transition)instance.fromKtoL(instance.fromLtoK(node))).getTlb());
    	
		assertNull(instance.fromKtoR(instance.fromLtoK(node)));
		assertNull(instance.fromLtoR(node));
    }

    /**
     * Test of fromRtoK method, of class Rule.
     */
    @Test
    public void testFromRtoK_INode() {
        System.out.println("fromRtoK");
        Rule instance = new Rule();
        Place place = instance.addPlaceToR("a");
        
        assertEquals(place.getName(), instance.fromRtoK(place).getName());
    	assertEquals(place.getName(), instance.fromKtoR(instance.fromRtoK(place)).getName());

    	assertEquals(place.getMark(), instance.fromKtoR(instance.fromRtoK(place)).getMark());
    	assertEquals(place.getMark(), instance.fromKtoR(instance.fromRtoK(place)).getMark());
    	
    	
		assertNull(instance.fromKtoL(instance.fromLtoK(place)));
		assertNull(instance.fromRtoL(place));
    }

    /**
     * Test of fromRtoK method, of class Rule.
     */
    @Test
    public void testFromRtoK_Arc() {
        System.out.println("fromRtoK");
        
        Rule instance   = TransformationComponent.getTransformation().createRule();
        Transition node = instance.addTransitionToR("a", Renews.COUNT);
        
        assertEquals(node.getName(), instance.fromRtoK(node).getName());
    	assertEquals(node.getName(), instance.fromKtoR(instance.fromRtoK(node)).getName());
    	
        assertEquals(node.getRnw(), ((Transition)instance.fromRtoK(node)).getRnw());
    	assertEquals(node.getRnw(), ((Transition)instance.fromKtoR(instance.fromRtoK(node))).getRnw());

    	assertEquals(node.getTlb(), ((Transition)instance.fromKtoR(instance.fromRtoK(node))).getTlb());
    	assertEquals(node.getTlb(), ((Transition)instance.fromKtoR(instance.fromRtoK(node))).getTlb());
    	
		assertNull(instance.fromKtoL(instance.fromLtoK(node)));
		assertNull(instance.fromRtoL(node));
    }

    @Test
    public void testPlaces(){
        System.out.println("Places");
        Set<Place> k = rule1.getK().getPlaces();
        Set<Place> l = rule1.getL().getPlaces();
        Set<Place> r = rule1.getR().getPlaces();


        for(Place place : r){
            assertTrue(k.contains(rule1.fromRtoK(place)));
            
            if (rule1.fromRtoL(place) != null) {
                assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL(place))));            	
            }
        }
        
        for(Place place : l){
            assertTrue(k.contains(rule1.fromLtoK(place)));
            
            if (rule1.fromLtoR(place) != null) {
                assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR(place))));            	
            }
        }
    }

    @Test
    public void testArcs(){
        System.out.println("Arcs");
        Set<IArc> k = rule1.getK().getArcs();
        Set<IArc> l = rule1.getL().getArcs();
        Set<IArc> r = rule1.getR().getArcs();
        
        for(IArc arc : r){
        	if (arc instanceof PreArc) {
        		assertTrue(k.contains(rule1.fromRtoK((PreArc) arc)));

                if (rule1.fromRtoL((PreArc) arc) != null) {
                    assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL((PreArc) arc))));            	
                }
        	} else {
        		assertTrue(k.contains(rule1.fromRtoK((PostArc) arc)));    

                if (rule1.fromRtoL((PostArc) arc) != null) {
                    assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL((PostArc) arc))));            	
                }
        	}
        }
        
        for(IArc arc : l) {            
        	if (arc instanceof PreArc) {
        		assertTrue(k.contains(rule1.fromLtoK((PreArc) arc)));

                if (rule1.fromLtoR((PreArc) arc) != null) {
                    assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR((PreArc) arc))));            	
                }
        	} else {
        		assertTrue(k.contains(rule1.fromLtoK((PostArc) arc)));        	

                if (rule1.fromLtoR((PostArc) arc) != null) {
                    assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR((PostArc) arc))));            	
                }
        	}
        }
    }

    @Test
    public void testTransitions(){
        System.out.println("Transitions");
        Set<Transition> k = rule1.getK().getTransitions();
        Set<Transition> l = rule1.getL().getTransitions();
        Set<Transition> r = rule1.getR().getTransitions();
        
        for(Transition transition : r){
            assertTrue(k.contains(rule1.fromRtoK(transition)));

            if (rule1.fromRtoL(transition) != null) {
                assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL(transition))));            	
            }
        }
        
        for(Transition transition : l){
            assertTrue(k.contains(rule1.fromLtoK(transition)));

            
            if (rule1.fromLtoR(transition) != null) {
                assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR(transition))));            	
            }
        }
    }

    @Test
    public void testAddPlace(){
        Rule  rule  = new Rule();
        Place place = rule.addPlaceToL("a");

		assertEquals(place, rule.fromKtoL(rule.fromLtoK(place)));
		assertNull(rule.fromLtoR(place));

        rule  = new Rule();
        place = rule.addPlaceToK("b");

		assertEquals(place, rule.fromLtoK(rule.fromKtoL(place)));
		assertEquals(place, rule.fromRtoK(rule.fromKtoR(place)));

        rule  = new Rule();
        place = rule.addPlaceToR("c");

		assertNull(rule.fromRtoL(place));
		assertEquals(place, rule.fromKtoR(rule.fromRtoK(place)));  
    }


    @Test
    public void testAddTransition(){
        Rule  rule  = new Rule();
        Transition transition = rule.addTransitionToL("a");

		assertEquals(transition, rule.fromKtoL(rule.fromLtoK(transition)));
		assertNull(rule.fromLtoR(transition));

        rule  = new Rule();
        transition = rule.addTransitionToK("b");

		assertEquals(transition, rule.fromLtoK(rule.fromKtoL(transition)));
		assertEquals(transition, rule.fromRtoK(rule.fromKtoR(transition)));

        rule  = new Rule();
        transition = rule.addTransitionToR("c");

		assertNull(rule.fromRtoL(transition));
		assertEquals(transition, rule.fromKtoR(rule.fromRtoK(transition)));  
    }


    @Test
    public void testAddArc(){
        Rule  	   rule       = new Rule();
        Place      place      = rule.addPlaceToL("a");
        Transition transition = rule.addTransitionToL("b");
        PreArc	   preArc     = rule.addPreArcToL("c", place, transition);
        PostArc	   postArc    = rule.addPostArcToL("c", transition, place);

		assertEquals(preArc, rule.fromKtoL(rule.fromLtoK(preArc)));
		assertEquals(postArc, rule.fromKtoL(rule.fromLtoK(postArc)));
		assertNull(rule.fromLtoR(preArc));
		assertNull(rule.fromLtoR(postArc));

        rule       = new Rule();
        place      = rule.addPlaceToK("a");
        transition = rule.addTransitionToK("b");
        preArc     = rule.addPreArcToK("c", place, transition);
        postArc    = rule.addPostArcToK("c", transition, place);

		assertEquals(preArc, rule.fromLtoK(rule.fromKtoL(preArc)));
		assertEquals(postArc, rule.fromLtoK(rule.fromKtoL(postArc)));
		assertEquals(preArc, rule.fromRtoK(rule.fromKtoR(preArc)));
		assertEquals(postArc, rule.fromRtoK(rule.fromKtoR(postArc)));


        rule       = new Rule();
        place      = rule.addPlaceToR("a");
        transition = rule.addTransitionToR("b");
        preArc     = rule.addPreArcToR("c", place, transition);
        postArc    = rule.addPostArcToR("c", transition, place);

		assertNull(rule.fromRtoL(preArc));
		assertNull(rule.fromRtoL(postArc));
		assertEquals(preArc, rule.fromKtoR(rule.fromRtoK(preArc)));  
		assertEquals(postArc, rule.fromKtoR(rule.fromRtoK(postArc)));  
    }


    @Test
    public void testRemovePlace_1() {
        Rule  rule   = new Rule();
        Place place  = rule.addPlaceToL("a");
        Place lPlace = place;
        Place kPlace = rule.fromLtoK(place);
        Place rPlace = rule.fromLtoR(place);        
        assertNull(rPlace);
        rule.removePlaceFromL(lPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace);
    }

    @Test
    public void testRemovePlace_2() {
        Rule rule   = new Rule();
        Place  place  = rule.addPlaceToL("a");
        Place  lPlace = place;
        Place  kPlace = rule.fromLtoK(place);
        Place  rPlace = rule.fromLtoR(place);    
        assertNull(rPlace);    
        rule.removePlaceFromK(kPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace); 
    }

    @Test
    public void testRemovePlace_3() {
        Rule rule   = new Rule();
        Place  place  = rule.addPlaceToK("a");
        Place  lPlace = rule.fromKtoL(place);
        Place  kPlace = place;
        Place  rPlace = rule.fromKtoR(place);
        rule.removePlaceFromL(lPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace);
    }

    @Test
    public void testRemovePlace_4() {
        Rule rule   = new Rule();
        Place  place  = rule.addPlaceToK("a");
        Place  lPlace = rule.fromKtoL(place);
        Place  kPlace = place;
        Place  rPlace = rule.fromKtoR(place);        
        rule.removePlaceFromK(kPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace); 
    }

    @Test
    public void testRemovePlace_5() {
        Rule rule   = new Rule();
        Place  place  = rule.addPlaceToK("a");
        Place  lPlace = rule.fromKtoL(place);
        Place  kPlace = place;
        Place  rPlace = rule.fromKtoR(place);        
        rule.removePlaceFromR(rPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace); 
    }

    @Test
    public void testRemovePlace_6() {
        Rule rule   = new Rule();
        Place  place  = rule.addPlaceToR("a");
        Place  lPlace = rule.fromRtoL(place);
        Place  kPlace = rule.fromRtoK(place);
        Place  rPlace = place;         
        assertNull(lPlace);     
        rule.removePlaceFromK(kPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace); 
    }

    @Test
    public void testRemovePlace_7() {
        Rule rule   = new Rule();
        Place  place  = rule.addPlaceToR("a");
        Place  lPlace = rule.fromRtoL(place);
        Place  kPlace = rule.fromRtoK(place);
        Place  rPlace = place;         
        assertNull(lPlace);     
        rule.removePlaceFromR(rPlace);
        removePlaceAssertions(rule, lPlace, kPlace, rPlace); 
    }

    @Test
    public void testRemoveTransition_1(){
        Rule  rule   = new Rule();
        Transition transition  = rule.addTransitionToL("a");
        Transition lTransition  = transition;
        Transition kTransition  = rule.fromLtoK(transition);
        Transition rTransition  = rule.fromLtoR(transition);        
        assertNull(rTransition);
        rule.removeTransitionFromL(lTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
    }

    @Test
    public void testRemoveTransition_2() {
        Rule rule   = new Rule();
        Transition  transition  = rule.addTransitionToL("a");
        Transition  lTransition  = transition;
        Transition  kTransition  = rule.fromLtoK(transition);
        Transition  rTransition  = rule.fromLtoR(transition);    
        assertNull(rTransition);    
        rule.removeTransitionFromK(kTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition); 
    }

    @Test
    public void testRemoveTransition_3() {
        Rule rule   = new Rule();
        Transition  transition  = rule.addTransitionToK("a");
        Transition  lTransition  = rule.fromKtoL(transition);
        Transition  kTransition  = transition;
        Transition  rTransition  = rule.fromKtoR(transition);
        rule.removeTransitionFromL(lTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
    }

    @Test
    public void testRemoveTransition_4() {
        Rule rule   = new Rule();
        Transition  transition  = rule.addTransitionToK("a");
        Transition  lTransition  = rule.fromKtoL(transition);
        Transition  kTransition  = transition;
        Transition  rTransition  = rule.fromKtoR(transition);        
        rule.removeTransitionFromK(kTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition); 
    }

    @Test
    public void testRemoveTransition_5() {
        Rule rule   = new Rule();
        Transition  transition  = rule.addTransitionToK("a");
        Transition  lTransition  = rule.fromKtoL(transition);
        Transition  kTransition  = transition;
        Transition  rTransition  = rule.fromKtoR(transition);        
        rule.removeTransitionFromR(rTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition); 
    }

    @Test
    public void testRemoveTransition_6() {
        Rule rule   = new Rule();
        Transition  transition  = rule.addTransitionToR("a");
        Transition  lTransition  = rule.fromRtoL(transition);
        Transition  kTransition  = rule.fromRtoK(transition);
        Transition  rTransition  = transition;         
        assertNull(lTransition);     
        rule.removeTransitionFromK(kTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition); 
    }

    @Test
    public void testRemoveTransition_7() {
        Rule rule   = new Rule();
        Transition  transition  = rule.addTransitionToR("a");
        Transition  lTransition  = rule.fromRtoL(transition);
        Transition  kTransition  = rule.fromRtoK(transition);
        Transition  rTransition  = transition;         
        assertNull(lTransition);     
        rule.removeTransitionFromR(rTransition);
        removeTransitionAssertions(rule, lTransition, kTransition, rTransition); 
    }


    @Test
    public void testRemovePreArc_1(){
        Rule  rule   = new Rule();
        PreArc preArc  = rule.addPreArcToL("a", rule.addPlaceToL("a"), rule.addTransitionToL("a"));
        PreArc lPreArc = preArc;
        PreArc kPreArc = rule.fromLtoK(preArc);
        PreArc rPreArc = rule.fromLtoR(preArc);        
        assertNull(rPreArc);
        rule.removePreArcFromL(lPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
    }

    @Test
    public void testRemovePreArc_2() {
        Rule rule   = new Rule();
        PreArc preArc  = rule.addPreArcToL("a", rule.addPlaceToL("a"), rule.addTransitionToL("a"));
        PreArc lPreArc = preArc;
        PreArc kPreArc = rule.fromLtoK(preArc);
        PreArc rPreArc = rule.fromLtoR(preArc);    
        assertNull(rPreArc);    
        rule.removePreArcFromK(kPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc); 
    }

    @Test
    public void testRemovePreArc_3() {
        Rule rule   = new Rule();
        PreArc preArc  = rule.addPreArcToK("a", rule.addPlaceToK("a"), rule.addTransitionToK("a"));
        PreArc lPreArc = rule.fromKtoL(preArc);
        PreArc kPreArc = preArc;
        PreArc rPreArc = rule.fromKtoR(preArc);
        rule.removePreArcFromL(lPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
    }

    @Test
    public void testRemovePreArc_4() {
        Rule rule   = new Rule();
        PreArc preArc  = rule.addPreArcToK("a", rule.addPlaceToK("a"), rule.addTransitionToK("a"));
        PreArc lPreArc = rule.fromKtoL(preArc);
        PreArc kPreArc = preArc;
        PreArc rPreArc = rule.fromKtoR(preArc);        
        rule.removePreArcFromK(kPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc); 
    }

    @Test
    public void testRemovePreArc_5() {
        Rule rule   = new Rule();
        PreArc preArc  = rule.addPreArcToK("a", rule.addPlaceToK("a"), rule.addTransitionToK("a"));
        PreArc lPreArc = rule.fromKtoL(preArc);
        PreArc kPreArc = preArc;
        PreArc rPreArc = rule.fromKtoR(preArc);        
        rule.removePreArcFromR(rPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc); 
    }

    @Test
    public void testRemovePreArc_6() {
        Rule rule   = new Rule();
        PreArc preArc  = rule.addPreArcToR("a", rule.addPlaceToR("a"), rule.addTransitionToR("a"));
        PreArc lPreArc = rule.fromRtoL(preArc);
        PreArc kPreArc = rule.fromRtoK(preArc);
        PreArc rPreArc = preArc;         
        assertNull(lPreArc);     
        rule.removePreArcFromK(kPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc); 
    }

    @Test
    public void testRemovePreArc_7() {
        Rule rule   = new Rule();
        PreArc preArc  = rule.addPreArcToR("a", rule.addPlaceToR("a"), rule.addTransitionToR("a"));
        PreArc lPreArc = rule.fromRtoL(preArc);
        PreArc kPreArc = rule.fromRtoK(preArc);
        PreArc rPreArc = preArc;         
        assertNull(lPreArc);     
        rule.removePreArcFromR(rPreArc);
        removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc); 
    }


    @Test
    public void testRemovePostArc_1(){
        Rule  rule   = new Rule();
        PostArc postArc  = rule.addPostArcToL("a", rule.addTransitionToL("a"), rule.addPlaceToL("a"));
        PostArc lPostArc = postArc;
        PostArc kPostArc = rule.fromLtoK(postArc);
        PostArc rPostArc = rule.fromLtoR(postArc);        
        assertNull(rPostArc);
        rule.removePostArcFromL(lPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
    }

    @Test
    public void testRemovePostArc_2() {
        Rule rule   = new Rule();
        PostArc postArc  = rule.addPostArcToL("a", rule.addTransitionToL("a"), rule.addPlaceToL("a"));
        PostArc lPostArc = postArc;
        PostArc kPostArc = rule.fromLtoK(postArc);
        PostArc rPostArc = rule.fromLtoR(postArc);    
        assertNull(rPostArc);    
        rule.removePostArcFromK(kPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc); 
    }

    @Test
    public void testRemovePostArc_3() {
        Rule rule   = new Rule();
        PostArc postArc  = rule.addPostArcToK("a", rule.addTransitionToK("a"), rule.addPlaceToK("a"));
        PostArc lPostArc = rule.fromKtoL(postArc);
        PostArc kPostArc = postArc;
        PostArc rPostArc = rule.fromKtoR(postArc);
        rule.removePostArcFromL(lPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
    }

    @Test
    public void testRemovePostArc_4() {
        Rule rule   = new Rule();
        PostArc postArc  = rule.addPostArcToK("a", rule.addTransitionToK("a"), rule.addPlaceToK("a"));
        PostArc lPostArc = rule.fromKtoL(postArc);
        PostArc kPostArc = postArc;
        PostArc rPostArc = rule.fromKtoR(postArc);        
        rule.removePostArcFromK(kPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc); 
    }

    @Test
    public void testRemovePostArc_5() {
        Rule rule   = new Rule();
        PostArc postArc  = rule.addPostArcToK("a", rule.addTransitionToK("a"), rule.addPlaceToK("a"));
        PostArc lPostArc = rule.fromKtoL(postArc);
        PostArc kPostArc = postArc;
        PostArc rPostArc = rule.fromKtoR(postArc);        
        rule.removePostArcFromR(rPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc); 
    }

    @Test
    public void testRemovePostArc_6() {
        Rule rule   = new Rule();
        PostArc postArc  = rule.addPostArcToR("a", rule.addTransitionToR("a"), rule.addPlaceToR("a"));
        PostArc lPostArc = rule.fromRtoL(postArc);
        PostArc kPostArc = rule.fromRtoK(postArc);
        PostArc rPostArc = postArc;         
        assertNull(lPostArc);     
        rule.removePostArcFromK(kPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc); 
    }

    @Test
    public void testRemovePostArc_7() {
        Rule rule   = new Rule();
        PostArc postArc  = rule.addPostArcToR("a", rule.addTransitionToR("a"), rule.addPlaceToR("a"));
        PostArc lPostArc = rule.fromRtoL(postArc);
        PostArc kPostArc = rule.fromRtoK(postArc);
        PostArc rPostArc = postArc;         
        assertNull(lPostArc);     
        rule.removePostArcFromR(rPostArc);
        removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc); 
    }
    
    private void removePlaceAssertions(Rule rule, Place lPlace, Place kPlace, Place rPlace) {
        assertFalse(lPlace != null && rule.getL().containsPlace(lPlace));
        assertFalse(rule.getK().containsPlace(kPlace));
        assertFalse(rPlace != null && rule.getR().containsPlace(rPlace));
       
		assertNull(rule.fromLtoK(lPlace));		
		assertNull(rule.fromLtoR(lPlace));

		assertNull(rule.fromKtoL(kPlace));		
		assertNull(rule.fromKtoR(kPlace));  

		assertNull(rule.fromRtoL(rPlace));		
		assertNull(rule.fromRtoK(rPlace));  
    }

    private void removeTransitionAssertions(Rule rule, Transition lTransition, Transition kTransition, Transition rTransition) {
        assertFalse(lTransition != null && rule.getL().containsTransition(lTransition));
        assertFalse(rule.getK().containsTransition(kTransition));
        assertFalse(rTransition != null && rule.getR().containsTransition(rTransition));
       
		assertNull(rule.fromLtoK(lTransition));		
		assertNull(rule.fromLtoR(lTransition));

		assertNull(rule.fromKtoL(kTransition));		
		assertNull(rule.fromKtoR(kTransition));  

		assertNull(rule.fromRtoL(rTransition));		
		assertNull(rule.fromRtoK(rTransition));  
    }
    
    private void removePreArcAssertions(Rule rule, PreArc lPreArc, PreArc kPreArc, PreArc rPreArc) {
        assertFalse(lPreArc != null && rule.getL().containsPreArc(lPreArc));
        assertFalse(rule.getK().containsPreArc(kPreArc));
        assertFalse(rPreArc != null && rule.getR().containsPreArc(rPreArc));
       
		assertNull(rule.fromLtoK(lPreArc));		
		assertNull(rule.fromLtoR(lPreArc));

		assertNull(rule.fromKtoL(kPreArc));		
		assertNull(rule.fromKtoR(kPreArc));  

		assertNull(rule.fromRtoL(rPreArc));		
		assertNull(rule.fromRtoK(rPreArc));  
    }
    
    private void removePostArcAssertions(Rule rule, PostArc lPostArc, PostArc kPostArc, PostArc rPostArc) {
        assertFalse(lPostArc != null && rule.getL().containsPostArc(lPostArc));
        assertFalse(rule.getK().containsPostArc(kPostArc));
        assertFalse(rPostArc != null && rule.getR().containsPostArc(rPostArc));
       
		assertNull(rule.fromLtoK(lPostArc));		
		assertNull(rule.fromLtoR(lPostArc));

		assertNull(rule.fromKtoL(kPostArc));		
		assertNull(rule.fromKtoR(kPostArc));  

		assertNull(rule.fromRtoL(rPostArc));		
		assertNull(rule.fromRtoK(rPostArc));  
    }

    @Test
    public void testSetNamePlace_1() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToL("a");
        
        rule.setNameInL(place, "b");

        assertEquals("b", place.getName());
        assertEquals("b", rule.fromLtoK(place).getName());
    }

    @Test
    public void testSetNamePlace_2() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToL("a");
        Place otherPlace = rule.fromLtoK(place);
        
        rule.setNameInK(otherPlace, "b");

        assertEquals("b", place.getName());
        assertEquals("b", rule.fromKtoL(otherPlace).getName());
        assertEquals("b", otherPlace.getName());
    }


    @Test
    public void testSetNamePlace_3() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToK("a");
        Place otherPlace = rule.fromKtoL(place);
        
        rule.setNameInL(otherPlace, "b");

        assertEquals("b", otherPlace.getName());
        assertEquals("b", place.getName());
        assertEquals("b", rule.fromLtoK(otherPlace).getName());
        assertEquals("b", rule.fromLtoR(otherPlace).getName());
    }

    @Test
    public void testSetNamePlace_4() {
        Rule  rule   = new Rule();
        Place place  = rule.addPlaceToK("a");
        
        rule.setNameInK(place, "b");

        assertEquals("b", rule.fromKtoL(place).getName());
        assertEquals("b", place.getName());
        assertEquals("b", rule.fromKtoR(place).getName());
    }

    @Test
    public void testSetNamePlace_5() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToK("a");
        Place otherPlace = rule.fromKtoR(place);
        
        rule.setNameInR(otherPlace, "b");

        assertEquals("b", rule.fromRtoL(otherPlace).getName());
        assertEquals("b", place.getName());
        assertEquals("b", rule.fromRtoK(otherPlace).getName());
        assertEquals("b", otherPlace.getName());
    }

    @Test
    public void testSetNamePlace_6() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToR("a");
        
        rule.setNameInR(place, "b");

        assertEquals("b", rule.fromRtoK(place).getName());
        assertEquals("b", place.getName());
    }

    @Test
    public void testSetNamePlace_7() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToR("a");
        Place otherPlace = rule.fromRtoK(place);
        
        rule.setNameInK(otherPlace, "b");

        assertEquals("b", otherPlace.getName());
        assertEquals("b", rule.fromKtoR(otherPlace).getName());
        assertEquals("b", place.getName());
    }


    @Test
    public void testSetNameTransition_1() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToL("a");
        
        rule.setNameInL(transition, "b");

        assertEquals("b", transition.getName());
        assertEquals("b", rule.fromLtoK(transition).getName());
    }

    @Test
    public void testSetNameTransition_2() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToL("a");
        Transition otherTransition = rule.fromLtoK(transition);
        
        rule.setNameInK(otherTransition, "b");

        assertEquals("b", transition.getName());
        assertEquals("b", rule.fromKtoL(otherTransition).getName());
        assertEquals("b", otherTransition.getName());
    }


    @Test
    public void testSetNameTransition_3() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToK("a");
        Transition otherTransition = rule.fromKtoL(transition);
        
        rule.setNameInL(otherTransition, "b");

        assertEquals("b", otherTransition.getName());
        assertEquals("b", transition.getName());
        assertEquals("b", rule.fromLtoK(otherTransition).getName());
        assertEquals("b", rule.fromLtoR(otherTransition).getName());
    }

    @Test
    public void testSetNameTransition_4() {
        Rule  rule   = new Rule();
        Transition transition  = rule.addTransitionToK("a");
        
        rule.setNameInK(transition, "b");

        assertEquals("b", rule.fromKtoL(transition).getName());
        assertEquals("b", transition.getName());
        assertEquals("b", rule.fromKtoR(transition).getName());
    }

    @Test
    public void testSetNameTransition_5() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToK("a");
        Transition otherTransition = rule.fromKtoR(transition);
        
        rule.setNameInR(otherTransition, "b");

        assertEquals("b", rule.fromRtoL(otherTransition).getName());
        assertEquals("b", transition.getName());
        assertEquals("b", rule.fromRtoK(otherTransition).getName());
        assertEquals("b", otherTransition.getName());
    }

    @Test
    public void testSetNameTransition_6() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToR("a");
        
        rule.setNameInR(transition, "b");

        assertEquals("b", rule.fromRtoK(transition).getName());
        assertEquals("b", transition.getName());
    }

    @Test
    public void testSetNameTransition_7() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToR("a");
        Transition otherTransition = rule.fromRtoK(transition);
        
        rule.setNameInK(otherTransition, "b");

        assertEquals("b", otherTransition.getName());
        assertEquals("b", rule.fromKtoR(otherTransition).getName());
        assertEquals("b", transition.getName());
    }


    @Test
    public void testSetTlbTransition_1() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToL("a");
        
        rule.setTlbInL(transition, "b");

        assertEquals("b", transition.getTlb());
        assertEquals("b", rule.fromLtoK(transition).getTlb());
    }

    @Test
    public void testSetTlbTransition_2() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToL("a");
        Transition otherTransition = rule.fromLtoK(transition);
        
        rule.setTlbInK(otherTransition, "b");

        assertEquals("b", transition.getTlb());
        assertEquals("b", rule.fromKtoL(otherTransition).getTlb());
        assertEquals("b", otherTransition.getTlb());
    }


    @Test
    public void testSetTlbTransition_3() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToK("a");
        Transition otherTransition = rule.fromKtoL(transition);
        
        rule.setTlbInL(otherTransition, "b");

        assertEquals("b", otherTransition.getTlb());
        assertEquals("b", transition.getTlb());
        assertEquals("b", rule.fromLtoK(otherTransition).getTlb());
        assertEquals("b", rule.fromLtoR(otherTransition).getTlb());
    }

    @Test
    public void testSetTlbTransition_4() {
        Rule  rule   = new Rule();
        Transition transition  = rule.addTransitionToK("a");
        
        rule.setTlbInK(transition, "b");

        assertEquals("b", rule.fromKtoL(transition).getTlb());
        assertEquals("b", transition.getTlb());
        assertEquals("b", rule.fromKtoR(transition).getTlb());
    }

    @Test
    public void testSetTlbTransition_5() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToK("a");
        Transition otherTransition = rule.fromKtoR(transition);
        
        rule.setTlbInR(otherTransition, "b");

        assertEquals("b", rule.fromRtoL(otherTransition).getTlb());
        assertEquals("b", transition.getTlb());
        assertEquals("b", rule.fromRtoK(otherTransition).getTlb());
        assertEquals("b", otherTransition.getTlb());
    }

    @Test
    public void testSetTlbTransition_6() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToR("a");
        
        rule.setTlbInR(transition, "b");

        assertEquals("b", rule.fromRtoK(transition).getTlb());
        assertEquals("b", transition.getTlb());
    }

    @Test
    public void testSetTlbTransition_7() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToR("a");
        Transition otherTransition = rule.fromRtoK(transition);
        
        rule.setTlbInK(otherTransition, "b");

        assertEquals("b", otherTransition.getTlb());
        assertEquals("b", rule.fromKtoR(otherTransition).getTlb());
        assertEquals("b", transition.getTlb());
    }


    @Test
    public void testSetMarkingPlace_1() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToL("a");
        
        rule.setMarkInL(place, 97);

        assertEquals(97, place.getMark());
        assertEquals(97, rule.fromLtoK(place).getMark());
    }

    @Test
    public void testSetMarkingPlace_2() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToL("a");
        Place otherPlace = rule.fromLtoK(place);
        
        rule.setMarkInK(otherPlace, 97);

        assertEquals(97, place.getMark());
        assertEquals(97, rule.fromKtoL(otherPlace).getMark());
        assertEquals(97, otherPlace.getMark());
    }


    @Test
    public void testSetMarkingPlace_3() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToK("a");
        Place otherPlace = rule.fromKtoL(place);
        
        rule.setMarkInL(otherPlace, 97);

        assertEquals(97, otherPlace.getMark());
        assertEquals(97, place.getMark());
        assertEquals(97, rule.fromLtoK(otherPlace).getMark());
        assertEquals(97, rule.fromLtoR(otherPlace).getMark());
    }

    @Test
    public void testSetMarkingPlace_4() {
        Rule  rule   = new Rule();
        Place place  = rule.addPlaceToK("a");
        
        rule.setMarkInK(place, 97);

        assertEquals(97, rule.fromKtoL(place).getMark());
        assertEquals(97, place.getMark());
        assertEquals(97, rule.fromKtoR(place).getMark());
    }

    @Test
    public void testSetMarkingPlace_5() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToK("a");
        Place otherPlace = rule.fromKtoR(place);
        
        rule.setMarkInR(otherPlace, 97);

        assertEquals(97, rule.fromRtoL(otherPlace).getMark());
        assertEquals(97, place.getMark());
        assertEquals(97, rule.fromRtoK(otherPlace).getMark());
        assertEquals(97, otherPlace.getMark());
    }

    @Test
    public void testSetMarkingPlace_6() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToR("a");
        
        rule.setMarkInR(place, 97);

        assertEquals(97, rule.fromRtoK(place).getMark());
        assertEquals(97, place.getMark());
    }

    @Test
    public void testSetMarkingPlace_7() {
        Rule  rule       = new Rule();
        Place place      = rule.addPlaceToR("a");
        Place otherPlace = rule.fromRtoK(place);
        
        rule.setMarkInK(otherPlace, 97);

        assertEquals(97, otherPlace.getMark());
        assertEquals(97, rule.fromKtoR(otherPlace).getMark());
        assertEquals(97, place.getMark());
    }


    @Test
    public void testSetRnwTransition_1() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToL("a");

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInL(transition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, transition.getRnw());
        assertEquals(Renews.TOGGLE, rule.fromLtoK(transition).getRnw());
    }

    @Test
    public void testSetRnwTransition_2() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToL("a");
        Transition otherTransition = rule.fromLtoK(transition);

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInK(otherTransition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, transition.getRnw());
        assertEquals(Renews.TOGGLE, rule.fromKtoL(otherTransition).getRnw());
        assertEquals(Renews.TOGGLE, otherTransition.getRnw());
    }


    @Test
    public void testSetRnwTransition_3() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToK("a");
        Transition otherTransition = rule.fromKtoL(transition);

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInL(otherTransition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, otherTransition.getRnw());
        assertEquals(Renews.TOGGLE, transition.getRnw());
        assertEquals(Renews.TOGGLE, rule.fromLtoK(otherTransition).getRnw());
        assertEquals(Renews.TOGGLE, rule.fromLtoR(otherTransition).getRnw());
    }

    @Test
    public void testSetRnwTransition_4() {
        Rule  rule   = new Rule();
        Transition transition  = rule.addTransitionToK("a");

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInK(transition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, rule.fromKtoL(transition).getRnw());
        assertEquals(Renews.TOGGLE, transition.getRnw());
        assertEquals(Renews.TOGGLE, rule.fromKtoR(transition).getRnw());
    }

    @Test
    public void testSetRnwTransition_5() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToK("a");
        Transition otherTransition = rule.fromKtoR(transition);

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInR(otherTransition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, rule.fromRtoL(otherTransition).getRnw());
        assertEquals(Renews.TOGGLE, transition.getRnw());
        assertEquals(Renews.TOGGLE, rule.fromRtoK(otherTransition).getRnw());
        assertEquals(Renews.TOGGLE, otherTransition.getRnw());
    }

    @Test
    public void testSetRnwTransition_6() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToR("a");

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInR(transition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, rule.fromRtoK(transition).getRnw());
        assertEquals(Renews.TOGGLE, transition.getRnw());
    }

    @Test
    public void testSetRnwTransition_7() {
        Rule  rule       = new Rule();
        Transition transition      = rule.addTransitionToR("a");
        Transition otherTransition = rule.fromRtoK(transition);

        assertEquals(Renews.IDENTITY, transition.getRnw());
        rule.setRnwInK(otherTransition, Renews.TOGGLE);

        assertEquals(Renews.TOGGLE, otherTransition.getRnw());
        assertEquals(Renews.TOGGLE, rule.fromKtoR(otherTransition).getRnw());
        assertEquals(Renews.TOGGLE, transition.getRnw());
    }
    


    @Test
    public void testSetWeightPreArc_1() {
        Rule  rule       = new Rule();
        PreArc preArc      = rule.addPreArcToL("a", rule.addPlaceToL("a"), rule.addTransitionToL("a"));
        
        rule.setWeightInL(preArc, 97);

        assertEquals(97, preArc.getMark());
        assertEquals(97, rule.fromLtoK(preArc).getMark());
    }

    @Test
    public void testSetWeightPreArc_2() {
        Rule  rule       = new Rule();
        PreArc preArc      = rule.addPreArcToL("a", rule.addPlaceToL("a"), rule.addTransitionToL("a"));
        PreArc otherPreArc = rule.fromLtoK(preArc);
        
        rule.setWeightInK(otherPreArc, 97);

        assertEquals(97, preArc.getMark());
        assertEquals(97, rule.fromKtoL(otherPreArc).getMark());
        assertEquals(97, otherPreArc.getMark());
    }


    @Test
    public void testSetWeightPreArc_3() {
        Rule  rule       = new Rule();
        PreArc preArc      = rule.addPreArcToK("a", rule.addPlaceToK("a"), rule.addTransitionToK("a"));
        PreArc otherPreArc = rule.fromKtoL(preArc);
        
        rule.setWeightInL(otherPreArc, 97);

        assertEquals(97, otherPreArc.getMark());
        assertEquals(97, preArc.getMark());
        assertEquals(97, rule.fromLtoK(otherPreArc).getMark());
        assertEquals(97, rule.fromLtoR(otherPreArc).getMark());
    }

    @Test
    public void testSetWeightPreArc_4() {
        Rule  rule   = new Rule();
        PreArc preArc  = rule.addPreArcToK("a", rule.addPlaceToK("a"), rule.addTransitionToK("a"));
        
        rule.setWeightInK(preArc, 97);

        assertEquals(97, rule.fromKtoL(preArc).getMark());
        assertEquals(97, preArc.getMark());
        assertEquals(97, rule.fromKtoR(preArc).getMark());
    }

    @Test
    public void testSetWeightPreArc_5() {
        Rule  rule       = new Rule();
        PreArc preArc      = rule.addPreArcToK("a", rule.addPlaceToK("a"), rule.addTransitionToK("a"));
        PreArc otherPreArc = rule.fromKtoR(preArc);
        
        rule.setWeightInR(otherPreArc, 97);

        assertEquals(97, rule.fromRtoL(otherPreArc).getMark());
        assertEquals(97, preArc.getMark());
        assertEquals(97, rule.fromRtoK(otherPreArc).getMark());
        assertEquals(97, otherPreArc.getMark());
    }

    @Test
    public void testSetWeightPreArc_6() {
        Rule  rule       = new Rule();
        PreArc preArc      = rule.addPreArcToR("a", rule.addPlaceToR("a"), rule.addTransitionToR("a"));
        
        rule.setWeightInR(preArc, 97);

        assertEquals(97, rule.fromRtoK(preArc).getMark());
        assertEquals(97, preArc.getMark());
    }

    @Test
    public void testSetWeightPreArc_7() {
        Rule  rule       = new Rule();
        PreArc preArc      = rule.addPreArcToR("a", rule.addPlaceToR("a"), rule.addTransitionToR("a"));
        PreArc otherPreArc = rule.fromRtoK(preArc);
        
        rule.setWeightInK(otherPreArc, 97);

        assertEquals(97, otherPreArc.getMark());
        assertEquals(97, rule.fromKtoR(otherPreArc).getMark());
        assertEquals(97, preArc.getMark());
    }
    


    @Test
    public void testSetWeightPostArc_1() {
        Rule  rule       = new Rule();
        PostArc postArc      = rule.addPostArcToL("a", rule.addTransitionToL("a"), rule.addPlaceToL("a"));
        
        rule.setWeightInL(postArc, 97);

        assertEquals(97, postArc.getMark());
        assertEquals(97, rule.fromLtoK(postArc).getMark());
    }

    @Test
    public void testSetWeightPostArc_2() {
        Rule  rule       = new Rule();
        PostArc postArc      = rule.addPostArcToL("a", rule.addTransitionToL("a"), rule.addPlaceToL("a"));
        PostArc otherPostArc = rule.fromLtoK(postArc);
        
        rule.setWeightInK(otherPostArc, 97);

        assertEquals(97, postArc.getMark());
        assertEquals(97, rule.fromKtoL(otherPostArc).getMark());
        assertEquals(97, otherPostArc.getMark());
    }


    @Test
    public void testSetWeightPostArc_3() {
        Rule  rule       = new Rule();
        PostArc postArc      = rule.addPostArcToK("a", rule.addTransitionToK("a"), rule.addPlaceToK("a"));
        PostArc otherPostArc = rule.fromKtoL(postArc);
        
        rule.setWeightInL(otherPostArc, 97);

        assertEquals(97, otherPostArc.getMark());
        assertEquals(97, postArc.getMark());
        assertEquals(97, rule.fromLtoK(otherPostArc).getMark());
        assertEquals(97, rule.fromLtoR(otherPostArc).getMark());
    }

    @Test
    public void testSetWeightPostArc_4() {
        Rule  rule   = new Rule();
        PostArc postArc  = rule.addPostArcToK("a", rule.addTransitionToK("a"), rule.addPlaceToK("a"));
        
        rule.setWeightInK(postArc, 97);

        assertEquals(97, rule.fromKtoL(postArc).getMark());
        assertEquals(97, postArc.getMark());
        assertEquals(97, rule.fromKtoR(postArc).getMark());
    }

    @Test
    public void testSetWeightPostArc_5() {
        Rule  rule       = new Rule();
        PostArc postArc      = rule.addPostArcToK("a", rule.addTransitionToK("a"), rule.addPlaceToK("a"));
        PostArc otherPostArc = rule.fromKtoR(postArc);
        
        rule.setWeightInR(otherPostArc, 97);

        assertEquals(97, rule.fromRtoL(otherPostArc).getMark());
        assertEquals(97, postArc.getMark());
        assertEquals(97, rule.fromRtoK(otherPostArc).getMark());
        assertEquals(97, otherPostArc.getMark());
    }

    @Test
    public void testSetWeightPostArc_6() {
        Rule  rule       = new Rule();
        PostArc postArc      = rule.addPostArcToR("a", rule.addTransitionToR("a"), rule.addPlaceToR("a"));
        
        rule.setWeightInR(postArc, 97);

        assertEquals(97, rule.fromRtoK(postArc).getMark());
        assertEquals(97, postArc.getMark());
    }

    @Test
    public void testSetWeightPostArc_7() {
        Rule  rule       = new Rule();
        PostArc postArc      = rule.addPostArcToR("a", rule.addTransitionToR("a"), rule.addPlaceToR("a"));
        PostArc otherPostArc = rule.fromRtoK(postArc);
        
        rule.setWeightInK(otherPostArc, 97);

        assertEquals(97, otherPostArc.getMark());
        assertEquals(97, rule.fromKtoR(otherPostArc).getMark());
        assertEquals(97, postArc.getMark());
    }
}





