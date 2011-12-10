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
	private int idPetrinetData = 0;
	
	private SessionManager(){
		petrinetdata = new HashMap<Integer, PetrinetData>();
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
	 * @param l
	 * @param k
	 * @param r
	 * @return
	 */
	public RuleData createRuleData(PetrinetData l, PetrinetData k, PetrinetData r){
		return null; // TODO: !
	}
	
}
