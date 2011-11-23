package engine;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.util.Animator;

public class LayoutEditor{

    private final EngineContext context;

    public LayoutEditor(EngineContext context) {
        this.context = context;
    }
    
    /**
     * Lock the position of a node. So it cannot be moved
     * @param node which should be locked
     */
    public void lock(INode node) {
        context.getLayout().lock(node, true);
    }

    /**
     * Unlock the position of a node. So it can be moved
     *
     * @param node which should be unlocked
     */
    public void unlockNode(INode node) {
        context.getLayout().lock(node, false);
    }

    /**
     * checks if a node is locked
     *
     * @param node the node to be checked
     * @return <code> true </code> if node is locked, <code> false </code> otherwise
     */
    public boolean isLocked(INode node) {
        return context.getLayout().isLocked(node);
    }

    /**
     * gets the position of the graphical representation of the node.
     *
     * @param node which position you want to get
     *
     * @return position as a 2D Point 
     */
    public Point2D getPosition(INode node) {
        // TODO tut es das, was ich denke?
        return new Point2D.Double(context.getLayout().getX(node), context.getLayout().getY(node));
    }

    /**
     * sets the position of the graphical representation of the node.
     *
     * @param node which position you want to set
     * @param location position where the point should be set
     */
    public void setPosition(INode node, Point2D location) {
        context.getLayout().setLocation(node, location);
    }

    /**
     * Unlock all locked nodes.
     */
    public void unlockAll() {
        // TODO tut es das, was ich denke?

    }

    /**
     * Applys the new layout.
     * @param vv new Visualition Viewer
     */
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
    
    /**
     * Sets new layout
     * After this the method <code>#apply</code> must be called to apply the layout.
     * @param l Layout to be set
     */
    public void setLayout(engine.Layout l) {
    	context.setLayout(l.getInstance(context.getGraph()));
    }
}
