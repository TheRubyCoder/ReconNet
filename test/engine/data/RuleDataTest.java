package engine.data;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.*;

import static org.junit.Assert.*;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

/**
 * @author Mathias Blumreiter
 */
public class RuleDataTest {
    private JungData emptyJungk;
    private JungData emptyJungl;
    private JungData emptyJungr;
    private JungData jungk;
    private JungData jungl;
    private JungData jungr;

    Rule emptyRule;
    Rule rule;
    
	/**
	 * Erstellen eines Netzes
	 */    
	@Before
	public void setUp() throws Exception {	
        emptyRule = TransformationComponent.getTransformation().createRule();
        rule      = TransformationComponent.getTransformation().createRule();
        
        DirectedGraph<INode, IArc> graph = new DirectedSparseGraph<INode, IArc>();
        emptyJungk = new JungData(graph, new StaticLayout<INode, IArc>(graph));
        
        graph = new DirectedSparseGraph<INode, IArc>();
        emptyJungl = new JungData(graph, new StaticLayout<INode, IArc>(graph));
        
        graph = new DirectedSparseGraph<INode, IArc>();
        emptyJungr = new JungData(graph, new StaticLayout<INode, IArc>(graph));
        

        graph = new DirectedSparseGraph<INode, IArc>();
        jungk = new JungData(graph, new StaticLayout<INode, IArc>(graph));
        
        graph = new DirectedSparseGraph<INode, IArc>();
        jungl = new JungData(graph, new StaticLayout<INode, IArc>(graph));
        
        graph = new DirectedSparseGraph<INode, IArc>();
        jungr = new JungData(graph, new StaticLayout<INode, IArc>(graph));

        Place 	   place1      = rule.addPlaceToL("test1");
        Transition transition1 = rule.addTransitionToL("test2");
        rule.addPlaceToK("test3");
        rule.addTransitionToR("test4");
        
        rule.addPreArcToL("test5", place1, transition1);

        buildJung(rule.getL(), jungl);
        buildJung(rule.getK(), jungk);
        buildJung(rule.getR(), jungr);
	}
	
	
	@Test
	public void testData() {
		assertFalse(rule.getK().getPlaces().isEmpty());
		
		RuleData data1 = buildAndTest(1, rule, jungl, jungk, jungr);
		RuleData data2 = buildAndTest(1, emptyRule, emptyJungl, emptyJungk, emptyJungr);
		
		RuleData data3 = buildAndTest(2, rule, jungl, jungk, jungr);		
		RuleData data4 = buildAndTest(2, emptyRule, emptyJungl, emptyJungk, emptyJungr);
		
		assertEquals(data1, data2);
		assertEquals(data3, data4);

		assertFalse(data1.equals(data3));
		assertFalse(data2.equals(data4));
	}
	
	
	private RuleData buildAndTest(int id, Rule rule, JungData lJungData, JungData kJungData, JungData rJungData) {
		RuleData data = new RuleData(id, rule, lJungData, kJungData, rJungData);

		assertEquals(id, data.getId());
		assertEquals(lJungData, data.getLJungData());
		assertEquals(kJungData, data.getKJungData());
		assertEquals(rJungData, data.getRJungData());

		assertNotNull(data.getLJungData());
		assertNotNull(data.getKJungData());
		assertNotNull(data.getRJungData());
		
		return data;
	}

	private void buildJung(Petrinet p, JungData jung) {        
        int y = 1;
        
        for (Place place : p.getPlaces()) {
        	y = y + 100;
        	jung.createPlace(place, new Point(1, y));
        }
        
        for (Transition transition : p.getTransitions()) {
        	y = y + 100;
        	jung.createTransition(transition, new Point(1, y));
        }
        
        for (IArc arc : p.getArcs()) { 
        	if (arc.getSource() instanceof Place) {
        		jung.createArc(arc, (Place) arc.getSource(), (Transition) arc.getTarget());
        	} else {
        		jung.createArc(arc, (Transition) arc.getSource(), (Place) arc.getTarget());
        	}
        }
	}
	
	
	
	

	/**
	 * Test auf Null
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_constructor_1() { 		
		new RuleData(1, null, emptyJungk, emptyJungl, emptyJungr); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_constructor_2() { 
		new RuleData(1, emptyRule, null, emptyJungl, emptyJungr);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_constructor_3() { 
		new RuleData(1, emptyRule, emptyJungk, null, emptyJungr);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_constructor_4() { 
		new RuleData(1, emptyRule, emptyJungk, emptyJungl, null);
	}
	
	
	/**
	 * Test mit Illegalen Parametern
	 */
	
	/**
	 * id zu klein 
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_id_1() { 
		new RuleData(0, emptyRule, emptyJungk, emptyJungl, emptyJungr); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_id_2() { 
		new RuleData(-1,  emptyRule, emptyJungk, emptyJungl, emptyJungr); 
	}
	
	/**
	 * Teilnetze dürfen nicht die selben JungData haben
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungDataSame_1() { 
		new RuleData(1,  emptyRule, emptyJungk, emptyJungk, emptyJungr); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungDataSame_2() { 
		new RuleData(1,  emptyRule, emptyJungk, emptyJungl, emptyJungk); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungDataSame_3() { 
		new RuleData(1,  emptyRule, emptyJungk, emptyJungl, emptyJungl); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungDataSame_4() { 
		new RuleData(1,  emptyRule, emptyJungk, emptyJungk, emptyJungk); 
	}

	
	/**
	 * JungData passt nicht zur Rule
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungData_1() { 
		new RuleData(1,  rule, emptyJungk, jungk, jungr); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungData_2() { 
		new RuleData(1,  rule, jungl, emptyJungl, jungr); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testIllegalArgument_constructor_jungData_3() { 
		new RuleData(1,  rule, jungl, jungk, emptyJungl); 
	}
}
