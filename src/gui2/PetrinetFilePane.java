package gui2;

import javax.swing.JButton;
import javax.swing.JFrame;
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

	static {
		instance = new PetrinetFilePane();
	}

	private PetrinetFilePane() {
		// petrinetTreeContainer = initiatepetrinetTreeContainer();
		// ruleTreeContainer = initiateRuleTreeContainer();
		newPetrinetButton = initiateNewPetrinetButton();
		savePetrinetButton = initiateSavePetrinetButton();
		loadPetrinetButton = initiateLoadPetrinetButton();
		buttonContainer = initiateButtonContainer(newPetrinetButton,
				savePetrinetButton, loadPetrinetButton);
		petrinetTree = initiatePetrinetTree();
		treeAndButtonContainerWithBorder = initiatetreeAndButtonContainerWithBorder(
				petrinetTree, buttonContainer);
	}

	private JButton initiateLoadPetrinetButton() {
		JButton button = new JButton("Petrinetz laden");
		button.setBounds(LOAD_BUTTON_X, LOAD_BUTTON_Y, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		return button;
	}

	private JButton initiateSavePetrinetButton() {
		JButton button = new JButton("Petrinetz speichern");
		button.setBounds(SAVE_BUTTON_X, SAVE_BUTTON_Y, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		return button;
	}

	// private JTree initiateRuleTree() {
	// JTree tree = new JTree(new DefaultMutableTreeNode("Geladene Regeln"));
	// return tree;
	// }

	private JButton initiateNewPetrinetButton() {
		JButton button = new JButton("Petrinetz erstellen");
		button.setBounds(NEW_BUTTON_X, NEW_BUTTON_Y, BUTTON_WIDTH,
				BUTTON_HEIGHT);
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
			JButton saveButton, JButton loadButton) {
		JPanel panel = new JPanel();
		panel.setLayout(null); // layouting with setbounds(...)
		panel.add(newButton);
		panel.add(saveButton);
		panel.add(loadButton);

		return panel;
	}

	// private JPanel initiateRuleTreeContainer() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// private JPanel initiatepetrinetTreeContainer() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	private JPanel initiatetreeAndButtonContainerWithBorder(JTree petrinetTree,
			JPanel buttonContainer) {
		JPanel panel = new JPanel();
		panel.setLayout(FILE_PANE_LAYOUT);
		panel.setBorder(PETRINET_FILE_PANE_BORDER);
		panel.setBounds(PETRINET_FILE_PANE_X, PETRINET_FILE_PANE_Y,
				PETRINET_FILE_PANE_WIDTH, PETRINET_FILE_PANE_HEIGHT);

		panel.add(new JScrollPane(petrinetTree));
		panel.add(buttonContainer);

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

	public void addTo(JFrame frame) {
		frame.add(getTreeAndButtonContainerWithBorder());
	}

}
