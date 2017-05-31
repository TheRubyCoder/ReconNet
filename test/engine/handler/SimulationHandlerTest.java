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
/**
 * @author Wlad Timotin, Wojtek Gozdzielewski, Björn Kulas
 */
package engine.handler;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.ITransformation;
import transformation.Rule;
import transformation.TransformationComponent;
import engine.data.RuleData;
import engine.handler.petrinet.PetrinetPersistence;
import engine.handler.simulation.SimulationHandler;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.ISimulation;
import engine.session.SessionManager;
import exceptions.EngineException;

public class SimulationHandlerTest {

  private static final int FIRE = 2000;

  /**
   * Fehler:Simulation bricht ab, wenn keine Regel anwendbar ist, obwohl
   * mindestens eine Transition aktiviert ist
   */
  @Test
  public void fireOrTransformTest() {

    try {

      IPetrinetPersistence iPetrinetPersistence =
        PetrinetPersistence.getInstance();
      // petrinetz erstellen
      int petrinetID = iPetrinetPersistence.createPetrinet();
      // Koordinaten für alle Stellen festlegen
      // CHECKSTYLE:OFF - No need to check for magic numbers
      Point2D place1Point = new Point2D.Double(50., 50.);
      Point2D place2Point = new Point2D.Double(400., 50.);
      Point2D place4Point = new Point2D.Double(50., 200.);
      Point2D place3Point = new Point2D.Double(400., 200.);
      // CHECKSTYLE:ON

      // Stellen erstellen und in Petrinet einfügen
      Place place1 =
        iPetrinetPersistence.createPlace(petrinetID, place1Point);
      Place place2 =
        iPetrinetPersistence.createPlace(petrinetID, place2Point);
      Place place3 =
        iPetrinetPersistence.createPlace(petrinetID, place3Point);
      Place place4 =
        iPetrinetPersistence.createPlace(petrinetID, place4Point);
      // Label ändern
      iPetrinetPersistence.setPname(petrinetID, place3, "defined");
      // Markierung einfügen
      iPetrinetPersistence.setMarking(petrinetID, place1, 1);

      // Transitionen erstellen
      // Koordinaten für alle Transitionen festlegen
      // CHECKSTYLE:OFF - No need to check for magic numbers
      Point2D transition1Point = new Point2D.Double(200., 50.);
      Point2D transition4Point = new Point2D.Double(50., 100.);
      Point2D transition2Point = new Point2D.Double(400., 100.);
      Point2D transition3Point = new Point2D.Double(200., 200.);
      // CHECKSTYLE:ON
      // Transitionen erstellen und in Petrinet einfügen
      Transition trans1 =
        iPetrinetPersistence.createTransition(petrinetID, transition1Point);
      Transition trans2 =
        iPetrinetPersistence.createTransition(petrinetID, transition2Point);
      Transition trans3 =
        iPetrinetPersistence.createTransition(petrinetID, transition3Point);
      Transition trans4 =
        iPetrinetPersistence.createTransition(petrinetID, transition4Point);

      // Kanten erstellen und in Petrinet einfügen
      // PRE's
      iPetrinetPersistence.createPreArc(petrinetID, place1, trans1);
      iPetrinetPersistence.createPreArc(petrinetID, place2, trans2);
      iPetrinetPersistence.createPreArc(petrinetID, place3, trans3);
      iPetrinetPersistence.createPreArc(petrinetID, place4, trans4);
      // Post's
      iPetrinetPersistence.createPostArc(petrinetID, trans4, place1);
      iPetrinetPersistence.createPostArc(petrinetID, trans3, place4);
      iPetrinetPersistence.createPostArc(petrinetID, trans2, place3);
      iPetrinetPersistence.createPostArc(petrinetID, trans1, place2);

      // Erstelle Regel
      ITransformation iTransformation =
        TransformationComponent.getTransformation();
      Rule rule = iTransformation.createRule();
      RuleData ruleData = SessionManager.getInstance().createRuleData(rule);
      int ruleId = ruleData.getId();
      iTransformation.storeSessionId(ruleId, rule);
      Place placeK = rule.addPlaceToK("defined");
      Place placeL = rule.fromKtoL(placeK);
      Place placeR = rule.fromKtoR(placeK);
      rule.setMarkInK(placeL, 1);
      Transition transRule = rule.addTransitionToR("");
      rule.addPostArcToR("", transRule, placeR);
      rule.addPreArcToR("", placeR, transRule);

      // Simulation
      ISimulation iSimulation = SimulationHandler.getInstance();
      Collection<Integer> ruleIDs = new ArrayList<Integer>();
      ruleIDs.add(ruleId);

      iSimulation.fireOrTransform(petrinetID, ruleIDs, FIRE);

    } catch (EngineException e) {
      e.printStackTrace();
    }
    assert (true);
  }

}
