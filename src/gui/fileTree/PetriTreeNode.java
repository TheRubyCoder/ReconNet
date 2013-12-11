package gui.fileTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class PetriTreeNode extends DefaultMutableTreeNode {

    public static final int ROOT_NODE = -1;
    public static final int RULE_ROOT_NODE = 0;
    public static final int RULE_NODE = 1;
    public static final int NET_ROOT_NODE = 2;
    public static final int NET_NODE = 3;

    /**
     * generated UID
     */
    private static final long serialVersionUID = -5162180130369164448L;

    private int noteType;

    public PetriTreeNode(int noteType) {
        super();
        this.noteType = noteType;
    }
    

    public PetriTreeNode(int noteType, Object userObject) {
        super(userObject);
        this.noteType = noteType;
    }

    public PetriTreeNode(int noteType, Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.noteType = noteType;
    }

    public boolean isRuleRootNode() {
        return this.noteType == RULE_ROOT_NODE;
    }

    public boolean isRuleNode() {
        return this.noteType == RULE_NODE;
    }

    public boolean isNetRootNode() {
        return this.noteType == NET_ROOT_NODE;
    }

    public boolean isNetNode() {
        return this.noteType == NET_NODE;
    }



    @Override
    public void add(MutableTreeNode newChild) {
        super.add(newChild);
    }
}
