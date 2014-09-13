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

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.model.IArc;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Renews;
import petrinet.model.Transition;

/**
 * @author Niklas, Philipp K�hn
 */
public class RuleTest {

  private Rule rule1;
  static final int NUMBER_OF_MARKS = 97;
  static final int WEIGHT = 97;
  static final int CAPACITY = 5;

  public RuleTest() {

  }

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

    rule1 = new Rule();
    // L von r1
    Place p1 = rule1.addPlaceToL("Wecker ein");
    Place p2 = rule1.addPlaceToK("Wecker ein");
    rule1.setMarkInK(p2, 1);
    Place p3 = rule1.addPlaceToK("");
    Place p4 = rule1.addPlaceToK("");
    Place p5 = rule1.addPlaceToK("Wecker aus");
    Place p6 = rule1.addPlaceToL("Wecker aus");

    Transition t1 = rule1.addTransitionToL("", Renews.COUNT);

    rule1.addPreArcToL("", p1, t1);
    rule1.addPostArcToL("", t1, rule1.fromKtoL(p4));

    Transition t2 = rule1.addTransitionToL("", Renews.COUNT);

    rule1.addPreArcToL("", rule1.fromKtoL(p3), t2);
    rule1.addPostArcToL("", t2, p6);

    Transition t3 = rule1.addTransitionToR("", Renews.COUNT);

    rule1.addPreArcToR("", rule1.fromKtoR(p2), t3);
    rule1.addPostArcToR("", t3, rule1.fromKtoR(p4));

    Transition t4 = rule1.addTransitionToR("", Renews.COUNT);

    rule1.addPreArcToR("", rule1.fromKtoR(p3), t4);
    rule1.addPostArcToR("", t4, rule1.fromKtoR(p5));

  }

  @After
  public void tearDown() {

  }

  /**
   * Test of fromLtoK method, of class Rule.
   */
  @Test
  public void testFromLtoK_INode() {

    System.out.println("fromLtoK");
    Rule instance = new Rule();
    Place place = instance.addPlaceToL("a");
    instance.setCapacityInL(place, CAPACITY);

    assertEquals(place.getName(), instance.fromLtoK(place).getName());
    assertEquals(place.getName(),
      instance.fromKtoL(instance.fromLtoK(place)).getName());

    assertEquals(place.getMark(), instance.fromLtoK(place).getMark());
    assertEquals(place.getMark(),
      instance.fromKtoL(instance.fromLtoK(place)).getMark());

    assertEquals(place.getCapacity(), instance.fromLtoK(place).getCapacity());
    assertEquals(place.getCapacity(), instance.fromKtoL(
      instance.fromLtoK(place)).getCapacity());

    assertNull(instance.fromKtoR(instance.fromLtoK(place)));
    assertNull(instance.fromLtoR(place));
  }

  /**
   * Test of fromLtoK method, of class Rule.
   */
  @Test
  public void testFromLtoK_Arc() {

    System.out.println("fromLtoK");
    Rule instance = new Rule();
    Transition node = instance.addTransitionToL("a", Renews.COUNT);

    assertEquals(node.getName(), instance.fromLtoK(node).getName());
    assertEquals(node.getName(),
      instance.fromKtoL(instance.fromLtoK(node)).getName());

    assertEquals(node.getRnw(), instance.fromLtoK(node).getRnw());
    assertEquals(node.getRnw(),
      instance.fromKtoL(instance.fromLtoK(node)).getRnw());

    assertEquals(node.getTlb(),
      instance.fromKtoL(instance.fromLtoK(node)).getTlb());
    assertEquals(node.getTlb(),
      instance.fromKtoL(instance.fromLtoK(node)).getTlb());

    assertNull(instance.fromKtoR(instance.fromLtoK(node)));
    assertNull(instance.fromLtoR(node));
  }

  /**
   * Test of fromRtoK method, of class Rule.
   */
  @Test
  public void testFromRtoK_INode() {

    System.out.println("fromRtoK");
    Rule instance = new Rule();
    Place place = instance.addPlaceToR("a");
    instance.setCapacityInR(place, CAPACITY);

    assertEquals(place.getName(), instance.fromRtoK(place).getName());
    assertEquals(place.getName(),
      instance.fromKtoR(instance.fromRtoK(place)).getName());

    assertEquals(place.getMark(),
      instance.fromKtoR(instance.fromRtoK(place)).getMark());
    assertEquals(place.getMark(),
      instance.fromKtoR(instance.fromRtoK(place)).getMark());

    assertEquals(place.getCapacity(), instance.fromKtoR(
      instance.fromRtoK(place)).getCapacity());
    assertEquals(place.getCapacity(), instance.fromKtoR(
      instance.fromRtoK(place)).getCapacity());

    assertNull(instance.fromKtoL(instance.fromLtoK(place)));
    assertNull(instance.fromRtoL(place));
  }

  /**
   * Test of fromRtoK method, of class Rule.
   */
  @Test
  public void testFromRtoK_Arc() {

    System.out.println("fromRtoK");

    Rule instance = TransformationComponent.getTransformation().createRule();
    Transition node = instance.addTransitionToR("a", Renews.COUNT);

    assertEquals(node.getName(), instance.fromRtoK(node).getName());
    assertEquals(node.getName(),
      instance.fromKtoR(instance.fromRtoK(node)).getName());

    assertEquals(node.getRnw(), instance.fromRtoK(node).getRnw());
    assertEquals(node.getRnw(),
      instance.fromKtoR(instance.fromRtoK(node)).getRnw());

    assertEquals(node.getTlb(),
      instance.fromKtoR(instance.fromRtoK(node)).getTlb());
    assertEquals(node.getTlb(),
      instance.fromKtoR(instance.fromRtoK(node)).getTlb());

    assertNull(instance.fromKtoL(instance.fromLtoK(node)));
    assertNull(instance.fromRtoL(node));
  }

  @Test
  public void testPlaces() {

    System.out.println("Places");
    Set<Place> k = rule1.getK().getPlaces();
    Set<Place> l = rule1.getL().getPlaces();
    Set<Place> r = rule1.getR().getPlaces();

    for (Place place : r) {
      assertTrue(k.contains(rule1.fromRtoK(place)));

      if (rule1.fromRtoL(place) != null) {
        assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL(place))));
      }
    }

    for (Place place : l) {
      assertTrue(k.contains(rule1.fromLtoK(place)));

      if (rule1.fromLtoR(place) != null) {
        assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR(place))));
      }
    }
  }

  @Test
  public void testArcs() {

    System.out.println("Arcs");
    Set<IArc> k = rule1.getK().getArcs();
    Set<IArc> l = rule1.getL().getArcs();
    Set<IArc> r = rule1.getR().getArcs();

    for (IArc arc : r) {
      if (arc instanceof PreArc) {
        assertTrue(k.contains(rule1.fromRtoK((PreArc) arc)));

        if (rule1.fromRtoL((PreArc) arc) != null) {
          assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL((PreArc) arc))));
        }
      } else {
        assertTrue(k.contains(rule1.fromRtoK((PostArc) arc)));

        if (rule1.fromRtoL((PostArc) arc) != null) {
          assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL((PostArc) arc))));
        }
      }
    }

    for (IArc arc : l) {
      if (arc instanceof PreArc) {
        assertTrue(k.contains(rule1.fromLtoK((PreArc) arc)));

        if (rule1.fromLtoR((PreArc) arc) != null) {
          assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR((PreArc) arc))));
        }
      } else {
        assertTrue(k.contains(rule1.fromLtoK((PostArc) arc)));

        if (rule1.fromLtoR((PostArc) arc) != null) {
          assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR((PostArc) arc))));
        }
      }
    }
  }

  @Test
  public void testTransitions() {

    System.out.println("Transitions");
    Set<Transition> k = rule1.getK().getTransitions();
    Set<Transition> l = rule1.getL().getTransitions();
    Set<Transition> r = rule1.getR().getTransitions();

    for (Transition transition : r) {
      assertTrue(k.contains(rule1.fromRtoK(transition)));

      if (rule1.fromRtoL(transition) != null) {
        assertTrue(r.contains(rule1.fromLtoR(rule1.fromRtoL(transition))));
      }
    }

    for (Transition transition : l) {
      assertTrue(k.contains(rule1.fromLtoK(transition)));

      if (rule1.fromLtoR(transition) != null) {
        assertTrue(l.contains(rule1.fromRtoL(rule1.fromLtoR(transition))));
      }
    }
  }

  @Test
  public void testAddPlace() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");

    assertEquals(place, rule.fromKtoL(rule.fromLtoK(place)));
    assertNull(rule.fromLtoR(place));

    rule = new Rule();
    place = rule.addPlaceToK("b");

    assertEquals(place, rule.fromLtoK(rule.fromKtoL(place)));
    assertEquals(place, rule.fromRtoK(rule.fromKtoR(place)));

    rule = new Rule();
    place = rule.addPlaceToR("c");

    assertNull(rule.fromRtoL(place));
    assertEquals(place, rule.fromKtoR(rule.fromRtoK(place)));
  }

  @Test
  public void testAddTransition() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");

    assertEquals(transition, rule.fromKtoL(rule.fromLtoK(transition)));
    assertNull(rule.fromLtoR(transition));

    rule = new Rule();
    transition = rule.addTransitionToK("b");

    assertEquals(transition, rule.fromLtoK(rule.fromKtoL(transition)));
    assertEquals(transition, rule.fromRtoK(rule.fromKtoR(transition)));

    rule = new Rule();
    transition = rule.addTransitionToR("c");

    assertNull(rule.fromRtoL(transition));
    assertEquals(transition, rule.fromKtoR(rule.fromRtoK(transition)));
  }

  @Test
  public void testAddArc() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");
    Transition transition = rule.addTransitionToL("b");
    PreArc preArc = rule.addPreArcToL("c", place, transition);
    PostArc postArc = rule.addPostArcToL("c", transition, place);

    assertEquals(preArc, rule.fromKtoL(rule.fromLtoK(preArc)));
    assertEquals(postArc, rule.fromKtoL(rule.fromLtoK(postArc)));
    assertNull(rule.fromLtoR(preArc));
    assertNull(rule.fromLtoR(postArc));

    rule = new Rule();
    place = rule.addPlaceToK("a");
    transition = rule.addTransitionToK("b");
    preArc = rule.addPreArcToK("c", place, transition);
    postArc = rule.addPostArcToK("c", transition, place);

    assertEquals(preArc, rule.fromLtoK(rule.fromKtoL(preArc)));
    assertEquals(postArc, rule.fromLtoK(rule.fromKtoL(postArc)));
    assertEquals(preArc, rule.fromRtoK(rule.fromKtoR(preArc)));
    assertEquals(postArc, rule.fromRtoK(rule.fromKtoR(postArc)));

    rule = new Rule();
    place = rule.addPlaceToR("a");
    transition = rule.addTransitionToR("b");
    preArc = rule.addPreArcToR("c", place, transition);
    postArc = rule.addPostArcToR("c", transition, place);

    assertNull(rule.fromRtoL(preArc));
    assertNull(rule.fromRtoL(postArc));
    assertEquals(preArc, rule.fromKtoR(rule.fromRtoK(preArc)));
    assertEquals(postArc, rule.fromKtoR(rule.fromRtoK(postArc)));
  }

  @Test
  public void testRemovePlace_1() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");
    Place lPlace = place;
    Place kPlace = rule.fromLtoK(place);
    Place rPlace = rule.fromLtoR(place);
    assertNull(rPlace);
    rule.removePlaceFromL(lPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemovePlace_2() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");
    Place lPlace = place;
    Place kPlace = rule.fromLtoK(place);
    Place rPlace = rule.fromLtoR(place);
    assertNull(rPlace);
    rule.removePlaceFromK(kPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemovePlace_3() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place lPlace = rule.fromKtoL(place);
    Place kPlace = place;
    Place rPlace = rule.fromKtoR(place);
    rule.removePlaceFromL(lPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemovePlace_4() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place lPlace = rule.fromKtoL(place);
    Place kPlace = place;
    Place rPlace = rule.fromKtoR(place);
    rule.removePlaceFromK(kPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemovePlace_5() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place lPlace = rule.fromKtoL(place);
    Place kPlace = place;
    Place rPlace = rule.fromKtoR(place);
    rule.removePlaceFromR(rPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemovePlace_6() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToR("a");
    Place lPlace = rule.fromRtoL(place);
    Place kPlace = rule.fromRtoK(place);
    Place rPlace = place;
    assertNull(lPlace);
    rule.removePlaceFromK(kPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemovePlace_7() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToR("a");
    Place lPlace = rule.fromRtoL(place);
    Place kPlace = rule.fromRtoK(place);
    Place rPlace = place;
    assertNull(lPlace);
    rule.removePlaceFromR(rPlace);
    removePlaceAssertions(rule, lPlace, kPlace, rPlace);
  }

  @Test
  public void testRemoveTransition_1() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");
    Transition lTransition = transition;
    Transition kTransition = rule.fromLtoK(transition);
    Transition rTransition = rule.fromLtoR(transition);
    assertNull(rTransition);
    rule.removeTransitionFromL(lTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemoveTransition_2() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");
    Transition lTransition = transition;
    Transition kTransition = rule.fromLtoK(transition);
    Transition rTransition = rule.fromLtoR(transition);
    assertNull(rTransition);
    rule.removeTransitionFromK(kTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemoveTransition_3() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition lTransition = rule.fromKtoL(transition);
    Transition kTransition = transition;
    Transition rTransition = rule.fromKtoR(transition);
    rule.removeTransitionFromL(lTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemoveTransition_4() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition lTransition = rule.fromKtoL(transition);
    Transition kTransition = transition;
    Transition rTransition = rule.fromKtoR(transition);
    rule.removeTransitionFromK(kTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemoveTransition_5() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition lTransition = rule.fromKtoL(transition);
    Transition kTransition = transition;
    Transition rTransition = rule.fromKtoR(transition);
    rule.removeTransitionFromR(rTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemoveTransition_6() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");
    Transition lTransition = rule.fromRtoL(transition);
    Transition kTransition = rule.fromRtoK(transition);
    Transition rTransition = transition;
    assertNull(lTransition);
    rule.removeTransitionFromK(kTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemoveTransition_7() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");
    Transition lTransition = rule.fromRtoL(transition);
    Transition kTransition = rule.fromRtoK(transition);
    Transition rTransition = transition;
    assertNull(lTransition);
    rule.removeTransitionFromR(rTransition);
    removeTransitionAssertions(rule, lTransition, kTransition, rTransition);
  }

  @Test
  public void testRemovePreArc_1() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToL("a");
    Place p = rule.addPlaceToL("a");
    PreArc preArc = rule.addPreArcToL("a", p, trans);
    PreArc lPreArc = preArc;
    PreArc kPreArc = rule.fromLtoK(preArc);
    PreArc rPreArc = rule.fromLtoR(preArc);
    assertNull(rPreArc);
    rule.removePreArcFromL(lPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePreArc_2() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToL("a");
    Place p = rule.addPlaceToL("a");
    PreArc preArc = rule.addPreArcToL("a", p, trans);
    PreArc lPreArc = preArc;
    PreArc kPreArc = rule.fromLtoK(preArc);
    PreArc rPreArc = rule.fromLtoR(preArc);
    assertNull(rPreArc);
    rule.removePreArcFromK(kPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePreArc_3() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PreArc preArc = rule.addPreArcToK("a", p, trans);
    PreArc lPreArc = rule.fromKtoL(preArc);
    PreArc kPreArc = preArc;
    PreArc rPreArc = rule.fromKtoR(preArc);
    rule.removePreArcFromL(lPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePreArc_4() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PreArc preArc = rule.addPreArcToK("a", p, trans);
    PreArc lPreArc = rule.fromKtoL(preArc);
    PreArc kPreArc = preArc;
    PreArc rPreArc = rule.fromKtoR(preArc);
    rule.removePreArcFromK(kPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePreArc_5() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PreArc preArc = rule.addPreArcToK("a", p, trans);
    PreArc lPreArc = rule.fromKtoL(preArc);
    PreArc kPreArc = preArc;
    PreArc rPreArc = rule.fromKtoR(preArc);
    rule.removePreArcFromR(rPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePreArc_6() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToR("a");
    Place p = rule.addPlaceToR("a");
    PreArc preArc = rule.addPreArcToR("a", p, trans);
    PreArc lPreArc = rule.fromRtoL(preArc);
    PreArc kPreArc = rule.fromRtoK(preArc);
    PreArc rPreArc = preArc;
    assertNull(lPreArc);
    rule.removePreArcFromK(kPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePreArc_7() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToR("a");
    Place p = rule.addPlaceToR("a");
    PreArc preArc = rule.addPreArcToR("a", p, trans);
    PreArc lPreArc = rule.fromRtoL(preArc);
    PreArc kPreArc = rule.fromRtoK(preArc);
    PreArc rPreArc = preArc;
    assertNull(lPreArc);
    rule.removePreArcFromR(rPreArc);
    removePreArcAssertions(rule, lPreArc, kPreArc, rPreArc);
  }

  @Test
  public void testRemovePostArc_1() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToL("a");
    Place p = rule.addPlaceToL("a");
    PostArc postArc = rule.addPostArcToL("a", trans, p);
    PostArc lPostArc = postArc;
    PostArc kPostArc = rule.fromLtoK(postArc);
    PostArc rPostArc = rule.fromLtoR(postArc);
    assertNull(rPostArc);
    rule.removePostArcFromL(lPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  @Test
  public void testRemovePostArc_2() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToL("a");
    Place p = rule.addPlaceToL("a");
    PostArc postArc = rule.addPostArcToL("a", trans, p);
    PostArc lPostArc = postArc;
    PostArc kPostArc = rule.fromLtoK(postArc);
    PostArc rPostArc = rule.fromLtoR(postArc);
    assertNull(rPostArc);
    rule.removePostArcFromK(kPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  @Test
  public void testRemovePostArc_3() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PostArc postArc = rule.addPostArcToK("a", trans, p);
    PostArc lPostArc = rule.fromKtoL(postArc);
    PostArc kPostArc = postArc;
    PostArc rPostArc = rule.fromKtoR(postArc);
    rule.removePostArcFromL(lPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  @Test
  public void testRemovePostArc_4() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PostArc postArc = rule.addPostArcToK("a", trans, p);
    PostArc lPostArc = rule.fromKtoL(postArc);
    PostArc kPostArc = postArc;
    PostArc rPostArc = rule.fromKtoR(postArc);
    rule.removePostArcFromK(kPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  @Test
  public void testRemovePostArc_5() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PostArc postArc = rule.addPostArcToK("a", trans, p);
    PostArc lPostArc = rule.fromKtoL(postArc);
    PostArc kPostArc = postArc;
    PostArc rPostArc = rule.fromKtoR(postArc);
    rule.removePostArcFromR(rPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  @Test
  public void testRemovePostArc_6() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToR("a");
    Place p = rule.addPlaceToR("a");
    PostArc postArc = rule.addPostArcToR("a", trans, p);
    PostArc lPostArc = rule.fromRtoL(postArc);
    PostArc kPostArc = rule.fromRtoK(postArc);
    PostArc rPostArc = postArc;
    assertNull(lPostArc);
    rule.removePostArcFromK(kPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  @Test
  public void testRemovePostArc_7() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToR("a");
    Place p = rule.addPlaceToR("a");
    PostArc postArc = rule.addPostArcToR("a", trans, p);
    PostArc lPostArc = rule.fromRtoL(postArc);
    PostArc kPostArc = rule.fromRtoK(postArc);
    PostArc rPostArc = postArc;
    assertNull(lPostArc);
    rule.removePostArcFromR(rPostArc);
    removePostArcAssertions(rule, lPostArc, kPostArc, rPostArc);
  }

  private void removePlaceAssertions(Rule rule, Place lPlace, Place kPlace,
    Place rPlace) {

    assertFalse(lPlace != null && rule.getL().containsPlace(lPlace));
    assertFalse(rule.getK().containsPlace(kPlace));
    assertFalse(rPlace != null && rule.getR().containsPlace(rPlace));

    assertNull(rule.fromLtoK(lPlace));
    assertNull(rule.fromLtoR(lPlace));

    assertNull(rule.fromKtoL(kPlace));
    assertNull(rule.fromKtoR(kPlace));

    assertNull(rule.fromRtoL(rPlace));
    assertNull(rule.fromRtoK(rPlace));
  }

  private void removeTransitionAssertions(Rule rule, Transition lTransition,
    Transition kTransition, Transition rTransition) {

    assertFalse(lTransition != null
      && rule.getL().containsTransition(lTransition));
    assertFalse(rule.getK().containsTransition(kTransition));
    assertFalse(rTransition != null
      && rule.getR().containsTransition(rTransition));

    assertNull(rule.fromLtoK(lTransition));
    assertNull(rule.fromLtoR(lTransition));

    assertNull(rule.fromKtoL(kTransition));
    assertNull(rule.fromKtoR(kTransition));

    assertNull(rule.fromRtoL(rTransition));
    assertNull(rule.fromRtoK(rTransition));
  }

  private void removePreArcAssertions(Rule rule, PreArc lPreArc,
    PreArc kPreArc, PreArc rPreArc) {

    assertFalse(lPreArc != null && rule.getL().containsPreArc(lPreArc));
    assertFalse(rule.getK().containsPreArc(kPreArc));
    assertFalse(rPreArc != null && rule.getR().containsPreArc(rPreArc));

    assertNull(rule.fromLtoK(lPreArc));
    assertNull(rule.fromLtoR(lPreArc));

    assertNull(rule.fromKtoL(kPreArc));
    assertNull(rule.fromKtoR(kPreArc));

    assertNull(rule.fromRtoL(rPreArc));
    assertNull(rule.fromRtoK(rPreArc));
  }

  private void removePostArcAssertions(Rule rule, PostArc lPostArc,
    PostArc kPostArc, PostArc rPostArc) {

    assertFalse(lPostArc != null && rule.getL().containsPostArc(lPostArc));
    assertFalse(rule.getK().containsPostArc(kPostArc));
    assertFalse(rPostArc != null && rule.getR().containsPostArc(rPostArc));

    assertNull(rule.fromLtoK(lPostArc));
    assertNull(rule.fromLtoR(lPostArc));

    assertNull(rule.fromKtoL(kPostArc));
    assertNull(rule.fromKtoR(kPostArc));

    assertNull(rule.fromRtoL(rPostArc));
    assertNull(rule.fromRtoK(rPostArc));
  }

  @Test
  public void testSetNamePlace_1() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");

    rule.setNameInL(place, "b");

    assertEquals("b", place.getName());
    assertEquals("b", rule.fromLtoK(place).getName());
  }

  @Test
  public void testSetNamePlace_2() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");
    Place otherPlace = rule.fromLtoK(place);

    rule.setNameInK(otherPlace, "b");

    assertEquals("b", place.getName());
    assertEquals("b", rule.fromKtoL(otherPlace).getName());
    assertEquals("b", otherPlace.getName());
  }

  @Test
  public void testSetNamePlace_3() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place otherPlace = rule.fromKtoL(place);

    rule.setNameInL(otherPlace, "b");

    assertEquals("b", otherPlace.getName());
    assertEquals("b", place.getName());
    assertEquals("b", rule.fromLtoK(otherPlace).getName());
    assertEquals("b", rule.fromLtoR(otherPlace).getName());
  }

  @Test
  public void testSetNamePlace_4() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");

    rule.setNameInK(place, "b");

    assertEquals("b", rule.fromKtoL(place).getName());
    assertEquals("b", place.getName());
    assertEquals("b", rule.fromKtoR(place).getName());
  }

  @Test
  public void testSetNamePlace_5() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place otherPlace = rule.fromKtoR(place);

    rule.setNameInR(otherPlace, "b");

    assertEquals("b", rule.fromRtoL(otherPlace).getName());
    assertEquals("b", place.getName());
    assertEquals("b", rule.fromRtoK(otherPlace).getName());
    assertEquals("b", otherPlace.getName());
  }

  @Test
  public void testSetNamePlace_6() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToR("a");

    rule.setNameInR(place, "b");

    assertEquals("b", rule.fromRtoK(place).getName());
    assertEquals("b", place.getName());
  }

  @Test
  public void testSetNamePlace_7() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToR("a");
    Place otherPlace = rule.fromRtoK(place);

    rule.setNameInK(otherPlace, "b");

    assertEquals("b", otherPlace.getName());
    assertEquals("b", rule.fromKtoR(otherPlace).getName());
    assertEquals("b", place.getName());
  }

  @Test
  public void testSetNameTransition_1() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");

    rule.setNameInL(transition, "b");

    assertEquals("b", transition.getName());
    assertEquals("b", rule.fromLtoK(transition).getName());
  }

  @Test
  public void testSetNameTransition_2() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");
    Transition otherTransition = rule.fromLtoK(transition);

    rule.setNameInK(otherTransition, "b");

    assertEquals("b", transition.getName());
    assertEquals("b", rule.fromKtoL(otherTransition).getName());
    assertEquals("b", otherTransition.getName());
  }

  @Test
  public void testSetNameTransition_3() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition otherTransition = rule.fromKtoL(transition);

    rule.setNameInL(otherTransition, "b");

    assertEquals("b", otherTransition.getName());
    assertEquals("b", transition.getName());
    assertEquals("b", rule.fromLtoK(otherTransition).getName());
    assertEquals("b", rule.fromLtoR(otherTransition).getName());
  }

  @Test
  public void testSetNameTransition_4() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");

    rule.setNameInK(transition, "b");

    assertEquals("b", rule.fromKtoL(transition).getName());
    assertEquals("b", transition.getName());
    assertEquals("b", rule.fromKtoR(transition).getName());
  }

  @Test
  public void testSetNameTransition_5() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition otherTransition = rule.fromKtoR(transition);

    rule.setNameInR(otherTransition, "b");

    assertEquals("b", rule.fromRtoL(otherTransition).getName());
    assertEquals("b", transition.getName());
    assertEquals("b", rule.fromRtoK(otherTransition).getName());
    assertEquals("b", otherTransition.getName());
  }

  @Test
  public void testSetNameTransition_6() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");

    rule.setNameInR(transition, "b");

    assertEquals("b", rule.fromRtoK(transition).getName());
    assertEquals("b", transition.getName());
  }

  @Test
  public void testSetNameTransition_7() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");
    Transition otherTransition = rule.fromRtoK(transition);

    rule.setNameInK(otherTransition, "b");

    assertEquals("b", otherTransition.getName());
    assertEquals("b", rule.fromKtoR(otherTransition).getName());
    assertEquals("b", transition.getName());
  }

  @Test
  public void testSetTlbTransition_1() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");

    rule.setTlbInL(transition, "b");

    assertEquals("b", transition.getTlb());
    assertEquals("b", rule.fromLtoK(transition).getTlb());
  }

  @Test
  public void testSetTlbTransition_2() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");
    Transition otherTransition = rule.fromLtoK(transition);

    rule.setTlbInK(otherTransition, "b");

    assertEquals("b", transition.getTlb());
    assertEquals("b", rule.fromKtoL(otherTransition).getTlb());
    assertEquals("b", otherTransition.getTlb());
  }

  @Test
  public void testSetTlbTransition_3() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition otherTransition = rule.fromKtoL(transition);

    rule.setTlbInL(otherTransition, "b");

    assertEquals("b", otherTransition.getTlb());
    assertEquals("b", transition.getTlb());
    assertEquals("b", rule.fromLtoK(otherTransition).getTlb());
    assertEquals("b", rule.fromLtoR(otherTransition).getTlb());
  }

  @Test
  public void testSetTlbTransition_4() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");

    rule.setTlbInK(transition, "b");

    assertEquals("b", rule.fromKtoL(transition).getTlb());
    assertEquals("b", transition.getTlb());
    assertEquals("b", rule.fromKtoR(transition).getTlb());
  }

  @Test
  public void testSetTlbTransition_5() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition otherTransition = rule.fromKtoR(transition);

    rule.setTlbInR(otherTransition, "b");

    assertEquals("b", rule.fromRtoL(otherTransition).getTlb());
    assertEquals("b", transition.getTlb());
    assertEquals("b", rule.fromRtoK(otherTransition).getTlb());
    assertEquals("b", otherTransition.getTlb());
  }

  @Test
  public void testSetTlbTransition_6() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");

    rule.setTlbInR(transition, "b");

    assertEquals("b", rule.fromRtoK(transition).getTlb());
    assertEquals("b", transition.getTlb());
  }

  @Test
  public void testSetTlbTransition_7() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");
    Transition otherTransition = rule.fromRtoK(transition);

    rule.setTlbInK(otherTransition, "b");

    assertEquals("b", otherTransition.getTlb());
    assertEquals("b", rule.fromKtoR(otherTransition).getTlb());
    assertEquals("b", transition.getTlb());
  }

  @Test
  public void testCapacityFromKtoLandR() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("K");

    rule.setCapacityInK(place, CAPACITY);

    assertEquals(CAPACITY, place.getCapacity());
    assertEquals(CAPACITY, rule.fromKtoL(place).getCapacity());
    assertEquals(CAPACITY, rule.fromKtoR(place).getCapacity());
  }

  @Test
  public void testSetMarkingPlace_1() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");

    rule.setMarkInL(place, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, place.getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromLtoK(place).getMark());
  }

  @Test
  public void testSetMarkingPlace_2() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToL("a");
    Place otherPlace = rule.fromLtoK(place);

    rule.setMarkInK(otherPlace, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, place.getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromKtoL(otherPlace).getMark());
    assertEquals(NUMBER_OF_MARKS, otherPlace.getMark());
  }

  @Test
  public void testSetMarkingPlace_3() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place otherPlace = rule.fromKtoL(place);

    rule.setMarkInL(otherPlace, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, otherPlace.getMark());
    assertEquals(NUMBER_OF_MARKS, place.getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromLtoK(otherPlace).getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromLtoR(otherPlace).getMark());
  }

  @Test
  public void testSetMarkingPlace_4() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");

    rule.setMarkInK(place, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, rule.fromKtoL(place).getMark());
    assertEquals(NUMBER_OF_MARKS, place.getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromKtoR(place).getMark());
  }

  @Test
  public void testSetMarkingPlace_5() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToK("a");
    Place otherPlace = rule.fromKtoR(place);

    rule.setMarkInR(otherPlace, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, rule.fromRtoL(otherPlace).getMark());
    assertEquals(NUMBER_OF_MARKS, place.getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromRtoK(otherPlace).getMark());
    assertEquals(NUMBER_OF_MARKS, otherPlace.getMark());
  }

  @Test
  public void testSetMarkingPlace_6() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToR("a");

    rule.setMarkInR(place, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, rule.fromRtoK(place).getMark());
    assertEquals(NUMBER_OF_MARKS, place.getMark());
  }

  @Test
  public void testSetMarkingPlace_7() {

    Rule rule = new Rule();
    Place place = rule.addPlaceToR("a");
    Place otherPlace = rule.fromRtoK(place);

    rule.setMarkInK(otherPlace, NUMBER_OF_MARKS);

    assertEquals(NUMBER_OF_MARKS, otherPlace.getMark());
    assertEquals(NUMBER_OF_MARKS, rule.fromKtoR(otherPlace).getMark());
    assertEquals(NUMBER_OF_MARKS, place.getMark());
  }

  @Test
  public void testSetRnwTransition_1() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInL(transition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, transition.getRnw());
    assertEquals(Renews.TOGGLE, rule.fromLtoK(transition).getRnw());
  }

  @Test
  public void testSetRnwTransition_2() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToL("a");
    Transition otherTransition = rule.fromLtoK(transition);

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInK(otherTransition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, transition.getRnw());
    assertEquals(Renews.TOGGLE, rule.fromKtoL(otherTransition).getRnw());
    assertEquals(Renews.TOGGLE, otherTransition.getRnw());
  }

  @Test
  public void testSetRnwTransition_3() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition otherTransition = rule.fromKtoL(transition);

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInL(otherTransition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, otherTransition.getRnw());
    assertEquals(Renews.TOGGLE, transition.getRnw());
    assertEquals(Renews.TOGGLE, rule.fromLtoK(otherTransition).getRnw());
    assertEquals(Renews.TOGGLE, rule.fromLtoR(otherTransition).getRnw());
  }

  @Test
  public void testSetRnwTransition_4() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInK(transition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, rule.fromKtoL(transition).getRnw());
    assertEquals(Renews.TOGGLE, transition.getRnw());
    assertEquals(Renews.TOGGLE, rule.fromKtoR(transition).getRnw());
  }

  @Test
  public void testSetRnwTransition_5() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToK("a");
    Transition otherTransition = rule.fromKtoR(transition);

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInR(otherTransition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, rule.fromRtoL(otherTransition).getRnw());
    assertEquals(Renews.TOGGLE, transition.getRnw());
    assertEquals(Renews.TOGGLE, rule.fromRtoK(otherTransition).getRnw());
    assertEquals(Renews.TOGGLE, otherTransition.getRnw());
  }

  @Test
  public void testSetRnwTransition_6() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInR(transition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, rule.fromRtoK(transition).getRnw());
    assertEquals(Renews.TOGGLE, transition.getRnw());
  }

  @Test
  public void testSetRnwTransition_7() {

    Rule rule = new Rule();
    Transition transition = rule.addTransitionToR("a");
    Transition otherTransition = rule.fromRtoK(transition);

    assertEquals(Renews.IDENTITY, transition.getRnw());
    rule.setRnwInK(otherTransition, Renews.TOGGLE);

    assertEquals(Renews.TOGGLE, otherTransition.getRnw());
    assertEquals(Renews.TOGGLE, rule.fromKtoR(otherTransition).getRnw());
    assertEquals(Renews.TOGGLE, transition.getRnw());
  }

  @Test
  public void testSetWeightPreArc_1() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToL("a", rule.addPlaceToL("a"),
        rule.addTransitionToL("a"));

    rule.setWeightInL(preArc, WEIGHT);

    assertEquals(WEIGHT, preArc.getWeight());
    assertEquals(WEIGHT, rule.fromLtoK(preArc).getWeight());
  }

  @Test
  public void testSetWeightPreArc_2() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToL("a", rule.addPlaceToL("a"),
        rule.addTransitionToL("a"));
    PreArc otherPreArc = rule.fromLtoK(preArc);

    rule.setWeightInK(otherPreArc, WEIGHT);

    assertEquals(WEIGHT, preArc.getWeight());
    assertEquals(WEIGHT, rule.fromKtoL(otherPreArc).getWeight());
    assertEquals(WEIGHT, otherPreArc.getWeight());
  }

  @Test
  public void testSetWeightPreArc_3() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToK("a", rule.addPlaceToK("a"),
        rule.addTransitionToK("a"));
    PreArc otherPreArc = rule.fromKtoL(preArc);

    rule.setWeightInL(otherPreArc, WEIGHT);

    assertEquals(WEIGHT, otherPreArc.getWeight());
    assertEquals(WEIGHT, preArc.getWeight());
    assertEquals(WEIGHT, rule.fromLtoK(otherPreArc).getWeight());
    assertEquals(WEIGHT, rule.fromLtoR(otherPreArc).getWeight());
  }

  @Test
  public void testSetWeightPreArc_4() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToK("a", rule.addPlaceToK("a"),
        rule.addTransitionToK("a"));

    rule.setWeightInK(preArc, WEIGHT);

    assertEquals(WEIGHT, rule.fromKtoL(preArc).getWeight());
    assertEquals(WEIGHT, preArc.getWeight());
    assertEquals(WEIGHT, rule.fromKtoR(preArc).getWeight());
  }

  @Test
  public void testSetWeightPreArc_5() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToK("a", rule.addPlaceToK("a"),
        rule.addTransitionToK("a"));
    PreArc otherPreArc = rule.fromKtoR(preArc);

    rule.setWeightInR(otherPreArc, WEIGHT);

    assertEquals(WEIGHT, rule.fromRtoL(otherPreArc).getWeight());
    assertEquals(WEIGHT, preArc.getWeight());
    assertEquals(WEIGHT, rule.fromRtoK(otherPreArc).getWeight());
    assertEquals(WEIGHT, otherPreArc.getWeight());
  }

  @Test
  public void testSetWeightPreArc_6() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToR("a", rule.addPlaceToR("a"),
        rule.addTransitionToR("a"));

    rule.setWeightInR(preArc, WEIGHT);

    assertEquals(WEIGHT, rule.fromRtoK(preArc).getWeight());
    assertEquals(WEIGHT, preArc.getWeight());
  }

  @Test
  public void testSetWeightPreArc_7() {

    Rule rule = new Rule();
    PreArc preArc =
      rule.addPreArcToR("a", rule.addPlaceToR("a"),
        rule.addTransitionToR("a"));
    PreArc otherPreArc = rule.fromRtoK(preArc);

    rule.setWeightInK(otherPreArc, WEIGHT);

    assertEquals(WEIGHT, otherPreArc.getWeight());
    assertEquals(WEIGHT, rule.fromKtoR(otherPreArc).getWeight());
    assertEquals(WEIGHT, preArc.getWeight());
  }

  @Test
  public void testSetWeightPostArc_1() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToL("a");
    Place p = rule.addPlaceToL("a");
    PostArc postArc = rule.addPostArcToL("a", trans, p);

    rule.setWeightInL(postArc, WEIGHT);

    assertEquals(WEIGHT, postArc.getWeight());
    assertEquals(WEIGHT, rule.fromLtoK(postArc).getWeight());
  }

  @Test
  public void testSetWeightPostArc_2() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToL("a");
    Place p = rule.addPlaceToL("a");
    PostArc postArc = rule.addPostArcToL("a", trans, p);
    PostArc otherPostArc = rule.fromLtoK(postArc);

    rule.setWeightInK(otherPostArc, WEIGHT);

    assertEquals(WEIGHT, postArc.getWeight());
    assertEquals(WEIGHT, rule.fromKtoL(otherPostArc).getWeight());
    assertEquals(WEIGHT, otherPostArc.getWeight());
  }

  @Test
  public void testSetWeightPostArc_3() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PostArc postArc = rule.addPostArcToK("a", trans, p);
    PostArc otherPostArc = rule.fromKtoL(postArc);

    rule.setWeightInL(otherPostArc, WEIGHT);

    assertEquals(WEIGHT, otherPostArc.getWeight());
    assertEquals(WEIGHT, postArc.getWeight());
    assertEquals(WEIGHT, rule.fromLtoK(otherPostArc).getWeight());
    assertEquals(WEIGHT, rule.fromLtoR(otherPostArc).getWeight());
  }

  @Test
  public void testSetWeightPostArc_4() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PostArc postArc = rule.addPostArcToK("a", trans, p);

    rule.setWeightInK(postArc, WEIGHT);

    assertEquals(WEIGHT, rule.fromKtoL(postArc).getWeight());
    assertEquals(WEIGHT, postArc.getWeight());
    assertEquals(WEIGHT, rule.fromKtoR(postArc).getWeight());
  }

  @Test
  public void testSetWeightPostArc_5() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToK("a");
    Place p = rule.addPlaceToK("a");
    PostArc postArc = rule.addPostArcToK("a", trans, p);
    PostArc otherPostArc = rule.fromKtoR(postArc);

    rule.setWeightInR(otherPostArc, WEIGHT);

    assertEquals(WEIGHT, rule.fromRtoL(otherPostArc).getWeight());
    assertEquals(WEIGHT, postArc.getWeight());
    assertEquals(WEIGHT, rule.fromRtoK(otherPostArc).getWeight());
    assertEquals(WEIGHT, otherPostArc.getWeight());
  }

  @Test
  public void testSetWeightPostArc_6() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToR("a");
    Place p = rule.addPlaceToR("a");
    PostArc postArc = rule.addPostArcToR("a", trans, p);

    rule.setWeightInR(postArc, WEIGHT);

    assertEquals(WEIGHT, rule.fromRtoK(postArc).getWeight());
    assertEquals(WEIGHT, postArc.getWeight());
  }

  @Test
  public void testSetWeightPostArc_7() {

    Rule rule = new Rule();
    Transition trans = rule.addTransitionToR("a");
    Place p = rule.addPlaceToR("a");
    PostArc postArc = rule.addPostArcToR("a", trans, p);
    PostArc otherPostArc = rule.fromRtoK(postArc);

    rule.setWeightInK(otherPostArc, WEIGHT);

    assertEquals(WEIGHT, otherPostArc.getWeight());
    assertEquals(WEIGHT, rule.fromKtoR(otherPostArc).getWeight());
    assertEquals(WEIGHT, postArc.getWeight());
  }
}
