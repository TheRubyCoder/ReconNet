package gui2;

import static gui2.Style.DELETE_BUTTON_X;
import static gui2.Style.DELETE_BUTTON_Y;
import static gui2.Style.DELETE_PETRINET;
import static gui2.Style.DELETE_PETRINET_DISABLED_ICON;
import static gui2.Style.DELETE_PETRINET_PRESSED_ICON;
import static gui2.Style.FILE_PANE_ICON_BUTTON_SIZE;
import static gui2.Style.LEFT_PANEL_DIMENSION;
import static gui2.Style.LOAD_BUTTON_X;
import static gui2.Style.LOAD_BUTTON_Y;
import static gui2.Style.LOAD_PETRINET_DISABLED_ICON;
import static gui2.Style.LOAD_PETRINET_ICON;
import static gui2.Style.LOAD_PETRINET_PRESSED_ICON;
import static gui2.Style.NEW_BUTTON_X;
import static gui2.Style.NEW_BUTTON_Y;
import static gui2.Style.NEW_PETRINET_DISABLED_ICON;
import static gui2.Style.NEW_PETRINET_ICON;
import static gui2.Style.NEW_PETRINET_PRESSED_ICON;
import static gui2.Style.SAVE_AS_BUTTON_X;
import static gui2.Style.SAVE_AS_BUTTON_Y;
import static gui2.Style.SAVE_AS_PETRINET_DISABLED_ICON;
import static gui2.Style.SAVE_AS_PETRINET_ICON;
import static gui2.Style.SAVE_AS_PETRINET_PRESSED_ICON;
import static gui2.Style.SAVE_BUTTON_X;
import static gui2.Style.SAVE_BUTTON_Y;
import static gui2.Style.SAVE_PETRINET_DISABLED_ICON;
import static gui2.Style.SAVE_PETRINET_ICON;
import static gui2.Style.SAVE_PETRINET_PRESSED_ICON;
import static gui2.Style.SOUTH_PANEL_HEIHT;

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
 * There are two instances of filePane: petrinet file pane and rule file pane
 * */
class FilePane {

	private static final String FILE_EXTENSION = ".PNML";
	private static final String FILE_EXTENSION_WITHOUT_DOT = "PNML";

	/** Listener for button new petri net */
	private static class NewPetrinetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getPetrinetFilePane().create("Petrinetz");
		}
	}

	private static class PetrinetListSelectionListener implements
			ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				@SuppressWarnings("unchecked")
				String selectedValue = ((JList<String>) e.getSource())
						.getSelectedValue();
				if (selectedValue != null) {
					int pId = FilePane.getPetrinetFilePane()
							.getIdFromSelectedItem();
					PetrinetPane.getInstance().displayPetrinet(pId,
							"Petrinetz: " + selectedValue);
				} else {
					PetrinetPane.getInstance().displayEmpty();
				}
			}
		}
	}

	private static class RuleListSelectionListener implements
			ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				Object selectedValue = ((JList<?>) e.getSource())
						.getSelectedValue();
				if (selectedValue != null) {
					int ruleId = FilePane.getRuleFilePane()
							.getIdFromSelectedItem();
					RulePane.getInstance().displayRule(ruleId);
				} else {
					RulePane.getInstance().displayEmpty();
				}
			}
		}
	}

	/** Listener for button load petri net */
	private static class LoadPetrinetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getPetrinetFilePane().load("Petrinetz");
		}
	}

	/** Listener for button save petri net */
	private static class SavePetrinetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getPetrinetFilePane().saveSelected("Petrinetz");
		}
	}

	/** Listener for button save as petri net */
	private static class SaveAsPetrinetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getPetrinetFilePane().saveSelectedAs("Petrinetz");
		}
	}

	/** Listener for button new rule */
	private static class NewRuleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getRuleFilePane().create("Regel");
		}
	}

	/** Listener for button load rule */
	private static class LoadRuleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getRuleFilePane().load("Regel");
		}
	}

	/** Listener for button save rule */
	private static class SaveRuleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getRuleFilePane().saveSelected("Regel");
		}
	}

	/** Listener for button save rule as */
	private static class SaveAsRuleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getRuleFilePane().saveSelectedAs("Regel");
		}
	}

	/** Listener for button delete rule */
	private static class DeleteRuleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FilePane.getRuleFilePane().delete();
		}
	}

	/** Listener for button delete petrinet */
	private static class DeletePetrinetListener implements ActionListener {
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
		petrinetFilePane = new FilePane("Petrinetz", "Petrinetze",
				new NewPetrinetListener(), new LoadPetrinetListener(),
				new SavePetrinetListener(), new SaveAsPetrinetListener(),
				new DeletePetrinetListener(),
				new PetrinetListSelectionListener());

		ruleFilePane = new FilePane("Regel", "Regeln", new NewRuleListener(),
				new LoadRuleListener(), new SaveRuleListener(),
				new SaveAsRuleListener(), new DeleteRuleListener(),
				new RuleListSelectionListener());
	}

	/** Retruns the only instance of a petrinet file panel */
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
	private JList<String> list;

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
	private DefaultListModel<String> listModel;

	/** To remember what list entry refers to wich petrinet */
	private Map<String, Integer> nameToPId;

	/** To remember waht list entry refers to which filepath */
	private Map<String, File> nameToFilepath;

	// private DefaultMutableTreeNode root;

	/** No default instances */
	private FilePane() {
	};

	/**
	 * Constructor that sets all the instance variables
	 * 
	 * @param type
	 *            "Petrinetz" / "Regel"
	 * @param typePlural
	 *            "Petrinetze" / "Regeln"
	 * @param newListener
	 *            Listener for new button
	 * @param loadListener
	 *            Listener for load button
	 * @param saveListener
	 *            Listener for save button
	 * @param saveAsListener
	 *            Listener for save as button
	 * @param deleteListener
	 *            Listener for delete button
	 */
	private FilePane(String type, String typePlural,
			ActionListener newListener, ActionListener loadListener,
			ActionListener saveListener, ActionListener saveAsListener,
			ActionListener deleteListener,
			ListSelectionListener selectionListener) {
		nameToPId = initiateListItemToPid();
		nameToFilepath = iniateListItemToFilepath();

		newButton = initiateNewButton(type, newListener);
		saveButton = initiateSaveButton(type, saveListener);
		loadButton = initiateLoadButton(type, loadListener);
		saveAsButton = initiateSaveAsButton(type, saveAsListener);
		deleteButton = initiateDeleteButton(type, deleteListener);
		buttonContainer = initiateButtonContainer(newButton, saveButton,
				loadButton, saveAsButton, deleteButton);
		list = initiateList(selectionListener);
		treeAndButtonContainerWithBorder = initiateTreeAndButtonContainerWithBorder(
				list, buttonContainer, typePlural);

		saveAsButton.setEnabled(false);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
		list.setEnabled(false);
	}

	private Map<String, Integer> initiateListItemToPid() {
		return new HashMap<String, Integer>();
	}

	private Map<String, File> iniateListItemToFilepath() {
		return new HashMap<String, File>();
	}

	private JList<String> initiateList(ListSelectionListener listener) {
		listModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listModel);
		list.addListSelectionListener(listener);
		return list;
	}

	/**
	 * Initiates the top level JPanel for layouting
	 * 
	 * @param tree
	 * @param buttonContainer
	 * @param typePlural
	 *            Descriptor: "Petrinetze" / "Regeln"
	 * @return
	 */
	private JPanel initiateTreeAndButtonContainerWithBorder(JList<String> list,
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
	 *            Descriptor: "Petrinetz" / "Regel"
	 * @param saveAsListener
	 *            Listener for button
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
	 *            Descriptor: "Petrinetz" / "Regel"
	 * @param deleteListener
	 *            Listener for button
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
	 *            Descriptor: "Petrinetz" / "Regel"
	 * @param loadListener
	 *            Listener for button
	 * @return
	 */
	private JButton initiateLoadButton(String type, ActionListener loadListener) {
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
	 *            Descriptor: "Petrinetz" / "Regel"
	 * @param saveListener
	 *            Listener for button
	 * @return
	 */
	private JButton initiateSaveButton(String type, ActionListener saveListener) {
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
	 *            Descriptor: "Petrinetz" / "Regel"
	 * @param newListener
	 *            Listener for button
	 * @return
	 */
	private JButton initiateNewButton(String type, ActionListener newListener) {
		JButton button = new JButton(NEW_PETRINET_ICON);
		button.setBounds(NEW_BUTTON_X, NEW_BUTTON_Y,
				FILE_PANE_ICON_BUTTON_SIZE, FILE_PANE_ICON_BUTTON_SIZE);
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
	 * 
	 * */
	private File ensurePNMLEnding(File file) {
		if (file.getName().endsWith(FILE_EXTENSION)) {
			return file;
		} else {
			return new File(file.getPath() + FILE_EXTENSION);
		}
	}

	/** Returns the name of the file without folders and without .PNML */
	private String fileToListEntry(File file) {
		return file.getName().substring(0, file.getName().length() - 5);
	}

	private boolean userWantsToOverwrite(File file) {
		if (file.exists()) {
			int confirm = JOptionPane
					.showConfirmDialog(treeAndButtonContainerWithBorder,
							"Die Datei existiert bereits. Möchten Sie sie überspeichern?");
			if (confirm == JOptionPane.YES_OPTION) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * Creates a new petrinet or rule
	 * 
	 * @param type
	 *            of panel
	 * @return name of the petrinet, if no data entered return <tt>null</tt>
	 */
	String create(String type) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Bitte Speicherort für neue(s) " + type
				+ " aussuchen");
		int ret = fileChooser.showDialog(treeAndButtonContainerWithBorder,
				"Dort erstellen");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = ensurePNMLEnding(fileChooser.getSelectedFile());
			String name = fileToListEntry(file);

			if (userWantsToOverwrite(file)) {
				try {
					file.createNewFile();
					ListSelectionListener petrinetListSelectionListener = list
							.getListSelectionListeners()[0];
					list.removeListSelectionListener(petrinetListSelectionListener);

					listModel.add(0, name);

					// After inserting add the listener again
					list.addListSelectionListener(petrinetListSelectionListener);

					int id;
					if (this == FilePane.getPetrinetFilePane()) {
						id = EngineAdapter.getPetrinetManipulation()
								.createPetrinet();
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
		String name = listModel.get(list.getMinSelectionIndex());
		int id = getIdFromSelectedItem();
		File file = nameToFilepath.get(name);
		try {
			if (this == FilePane.getPetrinetFilePane()) {
				EngineAdapter.getPetrinetManipulation().save(id,
						file.getParent(), fileToListEntry(file),
						FILE_EXTENSION_WITHOUT_DOT);
			} else {
				EngineAdapter.getRuleManipulation().save(id, file.getParent(),
						fileToListEntry(file), FILE_EXTENSION_WITHOUT_DOT);
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
		int ret = fileChooser.showDialog(treeAndButtonContainerWithBorder,
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
								FILE_EXTENSION_WITHOUT_DOT);
					} else {
						EngineAdapter.getRuleManipulation().save(id,
								file.getParent(), fileToListEntry(file),
								FILE_EXTENSION_WITHOUT_DOT);
					}

					loadFromFile(file);
				} catch (EngineException e) {
					throw new ShowAsWarningException(e);
				}
			}
		}
	}

	/** Loads a petrinet or file from a given file */
	private void loadFromFile(File file) {
		int id;
		String name = fileToListEntry(file);
		if (this == FilePane.getPetrinetFilePane()) {
			id = EngineAdapter.getPetrinetManipulation().load(file.getParent(),
					file.getName());
		} else {
			id = EngineAdapter.getRuleManipulation().load(file.getParent(),
					file.getName());
		}
		nameToPId.put(name, id);
		nameToFilepath.put(name, file);

		ListSelectionListener petrinetListSelectionListener = list
				.getListSelectionListeners()[0];
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
						"Die ausgewählte Datei existiert nicht. Haben sie vieleicht die Endung vergessen?");
			}
		}

	}

	/** Deletes all selected petrinets or rules */
	public void delete() {
		int[] selectedIndices = list.getSelectedIndices();
		if (selectedIndices.length < 1) {
			throw new ShowAsInfoException("Es sind keine Dateien ausgewählt");
		} else {
			int loeschen = JOptionPane.showOptionDialog(treeAndButtonContainerWithBorder,
					"Sollen die Dateien vom Dateisystem gelöscht werden?",
					"Löschen", 0, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "Dateien löschen",
							"Nur aus Übersicht löschen" },
					"Nur aus Übersicht löschen");

			ListSelectionListener petrinetListSelectionListener = list
					.getListSelectionListeners()[0];
			list.removeListSelectionListener(petrinetListSelectionListener);

			for (int i = selectedIndices.length - 1; i <= 0; i++) {
				int index = selectedIndices[i];
				String name = listModel.get(index);
				listModel.removeElementAt(index);
				nameToPId.remove(name);
				if(loeschen == 0){
					nameToFilepath.get(name).delete();
				}
				nameToFilepath.remove(name);
			}

			// After removing add the listener again
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
		String selectedValue = list.getSelectedValue();
		if (selectedValue != null) {
			return getIdFrom(selectedValue);
		} else {
			return -1;
		}
	}

	@SuppressWarnings("deprecation")
	// deprecated in 1.7 but recon is developet on 1.6
	public Collection<Integer> getIdsFromSelectedListItems() {
		Collection<Integer> result = new HashSet<Integer>();
		for (Object value : list.getSelectedValues()) {
			result.add(nameToPId.get(value));
		}
		return result;
	}

}
