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

package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import transformation.matcher.PNVF2;
// import transformation.matcher.*;
import transformation.matcher.PNVF2.MatchException;
import data.MorphismData;

/**
 * Testing the morphism of places like specified in
 * "../additional/images/Isomorphism_places.png"
 */
public class MorphismPlacesTest {

  private static final int MORPHISM_COUNT = 2000;
  private static final int COUNTER_SIZE = 4;

  private static Petrinet placesFromNet =
    MorphismData.getPetrinetIsomorphismPlacesFrom();

  private static Petrinet placesToNet =
    MorphismData.getPetrinetIsomorphismPlacesTo();

  private static Place fromPlace;
  private static Map<Place, Integer> counter;

  @BeforeClass
  public static void setUpOnce()
    throws Exception {

    // Get the first (and only) place in the "from" net
    fromPlace = placesFromNet.getPlaces().iterator().next();

    counter = new HashMap<Place, Integer>();

    Place targetPlace;
    // try 100 morphism and count them
    for (int i = 0; i < MORPHISM_COUNT; i++) {
      Match match =
        PNVF2.getInstance(placesFromNet, placesToNet).getMatch(false);
      // Ullmann.createMatch(placesFromNet, placesToNet);
      targetPlace = match.getPlace(fromPlace);

      if (counter.containsKey(targetPlace)) {
        counter.put(targetPlace, counter.get(targetPlace) + 1);
      } else {
        counter.put(targetPlace, 1);
      }
    }
  }

  @Test
  public void testPlacesMorphismCount() {

    // only 4 morphisms should have been possible
    assertEquals(COUNTER_SIZE, counter.size());
  }

  @Test
  public void testPlacesMorphismMatchingDistribution() {

    // since its not deterministic we check if its approximately equally
    // often machted
    int average = MORPHISM_COUNT / COUNTER_SIZE;
    int allowedDeviation = average / 2;
    int max = average + allowedDeviation;
    int min = average - allowedDeviation;
    for (Integer count : counter.values()) {
      boolean accepted = min < count && count < max;
      if (!accepted) {
        System.out.println("This test failed due to non determinism. "
          + "Its not too bad it failed.\n" + "It should have been between "
          + min + " and " + max + " but was " + count);
      }
      assertEquals(true, accepted);
    }
  }

  @Test
  public void testPlacesMorphismCorrectMatches()
    throws Exception {

    placesFromNet = MorphismData.getPetrinetIsomorphismPlacesFrom();
    placesToNet = MorphismData.getPetrinetIsomorphismPlacesTo();
    setUpOnce();

    assertEquals(MorphismData.getIdFromPlaces(), fromPlace.getId());

    Set<Integer> expectedMatches = MorphismData.getIdsMatchedPlaces();

    Set<Integer> actualMatches = new HashSet<Integer>();
    for (Place place : counter.keySet()) {
      actualMatches.add(place.getId());
    }
    assertEquals(expectedMatches, actualMatches);
  }

  @Test
  public void testStupidMethodToGetCodeCoverageForGetters() {

    // Match match = Ullmann.createMatch(placesFromNet, placesToNet);
    Match match;
    try {
      match = PNVF2.getInstance(placesFromNet, placesToNet).getMatch(false);
      match.getPreArc(placesFromNet.getPreArcs().iterator().next());
      match.getPreArcs();
      match.getPostArc(placesFromNet.getPostArcs().iterator().next());
      match.getPostArcs();
      match.getSource();
      match.getPlaces();
      match.getTarget();
      match.getTransitions();
    } catch (MatchException e) {
      fail();
    }
  }
}
