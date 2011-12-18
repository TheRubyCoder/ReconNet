package gui2;

import static gui2.Style.*;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/** The panel for displaying rules */
public class RulePane {
	
	/** singleton instance of this pane */
	private static RulePane instance;
	
	/** The mail rule panel*/
	private JPanel RulePanel;

	/** the left panel in main rule panel*/
	private JScrollPane LPanel;

	/** the middle (k) panel in main rule panel*/
	private JScrollPane KPanel;

	/** the right panel in main rule panel*/
	private JScrollPane RPanel;
	
	
	/** Returns the singleton instance for this pane */
	public static RulePane getInstance(){
		return instance;
	}
	
	/** Returns the only instance of the the Rulepanel */
	static {
		instance = new RulePane();
	}
	
	/** Private Constructor that configures the rulepanel */
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
	
	/**
	 * Layout the rulepanel and the 3 panes within 
	 */
	private void layoutRulePane() {
		RulePanel.setBorder(RULE_PANE_BORDER);
		
		LPanel.setBorder(RULE_PANE_BORDER_L);
		KPanel.setBorder(RULE_PANE_BORDER_K);
		RPanel.setBorder(RULE_PANE_BORDER_R);
		
	}

	/** Adds the rule panel to the given JPanel (frame) */
	public void addTo(JPanel frame) {
		frame.add(getRulePanel());
	}
	

}
