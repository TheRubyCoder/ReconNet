package gui;

import engine.EditMode;
import engine.Engine;
import engine.EngineFactory;
import engine.Simulation;
import engine.StepListener;
import gui.PetrinetTreeModel.PetrinetNode;
import gui.PetrinetTreeModel.RuleNode;
import gui.PetrinetTreeModel.RulesNode;
import gui.TableModels.*;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import petrinetze.IArc;
import petrinetze.INode;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.Renews;
import petrinetze.impl.Petrinet;
import petrinetze.impl.RenewCount;
import transformation.IRule;
import transformation.Rule;

/*
 * GUI zum anzeigen, bearbeiten und testen von Petrinetzen
 */
public class MainGUI extends javax.swing.JFrame implements StepListener {

    private PetrinetTree petrinetTree;
    private final Action addPetrinetAction = new AbstractAction("Neues Petrinetz") {

        @Override
        public void actionPerformed(ActionEvent ae) {
            createPetrinet();
        }
    };
    private final Action showPetrinetAction = new AbstractAction("Anzeigen") {

        public void actionPerformed(ActionEvent e) {
            openPetrinet();
        }
    };
    private final Action addRuleAction = new AbstractAction("Neue Regel") {

        public void actionPerformed(ActionEvent e) {
            addRule();
        }
    };
    private final Action showRuleAction = new AbstractAction("Anzeigen") {

        public void actionPerformed(ActionEvent e) {
            JInternalFrame frame = ((RuleNode)petrinetTree.getSelectionPath().getLastPathComponent()).getWrapper().getRuleFrame();
            desktop.add(frame);
            frame.setBounds(40, 20, 360, 250);
            frame.setVisible(true);
            frame.requestFocusInWindow();
        }
    };
    private final Action transfromPetrinetAction = new AbstractAction("Anwenden") {

        public void actionPerformed(ActionEvent e) {
            PetrinetNode node = (PetrinetNode)petrinetTree.getSelectionPath().getPathComponent(1);
            node.getEngine().transform(((RuleNode)petrinetTree.getSelectionPath().getLastPathComponent()).getRule());
        }
    };

    /** Creates new form MainGUI */
    public MainGUI() {
        initTree();
        initComponents();
        initLanguage("de", "DE");
    }

    private PetrinetNode activeNode() {
        final PetrinetFrame frame = (PetrinetFrame) desktop.getSelectedFrame();

        return frame == null ? null : frame.getNode();
    }

    private void setVisible(Iterable<? extends JMenuItem> items, boolean visible) {
        for (JMenuItem item : items) {
            item.setVisible(visible);
        }
    }

    private void initTree() {
        petrinetTree = new PetrinetTree();

        JPopupMenu menu = new JPopupMenu();

        final Set<JMenuItem> petrinetsItems = new HashSet<JMenuItem>();
        final Set<JMenuItem> petrinetItems = new HashSet<JMenuItem>();
        final Set<JMenuItem> rulesItems = new HashSet<JMenuItem>();
        final Set<JMenuItem> ruleItems = new HashSet<JMenuItem>();
        petrinetsItems.add(menu.add(addPetrinetAction));
        petrinetItems.add(menu.add(showPetrinetAction));
        petrinetItems.add(menu.add(addRuleAction));
        ruleItems.add(menu.add(showRuleAction));
        ruleItems.add(menu.add(transfromPetrinetAction));

        setVisible(petrinetsItems, false);

        petrinetTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                final Object selected = tse.getNewLeadSelectionPath().getLastPathComponent();

                if (selected == petrinetTree.getModel().getRoot()) {
                    setVisible(petrinetsItems, true);
                    setVisible(petrinetItems, false);
                    setVisible(rulesItems, false);
                    setVisible(ruleItems, false);
                } else if (selected instanceof PetrinetNode) {
                    setVisible(petrinetItems, true);
                    setVisible(petrinetsItems, false);
                    setVisible(rulesItems, false);
                    setVisible(ruleItems, false);
                } else if (selected instanceof RulesNode) {
                    setVisible(petrinetsItems, false);
                    setVisible(petrinetItems, false);
                    setVisible(rulesItems, true);
                    setVisible(ruleItems, false);
                } else if (selected instanceof RuleNode) {
                    setVisible(petrinetsItems, false);
                    setVisible(petrinetItems, false);
                    setVisible(rulesItems, false);
                    setVisible(ruleItems, true);
                } else {
                    setVisible(petrinetsItems, false);
                }
            }
        });

        petrinetTree.setComponentPopupMenu(menu);
    }

    private void step() {
        try {
            activeNode().getEngine().getSimulation().step();
        } catch (Exception ex) {
            Error.create(ex.getMessage());
        }
    }

    private void stepXtimes() {
        stepXTimes(activeNode(), (Integer) jSpinner1.getValue());
    }

    private void stepXTimes(final PetrinetNode node, final int times) {
        if (times < 1000) {
            stepXTimesInternal(node, times);
        } else {
            final SwingWorker w = new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    stepXTimesInternal(node, times);
                    return null;
                }

                @Override
                protected void done() {
                }
            };

            w.execute();
        }
    }

    private void stepXTimesInternal(PetrinetNode node, int times) {
        final Simulation sim = node.getEngine().getSimulation();

        // TODO GUI m��?sste hier gesperrt werden...
        sim.removeStepListener(this);

        // TODO muss node benachrichtigt werden ?
        // sim.removeStepListener(project);

        int i = 0;
        try {
            for (i = 0; i < times; i++) {
                sim.step();
            }
        } catch (Exception ex) {
            Error.create(String.format("Kann nicht mehr schalten (nach %d Schritten)", i));  // TODO i18n
        } finally {
            // TODO GUI entsperren
            sim.addStepListener(this);

            // TODO s.o.
            // sim.addStepListener(project);
        }
    }

    private void startSimulation() {
        try {
            activeNode().getEngine().getSimulation().start(200);
        } catch (Exception ex) {
            Error.create(ex.getMessage());
        }
    }

    private void stopSimulation() {
        try {
            activeNode().getEngine().getSimulation().stop();
        } catch (Exception ex) {
            Error.create(ex.getMessage());
        }
    }

    private void initLanguage(String lang, String lang2) {
        I18n.setLocale(lang, lang2);
        toggleButtonPlace.setText(I18n.translate("place"));
        toggleButtonTransition.setText(I18n.translate("transition"));
        buttonStep.setText(I18n.translate("step"));
        buttonSteps.setText(I18n.translate("steps"));
        toggleButtonPlay.setText(I18n.translate("play"));
        status.setText(I18n.translate("status"));
        fileMenu.setText(I18n.translate("file"));
        openMenuItem.setText(I18n.translate("open"));
        saveMenuItem.setText(I18n.translate("save"));
        exitMenuItem.setText(I18n.translate("exit"));
        languageMenu.setText(I18n.translate("language"));
        deutschMenuItem.setText(I18n.translate("german"));
        EnglishMenuItem.setText(I18n.translate("english"));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        petriTools = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JTree petrinetTree = this.petrinetTree;
        editToolBar = new javax.swing.JToolBar();
        toggleButtonPlace = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        toggleButtonTransition = new javax.swing.JToggleButton();
        playToolBar = new javax.swing.JToolBar();
        buttonStep = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jSpinner1 = new javax.swing.JSpinner();
        buttonSteps = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        toggleButtonPlay = new javax.swing.JToggleButton();
        desktop = new javax.swing.JDesktopPane();
        progressBar = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        status = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        languageMenu = new javax.swing.JMenu();
        deutschMenuItem = new javax.swing.JMenuItem();
        EnglishMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        petrinetTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                petrinetTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(petrinetTree);

        editToolBar.setRollover(true);

        petriTools.add(toggleButtonPlace);
        toggleButtonPlace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/place.png"))); // NOI18N
        toggleButtonPlace.setFocusable(false);
        toggleButtonPlace.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toggleButtonPlace.setMargin(new java.awt.Insets(4, 14, 4, 14));
        toggleButtonPlace.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toggleButtonPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPlaceActionPerformed(evt);
            }
        });
        editToolBar.add(toggleButtonPlace);
        editToolBar.add(jSeparator3);

        petriTools.add(toggleButtonTransition);
        toggleButtonTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/transition.png"))); // NOI18N
        toggleButtonTransition.setFocusable(false);
        toggleButtonTransition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toggleButtonTransition.setMargin(new java.awt.Insets(4, 14, 4, 14));
        toggleButtonTransition.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toggleButtonTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonTransitionActionPerformed(evt);
            }
        });
        editToolBar.add(toggleButtonTransition);

        playToolBar.setRollover(true);
        playToolBar.setMaximumSize(new java.awt.Dimension(32925, 32769));

        buttonStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/step.png"))); // NOI18N
        buttonStep.setFocusable(false);
        buttonStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonStep.setMargin(new java.awt.Insets(4, 14, 4, 14));
        buttonStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStepActionPerformed(evt);
            }
        });
        playToolBar.add(buttonStep);
        playToolBar.add(jSeparator1);

        jSpinner1.setMaximumSize(new java.awt.Dimension(29, 20));
        playToolBar.add(jSpinner1);

        buttonSteps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/step.png"))); // NOI18N
        buttonSteps.setMargin(new java.awt.Insets(4, 14, 4, 14));
        buttonSteps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStepsActionPerformed(evt);
            }
        });
        playToolBar.add(buttonSteps);
        playToolBar.add(jSeparator2);

        toggleButtonPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resource/play.png"))); // NOI18N
        toggleButtonPlay.setFocusable(false);
        toggleButtonPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toggleButtonPlay.setMargin(new java.awt.Insets(4, 14, 4, 14));
        toggleButtonPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toggleButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPlayActionPerformed(evt);
            }
        });
        playToolBar.add(toggleButtonPlay);

        desktop.setBackground(new java.awt.Color(204, 204, 204));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        status.setText("Status");
        status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        fileMenu.setText("File");

        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        languageMenu.setText("Language");

        deutschMenuItem.setText("Deutsch");
        deutschMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deutschMenuItemActionPerformed(evt);
            }
        });
        languageMenu.add(deutschMenuItem);

        EnglishMenuItem.setText("English");
        EnglishMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnglishMenuItemActionPerformed(evt);
            }
        });
        languageMenu.add(EnglishMenuItem);

        menuBar.add(languageMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                    .addComponent(status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(playToolBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(editToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void deutschMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deutschMenuItemActionPerformed
        initLanguage("de", "DE");
    }//GEN-LAST:event_deutschMenuItemActionPerformed

    private void EnglishMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnglishMenuItemActionPerformed
        initLanguage("en", "US");
    }//GEN-LAST:event_EnglishMenuItemActionPerformed

    private void toggleButtonPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonPlaceActionPerformed
        petrinetTree.setEditMode(EditMode.ADD_PLACE);
    }//GEN-LAST:event_toggleButtonPlaceActionPerformed

    private void toggleButtonTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonTransitionActionPerformed
        petrinetTree.setEditMode(EditMode.ADD_TRANSITION);
    }//GEN-LAST:event_toggleButtonTransitionActionPerformed

    private void buttonStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStepActionPerformed
        step();
    }//GEN-LAST:event_buttonStepActionPerformed

    private void buttonStepsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStepsActionPerformed
        stepXtimes();
    }//GEN-LAST:event_buttonStepsActionPerformed

    private void toggleButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonPlayActionPerformed
        if (toggleButtonPlay.isSelected()) {
            startSimulation();
        } else {
            stopSimulation();
        }
    }//GEN-LAST:event_toggleButtonPlayActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void petrinetTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_petrinetTreeValueChanged
        Object o = evt.getNewLeadSelectionPath().getLastPathComponent();
        if(o instanceof PetrinetNode){
            jTable1.setModel(new PetrinetTableModel((PetrinetNode) o));
        }else if(o instanceof RuleNode){
            jTable1.setModel(new RuleTableModel((RuleNode)o));
        }
    }//GEN-LAST:event_petrinetTreeValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem EnglishMenuItem;
    private javax.swing.JButton buttonStep;
    private javax.swing.JButton buttonSteps;
    private javax.swing.JMenuItem deutschMenuItem;
    private javax.swing.JToolBar editToolBar;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenu languageMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.ButtonGroup petriTools;
    private javax.swing.JToolBar playToolBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JLabel status;
    private javax.swing.JToggleButton toggleButtonPlace;
    private javax.swing.JToggleButton toggleButtonPlay;
    private javax.swing.JToggleButton toggleButtonTransition;
    private javax.swing.JDesktopPane desktop;
    // End of variables declaration//GEN-END:variables

    public IPetrinet getTestPetrinet() {
        Petrinet petrinet = new Petrinet();
        IPlace p1 = petrinet.createPlace("Stelle1");
        p1.setMark(1);
        IPlace p2 = petrinet.createPlace("Stelle2");
        p2.setMark(1);
        IPlace p3 = petrinet.createPlace("Stelle3");
        p3.setMark(1);
        ITransition t1 = petrinet.createTransition("t1", Renews.IDENTITY);
        petrinet.createArc("", p1, t1);
        petrinet.createArc("", t1, p2);
        ITransition t2 = petrinet.createTransition("t2", Renews.IDENTITY);
        petrinet.createArc("", p2, t2);
        petrinet.createArc("", t2, p3);
        ITransition t3 = petrinet.createTransition("t3", Renews.IDENTITY);
        petrinet.createArc("", p3, t3);
        petrinet.createArc("", t3, p1);
        return petrinet;
    }

    public IRule createTestRule() {
        IRule rule1 = new Rule();
        //L von r1
        IPlace p1 = rule1.L().createPlace("Wecker ein");
        IPlace p2 = rule1.K().createPlace("Wecker ein");
        p2.setMark(1);
        IPlace p3 = rule1.K().createPlace("");
        IPlace p4 = rule1.K().createPlace("");
        IPlace p5 = rule1.K().createPlace("Wecker aus");
        IPlace p6 = rule1.L().createPlace("Wecker aus");
        ITransition t1 = rule1.L().createTransition("", new RenewCount());
        rule1.L().createArc("", p1, t1);
        rule1.L().createArc("", t1, rule1.fromKtoL(p4));
        ITransition t2 = rule1.L().createTransition("", new RenewCount());
        rule1.L().createArc("", rule1.fromKtoL(p3), t2);
        rule1.L().createArc("", t2, p6);
        ITransition t3 = rule1.R().createTransition("", new RenewCount());
        rule1.R().createArc("", rule1.fromKtoR(p2), t3);
        rule1.R().createArc("", t3, rule1.fromKtoR(p4));
        ITransition t4 = rule1.R().createTransition("", new RenewCount());
        rule1.R().createArc("", rule1.fromKtoR(p3), t4);
        rule1.R().createArc("", t4, rule1.fromKtoR(p5));
        return rule1;
    }

    public void stepped(Simulation s) {
    }

    public void started(Simulation s) {
        toggleButtonTransition.setEnabled(false);
        toggleButtonPlace.setEnabled(false);
    }

    public void stopped(Simulation s) {
        toggleButtonTransition.setEnabled(true);
        toggleButtonPlace.setEnabled(true);
    }

    private void createPetrinet() {
        final String input =
                JOptionPane.showInputDialog(
                this,
                "Bitte geben Sie einen Namen für das Petrinetz ein", "Neues Petrinetz");

        if (input != null) {
            IPetrinet pn = new Petrinet();
            Engine engine = EngineFactory.newFactory().createEngine(pn);
            PetrinetNode node = petrinetTree.addPetrinet(input, engine);
            engine.getGraphEditor().getGraphPanel().addPropertyChangeListener("pickedNodes", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    Set<INode> nodes = (Set<INode>) evt.getNewValue();
                    INode node = nodes.iterator().next();
                    if (node instanceof IArc) {
                        jTable1.setModel(new ArcTableModel((IArc) node));
                    } else if (node instanceof ITransition) {
                        jTable1.setModel(new TransitionTableModel((ITransition) node));
                    } else if (node instanceof IPlace) {
                        jTable1.setModel(new PlaceTalbeModel((IPlace) node));
                    }
                }
            });
            PetrinetFrame frame = new PetrinetFrame(node);
            desktop.add(frame);
            frame.setBounds(40, 20, 360, 250);
            frame.setVisible(true);
        }
    }

    private void openPetrinet() {
        PetrinetNode node = (PetrinetNode) petrinetTree.getSelectionPath().getLastPathComponent();
        PetrinetFrame frame = new PetrinetFrame(node);
        desktop.add(frame);
        frame.setBounds(40, 20, 360, 250);
        frame.setVisible(true);
    }

    private void addRule() {
        final String input =
                JOptionPane.showInputDialog(
                this,
                "Bitte geben Sie einen Namen für die Regel ein", "Neue Regel");

        if (input != null) {
            IRule rule = new Rule();
            PetrinetNode node = (PetrinetNode) petrinetTree.getSelectionPath().getLastPathComponent();
            RuleWrapper wrapper = new RuleWrapper(input, rule,jTable1);
            node.addRule(input, wrapper);
            JInternalFrame frame = wrapper.getRuleFrame();
            desktop.add(frame);
            frame.setBounds(40, 20, 360, 250);
            frame.setVisible(true);
            frame.requestFocusInWindow();
        }
    }

}
