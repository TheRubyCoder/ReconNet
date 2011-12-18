package gui2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import static gui2.Style.*;

/** Singleton class that represents the file tree at the left side */
public class PetrinetFilePane {

	private static PetrinetFilePane instance;

	private JPanel treeAndButtonContainerWithBorder;

	private JPanel buttonContainer;

	private JTree petrinetTree;

	private JButton newPetrinetButton;

	private JButton savePetrinetButton;

	private JButton loadPetrinetButton;
	
	private JButton saveAsPetrinetButton;

	static {
		instance = new PetrinetFilePane();
	}

	private PetrinetFilePane() {
		// petrinetTreeContainer = initiatepetrinetTreeContainer();
		// ruleTreeContainer = initiateRuleTreeContainer();
		newPetrinetButton = initiateNewPetrinetButton();
		savePetrinetButton = initiateSavePetrinetButton();
		loadPetrinetButton = initiateLoadPetrinetButton();
		saveAsPetrinetButton = initiateSaveAsPetrinetButton();
		buttonContainer = initiateButtonContainer(newPetrinetButton,
				savePetrinetButton, loadPetrinetButton,saveAsPetrinetButton);
		petrinetTree = initiatePetrinetTree();
		treeAndButtonContainerWithBorder = initiatetreeAndButtonContainerWithBorder(
				petrinetTree, buttonContainer);
	}

	private JButton initiateSaveAsPetrinetButton() {
		JButton button = new JButton(SAVE_AS_PETRINET_ICON);
		button.setBounds(SAVE_AS_BUTTON_X, SAVE_AS_BUTTON_Y, FILE_PANE_ICON_BUTTON_SIZE,
				FILE_PANE_ICON_BUTTON_SIZE);
		button.setPressedIcon(SAVE_AS_PETRINET_PRESSED_ICON);
		button.setDisabledIcon(SAVE_AS_PETRINET_DISABLED_ICON);
		
		button.setToolTipText("Petrinetz speichern unter...");
		button.setRolloverEnabled(true);
		
		return button;
	}

	private JButton initiateLoadPetrinetButton() {
		JButton button = new JButton(LOAD_PETRINET_ICON);
		button.setBounds(LOAD_BUTTON_X, LOAD_BUTTON_Y, FILE_PANE_ICON_BUTTON_SIZE,
				FILE_PANE_ICON_BUTTON_SIZE);
		button.setPressedIcon(LOAD_PETRINET_PRESSED_ICON);
		button.setDisabledIcon(LOAD_PETRINET_DISABLED_ICON);
		
		button.setToolTipText("Petrinetz aus Datei laden.");
		button.setRolloverEnabled(true);
		
		return button;
	}

	private JButton initiateSavePetrinetButton() {
		JButton button = new JButton(SAVE_PETRINET_ICON);
		button.setBounds(SAVE_BUTTON_X, SAVE_BUTTON_Y, FILE_PANE_ICON_BUTTON_SIZE,
				FILE_PANE_ICON_BUTTON_SIZE);

		button.setPressedIcon(SAVE_PETRINET_PRESSED_ICON);
		button.setDisabledIcon(SAVE_PETRINET_DISABLED_ICON);
		
		button.setToolTipText("Petrinetz speichern.");
		button.setRolloverEnabled(true);
		
		return button;
	}


	private JButton initiateNewPetrinetButton() {
		JButton button = new JButton(NEW_PETRINET_ICON);
		button.setBounds(NEW_BUTTON_X, NEW_BUTTON_Y, FILE_PANE_ICON_BUTTON_SIZE,
				FILE_PANE_ICON_BUTTON_SIZE);
		button.setPressedIcon(NEW_PETRINET_PRESSED_ICON);
		button.setDisabledIcon(NEW_PETRINET_DISABLED_ICON);
		
		button.setToolTipText("neues Petrinetz erstellen.");
		button.setRolloverEnabled(true);
		
		return button;
	}

	private JTree initiatePetrinetTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				"Geladene Petrinetze");
		MutableTreeNode mock1 = new DefaultMutableTreeNode("Mock petrinetz 1");
		MutableTreeNode mock2 = new DefaultMutableTreeNode(
				"Mock petrinetz 2 - sehr langer Dateiname. Ob der wohl noch richtig angezeigt wird?");
		root.add(mock1);
		root.add(mock2);
		for (int i = 0; i <= 10; i++) {
			root.add(new DefaultMutableTreeNode(i));
		}
		JTree tree = new JTree(root);
		return tree;
	}

	private JPanel initiateButtonContainer(JButton newButton,
			JButton saveButton, JButton loadButton, JButton saveAsButton) {
		JPanel panel = new JPanel();
		panel.setLayout(null); // layouting with setbounds(...)
		panel.setPreferredSize(new Dimension( LEFT_PANEL_DIMENSION.width,SOUTH_PANEL_HEIHT));
		panel.add(newButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(saveAsButton);

		return panel;
	}

	private JPanel initiatetreeAndButtonContainerWithBorder(JTree petrinetTree,
			JPanel buttonContainer) {
		JPanel panel = new JPanel();
		panel.setLayout(FILE_PANE_LAYOUT);
		panel.setBorder(PETRINET_FILE_PANE_BORDER);

		panel.add(new JScrollPane(petrinetTree),BorderLayout.CENTER);
		panel.add(buttonContainer,BorderLayout.SOUTH);

		return panel;
	}

	public static PetrinetFilePane getInstance() {
		return instance;
	}

	private JPanel getTreeAndButtonContainerWithBorder() {
		return treeAndButtonContainerWithBorder;
	}

	private JPanel getButtonContainer() {
		return buttonContainer;
	}

	private JTree getPetrinetTree() {
		return petrinetTree;
	}

	public void addTo(JPanel frame) {
		frame.add(getTreeAndButtonContainerWithBorder());
	}

}
