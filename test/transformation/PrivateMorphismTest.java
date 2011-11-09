//package transformation;
//
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import petrinetze.IPetrinet;
//import petrinetze.IPlace;
//import petrinetze.ITransition;
//import transformation.IMorphism;
//import transformation.MorphismFactory;
//
//
//
//public class PrivateMorphismTest {
//
//	public static void main(String[] args) throws FileNotFoundException {
//
////			System.out.println("Petrinetze Seite 145 / 146");
////			test("tempPetriNet/Petrinetz_Seite_145.pn", "tempPetriNet/Petrinetz_Seite_146.pn");
//
//		System.out.println("Petrinetze Seite 148");
//		test("Petrinetz_A_Seite_148.pn", "Petrinetz_B_Seite_148.pn");
//
////		System.out.println("Testobjekt");
////		test("Testobjekt_Regel_r1_Linke_Seite.pn", "Testobjekt_unten_links.pn");
//
//
//	}
//
//
//	private static void test(String netA, String netB) throws FileNotFoundException {
//		InputStream isA = PrivateMorphismTest.class.getResourceAsStream(netA);
//		InputStream isB = PrivateMorphismTest.class.getResourceAsStream(netB);
//
//		if (isA == null || isB == null) {
//			System.out.println("Resource not found");
//			return;
//		}
//		IPetrinet pnA = new PetrinetMock(isA);
//		System.out.println(pnA);
//
//		IPetrinet pnB = new PetrinetMock(isB);
//		System.out.println(pnB);
//
//		class Container {
//			Map<IPlace, IPlace> places;
//			Map<ITransition, ITransition> transitions;
//
//			Container(Map<IPlace, IPlace> places, Map<ITransition, ITransition> transitions) {
//				this.places = places;
//				this.transitions = transitions;
//			}
//
//            @Override
//            public boolean equals(Object o) {
//                if (this == o) return true;
//                if (o == null || getClass() != o.getClass()) return false;
//
//                Container container = (Container) o;
//
//                if (places != null ? !places.equals(container.places) : container.places != null) return false;
//                if (transitions != null ? !transitions.equals(container.transitions) : container.transitions != null)
//                    return false;
//
//                return true;
//            }
//
//            @Override
//            public int hashCode() {
//                int result = places != null ? places.hashCode() : 0;
//                result = 31 * result + (transitions != null ? transitions.hashCode() : 0);
//                return result;
//            }
//        }
//
//		List<Container> results = new LinkedList<Container>();
//
//		int[] counter = new int[100];
//
//		IMorphism testMorphism = MorphismFactory.createMorphism(pnA, pnB);
//		if (testMorphism == null) {
//			System.out.println("Es existiert kein Morphismus");
//			System.exit(1);
//		}
//
//		for (int i = 0; i < 1000; i++) {
//			IMorphism mm = MorphismFactory.createMorphism(pnA, pnB);
//
//			//		System.out.printf("m0_places%n%s%nm0_transtions%n%s%n", 
//			//				booleanMatrixToString(mm.m0_places),
//			//				booleanMatrixToString(mm.m0_transitions));
////			System.out.printf("places %s   transitions %s%n", mm.places(), mm.transitions());
//
//			int index;
//			Container cont = new Container(mm.getPlacesMorphism(), mm.getTransitionsMorphism());
//			if ((index = results.indexOf(cont)) == -1) {
//				counter[results.size()]++;
//				results.add(cont);
//
//			} else {
//				counter[index]++;
//			}
//		}
//
//		for (int i = 0; i < results.size(); i++) {
//			System.out.printf(
//					"Variante %3d: %3d mal erzeugt%n" +
//					"     places-Map %s%ntransitions-Map %s%n",
//					i, counter[i], results.get(i).places, results.get(i).transitions);
////			System.out.printf("     places %s%ntransitions %s%n", results.get(i).places, results.get(i).transitions);
//		}
//		System.out.println("ENDE");
//
//	}
//
//
//
//
//	private static String booleanMatrixToString(boolean[][] matrix) {
//		String separator = System.getProperty("line.separator");
//		String result = "";
//		for (boolean[] b : matrix) {
//			for (boolean v : b) {
//				result = result.concat(v == true ? " 1" : " 0");
//			}
//			result = result.concat(separator);
//		}
//		return result;
//	}
//
//
//}
