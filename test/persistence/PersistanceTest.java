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

package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import transformation.Rule;
import util.StringUtilities;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.EngineMockupForPersistence;
import engine.attribute.NodeLayoutAttribute;
import engine.data.JungData;
import engine.data.RuleData;
import engine.handler.RuleNet;
import engine.handler.rule.RuleManipulation;
import engine.handler.rule.RulePersistence;
import engine.ihandler.IRulePersistence;
import engine.session.SessionManager;
import exceptions.EngineException;
import gui.Style;

public class PersistanceTest {

  private String pathString;

  @Before
  public void setup() {

    File directory = new File(".");
    pathString =
      StringUtilities.substringBeforeLast(directory.getAbsolutePath(), ".");
  }

  @Test
  public void testExamplePNMLParsing() {

    @SuppressWarnings("unused")
    Pnml pnml = new Pnml();
    JAXBContext context;
    try {
      context =
        JAXBContext.newInstance(persistence.Pnml.class, Arc.class,
          Converter.class, Graphics.class, InitialMarking.class, Name.class,
          Net.class, Page.class, Place.class, PlaceName.class,
          Position.class, Transition.class, TransitionLabel.class,
          TransitionName.class, TransitionRenew.class);
      Unmarshaller m = context.createUnmarshaller();

      DefaultValidationEventHandler dveh =
        new javax.xml.bind.helpers.DefaultValidationEventHandler();

      m.setEventHandler(dveh);

      pnml =
        (Pnml) m.unmarshal(new File(pathString
          + "test/persistence/example.pnml"));

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

    // TODO: enable code again if internal API is fully implemented; see
    // engine.session.SessionManager
    // .createPetrinetData(SessionManager.java:80)

    /*
     * PetrinetManipulation petriManipulation =
     * PetrinetManipulation.getInstance(); boolean success =
     * Converter.convertToPetrinet(pnml, petriManipulation);
     * assertEquals(true, success);
     */

    // TODO: fix this after the interface disaster is fixed.

    /*
     * Set<petrinet.Place> places = petrinet.getAllPlaces(); assertEquals(1,
     * places.size()); petrinet.Place place = places.iterator().next();
     * assertEquals(2, place.getMark()); assertEquals("myplace",
     * place.getName()); petrinet.Transition transition =
     * petrinet.getAllTransitions().iterator().next(); assertEquals("mytrans",
     * transition.getName());
     */

  }

  @Ignore
  @Test
  public void testSavePetrinet() {

    EngineMockupForPersistence mockup = new EngineMockupForPersistence();

    try {
      int pid = mockup.build();
      mockup.saveTest(pid, "/tmp", "petrinet_save_test", "pnml",
        Style.getNodeDistanceDefault());

    } catch (EngineException e) {
      e.printStackTrace();
    }

    assertTrue(new File("/tmp/petrinet_save_test.pnml").exists());
  }

  @Test
  public void testLoadRule() {

    IRulePersistence rulePersistence = RulePersistence.getInstance();

    int id =
      Persistence.loadRule(pathString + "test/persistence/testRule.PNML",
        rulePersistence);

    assert (id > -1);

  }

  @Ignore
  @Test
  public void testSaveRule()
    throws EngineException {

    RulePersistence handler = RulePersistence.getInstance();
    RuleManipulation handlerSave = RuleManipulation.getInstance();

    // try {
    // CHECKSTYLE:OFF - No need to check for magic numbers
    int id = handler.createRule();
    petrinet.model.Place place1 =
      handler.createPlace(id, RuleNet.K, new Point2D.Double(10, 10));
    petrinet.model.Place place3 =
      handler.createPlace(id, RuleNet.R, new Point2D.Double(10, 1000));
    /*
     * System.out.println("size:" + handler.getJungLayout(id,
     * RuleNet.K).getGraph().getVertices() .size());
     */

    handler.setPlaceColor(id, place1, new java.awt.Color(255, 0, 0));
    handler.setPlaceColor(id, place3, new java.awt.Color(0, 255, 0));

    handler.setCapacity(id, place1, 5);
    handler.setCapacity(id, place3, 6);

    petrinet.model.Transition trans2 =
      handler.createTransition(id, RuleNet.R, new Point2D.Double(10, 500));
    // CHECKSTYLE:ON

    /** TODO: map place 1 to INode in R */

    handler.createPreArc(id, RuleNet.R, place1, trans2);
    handler.createPostArc(id, RuleNet.R, trans2, place3);

    handlerSave.save(id, pathString + "test", "rule_save_test", "pnml");

    /*
     * } catch (EngineException e) { e.printStackTrace(); }
     */

    assertTrue(new File(pathString + "test/rule_save_test.pnml").exists());
  }

  /*
   * TODO: Test ausführen, sobald RuleHandler erweitert wurde. Noch nicht
   * lauffähig. Sollte funktionieren, sobald ie Methode createPlace im
   * RuleHandler erweitert wurde um Stellen im NAC-Netz zu speichern
   */
  @Ignore
  @Test
  public void testSaveRuleWithNac()
    throws EngineException {

    RulePersistence handler = RulePersistence.getInstance();
    RuleManipulation handlerSave = RuleManipulation.getInstance();

    // try {
    // CHECKSTYLE:OFF - No need to check for magic numbers
    int id = handler.createRule();
    handlerSave.addNac(0, id);
    petrinet.model.Place place1 =
      handler.createPlace(id, RuleNet.K, new Point2D.Double(10, 10));
    petrinet.model.Place place2 =
      handler.createPlace(id, RuleNet.NAC, new Point2D.Double(10, 100));
    petrinet.model.Place place3 =
      handler.createPlace(id, RuleNet.R, new Point2D.Double(10, 1000));
    /*
     * System.out.println("size:" + handler.getJungLayout(id,
     * RuleNet.K).getGraph().getVertices() .size());
     */

    handler.setPlaceColor(id, place1, new java.awt.Color(255, 0, 0));
    handler.setPlaceColor(id, place2, new java.awt.Color(0, 255, 0));
    handler.setPlaceColor(id, place3, new java.awt.Color(0, 0, 255));

    handler.setCapacity(id, place1, 4);
    handler.setCapacity(id, place2, 5);
    handler.setCapacity(id, place3, 6);

    petrinet.model.Transition trans2 =
      handler.createTransition(id, RuleNet.R, new Point2D.Double(10, 500));
    // CHECKSTYLE:ON

    /** TODO: map place 1 to INode in R */

    handler.createPreArc(id, RuleNet.R, place1, trans2);
    handler.createPostArc(id, RuleNet.R, trans2, place3);

    // Handler.Save()-Part
    SessionManager sessionManager = SessionManager.getInstance();

    RuleData ruleData = sessionManager.getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData jungDataL = ruleData.getLJungData();
    JungData jungDataK = ruleData.getKJungData();
    JungData jungDataR = ruleData.getRJungData();
    JungData jungDataNAC = ruleData.getNacJungData();

    Map<INode, NodeLayoutAttribute> nodeMapL =
      jungDataL.getNodeLayoutAttributes();
    Map<INode, NodeLayoutAttribute> nodeMapK =
      jungDataK.getNodeLayoutAttributes();
    Map<INode, NodeLayoutAttribute> nodeMapR =
      jungDataR.getNodeLayoutAttributes();
    Map<INode, NodeLayoutAttribute> nodeMapNAC =
      jungDataNAC.getNodeLayoutAttributes();

    if (nodeMapL == null) {
      System.out.println("save - nodeMapL == null");
    }
    if (nodeMapK == null) {
      System.out.println("save - nodeMapK == null");
    }
    if (nodeMapR == null) {
      System.out.println("save - nodeMapR == null");
    }
    if (nodeMapNAC == null) {
      System.out.println("save - nodeMapNAC == null");
    }

    Persistence.saveRule("rule_with_NAC_save_test.pnml", rule, nodeMapL,
      nodeMapK, nodeMapR, nodeMapNAC, ruleData.getKJungData().getNodeSize());

    /*
     * } catch (EngineException e) { e.printStackTrace(); }
     */

    assertTrue(new File(pathString + "rule_with_NAC_save_test.pnml").exists());
  }

  @Ignore
  @Test
  public void testPetrinetSaveLoadEquality()
    throws Exception {

    EngineMockupForPersistence mockup = new EngineMockupForPersistence();

    // save
    int pid = -1;
    // try {
    pid = mockup.build();
    Petrinet saved = mockup.getPetrinet(pid);

    mockup.saveTest(pid, "/tmp", "petrinet_saveload_test", "pnml",
      Style.getNodeDistanceDefault());
    int loaded_pid = mockup.load("/tmp", "petrinet_saveload_test.pnml");
    if (loaded_pid == -1) {
      fail("failed to load/parse petrinet");
    }
    Petrinet loaded = mockup.getPetrinet(loaded_pid);
    @SuppressWarnings({"rawtypes", "unused"})
    AbstractLayout layout = mockup.getJungLayout(loaded_pid);
    assertTrue(petrinetEqualsBasedOnLayout(mockup, saved, pid,
      mockup.getJungLayout(pid), loaded, loaded_pid,
      mockup.getJungLayout(loaded_pid)));
    /*
     * } catch(Exception e) { e.printStackTrace(); fail(e.getMessage()); }
     */
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Ignore
  public static boolean petrinetEqualsBasedOnLayout(
    EngineMockupForPersistence mockup, petrinet.model.Petrinet net1,
    int net1_pid, AbstractLayout layout1, petrinet.model.Petrinet net2,
    int net2_pid, AbstractLayout layout2)
    throws EngineException {

    Map<Point2D, petrinet.model.Place> pos2place =
      new HashMap<Point2D, petrinet.model.Place>();
    Map<Point2D, petrinet.model.Transition> pos2trans =
      new HashMap<Point2D, petrinet.model.Transition>();

    for (petrinet.model.Place p : net2.getPlaces()) {
      Point2D pos = new Point2D.Double(layout2.getX(p), layout2.getY(p));
      pos2place.put(pos, p);
    }

    for (petrinet.model.Transition t : net2.getTransitions()) {
      Point2D pos = new Point2D.Double(layout2.getX(t), layout2.getY(t));
      pos2trans.put(pos, t);
    }

    // test that all places and transitions of net2 are at the same
    // locations in net1
    for (petrinet.model.Place p : net1.getPlaces()) {
      Point2D pos = new Point2D.Double(layout1.getX(p), layout1.getY(p));
      if (pos2place.containsKey(pos)) {
        petrinet.model.Place net2Place = pos2place.get(pos);

        if (!net2Place.getName().equals(p.getName())) {
          return false;
        }
        if (net2Place.getCapacity() != p.getCapacity()) {
          return false;
        }
        if (net2Place.getMark() != p.getMark()) {
          return false;
        }

        java.awt.Color net2PlaceColor =
          mockup.getPlaceAttribute(net2_pid, net2Place).getColor();
        java.awt.Color net1PlaceColor =
          mockup.getPlaceAttribute(net1_pid, p).getColor();
        if (!net1PlaceColor.equals(net2PlaceColor)) {
          return false;
        }
        if (!mockup.getPlaceAttribute(net2_pid, net2Place).getPname().equals(
          mockup.getPlaceAttribute(net1_pid, p).getPname())) {
          return false;
        }
        int net1_marking = mockup.getPlaceAttribute(net1_pid, p).getMarking();
        int net2_marking =
          mockup.getPlaceAttribute(net2_pid, net2Place).getMarking();
        if (net2_marking != net1_marking) {
          return false;
        }
      } else {
        return false;
      }
    }

    for (petrinet.model.Transition t : net1.getTransitions()) {
      Point2D pos = new Point2D.Double(layout1.getX(t), layout1.getY(t));
      if (pos2trans.containsKey(pos)) {
        petrinet.model.Transition net2Transition = pos2trans.get(pos);

        if (!net2Transition.getName().equals(t.getName())) {
          return false;
        }
        if (!net2Transition.getTlb().equals(t.getTlb())) {
          return false;
        }
      } else {
        return false;
      }
    }

    // test the arcs
    for (IArc arcIn1 : net1.getArcs()) {
      Point2D startA =
        new Point2D.Double(layout1.getX(arcIn1.getSource()),
          layout1.getX(arcIn1.getSource()));
      Point2D endA =
        new Point2D.Double(layout1.getX(arcIn1.getTarget()),
          layout1.getX(arcIn1.getTarget()));

      boolean found = false;
      for (IArc arcIn2 : net2.getArcs()) {
        Point2D startB =
          new Point2D.Double(layout2.getX(arcIn2.getSource()),
            layout2.getX(arcIn2.getSource()));
        Point2D endB =
          new Point2D.Double(layout2.getX(arcIn2.getTarget()),
            layout2.getX(arcIn2.getTarget()));

        if (startA.equals(startB) && endA.equals(endB)) {
          found = true;
          break;
        }
      }
      if (!found) {
        return false;
      }
    }

    return true;
  }
}
