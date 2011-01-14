/**
 * 
 */
package engine.impl;
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
import petrinetze.impl.Petrinet;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 * JUnit Testclass for the package "engine" of the WPP RPN in 2010/2011 
 * @author Thorsten Paech
 *
 */
public class EngineTest extends TestCase{

    private IPetrinet petrinet;

    private Engine engine;

    private INode node;

    private IPlace place;

    private ITransition trans;

    private IArc arc;

    private Point2D location;

    private Point2D location2;

	/**
	 * Set every needed variable before every test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        petrinet = new Petrinet();
        engine = EngineFactory.newFactory().createEngine(petrinet);
		location = new Point2D.Float(0,0);
		location2= new Point2D.Float(1,1);
        node = petrinet.createPlace("test");
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
	public void testLockingLE(){
		engine.getLayoutEditor().unlockAll();
		assertTrue(!engine.getLayoutEditor().isLocked(node));
		engine.getLayoutEditor().lock(node);
		assertTrue(engine.getLayoutEditor().isLocked(node));
		engine.getLayoutEditor().unlockNode(node);
		assertTrue(!engine.getLayoutEditor().isLocked(node));
		/*

		This part of the test is actually wrong: locking of a node does not prevent manual
		altering of positions but only tells the layout manager not to change an INode's position.

		engine.getLayoutEditor().lock(node); //testing if function is working
		Point2D location= new Point2D.Float(5,4);
		engine.getLayoutEditor().setPosition(node, location);
		assertTrue(!(engine.getLayoutEditor().getPosition(node).equals(location))); //Position should not change
		*/
	}
	
	/**
	 * Testing if set/get position of the class LayoutEditor method is working proper
	 * @todo: test "null-positions" and infinity etc.
	 */
	@Test	
	public void testPostitionLE(){
		engine.getLayoutEditor().setPosition(node, location);
		location2 = engine.getLayoutEditor().getPosition(node);
		assertEquals(location.getX(), location2.getX());
		assertEquals(location.getY(), location2.getY());
		location.setLocation(5, -4);
		engine.getLayoutEditor().setPosition(node, location);
		assertEquals(location.getX(), 5.0);
		assertEquals(location.getY(), -4.0);
	}
	/*
	 * Test for the class GraphEditor
	 */
	@Test
	public void testGraphEditor(){
		place = engine.getGraphEditor().createPlace(location);
		trans = engine.getGraphEditor().createTransition(location2);
		arc = engine.getGraphEditor().createArc(place, trans);
    }

    /**
     * Creating duplicate edges should throw an <tt>IllegalArgumentException</tt>.
     *
     * <p>
     * <b>Remark</b><br>
     *  The former implementation of this test case implied that it made sense to have the createArc()-method
     *  return an existing edge in the case it existed. This was not the case in the implementetation of the
     *  corresponding method in the Petrinet class and would not be what you expect if you <b>create</be> an
     *  edge.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void duplicateEdgesShouldNotBeAllowed() {
        place = engine.getGraphEditor().createPlace(location);
		trans = engine.getGraphEditor().createTransition(location2);
        engine.getGraphEditor().createArc(place, trans);
        engine.getGraphEditor().createArc(place, trans);
	}
	
	/*
	 * Test for the "callback" method in class StepListener	
	 * @todo: wait for more implementation
	 */
	@Ignore 
	@Test
	public void testStepListerner(){
		
	}
	/*
	 * Test for the class Simulation
	 * @todo: wait for more implementation
	 */
	@Ignore
	@Test
	public void testSimulation(){
        final Simulation sim = engine.getSimulation();
		long x = 100;
		sim.start(x);
		assertTrue(sim.isRunning());
		sim.stop();
		assertTrue(!sim.isRunning());
        /*
        TODO: While it does not make any sense to set the simulation delay to zero it is actually allowed.

        The delay is internally passed to a javax.swing.Timer instance.

		x = 0;
		sim.start(x);
		assertTrue(!sim.isRunning());
		*/
	}
}
