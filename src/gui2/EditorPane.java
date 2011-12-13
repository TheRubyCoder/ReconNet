package gui2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import static gui2.Style.*;

/** The Panel that contains buttons for Editor-Modes like Pick, Translate, AddTransition, AddPlace etc */
class EditorPane {
	
	/** Internal JPanel */
	private JPanel editorPane;
	
	/** Button for choosing pick mode */
	private JRadioButton pickButton;
	
	/** Button for choosing create place mode */
	private JRadioButton createPlaceButton;
	
	/** Button for choosing create transition mode */
	private JRadioButton createTransitionButton;
	
	/** Button for choosing create arc mode */
	private JRadioButton createArcButton;
	
	
	
	/** Singleton instance  */
	private static EditorPane instance;
	
	/** static constructor that initializes the instance */
	static {
		instance = new EditorPane();
	}
	
	/** Private default constructor */
	private EditorPane() {
		editorPane = new JPanel();
		getEditorPane().setBounds(EDITOR_PANE_X, 
				EDITOR_PANE_Y, 
				EDITOR_PANE_WIDTH, 
				EDITOR_PANE_HEIGHT);
		getEditorPane().setBorder(EDITOR_PANE_BORDER);
		getEditorPane().setLayout(EDITOR_PANE_LAYOUT);
		
		initiateRadioButton();
//		getEditorPane().setLayout(null);//custom layout with setBounds
//		getEditorPane().setLayout(EDITOR_PANE_LAYOUT);
//		pickButton = initiatePickButton();
//		createPlaceButton = initiateCreatePlaceButton();
//		createTransitionButton = initiateCreateTransitionButton();
//		createArcButton = initiateCreateArcButton();
	}
	

	/**
	 * Returns the singleton instance
	 * @return
	 */
	public static EditorPane getInstance(){
		return instance;
	}
	
	/** Returns the internal JPanel */
	private JPanel getEditorPane(){
		return editorPane;
	}
	
	/**
	 * Adds the EditorPane to a frame
	 * @param frame
	 */
	public void addTo(JPanel frame){
		frame.add(getEditorPane());
	}
	
	private void initiateRadioButton() {
		JRadioButton pick = initiateRadioPickButton();
		JRadioButton createArc = initiateRadioArcButton();
		JRadioButton createPlace = initiateRadioPlaceButton();
		JRadioButton createTransition = initiateRadioTransitionButton();
		
		ButtonGroup radiobuttonGroup = new ButtonGroup();
		radiobuttonGroup.add(pick);
		radiobuttonGroup.add(createArc);
		radiobuttonGroup.add(createPlace);
		radiobuttonGroup.add(createTransition);
	}

	private JRadioButton initiateRadioTransitionButton() {
		createTransitionButton = new JRadioButton("Stelle einfügen");
		createTransitionButton.setToolTipText("In diesem Modus fügen Sie mit einem Klick eine neue Transition hinzu");
		
		createTransitionButton.setActionCommand("Transition-erstellen-Button");
		getEditorPane().add(createTransitionButton);
		
		return createTransitionButton;
	}
	
	private JRadioButton initiateRadioPlaceButton() {
		createPlaceButton = new JRadioButton("Stelle einfügen");
		createPlaceButton.setToolTipText("In diesem Modus fügen Sie mit einem Klick eine neue Stelle hinzu");
		
		createPlaceButton.setActionCommand("Stelle-erstellen-Button");
		getEditorPane().add(createPlaceButton);
		
		return createPlaceButton;
	}
	
	private JRadioButton initiateRadioArcButton() {
		createArcButton = new JRadioButton("Kante einfügen");
		createArcButton.setToolTipText("In diesem Modus fügen Sie mit einem Klick eine neue Kante hinzu");
		
		createArcButton.setActionCommand("Kante-erstellen-Button");
		getEditorPane().add(createArcButton);
		
		return createArcButton;
	}


	private JRadioButton initiateRadioPickButton(){
		pickButton = new JRadioButton("Auswählen");
		pickButton.setSelected(true);
		pickButton.setToolTipText("Editieren und verschieben Sie Stellen und Transitionen " +
				"indem sie auf sei klicken. Mit drag & drop auf eine weiße Stelle verschieben " +
				"sie das ganze Petrinetze");
		
		pickButton.setActionCommand("Auswählen-Button");
		getEditorPane().add(pickButton);
		
		return pickButton;
	}
	
	
//	// ##### Pick Button #####
//	
//	private JButton initiatePickButton(){
//		JButton pickButton = new JButton("Auswählen");
//		pickButton.setToolTipText("Editieren und verschieben sie Stellen und Transitionen " +
//				"indem sie auf sei klicken. Mit drag & drop auf eine weiße Stelle verschieben " +
//				"sie das ganze Petrinetze");
//		pickButton.setLocation(EDITOR_PANE_BUTTON_PICK_LOCATION);
//		pickButton.setSize(EDITOR_PANE_BUTTON_PICK_SIZE);
//		pickButton.addActionListener(new PickButtonListener());
//		getEditorPane().add(pickButton);
//		return pickButton;
//	}
//	
//	private class PickButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("Auswählen Button gedrückt");
//		}
//	}
//	
//	// ##### Create Place Button #####
//	
//	private JButton initiateCreatePlaceButton() {
//		JButton createPlaceButton = new JButton("Stelle einfügen");
//		createPlaceButton.setToolTipText("In diesem Modus fügen sie mit einem Klick eine neue Stelle hinzu");
//		createPlaceButton.setLocation(EDITOR_PANE_BUTTON_CREATEPLACE_LOCATION);
//		createPlaceButton.setSize(EDITOR_PANE_BUTTON_CREATEPLACE_SIZE);
//		createPlaceButton.addActionListener(new CreatePlaceButtonListener());
//		getEditorPane().add(createPlaceButton);
//		return createPlaceButton;
//	}
//	
//	private class CreatePlaceButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("Stelle einfügen Button gedrückt");
//		}
//	}
//	
//	// ##### Create Transition Button #####
//	
//	private JButton initiateCreateTransitionButton(){
//		JButton createTransitionButton = new JButton("Transition einfügen");
//		createTransitionButton.setToolTipText("In diesem Modus fügen sie mit einem Klick eine neue Transition hinzu");
//		createTransitionButton.setLocation(EDITOR_PANE_BUTTON_CREATETRANSITION_LOCATION);
//		createTransitionButton.setSize(EDITOR_PANE_BUTTON_CREATETRANSITION_SIZE);
//		createTransitionButton.addActionListener(new CreateTransitionButtonListener());
//		getEditorPane().add(createTransitionButton);
//		return createTransitionButton;
//	}
//	
//	private class CreateTransitionButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("Transition einfügen Button gedrückt");
//		}
//	}
//	
//	// ##### Create Arc Button #####
//	private JButton initiateCreateArcButton(){
//		JButton createArcButton = new JButton("Pfeil einfügen");
//		createArcButton.setToolTipText("In diesem Modus fügen sie mit einem Klick eine neue Kante hinzu");
//		createArcButton.setLocation(EDITOR_PANE_BUTTON_CREATEARC_LOCATION);
//		createArcButton.setSize(EDITOR_PANE_BUTTON_CREATEARC_SIZE);
//		createArcButton.addActionListener(new CreateArcButtonListener());
//		getEditorPane().add(createArcButton);
//		return createArcButton;
//	}
//	
//	private class CreateArcButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("Pfeil einfügen Button gedrückt");
//		}
//	}

}

