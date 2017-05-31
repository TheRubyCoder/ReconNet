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

package engine.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import gui.Style;
import petrinet.PetrinetComponent;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;

/**
 * @author Mathias Blumreiter
 */
public class JungDataTest {

  private Petrinet p;

  private Place place1;
  private Place place2;

  private Transition transition1;
  private Transition transition2;

  private IArc arc11;
  private IArc arc12;
  private IArc arc22;

  private JungData emptyJung;
  private DirectedGraph<INode, IArc> graph;
  private AbstractLayout<INode, IArc> layout;

  private Point2D pointPositive1;
  private Point2D pointPositive1TooCloseLeft;
  private Point2D pointPositive1TooCloseTop;
  private Point2D pointPositive1TooCloseBottom;
  private Point2D pointPositive1TooCloseRight;

  private Point2D pointPositive1JustEnoughLeft;
  private Point2D pointPositive1JustEnoughTop;
  private Point2D pointPositive1JustEnoughBottom;
  private Point2D pointPositive1JustEnoughRight;

  private Point2D pointPositive2;
  private Point2D pointNegativeX;
  private Point2D pointNegativeY;
  private Point2D pointNegativeXY;
  private int justFarEnoughAway =
    (int) Math.round(Style.getNodeDistanceDefault() + 0.5d);
  private int farEnoughAway = justFarEnoughAway + 1;
  private static final int X = 100;
  private static final int Y = 100;

  static final float COLOR_H = 67;
  static final float COLOR_S = 155;
  static final float COLOR_B = 17;

  /**
   * Erstellen eines Netzes
   */
  @Before
  public void setUp()
    throws Exception {

    p = PetrinetComponent.getPetrinet().createPetrinet();

    place1 = p.addPlace("A");
    place2 = p.addPlace("B");

    transition1 = p.addTransition("t1", Renews.COUNT);
    transition2 = p.addTransition("t2", Renews.COUNT);

    arc11 = p.addPreArc("y1", place1, transition1);

    arc12 = p.addPostArc("x1", transition1, place2);
    arc22 = p.addPostArc("x2", transition2, place2);

    place1.setMark(1);

    graph = new DirectedSparseGraph<INode, IArc>();
    layout = new StaticLayout<INode, IArc>(graph);

    emptyJung = new JungData(graph, layout);

    pointPositive1 = new Point(X, Y);

    pointPositive1TooCloseLeft = new Point(X - justFarEnoughAway + 1, Y);
    pointPositive1TooCloseRight = new Point(X + justFarEnoughAway - 1, Y);
    pointPositive1TooCloseTop = new Point(X, Y + justFarEnoughAway - 1);
    pointPositive1TooCloseBottom = new Point(X, Y - justFarEnoughAway + 1);

    pointPositive1JustEnoughLeft = new Point(X - justFarEnoughAway, Y);
    pointPositive1JustEnoughRight = new Point(X + justFarEnoughAway, Y);
    pointPositive1JustEnoughTop = new Point(X, Y + justFarEnoughAway);
    pointPositive1JustEnoughBottom = new Point(X, Y - justFarEnoughAway);

    pointPositive2 = new Point(X + farEnoughAway, Y + farEnoughAway);

    pointNegativeX = new Point(-1, 1);
    pointNegativeY = new Point(1, -1);
    pointNegativeXY = new Point(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullTest_construct_1() {

    new JungData(null, layout);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullTest_construct_2() {

    new JungData(graph, null);
  }

  @Test
  public void getJungGraph() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive2);
    emptyJung.createArc(arc11, place1, transition1);
    assertTrue(graph.getVertexCount() > 0 && graph.getEdgeCount() > 0);
    assertTrue((new JungData(graph, layout)).getJungGraph().equals(graph));
  }

  @Test
  public void getJungLayout() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive2);
    emptyJung.createArc(arc11, place1, transition1);
    assertTrue(graph.getVertexCount() > 0 && graph.getEdgeCount() > 0);
    assertTrue((new JungData(graph, layout)).getJungLayout().equals(layout));
  }

  /**
   * Mindestentfernung 2er Nodes mindestens 1
   */
  @Test
  public void testCreateArc_MinNodeDistanceRadiusSize() {

    assertTrue(Style.getNodeDistanceDefault() >= 1);
  }

  /**
   * Arc (Place > Transition) nach hinzufügen auch enthalten
   */
  @Test
  public void testCreateArc_PlaceToTranstion() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive2);

    emptyJung.createArc(arc11, place1, transition1);

    assertTrue(emptyJung.getJungGraph().containsEdge(arc11));
  }

  /**
   * Arc (Transition > Place) nach hinzufügen auch enthalten
   */
  @Test
  public void testCreateArc_TranstionToPlace() {

    emptyJung.createPlace(place2, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive2);

    emptyJung.createArc(arc12, transition1, place2);

    assertTrue(emptyJung.getJungGraph().containsEdge(arc12));
  }

  /**
   * Place hinzufügen
   */
  @Test
  public void testCreatePlace() {

    emptyJung.createPlace(place1, pointPositive1);
    assertTrue(emptyJung.getJungGraph().containsVertex(place1));
    assertTrue(Double.compare(pointPositive1.getX(),
      emptyJung.getJungLayout().getX(place1)) == 0);
    assertTrue(Double.compare(pointPositive1.getY(),
      emptyJung.getJungLayout().getY(place1)) == 0);
  }

  /**
   * Transition hinzufügen
   */
  @Test
  public void testCreateTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    assertTrue(emptyJung.getJungGraph().containsVertex(transition1));
    assertTrue(Double.compare(pointPositive1.getX(),
      emptyJung.getJungLayout().getX(transition1)) == 0);
    assertTrue(Double.compare(pointPositive1.getY(),
      emptyJung.getJungLayout().getY(transition1)) == 0);
  }

  @Test
  public void testDelete_emptyCollections() {

    JungData jung = buildJung(p);

    Set<INode> nodesBefore =
      new HashSet<INode>(jung.getJungGraph().getVertices());
    Set<IArc> arcsBefore = new HashSet<IArc>(jung.getJungGraph().getEdges());

    jung.delete(new HashSet<IArc>(), new HashSet<INode>());

    assertTrue(nodesBefore.equals(new HashSet<INode>(
      jung.getJungGraph().getVertices())));
    assertTrue(arcsBefore.equals(new HashSet<IArc>(
      jung.getJungGraph().getEdges())));
  }

  @Test
  public void testDelete() {

    // erstellen eines Petrinetzes
    // - 2 Gruppen von Nodes und Arcs
    // - es wird ein Jung Graph erstellt in dem beide entahlten sind
    // - Gruppe 2 wird aus Jung gelöscht
    // - prüfen ob Gruppe 1 noch vollständig enthalten ist, und ob sonst
    // nichts weiter enthalten ist
    Set<Place> placesToContain = new HashSet<Place>();
    Set<Place> placesToDelete = new HashSet<Place>();

    Set<Transition> transitionsToContain = new HashSet<Transition>();
    Set<Transition> transitionsToDelete = new HashSet<Transition>();

    Set<IArc> arcsToContain = new HashSet<IArc>();
    Set<IArc> arcsToDelete = new HashSet<IArc>();

    // Petrinet p = PetrinetComponent.getPetrinet().createPetrinet();
    p = PetrinetComponent.getPetrinet().createPetrinet();

    Place placeA = p.addPlace("A");
    Place placeB = p.addPlace("B");
    Place placeY = p.addPlace("Y");
    Place placeZ = p.addPlace("Z");

    // Transition transition1 = p.addTransition("t1", Renews.COUNT);
    // Transition transition2 = p.addTransition("t2", Renews.COUNT);
    transition1 = p.addTransition("t1", Renews.COUNT);
    transition2 = p.addTransition("t2", Renews.COUNT);
    Transition transition9 = p.addTransition("t9", Renews.COUNT);
    Transition transition10 = p.addTransition("t10", Renews.COUNT);

    placesToContain.add(placeA);
    placesToContain.add(placeB);
    placesToDelete.add(placeY);
    placesToDelete.add(placeZ);

    transitionsToContain.add(transition1);
    transitionsToContain.add(transition2);
    transitionsToDelete.add(transition9);
    transitionsToDelete.add(transition10);

    // Kanten von allen Places zu allen Transitions

    IArc arcA1 = p.addPreArc("xA1", placeA, transition1);
    IArc arcA2 = p.addPreArc("xA2", placeA, transition2);
    IArc arcA9 = p.addPreArc("xA9", placeA, transition9);
    IArc arcA10 = p.addPreArc("xA10", placeA, transition10);

    IArc arcB1 = p.addPreArc("xB1", placeB, transition1);
    IArc arcB2 = p.addPreArc("xB2", placeB, transition2);
    IArc arcB9 = p.addPreArc("xB9", placeB, transition9);
    IArc arcB10 = p.addPreArc("xB10", placeB, transition10);

    IArc arcY1 = p.addPreArc("xY1", placeY, transition1);
    IArc arcY2 = p.addPreArc("xY2", placeY, transition2);
    IArc arcY9 = p.addPreArc("xY9", placeY, transition9);
    IArc arcY10 = p.addPreArc("xY10", placeY, transition10);

    IArc arcZ1 = p.addPreArc("xZ1", placeZ, transition1);
    IArc arcZ2 = p.addPreArc("xZ2", placeZ, transition2);
    IArc arcZ9 = p.addPreArc("xZ9", placeZ, transition9);
    IArc arcZ10 = p.addPreArc("xZ10", placeZ, transition10);

    arcsToContain.add(arcA1);
    arcsToContain.add(arcA2);
    arcsToDelete.add(arcA9);
    arcsToDelete.add(arcA10);

    arcsToContain.add(arcB1);
    arcsToContain.add(arcB2);
    arcsToDelete.add(arcB9);
    arcsToDelete.add(arcB10);

    arcsToDelete.add(arcY1);
    arcsToDelete.add(arcY2);
    arcsToDelete.add(arcY9);
    arcsToDelete.add(arcY10);

    arcsToDelete.add(arcZ1);
    arcsToDelete.add(arcZ2);
    arcsToDelete.add(arcZ9);
    arcsToDelete.add(arcZ10);

    // Kanten von allen Trasitions zu allen Places

    IArc arc1A = p.addPostArc("x1A", transition1, placeA);
    IArc arc1B = p.addPostArc("x1B", transition1, placeB);
    IArc arc1Y = p.addPostArc("x1Y", transition1, placeY);
    IArc arc1Z = p.addPostArc("x1Z", transition1, placeZ);

    IArc arc2A = p.addPostArc("x2A", transition2, placeA);
    IArc arc2B = p.addPostArc("x2B", transition2, placeB);
    IArc arc2Y = p.addPostArc("x2Y", transition2, placeY);
    IArc arc2Z = p.addPostArc("x2Z", transition2, placeZ);

    IArc arc9A = p.addPostArc("x9A", transition9, placeA);
    IArc arc9B = p.addPostArc("x9B", transition9, placeB);
    IArc arc9Y = p.addPostArc("x9Y", transition9, placeY);
    IArc arc9Z = p.addPostArc("x9Z", transition9, placeZ);

    IArc arc10A = p.addPostArc("x10A", transition10, placeA);
    IArc arc10B = p.addPostArc("x10B", transition10, placeB);
    IArc arc10Y = p.addPostArc("x10Y", transition10, placeY);
    IArc arc10Z = p.addPostArc("x10Z", transition10, placeZ);

    // Kanten in zu löschende und zu behaltende aufteilen

    arcsToContain.add(arc1A);
    arcsToContain.add(arc1B);
    arcsToDelete.add(arc1Y);
    arcsToDelete.add(arc1Z);

    arcsToContain.add(arc2A);
    arcsToContain.add(arc2B);
    arcsToDelete.add(arc2Y);
    arcsToDelete.add(arc2Z);

    arcsToDelete.add(arc9A);
    arcsToDelete.add(arc9B);
    arcsToDelete.add(arc9Y);
    arcsToDelete.add(arc9Z);

    arcsToDelete.add(arc10A);
    arcsToDelete.add(arc10B);
    arcsToDelete.add(arc10Y);
    arcsToDelete.add(arc10Z);

    JungData jung = buildJung(p);

    Set<INode> nodesToDelete = new HashSet<INode>(placesToDelete);
    nodesToDelete.addAll(transitionsToDelete);

    jung.delete(arcsToDelete, nodesToDelete);

    Set<IArc> remainingArcs =
      new HashSet<IArc>(jung.getJungGraph().getEdges());
    Set<INode> remainingNodes =
      new HashSet<INode>(jung.getJungGraph().getVertices());
    Set<INode> nodesToContain = new HashSet<INode>(placesToContain);
    nodesToContain.addAll(transitionsToContain);

    assertTrue(remainingArcs.equals(arcsToContain));
    assertTrue(remainingNodes.equals(nodesToContain));
  }

  private JungData buildJung(Petrinet p) {

    DirectedSparseGraph<INode, IArc> graphTmp =
      new DirectedSparseGraph<INode, IArc>();
    JungData jung =
      new JungData(graphTmp, new StaticLayout<INode, IArc>(graphTmp));

    int yLocal = 1;

    for (Place place : p.getPlaces()) {
      yLocal += farEnoughAway;
      jung.createPlace(place, new Point(1, yLocal));
    }

    for (Transition transition : p.getTransitions()) {
      yLocal += farEnoughAway;
      jung.createTransition(transition, new Point(1, yLocal));
    }

    for (IArc arc : p.getArcs()) {

      if (arc.getSource() instanceof Place) {
        jung.createArc(arc, (Place) arc.getSource(),
          (Transition) arc.getTarget());
      } else {
        jung.createArc(arc, (Transition) arc.getSource(),
          (Place) arc.getTarget());
      }
    }

    return jung;
  }

  @Test
  public void testMoveNode() {

    emptyJung.createTransition(transition1, pointPositive1);

    emptyJung.moveNodeWithPositionCheck(transition1, pointPositive2);

    assertTrue(Double.compare(pointPositive2.getX(),
      emptyJung.getJungLayout().getX(transition1)) == 0);
    assertTrue(Double.compare(pointPositive2.getY(),
      emptyJung.getJungLayout().getY(transition1)) == 0);
  }

  /**
   * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
   */
  @Test
  public void testMoveNode_selfOverlay() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointPositive1);
  }

  /**
   * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
   */
  @Test
  public void testMoveNode_selfCloseLeft() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointPositive1TooCloseLeft);
  }

  /**
   * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
   */
  @Test
  public void testMoveNode_selfCloseRight() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointPositive1TooCloseRight);
  }

  /**
   * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
   */
  @Test
  public void testMoveNode_selfCloseTop() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointPositive1TooCloseTop);
  }

  /**
   * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
   */
  @Test
  public void testMoveNode_selfCloseBottom() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointPositive1TooCloseBottom);
  }

  /**
   * Node gerade weit genug weg von einem anderen Node
   */
  @Test
  public void testMoveNode_JustEnoughLeft() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1JustEnoughLeft);
  }

  /**
   * Node gerade weit genug weg von einem anderen Node
   */
  @Test
  public void testMoveNode_JustEnoughRight() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1JustEnoughRight);
  }

  /**
   * Node gerade weit genug weg von einem anderen Node
   */
  @Test
  public void testMoveNode_JustEnoughTop() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1JustEnoughTop);
  }

  /**
   * Node gerade weit genug weg von einem anderen Node
   */
  @Test
  public void testMoveNode_JustEnoughBottom() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2,
      pointPositive1JustEnoughBottom);
  }

  /**
   * Farbe nicht gesetzt -> Standardfarbe
   */
  @Test
  public void testPlaceColor_defaultColor() {

    emptyJung.createPlace(place1, pointPositive1);

    assertEquals(emptyJung.getPlaceColor(place1),
      JungData.DEFAULT_COLOR_PLACE);
  }

  /**
   * Farbe persistent
   */
  @Test
  public void testPlaceColor_setColor() {

    emptyJung.createPlace(place1, pointPositive1);

    Color color = Color.getHSBColor(COLOR_H, COLOR_S, COLOR_B);

    emptyJung.setPlaceColor(place1, color);

    // Farbe darf nicht Standardfarbe sein, sonst ist der Test sinnlos
    assertFalse(color.equals(JungData.DEFAULT_COLOR_PLACE));

    assertEquals(emptyJung.getPlaceColor(place1), color);
  }

  /**
   * Farbe muss beim löschen eines Nodes mit gelöscht werden
   */
  @Test
  public void testPlaceColor_createDeleteCreate() {

    emptyJung.createPlace(place1, pointPositive1);

    Color color = Color.getHSBColor(COLOR_H, COLOR_S, COLOR_B);

    emptyJung.setPlaceColor(place1, color);

    // Farbe darf nicht Standardfarbe sein, sonst ist der Test sinnlos
    assertFalse(color.equals(JungData.DEFAULT_COLOR_PLACE));

    assertEquals(emptyJung.getPlaceColor(place1), color);

    Set<INode> nodes = new HashSet<INode>();
    Set<IArc> arcs = new HashSet<IArc>();

    nodes.add(place1);

    emptyJung.delete(arcs, nodes);

    emptyJung.createPlace(place1, pointPositive1);

    assertEquals(emptyJung.getPlaceColor(place1),
      JungData.DEFAULT_COLOR_PLACE);
  }

  /**
   * Koordinate ist zu nah an einer Transition (Überlagerung)
   */
  @Test
  public void test_isCreatePossibleAt_toClose_OverlayTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    assertFalse(emptyJung.isCreatePossibleAt(pointPositive1));
  }

  /**
   * Koordinate ist zu nah an einer Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test
  public void test_isCreatePossibleAt_toClose_TooCloseLeftTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseLeft));
  }

  @Test
  public void test_isCreatePossibleAt_toClose_TooCloseRightTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseRight));
  }

  @Test
  public void test_isCreatePossibleAt_toClose_TooCloseTopTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseTop));
  }

  @Test
  public void test_isCreatePossibleAt_toClose_TooCloseBottomTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseBottom));
  }

  /**
   * Koordinate gerade weit genug weg von einem anderen Node
   */
  @Test
  public void test_isCreatePossibleAt_JustEnoughLeft() {

    emptyJung.createPlace(place1, pointPositive1);
    assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughLeft));
  }

  /**
   * Koordinate gerade weit genug weg von einem anderen Node
   */
  @Test
  public void test_isCreatePossibleAt_JustEnoughRight() {

    emptyJung.createPlace(place1, pointPositive1);
    assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughRight));
  }

  /**
   * Koordinate gerade weit genug weg von einem anderen Node
   */
  @Test
  public void test_isCreatePossibleAt_JustEnoughTop() {

    emptyJung.createPlace(place1, pointPositive1);
    assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughTop));
  }

  /**
   * Koordinate gerade weit genug weg von einem anderen Node
   */
  @Test
  public void test_isCreatePossibleAt_JustEnoughBottom() {

    emptyJung.createPlace(place1, pointPositive1);
    assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughBottom));
  }

  // //////////////////////////////////////////////////
  // Testen der Methoden mit Parameter null
  // //////////////////////////////////////////////////
  /**
   * Null-Test von createArc (Place nach Transititon)
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNull_createArc_PlaceToTranstion_1() {

    emptyJung.createArc(null, place1, transition1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createArc_PlaceToTranstion_2() {

    emptyJung.createArc(arc11, null, transition1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createArc_PlaceToTranstion_3() {

    emptyJung.createArc(arc11, place1, null);
  }

  /**
   * Null-Test von createArc (Transititon nach Place)
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNull_createArc_TranstionToPlace_1() {

    emptyJung.createArc(null, transition1, place1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createArc_TranstionToPlace_2() {

    emptyJung.createArc(arc11, null, place1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createArc_TranstionToPlace_3() {

    emptyJung.createArc(arc11, transition1, null);
  }

  /**
   * restliche Null-Tests
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNull_createPlace_1() {

    emptyJung.createPlace(null, pointPositive1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createPlace_2() {

    emptyJung.createPlace(place1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createTransition_1() {

    emptyJung.createTransition(null, pointPositive1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_createTransition_2() {

    emptyJung.createTransition(transition1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_delete_1() {

    emptyJung.delete(null, new HashSet<INode>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_delete_2() {

    emptyJung.delete(new HashSet<IArc>(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_delete_4() {

    Set<IArc> arcs = new HashSet<IArc>();
    arcs.add(null);
    emptyJung.delete(arcs, new HashSet<INode>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_delete_5() {

    Set<INode> nodes = new HashSet<INode>();
    nodes.add(null);
    emptyJung.delete(new HashSet<IArc>(), nodes);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_moveNode_1() {

    emptyJung.moveNodeWithPositionCheck(null, pointPositive1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_moveNode_2() {

    emptyJung.moveNodeWithPositionCheck(place1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_getPlaceColor() {

    emptyJung.getPlaceColor(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_setPlaceColor_1() {

    emptyJung.setPlaceColor(null, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_setPlaceColor_2() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.setPlaceColor(place1, null);
  }

  @Test
  public void testNull_isCreatePossibleAt() {

    assertFalse(emptyJung.isCreatePossibleAt(null));
  }

  // ////////////////////////////////////////////////////////
  // Testen der Methoden mit fachlich ungültigen Parametern
  // ////////////////////////////////////////////////////////

  /**
   * Test von createArc (Place nach Transititon) Place nicht inzident zu Arc
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_PlaceToTranstion_invalidPlace() {

    emptyJung.createArc(arc11, place2, transition1);
  }

  /**
   * Transition nicht inzident zu Arc
   */
  @Test(expected = IllegalArgumentException.class)
  public void
    testInvalidArguments_createArc_PlaceToTranstion_invalidTransition() {

    emptyJung.createArc(arc11, place1, transition2);
  }

  /**
   * Place und Transition nicht inzident zu Arc
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_PlaceToTranstion_bothInvalid() {

    emptyJung.createArc(arc22, place1, transition1);
  }

  /**
   * Place ist nicht im jung Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_PlaceToTranstion_unknownPlace() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createArc(arc11, place1, transition1);
  }

  /**
   * Transition ist nicht im jung Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void
    testInvalidArguments_createArc_PlaceToTranstion_unknownTransition() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createArc(arc11, place1, transition1);
  }

  /**
   * beides ist nicht im jung Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_PlaceToTranstion_bothUnknown() {

    emptyJung.createArc(arc11, place1, transition1);
  }

  /**
   * Test von createArc (Transititon nach Place) Place nicht inzident zu Arc
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_TranstionToPlace_invalidPlace() {

    emptyJung.createArc(arc11, transition1, place2);
  }

  /**
   * Transition nicht inzident zu Arc
   */
  @Test(expected = IllegalArgumentException.class)
  public void
    testInvalidArguments_createArc_TranstionToPlace_invalidTransition() {

    emptyJung.createArc(arc11, transition2, place1);
  }

  /**
   * Place und Transition nicht inzident zu Arc
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_TranstionToPlace_bothInvalid() {

    emptyJung.createArc(arc22, transition1, place1);
  }

  /**
   * Place ist nicht im jung Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_TranstionToPlace_unknownPlace() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createArc(arc11, transition1, place1);
  }

  /**
   * Transition ist nicht im jung Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void
    testInvalidArguments_createArc_TranstionToPlace_unknownTransition() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createArc(arc11, transition1, place1);
  }

  /**
   * beides ist nicht im jung Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_createArc_TranstionToPlace_bothUnknown() {

    emptyJung.createArc(arc11, transition1, place1);
  }

  /**
   * Place schon im Graphen enthalten
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_containsPlace() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place1, pointPositive1);
  }

  /**
   * Place ist zu nah an einem anderen Place (Überlagerung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_OverlayPlace() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1);
  }

  /**
   * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_TooCloseLeftPlace() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseLeft);
  }

  /**
   * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_TooCloseRightPlace() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseRight);
  }

  /**
   * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testInvalidArguments_createPlace_toClose_TooCloseTopPlace() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseTop);
  }

  /**
   * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_TooCloseBottomPlace() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseBottom);
  }

  /**
   * Place ist zu nah an einem anderen Transition (Überlagerung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_OverlayTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1);
  }

  /**
   * Place ist zu nah an einem anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_TooCloseLeftTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseLeft);
  }

  /**
   * Place ist zu nah an einem anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_TooCloseRightTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseRight);
  }

  /**
   * Place ist zu nah an einem anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createPlace_toClose_TooCloseTopTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseTop);
  }

  /**
   * Place ist zu nah an einem anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void
    testShowAsWarning_createPlace_toClose_TooCloseBottomTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive1TooCloseBottom);
  }

  /**
   * Transition schon im Graphen enthalten
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createTransition_containsTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive1);
  }

  /**
   * Transition ist zu nah an einer anderen Transition (Überlagerung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createTransition_toClose_OverlayTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createTransition(transition2, pointPositive1);
  }

  /**
   * Transition ist zu nah an einer anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void
    testShowAsWarning_createTransition_toClose_TooCloseLeftTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createTransition(transition2, pointPositive1TooCloseLeft);
  }

  /**
   * Transition ist zu nah an einer anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void
    testShowAsWarning_createTransition_toClose_TooCloseRightTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createTransition(transition2, pointPositive1TooCloseRight);
  }

  /**
   * Transition ist zu nah an einer anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void
    testShowAsWarning_createTransition_toClose_TooCloseTopTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createTransition(transition2, pointPositive1TooCloseTop);
  }

  /**
   * Transition ist zu nah an einer anderen Transition (Innerhalb der
   * Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void
    testShowAsWarning_createTransition_toClose_TooCloseBottomTransition() {

    emptyJung.createTransition(transition1, pointPositive1);
    emptyJung.createTransition(transition2, pointPositive1TooCloseBottom);
  }

  /**
   * Transition ist zu nah an einem Place (Überlagerung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createTransition_toClose_OverlayPlace() {

    emptyJung.createPlace(place2, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive1);
  }

  /**
   * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createTransition_toClose_TooCloseLeftPlace() {

    emptyJung.createPlace(place2, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive1TooCloseLeft);
  }

  /**
   * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createTransition_toClose_TooCloseRightPlace() {

    emptyJung.createPlace(place2, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive1TooCloseRight);
  }

  /**
   * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_createTransition_toClose_TooCloseTopPlace() {

    emptyJung.createPlace(place2, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive1TooCloseTop);
  }

  /**
   * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void
    testShowAsWarning_createTransition_toClose_TooCloseBottomPlace() {

    emptyJung.createPlace(place2, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive1TooCloseBottom);
  }

  /**
   * Place nicht im Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_getPlaceColor_unknownPlace() {

    emptyJung.getPlaceColor(place1);
  }

  /**
   * Place nicht im Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_setPlaceColor_1() {

    emptyJung.setPlaceColor(place1, Color.BLACK);
  }

  /**
   * Arc nicht im Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_delete_unknownArc() {

    Set<IArc> arcs = new HashSet<IArc>();
    Set<INode> nodes = new HashSet<INode>();

    arcs.add(arc11);

    emptyJung.delete(arcs, nodes);
  }

  /**
   * INode nicht im Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_delete_unknownINode() {

    Set<IArc> arcs = new HashSet<IArc>();
    Set<INode> nodes = new HashSet<INode>();

    nodes.add(place1);

    emptyJung.delete(arcs, nodes);
  }

  /**
   * Beim Node löschen werden Kanten vergessen
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_delete_missingArcsToDelete() {

    Set<IArc> arcs = new HashSet<IArc>();
    Set<INode> nodes = new HashSet<INode>();

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createTransition(transition1, pointPositive2);
    emptyJung.createArc(arc11, place1, transition1);

    nodes.add(place1);

    emptyJung.delete(arcs, nodes);
  }

  /**
   * Position durch negativen X-Wert ungültig
   */
  // There is no IllegalArgumentException expected here any more as nodes can
  // be moved into negativ positions
  // @Test(expected=IllegalArgumentException.class)
  public void testInvalidArguments_moveNode_NegativeX() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointNegativeX);
  }

  /**
   * Position durch negativen Y-Wert ungültig
   */
  // There is no IllegalArgumentException expected here any more as nodes can
  // be moved into negativ positions
  // @Test(expected=IllegalArgumentException.class)
  public void testInvalidArguments_moveNode_NegativeY() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointNegativeY);
  }

  /**
   * Position durch negative X und Y-Werte ungültig
   */
  // There is no IllegalArgumentException expected here any more as nodes can
  // be moved into negativ positions
  // @Test(expected=IllegalArgumentException.class)
  public void testInvalidArguments_moveNode_NegativeXY() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.moveNodeWithPositionCheck(place1, pointNegativeXY);
  }

  /**
   * Node nicht im Graphen enthalten
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArguments_moveNode_UnknownNode() {

    emptyJung.moveNodeWithPositionCheck(place1, pointPositive1);
  }

  /**
   * Node kommt zu nah an einen anderen Node (Überlagerung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_moveNode_Overlay() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1);
  }

  /**
   * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_moveNode_TooCloseLeft() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1TooCloseLeft);
  }

  /**
   * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_moveNode_TooCloseRight() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1TooCloseRight);
  }

  /**
   * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_moveNode_TooCloseTop() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1TooCloseTop);
  }

  /**
   * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
   */
  @Test(expected = exceptions.ShowAsWarningException.class)
  public void testShowAsWarning_moveNode_TooCloseBottom() {

    emptyJung.createPlace(place1, pointPositive1);
    emptyJung.createPlace(place2, pointPositive2);
    emptyJung.moveNodeWithPositionCheck(place2, pointPositive1TooCloseBottom);
  }
}
