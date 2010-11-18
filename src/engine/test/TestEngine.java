/**
 * 
 */
package engine.test;
import org.junit.After;
import engine.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import petrinetze.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 * JUnit Testclass for the package "engine" of the WPP RPN in 2010/2011 
 * @author Thorsten Paech
 *
 */
public class TestEngine extends TestCase{
	
	private LayoutEditor layoutEditor;
	private INode node;
	private GraphEditor graphEditor;
	private IPlace place;
	private ITransition trans;
	private IArc arc;
	private Point2D location;
	private Point2D.Float location2;
	private Simulation sim; 
	
	/**
	 * Set the needed classes initial, like nodes, layout editor, etc.
	 * @Todo implementation
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// layoutEditor = new LayoutEditor(); 		//can not work, no implementation of this class jet
		// node = new INode();  					//can not work, no implementation of this class jet
		
	}
	
	/**
	 * Set every needed variable before every test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		location = new Point2D.Float(0,0);
		location2= new Point2D.Float(1,1);
	}
	
	/**
	 * Method to be execute after every test
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		location  = new Point2D.Float(0,0);
		location2 = new Point2D.Float(1,1);
	}

	/**
	 * Method to be execute after the test
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Testing, if the locking methods of the class LayoutEditor are behaving proper
	 * @author Thorsten 
	 */	
	@Test
	public void TestLockingLE(){
		layoutEditor.unlockAll();
		assertTrue(!layoutEditor.isLocked(node));
		layoutEditor.lock(node);
		assertTrue(layoutEditor.isLocked(node));
		layoutEditor.unlockNode(node);
		assertTrue(!layoutEditor.isLocked(node));
		layoutEditor.lock(node); //testing if function is working
		Point2D location= new Point2D.Float(5,4);
		layoutEditor.setPosition(node, location);
		assertTrue(!(layoutEditor.getPosition(node).equals(location))); //Position should not change
		
		
	}
	
	/**
	 * Testing if set/get position of the class LayoutEditor method is working proper
	 * @todo: test "null-positions" and infinity etc.
	 */
	@Test	
	public void TestPostitionLE(){
		layoutEditor.setPosition(node, location);
		location2 = (Float)layoutEditor.getPosition(node);
		assertEquals(location.getX(), location2.getX());
		assertEquals(location.getY(), location2.getY());
		location.setLocation(5, -4);
		layoutEditor.setPosition(node, location);
		assertEquals(location.getX(),5);
		assertEquals(location.getY(),-4);		
	}
	/*
	 * Test for the class GraphEditor
	 */
	@Test
	public void TestGraphEditor(){
		place = graphEditor.createPlace((Float)location);		
		trans = graphEditor.createTransition(location2);
		arc = graphEditor.createIncomingArc(place, trans);
		assertEquals(arc,graphEditor.createIncomingArc(place, trans)); //Test double entry
		arc = graphEditor.createOutgoingArc(trans, place);
		assertEquals(arc,graphEditor.createOutgoingArc(trans, place)); //Test double entry
			
	}
	
	/*
	 * Test for the "callback" method in class StepListener	
	 * @todo: wait for more implementation
	 */
	@Ignore 
	@Test
	public void TestStepListerner(){
		
	}
	/*
	 * Test for the class Simulation
	 * @todo: wait for more implementation
	 */
	@Ignore 
	@Test
	public void TestSimulation(){
		long x = 1000000000;
		sim.start(x);
		assertTrue(sim.isRunning());
		sim.stop();
		assertTrue(!sim.isRunning());
		x = 0;
		sim.start(x);
		assertTrue(!sim.isRunning());		
		
	}
	


}
