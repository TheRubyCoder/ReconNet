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
import java.awt.geom.Point2D;

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
}
