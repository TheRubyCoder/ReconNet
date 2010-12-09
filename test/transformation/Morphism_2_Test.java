package transformation;

import transformation.tempPetriNet.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Morphism_2_Test {

	public static void main(String[] args) throws FileNotFoundException {
		
//		System.out.println("Petrinetze Seite 145 / 146");
//		test("tempPetriNet/Petrinetz_Seite_145.pn", "tempPetriNet/Petrinetz_Seite_146.pn");
		
		System.out.println("Petrinetze Seite 148");
		test("tempPetriNet/Petrinetz_A_Seite_148.pn", "tempPetriNet/Petrinetz_B_Seite_148.pn");
		
		
	}
	
	
	private static void test(String netA, String netB) throws FileNotFoundException {
		InputStream isA = Morphism_2_Test.class.getResourceAsStream(netA);
		InputStream isB = Morphism_2_Test.class.getResourceAsStream(netB);
		
		if (isA == null || isB == null) {
			System.out.println("Resource not found");
			return;
		}
		PetriNet pnA = new PetriNetImpl(isA);
		System.out.println(pnA);

		PetriNet pnB = new PetriNetImpl(isB);
		System.out.println(pnB);
		
		class Container {
			Map<Integer, Integer> places;
			Map<Integer, Integer> transitions;
			
			Container(Map<Integer, Integer> places, Map<Integer, Integer> transitions) {
				this.places = places;
				this.transitions = transitions;
			}


			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Container other = (Container) obj;
				if (places == null) {
					if (other.places != null)
						return false;
				} else if (!places.equals(other.places))
					return false;
				if (transitions == null) {
					if (other.transitions != null)
						return false;
				} else if (!transitions.equals(other.transitions))
					return false;
				return true;
			}
			
		}
		
		List<Container> results = new LinkedList<Container>();
		
		int[] counter = new int[100];
		
		if (new Morphism_2(pnA, pnB).places() == null) {
			System.exit(1);
		}
		
		for (int i = 0; i < 1000; i++) {
			Morphism_2 mm = new Morphism_2(pnA, pnB);

			//		System.out.printf("m0_places%n%s%nm0_transtions%n%s%n", 
			//				booleanMatrixToString(mm.m0_places),
			//				booleanMatrixToString(mm.m0_transitions));
			System.out.printf("places %s   transitions %s%n", mm.places(), mm.transitions());

			int index;
			Container cont = new Container(mm.places(), mm.transitions());
			if ((index = results.indexOf(cont)) == -1) {
				counter[results.size()]++;
				results.add(cont);
				
			} else {
				counter[index]++;
			}
		}
		
		for (int i = 0; i < results.size(); i++) {
			System.out.printf("%3d: %3d%n", i, counter[i]);
			System.out.printf("     places %s   transitions %s%n", results.get(i).places, results.get(i).transitions);
		}
		System.out.println("ENDE");
		
	}

	
	
	
	private static String booleanMatrixToString(boolean[][] matrix) {
		String separator = System.getProperty("line.separator");
		String result = "";
		for (boolean[] b : matrix) {
			for (boolean v : b) {
				result = result.concat(v == true ? " 1" : " 0");
			}
			result = result.concat(separator);
		}
		return result;
	}
	
}
