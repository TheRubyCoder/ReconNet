package engine;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 09.01.11
 * Time: 21:01
 * To change this template use File | Settings | File Templates.
 */
public enum EditMode {

    /** Auswählen v. Stellen / Transitionen */
    PICK,

    ADD_TRANSITION,

    ADD_PLACE,

    /** Verschieben des Graphen. */
    TRANSLATE;
}
