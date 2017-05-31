/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind
 * unter den folgenden Bedingungen zulässig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT;
 * VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
 * and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. Neither the name of the
 * University nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written
 * permission. THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS
 * “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package gui.fileTree;

import static gui.Style.FILE_TREE_PANE_PREFERRED_SIZE;
import static gui.Style.TREE_STRING_NET;
import static gui.Style.TREE_STRING_ROOT;
import static gui.Style.TREE_STRING_RULE;
import static gui.Style.TREE_STRING_TRANSFORMATION_UNIT;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * A panel to display the file tree. Extends {@link JPanel}
 */
public final class FileTreePane
  extends JPanel {

  /**
   * Root node (not visible).
   */
  private DefaultMutableTreeNode rootNode;

  /**
   * Net root node. Root of all nets.
   */
  private DefaultMutableTreeNode netRootNode;

  /**
   * Rule root node. Root of all rules.
   */
  private DefaultMutableTreeNode ruleRootNode;

  /**
   * Transformation Unit root node. Root of all transformation units.
   */
  private DefaultMutableTreeNode transformationUnitRootNode;

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
    this.tree.setCellRenderer(new TreeNodeRenderer());
  }

  /**
   * Creates the TreeModel.
   */
  private void createTreeModel() {

    this.treeModel = new DefaultTreeModel(rootNode);
  }

  /**
   * Creates the root node and adds the netRootNode and the ruleRootNode.
   *
   * @return Configured root node {@link DefaultMutableTreeNode}.
   */
  private DefaultMutableTreeNode createRoot() {

    this.rootNode = new DefaultMutableTreeNode(TREE_STRING_ROOT);

    netRootNode = new NetRootTreeNode(TREE_STRING_NET);
    ruleRootNode = new RuleRootTreeNode(TREE_STRING_RULE);
    transformationUnitRootNode =
      new TransformationUnitRootTreeNode(TREE_STRING_TRANSFORMATION_UNIT);

    this.rootNode.add(netRootNode);
    this.rootNode.add(ruleRootNode);
    this.rootNode.add(transformationUnitRootNode);

    return this.rootNode;
  }

  /**
   * Adds this Pane to given {@link JPanel}.
   *
   * @param panel
   *        {@link JPanel} to add this {@link FileTreePane} to.
   */
  public void addTo(JPanel panel) {

    panel.add(this, BorderLayout.LINE_START);
  }

  /**
   * Retruns the net root node.
   *
   * @return The net root node {@link DefaultMutableTreeNode}.
   */
  public DefaultMutableTreeNode getNetRootNode() {

    return netRootNode;
  }

  /**
   * Retruns the rule root node.
   *
   * @return The rule root node {@link DefaultMutableTreeNode}.
   */
  public DefaultMutableTreeNode getRuleRootNode() {

    return ruleRootNode;
  }

  /**
   * Adds the given node to the tree. Action describes which kind of node is
   * about to be added.
   *
   * @param action
   *        Kind of node to be added
   *        <tt>PopupMenuListener.SELECTED_TYPE_IS_NET</tt> or
   *        <tt>PopupMenuListener.SELECTED_TYPE_IS_NET</tt>).
   * @param nodeToAdd
   */
  /*
   * public void addNode(int action, DefaultMutableTreeNode nodeToAdd) {
   * DefaultMutableTreeNode n = null; if (action ==
   * PopupMenuListener.SELECTED_TYPE_IS_NET) { n = netNode; } else if (action
   * == PopupMenuListener.SELECTED_TYPE_IS_NET) { n = ruleNode; }
   * n.add(nodeToAdd); this.tree.expandRow(0); }
   */

  /**
   * Returns the selected node.
   *
   * @return Selected {@link DefaultMutableTreeNode}.
   */
  public DefaultMutableTreeNode getSelectedNode() {

    DefaultMutableTreeNode dmt =
      (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();

    return dmt;
  }

  /**
   * Returns the tree model.
   *
   * @return {@link DefaultTreeModel}
   */
  public DefaultTreeModel getTreeModel() {

    return treeModel;
  }

  /**
   * Returns the root node.
   *
   * @return root node {@link DefaultMutableTreeNode}
   */
  public DefaultMutableTreeNode getRootNode() {

    return rootNode;
  }

  /**
   * Sets the rule node.
   *
   * @param {@link DefaultMutableTreeNode} ruleNode
   */
  public void setRuleNode(DefaultMutableTreeNode ruleNode) {

    this.ruleRootNode = ruleNode;
  }

  /**
   * Returns the IDs of the selected rules.
   *
   * @return IDs {@link Collection}
   */
  public Collection<Integer> getSelectedRuleIds() {

    List<Integer> list = new ArrayList<Integer>();

    int numberOfRules = this.getRuleRootNode().getChildCount();

    for (int i = 0; i < numberOfRules; i++) {

      RuleTreeNode ruleNode = (RuleTreeNode) ruleRootNode.getChildAt(i);

      if (ruleNode.isChecked()) {
        list.add(ruleNode.getRuleId());
      }

    }

    return list;
  }

  public Map<String, Integer> getRuleNamesToIdsMapping() {

    Map<String, Integer> ruleNameToIdMapping = new HashMap<String, Integer>();

    int numberOfRules = this.getRuleRootNode().getChildCount();

    for (int i = 0; i < numberOfRules; i++) {
      RuleTreeNode ruleNode = (RuleTreeNode) ruleRootNode.getChildAt(i);
      ruleNameToIdMapping.put(ruleNode.toString(), ruleNode.getRuleId());
    }

    return ruleNameToIdMapping;
  }

  public Integer getRuleIdByRuleName(String ruleName) {

    DefaultMutableTreeNode ruleRootNode = this.getRuleRootNode();
    int ruleCount = ruleRootNode.getChildCount();

    for (int i = 0; i < ruleCount; i++) {

      RuleTreeNode ruleNode = (RuleTreeNode) ruleRootNode.getChildAt(i);

      if (ruleNode.toString().equals(ruleName)) {
        return ruleNode.getRuleId();
      }

    }

    return null;
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
