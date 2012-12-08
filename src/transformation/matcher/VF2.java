package transformation.matcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Match;

public final class VF2 {
	private final Random RANDOM;
	private final int    CORE_NULL_NODE     = Integer.MAX_VALUE;
	private final int    SET_NULL_VALUE     = 0;

	private final int    INDEX_TERMINAL_IN  = 0;
	private final int    INDEX_TERMINAL_OUT = 1;
	private final int    INDEX_NEW_PAIR     = 2;

	private final int    INDEX_SOURCE_CANDIDATE = 0;
	private final int    INDEX_TARGET_CANDIDATE = 1;

	private final byte   NEXT_TERMINAL_OUT = 0;
	private final byte   NEXT_TERMINAL_IN  = 1;
	private final byte   NEXT_NEW          = 2;

	private Petrinet source;
	private Petrinet target;

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
	
	private Match lastMatch;
	
	public VF2(Petrinet source, Petrinet target, Random random) {
		this.source = source;
		this.target = target;
		this.RANDOM = random;
	}

	public VF2(Petrinet source, Petrinet target) {
		this.source = source;
		this.target = target;
		this.RANDOM = new Random();
	}

	private void init() {
		int sourcePlacesCount      = source.getPlaces().size();
		int sourceTransitionsCount = source.getTransitions().size();
		int targetPlacesCount      = target.getPlaces().size();
		int targetTransitionsCount = target.getTransitions().size();

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
	}

	private void debug(Object message) {
//System.out.println(message);
	}

	public Match getMatch(boolean isStrictMatch, Match partialMatch) {
		return getMatch(isStrictMatch, partialMatch, new DefaultMatchVisitor());
	}
	
	public Match getMatch(boolean isStrictMatch, Match partialMatch, MatchVisitor visitor) {
		if (source.getPlaces().size() > target.getPlaces().size()
	     || source.getTransitions().size() > target.getTransitions().size()) {
			return null;
		}

		init();

		if (!generateSemanticPlaces(isStrictMatch) || !generateSemanticTransitions()) {
			return null;
		}
		
		for (Map.Entry<Place, Place> mapping : partialMatch.getPlaces().entrySet()) {
			if (sourcePlacesIndexes.get(mapping.getKey()) == null
			 || targetPlacesIndexes.get(mapping.getValue()) == null) {
				return null;
			}
			
			if (!semanticEqualPlaces[sourcePlacesIndexes.get(mapping.getKey())][targetPlacesIndexes.get(mapping.getValue())]
			|| !isPlaceFeasible(sourcePlacesIndexes.get(mapping.getKey()), targetPlacesIndexes.get(mapping.getValue()))) {
				return null;				
			}
			
			addPlacePair(sourcePlacesIndexes.get(mapping.getKey()), targetPlacesIndexes.get(mapping.getValue()));
		}
		
		for (Map.Entry<Transition, Transition> mapping : partialMatch.getTransitions().entrySet()) {
			if (sourceTransitionsIndexes.get(mapping.getKey()) == null
			 || targetTransitionsIndexes.get(mapping.getValue()) == null) {
				return null;
			}
			
			if (!semanticEqualTransitions[sourceTransitionsIndexes.get(mapping.getKey())][targetTransitionsIndexes.get(mapping.getValue())]
			|| !isTransitionFeasible(sourceTransitionsIndexes.get(mapping.getKey()), targetTransitionsIndexes.get(mapping.getValue()))) {
				return null;				
			}
			
			addTransitionPair(sourceTransitionsIndexes.get(mapping.getKey()), targetTransitionsIndexes.get(mapping.getValue()));
		}

		assert partialMatch.getPlaces().size()      == coreSourcePlacesCount;
		assert partialMatch.getTransitions().size() == coreSourceTransitionsCount;

		if (match(visitor)) {
			return lastMatch;
		}

		return null;
	}

	public Match getMatch(boolean isStrictMatch) {
		return getMatch(isStrictMatch, new DefaultMatchVisitor());
	}

	public Match getMatch(boolean isStrictMatch, MatchVisitor visitor) {
		if (source.getPlaces().size() > target.getPlaces().size()
	     || source.getTransitions().size() > target.getTransitions().size()) {
			return null;
		}

		init();

		if (!generateSemanticPlaces(isStrictMatch) || !generateSemanticTransitions()) {
			return null;
		}

		if (match(visitor)) {
			return lastMatch;
		}

		return null;
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
			}

			for (PostArc arc : mapping.getKey().getOutgoingArcs()) {
				postArcs.put(arc, mapping.getValue().getOutgoingArc(places.get(arc.getPlace())));
			}
		}

		return new Match(source, target, places, transitions, preArcs, postArcs);
	}

	private boolean match(MatchVisitor visitor) {
		assert assertValidState();
		
		if (coreSourcePlaces.length == coreSourcePlacesCount
		 && coreSourceTransitions.length == coreSourceTransitionsCount) {
			lastMatch = getBuildMatch();
			
			return visitor.visit(lastMatch);
		}
		
		assert coreNodesCount != coreSourcePlaces.length + coreSourceTransitions.length;

		if (hasNextOutCandidatePairs()) {
			return matchByOut(visitor);
		} else if (hasNextInCandidatePairs()) {
			return matchByIn(visitor);
		} else {
			return matchByNew(visitor);
		}
	}
	
	private boolean matchByOut(MatchVisitor visitor) {
		assert hasNextOutCandidatePairs();
		
		if (matchByPlacePairs(getNextOutPlaceCandidatePairsCount(), getNextOutTransitionCandidatePairsCount())) {
			return matchByPlace(visitor, NEXT_TERMINAL_OUT);			
		} else {
			return matchByTransition(visitor, NEXT_TERMINAL_OUT);
		}
	}
	
	private boolean matchByIn(MatchVisitor visitor) {
		assert hasNextInCandidatePairs();
		
		if (matchByPlacePairs(getNextInPlaceCandidatePairsCount(), getNextInTransitionCandidatePairsCount())) {
			return matchByPlace(visitor, NEXT_TERMINAL_IN);			
		} else {
			return matchByTransition(visitor, NEXT_TERMINAL_IN);
		}
	}
	
	private boolean matchByNew(MatchVisitor visitor) {
		assert hasNextPlaceCandidatePairs() || hasNextTransitionCandidatePairs();
		
		//if (matchByPlacePairs(getNextPlaceCandidatePairsCount(), getNextTransitionCandidatePairsCount())) {		
		if (getNextPlaceCandidatePairsCount() > getNextTransitionCandidatePairsCount()
		|| (getNextPlaceCandidatePairsCount() == getNextTransitionCandidatePairsCount() && RANDOM.nextInt(2) == 0)) {
			return matchByPlace(visitor, NEXT_NEW);			
		} else {
			return matchByTransition(visitor, NEXT_NEW);
		}
	}
	
	private boolean matchByPlacePairs(int placePairs, int transitionPairs) {
		return placePairs > 0 && (transitionPairs == 0 || RANDOM.nextInt(placePairs + transitionPairs) < placePairs);
	}

	private boolean matchByTransition(MatchVisitor visitor, byte next) {
		int[] lastPair = {CORE_NULL_NODE, CORE_NULL_NODE};

		boolean areOutExhausted = false;

		debug(coreNodesCount + ": " + next +" - source - places: " + Arrays.toString(outSourcePlaces));
		debug(coreNodesCount + ": " + next +" - source - transition: " + Arrays.toString(outSourceTransitions));
		debug(coreNodesCount + ": " + next +" - target - places: " + Arrays.toString(outTargetPlaces));
		debug(coreNodesCount + ": " + next +" - target - transition: " + Arrays.toString(outTargetTransitions));

		while (areOutExhausted == false) {
			lastPair = getNextTransitionCandidatePair(lastPair, next);

			debug(coreNodesCount + ": " + next +" - candidate - transition: " + Arrays.toString(lastPair));

			if (lastPair[INDEX_SOURCE_CANDIDATE] == CORE_NULL_NODE || lastPair[INDEX_TARGET_CANDIDATE] == CORE_NULL_NODE) {
				debug(coreNodesCount + ": " + next +" - Transitions : exhausted");
				areOutExhausted = true;
			} else if (
				   semanticEqualTransitions[lastPair[INDEX_SOURCE_CANDIDATE]][lastPair[INDEX_TARGET_CANDIDATE]]
				&& isTransitionFeasible(lastPair[INDEX_SOURCE_CANDIDATE], lastPair[INDEX_TARGET_CANDIDATE])) {

				debug(coreNodesCount + ": " + next +" - add - Transition : " + Arrays.toString(lastPair));

				addTransitionPair(
					lastPair[INDEX_SOURCE_CANDIDATE],
					lastPair[INDEX_TARGET_CANDIDATE]
				);

				if (match(visitor)) {
					return true;
				}

				backtrackTransitionPair(
					lastPair[INDEX_SOURCE_CANDIDATE],
					lastPair[INDEX_TARGET_CANDIDATE]
				);

				debug(coreNodesCount + ": " + next +" - Backtrack - Transition : " + Arrays.toString(lastPair));
			}
		}

		debug(coreNodesCount + ": " + next +" - Both : exhausted");

		return false;
	}

	private boolean matchByPlace(MatchVisitor visitor, byte next) {
		int[] lastPair      = {CORE_NULL_NODE, CORE_NULL_NODE};

		boolean areOutExhausted      = false;

		debug(coreNodesCount + ": " + next +" - source - places: " + Arrays.toString(outSourcePlaces));
		debug(coreNodesCount + ": " + next +" - source - transition: " + Arrays.toString(outSourceTransitions));
		debug(coreNodesCount + ": " + next +" - target - places: " + Arrays.toString(outTargetPlaces));
		debug(coreNodesCount + ": " + next +" - target - transition: " + Arrays.toString(outTargetTransitions));

		while (areOutExhausted == false) {
			lastPair = getNextPlaceCandidatePair(lastPair, next);

			debug(coreNodesCount + ": " + next +" - candidate - Place: " + Arrays.toString(lastPair));

			if (lastPair[INDEX_SOURCE_CANDIDATE] == CORE_NULL_NODE || lastPair[INDEX_TARGET_CANDIDATE] == CORE_NULL_NODE) {
				debug(coreNodesCount + ": " + next +" - Places : exhausted");
				areOutExhausted = true;
			} else if (semanticEqualPlaces[lastPair[INDEX_SOURCE_CANDIDATE]][lastPair[INDEX_TARGET_CANDIDATE]]
					&& isPlaceFeasible(lastPair[INDEX_SOURCE_CANDIDATE], lastPair[INDEX_TARGET_CANDIDATE])) {

				debug(coreNodesCount + ": " + next +" - add - Place : " + Arrays.toString(lastPair));

				addPlacePair(
					lastPair[INDEX_SOURCE_CANDIDATE],
					lastPair[INDEX_TARGET_CANDIDATE]
				);

				if (match(visitor)) {
					return true;
				}

				backtrackPlacePair(
					lastPair[INDEX_SOURCE_CANDIDATE],
					lastPair[INDEX_TARGET_CANDIDATE]
				);

				debug(coreNodesCount + ": " + next +" - Backtrack - Place : " + Arrays.toString(lastPair));
			}
		}

		debug(coreNodesCount + ": " + next +" - Both : exhausted");

		return false;
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
	
	
	private int[] getNextTransitionCandidatePair(int[] previousCancidatePair, byte next) {
		assert next == NEXT_TERMINAL_OUT || next == NEXT_TERMINAL_IN || next == NEXT_NEW;
		
		if (next == NEXT_TERMINAL_OUT) {
			return getNextOutTransitionCandidatePair(previousCancidatePair);
		} else if (next == NEXT_TERMINAL_IN) {
			return getNextInTransitionCandidatePair(previousCancidatePair);			
		} else {
			return getNextNewTransitionCandidatePair(previousCancidatePair);			
		}
	}
	
	private int[] getNextPlaceCandidatePair(int[] previousCancidatePair, byte next) {
		assert next == NEXT_TERMINAL_OUT || next == NEXT_TERMINAL_IN || next == NEXT_NEW;
		
		if (next == NEXT_TERMINAL_OUT) {
			return getNextOutPlaceCandidatePair(previousCancidatePair);
		} else if (next == NEXT_TERMINAL_IN) {
			return getNextInPlaceCandidatePair(previousCancidatePair);			
		} else {
			return getNextNewPlaceCandidatePair(previousCancidatePair);			
		}
	}

	private int[] getNextOutTransitionCandidatePair(int[] previousCancidatePair) {
		assert previousCancidatePair.length == 2;
		
		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		int[] pair = {CORE_NULL_NODE, CORE_NULL_NODE};

		if (!hasNextOutTransitionCandidatePairs()
		 || (previousSourceIndex != CORE_NULL_NODE && previousSourceIndex == coreTargetTransitions.length - 1)) {
			return pair;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			sourceIndex = 0;
			targetIndex = 0;
			
			for (; sourceIndex < coreSourceTransitions.length; sourceIndex++) {
				if (outSourceTransitions[sourceIndex]  != SET_NULL_VALUE
				 && coreSourceTransitions[sourceIndex] == CORE_NULL_NODE) {
					break;
				}
			}
		}
		
		for (; targetIndex < coreTargetTransitions.length; targetIndex++) {
			if (outTargetTransitions[targetIndex]  != SET_NULL_VALUE
			 && coreTargetTransitions[targetIndex] == CORE_NULL_NODE) {
				break;
			}
		}

		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (sourceIndex >= coreSourceTransitions.length || targetIndex >= coreTargetTransitions.length) {
			return pair;
		}

		pair[INDEX_SOURCE_CANDIDATE] = sourceIndex;
		pair[INDEX_TARGET_CANDIDATE] = targetIndex;

		return pair;
	}

	private int[] getNextInTransitionCandidatePair(int[] previousCancidatePair) {
		assert previousCancidatePair.length == 2;
		
		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		int[] pair = {CORE_NULL_NODE, CORE_NULL_NODE};

		if (!hasNextInTransitionCandidatePairs()
		 || (previousSourceIndex != CORE_NULL_NODE && previousSourceIndex == coreTargetTransitions.length - 1)) {
			return pair;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			sourceIndex = 0;
			targetIndex = 0;
			
			for (; sourceIndex < coreSourceTransitions.length; sourceIndex++) {
				if (inSourceTransitions[sourceIndex]  != SET_NULL_VALUE
				 && coreSourceTransitions[sourceIndex] == CORE_NULL_NODE) {
					break;
				}
			}
		}

		for (; targetIndex < coreTargetTransitions.length; targetIndex++) {
			if (inTargetTransitions[targetIndex]  != SET_NULL_VALUE
			 && coreTargetTransitions[targetIndex] == CORE_NULL_NODE) {
				break;
			}
		}
		
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (sourceIndex >= coreSourceTransitions.length || targetIndex >= coreTargetTransitions.length) {
			return pair;
		}

		pair[INDEX_SOURCE_CANDIDATE] = sourceIndex;
		pair[INDEX_TARGET_CANDIDATE] = targetIndex;

		return pair;
	}



	private int[] getNextNewTransitionCandidatePair(int[] previousCancidatePair) {
		assert previousCancidatePair.length == 2;
		
		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		int[] pair = {CORE_NULL_NODE, CORE_NULL_NODE};

		if (previousSourceIndex != CORE_NULL_NODE && previousSourceIndex == coreTargetTransitions.length - 1) {
			return pair;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			sourceIndex = 0;
			targetIndex = 0;
			
			for (; sourceIndex < coreSourceTransitions.length; sourceIndex++) {
				if (coreSourceTransitions[sourceIndex] == CORE_NULL_NODE) {
					break;
				}
			}

		}

		for (; targetIndex < coreTargetTransitions.length; targetIndex++) {
			if (coreTargetTransitions[targetIndex] == CORE_NULL_NODE) {
				break;
			}
		}
		
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (sourceIndex >= coreSourceTransitions.length || targetIndex >= coreTargetTransitions.length) {
			return pair;
		}

		pair[INDEX_SOURCE_CANDIDATE] = sourceIndex;
		pair[INDEX_TARGET_CANDIDATE] = targetIndex;

		return pair;
	}


	private int[] getNextOutPlaceCandidatePair(int[] previousCancidatePair) {
		assert previousCancidatePair.length == 2;
		
		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		int[] pair = {CORE_NULL_NODE, CORE_NULL_NODE};

		if (!hasNextOutPlaceCandidatePairs()
		 || (previousSourceIndex != CORE_NULL_NODE && previousSourceIndex == coreTargetPlaces.length - 1)) {
			return pair;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			sourceIndex = 0;
			targetIndex = 0;
			
			for (; sourceIndex < coreSourcePlaces.length; sourceIndex++) {
				if (outSourcePlaces[sourceIndex]  != SET_NULL_VALUE
				 && coreSourcePlaces[sourceIndex] == CORE_NULL_NODE) {
					break;
				}
			}

		}

		for (; targetIndex < coreTargetPlaces.length; targetIndex++) {
			if (outTargetPlaces[targetIndex]  != SET_NULL_VALUE
			 && coreTargetPlaces[targetIndex] == CORE_NULL_NODE) {
				break;
			}
		}
		
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (sourceIndex >= coreSourcePlaces.length || targetIndex >= coreTargetPlaces.length) {
			return pair;
		}

		pair[INDEX_SOURCE_CANDIDATE] = sourceIndex;
		pair[INDEX_TARGET_CANDIDATE] = targetIndex;

		return pair;
	}

	private int[] getNextInPlaceCandidatePair(int[] previousCancidatePair) {
		assert previousCancidatePair.length == 2;
		
		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		int[] pair = {CORE_NULL_NODE, CORE_NULL_NODE};

		if (!hasNextInPlaceCandidatePairs()
		 || (previousSourceIndex != CORE_NULL_NODE && previousSourceIndex == coreTargetPlaces.length - 1)) {
			return pair;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			sourceIndex = 0;
			targetIndex = 0;
			
			for (; sourceIndex < coreSourcePlaces.length; sourceIndex++) {
				if (inSourcePlaces[sourceIndex]  != SET_NULL_VALUE && coreSourcePlaces[sourceIndex] == CORE_NULL_NODE) {
					break;
				}
			}

		}

		for (; targetIndex < coreTargetPlaces.length; targetIndex++) {
			if (inTargetPlaces[targetIndex] != SET_NULL_VALUE && coreTargetPlaces[targetIndex] == CORE_NULL_NODE) {
				break;
			}
		}
		
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (sourceIndex >= coreSourcePlaces.length || targetIndex >= coreTargetPlaces.length) {
			return pair;
		}

		pair[INDEX_SOURCE_CANDIDATE] = sourceIndex;
		pair[INDEX_TARGET_CANDIDATE] = targetIndex;

		return pair;
	}



	private int[] getNextNewPlaceCandidatePair(int[] previousCancidatePair) {
		assert previousCancidatePair.length == 2;
		
		int previousSourceIndex = previousCancidatePair[INDEX_SOURCE_CANDIDATE];
		int previousTargetIndex = previousCancidatePair[INDEX_TARGET_CANDIDATE];

		int[] pair = {CORE_NULL_NODE, CORE_NULL_NODE};

		if (previousSourceIndex != CORE_NULL_NODE && previousSourceIndex == coreTargetPlaces.length - 1) {
			return pair;
		}

		int sourceIndex = previousSourceIndex;
		int targetIndex = previousTargetIndex + 1;

		if (sourceIndex == CORE_NULL_NODE) {
			sourceIndex = 0;
			targetIndex = 0;
			
			for (; sourceIndex < coreSourcePlaces.length; sourceIndex++) {
				if (coreSourcePlaces[sourceIndex] == CORE_NULL_NODE) {
					break;
				}
			}
		}

		for (; targetIndex < coreTargetPlaces.length; targetIndex++) {
			if (coreTargetPlaces[targetIndex] == CORE_NULL_NODE) {
				break;
			}
		}
		
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (sourceIndex >= coreSourcePlaces.length ||  targetIndex >= coreTargetPlaces.length) {
			return pair;
		}

		pair[INDEX_SOURCE_CANDIDATE] = sourceIndex;
		pair[INDEX_TARGET_CANDIDATE] = targetIndex;

		return pair;
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
			int sourcePlaceIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (inSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
				inSourcePlaces[sourcePlaceIndex] = coreNodesCount;
				inSourcePlacesCount++;
			}
		}

		for (PostArc arc : source.getOutgoingArcs()) {
			int sourcePlaceIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (outSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
				outSourcePlaces[sourcePlaceIndex] = coreNodesCount;
				outSourcePlacesCount++;
			}
		}

		for (PreArc arc : target.getIncomingArcs()) {
			int targetPlaceIndex = targetPlacesIndexes.get(arc.getPlace());

			if (inTargetPlaces[targetPlaceIndex] == SET_NULL_VALUE) {
				inTargetPlaces[targetPlaceIndex] = coreNodesCount;
				inTargetPlacesCount++;
			}
		}

		for (PostArc arc : target.getOutgoingArcs()) {
			int targetPlaceIndex = targetPlacesIndexes.get(arc.getPlace());

			if (outTargetPlaces[targetPlaceIndex] == SET_NULL_VALUE) {
				outTargetPlaces[targetPlaceIndex] = coreNodesCount;
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
			int sourcePlaceIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (inSourcePlaces[sourcePlaceIndex] == coreNodesCount) {
				inSourcePlaces[sourcePlaceIndex] = SET_NULL_VALUE;
				inSourcePlacesCount--;
			}
		}

		for (PostArc arc : source.getOutgoingArcs()) {
			int sourcePlaceIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (outSourcePlaces[sourcePlaceIndex] == coreNodesCount) {
				outSourcePlaces[sourcePlaceIndex] = SET_NULL_VALUE;
				outSourcePlacesCount--;
			}
		}

		for (PreArc arc : target.getIncomingArcs()) {
			int targetPlaceIndex = targetPlacesIndexes.get(arc.getPlace());

			if (inTargetPlaces[targetPlaceIndex] == coreNodesCount) {
				inTargetPlaces[targetPlaceIndex] = SET_NULL_VALUE;
				inTargetPlacesCount--;
			}
		}

		for (PostArc arc : target.getOutgoingArcs()) {
			int targetPlaceIndex = targetPlacesIndexes.get(arc.getPlace());

			if (outTargetPlaces[targetPlaceIndex] == coreNodesCount) {
				outTargetPlaces[targetPlaceIndex] = SET_NULL_VALUE;
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
			int sourceTransitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (inSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE) {
				inSourceTransitions[sourceTransitionIndex] = coreNodesCount;
				inSourceTransitionsCount++;
			}
		}

		for (PreArc arc : source.getOutgoingArcs()) {
			int sourceTransitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (outSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE) {
				outSourceTransitions[sourceTransitionIndex] = coreNodesCount;
				outSourceTransitionsCount++;
			}
		}

		for (PostArc arc : target.getIncomingArcs()) {
			int targetTransitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (inTargetTransitions[targetTransitionIndex] == SET_NULL_VALUE) {
				inTargetTransitions[targetTransitionIndex] = coreNodesCount;
				inTargetTransitionsCount++;
			}
		}

		for (PreArc arc : target.getOutgoingArcs()) {
			int targetTransitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (outTargetTransitions[targetTransitionIndex] == SET_NULL_VALUE) {
				outTargetTransitions[targetTransitionIndex] = coreNodesCount;
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
			int sourceTransitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (inSourceTransitions[sourceTransitionIndex] == coreNodesCount) {
				inSourceTransitions[sourceTransitionIndex] = SET_NULL_VALUE;
				inSourceTransitionsCount--;
			}
		}

		for (PreArc arc : source.getOutgoingArcs()) {
			int sourceTransitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (outSourceTransitions[sourceTransitionIndex] == coreNodesCount) {
				outSourceTransitions[sourceTransitionIndex] = SET_NULL_VALUE;
				outSourceTransitionsCount--;
			}
		}

		for (PostArc arc : target.getIncomingArcs()) {
			int targetTransitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (inTargetTransitions[targetTransitionIndex] == coreNodesCount) {
				inTargetTransitions[targetTransitionIndex] = SET_NULL_VALUE;
				inTargetTransitionsCount--;
			}
		}

		for (PreArc arc : target.getOutgoingArcs()) {
			int targetTransitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (outTargetTransitions[targetTransitionIndex] == coreNodesCount) {
				outTargetTransitions[targetTransitionIndex] = SET_NULL_VALUE;
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

				if (!target.hasOutgoingArc(targetTransition)) {
					return false;
				}

				if (!isSemanticEqual(arc, target.getOutgoingArc(targetTransition))) {
					return false;
				}
			} else {
				if (inSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (outSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
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

				if (!target.hasIncomingArc(targetTransition)) {
					return false;
				}

				if (!isSemanticEqual(arc, target.getIncomingArc(targetTransition))) {
					return false;
				}
			} else {
				if (inSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (outSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
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

				if (!target.hasOutgoingArc(targetPlace)) {
					return false;
				}

				if (!isSemanticEqual(arc, target.getOutgoingArc(targetPlace))) {
					return false;
				}
			} else {
				if (inSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (outSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
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

				if (!target.hasIncomingArc(targetPlace)) {
					return false;
				}

				if (!isSemanticEqual(arc, target.getIncomingArc(targetPlace))) {
					return false;
				}
			} else {
				if (inSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_IN]++;
				}

				if (outSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					cardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE
						&& outSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
					cardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		return true;
	}


	private boolean generateSemanticPlaces(boolean isStrictMatch) {
		for (int sourcePlaceIndex = 0; sourcePlaceIndex < sourcePlaces.length; sourcePlaceIndex++) {
			Place   sourcePlace        = sourcePlaces[sourcePlaceIndex];
			boolean hasSupportingPlace = false;

			for (int targetPlaceIndex = 0; targetPlaceIndex < targetPlaces.length; targetPlaceIndex++) {
				if (isSemanticEqual(sourcePlace, targetPlaces[targetPlaceIndex], isStrictMatch)) {
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

	private boolean isSemanticEqual(Place source, Place target, boolean isStrictMatch) {
		return source.getMark() <= target.getMark()
			&& (!isStrictMatch || (source.getMark() == target.getMark())) 
		    && source.getIncomingArcs().size() <= target.getIncomingArcs().size()
	   	    && source.getOutgoingArcs().size() <= target.getOutgoingArcs().size()
	   		&& source.getName().equals(target.getName());
	}

	private boolean isSemanticEqual(Transition source, Transition target) {
		boolean isSemanticEqual = source.getName().equals(target.getName())
			&& source.getTlb().equals(target.getTlb())
		    && source.getRnw().equals(target.getRnw())
		    && source.getIncomingArcs().size() == target.getIncomingArcs().size()
		    && source.getOutgoingArcs().size() == target.getOutgoingArcs().size();

		if (!isSemanticEqual) {
			return false;
		}

		return true;
	}


	private boolean assertValidState() {
		assert coreNodesCount == coreSourcePlacesCount + coreSourceTransitionsCount;
		assert coreNodesCount == coreTargetPlacesCount + coreTargetTransitionsCount;

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

		assert inSourcePlacesCount >= coreSourcePlacesCount;
		assert inSourceTransitionsCount >= coreSourceTransitionsCount;
		assert outSourcePlacesCount >= coreSourcePlacesCount;
		assert outSourceTransitionsCount >= coreSourceTransitionsCount;

		assert inTargetPlacesCount >= coreTargetPlacesCount;
		assert inTargetTransitionsCount >= coreTargetTransitionsCount;
		assert outTargetPlacesCount >= coreTargetPlacesCount;
		assert outTargetTransitionsCount >= coreTargetTransitionsCount;

		for (int index : sourcePlacesIndexes.values()) {
			assert index == sourcePlacesIndexes.get(sourcePlaces[index]);
		}

		for (int index : sourceTransitionsIndexes.values()) {
			assert index == sourceTransitionsIndexes.get(sourceTransitions[index]);
		}

		for (int index : targetPlacesIndexes.values()) {
			assert index == targetPlacesIndexes.get(targetPlaces[index]);
		}

		for (int index : targetTransitionsIndexes.values()) {
			assert index == targetTransitionsIndexes.get(targetTransitions[index]);
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
	
	private final class DefaultMatchVisitor implements MatchVisitor {		
		public boolean visit(Match match) {
			return true;
		}
	}
}
