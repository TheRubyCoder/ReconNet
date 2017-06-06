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

package engine.handler.rule;

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
import engine.handler.RuleNet;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;

/**
 * This class implements {@link IRulePersistence}. Its the interface for the
 * gui component It can be used for saving and loading rules. Its
 * functionalities are delegated to ruleManipulationBackend
 */
public final class RulePersistence
implements IRulePersistence {

  /** Singleton instance */
  private static RulePersistence rulePersistence;

  /** Object with actual logic to delegate to */
  private RuleHandler ruleManipulationBackend;

  private RulePersistence() {

    this.ruleManipulationBackend = RuleHandler.getInstance();
  }

  /** Returns the singleton instance */
  public static RulePersistence getInstance() {

    if (rulePersistence == null) {
      rulePersistence = new RulePersistence();
    }

    return rulePersistence;
  }

  @Override
  public PreArc createPreArc(int id, RuleNet net, Place place,
    Transition transition)
      throws EngineException {

    return ruleManipulationBackend.createPreArc(id, net, place, transition);
  }

  @Override
  public PostArc createPostArc(int id, RuleNet net, Transition transition,
    Place place)
      throws EngineException {

    PostArc arc =
      ruleManipulationBackend.createPostArc(id, net, transition, place);

    return arc;
  }

  @Override
  public Place createPlace(int id, RuleNet net, Point2D coordinate)
    throws EngineException {

    return ruleManipulationBackend.createPlace(id, net, coordinate);
  }

  @Override
  public int createRule() {

    return ruleManipulationBackend.createRule();
  }

  @Override
  public Transition createTransition(int id, RuleNet net, Point2D coordinate)
    throws EngineException {

    return ruleManipulationBackend.createTransition(id, net, coordinate);
  }

  @Override
  public void setMarking(int id, Place place, int marking)
    throws EngineException {

    ruleManipulationBackend.setMarking(id, place, marking);
  }

  @Override
  public void setCapacity(int id, Place place, int capacity)
    throws EngineException {

    ruleManipulationBackend.setCapacity(id, place, capacity);
  }

  @Override
  public void setPname(int id, Place place, String pname)
    throws EngineException {

    ruleManipulationBackend.setPname(id, place, pname);
  }

  @Override
  public void setTlb(int id, Transition transition, String tlb)
    throws EngineException {

    ruleManipulationBackend.setTlb(id, transition, tlb);
  }

  @Override
  public void setTname(int id, Transition transition, String tname)
    throws EngineException {

    ruleManipulationBackend.setTname(id, transition, tname);
  }

  @Override
  public void setWeight(int id, PreArc preArc, int weight)
    throws EngineException {

    ruleManipulationBackend.setWeight(id, preArc, weight);
  }

  @Override
  public void setWeight(int id, PostArc postArc, int weight)
    throws EngineException {

    ruleManipulationBackend.setWeight(id, postArc, weight);
  }

  @Override
  public void setRnw(int id, Transition transition, IRenew renews)
    throws EngineException {

    ruleManipulationBackend.setRnw(id, transition, renews);
  }

  @Override
  public void setPlaceColor(int id, Place place, Color color)
    throws EngineException {

    ruleManipulationBackend.setPlaceColor(id, place, color);
  }

  @Override
  public void setNodeSize(int id, double nodeSize)
    throws EngineException {

    ruleManipulationBackend.setNodeSize(id, nodeSize);
  }

  @Override
  public UUID createNac(int ruleId)
    throws EngineException {

    return ruleManipulationBackend.createNac(ruleId);
  }

  @Override
  public Place createPlace(int id, UUID nacId, Point2D coordinate)
    throws EngineException {

    return ruleManipulationBackend.createPlace(id, nacId, coordinate);
  }

  @Override
  public Transition createTransition(int id, UUID nacId, Point2D coordinate)
    throws EngineException {

    return ruleManipulationBackend.createTransition(id, nacId, coordinate);
  }

  @Override
  public PreArc createPreArc(int id, UUID nacId, Place from, Transition to)
    throws EngineException {

    return ruleManipulationBackend.createPreArc(id, nacId, from, to);
  }

  @Override
  public PostArc createPostArc(int id, UUID nacId, Transition from, Place to)
    throws EngineException {

    return ruleManipulationBackend.createPostArc(id, nacId, from, to);
  }

  @Override
  public void setWeight(int id, UUID nacId, IArc arc, int weight)
    throws EngineException {

    ruleManipulationBackend.setWeight(id, nacId, arc, weight);
  }

  @Override
  public void setPname(int id, UUID nacId, INode place, String pname)
    throws EngineException {

    ruleManipulationBackend.setPname(id, nacId, (Place) place, pname);
  }

  @Override
  public void setCapacity(int id, UUID nacId, INode place, int capacity)
    throws EngineException {

    ruleManipulationBackend.setCapacity(id, nacId, (Place) place, capacity);
  }

  @Override
  public void setMarking(int id, UUID nacId, INode place, int marking)
    throws EngineException {

    ruleManipulationBackend.setMarking(id, nacId, (Place) place, marking);
  }

  @Override
  public void setPlaceColor(int id, UUID nacId, INode place, Color color)
    throws EngineException {

    ruleManipulationBackend.setPlaceColor(id, nacId, (Place) place, color);
  }

  @Override
  public void setTlb(int id, UUID nacId, INode transition, String tlb)
    throws EngineException {

    ruleManipulationBackend.setTlb(id, nacId, (Transition) transition, tlb);
  }

  @Override
  public void setTname(int id, UUID nacId, INode transition, String tname)
    throws EngineException {

    ruleManipulationBackend.setTname(id, nacId, (Transition) transition,
      tname);
  }

  @Override
  public void setRnw(int id, UUID nacId, INode transition, IRenew renews)
    throws EngineException {

    ruleManipulationBackend.setRnw(id, nacId, (Transition) transition, renews);
  }
}
