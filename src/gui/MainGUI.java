package gui;

import engine.EditMode;
import engine.Simulation;
import engine.StepListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.Renews;
import petrinetze.impl.Petrinet;
import transformation.IRule;

/*
 * GUI zum anzeigen, bearbeiten und testen von Petrinetzen
 */
public class MainGUI extends javax.swing.JFrame implements StepListener {

    private Projects projects;
    private Set<JInternalFrame> internalFrames;

    /** Creates new form MainGUI */
    public MainGUI() {
        projects = new Projects();
        internalFrames = new HashSet<JInternalFrame>();
        initComponents();
        petrinetTree.setModel(projects.getPetrinetTreeModel());
        initLanguage("de", "DE");
        Project pro = new Project("Petrinetz1",this.getTestPetrinet());
        openProject(pro);
        //test();
    }

    private void test(){
        Project pro = getSelectedProject();
        
    }

    private void openProject(Project pro){
       petrinetTree.setModel(projects.addProject(pro));
       jDesktopPane1.add(pro.getPetrinetFrame());
       pro.getPetrinetFrame().setBounds(40, 20, 360, 250);
       pro.getPetrinetFrame().setVisible(true);
    }
    

    private void createPetrinet(String name){
        Project p = new Project(name);
        openProject(p);
    }

    private void step(){
        Project pro = getActiveProject();
        try{
            pro.getEngine().getSimulation().step();
        }catch(Exception ex){
            Error.create(ex.getMessage());
        }
    }

    private Project getActiveProject() {
        return projects.getProject(jDesktopPane1.getSelectedFrame());
    }

    private void stepXtimes(){
        stepXTimes(getActiveProject(), (Integer) jSpinner1.getValue());
    }

    private void stepXTimes(final Project project, final int times) {
        if (times < 1000) {
            stepXTimesInternal(project, times);
        }
        else {
            final SwingWorker w = new SwingWorker<Void,Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    stepXTimesInternal(project, times);
                    return null;
                }

                @Override
                protected void done() {

                }
            };

            w.execute();
        }
    }

    private void stepXTimesInternal(Project project, int times) {
        final Simulation sim = getActiveProject().getEngine().getSimulation();

        // TODO GUI müsste hier gesperrt werden...
        sim.removeStepListener(this);
        sim.removeStepListener(project);

        int i = 0;
        try {
            for(i = 0; i < times; i++){
                sim.step();
            }
        }
        catch (Exception ex) {
            Error.create(String.format("Kann nicht mehr schalten (nach %d Schritten)", i));  // TODO i18n
        }
        finally {
            // TODO GUI entsperren
            sim.addStepListener(this);
            sim.addStepListener(project);
        }
    }

    private void startSimulation(){
        Project pro = projects.getProject(jDesktopPane1.getSelectedFrame());
        try{
            pro.getEngine().getSimulation().start(200);
        }catch(Exception ex){
            Error.create(ex.getMessage());
        }
    }

    private void stopSimulation(){
        Project pro = projects.getProject(jDesktopPane1.getSelectedFrame());
        try{
            pro.getEngine().getSimulation().stop();
        }catch(Exception ex){
            Error.create(ex.getMessage());
        }
    }

    private Project getSelectedProject(){
        return projects.getProject((String) petrinetTree.getSelectionPath().getPath()[1]);
    }

    private IRule getSelectedRule(){
        //projects.getProject((String) petrinetTree.getSelectionPath().getPath()[1]);
        return null;
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
        saveAsMenuItem.setText(I18n.translate("saveas"));
        exitMenuItem.setText(I18n.translate("exit"));
        editMenu.setText(I18n.translate("edit"));
        cutMenuItem.setText(I18n.translate("cut"));
        copyMenuItem.setText(I18n.translate("copy"));
        pasteMenuItem.setText(I18n.translate("paste"));
        deleteMenuItem.setText(I18n.translate("delete"));
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
        petrinetTree = new javax.swing.JTree();
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
        jDesktopPane1 = new javax.swing.JDesktopPane();
        status = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jToolBar1 = new javax.swing.JToolBar();
        jComboBox1 = new javax.swing.JComboBox();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        languageMenu = new javax.swing.JMenu();
        deutschMenuItem = new javax.swing.JMenuItem();
        EnglishMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        petrinetTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
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

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 204));

        status.setText("Status");
        status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jToolBar1.setRollover(true);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        jToolBar1.add(jComboBox1);

        fileMenu.setText("File");

        newMenuItem.setText("New");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As ...");
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(editToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(playToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 691, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(status)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
       this.createPetrinet("Test");
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void toggleButtonPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonPlaceActionPerformed
        projects.setCreateMode(EditMode.ADD_PLACE);
    }//GEN-LAST:event_toggleButtonPlaceActionPerformed

    private void toggleButtonTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonTransitionActionPerformed
        projects.setCreateMode(EditMode.ADD_TRANSITION);
    }//GEN-LAST:event_toggleButtonTransitionActionPerformed

    private void buttonStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStepActionPerformed
        step();
    }//GEN-LAST:event_buttonStepActionPerformed

    private void buttonStepsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStepsActionPerformed
        stepXtimes();
    }//GEN-LAST:event_buttonStepsActionPerformed

    private void toggleButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonPlayActionPerformed
        if(toggleButtonPlay.isSelected()){
            startSimulation();
        }else{
            stopSimulation();
        }
    }//GEN-LAST:event_toggleButtonPlayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem EnglishMenuItem;
    private javax.swing.JButton buttonStep;
    private javax.swing.JButton buttonSteps;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenuItem deutschMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JToolBar editToolBar;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu languageMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.ButtonGroup petriTools;
    private javax.swing.JTree petrinetTree;
    private javax.swing.JToolBar playToolBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JLabel status;
    private javax.swing.JToggleButton toggleButtonPlace;
    private javax.swing.JToggleButton toggleButtonPlay;
    private javax.swing.JToggleButton toggleButtonTransition;
    // End of variables declaration//GEN-END:variables


    



public IPetrinet getTestPetrinet(){
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
}
