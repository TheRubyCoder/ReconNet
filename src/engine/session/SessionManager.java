package engine.session;

import java.util.HashMap;
import java.util.Map;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
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
		SessionData result = null;
		result = sessionData.get(id);
		if (result == null) {
			result = petrinetData.get(id);
		}
		if (result == null) {
			result = ruleData.get(id);
		}
		return result;
	}

	/**
	 * Create a new PetrinetData.
	 * 
	 * @param empty
	 *            petrinet for the PetrinetData
	 * 
	 * @return the new PetrinetData
	 */
	public PetrinetData createPetrinetData(Petrinet petrinet) {
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
	public RuleData createRuleData(Rule rule) {
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
		DirectedSparseGraph<INode, Arc> graph = new DirectedSparseGraph<INode, Arc>();

		return new JungData(graph, new StaticLayout<INode, Arc>(graph));
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
		check(petrinet.getAllArcs().isEmpty(), "arcs have to be empty");
		check(petrinet.getAllPlaces().isEmpty(), "arcs have to be empty");
		check(petrinet.getAllTransitions().isEmpty(), "arcs have to be empty");
	}

	/** Checks whether a rule is empty */
	private void checkEmptyRule(Rule rule) {
		checkEmptyPetrinet(rule.getK());
		checkEmptyPetrinet(rule.getL());
		checkEmptyPetrinet(rule.getR());
	}
}
