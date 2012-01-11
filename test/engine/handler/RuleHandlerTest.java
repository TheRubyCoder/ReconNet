package engine.handler;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;

import engine.handler.rule.RuleManipulation;
import engine.handler.rule.RulePersistence;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;

public class RuleHandlerTest {

	@Test
	public void testCreateArc() {
		RulePersistence foo = RulePersistence.getInstance();

		int id = foo.createRule();

		try {
			INode place1 = foo.createPlace(id, RuleNet.R, new Point2D.Double(10,10));
			INode transition1 = foo.createTransition(id, RuleNet.R, new Point2D.Double(50,50));
			Arc arc1 = foo.createArc(id, RuleNet.R, place1, transition1);
			INode place2 = foo.createPlace(id, RuleNet.K, new Point2D.Double(100,10));
			INode transition2 = foo.createTransition(id, RuleNet.K, new Point2D.Double(100,50));
			Arc arc2 = foo.createArc(id, RuleNet.K, transition2, place2);
			//foo.createArc(id, RuleNet.K, place2, transition2);
			foo.setPname(id, place1, "Place in R");
			foo.setPname(id, place2, "Place in K");
			foo.setMarking(id, place1, 42);
			foo.setMarking(id, place2, 666);
			foo.setTlb(id, transition1, "Tlb in R");
			foo.setTlb(id, transition2, "Tlb in K");
			foo.setTname(id, transition1, "Transition in R");
			foo.setTname(id, transition2, "Transition in K");
			foo.setWeight(id, arc1, 42);
			foo.setWeight(id, arc2, 666);
		} catch (EngineException e) {
			System.out.println("---------------------------------");
			e.printStackTrace();
		}

		//foo.bla(id);
		// Just Test Stub
	}

	@Test
	public void testCreatePlace() {
		// Just Test Stub
	}

	@Test
	public void testCreateTransition() {
		// Just Test Stub
	}

	@Test
	public void testDeleteArc() {
		// Just Test Stub
	}

	@Test
	public void testDeletePlace() {
		// Just Test Stub
	}

	@Test
	public void testDeleteTransition() {
		// Just Test Stub
	}

	@Test
	public void testGetArcAttribute() {
		// Just Test Stub
	}

	@Test
	public void testGetJungLayout() {
		// Just Test Stub
	}

	@Test
	public void testGetPlaceAttribute() {
		// Just Test Stub
	}

	@Test
	public void testGetTransitionAttribute() {
		// Just Test Stub
	}

	@Test
	public void testGetRuleAttribute() {
		// Just Test Stub
	}

	@Test
	public void testMoveNode() {
		// Just Test Stub
	}

	@Test
	public void testSave() {
		// Just Test Stub
	}

	@Test
	public void testLoad() {
		// Just Test Stub
	}

	@Test
	public void testSetMarking() {
		// Just Test Stub
	}

	@Test
	public void testSetPname() {
		// Just Test Stub
	}

	@Test
	public void testSetTlb() {
		// Just Test Stub
	}

	@Test
	public void testSetTname() {
		// Just Test Stub
	}

	@Test
	public void testSetWeight() {
		// Just Test Stub
	}

	@Test
	public void testSetRnw() {
		// Just Test Stub
	}

	@Test
	public void testSetPlaceColor() {
		// Just Test Stub
	}

	@Test
	public void testGetNodeType() {
		// Just Test Stub
	}

}
