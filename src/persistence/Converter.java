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
 * “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
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

package persistence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Renews;
import transformation.ITransformation;
import transformation.NAC;
import transformation.Rule;
import transformation.TransformationComponent;
import engine.attribute.NodeLayoutAttribute;
import engine.handler.RuleNet;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;
import exceptions.ShowAsWarningException;
import gui.PopUp;

/**
 * This utility class provides converting methods which are used by
 * persistence class to convert from and to JAXB-classes.
 */
public final class Converter {

  private Converter() {

    // utilitiy class
  }

  /**
   * Value for attribute {@link Net#getType() type} of {@link Net xmlNet}
   */
  private static final String PETRINET_IDENT = "petrinet";
  /**
   * Value for attribute {@link Net#getType() type} of {@link Net xmlNet}
   */
  private static final String RULE_IDENT = "rule";

  /**
   * Converts a logical {@link Petrinet petrinet} object into the equivalent
   * Pnml object tree, which can the be marshalled into a file.
   *
   * @param petrinet
   *        logical petrinet
   * @param layout
   *        layout information in the following format:
   *        <ul>
   *        <li>key: id of node as String</li>
   *        <li>value: a list of Strings consistent of: [x, y, red, green,
   *        blue] all as Strings. Where x and y are position in pixels and
   *        red, green, blue are between 0 and 255
   *        </ul>
   * @param nodeSize
   *        the size of the nodes in pixels. This is important due to
   *        collision detection when loading the petrinet.
   * @return
   */
  public static Pnml convertPetrinetToPnml(Petrinet petrinet,
    Map<String, String[]> layout, double nodeSize) {

    // PNML node
    Pnml pnml = new Pnml();
    pnml.setNodeSize(nodeSize);
    pnml.setType(PETRINET_IDENT);
    pnml.setNet(new ArrayList<Net>());

    // Net node
    Net xmlnet = new Net();
    xmlnet.setId(String.valueOf(petrinet.getId()));
    pnml.getNet().add(xmlnet);

    // Page node
    Page page = new Page();
    xmlnet.setPage(page);

    // Places
    Set<petrinet.model.Place> set = petrinet.getPlaces();
    List<Place> places = new ArrayList<Place>();
    page.setPlace(places);
    for (petrinet.model.Place place : set) {
      addPlaceToPnml(layout, places, place);
    }

    // Transitions
    Set<petrinet.model.Transition> transis = petrinet.getTransitions();
    List<Transition> tList = new ArrayList<Transition>();
    for (petrinet.model.Transition t : transis) {
      addTransitionToPnml(layout, tList, t);
    }
    page.setTransition(tList);

    // Arcs
    Set<petrinet.model.IArc> arcs = petrinet.getArcs();
    List<Arc> newArcs = new ArrayList<Arc>();
    for (petrinet.model.IArc arc : arcs) {
      addArcToPnml(places, tList, newArcs, arc);
    }
    page.setArc(newArcs);
    return pnml;
  }

  /**
   * Converts a {@link Pnml} object that was unmarshalled from an XMLFile into
   * a {@link Petrinet}
   *
   * @param pnml
   *        The object tree, representing the XML file
   * @param handler
   *        The engine handler to create and modify the petrinet.
   * @return id of petrinet Id of the created petrinet
   */
  public static int convertPnmlToPetrinet(Pnml pnml,
    IPetrinetPersistence handler) {

    // In each XML file there is the type attribute for the pnml node to
    // quick-check if its a rule or a petrinet
    if (!pnml.getType().equals(PETRINET_IDENT)) {
      throw new ShowAsWarningException(
        "Die ausgewaehlte Datei enthaelt eine Regel, kein Petrinetz");
    }
    int petrinetID = -1;
    try {
      // create petrinet
      petrinetID = handler.createPetrinet();

      handler.setNodeSize(petrinetID, pnml.getNodeSize());

      /**
       * Maps XML id to logical object
       */
      Map<String, petrinet.model.INode> placesAndTransis =
        new HashMap<String, petrinet.model.INode>();

      // create places
      for (Place place : pnml.getNet().get(0).getPage().getPlace()) {
        petrinet.model.Place realPlace =
          handler.createPlace(petrinetID,
            positionToPoint2D(place.getGraphics().getPosition()));

        handler.setPlaceColor(petrinetID, realPlace,
          place.getGraphics().getColor().toAWTColor());

        handler.setPname(petrinetID, realPlace,
          place.getPlaceName().getText());

        handler.setMarking(petrinetID, realPlace,
          Integer.parseInt(place.getInitialMarking().getText()));

        if (place.getInitialCapacity() != null) {
          handler.setCapacity(petrinetID, realPlace,
            Integer.parseInt(place.getInitialCapacity().getText()));
        } else {
          handler.setCapacity(petrinetID, realPlace, Integer.MAX_VALUE);
        }

        placesAndTransis.put(place.getId(), realPlace);
      }

      // create transitions
      List<Transition> transitions =
        pnml.getNet().get(0).getPage().getTransition();

      for (Transition pnmlTransition : transitions) {
        petrinet.model.Transition realTransition =
          handler.createTransition(petrinetID,
            positionToPoint2D(pnmlTransition.getGraphics().getPosition()));

        handler.setTname(petrinetID, realTransition,
          pnmlTransition.getTransitionName().getText());
        handler.setTlb(petrinetID, realTransition,
          pnmlTransition.getTransitionLabel().getText());
        handler.setRnw(petrinetID, realTransition,
          Renews.fromString(pnmlTransition.getTransitionRenew().getText()));

        placesAndTransis.put(pnmlTransition.getId(), realTransition);

      }

      // create arcs
      for (Arc arc : pnml.getNet().get(0).getPage().getArc()) {
        int weight = 1;

        if (arc.getToolspecific() != null) {
          weight =
            Integer.valueOf(arc.getToolspecific().getWeight().getText());
        }

        INode source = placesAndTransis.get(arc.getSource());
        INode target = placesAndTransis.get(arc.getTarget());

        if (source instanceof petrinet.model.Place
          && target instanceof petrinet.model.Transition) {

          petrinet.model.Place p = (petrinet.model.Place) source;
          petrinet.model.Transition t = (petrinet.model.Transition) target;

          petrinet.model.PreArc preArc =
            handler.createPreArc(petrinetID, p, t);

          handler.setWeight(petrinetID, preArc, weight);
        } else {
          petrinet.model.PostArc postArc =
            handler.createPostArc(
              petrinetID,
              (petrinet.model.Transition) placesAndTransis.get(arc.getSource()),
              (petrinet.model.Place) placesAndTransis.get(arc.getTarget()));
          handler.setWeight(petrinetID, postArc, weight);
        }
      }

      return petrinetID;
    } catch (EngineException e) {
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * Converts a logical {@link Rule rule} object into the equivalent Pnml
   * object tree, which can the be marshalled into a file.
   *
   * @param rule
   *        logical rule
   * @param layout
   *        layout information in the following format:
   *        <ul>
   *        <li>key: id of node as String</li>
   *        <li>value: {@link NodeLayoutAttribute layout}
   *        </ul>
   * @param nodeSize
   *        the size of the nodes in pixels. This is important due to
   *        collision detection when loading the petrinet.
   * @return
   */
  public static Pnml convertRuleWithNacsToPnml(Rule rule,
    Map<INode, NodeLayoutAttribute> map, double nodeSize) {

    System.out.println("convertRuleWithNacToPnml");

    Pnml pnml = new Pnml();

    pnml.setType(RULE_IDENT);
    pnml.setNodeSize(nodeSize);

    pnml.setNet(new ArrayList<Net>());
    final Net lNet = createNet(rule.getL(), map, RuleNet.L, rule);
    final Net kNet = createNet(rule.getK(), map, RuleNet.K, rule);
    final Net rNet = createNet(rule.getR(), map, RuleNet.R, rule);

    final ArrayList<Net> nacNetList = new ArrayList<Net>();

    for (NAC nac : rule.getNACs()) {
      nacNetList.add(createNetFromNac(nac, map, RuleNet.NAC, rule));
    }

    pnml.setNet(new ArrayList<Net>() {

      private static final long serialVersionUID = 8434245017694015611L;

      {
        add(lNet);
        add(kNet);
        add(rNet);
        addAll(nacNetList);
      }
    });

    return pnml;
  }

  /**
   * Converts a {@link Pnml} object that was unmarshalled from an XMLFile into
   * a {@link Rule}
   *
   * @param pnml
   *        The object tree, representing the XML file
   * @param handler
   *        The engine handler to create and modify the rule.
   * @return id of petrinet Id of the created petrinet
   */
  public static int convertPnmlToRuleWithNacs(Pnml pnml,
    IRulePersistence handler) {

    if (!pnml.getType().equals(RULE_IDENT)) {
      throw new ShowAsWarningException(
        "The selected file doesn't have a rule.");
    }

    int ruleId = handler.createRule();

    try {
      handler.setNodeSize(ruleId, pnml.getNodeSize());

      Net lNet = null;
      Net kNet = null;
      Net rNet = null;
      ArrayList<Net> nacNetList = new ArrayList<Net>();

      for (Net net : pnml.getNet()) {

        if (net.getNettype().equals(RuleNet.L.name())) {
          lNet = net;
        } else if (net.getNettype().equals(RuleNet.K.name())) {
          kNet = net;
        } else if (net.getNettype().equals(RuleNet.R.name())) {
          rNet = net;
        } else if (net.getNettype().equals(RuleNet.NAC.name())) {
          nacNetList.add(net);
        }
      }

      if (lNet == null || kNet == null || rNet == null) {
        throw new ShowAsWarningException(
          "Cannot load rule. PNML data is corrupt.");
      }

      // Elements of L
      List<Place> lPlaces = lNet.getPage().getPlace();
      List<Transition> lTransis = lNet.getPage().getTransition();
      List<Arc> lArcs = lNet.getPage().getArc();

      // Elements of K
      List<Place> kPlaces = kNet.getPage().getPlace();
      List<Transition> kTransis = kNet.getPage().getTransition();
      List<Arc> kArcs = kNet.getPage().getArc();

      // Elements of R
      List<Place> rPlaces = rNet.getPage().getPlace();
      List<Transition> rTransis = rNet.getPage().getTransition();
      List<Arc> rArcs = rNet.getPage().getArc();

      // transform lists into sets for mathematical operations

      Set<Place> lPlaceSet = new HashSet<Place>(lPlaces);
      Set<Transition> lTransitionSet = new HashSet<Transition>(lTransis);
      Set<Arc> lArcSet = new HashSet<Arc>(lArcs);

      Set<Place> kPlaceSet = new HashSet<Place>(kPlaces);
      Set<Transition> kTransitionSet = new HashSet<Transition>(kTransis);
      Set<Arc> kArcSet = new HashSet<Arc>(kArcs);

      Set<Place> rPlaceSet = new HashSet<Place>(rPlaces);
      Set<Transition> rTransitionSet = new HashSet<Transition>(rTransis);
      Set<Arc> rArcSet = new HashSet<Arc>(rArcs);

      // Skizze
      // leere Nacs hinzufuegen (Anzahl anhand PNML)
      // Regel aufbauen -> baut auch Teile von NACs auf
      // restlichen Elemente zu NACs hinzufuegen

      // initialize NACs - must be done before L, K, and R will be build
      // safe nacId -> Net mapping for further processing
      Map<UUID, Net> nacNetMap = new HashMap<UUID, Net>();
      for (int i = 0; i < nacNetList.size(); i++) {
        UUID nacId = handler.createNac(ruleId);
        nacNetMap.put(nacId, nacNetList.get(i));
      }

      // maps used to create arcs between nodes by their id
      HashMap<String, INode> addedNodesInL = new HashMap<String, INode>();
      HashMap<String, INode> addedNodesInK = new HashMap<String, INode>();
      HashMap<String, INode> addedNodesInR = new HashMap<String, INode>();

      ITransformation iTransformation =
        TransformationComponent.getTransformation();

      // ################
      // add places
      // >

      // places to add in L
      Set<Place> kOr_Places = new HashSet<Place>(kPlaceSet);
      kOr_Places.removeAll(rPlaceSet);
      for (Place place : kOr_Places) {
        petrinet.model.Place lPlace =
          handler.createPlace(ruleId, RuleNet.L,
            positionToPoint2D(place.getGraphics().getPosition()));

        setPlaceAttributes(handler, ruleId, place, lPlace);

        List<INode> mappings = iTransformation.getMappings(ruleId, lPlace);
        addedNodesInL.put(place.getId(), lPlace);
        addedNodesInK.put(place.getId(), mappings.get(0));
      }
      // places to add in L

      // places to add in K
      Set<Place> lSr_Places = new HashSet<Place>(lPlaceSet);
      lSr_Places.retainAll(rPlaces);

      for (Place place : lSr_Places) {
        petrinet.model.Place kPlace =
          handler.createPlace(ruleId, RuleNet.K,
            positionToPoint2D(place.getGraphics().getPosition()));

        setPlaceAttributes(handler, ruleId, place, kPlace);

        List<INode> mappings = iTransformation.getMappings(ruleId, kPlace);
        addedNodesInK.put(place.getId(), kPlace);
        addedNodesInL.put(place.getId(), mappings.get(0));
        addedNodesInR.put(place.getId(), mappings.get(2));
      }
      // places to add in K

      // places to add in R
      Set<Place> kOl_Places = new HashSet<Place>(kPlaceSet);
      kOl_Places.removeAll(lPlaceSet);
      for (Place place : kOl_Places) {
        petrinet.model.Place rPlace =
          handler.createPlace(ruleId, RuleNet.R,
            positionToPoint2D(place.getGraphics().getPosition()));

        setPlaceAttributes(handler, ruleId, place, rPlace);

        List<INode> mappings = iTransformation.getMappings(ruleId, rPlace);
        addedNodesInR.put(place.getId(), rPlace);
        addedNodesInK.put(place.getId(), mappings.get(1));
      }
      // places to add in R

      // >
      // add places
      // ################

      // ################
      // add transitions
      // >

      // transitions to add in L
      Set<Transition> kOr_Transitions =
        new HashSet<Transition>(kTransitionSet);
      kOr_Transitions.removeAll(rTransitionSet);
      for (Transition transition : kOr_Transitions) {
        petrinet.model.Transition lTransition =
          handler.createTransition(ruleId, RuleNet.L,
            positionToPoint2D(transition.getGraphics().getPosition()));

        setTransitionAttributes(handler, ruleId, transition, lTransition);

        List<INode> mappings =
          iTransformation.getMappings(ruleId, lTransition);
        addedNodesInL.put(transition.getId(), lTransition);
        addedNodesInK.put(transition.getId(), mappings.get(1));
      }
      // transitions to add in L

      // transitions to add in K
      Set<Transition> lSr_Transitions =
        new HashSet<Transition>(lTransitionSet);
      lSr_Transitions.retainAll(rTransitionSet);

      for (Transition transition : lSr_Transitions) {
        petrinet.model.Transition kTransition =
          handler.createTransition(ruleId, RuleNet.K,
            positionToPoint2D(transition.getGraphics().getPosition()));

        setTransitionAttributes(handler, ruleId, transition, kTransition);

        List<INode> mappings =
          iTransformation.getMappings(ruleId, kTransition);
        addedNodesInK.put(transition.getId(), kTransition);
        addedNodesInL.put(transition.getId(), mappings.get(0));
        addedNodesInR.put(transition.getId(), mappings.get(2));
      }
      // transitions to add in K

      // transitions to add in R
      Set<Transition> kOl_Transitions =
        new HashSet<Transition>(kTransitionSet);
      kOl_Transitions.removeAll(lTransitionSet);
      for (Transition transition : kOl_Transitions) {
        petrinet.model.Transition rTransition =
          handler.createTransition(ruleId, RuleNet.R,
            positionToPoint2D(transition.getGraphics().getPosition()));

        setTransitionAttributes(handler, ruleId, transition, rTransition);

        List<INode> mappings =
          iTransformation.getMappings(ruleId, rTransition);
        addedNodesInR.put(transition.getId(), rTransition);
        addedNodesInK.put(transition.getId(), mappings.get(1));
      }
      // transitions to add in R

      // >
      // add transitions
      // ################

      // System.out.println("addedNodesInL " + addedNodesInL.size());
      // System.out.println("addedNodesInK " + addedNodesInK.size());
      // System.out.println("addedNodesInR " + addedNodesInR.size());

      // ################
      // add arcs
      // >

      // arcs to add in L
      Set<Arc> kOr_Arcs = new HashSet<Arc>(kArcSet);
      kOr_Arcs.removeAll(rArcSet);

      for (Arc arc : kOr_Arcs) {

        INode source = addedNodesInL.get(arc.getSource());
        INode target = addedNodesInL.get(arc.getTarget());

        if (source instanceof petrinet.model.Place) {
          // if source is a place -> PreArc
          petrinet.model.PreArc createdArc =
            handler.createPreArc(ruleId, RuleNet.L,
              (petrinet.model.Place) source,
              (petrinet.model.Transition) target);
          setPreArcAttributes(handler, ruleId, arc, createdArc);
        } else {
          // if source is a place -> PreArc
          petrinet.model.PostArc createdArc =
            handler.createPostArc(ruleId, RuleNet.L,
              (petrinet.model.Transition) source,
              (petrinet.model.Place) target);
          setPostArcAttributes(handler, ruleId, arc, createdArc);
        }
      }
      // arcs to add in L

      // arcs to add in K
      Set<Arc> lSr_Arcs = new HashSet<Arc>(lArcSet);
      lSr_Arcs.retainAll(rArcSet);

      for (Arc arc : lSr_Arcs) {

        INode source = addedNodesInK.get(arc.getSource());
        INode target = addedNodesInK.get(arc.getTarget());

        if (source instanceof petrinet.model.Place) {
          // if source is a place -> PreArc
          petrinet.model.PreArc createdArc =
            handler.createPreArc(ruleId, RuleNet.K,
              (petrinet.model.Place) source,
              (petrinet.model.Transition) target);
          setPreArcAttributes(handler, ruleId, arc, createdArc);

        } else {
          // if source is a transition -> PostArc
          petrinet.model.PostArc createdArc =
            handler.createPostArc(ruleId, RuleNet.K,
              (petrinet.model.Transition) source,
              (petrinet.model.Place) target);
          setPostArcAttributes(handler, ruleId, arc, createdArc);
        }
      }
      // arcs to add in K

      // arcs to add in R
      Set<Arc> kOl_Arcs = new HashSet<Arc>(kArcSet);
      kOl_Arcs.removeAll(lArcSet);

      for (Arc arc : kOl_Arcs) {

        INode source = addedNodesInR.get(arc.getSource());
        INode target = addedNodesInR.get(arc.getTarget());

        if (source instanceof petrinet.model.Place) {
          // if source is a place -> PreArc
          petrinet.model.PreArc createdArc =
            handler.createPreArc(ruleId, RuleNet.R,
              (petrinet.model.Place) source,
              (petrinet.model.Transition) target);
          setPreArcAttributes(handler, ruleId, arc, createdArc);
        } else {
          // if source is a place -> PreArc
          petrinet.model.PostArc createdArc =
            handler.createPostArc(ruleId, RuleNet.R,
              (petrinet.model.Transition) source,
              (petrinet.model.Place) target);
          setPostArcAttributes(handler, ruleId, arc, createdArc);
        }
      }
      // arcs to add in R

      // >
      // add arcs
      // ################

      HashMap<String, INode> addedNodesExplicitlyInNACs =
        new HashMap<String, INode>();

      for (Entry<UUID, Net> nac : nacNetMap.entrySet()) {

        Set<Place> nacPlaceSet =
          new HashSet<Place>(nac.getValue().getPage().getPlace());
        Set<Transition> nacTransitionSet =
          new HashSet<Transition>(nac.getValue().getPage().getTransition());
        Set<Arc> nacArcSet =
          new HashSet<Arc>(nac.getValue().getPage().getArc());

        // ################
        // add places
        // >

        // places to add in NAC
        Set<Place> nacOl_Places = new HashSet<Place>(nacPlaceSet);
        nacOl_Places.removeAll(lPlaceSet);
        for (Place place : nacOl_Places) {

          petrinet.model.Place nacPlace =
            handler.createPlace(ruleId, nac.getKey(),
              positionToPoint2D(place.getGraphics().getPosition()));

          setPlaceAttributes(handler, ruleId, nac.getKey(), place, nacPlace);

          addedNodesExplicitlyInNACs.put(place.getId(), nacPlace);
        }
        // places to add in NAC

        // >
        // add places
        // ################

        // ################
        // add transitions
        // >

        // transitions to add in NAC
        Set<Transition> nacOl_Transitions =
          new HashSet<Transition>(nacTransitionSet);
        nacOl_Transitions.removeAll(lTransitionSet);
        for (Transition transition : nacOl_Transitions) {

          petrinet.model.Transition nacTransition =
            handler.createTransition(ruleId, nac.getKey(),
              positionToPoint2D(transition.getGraphics().getPosition()));

          setTransitionAttributes(handler, ruleId, nac.getKey(), transition,
            nacTransition);

          addedNodesExplicitlyInNACs.put(transition.getId(), nacTransition);
        }
        // transitions to add in NAC

        // >
        // add transitions
        // ################

        // ################
        // add arcs
        // >

        // arcs to add in NAC
        Set<Arc> nacOl_Arcs = new HashSet<Arc>(nacArcSet);
        nacOl_Arcs.removeAll(lArcSet);

        for (Arc arc : nacOl_Arcs) {

          INode source = addedNodesExplicitlyInNACs.get(arc.getSource());
          INode target = addedNodesExplicitlyInNACs.get(arc.getTarget());

          // Note: if source or target is null, than it is not a NAC explicit
          // node. Therefore it must be in L. So find nacNode via
          // addedLNode(id) -> lNode -> nacNode

          // System.out.println("load arc - BEFORE: " + source + " -> " +
          // target);

          if (source == null) {
            source =
              iTransformation.getNacNodeOfNodeInL(ruleId, nac.getKey(),
                addedNodesInL.get(arc.getSource()));
          }
          if (target == null) {
            target =
              iTransformation.getNacNodeOfNodeInL(ruleId, nac.getKey(),
                addedNodesInL.get(arc.getTarget()));
          }

          // System.out.println("load arc - AFTER: " + source + " -> " +
          // target);

          if (source instanceof petrinet.model.Place) {
            // if source is a place -> PreArc
            petrinet.model.PreArc createdArc =
              handler.createPreArc(ruleId, nac.getKey(),
                (petrinet.model.Place) source,
                (petrinet.model.Transition) target);

            setArcAttributes(handler, ruleId, nac.getKey(), arc, createdArc);

          } else {
            // if source is a place -> PreArc
            petrinet.model.PostArc createdArc =
              handler.createPostArc(ruleId, nac.getKey(),
                (petrinet.model.Transition) source,
                (petrinet.model.Place) target);

            setArcAttributes(handler, ruleId, nac.getKey(), arc, createdArc);
          }
        }
        // arcs to add in NAC

        // >
        // add arcs
        // ################

      }

    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

    return ruleId;
  }

  /**
   * Similar to
   * {@link Converter#addPlaceToPnml(Map, List, petrinet.model.Place)}
   *
   * @param places
   * @param transitions
   * @param pnmlArcs
   * @param logicalArc
   */
  private static void addArcToPnml(List<Place> places,
    List<Transition> transitions, List<Arc> pnmlArcs,
    petrinet.model.IArc logicalArc) {

    // Arc and ID
    Arc pnmlArc = new Arc();
    pnmlArc.setId(String.valueOf(logicalArc.getId()));

    // End of Arc -- Place
    String arcEnd = null;
    String logicalArcID = "";
    for (Place place : places) {
      logicalArcID = String.valueOf(logicalArc.getTarget().getId());
      if (place.getId().equals(logicalArcID)) {
        arcEnd = place.getId();
        break;
      }
    }

    // End of Arc -- Transition
    if (arcEnd == null) {
      for (Transition t : transitions) {
        if (t.getId().equals(String.valueOf(logicalArc.getTarget().getId()))) {
          arcEnd = t.getId();
          break;
        }
      }
    }
    pnmlArc.setTarget(arcEnd);

    // Start of Arc -- Transition
    String arcStart = null;
    for (Place p : places) {
      if (p.getId().equals(String.valueOf(logicalArc.getSource().getId()))) {
        arcStart = p.getId();
        break;
      }
    }

    // Start of Arc -- Place
    if (arcStart == null) {
      for (Transition t : transitions) {
        if (t.getId().equals(String.valueOf(logicalArc.getSource().getId()))) {
          arcStart = t.getId();
          break;
        }
      }
    }
    pnmlArc.setSource(arcStart);

    // Text
    Inscription inscription = new Inscription();
    inscription.setText(logicalArc.getName());
    pnmlArc.setInscription(inscription);

    Weight weight = new Weight();
    weight.setText(String.valueOf(logicalArc.getWeight()));

    Toolspecific toolspecific = new Toolspecific();
    toolspecific.setTool("ReConNet");
    toolspecific.setVersion("1.0");
    toolspecific.setWeight(weight);
    pnmlArc.setToolspecific(toolspecific);

    // Add to List
    pnmlArcs.add(pnmlArc);
  }

  /**
   * Similar to Converter#addPlaceToPnml(Map, List, petrinet.Place)
   *
   * @param layout
   * @param transitionsList
   * @param logicalTransition
   */
  private static void addTransitionToPnml(Map<String, String[]> layout,
    List<Transition> transitionsList,
    petrinet.model.Transition logicalTransition) {

    // Transition and ID
    Transition pnmlTransition = new Transition();
    pnmlTransition.setId(String.valueOf(logicalTransition.getId()));

    // Name
    TransitionName name = new TransitionName();
    name.setText(logicalTransition.getName());
    pnmlTransition.setTransitionName(name);

    // Graphics -- Position
    Graphics graphics = new Graphics();
    Position position = new Position();
    position.setX(layout.get(pnmlTransition.getId())[0]);
    position.setY(layout.get(pnmlTransition.getId())[1]);
    List<Position> positionsList = new ArrayList<Position>();
    positionsList.add(position);
    graphics.setPosition(positionsList);

    pnmlTransition.setGraphics(graphics);

    // Label
    TransitionLabel label = new TransitionLabel();
    label.setText(logicalTransition.getTlb());
    pnmlTransition.setTransitionLabel(label);

    // Renew
    TransitionRenew rnw = new TransitionRenew();
    rnw.setText(logicalTransition.getRnw().toGUIString());
    pnmlTransition.setTransitionRenew(rnw);

    // Add to list
    transitionsList.add(pnmlTransition);
  }

  /**
   * Adds a {@link petrinet.model.Place} into the <code>places</code> List
   * using the given <code>layout</code>
   *
   * @param layout
   * @param places
   * @param place
   */
  private static void addPlaceToPnml(Map<String, String[]> layout,
    List<Place> places, petrinet.model.Place place) {

    // Place and ID
    Place newPlace = new Place();
    newPlace.setId(String.valueOf(place.getId()));

    // Name
    PlaceName placeName = new PlaceName();
    placeName.setText(place.getName());
    newPlace.setPlaceName(placeName);

    // Marking
    InitialMarking initm = new InitialMarking();
    initm.setText(String.valueOf(place.getMark()));
    newPlace.setInitialMarking(initm);

    // Capacity
    InitialCapacity initc = new InitialCapacity();
    initc.setText(String.valueOf(place.getCapacity()));
    newPlace.setInitialCapacity(initc);

    // Graphics
    Graphics graphics = new Graphics();

    // CHECKSTYLE:OFF - No need to check for magic numbers
    // Graphics -- Color
    Color c = new Color();
    c.setR(layout.get(newPlace.getId())[2]);
    c.setG(layout.get(newPlace.getId())[3]);
    c.setB(layout.get(newPlace.getId())[4]);
    graphics.setColor(c);
    // CHECKSTYLE:ON

    // Graphics -- Position
    List<Position> positionList = new ArrayList<Position>();
    Position position = new Position();
    position.setX(layout.get(newPlace.getId())[0]);
    position.setY(layout.get(newPlace.getId())[1]);
    positionList.add(position);

    graphics.setPosition(positionList);
    newPlace.setGraphics(graphics);

    // Add to List
    places.add(newPlace);
  }

  /**
   * Converts the position as a List of Strings into a {@link Point2D.Double}
   * object like specified in
   * {@link Converter#convertPetrinetToPnml(Petrinet, Map, double)}
   *
   * @param pos
   * @throws NullPointerException
   *         if the any string is null
   * @throws NumberFormatException
   *         if any string does not contain a parsable double.
   * @return
   */
  private static Point2D positionToPoint2D(List<Position> pos) {

    return new Point2D.Double(Double.parseDouble(pos.get(0).getX()),
      Double.parseDouble(pos.get(0).getY()));
  }

  private static void
  setPlaceAttributes(IRulePersistence handler, int ruleId,
    persistence.Place xmlPlace, petrinet.model.Place createdPlace)
      throws EngineException {

    handler.setPlaceColor(ruleId, createdPlace,
      xmlPlace.getGraphics().getColor().toAWTColor());
    handler.setPname(ruleId, createdPlace, xmlPlace.getPlaceName().getText());
    handler.setMarking(ruleId, createdPlace,
      Integer.valueOf(xmlPlace.getInitialMarking().getText()));
    if (xmlPlace.getInitialCapacity() != null) {
      handler.setCapacity(ruleId, createdPlace,
        Integer.valueOf(xmlPlace.getInitialCapacity().getText()));
    } else {
      handler.setCapacity(ruleId, createdPlace, Integer.MAX_VALUE);
    }

  }

  private static void setPlaceAttributes(IRulePersistence handler,
    int ruleId, UUID nacId, persistence.Place xmlPlace,
    petrinet.model.Place createdPlace)
      throws EngineException {

    handler.setPlaceColor(ruleId, nacId, createdPlace,
      xmlPlace.getGraphics().getColor().toAWTColor());
    handler.setPname(ruleId, nacId, createdPlace,
      xmlPlace.getPlaceName().getText());
    handler.setMarking(ruleId, nacId, createdPlace,
      Integer.valueOf(xmlPlace.getInitialMarking().getText()));
    if (xmlPlace.getInitialCapacity() != null) {
      handler.setCapacity(ruleId, nacId, createdPlace,
        Integer.valueOf(xmlPlace.getInitialCapacity().getText()));
    } else {
      handler.setCapacity(ruleId, nacId, createdPlace, Integer.MAX_VALUE);
    }
  }

  private static void setTransitionAttributes(IRulePersistence handler,
    int ruleId, persistence.Transition xmlTransition,
    petrinet.model.Transition createdTransition)
      throws EngineException {

    handler.setTlb(ruleId, createdTransition,
      xmlTransition.getTransitionLabel().getText());
    handler.setTname(ruleId, createdTransition,
      xmlTransition.getTransitionName().getText());
    handler.setRnw(ruleId, createdTransition,
      Renews.fromString(xmlTransition.getTransitionRenew().getText()));
  }

  private static void setTransitionAttributes(IRulePersistence handler,
    int ruleId, UUID nacId, persistence.Transition xmlTransition,
    petrinet.model.Transition createdTransition)
      throws EngineException {

    handler.setTlb(ruleId, nacId, createdTransition,
      xmlTransition.getTransitionLabel().getText());
    handler.setTname(ruleId, nacId, createdTransition,
      xmlTransition.getTransitionName().getText());
    handler.setRnw(ruleId, nacId, createdTransition,
      Renews.fromString(xmlTransition.getTransitionRenew().getText()));
  }

  private static void setPostArcAttributes(IRulePersistence handler,
    int ruleId, persistence.Arc xmlArc, petrinet.model.PostArc createdArc)
      throws EngineException {

    if (xmlArc.getToolspecific() != null) {

      int weight =
        Integer.valueOf(xmlArc.getToolspecific().getWeight().getText());
      handler.setWeight(ruleId, createdArc, weight);
    }
  }

  private static void setArcAttributes(IRulePersistence handler, int ruleId,
    UUID nacId, persistence.Arc xmlArc, petrinet.model.IArc createdArc)
      throws EngineException {

    if (xmlArc.getToolspecific() != null) {

      int weight =
        Integer.valueOf(xmlArc.getToolspecific().getWeight().getText());
      handler.setWeight(ruleId, nacId, createdArc, weight);
    }
  }

  private static void setPreArcAttributes(IRulePersistence handler,
    int ruleId, persistence.Arc xmlArc, petrinet.model.PreArc createdArc)
      throws EngineException {

    if (xmlArc.getToolspecific() != null) {

      int weight =
        Integer.valueOf(xmlArc.getToolspecific().getWeight().getText());
      handler.setWeight(ruleId, createdArc, weight);
    }
  }

  /**
   * Creates an {@link Net xml petrinet} as part of a rule. You can view this
   * as generating a sub tree
   *
   * @param petrinet
   * @param map
   * @param type
   * @param rule
   * @return
   */
  // CHECKSTYLE:OFF - Method lenght is greater then 150 lines
  private static Net createNet(Petrinet petrinet,
    Map<INode, NodeLayoutAttribute> map, RuleNet type, Rule rule) {

    // CHECKSTYLE:ON

    // Net, Page, ID and Type
    Net net = new Net();
    Page page = new Page();
    net.setId(String.valueOf(petrinet.getId()));
    net.setNettype(type.name());
    net.setPage(page);

    Set<petrinet.model.IArc> arcs = petrinet.getArcs();
    Set<petrinet.model.Place> places = petrinet.getPlaces();
    Set<petrinet.model.Transition> transis = petrinet.getTransitions();

    List<Arc> listArcs = new ArrayList<Arc>();
    List<Place> listPlace = new ArrayList<Place>();
    List<Transition> listTrans = new ArrayList<Transition>();

    try {
      // inserting places
      for (petrinet.model.Place place : places) {
        Place newPlace = new Place();

        // This "redirects" the variable place to the node in K in case
        // it is not already a node in K
        if (type != RuleNet.K) {

          INode correspondingNode;

          if (type == RuleNet.L) {
            correspondingNode = rule.fromLtoK(place);
          } else {
            correspondingNode = rule.fromRtoK(place);
          }

          if (correspondingNode != null) {
            place = (petrinet.model.Place) correspondingNode;
          }
        }
        // id
        newPlace.setId(String.valueOf(place.getId()));

        // Name
        PlaceName name = new PlaceName();
        name.setText(place.getName());
        newPlace.setPlaceName(name);

        // Graphics -- Color
        Graphics graphics = new Graphics();
        Color color = new Color();
        color.setR(String.valueOf(map.get(place).getColor().getRed()));
        color.setG(String.valueOf(map.get(place).getColor().getGreen()));
        color.setB(String.valueOf(map.get(place).getColor().getBlue()));
        graphics.setColor(color);

        // Graphics -- Position
        Position position = new Position();
        position.setX(String.valueOf(map.get(place).getCoordinate().getX()));
        position.setY(String.valueOf(map.get(place).getCoordinate().getY()));
        List<Position> positions = new ArrayList<Position>();
        positions.add(position);
        graphics.setPosition(positions);

        newPlace.setGraphics(graphics);

        // Marking
        InitialMarking initM = new InitialMarking();
        initM.setText(String.valueOf(place.getMark()));

        newPlace.setInitialMarking(initM);

        // Capacity
        InitialCapacity initC = new InitialCapacity();
        initC.setText(String.valueOf(place.getCapacity()));

        newPlace.setInitialCapacity(initC);

        // Add to List
        listPlace.add(newPlace);
      }

      // inserting Transitions
      for (petrinet.model.Transition logicalTransition : transis) {
        Transition xmlTransition = new Transition();

        // This "redirects" the variable place to the node in K in case
        // it is not already a node in K
        if (type != RuleNet.K) {

          INode correspondingNode;

          if (type == RuleNet.L) {
            correspondingNode = rule.fromLtoK(logicalTransition);
          } else {
            correspondingNode = rule.fromRtoK(logicalTransition);
          }

          if (correspondingNode != null) {
            logicalTransition = (petrinet.model.Transition) correspondingNode;
          }
        }

        // ID
        xmlTransition.setId(String.valueOf(logicalTransition.getId()));

        // Name
        TransitionName name = new TransitionName();
        name.setText(logicalTransition.getName());
        xmlTransition.setTransitionName(name);

        // Graphics -- Position
        Graphics graphics = new Graphics();
        List<Position> positions = new ArrayList<Position>();
        Position position = new Position();
        double x = map.get(logicalTransition).getCoordinate().getX();
        double y = map.get(logicalTransition).getCoordinate().getY();
        position.setX(String.valueOf(x));
        position.setY(String.valueOf(y));
        positions.add(position);
        graphics.setPosition(positions);
        xmlTransition.setGraphics(graphics);

        // Label
        TransitionLabel label = new TransitionLabel();
        label.setText(logicalTransition.getTlb());
        xmlTransition.setTransitionLabel(label);

        // Renew
        TransitionRenew rnw = new TransitionRenew();
        rnw.setText(logicalTransition.getRnw().toGUIString());
        xmlTransition.setTransitionRenew(rnw);

        // Add to List
        listTrans.add(xmlTransition);
      }

      // inserting arcs
      for (petrinet.model.IArc a : arcs) {

        Arc arc = new Arc();

        // This "redirects" the variable place to the node in K in case
        // it is not already a node in K
        if (type != RuleNet.K) {
          petrinet.model.IArc correspondingNode;

          if (a instanceof petrinet.model.PreArc) {
            if (type == RuleNet.L) {
              correspondingNode = rule.fromLtoK((petrinet.model.PreArc) a);
            } else {
              correspondingNode = rule.fromRtoK((petrinet.model.PreArc) a);

            }

          } else {

            if (type == RuleNet.L) {
              correspondingNode = rule.fromLtoK((petrinet.model.PostArc) a);
            } else {
              correspondingNode = rule.fromRtoK((petrinet.model.PostArc) a);
            }

          }

          if (correspondingNode != null) {
            a = correspondingNode;
          }
        }

        // ID
        arc.setId(String.valueOf(a.getId()));

        // Graphics -- Position
        Graphics graphics = new Graphics();
        Position position = new Position();

        List<Position> positions = new ArrayList<Position>();
        positions.add(position);
        graphics.setPosition(positions);

        arc.setGraphics(graphics);

        // Text
        Inscription inscription = new Inscription();
        inscription.setText(a.getName());
        arc.setInscription(inscription);

        Weight weight = new Weight();
        weight.setText(String.valueOf(a.getWeight()));

        Toolspecific toolspecific = new Toolspecific();
        toolspecific.setTool("ReConNet");
        toolspecific.setVersion("1.0");
        toolspecific.setWeight(weight);
        arc.setToolspecific(toolspecific);

        // Source and Target
        arc.setSource(String.valueOf(a.getSource().getId()));
        arc.setTarget(String.valueOf(a.getTarget().getId()));

        // Add to List
        listArcs.add(arc);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    page.setArc(listArcs);
    page.setPlace(listPlace);
    page.setTransition(listTrans);

    return net;
  }

  /**
   * Creates an {@link Net xml petrinet} as part of a rule. You can view this
   * as generating a sub tree
   *
   * @param petrinet
   * @param map
   * @param type
   * @param rule
   * @return
   */
  // CHECKSTYLE:OFF - Method lenght is greater then 150 lines
  private static Net createNetFromNac(NAC nac,
    Map<INode, NodeLayoutAttribute> map, RuleNet type, Rule rule) {

    // CHECKSTYLE:ON

    Petrinet petrinet = nac.getNac();

    // Net, Page, ID and Type
    Net net = new Net();
    Page page = new Page();
    net.setId(String.valueOf(petrinet.getId()));
    net.setNettype(type.name());
    net.setPage(page);

    Set<petrinet.model.IArc> arcs = petrinet.getArcs();
    Set<petrinet.model.Place> places = petrinet.getPlaces();
    Set<petrinet.model.Transition> transitions = petrinet.getTransitions();

    List<Arc> listArcs = new ArrayList<Arc>();
    List<Place> listPlace = new ArrayList<Place>();
    List<Transition> listTrans = new ArrayList<Transition>();

    try {
      // inserting places
      for (petrinet.model.Place place : places) {
        Place newPlace = new Place();

        // This "redirects" the variable place to the node in K
        petrinet.model.Place nodeInL = nac.fromNacToL(place);
        INode correspondingNode = rule.fromLtoK(nodeInL);

        if (correspondingNode != null) {
          place = (petrinet.model.Place) correspondingNode;
        }

        // id
        newPlace.setId(String.valueOf(place.getId()));

        // Name
        PlaceName name = new PlaceName();
        name.setText(place.getName());
        newPlace.setPlaceName(name);

        // Graphics -- Color
        Graphics graphics = new Graphics();
        Color color = new Color();
        color.setR(String.valueOf(map.get(place).getColor().getRed()));
        color.setG(String.valueOf(map.get(place).getColor().getGreen()));
        color.setB(String.valueOf(map.get(place).getColor().getBlue()));
        graphics.setColor(color);

        // Graphics -- Position
        Position position = new Position();
        position.setX(String.valueOf(map.get(place).getCoordinate().getX()));
        position.setY(String.valueOf(map.get(place).getCoordinate().getY()));
        List<Position> positions = new ArrayList<Position>();
        positions.add(position);
        graphics.setPosition(positions);

        newPlace.setGraphics(graphics);

        // Marking
        InitialMarking initM = new InitialMarking();
        initM.setText(String.valueOf(place.getMark()));

        newPlace.setInitialMarking(initM);

        // Capacity
        InitialCapacity initC = new InitialCapacity();
        initC.setText(String.valueOf(place.getCapacity()));

        newPlace.setInitialCapacity(initC);

        // Add to List
        listPlace.add(newPlace);
      }

      // inserting Transitions
      for (petrinet.model.Transition transition : transitions) {
        Transition xmlTransition = new Transition();

        // This "redirects" the variable transition to the node in K
        petrinet.model.Transition nodeInL = nac.fromNacToL(transition);
        INode correspondingNode = rule.fromLtoK(nodeInL);

        if (correspondingNode != null) {
          transition = (petrinet.model.Transition) correspondingNode;
        }

        // ID
        xmlTransition.setId(String.valueOf(transition.getId()));

        // Name
        TransitionName name = new TransitionName();
        name.setText(transition.getName());
        xmlTransition.setTransitionName(name);

        // Graphics -- Position
        Graphics graphics = new Graphics();
        List<Position> positions = new ArrayList<Position>();
        Position position = new Position();
        double x = map.get(transition).getCoordinate().getX();
        double y = map.get(transition).getCoordinate().getY();
        position.setX(String.valueOf(x));
        position.setY(String.valueOf(y));
        positions.add(position);
        graphics.setPosition(positions);
        xmlTransition.setGraphics(graphics);

        // Label
        TransitionLabel label = new TransitionLabel();
        label.setText(transition.getTlb());
        xmlTransition.setTransitionLabel(label);

        // Renew
        TransitionRenew rnw = new TransitionRenew();
        rnw.setText(transition.getRnw().toGUIString());
        xmlTransition.setTransitionRenew(rnw);

        // Add to List
        listTrans.add(xmlTransition);
      }

      // inserting arcs
      for (petrinet.model.IArc arc : arcs) {

        Arc xmlArc = new Arc();

        // This "redirects" the variable transition to the node in K
        petrinet.model.IArc correspondingArc = null;

        if (arc instanceof petrinet.model.PreArc) {

          petrinet.model.PreArc arcInL =
            nac.fromNacToL((petrinet.model.PreArc) arc);
          correspondingArc = rule.fromLtoK(arcInL);

        } else {

          petrinet.model.PostArc arcInL =
            nac.fromNacToL((petrinet.model.PostArc) arc);
          correspondingArc = rule.fromLtoK(arcInL);
        }

        if (correspondingArc != null) {
          arc = correspondingArc;
        }

        // ID
        xmlArc.setId(String.valueOf(arc.getId()));

        // Graphics -- Position
        Graphics graphics = new Graphics();
        Position position = new Position();

        List<Position> positions = new ArrayList<Position>();
        positions.add(position);
        graphics.setPosition(positions);

        xmlArc.setGraphics(graphics);

        // Text
        Inscription inscription = new Inscription();
        inscription.setText(arc.getName());
        xmlArc.setInscription(inscription);

        Weight weight = new Weight();
        weight.setText(String.valueOf(arc.getWeight()));

        Toolspecific toolspecific = new Toolspecific();
        toolspecific.setTool("ReConNet");
        toolspecific.setVersion("1.0");
        toolspecific.setWeight(weight);
        xmlArc.setToolspecific(toolspecific);

        // Source and Target
        xmlArc.setSource(String.valueOf(arc.getSource().getId()));
        xmlArc.setTarget(String.valueOf(arc.getTarget().getId()));

        // Override NodeIDs if nodes come from L/K (IDs taken from nodes in K)
        if (arc instanceof petrinet.model.PreArc) {

          petrinet.model.Place lSource =
            nac.fromNacToL((petrinet.model.Place) arc.getSource());
          petrinet.model.Place kSource = rule.fromLtoK(lSource);

          petrinet.model.Transition lTarget =
            nac.fromNacToL((petrinet.model.Transition) arc.getTarget());
          petrinet.model.Transition kTarget = rule.fromLtoK(lTarget);

          if (kSource != null) {
            xmlArc.setSource(String.valueOf(kSource.getId()));
          }
          if (kTarget != null) {
            xmlArc.setTarget(String.valueOf(kTarget.getId()));
          }

        } else if (arc instanceof petrinet.model.PostArc) {

          petrinet.model.Transition lSource =
            nac.fromNacToL((petrinet.model.Transition) arc.getSource());
          petrinet.model.Transition kSource = rule.fromLtoK(lSource);

          petrinet.model.Place lTarget =
            nac.fromNacToL((petrinet.model.Place) arc.getTarget());
          petrinet.model.Place kTarget = rule.fromLtoK(lTarget);

          if (kSource != null) {
            xmlArc.setSource(String.valueOf(kSource.getId()));
          }
          if (kTarget != null) {
            xmlArc.setTarget(String.valueOf(kTarget.getId()));
          }
        }

        // Add to List
        listArcs.add(xmlArc);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    page.setArc(listArcs);
    page.setPlace(listPlace);
    page.setTransition(listTrans);

    return net;
  }
}
