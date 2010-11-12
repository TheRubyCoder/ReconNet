package engine;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;

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
    IPlace createPlace(Point2D.Float location);

    /**
     * Erzeugen einer Transition an angegebener Position.
     *
     * @param location Position der Transition
     *
     * @return neu erzeugte Transition
     */
    ITransition createTransition(Point2D.Float location);

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
    IArc createIncomingArc(IPlace from, ITransition to);

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
    IArc createOutgoingArc(ITransition from, IPlace to);

    /**
     * Entfernen der übergegebenen Knoten.
     *
     * @param nodes die zu entfernenden Knoten
     */
    void remove(Set<INode> nodes);
}
