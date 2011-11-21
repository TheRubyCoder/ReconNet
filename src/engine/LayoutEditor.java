package engine;

import petrinetze.Arc;
import petrinetze.INode;

import java.awt.geom.Point2D;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * class for editing the layouts
 *
 */
public interface LayoutEditor {

    /**
     * Lock the position of a node. So it cannot be moved
     * @param node which should be locked
     */
    void lock(INode node);

    /**
     * Unlock the position of a node. So it can be moved
     *
     * @param node which should be unlocked
     */
    void unlockNode(INode node);

    /**
     * checks if a node is locked
     *
     * @param node the node to be checked
     * @return <code> true </code> if node is locked, <code> false </code> otherwise
     */
    boolean isLocked(INode node);

    /**
     * gets the position of the graphical representation of the node.
     *
     * @param node which position you want to get
     *
     * @return position as a 2D Point 
     */
    Point2D getPosition(INode node);

    /**
     * sets the position of the graphical representation of the node.
     *
     * @param node which position you want to set
     * @param location position where the point should be set
     */
    void setPosition(INode node, Point2D location);

    /**
     * Unlock all locked nodes.
     */
    void unlockAll();

    /**
     * Applys the new layout.
     * @param vv new Visualition Viewer
     */
    void apply(VisualizationViewer<INode, Arc> vv);
   
    /**
     * Sets new layout
     * After this the method <code>#apply</code> must be called to apply the layout.
     * @param l Layout to be set
     */
    public void setLayout(engine.Layout l);
}
