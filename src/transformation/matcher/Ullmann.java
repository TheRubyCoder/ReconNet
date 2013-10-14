/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package transformation.matcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Match;

//Will not be documented any further as it may be reimplemented by Mathias soon
/**
 * Finds Matchs between two petrinets in a not deterministic way
 */
public class Ullmann {

	/**
	 * Finds a match between two petrinets.<br/>
	 * Not deterministic.
	 * 
	 * @param from
	 *            the Petrinet from which this match starts.
	 * @param to
	 *            the Petrinet into which this match maps to.
	 * @return the new match<br/>
	 *         Empty match is fromNet is empty<br/>
	 *         <tt>null</tt> if no match found
	 * 
	 */
	public static Match createMatch(Petrinet from, Petrinet to) {
		return new Ullmann(from, to).getMatch();
	}

	/** "from" petrinet of the match to find */
	private final Petrinet fromNet;
	/** Matrix that represents pre of "from" matrix */
	private final Matrix fromNetPre;
	/** Matrix that represents post of "from" matrix */
	private final Matrix fromNetPost;
	/** Number of places in "from" net */
	private final int numPlacesFromNet;
	/** Number of transitions in "from" net */
	private final int numTransitionsFromNet;

	/** "to" petrinet of the match to find */
	private final Petrinet toNet;
	/** Matrix that represents pre of "to" matrix */
	private final Matrix toNetPre;
	/** Matrix that represents post of "to" matrix */
	private final Matrix toNetPost;
	/** Number of places in "to" net */
	private final int numPlacesToNet;
	/** Number of transitions in "to" net */
	private final int numTransitionsToNet;

	// Bedeutung von Arrayindex, key und value der Maps in den folgenden Zeilen:
	// Arrayindex: Eine Stelle/Transition (Zeilennr./Spaltennr. in
	// pre-/post-Matrix)
	// key: Kantengewicht
	// value: Die Anzahl der Kanten, die dieses Kantengewicht haben
	/**
	 * Mapping for each entry in the <b>preMatrix</b> of "from" net with
	 * <ul>
	 * <tt>key</tt> = weight of edges and <br/>
	 * <tt>value</tt> = number of edges with that weight
	 * </ul>
	 * <h1>Examples:</h1> If there is an 8 in the matrix, does it mean
	 * <ul>
	 * <li>1 edge with weight 5 and 1 edge with weight 3 (5+3)</li>
	 * <li>or 1 edge with weight 6 and 1 edge weight 2 (6+2)</li>
	 * <li>or just 1 edge with weight 8 (8)?</li>
	 * </ul>
	 */
	private Map<Integer, Integer>[] weightDistributionInPreFrom; // Kantengewichte
																	// der in
																	// die
																	// Stellen
																	// von netA
																	// eingehenden
																	// Kanten

	/**
	 * Mapping for each entry in the <b>postMatrix</b>of "from" net with
	 * <ul>
	 * <tt>key</tt> = weight of edges and <br/>
	 * <tt>value</tt> = number of edges with that weight
	 * </ul>
	 * <h1>Examples:</h1> If there is an 8 in the matrix, does it mean
	 * <ul>
	 * <li>1 edge with weight 5 and 1 edge with weight 3 (5+3)</li>
	 * <li>or 1 edge with weight 6 and 1 edge weight 2 (6+2)</li>
	 * <li>or just 1 edge with weight 8 (8)?</li>
	 * </ul>
	 */
	private Map<Integer, Integer>[] weightDistributionInPostFrom; // Kantengewichte
																	// der aus
																	// den
																	// Stellen
																	// von netA
																	// ausgehenden
																	// Kanten

	private Map<Integer, Integer>[] netA_transitionsInEdges; // Kantengewichte
																// der in die
																// Transitionen
																// von netA
																// eingehenden
																// Kanten
	private Map<Integer, Integer>[] netA_transitionsOutEdges; // Kantengewichte
																// der aus den
																// Transitionen
																// von netA
																// ausgehenden
																// Kanten
	private Map<Integer, Integer>[] netB_placesInEdges; // ab hier das gleiche
														// fuer netB
	private Map<Integer, Integer>[] netB_placesOutEdges;
	private Map<Integer, Integer>[] netB_transitionsInEdges;
	private Map<Integer, Integer>[] netB_transitionsOutEdges;

	boolean[][] m0_places;
	boolean[][] m0_transitions;

	private Map<Place, Place> places;
	private Map<Transition, Transition> transitions;
	private Map<PreArc, PreArc> preArcs;
	private Map<PostArc, PostArc> postArcs;

	private Ullmann(Petrinet from, Petrinet to) {
		fromNet = from;
		toNet = to;
		fromNetPre = new Matrix(fromNet.getPre().getPreAsArray());
		fromNetPost = new Matrix(fromNet.getPost().getPostAsArray());
		numPlacesFromNet = fromNetPre.getNumRows();
		numTransitionsFromNet = fromNetPre.getNumCols();
		toNetPre = new Matrix(toNet.getPre().getPreAsArray());
		toNetPost = new Matrix(toNet.getPost().getPostAsArray());
		numPlacesToNet = toNetPre.getNumRows();
		numTransitionsToNet = toNetPre.getNumCols();

		init();

		// Nun werden die
		createM0_places();
		createM0_transitions();
	}

	/**
	 * 
	 * @return Empty match is fromNet is empty<br/>
	 *         <tt>null</tt> if no match found
	 */
	private Match getMatch() {
		// L was empty? Not too bad. Lets return an empty match
		if (fromNet.isEmpty()) {
			Match emptyMatch = new Match(
				fromNet, toNet,
				new HashMap<Place, Place>(),
				new HashMap<Transition, Transition>(),
				new HashMap<PreArc, PreArc>(),
				new HashMap<PostArc, PostArc>()
			);
			
			return emptyMatch;
		} else {
			boolean successful = findMatch();
			// found usual match? Great. Lets return it.
			if (successful) {
				return new Match(fromNet, toNet, places, transitions, preArcs, postArcs);
			} else {
				// Failed although L was not empty? No match found. Return
				// null
				return null;
			}
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
			final Place placeA = fromNet.getPlace(fromNet.getPre()
					.getPlaceIds()[indexA]);
			for (int indexB = 0; indexB < numPlacesToNet; indexB++) {
				final Place placeB = toNet.getPlace(toNet.getPre()
						.getPlaceIds()[indexB]);
				// Alle Bedingungen pruefen, die erfuellt sein muessen, damit
				// die m0-Matrix fuer das aktuelle Mapping placeA --> placeB
				// einen true-Eintrag bekommt
				if (placeA.getName().equals(placeB.getName())
						&& placeA.getMark() <= placeB.getMark()
						&& testPlaceEdges(weightDistributionInPreFrom[indexA],
								netB_placesInEdges[indexB])
						&& testPlaceEdges(weightDistributionInPostFrom[indexA],
								netB_placesOutEdges[indexB])) {

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
			final Transition transitionA = fromNet.getTransition(fromNet
					.getPre().getTransitionIds()[indexA]);
			for (int indexB = 0; indexB < numTransitionsToNet; indexB++) {
				final Transition transitionB = toNet.getTransition(toNet
						.getPre().getTransitionIds()[indexB]);
				if (transitionA.getName().equals(transitionB.getName())
						&& transitionA.getTlb().equals(transitionB.getTlb())
						&& transitionA.getRnw().equals(transitionB.getRnw())
						&& testTransitionEdges(netA_transitionsInEdges[indexA],
								netB_transitionsInEdges[indexB])
						&& testTransitionEdges(
								netA_transitionsOutEdges[indexA],
								netB_transitionsOutEdges[indexB])) {

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

	private boolean testPlaceEdges(Map<Integer, Integer> edgesNetA,
			Map<Integer, Integer> edgesNetB) {
		for (Entry<Integer, Integer> e : edgesNetA.entrySet()) {
			int numWeightA = e.getValue(); // key: Kantengewicht, value: Anzahl
											// Kanten mit diesem Kantengewicht
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

	private boolean testTransitionEdges(Map<Integer, Integer> edgesNetA,
			Map<Integer, Integer> edgesNetB) {
		return edgesNetA.equals(edgesNetB);
		// for (Entry<Integer, Integer> e : edgesNetA.entrySet()) {
		// int numWeightA = e.getValue();
		// Achtung: Aus der Map edgesNetB werden Elemente geloescht!
		// Integer temp = edgesNetB.remove(e.getKey());
		// int numWeightB = (temp == null ? 0 : temp);
		// if (numWeightA != numWeightB || !edgesNetB.isEmpty()) {
		// return false;
		// }
		// }
		// return true;
	}

	private static class Container {
		final Map<Integer, Integer>[] places;
		final Map<Integer, Integer>[] transitions;

		public Container(Map<Integer, Integer>[] places,
				Map<Integer, Integer>[] transitions) {
			this.places = places;
			this.transitions = transitions;
		}

	}

	private Container countEdges(Matrix m) {

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

	private boolean findMatch() {
		final int numVertices = numPlacesFromNet + numTransitionsFromNet;

		final BoolMatrix[] m_places = new BoolMatrix[numVertices];
		final BoolMatrix[] m_transitions = new BoolMatrix[numVertices];

		@SuppressWarnings("unchecked")
		final List<Integer>[] placesB = new List[numPlacesFromNet]; // Alle
																	// moeglichen
																	// Zuordnungen
																	// von
																	// Stellen
		@SuppressWarnings("unchecked")
		final List<Integer>[] transitionsB = new List[numTransitionsFromNet]; // Alle
																				// moeglichen
																				// Zuordnungen
																				// von
																				// Transitionen

		final BoolMatrix m0_placesExt = new BoolMatrix(m0_places);
		final BoolMatrix m0_transitionsExt = new BoolMatrix(m0_transitions);

		int currentRow = 0;

		// In jedem Schleifendurchlauf in der aktuellen Zeile eine Zuordnung
		// festlegen
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
				// ------------------- Die Transition-Matrix bearbeiten
				// ---------------------------------------------

				List<Integer> possibleMappings = transitionsB[currentRow]; // Die
																			// Liste
																			// aller
																			// moeglichen
																			// Zuordnungen
																			// in
																			// der
																			// aktuellen
																			// Zeile
				if (possibleMappings == null) {
					transitionsB[currentRow] = prev_M_trans
							.getVerticesB(currentRow);
					possibleMappings = transitionsB[currentRow];
					Collections.shuffle(possibleMappings);
				}

				boolean searchReady = false;
				do {
					if (possibleMappings.isEmpty()) {
						// Wenn keine Moeglichkeit der Zuordnung vorhanden, eine
						// Zeile zurueck gehen
						transitionsB[currentRow] = null;
						currentRow--;
						searchReady = true; // Die innere Schleife beenden und
											// dann die aeussere Schleife
											// wiederholen
					} else {
						// Eine Zuordnung festlegen und aus der Liste der moegl.
						// Zuordnungen entfernen
						final int currentTransB = possibleMappings.remove(0);

						// Die Matrizen fuer diesen Iterationsschritt als Kopien
						// aus den Vorgaenger-Matrizen erstellen
						m_transitions[currentRow] = new BoolMatrix(prev_M_trans);
						m_places[currentRow] = new BoolMatrix(prev_M_places);

						// Wenn eine Nullzeile entsteht: Zuordnung nicht Ok -->
						// die Schleife wiederholen
						if (m_transitions[currentRow].setTrue(currentRow,
								currentTransB) == false) {
							continue;
						}
						// Alle Zuordnungen entfernen, bei denen die
						// Nachbarschaftsbedingung verletzt ist
						// Wenn eine Nullzeile entsteht: Zuordnung nicht Ok -->
						// die Schleife wiederholen
						if (neighbourCheckTransition(currentRow, currentTransB,
								m_places[currentRow]) == false) {
							continue;
						}

						// Wenn eine Zuordnung gefunden, die keine Nullzeilen
						// produziert, eine Zeile weiter gehen
						currentRow++;
						searchReady = true; // Aus der inneren Schleife
											// ausbrechen und dann die aeussere
											// Schleife wiederholen
					}
				} while (!searchReady);

			} else {
				// ------------------- Die Place-Matrix bearbeiten
				// ---------------------------------------------

				final int currentRowPlaces = currentRow - numTransitionsFromNet; // Index
																					// berechnen
																					// fuer
																					// den
																					// Zugriff
																					// auf
																					// das
																					// Array
																					// placesB
				List<Integer> possibleMappings = placesB[currentRowPlaces]; // Die
																			// Liste
																			// aller
																			// moeglichen
																			// Zuordnungen
																			// in
																			// der
																			// aktuellen
																			// Zeile
				if (possibleMappings == null) {
					possibleMappings = prev_M_places
							.getVerticesB(currentRowPlaces);
					Collections.shuffle(possibleMappings);
				}

				boolean searchReady = false;
				do {
					if (possibleMappings.isEmpty()) {
						// Wenn keine Moeglichkeit der Zuordnung vorhanden, eine
						// Zeile zurueck gehen
						placesB[currentRowPlaces] = null;
						currentRow--;
						searchReady = true; // Die innere Schleife beenden und
											// dann die aeussere Schleife
											// wiederholen
					} else {
						// Eine Zuordnung festlegen und aus der Liste der moegl.
						// Zuordnungen entfernen
						final int currentPlaceB = possibleMappings.remove(0);

						// Die Matrizen fuer diesen Iterationsschritt als Kopien
						// aus den Vorgaenger-Matrizen erstellen
						m_transitions[currentRow] = new BoolMatrix(prev_M_trans);
						m_places[currentRow] = new BoolMatrix(prev_M_places);

						// Wenn eine Nullzeile entsteht: Zuordnung nicht Ok -->
						// die Schleife wiederholen
						if (m_places[currentRow].setTrue(currentRowPlaces,
								currentPlaceB) == false) {
							continue;
						}
						// Alle Zuordnungen entfernen, bei denen die
						// Nachbarschaftsbedingung verletzt ist
						// Wenn eine Nullzeile entsteht: Zuordnung nicht Ok -->
						// die Schleife wiederholen
						if (neighbourCheckPlace(currentRowPlaces,
								currentPlaceB, m_transitions[currentRow]) == false) {
							continue;
						}

						// Wenn eine Zuordnung gefunden, die keine Nullzeilen
						// produziert, eine Zeile weiter gehen
						currentRow++;
						searchReady = true; // Aus der inneren Schleife
											// ausbrechen und dann die aeussere
											// Schleife wiederholen
					}
				} while (!searchReady);
			}
		}

		if (currentRow < 0) {
			// Kein Matchus vorhanden
			return false;
		} else {
			assert currentRow == numVertices;
			final int temp = currentRow - 1;

			// System.out.printf("m_places%n%s%nm_transitions%n%s%n",
			// m_places[temp],
			// m_transitions[temp]);

			places      = createPlacesMap(m_places[temp]);
			transitions = createTransitionsMap(m_transitions[temp]);
			
			createBothArcsMaps();
			
			return true;
		}
	}

	private boolean neighbourCheckTransition(final int transA,
			final int transB, BoolMatrix m_places) {
		// Nachbarschaften im Vorbereich der Transition testen
		for (int placeA = 0; placeA < numPlacesFromNet; placeA++) {
			final int a = fromNetPre.get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von transA: placeA
				// Abbildungen von placeA nach placeB ermitteln
				for (int placeB = 0; placeB < numPlacesToNet; placeB++) {
					// Bei allen moeglichen Zuordnungen placeA --> placeB die
					// Nachbarschaftsbedingung pruefen
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
					// Bei allen moeglichen Zuordnungen placeA --> placeB die
					// Nachbarschaftsbedingung pruefen
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

	private boolean neighbourCheckPlace(final int placeA, final int placeB,
			BoolMatrix m_transitions) {
		// Nachbarschaften im Vorbereich der Stelle testen
		for (int transA = 0; transA < numTransitionsFromNet; transA++) {
			final int a = fromNetPost.get(placeA, transA);
			if (a > 0) { // Stelle im Vorbereich von placeA: transA
				// Abbildungen von transA nach transB ermitteln
				for (int transB = 0; transB < numTransitionsToNet; transB++) {
					// Bei allen moeglichen Zuordnungen transA --> transB die
					// Nachbarschaftsbedingung pruefen
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
					// Bei allen moeglichen Zuordnungen transA --> transB die
					// Nachbarschaftsbedingung pruefen
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

	private Map<Place, Place> createPlacesMap(BoolMatrix matrix) {
		Map<Place, Place> result = new HashMap<Place, Place>();

		for (int r = 0; r < matrix.getNumRows(); r++) {
			assert matrix.getNumTrues(r) == 1;
			final Place placeA = fromNet.getPlace(fromNet.getPre()
					.getPlaceIds()[r]);
			for (int c = 0; /* c < m[0].getNumCols() */; c++) {
				if (matrix.getValue(r, c)) {
					final Place placeB = toNet.getPlace(toNet.getPre()
							.getPlaceIds()[c]);
					result.put(placeA, placeB);
					break;
				}
			}
		}

		return Collections.unmodifiableMap(result);
	}

	private Map<Transition, Transition> createTransitionsMap(BoolMatrix m) {
		Map<Transition, Transition> result = new HashMap<Transition, Transition>();

		for (int r = 0; r < m.getNumRows(); r++) {
			assert m.getNumTrues(r) == 1;
			final Transition transitionA = fromNet.getTransition(fromNet
					.getPre().getTransitionIds()[r]);
			for (int c = 0; /* c < m[0].getNumCols() */; c++) {
				if (m.getValue(r, c)) {
					final Transition transitionB = toNet
							.getTransition(toNet.getPre()
									.getTransitionIds()[c]);
					result.put(transitionA, transitionB);
					break;
				}
			}
		}

		return Collections.unmodifiableMap(result);
	}

	private void createBothArcsMaps() {
		Map<PreArc, PreArc> resultPreArcs = new HashMap<PreArc, PreArc>();
		Map<PostArc, PostArc> resultPostArcs = new HashMap<PostArc, PostArc>();
		
		Set<IArc> arcsA = fromNet.getArcs();
		Set<IArc> arcsB = toNet.getArcs();

		Set<IArc> arcsB_p_to_t = new HashSet<IArc>();
		Set<IArc> arcsB_t_to_p = new HashSet<IArc>();

		for (IArc arcB : arcsB) {
			if (arcB.getSource() instanceof Place) {
				arcsB_p_to_t.add(arcB);
			} else { // arcB.getStart() instanceof Transition
				arcsB_t_to_p.add(arcB);
			}
		}

		for (IArc arcA : arcsA) {
			Place placeB;
			Transition transB;
			INode tempStartA = arcA.getSource();

			if (tempStartA instanceof Place) {
				placeB = places.get(tempStartA);
				transB = transitions.get(arcA.getTarget());
				for (IArc arcB : arcsB_p_to_t) {
					if (arcB.getSource() == placeB && arcB.getTarget() == transB) {
						if (arcA instanceof PreArc && arcB instanceof PreArc) {
							resultPreArcs.put((PreArc) arcA, (PreArc) arcB);
						} else if (arcA instanceof PostArc && arcB instanceof PostArc) {
							resultPostArcs.put((PostArc) arcA, (PostArc) arcB);							
						}
												
						break;
					}
				}
			} else { // tempStartA instanceof Transition
				transB = transitions.get(tempStartA);
				placeB = places.get(arcA.getTarget());
				for (IArc arcB : arcsB_t_to_p) {
					if (arcB.getSource() == transB && arcB.getTarget() == placeB) {
						
						if (arcA instanceof PreArc && arcB instanceof PreArc) {
							resultPreArcs.put((PreArc) arcA, (PreArc) arcB);
						} else if (arcA instanceof PostArc && arcB instanceof PostArc) {
							resultPostArcs.put((PostArc) arcA, (PostArc) arcB);							
						}
						
						break;
					}
				}
			}
		}

		preArcs  = Collections.unmodifiableMap(resultPreArcs);
		postArcs = Collections.unmodifiableMap(resultPostArcs);
	}

	static class BoolMatrix {
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
				for (col = 0; /* col < result.getNumCols() */; col++) {
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
	
	class Matrix {

		/** Matrix is internally realized as a nested array */
		private final int[][] matrix;
		
		
		public Matrix(int[][] matrix) {
			this.matrix = matrix;
		}
		
		
		public int getNumRows() {
			return matrix.length;
		}

		public int getNumCols() {
			if(matrix.length == 0) {
				return 0;
			}
			
			return matrix[0].length;
		}

		/**
		 * Returns the value at given position
		 * @param row index of row of position
		 * @param column index of column of position
		 * @return value
		 * @throws ArrayIndexOutOfBoundsException if <tt>row</tt> or <tt>column</tt> bigger than matrix
		 */
		public int get(int row, int column) {
			return matrix[row][column];
		}

	}
}
