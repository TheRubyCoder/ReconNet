package transformation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import petrinetze.impl.Petrinet;

/**
 * Finds Morphisms between two petrinets in a not deterministic way
 */
public class MorphismFactory {

	
	/**
	 * Finds a morphism between two petrinets.<br/>
	 * Not deterministic.
	 * @param from the Petrinet from which this morphism starts.
	 * @param to the Petrinet into which this morphism maps to.
	 * @return the new morphism or null if no morphism exists between from and to
	 */
	public static IMorphism createMorphism(IPetrinet from, IPetrinet to) {
		return new MorphismFactory(from, to).getMorphism();
	}
	
	/** "from" petrinet of the morphism to find */
	private final IPetrinet fromNet;
	/** Matrix that represents pre of "from" matrix */
	private final IMatrix fromNetPre;
	/** Matrix that represents post of "from" matrix */
	private final IMatrix fromNetPost;
	/** Number of places in "from" net */
	private final int numPlacesFromNet;
	/** Number of transitions in "from" net */
	private final int numTransitionsFromNet;
	
	/** "to" petrinet of the morphism to find */
	private final IPetrinet toNet;
	/** Matrix that represents pre of "to" matrix */
	private final IMatrix toNetPre;
	/** Matrix that represents post of "to" matrix */
	private final IMatrix toNetPost;
	/** Number of places in "to" net */
	private final int numPlacesToNet;
	/** Number of transitions in "to" net */
	private final int numTransitionsToNet;


	// Bedeutung von Arrayindex, key und value der Maps in den folgenden Zeilen:
	// Arrayindex: Eine Stelle/Transition (Zeilennr./Spaltennr. in pre-/post-Matrix)
	// key: Kantengewicht
	// value: Die Anzahl der Kanten, die dieses Kantengewicht haben 
	/** Mapping for each entry in the <b>preMatrix</b> of "from" net with <ul><tt>key</tt> = weight of edges and <br/><tt>value</tt> = number of edges with that weight</ul>
	 * <h1>Examples:</h1>
	 * If there is an 8 in the matrix, does it mean <ul><li>1 edge with weight 5 and 1 edge with weight 3 (5+3)</li> 
	 * <li>or 1 edge with weight 6 and 1 edge weight 2 (6+2)</li>
	 * <li>or just 1 edge with weight 8 (8)?</li></ul> */
	private Map<Integer, Integer>[] weightDistributionInPreFrom; // Kantengewichte der in die Stellen von netA eingehenden Kanten
	
	/** Mapping for each entry in the <b>postMatrix</b>of "from" net with <ul><tt>key</tt> = weight of edges and <br/><tt>value</tt> = number of edges with that weight</ul>
	 * <h1>Examples:</h1>
	 * If there is an 8 in the matrix, does it mean <ul><li>1 edge with weight 5 and 1 edge with weight 3 (5+3)</li> 
	 * <li>or 1 edge with weight 6 and 1 edge weight 2 (6+2)</li>
	 * <li>or just 1 edge with weight 8 (8)?</li></ul> */
	private Map<Integer, Integer>[] weightDistributionInPostFrom; // Kantengewichte der aus den Stellen von netA ausgehenden Kanten
	
	private Map<Integer, Integer>[] netA_transitionsInEdges; // Kantengewichte der in die Transitionen von netA eingehenden Kanten
	private Map<Integer, Integer>[] netA_transitionsOutEdges; // Kantengewichte der aus den Transitionen von netA ausgehenden Kanten
	private Map<Integer, Integer>[] netB_placesInEdges; // ab hier das gleiche fuer netB
	private Map<Integer, Integer>[] netB_placesOutEdges;
	private Map<Integer, Integer>[] netB_transitionsInEdges;
	private Map<Integer, Integer>[] netB_transitionsOutEdges;

	boolean[][] m0_places;
	boolean[][] m0_transitions;

	private Map<IPlace, IPlace> places;
	private Map<ITransition, ITransition> transitions;
	private Map<IArc, IArc> edges;




	private MorphismFactory(IPetrinet from, IPetrinet to) {
		fromNet = from;
		toNet = to;
		fromNetPre = new MatrixImpl(fromNet.getPre().getPreAsArray());
		fromNetPost = new MatrixImpl(fromNet.getPost().getPostAsArray());
		numPlacesFromNet = fromNetPre.getNumRows();
		numTransitionsFromNet = fromNetPre.getNumCols();
		toNetPre = new MatrixImpl(toNet.getPre().getPreAsArray());
		toNetPost = new MatrixImpl(toNet.getPost().getPostAsArray());
		numPlacesToNet = toNetPre.getNumRows();
		numTransitionsToNet = toNetPre.getNumCols();

		init();
		
		// Nun werden die 
		createM0_places();
		createM0_transitions();
	}
	
	
	private IMorphism getMorphism() {
		boolean successful = findMorphism();
		if (successful) {
			return new Morphism(fromNet, toNet, places, transitions, edges);
		} else {
			return null;
			//return new Morphism(new Petrinet(), new Petrinet(), new HashMap<IPlace, IPlace>(), new HashMap<ITransition, ITransition>(), new HashMap<IArc, IArc>());
		}
	}



	private void init() {
		Container temp = countEdges(fromNetPre);
		weightDistributionInPostFrom = temp.places;
		netA_transitionsInEdges = temp.transitions;

		temp = countEdges(fromNetPost);
		weightDistributionInPreFrom = temp.places;
		netA_transitionsOutEdges = temp.transitions;

		temp = countEdges(toNetPre);
		netB_placesOutEdges = temp.places;
		netB_transitionsInEdges = temp.transitions;

		temp = countEdges(toNetPost);
		netB_placesInEdges = temp.places;
		netB_transitionsOutEdges = temp.transitions;
	}


	private void createM0_places() {
		m0_places = new boolean[numPlacesFromNet][numPlacesToNet];

		for (int indexA = 0; indexA < numPlacesFromNet; indexA++) {
			final IPlace placeA = fromNet.getPlaceById(fromNet.getPre().getPlaceIds()[indexA]);
			for (int indexB = 0; indexB < numPlacesToNet; indexB++) {
				final IPlace placeB = toNet.getPlaceById(toNet.getPre().getPlaceIds()[indexB]);
				// Alle Bedingungen pruefen, die erfuellt sein muessen, damit die m0-Matrix fuer das aktuelle Mapping placeA --> placeB einen true-Eintrag bekommt
				if (	placeA.getName().equals(placeB.getName()) &&
						placeA.getMark() <= placeB.getMark() &&
						testPlaceEdges(weightDistributionInPreFrom[indexA], netB_placesInEdges[indexB]) &&
						testPlaceEdges(weightDistributionInPostFrom[indexA], netB_placesOutEdges[indexB])) {

					m0_places[indexA][indexB] = true;
				}
			}
		}

		// Speicherplatz freigeben
		weightDistributionInPreFrom = null;
		netB_placesInEdges = null;
		weightDistributionInPostFrom = null;
		netB_placesOutEdges = null;
	}


	private void createM0_transitions() {
		m0_transitions = new boolean[numTransitionsFromNet][numTransitionsToNet];

		for (int indexA = 0; indexA < numTransitionsFromNet; indexA++) {
			final ITransition transitionA = fromNet.getTransitionById(fromNet.getPre().getTransitionIds()[indexA]);
			for (int indexB = 0; indexB < numTransitionsToNet; indexB++) {
				final ITransition transitionB = toNet.getTransitionById(toNet.getPre().getTransitionIds()[indexB]);
				if (	transitionA.getName().equals(transitionB.getName()) &&
						transitionA.getTlb().equals(transitionB.getTlb()) &&
						transitionA.getRnw().equals(transitionB.getRnw()) &&
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
			int numWeightA = e.getValue(); // key: Kantengewicht, value: Anzahl Kanten mit diesem Kantengewicht
			Integer numWeightB = edgesNetB.get(e.getKey());
			if (numWeightB == null) {
				return false;
			}
			if (numWeightA > numWeightB) {
				return false;
			}
		}
		return true;
	}


	private boolean testTransitionEdges(Map<Integer, Integer> edgesNetA, Map<Integer, Integer> edgesNetB) {
		return edgesNetA.equals(edgesNetB);
//		for (Entry<Integer, Integer> e : edgesNetA.entrySet()) {
//			int numWeightA = e.getValue();
			// Achtung: Aus der Map edgesNetB werden Elemente geloescht!
//			Integer temp = edgesNetB.remove(e.getKey());
//			int numWeightB = (temp == null ? 0 : temp);
//			if (numWeightA != numWeightB || !edgesNetB.isEmpty()) {
//				return false;
//			}
//		}
//		return true;
	}

	
	private static class Container {
		final Map<Integer, Integer>[] places;
		final Map<Integer, Integer>[] transitions;
		
		public Container(Map<Integer, Integer>[] places, Map<Integer, Integer>[] transitions) {
			this.places = places;
			this.transitions = transitions;
		}
		
	}

	
	private Container countEdges(IMatrix m) {

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
		return new Container(places, transitions);
	}


	private boolean findMorphism() {
		final int numVertices = numPlacesFromNet + numTransitionsFromNet;

		final BoolMatrix[] m_places = new BoolMatrix[numVertices];
		final BoolMatrix[] m_transitions = new BoolMatrix[numVertices];

		final List<Integer>[] placesB = new List[numPlacesFromNet]; // Alle moeglichen Zuordnungen von Stellen
		final List<Integer>[] transitionsB = new List[numTransitionsFromNet]; // Alle moeglichen Zuordnungen von Transitionen


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

			if (currentRow < numTransitionsFromNet) {
				// ------------------- Die Transition-Matrix bearbeiten ---------------------------------------------

				List<Integer> possibleMappings = transitionsB[currentRow]; // Die Liste aller moeglichen Zuordnungen in der aktuellen Zeile
				if (possibleMappings == null) {
					transitionsB[currentRow] = prev_M_trans.getVerticesB(currentRow);
					possibleMappings = transitionsB[currentRow];
					Collections.shuffle(possibleMappings);
				}

				boolean searchReady = false;
				do {
					if (possibleMappings.isEmpty()) {
						// Wenn keine Moeglichkeit der Zuordnung vorhanden, eine Zeile zurueck gehen
						transitionsB[currentRow] = null;
						currentRow--;
						searchReady = true; // Die innere Schleife beenden und dann die aeussere Schleife wiederholen
					} else {
						// Eine Zuordnung festlegen und aus der Liste der moegl. Zuordnungen entfernen
						final int currentTransB = possibleMappings.remove(0);

						// Die Matrizen fuer diesen Iterationsschritt als Kopien aus den Vorgaenger-Matrizen erstellen
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
						searchReady = true; // Aus der inneren Schleife ausbrechen und dann die aeussere Schleife wiederholen
					}
				} while (!searchReady);

			} else {
				// ------------------- Die Place-Matrix bearbeiten ---------------------------------------------

				final int currentRowPlaces = currentRow  - numTransitionsFromNet; // Index berechnen fuer den Zugriff auf das Array placesB
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
						searchReady = true; // Die innere Schleife beenden und dann die aeussere Schleife wiederholen
					} else {
						// Eine Zuordnung festlegen und aus der Liste der moegl. Zuordnungen entfernen
						final int currentPlaceB = possibleMappings.remove(0);

						// Die Matrizen fuer diesen Iterationsschritt als Kopien aus den Vorgaenger-Matrizen erstellen
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
						searchReady = true; // Aus der inneren Schleife ausbrechen und dann die aeussere Schleife wiederholen
					}
				} while (!searchReady);
			}
		}

		if (currentRow < 0) {
			// Kein Morphismus vorhanden
			return false;
		} else {
			assert currentRow == numVertices;
			final int temp = currentRow - 1;

			//				System.out.printf("m_places%n%s%nm_transitions%n%s%n", 
			//						m_places[temp],
			//						m_transitions[temp]);

			places = createPlacesMap(m_places[temp]);
			transitions = createTransitionsMap(m_transitions[temp]);
			edges = createEdgesMap();
			return true;
		}
	}


	private boolean neighbourCheckTransition(final int transA, final int transB, BoolMatrix m_places) {
		// Nachbarschaften im Vorbereich der Transition testen
		for (int placeA = 0; placeA < numPlacesFromNet; placeA++) {
			final int a = fromNetPre.get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von transA: placeA
				// Abbildungen von placeA nach placeB ermitteln
				for (int placeB = 0; placeB < numPlacesToNet; placeB++) {
					// Bei allen moeglichen Zuordnungen placeA --> placeB die Nachbarschaftsbedingung pruefen
					if (m_places.getValue(placeA, placeB)) {
						if (toNetPre.get(placeB, transB) != a) {
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
		for (int placeA = 0; placeA < numPlacesFromNet; placeA++) {
			final int a = fromNetPost.get(placeA, transA);
			if (a > 0) { // Stelle im Nachbereich von transA: placeA
				// Abbildungen von placeA nach placeB ermitteln
				for (int placeB = 0; placeB < numPlacesToNet; placeB++) {
					// Bei allen moeglichen Zuordnungen placeA --> placeB die Nachbarschaftsbedingung pruefen
					if (m_places.getValue(placeA, placeB)) {
						if (toNetPost.get(placeB, transB) != a) {
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
		for (int transA = 0; transA < numTransitionsFromNet; transA++) {
			final int a = fromNetPost.get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von placeA: transA
				// Abbildungen von transA nach transB ermitteln
				for (int transB = 0; transB < numTransitionsToNet; transB++) {
					// Bei allen moeglichen Zuordnungen transA --> transB die Nachbarschaftsbedingung pruefen
					if (m_transitions.getValue(transA, transB)) {
						if (toNetPost.get(placeB, transB) != a) {
							m_transitions.setFalse(transA, transB);
							if (m_transitions.getNumTrues(transA) == 0) {
								return false;
							}
						}
					}
				}
			}
		}

		// Nachbarschaften im Nachbereich der Stelle testen
		for (int transA = 0; transA < numTransitionsFromNet; transA++) {
			final int a = fromNetPre.get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von placeA: transA
				// Abbildungen von transA nach transB ermitteln
				for (int transB = 0; transB < numTransitionsToNet; transB++) {
					// Bei allen moeglichen Zuordnungen transA --> transB die Nachbarschaftsbedingung pruefen
					if (m_transitions.getValue(transA, transB)) {
						if (toNetPre.get(placeB, transB) != a) {
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


	private Map<IPlace, IPlace> createPlacesMap(BoolMatrix matrix) {
		Map<IPlace, IPlace> result = new HashMap<IPlace, IPlace>();

		for (int r = 0; r < matrix.getNumRows(); r++) {
			assert matrix.getNumTrues(r) == 1;
			final IPlace placeA = fromNet.getPlaceById(fromNet.getPre().getPlaceIds()[r]);
			for (int c = 0; /*c < m[0].getNumCols()*/; c++) {
				if (matrix.getValue(r, c)) {
					final IPlace placeB = toNet.getPlaceById(toNet.getPre().getPlaceIds()[c]);
					result.put(placeA, placeB);
					break;
				}
			}
		}

		return Collections.unmodifiableMap(result);
	}


	private Map<ITransition, ITransition> createTransitionsMap(BoolMatrix m) {
		Map<ITransition, ITransition> result = new HashMap<ITransition, ITransition>();

		for (int r = 0; r < m.getNumRows(); r++) {
			assert m.getNumTrues(r) == 1;
			final ITransition transitionA = fromNet.getTransitionById(fromNet.getPre().getTransitionIds()[r]);
			for (int c = 0; /*c < m[0].getNumCols()*/; c++) {
				if (m.getValue(r, c)) {
					final ITransition transitionB = toNet.getTransitionById(toNet.getPre().getTransitionIds()[c]);
					result.put(transitionA, transitionB);
					break;
				}
			}
		}

		return Collections.unmodifiableMap(result);
	}
	
	
	private Map<IArc, IArc> createEdgesMap() {
		Map<IArc, IArc> result = new HashMap<IArc, IArc>();
		
		Set<IArc> arcsA = fromNet.getAllArcs();
		Set<IArc> arcsB = toNet.getAllArcs();
		
		Set<IArc> arcsB_p_to_t = new HashSet<IArc>();
		Set<IArc> arcsB_t_to_p = new HashSet<IArc>();
		
		for (IArc arcB : arcsB) {
			if (arcB.getStart() instanceof IPlace) {
				arcsB_p_to_t.add(arcB);
			} else { // arcB.getStart() instanceof ITransition
				arcsB_t_to_p.add(arcB);
			}
		}
		
		for (IArc arcA : arcsA) {
			IPlace placeB;
			ITransition transB;
			INode tempStartA = arcA.getStart();

			if (tempStartA instanceof IPlace) {
				placeB = places.get(tempStartA);
				transB = transitions.get(arcA.getEnd());
				for (IArc arcB : arcsB_p_to_t) {
					if (arcB.getStart() == placeB && arcB.getEnd() == transB) {
						result.put(arcA, arcB);
						break;
					}
				}
			} else { // tempStartA instanceof ITransition
				transB = transitions.get(tempStartA);
				placeB = places.get(arcA.getEnd());
				for (IArc arcB : arcsB_t_to_p) {
					if (arcB.getStart() == transB && arcB.getEnd() == placeB) {
						result.put(arcA, arcB);
						break;
					}
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

	} // class BoolMatrix


}


