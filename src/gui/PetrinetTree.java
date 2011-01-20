/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.EditMode;
import engine.Engine;
import gui.PetrinetTreeModel.PetrinetNode;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author moritz
 */
public class PetrinetTree extends JTree {

    public PetrinetTree(PetrinetTreeModel model) {
        super(model);
    }

    public PetrinetTree() {
        this(new PetrinetTreeModel());
    }

    @Override
    public PetrinetTreeModel getModel() {
        return (PetrinetTreeModel) super.getModel();
    }

    @Override
    public void setModel(TreeModel tm) {
        if (tm == null) throw new IllegalArgumentException("Model must not be null");
        if (!(tm instanceof PetrinetTreeModel)) throw new IllegalArgumentException("Unsupported model");
        super.setModel(tm);
    }

    public void setEditMode(EditMode mode) {
        getModel().setEditMode(mode);
    }

    PetrinetNode addPetrinet(String name, Engine engine) {
        return getModel().getRoot().addPetrinet(name, engine);
    }

}
