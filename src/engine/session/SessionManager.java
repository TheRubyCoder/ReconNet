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

package engine.session;

import java.util.HashMap;
import java.util.Map;

import com.sun.istack.NotNull;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import transformation.Rule;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.data.SessionData;

/**
 * The instance manager manages all the different types of data objects like
 * {@link PetrinetData}, {@link JungData} etc. It has only a singleton instance
 */
final public class SessionManager {
	/** Singleton instance */
	private static SessionManager instance;
	
	/** Counter for creating data IDs */
	private int idSessionData = 0;
	
	/** Stores petrinet data by their ID */
	private Map<Integer, PetrinetData> petrinetData;
	
	/** Stores rule data by their ID */
	private Map<Integer, RuleData> ruleData;
	
	
	private Map<Integer, SessionData> sessionData;

	private SessionManager() {
		sessionData = new HashMap<Integer, SessionData>();
		petrinetData = new HashMap<Integer, PetrinetData>();
		ruleData = new HashMap<Integer, RuleData>();
	}

	/** Returns the singleton instace */
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}

		return instance;
	}

	/**
	 * Gets PetrinetData with the ID.
	 * 
	 * @param id
	 *            from the PetrinetData
	 * 
	 * @return the PetrinetData with this id, null if the Id is not known
	 */
	public PetrinetData getPetrinetData(int id) {
		return petrinetData.get(id);
	}

	/**
	 * This method return the RuleData for the Id
	 * 
	 * @param id
	 *            from the RuleData
	 * 
	 * @return the RuleData or null if the Id is not valid
	 */
	public RuleData getRuleData(int id) {
		return ruleData.get(id);
	}

	/**
	 * @param id
	 * 
	 * @return
	 */
	public SessionData getSessionData(int id) {
		return sessionData.get(id);
	}

	/**
	 * Create a new PetrinetData.
	 * 
	 * @param empty
	 *            petrinet for the PetrinetData
	 * 
	 * @return the new PetrinetData
	 */
	public PetrinetData createPetrinetData(@NotNull Petrinet petrinet) {
		checkEmptyPetrinet(petrinet);

		PetrinetData data = new PetrinetData(getNextSessionDataId(), petrinet,
				getNewJungData());

		petrinetData.put(data.getId(), data);

		return data;
	}

	/**
	 * Creates a new RuleData from all Petrinet (l, k, r).
	 * 
	 * @param l
	 *            id of left Petrinet
	 * @param k
	 *            id of middle Petrinet
	 * @param r
	 *            id of right Petrinet
	 * 
	 * @return the new RuleData
	 */
	public RuleData createRuleData(@NotNull Rule rule) {
		checkEmptyRule(rule);

		RuleData data = new RuleData(getNextSessionDataId(), rule,
				getNewJungData(), getNewJungData(), getNewJungData());

		ruleData.put(data.getId(), data);

		return data;
	}

	/**
	 * removes a Data from instance manager
	 * 
	 * @param id
	 *            of a SessionData
	 * @return true if Data was successful closed, false if id was not found, or
	 *         data coulnd't be closed
	 */
	public boolean closeSessionData(int id) {
		return sessionData.remove(id) != null;
	}

	private JungData getNewJungData() {
		DirectedSparseGraph<INode, IArc> graph = new DirectedSparseGraph<INode, IArc>();

		return new JungData(graph, new StaticLayout<INode, IArc>(graph));
	}

	private int getNextSessionDataId() {
		idSessionData++;

		return idSessionData;
	}

	/**
	 * throws an exception, if check result is negative.
	 * 
	 * @param isValid
	 *            if false, exception is thrown
	 * @param message
	 *            message of exception
	 */
	private void check(boolean isValid, String message) {
		if (!isValid) {
			throw new IllegalArgumentException(message);
		}
	}

	/** Checks whether a petrinet is empty */
	private void checkEmptyPetrinet(Petrinet petrinet) {
		check(petrinet.getArcs().isEmpty(), "arcs have to be empty");
		check(petrinet.getPlaces().isEmpty(), "arcs have to be empty");
		check(petrinet.getTransitions().isEmpty(), "arcs have to be empty");
	}

	/** Checks whether a rule is empty */
	private void checkEmptyRule(Rule rule) {
		checkEmptyPetrinet(rule.getK());
		checkEmptyPetrinet(rule.getL());
		checkEmptyPetrinet(rule.getR());
	}
}
