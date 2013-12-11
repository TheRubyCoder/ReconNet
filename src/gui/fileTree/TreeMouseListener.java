package gui.fileTree;

import static gui.Style.TREE_MENU_ADD_NAC;
import static gui.Style.TREE_MENU_ADD_NET;
import static gui.Style.TREE_MENU_ADD_RULE;
import static gui.Style.TREE_MENU_LOAD_NET;
import static gui.Style.TREE_MENU_LOAD_RULE;
import static gui.Style.TREE_MENU_REMOVE_NET;
import static gui.Style.TREE_MENU_REMOVE_RULE;
import static gui.Style.TREE_MENU_RELOAD_NET;
import static gui.Style.TREE_MENU_RELOAD_RULE;
import static gui.Style.TREE_MENU_SAVE;
import static gui.Style.TREE_MENU_SAVE_ALL;
import gui.PetrinetPane;
import gui.RulePane;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeMouseListener implements MouseListener {

    private JTree tree;

    private ActionListener menuListener;

    public TreeMouseListener(JTree tree) {
        super();
        this.tree = tree;
        this.menuListener = PopupMenuListener.getInstance();
    }

    private void showPopupMenu(MouseEvent e, Object o) {
        PetriTreeNode selectedNode = (PetriTreeNode) o;

        if (selectedNode.isNetRootNode()) {
            this.showNetRootMenu(e, selectedNode);
        } else if (selectedNode.isRuleRootNode()) {
            this.showRuleRootMenu(e, selectedNode);
        } else if (selectedNode.isNetNode()) {
            this.showNetMenu(e, selectedNode);
        } else if (selectedNode.isRuleNode()) {
            this.showRuleMenu(e, selectedNode);
        } else {
            // TODO: throw exception here
            System.out.println("unknown node type");
        }

    }

    private void showRuleMenu(MouseEvent e, DefaultMutableTreeNode selectedNode) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem i;

        i = new JMenuItem(TREE_MENU_SAVE_ALL);
        popup.add(i);
        i.addActionListener(this.menuListener);
        
        popup.addSeparator();

        i = new JMenuItem(TREE_MENU_SAVE);
        popup.add(i);
        i.addActionListener(this.menuListener);
        
        i = new JMenuItem(TREE_MENU_ADD_NAC);
        popup.add(i);
        i.addActionListener(this.menuListener);

        i = new JMenuItem(TREE_MENU_REMOVE_RULE);
        popup.add(i);
        i.addActionListener(this.menuListener);
        
        i = new JMenuItem(TREE_MENU_RELOAD_RULE);
        popup.add(i);
        i.addActionListener(this.menuListener);

        popup.show(tree, e.getX(), e.getY());
    }

    private void showNetMenu(MouseEvent e, DefaultMutableTreeNode selectedNode) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem i;

        i = new JMenuItem(TREE_MENU_SAVE_ALL);
        popup.add(i);
        i.addActionListener(this.menuListener);
        
        popup.addSeparator();

        i = new JMenuItem(TREE_MENU_SAVE);
        popup.add(i);
        i.addActionListener(this.menuListener);

        i = new JMenuItem(TREE_MENU_REMOVE_NET);
        popup.add(i);
        i.addActionListener(this.menuListener);

        i = new JMenuItem(TREE_MENU_RELOAD_NET);
        popup.add(i);
        i.addActionListener(this.menuListener);

        popup.show(tree, e.getX(), e.getY());
    }

    private void showRuleRootMenu(MouseEvent e, DefaultMutableTreeNode selectedNode) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem i;

        i = new JMenuItem(TREE_MENU_SAVE_ALL);
        popup.add(i);
        i.addActionListener(this.menuListener);

        popup.addSeparator();

        i = new JMenuItem(TREE_MENU_ADD_RULE);
        popup.add(i);
        i.addActionListener(this.menuListener);

        i = new JMenuItem(TREE_MENU_LOAD_RULE);
        popup.add(i);
        i.addActionListener(this.menuListener);

        popup.show(tree, e.getX(), e.getY());
    }

    private void showNetRootMenu(MouseEvent e, DefaultMutableTreeNode selectedNode) {
        
        JPopupMenu popup = new JPopupMenu();
        JMenuItem i;

        i = new JMenuItem(TREE_MENU_SAVE_ALL);
        popup.add(i);
        i.addActionListener(this.menuListener);

        popup.addSeparator();

        i = new JMenuItem(TREE_MENU_ADD_NET);
        popup.add(i);
        i.addActionListener(this.menuListener);

        i = new JMenuItem(TREE_MENU_LOAD_NET);
        popup.add(i);
        i.addActionListener(this.menuListener);

        popup.show(tree, e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        TreePath path = this.tree.getPathForLocation(e.getX(), e.getY());

        if (path != null) {
            this.tree.setSelectionPath(path);
            PetriTreeNode node = (PetriTreeNode) path.getLastPathComponent();

            String name = node.toString();
            Integer id = PopupMenuListener.getInstance().getPidOf(name);

            if (node.isNetNode()) {
                PetrinetPane.getInstance().displayPetrinet(id, name);
            } else if (node.isRuleNode()) {
                RulePane.getInstance().displayRule(id);
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                showPopupMenu(e, path.getLastPathComponent());
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
