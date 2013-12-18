package gui.fileTree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A special type of {@link DefaultMutableTreeNode} holding some additional information for ReconNet.
 */
public class PetriTreeNode extends DefaultMutableTreeNode {

    /**
     * The {@link NodeType} of this {@link PetriTreeNode}.
     */
    private NodeType nodeType;

    /**
     * This node is displayed with a checkbox (rule node).
     */
    private boolean hasCheckBox;

    /**
     * State of checkbox.
     */
    private boolean checked;

    /**
     * This node is selected and highlighted in tree.
     */
    private boolean selected;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 602894489830122046L;

    /**
     * Constructor.
     * @param nodeType Type of this node.
     * @param userObject user object.
     */
    PetriTreeNode(NodeType nodeType, Object userObject) {
        super(userObject);
        init(nodeType);
    }

    /**
     * Initializes this node.
     * @param nodeType Type of this node.
     */
    private void init(NodeType nodeType) {
        this.checked = false;
        this.nodeType = nodeType;
        if (this.nodeType.equals(NodeType.RULE)) {
            this.setHasCheckBox(true);
        }
    }

    /**
     * Calls super.toString().
     */
    public String toString() {
        return super.toString();
    }

    /**
     * Compares type of this node with given node type.
     * @param nodeType {@link NodeType} to compare this node with.
     * @return Returns whether this node is of type of given type.
     */
    public boolean isNodeType(NodeType nodeType) {
        return this.nodeType.equals(nodeType);
    }

    /**
     * Returns true if this node should be displayed with checkbox (rule node).
     * @return true if this node should be displayed with checkbox (rule node).
     */
    public boolean isHasCheckBox() {
        return hasCheckBox;
    }

    /**
     * Sets this node to be displayed with checkbox or not.
     * @param hasCheckBox True = display with checkbox, false = display without checkbox.
     */
    public void setHasCheckBox(boolean hasCheckBox) {
        this.hasCheckBox = hasCheckBox;
    }

    /**
     * Returns checked state of checkbox.
     * @return checked state of checkbox.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Changes the checked state of checkbox.
     * @param checked new checked state.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * returns selected state of this node (marked to be selected in tree).
     * @return selected state.
     */
    public boolean isSelected() {

        return selected;
    }

    /**
     * sets whether this node should be displayed selected or not.
     * @param selected new seleted state.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
