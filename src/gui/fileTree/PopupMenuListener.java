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

import static gui.Style.TREE_MENU_ADD_NAC;
import static gui.Style.TREE_MENU_ADD_NET;
import static gui.Style.TREE_MENU_ADD_RULE;
import static gui.Style.TREE_MENU_CHECK_RULE;
import static gui.Style.TREE_MENU_LOAD_NET;
import static gui.Style.TREE_MENU_LOAD_RULE;
import static gui.Style.TREE_MENU_RELOAD_NET;
import static gui.Style.TREE_MENU_REMOVE_NAC;
import static gui.Style.TREE_MENU_REMOVE_NET;
import static gui.Style.TREE_MENU_SAVE;
import static gui.Style.TREE_MENU_SAVE_ALL;
import static gui.Style.TREE_MENU_UNCHECK_RULE;
import exceptions.EngineException;
import exceptions.ShowAsInfoException;
import exceptions.ShowAsWarningException;
import gui.EngineAdapter;
import gui.PetrinetPane;
import gui.PopUp;
import gui.RulePane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Custom popup menu listener extending {@link ActionListener}.
 */
public final class PopupMenuListener
  implements ActionListener {

  /**
   * singleton: the instance
   */
  private static PopupMenuListener instance;

  /**
   * singleton: creates the instance.
   */
  static {
    instance = new PopupMenuListener();
  }

  /**
   * singleton: returns the instance.
   *
   * @return the instance of {@link PopupMenuListener}
   */
  public static PopupMenuListener getInstance() {

    return instance;
  }

  /**
   * constant to determine operating with nets.
   */
  public static final int SELECTED_TYPE_IS_NET = 0;

  /**
   * constant to determine operating with rules.
   */
  public static final int SELECTED_TYPE_IS_RULE = 1;

  /**
   * constant to determine creating nets.
   */
  public static final int DIALOG_CREATE_NET = SELECTED_TYPE_IS_NET;

  /**
   * constant to determine creating rules.
   */
  public static final int DIALOG_CREATE_RULE = SELECTED_TYPE_IS_RULE;

  /**
   * constant to determine loading nets.
   */
  public static final int DIALOG_LOAD_NET = 2;

  /**
   * constant to determine loading rules.
   */
  public static final int DIALOG_LOAD_RULE = 3;

  /**
   * The extension a file needs to have with the dot: ".PNML"
   */
  private static final String FILE_EXTENSION = ".PNML";

  /**
   * The extension a file needs to have without the dot: "PNML"
   */
  private static final String FILE_EXTENSION_WITHOUT_DOT = "PNML";

  /**
   * The extension a file needs to have with the dot: ".pnml"
   */
  private static final String FILE_EXTENSION_LOWER_CASE = ".pnml";

  private JFileChooser fileChooser;

  /**
   * To remember what list entry refers to wich petrinet
   */
  private Map<String, Integer> nameToPId;

  /**
   * To remember what list entry refers to wich petrinet
   */
  private Map<Integer, Set<Integer>> ruleToNacs;

  /**
   * Link nac name to display name
   */
  private Map<String, String> nacNameToDisplayName;

  /**
   * To remember what list entry refers to which filepath
   */
  private Map<String, File> nameToFilepath;

  /**
   * Constructor.
   */
  private PopupMenuListener() {

    nameToPId = new HashMap<String, Integer>();
    nameToFilepath = new HashMap<String, File>();
    ruleToNacs = new HashMap<Integer, Set<Integer>>();
    nacNameToDisplayName = new HashMap<String, String>();
    this.initializeFileChooser();
  }

  /**
   * initializes the file choosing dialog.
   */
  private void initializeFileChooser() {

    FileFilter filter =
      new FileNameExtensionFilter("PetriNetModellingLanguage (*.PNML)",
        "PNML");
    this.fileChooser = new JFileChooser();
    this.fileChooser.addChoosableFileFilter(filter);
    this.fileChooser.setFileFilter(filter);
    this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    this.fileChooser.setAcceptAllFileFilterUsed(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    // set up action for file choose dialog.
    if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_ADD_NET)) {
      this.addToTree(SELECTED_TYPE_IS_NET);
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_ADD_RULE)) {
      this.addToTree(SELECTED_TYPE_IS_RULE);
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_ADD_NAC)) {
      this.createNac();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_LOAD_NET)) {
      this.loadNed();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_LOAD_RULE)) {
      this.loadRule();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_SAVE)) {
      this.save();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_SAVE_ALL)) {
      this.saveAll();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_RELOAD_NET)) {
      this.reload();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_REMOVE_NET)) {
      this.remove();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_CHECK_RULE)) {
      this.checkNode();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_UNCHECK_RULE)) {
      this.uncheckNode();
    } else if (e.getActionCommand().equalsIgnoreCase(TREE_MENU_REMOVE_NAC)) {
      this.removeNac();
    }
  }

  /**
   * unchecks the checkbox of the selected node.
   */
  private void uncheckNode() {

    PetriTreeNode node =
      (PetriTreeNode) FileTreePane.getInstance().getSelectedNode();
    node.setChecked(false);
    FileTreePane.getInstance().getTree().invalidate();
    FileTreePane.getInstance().getTree().validate();
    FileTreePane.getInstance().getTree().repaint();
  }

  /**
   * unchecks the checkbox of the selected node.
   */
  private void checkNode() {

    PetriTreeNode node =
      (PetriTreeNode) FileTreePane.getInstance().getSelectedNode();
    node.setChecked(true);
    FileTreePane.getInstance().getTree().invalidate();
    FileTreePane.getInstance().getTree().validate();
    FileTreePane.getInstance().getTree().repaint();
  }

  /**
   * removes the selected node from tree.
   */
  private void remove() {

    // CHECKSTYLE:OFF - Ternary operator is fine here
    boolean delete =
      JOptionPane.showOptionDialog(null,
        "Sollen die Dateien vom Dateisystem gelöscht werden?", "Löschen", 0,
        JOptionPane.QUESTION_MESSAGE, null, new String[]{"Dateien löschen",
          "Nur aus Übersicht löschen"}, "Nur aus Übersicht löschen") == 0
        ? true : false;
    // CHECKSTYLE:ON

    PetriTreeNode node =
      (PetriTreeNode) FileTreePane.getInstance().getSelectedNode();
    PetriTreeNode parentNode = null;
    String name = node.toString();

    if (delete) {
      nameToFilepath.get(name).delete();
    }

    nameToFilepath.remove(name);
    nameToPId.remove(name);

    if (node.isNodeType(NodeType.NET)) {
      PetrinetPane.getInstance().displayEmpty();
      parentNode = (PetriTreeNode) FileTreePane.getInstance().getNetNode();
    } else if (node.isNodeType(NodeType.RULE)) {
      RulePane.getInstance().displayEmpty();
      parentNode = (PetriTreeNode) FileTreePane.getInstance().getRuleNode();
    }

    FileTreePane.getInstance().getTreeModel().removeNodeFromParent(node);
    FileTreePane.getInstance().getTree().scrollPathToVisible(
      new TreePath(parentNode.getPath()));
    FileTreePane.getInstance().getTree().setSelectionPath(
      new TreePath(FileTreePane.getInstance().getTreeModel().getPathToRoot(
        parentNode)));
  }

  /**
   * reloads the file contents from disk to reset changes done to the net or
   * rule.
   */
  private void reload() {

    PetriTreeNode node =
      (PetriTreeNode) FileTreePane.getInstance().getSelectedNode();
    File file = this.nameToFilepath.get(node.toString());
    int netType = -1;
    if (node.isNodeType(NodeType.NET)) {
      netType = PopupMenuListener.SELECTED_TYPE_IS_NET;
    } else if (node.isNodeType(NodeType.RULE)) {
      netType = PopupMenuListener.SELECTED_TYPE_IS_RULE;
    }
    this.loadFromFile(file, netType);

  }

  /**
   * shows file dialog and loads chosen net file.
   */
  private void loadNed() {

    File file = null;
    int status = this.showDialog(PopupMenuListener.DIALOG_LOAD_NET);

    if (status == JFileChooser.APPROVE_OPTION) {
      file = ensurePNMLEnding(fileChooser.getSelectedFile());
      String name = getFilenameWithoutExtension(file);

      if (file.exists()) {
        if (this.nameToPId.containsKey(name)) {
          throw new ShowAsInfoException(
            "Die ausgewählte Datei ist bereits geladen.");
        } else {
          loadFromFile(file, PopupMenuListener.SELECTED_TYPE_IS_NET);
        }
      } else {
        throw new ShowAsInfoException(
          "Die ausgewählte Datei existiert nicht. "
            + "Haben sie vieleicht die Endung vergessen?");
      }
    }

  }

  /**
   * shows file dialog and loads chosen rule file.
   */
  private void loadRule() {

    File file = null;
    int status = this.showDialog(PopupMenuListener.DIALOG_LOAD_RULE);

    if (status == JFileChooser.APPROVE_OPTION) {
      file = ensurePNMLEnding(fileChooser.getSelectedFile());
      String name = getFilenameWithoutExtension(file);

      if (file.exists()) {
        if (this.nameToPId.containsKey(name)) {
          throw new ShowAsInfoException(
            "Die ausgewählte Datei ist bereits geladen.");
        } else {
          loadFromFile(file, PopupMenuListener.SELECTED_TYPE_IS_RULE);
        }
      } else {
        throw new ShowAsInfoException(
          "Die ausgewählte Datei existiert nicht. "
            + "Haben sie vieleicht die Endung vergessen?");
      }
    }

  }

  /**
   * Loads a petrinet or file from a given file
   *
   * @param file
   *        the file containing the net or rule
   * @param netType
   *        type of file (rule or net)
   */
  private void loadFromFile(File file, int netType) {

    int id;
    String name = this.getFilenameWithoutExtension(file);
    if (netType == PopupMenuListener.SELECTED_TYPE_IS_NET) {

      id =
        EngineAdapter.getPetrinetManipulation().load(file.getParent(),
          file.getName());
      PetrinetPane.getInstance().displayPetrinet(id, name);
    } else {

      id =
        EngineAdapter.getRuleManipulation().load(file.getParent(),
          file.getName());
      RulePane.getInstance().displayRule(id);
    }

    if (!nameToPId.containsKey(name)) {

      DefaultMutableTreeNode parentNode = null;
      PetriTreeNode n = null;

      if (netType == SELECTED_TYPE_IS_NET) {
        parentNode = FileTreePane.getInstance().getNetNode();
        n = new PetriTreeNode(NodeType.NET, name);
      } else if (netType == SELECTED_TYPE_IS_RULE) {
        parentNode = FileTreePane.getInstance().getRuleNode();
        n = new PetriTreeNode(NodeType.RULE, name);
        n.setChecked(true);
      } else {
        // TODO: hier nac mit if else
      }

      // FileTreePane.getInstance().getTreeModel()
      // .reload(FileTreePane.getInstance().getRootNode());
      FileTreePane flTrPn = FileTreePane.getInstance();
      flTrPn.getTreeModel().insertNodeInto(n, parentNode,
        parentNode.getChildCount());
      flTrPn.getTree().scrollPathToVisible(new TreePath(n.getPath()));
      flTrPn.getTree().setSelectionPath(
        new TreePath(flTrPn.getTreeModel().getPathToRoot(n)));
    }

    nameToPId.put(name, id);
    nameToFilepath.put(name, file);
  }

  /**
   * saves selected file.
   */
  private void save() {

    saveNode(FileTreePane.getInstance().getSelectedNode());
  }

  /**
   * saves all files.
   */
  private void saveAll() {

    FileTreePane flTrPn = FileTreePane.getInstance();
    int childCount = flTrPn.getNetNode().getChildCount();

    for (int i = childCount - 1; i >= 0; i--) {
      saveNode((DefaultMutableTreeNode) flTrPn.getNetNode().getChildAt(i));
    }

    childCount = flTrPn.getRuleNode().getChildCount();

    for (int i = childCount - 1; i >= 0; i--) {
      saveNode((DefaultMutableTreeNode) flTrPn.getRuleNode().getChildAt(i));
    }
  }

  /**
   * saves the file via engine.
   *
   * @param node
   *        node to save.
   */
  private void saveNode(DefaultMutableTreeNode node) {

    String name = node.getUserObject().toString();
    int id = nameToPId.get(name);
    File file = nameToFilepath.get(name);
    try {
      if (((PetriTreeNode) node).isNodeType(NodeType.NET)) {
        EngineAdapter.getPetrinetManipulation().save(id, file.getParent(),
          name, FILE_EXTENSION_WITHOUT_DOT,
          PetrinetPane.getInstance().getCurrentNodeSize());
      } else if (((PetriTreeNode) node).isNodeType(NodeType.RULE)) {
        EngineAdapter.getRuleManipulation().save(id, file.getParent(), name,
          FILE_EXTENSION_WITHOUT_DOT);
      }
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * shows file chooser dialog, creates a new file via engine and adds it to
   * tree.
   *
   * @param netType
   *        the type of net which should be created.
   */
  private void addToTree(int netType) {

    File file = null;
    int status = JFileChooser.ERROR_OPTION;

    // ask for file name as long user chose filename and accepted if
    // overwrite needed.
    while (true) {
      int result = -1;
      // show file choose dialog.
      status = this.showDialog(netType);
      if (status == JFileChooser.APPROVE_OPTION) {
        file = ensurePNMLEnding(fileChooser.getSelectedFile());
        result = userWantsToOverwrite(file);
      }

      if (result == JOptionPane.NO_OPTION) {
        /*
         * user chose filename already existing and wants to not overwrite. so
         * ask again for filename.
         */
        continue;
      } else if (result == JOptionPane.YES_OPTION) {
        /*
         * user chose filename already existing and wants to overwrite. so
         * continue code.
         */
        break;
      } else {
        /*
         * user chose filename already existing and wants to cancel operation.
         * so abort this method.
         */
        return;
      }
    }

    try {
      file.createNewFile();

      String name = this.getFilenameWithoutExtension(file);
      int id = -1;

      if (netType == SELECTED_TYPE_IS_NET) {
        id = EngineAdapter.getPetrinetManipulation().createPetrinet();
      } else if (netType == SELECTED_TYPE_IS_RULE) {
        id = EngineAdapter.getRuleManipulation().createRule();
      } else {
        // TODO : Exception für false action
        System.out.println("falsche action gewählt");
        return;
      }

      nameToPId.put(name, id);
      nameToFilepath.put(name, file);

      try {
        if (netType == SELECTED_TYPE_IS_NET) {
          PetrinetPane.getInstance().displayPetrinet(id, name);
          EngineAdapter.getPetrinetManipulation().save(id, file.getParent(),
            name, FILE_EXTENSION_WITHOUT_DOT,
            PetrinetPane.getInstance().getCurrentNodeSize());
        } else if (netType == SELECTED_TYPE_IS_RULE) {
          RulePane.getInstance().displayRule(id);
          EngineAdapter.getRuleManipulation().save(id, file.getParent(),
            name, FILE_EXTENSION_WITHOUT_DOT);
        }
      } catch (EngineException ex) {
        PopUp.popError(ex);
        ex.printStackTrace();
      }

      DefaultMutableTreeNode parentNode = null;
      PetriTreeNode n = null;
      FileTreePane flTrPn = FileTreePane.getInstance();

      if (netType == SELECTED_TYPE_IS_NET) {
        parentNode = flTrPn.getNetNode();
        n = new PetriTreeNode(NodeType.NET, name);
      } else if (netType == SELECTED_TYPE_IS_RULE) {
        parentNode = flTrPn.getRuleNode();
        n = new PetriTreeNode(NodeType.RULE, name);
        n.setChecked(true);
      } else {
        // TODO: hier nac mit if else
      }

      // FileTreePane.getInstance().getTreeModel()
      // .reload(FileTreePane.getInstance().getRootNode());
      flTrPn.getTreeModel().insertNodeInto(n, parentNode,
        parentNode.getChildCount());
      flTrPn.getTree().scrollPathToVisible(new TreePath(n.getPath()));
      flTrPn.getTree().setSelectionPath(
        new TreePath(flTrPn.getTreeModel().getPathToRoot(n)));

    } catch (IOException ex) {
      throw new ShowAsWarningException(ex);
    }
  }

  private void createNac() {

    System.out.println(PopupMenuListener.class + " - createNac");

    PetriTreeNode ruleNode =
      (PetriTreeNode) FileTreePane.getInstance().getSelectedNode();
    String ruleName = ruleNode.getUserObject().toString();
    int ruleId = nameToPId.get(ruleName);

    try {

      int nacId = this.createNacInBackend(ruleId);
      this.createNacTreeItem(nacId);

      RulePane.getInstance().displayRule(ruleId);
      RulePane.getInstance().displayNAC(ruleId, nacId);

    } catch (EngineException e) {
      PopUp.popError(e);
    }

  }

  private int createNacInBackend(int ruleId)
    throws EngineException {

    int nacId = EngineAdapter.getRuleManipulation().createNac(ruleId);

    return nacId;
  }

  private void createNacTreeItem(int nacId) {

    PetriTreeNode ruleNode =
      (PetriTreeNode) FileTreePane.getInstance().getSelectedNode();

    String nacDisplayName = "NAC " + nacId;

    PetriTreeNode n = new PetriTreeNode(NodeType.NAC, nacDisplayName);
    FileTreePane flTrPn = FileTreePane.getInstance();

    flTrPn.getTreeModel().insertNodeInto(n, ruleNode,
      ruleNode.getChildCount());
    flTrPn.getTree().scrollPathToVisible(new TreePath(n.getPath()));
    flTrPn.getTree().setSelectionPath(
      new TreePath(flTrPn.getTreeModel().getPathToRoot(n)));
  }

  private void removeNac() {

    // Knoten aus Tree löschen
    // NAC aus Backend löschen
  }

  /*
   * private void addNacToTree() { System.out.println(PopupMenuListener.class
   * + " - addNacToTree"); PetriTreeNode ruleNode = (PetriTreeNode)
   * FileTreePane.getInstance().getSelectedNode(); String ruleName =
   * ruleNode.getUserObject().toString(); int ruleId =
   * nameToPId.get(ruleName); // TODO: multiple NACs hier um Namensgebung
   * kümmern SimpleDateFormat dateFormatter = new SimpleDateFormat(); String
   * dateString = dateFormatter.format(new Date()); String nacDisplayName =
   * "NAC [" + dateString + "]"; String nacName = ruleName + "." +
   * nacDisplayName; if (!nameToPId.containsKey(nacName)) { int nacId =
   * EngineAdapter.getPetrinetManipulation().createNac();
   * nameToPId.put(nacName, nacId); this.nacNameToDisplayName.put(nacName,
   * nacDisplayName); Set<Integer> nacs = ruleToNacs.get(ruleId); if (nacs ==
   * null) { nacs = new HashSet<Integer>(); ruleToNacs.put(ruleId, nacs); }
   * nacs.add(nacId); RulePane.getInstance().displayRule(ruleId);
   * EngineAdapter.getRuleManipulation().addNac(nacId, ruleId); PetriTreeNode
   * n = new PetriTreeNode(NodeType.NAC, nacDisplayName); FileTreePane flTrPn
   * = FileTreePane.getInstance(); flTrPn.getTreeModel().insertNodeInto(n,
   * ruleNode, ruleNode.getChildCount());
   * flTrPn.getTree().scrollPathToVisible(new TreePath(n.getPath()));
   * flTrPn.getTree().setSelectionPath( new
   * TreePath(flTrPn.getTreeModel().getPathToRoot(n))); } }
   */

  /*
   * public void createNAC() { System.out.println(PopupMenuListener.class +
   * " - createNAC"); PetriTreeNode ruleNode = (PetriTreeNode)
   * FileTreePane.getInstance().getSelectedNode(); String ruleName =
   * ruleNode.getUserObject().toString(); int ruleId =
   * nameToPId.get(ruleName);
   * System.out.println(".. adding NAC to rule with ruleId : " + ruleId);
   * SimpleDateFormat dateFormatter = new SimpleDateFormat(); String
   * dateString = dateFormatter.format(new Date()); String newNacName =
   * "NAC [" + dateString + "]";
   * EngineAdapter.getRuleManipulation().addNac(nacId, ruleId) }
   */
  /**
   * shows the file choosing dialog.
   *
   * @param intend
   *        the intend what the dialog is shown for.
   * @return result of JFileChooser .showDialog() call.
   */
  private int showDialog(int intend) {

    switch (intend) {
    case PopupMenuListener.DIALOG_CREATE_NET:
      this.fileChooser.setDialogTitle("Create Petrinet File as..");
      return fileChooser.showDialog(null, "Create Net");
    case PopupMenuListener.DIALOG_CREATE_RULE:
      this.fileChooser.setDialogTitle("Create Rule File as..");
      return fileChooser.showDialog(null, "Create Rule");
    case PopupMenuListener.DIALOG_LOAD_NET:
      this.fileChooser.setDialogTitle("Load Petrinet File..");
      return fileChooser.showDialog(null, "Load Net");
    case PopupMenuListener.DIALOG_LOAD_RULE:
      this.fileChooser.setDialogTitle("Load Rule File..");
      return fileChooser.showDialog(null, "Load Rule");
    default:
      break;
    }
    return JFileChooser.ERROR_OPTION;
  }

  /**
   * Makes sure the user wants to overwrite a file
   *
   * @return <code>true</code> if he wants to overwrite, <code>false</code> if
   *         not
   */
  private int userWantsToOverwrite(File file) {

    if (file.exists()) {
      return JOptionPane.showConfirmDialog(null,
        "Die Datei existiert bereits. " + "Möchten Sie sie überspeichern?");
    } else {
      return JOptionPane.YES_OPTION;
    }
  }

  /**
   * Makes sure the <tt>file</tt> ends with ".PNML"
   *
   * @return <tt>file</tt> if it already ends with ".PNML" <br>
   *         else a new file with that ending
   */
  private File ensurePNMLEnding(File file) {

    if (file.getName().endsWith(FILE_EXTENSION)) {
      return file;
    } else if (file.getName().endsWith(FILE_EXTENSION_LOWER_CASE)) {
      return new File(getFilenameWithoutExtension(file) + FILE_EXTENSION);
    } else {
      return new File(file.getPath() + FILE_EXTENSION);
    }
  }

  /**
   * returns the filename without extension (.pnml).
   *
   * @param f
   *        file object to get name from.
   * @return the name of the file.
   */
  private String getFilenameWithoutExtension(File f) {

    // Assuming file is a .PNML file cut last 5 characters off.
    return f.getName().substring(0,
      f.getName().length() - FILE_EXTENSION.length());
  }

  /**
   * returns the PID for the given name.
   *
   * @param name
   *        name to get ID for
   * @return the PID
   */
  public Integer getPidOf(String name) {

    return this.nameToPId.get(name);

  }

  /**
   * returns the PID for the given tree node.
   *
   * @param n
   *        node to get ID for
   * @return the PID
   */
  public Integer getIdForNode(PetriTreeNode n) {

    return nameToPId.get(n.toString());
  }

  // TODO: jetzt obsolet - nac panel wird immer angezeigt
  public boolean ruleHasNacs(int ruleId) {

    Set<Integer> nacs = ruleToNacs.get(ruleId);
    if (nacs != null) {
      return (nacs.size() > 0);
    }
    return false;
  }
}