package gui;

import engine.EditMode;
import engine.EngineFactory;
import engine.Engine;
import gui.tableModels.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;
import transformation.Rule;

/**
 *
 * @author steffen
 */
public class RuleWrapper {

    private String name;
    private Rule rule;
    private Engine Lengine;
    private Engine Kengine;
    private Engine Rengine;
    private Set<INode> selected;
    private JInternalFrame frame;

    public RuleWrapper(String name, Rule rule,final JTable table) {
        this.name = name;
        this.rule = rule;
        this.Kengine = EngineFactory.newFactory().createEngine(rule.getK());
        this.Lengine = EngineFactory.newFactory().createEngine(rule.getL());
        this.Rengine = EngineFactory.newFactory().createEngine(rule.getR());
        Kengine.getGraphEditor().getGraphPanel().addPropertyChangeListener("pickedNodes", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    Set<INode> nodes = (Set<INode>) evt.getNewValue();
                    selected = nodes;
                    INode node = nodes.iterator().next();
                    if (node instanceof Arc) {
                        table.setModel(new ArcTableModel((Arc) node));
                    } else if (node instanceof Transition) {
                        table.setModel(new TransitionTableModel((Transition) node));
                    } else if (node instanceof Place) {
                        table.setModel(new PlaceTalbeModel((Place) node));
                    }
                }
        });
        Rengine.getGraphEditor().getGraphPanel().addPropertyChangeListener("pickedNodes", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    Set<INode> nodes = (Set<INode>) evt.getNewValue();
                    selected = nodes;
                    INode node = nodes.iterator().next();
                    if (node instanceof Arc) {
                        table.setModel(new ArcTableModel((Arc) node));
                    } else if (node instanceof Transition) {
                        table.setModel(new TransitionTableModel((Transition) node));
                    } else if (node instanceof Place) {
                        table.setModel(new PlaceTalbeModel((Place) node));
                    }
                }
        });
        Lengine.getGraphEditor().getGraphPanel().addPropertyChangeListener("pickedNodes", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    Set<INode> nodes = (Set<INode>) evt.getNewValue();
                    selected = nodes;
                    INode node = nodes.iterator().next();
                    if (node instanceof Arc) {
                        table.setModel(new ArcTableModel((Arc) node));
                    } else if (node instanceof Transition) {
                        table.setModel(new TransitionTableModel((Transition) node));
                    } else if (node instanceof Place) {
                        table.setModel(new PlaceTalbeModel((Place) node));
                    }
                }
        });
        this.frame = createFrame();
        
    }
    
    public void setEditMode(EditMode mode){
        Kengine.getGraphEditor().setEditMode(mode);
        Lengine.getGraphEditor().setEditMode(mode);
        Rengine.getGraphEditor().setEditMode(mode);
    }

    public Rule getRule() {
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
        ruleLeftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel ruleGluePanel = Kengine.getGraphEditor().getGraphPanel();
        ruleGluePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel ruleRightPanel = Rengine.getGraphEditor().getGraphPanel();
        ruleRightPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        GridLayout ruleGrid = new java.awt.GridLayout(1, 3);
        JPanel rulePanel = new javax.swing.JPanel(ruleGrid);
        rulePanel.add(ruleLeftPanel);
        rulePanel.add(ruleGluePanel);
        rulePanel.add(ruleRightPanel);
        
        FlowLayout flowLayoutArrows = new java.awt.FlowLayout();
        JButton left = new javax.swing.JButton();
        left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/arrow_left.png")));
        left.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               for(INode node : selected){
                   rule.fromKtoL(node);
               }
            }
        });
        JButton right = new javax.swing.JButton();
        right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/arrow_right.png")));
        right.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               for(INode node : selected){
                   rule.fromKtoR(node);
               }
            }
        });
        JPanel arrowPanel = new javax.swing.JPanel(flowLayoutArrows);
        arrowPanel.add(left);
        arrowPanel.add(right);

        JPanel LkrPanel = new javax.swing.JPanel();
        LkrPanel.add(new JLabel("L"));
        LkrPanel.add(new JLabel("K"));
        LkrPanel.add(new JLabel("R"));

        internalRuleFrame.add(LkrPanel,"North");
        internalRuleFrame.add(rulePanel,"Center");
        internalRuleFrame.add(arrowPanel,"South");
        internalRuleFrame.pack();
        internalRuleFrame.setLocation(10, 10);
        internalRuleFrame.setVisible(true);
        
        return internalRuleFrame;
    }
}
