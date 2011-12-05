package gui2;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** The Panel that contains buttons for Editor-Modes like Pick, Translate, AddTransition, AddPlace etc */
public class EditorPane {
	
	/** Singleton instance  */
	private static EditorPane instance;
	
	/** static constructor that initializes the instance */
	static {
		instance = new EditorPane();
	}
	
	/** Private default constructor */
	private EditorPane() {
		editorPane = new JPanel();
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
	public static EditorPane initiateEditorPane(int width){
//		getInstance().getEditorPane().setPreferredSize(new Dimension(width, MainWindow.HEIGHT_TOP_ELEMENTS));
		getInstance().getEditorPane().setBounds(0, 0, width, MainWindow.HEIGHT_TOP_ELEMENTS);
		getInstance().getEditorPane().setBorder(BorderFactory.createEtchedBorder());
		getInstance().getEditorPane().setLocation(0, 0);
		getInstance().getEditorPane().setBackground(Color.GREEN);
		return getInstance();
	}
	
	/** Internal JPanel */
	private JPanel editorPane;
	
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
	

}
