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

package persistence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

  private static final int L_INDEX = 0;
  private static final int K_INDEX = 1;
  private static final int R_INDEX = 2;
  private static final int NAC_INDEX = 3;

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
        "Die ausgewählte Datei enthält eine Regel, kein Petrinetz");
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
   * Converts a {@link Pnml} object that was unmarshalled from an XMLFile into
   * a {@link Rule}
   *
   * @param pnml
   *        The object tree, representing the XML file
   * @param handler
   *        The engine handler to create and modify the rule.
   * @return id of petrinet Id of the created petrinet
   */
  public static int convertPnmlToRule(Pnml pnml, IRulePersistence handler) {

    // In each XML file there is the type attribute for the pnml node to
    // quick-check if its a rule or a petrinet
    if (!pnml.getType().equals(RULE_IDENT)) {
      throw new ShowAsWarningException(
        "Die ausgewählte Datei enthält ein Petrinetz, keine Regel");
    }
    int id = handler.createRule();

    try {
      handler.setNodeSize(id, pnml.getNodeSize());

      Net lNet = pnml.getNet().get(0);
      Net kNet = pnml.getNet().get(1);
      Net rNet = pnml.getNet().get(2);

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

      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInL = new HashMap<String, INode>();
      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInK = new HashMap<String, INode>();
      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInR = new HashMap<String, INode>();

      addPlacesToRule(id, lPlaces, kPlaces, rPlaces, handler, idToINodeInL,
        idToINodeInK, idToINodeInR);
      addTransitionsToRule(id, lTransis, kTransis, rTransis, handler,
        idToINodeInL, idToINodeInK, idToINodeInR);
      fillMapsWithMissingMappings(id, idToINodeInL, idToINodeInK,
        idToINodeInR);
      addArcsToTule(id, lArcs, kArcs, rArcs, handler, idToINodeInL,
        idToINodeInK, idToINodeInR);
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

    return id;
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
  @Deprecated
  public static int convertPnmlToRuleWithNac(Pnml pnml,
    IRulePersistence handler) {

    // In each XML file there is the type attribute for the pnml node to
    // quick-check if its a rule or a petrinet
    if (!pnml.getType().equals(RULE_IDENT)) {
      throw new ShowAsWarningException(
        "Die ausgewählte Datei enthält ein Petrinetz, keine Regel");
    }
    int id = handler.createRule();

    try {
      handler.setNodeSize(id, pnml.getNodeSize());

      Net lNet = pnml.getNet().get(L_INDEX);
      Net kNet = pnml.getNet().get(K_INDEX);
      Net rNet = pnml.getNet().get(R_INDEX);
      Net nacNet = pnml.getNet().get(NAC_INDEX);

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

      // Elements of NAC
      List<Place> nacPlaces = nacNet.getPage().getPlace();
      List<Transition> nacTransis = nacNet.getPage().getTransition();
      List<Arc> nacArcs = nacNet.getPage().getArc();

      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInL = new HashMap<String, INode>();
      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInK = new HashMap<String, INode>();
      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInR = new HashMap<String, INode>();
      /** Contains the created INode object for each XML-id */
      Map<String, INode> idToINodeInNAC = new HashMap<String, INode>();

      addPlacesToRule(id, lPlaces, kPlaces, rPlaces, nacPlaces, handler,
        idToINodeInL, idToINodeInK, idToINodeInR, idToINodeInNAC);
      addTransitionsToRule(id, lTransis, kTransis, rTransis, nacTransis,
        handler, idToINodeInL, idToINodeInK, idToINodeInR, idToINodeInNAC);
      fillMapsWithMissingMappings(id, idToINodeInL, idToINodeInK,
        idToINodeInR, idToINodeInNAC);
      addArcsToTule(id, lArcs, kArcs, rArcs, nacArcs, handler, idToINodeInL,
        idToINodeInK, idToINodeInR, idToINodeInNAC);
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

    return id;
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
      // leere Nacs hinzufügen (Anzahl anhand PNML)
      // Regel aufbauen -> baut auch Teile von NACs auf
      // restlichen Elemente zu NACs hinzufügen

      // initialize NACs - must be done before L, K, and R will be build
      List<UUID> nacIds = new ArrayList<UUID>();
      for (int i = 0; i < nacNetList.size(); i++) {
        UUID nacId = handler.createNac(ruleId);
        nacIds.add(nacId);
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

        List<INode> mappings =
          iTransformation.getMappings(ruleId, rTransition);
        addedNodesInR.put(transition.getId(), rTransition);
        addedNodesInK.put(transition.getId(), mappings.get(1));
      }
      // transitions to add in R

      // >
      // add transitions
      // ################

      System.out.println("addedNodesInL " + addedNodesInL.size());
      System.out.println("addedNodesInK " + addedNodesInK.size());
      System.out.println("addedNodesInR " + addedNodesInR.size());

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
          handler.createPreArc(ruleId, RuleNet.L,
            (petrinet.model.Place) source, (petrinet.model.Transition) target);
        } else {
          // if source is a place -> PreArc

          handler.createPostArc(ruleId, RuleNet.L,
            (petrinet.model.Transition) source, (petrinet.model.Place) target);
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
          handler.createPreArc(ruleId, RuleNet.K,
            (petrinet.model.Place) source, (petrinet.model.Transition) target);

        } else {
          // if source is a transition -> PostArc
          handler.createPostArc(ruleId, RuleNet.K,
            (petrinet.model.Transition) source, (petrinet.model.Place) target);
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
          handler.createPreArc(ruleId, RuleNet.R,
            (petrinet.model.Place) source, (petrinet.model.Transition) target);
        } else {
          // if source is a place -> PreArc

          handler.createPostArc(ruleId, RuleNet.R,
            (petrinet.model.Transition) source, (petrinet.model.Place) target);
        }
      }
      // arcs to add in R

      // >
      // add arcs
      // ################

      for (UUID nacId : nacIds) {

      }

    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

    return ruleId;
  }

  /**
   * After Places and Rules have been added, there are only
   * String-id-to-INode-mappings for the original inserted nodes, but not for
   * the ones that were inserted automatically For beeing able to properly add
   * arcs to the rule, we need those mappings
   */
  private static void fillMapsWithMissingMappings(int ruleId,
    Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
    Map<String, INode> idToINodeInR) {

    ITransformation transformation =
      TransformationComponent.getTransformation();
    // fill with mappings of Ls nodes
    for (String xmlId : idToINodeInL.keySet()) {
      INode node = idToINodeInL.get(xmlId);
      List<INode> mappings = transformation.getMappings(ruleId, node);
      for (int i = 0; i < mappings.size(); i++) {
        INode respectiveNode = mappings.get(i);
        if (respectiveNode != null) {
          if (i == 0) {
            idToINodeInL.put(xmlId, respectiveNode);
          } else if (i == 1) {
            idToINodeInK.put(xmlId, respectiveNode);
          } else if (i == 2) {
            idToINodeInR.put(xmlId, respectiveNode);
          }
        }
      }
    }
    // fill with mappings of Ks nodes
    for (String xmlId : idToINodeInK.keySet()) {
      INode node = idToINodeInK.get(xmlId);
      List<INode> mappings = transformation.getMappings(ruleId, node);
      for (int i = 0; i < mappings.size(); i++) {
        INode respectiveNode = mappings.get(i);
        if (respectiveNode != null) {
          if (i == 0) {
            idToINodeInL.put(xmlId, respectiveNode);
          } else if (i == 1) {
            idToINodeInK.put(xmlId, respectiveNode);
          } else if (i == 2) {
            idToINodeInR.put(xmlId, respectiveNode);
          }
        }
      }
    }
    // fill with mappings of Rs nodes
    for (String xmlId : idToINodeInR.keySet()) {
      INode node = idToINodeInR.get(xmlId);
      List<INode> mappings = transformation.getMappings(ruleId, node);
      for (int i = 0; i < mappings.size(); i++) {
        INode respectiveNode = mappings.get(i);
        if (respectiveNode != null) {
          if (i == 0) {
            idToINodeInL.put(xmlId, respectiveNode);
          } else if (i == 1) {
            idToINodeInK.put(xmlId, respectiveNode);
          } else if (i == 2) {
            idToINodeInR.put(xmlId, respectiveNode);
          }
        }
      }
    }
  }

  /**
   * After Places and Rules have been added, there are only
   * String-id-to-INode-mappings for the original inserted nodes, but not for
   * the ones that were inserted automatically For beeing able to properly add
   * arcs to the rule, we need those mappings
   */
  private static void fillMapsWithMissingMappings(int ruleId,
    Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
    Map<String, INode> idToINodeInR, Map<String, INode> idToINodeInNAC) {

    ITransformation transformation =
      TransformationComponent.getTransformation();
    // fill with mappings of Ls nodes
    for (String xmlId : idToINodeInL.keySet()) {
      INode node = idToINodeInL.get(xmlId);
      List<INode> mappings = transformation.getMappings(ruleId, node);
      for (int i = 0; i < mappings.size(); i++) {
        INode respectiveNode = mappings.get(i);
        if (respectiveNode != null) {
          if (i == L_INDEX) {
            idToINodeInL.put(xmlId, respectiveNode);
          } else if (i == K_INDEX) {
            idToINodeInK.put(xmlId, respectiveNode);
          } else if (i == R_INDEX) {
            idToINodeInR.put(xmlId, respectiveNode);
          } else if (i == NAC_INDEX) {
            idToINodeInNAC.put(xmlId, respectiveNode);
          }
        }
      }
    }
    // fill with mappings of Ks nodes
    for (String xmlId : idToINodeInK.keySet()) {
      INode node = idToINodeInK.get(xmlId);
      List<INode> mappings = transformation.getMappings(ruleId, node);
      for (int i = 0; i < mappings.size(); i++) {
        INode respectiveNode = mappings.get(i);
        if (respectiveNode != null) {
          if (i == L_INDEX) {
            idToINodeInL.put(xmlId, respectiveNode);
          } else if (i == K_INDEX) {
            idToINodeInK.put(xmlId, respectiveNode);
          } else if (i == R_INDEX) {
            idToINodeInR.put(xmlId, respectiveNode);
          } else if (i == NAC_INDEX) {
            idToINodeInNAC.put(xmlId, respectiveNode);
          }
        }
      }
    }
    // fill with mappings of Rs nodes
    for (String xmlId : idToINodeInR.keySet()) {
      INode node = idToINodeInR.get(xmlId);
      List<INode> mappings = transformation.getMappings(ruleId, node);
      for (int i = 0; i < mappings.size(); i++) {
        INode respectiveNode = mappings.get(i);
        if (respectiveNode != null) {
          if (i == L_INDEX) {
            idToINodeInL.put(xmlId, respectiveNode);
          } else if (i == K_INDEX) {
            idToINodeInK.put(xmlId, respectiveNode);
          } else if (i == R_INDEX) {
            idToINodeInR.put(xmlId, respectiveNode);
          } else if (i == NAC_INDEX) {
            idToINodeInNAC.put(xmlId, respectiveNode);
          }
        }
      }
    }
  }

  /**
   * Adds all arcs from the XML to the rule object, using the engine handler
   *
   * @param idToINodeInR
   * @param idToINodeInK
   */
  private static void addArcsToTule(int id, List<Arc> lArcs, List<Arc> kArcs,
    List<Arc> rArcs, IRulePersistence handler,
    Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
    Map<String, INode> idToINodeInR)
      throws EngineException {

    /*
     * All arcs must be in K. To determine where the arcs must be added, we
     * have to find out: Are they also in L AND in K or just in one of them?
     * And if they are only in one of them - in which?
     */
    for (Arc arc : kArcs) {
      RuleNet toAddto;
      INode source;
      INode target;
      if (getIdsOfArcsList(lArcs).contains(arc.getId())) {
        if (getIdsOfArcsList(rArcs).contains(arc.getId())) {
          toAddto = RuleNet.K;
          source = idToINodeInK.get(arc.getSource());
          target = idToINodeInK.get(arc.getTarget());
        } else {
          toAddto = RuleNet.L;
          source = idToINodeInL.get(arc.getSource());
          target = idToINodeInL.get(arc.getTarget());
        }
      } else {
        toAddto = RuleNet.R;
        source = idToINodeInR.get(arc.getSource());
        target = idToINodeInR.get(arc.getTarget());
      }

      int weight = 1;

      if (arc.getToolspecific() != null) {
        weight = Integer.valueOf(arc.getToolspecific().getWeight().getText());
      }

      if (source instanceof petrinet.model.Place
        && target instanceof petrinet.model.Transition) {
        petrinet.model.PreArc preArc =
          handler.createPreArc(id, toAddto, (petrinet.model.Place) source,
            (petrinet.model.Transition) target);
        handler.setWeight(id, preArc, weight);
      } else {
        petrinet.model.PostArc postArc =
          handler.createPostArc(id, toAddto,
            (petrinet.model.Transition) source, (petrinet.model.Place) target);
        handler.setWeight(id, postArc, weight);
      }
    }

  }

  /**
   * Adds all arcs from the XML to the rule object, using the engine handler
   *
   * @param idToINodeInR
   * @param idToINodeInR
   * @param idToINodeInK
   * @param idToINodeInNAC
   */
  private static void addArcsToTule(int id, List<Arc> lArcs, List<Arc> kArcs,
    List<Arc> rArcs, List<Arc> nacArcs, IRulePersistence handler,
    Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
    Map<String, INode> idToINodeInR, Map<String, INode> idToINodeInNAC)
      throws EngineException {

    /*
     * All arcs must be in K. To determine where the arcs must be added, we
     * have to find out: Are they also in L AND in K or just in one of them?
     * And if they are only in one of them - in which?
     */
    for (Arc arc : kArcs) {
      RuleNet toAddto;
      INode source;
      INode target;
      if (getIdsOfArcsList(lArcs).contains(arc.getId())) {
        if (getIdsOfArcsList(rArcs).contains(arc.getId())) {
          toAddto = RuleNet.K;
          source = idToINodeInK.get(arc.getSource());
          target = idToINodeInK.get(arc.getTarget());
        } else {
          toAddto = RuleNet.L;
          source = idToINodeInL.get(arc.getSource());
          target = idToINodeInL.get(arc.getTarget());
        }
      } else if (getIdsOfArcsList(rArcs).contains(arc.getId())) {
        toAddto = RuleNet.R;
        source = idToINodeInR.get(arc.getSource());
        target = idToINodeInR.get(arc.getTarget());
      } else {
        toAddto = RuleNet.NAC;
        source = idToINodeInNAC.get(arc.getSource());
        target = idToINodeInNAC.get(arc.getTarget());
      }

      int weight = 1;

      if (arc.getToolspecific() != null) {
        weight = Integer.valueOf(arc.getToolspecific().getWeight().getText());
      }

      if (source instanceof petrinet.model.Place
        && target instanceof petrinet.model.Transition) {
        petrinet.model.PreArc preArc =
          handler.createPreArc(id, toAddto, (petrinet.model.Place) source,
            (petrinet.model.Transition) target);
        handler.setWeight(id, preArc, weight);
      } else {
        petrinet.model.PostArc postArc =
          handler.createPostArc(id, toAddto,
            (petrinet.model.Transition) source, (petrinet.model.Place) target);
        handler.setWeight(id, postArc, weight);
      }
    }

  }

  /**
   * Adds places to the rule, writing the mappings of created places into the
   * resprective maps (last 3 parameters)
   *
   * @param id
   * @param lPlaces
   * @param kPlaces
   * @param rPlaces
   * @param handler
   * @param idToINodeInL
   * @param idToINodeInK
   * @param idToINodeInR
   * @return
   * @throws EngineException
   */
  private static Map<String, INode> addPlacesToRule(int id,
    List<Place> lPlaces, List<Place> kPlaces, List<Place> rPlaces,
    IRulePersistence handler, Map<String, INode> idToINodeInL,
    Map<String, INode> idToINodeInK, Map<String, INode> idToINodeInR)
      throws EngineException {

    Map<String, INode> result = new HashMap<String, INode>();
    /*
     * All places must be in K. To determine where the places must be added,
     * we have to find out: Are they also in L AND in K or just in one of
     * them? And if they are only in one of them - in which?
     */
    for (Place place : kPlaces) {
      RuleNet toAddto;
      if (getIdsOfPlaceList(lPlaces).contains(place.getId())) {
        if (getIdsOfPlaceList(rPlaces).contains(place.getId())) {
          toAddto = RuleNet.K;
        } else {
          toAddto = RuleNet.L;
        }
      } else {
        toAddto = RuleNet.R;
      }
      petrinet.model.Place createdPlace =
        handler.createPlace(id, toAddto,
          positionToPoint2D(place.getGraphics().getPosition()));
      handler.setPlaceColor(id, createdPlace,
        place.getGraphics().getColor().toAWTColor());
      handler.setPname(id, createdPlace, place.getPlaceName().getText());
      handler.setMarking(id, createdPlace,
        Integer.valueOf(place.getInitialMarking().getText()));
      if (place.getInitialCapacity() != null) {
        handler.setCapacity(id, createdPlace,
          Integer.valueOf(place.getInitialCapacity().getText()));
      } else {
        handler.setCapacity(id, createdPlace, Integer.MAX_VALUE);
      }
      if (toAddto == RuleNet.L) {
        idToINodeInL.put(place.getId(), createdPlace);
      } else if (toAddto == RuleNet.K) {
        idToINodeInK.put(place.getId(), createdPlace);
      } else {
        idToINodeInR.put(place.getId(), createdPlace);
      }
    }
    return result;
  }

  /**
   * Adds places to the rule, writing the mappings of created places into the
   * resprective maps (last 3 parameters)
   *
   * @param id
   * @param lPlaces
   * @param kPlaces
   * @param rPlaces
   * @param nacPlaces
   * @param handler
   * @param idToINodeInL
   * @param idToINodeInK
   * @param idToINodeInR
   * @param idToINodeInNac
   * @return
   * @throws EngineException
   */
  private static Map<String, INode> addPlacesToRule(int id,
    List<Place> lPlaces, List<Place> kPlaces, List<Place> rPlaces,
    List<Place> nacPlaces, IRulePersistence handler,
    Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
    Map<String, INode> idToINodeInR, Map<String, INode> idToINodeInNAC)
      throws EngineException {

    Map<String, INode> result = new HashMap<String, INode>();
    /*
     * All places must be in K. To determine where the places must be added,
     * we have to find out: Are they also in L AND in K or just in one of
     * them? And if they are only in one of them - in which?
     */
    for (Place place : kPlaces) {
      RuleNet toAddto;
      if (getIdsOfPlaceList(lPlaces).contains(place.getId())) {
        if (getIdsOfPlaceList(rPlaces).contains(place.getId())) {
          toAddto = RuleNet.K;
        } else {
          toAddto = RuleNet.L;
        }
      } else if (getIdsOfPlaceList(rPlaces).contains(place.getId())) {
        toAddto = RuleNet.R;
      } else {
        toAddto = RuleNet.NAC;
      }

      petrinet.model.Place createdPlace =
        handler.createPlace(id, toAddto,
          positionToPoint2D(place.getGraphics().getPosition()));
      handler.setPlaceColor(id, createdPlace,
        place.getGraphics().getColor().toAWTColor());
      handler.setPname(id, createdPlace, place.getPlaceName().getText());
      handler.setMarking(id, createdPlace,
        Integer.valueOf(place.getInitialMarking().getText()));
      if (place.getInitialCapacity() != null) {
        handler.setCapacity(id, createdPlace,
          Integer.valueOf(place.getInitialCapacity().getText()));
      } else {
        handler.setCapacity(id, createdPlace, Integer.MAX_VALUE);
      }
      if (toAddto == RuleNet.L) {
        idToINodeInL.put(place.getId(), createdPlace);
      } else if (toAddto == RuleNet.K) {
        idToINodeInK.put(place.getId(), createdPlace);
      } else if (toAddto == RuleNet.R) {
        idToINodeInR.put(place.getId(), createdPlace);
      } else if (toAddto == RuleNet.NAC) {
        idToINodeInNAC.put(place.getId(), createdPlace);
      }
    }

    return result;
  }

  /**
   * Adds transitions to the rule, writing the mappings of created places into
   * the resprective maps (last 3 parameters)
   *
   * @param id
   * @param lPlaces
   * @param kPlaces
   * @param rPlaces
   * @param handler
   * @param idToINodeInL
   * @param idToINodeInK
   * @param idToINodeInR
   * @return
   * @throws EngineException
   */
  private static Map<String, INode> addTransitionsToRule(int id,
    List<Transition> lTransitions, List<Transition> kTransition,
    List<Transition> rTransition, IRulePersistence handler,
    Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
    Map<String, INode> idToINodeInR)
      throws EngineException {

    Map<String, INode> result = new HashMap<String, INode>();
    /*
     * All Transitions must be in K. To determine where the transitions must
     * be added, we have to find out: Are they also in L AND in K or just in
     * one of them? And if they are only in one of them - in which?
     */
    for (Transition transition : kTransition) {
      RuleNet toAddto;
      if (getIdsOfTransitionList(lTransitions).contains(transition.getId())) {
        if (getIdsOfTransitionList(rTransition).contains(transition.getId())) {
          toAddto = RuleNet.K;
        } else {
          toAddto = RuleNet.L;
        }
      } else {
        toAddto = RuleNet.R;
      }
      petrinet.model.Transition createdTransition =
        handler.createTransition(id, toAddto,
          positionToPoint2D(transition.getGraphics().getPosition()));
      handler.setTlb(id, createdTransition,
        transition.getTransitionLabel().getText());
      handler.setTname(id, createdTransition,
        transition.getTransitionName().getText());
      handler.setRnw(id, createdTransition,
        Renews.fromString(transition.getTransitionRenew().getText()));
      if (toAddto == RuleNet.L) {
        idToINodeInL.put(transition.getId(), createdTransition);
      } else if (toAddto == RuleNet.K) {
        idToINodeInK.put(transition.getId(), createdTransition);
      } else {
        idToINodeInR.put(transition.getId(), createdTransition);
      }
    }
    return result;
  }

  /**
   * Adds transitions to the rule, writing the mappings of created places into
   * the resprective maps (last 3 parameters)
   *
   * @param id
   * @param lPlaces
   * @param kPlaces
   * @param rPlaces
   * @param nacPlaces
   * @param handler
   * @param idToINodeInL
   * @param idToINodeInK
   * @param idToINodeInR
   * @param idToINodeInNAC
   * @return
   * @throws EngineException
   */
  private static Map<String, INode> addTransitionsToRule(int id,
    List<Transition> lTransitions, List<Transition> kTransition,
    List<Transition> rTransition, List<Transition> nacTransition,
    IRulePersistence handler, Map<String, INode> idToINodeInL,
    Map<String, INode> idToINodeInK, Map<String, INode> idToINodeInR,
    Map<String, INode> idToINodeInNAC)
      throws EngineException {

    Map<String, INode> result = new HashMap<String, INode>();
    /*
     * All Transitions must be in K. To determine where the transitions must
     * be added, we have to find out: Are they also in L AND in K or just in
     * one of them? And if they are only in one of them - in which?
     */
    for (Transition transition : kTransition) {
      RuleNet toAddto;
      if (getIdsOfTransitionList(lTransitions).contains(transition.getId())) {
        if (getIdsOfTransitionList(rTransition).contains(transition.getId())) {
          toAddto = RuleNet.K;
        } else {
          toAddto = RuleNet.L;
        }
      } else if (getIdsOfTransitionList(rTransition).contains(
        transition.getId())) {
        toAddto = RuleNet.R;
      } else {
        toAddto = RuleNet.NAC;
      }
      petrinet.model.Transition createdTransition =
        handler.createTransition(id, toAddto,
          positionToPoint2D(transition.getGraphics().getPosition()));
      handler.setTlb(id, createdTransition,
        transition.getTransitionLabel().getText());
      handler.setTname(id, createdTransition,
        transition.getTransitionName().getText());
      handler.setRnw(id, createdTransition,
        Renews.fromString(transition.getTransitionRenew().getText()));
      if (toAddto == RuleNet.L) {
        idToINodeInL.put(transition.getId(), createdTransition);
      } else if (toAddto == RuleNet.K) {
        idToINodeInK.put(transition.getId(), createdTransition);
      } else if (toAddto == RuleNet.R) {
        idToINodeInR.put(transition.getId(), createdTransition);
      } else if (toAddto == RuleNet.NAC) {
        idToINodeInNAC.put(transition.getId(), createdTransition);
      }
    }
    return result;
  }

  /**
   * Extracts the XML ids of {@link Place places} into a List of ids as String
   *
   * @param placeList
   * @return
   */
  private static List<String> getIdsOfPlaceList(List<Place> placeList) {

    List<String> ids = new LinkedList<String>();
    for (Place place : placeList) {
      ids.add(place.getId());
    }
    return ids;
  }

  /**
   * Extracts the XML ids of {@link Transition transitions} into a List of ids
   * as String
   *
   * @param placeList
   * @return
   */
  private static List<String> getIdsOfTransitionList(
    List<Transition> transitionList) {

    List<String> ids = new LinkedList<String>();
    for (Transition place : transitionList) {
      ids.add(place.getId());
    }
    return ids;
  }

  /**
   * Extracts the XML ids of {@link Arc arc} into a List of ids as String
   *
   * @param placeList
   * @return
   */
  private static List<String> getIdsOfArcsList(List<Arc> arcList) {

    List<String> ids = new LinkedList<String>();
    for (Arc arc : arcList) {
      ids.add(arc.getId());
    }
    return ids;
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
  public static Pnml convertRuleToPnml(Rule rule,
    Map<INode, NodeLayoutAttribute> map, double nodeSize) {

    Pnml pnml = new Pnml();

    pnml.setType(RULE_IDENT);
    pnml.setNodeSize(nodeSize);

    pnml.setNet(new ArrayList<Net>());
    final Net lNet = createNet(rule.getL(), map, RuleNet.L, rule);
    final Net kNet = createNet(rule.getK(), map, RuleNet.K, rule);
    final Net rNet = createNet(rule.getR(), map, RuleNet.R, rule);

    pnml.setNet(new ArrayList<Net>() {

      private static final long serialVersionUID = 8434245017694015611L;

      {
        add(lNet);
        add(kNet);
        add(rNet);
      }
    });

    return pnml;
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
