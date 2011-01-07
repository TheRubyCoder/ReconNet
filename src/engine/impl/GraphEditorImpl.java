package engine.impl;

import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.JPanel;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;
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
    private CreateMode createMode = CreateMode.PLACE;
    private GraphPanel graphPanel;

	public GraphEditorImpl(EngineContext context, Engine engine) {
        this.context = context;

        graphPanel = new GraphPanel(engine, context);

    }

    private <T extends INode> T initNode(T node, Point2D location) {
        context.getGraph().addVertex(node);
        context.getLayout().setLocation(node, location);
        return node;
    }

    public IPlace createPlace(Point2D location) {
    	System.out.println(String.format("createPlace(%s)",location));
        return initNode(context.getPetrinet().createPlace("untitled"), location);
    }

    public ITransition createTransition(Point2D location) {
    	System.out.println(String.format("createTransition(%s)",location));
    	//TODO IRenew durchreichen
        return initNode(context.getPetrinet().createTransition("untitled", null), location);
    }

    public IArc createArc(INode from, INode to) {
    	try {
    		System.out.println(String.format("createArc(%s, %s)",from, to));
    		IArc arc = context.getPetrinet().createArc("", from, to);
    		context.getGraph().addEdge(arc, from, to, EdgeType.DIRECTED);
    		return arc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }


    public void remove(Set<INode> nodes) {
        for (INode node : nodes) {
            // TODO besser nur aus Petrinet l�schen und dann bei Listener-Callback aus Graph entfernen?
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

    /**
     * @return den gewählten createMode;
     */
    public CreateMode getCreateMode() {
		return createMode;
	}

    /**
     * Setzt den gegebenen createMode.
     * Hiervon ist abhängig ob beim Klicken in einen leeren Bereich des Graphenpannels
     * im Editmode eine Transition oder ein Place erzeugt wird.
     */
	public void setCreateMode(CreateMode createMode) {
		if(createMode == null) throw new IllegalArgumentException("createMode must not be null");
		this.createMode = createMode;
	}

	@Override
	public JPanel getGraphPanel() {
		return graphPanel;
	}

}
