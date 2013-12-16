package gui.fileTree;

import static gui.Style.FILE_TREE_PANE_PREFERRED_SIZE;
import static gui.Style.TREE_STRING_NET;
import static gui.Style.TREE_STRING_ROOT;
import static gui.Style.TREE_STRING_RULE;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FileTreePane extends JPanel {

    DefaultMutableTreeNode rootNode;
    DefaultMutableTreeNode netNode;
    DefaultMutableTreeNode ruleNode;

    private JTree tree;

    public JTree getTree() {
        return tree;
    }

    private DefaultTreeModel treeModel;

    private JScrollPane treeView;

    private static FileTreePane instance;

    /**
     * generated ID
     */
    private static final long serialVersionUID = -2388963754966220772L;

    static {
        instance = new FileTreePane();
    }

    public static FileTreePane getInstance() {
        return instance;
    }

    private FileTreePane() {
        this.initializeFileTreePane();
    }

    private void initializeFileTreePane() {
        this.initializeTreeView();
        this.setLayout(new GridLayout(1, 1));
        this.add(this.treeView);
    }

    private void initializeTreeView() {
        this.initializeTree();
        this.treeView = new JScrollPane(this.tree);
    }

    private void initializeTree() {
        this.createRoot();
        this.createTreeModel();
        this.tree = new JTree(this.treeModel);
        this.tree.setRootVisible(false);
        this.tree.setPreferredSize(FILE_TREE_PANE_PREFERRED_SIZE);
        this.tree.addMouseListener(new TreeMouseListener(this.tree));
        this.tree.setCellRenderer(new PetriTreeNodeRenderer());
    }

    private void createTreeModel() {
        this.treeModel = new DefaultTreeModel((DefaultMutableTreeNode) rootNode);
        // TODO: modellistener???
    }

    private DefaultMutableTreeNode createRoot() {
        this.rootNode = new PetriTreeNode(NodeType.ROOT, TREE_STRING_ROOT);
        netNode = new PetriTreeNode(NodeType.NET_ROOT, TREE_STRING_NET);
        ruleNode = new PetriTreeNode(NodeType.RULE_ROOT, TREE_STRING_RULE);
        this.rootNode.add(netNode);
        this.rootNode.add(ruleNode);
        return this.rootNode;
    }

    public void addTo(JPanel p) {
        p.add(this, BorderLayout.LINE_START);
    }

    public DefaultMutableTreeNode getNetNode() {
        return netNode;
    }

    public DefaultMutableTreeNode getRuleNode() {
        return ruleNode;
    }

    public void addNode(int action, DefaultMutableTreeNode nodeToAdd) {
        DefaultMutableTreeNode n = null;
        if (action == PopupMenuListener.SELECTED_TYPE_IS_NET) {
            n = netNode;
        } else if (action == PopupMenuListener.SELECTED_TYPE_IS_NET) {
            n = ruleNode;
        }
        n.add(nodeToAdd);
        this.tree.expandRow(0);
    }

    public DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }

    public void setRuleNode(DefaultMutableTreeNode ruleNode) {
        this.ruleNode = ruleNode;
    }

    public Collection<Integer> getSelectedRuleIds() {
        List<Integer> l = new ArrayList<Integer>();
        PetriTreeNode parent = (PetriTreeNode) this.ruleNode;
        PetriTreeNode child;
        int numberOfChilds = parent.getChildCount();
        for (int i = 0; i < numberOfChilds; i++) {
            child = (PetriTreeNode) parent.getChildAt(i);
            if (child.isChecked()) {
                l.add(PopupMenuListener.getInstance().getIdForNode(child));
            }
        }
        return l;
    }

}
