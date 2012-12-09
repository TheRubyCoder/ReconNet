package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.matcher.VF2;
import transformation.matcher.VF2.MatchException;
import transformation.matcher.VF2.*;

/**
 * Testing the morphism of places like specified in
 * "../additional/images/Isomorphism_transitions.png"
 */

public class VF2Test {
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
	
	private final Random RANDOM = new Random(55152213); 
	
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    

	
	@Test
	public void testMorphismCountWithoutArcs() {
		int[] bla = new int[0];
		System.out.println(	"test");
		System.out.println(		Arrays.toString(bla));

		Petrinet a = new Petrinet();
		Petrinet b = new Petrinet(); 
		
		a.addPlace("");
		b.addPlace("");
		testMorphismCount(a, b);

		a.addTransition("");
		b.addTransition("");
		testMorphismCount(a, b);

		b.addPlace("");
		b.addPlace("");
		testMorphismCount(a, b);
		
		a.addPlace("");
		a.addPlace("");
		b.addTransition("");
		b.addTransition("");
		testMorphismCount(a, b);
		
		a.addPlace("");
		a.addTransition("");
		a.addTransition("");
		a.addTransition("");
		b.addPlace("");
		b.addTransition("");
		testMorphismCount(a, b);
	}
	
	private void testMorphismCount(Petrinet source, Petrinet target) {
		VF2 vf2 = VF2.getInstance(source, target);

		NoTwiceMatchesMatchVisitor visitor = new NoTwiceMatchesMatchVisitor();		
		
		try {
			assertNotNull(vf2.getMatch(false, visitor));
			fail();
		} catch (MatchException e) {
			long variations = getVariationsCount(target.getPlaces().size(), source.getPlaces().size()) *
					getVariationsCount(target.getTransitions().size(), source.getTransitions().size());
			
			assertEquals(
				visitor.getMatchesCount(),
				variations
			);	
		}
	}
	/*
	@Test
	public void testStack() {
		final Petrinet source = new Petrinet();	
		final Petrinet target = new Petrinet();		
		
		Place previousPlace = source.addPlace("a");

		for (int i = 0; i < 1000; i++) {
			Transition transition = source.addTransition("");
			Place      place      = source.addPlace("");
			source.addPreArc("", previousPlace, transition);
			source.addPostArc("", transition, previousPlace);
		}

		previousPlace = target.addPlace("");
		
		for (int i = 0; i < 1000; i++) {
			Transition transition = target.addTransition("");
			Place      place      = target.addPlace("");
			target.addPreArc("", previousPlace, transition);
			target.addPostArc("", transition, previousPlace);
		}


		long      randomSeed              = 1135113;
		
		VF2 matcher = VF2.getInstance(source, target, new Random(randomSeed++));
		try {
			System.out.println(System.currentTimeMillis());
			assertNotNull(matcher.getMatch(false));
			System.out.println(System.currentTimeMillis());
		} catch (MatchException e) {
			System.out.println(System.currentTimeMillis());
			fail();
		}
		
	}
*/

	@Test
	public void testNondeterminism_1() {
		int[] sourceLayersNodes  = {1,1,1,2};		
		int[] targetLayersNodes1 = sourceLayersNodes;	
		int[] targetLayersNodes2 = {1,8,1,2};
		
		final Petrinet source = new Petrinet();	
		final Petrinet target = new Petrinet();		

		buildTreePetrinetComponent(source, sourceLayersNodes); 
		buildTreePetrinetComponent(target, targetLayersNodes1);
		buildTreePetrinetComponent(target, targetLayersNodes2); 
		buildTreePetrinetComponent(target, targetLayersNodes1);	

		MatchesCountsMatchVisitor visitor = new MatchesCountsMatchVisitor();	
		long      randomSeed              = 113513;
		final int rounds                  = 2000;
		
		
		for (int i = 0; i < rounds; i++) {
			VF2 matcher = VF2.getInstance(source, target, new Random(randomSeed++));	
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
	//	System.out.println(lowerBorder + " <= " + minMatched + " | " + idealMatched + " | " + maxMatched + " <= " + upperBorder);

		assertTrue(lowerBorder <= minMatched && maxMatched <= upperBorder);
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
	
	private long getVariationsCount(int n, int k) {
		return factorial(n) / factorial(n - k);
	}
	
	private long factorial(int n) {
		long factorial = 1;
		
		for (int i = 1; i <= n; i++) {
			factorial *= i;
		}
		
		return factorial;
	}
}