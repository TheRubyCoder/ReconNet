package gui2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static gui2.Style.*;

/** The Panel that contains buttons for Editor-Modes like Pick, Translate, AddTransition, AddPlace etc */
class EditorPane {
	
	/** Internal JPanel */
	private JPanel editorPane;
	
	/** Button for choosing pick mode */
	private JButton pickButton;
	
	/** Button for choosing create place mode */
	private JButton createPlaceButton;
	
	/** Button for choosing create transition mode */
	private JButton createTransitionButton;
	
	/** Button for choosing create arc mode */
	private JButton createArcButton;
	
	
	
	/** Singleton instance  */
	private static EditorPane instance;
	
	/** static constructor that initializes the instance */
	static {
		instance = new EditorPane();
	}
	
	/** Private default constructor */
	private EditorPane() {
		editorPane = new JPanel();
		pickButton = initiatePickButton();
		createPlaceButton = initiateCreatePlaceButton();
		createTransitionButton = initiateCreateTransitionButton();
		createArcButton = initiateCreateArcButton();
	}
	
	/** Returns the singleton instance of the editorPane */
	public static EditorPane getInstance() {
		return instance;
	}
	
	/**
	 * Initiates the EditorPane with a certain width and default values for Border, Backgroundcolor etc
	 * @param width
	 * @return
	 */
	public static EditorPane initiateEditorPane(){
		getInstance().getEditorPane().setBounds(0, 0, WIDTH_EDITOR_PANE, HEIGHT_TOP_ELEMENTS);
		getInstance().getEditorPane().setBorder(BORDER_EDITOR_PANE);
		getInstance().getEditorPane().setLayout(EDITOR_PANE_LAYOUT);
		return getInstance();
	}
	
	/** Returns the internal JPanel */
	private JPanel getEditorPane(){
		return editorPane;
	}
	
	/**
	 * Adds the EditorPane to a frame
	 * @param frame
	 */
	public void addTo(JFrame frame){
		frame.add(getEditorPane());
	}
	
	// ##### Pick Button #####
	
	private JButton initiatePickButton(){
		JButton pickButton = new JButton("Auswählen");
		pickButton.addActionListener(new PickButtonListener());
		getEditorPane().add(pickButton);
		return pickButton;
	}
	
	private class PickButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Auswählen Button gedrückt");
		}
	}
	
	// ##### Create Place Button #####
	
	private JButton initiateCreatePlaceButton() {
		JButton createPlaceButton = new JButton("Stelle einfügen");
//		createPlaceButton.setBounds(
//				WIDTH_SIMULATION_PANE/2, 
//				0, 
//				WIDTH_SIMULATION_PANE / 3,
//				HEIGHT_TOP_ELEMENTS/3);
		createPlaceButton.addActionListener(new CreatePlaceButtonListener());
		getEditorPane().add(createPlaceButton);
		return createPlaceButton;
	}
	
	private class CreatePlaceButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Stelle einfügen Button gedrückt");
		}
	}
	
	// ##### Create Transition Button #####
	
	private JButton initiateCreateTransitionButton(){
		JButton createTransitionButton = new JButton("Transition einfügen");
		createTransitionButton.addActionListener(new CreateTransitionButtonListener());
		getEditorPane().add(createTransitionButton);
		return createTransitionButton;
	}
	
	private class CreateTransitionButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Transition einfügen Button gedrückt");
		}
	}
	
	// ##### Create Arc Button #####
	private JButton initiateCreateArcButton(){
		JButton createArcButton = new JButton("Pfeil einfügen");
		createArcButton.addActionListener(new CreateArcButtonListener());
		getEditorPane().add(createArcButton);
		return createArcButton;
	}
	
	private class CreateArcButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Pfeil einfügen Button gedrückt");
		}
	}

}

