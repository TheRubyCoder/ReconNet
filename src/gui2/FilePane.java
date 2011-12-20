package gui2;

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
import static gui2.Style.PETRINET_FILE_PANE_BORDER;
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * Class for FilePanes (saving and creating petrinets and rules)<br/>
 * They are in one class as they are almost the same<br/>
 * There are two instances of filePane: petrinet file pane and rule file pane
 *  */
class FilePane {
	
	/** Listener for button new petri net */
	private static class NewPetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("create new petrinet pressed");
			PopUp.popUnderConstruction("Petrinetz erstellen");
		}
	}
	
	/** Listener for button load petri net */
	private static class LoadPetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("load petrinet pressed");
		}
	}
	
	/** Listener for button save petri net */
	private static class SavePetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save petrinet pressed");
		}
	}
	
	/** Listener for button save as petri net */
	private static class SaveAsPetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save as petrinet pressed");
		}
	}
	
	/** Listener for button new rule */
	private static class NewRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("create new rule pressed");
		}
	}
	
	/** Listener for button load rule */
	private static class LoadRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("load rule pressed");
		}
	}
	
	/** Listener for button save rule */
	private static class SaveRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save rule pressed");
		}
	}
	
	/** Listener for button save rule as */
	private static class SaveAsRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save as rule pressed");
		}
	}
	
	/** One of the instances. This instance is the file pane for petrinets */
	private static FilePane petrinetFilePane;
	
	/** One of the instances. This instance is the file pane for rules */
	private static FilePane ruleFilePane;
	
	/* Initiating the two "singleton" instances 
	 * (singleton because there can only be one petrinet file pane and only one rule file pane)
	 * The only differences are the descriptions and the listeners (look at constructors)
	 * */
	static {
		petrinetFilePane = new FilePane("Petrinetz", "Petrinetze",
				new NewPetrinetListener(), new LoadPetrinetListener(),
				new SavePetrinetListener(), new SaveAsPetrinetListener());
		
		ruleFilePane = new FilePane("Regel", "Regeln", new NewRuleListener(),
				new LoadRuleListener(), new SaveRuleListener(),
				new SaveAsRuleListener());
	}

	/** Retruns the only instance of a petrinet file panel */
	public static FilePane getPetrinetFilePane() {
		return petrinetFilePane;
	}
	
	/** Returns the only instance of a rule file panel */
	public static FilePane getRuleFilePane(){
		return ruleFilePane;
	}
	
	/** Top level JPanel for layouting. This is added into the main frame */
	private JPanel treeAndButtonContainerWithBorder;

	/** Sub level JPanel for layouting buttons. This is added into top level JPanel */
	private JPanel buttonContainer;

	/** The Tree containing all loaded files*/
	private JTree tree;

	/** The button for creating a new petrinet/rule */
	private JButton newButton;

	/** The button for saving a petrinet/rule */
	private JButton saveButton;

	/** The button for loading a petrinet/rule */
	private JButton loadButton;

	/** The button for saving a petrinet/rule in a certain file*/
	private JButton saveAsButton;
	
	/** No default instances */
	private FilePane() {};

	/**
	 *  Constructor that sets all the instance variables 
	 * @param type "Petrinetz" / "Regel"
	 * @param typePlural "Petrinetze" / "Regeln"
	 * @param newListener Listener for new button
	 * @param loadListener Listener for load button
	 * @param saveListener Listener for save button
	 * @param saveAsListener Listener for save as button
	 */
	private FilePane(String type, 
			String typePlural,
			ActionListener newListener, 
			ActionListener loadListener,
			ActionListener saveListener, 
			ActionListener saveAsListener) {
		newButton = initiateNewButton(type, newListener);
		saveButton = initiateSaveButton(type, saveListener);
		loadButton = initiateLoadButton(type, loadListener);
		saveAsButton = initiateSaveAsButton(type, saveAsListener);
		buttonContainer = initiateButtonContainer(newButton, 
				saveButton,
				loadButton, 
				saveAsButton);
		tree = initiateTree(typePlural);
		treeAndButtonContainerWithBorder = initiateTreeAndButtonContainerWithBorder(
				tree, 
				buttonContainer,
				typePlural);
	}

	/**
	 * Initiates the top level JPanel for layouting
	 * @param tree
	 * @param buttonContainer
	 * @param typePlural Descriptor: "Petrinetze" / "Regeln"
	 * @return
	 */
	private JPanel initiateTreeAndButtonContainerWithBorder(
			JTree tree,
			JPanel buttonContainer,
			String typePlural) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
				"Speichern/Laden - " + typePlural));

		panel.add(new JScrollPane(tree), BorderLayout.CENTER);
		panel.add(buttonContainer, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Initiates the tree for loaded files with root node
	 * @param typePlural
	 * @return
	 */
	private JTree initiateTree(String typePlural) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Geladene "
				+ typePlural);
		MutableTreeNode mock1 = new DefaultMutableTreeNode("Mock 1");
		MutableTreeNode mock2 = new DefaultMutableTreeNode(
				"Mock 2 - sehr langer Dateiname. Ob der wohl noch richtig angezeigt wird?");
		root.add(mock1);
		root.add(mock2);
		for (int i = 0; i <= 10; i++) {
			root.add(new DefaultMutableTreeNode(i));
		}
		JTree tree = new JTree(root);
		return tree;
	}

	/**
	 * Initiates an internal JPanel thats used for layouting the buttons. 
	 * Also adds the buttons to the JPanel
	 * @param newButton
	 * @param saveButton
	 * @param loadButton
	 * @param saveAsButton
	 * @return
	 */
	private JPanel initiateButtonContainer(JButton newButton,
			JButton saveButton, 
			JButton loadButton, 
			JButton saveAsButton) {
		JPanel panel = new JPanel();
		panel.setLayout(null); // layouting with setbounds(...)
		panel.setPreferredSize(new Dimension(LEFT_PANEL_DIMENSION.width,
				SOUTH_PANEL_HEIHT));
		panel.add(newButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(saveAsButton);

		return panel;
	}

	/** 
	 * Initiates the save as button with size, icon, tooltip etc
	 * @param type Descriptor: "Petrinetz" / "Regel"
	 * @param saveAsListener Listener for button
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
	 * Initiates the load button with size, icon, tooltip etc
	 * @param type Descriptor: "Petrinetz" / "Regel"
	 * @param loadListener Listener for button
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
	 * @param type Descriptor: "Petrinetz" / "Regel"
	 * @param saveListener Listener for button
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

		button.addActionListener(saveListener);

		return button;
	}

	/** 
	 * Initiates the new button with size, icon, tooltip etc
	 * @param type Descriptor: "Petrinetz" / "Regel"
	 * @param newListener Listener for button
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

}
