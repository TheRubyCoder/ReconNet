package gui2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import gui2.EditorPane.EditorMode;

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
		getInstance().getDrawingPanel().addMouseListener(new PetrinetMouseClickListener());
//		getInstance().getDrawingPanel().addMouseMotionListener(new PetrinetMouseMotionListener());
		
		return getInstance();
	}
	
	/** mouse click listener for the drawing panel */
	private static class PetrinetMouseClickListener implements MouseListener{
		/** Indicates whether a new drag has begun or not */
		private boolean pressedBefore = false;
		/** X-coordinate of begin of drag */
		private int pressedX = 0;
		/** Y-coordinate of begin of drag */
		private int pressedY = 0;
		@Override public void mouseClicked(MouseEvent e) {
			System.out.println("mouse clicked on petrinet at [" + e.getX() + 
					"," + e.getY() +
					"] in mode: " + EditorPane.getInstance().getCurrentMode());
		}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}
		@Override public void mousePressed(MouseEvent e) {
			if(EditorPane.getInstance().getCurrentMode() == EditorMode.PICK && !pressedBefore){
				pressedX = e.getX();
				pressedY = e.getY();
				pressedBefore = true;
			}
		}
		@Override public void mouseReleased(MouseEvent e) {
			if(EditorPane.getInstance().getCurrentMode() == EditorMode.PICK && pressedBefore){
				pressedBefore = false;
				System.out.println("TODO: Translate petrinet by [" + 
						(e.getX() - pressedX) + "," + 
						(e.getY() - pressedY) + "]");
			}
		}
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
