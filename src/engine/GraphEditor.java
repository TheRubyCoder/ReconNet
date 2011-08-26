package engine;

import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.JPanel;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;

// Anders encoding

/**
 * Klasse zur Manipulation der Graphendarstellung des Petrinetzes.
 *
 */
public interface GraphEditor {

    EditMode getEditMode();

    void setEditMode(EditMode mode);

    /**
     * Erzeugen einer Stelle an angegebener Position.
     *
     * @param location Position der Stelle
     *
     * @return neu erzeugte Stelle
     */
    IPlace createPlace(Point2D location);

    /**
     * Erzeugen einer Transition an angegebener Position.
     *
     * @param location Position der Transition
     *
     * @return neu erzeugte Transition
     */
    ITransition createTransition(Point2D location);

    /**
     * Erzeugen einer Kante zwischen Transition und Stelle.
     *
     * Falls es sich nicht um Transition und Stelle handelt
     * wird die Kante nicht erzeugt und es wird null zurück gegeben
     *
     * @param from
     * @param to
     *
     * @return Kante zwischen Transition und Stelle oder null falls vorbindung nicht erfüllt
     */
    IArc createArc(INode from, INode to);

    /**
     * Entfernen der �bergegebenen Knoten.
     *
     * @param nodes die zu entfernenden Knoten
     */
    void remove(Set<? extends INode> nodes);

    /**
     * Gibt das JPanel zum Anzeigen des Graphen zurück
     * @return das JPanel zum Anzeigen des Graphen
     */
    public JPanel getGraphPanel();

    /**
     * Gibt die ausgew�hlten Knoten / Kanten des Graphen zur�ck.
     *
     * @return die ausgew�hlten Knoten / Kanten des Graphen zur�ck.
     */
    Set<INode> getPickedNodes();
}
