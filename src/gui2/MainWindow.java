package gui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.EngineMockup;
import engine.ihandler.IPetrinetManipulation;
import exceptions.EngineException;


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
	
	/** PetrinetManipulation aspect of engine */
	private static EngineMockup manipulation;
	
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
	
	public static IPetrinetManipulation getPetrinetManipulation(){
		return manipulation;
	}
	
	/** Private Constructor that configures the main window */
	private MainWindow() {
		manipulation = new EngineMockup();
		try {
			manipulation.build();
		} catch (EngineException e) {
			e.printStackTrace();
		}
		
		initializeMainFrame();
		addEditorPane();
		addAttributePane();
		addSimulationPane();
		addPetrinetPane();
		addFilePane();
		addRulePane();
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
	}

	/** Initialize the Mainpanel in the Center with 2 Rows 
	 * for the Petrinetpane and Rulepane */
	private JPanel initializeCenterPanel() {
		GridLayout layout = new GridLayout(2,1);
		centerPanel = new JPanel(layout);
		
		return centerPanel;
	}

	/** Initialize the Headpanel with Borderlayout and the Dimension for the Panel*/
	private JPanel initializeHeadPanel() {
		BorderLayout layout = new BorderLayout();
		headPanel = new JPanel(layout);
		headPanel.setPreferredSize(HEADER_DIMENSION);
		headPanel.setMinimumSize(HEADER_DIMENSION);
		
		return headPanel;
	}

	/** Initialize the Panel of the left side with 2 Rows for the FilePanes(Petrinet an Rule) 
	 *  And set the Dimension of the Pane*/
	private JPanel initializeLeftPanel() {
		GridLayout layout = new GridLayout(2,1);
		leftPanel = new JPanel(layout);
		leftPanel.setPreferredSize(LEFT_PANEL_DIMENSION);
		leftPanel.setMinimumSize(LEFT_PANEL_DIMENSION);
		
		return leftPanel;
	}
	
	/** Add the Editorpane to the Headpane*/
	private void addEditorPane(){
		EditorPane editorPane = EditorPane.getInstance();
		editorPane.addTo(headPanel);
	}
	
	/** Add the Simulationpane to the Headpane*/
	private void addSimulationPane(){
		SimulationPane simulationPane = SimulationPane.getInstance();
		simulationPane.addTo(headPanel);
	}
	
	/** Add the Petrinetpane to the Centerpane*/
	private void addPetrinetPane(){
		PetrinetPane.getInstance().addTo(centerPanel);
	}
	
	/** Add the Filepanes to the Leftpane*/
	private void addFilePane(){
		FilePane.getPetrinetFilePane().addTo(leftPanel);
		FilePane.getRuleFilePane().addTo(leftPanel);
	}
	
	/** Add the Attributepane to the Headpane*/
	private void addAttributePane(){
		AttributePane.getInstance().addTo(headPanel);
	}
	
	/** Add the Rulepane to the Centerpane*/
	private void addRulePane() {
		RulePane.getInstance().addTo(centerPanel);
		
	}
	
	/** Set Size of Mainframe and make it visible*/
	private void show(){
		mainFrame.pack();
		mainFrame.setBounds(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
		mainFrame.setVisible(true);
	}

}
