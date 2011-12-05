package gui2;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/** Utility class for constants of gui styling */
class Style {
	
	private Style() {}
	
	/** Height of Panels with buttons at top of gui */
	public static final int HEIGHT_TOP_ELEMENTS = 100;
	
	/** Total width of main window */
	public static final int TOTAL_WIDTH = 1000;

	/** Total height of main window */
	public static final int TOTAL_HEIGHT = 800;
	
	/** Width of the editor pane */
	public static final int WIDTH_EDITOR_PANE = TOTAL_WIDTH / 3;
	
	public static final int WIDTH_SIMULATION_PANE = TOTAL_WIDTH / 3;
	
	public static final int WIDTH_OF_LEFT_ELEMENTS = 200;
	
	public static final int HEIGHT_PETRINET = 400;
	
	public static final int OFFSET_PER_BORDER = 5;
	
	
	public static final Border BORDER_EDITOR_PANE = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
				"Editieren");
	
	public static final Border BORDER_SIMULATION_PANE = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), 
		"Simulieren");
	
	public static final Border BORDER_PETRINET = 
		BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Petrinetz");
	
	public static final GridLayout EDITOR_PANE_LAYOUT;
	
	public static final GridLayout PETRINET_PANE_LAYOUT = new GridLayout(1,1);
	
	
	static {
		EDITOR_PANE_LAYOUT = new GridLayout(2,2);
		EDITOR_PANE_LAYOUT.setHgap(10);
		EDITOR_PANE_LAYOUT.setVgap(10);
	}
	
}
