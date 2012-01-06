package gui2;

import static gui2.Style.*;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import exceptions.EngineException;
import gui2.EditorPane.EditorMode;

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
	public static PetrinetPane getInstance() {
		return instance;
	}

	/** mouse click listener for the drawing panel */
	private static class PetrinetMouseListener extends
			PickingGraphMousePlugin<INode, Arc> implements MouseWheelListener {

		private static enum DragMode {
			SCROLL, MOVENODE, ARC, NONE
		}

		private PetrinetPane petrinetPane;

		private DragMode dragMode = DragMode.NONE;

		PetrinetMouseListener(PetrinetPane petrinetPane) {
			this.petrinetPane = petrinetPane;
		}

		/** X-coordinate of begin of drag */
		private int pressedX = 0;
		/** Y-coordinate of begin of drag */
		private int pressedY = 0;

		/**
		 * For defining maximim zoom. Places are not displayed correctly if the
		 * user zooms too deep
		 */
		float currentZoom = 1;

		/**
		 * ID of node that was clicked at beginning of drag. Needed for drawing
		 * arcs
		 */
		private INode nodeFromDrag = null;

		/** Zoom petrinet on mouse wheel */
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			Point point = new Point(e.getX(), e.getY());
			if (e.getWheelRotation() < 0) {
				if (currentZoom * 1.1f <= 1) {
					petrinetPane.scaler.scale(petrinetPane.visualizationViewer,
							1.1f, point);
					currentZoom *= 1.1f;
				}
			} else {
				petrinetPane.scaler.scale(petrinetPane.visualizationViewer,
						0.9f, point);
				currentZoom *= 0.9f;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mousePressed(e); // mousePressedEvent in class
									// PickingGraphMousePlugin selects nodes
			EditorMode mode = EditorPane.getInstance().getCurrentMode();

			
			//left-click PICK : display clicked node
			//right-click: display pop-up-menu
			//left-click PLACE: create place at position
			//left-click TRANSITION: etc...
			if (mode == EditorMode.PICK) {
				if (edge != null) {
					AttributePane.getInstance().displayEdge(edge);
					if (e.isMetaDown()) {
						PetrinetPopUpMenu.fromArc(edge).show(
								PetrinetPane.getInstance().visualizationViewer,
								e.getX(), e.getY());
					}

				} else if (vertex != null) {
					AttributePane.getInstance().displayNode(vertex);
					if (e.isMetaDown()) {
						PetrinetPopUpMenu.fromNode(vertex).show(
								PetrinetPane.getInstance().visualizationViewer,
								e.getX(), e.getY());
					}
				}
			} else
				try {
					if (mode == EditorMode.PLACE) {
						MainWindow.getPetrinetManipulation().createPlace(
								petrinetPane.currentPetrinetId,
								new Point(e.getX(), e.getY()));
					} else if (mode == EditorMode.TRANSITION) {
						MainWindow.getPetrinetManipulation().createTransition(
								petrinetPane.currentPetrinetId,
								new Point(e.getX(), e.getY()));
					}
					petrinetPane.petrinetPanel.repaint();
				} catch (EngineException e1) {
					e1.printStackTrace();
				}
		}

		/** Checks if something is clicked without changing selection */
		private boolean isAnythingClicked(MouseEvent e) {
			Arc beforeEdge = edge;
			INode beforeNode = vertex;
			edge = null;
			vertex = null;
			super.mousePressed(e);

			boolean result = edge != null || vertex != null;
			edge = beforeEdge;
			vertex = beforeNode;

			return result;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			if (dragMode == DragMode.NONE) {
				pressedX = e.getX();
				pressedY = e.getY();
				EditorMode editorMode = EditorPane.getInstance()
						.getCurrentMode();
				// dragging in pick mode
				if (editorMode == EditorMode.PICK) {
					// find out: scrolling or moving? -> is something clicked?
					if (vertex != null) {
						// something is clicked -> MOVENODE
						dragMode = DragMode.MOVENODE;
						nodeFromDrag = vertex;
					} else {
						// nothing is clicked -> SCROLL
						dragMode = DragMode.SCROLL;
					}
					// dragging in arc mode
				} else if (editorMode == EditorMode.ARC) {
					// find out what was clicked on
					super.mousePressed(e);
					if (vertex != null) {
						nodeFromDrag = vertex;
						vertex = null;
						dragMode = DragMode.ARC;
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Point oldPoint = new Point(pressedX, pressedY);
			Point newPoint = new Point(e.getX(), e.getY());

			if (!oldPoint.equals(newPoint)) {
				if (dragMode == DragMode.SCROLL) {
					/*
					 * Scrolling is realized by zooming out of old position and
					 * zooming in to new position
					 */
					petrinetPane.scaler.scale(petrinetPane.visualizationViewer,
							0.5f, newPoint);
					petrinetPane.scaler.scale(petrinetPane.visualizationViewer,
							2f, oldPoint);
				} else if (dragMode == DragMode.MOVENODE) {
					try {
						MainWindow.getPetrinetManipulation().moveNode(
								PetrinetPane.getInstance().currentPetrinetId,
								nodeFromDrag,
								new Point((int) (newPoint.getX() - oldPoint
										.getX()),
										(int) (newPoint.getY() - oldPoint
												.getY())));
						PetrinetPane.getInstance().repaint();
					} catch (EngineException e1) {
						PopUp.popError("Dahin können sie nicht verschieben");
						e1.printStackTrace();
					}
				} else if (dragMode == DragMode.ARC) {
					// find out what was released on
					super.mousePressed(e);
					if (vertex != null) {
						try {
							MainWindow.getPetrinetManipulation().createArc(
									petrinetPane.currentPetrinetId,
									nodeFromDrag, vertex);
						} catch (EngineException e1) {
							e1.printStackTrace();
						}

					}
				}
			}
			dragMode = DragMode.NONE;
			nodeFromDrag = null;
		}
	} // end of mouse listener

	/**
	 * Pop up menu that appears when a node or arc is right-clicked. It is used
	 * for deleting
	 */
	private static class PetrinetPopUpMenu extends JPopupMenu {

		/**
		 * Listener for clicking on menu items<br>
		 * Each menu item has its own listener
		 */
		/*
		 * There are 3 types of listeners as there are 3 types of pop up menus:
		 * Node(Place/Transition) and Arc
		 */
		private static class DeleteListener implements ActionListener {

			private DeleteListener() {
			}

			private Transition transition;

			private Place place;

			private Arc arc;

			private int pId;

			private DeleteListener(Transition transition, Place place, Arc arc,
					int pId) {
				this.transition = transition;
				this.place = place;
				this.arc = arc;
				this.pId = pId;
			}

			static DeleteListener fromPlace(Place place, int pId) {
				return new DeleteListener(null, place, null, pId);
			}

			static DeleteListener fromArc(Arc arc, int pId) {
				return new DeleteListener(null, null, arc, pId);
			}

			static DeleteListener fromTransition(Transition transition, int pId) {
				return new DeleteListener(transition, null, null, pId);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (place != null) {
						MainWindow.getPetrinetManipulation().deletePlace(pId,
								place);
					} else if (transition != null) {
						MainWindow.getPetrinetManipulation().deleteTransition(
								pId, transition);
					} else {
						MainWindow.getPetrinetManipulation()
								.deleteArc(pId, arc);
					}
					PetrinetPane.getInstance().repaint();
				} catch (EngineException e1) {
					PopUp.popUnderConstruction(e.getActionCommand());
					e1.printStackTrace();
				}
			}
		}

		/** Listener for clicks on color fields in context menus of places */
		private static class ChangeColorListener implements ActionListener {

			private Color color;

			private INode place;

			private ChangeColorListener(Color color, INode place) {
				this.color = color;
				this.place = place;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(color);
				MainWindow.getPetrinetManipulation().setPlaceColor(
						PetrinetPane.getInstance().currentPetrinetId, place,
						color);
				PetrinetPane.getInstance().repaint();
			}

		}

		private PetrinetPopUpMenu() {
		}

		/** Adds a new item to the menu for changing the color of a node */
		static private void addColorToPopUpMenu(PetrinetPopUpMenu menu,
				String description, Color color, INode node) {
			JMenuItem item = new JMenuItem(description);
			item.setBackground(color);
			item.addActionListener(new ChangeColorListener(color, node));
			menu.add(item);
		}

		static PetrinetPopUpMenu fromNode(INode node) {
			PetrinetPopUpMenu result = new PetrinetPopUpMenu();
			int pId = PetrinetPane.getInstance().currentPetrinetId;
			try {
				if (MainWindow.getPetrinetManipulation().getNodeType(node) == NodeTypeEnum.Place) {
					PlaceAttribute placeAttribute = MainWindow
							.getPetrinetManipulation().getPlaceAttribute(pId,
									node);

					// Delete
					JMenuItem delete = new JMenuItem("Stelle "
							+ placeAttribute.getPname() + " [" + node.getId()
							+ "] " + "löschen");
					delete.addActionListener(DeleteListener.fromPlace(
							(Place) node, pId));
					result.add(delete);

					// Color blue
					addColorToPopUpMenu(result, "Blau", Color.BLUE, node);

					// Color light blue
					addColorToPopUpMenu(result, "Hellblau", new Color(200, 200,
							250), node);

					// Color red
					addColorToPopUpMenu(result, "Rot", Color.RED, node);

					// Light red
					addColorToPopUpMenu(result, "HellRot", new Color(250, 200,
							200), node);

				} else {
					TransitionAttribute transitionAttribute = MainWindow
							.getPetrinetManipulation()
							.getTransitionAttribute(
									PetrinetPane.getInstance().currentPetrinetId,
									node);
					JMenuItem jMenuItem = new JMenuItem("Transition "
							+ transitionAttribute.getTname() + " ["
							+ node.getId() + "] " + "löschen");
					jMenuItem.addActionListener(DeleteListener.fromTransition(
							(Transition) node, pId));
					result.add(jMenuItem);
				}
			} catch (EngineException e) {
				PopUp.popError(e);
				e.printStackTrace();
			}
			return result;
		}

		static PetrinetPopUpMenu fromArc(Arc arc) {
			PetrinetPopUpMenu result = new PetrinetPopUpMenu();
			JMenuItem jMenuItem = new JMenuItem("Pfeil [" + arc.getId()
					+ "] löschen");
			jMenuItem.addActionListener(DeleteListener.fromArc(arc,
					PetrinetPane.getInstance().currentPetrinetId));
			result.add(jMenuItem);
			return result;
		}
	}

	/**
	 * Custom renderer that is used from jung to make transitions cornered and
	 * places circular
	 */
	private static class PetrinetRenderer implements Vertex<INode, Arc> {

		@Override
		public void paintVertex(RenderContext<INode, Arc> renderContext,
				Layout<INode, Arc> layout, INode node) {
			GraphicsDecorator decorator = renderContext.getGraphicsContext();
			Point2D center = layout.transform(node);
			try {
				if (MainWindow.getPetrinetManipulation().getNodeType(node) == NodeTypeEnum.Place) {
					PlaceAttribute placeAttribute = MainWindow
							.getPetrinetManipulation()
							.getPlaceAttribute(
									PetrinetPane.getInstance().currentPetrinetId,
									node);
					int width = PLACE_WIDTH;
					int height = PLACE_HEIGHT;
					int x = (int) (center.getX() - width / 2);
					int y = (int) (center.getY() - height / 2);
					Color lightBlue = new Color(200, 200, 250);
					// decorator.setPaint(lightBlue);
					decorator.setPaint(placeAttribute.getColor());
					// TODO let user set color
					decorator.fillOval(x, y, width, height);

					// draw frame
					decorator.setPaint(NODE_BORDER_COLOR);
					decorator.drawOval(x, y, width, height);

					// write name
					decorator.setPaint(FONT_COLOR);
					decorator.drawString(placeAttribute.getPname(), x + width,
							y + height);

					// display marking
					int marking = placeAttribute.getMarking();
					if (marking == 0) {
						// view nothing (empty place)
					} else if (marking == 1) {
						// view black dot within place
						decorator.setPaint(Color.BLACK);
						decorator.fillOval(x + width / 4, y + height / 4,
								height / 2, height / 2);
					} else {
						// draw number within place
						decorator.drawString(String.valueOf(marking), x + width
								/ 3, y + (int) (height / 1.5));
					}
				} else {
					TransitionAttribute transitionAttribute = MainWindow
							.getPetrinetManipulation()
							.getTransitionAttribute(
									PetrinetPane.getInstance().currentPetrinetId,
									node);

					Color blackOrWhite = null;
					if (transitionAttribute.getIsActivated()) {
						blackOrWhite = Color.BLACK;
					} else {
						blackOrWhite = Color.LIGHT_GRAY;
					}

					// draw rect
					int size = TRANSITION_SIZE;
					int x = (int) (center.getX() - size / 2);
					int y = (int) (center.getY() - size / 2);
					decorator.setPaint(blackOrWhite);
					decorator.fillRect(x, y, size, size);

					// draw frame
					decorator.setPaint(NODE_BORDER_COLOR);
					decorator.drawRect(x, y, size, size);

					// draw name
					decorator.setPaint(FONT_COLOR);
					decorator.drawString(transitionAttribute.getTname(), x
							+ size + 2, y + size);

					// draw label
					if (transitionAttribute.getIsActivated()) {
						decorator.setPaint(Color.LIGHT_GRAY);
					} else {
						decorator.setPaint(Color.DARK_GRAY);
					}
					decorator.drawString(transitionAttribute.getTLB(), x + size
							/ 3, y + size / 1.5f);
				}
			} catch (EngineException e) {
				PopUp.popError(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Transforming Arcs to Strings. Actually just used for getting the label of
	 * an Arc
	 */
	private static class PetrinetArcLabelTransformer implements
			Transformer<Arc, String> {
		@Override
		public String transform(Arc arc) {
			int weight = 1;
			try {
				ArcAttribute arcAttribute = MainWindow
						.getPetrinetManipulation().getArcAttribute(
								PetrinetPane.getInstance().currentPetrinetId,
								arc);
				weight = arcAttribute.getWeight();
			} catch (EngineException e) {
				e.printStackTrace();
			}
			if (weight == 1) {
				return "";
			} else {
				return String.valueOf(weight);
			}
		}
	}

	/**
	 * This is for defining the nodes' shapes so the arrows properly connects to
	 * them
	 */
	private static class PetrinetNodeShapeTransformer implements
			Transformer<INode, Shape> {

		@Override
		public Shape transform(INode node) {
			try {
				if (MainWindow.getPetrinetManipulation().getNodeType(node) == NodeTypeEnum.Place) {
					return new Ellipse2D.Double(-PLACE_WIDTH / 2,
							-PLACE_HEIGHT / 2, PLACE_WIDTH, PLACE_HEIGHT);
				} else {
					return new Rectangle2D.Double(-TRANSITION_SIZE / 2,
							-TRANSITION_SIZE / 2, TRANSITION_SIZE,
							TRANSITION_SIZE);
				}
			} catch (EngineException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	/* Static constructor that initiates the singleton */
	static {
		instance = new PetrinetPane();
	}

	/** Private default constructor */
	private PetrinetPane() {
		petrinetPanel = new JPanel();
		petrinetPanel.setLayout(PETRINET_PANE_LAYOUT);
		petrinetPanel.setBorder(PETRINET_BORDER);

		visualizationViewer = initializeVisualizationViever();
		scaler = new CrossoverScalingControl();
		visualizationViewer.setBackground(Color.WHITE);
		scrollPanel = new GraphZoomScrollPane(visualizationViewer);

		petrinetPanel.add(scrollPanel);

		visualizationViewer.addMouseListener(new PetrinetMouseListener(this));
		visualizationViewer.addMouseWheelListener(new PetrinetMouseListener(
				this));
		visualizationViewer.getRenderer().setVertexRenderer(
				new PetrinetRenderer()); // make transitions and places look
											// like transitions and places

		visualizationViewer.getRenderContext().setEdgeLabelTransformer(
				new PetrinetArcLabelTransformer());
		visualizationViewer.getRenderContext().setVertexShapeTransformer(
				new PetrinetNodeShapeTransformer());
	}

	private JPanel getPetrinetPanel() {
		return petrinetPanel;
	}

	/**
	 * Returns the graphLayout for the currently displayed petrinet
	 * 
	 * @return <tt>null</tt> if Error was thrown
	 */
	private AbstractLayout<INode, Arc> getCurrentLayout() {
		try {
			AbstractLayout<INode, Arc> layout = MainWindow
					.getPetrinetManipulation().getJungLayout(currentPetrinetId);

			return layout;
		} catch (EngineException e) {
			e.printStackTrace();
		}
		return null;
	}

	private VisualizationViewer<INode, Arc> initializeVisualizationViever() {
		currentPetrinetId = MainWindow.getPetrinetManipulation()
				.createPetrinet();

		VisualizationViewer<INode, Arc> visuServer = new VisualizationViewer<INode, Arc>(
				getCurrentLayout());

		return visuServer;
	}

	/** Adds the petrinet pane to the given JPanel (frame) */
	public void addTo(JPanel frame) {
		frame.add(getPetrinetPanel());
	}

	public void repaint() {
		visualizationViewer.repaint();
	}
}
