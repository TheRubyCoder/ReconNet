package engine.data;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.*;

import static org.junit.Assert.*;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;
import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Renews;
import petrinet.Transition;

/**
 * @author Mathias Blumreiter
 */
public class PetrinetDataTest {
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
	}
	
	
	@Test
	public void testData() { 		
		PetrinetData data1 = buildAndTest(1, false, 0, p, jung);
		PetrinetData data2 = buildAndTest(1, true, 1, p, jung);
		
		PetrinetData data3 = buildAndTest(2, false, 0, emptyPetrinet, emptyJung);
		PetrinetData data4 = buildAndTest(2, true, 1, emptyPetrinet, emptyJung);

		assertEquals(data1, data2);
		assertEquals(data3, data4);

		assertFalse(data1.equals(data3));
		assertFalse(data2.equals(data4));
	}
	
	private PetrinetData buildAndTest(int id, boolean isSimulation, int parentId, Petrinet petrinet, JungData jungData) {
		PetrinetData data = new PetrinetData(id, isSimulation, parentId, petrinet, jungData);

		assertEquals(id, data.getId());
		assertEquals(isSimulation, data.isSimulation());
		assertEquals(parentId, data.getParentId());
		assertEquals(petrinet, data.getPetrinet());
		assertEquals(jungData, data.getJungData());

		assertNotNull(data.getPetrinet());
		assertNotNull(data.getJungData());
		
		return data;
	}
	
	
	
	

	/**
	 * Test auf Null
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_constructor_1() { 
		new PetrinetData(1, false, 0, null, emptyJung); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_constructor_2() { 
		new PetrinetData(1, false, 0, emptyPetrinet, null); 
	}
	
	/**
	 * Test mit Illegalen Parametern
	 */
	
	/**
	 * id zu klein 
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_id_1() { 
		new PetrinetData(0, false, 0, emptyPetrinet, emptyJung); 
	}
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_id_2() { 
		new PetrinetData(-1, false, 0, emptyPetrinet, emptyJung); 
	}

	/**
	 * parentId zu klein
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_parentId_1() {
		new PetrinetData(1, true, -1, emptyPetrinet, emptyJung); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_parentId_2() {
		new PetrinetData(1, false, -1, emptyPetrinet, emptyJung); 
	}
	
	/**
	 * Zusammenhang zwischen Simulationsnetz und parentId
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_simulation_1() { 
		new PetrinetData(1, true, 0, emptyPetrinet, emptyJung); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_simulation_2() { 
		new PetrinetData(1, false, 1, emptyPetrinet, emptyJung); 
	}
		
	/**
	 * Jung enthält nicht alle Petrinetz-Elemente 
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_petrinetAndJung_1() { 
		new PetrinetData(1, false, 0, p, emptyJung); 
	}

	/**
	 * Jung enthält zu viele Places 
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_petrinetAndJung_2() { 
		emptyJung.createPlace(place1, pointPositive1);
		new PetrinetData(1, false, 0, emptyPetrinet, emptyJung); 
	}

	/**
	 * Jung enthält zu viele Transitions 
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_petrinetAndJung_3() { 
		emptyJung.createTransition(transition1, pointPositive1);
		new PetrinetData(1, false, 0, emptyPetrinet, emptyJung); 
	}

	/**
	 * Jung enthält zu viele Arcs 
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_petrinetAndJung_4() {
		Place      place      = p.getAllPlaces().iterator().next();
		Transition transition = p.getAllTransitions().iterator().next();
		
		Arc arc = p.createArc("test", place, transition); 
		
		emptyJung.createArc(arc, place, transition);
		
		int count = p.getAllArcs().size();
		
		p.deleteArcByID(arc.getId());
		
		assertEquals(count - 1, p.getAllArcs().size());
		
		new PetrinetData(1, false, 0, p, emptyJung); 
	}
}
