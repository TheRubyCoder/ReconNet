package gui;

import static gui.Style.RULE_PANEL_LAYOUT;
import static gui.Style.RULE_PANE_BORDER_K;
import static gui.Style.RULE_PANE_BORDER_L;
import static gui.Style.RULE_PANE_BORDER_R;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import petrinet.Arc;
import petrinet.INode;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.Layout;
import engine.handler.RuleNet;
import exceptions.EngineException;

/** Pane for displaying rules */
class RulePane {

	/** Internal JPanel for gui-layouting the petrinet */
	private JPanel rulePanel;

	private JPanel lBorderPanel;

	private JPanel kBorderPanel;

	private JPanel rBorderPanel;

	private PetrinetViewer lViewer;

	private PetrinetViewer kViewer;

	private PetrinetViewer rViewer;

	private int currentId;

	/** Singleton */
	private RulePane() {
		rulePanel = new JPanel();
		rulePanel.setLayout(RULE_PANEL_LAYOUT);

		lBorderPanel = new JPanel();
		kBorderPanel = new JPanel();
		rBorderPanel = new JPanel();

		rulePanel.add(lBorderPanel);
		rulePanel.add(kBorderPanel);
		rulePanel.add(rBorderPanel);

		lBorderPanel.setBorder(RULE_PANE_BORDER_L);
		lBorderPanel.setLayout(new GridLayout(1, 1));

		kBorderPanel.setBorder(RULE_PANE_BORDER_K);
		kBorderPanel.setLayout(new GridLayout(1, 1));

		rBorderPanel.setBorder(RULE_PANE_BORDER_R);
		rBorderPanel.setLayout(new GridLayout(1, 1));

	}

	private static RulePane instance;

	static {
		instance = new RulePane();
	}

	static RulePane getInstance() {
		return instance;
	}

	/** Adds the rule pane to the given JPanel (frame) */
	void addTo(JPanel frame) {
		frame.add(rulePanel);
	}

	void repaint() {
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
			Layout<INode, Arc> lLayout = EngineAdapter.getRuleManipulation()
					.getJungLayout(ruleId, RuleNet.L);
			Layout<INode, Arc> kLayout = EngineAdapter.getRuleManipulation()
					.getJungLayout(ruleId, RuleNet.K);
			Layout<INode, Arc> rLayout = EngineAdapter.getRuleManipulation()
					.getJungLayout(ruleId, RuleNet.R);
			if (lViewer != null) {
				lViewer.removeFrom(lBorderPanel);
				kViewer.removeFrom(kBorderPanel);
				rViewer.removeFrom(rBorderPanel);
			}
			lViewer = new PetrinetViewer(lLayout, ruleId, RuleNet.L);
			kViewer = new PetrinetViewer(kLayout, ruleId, RuleNet.K);
			rViewer = new PetrinetViewer(rLayout, ruleId, RuleNet.R);

			lViewer.addTo(lBorderPanel);
			kViewer.addTo(kBorderPanel);
			rViewer.addTo(rBorderPanel);

			MainWindow.getInstance().repaint();
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

/**
	 * Very similar to {@link ITransformation#getMappings(transformation.Rule, INode)}. But it returns the mappings for the one selected node of the user
	 * @see {@link PetrinetViewer#currentSelectedNode}
	 * @return <code>null</code> if selected node does not exists anymore
	 */
	public List<INode> getMappingsOfSelectedNode() {
		return TransformationComponent.getTransformation().getMappings(
				currentId, getCurrentSelectedNode());
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

	public void displayEmpty() {
		if (lViewer != null) {
			lViewer.removeFrom(lBorderPanel);
			kViewer.removeFrom(kBorderPanel);
			rViewer.removeFrom(rBorderPanel);
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
		if(lViewer != petrinetViewer){
			lViewer.currentSelectedNode = null;
		}
		if(kViewer != petrinetViewer){
			kViewer.currentSelectedNode = null;
		}
		if(rViewer != petrinetViewer){
			rViewer.currentSelectedNode = null;
		}
	}

	/**
	 * Resizes Nodes on all parts of the rule
	 * @see {@link PetrinetViewer#resizeNodes(float)}
	 * @param factor
	 */
	public void resizeNodes(float factor) {
		lViewer.resizeNodesOnlyOnThisPartOfRule(factor);
		kViewer.resizeNodesOnlyOnThisPartOfRule(factor);
		rViewer.resizeNodesOnlyOnThisPartOfRule(factor);
	}

}
