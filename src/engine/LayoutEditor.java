package engine;

import petrinetze.Node;

import java.awt.geom.Point2D;

/**
 * Klasse zum Bearbeiten des Layouts.
 *
 */
public interface LayoutEditor {

    /**
     * Sperren der Position eines Knoten.
     * @param node
     */
    void lock(Node node);

    /**
     * Aufhebung der Positionssperre für einen Knoten.
     *
     * @param node
     */
    void unlockNode(Node node);

    /**
     * Abfragen des Sperrzustandes eines Knoten
     *
     * @param node
     * @return
     */
    boolean isLocked(Node node);

    /**
     * Position der grafischen Repräsentation des Knoten.
     *
     * @param node
     *
     * @return
     */
    Point2D getPosition(Node node);

    /**
     * Setzen der Position der grafischen Repräsentation des Knoten.
     *
     * @param node
     * @param location
     */
    void setPosition(Node node, Point2D location);

    /**
     * Alle gesperrten Knoten entsperren.
     */
    void unlockAll();

    /**
     * Anwenden des LayoutEditor.
     */
    void apply();
}
