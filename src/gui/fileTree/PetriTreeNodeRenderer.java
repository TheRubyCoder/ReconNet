package gui.fileTree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

public class PetriTreeNodeRenderer extends DefaultTreeCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = -5797712768845785147L;

    private Color selectedBkgrnd;
    private Color nonSelectedBkgrnd;
    private Font fontValue;
    
    
    public PetriTreeNodeRenderer() {
        super();
        DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
        this.selectedBkgrnd = r.getBackgroundSelectionColor();
        this.nonSelectedBkgrnd = r.getBackgroundNonSelectionColor();
        fontValue = UIManager.getFont("Tree.font");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component renderer;
        
        
        
        if(value instanceof PetriTreeNode) {
            PetriTreeNode node = (PetriTreeNode) value;
            if(node.isNodeType(NodeType.RULE)) {
                JCheckBox box = new JCheckBox(node.toString());
                box.setSelected(node.isChecked());
                
                if (fontValue != null) {
                  box.setFont(fontValue);
                }
                if(node.isSelected()) {
                    box.setBackground(selectedBkgrnd);
                } else {
                    box.setBackground(nonSelectedBkgrnd);
                }
                renderer = box;
            }else {
                renderer = (new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, node.isSelected(), expanded, leaf, row, hasFocus));
            }
        }else {
            renderer = (new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus));
        }
        
        return renderer;
    }

}
