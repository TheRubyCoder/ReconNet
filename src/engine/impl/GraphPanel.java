package engine.impl;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JPanel;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.BasicVertexRenderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import engine.EditMode;
import org.apache.commons.collections15.Transformer;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import engine.Engine;
import engine.impl.ctrl.EditingModalGraphMouseEx;
import petrinetze.ITransition;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final EngineContext ec;

    private final VisualizationViewer<INode, IArc> vv;

    private final EditingModalGraphMouseEx<INode, IArc> graphMouse;

    private Set<INode> picked = Collections.emptySet();

	public GraphPanel(Engine engine, EngineContext context) {
		ec = context;
		vv = new VisualizationViewer<INode, IArc>(ec.getLayout());
		
		setLayout(new BorderLayout());
		add(vv, BorderLayout.CENTER);
		
		graphMouse = new EditingModalGraphMouseEx<INode, IArc>(engine, vv.getRenderContext());
		vv.setGraphMouse(graphMouse);
		vv.addKeyListener(graphMouse.getModeKeyListener());
        vv.setBackground(Color.WHITE);
        vv.setGraphLayout(context.getLayout());

        // Make sure that arc selection clears node selection and  the other way round
        final ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    if (e.getSource() == vv.getPickedEdgeState() && vv.getPickedEdgeState().getPicked().size() == 1) {
                        vv.getPickedVertexState().clear();
                    }
                    else if (vv.getPickedVertexState().getPicked().size() == 1) {
                        vv.getPickedEdgeState().clear();
                    }

                    Set<INode> picked = new HashSet<INode>();
                    picked.addAll(vv.getPickedVertexState().getPicked());
                    picked.addAll(vv.getPickedEdgeState().getPicked());
                    GraphPanel.this.picked = picked;
                }
            }
        };

        // Handle changes of layout and edit mode
        context.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(EngineContext.PROPERTY_LAYOUT)) {
                    vv.setGraphLayout((AbstractLayout<INode,IArc>)evt.getNewValue());
                }
                else if (evt.getPropertyName().equals(EngineContext.PROPERTY_EDIT_MODE)) {
                    switch ((EditMode)evt.getNewValue()) {
                        case ADD_PLACE:
                        case ADD_TRANSITION:
                            graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
                            break;

                        case TRANSLATE:
                            graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                            break;

                        case PICK:
                            graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
                            break;
                    }
                }
            }
        });

        vv.getPickedEdgeState().addItemListener(itemListener);
        vv.getPickedVertexState().addItemListener(itemListener);

		vertexInit();
		edgeInit();
	}

	private void vertexInit() {
    	vv.getRenderContext().setVertexLabelTransformer(new Transformer<INode, String>() {
			@Override
			public String transform(INode node) {
				return node.getName();
			}
		});

        vv.getRenderContext().setVertexShapeTransformer(new Transformer<INode, Shape>() {

            final Shape transitionShape = new Rectangle2D.Float(-12, -12, 24, 24);

            final Shape placeShape = new Ellipse2D.Float(-24, -12, 48, 24);

            @Override
            public Shape transform(INode node) {
                return (node instanceof ITransition) ? transitionShape : placeShape;
            }
        });

        vv.getRenderContext().setVertexFillPaintTransformer(new Transformer<INode,Paint>() {
            @Override
            public Paint transform(INode node) {
                if (node instanceof IPlace) {
                    return ec.getUIEditor().getPlacePaint((IPlace)node);
                }

                return (node instanceof ITransition && ((ITransition)node).isActivated()) ?
                    Color.BLACK : Color.WHITE;
            }
        });

        vv.getRenderer().setVertexRenderer(new BasicVertexRenderer<INode, IArc>() {
            @Override
            protected void paintShapeForVertex(RenderContext<INode, IArc> rc, INode node, Shape shape) {
                super.paintShapeForVertex(rc, node, shape);
                if (node instanceof IPlace) {
                    final IPlace place = (IPlace) node;


                    if (place.getMark() == 1) {
                        GraphicsDecorator g = rc.getGraphicsContext();
                        Rectangle2D bounds = shape.getBounds2D();

                        g.setPaint(rc.getVertexDrawPaintTransformer().transform(node));

                        g.fillOval((int)(bounds.getCenterX() - 4), (int)(bounds.getCenterY() - 4), 8, 8);
                    }
                    else if (place.getMark() > 1) {
                        GraphicsDecorator g = rc.getGraphicsContext();

                        Rectangle2D bounds = shape.getBounds2D();

                        g.setPaint(rc.getVertexDrawPaintTransformer().transform(node));

                        final FontMetrics fm = g.getFontMetrics();
                        final String s = String.valueOf(place.getMark());
                        g.drawString(s,
                            (int)(bounds.getCenterX() - fm.stringWidth(s) / 2d),
                            (int)(bounds.getCenterY() + fm.getAscent() - fm.getHeight() / 2d)
                        );
                    }
                }
            }
        });
	}
	
	private void edgeInit() {

		vv.getRenderContext().setEdgeLabelTransformer(new Transformer<IArc, String>() {

            @Override
            public String transform(IArc arg0) {
                return String.valueOf(arg0.getMark());
            }
        });
	}
	
}
