/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

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
