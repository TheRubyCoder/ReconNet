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

package transformation;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import data.MorphismData;
import petrinet.model.Petrinet;
import petrinet.model.Transition;
import transformation.matcher.PNVF2;

/**
 * Testing the morphism of places like specified in
 * "../additional/images/Isomorphism_transitions.png"
 */
public class MorphismTransitionsTest {

  private static Petrinet transitionFromNet =
    MorphismData.getPetrinetIsomorphismTransitionsFrom();

  private static Petrinet transitionToNet =
    MorphismData.getPetrinetIsomorphismTransitionsTo();

  private static Transition fromTransition;
  private static Map<Transition, Integer> counter;

  private static final int AVERAGE = 20;

  @BeforeClass
  public static void setUpOnce()
    throws Exception {

    // Get the first (and only) transition in the "from" net
    fromTransition = transitionFromNet.getTransitions().iterator().next();

    counter = new HashMap<Transition, Integer>();

    Transition targetTransition;
    // try 100 morphism and count them
    for (int i = 0; i < AVERAGE; i++) {
      // Match match = Ullmann.createMatch(transitionFromNet,
      // transitionToNet);
      Match match =
        PNVF2.getInstance(transitionFromNet, transitionToNet).getMatch(false);

      targetTransition = match.getTransition(fromTransition);

      if (counter.containsKey(targetTransition)) {
        counter.put(targetTransition, counter.get(targetTransition) + 1);
      } else {
        counter.put(targetTransition, 1);
      }
    }
  }

  @Test
  public void testPlacesMorphismCount() {

    // only 1 morphisms should have been possible
    assertEquals(1, counter.size());
  }

  @Test
  public void testPlacesMorphismMatchingDistribution() {

    // since its not deterministic we check if its approximately equally
    // often machted
    int allowedDeviation = 0;
    int max = AVERAGE + allowedDeviation;
    int min = AVERAGE - allowedDeviation;
    for (Integer count : counter.values()) {
      boolean accepted = min <= count && count <= max;
      if (!accepted) {
        System.out.println("Should have matched 20 times but it matched "
          + count + " times.");
      }
      assertEquals(true, accepted);
    }
  }

  @Test
  public void testPlacesMorphismCorrectMatches() {

    /*
     * see if we got the right transition. id=1 is in "from". id=2 should be
     * matched in "to"
     */
    assertEquals(MorphismData.getIdFromTransitions(), fromTransition.getId());

    Set<Integer> expectedMatches = new HashSet<Integer>();
    expectedMatches.add(MorphismData.getIdMatchedTransition());

    Set<Integer> actualMatches = new HashSet<Integer>();
    for (Transition transition : counter.keySet()) {
      actualMatches.add(transition.getId());
    }
    assertEquals(expectedMatches, actualMatches);

  }

}
