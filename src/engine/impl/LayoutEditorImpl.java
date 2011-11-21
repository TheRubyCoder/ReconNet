package engine.impl;

import java.awt.geom.Point2D;

import petrinetze.Arc;
import petrinetze.INode;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.util.Animator;
import engine.LayoutEditor;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 17:38:37
 * To change this template use File | Settings | File Templates.
 */
class LayoutEditorImpl implements LayoutEditor {

    private final EngineContext context;

    public LayoutEditorImpl(EngineContext context) {
        this.context = context;
    }

    @Override
    public void lock(INode node) {
        context.getLayout().lock(node, true);
    }

    @Override
    public void unlockNode(INode node) {
        context.getLayout().lock(node, false);
    }

    @Override
    public boolean isLocked(INode node) {
        return context.getLayout().isLocked(node);
    }

    @Override
    public Point2D getPosition(INode node) {
        // TODO tut es das, was ich denke?
        return new Point2D.Double(context.getLayout().getX(node), context.getLayout().getY(node));
    }

    @Override
    public void setPosition(INode node, Point2D location) {
        context.getLayout().setLocation(node, location);
    }

    @Override
    public void unlockAll() {
        // TODO tut es das, was ich denke?

    }

    @Override
    public void apply(VisualizationViewer<INode, Arc> vv) {

        PickedState<INode> pvs = vv.getPickedVertexState();

        if(!pvs.getPicked().isEmpty()) {
        	context.getLayout().lock(true);
    		for(INode node : pvs.getPicked()) {
    			context.getLayout().lock(node, false);
    		}
        } else {
        	context.getLayout().lock(false);
        }


        context.getLayout().setInitializer(vv.getGraphLayout());
        context.getLayout().setSize(vv.getSize());

		LayoutTransition<INode, Arc> lt =
			new LayoutTransition<INode, Arc>(vv, vv.getGraphLayout(), context.getLayout());
		Animator animator = new Animator(lt);
		animator.start();
		vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
		vv.repaint();

    }

    public void setLayout(engine.impl.Layout l) {
    	context.setLayout(l.getInstance(context.getGraph()));
    }
}
