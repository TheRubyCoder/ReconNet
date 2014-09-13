/*
 * BSD-Lizenz Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind
 * unter den folgenden Bedingungen zulässig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT;
 * VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
 * and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. Neither the name of the
 * University nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written
 * permission. THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS
 * “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package petrinetze.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import petrinet.PetrinetComponent;
import petrinet.model.IArc;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;

public class PetrinetzTest {

  private Petrinet p;

  private Place place1;
  private Place place2;

  private Transition transition;

  static final int VARIATION = 10;
  // for every day testing so jenkins does not take 5 minutes per build
  static final int TESTS4DAILY = 1000;
  // for serious testing
  static final int TESTS4SERIOUS = 100000000;

  @Before
  public void setUp()
    throws Exception {

    p = PetrinetComponent.getPetrinet().createPetrinet();

    place1 = p.addPlace("place1");
    place2 = p.addPlace("place2");

    transition = p.addTransition("transition", Renews.COUNT);

    p.addPreArc("p1t", place1, transition);
    p.addPostArc("tp2", transition, place2);
  }

  @Test
  public void createPlace() {

    assertEquals(place1.getName(), "place1");
    assertTrue(p.getPlaces().contains(place1));

    assertEquals(place2.getName(), "place2");
    assertTrue(p.getPlaces().contains(place2));
  }

  @Test
  public void createTransition() {

    assertEquals(transition.getName(), "transition");
    assertTrue(p.getTransitions().contains(transition));
    assertEquals(Renews.COUNT, transition.getRnw());
  }

  @Test
  public void createArc() {

    Transition transitionT = p.addTransition("t");
    IArc edge1 = p.addPreArc("edge1", place1, transitionT);
    assertEquals("edge1", edge1.getName());
    assertTrue(p.getArcs().contains(edge1));
    assertEquals(place1, edge1.getSource());
    assertEquals(transitionT, edge1.getTarget());
    assertEquals(1, edge1.getWeight());

    IArc edge2 = p.addPostArc("edge2", transitionT, place2);
    assertEquals("edge2", edge2.getName());
    assertTrue(p.getArcs().contains(edge2));
    assertEquals(transitionT, edge2.getSource());
    assertEquals(place2, edge2.getTarget());
    assertEquals(1, edge2.getWeight());
  }

  @Test
  public void tokenFire() {

    place1.setMark(1);
    assertEquals(1, place1.getMark());
    assertEquals(0, place2.getMark());
    p.fire(transition.getId());
    assertEquals(0, place1.getMark());
    assertEquals(1, place2.getMark());
  }

  @Test
  public void randomTokenFire() {

    final Petrinet petrinet =
      PetrinetComponent.getPetrinet().createPetrinet();

    Place placeP = petrinet.addPlace("p");
    placeP.setMark(1);
    Transition a = petrinet.addTransition("a", Renews.COUNT);
    Transition b = petrinet.addTransition("b", Renews.COUNT);
    petrinet.addPreArc("pa", placeP, a);
    petrinet.addPreArc("pb", placeP, b);
    petrinet.addPostArc("ap", a, placeP);
    petrinet.addPostArc("bp", b, placeP);

    // int times = TESTS4SERIOUS;
    int times = TESTS4DAILY;
    for (int i = 0; i < times; i++) {
      petrinet.fire();
    }

    int distance =
      Math.abs(Integer.parseInt(a.getTlb()) - Integer.parseInt(b.getTlb()));
    System.out.println(a.getTlb());
    System.out.println(b.getTlb());
    assertTrue("Variation must not be greater than 10%", distance < times
      / VARIATION);
  }
}
