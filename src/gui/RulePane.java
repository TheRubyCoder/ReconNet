/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package gui;

import static gui.Style.RULE_PANE_BORDER_K;
import static gui.Style.RULE_PANE_BORDER_L;
import static gui.Style.RULE_PANE_BORDER_NAC;
import static gui.Style.RULE_PANE_BORDER_R;
import static gui.Style.TOTAL_WIDTH;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import petrinet.model.IArc;
import petrinet.model.INode;
import transformation.ITransformation;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.Layout;
import engine.handler.RuleNet;
import exceptions.EngineException;

/** Pane for displaying rules */
public class RulePane {

    /** Internal JPanel for gui-layouting the petrinet */
    private JSplitPane rulePanel;

    private JPanel lPanel;

    private JPanel kPanel;

    private JPanel rPanel;

    private JPanel nacPanel;

    private PetrinetViewer lViewer;

    private PetrinetViewer kViewer;

    private PetrinetViewer rViewer;

    private PetrinetViewer nacViewer;

    private int currentId;

    private JSplitPane leftRulePanel;

    private JSplitPane rightRulePanel;

    /** Singleton */
    private RulePane() {
        this.initializeLeftRulePanel();
        this.initializeRightRulePanel();

        this.rulePanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.leftRulePanel, this.rightRulePanel);
        this.rulePanel.setOneTouchExpandable(true);
        this.rulePanel.setResizeWeight(.5);

        this.setDividerLocations();
    }

    private static RulePane instance;

    static {
        instance = new RulePane();
    }

    public static RulePane getInstance() {
        return instance;
    }

    private void initializeLeftRulePanel() {
        this.nacPanel = new JPanel(new GridLayout(1, 1));
        this.lPanel = new JPanel(new GridLayout(1, 1));

        this.nacPanel.setBorder(RULE_PANE_BORDER_NAC);
        this.lPanel.setBorder(RULE_PANE_BORDER_L);

        this.leftRulePanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.nacPanel, this.lPanel);
        this.leftRulePanel.setOneTouchExpandable(true);
        this.leftRulePanel.setResizeWeight(.5);
    }

    private void initializeRightRulePanel() {
        this.kPanel = new JPanel(new GridLayout(1, 1));
        this.rPanel = new JPanel(new GridLayout(1, 1));

        this.kPanel.setBorder(RULE_PANE_BORDER_K);
        this.rPanel.setBorder(RULE_PANE_BORDER_R);

        this.rightRulePanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.kPanel, this.rPanel);
        this.rightRulePanel.setOneTouchExpandable(true);
        this.rightRulePanel.setResizeWeight(.5);
    }

    /** Adds the rule pane to the given JPanel (frame) */
    void addTo(JPanel frame) {
        frame.add(rulePanel);
    }

    void repaint() {
        nacViewer.repaint();
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
            // TODO:
            Layout<INode, IArc> nacLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.L);
            Layout<INode, IArc> lLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.L);
            Layout<INode, IArc> kLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.K);
            Layout<INode, IArc> rLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.R);
            if (lViewer != null) {
                nacViewer.removeFrom(nacPanel);
                lViewer.removeFrom(lPanel);
                kViewer.removeFrom(kPanel);
                rViewer.removeFrom(rPanel);
            }
            nacViewer = new PetrinetViewer(nacLayout, ruleId, RuleNet.L);
            lViewer = new PetrinetViewer(lLayout, ruleId, RuleNet.L);
            kViewer = new PetrinetViewer(kLayout, ruleId, RuleNet.K);
            rViewer = new PetrinetViewer(rLayout, ruleId, RuleNet.R);

            double nodeSize = EngineAdapter.getRuleManipulation().getNodeSize(ruleId);
            nacViewer.setNodeSize(nodeSize);
            lViewer.setNodeSize(nodeSize);
            kViewer.setNodeSize(nodeSize);
            rViewer.setNodeSize(nodeSize);

            nacViewer.addTo(nacPanel);
            lViewer.addTo(lPanel);
            kViewer.addTo(kPanel);
            rViewer.addTo(rPanel);

            MainWindow.getInstance().repaint();
        } catch (EngineException e) {
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
        return TransformationComponent.getTransformation().getMappings(currentId, getCurrentSelectedNode());
    }

    /**
     * Returns the currently selected node of the rule. <code>null</code> if no
     * node is selected
     * 
     * @return
     */
    private INode getCurrentSelectedNode() {
        if (lViewer.currentSelectedNode != null) {
            return lViewer.currentSelectedNode;
        } else if (kViewer.currentSelectedNode != null) {
            return kViewer.currentSelectedNode;
        } else {
            return rViewer.currentSelectedNode;
        }
    }

    /**
     * Makes the {@link RulePane} display empty space, in case no rule is
     * selected
     */
    public void displayEmpty() {
        if (lViewer != null) {
            nacViewer.removeFrom(nacPanel);
            lViewer.removeFrom(lPanel);
            kViewer.removeFrom(kPanel);
            rViewer.removeFrom(rPanel);
            MainWindow.getInstance().repaint();
        }
    }

    /**
     * Resets {@link PetrinetViewer#currentSelectedNode} for all viewers in the
     * rule but not for <code>petrinetViewer</code>
     * 
     * @param petrinetViewer
     */
    public void deselectBut(PetrinetViewer petrinetViewer) {
        if (nacViewer != petrinetViewer) {
            nacViewer.currentSelectedNode = null;
        }
        if (lViewer != petrinetViewer) {
            lViewer.currentSelectedNode = null;
        }
        if (kViewer != petrinetViewer) {
            kViewer.currentSelectedNode = null;
        }
        if (rViewer != petrinetViewer) {
            rViewer.currentSelectedNode = null;
        }
    }

    /**
     * Resizes Nodes on all parts of the rule
     * 
     * @see {@link PetrinetViewer#resizeNodes(float)}
     * @param factor
     */
    public void resizeNodes(float factor) {
        nacViewer.resizeNodesOnlyOnThisPartOfRule(factor);
        lViewer.resizeNodesOnlyOnThisPartOfRule(factor);
        kViewer.resizeNodesOnlyOnThisPartOfRule(factor);
        rViewer.resizeNodesOnlyOnThisPartOfRule(factor);
    }

    private void setDividerLocations() {
        this.rulePanel.setDividerLocation(TOTAL_WIDTH / 2);
        this.leftRulePanel.setDividerLocation(TOTAL_WIDTH / 4);
        this.rightRulePanel.setDividerLocation(TOTAL_WIDTH / 4);
    }

}
