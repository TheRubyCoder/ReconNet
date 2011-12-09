package gui2;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.sun.xml.internal.ws.api.pipe.ServerPipeAssemblerContext;

/** Utility class for constants of gui styling */
class Style {
	
	private Style() {}
	
	/* ************************************* 
	 * Main Window
	 * *************************************/
	
	/** X main window */
	public static final int TOTAL_X = 0;
	
	/** Y main window */
	public static final int TOTAL_Y = 0;

	/** Total width of main window */
	public static final int TOTAL_WIDTH = 1000;
	
	/** Total height of main window */
	public static final int TOTAL_HEIGHT = 800;

	
	/* ************************************* 
	 * Commons
	 * *************************************/
	
	/** Height of Panels with buttons at top of gui */
	private static final int HEIGHT_TOP_ELEMENTS = 125;	
	/** Width of Panels at left of gui */
	private static final int WIDTH_OF_LEFT_ELEMENTS = 200;

	/** Space between panes */
	private static final int SPACING_PANES = 2;
	
	private static final int SPACING_BUTTONS = 5;
	
	/** Border of os window */
	private static final int WINDOW_BORDER = 15;
	
	public static final int BUTTON_HEIGHT = 30;
	
	public static final int BUTTON_WIDTH = 150;
	
	private static final int INSET_TOP = 20;
	
	private static final int INSET_LEFT = 10;
	
	
	/* ************************************* 
	 * Editor Pane
	 * *************************************/
	
	/** X of editor pane */
	public static final int EDITOR_PANE_X = 0;
	
	/** Y of editor pane */
	public static final int EDITOR_PANE_Y = 0;
	
	/** Height of editor pane */
	public static final int EDITOR_PANE_HEIGHT = HEIGHT_TOP_ELEMENTS;
	
	/** Width of the editor pane */
	public static final int EDITOR_PANE_WIDTH = TOTAL_WIDTH / 3;
	
	public static final Point EDITOR_PANE_BUTTON_PICK_LOCATION = 
		new Point( 0 + INSET_LEFT, 0 + INSET_TOP);
	
	public static final Dimension EDITOR_PANE_BUTTON_PICK_SIZE = 
		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
	
	public static final Point EDITOR_PANE_BUTTON_CREATEPLACE_LOCATION = 
		new Point( 0 + INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP);
	
	public static final Dimension EDITOR_PANE_BUTTON_CREATEPLACE_SIZE = 
		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
	
	public static final Point EDITOR_PANE_BUTTON_CREATETRANSITION_LOCATION = 
		new Point( 0 + INSET_LEFT, 0 + INSET_TOP + BUTTON_HEIGHT + SPACING_BUTTONS);
	
	public static final Dimension EDITOR_PANE_BUTTON_CREATEARC_SIZE = 
		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
	
	public static final Point EDITOR_PANE_BUTTON_CREATEARC_LOCATION = 
		new Point( 0 + INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP + BUTTON_HEIGHT + SPACING_BUTTONS);
	
	public static final Dimension EDITOR_PANE_BUTTON_CREATETRANSITION_SIZE = 
		new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT);
	
	/** Border of editor pane */
	public static final Border EDITOR_PANE_BORDER = 
			BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					"Editieren");
	
	/** Layout of editor pane */
	public static final GridLayout EDITOR_PANE_LAYOUT = new GridLayout(2,2,10,10);

	
	
	/* ************************************* 
	 * Simulation pane
	 * *************************************/
	
	public static final int SIMULATION_PANEL_X = EDITOR_PANE_X + 
			EDITOR_PANE_WIDTH +
			SPACING_PANES;
	
	public static final int SIMULATION_PANEL_Y = 0;
	
	/** Height of simulation panel */
	public static final int SIMULATION_PANE_HEIGHT = HEIGHT_TOP_ELEMENTS;
	
	public static final int SIMULATION_PANE_WIDTH = TOTAL_WIDTH / 2;
	
	public static final Point SIMULATION_PANE_BUTTON_ONESTEP_LOCATION =
		new Point(0 + INSET_LEFT, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_ONESTEP_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_KSTEPS_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_KSTEPS_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION =
		new Point(0 + INSET_LEFT, BUTTON_HEIGHT + INSET_TOP + SPACING_BUTTONS);
	
	public static final Dimension SIMULATION_PANE_BUTTON_TRANSFORM_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION =
		new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 
				0 + INSET_TOP + SPACING_BUTTONS + BUTTON_HEIGHT);
	
	public static final Dimension SIMULATION_PANE_BUTTON_STARTSIMULATION_SIZE = 
		new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_SPINNER_LOCATION =
		new Point(INSET_LEFT + (BUTTON_WIDTH + SPACING_BUTTONS) * 2, 
				0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_SPINNER_SIZE =
		new Dimension(BUTTON_WIDTH / 2, BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_SLIDER_LOCATION =
		new Point(INSET_LEFT + (BUTTON_WIDTH + SPACING_BUTTONS) * 2, 
				0 + INSET_TOP + BUTTON_HEIGHT + SPACING_BUTTONS);
	
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
	
	public static final int PETRINET_X = WIDTH_OF_LEFT_ELEMENTS;
	
	public static final int PETRINET_Y = HEIGHT_TOP_ELEMENTS;
	
	public static final int PETRINET_WIDTH = TOTAL_WIDTH -
			WIDTH_OF_LEFT_ELEMENTS -
			WINDOW_BORDER;
	
	public static final int PETRINET_HEIGHT = 400;
	
	public static final Border PETRINET_BORDER = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Petrinetz");
	
	public static final GridLayout PETRINET_PANE_LAYOUT = new GridLayout(1,1);
	
	
	
}
