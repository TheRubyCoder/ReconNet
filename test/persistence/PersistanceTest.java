package persistence;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Ignore;
import org.junit.Test;

import petrinet.INode;
import petrinet.Petrinet;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.EngineMockupForPersistence;
import engine.attribute.NodeLayoutAttribute;
import engine.handler.RuleNet;
import engine.handler.petrinet.PetrinetHandler;
import engine.handler.petrinet.PetrinetPersistence;
import engine.handler.rule.RuleHandler;
import engine.handler.rule.RulePersistence;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;

public class PersistanceTest {
	

	@Test
	public void testExamplePNMLParsing() {
		Pnml pnml = new Pnml();
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(persistence.Pnml.class,
					Arc.class, Converter.class, Dimension.class,
					Graphics.class, InitialMarking.class, Name.class,
					Net.class, Page.class, Place.class, PlaceName.class,
					Position.class, Transition.class, TransitionLabel.class,
					TransitionName.class, TransitionRenew.class);
			Unmarshaller m = context.createUnmarshaller();

			m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			pnml = (Pnml) m
					.unmarshal(new File("test/persistence/example.pnml"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		// TODO: enable code again if internal API is fully implemented; see engine.session.SessionManager.createPetrinetData(SessionManager.java:80)
		/*
		PetrinetManipulation petriManipulation = PetrinetManipulation.getInstance();
		
		boolean success = Converter.convertToPetrinet(pnml, petriManipulation);
		assertEquals(true, success);
		*/
		
		// TODO: fix this after the interface disaster is fixed.
		
		/*
		Set<petrinet.Place> places = petrinet.getAllPlaces();
		assertEquals(1, places.size());
		
		petrinet.Place place = places.iterator().next();
		assertEquals(2, place.getMark());
		assertEquals("myplace", place.getName());
		
		petrinet.Transition transition = petrinet.getAllTransitions().iterator().next();
		assertEquals("mytrans", transition.getName());*/
		
	}
	
	
	@Test
	public void testSavePetrinet() {
		EngineMockupForPersistence mockup = new EngineMockupForPersistence();
		
		try {
			int pid = mockup.build();
			mockup.saveTest(pid, "/tmp", "petrinet_save_test", "pnml");
			
		} catch (EngineException e) {
			e.printStackTrace();
		}
	
		assertTrue(new File("/tmp/petrinet_save_test.pnml").exists());
	}
	
	@Test
	public void testLoadRule() {
		IRulePersistence rulePersistence=RulePersistence.getInstance();
		
		int id=Persistence.loadRule("test/persistence/exampleRule.rnml", rulePersistence);
		assert(id>-1);
		
	}
	
	@Test
	@Ignore
	public void testSaveRule() throws EngineException{
		RuleHandler handler = RuleHandler.getInstance();
		
	//	try {
			
			int id=handler.createRule();
			INode place1=handler.createPlace(id, RuleNet.K, new Point2D.Double(10,10));
			INode place3=handler.createPlace(id, RuleNet.R, new Point2D.Double(10,1000));
			
	
			System.out.println("size:"+handler.getJungLayout(id, RuleNet.K).getGraph().getVertices().size());
			
			handler.setPlaceColor(id, place1, new java.awt.Color(255,0,0));
			handler.setPlaceColor(id, place3, new java.awt.Color(0,255,0));
			
			INode trans2=handler.createTransition(id, RuleNet.R, new Point2D.Double(10,500));
			
			handler.createArc(id, RuleNet.R, place1, trans2);
			handler.createArc(id, RuleNet.R, trans2, place3);
			
			
			handler.save(id, "test", "rule_save_test", "pnml");
			
	/*	} catch (EngineException e) {
			e.printStackTrace();
		}*/
	
		assertTrue(new File("test/rule_save_test.pnml").exists());
	}
	

	@Test
	public void testPetrinetSaveLoadEquality() throws Exception {
		EngineMockupForPersistence mockup = new EngineMockupForPersistence();
		
		// save
		int pid = -1;
		//try {
			pid = mockup.build();
			Petrinet saved = mockup.getPetrinet(pid);
			
			mockup.saveTest(pid, "/tmp", "petrinet_saveload_test", "pnml");
			int loaded_pid = mockup.load("/tmp", "petrinet_saveload_test.pnml");
			if (loaded_pid == -1) fail("failed to load/parse petrinet");
			Petrinet loaded = mockup.getPetrinet(loaded_pid);
			AbstractLayout layout = mockup.getJungLayout(loaded_pid);
			assertTrue(petrinetEqualsBasedOnLayout(mockup, saved, pid, mockup.getJungLayout(pid), loaded, loaded_pid, mockup.getJungLayout(loaded_pid)));
			/*
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}*/
	}

	public static boolean petrinetEqualsBasedOnLayout(EngineMockupForPersistence mockup, petrinet.Petrinet net1, int net1_pid, AbstractLayout layout1, petrinet.Petrinet net2, int net2_pid, AbstractLayout layout2) throws EngineException {
		Map<Point2D, petrinet.Place> pos2place = new HashMap<Point2D, petrinet.Place>();
		Map<Point2D, petrinet.Transition> pos2trans = new HashMap<Point2D, petrinet.Transition>();
		
		for (petrinet.Place p :net2.getAllPlaces()) {
			Point2D pos = new Point2D.Double(layout2.getX(p), layout2.getY(p));
			pos2place.put(pos, p);
		}
		
		for (petrinet.Transition t :net2.getAllTransitions()) {
			Point2D pos = new Point2D.Double(layout2.getX(t), layout2.getY(t));
			pos2trans.put(pos, t);
		}
		
		// test that all places and transitions of net2 are at the same locations in net1
		for (petrinet.Place p :net1.getAllPlaces()) {
			Point2D pos = new Point2D.Double(layout1.getX(p), layout1.getY(p));
			if (pos2place.containsKey(pos)) {
				petrinet.Place net2Place = pos2place.get(pos);
				
				if (!net2Place.getName().equals(p.getName())) return false;
				if (net2Place.getMark() != p.getMark()) return false;
				
				java.awt.Color net2PlaceColor = mockup.getPlaceAttribute(net2_pid, net2Place).getColor();
				java.awt.Color net1PlaceColor = mockup.getPlaceAttribute(net1_pid, p).getColor();
				if (!net1PlaceColor.equals(net2PlaceColor)) return false;
				if (!mockup.getPlaceAttribute(net2_pid, net2Place).getPname().equals(mockup.getPlaceAttribute(net1_pid, p).getPname())) return false;
				if (mockup.getPlaceAttribute(net2_pid, net2Place).getMarking() != (mockup.getPlaceAttribute(net1_pid, p).getMarking())) return false;
			} else {
				return false;
			}
		}
		
		for (petrinet.Transition t :net1.getAllTransitions()) {
			Point2D pos = new Point2D.Double(layout1.getX(t), layout1.getY(t));
			if (pos2trans.containsKey(pos)) {
				petrinet.Transition net2Transition = pos2trans.get(pos);
				
				if (!net2Transition.getName().equals(t.getName())) return false;
				if (!net2Transition.getTlb().equals(t.getTlb())) return false;
			} else {
				return false;
			}
		}
		
		// test the arcs
		for (petrinet.Arc arcIn1 :net1.getAllArcs()) {
			Point2D startA = new Point2D.Double(layout1.getX(arcIn1.getStart()), layout1.getX(arcIn1.getStart()));
			Point2D endA = new Point2D.Double(layout1.getX(arcIn1.getEnd()), layout1.getX(arcIn1.getEnd()));
			
			boolean found = false;
			for (petrinet.Arc arcIn2 :net2.getAllArcs()) {
				Point2D startB = new Point2D.Double(layout2.getX(arcIn2.getStart()), layout2.getX(arcIn2.getStart()));
				Point2D endB = new Point2D.Double(layout2.getX(arcIn2.getEnd()), layout2.getX(arcIn2.getEnd()));
				
				if (startA.equals(startB) && endA.equals(endB)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
}
