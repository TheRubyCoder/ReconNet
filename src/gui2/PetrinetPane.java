package gui2;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static gui2.Style.*;

/** Pane for displaying petrinets */
class PetrinetPane {
	
	/** Internal JPanel for gui-layouting the petrinet */
	private JPanel petrinetPanel;
	
	/** Internal JPanel for actually drawing the petrinet */
	private JPanel drawingPanel;
	
	/** singleton instance of this pane */
	private static PetrinetPane instance;
	
	/** Returns the singleton instance for this pane */
	public static PetrinetPane getInstance(){
		return instance;
	}
	
	public static PetrinetPane initiatePetrinetPane(){
		getInstance().getPetrinetPanel().setLayout(PETRINET_PANE_LAYOUT);
		
		getInstance().getPetrinetPanel().add(getInstance().getDrawingPanel());
		
		getInstance().getPetrinetPanel().setBorder(BORDER_PETRINET);
		getInstance().getPetrinetPanel().setBounds(WIDTH_OF_LEFT_ELEMENTS, 
				HEIGHT_TOP_ELEMENTS, 
				TOTAL_WIDTH - WIDTH_OF_LEFT_ELEMENTS - 4 * OFFSET_PER_BORDER, 
				HEIGHT_PETRINET);

		getInstance().getDrawingPanel().setBackground(Color.WHITE);
		
		return getInstance();
	}
	
	/* Static constructor that initiates the singleton */
	static {
		instance = new PetrinetPane();
	}
	
	/** Private default constructor */
	private PetrinetPane(){
		petrinetPanel = new JPanel();
		drawingPanel = new JPanel();
		dirtyTest();
//		data.MorphismData.getPetrinetIsomorphismTransitionsTo();
	}
	
	private JPanel getPetrinetPanel(){
		return petrinetPanel;
	}
	
	private JPanel getDrawingPanel(){
		return drawingPanel;
	}
	
	
	private void dirtyTest(){
		
	}
	
	public void addTo(JFrame frame){
		frame.add(getPetrinetPanel());
	}
}
