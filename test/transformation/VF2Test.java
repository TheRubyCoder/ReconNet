package transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
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
import transformation.matcher.VF2.*;

/**
 * Testing the morphism of places like specified in
 * "../additional/images/Isomorphism_transitions.png"
 */

public class VF2Test {
	class VF2TestMatchVisitor implements MatchVisitor {
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
		VF2 vf2 = new VF2(source, target);

		VF2TestMatchVisitor visitor = new VF2TestMatchVisitor();		
		vf2.getMatch(visitor);	
		
		
		long variations = getVariationsCount(target.getPlaces().size(), source.getPlaces().size()) *
				getVariationsCount(target.getTransitions().size(), source.getTransitions().size());
		
		assertEquals(
			visitor.getMatchesCount(),
			variations
		);		
	}


	@Test
	public void testNondeterminism_1() {
		int[] sourceLayersNodes  = {1,1,3,3};		
		int[] targetLayersNodes1 = sourceLayersNodes;	
		int[] targetLayersNodes2 = {1,8,3,3};
		
		Petrinet source = new Petrinet();	
		Petrinet target = new Petrinet();		

		Map<Integer, Set<INode>> sourceLayers = buildTreePetrinetComponent(source, sourceLayersNodes);
		Map<Integer, Set<INode>> targetLayers = buildTreePetrinetComponent(target, targetLayersNodes2); 
			
		for (int i = 0; i < 2; i++) {
			for (Map.Entry<Integer, Set<INode>> entry : buildTreePetrinetComponent(target, targetLayersNodes1).entrySet()) {
				targetLayers.get(entry.getKey()).addAll(entry.getValue());
			}
		}
/*
		System.out.println(source);
		System.out.println(sourceLayers);
		System.out.println(target);
		System.out.println(targetLayers);

		System.out.println(target.getPlaces().size());
		System.out.println(target.getTransitions().size());
		*/
				
		long      randomSeed                 = 156135113;
		final int rounds                     = 2000;

		Map<INode, Integer> nodesCount = new HashMap<INode, Integer>();
		
		for (int i = 0; i < rounds; i++) {
			VF2 matcher = new VF2(source, target, new Random(randomSeed++));
			
			Match match = matcher.getMatch();
			
			assertNotNull(match);
			
			for (Map.Entry<Integer, Set<INode>> layer : sourceLayers.entrySet()) {
				for (INode node : layer.getValue()) {
					INode   matchedNode;
					
					if (node instanceof Place) {
						matchedNode = match.getPlace((Place) node);
					} else {
						matchedNode = match.getTransition((Transition) node);					
					}
	
					if (nodesCount.get(matchedNode) != null) {
						nodesCount.put(matchedNode, nodesCount.get(matchedNode) + 1);
					} else  {
						nodesCount.put(matchedNode, 1);
					}
					
				}				
			}
		}
		

		final int allowedDeviationInPercent = 20;
		

		for (Map.Entry<Integer, Set<INode>> layer : targetLayers.entrySet()) {			
			//System.out.println("Ebene: " + layer.getKey());

			int matchedSum = 0;
			int minMatched = Integer.MAX_VALUE;
			int maxMatched = Integer.MIN_VALUE;
			
			for (INode node : layer.getValue()) {				
				assertNotNull(nodesCount.get(node));
				
				int matched = nodesCount.get(node);
				
				//System.out.println(node + " -> " + matched);
				
				matchedSum += matched;
				minMatched = Math.min(minMatched, matched);
				maxMatched = Math.max(minMatched, matched);	
			}				
			
			if (layer.getKey() == sourceLayersNodes.length - 1) {
				int idealMatched = matchedSum / layer.getValue().size();
				
				final int lowerBorder = (idealMatched / 100) * (100 - allowedDeviationInPercent);
				final int upperBorder = (idealMatched / 100) * (100 + allowedDeviationInPercent);
	
				assertTrue(lowerBorder <= minMatched && maxMatched <= upperBorder);
			}
		}
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