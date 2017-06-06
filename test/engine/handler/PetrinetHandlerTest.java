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

package engine.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import petrinet.model.IArc;
import petrinet.model.Place;
import petrinet.model.Transition;
import engine.handler.petrinet.PetrinetManipulation;
import engine.handler.petrinet.PetrinetPersistence;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;
import exceptions.ShowAsWarningException;

public class PetrinetHandlerTest {

  private IPetrinetPersistence petrinetHandler =
    PetrinetPersistence.getInstance();
  private IPetrinetManipulation petrinetHandler2 =
    PetrinetManipulation.getInstance();

  // ids from all Petrinet *************************************************
  private int idPetrinet = -1;

  // Places & Places *******************************************************
  private Point2D pointPlace1 = null;
  private Point2D pointPlace2 = null;
  private Point2D pointPlace3 = null;
  private Point2D pointPlace4 = null;

  private Place place1 = null;
  private Place place2 = null;
  private Place place3 = null;
  private Place place4 = null;

  // Transitions & Places **************************************************
  private Point2D pointTransition1 = null;
  private Point2D pointTransition2 = null;
  private Point2D pointTransition3 = null;
  private Point2D pointTransition4 = null;

  private Transition transition1 = null;
  private Transition transition2 = null;
  private Transition transition3 = null;
  private Transition transition4 = null;

  // Arcs ******************************************************************
  private IArc arc1;
  private IArc arc2;
  private IArc arc3;
  private IArc arc4;
  private IArc arc5;
  private IArc arc6;
  private IArc arc7;
  private IArc arc8;
  private IArc arc9;

  @Before
  public void setUp()
    throws Exception {

    // CHECKSTYLE:OFF - No need to check for magic numbers
    pointPlace1 = new Point(10, 10);
    pointPlace2 = new Point(90, 10);
    pointPlace3 = new Point(10, 90);
    pointPlace4 = new Point(90, 90);

    pointTransition1 = new Point(45, 10);
    pointTransition2 = new Point(90, 45);
    pointTransition3 = new Point(10, 45);
    pointTransition4 = new Point(45, 90);
    // CHECKSTYLE:ON
  }

  @Test
  public void testCreatePetrinet() {

    // init
    initPetrinet();

    // test
    assertTrue(idPetrinet != -1);

  }

  @Test
  public void testCreatePlace() {

    // init all
    initPetrinet();
    initPlace();

    // test
    assertNotNull(place1);
    assertNotNull(place2);
    assertNotNull(place3);
    assertNotNull(place4);

  }

  @Test
  public void testCreateTransition() {

    // init all
    initPetrinet();
    initTransition();

    // test
    assertNotNull(transition1);
    assertNotNull(transition2);
    assertNotNull(transition3);
    assertNotNull(transition4);

    try {

      // wrong id
      petrinetHandler.createTransition(-1, pointTransition1);

      fail("something is wrong (createTransition): wrong id");

    } catch (Exception e) {
      assertTrue(true);
    }

    try {

      // null as Point
      petrinetHandler.createTransition(idPetrinet, null);

      fail("something is wrong (createTransition): null as Point");
    } catch (EngineException e) {
      fail("No engine exception expected");
    } catch (ShowAsWarningException e) {
      assertTrue(true);
    }

    try {
      // wrong Point => Point(-1, -1)
      Point2D.Double pt2D = new Point2D.Double(-1., -1.);
      petrinetHandler.createTransition(idPetrinet, pt2D);

      // Fail is no longer correct here. Transitions are now allowed to be
      // created in negative positions
      // fail("something is wrong (createTransition):"
      // + " wrong Point => Point(-1, -1)");
    } catch (EngineException e) {
      fail("No engine exception expected");
    } catch (ShowAsWarningException e) {
      assertTrue(true);
    }

  }

  @Test
  public void testCreateArc() {

    // init all
    initPetrinet();
    initPlace();
    initTransition();
    initArc();

    assertNotNull(arc1);
    assertNotNull(arc2);
    assertNotNull(arc3);
    assertNotNull(arc4);
    assertNotNull(arc5);
    assertNotNull(arc6);
    assertNotNull(arc7);
    assertNotNull(arc8);
    assertNotNull(arc9);

    // id is wrong
    try {
      petrinetHandler.createPreArc(-1, place4, transition4);
      fail("testCreateArc: id is wrong");
    } catch (Exception e) {
      assertTrue(true);
    }
    try {
      petrinetHandler.createPostArc(-1, transition4, place4);
      fail("testCreateArc: id is wrong");
    } catch (Exception e) {
      assertTrue(true);
    }

    // from is wrong
    try {
      petrinetHandler.createPreArc(idPetrinet, null, transition4);
      fail("testCreateArc: from is wrong");
    } catch (EngineException e) {
      assertTrue(true);
    }

    try {
      petrinetHandler.createPostArc(idPetrinet, null, place4);
      fail("testCreateArc: from is wrong");
    } catch (EngineException e) {
      assertTrue(true);
    }

    // to is wrong
    try {
      petrinetHandler.createPreArc(idPetrinet, place4, null);
      fail("testCreateArc: to is wrong");
    } catch (EngineException e) {
      assertTrue(true);
    }
    try {
      petrinetHandler.createPostArc(idPetrinet, transition4, null);
      fail("testCreateArc: to is wrong");
    } catch (EngineException e) {
      assertTrue(true);
    }

  }

  @Test
  public void testDeleteArc() {

    // init all
    initPetrinet();
    initPlace();
    initTransition();
    initArc();

    // test
    try {
      petrinetHandler2.deleteArc(idPetrinet, arc1);
      petrinetHandler2.deleteArc(idPetrinet, arc2);
      petrinetHandler2.deleteArc(idPetrinet, arc3);
      petrinetHandler2.deleteArc(idPetrinet, arc4);
      petrinetHandler2.deleteArc(idPetrinet, arc5);
      petrinetHandler2.deleteArc(idPetrinet, arc6);
      petrinetHandler2.deleteArc(idPetrinet, arc7);
      petrinetHandler2.deleteArc(idPetrinet, arc8);
      petrinetHandler2.deleteArc(idPetrinet, arc9);
    } catch (EngineException e) {
      // if you this test.. something is wrong..!
      fail("testDeleteArc");
    }

    // delete one item two times
    try {
      petrinetHandler2.deleteArc(idPetrinet, arc1);
      fail("testDeleteArc: delete one item two times");
    } catch (EngineException e) {
      assertTrue(true);
    }

    // wrong id
    try {
      petrinetHandler2.deleteArc(-1, arc1);
      fail("testDeleteArc: wrong id");
    } catch (Exception e) {
      assertTrue(true);
    }

    // wrong Arc
    try {
      petrinetHandler2.deleteArc(idPetrinet, null);
      fail("testDeleteArc: wrong Arc");
    } catch (EngineException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testDeletePlace() {

    assertTrue(true);
  }

  @Test
  public void testDeleteTransition() {

    assertTrue(true);
  }

  @Test
  public void testGetArcAttribute() {

    assertTrue(true);
  }

  @Test
  public void testGetJungLayout() {

    assertTrue(true);
  }

  @Test
  public void testGetPlaceAttribute() {

    assertTrue(true);
  }

  @Test
  public void testGetTransitionAttribute() {

    assertTrue(true);
  }

  @Test
  public void testMoveGraph() {

    assertTrue(true);
  }

  @Test
  public void testMoveNode() {

    assertTrue(true);
  }

  @Test
  public void testSave() {

    assertTrue(true);
  }

  @Test
  public void testLoad() {

    assertTrue(true);
  }

  @Test
  public void testSetMarking() {

    assertTrue(true);
  }

  @Test
  public void testSetPname() {

    assertTrue(true);
  }

  @Test
  public void testSetTlb() {

    assertTrue(true);
  }

  @Test
  public void testSetTname() {

    assertTrue(true);
  }

  @Test
  public void testSetWeight() {

    assertTrue(true);
  }

  @Test
  public void testGetNodeType() {

    assertTrue(true);
  }

  private void initPetrinet() {

    idPetrinet = petrinetHandler.createPetrinet();
  }

  private void initPlace() {

    try {

      place1 = petrinetHandler.createPlace(idPetrinet, pointPlace1);
      place2 = petrinetHandler.createPlace(idPetrinet, pointPlace2);
      place3 = petrinetHandler.createPlace(idPetrinet, pointPlace3);
      place4 = petrinetHandler.createPlace(idPetrinet, pointPlace4);

    } catch (EngineException e) {

      // if you this test.. something is wrong..!
      fail("testCreatePlace: can not create Place");

    }
  }

  private void initTransition() {

    try {

      transition1 =
        petrinetHandler.createTransition(idPetrinet, pointTransition1);
      transition2 =
        petrinetHandler.createTransition(idPetrinet, pointTransition2);
      transition3 =
        petrinetHandler.createTransition(idPetrinet, pointTransition3);
      transition4 =
        petrinetHandler.createTransition(idPetrinet, pointTransition4);

    } catch (EngineException e) {

      // if you this test.. something is wrong..!
      fail("testCreateTransition: can not create Transitions");

    }
  }

  private void initArc() {

    try {

      arc1 = petrinetHandler.createPreArc(idPetrinet, place1, transition1);
      arc2 = petrinetHandler.createPreArc(idPetrinet, place2, transition1);
      arc3 = petrinetHandler.createPreArc(idPetrinet, place3, transition1);
      arc4 = petrinetHandler.createPreArc(idPetrinet, place4, transition1);
      arc5 = petrinetHandler.createPostArc(idPetrinet, transition1, place1);
      arc6 = petrinetHandler.createPostArc(idPetrinet, transition2, place2);
      arc7 = petrinetHandler.createPostArc(idPetrinet, transition3, place2);
      arc8 = petrinetHandler.createPostArc(idPetrinet, transition3, place3);
      arc9 = petrinetHandler.createPostArc(idPetrinet, transition4, place4);

    } catch (EngineException e) {

      // if you this test.. something is wrong..!
      fail("testCreateArc");

    }
  }

}
