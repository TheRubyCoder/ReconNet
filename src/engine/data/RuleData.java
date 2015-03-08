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

package engine.data;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import petrinet.model.INode;
import transformation.Rule;
import transformation.TransformationComponent;
import engine.Positioning;
import engine.attribute.ColorGenerator;
import exceptions.ShowAsInfoException;

/**
 * This Class is a data container for a Rule and all Petrinet in this Rule.
 *
 * @author alex (aas772)
 */

public final class RuleData
  extends SessionDataAbstract {

  private Rule rule;
  private JungData jungDataL;
  private JungData jungDataK;
  private JungData jungDataR;
  private JungData jungDataNac;
  /** Used for adding places so all places in the rule have the same color. */
  private ColorGenerator colorGenerator;

  private HashMap<UUID, JungData> jungDataNACs;

  @SuppressWarnings("unused")
  // no default constructor
  private RuleData() {

  }

  public RuleData(int id, Rule rule, JungData lJungData, JungData kJungData,
    JungData rJungData) {

    check(id > 0, "id have to be greater than 0");
    check(rule instanceof Rule, "petrinet not of type Petrinet");
    check(lJungData instanceof JungData, "lJungData not of type JungData");
    check(kJungData instanceof JungData, "kJungData not of type JungData");
    check(rJungData instanceof JungData, "rJungData not of type JungData");

    checkContaining(rule.getK(), kJungData);
    checkContaining(rule.getL(), lJungData);
    checkContaining(rule.getR(), rJungData);
    // TODO: hier für NACs
    // checkContaining(rule.getNACs()., nacJungData);

    // TODO: NAC
    boolean isEqual = kJungData == lJungData || lJungData == rJungData;
    isEqual = isEqual || kJungData == rJungData;

    check(!isEqual, "jungData same instance");

    this.id = id;
    this.rule = rule;
    this.jungDataL = lJungData;
    this.jungDataK = kJungData;
    this.jungDataR = rJungData;
    this.jungDataNACs = new HashMap<UUID, JungData>();

    this.colorGenerator = new ColorGenerator();
  }

  /**
   * Gets the JungData of L from a Rule
   *
   * @return JungData
   */
  public JungData getLJungData() {

    return jungDataL;
  }

  /**
   * Gets the JungData of K from a Rule
   *
   * @return JungData
   */
  public JungData getKJungData() {

    return jungDataK;
  }

  /**
   * Gets the JungData of R from a Rule
   *
   * @return JungData
   */
  public JungData getRJungData() {

    return jungDataR;
  }

  /**
   * Gets the JungData of NAC from a Rule
   *
   * @return JungData
   */
  @Deprecated
  public JungData getNacJungData() {

    return jungDataNac;
  }

  /**
   * Gets the JungData HashMap Value for a given nacId
   *
   * @param nacId
   * @return JungData
   */
  public JungData getNacJungData(UUID nacId) {

    return jungDataNACs.get(nacId);
  }

  /**
   * Sets the JungData HashMap Value for a given nacId
   *
   * @param nacId
   * @param jungData
   */
  public void putNacJungData(UUID nacId, JungData jungData) {

    this.jungDataNACs.put(nacId, jungData);
  }

  public Set<JungData> getNacJungDataSet() {

    return new HashSet<JungData>(jungDataNACs.values());
  }

  /**
   * Gets a Rule.
   *
   * @return Rule
   */
  public Rule getRule() {

    return rule;
  }

  /** Returns the color generator for this rule */
  public ColorGenerator getColorGenerator() {

    return colorGenerator;
  }

  /**
   * Removes data of elements that are no longer in the rule. This may be used
   * if the rule is altered from outside the engine.
   *
   * @param petrinet
   */
  public void deleteDataOfMissingElements(Rule rule) {

    getLJungData().deleteDataOfMissingElements(rule.getL());
    getKJungData().deleteDataOfMissingElements(rule.getK());
    getRJungData().deleteDataOfMissingElements(rule.getR());

    for (Entry<UUID, JungData> entry : jungDataNACs.entrySet()) {

      entry.getValue().deleteDataOfMissingElements(
        rule.getNAC(entry.getKey()).getNac());
    }
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RuleData other = (RuleData) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

  /**
   * Moves the <tt>node</tt> to the <tt>coordinate</tt> relative to its
   * current position. Also moves all mappings in L,K,R
   *
   * @param node
   * @param coordinate
   * @throws ShowAsInfoException
   *         if nodes are in the way
   */
  public void moveNodeRelative(INode node, Point2D coordinate) {

    // if (!getKJungData().isCreatePossibleAt(coordinate)) {
    // throw new ShowAsInfoException("Ein Knoten ist im Weg");
    // }

    List<INode> nodeMappings =
      TransformationComponent.getTransformation().getMappings(getRule(), node);

    INode nodeInL = nodeMappings.get(0);
    INode nodeInK = nodeMappings.get(1);
    INode nodeInR = nodeMappings.get(2);

    Map<UUID, INode> nodesInNacs =
      TransformationComponent.getTransformation().getCorrespondingNodesOfAllNacs(
        getRule(), node);

    if (nodeInL != null) {
      getLJungData().moveNodeWithPositionCheck(
        nodeInL,
        Positioning.addPoints(getLJungData().getNodeLayoutAttributes().get(
          nodeInL).getCoordinate(), coordinate));
    }

    if (nodeInK != null) {
      getKJungData().moveNodeWithPositionCheck(
        nodeInK,
        Positioning.addPoints(getKJungData().getNodeLayoutAttributes().get(
          nodeInK).getCoordinate(), coordinate));
    }

    if (nodeInR != null) {
      getRJungData().moveNodeWithPositionCheck(
        nodeInR,
        Positioning.addPoints(getRJungData().getNodeLayoutAttributes().get(
          nodeInR).getCoordinate(), coordinate));
    }

    for (Entry<UUID, INode> nodeInNac : nodesInNacs.entrySet()) {
      getNacJungData(nodeInNac.getKey()).moveNodeWithPositionCheck(
        nodeInNac.getValue(),
        Positioning.addPoints(
          getNacJungData(nodeInNac.getKey()).getNodeLayoutAttributes().get(
            nodeInNac.getValue()).getCoordinate(), coordinate));
    }

  }

  public void deleteNacJungData(UUID nacId) {

    this.jungDataNACs.remove(nacId);
  }
}
