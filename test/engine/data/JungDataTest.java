package engine.data;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.*;

import static org.junit.Assert.*;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;
import engine.jungmodification.JungModification;
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
public class JungDataTest {
	private Petrinet p;
	
    private Place place1;    
    private Transition transition1;
    
    private Arc arc11;
    
    private JungModification jungMod;
    private JungData jungData;
    private DirectedSparseGraph<INode, Arc> graph;
    private StaticLayout<INode, Arc> layout;

    private Point2D pointPositive;
    private Point2D pointPositive22;

	/**
	 * Erstellen eines Netzes
	 */    
	@Before
	public void setUp() throws Exception {	
		p = PetrinetComponent.getPetrinet().createPetrinet();
		
        place1 = p.createPlace("A");

        transition1 = p.createTransition("t1", Renews.COUNT);
        
        arc11 = p.createArc("y1", place1, transition1);
        
        place1.setMark(1);

        graph  = new DirectedSparseGraph<INode, Arc>();
        layout = new StaticLayout<INode, Arc>(graph);
        		
        jungMod  = JungModification.getInstance();
        jungData = new JungData(graph, layout);
        
        pointPositive   = new Point(1, 1);
        pointPositive22 = new Point(2, 2);

        jungMod.createPlace(jungData, place1, pointPositive);
        jungMod.createTransition(jungData, transition1, pointPositive22);
        
        jungMod.createArc(jungData, arc11, place1, transition1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTest_construct_1() { 
		new JungData(null, layout); 
	}

	@Test(expected=IllegalArgumentException.class)
	public void nullTest_construct_2() { 
		new JungData(graph, null); 
	}
	
	@Test
	public void getJungGraph() {
		assertTrue(graph.getVertexCount() > 0 && graph.getEdgeCount() > 0);
		assertTrue((new JungData(graph, layout)).getJungGraph().equals(graph));
	}
	
	@Test
	public void getgetJungLayout() {
		assertTrue(graph.getVertexCount() > 0 && graph.getEdgeCount() > 0);
		assertTrue((new JungData(graph, layout)).getJungLayout().equals(layout));		
	}
}
