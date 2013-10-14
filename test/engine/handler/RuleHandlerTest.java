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

package engine.handler;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Place;

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
			petrinet.model.Place place1 = foo.createPlace(id, RuleNet.R, new Point2D.Double(10,10));
			petrinet.model.Transition transition1 = foo.createTransition(id, RuleNet.R, new Point2D.Double(50,50));
			petrinet.model.PreArc arc1 = foo.createPreArc(id, RuleNet.R, place1, transition1);
			petrinet.model.Place place2 = foo.createPlace(id, RuleNet.K, new Point2D.Double(100,10));
			petrinet.model.Transition transition2 = foo.createTransition(id, RuleNet.K, new Point2D.Double(100,50));
			petrinet.model.PostArc arc2 = foo.createPostArc(id, RuleNet.K, transition2, place2);
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
