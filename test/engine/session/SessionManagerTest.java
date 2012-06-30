package engine.session;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.*;

import static org.junit.Assert.*;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.data.SessionData;
import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Renews;
import petrinet.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

/**
 * @author Mathias Blumreiter
 */
public class SessionManagerTest {
	private Petrinet p;
	private Petrinet emptyPetrinet;
	
    private Place place1;
    private Place place2;
    
    private Transition transition1;
    private Transition transition2; 
    
    private Arc arc11;
    private Arc arc12;
    private Arc arc22;

    private JungData emptyJung;
    private JungData jung;
    
	private DirectedGraph<INode, Arc> graph;
	private AbstractLayout<INode, Arc> layout;

    private Point2D pointPositive1;
    
    SessionManager sessionManager = SessionManager.getInstance();
    

    Rule emptyRule;
    Rule rule;
    
	/**
	 * Erstellen eines Netzes
	 */    
	@Before
	public void setUp() throws Exception {	
		emptyPetrinet = PetrinetComponent.getPetrinet().createPetrinet();
		
		p = PetrinetComponent.getPetrinet().createPetrinet();
		
        place1 = p.createPlace("A");
        place2 = p.createPlace("B");

        transition1 = p.createTransition("t1", Renews.COUNT);
        transition2 = p.createTransition("t2", Renews.COUNT);
        
        arc11 = p.createArc("y1", place1, transition1);

        arc12 = p.createArc("x1", transition1, place2);
        arc22 = p.createArc("x2", transition2, place2);
        
        place1.setMark(1);

        graph    = new DirectedSparseGraph<INode, Arc>();
        layout   = new StaticLayout<INode, Arc>(graph);
        		
        emptyJung = new JungData(graph, layout);
        
        int x = 100;
        int y = 100;
         
        pointPositive1  = new Point(x, y);


    	DirectedGraph<INode, Arc>  graph2    = new DirectedSparseGraph<INode, Arc>();
        AbstractLayout<INode, Arc> layout2   = new StaticLayout<INode, Arc>(graph2);
        		
        jung = new JungData(graph2, layout2);
        
        jung.createPlace(place1, new Point(100, 100));
        jung.createPlace(place2, new Point(100, 200));

        jung.createTransition(transition1, new Point(100, 300));
        jung.createTransition(transition2, new Point(100, 400));

        jung.createArc(arc11, place1, transition1);
        jung.createArc(arc12, transition1, place2);
        jung.createArc(arc22, transition2, place2);
        
        

        emptyRule = TransformationComponent.getTransformation().createRule();
        rule      = TransformationComponent.getTransformation().createRule();

        Place 	   place1      = rule.getL().createPlace("test1");
        Transition transition1 = rule.getL().createTransition("test2");
        rule.getK().createPlace("test3");
        rule.getR().createTransition("test4");
        
        rule.getL().createArc("test5", place1, transition1);
	}
	
	
	@Test
	public void testGetInstance() {
		assertTrue(SessionManager.getInstance() == SessionManager.getInstance());
	}

	@Test
	public void testGetPetrinetData() {
		PetrinetData data = sessionManager.createPetrinetData(emptyPetrinet);
		
		assertEquals(sessionManager.getPetrinetData(data.getId()), data);
	}

	@Test
	public void testGetRuleData() {
		RuleData data = sessionManager.createRuleData(emptyRule);
		
		assertEquals(sessionManager.getRuleData(data.getId()), data);		
	}

	@Test
	public void testGetSessionData() {
		PetrinetData data1 = sessionManager.createPetrinetData(emptyPetrinet);	
		assertEquals(sessionManager.getPetrinetData(data1.getId()), data1);	

		RuleData data2 = sessionManager.createRuleData(emptyRule);		
		assertEquals(sessionManager.getRuleData(data2.getId()), data2);			
	}
	

	@Test
	public void createPetrinetData() {
		PetrinetData data = sessionManager.createPetrinetData(emptyPetrinet);		
		assertNotNull(data);	
	}

	@Test
	public void createRuleData() {
		RuleData data = sessionManager.createRuleData(emptyRule);
		assertNotNull(data);
	}

	public void testGetPetrinetData_exception() {
		PetrinetData data = sessionManager.createPetrinetData(emptyPetrinet);
		
		RuleData ruleData = sessionManager.getRuleData(data.getId());
		
		assertNull(ruleData);
	}

	public void testGetRuleData_exception() {
		RuleData data = sessionManager.createRuleData(emptyRule);

		PetrinetData petrinetData = sessionManager.getPetrinetData(data.getId());
		
		assertNull(petrinetData);
	}
	
	/**
	 * Test auf NULL
	 */	
	@Test(expected=NullPointerException.class) 
	public void testNull_createPetrinetData() {
		sessionManager.createPetrinetData(null);
	}

	@Test(expected=NullPointerException.class) 
	public void testNull_createRuleData() {
		sessionManager.createRuleData(null);
	}
		
	/**
	 * IDs zu klein
	 */
	public void testInvalidArgument_getPetrinetData_1() {
		assertNull(sessionManager.getPetrinetData(0));
	}
	
	public void testInvalidArgument_getPetrinetData_2() {
		assertNull(sessionManager.getPetrinetData(-1));
	}

	
	public void testInvalidArgument_getRuleData_1() {
		assertNull(sessionManager.getRuleData(0));
	}
	
	public void testInvalidArgument_getRuleData_2() {
		assertNull(sessionManager.getRuleData(-1));
	}

	
	public void testInvalidArgument_getSessionData_1() {
		assertNull(sessionManager.getSessionData(0));
	}
	
	public void testInvalidArgument_getSessionData_2() {
		assertNull(sessionManager.getSessionData(-1));
	}
	
	
	/**
	 * data undefiniert
	 */	
	public void testInvalidArgument_getPetrinetData_3() {
		assertNull(sessionManager.getPetrinetData(9999));
	}
	
	public void testInvalidArgument_getRuleData_3() {
		assertNull(sessionManager.getRuleData(9999));
	}
	
	public void testInvalidArgument_getSessionData_3() {
		assertNull(sessionManager.getSessionData(9999));
	}
	
	/**
	 * Netze nicht leer
	 */
	
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArgument_createPetrinetData_nonEmpty() {
		sessionManager.createPetrinetData(p);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArgument_createRuleData_nonEmpty() {
		sessionManager.createRuleData(rule);
	}
}