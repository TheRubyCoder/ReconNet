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

package gui;

import static gui.Style.DELETE_BUTTON_X;
import static gui.Style.DELETE_BUTTON_Y;
import static gui.Style.DELETE_PETRINET;
import static gui.Style.DELETE_PETRINET_DISABLED_ICON;
import static gui.Style.DELETE_PETRINET_PRESSED_ICON;
import static gui.Style.FILE_PANE_ICON_BUTTON_SIZE;
import static gui.Style.LEFT_PANEL_DIMENSION;
import static gui.Style.LOAD_BUTTON_X;
import static gui.Style.LOAD_BUTTON_Y;
import static gui.Style.LOAD_PETRINET_DISABLED_ICON;
import static gui.Style.LOAD_PETRINET_ICON;
import static gui.Style.LOAD_PETRINET_PRESSED_ICON;
import static gui.Style.NEW_BUTTON_X;
import static gui.Style.NEW_BUTTON_Y;
import static gui.Style.NEW_PETRINET_DISABLED_ICON;
import static gui.Style.NEW_PETRINET_ICON;
import static gui.Style.NEW_PETRINET_PRESSED_ICON;
import static gui.Style.SAVE_AS_BUTTON_X;
import static gui.Style.SAVE_AS_BUTTON_Y;
import static gui.Style.SAVE_AS_PETRINET_DISABLED_ICON;
import static gui.Style.SAVE_AS_PETRINET_ICON;
import static gui.Style.SAVE_AS_PETRINET_PRESSED_ICON;
import static gui.Style.SAVE_BUTTON_X;
import static gui.Style.SAVE_BUTTON_Y;
import static gui.Style.SAVE_PETRINET_DISABLED_ICON;
import static gui.Style.SAVE_PETRINET_ICON;
import static gui.Style.SAVE_PETRINET_PRESSED_ICON;
import static gui.Style.SOUTH_PANEL_HEIHT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exceptions.EngineException;
import exceptions.ShowAsInfoException;
import exceptions.ShowAsWarningException;

/**
 * Class for FilePanes (saving and creating petrinets and rules)<br/>
 * They are in one class as they are almost the same<br/>
 * There are two instances of filePane: petrinet file pane (
 * {@link FilePane#getPetrinetFilePane()} ) and rule file pane (
 * {@link FilePane#getRuleFilePane()} )
 */
final class FilePane {

  /** The extension a file needs to have with the dot: ".PNML" */
  private static final String FILE_EXTENSION = ".PNML";
  /** The extension a file needs to have without the dot: "PNML" */
  private static final String FILE_EXTENSION_WITHOUT_DOT = "PNML";

  /** Listener for button new petri net */
  private static class NewPetrinetListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getPetrinetFilePane().create("Petrinetz");
    }
  }

  /**
   * A Listener that listens to the selection within the file list for
   * petrinets. Selecting an item in the list will display its respective
   * petrinet
   */
  private static class PetrinetListSelectionListener
    implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {

      if (!e.getValueIsAdjusting()) {
        @SuppressWarnings("rawtypes")
        // You have to use raw types for JList as Jenkins will not
        // compile these with
        // parameters
        String selectedValue =
          (String) ((JList) e.getSource()).getSelectedValue();
        if (selectedValue != null) {
          int pId = FilePane.getPetrinetFilePane().getIdFromSelectedItem();
          PetrinetPane.getInstance().displayPetrinet(pId,
            "Petrinetz: " + selectedValue);
        } else {
          PetrinetPane.getInstance().displayEmpty();
        }
      }
    }
  }

  /**
   * A Listener that listens to the selection within the file list for rules.
   * Selecting an item in the list will display its respective rule
   */
  private static class RuleListSelectionListener
    implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {

      if (!e.getValueIsAdjusting()) {
        @SuppressWarnings("rawtypes")
        // You have to use raw types for JList as Jenkins will not
        // compile these with
        // parameters
        Object selectedValue = ((JList) e.getSource()).getSelectedValue();
        if (selectedValue != null) {
          int ruleId = FilePane.getRuleFilePane().getIdFromSelectedItem();
          RulePane.getInstance().displayRule(ruleId);
        } else {
          RulePane.getInstance().displayEmpty();
        }
      }
    }
  }

  /** Listener for button load petri net */
  private static class LoadPetrinetListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getPetrinetFilePane().load("Petrinetz");
    }
  }

  /** Listener for button save petri net */
  private static class SavePetrinetListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getPetrinetFilePane().saveSelected("Petrinetz");
    }
  }

  /** Listener for button save as petri net */
  private static class SaveAsPetrinetListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getPetrinetFilePane().saveSelectedAs("Petrinetz");
    }
  }

  /** Listener for button new rule */
  private static class NewRuleListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getRuleFilePane().create("Regel");
    }
  }

  /** Listener for button load rule */
  private static class LoadRuleListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getRuleFilePane().load("Regel");
    }
  }

  /** Listener for button save rule */
  private static class SaveRuleListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getRuleFilePane().saveSelected("Regel");
    }
  }

  /** Listener for button save rule as */
  private static class SaveAsRuleListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getRuleFilePane().saveSelectedAs("Regel");
    }
  }

  /** Listener for button delete rule */
  private static class DeleteRuleListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getRuleFilePane().delete();
    }
  }

  /** Listener for button delete petrinet */
  private static class DeletePetrinetListener
    implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      FilePane.getPetrinetFilePane().delete();
    }
  }

  /** One of the instances. This instance is the file pane for petrinets */
  private static FilePane petrinetFilePane;

  /** One of the instances. This instance is the file pane for rules */
  private static FilePane ruleFilePane;

  /*
   * Initiating the two "singleton" instances (singleton because there can
   * only be one petrinet file pane and only one rule file pane) The only
   * differences are the descriptions and the listeners (look at constructors)
   */
  static {
    petrinetFilePane =
      new FilePane("Petrinetz", "Petrinetze", new NewPetrinetListener(),
        new LoadPetrinetListener(), new SavePetrinetListener(),
        new SaveAsPetrinetListener(), new DeletePetrinetListener(),
        new PetrinetListSelectionListener());

    ruleFilePane =
      new FilePane("Regel", "Regeln", new NewRuleListener(),
        new LoadRuleListener(), new SaveRuleListener(),
        new SaveAsRuleListener(), new DeleteRuleListener(),
        new RuleListSelectionListener());
  }

  /** Returns the only instance of a petrinet file panel */
  public static FilePane getPetrinetFilePane() {

    return petrinetFilePane;
  }

  /** Returns the only instance of a rule file panel */
  public static FilePane getRuleFilePane() {

    return ruleFilePane;
  }

  /** Top level JPanel for layouting. This is added into the main frame */
  private JPanel treeAndButtonContainerWithBorder;

  /**
   * Sub level JPanel for layouting buttons. This is added into top level
   * JPanel
   */
  private JPanel buttonContainer;

  /** The List containing all loaded files */
  @SuppressWarnings("rawtypes")
  // You have to use raw types for JList as Jenkins will not compile these
  // with
  // parameters
  private JList list;

  /** The button for creating a new petrinet/rule */
  private JButton newButton;

  /** The button for saving a petrinet/rule */
  private JButton saveButton;

  /** The button for loading a petrinet/rule */
  private JButton loadButton;

  /** The Button for deleting a petrinet/rule */
  private JButton deleteButton;

  /** The button for saving a petrinet/rule in a certain file */
  private JButton saveAsButton;

  /** Model for list */
  @SuppressWarnings("rawtypes")
  // You have to use raw types for JList as Jenkins will not compile these
  // with
  // parameters
  private DefaultListModel listModel;

  /** To remember what list entry refers to wich petrinet */
  private Map<String, Integer> nameToPId;

  /** To remember what list entry refers to which filepath */
  private Map<String, File> nameToFilepath;

  // private DefaultMutableTreeNode root;

  /** No default instances */
  private FilePane() {

  }

  /**
   * Constructor that sets all the instance variables
   *
   * @param type
   *        "Petrinetz" / "Regel"
   * @param typePlural
   *        "Petrinetze" / "Regeln"
   * @param newListener
   *        Listener for new button
   * @param loadListener
   *        Listener for load button
   * @param saveListener
   *        Listener for save button
   * @param saveAsListener
   *        Listener for save as button
   * @param deleteListener
   *        Listener for delete button
   */
  private FilePane(String type, String typePlural,
    ActionListener newListener, ActionListener loadListener,
    ActionListener saveListener, ActionListener saveAsListener,
    ActionListener deleteListener, ListSelectionListener selectionListener) {

    nameToPId = initiateListItemToPid();
    nameToFilepath = iniateListItemToFilepath();

    newButton = initiateNewButton(type, newListener);
    saveButton = initiateSaveButton(type, saveListener);
    loadButton = initiateLoadButton(type, loadListener);
    saveAsButton = initiateSaveAsButton(type, saveAsListener);
    deleteButton = initiateDeleteButton(type, deleteListener);
    buttonContainer =
      initiateButtonContainer(newButton, saveButton, loadButton,
        saveAsButton, deleteButton);
    list = initiateList(selectionListener);
    treeAndButtonContainerWithBorder =
      initiateTreeAndButtonContainerWithBorder(list, buttonContainer,
        typePlural);

    saveAsButton.setEnabled(false);
    saveButton.setEnabled(false);
    deleteButton.setEnabled(false);
    list.setEnabled(false);
  }

  /** Initializes the instance variable {@link FilePane#nameToPId} */
  private Map<String, Integer> initiateListItemToPid() {

    return new HashMap<String, Integer>();
  }

  /** Initializes the instance variable {@link FilePane#nameToFilepath} */
  private Map<String, File> iniateListItemToFilepath() {

    return new HashMap<String, File>();
  }

  /** Initializes the instance variable {@link FilePane#list} */
  @SuppressWarnings({"rawtypes", "unchecked"})
  // You have to use raw types for JList as Jenkins will not compile these
  // with
  // parameters
  private JList
    initiateList(ListSelectionListener listener) {

    listModel = new DefaultListModel();
    JList listRtrn = new JList(listModel);
    listRtrn.addListSelectionListener(listener);
    return listRtrn;
  }

  /**
   * Initiates the top level JPanel for layouting
   *
   * @param tree
   * @param buttonContainer
   * @param typePlural
   *        Descriptor: "Petrinetze" / "Regeln"
   * @return
   */
  @SuppressWarnings("rawtypes")
  private JPanel initiateTreeAndButtonContainerWithBorder(JList list,
    JPanel buttonContainer, String typePlural) {

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), "Geladene " + typePlural));

    panel.add(new JScrollPane(list), BorderLayout.CENTER);
    panel.add(buttonContainer, BorderLayout.SOUTH);

    return panel;
  }

  /**
   * Initiates an internal JPanel thats used for layouting the buttons. Also
   * adds the buttons to the JPanel
   *
   * @param newButton
   * @param saveButton
   * @param loadButton
   * @param saveAsButton
   * @return
   */
  private JPanel initiateButtonContainer(JButton newButton,
    JButton saveButton, JButton loadButton, JButton saveAsButton,
    JButton deleteButton) {

    JPanel panel = new JPanel();
    panel.setLayout(null); // layouting with setbounds(...)
    panel.setPreferredSize(new Dimension(LEFT_PANEL_DIMENSION.width,
      SOUTH_PANEL_HEIHT));
    panel.add(newButton);
    panel.add(saveButton);
    panel.add(loadButton);
    panel.add(saveAsButton);
    panel.add(deleteButton);

    return panel;
  }

  /**
   * Initiates the save as button with size, icon, tooltip etc
   *
   * @param type
   *        Descriptor: "Petrinetz" / "Regel"
   * @param saveAsListener
   *        Listener for button
   * @return
   */
  private JButton initiateSaveAsButton(String type,
    ActionListener saveAsListener) {

    JButton button = new JButton(SAVE_AS_PETRINET_ICON);
    button.setBounds(SAVE_AS_BUTTON_X, SAVE_AS_BUTTON_Y,
      FILE_PANE_ICON_BUTTON_SIZE, FILE_PANE_ICON_BUTTON_SIZE);
    button.setPressedIcon(SAVE_AS_PETRINET_PRESSED_ICON);
    button.setDisabledIcon(SAVE_AS_PETRINET_DISABLED_ICON);

    button.setToolTipText(type + " speichern unter...");
    button.setRolloverEnabled(true);

    button.addActionListener(saveAsListener);

    return button;
  }

  /**
   * Initiates the delete button with size, icon, tooltip etc
   *
   * @param type
   *        Descriptor: "Petrinetz" / "Regel"
   * @param deleteListener
   *        Listener for button
   * @return
   */
  private JButton initiateDeleteButton(String type,
    ActionListener deleteListener) {

    JButton button = new JButton(DELETE_PETRINET);
    button.setBounds(DELETE_BUTTON_X, DELETE_BUTTON_Y,
      FILE_PANE_ICON_BUTTON_SIZE, FILE_PANE_ICON_BUTTON_SIZE);
    button.setPressedIcon(DELETE_PETRINET_PRESSED_ICON);
    button.setDisabledIcon(DELETE_PETRINET_DISABLED_ICON);

    button.setToolTipText(type
      + " aus der Liste entfernen. Wird nicht aus Filesystem gelöscht.");
    button.setRolloverEnabled(true);

    button.addActionListener(deleteListener);

    return button;
  }

  /**
   * Initiates the load button with size, icon, tooltip etc
   *
   * @param type
   *        Descriptor: "Petrinetz" / "Regel"
   * @param loadListener
   *        Listener for button
   * @return
   */
  private JButton
    initiateLoadButton(String type, ActionListener loadListener) {

    JButton button = new JButton(LOAD_PETRINET_ICON);
    button.setBounds(LOAD_BUTTON_X, LOAD_BUTTON_Y,
      FILE_PANE_ICON_BUTTON_SIZE, FILE_PANE_ICON_BUTTON_SIZE);
    button.setPressedIcon(LOAD_PETRINET_PRESSED_ICON);
    button.setDisabledIcon(LOAD_PETRINET_DISABLED_ICON);

    button.setToolTipText(type + " aus Datei laden");
    button.setRolloverEnabled(true);

    button.addActionListener(loadListener);

    return button;
  }

  /**
   * Initiates the save button with size, icon, tooltip etc
   *
   * @param type
   *        Descriptor: "Petrinetz" / "Regel"
   * @param saveListener
   *        Listener for button
   * @return
   */
  private JButton
    initiateSaveButton(String type, ActionListener saveListener) {

    JButton button = new JButton(SAVE_PETRINET_ICON);
    button.setBounds(SAVE_BUTTON_X, SAVE_BUTTON_Y,
      FILE_PANE_ICON_BUTTON_SIZE, FILE_PANE_ICON_BUTTON_SIZE);

    button.setPressedIcon(SAVE_PETRINET_PRESSED_ICON);
    button.setDisabledIcon(SAVE_PETRINET_DISABLED_ICON);
    button.setToolTipText(type + " speichern");

    button.setRolloverEnabled(true);

    if (type == "Petrinetz") {
      button.setMnemonic(KeyEvent.VK_S);
    }
    button.addActionListener(saveListener);

    return button;
  }

  /**
   * Initiates the new button with size, icon, tooltip etc
   *
   * @param type
   *        Descriptor: "Petrinetz" / "Regel"
   * @param newListener
   *        Listener for button
   * @return
   */
  private JButton initiateNewButton(String type, ActionListener newListener) {

    JButton button = new JButton(NEW_PETRINET_ICON);
    button.setBounds(NEW_BUTTON_X, NEW_BUTTON_Y, FILE_PANE_ICON_BUTTON_SIZE,
      FILE_PANE_ICON_BUTTON_SIZE);
    button.setPressedIcon(NEW_PETRINET_PRESSED_ICON);
    button.setDisabledIcon(NEW_PETRINET_DISABLED_ICON);

    button.setToolTipText(type + " erstellen");
    button.setRolloverEnabled(true);

    button.addActionListener(newListener);

    return button;
  }

  /** Adds the internal JPanel into the given JPanel "frame" */
  public void addTo(JPanel frame) {

    frame.add(treeAndButtonContainerWithBorder);
  }

  /**
   * Return the logical id of the pertrinet/rule behind the
   * <code>listItem</code>.
   *
   * @return <code>-1</code> when <code>listItem</code> is not in the list
   */
  int getIdFrom(String listItem) {

    Integer integer = nameToPId.get(listItem);
    if (integer != null) {
      return integer;
    } else {
      return -1;
    }
  }

  /** disable hole buttons and tree */
  void disableWholeButtons() {

    loadButton.setEnabled(false);
    newButton.setEnabled(false);
    saveAsButton.setEnabled(false);
    saveButton.setEnabled(false);
    deleteButton.setEnabled(false);
    list.setEnabled(false);
  }

  /** enable hole buttons and tree */
  void enableWholeButtons() {

    loadButton.setEnabled(true);
    newButton.setEnabled(true);
    saveAsButton.setEnabled(true);
    saveButton.setEnabled(true);
    deleteButton.setEnabled(true);
    list.setEnabled(true);
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
    } else {
      return new File(file.getPath() + FILE_EXTENSION);
    }
  }

  /** Returns the name of the file without folders and without .PNML */
  private String fileToListEntry(File file) {

    String result = file.getName();

    if (file.getName().endsWith(FILE_EXTENSION)) {
      result =
        file.getName().substring(0,
          file.getName().length() - FILE_EXTENSION.length());
    }

    return result;
  }

  /**
   * Makes sure the user wants to overwrite a file
   *
   * @return <code>true</code> if he wants to overwrite, <code>false</code> if
   *         not
   */
  private boolean userWantsToOverwrite(File file) {

    if (file.exists()) {
      int confirm =
        JOptionPane.showConfirmDialog(treeAndButtonContainerWithBorder,
          "Die Datei existiert bereits. " + "Möchten Sie sie überspeichern?");
      return confirm == JOptionPane.YES_OPTION;
    } else {
      return true;
    }
  }

  /**
   * Creates a new petrinet or rule in a certain file defined by the user
   *
   * @param type
   *        of panel
   * @return name of the petrinet, if no data entered return <tt>null</tt>
   */
  @SuppressWarnings("unchecked")
  // You have to use raw types for JList as Jenkins will not compile these
  // with
  // parameters
  String
    create(String type) {

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Bitte Speicherort für neue(s) " + type
      + " aussuchen");
    int ret =
      fileChooser.showDialog(treeAndButtonContainerWithBorder,
        "Dort erstellen");
    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = ensurePNMLEnding(fileChooser.getSelectedFile());
      String name = fileToListEntry(file);

      if (userWantsToOverwrite(file)) {
        try {
          file.createNewFile();
          ListSelectionListener petrinetListSelectionListener =
            list.getListSelectionListeners()[0];
          list.removeListSelectionListener(petrinetListSelectionListener);

          listModel.add(0, name);

          // After inserting add the listener again
          list.addListSelectionListener(petrinetListSelectionListener);

          int id;
          if (this == FilePane.getPetrinetFilePane()) {
            id = EngineAdapter.getPetrinetManipulation().createPetrinet();
          } else {
            id = EngineAdapter.getRuleManipulation().createRule();
          }
          nameToPId.put(name, id);
          nameToFilepath.put(name, file);

          list.setSelectedIndex(0);
          saveSelected(type);
          enableWholeButtons();

          return name;
        } catch (IOException e) {
          throw new ShowAsWarningException(e);
        }
      } else {
        return null;
      }
    }
    return null;
  }

  /** Quick saves the petrinet or rule to the file it is mapped to */
  public void saveSelected(String type) {

    String name = (String) listModel.get(list.getMinSelectionIndex());
    int id = getIdFromSelectedItem();
    File file = nameToFilepath.get(name);
    try {
      if (this == FilePane.getPetrinetFilePane()) {
        EngineAdapter.getPetrinetManipulation().save(id, file.getParent(),
          fileToListEntry(file), FILE_EXTENSION_WITHOUT_DOT,
          PetrinetPane.getInstance().getCurrentNodeSize());
      } else {
        // EngineAdapter.getRuleManipulation().save(id, file.getParent(),
        // fileToListEntry(file), FILE_EXTENSION_WITHOUT_DOT);
        EngineAdapter.getRuleManipulation().saveRuleWithNacs(id,
          file.getParent(), fileToListEntry(file), FILE_EXTENSION_WITHOUT_DOT);
      }
    } catch (EngineException e) {
      PopUp.popError(e);
      e.printStackTrace();
    }
  }

  /**
   * Saves the petrinet or rule to a file that is chosen by the user.
   * Afterwards both petrinets or rules are opended. They are equally but not
   * identical
   */
  public void saveSelectedAs(String type) {

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Bitte Speicherort für neue(s) " + type
      + " aussuchen");
    int ret =
      fileChooser.showDialog(treeAndButtonContainerWithBorder,
        "Dort speichern");
    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = ensurePNMLEnding(fileChooser.getSelectedFile());
      // String name = fileToListEntry(file);

      if (userWantsToOverwrite(file)) {
        try {
          int id = getIdFromSelectedItem();
          if (this == FilePane.getPetrinetFilePane()) {
            EngineAdapter.getPetrinetManipulation().save(id,
              file.getParent(), fileToListEntry(file),
              FILE_EXTENSION_WITHOUT_DOT,
              PetrinetPane.getInstance().getCurrentNodeSize());
          } else {
            // EngineAdapter.getRuleManipulation().save(id, file.getParent(),
            // fileToListEntry(file), FILE_EXTENSION_WITHOUT_DOT);
            EngineAdapter.getRuleManipulation().saveRuleWithNacs(id,
              file.getParent(), fileToListEntry(file),
              FILE_EXTENSION_WITHOUT_DOT);
          }
          String name = fileToListEntry(file);
          // If it is already loaded, remove it. As it will be loaded
          // in the next line
          if (listModel.contains(name)) {
            listModel.removeElement(name);
          }
          loadFromFile(file);
        } catch (EngineException e) {
          throw new ShowAsWarningException(e);
        }
      }
    }
  }

  /** Loads a petrinet or file from a given file */
  @SuppressWarnings("unchecked")
  // You have to use raw types for JList as Jenkins will not compile these
  // with
  // parameters
  private void
    loadFromFile(File file) {

    int id;
    String name = fileToListEntry(file);
    if (this == FilePane.getPetrinetFilePane()) {
      id =
        EngineAdapter.getPetrinetManipulation().load(file.getParent(),
          file.getName());
    } else {
      id =
        EngineAdapter.getRuleManipulation().loadRuleWithNacs(
          file.getParent(), file.getName());
    }
    nameToPId.put(name, id);
    nameToFilepath.put(name, file);

    // Before changing the list programatically the listener has to be
    // removed so no change event will be fired
    ListSelectionListener petrinetListSelectionListener =
      list.getListSelectionListeners()[0];
    list.removeListSelectionListener(petrinetListSelectionListener);

    listModel.add(0, name);

    // After inserting add the listener again
    list.addListSelectionListener(petrinetListSelectionListener);

    list.setSelectedIndex(0);
    enableWholeButtons();
  }

  /** Loads a petrinet or rule from a rule that is chosen by the user */
  public void load(String type) {

    JFileChooser fileChooser = new JFileChooser();

    fileChooser.setDialogTitle("Was möchten sie laden?");

    int open = fileChooser.showOpenDialog(treeAndButtonContainerWithBorder);
    if (open == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      file = ensurePNMLEnding(file);
      String name = fileToListEntry(file);
      if (file.exists()) {
        if (listModel.contains(name)) {
          throw new ShowAsInfoException(
            "Die ausgewählte Datei ist bereits geladen.");
        } else {
          loadFromFile(file);
        }
      } else {
        throw new ShowAsInfoException(
          "Die ausgewählte Datei existiert nicht. "
            + "Haben sie vieleicht die Endung vergessen?");
      }
    }

  }

  /** Deletes all selected petrinets or rules */
  public void delete() {

    int[] selectedIndices = list.getSelectedIndices();
    if (selectedIndices.length < 1) {
      throw new ShowAsInfoException("Es sind keine Dateien ausgewählt");
    } else {
      int loeschen =
        JOptionPane.showOptionDialog(treeAndButtonContainerWithBorder,
          "Sollen die Dateien vom Dateisystem gelöscht werden?", "Löschen",
          0, JOptionPane.QUESTION_MESSAGE, null, new String[]{
            "Dateien löschen", "Nur aus Übersicht löschen"},
          "Nur aus Übersicht löschen");

      // When manipulating the list, the listener must be removed as
      // otherwise selection events would be triggered
      ListSelectionListener petrinetListSelectionListener =
        list.getListSelectionListeners()[0];
      list.removeListSelectionListener(petrinetListSelectionListener);

      for (int i = selectedIndices.length - 1; i <= 0; i++) {
        int index = selectedIndices[i];
        String name = (String) listModel.get(index);
        listModel.removeElementAt(index);
        nameToPId.remove(name);
        if (loeschen == 0) {
          nameToFilepath.get(name).delete();
        }
        nameToFilepath.remove(name);
      }

      // After removing the item, add the listener again
      list.addListSelectionListener(petrinetListSelectionListener);
      list.setSelectedIndex(0);
      if (list.getSelectedValue() == null) {
        if (this == FilePane.getPetrinetFilePane()) {
          PetrinetPane.getInstance().displayEmpty();
        } else {
          RulePane.getInstance().displayEmpty();
        }
      }
    }
  }

  /** Returns the id of the currently selected item */
  private int getIdFromSelectedItem() {

    Object selectedValue = list.getSelectedValue();
    if (selectedValue != null) {
      return getIdFrom((String) selectedValue);
    } else {
      return -1;
    }
  }

  @SuppressWarnings("deprecation")
  // deprecated in 1.7 but recon is developet on 1.6
  public Collection<Integer>
    getIdsFromSelectedListItems() {

    Collection<Integer> result = new HashSet<Integer>();
    for (Object value : list.getSelectedValues()) {
      result.add(nameToPId.get(value));
    }
    return result;
  }

}
