package gui2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sun.org.apache.xml.internal.security.signature.Manifest;

import petrinet.Arc;
import petrinet.INode;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import engine.EngineComponent;
import engine.EngineMockup;
import engine.ihandler.IPetrinetManipulation;
import exceptions.EngineException;
import gui2.EditorPane.EditorMode;

import static gui2.Style.*;

/** Pane for displaying petrinets */
class PetrinetPane {
	
	/** Internal JPanel for gui-layouting the petrinet */
	private JPanel petrinetPanel;
	
	/** Internal Panel for scrolling and zooming. This is added to petrinetPanel */
	private GraphZoomScrollPane scrollPanel;
	
	/** For zooming */
	private ScalingControl scaler;
	
	/** Internal JPanel for actually viewing and controlling the petrinet */
	private VisualizationViewer<INode, Arc> visualizationViewer;
	
	/** ID of currently displayed petrinet */
	int currentPetrinetId = -1;
	
	/** singleton instance of this pane */
	private static PetrinetPane instance;
	
	/** Returns the singleton instance for this pane */
	public static PetrinetPane getInstance(){
		return instance;
	}
	
	/** mouse click listener for the drawing panel */
	private static class PetrinetMouseListener extends PickingGraphMousePlugin<INode, Arc> implements MouseWheelListener{
		
		private PetrinetPane petrinetPane;
		
		PetrinetMouseListener(PetrinetPane petrinetPane){
			this.petrinetPane = petrinetPane;
		}
		
		/** Indicates whether a new drag has begun or not */
		private boolean pressedBefore = false;
		/** X-coordinate of begin of drag */
		private int pressedX = 0;
		/** Y-coordinate of begin of drag */
		private int pressedY = 0;
		
		/** Zoom petrinet on mouse wheel */
		@Override public void mouseWheelMoved(MouseWheelEvent e){
			Point point = new Point(e.getX(),e.getY());
			System.out.println(point);
			if(e.getWheelRotation() < 0) {
				petrinetPane.scaler.scale(petrinetPane.visualizationViewer, 
						1.1f,
						point);
			}else{
				petrinetPane.scaler.scale(petrinetPane.visualizationViewer, 
						0.9f, 
						point);
			}
		}
		
		@Override public void mouseClicked(MouseEvent e) {
			super.mousePressed(e); // mousePressedEvent in class PickingGraphMousePlugin selects nodes
			EditorMode mode = EditorPane.getInstance().getCurrentMode();
			int x = e.getX();
			int y = e.getY();

			if( mode == EditorMode.PICK){
				if(edge != null){
					AttributePane.getInstance().displayEdge(edge);
					edge = null;
				}else if(vertex != null){
					AttributePane.getInstance().displayNode(vertex);
					vertex = null;
				}
			}
		}
//		@Override public void mouseEntered(MouseEvent e) {}
//		@Override public void mouseExited(MouseEvent e) {}
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
				if(e.getX() != pressedX || e.getY() != pressedY){
					Point oldPoint = new Point(pressedX,pressedY);
					Point newPoint = new Point(e.getX(),e.getY());
					petrinetPane.scaler.scale(petrinetPane.visualizationViewer, 
							0.5f, 
							newPoint);
					petrinetPane.scaler.scale(petrinetPane.visualizationViewer, 
							2f, 
							oldPoint);
					
//					Rectangle newRect = petrinetPane.scrollPanel.getVisibleRect();
//					newRect.add(
//							new Point(
//									e.getX() - pressedX,
//									e.getY() - pressedY)
//							);
//					petrinetPane.scrollPanel.scrollRectToVisible(newRect);
					
//					System.out.println("TODO: Translate petrinet by [" + 
//							(e.getX() - pressedX) + "," + 
//							(e.getY() - pressedY) + "]");
				}
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
		petrinetPanel.setLayout(PETRINET_PANE_LAYOUT);
		petrinetPanel.setBorder(PETRINET_BORDER);
		
		visualizationViewer = dirtyTest(); //This is for ad hoc testing the engine
		scaler = new CrossoverScalingControl();
		visualizationViewer.setBackground(Color.WHITE);
		scrollPanel = new GraphZoomScrollPane(visualizationViewer);
		
		petrinetPanel.add(scrollPanel);
		
		visualizationViewer.addMouseListener(new PetrinetMouseListener(this));
		visualizationViewer.addMouseWheelListener(new PetrinetMouseListener(this));
	}
	
	private JPanel getPetrinetPanel(){
		return petrinetPanel;
	}
	
	/** Returns the graphLayout for the currently displayed petrinet 
	 * @return <tt>null</tt> if Error was thrown*/
	private AbstractLayout<INode, Arc> getCurrentLayout() {
		try {
			return MainWindow.getPetrinetManipulation().getJungLayout(currentPetrinetId);
		} catch (EngineException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** For quick testing if displaying works 
	 * don't try this at home*/
	private VisualizationViewer<INode, Arc> dirtyTest(){
		PickingGraphMousePlugin<INode, Arc> mouse = new PickingGraphMousePlugin<INode, Arc>();
		currentPetrinetId = MainWindow.getPetrinetManipulation()
				.createPetrinet();

		VisualizationViewer<INode, Arc> visuServer = new VisualizationViewer<INode, Arc>(
				getCurrentLayout());

		return visuServer;
		
//		engine.createPetrinet();
		
		
		
		
//		IPetrinetManipulation engine = PetrinetManipulation.getInstance();
//		
//		int petrinetId = engine.createPetrinet();
//		BasicVisualizationServer<INode, Arc> visServer = null;
//		try {
//			engine.createPlace(petrinetId, new Point(0,0));
//			AbstractLayout<INode, Arc> layout = engine.getJungLayout(petrinetId);
//			visServer = new BasicVisualizationServer<INode, Arc>(layout);
//		} catch (EngineException e) {
//			e.printStackTrace();
//		}
//		
//		return visServer;
//		
	}
	
	/** Adds the petrinet pane to the given JPanel (frame) */
	public void addTo(JPanel frame){
		frame.add(getPetrinetPanel());
	}
}
