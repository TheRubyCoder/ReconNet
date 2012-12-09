package transformation.matcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Match;

public final class VF2 {
	private final MatchVisitor ACCEPT_FIRST_MATCH_VISITOR = new AcceptFirstMatchVisitor();
	private final Random RANDOM;
	private final int    CORE_NULL_NODE     = Integer.MAX_VALUE;
	private final int    SET_NULL_VALUE     = 0;

	private final int    INDEX_TERMINAL_IN  = 0;
	private final int    INDEX_TERMINAL_OUT = 1;
	private final int    INDEX_NEW_PAIR     = 2;

	private final int    INDEX_SOURCE_CANDIDATE = 0;
	private final int    INDEX_TARGET_CANDIDATE = 1;

	private final byte   NEXT_CANDIDATE_TERMINAL_OUT = 0;
	private final byte   NEXT_CANDIDATE_TERMINAL_IN  = 1;
	private final byte   NEXT_CANDIDATE_NEW          = 2;

	private final int[]  CORE_NULL_NODE_CANDIDATE = {CORE_NULL_NODE, CORE_NULL_NODE};

	private final Petrinet source;
	private final Petrinet target;

	private boolean[][]  semanticEqualPlaces;
	private boolean[][]  semanticEqualTransitions;

	private Place[]      sourcePlaces;
	private Place[]      targetPlaces;
	private Transition[] sourceTransitions;
	private Transition[] targetTransitions;

	private Map<Place, Integer>      sourcePlacesIndexes;
	private Map<Place, Integer>      targetPlacesIndexes;
	private Map<Transition, Integer> sourceTransitionsIndexes;
	private Map<Transition, Integer> targetTransitionsIndexes;

	private int coreNodesCount;

	private int[] coreSourcePlaces;
	private int[] coreSourceTransitions;
	private int[] coreTargetPlaces;
	private int[] coreTargetTransitions;
	private int coreSourcePlacesCount;
	private int coreSourceTransitionsCount;
	private int coreTargetPlacesCount;
	private int coreTargetTransitionsCount;

	private int[] inSourcePlaces;
	private int[] inSourceTransitions;
	private int[] inTargetPlaces;
	private int[] inTargetTransitions;
	private int inSourcePlacesCount;
	private int inSourceTransitionsCount;
	private int inTargetPlacesCount;
	private int inTargetTransitionsCount;

	private int[] outSourcePlaces;
	private int[] outSourceTransitions;
	private int[] outTargetPlaces;
	private int[] outTargetTransitions;
	private int outSourcePlacesCount;
	private int outSourceTransitionsCount;
	private int outTargetPlacesCount;
	private int outTargetTransitionsCount;

	private Match 		 lastMatch;
	private MatchVisitor matchVisitor;

	private VF2(Petrinet source, Petrinet target, Random random) {
		this.source = source;
		this.target = target;
		this.RANDOM = random;
	}

	public static VF2 getInstance(Petrinet source, Petrinet target) {
		return  getInstance(source, target, new Random());
	}

	public static VF2 getInstance(Petrinet source, Petrinet target, Random random) {
		if (source == null || target == null || random == null) {
			throw new IllegalArgumentException();
		}

		return new VF2(source, target, random);
	}

	private void initialize(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlaces) throws MatchException {
		int sourcePlacesCount      = source.getPlaces().size();
		int sourceTransitionsCount = source.getTransitions().size();
		int targetPlacesCount      = target.getPlaces().size();
		int targetTransitionsCount = target.getTransitions().size();

		if (sourcePlacesCount > targetPlacesCount || sourceTransitionsCount > targetTransitionsCount) {
			throw new MatchException();
		}

		semanticEqualPlaces        = new boolean[sourcePlacesCount][targetPlacesCount];
		semanticEqualTransitions   = new boolean[sourceTransitionsCount][targetTransitionsCount];

		sourcePlaces               = new Place[sourcePlacesCount];
		sourceTransitions          = new Transition[sourceTransitionsCount];
		targetPlaces               = new Place[targetPlacesCount];
		targetTransitions          = new Transition[targetTransitionsCount];

		sourcePlacesIndexes        = new HashMap<Place, Integer>();
		sourceTransitionsIndexes   = new HashMap<Transition, Integer>();
		targetPlacesIndexes        = new HashMap<Place, Integer>();
		targetTransitionsIndexes   = new HashMap<Transition, Integer>();

		coreNodesCount             = 0;

		coreSourcePlaces 	  	   = new int[sourcePlacesCount];
		coreSourceTransitions 	   = new int[sourceTransitionsCount];
		coreTargetPlaces      	   = new int[targetPlacesCount];
		coreTargetTransitions 	   = new int[targetTransitionsCount];
		coreSourcePlacesCount 	   = 0;
		coreSourceTransitionsCount = 0;
		coreTargetPlacesCount 	   = 0;
		coreTargetTransitionsCount = 0;

		inSourcePlaces 		  	   = new int[sourcePlacesCount];
		inSourceTransitions   	   = new int[sourceTransitionsCount];
		inTargetPlaces 		  	   = new int[targetPlacesCount];
		inTargetTransitions   	   = new int[targetTransitionsCount];
		inSourcePlacesCount 	   = 0;
		inSourceTransitionsCount   = 0;
		inTargetPlacesCount 	   = 0;
		inTargetTransitionsCount   = 0;

		outSourcePlaces 	  	   = new int[sourcePlacesCount];
		outSourceTransitions  	   = new int[sourceTransitionsCount];
		outTargetPlaces       	   = new int[targetPlacesCount];
		outTargetTransitions  	   = new int[targetTransitionsCount];
		outSourcePlacesCount 	   = 0;
		outSourceTransitionsCount  = 0;
		outTargetPlacesCount 	   = 0;
		outTargetTransitionsCount  = 0;

		lastMatch 				   = null;
		matchVisitor               = null;

		initPlacesArray(sourcePlaces, source);
		initTransitionsArray(sourceTransitions, source);
		initPlacesArray(targetPlaces, target);
		initTransitionsArray(targetTransitions, target);

		shuffle(sourcePlaces);
		shuffle(sourceTransitions);
		shuffle(targetPlaces);
		shuffle(targetTransitions);

		initMap(sourcePlacesIndexes, sourcePlaces);
		initMap(sourceTransitionsIndexes, sourceTransitions);
		initMap(targetPlacesIndexes, targetPlaces);
		initMap(targetTransitionsIndexes, targetTransitions);

		Arrays.fill(coreSourcePlaces, CORE_NULL_NODE);
		Arrays.fill(coreSourceTransitions, CORE_NULL_NODE);
		Arrays.fill(coreTargetPlaces, CORE_NULL_NODE);
		Arrays.fill(coreTargetTransitions, CORE_NULL_NODE);

		if (!generateSemanticPlaces(isStrictMatch, arcRestrictedSourcePlaces) || !generateSemanticTransitions()) {
			throw new MatchException();
		}
	}

	public Match getMatch(boolean isStrictMatch) throws MatchException {
		return getMatch(isStrictMatch, new HashSet<Place>(), ACCEPT_FIRST_MATCH_VISITOR);
	}

	public Match getMatch(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlaces) throws MatchException {
		return getMatch(isStrictMatch, arcRestrictedSourcePlaces, ACCEPT_FIRST_MATCH_VISITOR);
	}

	public Match getMatch(boolean isStrictMatch, MatchVisitor visitor) throws MatchException {
		return getMatch(isStrictMatch, new HashSet<Place>(), visitor);
	}

	public Match getMatch(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlaces, MatchVisitor visitor) throws MatchException {
		if (arcRestrictedSourcePlaces == null || visitor == null) {
			throw new MatchException();
		}

		initialize(isStrictMatch, arcRestrictedSourcePlaces);
		matchVisitor = visitor;

		if (!match()) {
			throw new MatchException();
		}

		return lastMatch;
	}

	public Match getMatch(boolean isStrictMatch, Match partialMatch) throws MatchException {
		return getMatch(isStrictMatch, partialMatch, new HashSet<Place>(), ACCEPT_FIRST_MATCH_VISITOR);
	}

	public Match getMatch(boolean isStrictMatch, Match partialMatch, Set<Place> arcRestrictedSourcePlaces) throws MatchException {
		return getMatch(isStrictMatch, partialMatch, arcRestrictedSourcePlaces, ACCEPT_FIRST_MATCH_VISITOR);
	}

	public Match getMatch(boolean isStrictMatch, Match partialMatch, MatchVisitor visitor) throws MatchException {
		return getMatch(isStrictMatch, partialMatch, new HashSet<Place>(), ACCEPT_FIRST_MATCH_VISITOR);
	}

	public Match getMatch(boolean isStrictMatch, Match partialMatch, Set<Place> arcRestrictedSourcePlaces, MatchVisitor visitor) throws MatchException {
		if (partialMatch == null || arcRestrictedSourcePlaces == null || visitor == null) {
			throw new MatchException();
		}

		initialize(isStrictMatch, arcRestrictedSourcePlaces);
		matchVisitor = visitor;

		for (Map.Entry<Place, Place> mapping : partialMatch.getPlaces().entrySet()) {
			Integer sourceIndex = sourcePlacesIndexes.get(mapping.getKey());
			Integer targetIndex = targetPlacesIndexes.get(mapping.getValue());

			if (sourceIndex == null || targetIndex == null || !isPlaceFeasible(sourceIndex, targetIndex)) {
				throw new MatchException();
			}

			addPlacePair(sourceIndex, targetIndex);
		}

		for (Map.Entry<Transition, Transition> mapping : partialMatch.getTransitions().entrySet()) {
			Integer sourceIndex = sourceTransitionsIndexes.get(mapping.getKey());
			Integer targetIndex = targetTransitionsIndexes.get(mapping.getValue());

			if (sourceIndex == null || targetIndex == null || !isTransitionFeasible(sourceIndex, targetIndex)) {
				throw new MatchException();
			}

			addTransitionPair(sourceIndex, targetIndex);
		}

		assert partialMatch.getPlaces().size()      == coreSourcePlacesCount;
		assert partialMatch.getTransitions().size() == coreSourceTransitionsCount;
		assert partialMatch.getPlaces().size()      == coreTargetPlacesCount;
		assert partialMatch.getTransitions().size() == coreTargetTransitionsCount;

		if (!match()) {
			throw new MatchException();
		}

		return lastMatch;
	}

	private boolean match() {
		assert assertValidState();

		if (coreSourcePlacesCount      == coreSourcePlaces.length
		 && coreSourceTransitionsCount == coreSourceTransitions.length) {
			lastMatch = getBuildMatch();

			return matchVisitor.visit(lastMatch);
		}

		assert coreNodesCount != coreSourcePlaces.length + coreSourceTransitions.length;

		if (hasNextOutCandidatePairs()) {
			if (isMatchByPlacePairs(getNextOutPlaceCandidatePairsCount(), getNextOutTransitionCandidatePairsCount())) {
				return matchByPlace(NEXT_CANDIDATE_TERMINAL_OUT);
			} else {
				return matchByTransition(NEXT_CANDIDATE_TERMINAL_OUT);
			}

		} else if (hasNextInCandidatePairs()) {
			if (isMatchByPlacePairs(getNextInPlaceCandidatePairsCount(), getNextInTransitionCandidatePairsCount())) {
				return matchByPlace(NEXT_CANDIDATE_TERMINAL_IN);
			} else {
				return matchByTransition(NEXT_CANDIDATE_TERMINAL_IN);
			}

		} else {
			if (getNextPlaceCandidatePairsCount() > getNextTransitionCandidatePairsCount()
			|| (getNextPlaceCandidatePairsCount() == getNextTransitionCandidatePairsCount() && RANDOM.nextInt(2) == 0)) {
				return matchByPlace(NEXT_CANDIDATE_NEW);
			} else {
				return matchByTransition(NEXT_CANDIDATE_NEW);
			}
		}
	}

	private boolean isMatchByPlacePairs(int placePairsCount, int transitionPairsCount) {
		return placePairsCount > 0 && (transitionPairsCount == 0 
            || RANDOM.nextInt(placePairsCount + transitionPairsCount) < placePairsCount);
	}

	private boolean matchByPlace(byte nextCandidateSet) {
		int[] lastPair    = getNextPlaceCandidatePair(CORE_NULL_NODE_CANDIDATE, nextCandidateSet);
		int   sourceIndex = lastPair[INDEX_SOURCE_CANDIDATE];
		int   targetIndex = lastPair[INDEX_TARGET_CANDIDATE];

		while (sourceIndex != CORE_NULL_NODE && targetIndex != CORE_NULL_NODE) {
			if (isPlaceFeasible(sourceIndex, targetIndex)) {
				addPlacePair(sourceIndex, targetIndex);

				if (match()) {
					return true;
				}

				backtrackPlacePair(sourceIndex, targetIndex);
			}

			lastPair    = getNextPlaceCandidatePair(lastPair, nextCandidateSet);
			sourceIndex = lastPair[INDEX_SOURCE_CANDIDATE];
			targetIndex = lastPair[INDEX_TARGET_CANDIDATE];
		}

		return false;
	}

	private boolean matchByTransition(byte nextCandidateSet) {
		int[] lastPair    = getNextTransitionCandidatePair(CORE_NULL_NODE_CANDIDATE, nextCandidateSet);
		int   sourceIndex = lastPair[INDEX_SOURCE_CANDIDATE];
		int   targetIndex = lastPair[INDEX_TARGET_CANDIDATE];

		while (sourceIndex != CORE_NULL_NODE && targetIndex != CORE_NULL_NODE) {
			if (isTransitionFeasible(sourceIndex, targetIndex)) {
				addTransitionPair(sourceIndex, targetIndex);

				if (match()) {
					return true;
				}

				backtrackTransitionPair(sourceIndex, targetIndex);
			}

			lastPair    = getNextTransitionCandidatePair(lastPair, nextCandidateSet);
			sourceIndex = lastPair[INDEX_SOURCE_CANDIDATE];
			targetIndex = lastPair[INDEX_TARGET_CANDIDATE];
		}

		return false;
	}

	private int[] getNextPlaceCandidatePair(int[] previousCancidatePair, byte nextCandidateSet) {
		assert nextCandidateSet == NEXT_CANDIDATE_TERMINAL_OUT
			|| nextCandidateSet == NEXT_CANDIDATE_TERMINAL_IN
			|| nextCandidateSet == NEXT_CANDIDATE_NEW;

		assert previousCancidatePair.length == 2;

		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		if (previousSourceIndex != CORE_NULL_NODE && previousTargetIndex >= coreTargetPlaces.length - 1) {
			return CORE_NULL_NODE_CANDIDATE;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = sourceIndex == CORE_NULL_NODE ? 0 : previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_OUT) {
				sourceIndex = getNextUnmatchedNodeIndex(coreSourcePlaces, outSourcePlaces, 0);
			} else if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_IN) {
				sourceIndex = getNextUnmatchedNodeIndex(coreSourcePlaces, inSourcePlaces, 0);
			} else if (sourceIndex == CORE_NULL_NODE) {
				sourceIndex = getNextUnmatchedNodeIndex(coreSourcePlaces, 0);
			}
		}

		if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_OUT) {
			targetIndex = getNextUnmatchedNodeIndex(coreTargetPlaces, outTargetPlaces, targetIndex);
		} else if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_IN) {
			targetIndex = getNextUnmatchedNodeIndex(coreTargetPlaces, inTargetPlaces, targetIndex);
		} else {
			targetIndex = getNextUnmatchedNodeIndex(coreTargetPlaces, targetIndex);
		}

		if (sourceIndex == CORE_NULL_NODE || targetIndex == CORE_NULL_NODE) {
			return CORE_NULL_NODE_CANDIDATE;
		}

		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		return new int[] {sourceIndex, targetIndex};
	}

	private int[] getNextTransitionCandidatePair(int[] previousCancidatePair, byte nextCandidateSet) {
		assert nextCandidateSet == NEXT_CANDIDATE_TERMINAL_OUT
			|| nextCandidateSet == NEXT_CANDIDATE_TERMINAL_IN
			|| nextCandidateSet == NEXT_CANDIDATE_NEW;

		assert previousCancidatePair.length == 2;

		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		if (previousSourceIndex != CORE_NULL_NODE && previousTargetIndex >= coreTargetTransitions.length - 1) {
			return CORE_NULL_NODE_CANDIDATE;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = sourceIndex == CORE_NULL_NODE ? 0 : previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_OUT) {
				sourceIndex = getNextUnmatchedNodeIndex(coreSourceTransitions, outSourceTransitions, 0);
			} else if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_IN) {
				sourceIndex = getNextUnmatchedNodeIndex(coreSourceTransitions, inSourceTransitions, 0);
			} else if (sourceIndex == CORE_NULL_NODE) {
				sourceIndex = getNextUnmatchedNodeIndex(coreSourceTransitions, 0);
			}
		}

		if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_OUT) {
			targetIndex = getNextUnmatchedNodeIndex(coreTargetTransitions, outTargetTransitions, targetIndex);
		} else if (nextCandidateSet == NEXT_CANDIDATE_TERMINAL_IN) {
			targetIndex = getNextUnmatchedNodeIndex(coreTargetTransitions, inTargetTransitions, targetIndex);
		} else {
			targetIndex = getNextUnmatchedNodeIndex(coreTargetTransitions, targetIndex);
		}

		if (sourceIndex == CORE_NULL_NODE || targetIndex == CORE_NULL_NODE) {
			return CORE_NULL_NODE_CANDIDATE;
		}

		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		return new int[] {sourceIndex, targetIndex};
	}

	private void addTransitionPair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		coreSourceTransitionsCount++;
		coreTargetTransitionsCount++;
		coreNodesCount++;


		if (inSourceTransitions[sourceIndex] == SET_NULL_VALUE) {
			inSourceTransitions[sourceIndex] = coreNodesCount;
			inSourceTransitionsCount++;
		}


		if (outSourceTransitions[sourceIndex] == SET_NULL_VALUE) {
			outSourceTransitions[sourceIndex] = coreNodesCount;
			outSourceTransitionsCount++;
		}

		if (inTargetTransitions[targetIndex] == SET_NULL_VALUE) {
			inTargetTransitions[targetIndex] = coreNodesCount;
			inTargetTransitionsCount++;
		}

		if (outTargetTransitions[targetIndex] == SET_NULL_VALUE) {
			outTargetTransitions[targetIndex] = coreNodesCount;
			outTargetTransitionsCount++;
		}

		coreSourceTransitions[sourceIndex] = targetIndex;
		coreTargetTransitions[targetIndex] = sourceIndex;


		Transition source = sourceTransitions[sourceIndex];
		Transition target = targetTransitions[targetIndex];

		for (PreArc arc : source.getIncomingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (inSourcePlaces[placeIndex] == SET_NULL_VALUE) {
				inSourcePlaces[placeIndex] = coreNodesCount;
				inSourcePlacesCount++;
			}
		}

		for (PostArc arc : source.getOutgoingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (outSourcePlaces[placeIndex] == SET_NULL_VALUE) {
				outSourcePlaces[placeIndex] = coreNodesCount;
				outSourcePlacesCount++;
			}
		}

		for (PreArc arc : target.getIncomingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (inTargetPlaces[placeIndex] == SET_NULL_VALUE) {
				inTargetPlaces[placeIndex] = coreNodesCount;
				inTargetPlacesCount++;
			}
		}

		for (PostArc arc : target.getOutgoingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (outTargetPlaces[placeIndex] == SET_NULL_VALUE) {
				outTargetPlaces[placeIndex] = coreNodesCount;
				outTargetPlacesCount++;
			}
		}

		assert assertValidState();
	}

	private void backtrackTransitionPair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		Transition source = sourceTransitions[sourceIndex];
		Transition target = targetTransitions[targetIndex];

		if (inSourceTransitions[sourceIndex] == coreNodesCount) {
			inSourceTransitions[sourceIndex] = SET_NULL_VALUE;
			inSourceTransitionsCount--;

		}

		if (outSourceTransitions[sourceIndex] == coreNodesCount) {
			outSourceTransitions[sourceIndex] = SET_NULL_VALUE;
			outSourceTransitionsCount--;
		}

		if (inTargetTransitions[targetIndex] == coreNodesCount) {
			inTargetTransitions[targetIndex] = SET_NULL_VALUE;
			inTargetTransitionsCount--;
		}

		if (outTargetTransitions[targetIndex] == coreNodesCount) {
			outTargetTransitions[targetIndex] = SET_NULL_VALUE;
			outTargetTransitionsCount--;
		}

		for (PreArc arc : source.getIncomingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (inSourcePlaces[placeIndex] == coreNodesCount) {
				inSourcePlaces[placeIndex] = SET_NULL_VALUE;
				inSourcePlacesCount--;
			}
		}

		for (PostArc arc : source.getOutgoingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (outSourcePlaces[placeIndex] == coreNodesCount) {
				outSourcePlaces[placeIndex] = SET_NULL_VALUE;
				outSourcePlacesCount--;
			}
		}

		for (PreArc arc : target.getIncomingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (inTargetPlaces[placeIndex] == coreNodesCount) {
				inTargetPlaces[placeIndex] = SET_NULL_VALUE;
				inTargetPlacesCount--;
			}
		}

		for (PostArc arc : target.getOutgoingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (outTargetPlaces[placeIndex] == coreNodesCount) {
				outTargetPlaces[placeIndex] = SET_NULL_VALUE;
				outTargetPlacesCount--;
			}
		}

		coreSourceTransitions[sourceIndex] = CORE_NULL_NODE;
		coreTargetTransitions[targetIndex] = CORE_NULL_NODE;

		coreSourceTransitionsCount--;
		coreTargetTransitionsCount--;
		coreNodesCount--;

		assert assertValidState();
	}

	private void addPlacePair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		coreSourcePlacesCount++;
		coreTargetPlacesCount++;
		coreNodesCount++;

		if (inSourcePlaces[sourceIndex] == SET_NULL_VALUE) {
			inSourcePlaces[sourceIndex] = coreNodesCount;
			inSourcePlacesCount++;
		}


		if (outSourcePlaces[sourceIndex] == SET_NULL_VALUE) {
			outSourcePlaces[sourceIndex] = coreNodesCount;
			outSourcePlacesCount++;
		}

		if (inTargetPlaces[targetIndex] == SET_NULL_VALUE) {
			inTargetPlaces[targetIndex] = coreNodesCount;
			inTargetPlacesCount++;
		}

		if (outTargetPlaces[targetIndex] == SET_NULL_VALUE) {
			outTargetPlaces[targetIndex] = coreNodesCount;
			outTargetPlacesCount++;
		}

		coreSourcePlaces[sourceIndex] = targetIndex;
		coreTargetPlaces[targetIndex] = sourceIndex;

		Place source = sourcePlaces[sourceIndex];
		Place target = targetPlaces[targetIndex];

		for (PostArc arc : source.getIncomingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (inSourceTransitions[transitionIndex] == SET_NULL_VALUE) {
				inSourceTransitions[transitionIndex] = coreNodesCount;
				inSourceTransitionsCount++;
			}
		}

		for (PreArc arc : source.getOutgoingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (outSourceTransitions[transitionIndex] == SET_NULL_VALUE) {
				outSourceTransitions[transitionIndex] = coreNodesCount;
				outSourceTransitionsCount++;
			}
		}

		for (PostArc arc : target.getIncomingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (inTargetTransitions[transitionIndex] == SET_NULL_VALUE) {
				inTargetTransitions[transitionIndex] = coreNodesCount;
				inTargetTransitionsCount++;
			}
		}

		for (PreArc arc : target.getOutgoingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (outTargetTransitions[transitionIndex] == SET_NULL_VALUE) {
				outTargetTransitions[transitionIndex] = coreNodesCount;
				outTargetTransitionsCount++;
			}
		}

		assert assertValidState();
	}

	private void backtrackPlacePair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		Place source = sourcePlaces[sourceIndex];
		Place target = targetPlaces[targetIndex];

		if (inSourcePlaces[sourceIndex] == coreNodesCount) {
			inSourcePlaces[sourceIndex] = SET_NULL_VALUE;
			inSourcePlacesCount--;
		}

		if (outSourcePlaces[sourceIndex] == coreNodesCount) {
			outSourcePlaces[sourceIndex] = SET_NULL_VALUE;
			outSourcePlacesCount--;
		}

		if (inTargetPlaces[targetIndex] == coreNodesCount) {
			inTargetPlaces[targetIndex] = SET_NULL_VALUE;
			inTargetPlacesCount--;
		}

		if (outTargetPlaces[targetIndex] == coreNodesCount) {
			outTargetPlaces[targetIndex] = SET_NULL_VALUE;
			outTargetPlacesCount--;
		}

		for (PostArc arc : source.getIncomingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (inSourceTransitions[transitionIndex] == coreNodesCount) {
				inSourceTransitions[transitionIndex] = SET_NULL_VALUE;
				inSourceTransitionsCount--;
			}
		}

		for (PreArc arc : source.getOutgoingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (outSourceTransitions[transitionIndex] == coreNodesCount) {
				outSourceTransitions[transitionIndex] = SET_NULL_VALUE;
				outSourceTransitionsCount--;
			}
		}

		for (PostArc arc : target.getIncomingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (inTargetTransitions[transitionIndex] == coreNodesCount) {
				inTargetTransitions[transitionIndex] = SET_NULL_VALUE;
				inTargetTransitionsCount--;
			}
		}

		for (PreArc arc : target.getOutgoingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (outTargetTransitions[transitionIndex] == coreNodesCount) {
				outTargetTransitions[transitionIndex] = SET_NULL_VALUE;
				outTargetTransitionsCount--;
			}
		}

		coreSourcePlaces[sourceIndex] = CORE_NULL_NODE;
		coreTargetPlaces[targetIndex] = CORE_NULL_NODE;

		coreSourcePlacesCount--;
		coreTargetPlacesCount--;
		coreNodesCount--;

		assert assertValidState();
	}

	private boolean isPlaceFeasible(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (!semanticEqualPlaces[sourceIndex][targetIndex]) {
			return false;
		}

		Place source = sourcePlaces[sourceIndex];
		Place target = targetPlaces[targetIndex];

	    int[] sourceCardinality = new int[3];
	    int[] targetCardinality = new int[3];

		if (isPlaceFeasibleSynPred(source, target, sourceTransitionsIndexes,
				coreSourceTransitions, inSourceTransitions,
				outSourceTransitions, targetTransitions, sourceCardinality)
		 && isPlaceFeasibleSynPred(target, source,
				targetTransitionsIndexes, coreTargetTransitions,
				inTargetTransitions, outTargetTransitions,
				sourceTransitions, targetCardinality)
		 && isPlaceFeasibleSynSucc(source, target,
				sourceTransitionsIndexes, coreSourceTransitions,
				inSourceTransitions, outSourceTransitions,
				targetTransitions, sourceCardinality)
		 && isPlaceFeasibleSynSucc(target, source,
				targetTransitionsIndexes, coreTargetTransitions,
				inTargetTransitions, outTargetTransitions,
				sourceTransitions, targetCardinality)
		 && sourceCardinality[INDEX_TERMINAL_IN]  <= targetCardinality[INDEX_TERMINAL_IN]
		 && sourceCardinality[INDEX_TERMINAL_OUT] <= targetCardinality[INDEX_TERMINAL_OUT]
		 && sourceCardinality[INDEX_NEW_PAIR]     <= targetCardinality[INDEX_NEW_PAIR]) {
			return true;
		}

		return false;
	}

	private boolean isPlaceFeasibleSynSucc(Place source, Place target,
			Map<Transition, Integer> sourceTransitionsIndexes,
			int[] coreSourceTransitions, int[] inSourceTransitions,
			int[] outSourceTransitions, Transition[] targetTransitions,
			int[] cardinality) {

		assert cardinality.length == 3;

		for (PreArc arc : source.getOutgoingArcs()) {
			int sourceTransitionIndex = sourceTransitionsIndexes.get(arc.getTransition());
			int targetTransitionIndex = coreSourceTransitions[sourceTransitionIndex];

			if (targetTransitionIndex != CORE_NULL_NODE) {
				Transition targetTransition = targetTransitions[targetTransitionIndex];

				if (!target.hasOutgoingArc(targetTransition)
						|| !isSemanticEqual(arc, target.getOutgoingArc(targetTransition))) {
					return false;
				}
			} else {
				if (outSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE
						&& outSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE) {
					cardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		return true;
	}



	private boolean isPlaceFeasibleSynPred(Place source, Place target,
			Map<Transition, Integer> sourceTransitionsIndexes,
			int[] coreSourceTransitions, int[] inSourceTransitions,
			int[] outSourceTransitions, Transition[] targetTransitions,
			int[] cardinality) {

		assert cardinality.length == 3;

		for (PostArc arc : source.getIncomingArcs()) {
			int sourceTransitionIndex = sourceTransitionsIndexes.get(arc.getTransition());
			int targetTransitionIndex = coreSourceTransitions[sourceTransitionIndex];

			if (targetTransitionIndex != CORE_NULL_NODE) {
				Transition targetTransition = targetTransitions[targetTransitionIndex];

				if (!target.hasIncomingArc(targetTransition)
						|| !isSemanticEqual(arc, target.getIncomingArc(targetTransition))) {
					return false;
				}
			} else {
				if (outSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE
						&& outSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE) {
					cardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		return true;
	}



	private boolean isTransitionFeasible(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (!semanticEqualTransitions[sourceIndex][targetIndex]) {
			return false;
		}

		Transition source = sourceTransitions[sourceIndex];
		Transition target = targetTransitions[targetIndex];

	    int[] sourceCardinality = new int[3];
	    int[] targetCardinality = new int[3];

		if (isTransitionFeasibleSynPred(source, target, sourcePlacesIndexes,
				coreSourcePlaces, inSourcePlaces,
				outSourcePlaces, targetPlaces, sourceCardinality)
		 && isTransitionFeasibleSynPred(target, source,
				targetPlacesIndexes, coreTargetPlaces,
				inTargetPlaces, outTargetPlaces,
				sourcePlaces, targetCardinality)
		 && isTransitionFeasibleSynSucc(source, target,
				sourcePlacesIndexes, coreSourcePlaces,
				inSourcePlaces, outSourcePlaces,
				targetPlaces, sourceCardinality)
		 && isTransitionFeasibleSynSucc(target, source,
				targetPlacesIndexes, coreTargetPlaces,
				inTargetPlaces, outTargetPlaces,
				sourcePlaces, targetCardinality)
		 && sourceCardinality[INDEX_TERMINAL_IN]  == targetCardinality[INDEX_TERMINAL_IN]
		 && sourceCardinality[INDEX_TERMINAL_OUT] == targetCardinality[INDEX_TERMINAL_OUT]
		 && sourceCardinality[INDEX_NEW_PAIR]     == targetCardinality[INDEX_NEW_PAIR]) {
			return true;
		}

		return false;
	}

	private boolean isTransitionFeasibleSynSucc(Transition source, Transition target,
			Map<Place, Integer> sourcePlacesIndexes,
			int[] coreSourcePlaces, int[] inSourcePlaces,
			int[] outSourcePlaces, Place[] targetPlaces,
			int[] cardinality) {

		assert cardinality.length == 3;

		for (PostArc arc : source.getOutgoingArcs()) {
			int sourcePlaceIndex = sourcePlacesIndexes.get(arc.getPlace());
			int targetPlaceIndex = coreSourcePlaces[sourcePlaceIndex];

			if (targetPlaceIndex != CORE_NULL_NODE) {
				Place targetPlace = targetPlaces[targetPlaceIndex];

				if (!target.hasOutgoingArc(targetPlace)
						|| !isSemanticEqual(arc, target.getOutgoingArc(targetPlace))) {
					return false;
				}
			} else {
				if (outSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE
						&& outSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
					cardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		return true;
	}



	private boolean isTransitionFeasibleSynPred(Transition source, Transition target,
			Map<Place, Integer> sourcePlacesIndexes,
			int[] coreSourcePlaces, int[] inSourcePlaces,
			int[] outSourcePlaces, Place[] targetPlaces,
			int[] cardinality) {

		assert cardinality.length == 3;

		for (PreArc arc : source.getIncomingArcs()) {
			int sourcePlaceIndex = sourcePlacesIndexes.get(arc.getPlace());
			int targetPlaceIndex = coreSourcePlaces[sourcePlaceIndex];

			if (targetPlaceIndex != CORE_NULL_NODE) {
				Place targetPlace = targetPlaces[targetPlaceIndex];

				if (!target.hasIncomingArc(targetPlace)
						|| !isSemanticEqual(arc, target.getIncomingArc(targetPlace))) {
					return false;
				}
			} else {
				if (outSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE
						&& outSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
					cardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		return true;
	}


	private boolean generateSemanticPlaces(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlaces) {
		for (int sourcePlaceIndex = 0; sourcePlaceIndex < sourcePlaces.length; sourcePlaceIndex++) {
			Place   sourcePlace        = sourcePlaces[sourcePlaceIndex];
			boolean hasSupportingPlace = false;
			boolean isArcRestricted    = arcRestrictedSourcePlaces.contains(sourcePlace);

			for (int targetPlaceIndex = 0; targetPlaceIndex < targetPlaces.length; targetPlaceIndex++) {
				if (isSemanticEqual(sourcePlace, targetPlaces[targetPlaceIndex], isStrictMatch, isArcRestricted)) {
					hasSupportingPlace = true;
					semanticEqualPlaces[sourcePlaceIndex][targetPlaceIndex] = true;
				} else {
					semanticEqualPlaces[sourcePlaceIndex][targetPlaceIndex] = false;
				}
			}

			if (!hasSupportingPlace) {
				return false;
			}
		}

		return true;
	}

	private boolean generateSemanticTransitions() {
		for (int sourceTransitionIndex = 0; sourceTransitionIndex < sourceTransitions.length; sourceTransitionIndex++) {
			Transition sourceTransition        = sourceTransitions[sourceTransitionIndex];
			boolean    hasSupportingTransition = false;

			for (int targetTransitionIndex = 0; targetTransitionIndex < targetTransitions.length; targetTransitionIndex++) {
				if (isSemanticEqual(sourceTransition, targetTransitions[targetTransitionIndex])) {
					hasSupportingTransition = true;
					semanticEqualTransitions[sourceTransitionIndex][targetTransitionIndex] = true;
				} else {
					semanticEqualTransitions[sourceTransitionIndex][targetTransitionIndex] = false;
				}
			}

			if (!hasSupportingTransition) {
				return false;
			}
		}

		return true;
	}

	private boolean isSemanticEqual(PreArc source, PreArc target) {
		return source.getWeight() == target.getWeight()
		    && source.getName().equals(target.getName());
	}

	private boolean isSemanticEqual(PostArc source, PostArc target) {
		return source.getWeight() == target.getWeight()
		    && source.getName().equals(target.getName());
	}

	private boolean isSemanticEqual(Place source, Place target, boolean isStrictMatch, boolean isArcRestricted) {
		return source.getMark() <= target.getMark()
			&& (!isStrictMatch || (source.getMark() == target.getMark()))
		    && ((isArcRestricted && source.getIncomingArcs().size() == target.getIncomingArcs().size()
			  		&& source.getOutgoingArcs().size() == target.getOutgoingArcs().size())
			  || (!isArcRestricted && source.getIncomingArcs().size() <= target.getIncomingArcs().size()
	   	    		&& source.getOutgoingArcs().size() <= target.getOutgoingArcs().size())
	   	       )
	   		&& source.getName().equals(target.getName());
	}

	private boolean isSemanticEqual(Transition source, Transition target) {
		boolean isSemanticEqual = source.getName().equals(target.getName())
			&& source.getTlb().equals(target.getTlb())
		    && source.getRnw().equals(target.getRnw())
		    && source.getIncomingArcs().size() == target.getIncomingArcs().size()
		    && source.getOutgoingArcs().size() == target.getOutgoingArcs().size()
	    	/*&& getAccumulatedPreArcsWeight(source.getIncomingArcs()) ==
	    		getAccumulatedPreArcsWeight(target.getIncomingArcs())
			&& getAccumulatedPostArcsWeight(source.getOutgoingArcs()) ==
				getAccumulatedPostArcsWeight(target.getOutgoingArcs())*/;

		if (!isSemanticEqual) {
			return false;
		}

		return true;
	}

	private int getAccumulatedPreArcsWeight(Set<PreArc> preArcs) {
		int weight = 0;

		for (PreArc arc : preArcs) {
			weight += arc.getWeight();
		}

		return weight;
	}

	private int getAccumulatedPostArcsWeight(Set<PostArc> postArcs) {
		int weight = 0;

		for (PostArc arc : postArcs) {
			weight += arc.getWeight();
		}

		return weight;
	}

	private Match getBuildMatch() {
		assert assertValidState();

		Map<Place, Place>           places      = asMap(coreSourcePlaces, sourcePlaces, targetPlaces);
		Map<Transition, Transition> transitions = asMap(coreSourceTransitions, sourceTransitions, targetTransitions);
		Map<PreArc, PreArc> 		preArcs 	= new HashMap<PreArc, PreArc>();
		Map<PostArc, PostArc> 		postArcs 	= new HashMap<PostArc, PostArc>();

		for (Map.Entry<Transition, Transition> mapping : transitions.entrySet()) {
			for (PreArc arc : mapping.getKey().getIncomingArcs()) {
				preArcs.put(arc, mapping.getValue().getIncomingArc(places.get(arc.getPlace())));
				assert preArcs.get(arc) != null;
			}

			for (PostArc arc : mapping.getKey().getOutgoingArcs()) {
				postArcs.put(arc, mapping.getValue().getOutgoingArc(places.get(arc.getPlace())));
				assert postArcs.get(arc) != null;
			}
		}

		assert source.getPlaces().size()      == places.size();
		assert source.getTransitions().size() == transitions.size();
		assert source.getPreArcs().size()     == preArcs.size();
		assert source.getPostArcs().size()    == postArcs.size();

		return new Match(source, target, places, transitions, preArcs, postArcs);
	}


	private boolean hasNextOutCandidatePairs() {
		return hasNextOutPlaceCandidatePairs() || hasNextOutTransitionCandidatePairs();
	}

	private boolean hasNextOutPlaceCandidatePairs() {
		return outSourcePlacesCount > coreSourcePlacesCount && outTargetPlacesCount > coreTargetPlacesCount;
	}

	private int getNextOutPlaceCandidatePairsCount() {
		if (!hasNextOutPlaceCandidatePairs()) {
			return 0;
		}

		return outTargetPlacesCount - coreTargetPlacesCount;
	}

	private boolean hasNextOutTransitionCandidatePairs() {
		return outSourceTransitionsCount > coreSourceTransitionsCount && outTargetTransitionsCount > coreTargetTransitionsCount;
	}

	private int getNextOutTransitionCandidatePairsCount() {
		if (!hasNextOutTransitionCandidatePairs()) {
			return 0;
		}

		return outTargetTransitionsCount - coreTargetTransitionsCount;
	}

	private boolean hasNextInCandidatePairs() {
		return hasNextInPlaceCandidatePairs() || hasNextInTransitionCandidatePairs();
	}

	private boolean hasNextInPlaceCandidatePairs() {
		return inSourcePlacesCount > coreSourcePlacesCount && inTargetPlacesCount > coreTargetPlacesCount;
	}

	private int getNextInPlaceCandidatePairsCount() {
		if (!hasNextInPlaceCandidatePairs()) {
			return 0;
		}

		return inTargetPlacesCount - coreTargetPlacesCount;
	}

	private boolean hasNextInTransitionCandidatePairs() {
		return inSourceTransitionsCount > coreSourceTransitionsCount && inTargetTransitionsCount > coreTargetTransitionsCount;
	}

	private int getNextInTransitionCandidatePairsCount() {
		if (!hasNextInTransitionCandidatePairs()) {
			return 0;
		}

		return inTargetTransitionsCount - coreTargetTransitionsCount;
	}

	private boolean hasNextPlaceCandidatePairs() {
		return coreSourcePlaces.length > coreSourcePlacesCount && coreTargetPlaces.length > coreTargetPlacesCount;
	}

	private int getNextPlaceCandidatePairsCount() {
		if (!hasNextPlaceCandidatePairs()) {
			return 0;
		}

		return coreTargetPlaces.length - coreTargetPlacesCount;
	}

	private boolean hasNextTransitionCandidatePairs() {
		return coreSourceTransitions.length > coreSourceTransitionsCount && coreTargetTransitions.length > coreTargetTransitionsCount;
	}

	private int getNextTransitionCandidatePairsCount() {
		if (!hasNextTransitionCandidatePairs()) {
			return 0;
		}

		return coreTargetTransitions.length - coreTargetTransitionsCount;
	}


	private int getNextUnmatchedNodeIndex(int[] core, int[] terminalSet, int startIndex) {
		assert core.length == terminalSet.length && startIndex >= 0;

		for (int index = startIndex; index < core.length; index++) {
			if (terminalSet[index]  != SET_NULL_VALUE && core[index] == CORE_NULL_NODE) {
				return index;
			}
		}

		return CORE_NULL_NODE;
	}

	private int getNextUnmatchedNodeIndex(int[] core, int startIndex) {
		for (int index = startIndex; index < core.length; index++) {
			if (core[index] == CORE_NULL_NODE) {
				return index;
			}
		}

		return CORE_NULL_NODE;
	}


	private boolean assertValidState() {
		assert coreNodesCount == coreSourcePlacesCount + coreSourceTransitionsCount;
		assert coreNodesCount == coreTargetPlacesCount + coreTargetTransitionsCount;

		assert coreSourcePlacesCount      == coreTargetPlacesCount;
		assert coreSourceTransitionsCount == coreTargetTransitionsCount;

		assert getUnequalCount(coreSourcePlaces, CORE_NULL_NODE)      == coreSourcePlacesCount;
		assert getUnequalCount(coreSourceTransitions, CORE_NULL_NODE) == coreSourceTransitionsCount;
		assert getUnequalCount(coreTargetPlaces, CORE_NULL_NODE)      == coreTargetPlacesCount;
		assert getUnequalCount(coreTargetTransitions, CORE_NULL_NODE) == coreTargetTransitionsCount;

		assert getUnequalCount(inSourcePlaces, SET_NULL_VALUE)        == inSourcePlacesCount;
		assert getUnequalCount(inSourceTransitions, SET_NULL_VALUE)   == inSourceTransitionsCount;
		assert getUnequalCount(outSourcePlaces, SET_NULL_VALUE)       == outSourcePlacesCount;
		assert getUnequalCount(outSourceTransitions, SET_NULL_VALUE)  == outSourceTransitionsCount;

		assert getUnequalCount(inTargetPlaces, SET_NULL_VALUE)        == inTargetPlacesCount;
		assert getUnequalCount(inTargetTransitions, SET_NULL_VALUE)   == inTargetTransitionsCount;
		assert getUnequalCount(outTargetPlaces, SET_NULL_VALUE)       == outTargetPlacesCount;
		assert getUnequalCount(outTargetTransitions, SET_NULL_VALUE)  == outTargetTransitionsCount;

		assert inSourcePlacesCount       >= coreSourcePlacesCount;
		assert inSourceTransitionsCount  >= coreSourceTransitionsCount;
		assert outSourcePlacesCount      >= coreSourcePlacesCount;
		assert outSourceTransitionsCount >= coreSourceTransitionsCount;

		assert inTargetPlacesCount 		 >= coreTargetPlacesCount;
		assert inTargetTransitionsCount  >= coreTargetTransitionsCount;
		assert outTargetPlacesCount 	 >= coreTargetPlacesCount;
		assert outTargetTransitionsCount >= coreTargetTransitionsCount;

		assert sourcePlacesIndexes.size() 	   == sourcePlaces.length;
		assert sourceTransitionsIndexes.size() == sourceTransitions.length;
		assert targetPlacesIndexes.size() 	   == targetPlaces.length;
		assert targetTransitionsIndexes.size() == targetTransitions.length;

		assert matchVisitor instanceof MatchVisitor;

		for (Place place : sourcePlacesIndexes.keySet()) {
			assert sourcePlaces[sourcePlacesIndexes.get(place)].equals(place);
		}

		for (Transition transition : sourceTransitionsIndexes.keySet()) {
			assert sourceTransitions[sourceTransitionsIndexes.get(transition)].equals(transition);
		}

		for (Place place : targetPlacesIndexes.keySet()) {
			assert targetPlaces[targetPlacesIndexes.get(place)].equals(place);
		}

		for (Transition transition : targetTransitionsIndexes.keySet()) {
			assert targetTransitions[targetTransitionsIndexes.get(transition)].equals(transition);
		}

		return true;
	}

	private int getUnequalCount(int[] array, int checkValue) {
		int unequalCount = 0;

		for (int arrayValue : array) {
			if (checkValue != arrayValue) {
				unequalCount++;
			}
		}

		return unequalCount;
	}

	private <T> Map<T, T> asMap(int[] coreSource, T[] source, T[] target) {
		Map<T, T> map = new HashMap<T, T>();

		for (int sourceIndex = 0; sourceIndex < coreSource.length; sourceIndex++) {
			map.put(source[sourceIndex], target[coreSource[sourceIndex]]);
		}

		return map;
	}

	private <T> void shuffle(T[] objects) {
		if (objects.length <= 1) {
			return;
		}

		int swapIndex;
		T   swapTmp;

		// from lastIndex down to index 1
		for (int index = objects.length - 1; index >= 1; index--) {
			// gets a random index between 0 and the current index
			// index + 1, because nextInt is in [0,index)
			swapIndex = RANDOM.nextInt(index + 1);

			swapTmp            = objects[swapIndex];
			objects[swapIndex] = objects[index];
			objects[index]     = swapTmp;
		}
	}

	private void initPlacesArray(Place[] places, Petrinet petrinet) {
		int index = 0;

		for (Place place : petrinet.getPlaces()) {
			places[index] = place;
			index++;
		}
	}

	private void initTransitionsArray(Transition[] transitions, Petrinet petrinet) {
		int index = 0;

		for (Transition transition : petrinet.getTransitions()) {
			transitions[index] = transition;
			index++;
		}
	}

	private <T> void initMap(Map<T, Integer> map, T[] array) {
		for (int index = 0; index < array.length; index++) {
			map.put(array[index], index);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Source\n");
		builder.append(Arrays.toString(sourcePlaces) + "\n");
		builder.append(Arrays.toString(sourceTransitions) + "\n");
		builder.append("Target\n");
		builder.append(Arrays.toString(targetPlaces) + "\n");
		builder.append(Arrays.toString(targetTransitions) + "\n");

		if (semanticEqualPlaces != null) {
			builder.append("Semantic Places\n");
			for (int index = 0; index < semanticEqualPlaces.length; index++) {
				builder.append(Arrays.toString(semanticEqualPlaces[index]) + "\n");
			}
		}

		if (semanticEqualTransitions != null) {
			builder.append("Semantic Transitions\n");
			for (int index = 0; index < semanticEqualTransitions.length; index++) {
				builder.append(Arrays.toString(semanticEqualTransitions[index]) + "\n");
			}
		}


		return  builder.toString();
	}

	public interface MatchVisitor {
		public boolean visit(Match match);
	}

	public final class MatchException extends Exception {
		private static final long serialVersionUID = 6792173074685670051L;

		public MatchException() {
			super();
		}

		public MatchException(String arg0) {
			super(arg0);
		}
	}

	private final class AcceptFirstMatchVisitor implements MatchVisitor {
		public boolean visit(Match match) {
			return true;
		}
	}
}
