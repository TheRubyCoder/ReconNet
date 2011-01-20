/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import engine.Engine;
import engine.EngineFactory;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import petrinetze.IPetrinet;
import transformation.IRule;
import transformation.Rule;

/**
 *
 * @author steffen
 */
public class RuleWrapper {

    private String name;
    private IRule rule;
    private Engine Lengine;
    private Engine Kengine;
    private Engine Rengine;
    private JInternalFrame frame;

    public RuleWrapper(String name, IRule rule) {
        this.name = name;
        this.rule = rule;
        this.Kengine = EngineFactory.newFactory().createEngine(rule.K());
        this.Lengine = EngineFactory.newFactory().createEngine(rule.L());
        this.Rengine = EngineFactory.newFactory().createEngine(rule.R());
        this.frame = createFrame();
    }

    public IRule getRule() {
        return rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JInternalFrame getRuleFrame() {
        return frame;
    }

    private JInternalFrame createFrame() {
        JInternalFrame internalRuleFrame = new javax.swing.JInternalFrame();
        internalRuleFrame.setTitle(getName());
        internalRuleFrame.setClosable(true);
        internalRuleFrame.setResizable(true);
        internalRuleFrame.setMaximizable(true);
        
        JPanel ruleLeftPanel = Lengine.getGraphEditor().getGraphPanel();
        JPanel ruleGluePanel = Kengine.getGraphEditor().getGraphPanel();
        JPanel ruleRightPanel = Rengine.getGraphEditor().getGraphPanel();


        GridLayout ruleGrid = new java.awt.GridLayout(1, 3);
        JPanel rulePanel = new javax.swing.JPanel(ruleGrid);
        rulePanel.add(ruleLeftPanel);
        rulePanel.add(ruleGluePanel);
        rulePanel.add(ruleRightPanel);
        
        FlowLayout flowLayoutArrows = new java.awt.FlowLayout();
        JButton left = new javax.swing.JButton("<-");
        JButton right = new javax.swing.JButton("->");
        JPanel arrowPanel = new javax.swing.JPanel(flowLayoutArrows);
        arrowPanel.add(left);
        arrowPanel.add(right);
        
        internalRuleFrame.add(rulePanel,"Center");
        internalRuleFrame.add(arrowPanel,"South");
        internalRuleFrame.pack();
        internalRuleFrame.setLocation(10, 10);
        internalRuleFrame.setVisible(true);
        
        return internalRuleFrame;
    }
}
