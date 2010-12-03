package petrinetze.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import petrinetze.*;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 14.11.2010
 * Time: 15:00:11
 * To change this template use File | Settings | File Templates.
 */

public class PetrinetTest {

    private static class TestListener implements PetrinetListener {

        private boolean changed = false;

        INode lastChange = null;

        ActionType lastType = null;

        public void assertChanged() {
            assertTrue(changed);
            changed = false;
        }

        public void assertLastChangeIs(INode change, ActionType type) {
            assertEquals(change, lastChange);
            lastChange = null;
            assertEquals(type, lastType);
            lastChange = null;
        }

        @Override
        public void changed(IPetrinet petrinet, INode element, ActionType actionType) {
            changed = true;
            lastChange = element;
        }

        @Override
        public void changed(IPetrinet petrinet, IArc element, ActionType actionType) {
            changed = true;
            lastChange = element;
        }
    }


    /**
     * Einfaches Netz der Form
     *
     * --> (p1) -> [t1] -> (p2) -> [t2] --
     * \---------------------------------/
     *
     */
    @Test
    public void simpleNet() {
        final IPetrinet p = new Petrinet();

        final TestListener l = new TestListener();
        p.addPetrinetListener(l);

        final IPlace p1 = p.createPlace("p1");

        l.assertChanged();
        l.assertLastChangeIs(p1, ActionType.added);

        final IPlace p2 = p.createPlace("p2");

        l.assertChanged();
        l.assertLastChangeIs(p2, ActionType.added);


        final ITransition t1 = p.createTransition("t1");

        l.assertChanged();
        l.assertLastChangeIs(t1, ActionType.added);

        final ITransition t2 = p.createTransition("t2");

        l.assertChanged();
        l.assertLastChangeIs(t2, ActionType.added);

        final IArc p1t1 = p.createArc();
        p1t1.setMark(1);
        p1t1.setStart(p1);
        p1t1.setEnd(t1);

        l.assertChanged();
        l.assertLastChangeIs(p1t1, ActionType.added);

        final IArc t1p2 = p.createArc();
        t1p2.setMark(1);
        t1p2.setStart(t1);
        t1p2.setEnd(p2);

        l.assertChanged();
        l.assertLastChangeIs(t1p2, ActionType.added);

        final IArc p2t2 = p.createArc();
        p2t2.setMark(1);
        p2t2.setStart(p2);
        p2t2.setEnd(t2);

        l.assertChanged();
        l.assertLastChangeIs(p2t2, ActionType.added);

        final IArc t2p1 = p.createArc();
        t2p1.setMark(1);
        t2p1.setStart(t2);
        t2p1.setEnd(p1);

        l.assertChanged();
        l.assertLastChangeIs(t2p1, ActionType.added);

        final Set<IPlace> places = p.getAllPlaces();

        assertTrue(places.contains(p1));
        assertTrue(places.contains(p2));

        final Set<ITransition> transitions = p.getAllTransitions();

        assertTrue(transitions.contains(t1));
        assertTrue(transitions.contains(t2));

        final Set<IArc> arcs = p.getAllArcs();

        assertTrue(arcs.contains(p1t1));
        assertTrue(arcs.contains(t1p2));
        assertTrue(arcs.contains(p2t2));
        assertTrue(arcs.contains(t2p1));

        p1.setMark(1);

        // TODO Vor- und Nachbereich prüfen:
        // post(t1) = t2
        // pre(t2) = t1


        p.fire();
        assertEquals(0, p1.getMark());
        assertEquals(1, p2.getMark());

        // TODO Vor- und Nachbereich prüfen
        // post(t1) = t2
        // pre(t2) = t1

        p.fire();
        assertEquals(1, p1.getMark());
        assertEquals(0, p2.getMark());

        // TODO Vor- und Nachbereich prüfen

        p.fire(t1p2.getId());

        assertEquals(0, p1.getMark());
        assertEquals(1, p2.getMark());

        // TODO Vor- und Nachbereich prüfen

        // TODO soll das Petrinetz hier eine Exception auslösen oder sollten die Kanten mitgelöscht werden?
        p.deletePlaceById(p2.getId());
        assertFalse(p.getAllPlaces().contains(p2));
        l.assertChanged();
        l.assertLastChangeIs(p2, ActionType.deleted);

        p.deletePlaceById(p1.getId());
        assertFalse(p.getAllPlaces().contains(p1));
        l.assertChanged();
        l.assertLastChangeIs(p2, ActionType.deleted);


    }
}
