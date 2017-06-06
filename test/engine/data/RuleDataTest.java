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

package engine.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import exceptions.ShowAsWarningException;

/**
 * @author Mathias Blumreiter
 */
public class RuleDataTest {

  private JungData emptyJungk;
  private JungData emptyJungl;
  private JungData emptyJungr;
  private JungData emptyJungNac;
  private JungData jungk;
  private JungData jungl;
  private JungData jungr;
  private JungData jungNac;

  private Rule emptyRule;
  private Rule rule;

  /**
   * Erstellen eines Netzes
   */
  @Before
  public void setUp()
    throws Exception {

    emptyRule = TransformationComponent.getTransformation().createRule();
    rule = TransformationComponent.getTransformation().createRule();

    DirectedGraph<INode, IArc> graph = new DirectedSparseGraph<INode, IArc>();
    emptyJungk = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    emptyJungl = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    emptyJungr = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    emptyJungNac = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    jungk = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    jungl = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    jungr = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    jungNac = new JungData(graph, new StaticLayout<INode, IArc>(graph));

    Place place1 = rule.addPlaceToL("test1");
    Transition transition1 = rule.addTransitionToL("test2");
    rule.addPlaceToK("test3");
    rule.addTransitionToR("test4");

    rule.addPreArcToL("test5", place1, transition1);

    buildJung(rule.getL(), jungl);
    buildJung(rule.getK(), jungk);
    buildJung(rule.getR(), jungr);
    // TODO: NAC Test
    // buildJung(rule.getNacs(), jungNac);
  }

  @Test
  public void testData() {

    assertFalse(rule.getK().getPlaces().isEmpty());

    RuleData data1 = buildAndTest(1, rule, jungl, jungk, jungr);
    RuleData data2 =
      buildAndTest(1, emptyRule, emptyJungl, emptyJungk, emptyJungr);

    RuleData data3 = buildAndTest(2, rule, jungl, jungk, jungr);
    RuleData data4 =
      buildAndTest(2, emptyRule, emptyJungl, emptyJungk, emptyJungr);

    assertEquals(data1, data2);
    assertEquals(data3, data4);

    assertFalse(data1.equals(data3));
    assertFalse(data2.equals(data4));
  }

  private RuleData buildAndTest(int id, Rule rule, JungData lJungData,
    JungData kJungData, JungData rJungData) {

    RuleData data = new RuleData(id, rule, lJungData, kJungData, rJungData);

    assertEquals(id, data.getId());
    assertEquals(lJungData, data.getLJungData());
    assertEquals(kJungData, data.getKJungData());
    assertEquals(rJungData, data.getRJungData());

    assertNotNull(data.getLJungData());
    assertNotNull(data.getKJungData());
    assertNotNull(data.getRJungData());

    return data;
  }

  private void buildJung(Petrinet p, JungData jung) {

    int y = 1;

    for (Place place : p.getPlaces()) {
      y = y + 100;
      jung.createPlace(place, new Point(1, y));
    }

    for (Transition transition : p.getTransitions()) {
      y = y + 100;
      jung.createTransition(transition, new Point(1, y));
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
  }

  /**
   * Test auf Null
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNull_constructor_1() {

    new RuleData(1, null, emptyJungk, emptyJungl, emptyJungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_constructor_2() {

    new RuleData(1, emptyRule, null, emptyJungl, emptyJungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_constructor_3() {

    new RuleData(1, emptyRule, emptyJungk, null, emptyJungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_constructor_4() {

    new RuleData(1, emptyRule, emptyJungk, emptyJungl, null);
  }

  /**
   * Test mit Illegalen Parametern
   */

  /**
   * id zu klein
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_id_1() {

    new RuleData(0, emptyRule, emptyJungk, emptyJungl, emptyJungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_id_2() {

    new RuleData(-1, emptyRule, emptyJungk, emptyJungl, emptyJungr);
  }

  /**
   * Teilnetze dürfen nicht die selben JungData haben
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungDataSame_1() {

    new RuleData(1, emptyRule, emptyJungk, emptyJungk, emptyJungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungDataSame_2() {

    new RuleData(1, emptyRule, emptyJungk, emptyJungl, emptyJungk);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungDataSame_3() {

    new RuleData(1, emptyRule, emptyJungk, emptyJungl, emptyJungl);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungDataSame_4() {

    new RuleData(1, emptyRule, emptyJungk, emptyJungk, emptyJungk);
  }

  /**
   * JungData passt nicht zur Rule
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungData_1() {

    new RuleData(1, rule, emptyJungk, jungk, jungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungData_2() {

    new RuleData(1, rule, jungl, emptyJungl, jungr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgument_constructor_jungData_3() {

    new RuleData(1, rule, jungl, jungk, emptyJungl);
  }

  // TODO: hier für NAC testen
  /*
   * @Test(expected = IllegalArgumentException.class) public void
   * testIllegalArgument_constructor_jungData_4() { new RuleData(1, rule,
   * jungl, jungk, jungr, emptyJungNac); }
   */

  /**
   * Ueberlagerung zweier Nodes nicht moeglich
   */
  @Test(expected = ShowAsWarningException.class)
  public void testIllegalArgument_moveNodeRelative() {

    Rule rule1 = TransformationComponent.getTransformation().createRule();

    DirectedGraph<INode, IArc> graph = new DirectedSparseGraph<INode, IArc>();
    JungData jungk1 =
      new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    JungData jungl1 =
      new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();
    JungData jungr1 =
      new JungData(graph, new StaticLayout<INode, IArc>(graph));

    graph = new DirectedSparseGraph<INode, IArc>();

    Place place1 = rule1.addPlaceToL("test1");
    Place placeNew = rule1.addPlaceToL("test2");

    buildJung(rule1.getL(), jungl1);
    buildJung(rule1.getK(), jungk1);
    buildJung(rule1.getR(), jungr1);

    double xCoordinate1 = jungl1.getJungLayout().getX(place1);
    double yCoordinate1 = jungl1.getJungLayout().getY(place1);

    double xCoordinateNew = jungl1.getJungLayout().getX(placeNew);
    double yCoordinateNew = jungl1.getJungLayout().getY(placeNew);

    Double xToMove = xCoordinate1 - xCoordinateNew;
    Double yToMove = yCoordinate1 - yCoordinateNew;

    Point2D movePlaceNewToCoordinate =
      new Point(xToMove.intValue(), yToMove.intValue());
    // CHECKSTYLE:OFF - No need to ceck for magic numbers
    RuleData newRuleData = buildAndTest(3, rule1, jungl1, jungk1, jungr1);
    // CHECKSTYLE:ON
    newRuleData.moveNodeRelative(placeNew, movePlaceNewToCoordinate);
  }
}
