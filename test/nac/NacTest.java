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

package nac;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Renews;
import petrinet.model.Transition;
import transformation.NAC;
import transformation.Rule;
import engine.data.JungData;
import engine.data.RuleData;
import engine.handler.RuleNet;
import engine.handler.rule.RuleManipulation;
import engine.handler.rule.RulePersistence;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.IRulePersistence;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.IllegalNacManipulationException;

public class NacTest {

  // Interface used to get Object references
  private IRulePersistence rulePersistence;

  // Interface to test
  private IRuleManipulation ruleManipulation;

  private int ruleId = 12345;
  private RuleData ruleData;
  private Rule rule;

  /**
   * Sets up an empty RuleData instance for each test which can be modified
   * with the public interface via the fix ruleId.
   */
  @Before
  public void setUp() {

    rule = new Rule();

    ruleData = SessionManager.getInstance().createRuleData(rule);
    ruleId = ruleData.getId();

    ruleManipulation = RuleManipulation.getInstance();
    rulePersistence = RulePersistence.getInstance();
  }

  @Test
  public void testCreateSingleNacInEmptyRule()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    NAC nac = rule.getNAC(nacId);
    assertNotNull(nac);

    JungData nacJungData = ruleData.getNacJungData(nacId);

    assertEquals(0, nacJungData.getJungGraph().getEdgeCount());
    assertEquals(0, nacJungData.getJungGraph().getVertexCount());
  }

  @Test
  public void testCreateMultipleNacsInEmptyRule()
    throws EngineException {

    int nacsToCreate = 3;

    List<UUID> nacIds = new ArrayList<UUID>();

    for (int i = 0; i < nacsToCreate; i++) {
      UUID nacId = ruleManipulation.createNac(ruleId);
      nacIds.add(nacId);
    }

    for (UUID nacId : nacIds) {

      NAC nac = rule.getNAC(nacId);
      assertNotNull(nac);

      assertEquals(0, nac.getNac().getPlaces().size());
      assertEquals(0, nac.getNac().getTransitions().size());
      assertEquals(0, nac.getNac().getArcs().size());

      JungData nacJungData = ruleData.getNacJungData(nacId);

      assertEquals(0, nacJungData.getJungGraph().getEdgeCount());
      assertEquals(0, nacJungData.getJungGraph().getVertexCount());
    }
  }

  @Test
  public void testCreateSingleNacWithPlaceInL()
    throws EngineException {

    ruleManipulation.createPlace(ruleId, RuleNet.L, new Point2D.Double(0, 0));

    UUID nacId = ruleManipulation.createNac(ruleId);

    NAC nac = rule.getNAC(nacId);

    for (Place lPlace : rule.getL().getPlaces()) {
      assertNotNull(nac.fromLtoNac(lPlace));
    }

    assertEquals(0, rule.getL().getTransitions().size());
    assertEquals(0, rule.getL().getArcs().size());

    JungData lJungData = ruleData.getLJungData();
    assertEquals(1, lJungData.getJungGraph().getVertexCount());
    assertEquals(0, lJungData.getJungGraph().getEdgeCount());

    JungData nacJungData = ruleData.getNacJungData(nacId);
    assertEquals(1, nacJungData.getJungGraph().getVertexCount());
    assertEquals(0, nacJungData.getJungGraph().getEdgeCount());
  }

  @Test
  public void testCreateSingleNacWithTransitionInL()
    throws EngineException {

    ruleManipulation.createTransition(ruleId, RuleNet.L, new Point2D.Double(
      0, 0));

    UUID nacId = ruleManipulation.createNac(ruleId);

    NAC nac = rule.getNAC(nacId);

    for (Transition lTransition : rule.getL().getTransitions()) {
      assertNotNull(nac.fromLtoNac(lTransition));
    }

    assertEquals(0, rule.getL().getPlaces().size());
    assertEquals(0, rule.getL().getArcs().size());

    JungData lJungData = ruleData.getLJungData();
    assertEquals(1, lJungData.getJungGraph().getVertexCount());
    assertEquals(0, lJungData.getJungGraph().getEdgeCount());

    JungData nacJungData = ruleData.getNacJungData(nacId);
    assertEquals(1, nacJungData.getJungGraph().getVertexCount());
    assertEquals(0, nacJungData.getJungGraph().getEdgeCount());
  }

  @Test
  public void testSetPnameInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Place nacPlace =
      rulePersistence.createPlace(ruleId, nacId, new Point2D.Double(0, 0));

    String placeName = "xyz";

    ruleManipulation.setPname(ruleId, nacId, nacPlace, placeName);
    assertEquals(placeName, nacPlace.getName());
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLPlaceNameInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(0, 0));

    NAC nac = rule.getNAC(nacId);
    Place nacPlace = nac.fromLtoNac(lPlace);

    ruleManipulation.setPname(ruleId, nacId, nacPlace, "xyz");
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLPlaceMarkingInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(0, 0));

    NAC nac = rule.getNAC(nacId);
    Place nacPlace = nac.fromLtoNac(lPlace);

    ruleManipulation.setMarking(ruleId, nacId, nacPlace, 999);
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLPlaceCapacityInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(0, 0));

    NAC nac = rule.getNAC(nacId);
    Place nacPlace = nac.fromLtoNac(lPlace);

    ruleManipulation.setCapacity(ruleId, nacId, nacPlace, 999);
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLPlaceColorInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(0, 0));

    NAC nac = rule.getNAC(nacId);
    Place nacPlace = nac.fromLtoNac(lPlace);

    ruleManipulation.setPlaceColor(ruleId, nacId, nacPlace, Color.blue);
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestDeleteLPlaceInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(0, 0));

    NAC nac = rule.getNAC(nacId);
    Place nacPlace = nac.fromLtoNac(lPlace);

    ruleManipulation.deletePlace(ruleId, nacId, nacPlace);
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLTransitionNameInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));

    NAC nac = rule.getNAC(nacId);
    Transition nacTransition = nac.fromLtoNac(lTransition);

    ruleManipulation.setTname(ruleId, nacId, nacTransition, "xyz");
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLTransitionLabelInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));

    NAC nac = rule.getNAC(nacId);
    Transition nacTransition = nac.fromLtoNac(lTransition);

    ruleManipulation.setTlb(ruleId, nacId, nacTransition, "xyz");
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLTransitionRenewInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));

    NAC nac = rule.getNAC(nacId);
    Transition nacTransition = nac.fromLtoNac(lTransition);

    IRenew renew = Renews.fromString("id");
    ruleManipulation.setRnw(ruleId, nacId, nacTransition, renew);
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestSetLArcWeightInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));
    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(50,
        50));
    PostArc lPostArc =
      rulePersistence.createPostArc(ruleId, RuleNet.L, lTransition, lPlace);

    NAC nac = rule.getNAC(nacId);
    PostArc nacPostArc = nac.fromLtoNac(lPostArc);

    ruleManipulation.setWeight(ruleId, nacId, nacPostArc, 999);
  }

  @Test(expected = IllegalNacManipulationException.class)
  public void failtestDeleteLTransitionInNac()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));

    NAC nac = rule.getNAC(nacId);
    Transition nacTransition = nac.fromLtoNac(lTransition);

    ruleManipulation.deleteTransition(ruleId, nacId, nacTransition);
  }

  @Test
  public void testAfterwardRuleModellingWithMultipleNacs()
    throws EngineException {

    int nacsToCreate = 3;

    List<UUID> nacIds = new ArrayList<UUID>();

    for (int i = 0; i < nacsToCreate; i++) {
      UUID nacId = ruleManipulation.createNac(ruleId);
      nacIds.add(nacId);
    }

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));
    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(50,
        50));
    PostArc lPostArc =
      rulePersistence.createPostArc(ruleId, RuleNet.L, lTransition, lPlace);
    PreArc lPreArc =
      rulePersistence.createPreArc(ruleId, RuleNet.L, lPlace, lTransition);

    for (UUID nacId : nacIds) {

      NAC nac = rule.getNAC(nacId);

      Transition nacTransition = nac.fromLtoNac(lTransition);
      Place nacPlace = nac.fromLtoNac(lPlace);
      PostArc nacPostArc = nac.fromLtoNac(lPostArc);
      PreArc nacPreArc = nac.fromLtoNac(lPreArc);

      assertNotNull(nacTransition);
      assertNotNull(nacPlace);
      assertNotNull(nacPostArc);
      assertNotNull(nacPreArc);

      JungData nacJungData = ruleData.getNacJungData(nacId);
      assertTrue(nacJungData.getJungGraph().containsVertex(nacTransition));
      assertTrue(nacJungData.getJungGraph().containsVertex(nacPlace));
      assertTrue(nacJungData.getJungGraph().containsEdge(nacPostArc));
      assertTrue(nacJungData.getJungGraph().containsEdge(nacPreArc));
    }

  }

  @Test
  public void testDeleteNacFromRule()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    assertEquals(1, rule.getNACs().size());
    assertEquals(1, ruleData.getNacJungDataSet().size());

    ruleManipulation.deleteNac(ruleId, nacId);

    assertEquals(0, rule.getNACs().size());
    assertEquals(0, ruleData.getNacJungDataSet().size());

  }

  @Test
  public void testMoveNacNodeBecauseMoveLNode()
    throws EngineException {

    int nacsToCreate = 3;

    List<UUID> nacIds = new ArrayList<UUID>();

    for (int i = 0; i < nacsToCreate; i++) {
      UUID nacId = ruleManipulation.createNac(ruleId);
      nacIds.add(nacId);
    }

    int posTransitionOld = 100;
    int posPlaceOld = 200;
    int relativeMovement = 10;

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        posTransitionOld, posTransitionOld));
    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(
        posPlaceOld, posPlaceOld));

    // move nodes relative with x=y=11
    ruleManipulation.moveNode(ruleId, lTransition, new Point2D.Double(
      relativeMovement, relativeMovement));
    ruleManipulation.moveNode(ruleId, lPlace, new Point2D.Double(
      relativeMovement, relativeMovement));

    for (UUID nacId : nacIds) {

      NAC nac = rule.getNAC(nacId);

      Transition nacTransition = nac.fromLtoNac(lTransition);
      Place nacPlace = nac.fromLtoNac(lPlace);

      Point2D newPlacePos =
        new Point2D.Double(posPlaceOld + relativeMovement, posPlaceOld
          + relativeMovement);
      Point2D newTransitionPos =
        new Point2D.Double(posTransitionOld + relativeMovement,
          posTransitionOld + relativeMovement);

      assertEquals(
        newPlacePos,
        ruleData.getNacJungData(nacId).getNodeLayoutAttributes().get(nacPlace).getCoordinate());
      assertEquals(newTransitionPos,
        ruleData.getNacJungData(nacId).getNodeLayoutAttributes().get(
          nacTransition).getCoordinate());

    }

  }

  @Test
  @Ignore
  public void testDeleteLPlaceCascade()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(ruleId);

    Transition lTransition =
      rulePersistence.createTransition(ruleId, RuleNet.L, new Point2D.Double(
        0, 0));
    Place lPlace =
      rulePersistence.createPlace(ruleId, RuleNet.L, new Point2D.Double(50,
        50));
    PostArc lPostArc =
      rulePersistence.createPostArc(ruleId, RuleNet.L, lTransition, lPlace);
    PreArc lPreArc =
      rulePersistence.createPreArc(ruleId, RuleNet.L, lPlace, lTransition);

    PostArc nacPostArc = null;
    PreArc nacPreArc = null;

    nacPostArc = rule.getNAC(nacId).fromLtoNac(lPostArc);
    nacPreArc = rule.getNAC(nacId).fromLtoNac(lPreArc);

    assertNotNull(nacPostArc);
    assertNotNull(nacPreArc);

    ruleManipulation.deletePlace(ruleId, RuleNet.L, lPlace);

    nacPostArc = rule.getNAC(nacId).fromLtoNac(lPostArc);
    nacPreArc = rule.getNAC(nacId).fromLtoNac(lPreArc);

    assertNull(nacPostArc);
    assertNull(nacPreArc);

    // TODO: This test showed up a bug in ReConNet. If place with dangling
    // arcs is removed, the arcs itself will be removed by the Method
    // ruleData.deleteDataOfMissingElements(rule). But this does not clear the
    // arcs in the Bidi-Maps
  }

}
