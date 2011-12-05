package gui2;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** The Panel that contains buttons and slider for simulation purposes like "fire 1 time", "fire k times"
 * "transform 1 time", etc */
public class SimulationPane {
	
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
	public static SimulationPane initiateSimulationPane(int width){
//		getInstance().getSimulationPane().setPreferredSize(new Dimension(width, MainWindow.HEIGHT_TOP_ELEMENTS));
		getInstance().getSimulationPane().setBounds(0, width, width, MainWindow.HEIGHT_TOP_ELEMENTS);
		getInstance().getSimulationPane().setBorder(BorderFactory.createEtchedBorder());
		getInstance().getSimulationPane().setLocation(0, width);
		getInstance().getSimulationPane().setBackground(Color.BLUE);
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
