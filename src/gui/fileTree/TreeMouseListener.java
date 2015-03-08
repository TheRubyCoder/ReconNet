/*
 * BSD-Lizenz Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
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

import static gui.Style.TREE_MENU_LOAD_NET;
import static gui.Style.TREE_MENU_LOAD_RULE;
import static gui.Style.TREE_MENU_ADD_NAC;
import static gui.Style.TREE_MENU_ADD_NET;
import static gui.Style.TREE_MENU_ADD_RULE;
import static gui.Style.TREE_MENU_CHECK_RULE;
import static gui.Style.TREE_MENU_RELOAD_NET;
import static gui.Style.TREE_MENU_RELOAD_RULE;
import static gui.Style.TREE_MENU_REMOVE_NAC;
import static gui.Style.TREE_MENU_REMOVE_NET;
import static gui.Style.TREE_MENU_REMOVE_RULE;
import static gui.Style.TREE_MENU_SAVE;
import static gui.Style.TREE_MENU_SAVE_ALL;
import static gui.Style.TREE_MENU_UNCHECK_RULE;
import gui.RulePane;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * custom mouse listener for use in petrinet tree extending
 * {@link MouseListener}.
 */
public class TreeMouseListener
implements MouseListener {

  /**
   * reference to the {@link JTree} object.
   */
  private JTree tree;

  /**
   * reference to the {@link PopupMenuListener}.
   */
  private ActionListener menuListener;

  /**
   * Constructor
   *
   * @param tree
   *        reference to the {@link JTree} object.
   */
  public TreeMouseListener(JTree tree) {

    super();
    this.tree = tree;
    this.menuListener = PopupMenuListener.getInstance();
  }

  /**
   * dispatches the node type to show different popup menus.
   *
   * @param event
   *        the mouse event triggered action.
   * @param node
   *        the node clicked.
   */
  private void showPopupMenu(MouseEvent event,
    DefaultMutableTreeNode selectedNode) {

    if (selectedNode instanceof NetRootTreeNode) {
      this.showNetRootMenu(event, (NetRootTreeNode) selectedNode);
    } else if (selectedNode instanceof RuleRootTreeNode) {
      this.showRuleRootMenu(event, (RuleRootTreeNode) selectedNode);
    } else if (selectedNode instanceof PetriTreeNode) {
      this.showNetMenu(event, (PetriTreeNode) selectedNode);
    } else if (selectedNode instanceof RuleTreeNode) {
      this.showRuleMenu(event, (RuleTreeNode) selectedNode);
    } else if (selectedNode instanceof NacTreeNode) {
      this.showNacMenu(event, (NacTreeNode) selectedNode);
    }

  }

  /**
   * displays the popup menu for rules.
   *
   * @param event
   *        the mouse event triggered action.
   * @param selectedNode
   *        the node clicked.
   */
  private void showRuleMenu(MouseEvent event, RuleTreeNode selectedNode) {

    JPopupMenu popup = new JPopupMenu();
    JMenuItem i;

    i = new JMenuItem(TREE_MENU_SAVE_ALL);
    popup.add(i);
    i.addActionListener(this.menuListener);

    popup.addSeparator();

    if (selectedNode.isChecked()) {
      i = new JMenuItem(TREE_MENU_UNCHECK_RULE);
    } else {
      i = new JMenuItem(TREE_MENU_CHECK_RULE);
    }
    popup.add(i);
    i.addActionListener(this.menuListener);

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

    popup.show(tree, event.getX(), event.getY());
  }

  /**
   * displays the popup menu for nacs.
   *
   * @param event
   *        the mouse event triggered action.
   * @param selectedNode
   *        the node clicked.
   */
  private void showNacMenu(MouseEvent event, NacTreeNode selectedNode) {

    JPopupMenu popup = new JPopupMenu();
    JMenuItem i;

    i = new JMenuItem(TREE_MENU_SAVE_ALL);
    popup.add(i);
    i.addActionListener(this.menuListener);

    popup.addSeparator();

    i = new JMenuItem(TREE_MENU_REMOVE_NAC);
    popup.add(i);
    i.addActionListener(this.menuListener);

    popup.show(tree, event.getX(), event.getY());
  }

  /**
   * displays the popup menu for nets.
   *
   * @param event
   *        the mouse event triggered action.
   * @param selectedNode
   *        the node clicked.
   */
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

  /**
   * displays the popup menu for rule root.
   *
   * @param event
   *        the mouse event triggered action.
   * @param selectedNode
   *        the node clicked.
   */
  private void showRuleRootMenu(MouseEvent e,
    DefaultMutableTreeNode selectedNode) {

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

  /**
   * displays the popup menu for net root.
   *
   * @param event
   *        the mouse event triggered action.
   * @param selectedNode
   *        the node clicked.
   */
  private void showNetRootMenu(MouseEvent e,
    DefaultMutableTreeNode selectedNode) {

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

    // select corresponding node (important for rightclick)
    this.tree.setSelectionPath(path);

    if (path != null) {

      DefaultMutableTreeNode selectedNode =
        (DefaultMutableTreeNode) path.getLastPathComponent();

      if (e.getButton() == MouseEvent.BUTTON1) {

        if (selectedNode instanceof PetriTreeNode) {
          this.handleNetSelection((PetriTreeNode) selectedNode);
        } else if (selectedNode instanceof RuleTreeNode) {
          this.handleRuleSelection((RuleTreeNode) selectedNode);
        } else if (selectedNode instanceof NacTreeNode) {
          this.handleNacSelection((NacTreeNode) selectedNode);
        }

      } else if (e.getButton() == MouseEvent.BUTTON3) {

        this.showPopupMenu(e, selectedNode);
      }
    }

  }

  private void handleNetSelection(PetriTreeNode netNode) {

  }

  private void handleRuleSelection(RuleTreeNode ruleNode) {

    int ruleId = ruleNode.getRuleId();
    RulePane.getInstance().displayRule(ruleId);

    // if rule has nacs, display the first
    if (ruleNode.getChildCount() > 0) {
      NacTreeNode nacNode = (NacTreeNode) ruleNode.getChildAt(0);
      UUID nacId = nacNode.getNacId();
      RulePane.getInstance().displayNAC(ruleId, nacId);
    } else {
      RulePane.getInstance().displayEmptyNAC();
    }
  }

  private void handleNacSelection(NacTreeNode nacNode) {

    int ruleId = ((RuleTreeNode) nacNode.getParent()).getRuleId();
    UUID nacId = nacNode.getNacId();

    RulePane.getInstance().displayRule(ruleId);
    RulePane.getInstance().displayNAC(ruleId, nacId);
  }

  @Override
  public void mouseEntered(MouseEvent e) {

    // not used
  }

  @Override
  public void mouseExited(MouseEvent e) {

    // not used
  }

  @Override
  public void mousePressed(MouseEvent e) {

    // not used
  }

  @Override
  public void mouseReleased(MouseEvent e) {

    // not used
  }

}
