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

package gui;

import static gui.Style.FACTOR_SELECTED_NODE;
import static gui.Style.FONT_COLOR_BRIGHT;
import static gui.Style.FONT_COLOR_DARK;
import static gui.Style.NODE_BORDER_COLOR;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.UUID;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.AbstractEdgeShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.Line;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.QuadCurve;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import engine.attribute.ArcAttribute;
import engine.attribute.ColorGenerator;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import exceptions.EngineException;
import exceptions.ShowAsInfoException;
import exceptions.ShowAsWarningException;
import gui.EditorPane.EditorMode;

/**
 * A special jung visualization viewer for displaying and editing petrinets.
 * It also encapsulates the engine. This comes in handy when it is not
 * important whether a method should be called on
 * {@link IPetrinetManipulation} or {@link IRuleManipulation} (e.g. drawing a
 * node is the same for petrinets and rules)
 */
@SuppressWarnings("serial")
public class PetrinetViewer
  extends VisualizationViewer<INode, IArc> {

  /**
   * ID of currently displayed petrinet or rule. If RuleNet is set (L,K or R)
   * this is the rule Id. If RuleNet is not set (null) this id is the ID of
   * the petrinet
   */
  private int currentId = -1;

  // UUID of the NAC to be shown
  //
  private UUID nacId;

  /**
   * Returns the general node size. Do not use this for concrete nodes. Use
   * one of the following instead:
   *
   * @return
   */
  private double nodeSize = Style.NODE_SIZE_DEFAULT;

  /**
   * Defines whether the viewer displays a L, K or R net. Null if it displays
   * an N petrinet
   */
  private RuleNet ruleNet;

  /**
   * The node that is currently selected by user. <tt>null</tt> if no node is
   * selected
   */
  private INode currentSelectedNode = null;

  /**
   * The node that is currently selected by user. <tt>null</tt> if no node is
   * selected
   */
  private IArc currentSelectedArc = null;

  private static final int STRG_KEY = 17;
  private static final float SCALE_UP = 1.1f;
  private static final float SCALE_DOWN = 0.9f;

  private static final int COLOR_BRIGHT = 382;

  private static final int MIN_NODE_SIZE = 20;

  /**
   * Initiates a new petrinet viewer with a petrinet. Rulenet == null if the
   * viewer displays the N petrinet
   */
  PetrinetViewer(Layout<INode, IArc> layout, int petrinetOrRuleId,
    RuleNet ruleNet) {

    super(layout);
    // Logic
    this.ruleNet = ruleNet;
    this.currentId = petrinetOrRuleId;

    // Style
    setBackground(Color.WHITE);

    // Keyboard
    addKeyListener(new PetrinetKeyboardListener(this));

    // Mouse
    addMouseListener(new PetrinetMouseListener(this));
    addMouseWheelListener(new PetrinetMouseListener(this));
    addKeyListener(new PetrinetKeyboardListener(this));

    // Renderer
    getRenderer().setVertexRenderer(new PetrinetRenderer(this));
    getRenderContext().setEdgeLabelTransformer(
      new PetrinetArcLabelTransformer(this));
    getRenderContext().setVertexShapeTransformer(
      new PetrinetNodeShapeTransformer(this));
    getRenderContext().setEdgeShapeTransformer(
      new PetrinetArcShapeTransformer());
  }

  // Extended Constructor for NAC PetrinetViewer
  PetrinetViewer(Layout<INode, IArc> layout, int petrinetOrRuleId,
    UUID nacId, RuleNet ruleNet) {

    this(layout, petrinetOrRuleId, ruleNet);
    this.nacId = nacId;
  }

  /**
   * @return the currentSelectedNode
   */
  public INode getCurrentSelectedNode() {

    return currentSelectedNode;
  }

  /**
   * @param currentSelectedNode
   *        the currentSelectedNode to set
   */
  public void setCurrentSelectedNode(INode currentSelectedNode) {

    this.currentSelectedNode = currentSelectedNode;
  }

  /** Add this {@link PetrinetViewer} to the <code>component</code> */
  void addTo(JPanel component) {

    component.add(this);
  }

  /** Removes this {@link PetrinetViewer} from the <code>component</code> */
  public void removeFrom(JPanel frame) {

    frame.remove(this);
  }

  /**
   * Repaints the {@link PetrinetViewer}. Distinguishes between petrinet and
   * rule
   */
  void smartRepaint() {

    if (isN()) {
      super.repaint();
    } else {
      RulePane.getInstance().repaint();
    }
  }

  /** Returns the id of the currently displayed petrinet or rule */
  int getCurrentId() {

    return currentId;
  }

  /**
   * Returns <tt>true</tt> if the current Id is the id of a petrinet
   * (N-Petrinet)
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isIdPetrinetId() {

    return isN();
  }

  /**
   * Returns <tt>true</tt> if the current Id is the id of a petrinet within a
   * rule (L, K or R -Petrinet)
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isIdRuleId() {

    // return !isIdPetrinetId();
    return isL() || isR() || isK();
  }

  /**
   * Returns <tt>true</tt> if the current Id is the id of a petrinet within a
   * NAC (L, K or R -Petrinet)
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isIdNacId() {

    return isNAC();
  }

  /**
   * Is the currently displayed petrinet the R-part of a rule?
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isR() {

    return ruleNet == RuleNet.R;
  }

  /**
   * Is the currently displayed petrinet the K-part of a rule?
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isK() {

    return ruleNet == RuleNet.K;
  }

  /**
   * Is the currently displayed petrinet the L-part of a rule?
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isL() {

    return ruleNet == RuleNet.L;
  }

  /**
   * Is the currently displayed petrinet the N-part of a rule?
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  boolean isN() {

    return ruleNet == null;
  }

  /**
   * Is the currently displayed petrinet the NAC-part of a rule?
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   * @see PetrinetViewer#isNAC()
   */
  boolean isNAC() {

    return ruleNet == RuleNet.NAC;
  }

  /**
   * Returns the {@link RuleNet} of the {@link PetrinetViewer}.
   * <code>null</code> if the displayed petrinet is not part of a rule
   *
   * @see PetrinetViewer#isL()
   * @see PetrinetViewer#isK()
   * @see PetrinetViewer#isR()
   * @see PetrinetViewer#isN()
   */
  RuleNet getRuleNet() {

    return ruleNet;
  }

  /**
   * Makes the view zoom in or out (depending of <code>factor</code>) from a
   * certain <code>point</code>
   *
   * @param factor
   * @param point
   */
  void scale(float factor, Point point) {

    // Make sure nodes are not getting too small, as rounding errors would
    // prevent zooming back in
    if (this.nodeSize >= MIN_NODE_SIZE || factor > 1) {
      resizeNodes(factor);
      changeNodeSizeInJungData(getNodeSize());
      moveAllNodesTo(factor, point);
    }
    smartRepaint();
  }

  /**
   * Sets the nodeSize in the {@link PetrinetData} within the engine to
   * <code>nodeSize</code>
   *
   * @see PetrinetViewer#resizeNodes(float)
   */
  private void changeNodeSizeInJungData(double nodeSize) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setNodeSize(getCurrentId(),
          nodeSize);
      } else {
        EngineAdapter.getRuleManipulation().setNodeSize(getCurrentId(),
          nodeSize);
      }
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Changes the nodeSize in this {@link PetrinetViewer} by a certain
   * <code>factor</code>. If this {@link PetrinetViewer} is part of a rule,
   * the node size in the other parts of the rule will be also changed
   *
   * @see PetrinetViewer#changeNodeSizeInJungData(double)
   */
  private void resizeNodes(float factor) {

    if (isN()) {
      this.nodeSize *= factor;
    } else {
      RulePane.getInstance().resizeNodes(factor);
    }
  }

  /**
   * Changes the nodeSize by a certain <code>factor</code> only for this
   * {@link PetrinetViewer} and only if its part of a rule
   */
  public void resizeNodesOnlyOnThisPartOfRule(float factor) {

    if (!isN()) {
      this.nodeSize *= factor;
    }
  }

  /**
   * Moves all nodes to direction of <code>point</code> but only by
   * <code>factor</code><br \>
   * . If <code>factor</code> is smaller 1 nodes are moved towards the
   * <code>point</code>, if <code>factor</code> is greater 1 nodes are moved
   * away from <code>point</code>. <h4>example</h4> Position of (only) node is
   * [10,10], <code>point</code> is [0,5] and <code>factor</code> is 0,9.
   * Resulting position of node is [9,9,5]
   *
   * @param factor
   * @param point
   */
  public void moveAllNodesTo(float factor, Point point) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().moveAllNodesTo(
          getCurrentId(), factor, point);
      } else {
        EngineAdapter.getRuleManipulation().moveAllNodesTo(getCurrentId(),
          factor, point);
      }
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Returns the size of nodes in pixels
   *
   * @return
   */
  public double getNodeSize() {

    return nodeSize;
  }

  /**
   * Sets the nodSize only for this {@link PetrinetViewer} (a simple setter).
   * This should only be used for init purposes. To change the node size, use
   * {@link PetrinetViewer#resizeNodes(float)} and
   * {@link PetrinetViewer#changeNodeSizeInJungData(double)}
   */
  void setNodeSize(double nodeSize) {

    this.nodeSize = nodeSize;
  }

  /**
   * Returns the width of a place in pixels
   *
   * @return
   */
  private double getPlaceWidth() {

    return nodeSize;
  }

  /**
   * Returns the height of a place in pixels
   *
   * @return
   */
  private double getPlaceHeight() {

    return getPlaceWidth() / 1.5d;
  }

  /**
   * Returns the size of a (quadratic) transition
   *
   * @return
   */
  private double getTransitionSize() {

    return getPlaceHeight();
  }

  /**
   * Returns the distance that must be among nodes
   *
   * @return
   */
  public double getNodeDistance() {

    return getPlaceHeight();
  }

  /**
   * Returns the {@link PlaceAttribute} of the <code>place</code> no matter if
   * its part of a rule or a regular petrinet
   */
  PlaceAttribute getPlaceAttribute(Place place) {

    PlaceAttribute placeAttribute = null;
    try {
      if (isN()) {
        placeAttribute =
          EngineAdapter.getPetrinetManipulation().getPlaceAttribute(
            getCurrentId(), place);
      } else if (isNAC()) {
        placeAttribute =
          EngineAdapter.getRuleManipulation().getPlaceAttribute(
            getCurrentId(), nacId, place);
      } else {
        placeAttribute =
          EngineAdapter.getRuleManipulation().getPlaceAttribute(
            getCurrentId(), place);
      }
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
    return placeAttribute;
  }

  /**
   * Returns the {@link TransitionAttribute} of the <code>transition</code> no
   * matter if its part of a rule or a regular petrinet
   */
  TransitionAttribute getTransitionAttribute(Transition transition) {

    TransitionAttribute transitionAttribute = null;
    try {
      if (isN()) {
        transitionAttribute =
          EngineAdapter.getPetrinetManipulation().getTransitionAttribute(
            getCurrentId(), transition);
      } else {
        transitionAttribute =
          EngineAdapter.getRuleManipulation().getTransitionAttribute(
            getCurrentId(), transition);
      }
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
    return transitionAttribute;
  }

  /**
   * Returns the {@link ArcAttribute} of the <code>arc</code> no matter if its
   * part of a rule or a regular petrinet
   */
  ArcAttribute getArcAttribute(IArc arc) {

    ArcAttribute arcAttribute = null;
    try {
      if (isN()) {
        arcAttribute =
          EngineAdapter.getPetrinetManipulation().getArcAttribute(
            getCurrentId(), arc);
      } else {
        arcAttribute =
          EngineAdapter.getRuleManipulation().getArcAttribute(getCurrentId(),
            arc);
      }
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
    return arcAttribute;
  }

  /**
   * Checks for the type of the <code>node</code>
   *
   * @param node
   * @return <code>true</code> if its a {@link Place}, <code>false</code> else
   */
  boolean isNodePlace(INode node) {

    NodeTypeEnum result = null;
    try {
      if (isN()) {
        result = EngineAdapter.getPetrinetManipulation().getNodeType(node);
      } else {
        result = EngineAdapter.getRuleManipulation().getNodeType(node);
      }
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
    return result == NodeTypeEnum.Place;
  }

  /**
   * Moves a <code>node</code> by a vector called
   * <code>relativePosition</code>
   *
   * @param node
   * @param relativePosition
   */
  void moveNode(INode node, Point relativePosition) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().moveNode(getCurrentId(),
          node, relativePosition);
      } else {
        EngineAdapter.getRuleManipulation().moveNode(getCurrentId(), node,
          relativePosition);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Deletes the currently selected node or arc
   *
   * @return <code>false</code> if nothing was selected
   */
  public boolean deleteSelected() {

    if (currentSelectedArc != null) {
      deleteArc(this.currentSelectedArc);
    } else if (getCurrentSelectedNode() != null) {
      if (isNodePlace(getCurrentSelectedNode())) {
        deletePlace((Place) getCurrentSelectedNode());
      } else {
        deleteTransition((Transition) getCurrentSelectedNode());
      }
    } else {
      return false;
    }
    return true;
  }

  /**
   * Creates a new arc from <code>start</code> to <code>end</code> no matter
   * if its part of a rule or a regular petrinet
   */
  void createArc(INode start, INode end) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().createArc(getCurrentId(),
          start, end);
      } else if (isNAC()) {
        EngineAdapter.getRuleManipulation().createArc(getCurrentId(), nacId,
          start, end);
      } else {
        EngineAdapter.getRuleManipulation().createArc(getCurrentId(),
          getRuleNet(), start, end);
      }
      smartRepaint();
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Creates a new {@link Transition} at a certain <code>position</code> no
   * matter if its part of a rule or a regular petrinet
   */
  public void createTransition(Point position) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().createTransition(
          getCurrentId(), position);
      } else if (isNAC()) {
        System.out.println("PetrinetViewer.createTransition");
        EngineAdapter.getRuleManipulation().createTransition(getCurrentId(),
          nacId, position);

      } else {
        EngineAdapter.getRuleManipulation().createTransition(getCurrentId(),
          getRuleNet(), position);
      }
      smartRepaint();
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Creates a new {@link Place} at a certain <code>position</code> no matter
   * if its part of a rule or a regular petrinet
   */
  void createPlace(Point point) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().createPlace(getCurrentId(),
          point);
      } else if (isNAC()) {
        System.out.println("PetrinetViewer.createPlace");
        EngineAdapter.getRuleManipulation().createPlace(getCurrentId(),
          nacId, point);
      } else {
        EngineAdapter.getRuleManipulation().createPlace(getCurrentId(),
          getRuleNet(), point);
      }
      smartRepaint();
    } catch (EngineException e) {
      PopUp.popError(e);
    }
  }

  /**
   * Deletes the <code>place</code> from the currently displayed petrinet or
   * rule
   *
   * @param place
   */
  void deletePlace(Place place) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().deletePlace(getCurrentId(),
          place);
      } else if (isNAC()) {
        System.out.println("PetrinetViewer.deletePlace");
        EngineAdapter.getRuleManipulation().deletePlace(getCurrentId(),
          nacId, place);
      } else {
        EngineAdapter.getRuleManipulation().deletePlace(getCurrentId(),
          getRuleNet(), place);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Deletes the <code>transition</code> from the currently displayed petrinet
   * or rule
   *
   * @param transition
   */
  void deleteTransition(Transition transition) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().deleteTransition(
          getCurrentId(), transition);
      } else if (isNAC()) {
        System.out.println("PetrinetViewer.deleteTransition");
        EngineAdapter.getRuleManipulation().deleteTransition(getCurrentId(),
          nacId, transition);
      } else {
        EngineAdapter.getRuleManipulation().deleteTransition(getCurrentId(),
          getRuleNet(), transition);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Deletes the <code>arc</code> from the currently displayed petrinet or
   * rule
   *
   * @param arc
   */
  void deleteArc(IArc arc) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().deleteArc(getCurrentId(), arc);
      } else if (isNAC()) {
        EngineAdapter.getRuleManipulation().deleteArc(getCurrentId(), nacId,
          arc);
      } else {
        EngineAdapter.getRuleManipulation().deleteArc(getCurrentId(),
          getRuleNet(), arc);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Sets the name of a {@link Transition} no matter if its part a rule or a
   * regular petrinet
   *
   * @param place
   * @param pname
   */
  public void setPname(Place place, String pname) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setPname(getCurrentId(),
          place, pname);
      } else if (isNAC()) {
        EngineAdapter.getRuleManipulation().setPname(getCurrentId(), nacId,
          place, pname);
      } else {
        EngineAdapter.getRuleManipulation().setPname(getCurrentId(), place,
          pname);
      }
      smartRepaint();
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

  }

  /**
   * Sets the <code>marking</code>of a <code>place</code> no matter if its
   * part a rule or a regular petrinet
   *
   * @param place
   * @param marking
   */
  public void setMarking(Place place, int marking) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setMarking(getCurrentId(),
          place, marking);
      } else {
        EngineAdapter.getRuleManipulation().setMarking(getCurrentId(), place,
          marking);
      }
      smartRepaint();
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

  }

  /**
   * Sets the <code>capacity</code>of a <code>place</code> no matter if its
   * part a rule or a regular petrinet
   *
   * @param place
   * @param capacity
   */
  public void setCapacity(Place place, int capacity) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setCapacity(getCurrentId(),
          place, capacity);
      } else {
        EngineAdapter.getRuleManipulation().setCapacity(getCurrentId(),
          place, capacity);
      }
      smartRepaint();
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

  }

  /**
   * Sets the <code>name</code> of a <code>transition</code> no matter if its
   * part a rule or a regular petrinet
   *
   * @param transition
   * @param name
   */
  public void setTname(Transition transition, String name) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setTname(getCurrentId(),
          transition, name);
      } else if (isNAC()) {
        EngineAdapter.getRuleManipulation().setTname(getCurrentId(), nacId,
          transition, name);
      } else {
        EngineAdapter.getRuleManipulation().setTname(getCurrentId(),
          transition, name);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Sets the <code>label</code> of a <code>transition</code> no matter if its
   * part a rule or a regular petrinet
   *
   * @param transition
   * @param label
   */
  public void setTlb(Transition transition, String label) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setTlb(getCurrentId(),
          transition, label);
      } else {
        EngineAdapter.getRuleManipulation().setTlb(getCurrentId(),
          transition, label);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Sets the <code>renew</code> of a <code>transition</code> no matter if its
   * part a rule or a regular petrinet
   *
   * @param transition
   * @param renew
   */
  public void setRenew(Transition transition, String renew) {

    IRenew actualRenew = Renews.fromString(renew);
    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setRnw(getCurrentId(),
          transition, actualRenew);
      } else {
        EngineAdapter.getRuleManipulation().setRnw(getCurrentId(),
          transition, actualRenew);
      }
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Sets the <code>weight</code> of an <code>arc</code> no matter if its part
   * a rule or a regular petrinet
   *
   * @param arc
   * @param weight
   */
  public void setWeight(IArc arc, int weight) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setWeight(getCurrentId(),
          arc, weight);
      } else {
        EngineAdapter.getRuleManipulation().setWeight(getCurrentId(), arc,
          weight);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Sets the <code>color</code> of a <code>place</code> no matter if its part
   * a rule or a regular petrinet
   *
   * @param place
   * @param color
   */
  public void setPlaceColor(INode place, Color color) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().setPlaceColor(getCurrentId(),
          place, color);
      } else {
        EngineAdapter.getRuleManipulation().setPlaceColor(getCurrentId(),
          place, color);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Moves the whole graph of this {@link PetrinetViewer} by a vector called
   * <code>relativePosition</code>
   *
   * @param id
   * @param relativePosition
   */
  public void moveGraph(int id, Point2D relativePosition) {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().moveGraph(id,
          relativePosition);
      } else {
        EngineAdapter.getRuleManipulation().moveGraph(id, relativePosition);
      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
    }
  }

  /**
   * Moves the whole graph to the top left (element with lowest x will have x
   * == 0, element with lowest y will have y == 0)
   */
  public void moveIntoVision() {

    try {
      if (isN()) {
        EngineAdapter.getPetrinetManipulation().moveGraphIntoVision(
          getCurrentId());
      } else {
        EngineAdapter.getRuleManipulation().moveGraphIntoVision(
          getCurrentId());

      }
      smartRepaint();
    } catch (Exception e) {
      PopUp.popError(e);
    }

  }

  /**
   * Menu that show as up when right-clicking the white space. It has only one
   * item: "Ins Sichtfeld verschieben"
   */
  private static class PetrinetGraphPopUpMenu
    extends JPopupMenu {

    public PetrinetGraphPopUpMenu(PetrinetViewer petrinetViewer) {

      JMenuItem menuItem = new JMenuItem("Ins Sichtfeld verschieben");
      menuItem.addActionListener(new MoveListener(petrinetViewer));
      add(menuItem);
    }

    /** Listener that is invoced when the menu item is clicked */
    private static class MoveListener
      implements ActionListener {

      private PetrinetViewer petrinetViewer;

      public MoveListener(PetrinetViewer petrinetViewer) {

        this.petrinetViewer = petrinetViewer;
      }

      @Override
      public void actionPerformed(ActionEvent e) {

        petrinetViewer.moveIntoVision();
      }

    }

  }

  /**
   * Pop up menu that appears when a node or arc is right-clicked. It is used
   * for deleting and settings colors
   */
  private static final class PetrinetNodePopUpMenu
    extends JPopupMenu {

    /**
     * Listener for clicking on menu items<br>
     * Each menu item has its own listener
     */
    /*
     * There are 3 types of listeners as there are 3 types of pop up menus:
     * Node(Place/Transition) and Arc
     */
    private static final class DeleteListener
      implements ActionListener {

      private DeleteListener() {

      }

      private Transition transition;

      private Place place;

      private IArc arc;

      private PetrinetViewer petrinetViewer;

      private DeleteListener(Transition transition, Place place, IArc arc,
        PetrinetViewer petrinetViewer) {

        this.transition = transition;
        this.place = place;
        this.arc = arc;
        this.petrinetViewer = petrinetViewer;
      }

      /** Create a delete listener for a pop up menu if a place */
      static DeleteListener fromPlace(Place place,
        PetrinetViewer petrinetViewer) {

        return new DeleteListener(null, place, null, petrinetViewer);
      }

      /** Create a delete listener for a pop up menu if an arc */
      static DeleteListener fromArc(IArc arc, PetrinetViewer petrinetViewer) {

        return new DeleteListener(null, null, arc, petrinetViewer);
      }

      /** Create a delete listener for a pop up menu if a transition */
      static DeleteListener fromTransition(Transition transition,
        PetrinetViewer petrinetViewer) {

        return new DeleteListener(transition, null, null, petrinetViewer);
      }

      @Override
      public void actionPerformed(ActionEvent e) {

        if (place != null) {
          petrinetViewer.deletePlace(place);
        } else if (transition != null) {
          petrinetViewer.deleteTransition(transition);
        } else {
          petrinetViewer.deleteArc(arc);
        }
        // deselect node
        petrinetViewer.setCurrentSelectedNode(null);
        MainWindow.getInstance().repaint();
      }
    }

    /** Listener for clicks on color fields in context menus of places */
    private static final class ChangeColorListener
      implements ActionListener {

      private PetrinetViewer petrinetViewer;

      private Color color;

      private INode place;

      private ChangeColorListener(Color color, INode place,
        PetrinetViewer petrinetViewer) {

        this.petrinetViewer = petrinetViewer;
        this.color = color;
        this.place = place;
      }

      @Override
      public void actionPerformed(ActionEvent e) {

        petrinetViewer.setPlaceColor(place, color);
      }

    }

    private PetrinetNodePopUpMenu(PetrinetViewer petrinetViewer) {

    }

    /** Adds a new item to the menu for changing the color of a node */
    private static void addColorToPopUpMenu(PetrinetNodePopUpMenu menu,
      String description, Color color, INode node,
      PetrinetViewer petrinetViewer) {

      JMenuItem item = new JMenuItem(description);
      item.setBackground(color);
      item.addActionListener(new ChangeColorListener(color, node,
        petrinetViewer));
      menu.add(item);
    }

    static PetrinetNodePopUpMenu fromNode(INode node,
      PetrinetViewer petrinetViewer) {

      PetrinetNodePopUpMenu result =
        new PetrinetNodePopUpMenu(petrinetViewer);
      if (petrinetViewer.isNodePlace(node)) {
        PlaceAttribute placeAttribute =
          petrinetViewer.getPlaceAttribute((Place) node);
        // Delete
        JMenuItem delete =
          new JMenuItem("Stelle " + placeAttribute.getPname() + " ["
            + node.getId() + "] " + "löschen");
        delete.addActionListener(DeleteListener.fromPlace((Place) node,
          petrinetViewer));
        result.add(delete);

        // Colors
        ColorGenerator colorGenerator = new ColorGenerator();
        for (Color color : colorGenerator.getFixedColors()) {
          addColorToPopUpMenu(result, colorGenerator.getDescription(color),
            color, node, petrinetViewer);
        }

      } else {
        TransitionAttribute transitionAttribute =
          petrinetViewer.getTransitionAttribute((Transition) node);
        JMenuItem jMenuItem =
          new JMenuItem("Transition " + transitionAttribute.getTname() + " ["
            + node.getId() + "] " + "löschen");
        jMenuItem.addActionListener(DeleteListener.fromTransition(
          (Transition) node, petrinetViewer));
        result.add(jMenuItem);
      }
      return result;
    }

    static PetrinetNodePopUpMenu fromArc(IArc arc,
      PetrinetViewer petrinetViewer) {

      PetrinetNodePopUpMenu result =
        new PetrinetNodePopUpMenu(petrinetViewer);
      JMenuItem jMenuItem =
        new JMenuItem("Pfeil [" + arc.getId() + "] löschen");
      jMenuItem.addActionListener(DeleteListener.fromArc(arc, petrinetViewer));
      result.add(jMenuItem);
      return result;
    }
  }

  /** mouse click listener for the drawing panel */
  private static class PetrinetMouseListener
    extends PickingGraphMousePlugin<INode, IArc>
    implements MouseWheelListener, MouseMotionListener {

    private static enum DragMode {
      SCROLL, MOVENODE, ARC, NONE
    }

    private PetrinetViewer petrinetViewer;

    private DragMode dragMode = DragMode.NONE;

    PetrinetMouseListener(PetrinetViewer petrinetViewer) {

      this.petrinetViewer = petrinetViewer;
    }

    /** X-coordinate of begin of drag */
    private int pressedX = 0;
    /** Y-coordinate of begin of drag */
    private int pressedY = 0;

    /**
     * ID of node that was clicked at beginning of drag. Needed for drawing
     * arcs
     */
    private INode nodeFromDrag = null;

    /** Zoom petrinet on mouse wheel */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

      Point point = new Point(e.getX(), e.getY());
      float factor;
      if (e.getWheelRotation() < 0) {
        factor = SCALE_UP;
      } else {
        factor = SCALE_DOWN;
      }
      petrinetViewer.scale(factor, point);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

      super.mousePressed(e); // mousePressedEvent in class
      // PickingGraphMousePlugin selects nodes
      EditorMode mode = EditorPane.getInstance().getCurrentMode();

      // left-click PICK : display clicked node
      // right-click: display pop-up-menu
      // left-click PLACE: create place at position
      // left-click TRANSITION: etc...
      if (mode == EditorMode.PICK) {
        if (edge != null) {
          // edge clicked
          AttributePane.getInstance().displayEdge(edge, petrinetViewer);
          petrinetViewer.setCurrentSelectedNode(null);
          petrinetViewer.currentSelectedArc = edge;
          if (e.isMetaDown()) {
            PetrinetNodePopUpMenu.fromArc(edge, petrinetViewer).show(
              petrinetViewer, e.getX(), e.getY());
          }

        } else if (vertex != null) {
          // vertex clicked
          AttributePane.getInstance().displayNode(vertex, petrinetViewer);
          petrinetViewer.setCurrentSelectedNode(vertex);
          petrinetViewer.currentSelectedArc = null;
          if (e.isMetaDown()) {
            PetrinetNodePopUpMenu.fromNode(vertex, petrinetViewer).show(
              petrinetViewer, e.getX(), e.getY());
          }
        } else {
          // nothing clicked
          if (e.isMetaDown()) {
            PetrinetGraphPopUpMenu popUp =
              new PetrinetGraphPopUpMenu(petrinetViewer);
            popUp.show(petrinetViewer, e.getX(), e.getY());
          } else {
            // nothing
          }
          petrinetViewer.setCurrentSelectedNode(null);
          petrinetViewer.currentSelectedArc = null;
          AttributePane.getInstance().displayEmpty();
        }
      } else if (mode == EditorMode.PLACE) {
        petrinetViewer.createPlace(new Point(e.getX(), e.getY()));
      } else if (mode == EditorMode.TRANSITION) {
        petrinetViewer.createTransition(new Point(e.getX(), e.getY()));
      }

      if (petrinetViewer.isN()) {
        petrinetViewer.repaint();
      } else {
        RulePane.getInstance().deselectBut(petrinetViewer);
        RulePane.getInstance().repaint();
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {

      super.mousePressed(e);
      if (dragMode == DragMode.NONE) {
        pressedX = e.getX();
        pressedY = e.getY();
        EditorMode editorMode = EditorPane.getInstance().getCurrentMode();
        // dragging in pick mode
        if (editorMode == EditorMode.PICK) {
          // find out: scrolling or moving? -> is something clicked?
          if (vertex != null) {
            // something is clicked -> MOVENODE
            dragMode = DragMode.MOVENODE;
            nodeFromDrag = vertex;
          } else {
            // nothing is clicked -> SCROLL
            dragMode = DragMode.SCROLL;
          }
          // dragging in arc mode
        } else if (editorMode == EditorMode.ARC) {
          // find out what was clicked on
          super.mousePressed(e);
          if (vertex != null) {
            nodeFromDrag = vertex;
            vertex = null;
            dragMode = DragMode.ARC;
          }
        }
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

      try {

        Point oldPoint = new Point(pressedX, pressedY);
        Point newPoint = new Point(e.getX(), e.getY());

        if (!oldPoint.equals(newPoint)) {
          if (dragMode == DragMode.SCROLL) {
            petrinetViewer.moveGraph(petrinetViewer.getCurrentId(),
              new Point((int) (newPoint.getX() - oldPoint.getX()),
                (int) (newPoint.getY() - oldPoint.getY())));
            petrinetViewer.repaint();
            // petrinetViewer.scale(0.5f, newPoint);
            // petrinetViewer.scale(2f, oldPoint);
          } else if (dragMode == DragMode.MOVENODE) {
            petrinetViewer.moveNode(nodeFromDrag, new Point(
              (int) (newPoint.getX() - oldPoint.getX()),
              (int) (newPoint.getY() - oldPoint.getY())));
            petrinetViewer.repaint();
          } else if (dragMode == DragMode.ARC) {
            // find out what was released on
            super.mousePressed(e);
            if (vertex != null) {
              petrinetViewer.createArc(nodeFromDrag, vertex);
            }
          }
        }
      } catch (ShowAsWarningException arcAlreadyExists) {
        vertex = null;
        throw arcAlreadyExists;
      } finally {
        dragMode = DragMode.NONE;
        nodeFromDrag = null;
      }
    }
  } // end of mouse listener

  /**
   * Listener to handle key strokes. Current features: Strg+"+" -> zoom in,
   * Strg+"-" -> zomm out, entf/delete -> deleted currenty selected graph
   * element
   */
  private static class PetrinetKeyboardListener
    implements KeyListener {

    private PetrinetViewer petrinetViewer;

    private boolean strgPressed = false;

    public PetrinetKeyboardListener(PetrinetViewer petrinetViewer) {

      this.petrinetViewer = petrinetViewer;
    }

    @Override
    public void keyPressed(KeyEvent pressed) {

      // KeyCode 17 = Strg
      if (pressed.getKeyCode() == STRG_KEY) {
        strgPressed = true;
      }
    }

    @Override
    public void keyReleased(KeyEvent released) {

      // KeyCode 17 = Strg
      if (released.getKeyCode() == STRG_KEY) {
        strgPressed = false;
      }
      String keyText = KeyEvent.getKeyText(released.getKeyCode());
      if (strgPressed) {
        // Strg, + => zoom in
        if (keyText.equals("Plus")) {
          petrinetViewer.scale(SCALE_UP, new Point(0, 0));
          // Strg, - => zomm out
        } else if (keyText.equals("Minus")) {
          petrinetViewer.scale(SCALE_DOWN, new Point(0, 0));
        }
      } else {
        // delete => delete currently selected node
        if (keyText.equals("Delete")) {
          boolean wasSelected = petrinetViewer.deleteSelected();
          if (!wasSelected) {
            throw new ShowAsInfoException(
              "Es ist nichts zum Löschen ausgewählt");
          }
        }
      }
    }

    @Override
    public void keyTyped(KeyEvent typed) {

    }

  }

  /**
   * Custom renderer that is used from jung to make transitions cornered and
   * places circular
   */
  private static class PetrinetRenderer
    implements Vertex<INode, IArc> {

    private PetrinetViewer petrinetViewer;

    PetrinetRenderer(PetrinetViewer petrinetViewer) {

      this.petrinetViewer = petrinetViewer;
    }

    @Override
    public void paintVertex(RenderContext<INode, IArc> renderContext,
      Layout<INode, IArc> layout, INode node) {

      GraphicsDecorator decorator = renderContext.getGraphicsContext();
      decorator.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
      decorator.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      Point2D center = layout.transform(node);
      if (petrinetViewer.isNodePlace(node)) {
        PlaceAttribute placeAttribute =
          petrinetViewer.getPlaceAttribute((Place) node);
        double width = petrinetViewer.getPlaceWidth();
        double height = petrinetViewer.getPlaceHeight();
        double x = center.getX() - width / 2;
        double y = center.getY() - height / 2;

        /* Draw selected node bigger */
        if (petrinetViewer.isN()) {
          if (petrinetViewer.getCurrentSelectedNode() == node) {
            width *= FACTOR_SELECTED_NODE;
            height *= FACTOR_SELECTED_NODE;
          }
        } else {
          List<INode> nodes =
            RulePane.getInstance().getMappingsOfSelectedNode();
          if (node != null && nodes.contains(node)) {
            width *= FACTOR_SELECTED_NODE;
            height *= FACTOR_SELECTED_NODE;
          }
        }

        GradientPaint gradientPaintPlace =
          new GradientPaint((int) x, (int) y,
            placeAttribute.getColor().brighter(), (int) x,
            (int) (y + height), placeAttribute.getColor().darker());
        decorator.setPaint(gradientPaintPlace);
        decorator.fillOval((int) x, (int) y, (int) width, (int) height);

        // draw frame
        decorator.setPaint(NODE_BORDER_COLOR);
        decorator.drawOval((int) x, (int) y, (int) width, (int) height);

        // write name
        decorator.setPaint(FONT_COLOR_DARK);
        decorator.drawString(placeAttribute.getPname(), (int) (x + width),
          (int) (y + height));

        // write capacity
        if (placeAttribute.getCapacity() < Integer.MAX_VALUE) {
          decorator.drawString("K="
            + String.valueOf(placeAttribute.getCapacity()),
            (int) (x + decorator.getFont().getSize()),
            (int) (y + height + decorator.getFont().getSize()));
        }

        // display marking
        int marking = placeAttribute.getMarking();
        if (marking == 0) {
          // view nothing (empty place)
        } else if (marking == 1) {
          // view black dot within place
          decorator.setPaint(Color.BLACK);
          // CHECKSTYLE:OFF - No need to ceck for magic numbers
          decorator.fillOval((int) (x + width / 4), (int) (y + height / 4),
            (int) (height / 2), (int) (height / 2));
          // CHECKSTYLE:ON
        } else {
          // draw number within place
          if (colorIsBright(placeAttribute.getColor())) {
            decorator.setPaint(FONT_COLOR_DARK);
          } else {
            decorator.setPaint(FONT_COLOR_BRIGHT);
          }
          // CHECKSTYLE:OFF - No need to ceck for magic numbers
          decorator.drawString(String.valueOf(marking),
            (int) (x + width / 3), (int) (y + height / 1.5));
          // CHECKSTYLE:ON
        }
      } else {
        TransitionAttribute transitionAttribute =
          petrinetViewer.getTransitionAttribute((Transition) node);

        // draw rect
        int size = (int) petrinetViewer.getTransitionSize();
        int x = (int) (center.getX() - size / 2);
        int y = (int) (center.getY() - size / 2);

        GradientPaint blackOrWhite = null;
        if (transitionAttribute.getIsActivated()) {
          blackOrWhite =
            new GradientPaint(x, y, Color.DARK_GRAY, x, y + size, Color.BLACK);
        } else {
          blackOrWhite =
            new GradientPaint(x, y, Color.LIGHT_GRAY, x, y + size,
              Color.LIGHT_GRAY.darker());
        }

        /* Draw selected node bigger */
        if (petrinetViewer.isN()) {
          if (petrinetViewer.getCurrentSelectedNode() == node) {
            size *= FACTOR_SELECTED_NODE;
          }
        } else {
          List<INode> nodes =
            RulePane.getInstance().getMappingsOfSelectedNode();
          if (node != null && nodes.contains(node)) {
            size *= FACTOR_SELECTED_NODE;
          }
        }

        decorator.setPaint(blackOrWhite);
        decorator.fillRect(x, y, size, size);

        // draw frame
        decorator.setPaint(NODE_BORDER_COLOR);
        decorator.drawRect(x, y, size, size);

        // draw name
        decorator.setPaint(FONT_COLOR_DARK);
        decorator.drawString(transitionAttribute.getTname(), x + size + 2, y
          + size);

        // draw label
        if (transitionAttribute.getIsActivated()) {
          decorator.setPaint(Color.LIGHT_GRAY);
        } else {
          decorator.setPaint(Color.DARK_GRAY);
        }
        // CHECKSTYLE:OFF - No need to ceck for magic numbers
        decorator.drawString(transitionAttribute.getTLB(), x + size / 3, y
          + size / 1.5f);
        // CHECKSTYLE:ON
      }
    }

    /**
     * Returns <code>true</code> if the color is bright enough so text that is
     * displayed upon it, must be drawn with {@link Style#FONT_COLOR_DARK}
     *
     * @see Style#FONT_COLOR_BRIGHT
     * @param color
     * @return
     */
    private boolean colorIsBright(Color color) {

      return color.getBlue() + color.getRed() + color.getGreen() > COLOR_BRIGHT;
    }
  }

  /**
   * Return <code>true</code> if the color is bright enough so text on it cant
   * be drawn with {@link Style#FONT_COLOR_DARK}. Else it should be drawn with
   * {@link Style#FONT_COLOR_DARK}
   *
   * @param color
   * @return
   */

  /**
   * Transforming Arcs to Strings. Actually just used for getting the label of
   * an Arc
   */
  private static class PetrinetArcLabelTransformer
    implements Transformer<IArc, String> {

    private PetrinetViewer petrinetViewer;

    PetrinetArcLabelTransformer(PetrinetViewer petrinetViewer) {

      this.petrinetViewer = petrinetViewer;
    }

    @Override
    public String transform(IArc arc) {

      int weight = 1;
      ArcAttribute arcAttribute = petrinetViewer.getArcAttribute(arc);
      weight = arcAttribute.getWeight();
      if (weight == 1) {
        return "";
      } else {
        return String.valueOf(weight);
      }
    }
  }

  /**
   * Transforming Arcs Shapes. Actually just used for drawing straight or bent
   * lines
   */
  private static class PetrinetArcShapeTransformer
    extends AbstractEdgeShapeTransformer<INode, IArc> {

    private QuadCurve<INode, IArc> curve =
      new EdgeShape.QuadCurve<INode, IArc>();
    private Line<INode, IArc> line = new EdgeShape.Line<INode, IArc>();

    @Override
    public Shape transform(Context<Graph<INode, IArc>, IArc> context) {

      if (context.graph.findEdge(context.element.getTarget(),
        context.element.getSource()) != null) {
        return curve.transform(context);
      }

      return line.transform(context);
    }
  }

  /**
   * This is for defining the nodes' shapes so the arrows properly connects to
   * them
   */
  private static class PetrinetNodeShapeTransformer
    implements Transformer<INode, Shape> {

    private PetrinetViewer petrinetViewer;

    PetrinetNodeShapeTransformer(PetrinetViewer petrinetViewer) {

      this.petrinetViewer = petrinetViewer;
    }

    @Override
    public Shape transform(INode node) {

      if (petrinetViewer.isNodePlace(node)) {
        // return new Ellipse2D.Double(0d, 0d, Style.getPlaceWidth(),
        // Style.getPlaceHeight());
        return new Ellipse2D.Double(-petrinetViewer.getPlaceWidth() / 2,
          -petrinetViewer.getPlaceHeight() / 2,
          petrinetViewer.getPlaceWidth(), petrinetViewer.getPlaceHeight());
      } else {
        return new Rectangle2D.Double(
          -petrinetViewer.getTransitionSize() / 2,
          -petrinetViewer.getTransitionSize() / 2,
          petrinetViewer.getTransitionSize(),
          petrinetViewer.getTransitionSize());
      }
    }

  }

}
