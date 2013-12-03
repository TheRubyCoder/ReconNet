/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

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
import petrinet.PetrinetComponent;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;
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
    
    private IArc arc11;
    private IArc arc12;
    private IArc arc22;

    private JungData emptyJung;
    private JungData jung;
    
	private DirectedGraph<INode, IArc> graph;
	private AbstractLayout<INode, IArc> layout;

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
		
        place1 = p.addPlace("A");
        place2 = p.addPlace("B");

        transition1 = p.addTransition("t1", Renews.COUNT);
        transition2 = p.addTransition("t2", Renews.COUNT);
        
        arc11 = p.addPreArc("y1", place1, transition1);

        arc12 = p.addPostArc("x1", transition1, place2);
        arc22 = p.addPostArc("x2", transition2, place2);
        
        place1.setMark(1);
        
        place1.setCapacity(5);

        graph    = new DirectedSparseGraph<INode, IArc>();
        layout   = new StaticLayout<INode, IArc>(graph);
        		
        emptyJung = new JungData(graph, layout);
        
        int x = 100;
        int y = 100;
         
        pointPositive1  = new Point(x, y);


    	DirectedGraph<INode, IArc>  graph2    = new DirectedSparseGraph<INode, IArc>();
        AbstractLayout<INode, IArc> layout2   = new StaticLayout<INode, IArc>(graph2);
        		
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

        Place 	   place1      = rule.addPlaceToL("test1");
        Transition transition1 = rule.addTransitionToL("test2");
        rule.addPlaceToK("test3");
        rule.addTransitionToR("test4");
        
        rule.addPreArcToL("test5", place1, transition1);
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