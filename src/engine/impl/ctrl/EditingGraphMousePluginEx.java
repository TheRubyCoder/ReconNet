package engine.impl.ctrl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import org.apache.commons.collections15.Factory;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.util.ArrowFactory;
import engine.GraphEditor;

public class EditingGraphMousePluginEx<V extends INode, E extends IArc> extends EditingGraphMousePlugin<V, E> {


	private GraphEditor graphEditor;

	public EditingGraphMousePluginEx(GraphEditor graphEditor, Factory<V> vertexFactory, Factory<E> edgeFactory) {
		super(MouseEvent.BUTTON1_MASK, vertexFactory, edgeFactory);
		this.graphEditor = graphEditor;
	}



	/**
	 * If the mouse is pressed in an empty area, create a new vertex there. If
	 * the mouse is pressed on an existing vertex, prepare to create an edge
	 * from that vertex to another
	 */
	@SuppressWarnings("unchecked")
	public void mousePressed(MouseEvent e) {
		if (checkModifiers(e)) {
			final VisualizationViewer<V, E> vv = (VisualizationViewer<V, E>) e.getSource();
			final Point2D p = e.getPoint();
			GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();
			if (pickSupport != null) {
				Graph<V, E> graph = vv.getModel().getGraphLayout().getGraph();
				// set default edge type
				if (graph instanceof DirectedGraph) {
					edgeIsDirected = EdgeType.DIRECTED;
				} else {
					edgeIsDirected = EdgeType.UNDIRECTED;
				}

				final V vertex = pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());
				if (vertex != null) { // get ready to make an edge
					startVertex = vertex;
					down = e.getPoint();
					transformEdgeShape(down, down);
					vv.addPostRenderPaintable(edgePaintable);
					if ((e.getModifiers() & MouseEvent.SHIFT_MASK) != 0
							&& vv.getModel().getGraphLayout().getGraph() instanceof UndirectedGraph == false) {
						edgeIsDirected = EdgeType.DIRECTED;
					}
					if (edgeIsDirected == EdgeType.DIRECTED) {
						transformArrowShape(down, e.getPoint());
						vv.addPostRenderPaintable(arrowPaintable);
					}
				} else { // make a new vertex

//					V newVertex = vertexFactory.create();
//					Layout<V, E> layout = vv.getModel().getGraphLayout();
//					graph.addVertex(newVertex);
//					layout.setLocation(newVertex,
//							vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint()));				}

					//TODO changed code
					if(graphEditor.getCreateMode() == GraphEditor.CreateMode.PLACE) {
						graphEditor.createPlace(vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint()));
					}
					else {
						graphEditor.createTransition(vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint()));
					}
				}

			}
			vv.repaint();
		}
	}

	/**
	 * If startVertex is non-null, and the mouse is released over an existing
	 * vertex, create an undirected edge from startVertex to the vertex under
	 * the mouse pointer. If shift was also pressed, create a directed edge
	 * instead.
	 */
	@SuppressWarnings("unchecked")
	public void mouseReleased(MouseEvent e) {
		if (checkModifiers(e)) {
			final VisualizationViewer<V, E> vv = (VisualizationViewer<V, E>) e.getSource();
			final Point2D p = e.getPoint();
			Layout<V, E> layout = vv.getModel().getGraphLayout();
			GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();
			if (pickSupport != null) {
				final V vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
				if (vertex != null && startVertex != null) {
					System.out.println("Must create arc");
					//TODO changed code
					if(startVertex instanceof ITransition && vertex instanceof IPlace
							|| vertex instanceof ITransition && startVertex instanceof IPlace) {
						graphEditor.createArc(startVertex, vertex);
					}					
				}
			}
			startVertex = null;
			down = null;
			edgeIsDirected = EdgeType.UNDIRECTED;
			vv.removePostRenderPaintable(edgePaintable);
			vv.removePostRenderPaintable(arrowPaintable);
			vv.repaint();
		}
	}



	/**
	 * code lifted from PluggableRenderer to move an edge shape into an
	 * arbitrary position
	 */
	private void transformEdgeShape(Point2D down, Point2D out) {
		float x1 = (float) down.getX();
		float y1 = (float) down.getY();
		float x2 = (float) out.getX();
		float y2 = (float) out.getY();

		AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);

		float dx = x2 - x1;
		float dy = y2 - y1;
		float thetaRadians = (float) Math.atan2(dy, dx);
		xform.rotate(thetaRadians);
		float dist = (float) Math.sqrt(dx * dx + dy * dy);
		xform.scale(dist / rawEdge.getBounds().getWidth(), 1.0);
		edgeShape = xform.createTransformedShape(rawEdge);
	}

    private void transformArrowShape(Point2D down, Point2D out) {
        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x2, y2);

        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
        arrowShape = xform.createTransformedShape(rawArrowShape);
    }

}
