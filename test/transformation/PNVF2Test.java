package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.random.*;
import org.junit.Before;
import org.junit.Test;

import cern.colt.Arrays;

import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Renews;
import petrinet.model.Transition;
import transformation.matcher.PNVF2;
import transformation.matcher.PNVF2.MatchException;
import transformation.matcher.PNVF2.*;

/**
 * Testing the morphism of places like specified in
 * "../additional/images/Isomorphism_transitions.png"
 */

public class PNVF2Test {
	class NoTwiceMatchesMatchVisitor implements MatchVisitor {
		private Set<Match> matches = new HashSet<Match>();
		private int matchesCount = 0;
		
		public boolean visit(Match match) {		
			matches.add(match);
			
			matchesCount++;

			assertEquals(matches.size(), matchesCount);
			
			return false;
		}	
		
		public int getMatchesCount() {
			return matchesCount;
		}
		
		public Set<Match> getMatches() {
			return matches;
		}
	}

	class MatchesCountsMatchVisitor implements MatchVisitor {
		private Map<Match, Integer> matchesCounts = new HashMap<Match, Integer>();
		
		public boolean visit(Match match) {		
			Integer count = matchesCounts.get(match); 
			
			if (count != null) {
				matchesCounts.put(match, count + 1);
			} else {
				matchesCounts.put(match, 1);				
			}
	
			return true;
		}	
		
		public Map<Match, Integer> getMatchesCounts() {
			return matchesCounts;
		}
	}

	class CheckParallelCallExceptionMatchVisitor implements MatchVisitor {
		private PNVF2 pnvf2;
		
		public CheckParallelCallExceptionMatchVisitor(PNVF2 pnvf2) {
			this.pnvf2 = pnvf2;
		}
		
		public boolean visit(Match match) {
			try {
				pnvf2.getMatch(false);
				fail();
			} catch (IllegalArgumentException e) {
				// expected
			} catch (MatchException e) {
				fail();
			}
			
			return true;
		}	
	}
	
	Petrinet source;
	Petrinet target;
		
    @Before
    public void setUp() {
    	source  = new Petrinet();
    	target  = new Petrinet();
    }
    
    @Test 
    public void testRandomPetrinets() {
    	String[] placeNames      = {"p_1", "p_2", "p_3", "p_4", "p_5", "p_6", "p_7", "p_8", "p_9"};
    	String[] transitionNames = {"t_1", "t_2", "t_3", "t_4", "t_5", "t_6", "t_7"};
    	String[] transitionTlbs  = {"tlb_1", "tlb_2", "tlb_3", "tlb_4", "tlb_5", "tlb_6", "tlb_7", "tlb_8", "tlb_9"};
    	int[]    markings        = {0, 1, 2, 3};
    	int[]    arcWeights      = {1, 2, 3};
    	
    	
    	for (int count = 1; count <= 10000; count++) {
        	Random   random = new Random(15656112 + count);
    		Petrinet source = new Petrinet();
    		Petrinet target = new Petrinet();
    		Map<Place, Place> 			expectedPlacesMatch 	 = new HashMap<Place, Place>();
    		Map<Transition, Transition> expectedTransitionsMatch = new HashMap<Transition, Transition>();
    		Map<PreArc, PreArc> 		expectedPreArcsMatch 	 = new HashMap<PreArc, PreArc>();
    		Map<PostArc, PostArc> 		expectedPostArcsMatch 	 = new HashMap<PostArc, PostArc>();

    		int numPlaces 	   = random.nextInt(15) + 1;
    		int numTransitions = random.nextInt(15) + 1;
    		int numPreArcs     = random.nextInt(numPlaces * numTransitions);
    		int numPostArcs    = random.nextInt(numPlaces * numTransitions);

    		int numPlacesExtendedTarget      = numPlaces + random.nextInt(5) + 1;
    		int numTransitionsExtendedTarget = numTransitions + random.nextInt(5) + 1;
    		int numPreArcsExtendedTarget     = random.nextInt((numPlacesExtendedTarget - numPlaces) * (numTransitionsExtendedTarget - numTransitions));
    		int numPostArcsExtendedTarget    = random.nextInt((numPlacesExtendedTarget - numPlaces) * (numTransitionsExtendedTarget - numTransitions));

    		List<Place> sourcePlaces 		   = new ArrayList<Place>(numPlaces);
    		List<Place> targetPlaces 		   = new ArrayList<Place>(numTransitionsExtendedTarget);
    		List<Transition> sourceTransitions = new ArrayList<Transition>(numTransitions);
    		List<Transition> targetTransitions = new ArrayList<Transition>(numTransitionsExtendedTarget);
    		

    		// build both nets for graph isomorphism    		
    		for (int index = 0; index < numPlaces; index++) {
    			String placeName = placeNames[random.nextInt(placeNames.length)];
    			int    marking   = markings[random.nextInt(markings.length)];

    			Place pSource = source.addPlace(placeName);
    			Place pTarget = target.addPlace(placeName);
    			
    			pSource.setMark(marking);
    			pTarget.setMark(marking);

    			sourcePlaces.add(pSource);
    			targetPlaces.add(pTarget);
    			
    			expectedPlacesMatch.put(pSource, pTarget);
    		}
    		
    		for (int index = 0; index < numTransitions; index++) {
    			String transitionName = transitionNames[random.nextInt(transitionNames.length)];
    			String transitionTlb  = transitionTlbs[random.nextInt(transitionTlbs.length)];

    			Transition tSource = source.addTransition(transitionName);
    			Transition tTarget = target.addTransition(transitionName);
    			
    			tSource.setTlb(transitionTlb);
    			tTarget.setTlb(transitionTlb);

    			sourceTransitions.add(tSource);
    			targetTransitions.add(tTarget);

    			expectedTransitionsMatch.put(tSource, tTarget);
    		}
    		
    		for (int index = 0; index < numPreArcs; index++) {
    			int arcWeight    	= arcWeights[random.nextInt(arcWeights.length)];
    			int placeIndex 		= random.nextInt(numPlaces);
    			int transitionIndex = random.nextInt(numTransitions);
    			
    			if (!sourcePlaces.get(placeIndex).hasOutgoingArc(sourceTransitions.get(transitionIndex))) {	
	    			PreArc paSource = source.addPreArc("", sourcePlaces.get(placeIndex), sourceTransitions.get(transitionIndex));
	    	    	PreArc paTarget = target.addPreArc("", targetPlaces.get(placeIndex), targetTransitions.get(transitionIndex));
	
	    			paSource.setWeight(arcWeight);
	    			paTarget.setWeight(arcWeight);
	
	    			expectedPreArcsMatch.put(paSource, paTarget);
    			}
    		}
    		
    		for (int index = 0; index < numPostArcs; index++) {
    			int arcWeight    	= arcWeights[random.nextInt(arcWeights.length)];
    			int placeIndex 		= random.nextInt(numPlaces);
    			int transitionIndex = random.nextInt(numTransitions);
    			
    			if (!sourcePlaces.get(placeIndex).hasIncomingArc(sourceTransitions.get(transitionIndex))) {	
	    			PostArc paSource = source.addPostArc("", sourceTransitions.get(transitionIndex), sourcePlaces.get(placeIndex));
	    			PostArc paTarget = target.addPostArc("", targetTransitions.get(transitionIndex), targetPlaces.get(placeIndex));
	
	    			paSource.setWeight(arcWeight);
	    			paTarget.setWeight(arcWeight);
	
	    			expectedPostArcsMatch.put(paSource, paTarget);
    			}
    		}
    		
    		Match expectedMatch 	 = new Match(source, target, expectedPlacesMatch, expectedTransitionsMatch, expectedPreArcsMatch, expectedPostArcsMatch);
    		Set<Match> matches 	     = testUniqueMatch(source, target, false);
    		Set<Match> matchesStrict = testUniqueMatch(source, target, true);
    		
    		assertTrue(matches.contains(expectedMatch));
    		assertTrue(matchesStrict.contains(expectedMatch));
    		assertTrue(matchesStrict.size() <= matches.size());
    		
    		// extends target net for subgraph isomorphism   
    		// use new names and labels -> set of matches must be equal
    		for (int index = numPlaces; index < numPlacesExtendedTarget; index++) {
    			targetPlaces.add(target.addPlace(""));
    		}
    		
    		for (int index = numTransitions; index < numTransitionsExtendedTarget; index++) {
    			targetTransitions.add(target.addTransition(""));
    		}
    		
    		for (int index = numPreArcs; index < numPreArcsExtendedTarget; index++) {
    			int placeIndex 		= random.nextInt(numPlacesExtendedTarget);
    			int transitionIndex = random.nextInt(numTransitionsExtendedTarget - numTransitions) + numTransitions;
    			
    			if (!targetPlaces.get(placeIndex).hasOutgoingArc(targetTransitions.get(transitionIndex))) {	
	    	    	target.addPreArc("", targetPlaces.get(placeIndex), targetTransitions.get(transitionIndex));
    			}
    		}
    		
    		for (int index = numPostArcs; index < numPostArcsExtendedTarget; index++) {
    			int placeIndex 		= random.nextInt(numPlacesExtendedTarget);
    			int transitionIndex = random.nextInt(numTransitionsExtendedTarget - numTransitions) + numTransitions;
    			
    			if (!targetPlaces.get(placeIndex).hasIncomingArc(targetTransitions.get(transitionIndex))) {	
	    			target.addPostArc("", targetTransitions.get(transitionIndex), targetPlaces.get(placeIndex));
    			}
    		}

    		assertTrue(matches.equals(testUniqueMatch(source, target, false)));
    		assertTrue(matchesStrict.equals(testUniqueMatch(source, target, true)));
    		
    		target.removeTransition(targetTransitions.get(random.nextInt(numTransitions)));
    		testNoMatch(source, target, false);
    		testNoMatch(source, target, true); 		
    	}
    }

    @Test
    public void testArcRestrictedMatch() {
		Set<Place> arcRestrictedPlaces = new HashSet<Place>();		
		Petrinet source = createCompletePetrinet(1, 1);
		Petrinet target = createCompletePetrinet(1, 2);
		testUniqueMatchCount(source, target, false, 2);
		testUniqueMatchCount(source, target, true, 2);
		arcRestrictedPlaces.add(source.getPlaces().iterator().next());
		testNoMatch(source, target, false, arcRestrictedPlaces);
		testNoMatch(source, target, true, arcRestrictedPlaces);

		arcRestrictedPlaces = new HashSet<Place>();		
		source = createCompletePetrinet(1, 1);
		target = createCompletePetrinet(1, 1);
		testUniqueMatchCount(source, target, false, 1);
		testUniqueMatchCount(source, target, true, 1);
		arcRestrictedPlaces.add(source.getPlaces().iterator().next());
		testMatch(source, target, false, arcRestrictedPlaces);
		testMatch(source, target, true, arcRestrictedPlaces);

		source 		  = createCompletePetrinet(1, 1);
		target 		  = new Petrinet();
		Place 	   p1 = target.addPlace("");
		Place 	   p2 = target.addPlace("");
		Transition t1 = target.addTransition("");
		Transition t2 = target.addTransition("");
		Transition t3 = target.addTransition("");
		target.addPreArc("", p1, t1);
		target.addPreArc("", p1, t2);
		target.addPreArc("", p2, t3);
		target.addPostArc("", t1, p1);
		target.addPostArc("", t2, p1);
		target.addPostArc("", t3, p2);
		testUniqueMatchCount(source, target, false, 3);
		testUniqueMatchCount(source, target, true, 3);
		arcRestrictedPlaces.add(source.getPlaces().iterator().next());
		testUniqueMatchCount(source, target, false, arcRestrictedPlaces, 1);
		testUniqueMatchCount(source, target, true, arcRestrictedPlaces, 1);

		target 	= new Petrinet();
		p1 		= target.addPlace("");
		p2 		= target.addPlace("");
		t1 		= target.addTransition("");
		t2 		= target.addTransition("");
		t3 		= target.addTransition("");
		target.addPreArc("", p1, t1);
		target.addPreArc("", p2, t3);
		target.addPostArc("", t1, p1);
		target.addPostArc("", t2, p1);
		target.addPostArc("", t3, p2);
		testUniqueMatchCount(source, target, false, 2);
		testUniqueMatchCount(source, target, true, 2);
		arcRestrictedPlaces.add(source.getPlaces().iterator().next());
		testUniqueMatchCount(source, target, false, arcRestrictedPlaces, 1);
		testUniqueMatchCount(source, target, true, arcRestrictedPlaces, 1);

		target 	= new Petrinet();
		p1 		= target.addPlace("");
		p2 		= target.addPlace("");
		t1 		= target.addTransition("");
		t2 		= target.addTransition("");
		t3 		= target.addTransition("");
		target.addPreArc("", p1, t1);
		target.addPreArc("", p1, t2);
		target.addPreArc("", p2, t3);
		target.addPostArc("", t1, p1);
		target.addPostArc("", t3, p2);
		testUniqueMatchCount(source, target, false, 2);
		testUniqueMatchCount(source, target, true, 2);
		arcRestrictedPlaces.add(source.getPlaces().iterator().next());
		testUniqueMatchCount(source, target, false, arcRestrictedPlaces, 1);
		testUniqueMatchCount(source, target, true, arcRestrictedPlaces, 1);
    }
    	
	@Test
	public void testCompletePetrinetMatch() {
		Petrinet source = createCompletePetrinet(3, 3);
		Petrinet target = createCompletePetrinet(3, 3);
		testUniqueMatchCount(source, target, false, 36);
		testUniqueMatchCount(source, target, true, 36);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 4);
		testUniqueMatchCount(source, target, false, 144);
		testUniqueMatchCount(source, target, true, 144);

		target.removeArc(target.getPostArcs().iterator().next());
		testUniqueMatchCount(source, target, false, 36);
		testUniqueMatchCount(source, target, true, 36);	

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 4);
		target.removeArc(target.getPreArcs().iterator().next());
		testUniqueMatchCount(source, target, false, 36);
		testUniqueMatchCount(source, target, true, 36);	
		
		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(4, 3);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);
	}

	@Test
	public void testLabeledSourcePetrinetNoMatch() {
		Petrinet source = createCompletePetrinet(3, 3);
		Petrinet target = createCompletePetrinet(3, 3);
		source.getPreArcs().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getPreArcs().iterator().next().setWeight(2);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);
		
		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getPostArcs().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getPostArcs().iterator().next().setWeight(2);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getPlaces().iterator().next().setMark(1);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getPlaces().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getTransitions().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getTransitions().iterator().next().setTlb("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		source.getTransitions().iterator().next().setRnw(Renews.COUNT);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);
	}

	@Test
	public void testLabeledTargetPetrinetNoMatch() {
		Petrinet source = createCompletePetrinet(3, 3);
		Petrinet target = createCompletePetrinet(3, 3);
		target.getPreArcs().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getPreArcs().iterator().next().setWeight(2);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);
		
		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getPostArcs().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getPostArcs().iterator().next().setWeight(2);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getPlaces().iterator().next().setMark(1);
		testUniqueMatchCount(source, target, false, 36);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getPlaces().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getTransitions().iterator().next().setName("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getTransitions().iterator().next().setTlb("a");
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);

		source = createCompletePetrinet(3, 3);
		target = createCompletePetrinet(3, 3);
		target.getTransitions().iterator().next().setRnw(Renews.COUNT);
		testNoMatch(source, target, false);
		testNoMatch(source, target, true);
	}
	
	@Test
	public void testNondeterminism1() {
		int[] sourceLayersNodes  = {1,1,2};		
		int[] targetLayersNodes1 = sourceLayersNodes;	
		int[] targetLayersNodes2 = {1,8,2};
		
		final Petrinet source = new Petrinet();	
		final Petrinet target = new Petrinet();		

		buildTreePetrinetComponent(source, sourceLayersNodes); 
		buildTreePetrinetComponent(target, targetLayersNodes1);
		buildTreePetrinetComponent(target, targetLayersNodes1);
		buildTreePetrinetComponent(target, targetLayersNodes2);
		
		MatchesCountsMatchVisitor visitor = new MatchesCountsMatchVisitor();
		RandomGenerator           random  = new Well19937c(9092862831406672017L);	
		final int 				  rounds  = 20000;

		for (int i = 0; i < 1000; i++) {
			random.nextInt();
		}

		for (int i = 0; i < rounds; i++) {	
			PNVF2 matcher = PNVF2.getInstance(source, target, random);	
			
			try {
				assertNotNull(matcher.getMatch(false, visitor));
			} catch (MatchException e) {
				assertTrue(false);
			}
		}	

		final int allowedDeviationInPercent = 20;

		int minMatched = Integer.MAX_VALUE;
		int maxMatched = Integer.MIN_VALUE;
		
		for (Integer matched : visitor.getMatchesCounts().values()) {		
			minMatched = Math.min(minMatched, matched);
			maxMatched = Math.max(maxMatched, matched);
			//System.out.println(matched);
		}
		
		final int idealMatched = rounds / visitor.getMatchesCounts().size();
		
		final int lowerBorder = (int) (idealMatched * (1 - allowedDeviationInPercent / 100.0));
		final int upperBorder = (int) (idealMatched * (1 + allowedDeviationInPercent / 100.0));

		//System.out.println("--");
		System.out.println(lowerBorder + " <= " + minMatched + " | " + idealMatched + " | " + maxMatched + " <= " + upperBorder);

		//assertTrue(lowerBorder <= minMatched && maxMatched <= upperBorder);
	}

	@Test
	public void testCountWithoutArcsMatch1() {
		source.addPlace("");
		source.addPlace("");
		target.addPlace("");
		target.addPlace("");
		testUniqueMatchCount(false, 2);
		testUniqueMatchCount(true, 2);

		source.addTransition("");
		source.addTransition("");
		target.addTransition("");
		target.addTransition("");
		testUniqueMatchCount(false, 4);
		testUniqueMatchCount(true, 4);

		target.addPlace("");
		target.addPlace("");
		testUniqueMatchCount(false, 24);
		testUniqueMatchCount(true, 24);
		
		source.addPlace("");
		source.addPlace("");
		target.addTransition("");
		target.addTransition("");
		testUniqueMatchCount(false, 288);
		testUniqueMatchCount(true, 288);
		
		source.addPlace("");
		source.addTransition("");
		source.addTransition("");
		source.addTransition("");
		target.addPlace("");
		target.addTransition("");
		testUniqueMatchCount(false, 14400);
		testUniqueMatchCount(true, 14400);
	}
	
	@Test
	public void testCountWithoutArcsMatch2() {
		source.addPlace("");
		source.addPlace("a");
		source.addPlace("a");
		target.addPlace("");
		target.addPlace("a");
		target.addPlace("a");
		testUniqueMatchCount(false, 2);
		testUniqueMatchCount(true, 2);

		source.addTransition("");
		source.addTransition("").setTlb("a");
		target.addTransition("");
		target.addTransition("").setTlb("a");
		testUniqueMatchCount(false, 2);
		testUniqueMatchCount(true, 2);

		target.addPlace("").setMark(1);
		target.addPlace("").setMark(2);
		testUniqueMatchCount(false, 6);
		testUniqueMatchCount(true, 2);
		
		source.addPlace("");
		source.addPlace("");
		target.addTransition("");
		target.addTransition("");
		testUniqueMatchCount(false, 36);
		testUniqueMatchCount(true, 0);
	}

	@Test
	public void testEmptyPetrinetsMatch() {
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testEmptySourcePetrinetMatch1() {
		target.addPlace("");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testEmptySourcePetrinetMatch2() {
		target.addTransition("");		
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testEmptySourcePetrinetMatch3() {
		Place      p = target.addPlace("");
		Transition t = target.addTransition("");
		target.addPreArc("", p, t);
		target.addPostArc("", t, p);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testEmptyTargetPetrinetNoMatch1() {
		source.addPlace("");
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testEmptyTargetPetrinetNoMatch2() {
		source.addTransition("");		
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testEmptyTargetPetrinetNoMatch3() {
		Place      p = source.addPlace("");
		Transition t = source.addTransition("");
		source.addPreArc("", p, t);
		source.addPostArc("", t, p);
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimplePlaceMatch1() {
		source.addPlace("");
		target.addPlace("");
		testMatch(false);
		testMatch(true);
		
		target.addPlace("");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingMatch1() {
		source.addPlace("").setMark(1);
		target.addPlace("").setMark(1);
		testMatch(false);
		testMatch(true);

		target.addPlace("").setMark(1);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingMatch2() {
		source.addPlace("").setMark(1);
		target.addPlace("").setMark(2);
		testMatch(false);
		testNoMatch(true);

		target.addPlace("").setMark(2);
		testMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingMatch3() {		
		source.addPlace("").setMark(1);
		target.addPlace("").setMark(2);
		testMatch(false);
		testNoMatch(true);

		target.addPlace("").setMark(1);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceNameMatch1() {
		source.addPlace("a");
		target.addPlace("a");
		testMatch(false);
		testMatch(true);

		target.addPlace("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceNameMatch2() {
		source.addPlace("a");
		target.addPlace("b");
		testNoMatch(false);
		testNoMatch(true);

		target.addPlace("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingNameMatch1() {
		source.addPlace("a").setMark(1);
		target.addPlace("a").setMark(1);
		testMatch(false);
		testMatch(true);

		target.addPlace("a").setMark(1);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingNameMatch2() {
		source.addPlace("a").setMark(1);
		target.addPlace("a").setMark(2);
		testMatch(false);
		testNoMatch(true);

		target.addPlace("a").setMark(2);
		testMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingNameMatch3() {		
		source.addPlace("a").setMark(1);
		target.addPlace("a").setMark(2);
		testMatch(false);
		testNoMatch(true);

		target.addPlace("a").setMark(1);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingNameMatch4() {
		source.addPlace("a").setMark(1);
		target.addPlace("b").setMark(1);
		testNoMatch(false);
		testNoMatch(true);

		target.addPlace("a").setMark(1);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimplePlaceMarkingNameMatch5() {
		source.addPlace("a").setMark(1);
		target.addPlace("b").setMark(1);
		testNoMatch(false);
		testNoMatch(true);

		target.addPlace("a").setMark(2);
		testMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimpleTransitionMatch1() {
		source.addTransition("");
		target.addTransition("");
		testMatch(false);
		testMatch(true);
		
		target.addTransition("");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelMatch1() {
		source.addTransition("").setTlb("a");
		target.addTransition("").setTlb("a");
		testMatch(false);
		testMatch(true);

		target.addTransition("").setTlb("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelMatch2() {
		source.addTransition("").setTlb("a");
		target.addTransition("").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelMatch3() {		
		source.addTransition("").setTlb("a");
		target.addTransition("").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("").setTlb("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionNameMatch1() {
		source.addTransition("a");
		target.addTransition("a");
		testMatch(false);
		testMatch(true);
		
		target.addTransition("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionNameMatch2() {
		source.addTransition("a");
		target.addTransition("b");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelNameMatch1() {
		source.addTransition("a").setTlb("a");
		target.addTransition("a").setTlb("a");
		testMatch(false);
		testMatch(true);

		target.addTransition("a").setTlb("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelNameMatch2() {
		source.addTransition("a").setTlb("a");
		target.addTransition("a").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("a").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelNameMatch3() {		
		source.addTransition("a").setTlb("a");
		target.addTransition("a").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("a").setTlb("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelNameMatch4() {
		source.addTransition("a").setTlb("a");
		target.addTransition("b").setTlb("a");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("a").setTlb("a");
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionLabelNameMatch5() {
		source.addTransition("a").setTlb("a");
		target.addTransition("b").setTlb("a");
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("a").setTlb("b");
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimpleTransitionRnwMatch1() {
		source.addTransition("").setRnw(Renews.TOGGLE);
		target.addTransition("").setRnw(Renews.TOGGLE);
		testMatch(false);
		testMatch(true);

		target.addTransition("").setRnw(Renews.TOGGLE);
		testMatch(false);
		testMatch(true);
	}

	@Test
	public void testSimpleTransitionRnwMatch2() {
		source.addTransition("").setRnw(Renews.TOGGLE);
		target.addTransition("").setRnw(Renews.COUNT);
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("").setRnw(Renews.COUNT);
		testNoMatch(false);
		testNoMatch(true);
	}

	@Test
	public void testSimpleTransitionRnwMatch3() {		
		source.addTransition("").setRnw(Renews.TOGGLE);
		target.addTransition("").setRnw(Renews.COUNT);
		testNoMatch(false);
		testNoMatch(true);

		target.addTransition("").setRnw(Renews.TOGGLE);
		testMatch(false);
		testMatch(true);
	}
	
	@Test
	public void testParallelCall() {		
		source.addPlace("");
		target.addPlace("");
		target.addPlace("");
		testMatch(false);
		testMatch(true);
		
		PNVF2 pnvf2 = PNVF2.getInstance(source, target);
		Match match = null;
		
		try {
			match = pnvf2.getMatch(false, new CheckParallelCallExceptionMatchVisitor(pnvf2));
			pnvf2.getMatch(true, new CheckParallelCallExceptionMatchVisitor(pnvf2));
		} catch (MatchException e) {
			fail();
		}

		try {
			pnvf2.getMatch(false, match, new CheckParallelCallExceptionMatchVisitor(pnvf2));
			pnvf2.getMatch(true, match, new CheckParallelCallExceptionMatchVisitor(pnvf2));
		} catch (MatchException e) {
			fail();
		}
	}
	
	


	@Test
	public void testPlaceMatch1() {
		source.addPlace("");

		testMatch(source, createPetrinet1("", "", "", ""), false);
		testMatch(source, createPetrinet1("", "", "", ""), true);
	}

	@Test
	public void testPlaceMatch2() {
		source.addPlace("");
		source.addPlace("");

		testMatch(source, createPetrinet1("", "", "", ""), false);
		testMatch(source, createPetrinet1("", "", "", ""), true);
	}

	@Test
	public void testPlaceMatch3() {
		source.addPlace("");
		source.addPlace("");
		source.addPlace("");

		testMatch(source, createPetrinet1("", "", "", ""), false);
		testMatch(source, createPetrinet1("", "", "", ""), true);
	}

	@Test
	public void testPlaceMatch4() {
		source.addPlace("");
		source.addPlace("");
		source.addPlace("");
		source.addPlace("");

		testMatch(source, createPetrinet1("", "", "", ""), false);
		testMatch(source, createPetrinet1("", "", "", ""), true);
	}

	@Test
	public void testPlaceNoMatch1() {
		source.addPlace("");
		source.addPlace("");
		source.addPlace("");
		source.addPlace("");
		source.addPlace("");

		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);
	}

	@Test
	public void testPlaceNoMatch2() {
		source.addPlace("a");

		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);
	}

	@Test
	public void testPlaceNoMatch3() {
		source.addPlace("").setMark(1);

		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);
	}
	

	
	@Test
	public void testTransitionNoMatch1() {
		source.addTransition("");		
		testNoMatch(source, createPetrinet1("", "", "", ""), false);	
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}


	@Test
	public void testTransitionNoMatch2() {
		Place      p = source.addPlace("");
		Transition t  = source.addTransition("");

		source.addPreArc("", p, t);

		testNoMatch(source, createPetrinet1("", "", "", ""), false);	
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}

	@Test
	public void testTransitionNoMatch3() {
		Place      p1 = source.addPlace("");
		Place      p2 = source.addPlace("");
		Transition t  = source.addTransition("");

		source.addPreArc("", p1, t);
		source.addPreArc("", p2, t);
			
		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}

	@Test
	public void testTransitionNoMatch4() {
		Place      p1 = source.addPlace("");
		Place      p2 = source.addPlace("");
		Place      p3 = source.addPlace("");
		Transition t  = source.addTransition("");

		source.addPreArc("", p1, t);
		source.addPreArc("", p2, t);
		source.addPostArc("", t, p3);
			
		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}

	@Test
	public void testTransitionNoMatch5() {		
		Place      p = source.addPlace("");
		Transition t = source.addTransition("");
		
		source.addPostArc("", t, p);

		testNoMatch(source, createPetrinet1("", "", "", ""), false);	
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}

	@Test
	public void testTransitionNoMatch6() {
		Place      p1 = source.addPlace("");
		Place      p2 = source.addPlace("");
		Transition t  = source.addTransition("");

		source.addPostArc("", t, p1);
		source.addPostArc("", t, p2);
			
		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}

	@Test
	public void testTransitionNoMatch7() {
		Place      p1 = source.addPlace("");
		Place      p2 = source.addPlace("");
		Place      p3 = source.addPlace("");
		Transition t  = source.addTransition("");

		source.addPreArc("", p1, t);
		source.addPostArc("", t, p2);
		source.addPostArc("", t, p3);
			
		testNoMatch(source, createPetrinet1("", "", "", ""), false);
		testNoMatch(source, createPetrinet1("", "", "", ""), true);	
	}

	@Test
	public void testTransitionNoMatch8() {
		testNoMatch(createPetrinet1("a", "", "", ""), createPetrinet1("", "", "", ""), false);
		testNoMatch(createPetrinet1("", "a", "", ""), createPetrinet1("", "", "", ""), false);
		testNoMatch(createPetrinet1("", "", "a", ""), createPetrinet1("", "", "", ""), false);
		testNoMatch(createPetrinet1("", "", "", "a"), createPetrinet1("", "", "", ""), false);
	}

	
	
	@Test 
	public void testGetInstanceException() {
		try {
			PNVF2.getInstance(null, target);
			fail();
		} catch (IllegalArgumentException e) {}

		try {
			PNVF2.getInstance(source, null);
			fail();
		} catch (IllegalArgumentException e) {}

		try {
			PNVF2.getInstance(null, target, new JDKRandomGenerator());
			fail();
		} catch (IllegalArgumentException e) {}

		try {
			PNVF2.getInstance(source, null, new JDKRandomGenerator());
			fail();
		} catch (IllegalArgumentException e) {}

		try {
			PNVF2.getInstance(source, target, null);
			fail();
		} catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void testGetMatchExceptions() {
		PNVF2 matcher = PNVF2.getInstance(source, target);	
		Match match = new Match(source, target, new HashMap<Place, Place>(),
				new HashMap<Transition, Transition>(),
				new HashMap<PreArc, PreArc>(), new HashMap<PostArc, PostArc>());
		MatchVisitor visitor = new NoTwiceMatchesMatchVisitor();	

		try {
			matcher.getMatch(false, (Match) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, (Set<Place>) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, (MatchVisitor) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, (Match) null, visitor);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, match, (MatchVisitor) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, (Match) null, new HashSet<Place>());
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, match, (Set<Place>) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, (Set<Place>) null, visitor);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, new HashSet<Place>(), (MatchVisitor) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, (Match) null, new HashSet<Place>(), visitor);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, match, (Set<Place>) null, visitor);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}

		try {
			matcher.getMatch(false, match, new HashSet<Place>(), (MatchVisitor) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		} catch (MatchException e) {
			fail();
		}
	}
	

	private Match testMatch(boolean isStrictMatch) {		
		return testMatch(source, target, isStrictMatch);		
	}
	

	private Match testMatch(Petrinet source, Petrinet target, boolean isStrictMatch, Set<Place> arcRestrictedPlaces) {	
		try {
			Match match = PNVF2.getInstance(source, target).getMatch(isStrictMatch, arcRestrictedPlaces);
			
			assertNotNull(match);
			
			assertMatchMapsSizes(
				match,
				source.getPlaces().size(),
				source.getTransitions().size(),
				source.getPreArcs().size(),
				source.getPostArcs().size()
			);

			assertEquals(match, PNVF2.getInstance(source, target).getMatch(isStrictMatch, match, arcRestrictedPlaces));
			
			
			return match;
		} catch (MatchException e) {			
			fail();		
			return null;
		}		
	}

	private Match testMatch(Petrinet source, Petrinet target, boolean isStrictMatch) {
		try {
			Match match = PNVF2.getInstance(source, target).getMatch(isStrictMatch);
			
			assertNotNull(match);
			
			assertMatchMapsSizes(
				match,
				source.getPlaces().size(),
				source.getTransitions().size(),
				source.getPreArcs().size(),
				source.getPostArcs().size()
			);

			assertEquals(match, PNVF2.getInstance(source, target).getMatch(isStrictMatch, match));
			
			
			return match;
		} catch (MatchException e) {			
			fail();		
			return null;
		}			
	}

	private void testNoMatch(boolean isStrictMatch) {
		testNoMatch(source, target, isStrictMatch);		
	}

	private void testNoMatch(Petrinet source, Petrinet target, boolean isStrictMatch, Set<Place> arcRestrictedPlaces) {
		try {
			PNVF2.getInstance(source, target).getMatch(isStrictMatch, arcRestrictedPlaces);			
			fail();
		} catch (MatchException e) {}			
	}

	private void testNoMatch(Petrinet source, Petrinet target, boolean isStrictMatch) {
		try {
			PNVF2.getInstance(source, target).getMatch(isStrictMatch);			
			fail();
		} catch (MatchException e) {}			
	}
	
	private void assertMatchMapsSizes(Match match, int numPlaces, int numTransitions, int numPreArcs, int numPostArcs) {
		assertEquals(numPlaces, match.getPlaces().size());
		assertEquals(numTransitions, match.getTransitions().size());
		assertEquals(numPreArcs, match.getPreArcs().size());
		assertEquals(numPostArcs, match.getPostArcs().size());	
	}
	
	private Petrinet createPetrinet1(String placeName, String transitionName, String preArcName, String postArcName) {
		Petrinet petrinet = new Petrinet();

		Place 	   p1 = petrinet.addPlace(placeName);
		Place 	   p2 = petrinet.addPlace(placeName);
		Place 	   p3 = petrinet.addPlace(placeName);
		Place      p4 = petrinet.addPlace(placeName);		
		Transition t  = petrinet.addTransition(transitionName);

		petrinet.addPreArc(preArcName, p1, t);
		petrinet.addPreArc(preArcName, p2, t);
		
		petrinet.addPostArc(postArcName, t, p3);
		petrinet.addPostArc(postArcName, t, p4);
		
		
		return petrinet;
	}

	private Map<Integer, Set<INode>> buildTreePetrinetComponent(Petrinet petrinet, int[] layerNodesCounts) {
		Map<Integer, Set<INode>> layers =  new HashMap<Integer, Set<INode>>();
		
		if (layerNodesCounts.length == 0) {
			return layers;
		}
		
		Set<INode> layer = new HashSet<INode>();		
		
		for (int i = 0; i < layerNodesCounts[0]; i++) {
			layer.add(petrinet.addPlace(""));			
		}
		
		layers.put(0, layer);
		
		for (int i = 1; i < layerNodesCounts.length; i++) {
			layers.put(i, buildTreePetrinetLayer(petrinet, layerNodesCounts[i], layers.get(i - 1)));
		}
		
		return layers;
	}
	
	private Set<INode> buildTreePetrinetLayer(Petrinet petrinet, int layerNodesCount, Set<INode> previousLayer) {		
		Set<INode> layer = new HashSet<INode>();
				
		for (INode node : previousLayer) {
			for (int i = 0; i < layerNodesCount; i++) {
				if (node instanceof Place) {
					Transition transition = petrinet.addTransition("");
					petrinet.addPreArc("", (Place) node, transition);
					layer.add(transition);
				} else {
					Place place = petrinet.addPlace("");
					petrinet.addPostArc("", (Transition) node, place);
					layer.add(place);
				}
			}
		}
				
		return layer;
	}

	private Set<Match>  testUniqueMatchCount(boolean isStrictMatch, int count) {
		return testUniqueMatchCount(source, target, isStrictMatch, count);
	}

	private Set<Match> testUniqueMatchCount(Petrinet source, Petrinet target, boolean isStrictMatch, Set<Place> arcRestrictedPlaces, int count) {
		PNVF2 vf2 = PNVF2.getInstance(source, target);

		NoTwiceMatchesMatchVisitor visitor = new NoTwiceMatchesMatchVisitor();		
		
		try {
			assertNotNull(vf2.getMatch(isStrictMatch, arcRestrictedPlaces, visitor));
			fail();
		} catch (MatchException e) {
			//System.out.println(visitor.getMatchesCount());
			assertEquals(count, visitor.getMatchesCount());	
		}
		
		return visitor.getMatches();
	}

	private Set<Match>  testUniqueMatchCount(Petrinet source, Petrinet target, boolean isStrictMatch, int count) {
		PNVF2 vf2 = PNVF2.getInstance(source, target);

		NoTwiceMatchesMatchVisitor visitor = new NoTwiceMatchesMatchVisitor();		
		
		try {
			assertNotNull(vf2.getMatch(isStrictMatch, visitor));
			fail();
		} catch (MatchException e) {
			//System.out.println(visitor.getMatchesCount());
			assertEquals(count, visitor.getMatchesCount());	
		}

		return visitor.getMatches();
	}

	private Set<Match>  testUniqueMatch(Petrinet source, Petrinet target, boolean isStrictMatch) {
		PNVF2 vf2 = PNVF2.getInstance(source, target);

		NoTwiceMatchesMatchVisitor visitor = new NoTwiceMatchesMatchVisitor();		
		
		try {
			assertNotNull(vf2.getMatch(isStrictMatch, visitor));
			fail();
		} catch (MatchException e) {}

		return visitor.getMatches();
	}
	
	private Petrinet createCompletePetrinet(int numPlaces, int numTransitions) {
		Petrinet petrinet = new Petrinet();

		for (int i = 0; i < numPlaces; i++) {
			petrinet.addPlace("");
		}
		
		for (int i = 0; i < numTransitions; i++) {
			petrinet.addTransition("");
		}

		for (Place place : petrinet.getPlaces()) {
			for (Transition transition : petrinet.getTransitions()) {
				petrinet.addPreArc("", place, transition);
				petrinet.addPostArc("", transition, place);
			}
		}
		
		return petrinet;
	}
}