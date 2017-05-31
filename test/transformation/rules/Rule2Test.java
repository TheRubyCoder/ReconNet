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

package transformation.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import transformation.Match;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import data.Rule2Data;

/**
 * Testing if Rule 2 like specified in /../additional/images/Rule_2.png works
 * correctly
 */
public class Rule2Test {

  /** petrinet to transform */
  private static Petrinet nPetrinet = Rule2Data.getnPetrinet();
  /** rule to apply */
  private static Rule rule = Rule2Data.getRule();

  private static String preBefore;
  private static String postBefore;
  private static Transformation transformation;
  private static String preAfter;
  private static String postAfter;

  @BeforeClass
  public static void applyingRule() {

    System.out.println("Petrinetz:::: " + nPetrinet);
    System.out.println("Regel-L:::: " + rule.getL());
    // System.out.println("Regel-K:::: " + rule.getK());
    // Because the reference stays the same after transformation
    // nPetrinet will always equals itself, no matter what happens in
    // transformation
    // so we check the toString() of its pre-matrix instead
    preBefore = nPetrinet.getPre().matrixStringOnly();
    postBefore = nPetrinet.getPost().matrixStringOnly();
    try {
      transformation =
        TransformationComponent.getTransformation().transform(nPetrinet, rule);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Morphism should have been found");
    }
    preAfter = nPetrinet.getPre().matrixStringOnly();
    postAfter = nPetrinet.getPost().matrixStringOnly();
  }

  @Test
  public void testEquality() {

    assertFalse("The petrinet should have changed but were equal",
      preBefore.equals(preAfter));
    assertFalse("The petrinet should have changed but were equal",
      postBefore.equals(postAfter));
  }

  @Test
  public void testRightMorphism() {

    // CHECKSTYLE:OFF - No need to check for magic numbers
    // Has only 1 place been mapped?
    Match morphism = transformation.getMatch();
    System.out.println("MORPISM::PLACES::" + morphism.getPlaces());
    assertEquals(2, transformation.getMatch().getPlaces().size());
    // Have 5 transitions been mapped?
    assertEquals(10, transformation.getMatch().getTransitions().size());
    // CHECKSTYLE:ON
    // Right place matched?
    int idOfMatchedPlace =
      transformation.getMatch().getPlaces().values().iterator().next().getId();
    assertEquals(Rule2Data.getIdOfMatchedPlace(), idOfMatchedPlace);
  }

  @Test
  public void testChangesApplied() {

    // CHECKSTYLE:OFF - No need to check for magic numbers
    // Only 6 places left?
    assertEquals(6, nPetrinet.getPlaces().size());
    // Only 29 transitions left?
    assertEquals(30, nPetrinet.getTransitions().size());
    // Only 29 arcs left?
    assertEquals(30, nPetrinet.getArcs().size());
    // CHECKSTYLE:ON
    // right place deleted?
    for (Place place : nPetrinet.getPlaces()) {
      if (place.getId() == Rule2Data.getIdOfMatchedPlace()) {
        fail("the place that should have been deleted "
          + "is still in the petrinet");
      }
    }
  }

}
