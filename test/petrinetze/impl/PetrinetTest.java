package petrinetze.impl;

import org.junit.Test;
import static org.junit.Assert.*;

import petrinet.*;
import petrinet.model.IArc;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;

import java.util.Set;

public class PetrinetTest {


    /**
     * Einfaches Netz der Form
     *
     * --> (p1) -> [t1] -> (p2) -> [t2] --
     * \---------------------------------/
     *
     */
    @Test
    public void simpleNet() {
        final Petrinet p = PetrinetComponent.getPetrinet().createPetrinet();


        final Place p1 = p.addPlace("p1");
        final Place p2 = p.addPlace("p2");

        final Transition t1 = p.addTransition("t1");
        final Transition t2 = p.addTransition("t2");


        final IArc p1t1 = p.addPreArc("p1t1", p1, t1);
        p1t1.setWeight(1);
        final IArc t1p2 = p.addPostArc("t1p2", t1, p2);
        t1p2.setWeight(1);
        final IArc p2t2 = p.addPreArc("p2t2", p2, t2);
        p2t2.setWeight(1);
        final IArc t2p1 = p.addPostArc("t2p1", t2, p1);
        t2p1.setWeight(1);

        final Set<Place> places = p.getPlaces();

        assertTrue(places.contains(p1));
        assertTrue(places.contains(p2));

        final Set<Transition> transitions = p.getTransitions();

        assertTrue(transitions.contains(t1));
        assertTrue(transitions.contains(t2));

        final Set<IArc> arcs = p.getArcs();

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

        p.removePlace(p2.getId());
        assertFalse(p.getPlaces().contains(p2));

        p.removePlace(p1.getId());
        assertFalse(p.getPlaces().contains(p1));

        p.addPlace("test");
    }

    @Test
    public void activation() {
        Petrinet net = PetrinetComponent.getPetrinet().createPetrinet();

        final Place p1 = net.addPlace("p1");
        final Place p2 = net.addPlace("p2");

        final Transition t1 = net.addTransition("t1");

        assertTrue(
            "A transition with empty pre must be activated",
            t1.isActivated()
        );

        final IArc a = net.addPreArc("a", p1, t1);
        assertFalse(
            "A transition with an empty place in pre and an edge-weight of 1 must be activated",
            t1.isActivated()
        );

        final IArc b = net.addPreArc("b", p2, t1);
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

        a.setWeight(2);
        assertFalse(
            "A transition must not be activated if at least one condition of pre is not fulfilled",
            t1.isActivated()
        );

        p1.setMark(2);
        assertTrue(t1.isActivated());

        b.setWeight(2);
        assertFalse(t1.isActivated());

        p2.setMark(2);
        assertTrue(t1.isActivated());
    }
}
