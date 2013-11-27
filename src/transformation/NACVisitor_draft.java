package transformation;

import java.util.HashMap;
import java.util.Map;

import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.matcher.PNVF2;
import transformation.matcher.PNVF2.MatchException;
import transformation.matcher.PNVF2.MatchVisitor;

public class NACVisitor_draft implements MatchVisitor {

        /** The rule whose applicability for a particular match is to be checked */
        private Rule rule = null;

        /** The match to check the NACs against */
        private Match match = null;

        /**
         * Checks if the negative application conditions (NACs) are fulfilled <p>
         * If the given rule doesn't contain NACs, this acts like a trivial "accept first" visitor
         *
         * @param rule  The rule whose applicability for a particular match is to be checked
         */
        NACVisitor_draft(Rule rule) {
                this.rule       = rule;
        }

        @Override
        public boolean visit(Match match) {

                this.match = match;

                // catch trivial case (rule has no NACs)
                if (rule.getNACs() == null)
                        return true;

                // Check each NAC. Abort loop and return false if the first applicable morphism one is found
            for (NAC nac : rule.getNACs()){

                Match partialMatch      = createPartialMatch(nac);

                // try to find a complete match Nac->G(target) based on the partial match
                PNVF2 vf2 = PNVF2.getInstance(partialMatch.getSource(), match.getTarget());

                // TODO isStrictMatch sollte von irgendwo uebernommen werden??
                try {
                                        vf2.getMatch(false, partialMatch);
                                } catch (MatchException e) {
                                        continue;
                                }

                // if a match has been found the rule is not applicable, thus:
                return false;
            }

            // If no match has been found for any NAC we can confidently:
            return true;
        }


        /**
         * @brief Checks if the negative application conditions (NACs) are fulfilled
         * If the given rule doesn't contain NACs, this acts like a trivial "accept first" visitor
         *
         * @param nac                   NAC to be checked for
         * @return partialMatch Mapping from Nac to the target net, following the given Match
         */
        private Match createPartialMatch(NAC nac) {
                Map<Place, Place>           places      = new HashMap<Place,Place>();
                Map<Transition, Transition> transitions = new HashMap<Transition,Transition>();
                Map<PreArc, PreArc>             preArcs         = new HashMap<PreArc,PreArc>();
                Map<PostArc, PostArc>           postArcs        = new HashMap<PostArc,PostArc>();

                // for each element in L (rule) obtain the mapped elements in N and target and successively create a new mapping N->target
                // using each pair of obtained elements
                for (Place p: rule.getL().getPlaces()) {
                        places.put(nac.fromLtoNac(p), match.getPlaces().get(p));
                }

                for (Transition t: rule.getL().getTransitions()) {
                        transitions.put(nac.fromLtoNac(t), match.getTransitions().get(t));
                }

                for (PreArc preA: rule.getL().getPreArcs()) {
                        preArcs.put(nac.fromLtoNac(preA), match.getPreArcs().get(preA));
                }

                for (PostArc postA: rule.getL().getPostArcs()) {
                        postArcs.put(nac.fromLtoNac(postA), match.getPostArcs().get(postA));
                }

                return new Match(nac.getNac(), this.match.getTarget(), places, transitions, preArcs, postArcs);
        }
}
