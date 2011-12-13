package gui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


import static gui2.Style.*;

/**
 * Main Window that contains all sub areas
 */
class MainWindow {

	/** The main frame of the gui */
	JFrame mainFrame;
	
	/** The Head Panel */
	JPanel headPanel;
	
	/** The Panel of the left side*/
	JPanel leftPanel;
	
	/** The Panel of the center*/
	JPanel centerPanel;
	
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
		addAttributePane();
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
		mainLayout();
//		mainFrame.setResizable(true);
	}
	
	
	/** Initialize the mainlayout. The head-, left- and centerpane*/
	private void mainLayout() {
		mainFrame.setLayout(new BorderLayout());
		
		JPanel head = initializeHeadPanel();
		mainFrame.getContentPane().add(head, BorderLayout.PAGE_START);
		
		JPanel left = initializeLeftPanel();
		mainFrame.getContentPane().add(left, BorderLayout.WEST);
		
		JPanel center = initializeCenterPanel();
		mainFrame.getContentPane().add(center, BorderLayout.CENTER);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private JPanel initializeCenterPanel() {
		GridLayout layout = new GridLayout(2,1);
		centerPanel = new JPanel(layout);
		centerPanel.setBackground(Color.black);
		
		return centerPanel;
	}

	private JPanel initializeHeadPanel() {
		headPanel = new JPanel(null);
		headPanel.setPreferredSize(HEADER_DIMENSION);
		headPanel.setMinimumSize(HEADER_DIMENSION);
		
		return headPanel;
	}

	private JPanel initializeLeftPanel() {
		GridLayout layout = new GridLayout(2,1);
		leftPanel = new JPanel(layout);
		leftPanel.setPreferredSize(LEFT_PANEL_DIMENSION);
		leftPanel.setMinimumSize(LEFT_PANEL_DIMENSION);
		
		return leftPanel;
	}
	
	
	private void addEditorPane(){
		EditorPane editorPane = EditorPane.getInstance();
		editorPane.addTo(headPanel);
	}
	
	private void addSimulationPane(){
		SimulationPane simulationPane = SimulationPane.getInstance();
		simulationPane.addTo(headPanel);
	}
	
	private void addPetrinetPane(){
		PetrinetPane.initiatePetrinetPane().addTo(centerPanel);
	}
	
	private void addFilePane(){
		PetrinetFilePane.getInstance().addTo(leftPanel);
	}
	
	private void addAttributePane(){
		AttributePane.getInstance().addTo(headPanel);
	}
	
	private void show(){
		mainFrame.add(new JPanel());
		mainFrame.pack();
		mainFrame.setBounds(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
		mainFrame.setVisible(true);
	}

}
