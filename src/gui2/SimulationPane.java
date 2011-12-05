package gui2;


import javax.swing.JFrame;
import javax.swing.JPanel;

import static gui2.Style.*;

/** The Panel that contains buttons and slider for simulation purposes like "fire 1 time", "fire k times"
 * "transform 1 time", etc */
class SimulationPane {
	
	/** Internal JPanel */
	private JPanel simulationPane;
	
	/** The singleton instance of SimulationPane */
	private static SimulationPane instance;
	
	static {
		instance = new SimulationPane();
	}
	
	/** Private default constructor */
	private SimulationPane() {
		simulationPane = new JPanel();
	}
	
	public static SimulationPane getInstance(){
		return instance;
	}
	
	/** returns the internal JPanel */
	private JPanel getSimulationPane(){
		return simulationPane;
	}

	/**
	 * Initiates the EditorPane with a certain width and default values for Border, Backgroundcolor etc
	 * @param width
	 * @return
	 */
	public static SimulationPane initiateSimulationPane(){
//		getInstance().getSimulationPane().setPreferredSize(new Dimension(width, MainWindow.HEIGHT_TOP_ELEMENTS));
		getInstance().getSimulationPane().setBounds(
				WIDTH_EDITOR_PANE, 
				0, 
				WIDTH_SIMULATION_PANE, 
				HEIGHT_TOP_ELEMENTS);
		getInstance().getSimulationPane().setBorder(BORDER_SIMULATION_PANE);
		return getInstance();
	}
	
	/**
	 * Adds the SimulationPane to a frame
	 * @param frame
	 */
	public void addTo(JFrame frame){
		frame.add(getSimulationPane());
	}
}
