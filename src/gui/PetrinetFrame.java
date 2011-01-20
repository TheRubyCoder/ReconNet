/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import gui.PetrinetTreeModel.PetrinetNode;
import javax.swing.JInternalFrame;

/**
 *
 * @author moritz
 */
public class PetrinetFrame extends JInternalFrame {

    private final PetrinetNode node;

    public PetrinetFrame(PetrinetNode node) {
        super("Petrinet");
        this.node = node;

        setContentPane(node.getEngine().getGraphEditor().getGraphPanel());
    }

    public PetrinetNode getNode() {
        return node;
    }
}
