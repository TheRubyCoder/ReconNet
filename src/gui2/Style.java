package gui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;


/** Utility class for constants of gui styling */
class Style {
	
	private Style() {}
	
	/* ************************************* 
	 * Main Window
	 * *************************************/

	/** Total width of main window */
	public static final int TOTAL_WIDTH = 1000;
	
	/** Total height of main window */
	public static final int TOTAL_HEIGHT = 700;
	
	/** Dimension of Header */
	public static final Dimension HEADER_DIMENSION = new Dimension(1000,125);
	
	/** Dimension of left Panel */
	public static final Dimension LEFT_PANEL_DIMENSION = new Dimension(200,575);
	

	
	/* ************************************* 
	 * Commons
	 * *************************************/
	
	/** Height of Panels with buttons at top of gui */
	private static final int HEIGHT_TOP_ELEMENTS = 125;	

	
	private static final int SPACING_BUTTONS = 5;
	
	
	public static final int BUTTON_HEIGHT = 30;
	
	public static final int BUTTON_WIDTH = 150;
	
	private static final int INSET_TOP = 20;
	
	private static final int INSET_LEFT = 10;
	
	public static final BorderLayout FILE_PANE_LAYOUT = new BorderLayout();
	
	
	/* ************************************* 
	 * Editor Pane
	 * *************************************/
	
	/** Size of the Editor Pane*/
	public static final Dimension EDITOR_PANE_DIMENSION = new Dimension(INSET_LEFT + 137, HEIGHT_TOP_ELEMENTS);
	
	/** Border of editor pane */
	public static final Border EDITOR_PANE_BORDER = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"Editieren");
	
	/** Layout of editor pane */
	public static final GridLayout EDITOR_PANE_LAYOUT = new GridLayout(4,1,10,10);

	/* ************************************* 
	 * Attribute pane
	 * *************************************/
	
	
	public static final Dimension ATTRIBUTE_PANE_DIMENSION = new Dimension(225,HEIGHT_TOP_ELEMENTS);
	
	public static final Border ATTRIBUTE_PANE_BORDER = BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Knoten-Attribute");
	
	
	/* ************************************* 
	 * Simulation pane
	 * *************************************/
	
	public static final Dimension SIMULATION_PANE_DIMENSION = new Dimension(INSET_LEFT +
																			BUTTON_WIDTH * 3 +
																			SPACING_BUTTONS * 3, HEIGHT_TOP_ELEMENTS);
	
	public static final Point SIMULATION_PANE_BUTTON_ONESTEP_LOCATION =
		new Point(0 + INSET_LEFT, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_ONESTEP_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_KSTEPS_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + BUTTON_WIDTH/3 + SPACING_BUTTONS, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_KSTEPS_SIZE  = 
		new Dimension((BUTTON_WIDTH /3)*2 ,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION =
		new Point(0 + INSET_LEFT, BUTTON_HEIGHT + INSET_TOP + SPACING_BUTTONS);
	
	public static final Dimension SIMULATION_PANE_BUTTON_TRANSFORM_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 
				0 + INSET_TOP + SPACING_BUTTONS + BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_SPINNER_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_SPINNER_SIZE =
		new Dimension(BUTTON_WIDTH / 3, BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_SLIDER_LOCATION =
		new Point(INSET_LEFT + (BUTTON_WIDTH + SPACING_BUTTONS) * 2, 
				0 + INSET_TOP + BUTTON_HEIGHT + SPACING_BUTTONS );
	
	public static final Dimension SIMULATION_PANE_SLIDER_SIZE =
		new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 2 + SPACING_BUTTONS);
	
	public static final Point SIMULATION_PANE_COMBOBOX_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS,
				0 + INSET_TOP + (BUTTON_HEIGHT + SPACING_BUTTONS) * 2);
	
	public static final Dimension SIMULATION_PANE_COMBOBOX_SIZE = 
		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT - 3);

	public static final Border SIMULATION_PANE_BORDER = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"Simulieren");
	
	public static final Border SIMULATION_PANE_SPEED_SLIDER_BORDER = 
			BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Geschwindigkeit");
	
	public static final ImageIcon SIMULATION_START_ICON =  new ImageIcon("src/gui2/icons/simulationPlay.png");
	public static final ImageIcon SIMULATION_START_ICON_DISABLE =  new ImageIcon("src/gui2/icons/simulationPlayDisable.png");
	public static final ImageIcon SIMULATION_START_ICON_PRESSED =  new ImageIcon("src/gui2/icons/simulationPlayPressed.png");
	public static final ImageIcon SIMULATION_PAUSE_ICON =  new ImageIcon("src/gui2/icons/simulationPause.png");
	public static final ImageIcon SIMULATION_PAUSE_ICON_PRESSED =  new ImageIcon("src/gui2/icons/simulationPausePressed.png");
	
	
	/* ************************************* 
	 * Petrinet pane
	 * *************************************/
	
	public static final Border PETRINET_BORDER = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Petrinetz");
	
	public static final GridLayout PETRINET_PANE_LAYOUT = new GridLayout(1,1);
	
	public static final Color FONT_COLOR = Color.DARK_GRAY;
	
	public static final Color NODE_BORDER_COLOR = Color.DARK_GRAY;
	
	public static final int PLACE_WIDTH = 50;
	
	public static final int PLACE_HEIGHT = (int)(PLACE_WIDTH / 1.5d);
	
	public static final int TRANSITION_SIZE = PLACE_HEIGHT;
	
	/* ************************************* 
	 * petrinet file pane
	 * *************************************/
	
	public static final Border PETRINET_FILE_PANE_BORDER = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
				"Speichern/Laden - Petrinetze");
	
	public static final int SOUTH_PANEL_HEIHT = 35;
	
	public static final int FILE_PANE_ICON_BUTTON_SIZE = 30;
	public static final int FILE_PANE_ICON_SPACING_SIZE = 9;
	
	
	public static final ImageIcon NEW_PETRINET_ICON = new ImageIcon("src/gui2/icons/newPetrinet.png");
	public static final ImageIcon NEW_PETRINET_PRESSED_ICON = new ImageIcon("src/gui2/icons/newPetrinetPressed.png");
	public static final ImageIcon NEW_PETRINET_DISABLED_ICON = new ImageIcon("src/gui2/icons/newPetrinetDisabled.png");
	
	public static final ImageIcon LOAD_PETRINET_ICON = new ImageIcon("src/gui2/icons/loadPertinet.png");
	public static final ImageIcon LOAD_PETRINET_PRESSED_ICON = new ImageIcon("src/gui2/icons/loadPetrinetPressed.png");
	public static final ImageIcon LOAD_PETRINET_DISABLED_ICON = new ImageIcon("src/gui2/icons/loadPertinetDisabled.png");
	
	public static final ImageIcon SAVE_PETRINET_ICON = new ImageIcon("src/gui2/icons/savePertinet.png");
	public static final ImageIcon SAVE_PETRINET_PRESSED_ICON = new ImageIcon("src/gui2/icons/savePertinetPressed.png");
	public static final ImageIcon SAVE_PETRINET_DISABLED_ICON = new ImageIcon("src/gui2/icons/savePertinetDisabled.png");
	
	public static final ImageIcon SAVE_AS_PETRINET_ICON = new ImageIcon("src/gui2/icons/saveAsPertinet.png");
	public static final ImageIcon SAVE_AS_PETRINET_PRESSED_ICON = new ImageIcon("src/gui2/icons/saveAsPertinetPressed.png");
	public static final ImageIcon SAVE_AS_PETRINET_DISABLED_ICON = new ImageIcon("src/gui2/icons/saveAsPertinetDisabled.png");
	
	public static final ImageIcon DELETE_PETRINET = new ImageIcon("src/gui2/icons/deletePertinet.png");
	public static final ImageIcon DELETE_PETRINET_PRESSED_ICON = new ImageIcon("src/gui2/icons/deletePetrinetPressed.png");
	public static final ImageIcon DELETE_PETRINET_DISABLED_ICON = new ImageIcon("src/gui2/icons/deletePertinetDisabled.png");
	
	
	
	public static final int NEW_BUTTON_X = 0;
	public static final int NEW_BUTTON_Y = 0;
	
	public static final int LOAD_BUTTON_X = NEW_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int LOAD_BUTTON_Y = 0;
	
	public static final int SAVE_BUTTON_X = LOAD_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int SAVE_BUTTON_Y = 0;
	
	public static final int SAVE_AS_BUTTON_X = SAVE_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int SAVE_AS_BUTTON_Y = 0;
	
	public static final int DELETE_BUTTON_X = SAVE_AS_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int DELETE_BUTTON_Y = 0;
	
	
	/* ************************************* 
	 * rule pane
	 * *************************************/
	
	public static final GridLayout RULE_PANEL_LAYOUT = new GridLayout(1,3);
	
	public static final Border RULE_PANE_BORDER = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"Regel");
	
	public static final Border RULE_PANE_BORDER_L = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"L");
	
	public static final Border RULE_PANE_BORDER_K = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"K");
	
	public static final Border RULE_PANE_BORDER_R = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"R");
}
