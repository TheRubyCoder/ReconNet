package gui;

import static gui.Style.RULE_PANEL_LAYOUT;
import static gui.Style.RULE_PANE_BORDER_K;
import static gui.Style.RULE_PANE_BORDER_L;
import static gui.Style.RULE_PANE_BORDER_R;

import java.awt.GridLayout;

import javax.swing.JPanel;

import petrinet.Arc;
import petrinet.INode;
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
	
	static{
		instance = new RulePane();
	}
	
	static RulePane getInstance(){
		return instance;
	}
	
	/** Adds the rule pane to the given JPanel (frame) */
	void addTo(JPanel frame) {
		frame.add(rulePanel);
	}
	
	void repaint(){
		lViewer.repaint();
		kViewer.repaint();
		rViewer.repaint();
	}
	
	/**
	 * Replaces the current PetrinetViewers so the new rule is displayed.
	 */
	public void displayRule(int ruleId) {
		try {
			Layout<INode, Arc> lLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.L);
			Layout<INode, Arc> kLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.K);
			Layout<INode, Arc> rLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.R);
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

	public void displayEmpty() {
		if (lViewer != null) {
			lViewer.removeFrom(lBorderPanel);
			kViewer.removeFrom(kBorderPanel);
			rViewer.removeFrom(rBorderPanel);
			MainWindow.getInstance().repaint();
		}
	}

}
