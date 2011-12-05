package gui2;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Main Window that contains all sub areas
 */
public class MainWindow {
	
	/** singleton instance */
	private static MainWindow instance;
	
	public static final int HEIGHT_TOP_ELEMENTS;
	
	//static constructor that initiates the singleton instance and constants
	static {
		HEIGHT_TOP_ELEMENTS = 100;
		instance = new MainWindow();
	}
	
	/** Private Constructor that configures the main window */
	private MainWindow() {
		initializeMainFrame();
		addEditorPane();
		addSimulationPane();
		mainFrame.pack();
		mainFrame.setBounds(0, 0, 400, 400);
	}
	
	/** Returns the only instance of the main window */
	public static MainWindow getInstance() {
		return instance;
	}

	/** The main frame of the gui */
	JFrame mainFrame;

	/** Initializes the main frame with defaults values for title, size and position */
	private void initializeMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Petri Tool");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize((int)(screenSize.width * 0.8d) , (int)(screenSize.height * 0.8d));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	private void addEditorPane(){
		EditorPane editorPane = EditorPane.initiateEditorPane(mainFrame.getWidth() / 3);
		editorPane.addTo(mainFrame);
	}
	
	private void addSimulationPane(){
		SimulationPane simulationPane = SimulationPane.initiateSimulationPane(mainFrame.getWidth() / 3);
		simulationPane.addTo(mainFrame);
	}

}
