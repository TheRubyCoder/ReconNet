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

package engine.ihandler;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.UUID;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import exceptions.EngineException;

/**
 * This is a Interface for a RuleManipulation from the GUI-Component.
 * Implementation: engine.handler.rule.RuleManipulation
 *
 * @author alex (aas772)
 */

public interface IRuleManipulation {

  /**
   * Creates an Arc
   *
   * @param id
   *        ID of the Rule
   * @param from
   *        Source of the Arc
   * @param to
   *        Target of the Arc
   * @return the new Arc
   */
  void createArc(@NotNull int id, RuleNet net, @NotNull INode from,
    @NotNull INode to)
    throws EngineException;

  /**
   * Creates a Place
   *
   * @param id
   *        ID of the Rule
   * @param coordinate
   *        Point where the Place will be created
   * @return the new Place
   */
  void createPlace(@NotNull int id, @NotNull RuleNet net,
    @NotNull Point2D coordinate)
    throws EngineException;

  /**
   * Creates a Rule
   *
   * @return ID of the created Rule
   */
  int createRule();

  /**
   * Creates a Transition
   *
   * @param id
   *        ID of the Rule
   * @param coordinate
   *        Point where the Transition will be created
   * @throws EngineException
   * @return the new Transition
   */
  void createTransition(@NotNull int id, @NotNull RuleNet net,
    @NotNull Point2D coordinate)
    throws EngineException;

  /**
   * Deletes an Arc
   *
   * @param id
   *        ID of the Rule
   * @param arc
   *        which will be deleted
   */
  void deleteArc(@NotNull int id, @NotNull RuleNet net, @NotNull IArc arc)
    throws EngineException;

  /**
   * Deletes a Place
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        which will be deleted
   */
  void
    deletePlace(@NotNull int id, @NotNull RuleNet net, @NotNull INode place)
      throws EngineException;

  /**
   * Deletes a Transition
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        which will be deleted
   */
  void deleteTransition(@NotNull int id, @NotNull RuleNet net,
    @NotNull INode transition)
    throws EngineException;

  /**
   * Gets the Attributes from an Arc
   *
   * @param id
   *        ID of the Rule
   * @param arc
   *        which attributes are wanted
   * @return ArcAttribute
   */
  ArcAttribute getArcAttribute(@NotNull int id, @NotNull IArc arc)
    throws EngineException;

  /**
   * Gets the JungLayout from the Rule
   *
   * @param id
   *        ID of the Rule
   * @return AbstractLayout
   */
  AbstractLayout<INode, IArc> getJungLayout(@NotNull int id,
    @NotNull RuleNet net)
    throws EngineException;

  /**
   * Gets the JungLayout from the specified NAC of the given Rule
   *
   * @param ruleId
   *        ID of the rule which contains the NAC
   * @param nacId
   *        UUID of the NAC
   * @return AbstractLayout
   * @throws EngineException
   */
  AbstractLayout<INode, IArc> getJungLayout(@NotNull int ruleId, UUID nacId)
    throws EngineException;

  /**
   * Gets the Attributes from a Place
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        which attributes are wanted
   * @return PlaceAtrribute
   * @throws EngineException
   */
  PlaceAttribute getPlaceAttribute(@NotNull int id, @NotNull INode place)
    throws EngineException;

  /**
   * Gets the Attributes from a Transition
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        which attributes are wanted
   * @return TransitionAttribute
   * @throws EngineException
   */
  TransitionAttribute getTransitionAttribute(@NotNull int id,
    @NotNull INode transition)
    throws EngineException;

  /**
   * Gets the Attributes from a Rule
   *
   * @param id
   *        ID of the Rule
   * @return RuleAttribute or it throws a EngineException
   * @throws EngineException
   */
  RuleAttribute getRuleAttribute(@NotNull int id)
    throws EngineException;

  /**
   * Moves a node.
   *
   * @param id
   *        ID of the Rule
   * @param node
   *        to move
   * @param relativePosition
   *        relative movement of the node
   * @throws EngineException
   */
  void moveNode(@NotNull int id, @NotNull INode node,
    @NotNull Point2D relativePosition)
    throws EngineException;

  /**
   * Saves a Rule.
   *
   * @param id
   *        ID of the Rule
   * @param path
   *        where to save the Rule
   * @param filename
   *        name for the Rule
   * @param format
   *        which the Rule should be saved. (PNML the only option till now)
   */
  void save(@NotNull int id, @NotNull String path, @NotNull String filename,
    @NotNull String format)
    throws EngineException;

  /**
   * Load a Rule.
   *
   * @param path
   *        where can we find the Rule
   * @param filename
   *        name of the Rule
   * @return the Id of the Rule
   */
  int load(@NotNull String path, @NotNull String filename);

  /**
   * Sets the Marking of a Place and its corresponding nodes in the other
   * parts of the rule
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        where to set the Mark
   * @param marking
   *        amount of mark
   * @throws EngineException
   */
  void
    setMarking(@NotNull int id, @NotNull INode place, @NotNull int marking)
      throws EngineException;

  /**
   * Sets the Capacity of a Place and its corresponding nodes in the other
   * parts of the rule
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        where to set the Capacity
   * @param marking
   *        amount of capacity
   * @throws EngineException
   */
  void setCapacity(@NotNull int id, @NotNull INode place,
    @NotNull int capacity)
    throws EngineException;

  /**
   * Sets the PName of a Place and its corresponding nodes in the other parts
   * of the rule
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        where to set the PName
   * @param pname
   *        PName
   * @throws EngineException
   */
  void setPname(@NotNull int id, @NotNull INode place, @NotNull String pname)
    throws EngineException;

  /**
   * Sets the Tlb of a Transition and its corresponding nodes in the other
   * parts of the rule
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        where to set the tlb
   * @param tlb
   *        TransitionLabel
   * @throws EngineException
   */
  void
    setTlb(@NotNull int id, @NotNull INode transition, @NotNull String tlb)
      throws EngineException;

  /**
   * Sets the TName of a Transition and its corresponding nodes in the other
   * parts of the rule
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        where to set the TName
   * @param tname
   *        TName
   * @throws EngineException
   */
  void setTname(@NotNull int id, @NotNull INode transition,
    @NotNull String tname)
    throws EngineException;

  /**
   * Sets the Weight of an Arc.
   *
   * @param id
   *        ID of the Rule
   * @param arc
   *        where to set the weight
   * @param weight
   *        weight of the arc
   * @throws EngineException
   */
  void setWeight(@NotNull int id, @NotNull IArc arc, @NotNull int weight)
    throws EngineException;

  /**
   * Sets a Strings as RNW and its corresponding nodes in the other parts of
   * the rule
   *
   * @param id
   *        ID of the Rule
   * @param rnw
   *        String as RNW
   */
  void setRnw(int id, INode transition, IRenew renews)
    throws EngineException;

  /**
   * Set Color of a Place and its corresponding nodes in the other parts of
   * the rule
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        which should modify
   * @param color
   *        new Color
   */
  void setPlaceColor(int id, INode place, Color color)
    throws EngineException;

  /**
   * This methods clean the Tool from this Rule.
   *
   * @param id
   *        ID of the Rule
   * @throws EngineException
   */
  void closeRule(int id)
    throws EngineException;

  /**
   * Returns the type of the Object.
   *
   * @param node
   *        to check
   * @return Enum composed of Place, Transition
   * @throws EngineException
   */
  NodeTypeEnum getNodeType(@NotNull INode node)
    throws EngineException;

  /**
   * Moves all petrinets of the rule.
   *
   * @param id
   *        ID of rule
   * @param relativePosition
   * @see {@link IPetrinetManipulation#moveGraph(int, Point2D)}
   */
  void moveGraph(int id, Point2D relativePosition)
    throws EngineException;

  /**
   * Moves all petrinets of the rule into vision.
   *
   * @see {@link IPetrinetManipulation#moveGraphIntoVision(int)}
   * @param currentId
   * @throws EngineException
   */
  void moveGraphIntoVision(int currentId)
    throws EngineException;

  /**
   * Similar to {@link PetrinetViewer#moveAllNodesTo(float, Point)} but looks
   * up rule with <code>id</code> first and applies it to all parts of rule
   *
   * @param currentId
   * @param factor
   * @param point
   * @throws EngineException
   */
  void moveAllNodesTo(int id, float factor, Point point)
    throws EngineException;

  /**
   * Sets the nodeSize for the JungData of the petrinets of the rule with
   * <code>id</code>
   *
   * @see {@link JungData#setNodeSize(double)}
   * @param id
   * @param nodeSize
   * @throws EngineException
   */
  void setNodeSize(int id, double nodeSize)
    throws EngineException;

  /**
   * Returns the nodeSize of Petrinet with <code>id</code>
   *
   * @param id
   * @throws NullPointerException
   *         if id is wrong
   * @return
   */
  double getNodeSize(int id)
    throws EngineException;

  /**
   * Creates a new NAC to a given Rule
   *
   * @param ruleId
   * @return ID of the created NAC
   */
  UUID createNac(int ruleId)
    throws EngineException;

}
