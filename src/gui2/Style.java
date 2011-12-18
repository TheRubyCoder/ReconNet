package gui2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

//import com.sun.xml.internal.ws.api.pipe.ServerPipeAssemblerContext;

/** Utility class for constants of gui styling */
class Style {
	
	private Style() {}
	
	/* ************************************* 
	 * Main Window
	 * *************************************/
	
//	/** X main window */
//	public static final int TOTAL_X = 0;
	
//	/** Y main window */
//	public static final int TOTAL_Y = 0;

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
	/** Width of Panels at left of gui */
//	private static final int WIDTH_OF_LEFT_ELEMENTS = 200;

	/** Space between panes */
//	private static final int SPACING_PANES = 2;
	
	private static final int SPACING_BUTTONS = 5;
	
	/** Border of of window */
//	private static final int WINDOW_BORDER = 15;
	
	public static final int BUTTON_HEIGHT = 30;
	
	public static final int BUTTON_WIDTH = 200;
	
	private static final int INSET_TOP = 20;
	
	private static final int INSET_LEFT = 10;
	
//	public static final GridLayout FILE_PANE_LAYOUT = new GridLayout(2,1,0,SPACING_BUTTONS);
	public static final BorderLayout FILE_PANE_LAYOUT = new BorderLayout();
	
	
	/* ************************************* 
	 * Editor Pane
	 * *************************************/
	
//	/** X of editor pane */
//	public static final int EDITOR_PANE_X = 0;
//	
//	/** Y of editor pane */
//	public static final int EDITOR_PANE_Y = 0;
//	
//	/** Height of editor pane */
//	public static final int EDITOR_PANE_HEIGHT = HEIGHT_TOP_ELEMENTS;
//	
//	/** Width of the editor pane */
	public static final int EDITOR_PANE_WIDTH = INSET_LEFT + 120;
	
	/** Dimension of the Editor Pane*/
	public static final Dimension EDITOR_PANE_DIMENSION = new Dimension(INSET_LEFT + 120, HEIGHT_TOP_ELEMENTS);
//	BUTTON_WIDTH * 2 + 
//	SPACING_BUTTONS * 3;
	
//	public static final Point EDITOR_PANE_BUTTON_PICK_LOCATION = 
//		new Point( 0 + INSET_LEFT, 0 + INSET_TOP);
//	
//	public static final Dimension EDITOR_PANE_BUTTON_PICK_SIZE = 
//		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
//	
//	public static final Point EDITOR_PANE_BUTTON_CREATEPLACE_LOCATION = 
//		new Point( 0 + INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP);
//	
//	public static final Dimension EDITOR_PANE_BUTTON_CREATEPLACE_SIZE = 
//		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
//	
//	public static final Point EDITOR_PANE_BUTTON_CREATETRANSITION_LOCATION = 
//		new Point( 0 + INSET_LEFT, 0 + INSET_TOP + BUTTON_HEIGHT + SPACING_BUTTONS);
//	
//	public static final Dimension EDITOR_PANE_BUTTON_CREATEARC_SIZE = 
//		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
//	
//	public static final Point EDITOR_PANE_BUTTON_CREATEARC_LOCATION = 
//		new Point( 0 + INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP + BUTTON_HEIGHT + SPACING_BUTTONS);
//	
//	public static final Dimension EDITOR_PANE_BUTTON_CREATETRANSITION_SIZE = 
//		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
	
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
	
//	public static final int ATTRIBUTE_PANE_X = 
//		EDITOR_PANE_WIDTH + 
//		SPACING_PANES;
//	
//	public static final int ATTRIBUTE_PANE_Y = 0;
//	
//	public static final int ATTRIBUTE_PANE_WIDTH = 225;
//	
//	public static final int ATTRIBUTE_PANE_HEIGHT = HEIGHT_TOP_ELEMENTS;
	
	public static final Dimension ATTRIBUTE_PANE_DIMENSION = new Dimension(225,HEIGHT_TOP_ELEMENTS);
	
	public static final Border ATTRIBUTE_PANE_BORDER = BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Knoten-Attribute");
	
	
	/* ************************************* 
	 * Simulation pane
	 * *************************************/
//	
//	public static final int SIMULATION_PANEL_X = ATTRIBUTE_PANE_X +
//		ATTRIBUTE_PANE_WIDTH +
//		SPACING_PANES;
//	
//	public static final int SIMULATION_PANEL_Y = 0;
//	
//	/** Height of simulation panel */
//	public static final int SIMULATION_PANE_HEIGHT = HEIGHT_TOP_ELEMENTS;
//	
//	public static final int SIMULATION_PANE_WIDTH = INSET_LEFT +
//		BUTTON_WIDTH * 3 +
//		SPACING_BUTTONS * 3;
	
	public static final Dimension SIMULATION_PANE_DIMENSION = new Dimension(INSET_LEFT +
																			BUTTON_WIDTH * 3 +
																			SPACING_BUTTONS * 3, HEIGHT_TOP_ELEMENTS);
	
	public static final Point SIMULATION_PANE_BUTTON_ONESTEP_LOCATION =
		new Point(0 + INSET_LEFT, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_ONESTEP_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_KSTEPS_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + BUTTON_WIDTH/2 + SPACING_BUTTONS, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_KSTEPS_SIZE  = 
		new Dimension(BUTTON_WIDTH / 2,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION =
		new Point(0 + INSET_LEFT, BUTTON_HEIGHT + INSET_TOP + SPACING_BUTTONS);
	
	public static final Dimension SIMULATION_PANE_BUTTON_TRANSFORM_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 
				0 + INSET_TOP + SPACING_BUTTONS + BUTTON_HEIGHT);
	
//	public static final Dimension SIMULATION_PANE_BUTTON_STARTSIMULATION_SIZE = 
//		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_SPINNER_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_SPINNER_SIZE =
		new Dimension(BUTTON_WIDTH / 2, BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_SLIDER_LOCATION =
		new Point(INSET_LEFT + (BUTTON_WIDTH + SPACING_BUTTONS) * 2, 
				0 + INSET_TOP );
	
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
	
	
	/* ************************************* 
	 * Petrinet pane
	 * *************************************/
	
//	public static final int PETRINET_X = WIDTH_OF_LEFT_ELEMENTS;
//	
//	public static final int PETRINET_Y = HEIGHT_TOP_ELEMENTS;
//	
//	public static final int PETRINET_WIDTH = TOTAL_WIDTH -
//			WIDTH_OF_LEFT_ELEMENTS -
//			WINDOW_BORDER;
//	
//	public static final int PETRINET_HEIGHT = 500;
//	
	public static final Border PETRINET_BORDER = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Petrinetz");
	
	public static final GridLayout PETRINET_PANE_LAYOUT = new GridLayout(1,1);
	
	/* ************************************* 
	 * petrinet file pane
	 * *************************************/
	
//	public static final int PETRINET_FILE_PANE_X = 0;
//	
//	public static final int PETRINET_FILE_PANE_Y = HEIGHT_TOP_ELEMENTS;
//	
//	public static final int PETRINET_FILE_PANE_WIDTH = WIDTH_OF_LEFT_ELEMENTS;
//	
//	public static final int PETRINET_FILE_PANE_HEIGHT = 400;
//	
	public static final Border PETRINET_FILE_PANE_BORDER = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
				"Speichern/Laden - Petrinetze");
	
	public static final int SOUTH_PANEL_HEIHT = 35;
	
	public static final int FILE_PANE_ICON_BUTTON_SIZE = 30;
	public static final int FILE_PANE_ICON_SPACING_SIZE = 15;
	
	
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
	
	public static final int NEW_BUTTON_X = 0;
	public static final int NEW_BUTTON_Y = 0;
	
	public static final int LOAD_BUTTON_X = NEW_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int LOAD_BUTTON_Y = 0;
	
	public static final int SAVE_BUTTON_X = LOAD_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int SAVE_BUTTON_Y = 0;
	
	public static final int SAVE_AS_BUTTON_X = SAVE_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
	public static final int SAVE_AS_BUTTON_Y = 0;
	
	
	/* ************************************* 
	 * rule file pane
	 * *************************************/
	
//	public static final int RULE_FILE_PANE_X = 0;
	
//	public static final int RULE_FILE_PANE_Y = PETRINET_FILE_PANE_Y +
//		PETRINET_FILE_PANE_HEIGHT +
//		SPACING_PANES;
	
//	public static final int RULE_FILE_PANE_WIDTH = WIDTH_OF_LEFT_ELEMENTS;
	
//	public static final int RULE_FILE_PANE_HEIGHT = 300;
	
//	public static final Border RULE_FILE_PANE_BORDER = 
//		BorderFactory.createTitledBorder(
//				BorderFactory.createEtchedBorder(), 
//				"Speichern/Laden");
	
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
