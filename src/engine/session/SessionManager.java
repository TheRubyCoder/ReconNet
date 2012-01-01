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

final public class SessionManager {
	private static SessionManager     session;
	private Map<Integer, SessionData> sessiondata;	
	private int 					  idPetrinetData = 0;

	private SessionManager() {
		sessiondata  = new HashMap<Integer, SessionData>();
	}

	public static SessionManager getInstance() {
		if (session == null) {
			session = new SessionManager();
		}

		return session;
	}

	/**
	 * Gets PetrinetData with the ID.
	 * 
	 * @param  id from the PetrinetData
	 * 
	 * @return the PetrinetData with this id, null if the Id is not known
	 */
	public PetrinetData getPetrninetData(int id) {
		SessionData data = sessiondata.get(id);
		
		checkPetrinetData(data);

		return (PetrinetData) data;
	}

	/**
	 * This method return the RuleData for the Id
	 * 
	 * @param  id from the RuleData
	 * 
	 * @return the RuleData or null if the Id is not valid
	 */
	public RuleData getRuleData(int id) {
		SessionData data = sessiondata.get(id);
		
		checkRuleData(data);

		return (RuleData) data;
	}

	/**
	 * @param id
	 * 
	 * @return
	 */
	public SessionData getSessionData(int id) {
		SessionData data = sessiondata.get(id);
		
		checkSessionData(data);

		return data;
	}

	/**
	 * Create a new PetrinetData.
	 * 
	 * @param  empty petrinet for the PetrinetData
	 * 
	 * @return the new PetrinetData
	 */
	public PetrinetData createPetrinetData(Petrinet petrinet) {
		checkEmptyPetrinet(petrinet);
				
		PetrinetData data = new PetrinetData(
			getNextSessionDataId(), 
			false, 
			0, 
			petrinet, 
			getNewJungData()
		); 
		
		sessiondata.put(data.getId(), data);
				
		return data;
	}

	/**
	 * It create a new RuleData from all Petrinet (l, k, r).
	 * 
	 * @param  l id of left Petrinet
	 * @param  k id of middle Petrinet
	 * @param  r id of right Petrinet
	 * 
	 * @return the new RuleData
	 */
	public RuleData createRuleData(Rule rule) {
		checkEmptyRule(rule);
				
		RuleData data = new RuleData(
			getNextSessionDataId(), 
			rule, 
			getNewJungData(), 
			getNewJungData(), 
			getNewJungData()
		);
		
		sessiondata.put(data.getId(), data);
				
		return data;
	}
	
	
	private JungData getNewJungData() {
		DirectedSparseGraph<INode, Arc> graph = new DirectedSparseGraph<INode, Arc>();
		
		return new JungData(
			graph, 
			new StaticLayout<INode, Arc>(graph)
		);
	}
	
	private int getNextSessionDataId() {
		idPetrinetData++;
		
		return idPetrinetData;
	}
	

	/**
	 * throws an exception, if check result is negative.
	 * 
	 * @param isValid	if false, exception is thrown	
	 * @param message	message of exception
	 */
	private void check(boolean isValid, String message) {
		if (!isValid) {
			throw new IllegalArgumentException(message);
		}
	}


	public void checkSessionData(SessionData data) {
		check(data instanceof SessionData, "data not of type SessionData");
	}

	public void checkPetrinetData(SessionData data) {
		check(data instanceof PetrinetData, "data not of type PetrinetData");
	}


	public void checkRuleData(SessionData data) {
		check(data instanceof RuleData, "data not of type RuleData");
	}

	public void checkEmptyPetrinet(Petrinet petrinet) {
		check(petrinet instanceof Petrinet, "petrinet not of type Petrinet");
		check(petrinet.getAllArcs().isEmpty(), "arcs have to be empty");
		check(petrinet.getAllPlaces().isEmpty(), "arcs have to be empty");
		check(petrinet.getAllTransitions().isEmpty(), "arcs have to be empty");
	}

	public void checkEmptyRule(Rule rule) {
		check(rule instanceof Rule, "rule not of type Rule");
		checkEmptyPetrinet(rule.getK());
		checkEmptyPetrinet(rule.getL());
		checkEmptyPetrinet(rule.getR());
	}
}
