/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package gui;

import static gui.Style.BUTTON_HEIGHT;
import static gui.Style.BUTTON_WIDTH;
import static gui.Style.SIMULATION_PANE_BORDER;
import static gui.Style.SIMULATION_PANE_BUTTON_KSTEPS_LOCATION;
import static gui.Style.SIMULATION_PANE_BUTTON_KSTEPS_SIZE;
import static gui.Style.SIMULATION_PANE_BUTTON_ONESTEP_LOCATION;
import static gui.Style.SIMULATION_PANE_BUTTON_ONESTEP_SIZE;
import static gui.Style.SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION;
import static gui.Style.SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION;
import static gui.Style.SIMULATION_PANE_BUTTON_TRANSFORM_SIZE;
import static gui.Style.SIMULATION_PANE_COMBOBOX_LOCATION;
import static gui.Style.SIMULATION_PANE_COMBOBOX_SIZE;
import static gui.Style.SIMULATION_PANE_DIMENSION;
import static gui.Style.SIMULATION_PANE_SLIDER_LOCATION;
import static gui.Style.SIMULATION_PANE_SLIDER_SIZE;
import static gui.Style.SIMULATION_PANE_SPEED_SLIDER_BORDER;
import static gui.Style.SIMULATION_PANE_SPINNER_LOCATION;
import static gui.Style.SIMULATION_PANE_SPINNER_SIZE;
import static gui.Style.SIMULATION_PAUSE_ICON;
import static gui.Style.SIMULATION_PAUSE_ICON_PRESSED;
import static gui.Style.SIMULATION_START_ICON;
import static gui.Style.SIMULATION_START_ICON_DISABLE;
import static gui.Style.SIMULATION_START_ICON_PRESSED;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import exceptions.EngineException;
import exceptions.ShowAsInfoException;

/**
 * The Panel that contains buttons and slider for simulation purposes like
 * "fire 1 time", "fire k times" "transform 1 time", etc
 */
class SimulationPane {
	private static final String CANNOT_STEP_MESSAGE = "Keine der Transitionen kann schalten";
	private static final String CANNOT_TRANSFORM_MESSAGE = "Keine der Regeln passt auf das Petrinetz";
	private static final String CANNOT_STEP_NOR_TRANSFORM_MESSAGE = "Es konnte weder geschaltet noch transformiert werden";

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
	@SuppressWarnings("rawtypes")
	// You have to use raw types for JList as Jenkins will not compile these
	// with
	// parameters
	private JComboBox simulationModePicker;

	/** Button for start and pause the simulation */
	private JButton simulationButton;

	/** The singleton instance of SimulationPane */
	private static SimulationPane instance;

	/** Timer for autorunning simulation at certain speed */
	private Timer simulationTimer;

	/**
	 * Translate abstract speed (1 to 10) to concrete delay in milliseconds
	 * between simulation steps
	 */
	private Map<Integer, Integer> speedToDelay;

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

		speedToDelay = initiateSpeedToDelay();

		oneStepButton = initiateOneStepButton();
		transformButton = initateTransformButton();

		JComponent[] kSpinnerAndButton = initiateKSpinnerAndButton();
		kStepsSpinner = (JSpinner) kSpinnerAndButton[0];
		kStepsButton = (JButton) kSpinnerAndButton[1];

		simulationButton = initiateSimulateButton();
		setSimulationButtonPlay();

		transformSpeedSlider = initiateSpeedSlider();

		simulationModePicker = initiateModePicker();

		simulationTimer = initiateSimulationTimer();

		setSimulationPaneDisable();
	}

	/** Initializes the mapping of abstract speed to concrete delay */
	private Map<Integer, Integer> initiateSpeedToDelay() {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		result.put(1, 2000);
		result.put(2, 1000);
		result.put(3, 500);
		result.put(4, 250);
		result.put(5, 150);
		result.put(6, 50);
		result.put(7, 20);
		result.put(8, 10);
		result.put(9, 5);
		result.put(10, 1);
		return result;
	}

	/** Initializes the timer (not running) */ 
	private Timer initiateSimulationTimer() {
		Timer timer = new Timer(getCurrentDelayInMillis(),
				new SimulationStepper());
		timer.setRepeats(true);
		timer.stop();
		return timer;
	}

	/** Checks whether the simulation is currently running or not */
	boolean isSimulationIsRunning() {
		return simulationTimer.isRunning();
	}

	/**
	 * Initaite and layouting the Combobox and set the modi
	 * 
	 * @return Combobox with the holding modi
	 */
	@SuppressWarnings("rawtypes")
	// You have to use raw types for JList as Jenkins will not compile these
	// with
	// parameters
	private JComboBox initiateModePicker() {
		String[] modi = { "Nur Tokenspiel", "Nur Regeln",
				"Tokenspiel und Regeln" };
		@SuppressWarnings("unchecked")
		// You have to use raw types for JList as Jenkins will not compile these
		// with
		// parameters
		JComboBox comboBox = new JComboBox(modi);
		comboBox.setLocation(SIMULATION_PANE_COMBOBOX_LOCATION);
		comboBox.setSize(SIMULATION_PANE_COMBOBOX_SIZE);
		getSimulationPane().add(comboBox);
		return comboBox;
	}

	/** Initiate and layouting Simulationbutton */
	private JButton initiateSimulateButton() {
		JButton button = new JButton("Start SimulationHandler");
		button.setMnemonic(KeyEvent.VK_P);
		button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button.setLocation(SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION);

		button.addActionListener(new SimulateButtonListener());

		getSimulationPane().add(button);
		return button;
	}

	/** Initiate and layouting Speedslider */
	private JSlider initiateSpeedSlider() {
		JSlider slider = new JSlider(1, 10, 1);
		slider.setLocation(SIMULATION_PANE_SLIDER_LOCATION);
		slider.setSize(SIMULATION_PANE_SLIDER_SIZE);
		slider.setBorder(BorderFactory
				.createTitledBorder(SIMULATION_PANE_SPEED_SLIDER_BORDER));
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		slider.addChangeListener(new SpeedSliderListener());
		getSimulationPane().add(slider);

		return slider;
	}

	/** The Buttonsetting change to playmode and stop the simulation */
	void setSimulationButtonPlay() {
		simulationButton.setIcon(SIMULATION_START_ICON);
		simulationButton.setDisabledIcon(SIMULATION_START_ICON_DISABLE);
		simulationButton.setPressedIcon(SIMULATION_START_ICON_PRESSED);
		simulationButton.setText("start SimulationHandler");
		simulationButton.setIconTextGap(10);

		simulationButton
				.setToolTipText("Startet eine Endlossimulation, bis diese pausiert wird.");
		simulationButton.setRolloverEnabled(true);
	}

	/** The Buttonsetting change to playmode and stop the simulation */
	void setSimulationButtonPause() {
		simulationButton.setIcon(SIMULATION_PAUSE_ICON);
		simulationButton.setPressedIcon(SIMULATION_PAUSE_ICON_PRESSED);
		simulationButton.setText("stop SimulationHandler");

		simulationButton.setToolTipText("laufende Endlossimulation pausieren.");
		simulationButton.setRolloverEnabled(true);
	}

	/**
	 * Initiates k spinner and k button
	 * 
	 * @return index 0: JSpinner<br/>
	 *         index 1: JButton
	 */
	private JComponent[] initiateKSpinnerAndButton() {
		JComponent[] result = new JComponent[2];

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0,
				Integer.MAX_VALUE, 1));
		spinner.setLocation(SIMULATION_PANE_SPINNER_LOCATION);
		spinner.setSize(SIMULATION_PANE_SPINNER_SIZE);
		getSimulationPane().add(spinner);
		result[0] = spinner;

		JButton button = new JButton("k Schritte");
		button.setLocation(SIMULATION_PANE_BUTTON_KSTEPS_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_KSTEPS_SIZE);
		button.addActionListener(new KStepsListener());
		getSimulationPane().add(button);
		result[1] = button;

		return result;
	}

	/**
	 * Initiate and layouting the Transformbutton
	 * 
	 * @return the Transformbutton
	 */
	private JButton initateTransformButton() {
		JButton button = new JButton("Transformieren");
		button.setLocation(SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_TRANSFORM_SIZE);
		button.addActionListener(new TransformButtonListener());
		simulationPane.add(button);
		return button;
	}

	/**
	 * Initiate and layouting the OneStepbutton
	 * 
	 * @return the OneStepbutton
	 */
	private JButton initiateOneStepButton() {
		JButton button = new JButton("Einmal schalten");
		simulationPane.add(button);
		button.setLocation(SIMULATION_PANE_BUTTON_ONESTEP_LOCATION);
		button.setSize(SIMULATION_PANE_BUTTON_ONESTEP_SIZE);
		button.addActionListener(new OneStepListener());
		return button;
	}

	/** returns the internal JPanel */
	private JPanel getSimulationPane() {
		return simulationPane;
	}

	/**
	 * Initiates the SimulationPane with a certain width and default values for
	 * Border, Backgroundcolor etc
	 * 
	 * @return instace of Simulationpane
	 */
	public static SimulationPane getInstance() {
		return instance;
	}

	/**
	 * Adds the SimulationPane to a frame
	 * 
	 * @param frame
	 */
	public void addTo(JPanel frame) {
		JPanel simulationpane = getSimulationPane();
		frame.add(simulationpane, BorderLayout.LINE_END);
	}

	/**
	 * Returns the value if the spinner (k steps)
	 */
	int getK() {
		return Integer.valueOf(kStepsSpinner.getModel().getValue().toString());
	}

	/**
	 * Returns the abstract speed value as displayed in the slider
	 */
	int getSpeed() {
		return transformSpeedSlider.getModel().getValue();
	}

	/**
	 * Returns the concrete speed as delay in milliseconds
	 */
	int getCurrentDelayInMillis() {
		return speedToDelay.get(getSpeed());
	}

	/**
	 * Return the {@link SimulationMode} as displayed in the picker
	 */
	SimulationMode getMode() {
		if (simulationModePicker.getSelectedItem().equals("Nur Tokenspiel")) {
			return SimulationMode.TOKENS;
		} else if (simulationModePicker.getSelectedItem().equals("Nur Regeln")) {
			return SimulationMode.RULES;
		} else {
			return SimulationMode.TOKENS_AND_RULES;
		}
	}

	/** Sets the other panels disable, so the simualion can running */
	void setAllOtherPanesDisable() {
		oneStepButton.setEnabled(false);
		kStepsButton.setEnabled(false);
		kStepsSpinner.setEnabled(false);
		simulationModePicker.setEnabled(false);
		transformButton.setEnabled(false);
		EditorPane.getInstance().setTheHoleEditorPanelDisable();
		AttributePane.getInstance().setTableDisable();
		FilePane.getPetrinetFilePane().disableWholeButtons();
		FilePane.getRuleFilePane().disableWholeButtons();
		// PetrinetPane.getInstance().setPanelDisable();
	}

	/** Sets the other panels disable, so the simualion can running */
	void setAllOtherPanesEnable() {
		oneStepButton.setEnabled(true);
		kStepsButton.setEnabled(true);
		kStepsSpinner.setEnabled(true);
		simulationModePicker.setEnabled(true);
		transformButton.setEnabled(true);
		EditorPane.getInstance().setTheHoleEditorPanelEnable();
		AttributePane.getInstance().setTableEnable();
		FilePane.getPetrinetFilePane().enableWholeButtons();
		FilePane.getRuleFilePane().enableWholeButtons();
	}

	/**
	 * set all Buttons in Simulationpane disable
	 */
	void setSimulationPaneDisable() {
		oneStepButton.setEnabled(false);
		kStepsButton.setEnabled(false);
		kStepsSpinner.setEnabled(false);
		simulationModePicker.setEnabled(false);
		simulationButton.setEnabled(false);
		transformButton.setEnabled(false);
		transformSpeedSlider.setEnabled(false);
	}

	/**
	 * set all Buttons in Simulationpane enable
	 */
	void setSimulationPaneEnable() {
		oneStepButton.setEnabled(true);
		kStepsButton.setEnabled(true);
		kStepsSpinner.setEnabled(true);
		simulationModePicker.setEnabled(true);
		simulationButton.setEnabled(true);
		transformButton.setEnabled(true);
		transformSpeedSlider.setEnabled(true);
	}

	/** Listener for the "play" button */
	private class SimulateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (SimulationPane.getInstance().isSimulationIsRunning()) {
				SimulationPane.getInstance().stopSimulation();
			} else {
				SimulationPane.getInstance().startSimulation();
			}
		}
	}

	/** Stops the simulation */
	public void stopSimulation() {
		simulationTimer.stop();
		setSimulationButtonPlay();
		setAllOtherPanesEnable();
	}

	/** Starts the simulation */
	public void startSimulation() {
		simulationTimer.start();
		setSimulationButtonPause();
		setAllOtherPanesDisable();
	}

	/** Listener for the button "on step" */
	private class OneStepListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				EngineAdapter.getSimulation().fire(
						PetrinetPane.getInstance().getCurrentPetrinetId(), 1);

				PetrinetPane.getInstance().repaint();

			} catch (EngineException e1) {
				throw new ShowAsInfoException(CANNOT_STEP_MESSAGE);

			}

		}

	}

	/** Listener for the button "k steps" */
	private class KStepsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int k = SimulationPane.getInstance().getK();
			try {
				if (getMode() == SimulationMode.TOKENS) {
					try {
						EngineAdapter.getSimulation().fire(
								PetrinetPane.getInstance()
										.getCurrentPetrinetId(), k);
					} catch (EngineException step) {
						throw new ShowAsInfoException(CANNOT_STEP_MESSAGE);
					}
				} else if (getMode() == SimulationMode.RULES) {
					try {
						EngineAdapter.getSimulation().transform(
								PetrinetPane.getInstance()
										.getCurrentPetrinetId(),
								FilePane.getRuleFilePane()
										.getIdsFromSelectedListItems(), k);
					} catch (EngineException transform) {
						throw new ShowAsInfoException(CANNOT_TRANSFORM_MESSAGE);
					}
				} else {
					try {
						EngineAdapter.getSimulation().fireOrTransform(
								PetrinetPane.getInstance()
										.getCurrentPetrinetId(),
								FilePane.getRuleFilePane()
										.getIdsFromSelectedListItems(), k);
					} catch (EngineException both) {
						throw new ShowAsInfoException(
								CANNOT_STEP_NOR_TRANSFORM_MESSAGE);
					}
				}
				PetrinetPane.getInstance().repaint();
			} catch (NumberFormatException e1) {
				// cannot be thrown
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Listener that is invoked continuously when the simulation is running to
	 * perform a single simulation step
	 */
	private class SimulationStepper implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int k = 1;
			try {
				if (getMode() == SimulationMode.TOKENS) {
					try {
						EngineAdapter.getSimulation().fire(
								PetrinetPane.getInstance()
										.getCurrentPetrinetId(), k);
					} catch (EngineException step) {
						SimulationPane.getInstance().stopSimulation();
						throw new ShowAsInfoException(CANNOT_STEP_MESSAGE);
					}
				} else if (getMode() == SimulationMode.RULES) {
					try {
						EngineAdapter.getSimulation().transform(
								PetrinetPane.getInstance()
										.getCurrentPetrinetId(),
								FilePane.getRuleFilePane()
										.getIdsFromSelectedListItems(), k);
					} catch (EngineException transform) {
						SimulationPane.getInstance().stopSimulation();
						throw new ShowAsInfoException(CANNOT_TRANSFORM_MESSAGE);
					}
				} else {
					try {
						EngineAdapter.getSimulation().fireOrTransform(
								PetrinetPane.getInstance()
										.getCurrentPetrinetId(),
								FilePane.getRuleFilePane()
										.getIdsFromSelectedListItems(), k);
					} catch (EngineException both) {
						SimulationPane.getInstance().stopSimulation();
						throw new ShowAsInfoException(
								CANNOT_STEP_NOR_TRANSFORM_MESSAGE);
					}
				}
				PetrinetPane.getInstance().repaint();
			} catch (NumberFormatException e1) {
				// cannot be thrown
				e1.printStackTrace();
				SimulationPane.getInstance().stopSimulation();
			}
		}
	}

	/** Listener for the button "transform" */
	private class TransformButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				EngineAdapter.getSimulation().transform(
						PetrinetPane.getInstance().getCurrentPetrinetId(),
						FilePane.getRuleFilePane()
								.getIdsFromSelectedListItems(), 1);
				PetrinetPane.getInstance().repaint();
			} catch (EngineException e1) {
				throw new ShowAsInfoException(CANNOT_TRANSFORM_MESSAGE);
			}
		}

	}

	/** Listener sets the simulation timer speed when the slider is changed */
	private class SpeedSliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			simulationTimer.setDelay(getCurrentDelayInMillis());
		}
	}

	/**
	 * Enum for distinguishing the simulation modes: TOKENS, RULES,
	 * TOKENS_AND_RULES
	 */
	private enum SimulationMode {
		TOKENS, RULES, TOKENS_AND_RULES
	}
}
