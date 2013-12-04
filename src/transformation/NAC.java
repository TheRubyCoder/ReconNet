package transformation;

import static transformation.dependency.PetrinetAdapter.createPetrinet;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

/**
 * Represents a negative application condition (NAC) of a rule.<p>
 * A NAC consists of two parts: a petrinet (Nac) and an injective morphism L->Nac (n)<br/>
 * For a rule to comply with a NAC on a given morphism g: L->G (i.e. a match) there must be no morphism x: Nac->G
 * with (x after n) == g
 *
 */
public class NAC {

        // Nac part of the NAC
        private Petrinet nac;

        // Mapping part of the NAC
        //TODO BidiMap hat kein HashCode und Equals, geht auch normale Map?
        private final BidiMap<Place, Place>               placeMappingLToNac;
        private final BidiMap<PostArc, PostArc>           postArcMappingLToNac;
        private final BidiMap<PreArc, PreArc>             preArcMappingLToNac;
        private final BidiMap<Transition, Transition> transitionMappingLToNac;

        public NAC() {
                nac = createPetrinet();

                placeMappingLToNac      = new DualHashBidiMap<Place, Place>();
                postArcMappingLToNac    = new DualHashBidiMap<PostArc, PostArc>();
                preArcMappingLToNac     = new DualHashBidiMap<PreArc, PreArc>();
                transitionMappingLToNac = new DualHashBidiMap<Transition, Transition>();
        }

        public Petrinet getNac() {
                return nac;
        }

        /**
         * Returns the corresponding Place in N
         *
         * @param p Place in L
         * @return Place in N
         */
        public Place fromLtoNac(Place p) {
                return placeMappingLToNac.get(p);
        }

        /**
         * Returns the corresponding PreArc in N
         *
         * @param pre PreArc in L
         * @return PreArc in N
         */
        public PreArc fromLtoNac(PreArc pre){
                return preArcMappingLToNac.get(pre);
        }

        /**
         * Returns the corresponding PostArc in N
         *
         * @param post PostArc in L
         * @return PostArc in N
         */
        public PostArc fromLtoNac(PostArc post){
                return postArcMappingLToNac.get(post);
        }

        /**
         * Returns the corresponding Transition in N
         *
         * @param t Transition in L
         * @return Transition in N
         */
        public Transition fromLtoNac(Transition t){
                return transitionMappingLToNac.get(t);
        }
        
        //TODO Protected setters
}