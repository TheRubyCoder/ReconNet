/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.Simulation;
import engine.StepListener;
import gui.PetrinetTreeModel.PetrinetNode;
import javax.swing.JInternalFrame;

/**
 *
 * @author moritz
 */
public class PetrinetFrame extends JInternalFrame implements StepListener{

    private final PetrinetNode node;

    public PetrinetFrame(PetrinetNode node) {
        super("Petrinet");
        this.node = node;
        node.getEngine().getSimulation().addStepListener(this);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setContentPane(node.getEngine().getGraphEditor().getGraphPanel());
    }

    public PetrinetNode getNode() {
        return node;
    }

    public void stepped(Simulation s) {
        node.getEngine().getGraphEditor().getGraphPanel().repaint();
    }

    public void started(Simulation s) {
        node.getEngine().getGraphEditor().getGraphPanel().enable(false);
    }

    public void stopped(Simulation s) {
        node.getEngine().getGraphEditor().getGraphPanel().enable(true);
    }
}
