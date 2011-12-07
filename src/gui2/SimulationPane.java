package gui2;


import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import static gui2.Style.*;

/** The Panel that contains buttons and slider for simulation purposes like "fire 1 time", "fire k times"
 * "transform 1 time", etc */
class SimulationPane {
	
	/** Internal JPanel */
	private JPanel simulationPane;
	
	/** Button for simulating one step */
	private JButton oneStepButton;
	
	private JButton kStepsButton;
	
	/** Button for transforming the petrinet */
	private JButton transformButton;
	
	/** Spinner for defining how many steps are simulated */
	private JSpinner kStepsSpinner;
	
	/** Slider for adjusting simulation speed. Value = Ticks per minute */
	private JSlider transformSpeedSlider;
	
	/** The singleton instance of SimulationPane */
	private static SimulationPane instance;
	
	static {
		instance = new SimulationPane();
	}
	
	/** Private default constructor */
	private SimulationPane() {
//		simulationPane = new JPanel(new GridLayout(2,3,10,10));
		simulationPane = new JPanel();
		simulationPane.setLayout(null);
		
		oneStepButton = initiateOneStepButton();
		transformButton = initateTransformButton();
		
		JComponent[] kSpinnerAndButton = initiateKSpinnerAndButton();
		kStepsSpinner = (JSpinner) kSpinnerAndButton[0];
		kStepsButton = (JButton) kSpinnerAndButton[1];
		
		JComponent[] simulateButtonAndSlider = initiateSimulateButtonAndSlider();
		transformButton = (JButton) simulateButtonAndSlider[0];
		transformSpeedSlider = (JSlider) simulateButtonAndSlider[1];
	}
	
	/** Initiates simulate button and slider
	 * @return index 0: JButton<br/>index 1: JSlider
	 */
	private JComponent[] initiateSimulateButtonAndSlider() {
		JComponent[] result = new JComponent[2];
		
		JButton button = new JButton("Simulation starten");
		button.setSize(BUTTON_WIDHT, BUTTON_HEIGHT);
//		button.setBounds(0, 0, BUTTON_WIDHT, BUTTON_HEIGHT);
		getSimulationPane().add(button);
		
		JSlider slider = new JSlider(1, 10, 1);
		slider.setBorder(BorderFactory.createTitledBorder(SIMULATION_PANE_SPEED_SLIDER_BORDER));
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		getSimulationPane().add(slider);
		
		return result;
	}


	/** Initiates k spinner and k button
	 * @return index 0: JSpinner<br/>index 1: JButton
	 */
	private JComponent[] initiateKSpinnerAndButton() {
		JComponent[] result = new JComponent[2];
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		spinner.setBounds(0, 0, 20, 20);
		getSimulationPane().add(spinner);
		result[0] = spinner;
		
		JButton button = new JButton("k Schritte schalten");
		button.setLocation(SIMULATION_PANE_BUTTON_KSTEPS_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_KSTEPS_SIZE);
		getSimulationPane().add(button);
		result[1] = button;
		
		return result;
	}

	private JButton initateTransformButton() {
		JButton button = new JButton("Transformieren");
		simulationPane.add(button);

		return button;
	}

	private JButton initiateOneStepButton() {
		JButton button = new JButton("Einmal schalten");
		simulationPane.add(button);
		button.setLocation(SIMULATION_PANE_BUTTON_ONESTEP_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_ONESTEP_SIZE);
		return button;
	}

	public static SimulationPane getInstance(){
		return instance;
	}
	
	/** returns the internal JPanel */
	private JPanel getSimulationPane(){
		return simulationPane;
	}

	/**
	 * Initiates the SimulationPane with a certain width and default values for Border, Backgroundcolor etc
	 * @return
	 */
	public static SimulationPane initiateSimulationPane(){
		getInstance().getSimulationPane().setBounds(
				SIMULATION_PANEL_X, 
				SIMULATION_PANEL_Y, 
				SIMULATION_PANE_WIDTH, 
				SIMULATION_PANE_HEIGHT);
		getInstance().getSimulationPane().setBorder(SIMULATION_PANE_BORDER);
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
