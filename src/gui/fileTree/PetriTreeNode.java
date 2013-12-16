package gui.fileTree;

import javax.swing.tree.DefaultMutableTreeNode;

public class PetriTreeNode extends DefaultMutableTreeNode {

    

    private NodeType nodeType;

    private boolean hasCheckBox;

    private boolean checked;

    private boolean selected;

    /**
     * 
     */
    private static final long serialVersionUID = 602894489830122046L;

    PetriTreeNode(NodeType nodeType, Object userObject) {
        super(userObject);
        init(nodeType);
    }

    private void init(NodeType nodeType) {
        this.checked = false;
        this.nodeType = nodeType;
        if (this.nodeType.equals(NodeType.RULE)) {
            this.setHasCheckBox(true);
        }
    }

    public String toString() {
        return super.toString();
    }

    public boolean isNodeType(NodeType nodeType) {
        return this.nodeType.equals(nodeType);
    }

    public boolean isHasCheckBox() {
        return hasCheckBox;
    }

    public void setHasCheckBox(boolean hasCheckBox) {
        this.hasCheckBox = hasCheckBox;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelected() {

        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        System.out.println(super.toString() + " selcted: " + selected);
    }
}
