package gui2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import petrinet.Arc;
import petrinet.INode;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import engine.handler.PetrinetManipulation;
import engine.ihandler.IPetrinetManipulation;
import exceptions.EngineException;

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
		
		getInstance().getPetrinetPanel().setBorder(PETRINET_BORDER);
		getInstance().getPetrinetPanel().setBounds(PETRINET_X, 
				PETRINET_Y, 
				PETRINET_WIDTH, 
				PETRINET_HEIGHT);

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
//		drawingPanel = dirtyTest(); //This is for ad hoc testing the engine
	}
	
	private JPanel getPetrinetPanel(){
		return petrinetPanel;
	}
	
	private JPanel getDrawingPanel(){
		return drawingPanel;
	}
	
	
	private JPanel dirtyTest(){
		
		IPetrinetManipulation engine = PetrinetManipulation.getInstance();
		
		int petrinetId = engine.createPetrinet();
		BasicVisualizationServer<INode, Arc> visServer = null;
		try {
			engine.createPlace(petrinetId, new Point(0,0));
			AbstractLayout<INode, Arc> layout = engine.getJungLayout(petrinetId);
			visServer = new BasicVisualizationServer<INode, Arc>(layout);
		} catch (EngineException e) {
			e.printStackTrace();
		}
		
		return visServer;
		
	}
	
	public void addTo(JPanel frame){
		frame.add(getPetrinetPanel());
	}
}
