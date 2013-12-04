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

package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.PetrinetComponent;
import petrinet.model.IArc;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import petrinet.model.rnw.Identity;
import transformation.matcher.*;
import transformation.matcher.PNVF2.MatchException;
import data.MorphismData;

public class CapacityTest {
<<<<<<< Upstream, based on origin/master

	private static Petrinet fromNet;
	private static Petrinet toNet;
	private static Place fromPlace;
	private static Place toPlace;

	@BeforeClass
	public static void setUpOnce() throws Exception {

		fromNet = PetrinetComponent.getPetrinet().createPetrinet();

		fromPlace = fromNet.addPlace("P1");

		Transition t1 = fromNet.addTransition("A", new Identity());
		Transition t2 = fromNet.addTransition("A", new Identity());
		Transition t3 = fromNet.addTransition("A", new Identity());

		fromNet.addPostArc("", t1, fromPlace);
		fromNet.addPostArc("", t2, fromPlace);

		fromNet.addPreArc("", fromPlace, t3);

		fromPlace.setMark(2);

		toNet = PetrinetComponent.getPetrinet().createPetrinet();

		toPlace = toNet.addPlace("P1");

		Transition t11 = toNet.addTransition("A", new Identity());
		Transition t12 = toNet.addTransition("A", new Identity());
		Transition t13 = toNet.addTransition("A", new Identity());

		toNet.addPostArc("", t11, toPlace);
		toNet.addPostArc("", t12, toPlace);

		toNet.addPreArc("", toPlace, t13);

		toPlace.setMark(2);
	}

	@Test
	public void testMatchPlacesWithSameCapacity() throws MatchException {

		// Place fromPlace = fromNet.getPlaces().iterator().next();

		fromPlace.setCapacity(5);
		toPlace.setCapacity(5);

		Match match = PNVF2.getInstance(fromNet, toNet).getMatch(false);

		Place tmpToPlace = match.getPlace(fromPlace);

		assertEquals(tmpToPlace, toPlace);
	}

	@Test(expected = MatchException.class)
	public void testNoMatchOfPlacesWithDifferentSameCapacity()
			throws MatchException {

		fromPlace.setCapacity(5);
		toPlace.setCapacity(6);

		Match match = PNVF2.getInstance(fromNet, toNet).getMatch(false);

=======
	
	private static Petrinet fromNet;
	private static Petrinet toNet;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		
		fromNet = PetrinetComponent.getPetrinet().createPetrinet();
		
		Place p1 = fromNet.addPlace("P1");
		
		Transition t1 = fromNet.addTransition("A", new Identity());
		Transition t2 = fromNet.addTransition("A", new Identity());
		Transition t3 = fromNet.addTransition("A", new Identity());
	
		fromNet.addPostArc("", t1, p1);
		fromNet.addPostArc("", t2, p1);

		fromNet.addPreArc("", p1, t3);
				
		p1.setMark(2);
		
		p1.setCapacity(5);
		
		
		
		toNet = PetrinetComponent.getPetrinet().createPetrinet();
		
		Place p2 = fromNet.addPlace("P1");
		
		Transition t11 = fromNet.addTransition("A", new Identity());
		Transition t12 = fromNet.addTransition("A", new Identity());
		Transition t13 = fromNet.addTransition("A", new Identity());
		
		fromNet.addPostArc("", t11, p2);
		fromNet.addPostArc("", t12, p2);

		fromNet.addPreArc("", p2, t13);
		
		p2.setMark(2);
		
		
>>>>>>> e7ce3b2 start CapacityTest.java
	}

	
	@Test
	public void testPlacesTest() {
//		try {
//			Match match = PNVF2.getInstance(fromNet, toNet).getMatch(false);
//			assertEquals(match.getPlace(place))
//		} catch (MatchException e) {
//			
//			e.printStackTrace();
//		} 
		
	}
	
	
}
