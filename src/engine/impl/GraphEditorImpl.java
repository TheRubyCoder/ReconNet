package engine.impl;

import edu.uci.ics.jung.graph.DirectedGraph;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Set;

import javax.swing.JPanel;

import engine.EditMode;
import org.apache.commons.collections15.Transformer;
import petrinetze.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import engine.Engine;
import engine.GraphEditor;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:16:12
 * To change this template use File | Settings | File Templates.
 */
class GraphEditorImpl implements GraphEditor {

    private final EngineContext context;

    private GraphPanel graphPanel;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public GraphEditorImpl(EngineContext context, Engine engine) {
        this.context = context;
        graphPanel = new GraphPanel(engine, context);

        // FIXME hotfix for reacting direct on additions to IPetrinet
        engine.getNet().addPetrinetListener(new IPetrinetListener() {

            @Override
            public void changed(IPetrinet petrinet, INode element, ActionType actionType) {
                final DirectedGraph<INode,IArc> g = GraphEditorImpl.this.context.getGraph();

                switch (actionType) {
                    case added:
                        if (!g.containsVertex(element)) {
                            g.addVertex(element);
                        }
                        break;

                    case deleted:
                        if (g.containsVertex(element)) {
                            g.removeVertex(element);
                        }
                        break;
                }
            }

            @Override
            public void changed(IPetrinet petrinet, IArc element, ActionType actionType) {
                final DirectedGraph<INode,IArc> g = GraphEditorImpl.this.context.getGraph();

                switch (actionType) {
                    case added:
                        if (!g.containsEdge(element)) {
                            g.addEdge(element, element.getStart(), element.getEnd());
                        }
                        break;

                    case deleted:
                        if (g.containsEdge(element)) {
                            g.removeEdge(element);
                        }
                        break;
                }
            }
        });
    }

    private <T extends INode> T initNode(T node, final Point2D location) {
        // FIXME hotfix for reacting direct on additions to IPetrinet
        if (!context.getGraph().containsVertex(node)) {
            context.getGraph().addVertex(node);
        }
        System.out.println("in: " + location);
        context.getLayout().setLocation(node, location.getX(), location.getY());
        System.out.println("out: " + context.getLayout().getX(node) + "," + context.getLayout().getY(node));
        return node;
    }

    public IPlace createPlace(Point2D location) {
    	INode place = initNode(context.getPetrinet().createPlace("untitled"), location);
    	System.out.println(String.format("createPlace(%d,%s)",place.getId(),location));
        return (IPlace)place;
    }

    public ITransition createTransition(Point2D location) {
    	INode transition = initNode(context.getPetrinet().createTransition("untitled", Renews.IDENTITY), location);
    	System.out.println(String.format("createTransition(%d,%s)",transition.getId(), location));
    	//TODO IRenew durchreichen
        return (ITransition)transition;
    }

    public IArc createArc(INode from, INode to) {
        System.out.println(String.format("createArc(%s, %s)",from, to));
        IArc arc = context.getPetrinet().createArc("", from, to);
        // context.getGraph().addEdge(arc, from, to, EdgeType.DIRECTED);
        return arc;
    }

    public void remove(Set<? extends INode> nodes) {
        for (INode node : nodes) {
            // TODO besser nur aus Petrinet l?schen und dann bei Listener-Callback aus Graph entfernen?
            if (node instanceof IArc) {
                context.getGraph().removeEdge((IArc)node);
                context.getPetrinet().deleteArcByID(node.getId());
            }
            else {
                context.getGraph().removeVertex(node);

                if (node instanceof ITransition) {
                    context.getPetrinet().deleteTransitionByID(node.getId());
                }
                else if (node instanceof IPlace) {
                    context.getPetrinet().deletePlaceById(node.getId());
                }
            }
        }
    }

    @Override
    public EditMode getEditMode() {
        return context.getEditMode();
    }

    @Override
    public void setEditMode(EditMode editMode) {
        if (editMode == null) throw new IllegalArgumentException("EditMode must not be null");
        context.setEditMode(editMode);
    }

    @Override
	public JPanel getGraphPanel() {
		return graphPanel;
	}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    public boolean hasListeners(String propertyName) {
        return pcs.hasListeners(propertyName);
    }

    public Set<INode> getPickedNodes() {
        return graphPanel == null ? Collections.<INode>emptySet() : graphPanel.getSelectedNodes();
    }
}
