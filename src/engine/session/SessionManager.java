package engine.session;

import java.util.HashMap;
import java.util.Map;

import petrinet.Petrinet;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.data.SessionData;

public class SessionManager {

	private static SessionManager session;
	
	private Map<Integer, PetrinetData> petrinetdata;
	private Map<Integer, RuleData> ruledata;
	private int idPetrinetData = 0;
	
	private SessionManager(){
		petrinetdata = new HashMap<Integer, PetrinetData>();
		ruledata = new HashMap<Integer, RuleData>();
	}
	
	public static SessionManager getInstance(){
		if(session == null){
			session = new SessionManager();
		}
		
		return session;
	}
	
	/**
	 * Gets PetrinetData with the ID.
	 * @param id from the PetrinetData
	 * @return the PetrinetData with this id,
	 * 		   null if the Id is not known
	 */
	public PetrinetData getPetrninetData(int id){
		PetrinetData pd = petrinetdata.get(id);
		
		return pd;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PetrinetData getRuleData(int id){
//		RuleData rd = ruledata.get(id);
//		
//		return rd;
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public SessionData getSessionData(int id){
		return null; // TODO: !		
	}
	
	/**
	 * Create a new PetrinetData.
	 * @param petrinet for the PetrinetData
	 * @return the new PetrinetData
	 */
	public PetrinetData createPetrinetData(Petrinet petrinet){
		/*JungData jd = new JungData(petrinet);
		
		PetrinetData pd = new PetrinetData(idPetrinetData, petrinet, jd);
		
		idPetrinetData += 1;
		*/
		
		// TODO createPetrinetData, wie ohne koordinaten aufbauen?
		throw new UnsupportedOperationException();
		//return pd;
	}
	
	/**
	 * 
	 * It create a new RuleData from all Petrinet (l, k, r).
	 * 
	 * @param l id of left Petrinet
	 * @param k id of middle Petrinet
	 * @param r id of right Petrinet
	 * @return the new RuleData
	 * 
	 */
	public RuleData createRuleData(PetrinetData l, PetrinetData k, PetrinetData r){
		
		// TODO : id for internal map
		// int id = ..?
		
		// TODO : Rule ansich..?
		
		// RuleData rd = new RuleData(<Rule>, l, k, r);
		// return rd;
		
		throw new UnsupportedOperationException();

	}
	
}
