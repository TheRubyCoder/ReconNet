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

import static exceptions.Exceptions.exceptionIf;
import static exceptions.Exceptions.warning;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;

import persistence.Persistence;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.ITransformation;
import transformation.NAC;
import transformation.Rule;
import transformation.Rule.Net;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.Positioning;
import engine.attribute.ArcAttribute;
import engine.attribute.NodeLayoutAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.data.RuleData;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.IllegalNacManipulationException;

/**
 * This is the implementation of all methods regarding rules by engine.
 *
 * @see IRuleManipulation
 * @see IRulePersistence
 */
public final class RuleHandler {

  private final static Logger LOGGER =
    Logger.getLogger(RuleHandler.class.getName());

  /** Session manager of engine */
  private final SessionManager sessionManager;
  /** Singleton instance of this class */
  private static RuleHandler ruleManipulation;

  private RuleHandler() {

    LOGGER.setLevel(Level.OFF);

    sessionManager = SessionManager.getInstance();
  }

  protected static RuleHandler getInstance() {

    if (ruleManipulation == null) {
      ruleManipulation = new RuleHandler();
    }
    return ruleManipulation;
  }

  /**
   * Creates an PreArc in the rule referenced by <code>id</code>
   *
   * @param id
   *        Id of the rule
   * @param net
   *        Part of the rule the arc will be added to
   * @param place
   *        Place to start the edge at
   * @param transition
   *        Transition node of edge
   * @return Created PreArc
   * @throws EngineException
   */
  public PreArc createPreArc(int id, RuleNet net, Place place,
    Transition transition)
      throws EngineException {

    checkIsPlace(place);
    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData lJungData = ruleData.getLJungData();
    JungData kJungData = ruleData.getKJungData();
    JungData rJungData = ruleData.getRJungData();

    ensureLegalNodeCombination(net, place, transition, rule);

    // create Arc in defined map
    PreArc createdArc = null;
    PreArc kArc = null;

    if (net.equals(RuleNet.L)) {

      createdArc = rule.addPreArcToL("undefined", place, transition);
      kArc = rule.fromLtoK(createdArc);

      for (NAC nac : rule.getNACs()) {

        JungData nacJungData = ruleData.getNacJungData(nac.getId());
        PreArc nacPreArc = nac.fromLtoNac(createdArc);
        nacJungData.createArc(nacPreArc, nacPreArc.getSource(),
          nacPreArc.getTransition());
      }

    } else if (net.equals(RuleNet.K)) {

      createdArc = rule.addPreArcToK("undefined", place, transition);
      kArc = createdArc;

      for (NAC nac : rule.getNACs()) {

        JungData nacJungData = ruleData.getNacJungData(nac.getId());
        PreArc lPreArc = rule.fromKtoL(createdArc);
        PreArc nacPreArc = nac.fromLtoNac(lPreArc);
        nacJungData.createArc(nacPreArc, nacPreArc.getSource(),
          nacPreArc.getTarget());
      }

    } else if (net.equals(RuleNet.R)) {
      createdArc = rule.addPreArcToR("undefined", place, transition);
      kArc = rule.fromRtoK(createdArc);
    }

    // add those arcs into the corresponding jung data
    PreArc lArc = rule.fromKtoL(kArc);
    PreArc rArc = rule.fromKtoR(kArc);

    if (lArc != null) {
      lJungData.createArc(lArc, lArc.getSource(), lArc.getTarget());
    }

    kJungData.createArc(kArc, kArc.getSource(), kArc.getTarget());

    if (rArc != null) {
      rJungData.createArc(rArc, rArc.getSource(), rArc.getTarget());
    }

    return createdArc;
  }

  /**
   * Creates an PostArc in the rule referenced by <code>id</code>
   *
   * @param id
   *        Id of the rule
   * @param net
   *        Part of the rule the arc will be added to
   * @param place
   *        Place to start the edge at
   * @param transition
   *        Transition node of edge
   * @return Created PostArc
   * @throws EngineException
   */
  public PostArc createPostArc(int id, RuleNet net, Transition transition,
    Place place)
      throws EngineException {

    checkIsPlace(place);
    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData lJungData = ruleData.getLJungData();
    JungData kJungData = ruleData.getKJungData();
    JungData rJungData = ruleData.getRJungData();

    ensureLegalNodeCombination(net, transition, place, rule);

    // create Arc in defined map
    PostArc createdArc = null;
    PostArc kArc = null;

    if (net.equals(RuleNet.L)) {

      createdArc = rule.addPostArcToL("undefined", transition, place);
      kArc = rule.fromLtoK(createdArc);

      for (NAC nac : rule.getNACs()) {

        JungData nacJungData = ruleData.getNacJungData(nac.getId());
        PostArc nacPostArc = nac.fromLtoNac(createdArc);
        nacJungData.createArc(nacPostArc, nacPostArc.getSource(),
          nacPostArc.getTarget());
      }

    } else if (net.equals(RuleNet.K)) {

      createdArc = rule.addPostArcToK("undefined", transition, place);
      kArc = createdArc;

      for (NAC nac : rule.getNACs()) {

        JungData nacJungData = ruleData.getNacJungData(nac.getId());
        PostArc lPostArc = rule.fromKtoL(createdArc);
        PostArc nacPostArc = nac.fromLtoNac(lPostArc);
        nacJungData.createArc(nacPostArc, nacPostArc.getSource(),
          nacPostArc.getTarget());
      }

    } else if (net.equals(RuleNet.R)) {
      createdArc = rule.addPostArcToR("undefined", transition, place);
      kArc = rule.fromRtoK(createdArc);
    }

    // add those arcs into the corresponding jung data
    PostArc lArc = rule.fromKtoL(kArc);
    PostArc rArc = rule.fromKtoR(kArc);

    if (lArc != null) {
      lJungData.createArc(lArc, lArc.getSource(), lArc.getTarget());
    }

    kJungData.createArc(kArc, kArc.getSource(), kArc.getTarget());

    if (rArc != null) {
      rJungData.createArc(rArc, rArc.getSource(), rArc.getTarget());
    }

    return createdArc;
  }

  /**
   * @see IRuleManipulation#createPlace(int, RuleNet, Point2D)
   */
  public Place createPlace(int id, RuleNet net, Point2D coordinate)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData lJungData = ruleData.getLJungData();
    JungData kJungData = ruleData.getKJungData();
    JungData rJungData = ruleData.getRJungData();

    if (!lJungData.isCreatePossibleAt(coordinate)) {
      exception("Place too close to Node in L");
      return null;
    }

    if (!kJungData.isCreatePossibleAt(coordinate)) {
      exception("Place too close to Node in K");
      return null;
    }

    if (!rJungData.isCreatePossibleAt(coordinate)) {
      exception("Place too close to Node in R");
      return null;
    }

    Color newPlaceColor = ruleData.getColorGenerator().next();

    if (net.equals(RuleNet.L)) {

      // create a new Place
      Place newPlace = rule.addPlaceToL("undefined");

      // call JungModificator
      try {
        lJungData.createPlace(newPlace, coordinate);
      } catch (IllegalArgumentException e) {
        exception("createPlace - can not create Place in L");
      }

      // create analog Place in all NACs
      for (NAC nac : rule.getNACs()) {

        JungData nacJungData = ruleData.getNacJungData(nac.getId());
        Place nacPlace = nac.fromLtoNac(newPlace);
        nacJungData.createPlace(nacPlace, coordinate);
        nacJungData.setPlaceColor(nacPlace, newPlaceColor);
      }

      // get automatically added Corresponding Place in K
      Place newPlaceInK = rule.fromLtoK(newPlace);

      if (newPlaceInK != null) {
        try {
          kJungData.createPlace(newPlaceInK, coordinate);
        } catch (IllegalArgumentException e) {
          exception("createPlace - can not create Place in K");
        }
      }

      setPlaceColor(id, newPlace, newPlaceColor);

      return newPlace;

    } else if (net.equals(RuleNet.K)) {
      // create a new Place
      Place newPlace = rule.addPlaceToK("undefined");

      // call JungModificator
      try {
        kJungData.createPlace(newPlace, coordinate);
        kJungData.setPlaceColor(newPlace, newPlaceColor);
      } catch (IllegalArgumentException e) {
        exception("createPlace - can not create Place in K");
      }

      // get automatically added Corresponding Place in L and R
      Place newPlaceInL = rule.fromKtoL(newPlace);
      Place newPlaceInR = rule.fromKtoR(newPlace);

      if (newPlaceInL != null && newPlaceInR != null) {
        try {
          lJungData.createPlace(newPlaceInL, coordinate);
          lJungData.setPlaceColor(newPlaceInL, newPlaceColor);

          rJungData.createPlace(newPlaceInR, coordinate);
          rJungData.setPlaceColor(newPlaceInR, newPlaceColor);

          // create analog Place in all NACs
          for (NAC nac : rule.getNACs()) {

            JungData nacJungData = ruleData.getNacJungData(nac.getId());
            Place nacPlace = nac.fromLtoNac(newPlaceInL);
            nacJungData.createPlace(nacPlace, coordinate);
            nacJungData.setPlaceColor(nacPlace, newPlaceColor);
          }

        } catch (IllegalArgumentException e) {
          exception("createPlace - can not create Place in K");
        }
      }

      return newPlace;

    } else if (net.equals(RuleNet.R)) {
      // create a new Place
      Place newPlace = rule.addPlaceToR("undefined");

      // call JungModificator
      try {
        rJungData.createPlace(newPlace, coordinate);
      } catch (IllegalArgumentException e) {
        exception("createPlace - can not create Place in R");
      }

      // get automatically added Corresponding Place in K
      Place newPlaceInK = rule.fromRtoK(newPlace);

      if (newPlaceInK != null) {
        try {
          kJungData.createPlace(newPlaceInK, coordinate);
        } catch (IllegalArgumentException e) {
          exception("createPlace - can not create Place in K");
        }
      }
      setPlaceColor(id, newPlace, newPlaceColor);

      return newPlace;

    } else {
      exception("createPlace - Not given if Manipulation is in L,K or R");
      return null;
    }
  }

  /**
   * @see IRuleManipulation#createRule()
   */
  public int createRule() {

    Rule rule = TransformationComponent.getTransformation().createRule();
    RuleData ruleData = sessionManager.createRuleData(rule);

    TransformationComponent.getTransformation().storeSessionId(
      ruleData.getId(), rule);

    return ruleData.getId();
  }

  /**
   * @see IRuleManipulation#createTransition(int, RuleNet, Point2D)
   */
  public Transition createTransition(int id, RuleNet net, Point2D coordinate)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData lJungData = ruleData.getLJungData();
    JungData kJungData = ruleData.getKJungData();
    JungData rJungData = ruleData.getRJungData();

    if (!lJungData.isCreatePossibleAt(coordinate)) {
      exception("Transition too close to Node in L");
    }

    if (!kJungData.isCreatePossibleAt(coordinate)) {
      exception("Transition too close to Node in K");
    }

    if (!rJungData.isCreatePossibleAt(coordinate)) {
      exception("Transition too close to Node in R");
    }

    if (net.equals(RuleNet.L)) {
      // create a new transition
      Transition newTransition = rule.addTransitionToL("undefined");

      // call JungModificator
      try {
        lJungData.createTransition(newTransition, coordinate);
      } catch (IllegalArgumentException e) {
        exception("createTransition - can not create Transition in L");
      }

      // create analog Place in all NACs
      for (NAC nac : rule.getNACs()) {

        JungData nacJungData = ruleData.getNacJungData(nac.getId());
        Transition nacTransition = nac.fromLtoNac(newTransition);
        nacJungData.createTransition(nacTransition, coordinate);
      }

      // get automatically added Corresponding Place in K
      Transition newTransitionInK = rule.fromLtoK(newTransition);

      if (newTransitionInK != null) {
        try {
          kJungData.createTransition(newTransitionInK, coordinate);
        } catch (IllegalArgumentException e) {
          exception("createPlace - can not create Transition in K");
        }
      }

      return newTransition;

    } else if (net.equals(RuleNet.K)) {
      // create a new Transition
      Transition newTransition = rule.addTransitionToK("undefined");

      // call JungModificator
      try {
        kJungData.createTransition(newTransition, coordinate);
      } catch (IllegalArgumentException e) {
        exception("createTransition - can not create Transition in K");
      }

      // get automatically added Corresponding Transition in L and R
      Transition newTransitionInL = rule.fromKtoL(newTransition);
      Transition newTransitionInR = rule.fromKtoR(newTransition);

      if (newTransitionInL != null && newTransitionInR != null) {
        try {
          lJungData.createTransition(newTransitionInL, coordinate);
          rJungData.createTransition(newTransitionInR, coordinate);

          // create analog Place in all NACs
          for (NAC nac : rule.getNACs()) {

            JungData nacJungData = ruleData.getNacJungData(nac.getId());
            Transition nacTransition = nac.fromLtoNac(newTransitionInL);
            nacJungData.createTransition(nacTransition, coordinate);
          }

        } catch (IllegalArgumentException e) {
          exception("createTransition"
            + " - can not create Transition in L or R");
        }
      }

      return newTransition;

    } else if (net.equals(RuleNet.R)) {
      // create a new Transition
      Transition newTransition = rule.addTransitionToR("undefined");

      // call JungModificator
      try {
        rJungData.createTransition(newTransition, coordinate);
      } catch (IllegalArgumentException e) {
        exception("createTransition - can not create Transition in R");
      }

      // get automatically added Corresponding Transition in K
      Transition newTransitionInK = rule.fromRtoK(newTransition);

      if (newTransitionInK != null) {
        try {
          kJungData.createTransition(newTransitionInK, coordinate);
        } catch (IllegalArgumentException e) {
          exception("createTransition" + " - can not create Transition in K");
        }
      }

      return newTransition;

    } else {
      exception("createTransition"
        + " - Not given if Manipulation is in L,K or R");
      return null;
    }
  }

  /**
   * @see IRuleManipulation#deleteArc(int, RuleNet, Arc)
   */
  public void deleteArc(int id, RuleNet net, IArc arc)
    throws EngineException {

    checkIsIArc(arc);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    if (net == null) {
      exception("Netz nicht erkannt");
      return;
    }

    if (net == RuleNet.L) {

      if (arc instanceof PreArc) {
        rule.removePreArcFromL((PreArc) arc);
      } else if (arc instanceof PostArc) {
        rule.removePostArcFromL((PostArc) arc);
      }

    } else if (net == RuleNet.K) {

      if (arc instanceof PreArc) {
        rule.removePreArcFromK((PreArc) arc);
      } else if (arc instanceof PostArc) {
        rule.removePostArcFromK((PostArc) arc);
      }

    } else if (net == RuleNet.R) {

      if (arc instanceof PreArc) {
        rule.removePreArcFromR((PreArc) arc);
      } else if (arc instanceof PostArc) {
        rule.removePostArcFromR((PostArc) arc);
      }

    }

    ruleData.deleteDataOfMissingElements(rule);
  }

  /**
   * @see IRuleManipulation#deletePlace(int, RuleNet, INode)
   */
  public void deletePlace(int id, RuleNet net, Place place)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    if (net == null) {
      exception("Netz nicht erkannt");
      return;
    }

    if (net == RuleNet.L) {
      rule.removePlaceFromL(place);
    } else if (net == RuleNet.K) {
      rule.removePlaceFromK(place);
    } else if (net == RuleNet.R) {
      rule.removePlaceFromR(place);
    }

    ruleData.deleteDataOfMissingElements(rule);
  }

  /**
   * @see IRuleManipulation#deleteTransition(int, RuleNet, INode)
   */
  public void deleteTransition(int id, RuleNet net, Transition transition)
    throws EngineException {

    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    if (net == null) {
      exception("Netz nicht erkannt");
      return;
    }

    if (net == RuleNet.L) {
      rule.removeTransitionFromL(transition);
    } else if (net == RuleNet.K) {
      rule.removeTransitionFromK(transition);
    } else if (net == RuleNet.R) {
      rule.removeTransitionFromR(transition);
    }

    ruleData.deleteDataOfMissingElements(rule);
  }

  /**
   * @throws EngineException
   * @see IRuleManipulation#getArcAttribute(int, Arc)
   */
  public ArcAttribute getArcAttribute(int id, IArc arc)
    throws EngineException {

    checkIsIArc(arc);

    return new ArcAttribute(arc.getWeight());
  }

  /**
   * @see IRuleManipulation#getJungLayout(int, RuleNet)
   */
  public AbstractLayout<INode, IArc> getJungLayout(int id, RuleNet net)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    if (net.equals(RuleNet.L)) {
      // Manipulation in L
      // Get JungData
      return ruleData.getLJungData().getJungLayout();
    } else if (net.equals(RuleNet.K)) {
      // Manipulation in K
      // Get JungData
      return ruleData.getKJungData().getJungLayout();
    } else if (net.equals(RuleNet.R)) {
      // Manipulation in R
      // Get JungData
      return ruleData.getRJungData().getJungLayout();
    }

    exception("getJungLayout"
      + " - Not given if Manipulation is in L,K, R or NAC");
    return null;
  }

  /**
   * @see IRuleManipulation#getPlaceAttribute(int, INode)
   */
  public PlaceAttribute getPlaceAttribute(int id, Place place)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    RuleNet containingNet = getContainingNet(id, place);
    JungData petrinetData = null;

    switch (containingNet) {
    case L:
      petrinetData = ruleData.getLJungData();
      break;
    case K:
      petrinetData = ruleData.getKJungData();
      break;
    case R:
      petrinetData = ruleData.getRJungData();
      break;
    default:
      break;
    }

    Color color = null;

    try {
      color = petrinetData.getPlaceColor(place);
    } catch (IllegalArgumentException ex) {
      color = Color.gray;
    }

    return new PlaceAttribute(place.getMark(), place.getName(), color,
      place.getCapacity());
  }

  /**
   * @see IRuleManipulation#getTransitionAttribute(int, INode)
   */
  public TransitionAttribute getTransitionAttribute(int id,
    Transition transition)
      throws EngineException {

    String tlb = transition.getTlb();
    String name = transition.getName();
    IRenew rnw = transition.getRnw();
    boolean isActivated = transition.isActivated();

    TransitionAttribute transAttr =
      new TransitionAttribute(tlb, name, rnw, isActivated);

    return transAttr;
  }

  /**
   * @see IRuleManipulation#getRuleAttribute(int)
   */
  public RuleAttribute getRuleAttribute(int id)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    // create a RuleAttribute
    return new RuleAttribute(ruleData.getRule().getL().getId(),
      ruleData.getRule().getK().getId(), ruleData.getRule().getR().getId());
  }

  /**
   * @see IRuleManipulation#moveNode(int, INode, Point2D)
   */
  public void moveNode(int id, INode node, Point2D relativePosition)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    try {
      // get Position
      ruleData.moveNodeRelative(node, relativePosition);
    } catch (IllegalArgumentException e) {
      throw new EngineException(e.getMessage());
    }
  }

  public void saveRuleWithNacs(int id, String path, String filename,
    String format)
      throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData jungDataL = ruleData.getLJungData();
    JungData jungDataK = ruleData.getKJungData();
    JungData jungDataR = ruleData.getRJungData();

    Map<INode, NodeLayoutAttribute> nodeMapL =
      jungDataL.getNodeLayoutAttributes();
    Map<INode, NodeLayoutAttribute> nodeMapK =
      jungDataK.getNodeLayoutAttributes();
    Map<INode, NodeLayoutAttribute> nodeMapR =
      jungDataR.getNodeLayoutAttributes();

    ArrayList<Map<INode, NodeLayoutAttribute>> nodeMapNacs =
      new ArrayList<Map<INode, NodeLayoutAttribute>>();

    for (JungData jungDataNac : ruleData.getNacJungDataSet()) {
      nodeMapNacs.add(jungDataNac.getNodeLayoutAttributes());
    }

    checkNodeLayoutAttribute(nodeMapL == null, "save - nodeMapL == null");
    checkNodeLayoutAttribute(nodeMapK == null, "save - nodeMapK == null");
    checkNodeLayoutAttribute(nodeMapR == null, "save - nodeMapR == null");

    for (int i = 0; i < nodeMapNacs.size(); i++) {

      checkNodeLayoutAttribute(nodeMapNacs.get(i) == null,
        "save - nodeMapNac[" + i + "] == null");
    }

    double kNodeSize = ruleData.getKJungData().getNodeSize();

    Persistence.saveRuleWithNacs(path + "/" + filename + "." + format, rule,
      nodeMapL, nodeMapK, nodeMapR, nodeMapNacs, kNodeSize);
  }

  /**
   * @see IRuleManipulation#loadRuleWithNacs(String, String)
   */
  public int loadRuleWithNacs(String path, String filename) {

    return Persistence.loadRuleWithNacs(path + "/" + filename,
      RulePersistence.getInstance());
  }

  /**
   * @see IRuleManipulation#setMarking(int, INode, int)
   */
  public void setMarking(int id, Place place, int marking)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();
    Net net = TransformationComponent.getTransformation().getNet(rule, place);

    if (net == Net.L) {
      rule.setMarkInL(place, marking);

    } else if (net == Net.K) {
      rule.setMarkInK(place, marking);

    } else if (net == Net.R) {
      rule.setMarkInR(place, marking);
    }
  }

  /**
   * @see IRuleManipulation#setMarking(int, INode, int)
   */
  public void setCapacity(int id, Place place, int capacity)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();
    Net net = TransformationComponent.getTransformation().getNet(rule, place);

    if (net == Net.L) {
      rule.setCapacityInL(place, capacity);

    } else if (net == Net.K) {
      rule.setCapacityInK(place, capacity);

    } else if (net == Net.R) {
      rule.setCapacityInR(place, capacity);
    }
  }

  /**
   * @see IRuleManipulation#setPname(int, INode, String)
   */
  public void setPname(int id, Place place, String pname)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    Net net =
      TransformationComponent.getTransformation().getNet(ruleData.getRule(),
        place);

    if (net == Net.L) {
      ruleData.getRule().setNameInL(place, pname);

    } else if (net == Net.K) {
      ruleData.getRule().setNameInK(place, pname);

    } else if (net == Net.R) {
      ruleData.getRule().setNameInR(place, pname);
    }

  }

  /**
   * @see IRuleManipulation#setPlaceColor(int, INode, Color)
   */
  public void setPlaceColor(int id, Place place, Color color)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    JungData lJungData = ruleData.getLJungData();
    JungData kJungData = ruleData.getKJungData();
    JungData rJungData = ruleData.getRJungData();

    ITransformation trans = TransformationComponent.getTransformation();
    List<INode> mappings = trans.getMappings(rule, place);

    INode nodeInL = mappings.get(0);
    INode nodeInK = mappings.get(1);
    INode nodeInR = mappings.get(2);

    if (nodeInL != null) {
      lJungData.setPlaceColor((Place) nodeInL, color);
    }

    if (nodeInK != null) {
      kJungData.setPlaceColor((Place) nodeInK, color);
    }

    if (nodeInR != null) {
      rJungData.setPlaceColor((Place) nodeInR, color);
    }

    Map<UUID, INode> nacNodes =
      trans.getCorrespondingNodesOfAllNacs(rule, place);

    for (Entry<UUID, INode> nacNode : nacNodes.entrySet()) {
      ruleData.getNacJungData(nacNode.getKey()).setPlaceColor(
        (Place) nacNode.getValue(), color);
    }

  }

  /**
   * @see IRuleManipulation#setTlb(int, INode, String)
   */
  public void setTlb(int id, Transition transition, String tlb)
    throws EngineException {

    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();
    RuleNet net = getContainingNet(id, transition);

    if (net.equals(RuleNet.L)) {
      rule.setTlbInL(transition, tlb);

    } else if (net.equals(RuleNet.K)) {
      rule.setTlbInK(transition, tlb);

    } else if (net.equals(RuleNet.R)) {
      rule.setTlbInR(transition, tlb);
    }
  }

  public void setTlb(int id, UUID nacId, Transition transition, String tlb)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isTransitionSafeToChange(transition)) {
      throw new IllegalNacManipulationException(
        "The specific transition is part of L. Therefore it should be modified in L.");
    }

    transition.setTlb(tlb);
  }

  /**
   * @see IRuleManipulation#setTname(int, INode, String)
   */
  public void setTname(int id, Transition transition, String tname)
    throws EngineException {

    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Net net =
      TransformationComponent.getTransformation().getNet(ruleData.getRule(),
        transition);

    if (net == Net.L) {
      ruleData.getRule().setNameInL(transition, tname);

    } else if (net == Net.K) {
      ruleData.getRule().setNameInK(transition, tname);

    } else if (net == Net.R) {
      ruleData.getRule().setNameInR(transition, tname);
    }
  }

  /**
   * @see IRuleManipulation#setRnw(int, INode, IRenew)
   */
  public void setRnw(int id, Transition transition, IRenew renew)
    throws EngineException {

    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();
    RuleNet net = getContainingNet(id, transition);

    if (net.equals(RuleNet.L)) {
      rule.setRnwInL(transition, renew);

    } else if (net.equals(RuleNet.K)) {
      rule.setRnwInK(transition, renew);

    } else if (net.equals(RuleNet.R)) {
      rule.setRnwInR(transition, renew);
    }
  }

  /**
   * @see IRuleManipulation#setWeight(int, Arc, int)
   */
  public void setWeight(int id, PreArc preArc, int weight)
    throws EngineException {

    checkIsPreArc(preArc);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    // Synchronize Arcs in the other parts of the rules
    RuleNet net = getContainingNet(id, preArc);

    if (net == RuleNet.L) {
      rule.setWeightInL(preArc, weight);

    } else if (net == RuleNet.K) {
      rule.setWeightInK(preArc, weight);

    } else if (net == RuleNet.R) {
      rule.setWeightInR(preArc, weight);
    }
  }

  /**
   * @see IRuleManipulation#setWeight(int, Arc, int)
   */
  public void setWeight(int id, PostArc postArc, int weight)
    throws EngineException {

    checkIsPostArc(postArc);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    // Synchronize Arcs in the other parts of the rules
    RuleNet net = getContainingNet(id, postArc);

    if (net == RuleNet.L) {
      rule.setWeightInL(postArc, weight);

    } else if (net == RuleNet.K) {
      rule.setWeightInK(postArc, weight);

    } else if (net == RuleNet.R) {
      rule.setWeightInR(postArc, weight);
    }
  }

  /**
   * @see IRuleManipulation#closeRule(int)
   */
  public void closeRule(int id)
    throws EngineException {

    getRuleData(id);

    if (!sessionManager.closeSessionData(id)) {
      exception("closeRule - can not remove RuleData");
    }
  }

  /**
   * @throws EngineException
   * @see IRuleManipulation#getNodeType(INode)
   */
  public NodeTypeEnum getNodeType(INode node)
    throws EngineException {

    if (node instanceof Place) {
      return NodeTypeEnum.Place;
    } else if (node instanceof Transition) {
      return NodeTypeEnum.Transition;
    } else {
      exception("getNodeType - wrong type");
      return null;
    }
  }

  /**
   * @param value
   * @throws EngineException
   */
  private void exception(String value)
    throws EngineException {

    throw new EngineException("RuleHandler: " + value);
  }

  private void checkNodeLayoutAttribute(boolean value, String errorMessage)
    throws EngineException {

    if (value) {
      exception(errorMessage);
    }
  }

  // Helper Method
  private RuleNet getContainingNet(int id, INode node)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    if (rule.getL().getPlaces().contains(node)
      || rule.getL().getTransitions().contains(node)) {
      return RuleNet.L;
    }

    if (rule.getK().getPlaces().contains(node)
      || rule.getK().getTransitions().contains(node)) {
      return RuleNet.K;
    }

    if (rule.getR().getPlaces().contains(node)
      || rule.getR().getTransitions().contains(node)) {
      return RuleNet.R;
    }

    for (NAC nac : rule.getNACs()) {
      if (nac.getNac().getPlaces().contains(node)
        || nac.getNac().getTransitions().contains(node)) {
        return RuleNet.NAC;
      }
    }

    return null;
  }

  private RuleNet getContainingNet(int id, IArc arc)
    throws EngineException {

    checkIsIArc(arc);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    if (rule.getL().getArcs().contains(arc)) {
      return RuleNet.L;
    }

    if (rule.getK().getArcs().contains(arc)) {
      return RuleNet.K;
    }

    if (rule.getR().getArcs().contains(arc)) {
      return RuleNet.R;
    }

    return null;
  }

  /**
   * @see {@link IRuleManipulation#moveGraph(int, Point2D)}
   * @param id
   * @param relativePosition
   */
  public void moveGraph(int id, Point2D relativePosition)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    ruleData.getLJungData().moveGraph(relativePosition);
    ruleData.getKJungData().moveGraph(relativePosition);
    ruleData.getRJungData().moveGraph(relativePosition);

    for (JungData nacJungData : ruleData.getNacJungDataSet()) {
      nacJungData.moveGraph(relativePosition);
    }
  }

  /**
   * @see {@link IRuleManipulation#moveGraphIntoVision(int)}
   * @param id
   */
  public void moveGraphIntoVision(int id)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    // how must k be moved?
    Point2D.Double vectorToMoveIntoVision =
      ruleData.getKJungData().getVectorToMoveIntoVision();
    // move all graphs equally to k so their relative position stay the same
    ruleData.getLJungData().moveGraph(vectorToMoveIntoVision);
    ruleData.getKJungData().moveGraph(vectorToMoveIntoVision);
    ruleData.getRJungData().moveGraph(vectorToMoveIntoVision);

    for (JungData nacJungData : ruleData.getNacJungDataSet()) {
      nacJungData.moveGraph(vectorToMoveIntoVision);
    }
  }

  /**
   * @see {@link IRuleManipulation#moveAllNodesTo(int, float, Point)}
   * @param id
   * @param factor
   * @param point
   */
  public void moveAllNodesTo(int id, float factor, Point point)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    ruleData.getLJungData().moveAllNodesTo(factor, point);
    ruleData.getKJungData().moveAllNodesTo(factor, point);
    ruleData.getRJungData().moveAllNodesTo(factor, point);

    for (JungData nacJungData : ruleData.getNacJungDataSet()) {
      nacJungData.moveAllNodesTo(factor, point);
    }
  }

  /**
   * @see {@link IRuleManipulation#setNodeSize(int, double)}
   * @param id
   * @param nodeSize
   */
  public void setNodeSize(int id, double nodeSize)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    ruleData.getLJungData().setNodeSize(nodeSize);
    ruleData.getKJungData().setNodeSize(nodeSize);
    ruleData.getRJungData().setNodeSize(nodeSize);

    for (JungData nacJungData : ruleData.getNacJungDataSet()) {
      nacJungData.setNodeSize(nodeSize);
    }
  }

  /**
   * @see {@link IRuleManipulation#getNodeSize(int)}
   * @param id
   * @return
   */
  public double getNodeSize(int id)
    throws EngineException {

    RuleData ruleData = getRuleData(id);

    // NodeSize is equal for all parts of the rule
    return ruleData.getLJungData().getNodeSize();
  }

  private RuleData getRuleData(int ruleDataId)
    throws EngineException {

    // get the RuleData from the id and SessionManager
    RuleData ruleData = sessionManager.getRuleData(ruleDataId);

    // Test: is id valid
    if (ruleData == null) {
      exception("id of the Rule is wrong");
      return null;
    }

    return ruleData;
  }

  /**
   * Checks whether a combination of start and taget nodes is valid for
   * creating an arc in the rule
   *
   * @param net
   * @param from
   * @param to
   * @param rule
   * @throws ShowAsWarningException
   *         with human friendly text message if combination is illegal
   */
  private void ensureLegalNodeCombination(RuleNet net, Place from,
    Transition to, Rule rule) {

    if (!net.equals(RuleNet.K)) {
      return;
    }

    if (rule.fromKtoL(from) == null) {
      warning("Startknoten in L nicht verfuegbar");
    } else if (rule.fromKtoR(from) == null) {
      warning("Startknoten in R nicht verfuegbar");
    }

    if (rule.fromKtoL(to) == null) {
      warning("Zielknoten in L nicht verfuegbar");

    } else if (rule.fromKtoR(to) == null) {
      warning("Zielknoten in R nicht verfuegbar");
    }
  }

  /**
   * Checks whether a combination of start and taget nodes is valid for
   * creating an arc in the rule
   *
   * @param net
   * @param from
   * @param to
   * @param rule
   * @throws ShowAsWarningException
   *         with human friendly text message if combination is illegal
   */
  private void ensureLegalNodeCombination(RuleNet net, Transition from,
    Place to, Rule rule) {

    if (!net.equals(RuleNet.K)) {
      return;
    }

    if (rule.fromKtoL(from) == null) {
      warning("Startknoten in L nicht verfuegbar");
    } else if (rule.fromKtoR(from) == null) {
      warning("Startknoten in R nicht verfuegbar");
    }

    if (rule.fromKtoL(to) == null) {
      warning("Zielknoten in L nicht verfuegbar");

    } else if (rule.fromKtoR(to) == null) {
      warning("Zielknoten in R nicht verfuegbar");
    }
  }

  private void checkIsPlace(Place place)
    throws EngineException {

    exceptionIf(!(place instanceof Place), "this isn't a place");
  }

  private void checkIsTransition(Transition transition)
    throws EngineException {

    exceptionIf(!(transition instanceof Transition),
      "this isn't a transition");
  }

  private void checkIsPreArc(PreArc preArc)
    throws EngineException {

    exceptionIf(!(preArc instanceof PreArc), "this isn't a preArc");
  }

  private void checkIsPostArc(PostArc postArc)
    throws EngineException {

    exceptionIf(!(postArc instanceof PostArc), "this isn't a postArc");
  }

  private void checkIsIArc(IArc arc)
    throws EngineException {

    exceptionIf(!(arc instanceof IArc), "this isn't an arc");
  }

  /*
   * Checks if the given transition is present in L If so, adding pre or post
   * arc in the NAC is not allowed
   */
  private void checkIfNewArcInNacIsAllowed(NAC nac, Transition transition)
    throws EngineException {

    Transition transitionInL = nac.fromNacToL(transition);

    exceptionIf(
      (transitionInL != null),
      "The transition effected by this new arc is contained in L. Therefore creating this new arc is not allowed.");
  }

  public UUID createNac(int ruleId)
    throws EngineException {

    RuleData ruleData = getRuleData(ruleId);
    Rule rule = ruleData.getRule();

    NAC nac = rule.createPlainNAC();
    sessionManager.createJungLayoutForNac(ruleData, nac.getId());

    JungData nacJungData = ruleData.getNacJungData(nac.getId());

    /* Add Places from L to new NAC */
    for (Place lPlace : rule.getL().getPlaces()) {

      JungData lJungData = ruleData.getLJungData();

      double x_coord = lJungData.getJungLayout().getX(lPlace);
      double y_coord = lJungData.getJungLayout().getY(lPlace);
      Point2D coordinate = new Point2D.Double(x_coord, y_coord);

      Place nacPlace =
        rule.addPlaceToNacWithMappingToPlaceInL(lPlace, nac.getId());

      nacJungData.createPlace(nacPlace, coordinate);
      nacJungData.setPlaceColor(nacPlace, lJungData.getPlaceColor(lPlace));
    }

    /* Add Transitions from L to new NAC */
    for (Transition lTransition : rule.getL().getTransitions()) {

      JungData lJungData = ruleData.getLJungData();

      double x_coord = lJungData.getJungLayout().getX(lTransition);
      double y_coord = lJungData.getJungLayout().getY(lTransition);
      Point2D coordinate = new Point2D.Double(x_coord, y_coord);

      Transition nacTransition =
        rule.addTransitionToNacWithMappingToTransitionInL(lTransition,
          nac.getId());
      nacJungData.createTransition(nacTransition, coordinate);
    }

    /* Add PostArcs from L to new NAC */
    for (PostArc lPostArc : rule.getL().getPostArcs()) {

      PostArc nacPostArc =
        rule.addPostArcToNacWithMappingToPostArcInL(lPostArc, nac.getId());

      nacJungData.createArc(nacPostArc, nacPostArc.getSource(),
        nacPostArc.getTarget());
    }

    /* Add PreArcs from L to new NAC */
    for (PreArc lPreArc : rule.getL().getPreArcs()) {

      PreArc nacPreArc =
        rule.addPreArcToNacWithMappingToPreArcInL(lPreArc, nac.getId());

      nacJungData.createArc(nacPreArc, nacPreArc.getSource(),
        nacPreArc.getTarget());

    }

    return nac.getId();
  }

  public List<UUID> getNacIds(int ruleId)
    throws EngineException {

    RuleData ruleData = getRuleData(ruleId);
    Rule rule = ruleData.getRule();

    ArrayList<UUID> nacIds = new ArrayList<UUID>();

    for (NAC nac : rule.getNACs()) {
      nacIds.add(nac.getId());
    }

    return nacIds;
  }

  public void deleteNac(int ruleId, UUID nacId)
    throws EngineException {

    RuleData ruleData = getRuleData(ruleId);
    Rule rule = ruleData.getRule();

    rule.deleteNac(nacId);
    ruleData.deleteNacJungData(nacId);
  }

  public Place createPlace(int id, UUID nacId, Point2D coordinate)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    Place newPlace = rule.addPlaceToNac("undefined", nac);

    // create the Place in the jungData
    JungData nacJungData = ruleData.getNacJungData(nacId);
    nacJungData.createPlace(newPlace, coordinate);

    Color newPlaceColor = ruleData.getColorGenerator().next();
    setPlaceColor(id, nacId, newPlace, newPlaceColor);

    return newPlace;
  }

  public void deletePlace(int id, UUID nacId, Place place)
    throws EngineException {

    checkIsPlace(place);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isPlaceSafeToChange(place)) {
      throw new IllegalNacManipulationException(
        "The specific place is part of L. Therefore it should be modified in L.");
    }

    LOGGER.info(".. deletePlace from NAC " + nac);

    JungData nacJungData = ruleData.getNacJungData(nacId);

    // delete-collections must be build before deleting
    ArrayList<IArc> arcsToDelete = new ArrayList<IArc>();
    ArrayList<INode> nodesToDelete = new ArrayList<INode>();

    nodesToDelete.add(place);
    arcsToDelete.addAll(place.getIncomingArcs());
    arcsToDelete.addAll(place.getOutgoingArcs());

    // TODO check function of removeTransitionFromNac - method modifies L->NAC
    // mapping which is not intended here. Nodes coming from L should only be
    // deleted in L
    rule.removePlaceFromNac(place, nac);
    nacJungData.delete(arcsToDelete, nodesToDelete);
  }

  public Transition createTransition(int id, UUID nacId, Point2D coordinate)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    Transition newTransition = rule.addTransitionToNac("undefined", nac);

    JungData nacJungData = ruleData.getNacJungData(nacId);
    nacJungData.createTransition(newTransition, coordinate);

    return newTransition;
  }

  public void deleteTransition(int id, UUID nacId, Transition transition)
    throws EngineException {

    checkIsTransition(transition);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isTransitionSafeToChange(transition)) {
      throw new IllegalNacManipulationException(
        "The specific transition is part of L. Therefore it should be modified in L.");
    }

    LOGGER.info(".. deleteTransition from NAC " + nac);

    JungData nacJungData = ruleData.getNacJungData(nacId);

    // delete-collections must be build before deleting
    ArrayList<IArc> arcsToDelete = new ArrayList<IArc>();
    ArrayList<INode> nodesToDelete = new ArrayList<INode>();

    nodesToDelete.add(transition);
    arcsToDelete.addAll(transition.getIncomingArcs());
    arcsToDelete.addAll(transition.getOutgoingArcs());

    // TODO check function of removeTransitionFromNac - method modifies L->NAC
    // mapping which is not intended here. Nodes coming from L should only be
    // deleted in L
    rule.removeTransitionFromNac(transition, nac);
    nacJungData.delete(arcsToDelete, nodesToDelete);
  }

  public PreArc createPreArc(int id, UUID nacId, Place from, Transition to)
    throws EngineException {

    checkIsPlace(from);
    checkIsTransition(to);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    checkIfNewArcInNacIsAllowed(nac, to);

    PreArc newPreArc = rule.addPreArcToNac("undefined", from, to, nac);

    JungData nacJungData = ruleData.getNacJungData(nacId);
    nacJungData.createArc(newPreArc, from, to);

    return newPreArc;
  }

  public PostArc createPostArc(int id, UUID nacId, Transition from, Place to)
    throws EngineException {

    checkIsTransition(from);
    checkIsPlace(to);

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    checkIfNewArcInNacIsAllowed(nac, from);

    PostArc newPostArc = rule.addPostArcToNac("undefined", from, to, nac);

    JungData nacJungData = ruleData.getNacJungData(nacId);
    nacJungData.createArc(newPostArc, from, to);

    return newPostArc;
  }

  public void deleteArc(int id, UUID nacId, IArc arc)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isArcSafeToChange(arc)) {
      throw new IllegalNacManipulationException(
        "The specific arc is part of L. Therefore it should be modified in L.");
    }

    if (arc instanceof PreArc) {
      rule.removePreArcFromNac((PreArc) arc, nac);
    }

    if (arc instanceof PostArc) {
      rule.removePostArcFromNac((PostArc) arc, nac);
    }

    ruleData.deleteDataOfMissingElements(rule);
  }

  public AbstractLayout<INode, IArc> getJungLayout(int ruleId, UUID nacId)
    throws EngineException {

    RuleData ruleData = getRuleData(ruleId);

    JungData nacJungData = ruleData.getNacJungData(nacId);
    NAC nacModelData = ruleData.getRule().getNAC(nacId);

    LOGGER.info("nacJungData:" + " Nodes("
      + nacJungData.getJungGraph().getVertexCount() + ")" + " Edges("
      + nacJungData.getJungGraph().getEdgeCount() + ")");

    int nacNodes =
      nacModelData.getNac().getPlaces().size()
      + nacModelData.getNac().getTransitions().size();
    int nacEdges = nacModelData.getNac().getArcs().size();

    LOGGER.info("nacModelData:" + " Nodes(" + nacNodes + ")" + " Edges("
      + nacEdges + ")");

    return ruleData.getNacJungData(nacId).getJungLayout();
  }

  public PlaceAttribute getPlaceAttribute(int id, UUID nacId, Place place)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    JungData nacJungData = ruleData.getNacJungData(nacId);

    return new PlaceAttribute(place.getMark(), place.getName(),
      nacJungData.getPlaceColor(place), place.getCapacity());
  }

  public TransitionAttribute getTransitionAttribute(int id, UUID nacId,
    Transition transition)
      throws EngineException {

    String tlb = transition.getTlb();
    String name = transition.getName();
    IRenew rnw = transition.getRnw();
    boolean activated = transition.isActivated();

    return new TransitionAttribute(tlb, name, rnw, activated);
  }

  public ArcAttribute getArcAttribute(int id, UUID nacId, IArc arc)
    throws EngineException {

    checkIsIArc(arc);

    return new ArcAttribute(arc.getWeight());
  }

  public void setPname(int id, UUID nacId, Place place, String pname)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isPlaceSafeToChange(place)) {
      throw new IllegalNacManipulationException(
        "The specific place is part of L. Therefore it should be modified in L.");
    }

    place.setName(pname);
  }

  public void
  setTname(int id, UUID nacId, Transition transition, String tname)
      throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isTransitionSafeToChange(transition)) {
      throw new IllegalNacManipulationException(
        "The specific transition is part of L. Therefore it should be modified in L.");
    }

    transition.setName(tname);
  }

  public void setWeight(int id, UUID nacId, IArc arc, int weight)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isArcSafeToChange(arc)) {
      throw new IllegalNacManipulationException(
        "The specific arc is part of L. Therefore it should be modified in L.");
    }

    if (arc instanceof PreArc) {

      rule.setWeightInNac((PreArc) arc, weight, nac);

    } else if (arc instanceof PostArc) {

      rule.setWeightInNac((PostArc) arc, weight, nac);
    }

  }

  public void
  setRnw(int id, UUID nacId, Transition transition, IRenew renews)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isTransitionSafeToChange(transition)) {
      throw new IllegalNacManipulationException(
        "The specific transition is part of L. Therefore it should be modified in L.");
    }

    rule.setRnwInNac(transition, renews, nac);
  }

  public void setMarking(int id, UUID nacId, Place place, int marking)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    rule.setMarkInNac(place, marking, nac);
  }

  public void setCapacity(int id, UUID nacId, Place place, int capacity)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isPlaceSafeToChange(place)) {
      throw new IllegalNacManipulationException(
        "The specific place is part of L. Therefore it should be modified in L.");
    }

    rule.setCapacityInNac(place, capacity, nac);
  }

  public void setPlaceColor(int id, UUID nacId, Place place, Color color)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (!nac.isPlaceSafeToChange(place)) {
      throw new IllegalNacManipulationException(
        "The specific place is part of L. Therefore it should be modified in L.");
    }

    JungData nacJungData = ruleData.getNacJungData(nacId);
    nacJungData.setPlaceColor(place, color);
  }

  public void moveNode(int id, UUID nacId, INode node,
    Point2D relativePosition)
    throws EngineException {

    RuleData ruleData = getRuleData(id);
    Rule rule = ruleData.getRule();

    NAC nac = rule.getNAC(nacId);

    if (node instanceof Place) {
      if (!nac.isPlaceSafeToChange((Place) node)) {
        throw new IllegalNacManipulationException(
          "The specific place is part of L. Therefore it should be modified in L.");
      }
    } else if (node instanceof Transition) {
      if (!nac.isTransitionSafeToChange((Transition) node)) {
        throw new IllegalNacManipulationException(
          "The specific transition is part of L. Therefore it should be modified in L.");
      }
    }

    ruleData.getNacJungData(nacId).moveNodeWithPositionCheck(
      node,
      Positioning.addPoints(
        ruleData.getNacJungData(nacId).getNodeLayoutAttributes().get(node).getCoordinate(),
        relativePosition));

  }
}
