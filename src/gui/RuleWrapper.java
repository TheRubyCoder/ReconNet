/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.Engine;
import engine.EngineFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import transformation.IRule;

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

    public RuleWrapper(String name,IRule rule){
        this.name = name;
        this.rule = rule;
        this.Kengine = EngineFactory.newFactory().createEngine(rule.K());
        this.Lengine = EngineFactory.newFactory().createEngine(rule.L());
        this.Rengine = EngineFactory.newFactory().createEngine(rule.R());
        this.frame = createFrame();
    }

    public IRule getRule(){return rule;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}


    public JInternalFrame createFrame(){
        JInternalFrame internalRuleFrame = new javax.swing.JInternalFrame();
        JPanel ruleLeftPanel = Lengine.getGraphEditor().getGraphPanel();
        JPanel ruleGluePanel = Kengine.getGraphEditor().getGraphPanel();
        JPanel ruleRightPanel = Rengine.getGraphEditor().getGraphPanel();
        JToolBar jToolBar2 = new javax.swing.JToolBar();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();

        internalRuleFrame.setClosable(true);
        internalRuleFrame.setIconifiable(true);
        internalRuleFrame.setMaximizable(true);
        internalRuleFrame.setResizable(true);
        internalRuleFrame.setTitle(this.name);
        internalRuleFrame.setVisible(true);

        //ruleLeftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("L"));
        //ruleGluePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("K"));
        //ruleRightPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("R"));

        //javax.swing.GroupLayout ruleLeftPanelLayout = new javax.swing.GroupLayout(ruleLeftPanel);
        //ruleLeftPanel.setLayout(ruleLeftPanelLayout);
        //ruleLeftPanelLayout.setHorizontalGroup(
        //    ruleLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        //    .addGap(0, 76, Short.MAX_VALUE)
        //);
       // ruleLeftPanelLayout.setVerticalGroup(
       //     ruleLeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
       //     .addGap(0, 193, Short.MAX_VALUE)
       // );

        

        javax.swing.GroupLayout ruleGluePanelLayout = new javax.swing.GroupLayout(ruleGluePanel);
        ruleGluePanel.setLayout(ruleGluePanelLayout);
        ruleGluePanelLayout.setHorizontalGroup(
            ruleGluePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 94, Short.MAX_VALUE)
        );
        ruleGluePanelLayout.setVerticalGroup(
            ruleGluePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        

        javax.swing.GroupLayout ruleRightPanelLayout = new javax.swing.GroupLayout(ruleRightPanel);
        ruleRightPanel.setLayout(ruleRightPanelLayout);
        ruleRightPanelLayout.setHorizontalGroup(
            ruleRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );
        ruleRightPanelLayout.setVerticalGroup(
            ruleRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        jToolBar2.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/arrow_left.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/arrow_right.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton2);

        javax.swing.GroupLayout internalRuleFrameLayout = new javax.swing.GroupLayout(internalRuleFrame.getContentPane());
        internalRuleFrame.getContentPane().setLayout(internalRuleFrameLayout);
        internalRuleFrameLayout.setHorizontalGroup(
            internalRuleFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, internalRuleFrameLayout.createSequentialGroup()
                .addComponent(ruleLeftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ruleGluePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ruleRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );
        internalRuleFrameLayout.setVerticalGroup(
            internalRuleFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalRuleFrameLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalRuleFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ruleRightPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ruleGluePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ruleLeftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        this.frame = internalRuleFrame;
        return internalRuleFrame;
    }

}
