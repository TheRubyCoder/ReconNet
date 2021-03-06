/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veraenderung, sind
 * unter den folgenden Bedingungen zulaessig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare muessen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare muessen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden duerfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFUEGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FUER
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FUER IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFAELLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHAEDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGUETERN ODER -DIENSTLEISTUNGEN; EINSCHRAENKUNG DER NUTZUNGSFAEHIGKEIT;
 * VERLUST VON NUTZUNGSFAEHIGKEIT; DATEN; PROFIT ODER GESCHAEFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLAESSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
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
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FUER ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package engine.ihandler;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.UUID;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

import com.sun.istack.NotNull;

import engine.handler.RuleNet;
import exceptions.EngineException;

/**
 * This is a Interface for the Persistence-Component. Implementation:
 * engine.handler.rule.RulePersistence
 *
 * @author alex (aas772)
 */

public interface IRulePersistence {

  /**
   * Creates an PreArc
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        Place of the Arc
   * @param transition
   *        Transition of the Arc
   * @return the new PreArc
   */
  PreArc createPreArc(@NotNull int id, RuleNet net, @NotNull Place place,
    @NotNull Transition transition)
      throws EngineException;

  /**
   * Creates a PreArc in a NAC
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param from
   *        Source place
   * @param to
   *        Target transition
   * @return The created PreArc
   * @throws EngineException
   */
  PreArc createPreArc(int id, UUID nacId, Place from, Transition to)
    throws EngineException;

  /**
   * Creates an PostArc
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        Transition of the Arc
   * @param place
   *        Place of the Arc
   * @return the new PostArc
   */
  PostArc createPostArc(@NotNull int id, RuleNet net,
    @NotNull Transition transition, @NotNull Place place)
      throws EngineException;

  /**
   * Creates a PostArc in a NAC
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param from
   *        Source transition
   * @param to
   *        Target place
   * @return The created PostArc
   * @throws EngineException
   */
  PostArc createPostArc(int id, UUID nacId, Transition from, Place to)
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
  Place createPlace(@NotNull int id, @NotNull RuleNet net,
    @NotNull Point2D coordinate)
      throws EngineException;

  /**
   * Creates a Place in a NAC
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param coordinate
   *        Point where the place will be created
   * @return The created place
   * @throws EngineException
   */
  Place createPlace(int id, UUID nacId, Point2D coordinate)
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
  Transition createTransition(@NotNull int id, @NotNull RuleNet net,
    @NotNull Point2D coordinate)
      throws EngineException;

  /**
   * Creats a transition in a NAC
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param coordinate
   *        Point where the transition will be created
   * @return The created transition
   * @throws EngineException
   */
  Transition createTransition(int id, UUID nacId, Point2D coordinate)
    throws EngineException;

  /**
   * Sets the Marking of a Place.
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
  setMarking(@NotNull int id, @NotNull Place place, @NotNull int marking)
    throws EngineException;

  /**
   * Sets the Capacity of a Place
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        where to set the Capacity
   * @param capacity
   *        amount of capacity
   * @throws EngineException
   */
  void setCapacity(@NotNull int id, @NotNull Place place,
    @NotNull int capacity)
      throws EngineException;

  /**
   * Sets the PName of a Place.
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        where to set the PName
   * @param pname
   *        PName
   * @throws EngineException
   */
  void setPname(@NotNull int id, @NotNull Place place, @NotNull String pname)
    throws EngineException;

  /**
   * Sets the Tlb of a Transition.
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        where to set the tlb
   * @param tlb
   *        TransitionLabel
   * @throws EngineException
   */
  void setTlb(@NotNull int id, @NotNull Transition transition,
    @NotNull String tlb)
      throws EngineException;

  /**
   * Sets the TName of a Transition.
   *
   * @param id
   *        ID of the Rule
   * @param transition
   *        where to set the TName
   * @param tname
   *        TName
   * @throws EngineException
   */
  void setTname(@NotNull int id, @NotNull Transition transition,
    @NotNull String tname)
      throws EngineException;

  /**
   * Sets the Weight of a PreArc.
   *
   * @param id
   *        ID of the Rule
   * @param preArc
   *        where to set the weight
   * @param weight
   *        weight of the arc
   * @throws EngineException
   */
  void
  setWeight(@NotNull int id, @NotNull PreArc preArc, @NotNull int weight)
    throws EngineException;

  /**
   * Sets the Weight of a PostArc.
   *
   * @param id
   *        ID of the Rule
   * @param postArc
   *        where to set the weight
   * @param weight
   *        weight of the arc
   * @throws EngineException
   */
  void setWeight(@NotNull int id, @NotNull PostArc postArc,
    @NotNull int weight)
      throws EngineException;

  /**
   * Sets the weight of an arc in a nac
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param arc
   *        arc which weight should be set
   * @param weight
   *        Weight to set
   * @throws EngineException
   */
  void setWeight(int id, UUID nacId, IArc arc, int weight)
    throws EngineException;

  /**
   * Sets a Strings as RNW.
   *
   * @param id
   *        ID of the Rule
   * @param transition
   * @param rnw
   *        String as RNW
   */
  void setRnw(int id, Transition transition, IRenew renews)
    throws EngineException;

  /**
   * Set Color of a Place.
   *
   * @param id
   *        ID of the Rule
   * @param place
   *        which should modify
   * @param color
   *        new Color
   */
  void setPlaceColor(int id, Place place, Color color)
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
   * Creates a new NAC to a given Rule
   *
   * @param ruleId
   * @return ID of the created NAC
   */
  UUID createNac(int ruleId)
    throws EngineException;

  /**
   * Sets the Name of a NAC-explicit Place
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the nac
   * @param place
   *        place to be named
   * @param pname
   *        name
   * @throws EngineException
   */
  void setPname(@NotNull int id, @NotNull UUID nacId, @NotNull INode place,
    @NotNull String pname)
      throws EngineException;

  /**
   * Sets the capacity of a NAC-explicit place
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param place
   *        place which capacity should be set
   * @param capacity
   *        the capacity
   * @throws EngineException
   */
  void setCapacity(@NotNull int id, @NotNull UUID nacId,
    @NotNull INode place, @NotNull int capacity)
    throws EngineException;

  /**
   * Sets the marking of a NAC-explicit place
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param place
   *        place which marking should be set
   * @param marking
   *        the marking
   * @throws EngineException
   */
  void setMarking(@NotNull int id, @NotNull UUID nacId, @NotNull INode place,
    @NotNull int marking)
      throws EngineException;

  /**
   * Sets the color of a NAC-explicit place
   *
   * @param id
   * @param nacId
   * @param place
   * @param color
   * @throws EngineException
   */
  void setPlaceColor(int id, @NotNull UUID nacId, @NotNull INode place,
    @NotNull Color color)
    throws EngineException;

  /**
   * Sets the Transition Label of a NAC-explicit transition
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param transition
   *        the transition which tlb should be set
   * @param tlb
   *        the transition label
   * @throws EngineException
   */
  void setTlb(@NotNull int id, @NotNull UUID nacId,
    @NotNull INode transition, @NotNull String tlb)
    throws EngineException;

  /**
   * Sets the Name of a NAC-explicit transition
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param transition
   *        transition to be named
   * @param tname
   *        name
   * @throws EngineException
   */
  void setTname(@NotNull int id, @NotNull UUID nacId,
    @NotNull INode transition, @NotNull String tname)
      throws EngineException;

  /**
   * Sets the renew for a NAC-explicit transition
   *
   * @param id
   *        ID of the rule
   * @param nacId
   *        ID of the NAC
   * @param transition
   *        transition which renew should be set
   * @param renews
   *        the renew
   * @throws EngineException
   */
  void setRnw(int id, @NotNull UUID nacId, INode transition, IRenew renews)
    throws EngineException;
}
