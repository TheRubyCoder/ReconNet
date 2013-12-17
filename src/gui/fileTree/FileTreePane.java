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

/**
 * A panel to display the file tree. Extends {@link JPanel}
 */
public class FileTreePane extends JPanel {

    /**
     * Root node (not visible).
     */
    private DefaultMutableTreeNode rootNode;

    /**
     * Net root node. Root of all nets.
     */
    private DefaultMutableTreeNode netNode;

    /**
     * Rule root node. Root of all rules.
     */
    private DefaultMutableTreeNode ruleNode;

    /**
     * Reference to the tree object.
     */
    private JTree tree;

    /**
     * Reference to tree model instance
     */
    private DefaultTreeModel treeModel;

    /**
     * Scroll pane holding JTree.
     */
    private JScrollPane treeView;

    /**
     * Singleton: Instance of this {@link FileTreePane}.
     */
    private static FileTreePane instance;

    /**
     * generated ID
     */
    private static final long serialVersionUID = -2388963754966220772L;

    /**
     * Singleton: Create Instance.
     */
    static {
        instance = new FileTreePane();
    }

    /**
     * Singleton. Returns the Instance.
     * 
     * @return the Instance {@link FileTreePane}
     */
    public static FileTreePane getInstance() {
        return instance;
    }

    /**
     * Constructor
     */
    private FileTreePane() {
        this.initializeFileTreePane();
    }

    /**
     * Initialization of this {@link FileTreePane}.
     */
    private void initializeFileTreePane() {
        this.initializeTreeView();
        this.setLayout(new GridLayout(1, 1));
        this.add(this.treeView);
    }

    /**
     * Initialization of the {@link JScrollPane} and call initialization of
     * {@link JTree}.
     */
    private void initializeTreeView() {
        this.initializeTree();
        this.treeView = new JScrollPane(this.tree);
    }

    /**
     * Initialization of the {@link JTree}.
     */
    private void initializeTree() {
        this.createRoot();
        this.createTreeModel();
        this.tree = new JTree(this.treeModel);
        this.tree.setRootVisible(false);
        this.tree.setPreferredSize(FILE_TREE_PANE_PREFERRED_SIZE);
        this.tree.addMouseListener(new TreeMouseListener(this.tree));
        this.tree.setCellRenderer(new PetriTreeNodeRenderer());
    }

    /**
     * Creates the TreeModel.
     */
    private void createTreeModel() {
        this.treeModel = new DefaultTreeModel((DefaultMutableTreeNode) rootNode);
    }

    /**
     * Creates the root node and adds the netRootNode and the ruleRootNode.
     * 
     * @return Configured root node {@link DefaultMutableTreeNode}.
     */
    private DefaultMutableTreeNode createRoot() {
        this.rootNode = new PetriTreeNode(NodeType.ROOT, TREE_STRING_ROOT);
        netNode = new PetriTreeNode(NodeType.NET_ROOT, TREE_STRING_NET);
        ruleNode = new PetriTreeNode(NodeType.RULE_ROOT, TREE_STRING_RULE);
        this.rootNode.add(netNode);
        this.rootNode.add(ruleNode);
        return this.rootNode;
    }

    /**
     * Adds this Pane to given {@link JPanel}.
     * 
     * @param panel
     *            {@link JPanel} to add this {@link FileTreePane} to.
     */
    public void addTo(JPanel panel) {
        panel.add(this, BorderLayout.LINE_START);
    }

    /**
     * Retruns the net root node.
     * @return The net root node {@link DefaultMutableTreeNode}.
     */
    public DefaultMutableTreeNode getNetNode() {
        return netNode;
    }

    /**
     * Retruns the rule root node.
     * @return The rule root node {@link DefaultMutableTreeNode}.
     */
    public DefaultMutableTreeNode getRuleNode() {
        return ruleNode;
    }

    /**
     * Adds the given node to the tree. Action describes which kind of node is about to be added.
     * @param action Kind of node to be added <tt>PopupMenuListener.SELECTED_TYPE_IS_NET</tt> or <tt>PopupMenuListener.SELECTED_TYPE_IS_NET</tt>).
     * @param nodeToAdd
     *//*
    public void addNode(int action, DefaultMutableTreeNode nodeToAdd) {
        DefaultMutableTreeNode n = null;
        if (action == PopupMenuListener.SELECTED_TYPE_IS_NET) {
            n = netNode;
        } else if (action == PopupMenuListener.SELECTED_TYPE_IS_NET) {
            n = ruleNode;
        }
        n.add(nodeToAdd);
        this.tree.expandRow(0);
    }*/

    /**
     * Returns the selected node.
     * @return Selected {@link DefaultMutableTreeNode}.
     */
    public DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
    }

    /**
     * Returns the tree model.
     * @return {@link DefaultTreeModel}
     */
    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    /**
     * Returns the root node.
     * @return root node {@link DefaultMutableTreeNode}
     */
    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }

    /**
     * Sets the rule node.
     * @param {@link DefaultMutableTreeNode} ruleNode
     */
    public void setRuleNode(DefaultMutableTreeNode ruleNode) {
        this.ruleNode = ruleNode;
    }

    /**
     * Returns the IDs of the selected rules.
     * @return IDs {@link Collection}
     */
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

    /**
     * Getter for the tree object.
     * 
     * @return instance of the {@link JTree} object.
     */
    public JTree getTree() {
        return tree;
    }

}
