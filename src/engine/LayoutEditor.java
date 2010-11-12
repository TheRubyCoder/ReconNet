package engine;

import petrinetze.INode;

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
    void lock(INode node);

    /**
     * Aufhebung der Positionssperre f�r einen Knoten.
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
     * Position der grafischen Repr�sentation des Knoten.
     *
     * @param node
     *
     * @return
     */
    Point2D getPosition(INode node);

    /**
     * Setzen der Position der grafischen Repr�sentation des Knoten.
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
    void apply();
}
