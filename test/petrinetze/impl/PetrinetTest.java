package petrinetze.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import petrinetze.*;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 14.11.2010
 * Time: 15:00:11
 * To change this template use File | Settings | File Templates.
 */

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
        final ISession session = new Session();
        final IPetrinet p = session.createLabeldPetrinet();
        final IPlace p1 = p.createPlace("p1");
        final IPlace p2 = p.createPlace("p2");
        final ITransition t1 = p.createTransition("t1");
        final ITransition t2 = p.createTransition("t2");

        final IArc p1t1 = p.createArc();
        p1t1.setMark(1);
        p1t1.setStart(p1);
        p1t1.setEnd(t1);

        final IArc t1p2 = p.createArc();
        t1p2.setMark(1);
        t1p2.setStart(t1);
        t1p2.setEnd(p2);

        final IArc p2t2 = p.createArc();
        p2t2.setMark(1);
        p2t2.setStart(p2);
        p2t2.setEnd(t2);

        final IArc t2p1 = p.createArc();
        t2p1.setMark(1);
        t2p1.setStart(t2);
        t2p1.setEnd(p1);

        p1.setMark(1);

        p.fire();
        assertEquals(0, p1.getMark());
        assertEquals(1, p2.getMark());

        p.fire();
        assertEquals(1, p1.getMark());
        assertEquals(0, p2.getMark());
    }
}
