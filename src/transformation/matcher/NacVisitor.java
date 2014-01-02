package transformation.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.map.HashedMap;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Match;
import transformation.NAC;
import transformation.Rule;
import transformation.matcher.PNVF2.MatchException;
import transformation.matcher.PNVF2.MatchVisitor;

public class NacVisitor implements MatchVisitor {
	private Map<Match, Integer> matchesCounts = new HashMap<Match, Integer>();
	private Set<NAC> nacs;
	private Petrinet n;
	private Petrinet l;
	private Match match;

	public NacVisitor(Rule rule) {
		this.nacs = rule.getNACs();
		this.l = rule.getL();
	}

	public Map<Match, Integer> getMatchesCounts() {
		return matchesCounts;
	}

	public boolean visit(Match matchLinN) {
		n = matchLinN.getTarget();
		this.match = matchLinN;

		Integer count = matchesCounts.get(matchLinN);
		if (count != null) {
			matchesCounts.put(matchLinN, count + 1);
		} else {
			matchesCounts.put(matchLinN, 1);
		}

		for (NAC nac : nacs) {
			Match matchPartialNacInN = createPartialMatchNacInN(nac);
			try {
				Match matchNacInN = PNVF2.getInstance(nac.getNac(), n)
						.getMatch(false, matchPartialNacInN);
			} catch (MatchException e) {
				continue;
			}
			return false;
		}
		return true;
	}

	private Match createPartialMatchNacInN(NAC nac) {

		Map<Place, Place> places = new HashMap<Place, Place>();
		Map<Transition, Transition> transitions = new HashMap<Transition, Transition>();
		Map<PreArc, PreArc> preArcs = new HashMap<PreArc, PreArc>();
		Map<PostArc, PostArc> postArcs = new HashMap<PostArc, PostArc>();

		for (Place p : l.getPlaces()) {
			places.put(nac.fromLtoNac(p), match.getPlace(p));
		}

		for (Transition t : l.getTransitions()) {
			transitions.put(nac.fromLtoNac(t), match.getTransition(t));
		}

		for (PreArc preA : l.getPreArcs()) {
			preArcs.put(nac.fromLtoNac(preA), match.getPreArc(preA));
		}

		for (PostArc postA : l.getPostArcs()) {
			postArcs.put(nac.fromLtoNac(postA), match.getPostArc(postA));
		}
		
		return new Match(nac.getNac(), n, places, transitions, preArcs, postArcs);
	}
}
