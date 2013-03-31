package transformation.matcher;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497b;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import transformation.Match;


/**
 * @author  Mathias Blumreiter
 * @version 1.0
 * @see <a href="http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=712014">VF: Graph Matching: a Fast Algorithm and its Evaluation</a>
 * @see <a href="http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=797762">VF: Performance evaluation of the VF graph matching algorithm</a>
 * @see <a href="http://nerone.diiie.unisa.it/zope/mivia/databases/db_database/1/papers/vf-algorithm.pdf">VF2: An Improved Algorithm for Matching Large Graphs</a>
 * @see <a href="http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=1323804">VF2: A (Sub)Graph Isomorphism Algorithm for Matching Large Graphs</a>
 * @see <a href="http://amalfi.dis.unina.it/graph/db/vflib-2.0/">VF*: vflib-2.0 in C++</a>
 */
public final class VF2 {
	/**
	 * The default MatchVisitor, which is used when no specific one is given.
	 * This visitor accepts the first found match and finishes the matching process.
	 */
	private final MatchVisitor ACCEPT_FIRST_MATCH_VISITOR = new AcceptFirstMatchVisitor();

	/**
	 * This PRNG is used to realize the nondeterminism of the matching process. 
	 */
	private final RandomGenerator RANDOM;
	
	/**
	 * This PRNG is used over all instances, where no specific PRNG is given, and isn't intended 
	 * for local use (except of getInstance).
	 */
	private static final RandomGenerator DEFAULT_RANDOM = getDefaultRandomGenerator();
	
	/**
	 * Indicates a not matched node in the core arrays (coreSource.., soreTarget...). 
	 */
	private final int CORE_NULL_NODE = Integer.MAX_VALUE;
	
	/**
	 * Indicates a node, which isn't currently contained in the terminal sets of 
	 * the respective partial mapped petrinets.
	 */
	private final byte SET_NULL_VALUE = 0;

	/**
	 * Index of the terminal out nodes count in the array that is returned from the feasibility test sub-methods.
	 */
	private final byte INDEX_TERMINAL_OUT = 0;

	/**
	 * Index of the terminal in nodes count in the array that is returned from the feasibility test sub-methods.
	 */
	private final byte INDEX_TERMINAL_IN = 1;

	/**
	 * Index of new nodes count in the array that is returned from the feasibility test sub-methods.
	 */
	private final byte INDEX_NEW_PAIR = 2;

	/**
	 * Petrinet, which is to be found in the target net. The source net can be of the same size 
	 * (graph isomorphism) or smaller (subgraph isomorphism). Size means the number of places and transitions.
	 */
	private final Petrinet source;
	
	/**
	 * Petrinet in which a (subgraph) isomorphism is to be found.
	 */
	private final Petrinet target;


	/**
	 * Matrix, which indicates the semantic equality of 2 arbitrary places. Semantic equality concerns the 
	 * marking and other place "labels". Structurally equality is checked first by execution of the matcher. 
	 * Therefore this matrix gives no clue about structural equality. Changes of this array are only allowed
	 * by the intialize method.
	 */
	private boolean[][] semanticMatrixPlace;

	/**
	 * Matrix, which indicates the semantic equality of 2 arbitrary transitions. Semantic equality concerns 
	 * the tlb and other transition "labels". Structurally equality is checked first by execution of the matcher. 
	 * Therefore this matrix gives no clue about structural equality. Changes of this array are only allowed
	 * by the intialize method.
	 */
	private boolean[][] semanticMatrixTransition;

	/**
	 * Array over all places of the source petrinet. The index of a place in this array is the value, which is 
	 * used in the core-/out- and in-arrays. Furthermore, the order of places in this array realizes the 
	 * order relation, which is needed by vf2. Changes of this array are only allowed by the intialize method.
	 */
	private Place[]  sourcePlaces;

	/**
	 * Array over all places of the target petrinet. The index of a place in this array is the value, which is 
	 * used in the core-/out- and in-arrays. Furthermore, the order of places in this array realizes the 
	 * order relation, which is needed by vf2. Changes of this array are only allowed by the intialize method.
	 */
	private Place[] targetPlaces;

	/**
	 * Array over all transitions of the source petrinet. The index of a transition in this array is the value, 
	 * which is used in the core-/out- and in-arrays. Furthermore, the order of transitions in this array 
	 * realizes the order relation, which is needed by vf2. Changes of this array are only allowed by 
	 * the intialize method.
	 */
	private Transition[] sourceTransitions;

	/**
	 * Array over all transitions of the target petrinet. The index of a transition in this array is the value, 
	 * which is used in the core-/out- and in-arrays. Furthermore, the order of transitions in this array 
	 * realizes the order relation, which is needed by vf2. Changes of this array are only allowed 
	 * by the intialize method.
	 */
	private Transition[] targetTransitions;

	/**
	 * Reverse mapping in reference to the sourcePlaces array. With place as key and the corresponding index 
	 * as value. Changes of this map are only allowed by the intialize method. 
	 */
	private Map<Place, Integer> sourcePlacesIndexes;

	/**
	 * Reverse mapping in reference to the targetPlaces array. With place as key and the corresponding index 
	 * as value. Changes of this map are only allowed by the intialize method.
	 */
	private Map<Place, Integer> targetPlacesIndexes;

	/**
	 * Reverse mapping in reference to the sourceTransitions array. With transition as key and the corresponding 
	 * index as value. Changes of this map are only allowed by the intialize method. 
	 */
	private Map<Transition, Integer> sourceTransitionsIndexes;

	/**
	 * Reverse mapping in reference to the targetTransitions array. With transition as key and the corresponding 
	 * index as value. Changes of this map are only allowed by the intialize method.
	 */
	private Map<Transition, Integer> targetTransitionsIndexes;


	/**
	 * indicates for a source place whether the target place has to have the exact the same number of 
	 * pre arcs and post arcs  
	 */
	private boolean[] arcRestrictedSourcePlaces;
	
	
	/**
	 * Number of nodes, which were mapped so far. This includes places and transitions.
	 * This number is equivalent to the recursion depth and is used by the terminal sets.
	 */
	private int coreNodesCount;

	/**
	 * Mapping of source petrinet places to target petrinet places. The index of a source place is equivalent 
	 * to the index in sourcePlaces (of the same place). The value is the index of the target place in targetPlaces
	 * or CORE_NULL_NODE, if the source place isn't mapped so far.
	 */
	private int[] coreSourcePlaces;

	/**
	 * Mapping of source petrinet transition to target petrinet transitions. The index of a source transition 
	 * is equivalent to the index in sourceTransitions (of the same transition). The value is the index of the target 
	 * transition in targetTransitions or CORE_NULL_NODE, if the source transition isn't mapped so far.
	 */
	private int[] coreSourceTransitions;

	/**
	 * Mapping of target petrinet places to source petrinet places. The index of a target place is equivalent 
	 * to the index in targetPlaces (of the same place). The value is the index of the source place in sourcePlaces
	 * or CORE_NULL_NODE, if the target place isn't mapped so far.
	 */
	private int[] coreTargetPlaces;

	/**
	 * Mapping of target petrinet transition to source petrinet transitions. The index of a target transition 
	 * is equivalent to the index in targetTransitions (of the same transition). The value is the index of the source
	 * transition in sourceTransitions or CORE_NULL_NODE, if the target transition isn't mapped so far.
	 */
	private int[] coreTargetTransitions;
	
	/**
	 * Number of places, which were mapped so far. This value corresponds to the number of places in coreSourcePlaces
	 * and coreTargetPlaces, which are not equal to CORE_NULL_NODE. 
	 */
	private int corePlacesCount;

	/**
	 * Number of transitions, which were mapped so far. This value corresponds to the number of places in 
	 * coreSourceTransitions and coreTargetTransitions, which are not equal to CORE_NULL_NODE. 
	 */
	private int coreTransitionsCount;

	/**
	 * "Out terminal set" of the source petrinet places. The index of a place is equivalent to the corresponding index in 
	 * sourcePlaces (of the same place). The value corresponds to the depth of recursion where the place has been entered 
	 * in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] outSourcePlaces;

	/**
	 * "Out terminal set" of the source petrinet transitions. The index of a transition is equivalent to the corresponding 
	 * index in sourceTransitions (of the same transition). The value corresponds to the depth of recursion where the 
	 * transition has been entered in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] outSourceTransitions;

	/**
	 * "Out terminal set" of the target petrinet places. The index of a place is equivalent to the corresponding index in 
	 * targetPlaces (of the same place). The value corresponds to the depth of recursion where the place has been entered 
	 * in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] outTargetPlaces;

	/**
	 * "Out terminal set" of the target petrinet transitions. The index of a transition is equivalent to the corresponding 
	 * index in targetTransitions (of the same transition). The value corresponds to the depth of recursion where the 
	 * transition has been entered in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] outTargetTransitions;

	/**
	 * Number of source places, which are in the "out terminal set" so far. This value corresponds to the number of places in 
	 * outSourcePlaces, which are not equal to SET_NULL_VALUE. 
	 */
	private int outSourcePlacesCount;

	/**
	 * Number of source transitions, which are in the "out terminal set" so far. This value corresponds to the number of 
	 * transitions in outSourceTransitions, which are not equal to SET_NULL_VALUE. 
	 */
	private int outSourceTransitionsCount;

	/**
	 * Number of target places, which are in the "out terminal set" so far. This value corresponds to the number of places in 
	 * outTargetPlaces, which are not equal to SET_NULL_VALUE. 
	 */
	private int outTargetPlacesCount;

	/**
	 * Number of target transitions, which are in the "out terminal set" so far. This value corresponds to the number of 
	 * transitions in outTargetTransitions, which are not equal to SET_NULL_VALUE. 
	 */
	private int outTargetTransitionsCount;

	/**
	 * "In terminal set" of the source petrinet places. The index of a place is equivalent to the corresponding index in 
	 * sourcePlaces (of the same place). The value corresponds to the depth of recursion where the place has been entered 
	 * in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] inSourcePlaces;
	
	/**
	 * "In terminal set" of the source petrinet transitions. The index of a transition is equivalent to the corresponding 
	 * index in sourceTransitions (of the same transition). The value corresponds to the depth of recursion where the 
	 * transition has been entered in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] inSourceTransitions;	

	/**
	 * "In terminal set" of the target petrinet places. The index of a place is equivalent to the corresponding index in 
	 * targetPlaces (of the same place). The value corresponds to the depth of recursion where the place has been entered 
	 * in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] inTargetPlaces;
	
	/**
	 * "In terminal set" of the target petrinet transitions. The index of a transition is equivalent to the corresponding 
	 * index in targetTransitions (of the same transition). The value corresponds to the depth of recursion where the 
	 * transition has been entered in the terminal set or SET_NULL_VALUE otherwise. 
	 */
	private int[] inTargetTransitions;

	/**
	 * Number of source places, which are in the "in terminal set" so far. This value corresponds to the number of places in 
	 * inSourcePlaces, which are not equal to SET_NULL_VALUE. 
	 */
	private int inSourcePlacesCount;

	/**
	 * Number of source transitions, which are in the "in terminal set" so far. This value corresponds to the number of 
	 * transitions in inSourceTransitions, which are not equal to SET_NULL_VALUE. 
	 */
	private int inSourceTransitionsCount;

	/**
	 * Number of target places, which are in the "in terminal set" so far. This value corresponds to the number of places in 
	 * inTargetPlaces, which are not equal to SET_NULL_VALUE. 
	 */
	private int inTargetPlacesCount;

	/**
	 * Number of target transitions, which are in the "in terminal set" so far. This value corresponds to the number of 
	 * transitions in inTargetTransitions, which are not equal to SET_NULL_VALUE. 
	 */
	private int inTargetTransitionsCount;

	/**
	 * The last match, which was found by getMatch. The value is always a correct match or null, if no match was found or
	 * the visitor rejected to all matches. Changes of this value are only allowed by the intialize and match methods.
	 * In addition, the direct external is prohibited. 
	 */
	private Match lastMatch;
	
	/**
	 * Match visitor, which will be called, if a match is found. 
	 */
	private MatchVisitor matchVisitor;
	

	private VF2(Petrinet source, Petrinet target, RandomGenerator random) {
		this.source = source;
		this.target = target;
		this.RANDOM = random;
	}

	/**
	 * Gets an instance of VF2 to search for matches from source in target.
	 * This instance uses a shared PRNG with other instances. The given petrinets can be modified later, 
	 * as long as no matching process is in progress (getMatch). 
	 * But it is forbidden to change the nets by a match visitor.
	 *   
	 * @param  source  petrinet, from which a match in target should be found 
	 * @param  target  petrinet, in which a match will be searched
	 * @return 
	 * @throws IllegalArgumentException if a parameter is null
	 */
	public static VF2 getInstance(Petrinet source, Petrinet target) {
		return getInstance(source, target, DEFAULT_RANDOM);
	}


	/**
	 * Gets an instance of VF2 to search for matches from source in target.
	 * The given petrinets can be modified later, as long as no matching process is in progress (getMatch). 
	 * But it is forbidden to change the nets by a match visitor.
	 *   
	 * @param  source  petrinet, from which a match in target should be found 
	 * @param  target  petrinet, in which a match will be searched
	 * @param  random  PRNG, which will be used for the nondeterminism of this instance
	 * @return 
	 * @throws IllegalArgumentException if a parameter is null
	 */
	public static VF2 getInstance(Petrinet source, Petrinet target, RandomGenerator random) {
		if (source == null || target == null || random == null) {
			throw new IllegalArgumentException();
		}

		return new VF2(source, target, random);
	}

	

	/**
	 * initializes all local variables with the current state of source and target.
	 * 
	 * @param  isStrictMatch              	 indicates, that the marking of places in source and target have to be equal
	 * @param  arcRestrictedSourcePlacesSet  places in source, where the places in target have to 
	 *                                  	 have exact the same number of pre arcs and post arcs  
	 * @throws MatchException 		      	 if no match can be found
	 */
	private void initialize(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlacesSet) throws MatchException {
		int sourcePlacesCount      = source.getPlaces().size();
		int sourceTransitionsCount = source.getTransitions().size();
		int targetPlacesCount      = target.getPlaces().size();
		int targetTransitionsCount = target.getTransitions().size();

		if (sourcePlacesCount > targetPlacesCount || sourceTransitionsCount > targetTransitionsCount) {
			throw new MatchException();
		}

		semanticMatrixPlace        = new boolean[sourcePlacesCount][targetPlacesCount];
		semanticMatrixTransition   = new boolean[sourceTransitionsCount][targetTransitionsCount];

		arcRestrictedSourcePlaces  = new boolean[sourcePlacesCount];
		
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
		corePlacesCount 	       = 0;
		coreTransitionsCount       = 0;

		outSourcePlaces 	  	   = new int[sourcePlacesCount];
		outSourceTransitions  	   = new int[sourceTransitionsCount];
		outTargetPlaces       	   = new int[targetPlacesCount];
		outTargetTransitions  	   = new int[targetTransitionsCount];
		outSourcePlacesCount 	   = 0;
		outSourceTransitionsCount  = 0;
		outTargetPlacesCount 	   = 0;
		outTargetTransitionsCount  = 0;

		inSourcePlaces 		  	   = new int[sourcePlacesCount];
		inSourceTransitions   	   = new int[sourceTransitionsCount];
		inTargetPlaces 		  	   = new int[targetPlacesCount];
		inTargetTransitions   	   = new int[targetTransitionsCount];
		inSourcePlacesCount 	   = 0;
		inSourceTransitionsCount   = 0;
		inTargetPlacesCount 	   = 0;
		inTargetTransitionsCount   = 0;

		lastMatch 				   = null;
		matchVisitor               = null;

		initPlacesArray(sourcePlaces, source);
		initTransitionsArray(sourceTransitions, source);
		initPlacesArray(targetPlaces, target);
		initTransitionsArray(targetTransitions, target);

		// shuffle arrays to improve the nondeterminism
		// the order of the nodes isn't important for the correctness 
		// of the matching algorithm
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
		
		if (!generateSemanticMatrixPlaces(isStrictMatch, arcRestrictedSourcePlacesSet) 
		 || !generateSemanticMatrixTransitions()) {
			throw new MatchException();
		}
	}

	/**
	 * gets the first found match from source in target.
	 * 
	 * @param  isStrictMatch  indicates, that the marking of places in source and target have to be equal
	 * @return				  the found match, always != null
	 * @throws MatchException if no match can be found
	 */
	public Match getMatch(boolean isStrictMatch) throws MatchException {
		return getMatch(isStrictMatch, new HashSet<Place>(), ACCEPT_FIRST_MATCH_VISITOR);
	}

	/**
	 * gets the first found arc restricted match from source in target.
	 * 
	 * @param  isStrictMatch              indicates, that the marking of places in source and target have to be equal
	 * @param  arcRestrictedSourcePlaces  places in source, where the places in target have to 
	 *                                    have exact the same number of pre arcs and post arcs  
	 * @return				 			  the found match, always != null
	 * @throws MatchException 			  if no match can be found
	 * @throws IllegalArgumentException   if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlaces) throws MatchException {
		return getMatch(isStrictMatch, arcRestrictedSourcePlaces, ACCEPT_FIRST_MATCH_VISITOR);
	}

	/**
	 * gets the first found match from source in target, which the visitor accepts.
	 * 
	 * @param  isStrictMatch             indicates, that the marking of places in source and target have to be equal
	 * @param  visitor  				 visitor (callback) for checking matches, which are already structural and semantical correct,  
	 *  								 the visitor can accept or reject these matches 
	 * @return				 			 the found match, always != null
	 * @throws MatchException 			 if no match can be found or no match satisfies the visitor
	 * @throws IllegalArgumentException  if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, MatchVisitor visitor) throws MatchException {
		return getMatch(isStrictMatch, new HashSet<Place>(), visitor);
	}


	/**
	 * gets the first found arc restricted match from source in target, which the visitor accepts.
	 * 
	 * @param  isStrictMatch             indicates, that the marking of places in source and target have to be equal
	 * @param  arcRestrictedSourcePlaces places in source, where the places in target have to 
	 *                                   have exact the same number of pre arcs and post arcs  
	 * @param  visitor  				 visitor (callback) for checking matches, which are already structural and semantical correct,  
	 *  								 the visitor can accept or reject these matches 
	 * @return				 			 the found match, always != null
	 * @throws MatchException 			 if no match can be found or no match satisfies the visitor
	 * @throws IllegalArgumentException  if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlaces, MatchVisitor visitor) throws MatchException {
		if (arcRestrictedSourcePlaces == null || visitor == null) {
			throw new IllegalArgumentException();
		}

		initialize(isStrictMatch, arcRestrictedSourcePlaces);
		matchVisitor = visitor;

		if (!match()) {
			throw new MatchException();
		}

		return lastMatch;
	}

	/**
	 * gets the first found match from source in target, based on the given partial match.
	 * The resulting match contains the (checked) partial match and is extended by the missing nodes.
	 * 
	 * @param  isStrictMatch  			 indicates, that the marking of places in source and target have to be equal
	 * @param  partialMatch				 partialMatch, that the resulting match has to contain, 
	 * 									 the pre and post arcs mapping will be ignored
	 * @return				  			 the found match, always != null
	 * @throws MatchException 			 if no match can be found
	 * @throws IllegalArgumentException  if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, Match partialMatch) throws MatchException {
		return getMatch(isStrictMatch, partialMatch, new HashSet<Place>(), ACCEPT_FIRST_MATCH_VISITOR);
	}

	/**
	 * gets the first found arc restricted match from source in target, based on the given partial match.
	 * The resulting match contains the (checked) partial match and is extended  by the missing nodes.
	 * 
	 * @param  isStrictMatch  			 indicates, that the marking of places in source and target have to be equal
	 * @param  partialMatch				 partialMatch, that the resulting match has to contain, 
	 * 									 the pre and post arcs mapping will be ignored
	 * @param  arcRestrictedSourcePlaces places in source, where the places in target have to 
	 *                                   have exact the same number of pre arcs and post arcs 
	 * @return				  			 the found match, always != null
	 * @throws MatchException 			 if no match can be found
	 * @throws IllegalArgumentException  if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, Match partialMatch, Set<Place> arcRestrictedSourcePlaces) throws MatchException {
		return getMatch(isStrictMatch, partialMatch, arcRestrictedSourcePlaces, ACCEPT_FIRST_MATCH_VISITOR);
	}

	/**
	 * gets the first found match from source in target, based on the given partial match, which the visitor accepts.
	 * The resulting match contains the (checked) partial match and is extended  by the missing nodes.
	 * 
	 * @param  isStrictMatch  			 indicates, that the marking of places in source and target have to be equal
	 * @param  partialMatch				 partialMatch, that the resulting match has to contain, 
	 * 									 the pre and post arcs mapping will be ignored
	 * @param  visitor  				 visitor (callback) for checking matches, which are already structural and semantical correct,  
	 *  								 the visitor can accept or reject these matches
	 * @return				  			 the found match, always != null
	 * @throws MatchException 			 if no match can be found or no match satisfies the visitor
	 * @throws IllegalArgumentException  if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, Match partialMatch, MatchVisitor visitor) throws MatchException {
		return getMatch(isStrictMatch, partialMatch, new HashSet<Place>(), visitor);
	}

	/**
	 * gets the first found arc restricted match from source in target, based on the given partial match, which the visitor accepts.
	 * The resulting match contains the (checked) partial match and is extended by the missing nodes.
	 * 
	 * @param  isStrictMatch  			 indicates, that the marking of places in source and target have to be equal
	 * @param  partialMatch				 partialMatch, that the resulting match has to contain, 
	 * 									 the pre and post arcs mapping will be ignored
	 * @param  arcRestrictedSourcePlaces places in source, where the places in target have to 
	 *                                   have exact the same number of pre arcs and post arcs
	 * @param  visitor  				 visitor (callback) for checking matches, which are already structural and semantical correct,  
	 *  								 the visitor can accept or reject these matches
	 * @return				  			 the found match, always != null
	 * @throws MatchException 			 if no match can be found or no match satisfies the visitor
	 * @throws IllegalArgumentException  if a parameter is null
	 */
	public Match getMatch(boolean isStrictMatch, Match partialMatch, Set<Place> arcRestrictedSourcePlaces, MatchVisitor visitor) throws MatchException {
		if (partialMatch == null || arcRestrictedSourcePlaces == null || visitor == null) {
			throw new IllegalArgumentException();
		}

		initialize(isStrictMatch, arcRestrictedSourcePlaces);
		matchVisitor = visitor;

		for (Map.Entry<Place, Place> mapping : partialMatch.getPlaces().entrySet()) {
			Integer sourceIndex = sourcePlacesIndexes.get(mapping.getKey());
			Integer targetIndex = targetPlacesIndexes.get(mapping.getValue());

			if (sourceIndex == null || targetIndex == null || !isFeasiblePlace(sourceIndex, targetIndex)) {
				throw new MatchException();
			}

			addPlacePair(sourceIndex, targetIndex);
		}

		for (Map.Entry<Transition, Transition> mapping : partialMatch.getTransitions().entrySet()) {
			Integer sourceIndex = sourceTransitionsIndexes.get(mapping.getKey());
			Integer targetIndex = targetTransitionsIndexes.get(mapping.getValue());

			if (sourceIndex == null || targetIndex == null || !isFeasibleTransition(sourceIndex, targetIndex)) {
				throw new MatchException();
			}

			addTransitionPair(sourceIndex, targetIndex);
		}

		assert partialMatch.getPlaces().size()      == corePlacesCount;
		assert partialMatch.getTransitions().size() == coreTransitionsCount;

		if (!match()) {
			throw new MatchException();
		}

		return lastMatch;
	}

	/**
	 * performs the recursive matching process, based on the current matching state (local variables).
	 * The valid match, which leads to termination, is stored in lastMatch.
	 * If no match is found, lastMatch is null.
	 *  
	 * @return true, if a valid match was found
	 */
	private boolean match() {
		assert assertValidState();

		if (corePlacesCount      == coreSourcePlaces.length
		 && coreTransitionsCount == coreSourceTransitions.length) {
			lastMatch   = null;
			Match match = buildMatch();

			if (matchVisitor.visit(match)) {
				lastMatch = match;
				return true;				
			} else {
				return false;
			}
		}

		assert coreNodesCount != coreSourcePlaces.length + coreSourceTransitions.length;

		if (hasOutPairs()) {
			if (isMatchByPlacePairs(getOutPlacePairsCount(), getOutTransitionPairsCount())) {
				return matchPlace(NEXT_CANDIDATE_SET.TERMINAL_OUT);
			} else {
				return matchTransition(NEXT_CANDIDATE_SET.TERMINAL_OUT);
			}

		} else if (hasInPairs()) {
			if (isMatchByPlacePairs(getInPlacePairsCount(), getInTransitionPairsCount())) {
				return matchPlace(NEXT_CANDIDATE_SET.TERMINAL_IN);
			} else {
				return matchTransition(NEXT_CANDIDATE_SET.TERMINAL_IN);
			}

		} else {
			if (isMatchByPlacePairs(getPlacePairsCount(), getTransitionPairsCount())) {
				return matchPlace(NEXT_CANDIDATE_SET.NEW);
			} else {
				return matchTransition(NEXT_CANDIDATE_SET.NEW);
			}
		}
	}

	/**
	 * to provide nondeterminism in the matching process, this method decides randomly, if places 
	 * or transitions should be used.   
	 * 
	 * @param  placePairsCount       number of potential place candidate pairs
	 * @param  transitionPairsCount  number of potential transition candidate pairs
	 * @return true, if the place pairs should be used
	 */
	private boolean isMatchByPlacePairs(int placePairsCount, int transitionPairsCount) {
		return placePairsCount > 0 && (transitionPairsCount == 0 
            || RANDOM.nextInt(placePairsCount + transitionPairsCount) < placePairsCount);
	}

	/**
	 * performs the recursive matching process, starting  with valid place pairs matches
	 * 
	 * @param  nextCandidateSet	indicates which candidate set should be used for the generation of new candidates
	 *   
	 * @return true, if a valid match was found
	 */
	private boolean matchPlace(NEXT_CANDIDATE_SET nextCandidateSet) {
		int sourceIndex = getFirstNotMatchedSourcePlaceIndex(nextCandidateSet);
		int targetIndex = getNextNotMatchedTargetPlaceIndex(CORE_NULL_NODE, nextCandidateSet);
		
		if (sourceIndex == CORE_NULL_NODE || targetIndex == CORE_NULL_NODE) {
			return false;
		}
		
		while (targetIndex != CORE_NULL_NODE) {			
			if (isFeasiblePlace(sourceIndex, targetIndex)) {
				addPlacePair(sourceIndex, targetIndex);

				if (match()) {
					return true;
				}

				backtrackPlacePair(sourceIndex, targetIndex);
			}

			targetIndex = getNextNotMatchedTargetPlaceIndex(targetIndex, nextCandidateSet);	
		}

		return false;
	}

	/**
	 * performs the recursive matching process, starting  with valid transition pairs matches
	 * 
	 * @param  nextCandidateSet	indicates which candidate set should be used for the generation of new candidates
	 *   
	 * @return true, if a valid match was found
	 */
	private boolean matchTransition(NEXT_CANDIDATE_SET nextCandidateSet) {
		int sourceIndex = getFirstNotMatchedSourceTransitionIndex(nextCandidateSet);
		int targetIndex = getNextNotMatchedTargetTransitionIndex(CORE_NULL_NODE, nextCandidateSet);
		
		if (sourceIndex == CORE_NULL_NODE || targetIndex == CORE_NULL_NODE) {
			return false;
		}
		
		
		while (targetIndex != CORE_NULL_NODE) {			
			if (isFeasibleTransition(sourceIndex, targetIndex)) {
				addTransitionPair(sourceIndex, targetIndex);

				if (match()) {
					return true;
				}

				backtrackTransitionPair(sourceIndex, targetIndex);
			}

			targetIndex = getNextNotMatchedTargetTransitionIndex(targetIndex, nextCandidateSet);	
		}

		return false;
	}

	/**
	 * gets the index of the first not matched source place, which fulfills the condition implied by the 
	 * given candidate set. 
	 *                            							  
	 * @param  nextCandidateSet     the candidate set, to be searched
	 * 
	 * @return index of the first not matched place or CORE_NULL_NODE, if no not matched place exists 
	 */
	private int getFirstNotMatchedSourcePlaceIndex(NEXT_CANDIDATE_SET nextCandidateSet) {	
		assert nextCandidateSet != null;
		
		if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_OUT) {
			return getNotMatchedNodeIndex(coreSourcePlaces, outSourcePlaces, 0);
		} else if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_IN) {
			return getNotMatchedNodeIndex(coreSourcePlaces, inSourcePlaces, 0);
		} else {
			return getNotMatchedNodeIndex(coreSourcePlaces, 0);
		}		
	}


	/**
	 * gets the index of the next not matched target place, which fulfills the condition implied by the 
	 * given candidate set. 
	 * 
	 * @param  previousTargetIndex  index of the previously found place, from which a new not matched one 
	 *   							should be searched or CORE_NULL_NODE to indicate, that a new search is to be started                                							  
	 * @param  nextCandidateSet     the candidate set, to be searched
	 * 
	 * @return index of the next not matched place (> previousTargetIndex) or CORE_NULL_NODE, 
	 *         if all places are exhausted
	 */
	private int getNextNotMatchedTargetPlaceIndex(int previousTargetIndex, NEXT_CANDIDATE_SET nextCandidateSet) {
		assert nextCandidateSet != null;
		
		int searchStartIndex = (previousTargetIndex == CORE_NULL_NODE) ? 0 : previousTargetIndex + 1;

		if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_OUT) {
			return getNotMatchedNodeIndex(coreTargetPlaces, outTargetPlaces, searchStartIndex);
		} else if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_IN) {
			return getNotMatchedNodeIndex(coreTargetPlaces, inTargetPlaces, searchStartIndex);
		} else {
			return getNotMatchedNodeIndex(coreTargetPlaces, searchStartIndex);
		}	
	}

	/**
	 * gets the index of the first not matched source transition, which fulfills the condition implied by the 
	 * given candidate set. 
	 *                            							  
	 * @param  nextCandidateSet     the candidate set, to be searched
	 * 
	 * @return index of the first not matched transition or CORE_NULL_NODE, if no not matched transition exists 
	 */
	private int getFirstNotMatchedSourceTransitionIndex(NEXT_CANDIDATE_SET nextCandidateSet) {	
		assert nextCandidateSet != null;
		
		if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_OUT) {
			return getNotMatchedNodeIndex(coreSourceTransitions, outSourceTransitions, 0);
		} else if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_IN) {
			return getNotMatchedNodeIndex(coreSourceTransitions, inSourceTransitions, 0);
		} else {
			return getNotMatchedNodeIndex(coreSourceTransitions, 0);
		}		
	}

	/**
	 * gets the index of the next not matched target transition, which fulfills the condition implied by the 
	 * given candidate set. 
	 * 
	 * @param  previousTargetIndex  index of the previously found transition, from which a new not matched one 
	 *   							should be searched or CORE_NULL_NODE to indicate, that a new search is to be started                                							  
	 * @param  nextCandidateSet     the candidate set, to be searched
	 * 
	 * @return index of the next not matched transition (> previousTargetIndex) or CORE_NULL_NODE, 
	 *         if all transitions are exhausted
	 */
	private int getNextNotMatchedTargetTransitionIndex(int previousTargetIndex, NEXT_CANDIDATE_SET nextCandidateSet) {
		assert nextCandidateSet != null;
		
		int searchStartIndex = (previousTargetIndex == CORE_NULL_NODE) ? 0 : previousTargetIndex + 1;

		if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_OUT) {
			return getNotMatchedNodeIndex(coreTargetTransitions, outTargetTransitions, searchStartIndex);
		} else if (nextCandidateSet == NEXT_CANDIDATE_SET.TERMINAL_IN) {
			return getNotMatchedNodeIndex(coreTargetTransitions, inTargetTransitions, searchStartIndex);
		} else {
			return getNotMatchedNodeIndex(coreTargetTransitions, searchStartIndex);
		}	
	}

	/**
	 * adds the match between the transitions with sourceIndex and targetIndex to the current partial match
	 * and updates the local state (all relevant local variables).
	 * 
	 * @param sourceIndex  index of the source transition (from the source petrinet)
	 * @param targetIndex  index of the target transition (from the target petrinet)
	 */
	private void addTransitionPair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE && coreSourceTransitions[sourceIndex] == CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE && coreTargetTransitions[targetIndex] == CORE_NULL_NODE;

		coreTransitionsCount++;
		coreNodesCount++;

		if (outSourceTransitions[sourceIndex] == SET_NULL_VALUE) {
			outSourceTransitions[sourceIndex] = coreNodesCount;
			outSourceTransitionsCount++;
		}

		if (inSourceTransitions[sourceIndex] == SET_NULL_VALUE) {
			inSourceTransitions[sourceIndex] = coreNodesCount;
			inSourceTransitionsCount++;
		}

		if (outTargetTransitions[targetIndex] == SET_NULL_VALUE) {
			outTargetTransitions[targetIndex] = coreNodesCount;
			outTargetTransitionsCount++;
		}

		if (inTargetTransitions[targetIndex] == SET_NULL_VALUE) {
			inTargetTransitions[targetIndex] = coreNodesCount;
			inTargetTransitionsCount++;
		}

		coreSourceTransitions[sourceIndex] = targetIndex;
		coreTargetTransitions[targetIndex] = sourceIndex;


		Transition source = sourceTransitions[sourceIndex];
		Transition target = targetTransitions[targetIndex];

		for (PostArc arc : source.getOutgoingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (outSourcePlaces[placeIndex] == SET_NULL_VALUE) {
				outSourcePlaces[placeIndex] = coreNodesCount;
				outSourcePlacesCount++;
			}
		}

		for (PreArc arc : source.getIncomingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (inSourcePlaces[placeIndex] == SET_NULL_VALUE) {
				inSourcePlaces[placeIndex] = coreNodesCount;
				inSourcePlacesCount++;
			}
		}

		for (PostArc arc : target.getOutgoingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (outTargetPlaces[placeIndex] == SET_NULL_VALUE) {
				outTargetPlaces[placeIndex] = coreNodesCount;
				outTargetPlacesCount++;
			}
		}

		for (PreArc arc : target.getIncomingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (inTargetPlaces[placeIndex] == SET_NULL_VALUE) {
				inTargetPlaces[placeIndex] = coreNodesCount;
				inTargetPlacesCount++;
			}
		}

		assert assertValidState();
	}

	/**
	 * undoes all changes, which were made at the local state (all relevant local variables) 
	 * by adding the match between the transitions with sourceIndex and targetIndex.
	 * 
	 * @param sourceIndex  index of the source transition (from the source petrinet)
	 * @param targetIndex  index of the target transition (from the target petrinet)
	 */
	private void backtrackTransitionPair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE && coreSourceTransitions[sourceIndex] != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE && coreTargetTransitions[targetIndex] != CORE_NULL_NODE;

		Transition source = sourceTransitions[sourceIndex];
		Transition target = targetTransitions[targetIndex];

		if (outSourceTransitions[sourceIndex] == coreNodesCount) {
			outSourceTransitions[sourceIndex] = SET_NULL_VALUE;
			outSourceTransitionsCount--;
		}

		if (inSourceTransitions[sourceIndex] == coreNodesCount) {
			inSourceTransitions[sourceIndex] = SET_NULL_VALUE;
			inSourceTransitionsCount--;

		}

		if (outTargetTransitions[targetIndex] == coreNodesCount) {
			outTargetTransitions[targetIndex] = SET_NULL_VALUE;
			outTargetTransitionsCount--;
		}

		if (inTargetTransitions[targetIndex] == coreNodesCount) {
			inTargetTransitions[targetIndex] = SET_NULL_VALUE;
			inTargetTransitionsCount--;
		}

		for (PostArc arc : source.getOutgoingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (outSourcePlaces[placeIndex] == coreNodesCount) {
				outSourcePlaces[placeIndex] = SET_NULL_VALUE;
				outSourcePlacesCount--;
			}
		}

		for (PreArc arc : source.getIncomingArcs()) {
			int placeIndex = sourcePlacesIndexes.get(arc.getPlace());

			if (inSourcePlaces[placeIndex] == coreNodesCount) {
				inSourcePlaces[placeIndex] = SET_NULL_VALUE;
				inSourcePlacesCount--;
			}
		}

		for (PostArc arc : target.getOutgoingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (outTargetPlaces[placeIndex] == coreNodesCount) {
				outTargetPlaces[placeIndex] = SET_NULL_VALUE;
				outTargetPlacesCount--;
			}
		}

		for (PreArc arc : target.getIncomingArcs()) {
			int placeIndex = targetPlacesIndexes.get(arc.getPlace());

			if (inTargetPlaces[placeIndex] == coreNodesCount) {
				inTargetPlaces[placeIndex] = SET_NULL_VALUE;
				inTargetPlacesCount--;
			}
		}

		coreSourceTransitions[sourceIndex] = CORE_NULL_NODE;
		coreTargetTransitions[targetIndex] = CORE_NULL_NODE;

		coreTransitionsCount--;
		coreNodesCount--;

		assert assertValidState();
	}

	/**
	 * adds the match between the places with sourceIndex and targetIndex to the current partial match
	 * and updates the local state (all relevant local variables).
	 * 
	 * @param sourceIndex  index of the place transition (from the source petrinet)
	 * @param targetIndex  index of the place transition (from the target petrinet)
	 */
	private void addPlacePair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE && coreSourcePlaces[sourceIndex] == CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE && coreTargetPlaces[targetIndex] == CORE_NULL_NODE;

		corePlacesCount++;
		coreNodesCount++;

		if (outSourcePlaces[sourceIndex] == SET_NULL_VALUE) {
			outSourcePlaces[sourceIndex] = coreNodesCount;
			outSourcePlacesCount++;
		}

		if (inSourcePlaces[sourceIndex] == SET_NULL_VALUE) {
			inSourcePlaces[sourceIndex] = coreNodesCount;
			inSourcePlacesCount++;
		}

		if (outTargetPlaces[targetIndex] == SET_NULL_VALUE) {
			outTargetPlaces[targetIndex] = coreNodesCount;
			outTargetPlacesCount++;
		}

		if (inTargetPlaces[targetIndex] == SET_NULL_VALUE) {
			inTargetPlaces[targetIndex] = coreNodesCount;
			inTargetPlacesCount++;
		}

		coreSourcePlaces[sourceIndex] = targetIndex;
		coreTargetPlaces[targetIndex] = sourceIndex;

		Place source = sourcePlaces[sourceIndex];
		Place target = targetPlaces[targetIndex];

		for (PreArc arc : source.getOutgoingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (outSourceTransitions[transitionIndex] == SET_NULL_VALUE) {
				outSourceTransitions[transitionIndex] = coreNodesCount;
				outSourceTransitionsCount++;
			}
		}

		for (PostArc arc : source.getIncomingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (inSourceTransitions[transitionIndex] == SET_NULL_VALUE) {
				inSourceTransitions[transitionIndex] = coreNodesCount;
				inSourceTransitionsCount++;
			}
		}

		for (PreArc arc : target.getOutgoingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (outTargetTransitions[transitionIndex] == SET_NULL_VALUE) {
				outTargetTransitions[transitionIndex] = coreNodesCount;
				outTargetTransitionsCount++;
			}
		}

		for (PostArc arc : target.getIncomingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (inTargetTransitions[transitionIndex] == SET_NULL_VALUE) {
				inTargetTransitions[transitionIndex] = coreNodesCount;
				inTargetTransitionsCount++;
			}
		}

		assert assertValidState();
	}

	/**
	 * undoes all changes, which were made at the local state (all relevant local variables) 
	 * by adding the match between the places with sourceIndex and targetIndex.
	 * 
	 * @param sourceIndex  index of the source place (from the source petrinet)
	 * @param targetIndex  index of the target place (from the target petrinet)
	 */
	private void backtrackPlacePair(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE && coreSourcePlaces[sourceIndex] != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE && coreTargetPlaces[targetIndex] != CORE_NULL_NODE;

		Place source = sourcePlaces[sourceIndex];
		Place target = targetPlaces[targetIndex];

		if (outSourcePlaces[sourceIndex] == coreNodesCount) {
			outSourcePlaces[sourceIndex] = SET_NULL_VALUE;
			outSourcePlacesCount--;
		}

		if (inSourcePlaces[sourceIndex] == coreNodesCount) {
			inSourcePlaces[sourceIndex] = SET_NULL_VALUE;
			inSourcePlacesCount--;
		}

		if (outTargetPlaces[targetIndex] == coreNodesCount) {
			outTargetPlaces[targetIndex] = SET_NULL_VALUE;
			outTargetPlacesCount--;
		}

		if (inTargetPlaces[targetIndex] == coreNodesCount) {
			inTargetPlaces[targetIndex] = SET_NULL_VALUE;
			inTargetPlacesCount--;
		}

		for (PreArc arc : source.getOutgoingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (outSourceTransitions[transitionIndex] == coreNodesCount) {
				outSourceTransitions[transitionIndex] = SET_NULL_VALUE;
				outSourceTransitionsCount--;
			}
		}

		for (PostArc arc : source.getIncomingArcs()) {
			int transitionIndex = sourceTransitionsIndexes.get(arc.getTransition());

			if (inSourceTransitions[transitionIndex] == coreNodesCount) {
				inSourceTransitions[transitionIndex] = SET_NULL_VALUE;
				inSourceTransitionsCount--;
			}
		}

		for (PreArc arc : target.getOutgoingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (outTargetTransitions[transitionIndex] == coreNodesCount) {
				outTargetTransitions[transitionIndex] = SET_NULL_VALUE;
				outTargetTransitionsCount--;
			}
		}

		for (PostArc arc : target.getIncomingArcs()) {
			int transitionIndex = targetTransitionsIndexes.get(arc.getTransition());

			if (inTargetTransitions[transitionIndex] == coreNodesCount) {
				inTargetTransitions[transitionIndex] = SET_NULL_VALUE;
				inTargetTransitionsCount--;
			}
		}

		coreSourcePlaces[sourceIndex] = CORE_NULL_NODE;
		coreTargetPlaces[targetIndex] = CORE_NULL_NODE;

		corePlacesCount--;
		coreNodesCount--;

		assert assertValidState();
	}

	/**
	 * checks whether the given place combination is semantic and structural valid 
	 * with the current partial match.
	 * 
	 * @param  sourceIndex  index of the source place (from the source petrinet)
	 * @param  targetIndex  index of the target place (from the target petrinet)
	 * @return true, if the combination is valid
	 */
	private boolean isFeasiblePlace(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (!semanticMatrixPlace[sourceIndex][targetIndex]) {
			return false;
		}

		Place source = sourcePlaces[sourceIndex];
		Place target = targetPlaces[targetIndex];

	    int[] sourcePredCardinality = new int[3];
	    int[] sourceSuccCardinality = new int[3];
	    int[] targetPredCardinality = new int[3];
	    int[] targetSuccCardinality = new int[3];
	    
	    
	    
		if (!isRulePredPlace(source, target, sourcePredCardinality,  targetPredCardinality)
		 || !isRuleSuccPlace(source, target, sourceSuccCardinality,  targetSuccCardinality)) {
			return false;
		}

	    if (arcRestrictedSourcePlaces[sourceIndex]) {	    
	    	return sourcePredCardinality[INDEX_TERMINAL_IN]  == targetPredCardinality[INDEX_TERMINAL_IN]
	    		&& sourceSuccCardinality[INDEX_TERMINAL_IN]  == targetSuccCardinality[INDEX_TERMINAL_IN]
						 
	    		&& sourcePredCardinality[INDEX_TERMINAL_OUT] == targetPredCardinality[INDEX_TERMINAL_OUT]
	    		&& sourceSuccCardinality[INDEX_TERMINAL_OUT] == targetSuccCardinality[INDEX_TERMINAL_OUT]
						 
	    		&& sourcePredCardinality[INDEX_NEW_PAIR]     == targetPredCardinality[INDEX_NEW_PAIR]
	    		&& sourceSuccCardinality[INDEX_NEW_PAIR]     == targetSuccCardinality[INDEX_NEW_PAIR];	    	
	    	
	    } else {
	    	return sourcePredCardinality[INDEX_TERMINAL_IN]  <= targetPredCardinality[INDEX_TERMINAL_IN]
	    		&& sourceSuccCardinality[INDEX_TERMINAL_IN]  <= targetSuccCardinality[INDEX_TERMINAL_IN]
						 
	    		&& sourcePredCardinality[INDEX_TERMINAL_OUT] <= targetPredCardinality[INDEX_TERMINAL_OUT]
	    		&& sourceSuccCardinality[INDEX_TERMINAL_OUT] <= targetSuccCardinality[INDEX_TERMINAL_OUT]
						 
	    		&& sourcePredCardinality[INDEX_NEW_PAIR]     <= targetPredCardinality[INDEX_NEW_PAIR]
	    		&& sourceSuccCardinality[INDEX_NEW_PAIR]     <= targetSuccCardinality[INDEX_NEW_PAIR];

	    }
	}



	/**
	 * checks whether the matched predecessors transitions for a source place are also matched for 
	 * a target place and vice versa. If they aren't the cardinality array is changed accordingly.
	 * 
	 * @param source				place in N1, match source
	 * @param target				place in N2, match target
	 * @param sourceCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent transitions of the source place,
	 *                      		the values of this array are changed during the execution   
	 * @param targetCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent transitions of the target place,
	 *                      		the values of this array are changed during the execution   
	 * @return true, if the rule is satisfied
	 */
	private boolean isRulePredPlace(Place source, Place target,
			int[] sourceCardinality, int[] targetCardinality) {

		assert sourceCardinality.length == 3;
		assert targetCardinality.length == 3;

		// check from N1 to N2
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
					sourceCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					sourceCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE
						&& outSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE) {
					sourceCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		// check from N2 to N1
		for (PostArc arc : target.getIncomingArcs()) {
			int targetTransitionIndex = targetTransitionsIndexes.get(arc.getTransition());
			int sourceTransitionIndex = coreTargetTransitions[targetTransitionIndex];

			if (sourceTransitionIndex != CORE_NULL_NODE) {
				Transition sourceTransition = sourceTransitions[sourceTransitionIndex];

				if (!source.hasIncomingArc(sourceTransition)
						|| !isSemanticEqual(arc, source.getIncomingArc(sourceTransition))) {
					return false;
				}
			} else {
				if (outTargetTransitions[targetTransitionIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inTargetTransitions[targetTransitionIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inTargetTransitions[targetTransitionIndex] == SET_NULL_VALUE
						&& outTargetTransitions[targetTransitionIndex] == SET_NULL_VALUE) {
					targetCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}
		
		return true;
	}

	/**
	 * checks whether the matched predecessors transitions for a source place are also matched for 
	 * a target place and vice versa. If they aren't the cardinality array is changed accordingly.
	 * 
	 * @param source				place in N1, match source
	 * @param target				place in N2, match target
	 * @param sourceCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent transitions of the source place,
	 *                      		the values of this array are changed during the execution   
	 * @param targetCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent transitions of the target place,
	 *                      		the values of this array are changed during the execution   
	 * @return true, if the rule is satisfied
	 */
	private boolean isRuleSuccPlace(Place source, Place target,
			int[] sourceCardinality, int[] targetCardinality) {

		assert sourceCardinality.length == 3;
		assert targetCardinality.length == 3;

		// check from N1 to N2
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
					sourceCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] > SET_NULL_VALUE) {
					sourceCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE
						&& outSourceTransitions[sourceTransitionIndex] == SET_NULL_VALUE) {
					sourceCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		// check from N2 to N1
		for (PreArc arc : target.getOutgoingArcs()) {
			int targetTransitionIndex = targetTransitionsIndexes.get(arc.getTransition());
			int sourceTransitionIndex = coreTargetTransitions[targetTransitionIndex];

			if (sourceTransitionIndex != CORE_NULL_NODE) {
				Transition sourceTransition = sourceTransitions[sourceTransitionIndex];

				if (!source.hasOutgoingArc(sourceTransition)
						|| !isSemanticEqual(arc, source.getOutgoingArc(sourceTransition))) {
					return false;
				}
			} else {
				if (outTargetTransitions[targetTransitionIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inTargetTransitions[targetTransitionIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inTargetTransitions[targetTransitionIndex] == SET_NULL_VALUE
						&& outTargetTransitions[targetTransitionIndex] == SET_NULL_VALUE) {
					targetCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}
		
		return true;
	}


	/**
	 * checks whether the given transition combination is semantic and structural valid 
	 * with the current partial match.
	 * 
	 * @param  sourceIndex  index of the source transition (from the source petrinet)
	 * @param  targetIndex  index of the target transition (from the target petrinet)
	 * @return true, if the combination is valid
	 */
	private boolean isFeasibleTransition(int sourceIndex, int targetIndex) {
		assert sourceIndex != CORE_NULL_NODE;
		assert targetIndex != CORE_NULL_NODE;

		if (!semanticMatrixTransition[sourceIndex][targetIndex]) {
			return false;
		}

		Transition source = sourceTransitions[sourceIndex];
		Transition target = targetTransitions[targetIndex];

	    int[] sourcePredCardinality = new int[3];
	    int[] sourceSuccCardinality = new int[3];
	    int[] targetPredCardinality = new int[3];
	    int[] targetSuccCardinality = new int[3];

		if (isRulePredTransition(source, target, sourcePredCardinality, targetPredCardinality)
		 && isRuleSuccTransition(source, target, sourceSuccCardinality, targetSuccCardinality)
		 
		 && sourcePredCardinality[INDEX_TERMINAL_IN]  == targetPredCardinality[INDEX_TERMINAL_IN]
		 && sourceSuccCardinality[INDEX_TERMINAL_IN]  == targetSuccCardinality[INDEX_TERMINAL_IN]
				 
		 && sourcePredCardinality[INDEX_TERMINAL_OUT] == targetPredCardinality[INDEX_TERMINAL_OUT]
		 && sourceSuccCardinality[INDEX_TERMINAL_OUT] == targetSuccCardinality[INDEX_TERMINAL_OUT]
				 
		 && sourcePredCardinality[INDEX_NEW_PAIR]     == targetPredCardinality[INDEX_NEW_PAIR]
		 && sourceSuccCardinality[INDEX_NEW_PAIR]     == targetSuccCardinality[INDEX_NEW_PAIR]) {
			return true;
		}

		return false;
	}


	/**
	 * checks whether the matched predecessors places for a source transition are also matched for 
	 * a target transition and vice versa. If they aren't the cardinality array is changed accordingly.
	 * 
	 * @param source				transition in N1, match source
	 * @param target				transition in N2, match target
	 * @param sourceCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent places of the source transition,
	 *                      		the values of this array are changed during the execution   
	 * @param targetCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent places of the source transition,
	 *                      		the values of this array are changed during the execution   
	 * @return true, if the rule is satisfied
	 */
	private boolean isRulePredTransition(Transition source, Transition target,
			int[] sourceCardinality, int[] targetCardinality) {

		assert sourceCardinality.length == 3;
		assert targetCardinality.length == 3;

		// check from N1 to N2
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
					sourceCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					sourceCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE
						&& outSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
					sourceCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		// check from N2 to N1
		for (PreArc arc : target.getIncomingArcs()) {
			int targetPlaceIndex = targetPlacesIndexes.get(arc.getPlace());
			int sourcePlaceIndex = coreTargetPlaces[targetPlaceIndex];

			if (sourcePlaceIndex != CORE_NULL_NODE) {
				Place sourcePlace = sourcePlaces[sourcePlaceIndex];

				if (!source.hasIncomingArc(sourcePlace)
						|| !isSemanticEqual(arc, source.getIncomingArc(sourcePlace))) {
					return false;
				}
			} else {
				if (outTargetPlaces[targetPlaceIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inTargetPlaces[targetPlaceIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inTargetPlaces[targetPlaceIndex] == SET_NULL_VALUE
						&& outTargetPlaces[targetPlaceIndex] == SET_NULL_VALUE) {
					targetCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}
		
		return true;
	}

	/**
	 * checks whether the matched successors places for a source transition are also matched for 
	 * a target transition and vice versa. If they aren't the cardinality array is changed accordingly.
	 * 
	 * @param source				transition in N1, match source
	 * @param target				transition in N2, match target
	 * @param sourceCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent places of the source transition,
	 *                      		the values of this array are changed during the execution   
	 * @param targetCardinality   	cardinalities of the terminal sets, for the 
	 * 								adjacent places of the source transition,
	 *                      		the values of this array are changed during the execution  
	 * @return true, if the rule is satisfied
	 */
	private boolean isRuleSuccTransition(Transition source, Transition target,
			int[] sourceCardinality, int[] targetCardinality) {

		assert sourceCardinality.length == 3;
		assert targetCardinality.length == 3;

		// check from N1 to N2
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
					sourceCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] > SET_NULL_VALUE) {
					sourceCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE
						&& outSourcePlaces[sourcePlaceIndex] == SET_NULL_VALUE) {
					sourceCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}

		// check from N2 to N1
		for (PostArc arc : target.getOutgoingArcs()) {
			int targetPlaceIndex = targetPlacesIndexes.get(arc.getPlace());
			int sourcePlaceIndex = coreTargetPlaces[targetPlaceIndex];

			if (sourcePlaceIndex != CORE_NULL_NODE) {
				Place sourcePlace = sourcePlaces[sourcePlaceIndex];

				if (!source.hasOutgoingArc(sourcePlace)
						|| !isSemanticEqual(arc, source.getOutgoingArc(sourcePlace))) {
					return false;
				}
			} else {
				if (outTargetPlaces[targetPlaceIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_OUT]++;
				}

				if (inTargetPlaces[targetPlaceIndex] > SET_NULL_VALUE) {
					targetCardinality[INDEX_TERMINAL_IN]++;
				}

				if (inTargetPlaces[targetPlaceIndex] == SET_NULL_VALUE
						&& outTargetPlaces[targetPlaceIndex] == SET_NULL_VALUE) {
					targetCardinality[INDEX_NEW_PAIR]++;
				}
			}
		}
		
		return true;
	}

	
	


	/**
	 * initializes the semantic equality array for places. 
	 * 
	 * @param  isStrictMatch                 indicates, that the marking of places in source and target have to be equal
	 * @param  arcRestrictedSourcePlacesSet  places in source, where the places in target have to 
	 *                                       have exact the same number of pre arcs and post arcs  
	 * @return true, if a match is semantically possible
	 */
	private boolean generateSemanticMatrixPlaces(boolean isStrictMatch, Set<Place> arcRestrictedSourcePlacesSet) {
		for (int sourcePlaceIndex = 0; sourcePlaceIndex < sourcePlaces.length; sourcePlaceIndex++) {
			Place   sourcePlace        = sourcePlaces[sourcePlaceIndex];
			boolean hasSupportingPlace = false;
			boolean isArcRestricted    = arcRestrictedSourcePlacesSet.contains(sourcePlace);
			
			arcRestrictedSourcePlaces[sourcePlaceIndex] = isArcRestricted;

			for (int targetPlaceIndex = 0; targetPlaceIndex < targetPlaces.length; targetPlaceIndex++) {
				if (isSemanticEqual(sourcePlace, targetPlaces[targetPlaceIndex], isStrictMatch, isArcRestricted)) {
					hasSupportingPlace = true;
					semanticMatrixPlace[sourcePlaceIndex][targetPlaceIndex] = true;
				} else {
					semanticMatrixPlace[sourcePlaceIndex][targetPlaceIndex] = false;
				}
			}

			if (!hasSupportingPlace) {
				return false;
			}
		}

		return true;
	}

	/**
	 * initializes the semantic equality array for transitions. 
	 *   
	 * @return true, if a match is semantically possible
	 */
	private boolean generateSemanticMatrixTransitions() {
		for (int sourceTransitionIndex = 0; sourceTransitionIndex < sourceTransitions.length; sourceTransitionIndex++) {
			Transition sourceTransition        = sourceTransitions[sourceTransitionIndex];
			boolean    hasSupportingTransition = false;

			for (int targetTransitionIndex = 0; targetTransitionIndex < targetTransitions.length; targetTransitionIndex++) {
				if (isSemanticEqual(sourceTransition, targetTransitions[targetTransitionIndex])) {
					hasSupportingTransition = true;
					semanticMatrixTransition[sourceTransitionIndex][targetTransitionIndex] = true;
				} else {
					semanticMatrixTransition[sourceTransitionIndex][targetTransitionIndex] = false;
				}
			}

			if (!hasSupportingTransition) {
				return false;
			}
		}

		return true;
	}

	/**
	 * are the given arcs semantical equal
	 *   
	 * @return true, they are semantically equal
	 */
	private boolean isSemanticEqual(PreArc source, PreArc target) {
		return source.getWeight() == target.getWeight()
		    && source.getName().equals(target.getName());
	}

	/**
	 * are the given arcs semantical equal
	 *   
	 * @return true, they are semantically equal
	 */
	private boolean isSemanticEqual(PostArc source, PostArc target) {
		return source.getWeight() == target.getWeight()
		    && source.getName().equals(target.getName());
	}

	/**
	 * are the given places semantical equal
	 *   
	 * @return true, they are semantically equal
	 */
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

	/**
	 * are the given transitions semantical equal
	 *   
	 * @return true, they are semantically equal
	 */
	private boolean isSemanticEqual(Transition source, Transition target) {
		boolean isSemanticEqual = source.getIncomingArcs().size() == target.getIncomingArcs().size()
			&& source.getOutgoingArcs().size() == target.getOutgoingArcs().size()
	        && source.getName().equals(target.getName())
			&& source.getTlb().equals(target.getTlb())
		    && source.getRnw().equals(target.getRnw());
	    	/*&& getAccumulatedPreArcsWeight(source.getIncomingArcs()) ==
	    		getAccumulatedPreArcsWeight(target.getIncomingArcs())
			&& getAccumulatedPostArcsWeight(source.getOutgoingArcs()) ==
				getAccumulatedPostArcsWeight(target.getOutgoingArcs());*/

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

	/**
	 * takes the current state and builds a match
	 * 
	 * @return the build match
	 */
	private Match buildMatch() {
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


	/**
	 * Returns true, if at least one "out terminal set" nodes pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasOutPairs() {
		return hasOutPlacePairs() 
			|| hasOutTransitionPairs();
	}

	/**
	 * Returns true, if at least one "out terminal set" places pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasOutPlacePairs() {
		return outSourcePlacesCount > corePlacesCount 
			&& outTargetPlacesCount > corePlacesCount;
	}

	/**
	 * gets the number of "out terminal set" places pairs
	 * 
	 * @return number of place pairs
	 */
	private int getOutPlacePairsCount() {
		if (!hasOutPlacePairs()) {
			return 0;
		}

		return outTargetPlacesCount - corePlacesCount;
	}

	/**
	 * Returns true, if at least one "out terminal set" transitions pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasOutTransitionPairs() {
		return outSourceTransitionsCount > coreTransitionsCount 
			&& outTargetTransitionsCount > coreTransitionsCount;
	}

	/**
	 * gets the number of "out terminal set" transitions pairs
	 * 
	 * @return number of place pairs
	 */
	private int getOutTransitionPairsCount() {
		if (!hasOutTransitionPairs()) {
			return 0;
		}

		return outTargetTransitionsCount - coreTransitionsCount;
	}

	/**
	 * Returns true, if at least one "in terminal set" nodes pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasInPairs() {
		return hasInPlacePairs() 
			|| hasInTransitionPairs();
	}

	/**
	 * Returns true, if at least one "in terminal set" places pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasInPlacePairs() {
		return inSourcePlacesCount > corePlacesCount 
			&& inTargetPlacesCount > corePlacesCount;
	}

	/**
	 * gets the number of "in terminal set" places pairs
	 * 
	 * @return number of place pairs
	 */
	private int getInPlacePairsCount() {
		if (!hasInPlacePairs()) {
			return 0;
		}

		return inTargetPlacesCount - corePlacesCount;
	}

	/**
	 * Returns true, if at least one "in terminal set" transitions pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasInTransitionPairs() {
		return inSourceTransitionsCount > coreTransitionsCount 
			&& inTargetTransitionsCount > coreTransitionsCount;
	}

	/**
	 * gets the number of "in terminal set" transitions pairs
	 * 
	 * @return number of place pairs
	 */
	private int getInTransitionPairsCount() {
		if (!hasInTransitionPairs()) {
			return 0;
		}

		return inTargetTransitionsCount - coreTransitionsCount;
	}

	/**
	 * Returns true, if at least one "new set" places pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasPlacePairs() {
		return coreSourcePlaces.length > corePlacesCount 
			&& coreTargetPlaces.length > corePlacesCount;
	}

	/**
	 * gets the number of "new set" places pairs
	 * 
	 * @return number of place pairs
	 */
	private int getPlacePairsCount() {
		if (!hasPlacePairs()) {
			return 0;
		}

		return coreTargetPlaces.length - corePlacesCount;
	}

	/**
	 * Returns true, if at least one "new set" transitions pair exists.
	 * 
	 * @return true, if pairs exists
	 */
	private boolean hasTransitionPairs() {
		return coreSourceTransitions.length > coreTransitionsCount 
			&& coreTargetTransitions.length > coreTransitionsCount;
	}

	/**
	 * gets the number of "new set" transitions pairs
	 * 
	 * @return number of place pairs
	 */
	private int getTransitionPairsCount() {
		if (!hasTransitionPairs()) {
			return 0;
		}

		return coreTargetTransitions.length - coreTransitionsCount;
	}


	/**
	 * looks for a not matched node, which is also in the given terminal set.
	 * 
	 * @param  core         core array, to check the matching status
	 * @param  terminalSet  termin set, to check the terminal status   
	 * @param  startIndex   the non negative index, at which the search begins
	 * 
	 * @return index, if one exists, CORE_NULL_NODE otherwise
	 */
	private int getNotMatchedNodeIndex(int[] core, int[] terminalSet, int startIndex) {		
		assert core.length == terminalSet.length && startIndex >= 0;

		for (int index = startIndex; index < core.length; index++) {
			if (terminalSet[index]  != SET_NULL_VALUE && core[index] == CORE_NULL_NODE) {
				return index;
			}
		}

		return CORE_NULL_NODE;
	}

	/**
	 * looks for a not matched node
	 * 
	 * @param  core       core array, to check the matching status   
	 * @param  startIndex the non negative index, at which the search begins
	 * 
	 * @return index, if one exists, CORE_NULL_NODE otherwise
	 */
	private int getNotMatchedNodeIndex(int[] core, int startIndex) {
		assert startIndex >= 0;
		
		for (int index = startIndex; index < core.length; index++) {
			if (core[index] == CORE_NULL_NODE) {
				return index;
			}
		}

		return CORE_NULL_NODE;
	}


	private boolean assertValidState() {
		assert coreNodesCount == corePlacesCount + coreTransitionsCount;

		assert getUnequalCount(coreSourcePlaces, CORE_NULL_NODE)      == corePlacesCount;
		assert getUnequalCount(coreSourceTransitions, CORE_NULL_NODE) == coreTransitionsCount;
		assert getUnequalCount(coreTargetPlaces, CORE_NULL_NODE)      == corePlacesCount;
		assert getUnequalCount(coreTargetTransitions, CORE_NULL_NODE) == coreTransitionsCount;

		assert getUnequalCount(inSourcePlaces, SET_NULL_VALUE)        == inSourcePlacesCount;
		assert getUnequalCount(inSourceTransitions, SET_NULL_VALUE)   == inSourceTransitionsCount;
		assert getUnequalCount(outSourcePlaces, SET_NULL_VALUE)       == outSourcePlacesCount;
		assert getUnequalCount(outSourceTransitions, SET_NULL_VALUE)  == outSourceTransitionsCount;

		assert getUnequalCount(inTargetPlaces, SET_NULL_VALUE)        == inTargetPlacesCount;
		assert getUnequalCount(inTargetTransitions, SET_NULL_VALUE)   == inTargetTransitionsCount;
		assert getUnequalCount(outTargetPlaces, SET_NULL_VALUE)       == outTargetPlacesCount;
		assert getUnequalCount(outTargetTransitions, SET_NULL_VALUE)  == outTargetTransitionsCount;

		assert inSourcePlacesCount       >= corePlacesCount;
		assert inSourceTransitionsCount  >= coreTransitionsCount;
		assert outSourcePlacesCount      >= corePlacesCount;
		assert outSourceTransitionsCount >= coreTransitionsCount;

		assert inTargetPlacesCount 		 >= corePlacesCount;
		assert inTargetTransitionsCount  >= coreTransitionsCount;
		assert outTargetPlacesCount 	 >= corePlacesCount;
		assert outTargetTransitionsCount >= coreTransitionsCount;

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

	/**
	 * number of unequal items in comparison to the given value
	 * 
	 * @param  array	the array to be tested
	 * @param  value    comparison value
	 * 
	 * @return number of unequal items in comparison to the given value
	 */
	private int getUnequalCount(int[] array, int value) {
		int unequalCount = 0;

		for (int tmp : array) {
			if (value != tmp) {
				unequalCount++;
			}
		}

		return unequalCount;
	}

	/**
	 * converts the given match in an array with the source item as 
	 * key and the target item as value.
	 * 
	 * @param coreSource  array, with the match from source to target
	 * @param source	  
	 * @param target      
	 * @return
	 */
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
	
	private static RandomGenerator getDefaultRandomGenerator() {
		/*int[] seed = new int[1391];
		
		SecureRandom secureRandom = new SecureRandom();
		
		for (int index = 0; index < seed.length; index++) {
			seed[index] = ByteBuffer.wrap(secureRandom.generateSeed(4)).getInt();
		}*/

		SecureRandom secureRandom = new SecureRandom();
		long seed = ByteBuffer.wrap(secureRandom.generateSeed(8)).getLong();
		
		RandomGenerator defaultRandom = new Well44497b(seed);
		
		for (int i = 0; i < 1000; i++) {
			defaultRandom.nextInt();
		}
		
		return defaultRandom;
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

		if (semanticMatrixPlace != null) {
			builder.append("Semantic Places\n");
			for (int index = 0; index < semanticMatrixPlace.length; index++) {
				builder.append(Arrays.toString(semanticMatrixPlace[index]) + "\n");
			}
		}

		if (semanticMatrixTransition != null) {
			builder.append("Semantic Transitions\n");
			for (int index = 0; index < semanticMatrixTransition.length; index++) {
				builder.append(Arrays.toString(semanticMatrixTransition[index]) + "\n");
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
	}

	private final class AcceptFirstMatchVisitor implements MatchVisitor {
		public boolean visit(Match match) {
			return true;
		}
	}
	
	private enum NEXT_CANDIDATE_SET {
		TERMINAL_OUT, TERMINAL_IN, NEW;
	}
}
