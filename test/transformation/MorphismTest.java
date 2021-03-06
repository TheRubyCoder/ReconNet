/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
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
 * MOEGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
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
 * AS IS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
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

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.PetrinetComponent;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

import transformation.matcher.PNVF2;
import transformation.matcher.Ullmann;
import transformation.matcher.PNVF2.MatchException;

/**
 * @author Niklas
 */
public class MorphismTest {

  private static Petrinet fromPn;
  private static Petrinet toPn;

  private static Place[] from_p;
  private static Place[] to_p;
  private static Transition[] from_t;
  private static Transition[] to_t;
  private static PreArc[] from_pre_a;
  private static PreArc[] to_pre_a;
  private static PostArc[] from_post_a;
  private static PostArc[] to_post_a;

  private Match testObject;

  private static Map<Place, Place> expectedPlaceMap;
  private static Map<Transition, Transition> expectedTransitionMap;
  private static Map<PreArc, PreArc> expectedPreArcMap;
  private static Map<PostArc, PostArc> expectedPostArcMap;

  @BeforeClass
  public static void setUpClass()
    throws Exception {

  }

  @AfterClass
  public static void tearDownClass()
    throws Exception {

  }

  @Before
  public void setUp() {

    setupPetrinetFrom();
    setupPetrinetTo();
    setupExpectedResults();

    try {
      testObject = PNVF2.getInstance(fromPn, toPn).getMatch(false);
    } catch (MatchException e) {
      fail();
    }
  }

  @After
  public void tearDown() {

  }

  @Test
  public void testEqualMatches() {

    Match matchA;
    try {
      matchA = PNVF2.getInstance(fromPn, toPn).getMatch(false);
      Match matchB = Ullmann.createMatch(fromPn, toPn);

      assertEquals(matchA, matchB);
    } catch (MatchException e) {
      fail();
    }
  }

  /**
   * Test of places method, of class Morphism.
   */
  @Test
  public void testPlaces() {

    assertEquals(expectedPlaceMap, testObject.getPlaces());
  }

  /**
   * Test of transitions method, of class Morphism.
   */
  @Test
  public void testTransitions() {

    assertEquals(expectedTransitionMap, testObject.getTransitions());
  }

  /**
   * Test of edges method, of class Morphism.
   */
  @Test
  public void testEdges() {

    assertEquals(expectedPreArcMap, testObject.getPreArcs());
    assertEquals(expectedPostArcMap, testObject.getPostArcs());
  }

  /**
   * Test of morph method, of class Morphism.
   */
  @Test
  public void testMorph_Transition() {

    Set<Map.Entry<Transition, Transition>> expected =
      expectedTransitionMap.entrySet();

    for (Map.Entry<Transition, Transition> entry : expected) {
      assertEquals(entry.getValue(), testObject.getTransition(entry.getKey()));
    }
  }

  /**
   * Test of morph method, of class Morphism.
   */
  @Test
  public void testMorph_Place() {

    for (Map.Entry<Place, Place> entry : expectedPlaceMap.entrySet()) {
      assertEquals(entry.getValue(), testObject.getPlace(entry.getKey()));
    }
  }

  /**
   * Test of morph method, of class Morphism.
   */
  @Test
  public void testMorph_Arc() {

    for (Map.Entry<PreArc, PreArc> entry : expectedPreArcMap.entrySet()) {
      assertEquals(entry.getValue(), testObject.getPreArc(entry.getKey()));
    }

    for (Map.Entry<PostArc, PostArc> entry : expectedPostArcMap.entrySet()) {
      assertEquals(entry.getValue(), testObject.getPostArc(entry.getKey()));
    }
  }

  /**
   * Test of From method, of class Morphism.
   */
  @Test
  public void testFrom() {

    assertEquals(fromPn, testObject.getSource());
  }

  /**
   * Test of To method, of class Morphism.
   */
  @Test
  public void testTo() {

    assertEquals(toPn, testObject.getTarget());
  }

  private void setupPetrinetFrom() {

    fromPn = PetrinetComponent.getPetrinet().createPetrinet();

    // CHECKSTYLE:OFF - No need to check for magic numbers
    from_p = new Place[6];
    from_t = new Transition[2];
    from_pre_a = new PreArc[2];
    from_post_a = new PostArc[2];

    from_p[0] = fromPn.addPlace("Wecker Ein");
    from_p[0].setCapacity(5);
    from_p[1] = fromPn.addPlace("");
    from_p[1].setCapacity(6);
    from_p[2] = fromPn.addPlace("Wecker Ein");
    from_p[2].setCapacity(7);
    from_p[2].setMark(1);
    from_p[3] = fromPn.addPlace("Wecker Aus");
    from_p[3].setCapacity(8);
    from_p[4] = fromPn.addPlace("");
    from_p[4].setCapacity(9);
    from_p[5] = fromPn.addPlace("Wecker Aus");
    from_p[5].setCapacity(10);

    from_t[0] = fromPn.addTransition("");
    from_t[1] = fromPn.addTransition("");

    from_pre_a[0] = fromPn.addPreArc("", from_p[0], from_t[0]);
    from_pre_a[1] = fromPn.addPreArc("", from_p[4], from_t[1]);

    from_post_a[0] = fromPn.addPostArc("", from_t[0], from_p[1]);
    from_post_a[1] = fromPn.addPostArc("", from_t[1], from_p[5]);
    // CHECKSTYLE:ON
  }

  private void setupPetrinetTo() {

    // CHECKSTYLE:OFF - No need to check for magic numbers
    toPn = PetrinetComponent.getPetrinet().createPetrinet();

    to_p = new Place[10];
    to_t = new Transition[9];
    to_pre_a = new PreArc[9];
    to_post_a = new PostArc[9];

    to_p[0] = toPn.addPlace("Wecker Ein");
    to_p[0].setCapacity(5);
    to_p[1] = toPn.addPlace("Aufstehen");
    to_p[1].setMark(1);
    to_p[2] = toPn.addPlace("");
    to_p[2].setCapacity(6);
    to_p[3] = toPn.addPlace("Wecker Ein");
    to_p[3].setCapacity(7);
    to_p[3].setMark(1);
    to_p[4] = toPn.addPlace("Wecker Aus");
    to_p[4].setCapacity(8);
    to_p[5] = toPn.addPlace("");
    to_p[5].setCapacity(9);
    to_p[6] = toPn.addPlace("Wecker Aus");
    to_p[6].setCapacity(10);
    to_p[7] = toPn.addPlace("Badezimmer");
    to_p[8] = toPn.addPlace("Küche");
    to_p[9] = toPn.addPlace("");

    to_t[0] = toPn.addTransition("");
    to_t[1] = toPn.addTransition("Mit Wecker");
    to_t[2] = toPn.addTransition("Von Allein");
    to_t[3] = toPn.addTransition("snooze");
    to_t[4] = toPn.addTransition("klingelt");
    to_t[5] = toPn.addTransition("");
    to_t[6] = toPn.addTransition("");
    to_t[7] = toPn.addTransition("");
    to_t[8] = toPn.addTransition("");

    to_pre_a[0] = toPn.addPreArc("", to_p[0], to_t[0]);
    to_pre_a[1] = toPn.addPreArc("", to_p[2], to_t[4]);
    to_pre_a[2] = toPn.addPreArc("", to_p[5], to_t[3]);
    to_pre_a[3] = toPn.addPreArc("", to_p[5], to_t[5]);
    to_pre_a[4] = toPn.addPreArc("", to_p[1], to_t[1]);
    to_pre_a[5] = toPn.addPreArc("", to_p[1], to_t[2]);
    to_pre_a[6] = toPn.addPreArc("", to_p[4], to_t[8]);
    to_pre_a[7] = toPn.addPreArc("", to_p[9], to_t[6]);
    to_pre_a[8] = toPn.addPreArc("", to_p[9], to_t[7]);

    to_post_a[0] = toPn.addPostArc("", to_t[0], to_p[2]);
    to_post_a[1] = toPn.addPostArc("", to_t[4], to_p[5]);
    to_post_a[2] = toPn.addPostArc("", to_t[3], to_p[2]);
    to_post_a[3] = toPn.addPostArc("", to_t[5], to_p[6]);
    to_post_a[4] = toPn.addPostArc("", to_t[1], to_p[3]);
    to_post_a[5] = toPn.addPostArc("", to_t[2], to_p[9]);
    to_post_a[6] = toPn.addPostArc("", to_t[8], to_p[9]);
    to_post_a[7] = toPn.addPostArc("", to_t[6], to_p[7]);
    to_post_a[8] = toPn.addPostArc("", to_t[7], to_p[8]);
    // CHECKSTYLE:ON
  }

  private void setupExpectedResults() {

    expectedPlaceMap = new HashMap<Place, Place>();
    expectedTransitionMap = new HashMap<Transition, Transition>();
    expectedPreArcMap = new HashMap<PreArc, PreArc>();
    expectedPostArcMap = new HashMap<PostArc, PostArc>();

    // CHECKSTYLE:OFF - No need to check for magic numbers
    expectedPlaceMap.put(from_p[0], to_p[0]);
    expectedPlaceMap.put(from_p[1], to_p[2]);
    expectedPlaceMap.put(from_p[2], to_p[3]);
    expectedPlaceMap.put(from_p[3], to_p[4]);
    expectedPlaceMap.put(from_p[4], to_p[5]);
    expectedPlaceMap.put(from_p[5], to_p[6]);

    expectedTransitionMap.put(from_t[0], to_t[0]);
    expectedTransitionMap.put(from_t[1], to_t[5]);

    expectedPreArcMap.put(from_pre_a[0], to_pre_a[0]);
    expectedPreArcMap.put(from_pre_a[1], to_pre_a[3]);

    expectedPostArcMap.put(from_post_a[0], to_post_a[0]);
    expectedPostArcMap.put(from_post_a[1], to_post_a[3]);
    // CHECKSTYLE:ON
  }

  /**
   * Test of Capacity use in Morphism
   */
  @Test(expected = MatchException.class)
  public void testCapacity()
    throws MatchException {

    final int capacity = 2;

    Match matchA;
    Petrinet from = PetrinetComponent.getPetrinet().createPetrinet();
    Place p1 = from.addPlace("Place");

    Petrinet to = PetrinetComponent.getPetrinet().createPetrinet();
    Place p2 = to.addPlace("Place");

    try {
      matchA = PNVF2.getInstance(from, to).getMatch(false);
      Match matchB = Ullmann.createMatch(from, to);

      assertEquals(matchA.getPlace(p1), p2);
      assertEquals(matchA, matchB);

    } catch (MatchException e) {
      fail();
    }

    p1.setCapacity(capacity);

    matchA = PNVF2.getInstance(from, to).getMatch(false);

  }

}
