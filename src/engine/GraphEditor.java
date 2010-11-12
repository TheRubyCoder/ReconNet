package engine;

import petrinetze.Arc;
import petrinetze.Node;
import petrinetze.Place;
import petrinetze.Transition;

import java.awt.geom.Point2D;
import java.util.Set;

/**
 * Klasse zur Manipulation der Graphendarstellung des Petrinetzes.
 * 
 */
public interface GraphEditor {

    /**
     * Erzeugen einer Stelle an angegebener Position.
     *
     * @param location Position der Stelle
     *
     * @return neu erzeugte Stelle
     */
    Place createPlace(Point2D.Float location);

    /**
     * Erzeugen einer Transition an angegebener Position.
     *
     * @param location Position der Transition
     *
     * @return neu erzeugte Transition
     */
    Transition createTransition(Point2D.Float location);

    /**
     * Erzeugen einer Kante zwischen Stelle und Transition.
     *
     * Falls eine solche Kante bereits exisitiert, so wird diese zurückgegeben
     * ansonsten wird eine neu erzeugte Kante zurückgegeben.
     *
     * @param from Stelle
     * @param to Transition
     *
     * @return Kante zwischen Stelle und Transition
     */
    Arc createIncomingArc(Place from, Transition to);

    /**
     * Erzeugen einer Kante zwischen Transition und Stelle.
     *
     * Falls eine solche Kante bereits exisitiert, so wird diese zurückgegeben
     * ansonsten wird eine neu erzeugte Kante zurückgegeben.
     *
     * @param from Transition
     * @param to Stelle
     *
     * @return Kante zwischen Transition und Stelle 
     */
    Arc createOutgoingArc(Transition from, Place to);

    /**
     * Entfernen der übergegebenen Knoten.
     *
     * @param nodes die zu entfernenden Knoten
     */
    void remove(Set<Node> nodes);
}
