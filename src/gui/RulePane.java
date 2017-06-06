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

package gui;

import static gui.Style.RULE_PANE_BORDER_K;
import static gui.Style.RULE_PANE_BORDER_L;
import static gui.Style.RULE_PANE_BORDER_NAC;
import static gui.Style.RULE_PANE_BORDER_R;
import static gui.Style.TOTAL_WIDTH;

import java.awt.GridLayout;
import java.util.List;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import petrinet.model.IArc;
import petrinet.model.INode;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.Layout;
import engine.handler.RuleNet;
import exceptions.EngineException;

/** Pane for displaying rules */
public final class RulePane {

  /**
   * main split for the rule panel
   */
  private JSplitPane rulePanel;

  /**
   * left sub split
   */
  private JSplitPane leftRulePanel;

  /**
   * right sub split
   */
  private JSplitPane rightRulePanel;

  /**
   * panel holding viewer for L
   */
  private JPanel lPanel;

  /**
   * panel holding viewer for K
   */
  private JPanel kPanel;

  /**
   * panel holding viewer for R
   */
  private JPanel rPanel;

  /**
   * panel holding viewer for NAC
   */
  private JPanel nacPanel;

  /**
   * viewer for L
   */
  private PetrinetViewer lViewer;

  /**
   * viewer for K
   */
  private PetrinetViewer kViewer;

  /**
   * viewer for R
   */
  private PetrinetViewer rViewer;

  /**
   * viewer for NAC
   */
  private PetrinetViewer nacViewer;

  /**
   * the PID of the currently displayed rule.
   */
  private int currentId;

  private static final int RULE_PANEL_DEVIDER = 2;
  private static final int LEFT_PANEL_DEVIDER = 4;
  private static final int RIGHT_PANEL_DEVIDER = 4;

  /**
   * Singleton
   */
  private RulePane() {

    this.initializeLeftRulePanel();
    this.initializeRightRulePanel();

    this.rulePanel =
      new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.leftRulePanel,
        this.rightRulePanel);
    this.rulePanel.setOneTouchExpandable(true);
    this.rulePanel.setResizeWeight(.5);

    this.setDividerLocations();
  }

  /**
   * singleton: the instance.
   */
  private static RulePane instance;

  /**
   * singleton: chreates the instance.
   */
  static {
    instance = new RulePane();
  }

  /**
   * singleton: returns the instance
   *
   * @return the instance
   */
  public static RulePane getInstance() {

    return instance;
  }

  /**
   * initializes the left sub split pane
   */
  private void initializeLeftRulePanel() {

    this.nacPanel = new JPanel(new GridLayout(1, 1));
    this.nacPanel.setBorder(RULE_PANE_BORDER_NAC);

    this.lPanel = new JPanel(new GridLayout(1, 1));
    this.lPanel.setBorder(RULE_PANE_BORDER_L);

    this.leftRulePanel =
      new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.nacPanel, this.lPanel);
    this.leftRulePanel.setOneTouchExpandable(true);
    this.leftRulePanel.setResizeWeight(.5);
  }

  /**
   * initializes the right sub split pane
   */
  private void initializeRightRulePanel() {

    this.kPanel = new JPanel(new GridLayout(1, 1));
    this.rPanel = new JPanel(new GridLayout(1, 1));

    this.kPanel.setBorder(RULE_PANE_BORDER_K);
    this.rPanel.setBorder(RULE_PANE_BORDER_R);

    this.rightRulePanel =
      new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.kPanel, this.rPanel);
    this.rightRulePanel.setOneTouchExpandable(true);
    this.rightRulePanel.setResizeWeight(.5);
  }

  /**
   * Adds the rule pane to the given JPanel (frame).
   */
  void addTo(JPanel frame) {

    frame.add(rulePanel);
  }

  /**
   * invokes repainting the viewers.
   */
  void repaint() {

    if (nacViewer != null) {
      nacViewer.repaint();
    }

    lViewer.repaint();
    kViewer.repaint();
    rViewer.repaint();
  }

  /**
   * Replaces the current PetrinetViewers so the new rule is displayed.
   */
  public void displayRule(int ruleId) {

    currentId = ruleId;

    try {

      Layout<INode, IArc> lLayout =
        EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.L);
      Layout<INode, IArc> kLayout =
        EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.K);
      Layout<INode, IArc> rLayout =
        EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.R);

      if (lViewer != null) {
        lViewer.removeFrom(lPanel);
        kViewer.removeFrom(kPanel);
        rViewer.removeFrom(rPanel);
      }

      lViewer = new PetrinetViewer(lLayout, ruleId, RuleNet.L);
      kViewer = new PetrinetViewer(kLayout, ruleId, RuleNet.K);
      rViewer = new PetrinetViewer(rLayout, ruleId, RuleNet.R);

      double nodeSize =
        EngineAdapter.getRuleManipulation().getNodeSize(ruleId);

      lViewer.setNodeSize(nodeSize);
      kViewer.setNodeSize(nodeSize);
      rViewer.setNodeSize(nodeSize);

      lViewer.addTo(lPanel);
      kViewer.addTo(kPanel);
      rViewer.addTo(rPanel);

      MainWindow.getInstance().repaint();
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  public void displayNAC(int ruleId, UUID nacId) {

    // preventive remove old NAC viewer before displaying the new
    if (nacViewer != null) {
      nacPanel.remove(nacViewer);
    }

    try {

      Layout<INode, IArc> nacLayout =
        EngineAdapter.getRuleManipulation().getJungLayout(ruleId, nacId);

      nacViewer = new PetrinetViewer(nacLayout, ruleId, nacId, RuleNet.NAC);
      nacViewer.addTo(nacPanel);

      double nodeSize =
        EngineAdapter.getRuleManipulation().getNodeSize(ruleId);
      nacViewer.setNodeSize(nodeSize);

      MainWindow.getInstance().repaint();

    } catch (Exception e) {
      PopUp.popError(e);
      e.printStackTrace();
    }

  }

  /**
   * Very similar to
   * {@link ITransformation#getMappings(transformation.Rule, INode)}. But it
   * returns the mappings for the one selected node of the user
   *
   * @see {@link PetrinetViewer#currentSelectedNode}
   * @return <code>null</code> if selected node does not exists anymore
   */
  public List<INode> getMappingsOfSelectedNode() {

    return TransformationComponent.getTransformation().getAllNodeRepresentations(
      currentId, getCurrentSelectedNode());
  }

  /**
   * Returns the currently selected node of the rule. <code>null</code> if no
   * node is selected
   *
   * @return
   */
  private INode getCurrentSelectedNode() {

    if (lViewer.getCurrentSelectedNode() != null) {
      return lViewer.getCurrentSelectedNode();
    } else if (kViewer.getCurrentSelectedNode() != null) {
      return kViewer.getCurrentSelectedNode();
    } else if (rViewer.getCurrentSelectedNode() != null) {
      return rViewer.getCurrentSelectedNode();
    } else if ((nacViewer != null)
      && (nacViewer.getCurrentSelectedNode() != null)) {
      return nacViewer.getCurrentSelectedNode();
    } else {
      return null;
    }
  }

  /**
   * Makes the {@link RulePane} display empty space, in case no rule is
   * selected
   */
  public void displayEmpty() {

    if (nacViewer != null) {
      nacViewer.removeFrom(nacPanel);
    }

    if (lViewer != null) {
      lViewer.removeFrom(lPanel);
    }

    if (kViewer != null) {
      kViewer.removeFrom(kPanel);
    }

    if (rViewer != null) {
      rViewer.removeFrom(rPanel);
    }

    MainWindow.getInstance().repaint();
  }

  /**
   * Clears the NAC display.
   */
  public void displayEmptyNAC() {

    if (nacViewer != null) {
      nacViewer.removeFrom(nacPanel);
    }

    MainWindow.getInstance().repaint();
  }

  /**
   * Resets {@link PetrinetViewer#currentSelectedNode} for all viewers in the
   * rule but not for <code>petrinetViewer</code>
   *
   * @param petrinetViewer
   */
  public void deselectBut(PetrinetViewer petrinetViewer) {

    if (nacViewer != petrinetViewer) {
      // if no NAC is present, nacViewer is null
      if (nacViewer != null) {
        nacViewer.setCurrentSelectedNode(null);
      }
    }
    if (lViewer != petrinetViewer) {
      lViewer.setCurrentSelectedNode(null);
    }
    if (kViewer != petrinetViewer) {
      kViewer.setCurrentSelectedNode(null);
    }
    if (rViewer != petrinetViewer) {
      rViewer.setCurrentSelectedNode(null);
    }
  }

  /**
   * Resizes Nodes on all parts of the rule
   *
   * @see {@link PetrinetViewer#resizeNodes(float)}
   * @param factor
   */
  public void resizeNodes(float factor) {

    // if no NAC is present, nacViewer is null
    if (nacViewer != null) {
      nacViewer.resizeNodesOnlyOnThisPartOfRule(factor);
    }

    lViewer.resizeNodesOnlyOnThisPartOfRule(factor);
    kViewer.resizeNodesOnlyOnThisPartOfRule(factor);
    rViewer.resizeNodesOnlyOnThisPartOfRule(factor);
  }

  /**
   * sets the initial locations for the dividers of the {@link JSplitPane}s.
   */
  private void setDividerLocations() {

    this.rulePanel.setDividerLocation(TOTAL_WIDTH / RULE_PANEL_DEVIDER);
    this.leftRulePanel.setDividerLocation(TOTAL_WIDTH / LEFT_PANEL_DEVIDER);
    this.rightRulePanel.setDividerLocation(TOTAL_WIDTH / RIGHT_PANEL_DEVIDER);
  }

}
