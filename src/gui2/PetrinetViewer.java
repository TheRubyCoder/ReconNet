//package gui2;
//
//import java.awt.Color;
//import java.awt.Point;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//
//import javax.swing.JMenuItem;
//import javax.swing.JPopupMenu;
//
//import petrinet.Arc;
//import petrinet.INode;
//import petrinet.Place;
//import petrinet.Transition;
//import edu.uci.ics.jung.algorithms.layout.Layout;
//import edu.uci.ics.jung.visualization.VisualizationViewer;
//import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
//import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
//import edu.uci.ics.jung.visualization.control.ScalingControl;
//import engine.attribute.PlaceAttribute;
//import engine.attribute.TransitionAttribute;
//import engine.handler.NodeTypeEnum;
//import exceptions.EngineException;
//import gui2.EditorPane.EditorMode;
//
//@SuppressWarnings("serial")
//abstract class PetrinetViewer extends VisualizationViewer<INode, Arc> {
//
//	/** ID of currently displayed petrinet */
//	private int currentPetrinetId = -1;
//
//	/** For zooming */
//	private ScalingControl scaler;
//
//	/**
//	 * For defining maximim zoom. Places are not displayed correctly if the user
//	 * zooms too deep
//	 */
//	float currentZoom = 1;
//
//	PetrinetViewer(Layout<INode, Arc> layout) {
//		super(layout);
//		scaler = new CrossoverScalingControl();
//	}
//
//	int getCurrentPetrinetId() {
//		return currentPetrinetId;
//	}
//
//	void displayPetrinet(int pId) {
//
//	}
//
//	void scale(float factor, Point point) {
//		if (currentZoom * factor <= 1) {
//			scaler.scale(this, factor, point);
//			currentZoom *= factor;
//		}
//	}
//
//	abstract void moveNode(int pId, INode node, Point relativePosition);
//
//	abstract void createArc(int pId, INode start, INode end);
//
//	abstract void deletePlace(int pId, Place place);
//
//	abstract void deleteTransition(int pId, Transition transition);
//
//	abstract void deleteArc(int pId, Arc arc);
//	
//	abstract void createPlace(int currentPetrinetId2, Point point);
//
//	/**
//	 * Pop up menu that appears when a node or arc is right-clicked. It is used
//	 * for deleting
//	 */
//	private static class PetrinetPopUpMenu extends JPopupMenu {
//
//		/**
//		 * Listener for clicking on menu items<br>
//		 * Each menu item has its own listener
//		 */
//		/*
//		 * There are 3 types of listeners as there are 3 types of pop up menus:
//		 * Node(Place/Transition) and Arc
//		 */
//		private static class DeleteListener implements ActionListener {
//
//			private DeleteListener() {
//			}
//
//			private Transition transition;
//
//			private Place place;
//
//			private Arc arc;
//
//			private int pId;
//
//			private PetrinetViewer petrinetViewer;
//
//			private DeleteListener(Transition transition, Place place, Arc arc,
//					int pId, PetrinetViewer petrinetViewer) {
//				this.transition = transition;
//				this.place = place;
//				this.arc = arc;
//				this.pId = pId;
//				this.petrinetViewer = petrinetViewer;
//			}
//
//			static DeleteListener fromPlace(Place place, int pId,
//					PetrinetViewer petrinetViewer) {
//				return new DeleteListener(null, place, null, pId,
//						petrinetViewer);
//			}
//
//			static DeleteListener fromArc(Arc arc, int pId,
//					PetrinetViewer petrinetViewer) {
//				return new DeleteListener(null, null, arc, pId, petrinetViewer);
//			}
//
//			static DeleteListener fromTransition(Transition transition,
//					int pId, PetrinetViewer petrinetViewer) {
//				return new DeleteListener(transition, null, null, pId,
//						petrinetViewer);
//			}
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (place != null) {
//					petrinetViewer.deletePlace(pId, place);
//				} else if (transition != null) {
//					petrinetViewer.deleteTransition(pId, transition);
//				} else {
//					petrinetViewer.deleteArc(pId, arc);
//				}
//				PetrinetPane.getInstance().repaint();
//			}
//		}
//
//		/** Listener for clicks on color fields in context menus of places */
//		private static class ChangeColorListener implements ActionListener {
//
//			private Color color;
//
//			private INode place;
//
//			private ChangeColorListener(Color color, INode place) {
//				this.color = color;
//				this.place = place;
//			}
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println(color);
//				MainWindow.getPetrinetManipulation().setPlaceColor(
//						PetrinetPane.getInstance().currentPetrinetId, place,
//						color);
//				PetrinetPane.getInstance().repaint();
//			}
//
//		}
//
//		PetrinetViewer petrinetViewer;
//
//		private PetrinetPopUpMenu(PetrinetViewer petrinetViewer) {
//			this.petrinetViewer = petrinetViewer;
//		}
//
//		/** Adds a new item to the menu for changing the color of a node */
//		static private void addColorToPopUpMenu(PetrinetPopUpMenu menu,
//				String description, Color color, INode node) {
//			JMenuItem item = new JMenuItem(description);
//			item.setBackground(color);
//			item.addActionListener(new ChangeColorListener(color, node));
//			menu.add(item);
//		}
//
//		static PetrinetPopUpMenu fromNode(INode node,
//				PetrinetViewer petrinetViewer) {
//			PetrinetPopUpMenu result = new PetrinetPopUpMenu(petrinetViewer);
//			int pId = petrinetViewer.getCurrentPetrinetId();
//			try {
//				if (MainWindow.getPetrinetManipulation().getNodeType(node) == NodeTypeEnum.Place) {
//					PlaceAttribute placeAttribute = MainWindow
//							.getPetrinetManipulation().getPlaceAttribute(pId,
//									node);
//
//					// Delete
//					JMenuItem delete = new JMenuItem("Stelle "
//							+ placeAttribute.getPname() + " [" + node.getId()
//							+ "] " + "löschen");
//					delete.addActionListener(DeleteListener.fromPlace(
//							(Place) node, pId, petrinetViewer));
//					result.add(delete);
//
//					// Color blue
//					addColorToPopUpMenu(result, "Blau", Color.BLUE, node);
//
//					// Color light blue
//					addColorToPopUpMenu(result, "Hellblau", new Color(200, 200,
//							250), node);
//
//					// Color red
//					addColorToPopUpMenu(result, "Rot", Color.RED, node);
//
//					// Light red
//					addColorToPopUpMenu(result, "HellRot", new Color(250, 200,
//							200), node);
//
//				} else {
//					TransitionAttribute transitionAttribute = MainWindow
//							.getPetrinetManipulation().getTransitionAttribute(
//									petrinetViewer.currentPetrinetId, node);
//					JMenuItem jMenuItem = new JMenuItem("Transition "
//							+ transitionAttribute.getTname() + " ["
//							+ node.getId() + "] " + "löschen");
//					jMenuItem.addActionListener(DeleteListener.fromTransition(
//							(Transition) node, pId, petrinetViewer));
//					result.add(jMenuItem);
//				}
//			} catch (EngineException e) {
//				PopUp.popError(e);
//				e.printStackTrace();
//			}
//			return result;
//		}
//
//		static PetrinetPopUpMenu fromArc(Arc arc, PetrinetViewer petrinetViewer) {
//			PetrinetPopUpMenu result = new PetrinetPopUpMenu(petrinetViewer);
//			JMenuItem jMenuItem = new JMenuItem("Pfeil [" + arc.getId()
//					+ "] löschen");
//			jMenuItem.addActionListener(DeleteListener.fromArc(arc,
//					PetrinetPane.getInstance().currentPetrinetId,
//					petrinetViewer));
//			result.add(jMenuItem);
//			return result;
//		}
//	}
//
//	/** mouse click listener for the drawing panel */
//	private static class PetrinetMouseListener extends
//			PickingGraphMousePlugin<INode, Arc> implements MouseWheelListener {
//
//		private static enum DragMode {
//			SCROLL, MOVENODE, ARC, NONE
//		}
//
//		private PetrinetViewer petrinetViewer;
//
//		private DragMode dragMode = DragMode.NONE;
//
//		PetrinetMouseListener(PetrinetViewer petrinetViewer) {
//			this.petrinetViewer = petrinetViewer;
//		}
//
//		/** X-coordinate of begin of drag */
//		private int pressedX = 0;
//		/** Y-coordinate of begin of drag */
//		private int pressedY = 0;
//
//		/**
//		 * For defining maximim zoom. Places are not displayed correctly if the
//		 * user zooms too deep
//		 */
//		float currentZoom = 1;
//
//		/**
//		 * ID of node that was clicked at beginning of drag. Needed for drawing
//		 * arcs
//		 */
//		private INode nodeFromDrag = null;
//
//		/** Zoom petrinet on mouse wheel */
//		@Override
//		public void mouseWheelMoved(MouseWheelEvent e) {
//			Point point = new Point(e.getX(), e.getY());
//			float factor = e.getWheelRotation() < 0 ? 1.1f : 0.9f;
//			petrinetViewer.scale(factor, point);
//		}
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			super.mousePressed(e); // mousePressedEvent in class
//									// PickingGraphMousePlugin selects nodes
//			EditorMode mode = EditorPane.getInstance().getCurrentMode();
//
//			// left-click PICK : display clicked node
//			// right-click: display pop-up-menu
//			// left-click PLACE: create place at position
//			// left-click TRANSITION: etc...
//			if (mode == EditorMode.PICK) {
//				if (edge != null) {
//					AttributePane.getInstance().displayEdge(edge);
//					if (e.isMetaDown()) {
//						PetrinetPopUpMenu.fromArc(edge,petrinetViewer).show(
//								petrinetViewer,
//								e.getX(), e.getY());
//					}
//
//				} else if (vertex != null) {
//					AttributePane.getInstance().displayNode(vertex);
//					if (e.isMetaDown()) {
//						PetrinetPopUpMenu.fromNode(vertex,petrinetViewer).show(
//								petrinetViewer,
//								e.getX(), e.getY());
//					}
//				}
//			} else
//				try {
//					if (mode == EditorMode.PLACE) {
//						petrinetViewer.createPlace(
//								petrinetViewer.getCurrentPetrinetId(),
//								new Point(e.getX(), e.getY()));
//					} else if (mode == EditorMode.TRANSITION) {
//						MainWindow.getPetrinetManipulation().createTransition(
//								petrinetPane.currentPetrinetId,
//								new Point(e.getX(), e.getY()));
//					}
//					petrinetPane.petrinetPanel.repaint();
//				} catch (EngineException e1) {
//					e1.printStackTrace();
//				}
//		}
//
//		/** Checks if something is clicked without changing selection */
//		private boolean isAnythingClicked(MouseEvent e) {
//			Arc beforeEdge = edge;
//			INode beforeNode = vertex;
//			edge = null;
//			vertex = null;
//			super.mousePressed(e);
//
//			boolean result = edge != null || vertex != null;
//			edge = beforeEdge;
//			vertex = beforeNode;
//
//			return result;
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			super.mousePressed(e);
//			if (dragMode == DragMode.NONE) {
//				pressedX = e.getX();
//				pressedY = e.getY();
//				EditorMode editorMode = EditorPane.getInstance()
//						.getCurrentMode();
//				// dragging in pick mode
//				if (editorMode == EditorMode.PICK) {
//					// find out: scrolling or moving? -> is something clicked?
//					if (vertex != null) {
//						// something is clicked -> MOVENODE
//						dragMode = DragMode.MOVENODE;
//						nodeFromDrag = vertex;
//					} else {
//						// nothing is clicked -> SCROLL
//						dragMode = DragMode.SCROLL;
//					}
//					// dragging in arc mode
//				} else if (editorMode == EditorMode.ARC) {
//					// find out what was clicked on
//					super.mousePressed(e);
//					if (vertex != null) {
//						nodeFromDrag = vertex;
//						vertex = null;
//						dragMode = DragMode.ARC;
//					}
//				}
//			}
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			Point oldPoint = new Point(pressedX, pressedY);
//			Point newPoint = new Point(e.getX(), e.getY());
//
//			if (!oldPoint.equals(newPoint)) {
//				if (dragMode == DragMode.SCROLL) {
//					/*
//					 * Scrolling is realized by zooming out of old position and
//					 * zooming in to new position
//					 */
//					petrinetPane.scaler.scale(petrinetPane.visualizationViewer,
//							0.5f, newPoint);
//					petrinetPane.scaler.scale(petrinetPane.visualizationViewer,
//							2f, oldPoint);
//				} else if (dragMode == DragMode.MOVENODE) {
//					try {
//						MainWindow.getPetrinetManipulation().moveNode(
//								PetrinetPane.getInstance().currentPetrinetId,
//								nodeFromDrag,
//								new Point((int) (newPoint.getX() - oldPoint
//										.getX()),
//										(int) (newPoint.getY() - oldPoint
//												.getY())));
//						PetrinetPane.getInstance().repaint();
//					} catch (EngineException e1) {
//						PopUp.popError("Dahin können sie nicht verschieben");
//						e1.printStackTrace();
//					}
//				} else if (dragMode == DragMode.ARC) {
//					// find out what was released on
//					super.mousePressed(e);
//					if (vertex != null) {
//						try {
//							MainWindow.getPetrinetManipulation().createArc(
//									petrinetPane.currentPetrinetId,
//									nodeFromDrag, vertex);
//						} catch (EngineException e1) {
//							e1.printStackTrace();
//						}
//
//					}
//				}
//			}
//			dragMode = DragMode.NONE;
//			nodeFromDrag = null;
//		}
//	} // end of mouse listener
//}
