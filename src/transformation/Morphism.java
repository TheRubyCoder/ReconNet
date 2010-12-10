package transformation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;

public class Morphism implements IMorphism {

	
	private final PetriNet netA;
	private final PetriNet netB;
	
	
	private Map<Integer, Integer>[] netA_placesInEdges;
	private Map<Integer, Integer>[] netA_placesOutEdges;
	private Map<Integer, Integer>[] netA_transitionsInEdges;
	private Map<Integer, Integer>[] netA_transitionsOutEdges;
	private Map<Integer, Integer>[] netB_placesInEdges;
	private Map<Integer, Integer>[] netB_placesOutEdges;
	private Map<Integer, Integer>[] netB_transitionsInEdges;
	private Map<Integer, Integer>[] netB_transitionsOutEdges;
	
	/*private*/ boolean[][] m0_places;
	/*private*/ boolean[][] m0_transitions;
	
	private Map<Integer, Integer> transitions;

	private Map<Integer, Integer> places;
	
	
	
	
	
	public Morphism(IPetrinet from, IPetrinet to) {
		this.netA = new PetrinetWrapper(from);
		this.netB = new PetrinetWrapper(to);
		init();
		createM0_places();
		createM0_transitions();
		findMorphism();
	}
	
	
	public Morphism(PetriNet from, PetriNet to) {
		netA = from;
		netB = to;
		init();
		createM0_places();
		createM0_transitions();
		findMorphism();
	}
	
	
	public Map<Integer, Integer> temp_transitions() {
		return transitions;
	}
	
	
	public Map<Integer, Integer> temp_places() {
		return places;
	}
	
	
	private void init() {
		Map<Integer, Integer>[][] temp = countEdges(netA.getPre());
		netA_placesOutEdges = temp[0];
		netA_transitionsInEdges = temp[1];
		
		temp = countEdges(netA.getPost());
		netA_placesInEdges = temp[0];
		netA_transitionsOutEdges = temp[1];
		
		temp = countEdges(netB.getPre());
		netB_placesOutEdges = temp[0];
		netB_transitionsInEdges = temp[1];
		
		temp = countEdges(netB.getPost());
		netB_placesInEdges = temp[0];
		netB_transitionsOutEdges = temp[1];
	}
	
	
	private void createM0_places() {
		m0_places = new boolean[netA.getNumPlaces()][netB.getNumPlaces()];

		for (int indexA = 0; indexA < netA.getNumPlaces(); indexA++) {
			for (int indexB = 0; indexB < netB.getNumPlaces(); indexB++) {
				if (	netA.getPlaceNameByIndex(indexA).equals(netB.getPlaceNameByIndex(indexB)) &&
						netA.getTokenByIndex(indexA) <= netB.getTokenByIndex(indexB) &&
						testPlaceEdges(netA_placesInEdges[indexA], netB_placesInEdges[indexB]) &&
						testPlaceEdges(netA_placesOutEdges[indexA], netB_placesOutEdges[indexB])) {
						
					m0_places[indexA][indexB] = true;
				}
			}
		}
		
		// Speicherplatz freigeben
		netA_placesInEdges = null;
		netB_placesInEdges = null;
		netA_placesOutEdges = null;
		netB_placesOutEdges = null;
	}
	
	
	private void createM0_transitions() {
		m0_transitions = new boolean[netA.getNumTransitions()][netB.getNumTransitions()];

		for (int indexA = 0; indexA < netA.getNumTransitions(); indexA++) {
			for (int indexB = 0; indexB < netB.getNumTransitions(); indexB++) {
				if (	netA.getTransitionNameByIndex(indexA).equals(netB.getTransitionNameByIndex(indexB)) &&
						testTransitionEdges(netA_transitionsInEdges[indexA], netB_transitionsInEdges[indexB]) &&
						testTransitionEdges(netA_transitionsOutEdges[indexA], netB_transitionsOutEdges[indexB])) {
						
					m0_transitions[indexA][indexB] = true;
				}
			}
		}
		
		// Speicherplatz freigeben
		netA_transitionsInEdges = null;
		netB_transitionsInEdges = null;
		netA_transitionsOutEdges = null;
		netB_transitionsOutEdges = null;
	}
	
	
	private boolean testPlaceEdges(Map<Integer, Integer> edgesNetA, Map<Integer, Integer> edgesNetB) {
		for (Entry<Integer, Integer> e : edgesNetA.entrySet()) {
			int numWeightA = e.getValue();
			Integer temp = edgesNetB.get(e.getKey());
			int numWeightB = (temp == null ? 0 : temp);
			if (numWeightA > numWeightB) {
				return false;
			}
		}
		return true;
	}
	
	
	// Achtung: Aus der Map edgesNetB werden Elemente geloescht!
	private boolean testTransitionEdges(Map<Integer, Integer> edgesNetA, Map<Integer, Integer> edgesNetB) {
		for (Entry<Integer, Integer> e : edgesNetA.entrySet()) {
			int numWeightA = e.getValue();
			Integer temp = edgesNetB.remove(e.getKey());
			int numWeightB = (temp == null ? 0 : temp);
			if (numWeightA != numWeightB || !edgesNetB.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	private Map<Integer, Integer>[][] countEdges(Matrix m) {
		
		@SuppressWarnings("serial")
		class MyMap extends HashMap<Integer, Integer> {
			
			public void increment(int key) {
				Integer i = get(key);
				if (i == null) {
					put(key, 1);
				} else {
					put(key, ++i);
				}
			}
		}
		
		MyMap[] places = new MyMap[m.getNumRows()];
		MyMap[] transitions = new MyMap[m.getNumCols()];
		
		for (int i = 0; i < places.length; i++) {
			places[i] = new MyMap();
		}
		for (int j = 0; j < transitions.length; j++) {
			transitions[j] = new MyMap();
		}
		
		for (int r = 0; r < m.getNumRows(); r++) {
			for (int c = 0; c < m.getNumCols(); c++) {
				int weight = m.get(r, c);
				if (weight > 0) {
					places[r].increment(weight);
					transitions[c].increment(weight);
				}
			}
		}
		return new MyMap[][]{places, transitions};
	}
	
	
	
	
	
	
	
	
	private void findMorphism() {
		final int numVertices = netA.getNumPlaces() + netA.getNumTransitions();

		final BoolMatrix[] m_places = new BoolMatrix[numVertices];
		final BoolMatrix[] m_transitions = new BoolMatrix[numVertices];
		
		final List<Integer>[] placesB = new List[netA.getNumPlaces()]; // Alle moeglichen Zuordnungen von Stellen
		final List<Integer>[] transitionsB = new List[netA.getNumTransitions()]; // Alle moeglichen Zuordnungen von Transitionen
		

		final BoolMatrix m0_placesExt = new BoolMatrix(m0_places);
		final BoolMatrix m0_transitionsExt = new BoolMatrix(m0_transitions);
		
		
		int currentRow = 0;
		
		// In jedem Schleifendurchlauf in der aktuellen Zeile eine Zuordnung festlegen
		while (currentRow >= 0 && currentRow < numVertices) {
			
			// Referenzen auf Vorgaenger-Matrizen setzen
			final BoolMatrix prev_M_trans;
			final BoolMatrix prev_M_places;
			if (currentRow == 0) {
				prev_M_trans = m0_transitionsExt;
				prev_M_places = m0_placesExt;
			} else {
				prev_M_trans = m_transitions[currentRow - 1];
				prev_M_places = m_places[currentRow - 1];
			}

			if (currentRow < netA.getNumTransitions()) {
				// ------------------- Die Transition-Matrix bearbeiten ---------------------------------------------
				
				List<Integer> possibleMappings = transitionsB[currentRow]; // Die Liste aller moeglichen Zuordnungen in der aktuellen Zeile
				if (possibleMappings == null) {
					possibleMappings = prev_M_trans.getVerticesB(currentRow);
					Collections.shuffle(possibleMappings);
				}
				
				boolean searchReady = false;
				do {
					if (possibleMappings.isEmpty()) {
						// Wenn keine Moeglichkeit der Zuordnung vorhanden, eine Zeile zurueck gehen
						transitionsB[currentRow] = null;						
						currentRow--;
						searchReady = true; // Die innere Schleife beenden und dann die aeuﬂere Schleife wiederholen
					} else {
						// Eine Zuordnung festlegen und aus der Liste der moegl. Zuordnungen entfernen
						final int currentTransB = possibleMappings.remove(0);
						
						// Die Matrizen f¸r diesen Iterationsschritt als Kopien aus den Vorgaenger-Matrizen erstellen
						m_transitions[currentRow] = new BoolMatrix(prev_M_trans);
						m_places[currentRow] = new BoolMatrix(prev_M_places);
						
						// Wenn eine Nullzeile entsteht: Zuordnung nicht Ok --> die Schleife wiederholen
						if (m_transitions[currentRow].setTrue(currentRow, currentTransB) == false) {
							continue;
						}
						// Alle Zuordnungen entfernen, bei denen die Nachbarschaftsbedingung verletzt ist
						//  Wenn eine Nullzeile entsteht: Zuordnung nicht Ok --> die Schleife wiederholen
						if (neighbourCheckTransition(currentRow, currentTransB, m_places[currentRow]) == false) {
							continue;
						}
						
						// Wenn eine Zuordnung gefunden, die keine Nullzeilen produziert, eine Zeile weiter gehen
						currentRow++;
						searchReady = true; // Aus der inneren Schleife ausbrechen und dann die aeuﬂere Schleife wiederholen
					}
				} while (!searchReady);
				
			} else {
				// ------------------- Die Place-Matrix bearbeiten ---------------------------------------------

				final int currentRowPlaces = currentRow  - netA.getNumTransitions(); // Index berechnen fuer den Zugriff auf das Array placesB
				List<Integer> possibleMappings = placesB[currentRowPlaces]; // Die Liste aller moeglichen Zuordnungen in der aktuellen Zeile
				if (possibleMappings == null) {
					possibleMappings = prev_M_places.getVerticesB(currentRowPlaces);
					Collections.shuffle(possibleMappings);
				}
				
				boolean searchReady = false;
				do {
					if (possibleMappings.isEmpty()) {
						// Wenn keine Moeglichkeit der Zuordnung vorhanden, eine Zeile zurueck gehen
						placesB[currentRowPlaces] = null;						
						currentRow--;
						searchReady = true; // Die innere Schleife beenden und dann die aeuﬂere Schleife wiederholen
					} else {
						// Eine Zuordnung festlegen und aus der Liste der moegl. Zuordnungen entfernen
						final int currentPlaceB = possibleMappings.remove(0);
						
						// Die Matrizen f¸r diesen Iterationsschritt als Kopien aus den Vorgaenger-Matrizen erstellen
						m_transitions[currentRow] = new BoolMatrix(prev_M_trans);
						m_places[currentRow] = new BoolMatrix(prev_M_places);
						
						// Wenn eine Nullzeile entsteht: Zuordnung nicht Ok --> die Schleife wiederholen
						if (m_places[currentRow].setTrue(currentRowPlaces, currentPlaceB) == false) {
							continue;
						}
						// Alle Zuordnungen entfernen, bei denen die Nachbarschaftsbedingung verletzt ist
						//  Wenn eine Nullzeile entsteht: Zuordnung nicht Ok --> die Schleife wiederholen
						if (neighbourCheckPlace(currentRowPlaces, currentPlaceB, m_transitions[currentRow]) == false) {
							continue;
						}
						
						// Wenn eine Zuordnung gefunden, die keine Nullzeilen produziert, eine Zeile weiter gehen
						currentRow++;
						searchReady = true; // Aus der inneren Schleife ausbrechen und dann die aeuﬂere Schleife wiederholen
					}
				} while (!searchReady);
			}
		}
		
		if (currentRow < 0) {
			// Kein Morphismus vorhanden
			System.out.println("Kein Morphismus");
		} else { // currentRow == numVertices
			final int temp = currentRow - 1;
			
//			System.out.printf("m_places%n%s%nm_transitions%n%s%n", 
//					m_places[temp],
//					m_transitions[temp]);
			
			places = createMap(m_places[temp]);
			transitions = createMap(m_transitions[temp]);
		}
	
		// Speicherplatz freigeben
		m0_places = null;
		m0_transitions = null;
	}
	
	
	private boolean neighbourCheckTransition(final int transA, final int transB, BoolMatrix m_places) {
		// Nachbarschaften im Vorbereich der Transition testen
		for (int placeA = 0; placeA < netA.getNumPlaces(); placeA++) {
			final int a = netA.getPre().get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von transA: placeA
				// Abbildungen von placeA nach placeB ermitteln
				for (int placeB = 0; placeB < netB.getNumPlaces(); placeB++) {
					// Bei allen moeglichen Zuordnungen placeA --> placeB die Nachbarschaftsbedingung pruefen
					if (m_places.getValue(placeA, placeB)) {
						if (netB.getPre().get(placeB, transB) != a) {
							m_places.setFalse(placeA, placeB);
							if (m_places.getNumTrues(placeA) == 0) {
								return false;
							}
						}
					}
				}
			}
		}
		
		// Nachbarschaften im Nachbereich der Transition testen
		for (int placeA = 0; placeA < netA.getNumPlaces(); placeA++) {
			final int a = netA.getPost().get(placeA, transA);
			if (a > 0) { // Stelle im Nachbereich von transA: placeA
				// Abbildungen von placeA nach placeB ermitteln
				for (int placeB = 0; placeB < netB.getNumPlaces(); placeB++) {
					// Bei allen moeglichen Zuordnungen placeA --> placeB die Nachbarschaftsbedingung pruefen
					if (m_places.getValue(placeA, placeB)) {
						if (netB.getPost().get(placeB, transB) != a) {
							m_places.setFalse(placeA, placeB);
							if (m_places.getNumTrues(placeA) == 0) {
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
		
	}
	
	
	
	private boolean neighbourCheckPlace(final int placeA, final int placeB, BoolMatrix m_transitions) {
		// Nachbarschaften im Vorbereich der Stelle testen
		for (int transA = 0; transA < netA.getNumTransitions(); transA++) {
			final int a = netA.getPost().get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von placeA: transA
				// Abbildungen von transA nach transB ermitteln
				for (int transB = 0; transB < netB.getNumTransitions(); transB++) {
					// Bei allen moeglichen Zuordnungen transA --> transB die Nachbarschaftsbedingung pruefen
					if (m_transitions.getValue(transA, transB)) {
						if (netB.getPost().get(placeB, transB) != a) {
							m_transitions.setFalse(transA, transB);
							if (m_transitions.getNumTrues(transA) == 0) {
								return false;
							}
						}
					}
				}
			}
		}
		
		// Nachbarschaften im Vorbereich der Stelle testen
		for (int transA = 0; transA < netA.getNumTransitions(); transA++) {
			final int a = netA.getPre().get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von placeA: transA
				// Abbildungen von transA nach transB ermitteln
				for (int transB = 0; transB < netB.getNumTransitions(); transB++) {
					// Bei allen moeglichen Zuordnungen transA --> transB die Nachbarschaftsbedingung pruefen
					if (m_transitions.getValue(transA, transB)) {
						if (netB.getPre().get(placeB, transB) != a) {
							m_transitions.setFalse(transA, transB);
							if (m_transitions.getNumTrues(transA) == 0) {
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	
//	private boolean neighbourCheckPlace(BoolMatrix m_transitions, final int placeA, final int placeB) {
//		// Nachbarschaften im Vorbereich der Stelle testen
//		for (int transA = 0; transA < netA.getNumTransitions(); transA++) {
//			final int a = netA.getPost().get(placeA, transA);
//			if (a > 0) { // Stelle im Vorbereich von placeA: transA
//				// Abbildungen von transA nach transB ermitteln
//				for (int transB = 0; transB < netB.getNumTransitions(); transB++) {
//					// Bei allen moeglichen Zuordnungen transA --> transB die Nachbarschaftsbedingung pruefen
//					if (m_transitions.getValue(transA, transB)) {
//						if (netB.getPost().get(placeB, transB) != a) {
//							m_transitions.setFalse(transA, transB);
//							if (m_transitions.getNumTrues(transA) == 0) {
//								return false;
//							}
//						}
//					}
//				}
//			}
//		}
//		return true;
//	}
	
	
	private static Map<Integer, Integer> createMap(BoolMatrix m) {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		
		for (int r = 0; r < m.getNumRows(); r++) {
			assert m.getNumTrues(r) == 1;
			for (int c = 0; /*c < m[0].getNumCols()*/; c++) {
				if (m.getValue(r, c)) {
					result.put(r, c);
					break;
				}
			}
		}
		
		return Collections.unmodifiableMap(result);
	}
	
	
	/*private*/ static class BoolMatrix {
		final boolean[][] matrix;
		final int[] numTrues;
		
		BoolMatrix(boolean[][] matrix) {
			this.matrix = matrix;
			numTrues = new int[matrix.length];
			for (int r = 0; r < getNumRows(); r++) {
				for (int c = 0; c < getNumCols(); c++) {
					if (getValue(r, c)) {
						numTrues[r]++;
					}
				}
			}
		}
		
		BoolMatrix(BoolMatrix m) {
			matrix = new boolean[m.getNumRows()][];
			for (int r = 0; r < matrix.length; r++) {
				matrix[r] = Arrays.copyOf(m.matrix[r], m.getNumCols());
			}
			numTrues = Arrays.copyOf(m.numTrues, matrix.length);
		}
		
		int getNumRows() {
			return matrix.length;
		}
		
		int getNumCols() {
			return matrix[0].length;
		}
		
		boolean getValue(int row, int col) {
			return matrix[row][col];
		}
		
		
		int getNumTrues(int row) {
			return numTrues[row];
		}
		
		int getRowWithOneTrue() {
			for (int r = 0; r < getNumRows(); r++) {
				if (numTrues[r] == 1) {
					return r;
				}
			}
			return -1;
		}
		
		
		boolean setFalse(int row, int col) {
			if (matrix[row][col] == true) {
				matrix[row][col] = false;
				numTrues[row]--;
				return true;
			}
			return false;
		}
		
		
		boolean setTrue(int vertexA, int vertexB) {
			// in der Zeile alle anderen auf false
			for (int c = 0; c < getNumCols(); c++) {
				if (c != vertexB) {
					setFalse(vertexA, c);
				}
			}
			List<Integer> rowsWithOneTrue = new LinkedList<Integer>();
			// in der Spalte alle anderen auf false
			for (int r = 0; r < getNumRows(); r++) {
				if (r != vertexA) {
					if (setFalse(r, vertexB)) {
						if (numTrues[r] == 1) {
							rowsWithOneTrue.add(r);
						} else if (numTrues[r] == 0) {
							return false;
						}
					}
				}
			}
			while (!rowsWithOneTrue.isEmpty()) {
				final int rowWithOneTrue = rowsWithOneTrue.remove(0);
				int col;
				// die Spalte mit dem true ermitteln
				for (col = 0; /*col < result.getNumCols()*/; col++) {
					if (getValue(rowWithOneTrue, col) == true) {
						break;
					}
				}
				// in der Spalte alle anderen auf false
				for (int r = 0; r < getNumRows(); r++) {
					if (r != rowWithOneTrue) {
						if (setFalse(r, col)) {
							if (numTrues[r] == 1) {
								rowsWithOneTrue.add(r);
							} else if (numTrues[r] == 0) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}
		
		
		List<Integer> getVerticesB(int vertexA) {
			List<Integer> result = new LinkedList<Integer>();
			for (int c = 0; c < getNumCols(); c++) {
				if (getValue(vertexA, c) == true) {
					result.add(c);
				}
			}
			return result;
		}
		
		
		@Override
		public String toString() {
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


	@Override
	public Map<IArc, IArc> edges() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ITransition morph(ITransition transition) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IPlace morph(IPlace place) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IArc morph(IArc arc) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean IsValid() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public IPetrinet From() {
		return ((PetrinetWrapper) netA).getIPetrinet();
	}


	@Override
	public IPetrinet To() {
		return ((PetrinetWrapper) netB).getIPetrinet();
	}


	@Override
	public Map<IPlace, IPlace> places() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<ITransition, ITransition> transitions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
