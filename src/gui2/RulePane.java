package gui2;

import java.awt.Point;

import javax.swing.JPanel;

import petrinet.Arc;
import petrinet.INode;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import engine.handler.RuleNet;
import exceptions.EngineException;

import static gui2.Style.*;

/** Pane for displaying rules */
class RulePane {
	
	/** Internal JPanel for gui-layouting the petrinet */
	private JPanel rulePanel;
	
	private PetrinetViewer lViewer;
	
	private PetrinetViewer kViewer;
	
	private PetrinetViewer rViewer;
	
	/** Singleton */
	private RulePane() {
		rulePanel = new JPanel();
		rulePanel.setLayout(RULE_PANEL_LAYOUT);
		rulePanel.setBorder(RULE_PANE_BORDER);
		
		dirtyTest();
	}
	
	private void dirtyTest() {
		int ruleId = EngineAdapter.getRuleManipulation().createRule();
//		try{
//			EngineAdapter.getRuleManipulation().createPlace(ruleId, RuleNet.L, new Point(10,10));
//		}catch(EngineException e){
//			PopUp.popError(e);
//			e.printStackTrace();
//		}
		try {
			Layout<INode, Arc> lLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.L);
			Layout<INode, Arc> kLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.K);
			Layout<INode, Arc> rLayout = EngineAdapter.getRuleManipulation().getJungLayout(ruleId, RuleNet.R);

			lViewer = new PetrinetViewer(lLayout, ruleId, RuleNet.L);
			kViewer = new PetrinetViewer(kLayout, ruleId, RuleNet.K);
			rViewer = new PetrinetViewer(rLayout, ruleId, RuleNet.R);
			
			lViewer.setBorder(RULE_PANE_BORDER_L);
			kViewer.setBorder(RULE_PANE_BORDER_K);
			rViewer.setBorder(RULE_PANE_BORDER_R);
			
			rulePanel.add(lViewer);
			rulePanel.add(kViewer);
			rulePanel.add(rViewer);
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	private static RulePane instance;
	
	static{
		instance = new RulePane();
	}
	
	static RulePane getInstance(){
		return instance;
	}
	
	void displayRule(int rId){
		
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

}
