package engine.session;

import java.util.HashMap;
import java.util.Map;

import petrinet.IPetrinet;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.data.SessionData;

public class SessionManager {

	private SessionManager session;
	
	private Map<Integer, PetrinetData> petrinetdata;
	private int idPetrinetData = 0;
	
	private SessionManager(){
		petrinetdata = new HashMap<Integer, PetrinetData>();
	}
	
	public SessionManager getInstance(){
		if(session == null){
			session = new SessionManager();
		}
		
		return session;
	}
	
	/**
	 * Gets PetrinetData with the ID.
	 * @param id from the PetrinetData
	 * @return the PetrinetData with this id
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
		return null; // TODO: !		
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
	 * 
	 * @param petrinet
	 * @return
	 */
	public PetrinetData createPetrinetData(IPetrinet petrinet){
		JungData jd = new JungData(petrinet);
		
		PetrinetData pd = new PetrinetData(idPetrinetData, petrinet, jd);
		
		idPetrinetData += 1;
		
		return pd;
	}
	
	/**
	 * 
	 * @param l
	 * @param k
	 * @param r
	 * @return
	 */
	public RuleData createRuleData(PetrinetData l, PetrinetData k, PetrinetData r){
		return null; // TODO: !
	}
	
}
