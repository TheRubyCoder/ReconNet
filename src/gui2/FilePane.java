package gui2;

import static gui2.Style.FILE_PANE_ICON_BUTTON_SIZE;
import static gui2.Style.FILE_PANE_LAYOUT;
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
import static gui2.Style.PETRINET_FILE_PANE_HEIGHT;
import static gui2.Style.PETRINET_FILE_PANE_WIDTH;
import static gui2.Style.PETRINET_FILE_PANE_X;
import static gui2.Style.PETRINET_FILE_PANE_Y;
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
 * Class for FilePanes (saving and creating petrinets and rules)
 * They are in one class as they are almost the same
 *  */
class FilePane {
	
	private static FilePane petrinetFilePane;
	
	private static FilePane ruleFilePane;
	
	static {
		petrinetFilePane = new FilePane("Petrinetz", 
				"Petrinetze", 
				new NewPetrinetListener(), 
				new LoadPetrinetListener(), 
				new SavePetrinetListener(), 
				new SaveAsPetrinetListener());
		
		ruleFilePane = new FilePane("Regel", 
				"Regeln", 
				new NewRuleListener(), 
				new LoadRuleListener(), 
				new SaveRuleListener(), 
				new SaveAsRuleListener());
	}

	public static FilePane getPetrinetFilePane() {
		return petrinetFilePane;
	}
	
	public static FilePane getRuleFilePane(){
		return ruleFilePane;
	}
	
	private static class NewPetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("create new petrinet pressed");
		}
	}
	
	private static class LoadPetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("load petrinet pressed");
		}
	}
	
	private static class SavePetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save petrinet pressed");
		}
	}
	
	private static class SaveAsPetrinetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save as petrinet pressed");
		}
	}

	private static class NewRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("create new rule pressed");
		}
	}
	
	private static class LoadRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("load rule pressed");
		}
	}
	
	private static class SaveRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save rule pressed");
		}
	}
	
	private static class SaveAsRuleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("save as rule pressed");
		}
	}
	
	private JPanel treeAndButtonContainerWithBorder;

	private JPanel buttonContainer;

	private JTree tree;

	private JButton newButton;

	private JButton saveButton;

	private JButton loadButton;

	private JButton saveAsButton;
	
	private FilePane() {};

	private FilePane(String type, String typePlural,
			ActionListener newListener, ActionListener loadListener,
			ActionListener saveListener, ActionListener saveAsListener) {
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

	private JPanel initiateTreeAndButtonContainerWithBorder(JTree tree,
			JPanel buttonContainer,
			String typePlural) {
		JPanel panel = new JPanel();
		panel.setLayout(FILE_PANE_LAYOUT);
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
				"Speichern/Laden - " + typePlural));
//				PETRINET_FILE_PANE_BORDER);
		panel.setBounds(PETRINET_FILE_PANE_X, PETRINET_FILE_PANE_Y,
				PETRINET_FILE_PANE_WIDTH, PETRINET_FILE_PANE_HEIGHT);

		panel.add(new JScrollPane(tree), BorderLayout.CENTER);
		panel.add(buttonContainer, BorderLayout.SOUTH);

		return panel;
	}

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
	
	public void addTo(JPanel frame) {
		frame.add(treeAndButtonContainerWithBorder);
	}

}
