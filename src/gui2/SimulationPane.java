package gui2;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	/** Slider for adjusting simulation speed. Abstract speed */
	private JSlider transformSpeedSlider;
	
	/** Combo box for selecting the simulation mode */
	private JComboBox simulationModePicker;
	
	/** Button for start and pause the simulation*/
	private JButton simulationButton;
	
	/** */
	private boolean simulationIsRunning;
	
	/** The singleton instance of SimulationPane */
	private static SimulationPane instance;
	
	static {
		instance = new SimulationPane();
	}
	
	/** Private default constructor */
	private SimulationPane() {
		
		simulationPane = new JPanel();
		simulationPane.setLayout(null); // custom layout with setBounds(...)
		
		simulationPane.setPreferredSize(SIMULATION_PANE_DIMENSION);
		simulationPane.setMinimumSize(SIMULATION_PANE_DIMENSION);
		
		simulationPane.setBorder(SIMULATION_PANE_BORDER);
		
		oneStepButton = initiateOneStepButton();
		transformButton = initateTransformButton();
		
		JComponent[] kSpinnerAndButton = initiateKSpinnerAndButton();
		kStepsSpinner = (JSpinner) kSpinnerAndButton[0];
		kStepsButton = (JButton) kSpinnerAndButton[1];
		
		simulationButton = initiateSimulateButton();
		transformSpeedSlider = initiateSpeedSlider();
		
		simulationModePicker = initiateModePicker();
	}
	
	boolean getSimulationIsRunning(){
		return simulationIsRunning;
	}
	
	/**
	 * Initaite and layouting the Combobox and set the modi
	 * @return Combobox with the holding modi
	 */
	private JComboBox initiateModePicker() {
		String[] modi = {"Nur Tokenspiel", "Nur Regeln", "Tokenspiel und Regeln"};
		JComboBox comboBox = new JComboBox(modi);
		comboBox.setLocation(SIMULATION_PANE_COMBOBOX_LOCATION);
		comboBox.setSize(SIMULATION_PANE_COMBOBOX_SIZE);
		getSimulationPane().add(comboBox);
		return comboBox;
	}

	/** Initiate and layouting Simulationbutton*/
	private JButton initiateSimulateButton(){
		JButton button = new JButton("start Simulation");
		simulationButton = button;
		button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button.setLocation(SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION);
		
		setSimulationButtonPlay();
		
		button.addActionListener(new SimulateButtonListener());
		
		getSimulationPane().add(button);
		return button;
	}
	
	/** Initiate and layouting Speedslider*/
	private JSlider initiateSpeedSlider(){
		JSlider slider = new JSlider(1, 10, 1);
		slider.setLocation(SIMULATION_PANE_SLIDER_LOCATION);
		slider.setSize(SIMULATION_PANE_SLIDER_SIZE);
		slider.setBorder(BorderFactory.createTitledBorder(
				SIMULATION_PANE_SPEED_SLIDER_BORDER));
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		getSimulationPane().add(slider);
		
		return slider;
	}
	
	
	/** The Buttonsetting change to playmode and stop the simulation*/
	void setSimulationButtonPlay(){
		simulationIsRunning = false;
		
		simulationButton.setIcon(SIMULATION_START_ICON);
		simulationButton.setDisabledIcon(SIMULATION_START_ICON_DISABLE);
		simulationButton.setPressedIcon(SIMULATION_START_ICON_PRESSED);
		
		simulationButton.setToolTipText("startet eine Endlossimulation, bis diese pausiert wird.");
		simulationButton.setRolloverEnabled(true);
	}
	
	/** The Buttonsetting change to playmode and stop the simulation*/
	void setSimulationButtonPause(){
		simulationIsRunning = true;
		
		simulationButton.setIcon(SIMULATION_PAUSE_ICON);
		simulationButton.setPressedIcon(SIMULATION_PAUSE_ICON_PRESSED);
		
		simulationButton.setToolTipText("laufende Endlossimulation pausieren.");
		simulationButton.setRolloverEnabled(true);
	}
	


	/** Initiates k spinner and k button
	 * @return index 0: JSpinner<br/>index 1: JButton
	 */
	private JComponent[] initiateKSpinnerAndButton() {
		JComponent[] result = new JComponent[2];
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		spinner.setLocation(SIMULATION_PANE_SPINNER_LOCATION);
		spinner.setSize(SIMULATION_PANE_SPINNER_SIZE);
		getSimulationPane().add(spinner);
		result[0] = spinner;
		
		JButton button = new JButton("k Schritte");
		button.setLocation(SIMULATION_PANE_BUTTON_KSTEPS_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_KSTEPS_SIZE);
		getSimulationPane().add(button);
		result[1] = button;
		
		return result;
	}

	/** 
	 * Initiate and layouting the Transformbutton
	 * @return the Transformbutton
	 */
	private JButton initateTransformButton() {
		JButton button = new JButton("Transformieren");
		button.setLocation(SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_TRANSFORM_SIZE);
		simulationPane.add(button);
		return button;
	}
	
	/** 
	 * Initiate and layouting the OneStepbutton
	 * @return the OneStepbutton
	 */
	private JButton initiateOneStepButton() {
		JButton button = new JButton("Einmal schalten");
		simulationPane.add(button);
		button.setLocation(SIMULATION_PANE_BUTTON_ONESTEP_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_ONESTEP_SIZE);
		return button;
	}

	/** returns the internal JPanel */
	private JPanel getSimulationPane(){
		return simulationPane;
	}

	/**
	 * Initiates the SimulationPane with a certain width and default values for Border, Backgroundcolor etc
	 * @return instace of Simulationpane
	 */
	public static SimulationPane getInstance(){
		return instance;
	}
	
	/**
	 * Adds the SimulationPane to a frame
	 * @param frame
	 */
	public void addTo(JPanel frame){
		JPanel simulationpane = getSimulationPane();
		frame.add(simulationpane, BorderLayout.LINE_END);
	}
	
	/** Sets the other panels disable, so the simualion can running*/
	void setAllOtherPanesDisable(){
		oneStepButton.setEnabled(false);
		kStepsButton.setEnabled(false);
		kStepsSpinner.setEnabled(false);
		simulationModePicker.setEnabled(false);
		transformButton.setEnabled(false);
		EditorPane.getInstance().setTheHoleEditorPanelDisable();
		AttributePane.getInstance().setTableDisable();
		FilePane.getPetrinetFilePane().setHoleButtonsDisable();
		FilePane.getRuleFilePane().setHoleButtonsDisable();
	}
	
	/** Sets the other panels disable, so the simualion can running*/
	void setAllOtherPanesEnable(){
		oneStepButton.setEnabled(true);
		kStepsButton.setEnabled(true);
		kStepsSpinner.setEnabled(true);
		simulationModePicker.setEnabled(true);
		transformButton.setEnabled(false);
		EditorPane.getInstance().setTheHoleEditorPanelEnable();
		AttributePane.getInstance().setTableEnable();
		FilePane.getPetrinetFilePane().setHoleButtonsEnable();
		FilePane.getRuleFilePane().setHoleButtonsEnable();
	}
	
	
	void startAndPauseSimulation(){
		//TODO: simulation starten und stoppen
		PopUp.popUnderConstruction("Simulation starten Button");
	}
	
	
	
	
	public class SimulateButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(SimulationPane.getInstance().getSimulationIsRunning()){
				SimulationPane.getInstance().setSimulationButtonPlay();
				setAllOtherPanesEnable();
			}else{
				SimulationPane.getInstance().setSimulationButtonPause();
				setAllOtherPanesDisable();
//				SimulationPane.getInstance().startAndPauseSimulation();
			}
			
		}
		
	}
}
