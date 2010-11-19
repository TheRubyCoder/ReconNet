package engine.impl;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import engine.LayoutEditor;
import petrinetze.INode;

import java.awt.geom.Point2D;

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
        context.getLayout().lock(false);
    }

    @Override
    public void apply() {
        // TODO tut es das, was ich denke?
        context.getLayout().initialize();
    }
}
