package gui2;

import static gui2.Style.*;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RulePane {
	
	/** singleton instance of this pane */
	private static RulePane instance;
	
	private JPanel RulePanel;

	private JScrollPane LPanel;

	private JScrollPane KPanel;

	private JScrollPane RPanel;
	
	
	/** Returns the singleton instance for this pane */
	public static RulePane getInstance(){
		return instance;
	}
	
//	public static RulePane initiatePetrinetPane(){
//		return getInstance();
//	}
//	
	/* Static constructor that initiates the singleton */
	static {
		instance = new RulePane();
	}
	
	private JPanel getRulePanel(){
		return RulePanel;
	}
	
	/** Private default constructor */
	private RulePane(){
		RulePanel = new JPanel(RULE_PANEL_LAYOUT);
		LPanel = new JScrollPane();
		KPanel = new JScrollPane();
		RPanel = new JScrollPane();
		
		RulePanel.add(LPanel);
		RulePanel.add(KPanel);
		RulePanel.add(RPanel);
		
		layoutRulePane();
	}
	
	private void layoutRulePane() {
		RulePanel.setBorder(RULE_PANE_BORDER);
		
		LPanel.setBorder(RULE_PANE_BORDER_L);
		KPanel.setBorder(RULE_PANE_BORDER_K);
		RPanel.setBorder(RULE_PANE_BORDER_R);
		
	}

	public void addTo(JPanel frame) {
		frame.add(getRulePanel());
	}
	

}
