package engine;

import petrinetze.IArc;
import petrinetze.INode;

import java.awt.geom.Point2D;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * Klasse zum Bearbeiten des Layouts.
 *
 */
public interface LayoutEditor {

    /**
     * Sperren der Position eines Knoten.
     * @param node
     */
    void lock(INode node);

    /**
     * Aufhebung der Positionssperre für einen Knoten.
     *
     * @param node
     */
    void unlockNode(INode node);

    /**
     * Abfragen des Sperrzustandes eines Knoten
     *
     * @param node
     * @return
     */
    boolean isLocked(INode node);

    /**
     * Position der grafischen Repräsentation des Knoten.
     *
     * @param node
     *
     * @return
     */
    Point2D getPosition(INode node);

    /**
     * Setzen der Position der grafischen Repräsentation des Knoten.
     *
     * @param node
     * @param location
     */
    void setPosition(INode node, Point2D location);

    /**
     * Alle gesperrten Knoten entsperren.
     */
    void unlockAll();

    /**
     * Anwenden des LayoutEditor.
     */
    void apply(VisualizationViewer<INode, IArc> vv);

    /**
     * Es muß im Anschluß <code>#apply</code> aufgerufen werden damit das Layout angewendet wird.
     * @param l
     */
    public void setLayout(engine.impl.Layout l);
}
