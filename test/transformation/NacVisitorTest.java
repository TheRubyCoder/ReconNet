package transformation;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.matcher.NacVisitor;
import transformation.matcher.PNVF2;
import transformation.matcher.PNVF2.MatchException;

public class NacVisitorTest {

	@Before
	public void setUp() throws Exception {
		// Petrinet's create

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * N mit 1 Stelle und 2 Options
	 */

	/*
	 * Negative Test
	 */
	@Test
	public void testVisitNegativeNWithOneVertexOptions() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertFalse(nacVisitor.visit(matchLinN));
	}

	/*
	 * Positive Test
	 */
	@Test
	public void testVisitPositiveNWithOneVertexOptions() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac = rule.addTransitionToNac("", nac);
		rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
		rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertTrue(nacVisitor.visit(matchLinN));
	}

	/*
	 * Negative Test
	 */
	@Test
	public void testVisitNoNacNWithOneVertexOptions() {
		Rule rule = new Rule();
		rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertTrue(nacVisitor.visit(matchLinN));
	}

	/*
	 * Positive Test
	 */
	@Test
	public void testVisitPositiveTwoNacsNWithOneVertexOptions() {
		Rule rule = new Rule();
		NAC nac1 = rule.createNAC();
		NAC nac2 = rule.createNAC();

		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
		rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1,
				nac1);
		rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
				nac1);

		Transition transitionInNac2 = rule.addTransitionToNac("", nac2);
		rule.addPreArcToNac("", nac2.fromLtoNac(placeInL), transitionInNac2,
				nac2);
		rule.addPostArcToNac("", transitionInNac2, nac2.fromLtoNac(placeInL),
				nac2);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertTrue(nacVisitor.visit(matchLinN));
	}

	/*
	 * Negative Test
	 */
	@Test
	public void testVisitNegativeTwoNacsNWithOneVertexOptions() {
		Rule rule = new Rule();
		NAC nac1 = rule.createNAC();
		NAC nac2 = rule.createNAC();

		Place placeInL = rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertFalse(nacVisitor.visit(matchLinN));
	}

	/*
	 * Positive Test
	 */
	@Test
	public void testVisitNegativeTwoMixNacsNWithOneVertexOptions() {
		Rule rule = new Rule();
		NAC nac1 = rule.createNAC();
		NAC nac2 = rule.createNAC();

		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
		rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1,
				nac1);
		rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
				nac1);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertFalse(nacVisitor.visit(matchLinN));
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * N mit 2 Stellen und 2 Options
	 */

	/*
	 * Negative Test
	 */
	@Test
	public void testVisitNegativeNWithTwoVertexOptions() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		n.addPlace("");

		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertFalse(nacVisitor.visit(matchLinN));
	}

	/*
	 * Positive Test
	 */
	@Test
	public void testVisitPositiveNWithTwoVertexOptions() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac = rule.addTransitionToNac("", nac);
		rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
		rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertTrue(nacVisitor.visit(matchLinN));
	}

	/*
	 * Negative Test
	 */
	@Test
	public void testVisitNoNacNWithTwoVertexOptions() {
		Rule rule = new Rule();
		rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertTrue(nacVisitor.visit(matchLinN));
	}

	/*
	 * Positive Test
	 */
	@Test
	public void testVisitPositiveTwoNacsNWithTwoVertexOptions() {
		Rule rule = new Rule();
		NAC nac1 = rule.createNAC();
		NAC nac2 = rule.createNAC();

		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
		rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1,
				nac1);
		rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
				nac1);

		Transition transitionInNac2 = rule.addTransitionToNac("", nac2);
		rule.addPreArcToNac("", nac2.fromLtoNac(placeInL), transitionInNac2,
				nac2);
		rule.addPostArcToNac("", transitionInNac2, nac2.fromLtoNac(placeInL),
				nac2);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertTrue(nacVisitor.visit(matchLinN));
	}

	/*
	 * Negative Test
	 */
	@Test
	public void testVisitNegativeTwoNacsNWithTwoVertexOptions() {
		Rule rule = new Rule();
		NAC nac1 = rule.createNAC();
		NAC nac2 = rule.createNAC();

		Place placeInL = rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertFalse(nacVisitor.visit(matchLinN));
	}

	/*
	 * Positive Test
	 */
	@Test
	public void testVisitNegativeTwoMixNacsNWithTwoVertexOptions() {
		Rule rule = new Rule();
		NAC nac1 = rule.createNAC();
		NAC nac2 = rule.createNAC();

		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac1 = rule.addTransitionToNac("", nac1);
		rule.addPreArcToNac("", nac1.fromLtoNac(placeInL), transitionInNac1,
				nac1);
		rule.addPostArcToNac("", transitionInNac1, nac1.fromLtoNac(placeInL),
				nac1);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		n.addPlace("");
		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		assertFalse(nacVisitor.visit(matchLinN));
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 2 Stellen 1 Option
	 */

	/*
	 * Nac = * L = * N = * *-|
	 */
	@Test
	public void testMatchesCounts() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Match matchLinN = null;
		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false);
		} catch (MatchException e) {
			e.printStackTrace();
		}
		nacVisitor.visit(matchLinN);
		nacVisitor.visit(matchLinN);
		nacVisitor.visit(matchLinN);
		int res = 0;
		for (Integer count : nacVisitor.getMatchesCounts().values()) {
			res += count;
		}
		assertTrue(3 == res);
	}

	/*
	 * 
	 * 
	 * Backtracking Test
	 */

	/*
	 * Nac = * L = * N = * *-|
	 */
	@Test
	public void oneMorphismTwoOptions() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac = rule.addTransitionToNac("", nac);
		rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
		rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Match matchLinN = null;
		int res = 0;
		do {
			try {
				matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false,
						nacVisitor);
			} catch (MatchException e) {
				System.out.println("NAC ist in N vorhanden");
			}
			res = 0;
			for (Integer count : nacVisitor.getMatchesCounts().values()) {
				res += count;
			}
			System.out.println("nacVisitorCounter  = "
					+ nacVisitor.getMatchesCounts());
			System.out.println("--------");
		} while (res == 1);
		System.out.println(nacVisitor.getMatchesCounts());
		System.out.println("L *************");
		System.out.println(rule.getL());
		System.out.println("N *************");
		System.out.println(n);
		System.out.println("NAC *************");
		System.out.println(nac.getPlaceMappingLToNac());
		System.out.println("**************");

		assertTrue(res > 1);
	}

	/*
	 * 
	 * 
	 * Backtracking Test
	 */

	/*
	 * Nac = * L = * N = * *-|
	 */
	@Test
	public void noMorphismTwoOptions() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		Place placeInL = rule.addPlaceToL("");

		NacVisitor nacVisitor = new NacVisitor(rule);
		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Match matchLinN = null;
		int res = 0;

		try {
			matchLinN = PNVF2.getInstance(rule.getL(), n).getMatch(false,
					nacVisitor);
			fail();
		} catch (MatchException e) {

		}
		res = 0;
		for (Integer count : nacVisitor.getMatchesCounts().values()) {
			res += count;
		}
		System.out.println("nacVisitorCounter  = "
				+ nacVisitor.getMatchesCounts());
		System.out.println("--------");

		assertTrue(res == 2);
	}

	/*
	 * Keine Transformation möglich, da NAC = L ist
	 */
	@Test
	public void transformationWithNacVisitor() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		Place placeInL = rule.addPlaceToL("");

		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Transformation transformation = TransformationComponent
				.getTransformation().transform(n, rule);

		assertNull(transformation);
	}

	/*
	 * Transformation möglich
	 */
	@Test
	public void transformationWithNacVisitor2() {
		Rule rule = new Rule();
		NAC nac = rule.createNAC();
		Place placeInL = rule.addPlaceToL("");
		Transition transitionInNac = rule.addTransitionToNac("", nac);
		rule.addPreArcToNac("", nac.fromLtoNac(placeInL), transitionInNac, nac);
		rule.addPostArcToNac("", transitionInNac, nac.fromLtoNac(placeInL), nac);

		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Transformation transformation = TransformationComponent
				.getTransformation().transform(n, rule);

		assertNotNull(transformation);
	}

	/*
	 * Transformation möglich , ohne nac
	 */
	@Test
	public void transformationWithNacVisitor3() {
		Rule rule = new Rule();

		Place placeInL = rule.addPlaceToL("");

		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Transformation transformation = TransformationComponent
				.getTransformation().transform(n, rule);

		assertNotNull(transformation);
	}

	/*
	 * Keine Transformation möglich, L nicht in N, ohne NAC
	 */
	@Test
	public void transformationWithNacVisitor4() {
		Rule rule = new Rule();

		Place placeInL = rule.addPlaceToL("");
		Transition transitionInL = rule.addTransitionToL("");
		rule.addPreArcToL("", placeInL, transitionInL);
		rule.addPostArcToL("", transitionInL, placeInL);

		Petrinet n = new Petrinet();
		n.addPlace("");
		Place placeWithTransition = n.addPlace("");
		Transition transition = n.addTransition("");
		n.addPreArc("", placeWithTransition, transition);
		n.addPostArc("", transition, placeWithTransition);

		Transformation transformation = TransformationComponent
				.getTransformation().transform(n, rule);

		assertNotNull(transformation);
	}
}
