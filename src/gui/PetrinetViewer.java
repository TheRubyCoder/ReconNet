package gui;

import static gui.Style.FACTOR_SELECTED_NODE;
import static gui.Style.FONT_COLOR_BRIGHT;
import static gui.Style.FONT_COLOR_DARK;
import static gui.Style.NODE_BORDER_COLOR;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Place;
import petrinet.Renews;
import petrinet.Transition;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import engine.attribute.ArcAttribute;
import engine.attribute.ColorGenerator;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import exceptions.EngineException;
import gui.EditorPane.EditorMode;

/**
 * A special jung visualization viewer for displaying and editing petrinets Some
 * methods are abstract as they behave different depending on whether the
 * petrinet is part of a rule or not (different method calls to engine)
 */
@SuppressWarnings("serial")
public class PetrinetViewer extends VisualizationViewer<INode, Arc> {

	/**
	 * ID of currently displayed petrinet or rule. If RuleNet is set (L,K or R)
	 * this is the rule Id. If RuleNet is not set (null) this id is the ID of
	 * the petrinet
	 */
	private int currentId = -1;

	/**
	 * Returns the general node size. Do not use this for concrete nodes. Use
	 * one of the following instead:
	 * 
	 * @return
	 */
	private double nodeSize = Style.NODE_SIZE_DEFAULT;

	/**
	 * Defines whether the viewer displays a L, K or R net. Null if it displays
	 * an N petrinet
	 */
	private RuleNet ruleNet;

	/**
	 * The node that is currently selected by user. <tt>null</tt> if no node is
	 * selected
	 */
	INode currentSelectedNode = null;

	/**
	 * Initiates a new petrinet viewer with a petrinet. Rulenet == null if the
	 * viewer displays the N petrinet
	 */
	PetrinetViewer(Layout<INode, Arc> layout, int petrinetOrRuleId,
			RuleNet ruleNet) {
		super(layout);
		this.ruleNet = ruleNet;
		this.currentId = petrinetOrRuleId;

		setBackground(Color.WHITE);

		addMouseListener(new PetrinetMouseListener(this));
		addMouseWheelListener(new PetrinetMouseListener(this));
		getRenderer().setVertexRenderer(new PetrinetRenderer(this));
		getRenderContext().setEdgeLabelTransformer(
				new PetrinetArcLabelTransformer(this));
		getRenderContext().setVertexShapeTransformer(
				new PetrinetNodeShapeTransformer(this));
	}

	void addTo(JPanel component) {
		component.add(this);
	}

	public void removeFrom(JPanel frame) {
		frame.remove(this);
	}

	void smartRepaint() {
		if (isN()) {
			super.repaint();
		} else {
			RulePane.getInstance().repaint();
		}
	}

	int getCurrentId() {
		return currentId;
	}

	/**
	 * Returns <tt>true</tt> if the current Id is the id of a petrinet
	 * (N-Petrinet)
	 * 
	 * @see PetrinetViewer#isL()
	 * @see PetrinetViewer#isK()
	 * @see PetrinetViewer#isR()
	 * @see PetrinetViewer#isN()
	 */
	boolean isIdPetrinetId() {
		return isN();
	}

	/**
	 * Returns <tt>true</tt> if the current Id is the id of a petrinet within a
	 * rule (L, K or R -Petrinet)
	 * 
	 * @see PetrinetViewer#isL()
	 * @see PetrinetViewer#isK()
	 * @see PetrinetViewer#isR()
	 * @see PetrinetViewer#isN()
	 */
	boolean isIdRuleId() {
		return !isIdPetrinetId();
	}

	boolean isR() {
		return ruleNet == RuleNet.R;
	}

	boolean isK() {
		return ruleNet == RuleNet.K;
	}

	boolean isL() {
		return ruleNet == RuleNet.L;
	}

	boolean isN() {
		return ruleNet == null;
	}

	RuleNet getRuleNet() {
		return ruleNet;
	}

	void scale(float factor, Point point) {
		if (this.nodeSize >= 20 || factor > 1) {
			resizeNodes(factor);
			changeNodeSizeInJungData(getNodeSize());
			moveAllNodesTo(factor, point);
		}
		smartRepaint();
	}

	private void changeNodeSizeInJungData(double nodeSize) {
		if (isN()) {
			EngineAdapter.getPetrinetManipulation().setNodeSize(getCurrentId(),
					nodeSize);
		} else {
			EngineAdapter.getRuleManipulation().setNodeSize(getCurrentId(),
					nodeSize);
		}

	}

	private void resizeNodes(float factor) {
		if (isN()) {
			this.nodeSize *= factor;
		} else {
			RulePane.getInstance().resizeNodes(factor);
		}
	}

	public void resizeNodesOnlyOnThisPartOfRule(float factor) {
		if (!isN()) {
			this.nodeSize *= factor;
		}
	}

	/**
	 * Moves all nodes to direction of <code>point</code> but only by
	 * <code>factor</code><br \>
	 * . If <code>factor</code> is smaller 1 nodes are moved towards the
	 * <code>point</code>, if <code>factor</code> is greater 1 nodes are moved
	 * away from <code>point</code>.
	 * 
	 * <h4>example</h4> Position of (only) node is [10,10], <code>point</code>
	 * is [0,5] and <code>factor</code> is 0,9. Resulting position of node is
	 * [9,9,5]
	 * 
	 * @param factor
	 * @param point
	 */
	public void moveAllNodesTo(float factor, Point point) {
		if (isN()) {
			EngineAdapter.getPetrinetManipulation().moveAllNodesTo(
					getCurrentId(), factor, point);
		} else {
			EngineAdapter.getRuleManipulation().moveAllNodesTo(getCurrentId(),
					factor, point);
		}
	}

	/**
	 * Returns the size of nodes in pixels
	 * 
	 * @return
	 */
	public double getNodeSize() {
		return nodeSize;
	}

	void setNodeSize(double nodeSize) {
		this.nodeSize = nodeSize;
	}

	/**
	 * Returns the width of a place in pixels
	 * 
	 * @return
	 */
	private double getPlaceWidth() {
		return nodeSize;
	}

	/**
	 * Returns the height of a place in pixels
	 * 
	 * @return
	 */
	private double getPlaceHeight() {
		return getPlaceWidth() / 1.5d;
	}

	/**
	 * Returns the size of a (quadratic) transition
	 * 
	 * @return
	 */
	private double getTransitionSize() {
		return getPlaceHeight();
	}

	/**
	 * Returns the distance that must be among nodes
	 * 
	 * @return
	 */
	public double getNodeDistance() {
		return getPlaceHeight();
	}

	PlaceAttribute getPlaceAttribute(Place place) {
		PlaceAttribute placeAttribute = null;
		try {
			if (isN()) {
				placeAttribute = EngineAdapter.getPetrinetManipulation()
						.getPlaceAttribute(getCurrentId(), place);
			} else {
				placeAttribute = EngineAdapter.getRuleManipulation()
						.getPlaceAttribute(getCurrentId(), place);
			}
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
		return placeAttribute;
	}

	TransitionAttribute getTransitionAttribute(Transition transition) {
		TransitionAttribute transitionAttribute = null;
		try {
			if (isN()) {
				transitionAttribute = EngineAdapter.getPetrinetManipulation()
						.getTransitionAttribute(getCurrentId(), transition);
			} else {
				transitionAttribute = EngineAdapter.getRuleManipulation()
						.getTransitionAttribute(getCurrentId(), transition);
			}
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
		return transitionAttribute;
	}

	ArcAttribute getArcAttribute(Arc arc) {
		ArcAttribute arcAttribute = null;
		try {
			if (isN()) {
				arcAttribute = EngineAdapter.getPetrinetManipulation()
						.getArcAttribute(getCurrentId(), arc);
			} else {
				arcAttribute = EngineAdapter.getRuleManipulation()
						.getArcAttribute(getCurrentId(), arc);
			}
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
		return arcAttribute;
	}

	boolean isNodePlace(INode node) {
		NodeTypeEnum result = null;
		try {
			if (isN()) {
				result = EngineAdapter.getPetrinetManipulation().getNodeType(
						node);
			} else {
				result = EngineAdapter.getRuleManipulation().getNodeType(node);
			}
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
		return result == NodeTypeEnum.Place;
	}

	void moveNode(INode node, Point relativePosition) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().moveNode(
						getCurrentId(), node, relativePosition);
			} else {
				EngineAdapter.getRuleManipulation().moveNode(getCurrentId(),
						node, relativePosition);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	void createArc(INode start, INode end) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().createArc(
						getCurrentId(), start, end);
			} else {
				EngineAdapter.getRuleManipulation().createArc(getCurrentId(),
						getRuleNet(), start, end);
			}
			smartRepaint();
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void createTransition(Point point) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().createTransition(
						getCurrentId(), point);
			} else {
				EngineAdapter.getRuleManipulation().createTransition(
						getCurrentId(), getRuleNet(), point);
			}
			smartRepaint();
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	void createPlace(Point point) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().createPlace(
						getCurrentId(), point);
			} else {
				EngineAdapter.getRuleManipulation().createPlace(getCurrentId(),
						getRuleNet(), point);
			}
			smartRepaint();
		} catch (EngineException e) {
			PopUp.popError(e);
		}
	}

	void deletePlace(Place place) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().deletePlace(
						getCurrentId(), place);
			} else {
				EngineAdapter.getRuleManipulation().deletePlace(getCurrentId(),
						getRuleNet(), place);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	void deleteTransition(Transition transition) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().deleteTransition(
						getCurrentId(), transition);
			} else {
				EngineAdapter.getRuleManipulation().deleteTransition(
						getCurrentId(), getRuleNet(), transition);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	void deleteArc(Arc arc) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().deleteArc(
						getCurrentId(), arc);
			} else {
				EngineAdapter.getRuleManipulation().deleteArc(getCurrentId(),
						getRuleNet(), arc);
				// MainWindow.getInstance().repaint();
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void setPname(Place place, String data) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setPname(
						getCurrentId(), place, data);
			} else {
				EngineAdapter.getRuleManipulation().setPname(getCurrentId(),
						place, data);
			}
			smartRepaint();
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}

	}

	public void setMarking(Place place, int marking) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setMarking(
						getCurrentId(), place, marking);
			} else {
				EngineAdapter.getRuleManipulation().setMarking(getCurrentId(),
						place, marking);
			}
			smartRepaint();
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}

	}

	public void setTname(Transition transition, String data) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setTname(
						getCurrentId(), transition, data);
			} else {
				EngineAdapter.getRuleManipulation().setTname(getCurrentId(),
						transition, data);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void setTlb(Transition transition, String data) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setTlb(getCurrentId(),
						transition, data);
			} else {
				EngineAdapter.getRuleManipulation().setTlb(getCurrentId(),
						transition, data);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void setRenew(Transition transition, String renew) {

		IRenew actualRenew = Renews.fromString(renew);
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setRnw(getCurrentId(),
						transition, actualRenew);
			} else {
				EngineAdapter.getRuleManipulation().setRnw(getCurrentId(),
						transition, actualRenew);
			}
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void setWeight(Arc arc, int weight) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setWeight(
						getCurrentId(), arc, weight);
			} else {
				EngineAdapter.getRuleManipulation().setWeight(getCurrentId(),
						arc, weight);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void setPlaceColor(INode place, Color color) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().setPlaceColor(
						getCurrentId(), place, color);
			} else {
				EngineAdapter.getRuleManipulation().setPlaceColor(
						getCurrentId(), place, color);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
	}

	public void moveGraph(int id, Point2D relativePosition) {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().moveGraph(id,
						relativePosition);
			} else {
				EngineAdapter.getRuleManipulation().moveGraph(id,
						relativePosition);
			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
		}
	}

	public void moveIntoVision() {
		try {
			if (isN()) {
				EngineAdapter.getPetrinetManipulation().moveGraphIntoVision(
						getCurrentId());
			} else {
				EngineAdapter.getRuleManipulation().moveGraphIntoVision(
						getCurrentId());

			}
			smartRepaint();
		} catch (Exception e) {
			PopUp.popError(e);
		}

	}

	private static class PetrinetGraphPopUpMenu extends JPopupMenu {

		public PetrinetGraphPopUpMenu(PetrinetViewer petrinetViewer) {
			JMenuItem menuItem = new JMenuItem("Ins Sichtfeld verschieben");
			menuItem.addActionListener(new MoveListener(petrinetViewer));
			add(menuItem);
		}

		private static class MoveListener implements ActionListener {

			private PetrinetViewer petrinetViewer;

			public MoveListener(PetrinetViewer petrinetViewer) {
				this.petrinetViewer = petrinetViewer;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				petrinetViewer.moveIntoVision();
			}

		}

	}

	/**
	 * Pop up menu that appears when a node or arc is right-clicked. It is used
	 * for deleting and settings colors
	 */
	private static class PetrinetNodePopUpMenu extends JPopupMenu {

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

			private PetrinetViewer petrinetViewer;

			private DeleteListener(Transition transition, Place place, Arc arc,
					PetrinetViewer petrinetViewer) {
				this.transition = transition;
				this.place = place;
				this.arc = arc;
				this.petrinetViewer = petrinetViewer;
			}

			static DeleteListener fromPlace(Place place,
					PetrinetViewer petrinetViewer) {
				return new DeleteListener(null, place, null, petrinetViewer);
			}

			static DeleteListener fromArc(Arc arc, PetrinetViewer petrinetViewer) {
				return new DeleteListener(null, null, arc, petrinetViewer);
			}

			static DeleteListener fromTransition(Transition transition,
					PetrinetViewer petrinetViewer) {
				return new DeleteListener(transition, null, null,
						petrinetViewer);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (place != null) {
					petrinetViewer.deletePlace(place);
				} else if (transition != null) {
					petrinetViewer.deleteTransition(transition);
				} else {
					petrinetViewer.deleteArc(arc);
				}
				// deselect node
				petrinetViewer.currentSelectedNode = null;
				MainWindow.getInstance().repaint();
			}
		}

		/** Listener for clicks on color fields in context menus of places */
		private static class ChangeColorListener implements ActionListener {

			private PetrinetViewer petrinetViewer;

			private Color color;

			private INode place;

			private ChangeColorListener(Color color, INode place,
					PetrinetViewer petrinetViewer) {
				this.petrinetViewer = petrinetViewer;
				this.color = color;
				this.place = place;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				petrinetViewer.setPlaceColor(place, color);
			}

		}

		private PetrinetNodePopUpMenu(PetrinetViewer petrinetViewer) {
		}

		/** Adds a new item to the menu for changing the color of a node */
		static private void addColorToPopUpMenu(PetrinetNodePopUpMenu menu,
				String description, Color color, INode node,
				PetrinetViewer petrinetViewer) {
			JMenuItem item = new JMenuItem(description);
			item.setBackground(color);
			item.addActionListener(new ChangeColorListener(color, node,
					petrinetViewer));
			menu.add(item);
		}

		static PetrinetNodePopUpMenu fromNode(INode node,
				PetrinetViewer petrinetViewer) {
			PetrinetNodePopUpMenu result = new PetrinetNodePopUpMenu(
					petrinetViewer);
			if (petrinetViewer.isNodePlace(node)) {
				PlaceAttribute placeAttribute = petrinetViewer
						.getPlaceAttribute((Place) node);
				// Delete
				JMenuItem delete = new JMenuItem("Stelle "
						+ placeAttribute.getPname() + " [" + node.getId()
						+ "] " + "löschen");
				delete.addActionListener(DeleteListener.fromPlace((Place) node,
						petrinetViewer));
				result.add(delete);

				// Colors
				ColorGenerator colorGenerator = new ColorGenerator();
				for (Color color : colorGenerator.getFixedColors()) {
					addColorToPopUpMenu(result,
							colorGenerator.getDescription(color), color, node,
							petrinetViewer);
				}

			} else {
				TransitionAttribute transitionAttribute = petrinetViewer
						.getTransitionAttribute((Transition) node);
				JMenuItem jMenuItem = new JMenuItem("Transition "
						+ transitionAttribute.getTname() + " [" + node.getId()
						+ "] " + "löschen");
				jMenuItem.addActionListener(DeleteListener.fromTransition(
						(Transition) node, petrinetViewer));
				result.add(jMenuItem);
			}
			return result;
		}

		static PetrinetNodePopUpMenu fromArc(Arc arc,
				PetrinetViewer petrinetViewer) {
			PetrinetNodePopUpMenu result = new PetrinetNodePopUpMenu(
					petrinetViewer);
			JMenuItem jMenuItem = new JMenuItem("Pfeil [" + arc.getId()
					+ "] löschen");
			jMenuItem.addActionListener(DeleteListener.fromArc(arc,
					petrinetViewer));
			result.add(jMenuItem);
			return result;
		}
	}

	/** mouse click listener for the drawing panel */
	private static class PetrinetMouseListener extends
			PickingGraphMousePlugin<INode, Arc> implements MouseWheelListener {

		private static enum DragMode {
			SCROLL, MOVENODE, ARC, NONE
		}

		private PetrinetViewer petrinetViewer;

		private DragMode dragMode = DragMode.NONE;

		PetrinetMouseListener(PetrinetViewer petrinetViewer) {
			this.petrinetViewer = petrinetViewer;
		}

		/** X-coordinate of begin of drag */
		private int pressedX = 0;
		/** Y-coordinate of begin of drag */
		private int pressedY = 0;

		/**
		 * ID of node that was clicked at beginning of drag. Needed for drawing
		 * arcs
		 */
		private INode nodeFromDrag = null;

		/** Zoom petrinet on mouse wheel */
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			Point point = new Point(e.getX(), e.getY());
			float factor = e.getWheelRotation() < 0 ? 1.1f : 0.9f;
			petrinetViewer.scale(factor, point);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mousePressed(e); // mousePressedEvent in class
									// PickingGraphMousePlugin selects nodes
			EditorMode mode = EditorPane.getInstance().getCurrentMode();

			// left-click PICK : display clicked node
			// right-click: display pop-up-menu
			// left-click PLACE: create place at position
			// left-click TRANSITION: etc...
			if (mode == EditorMode.PICK) {
				if (edge != null) {
					// edge clicked
					AttributePane.getInstance().displayEdge(edge,
							petrinetViewer);
					petrinetViewer.currentSelectedNode = null;
					if (e.isMetaDown()) {
						PetrinetNodePopUpMenu.fromArc(edge, petrinetViewer)
								.show(petrinetViewer, e.getX(), e.getY());
					}

				} else if (vertex != null) {
					// vertex clicked
					AttributePane.getInstance().displayNode(vertex,
							petrinetViewer);
					petrinetViewer.currentSelectedNode = vertex;
					if (e.isMetaDown()) {
						PetrinetNodePopUpMenu.fromNode(vertex, petrinetViewer)
								.show(petrinetViewer, e.getX(), e.getY());
					}
				} else {
					// nothing clicked
					if (e.isMetaDown()) {
						PetrinetGraphPopUpMenu popUp = new PetrinetGraphPopUpMenu(
								petrinetViewer);
						popUp.show(petrinetViewer, e.getX(), e.getY());
					} else {
						// nothing
					}
					petrinetViewer.currentSelectedNode = null;
					AttributePane.getInstance().displayEmpty();
				}
			} else if (mode == EditorMode.PLACE) {
				petrinetViewer.createPlace(new Point(e.getX(), e.getY()));
			} else if (mode == EditorMode.TRANSITION) {
				petrinetViewer.createTransition(new Point(e.getX(), e.getY()));
			}

			if (petrinetViewer.isN()) {
				petrinetViewer.repaint();
			} else {
				RulePane.getInstance().deselectBut(petrinetViewer);
				RulePane.getInstance().repaint();
			}
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
					petrinetViewer.moveGraph(petrinetViewer.getCurrentId(),
							new Point(
									(int) (newPoint.getX() - oldPoint.getX()),
									(int) (newPoint.getY() - oldPoint.getY())));
					petrinetViewer.repaint();
					// petrinetViewer.scale(0.5f, newPoint);
					// petrinetViewer.scale(2f, oldPoint);
				} else if (dragMode == DragMode.MOVENODE) {
					petrinetViewer.moveNode(nodeFromDrag, new Point(
							(int) (newPoint.getX() - oldPoint.getX()),
							(int) (newPoint.getY() - oldPoint.getY())));
					petrinetViewer.repaint();
				} else if (dragMode == DragMode.ARC) {
					// find out what was released on
					super.mousePressed(e);
					if (vertex != null) {
						petrinetViewer.createArc(nodeFromDrag, vertex);
					}
				}
			}
			dragMode = DragMode.NONE;
			nodeFromDrag = null;
		}
	} // end of mouse listener

	/**
	 * Custom renderer that is used from jung to make transitions cornered and
	 * places circular
	 */
	private static class PetrinetRenderer implements Vertex<INode, Arc> {

		private PetrinetViewer petrinetViewer;

		PetrinetRenderer(PetrinetViewer petrinetViewer) {
			this.petrinetViewer = petrinetViewer;
		}

		@Override
		public void paintVertex(RenderContext<INode, Arc> renderContext,
				Layout<INode, Arc> layout, INode node) {
			GraphicsDecorator decorator = renderContext.getGraphicsContext();
			decorator.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			decorator.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Point2D center = layout.transform(node);
			if (petrinetViewer.isNodePlace(node)) {
				PlaceAttribute placeAttribute = petrinetViewer
						.getPlaceAttribute((Place) node);
				double width = petrinetViewer.getPlaceWidth();
				double height = petrinetViewer.getPlaceHeight();
				double x = center.getX() - width / 2;
				double y = center.getY() - height / 2;

				/* Draw selected node bigger */
				if (petrinetViewer.isN()) {
					if (petrinetViewer.currentSelectedNode == node) {
						width *= FACTOR_SELECTED_NODE;
						height *= FACTOR_SELECTED_NODE;
					}
				} else {
					if (node != null
							&& RulePane.getInstance()
									.getMappingsOfSelectedNode().contains(node)) {
						width *= FACTOR_SELECTED_NODE;
						height *= FACTOR_SELECTED_NODE;
					}
				}

				GradientPaint gradientPaintPlace = new GradientPaint((int) x,
						(int) y, placeAttribute.getColor().brighter(), (int) x,
						(int) (y + height), placeAttribute.getColor().darker());
				decorator.setPaint(gradientPaintPlace);
				decorator.fillOval((int) x, (int) y, (int) width, (int) height);

				// draw frame
				decorator.setPaint(NODE_BORDER_COLOR);
				decorator.drawOval((int) x, (int) y, (int) width, (int) height);

				// write name
				decorator.setPaint(FONT_COLOR_DARK);
				decorator.drawString(placeAttribute.getPname(),
						(int) (x + width), (int) (y + height));

				// display marking
				int marking = placeAttribute.getMarking();
				if (marking == 0) {
					// view nothing (empty place)
				} else if (marking == 1) {
					// view black dot within place
					decorator.setPaint(Color.BLACK);
					decorator.fillOval((int) (x + width / 4),
							(int) (y + height / 4), (int) (height / 2),
							(int) (height / 2));
				} else {
					// draw number within place
					if (colorIsBright(placeAttribute.getColor())) {
						decorator.setPaint(FONT_COLOR_DARK);
					} else {
						decorator.setPaint(FONT_COLOR_BRIGHT);
					}
					decorator.drawString(String.valueOf(marking),
							(int) (x + width / 3), (int) (y + height / 1.5));
				}
			} else {
				TransitionAttribute transitionAttribute = petrinetViewer
						.getTransitionAttribute((Transition) node);

				// draw rect
				int size = (int) petrinetViewer.getTransitionSize();
				int x = (int) (center.getX() - size / 2);
				int y = (int) (center.getY() - size / 2);

				GradientPaint blackOrWhite = null;
				if (transitionAttribute.getIsActivated()) {
					blackOrWhite = new GradientPaint(x, y, Color.DARK_GRAY, x,
							y + size, Color.BLACK);
				} else {
					blackOrWhite = new GradientPaint(x, y, Color.LIGHT_GRAY, x,
							y + size, Color.LIGHT_GRAY.darker());
				}

				/* Draw selected node bigger */
				if (petrinetViewer.isN()) {
					if (petrinetViewer.currentSelectedNode == node) {
						size *= FACTOR_SELECTED_NODE;
					}
				} else {
					if (node != null
							&& RulePane.getInstance()
									.getMappingsOfSelectedNode().contains(node)) {
						size *= FACTOR_SELECTED_NODE;
					}
				}

				decorator.setPaint(blackOrWhite);
				decorator.fillRect(x, y, size, size);

				// draw frame
				decorator.setPaint(NODE_BORDER_COLOR);
				decorator.drawRect(x, y, size, size);

				// draw name
				decorator.setPaint(FONT_COLOR_DARK);
				decorator.drawString(transitionAttribute.getTname(), x + size
						+ 2, y + size);

				// draw label
				if (transitionAttribute.getIsActivated()) {
					decorator.setPaint(Color.LIGHT_GRAY);
				} else {
					decorator.setPaint(Color.DARK_GRAY);
				}
				decorator.drawString(transitionAttribute.getTLB(),
						x + size / 3, y + size / 1.5f);
			}
		}

		/**
		 * Returns <code>true</code> if the color is bright enough so text that
		 * is displayed upon it, must be drawn with
		 * {@link Style#FONT_COLOR_DARK}
		 * 
		 * @see Style#FONT_COLOR_BRIGHT
		 * @param color
		 * @return
		 */
		private boolean colorIsBright(Color color) {
			return color.getBlue() + color.getRed() + color.getGreen() > 382;
		}
	}

	/**
	 * Return <code>true</code> if the color is bright enough so text on it cant
	 * be drawn with {@link Style#FONT_COLOR_DARK}. Else it should be drawn with
	 * {@link Style#FONT_COLOR_DARK}
	 * 
	 * @param color
	 * @return
	 */

	/**
	 * Transforming Arcs to Strings. Actually just used for getting the label of
	 * an Arc
	 */
	private static class PetrinetArcLabelTransformer implements
			Transformer<Arc, String> {

		private PetrinetViewer petrinetViewer;

		PetrinetArcLabelTransformer(PetrinetViewer petrinetViewer) {
			this.petrinetViewer = petrinetViewer;
		}

		@Override
		public String transform(Arc arc) {
			int weight = 1;
			ArcAttribute arcAttribute = petrinetViewer.getArcAttribute(arc);
			weight = arcAttribute.getWeight();
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

		PetrinetViewer petrinetViewer;

		PetrinetNodeShapeTransformer(PetrinetViewer petrinetViewer) {
			this.petrinetViewer = petrinetViewer;
		}

		@Override
		public Shape transform(INode node) {
			if (petrinetViewer.isNodePlace(node)) {
				// return new Ellipse2D.Double(0d, 0d, Style.getPlaceWidth(),
				// Style.getPlaceHeight());
				return new Ellipse2D.Double(
						-petrinetViewer.getPlaceWidth() / 2,
						-petrinetViewer.getPlaceHeight() / 2,
						petrinetViewer.getPlaceWidth(),
						petrinetViewer.getPlaceHeight());
			} else {
				return new Rectangle2D.Double(
						-petrinetViewer.getTransitionSize() / 2,
						-petrinetViewer.getTransitionSize() / 2,
						petrinetViewer.getTransitionSize(),
						petrinetViewer.getTransitionSize());
			}
		}

	}

}
