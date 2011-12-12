package gui2;

import javax.swing.JFrame;
import javax.swing.JPanel;


import static gui2.Style.*;

/**
 * Main Window that contains all sub areas
 */
class MainWindow {

	/** The main frame of the gui */
	JFrame mainFrame;
	
	/** singleton instance */
	private static MainWindow instance;
	
	
	//static constructor that initiates the singleton instance and constants
	static {
		instance = new MainWindow();
	}
	
	
	/** Returns the only instance of the main window */
	public static MainWindow getInstance() {
		return instance;
	}
	
	/** Private Constructor that configures the main window */
	private MainWindow() {
		initializeMainFrame();
		addEditorPane();
		addSimulationPane();
		addPetrinetPane();
		addFilePane();
		show();
	}

	/** Initializes the main frame with defaults values for title, size and position */
	private void initializeMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Petri Tool");
		mainFrame.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addEditorPane(){
		EditorPane editorPane = EditorPane.getInstance();
		editorPane.addTo(mainFrame);
	}
	
	private void addSimulationPane(){
		SimulationPane simulationPane = SimulationPane.getInstance();
		simulationPane.addTo(mainFrame);
	}
	
	private void addPetrinetPane(){
		PetrinetPane.initiatePetrinetPane().addTo(mainFrame);
	}
	
	private void addFilePane(){
		PetrinetFilePane.getInstance().addTo(mainFrame);
	}
	
	private void show(){
		mainFrame.add(new JPanel());
		mainFrame.pack();
		mainFrame.setBounds(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
		mainFrame.setVisible(true);
	}

}
