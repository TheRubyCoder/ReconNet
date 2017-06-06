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
/**
 * @author Wlad Timotin, Wojtek Gozdzielewski, Björn Kulas
 */
package transformation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.matcher.NacVisitor;
import transformation.matcher.PNVF2;
import transformation.matcher.PNVF2.MatchException;

public class NacVisitorTest {

  @Test
  public void testVisitMophismExists() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);

    Petrinet n = new Petrinet();
    n.addPlace("");

    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertFalse(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitNoMophismExists() {

    Rule rule = new Rule();
    NAC nac = rule.createNAC();
    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac = rule.addTransitionToNac("", nac);
    rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
    rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertTrue(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithoutNACOneNPlaceNoMophismExists() {

    Rule rule = new Rule();
    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertTrue(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithTwoNACNoMophismExists() {

    Rule rule = new Rule();
    NAC nac1 = rule.createNAC();
    NAC nac2 = rule.createNAC();

    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
    rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1, nac1);
    rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
      nac1);

    Transition transitionInNac2 = rule.addTransitionToNac("", nac2);
    rule.addPreArcToNac("", nac2.fromLtoNac(placeInL), transitionInNac2, nac2);
    rule.addPostArcToNac("", transitionInNac2, nac2.fromLtoNac(placeInL),
      nac2);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertTrue(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithTwoNACTwoMophismsExists() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.createNAC();

    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertFalse(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithTwoNACOneMophismExists() {

    Rule rule = new Rule();
    NAC nac1 = rule.createNAC();
    rule.createNAC();

    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
    rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1, nac1);
    rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
      nac1);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertFalse(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithOneNACTwoMophismsExists() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    n.addPlace("");

    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertFalse(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithOneNACNoMophismsExists() {

    Rule rule = new Rule();
    NAC nac = rule.createNAC();
    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac = rule.addTransitionToNac("", nac);
    rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
    rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertTrue(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithoutNACTwoNPlacesNoMophismExists() {

    Rule rule = new Rule();
    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertTrue(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithTwoNACTwoNPlacesNoMophismExists() {

    Rule rule = new Rule();
    NAC nac1 = rule.createNAC();
    NAC nac2 = rule.createNAC();

    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
    rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1, nac1);
    rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
      nac1);

    Transition transitionInNac2 = rule.addTransitionToNac("", nac2);
    rule.addPreArcToNac("", nac2.fromLtoNac(placeInL), transitionInNac2, nac2);
    rule.addPostArcToNac("", transitionInNac2, nac2.fromLtoNac(placeInL),
      nac2);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertTrue(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithTwoNACTwoMophismExists() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.createNAC();

    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertFalse(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testVisitWithTwoNACOnMophismExists() {

    Rule rule = new Rule();
    NAC nac1 = rule.createNAC();
    rule.createNAC();

    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
    rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1, nac1);
    rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
      nac1);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    n.addPlace("");
    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }
    assertFalse(nacVisitor.visit(matchLinN));
  }

  @Test
  public void testMatchesCountsRandom() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Place placeWithTransition = n.addPlace("");
    Transition transition = n.addTransition("");
    n.addPreArc("", placeWithTransition, transition);
    n.addPostArc("", transition, placeWithTransition);

    Match matchLinN = null;
    try {
      matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
    } catch (MatchException e) {
      e.printStackTrace();
    }

    // CHECKSTYLE:OFF - No need to check for magic numbers
    int randNumOfVisits = (int) ((Math.random() * 1000.0) + 1);
    // CHECKSTYLE:ON

    for (int i = 1; i <= randNumOfVisits; i++) {
      nacVisitor.visit(matchLinN);
    }

    int res = 0;
    for (Integer count : nacVisitor.getMatchesCounts().values()) {
      res += count;
    }
    assertTrue(randNumOfVisits == res);
  }

  @Test
  public void testBacktrackingWithNACTwoNPlacesOneMorphismExists() {

    Rule rule = new Rule();
    NAC nac = rule.createNAC();
    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac = rule.addTransitionToNac("", nac);
    rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
    rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Place placeWithTransition = n.addPlace("");
    Transition transition = n.addTransition("");
    n.addPreArc("", placeWithTransition, transition);
    n.addPostArc("", transition, placeWithTransition);

    int res = 0;
    do {
      try {
        PNVF2.getInstance(rule.getL(), n).getMatch(false, nacVisitor);
      } catch (MatchException e) {
        System.err.println("NAC ist in N vorhanden");
      }
      res = 0;
      for (Integer count : nacVisitor.getMatchesCounts().values()) {
        res += count;
      }
      // System.out.println("nacVisitorCounter  = "+
      // nacVisitor.getMatchesCounts());
      // System.out.println("--------");
    } while (res == 1);
    // System.out.println(nacVisitor.getMatchesCounts());
    // System.out.println("L *************");
    // System.out.println(rule.getL());
    // System.out.println("N *************");
    // System.out.println(n);
    // System.out.println("NAC *************");
    // System.out.println(nac.getPlaceMappingLToNac());
    // System.out.println("**************");

    assertTrue(res > 1);
  }

  @Test
  public void testBacktrackingWithNACTwoNPlacesTwoMorphismExists() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.addPlaceToL("");

    NacVisitor nacVisitor = new NacVisitor(rule);
    Petrinet n = new Petrinet();
    n.addPlace("");
    Place placeWithTransition = n.addPlace("");
    Transition transition = n.addTransition("");
    n.addPreArc("", placeWithTransition, transition);
    n.addPostArc("", transition, placeWithTransition);

    int res = 0;

    try {
      PNVF2.getInstance(rule.getL(), n).getMatch(false, nacVisitor);
      fail();
    } catch (MatchException e) {
      res = 0;
      for (Integer count : nacVisitor.getMatchesCounts().values()) {
        res += count;
      }
      // System.out.println("nacVisitorCounter  = "+
      // nacVisitor.getMatchesCounts());
      // System.out.println("--------");

      assertTrue(res == 2);
    }

  }

  @Test
  public void testTransformationWithNacTransformationImpossible() {

    Rule rule = new Rule();
    rule.createNAC();
    rule.addPlaceToL("");

    Petrinet n = new Petrinet();
    n.addPlace("");
    Place placeWithTransition = n.addPlace("");
    Transition transition = n.addTransition("");
    n.addPreArc("", placeWithTransition, transition);
    n.addPostArc("", transition, placeWithTransition);

    Transformation transformation =
      TransformationComponent.getTransformation().transform(n, rule);

    assertNull(transformation);
  }

  @Test
  public void testTransformationWithNacTransformationPossible() {

    Rule rule = new Rule();
    NAC nac = rule.createNAC();
    Place placeInL = rule.addPlaceToL("");
    Transition transitionInNac = rule.addTransitionToNac("", nac);
    rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
    rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

    Petrinet n = new Petrinet();
    n.addPlace("");
    Place placeWithTransition = n.addPlace("");
    Transition transition = n.addTransition("");
    n.addPreArc("", placeWithTransition, transition);
    n.addPostArc("", transition, placeWithTransition);

    Transformation transformation =
      TransformationComponent.getTransformation().transform(n, rule);

    assertNotNull(transformation);
  }

  @Test
  public void testTransformationWithoutNacTransformationPossible() {

    Rule rule = new Rule();
    rule.addPlaceToL("");

    Petrinet n = new Petrinet();
    n.addPlace("");
    Place placeWithTransition = n.addPlace("");
    Transition transition = n.addTransition("");
    n.addPreArc("", placeWithTransition, transition);
    n.addPostArc("", transition, placeWithTransition);

    Transformation transformation =
      TransformationComponent.getTransformation().transform(n, rule);

    assertNotNull(transformation);
  }

  @Test
  public void testTransformationWithoutNacTransformationImpossible() {

    Rule rule = new Rule();
    Place placeInL = rule.addPlaceToL("");
    Transition transitionInL = rule.addTransitionToL("");
    rule.addPreArcToL("", placeInL, transitionInL);
    rule.addPostArcToL("", transitionInL, placeInL);

    Petrinet n = new Petrinet();
    n.addPlace("");

    Transformation transformation =
      TransformationComponent.getTransformation().transform(n, rule);

    assertNull(transformation);
  }
}
