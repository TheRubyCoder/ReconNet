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

package transformation.unit;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import petrinet.model.Place;
import engine.handler.RuleNet;
import engine.handler.rule.RulePersistence;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.IRulePersistence;
import engine.ihandler.ITransformationUnitManipulation;
import engine.session.SessionManager;
import exceptions.EngineException;
import gui.EngineAdapter;

public class TransformationUnitTest {

  private IPetrinetManipulation petrinetManipulation;
  private IRuleManipulation ruleManipulation;
  private ITransformationUnitManipulation transformationUnitManipulation;

  // IRulePersistence Interface used for test cases because it offers more
  // internal data to work with (place, transition objects)
  private IRulePersistence rulePersistence;

  // ID of the Petrinet created before each test
  private int petrinetId;

  private int r1;
  private int r2;
  private int r3;
  private int r4;

  private HashMap<String, Integer> ruleNameToIdMapping;

  @Before
  public void setUp() {

    this.petrinetManipulation = EngineAdapter.getPetrinetManipulation();
    this.ruleManipulation = EngineAdapter.getRuleManipulation();
    this.rulePersistence = RulePersistence.getInstance();
    this.transformationUnitManipulation =
      EngineAdapter.getTransformationUnitManipulation();

    this.petrinetId = this.petrinetManipulation.createPetrinet();

    // Create four empty rules which can be used in the tests
    this.r1 = this.ruleManipulation.createRule();
    this.r2 = this.ruleManipulation.createRule();
    this.r3 = this.ruleManipulation.createRule();
    this.r4 = this.ruleManipulation.createRule();

    // Names of rules to work with in the control conditions
    this.ruleNameToIdMapping = new HashMap<String, Integer>();
    ruleNameToIdMapping.put("r1", this.r1);
    ruleNameToIdMapping.put("r2", this.r2);
    ruleNameToIdMapping.put("r3", this.r3);
    ruleNameToIdMapping.put("r4", this.r4);
  }

  // Executes two rules:
  // r1 adds place p1
  // r2 removes place p1
  // r1;r2 is a legit sequence
  @Test
  public void testSequenceOperator()
    throws EngineException {

    // a Place 'p1' in (K+)R of rule 1 -> place will be created
    Place p1a =
      this.rulePersistence.createPlace(this.r1, RuleNet.R,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r1, p1a, "p1");

    // a Place 'p1' in L(+K) of rule 2 -> place will be removed
    Place p1b =
      this.rulePersistence.createPlace(this.r2, RuleNet.L,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r2, p1b, "p1");

    // File location can be ignored
    int transformationUnitId =
      this.transformationUnitManipulation.createTransformationUnit("", "");

    this.transformationUnitManipulation.setControlExpression(
      transformationUnitId, "r1;r2");

    this.transformationUnitManipulation.executeTransformationUnit(
      transformationUnitId, petrinetId, ruleNameToIdMapping);

    Assert.assertEquals(
      0,
      this.petrinetManipulation.getJungLayout(petrinetId).getGraph().getVertexCount());
  }

  // Executes a rules as long as possible
  // r1 adds a place 'x'
  // r1 has NAC with 10x place 'x'
  // r1 can be executed 10 times
  @Test
  public void testAsLongAsPossible()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(r1);

    Place p = null;

    p =
      this.rulePersistence.createPlace(this.r1, RuleNet.R,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r1, p, "x");

    for (int i = 0; i < 10; i++) {
      p =
        this.rulePersistence.createPlace(this.r1, nacId, new Point2D.Double(
          0, i * 50));
      this.ruleManipulation.setPname(this.r1, nacId, p, "x");
    }

    int transformationUnitId =
      this.transformationUnitManipulation.createTransformationUnit("", "");

    this.transformationUnitManipulation.setControlExpression(
      transformationUnitId, "r1!");

    this.transformationUnitManipulation.executeTransformationUnit(
      transformationUnitId, petrinetId, ruleNameToIdMapping);

    Assert.assertEquals(10, this.petrinetManipulation.getJungLayout(
      petrinetId).getGraph().getVertexCount());
  }

  // Executes a rules a random number of times
  // r1 adds a place 'x'
  // r1 has NAC with 10x place 'x'
  // r1 can be executed 0..10 times
  @Test
  public void testRandomNumberOfTimes()
    throws EngineException {

    UUID nacId = ruleManipulation.createNac(r1);

    Place p = null;

    p =
      this.rulePersistence.createPlace(this.r1, RuleNet.R,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r1, p, "x");

    for (int i = 0; i < 10; i++) {
      p =
        this.rulePersistence.createPlace(this.r1, nacId, new Point2D.Double(
          0, i * 50));
      this.ruleManipulation.setPname(this.r1, nacId, p, "x");
    }

    int transformationUnitId =
      this.transformationUnitManipulation.createTransformationUnit("", "");

    this.transformationUnitManipulation.setControlExpression(
      transformationUnitId, "r1*");

    this.transformationUnitManipulation.executeTransformationUnit(
      transformationUnitId, petrinetId, ruleNameToIdMapping);

    int cntPlaces =
      this.petrinetManipulation.getJungLayout(petrinetId).getGraph().getVertexCount();

    Assert.assertTrue((cntPlaces >= 0) && (cntPlaces <= 10));
  }

  // Bad test design - is only legit after subsequent test runs
  // Executes one of two rules
  // r1: adds place 'x'
  // r2: adds place 'y'
  // checks afterwards, if either a place 'x' or 'y' is present
  @Test
  public void testAlternative()
    throws EngineException {

    Place x =
      this.rulePersistence.createPlace(this.r1, RuleNet.R,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r1, x, "x");

    Place y =
      this.rulePersistence.createPlace(this.r2, RuleNet.R,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r2, y, "y");

    int transformationUnitId =
      this.transformationUnitManipulation.createTransformationUnit("", "");

    this.transformationUnitManipulation.setControlExpression(
      transformationUnitId, "r1|r2");

    this.transformationUnitManipulation.executeTransformationUnit(
      transformationUnitId, petrinetId, ruleNameToIdMapping);

    for (Place p : SessionManager.getInstance().getPetrinetData(petrinetId).getPetrinet().getPlaces()) {

      Assert.assertTrue((p.getName().equals("x"))
        || (p.getName().equals("y")));
    }
  }

  // Tests the recovery of the petrinet after execution of the transformation
  // unit failed
  // Executes r1 several times and then r2, which will fail
  // r1 adds place 'x'
  // r2 removes place 'y' which won't be present
  // petrinet must be recovered to initial state
  @Test
  public void testRecoveryAfterFail()
    throws EngineException {

    Place x =
      this.rulePersistence.createPlace(this.r1, RuleNet.R,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r1, x, "x");

    Place y =
      this.rulePersistence.createPlace(this.r2, RuleNet.L,
        new Point2D.Double(0, 0));
    this.ruleManipulation.setPname(this.r2, y, "y");

    int transformationUnitId =
      this.transformationUnitManipulation.createTransformationUnit("", "");

    this.transformationUnitManipulation.setControlExpression(
      transformationUnitId, "r1;r1;r1;r1;r2");

    try {
      this.transformationUnitManipulation.executeTransformationUnit(
        transformationUnitId, petrinetId, ruleNameToIdMapping);
    } catch (EngineException e) {
      Assert.assertEquals(0, SessionManager.getInstance().getPetrinetData(
        petrinetId).getPetrinet().getPlaces().size());
    }
  }

}
