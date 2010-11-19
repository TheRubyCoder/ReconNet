package engine.impl;

import engine.GraphEditor;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;

import java.awt.geom.Point2D;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:16:12
 * To change this template use File | Settings | File Templates.
 */
class GraphEditorImpl implements GraphEditor {

    private final EngineContext context;

    public GraphEditorImpl(EngineContext context) {
        this.context = context;
    }

    private <T extends INode> T init(T node, Point2D location) {
        context.getGraph().addVertex(node);
        context.getLayout().setLocation(node, location);
        return node;
    }

    public IPlace createPlace(Point2D.Float location) {
        return init(context.getPetrinet().createPlace("untitled"), location);
    }

    public ITransition createTransition(Point2D.Float location) {
        return init(context.getPetrinet().createTransition("untitled"), location);
    }

    private IArc createArcInternal(INode from, INode to) {
        final IArc arc = context.getPetrinet().createArc();
        arc.setStart(from);
        arc.setEnd(to);
        context.getGraph().addEdge(arc, from, to);
        return arc;
    }

    public IArc createIncomingArc(IPlace from, ITransition to) {
        return createArcInternal(from, to);
    }

    public IArc createOutgoingArc(ITransition from, IPlace to) {
        return createArcInternal(from, to);
    }

    public void remove(Set<INode> nodes) {
        for (INode node : nodes) {
            // TODO besser nur aus Petrinet löschen und dann bei Listener-Callback aus Graph entfernen?
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
}
