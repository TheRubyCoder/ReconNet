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

    private static class TestListener implements IPetrinetListener {

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
            lastType = actionType;
        }

        @Override
        public void changed(IPetrinet petrinet, IArc element, ActionType actionType) {
            changed = true;
            lastChange = element;
            lastType = actionType;
        }

        public void assertUnchanged() {
            assertNull(lastChange);
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

        final IArc p1t1 = p.createArc("p1t1", p1, t1);

        l.assertChanged();
        l.assertLastChangeIs(p1t1, ActionType.added);

        p1t1.setMark(1);

        l.assertChanged();
        l.assertLastChangeIs(p1t1, ActionType.changed);

        final IArc t1p2 = p.createArc("t1p2", t1, p2);

        l.assertChanged();
        l.assertLastChangeIs(t1p2, ActionType.added);

        t1p2.setMark(1);

        final IArc p2t2 = p.createArc("p2t2", p2, t2);

        l.assertChanged();
        l.assertLastChangeIs(p2t2, ActionType.added);

        p2t2.setMark(1);

        final IArc t2p1 = p.createArc("t2p1", t2, p1);
        l.assertChanged();
        l.assertLastChangeIs(t2p1, ActionType.added);

        t2p1.setMark(1);

        l.assertChanged();
        l.assertLastChangeIs(t2p1, ActionType.changed);

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

        p.fire();
        assertEquals(0, p1.getMark());
        assertEquals(1, p2.getMark());

        p.fire();
        assertEquals(1, p1.getMark());
        assertEquals(0, p2.getMark());
        assertTrue(t1.isActivated());

        p.fire(t1.getId());

        assertEquals(0, p1.getMark());
        assertEquals(1, p2.getMark());
        assertTrue(t2.isActivated());

        p.deletePlaceById(p2.getId());
        assertFalse(p.getAllPlaces().contains(p2));
        l.assertChanged();
        l.assertLastChangeIs(p2, ActionType.deleted);

        p.deletePlaceById(p1.getId());
        assertFalse(p.getAllPlaces().contains(p1));
        l.assertChanged();
        l.assertLastChangeIs(p1, ActionType.deleted);

        p.removePetrinetListener(l);
        p.createPlace("test");
        l.assertUnchanged();
    }

    @Test
    public void activation() {
        IPetrinet net = new Petrinet();

        final IPlace p1 = net.createPlace("p1");
        final IPlace p2 = net.createPlace("p2");

        final ITransition t1 = net.createTransition("t1");

        assertTrue(
            "A transition with empty pre must be activated",
            t1.isActivated()
        );

        final IArc a = net.createArc("a", p1, t1);
        assertFalse(
            "A transition with an empty place in pre and an edge-weight of 1 must be activated",
            t1.isActivated()
        );

        final IArc b = net.createArc("b", p2, t1);
        assertFalse(
            "A transition with two empty places in pre and an edge-weight of 1 must be activated",
            t1.isActivated()
        );

        p1.setMark(1);
        assertFalse(
            "A transition with one empty place and one containing one token in pre and an edge-weight of 1 " +
            "must be activated",
            t1.isActivated()
        );

        p2.setMark(1);
        assertTrue(
            "A transition having a pre of two places with one token each and arcs with weight of one to them " +
            "must be activated",
            t1.isActivated()
        );

        a.setMark(2);
        assertFalse(
            "A transition must not be activated if at least one condition of pre is not fulfilled",
            t1.isActivated()
        );

        p1.setMark(2);
        assertTrue(t1.isActivated());

        b.setMark(2);
        assertFalse(t1.isActivated());

        p2.setMark(2);
        assertTrue(t1.isActivated());
    }
}
