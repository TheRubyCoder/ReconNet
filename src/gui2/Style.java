package gui2;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

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
	private static final int HEIGHT_TOP_ELEMENTS = 150;
	
	/** Width of Panels at left of gui */
	private static final int WIDTH_OF_LEFT_ELEMENTS = 200;

	/** Space between panes */
	private static final int SPACING = 2;
	
	/** Border of os window */
	private static final int WINDOW_BORDER = 15;
	
	public static final int BUTTON_HEIGHT = 30;
	
	public static final int BUTTON_WIDHT = 130;
	
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
			SPACING;
	
	public static final int SIMULATION_PANEL_Y = 0;
	
	/** Height of simulation panel */
	public static final int SIMULATION_PANE_HEIGHT = HEIGHT_TOP_ELEMENTS;
	
	public static final int SIMULATION_PANE_WIDTH = TOTAL_WIDTH / 2;
	
	public static final Point SIMULATION_PANE_BUTTON_ONESTEP_LOCATION =
		new Point(0 + INSET_LEFT, 0 + INSET_TOP);
	
	public static final Dimension SIMULATION_PANE_BUTTON_ONESTEP_SIZE = 
		new Dimension(BUTTON_WIDHT,BUTTON_HEIGHT);
	
	public static final Point SIMULATION_PANE_BUTTON_KSTEPS_LOCATION =
		new Point(0 + INSET_LEFT, BUTTON_HEIGHT + INSET_TOP + (int)(INSET_TOP / 2));
	
	public static final Dimension SIMULATION_PANE_BUTTON_KSTEPS_SIZE = 
		new Dimension(BUTTON_WIDHT,BUTTON_HEIGHT);

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