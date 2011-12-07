package engine.data;

import petrinet.Petrinet;

/**
 * 
 * This Class holds all information: Petrinet and JungData with a Id
 * 
 */
public class PetrinetData {

	private int id;
	private Petrinet petrinet;
	private JungData jungData;
	
	/**
	 * Constructor for PetrinetData.
	 * @param id
	 * @param iPetrinet
	 * @param jungData
	 */
	public PetrinetData(int id, Petrinet petrinet, JungData jungData){
		this.id = id;
		this.petrinet = petrinet;
		this.jungData = jungData;
	}
	
	/**
	 * Gets the Petrinet. 
	 * 
	 * @return IPetrinet
	 */
	public Petrinet getPetrinet(){
		return petrinet;
	}
	
	/**
	 * Gets the JungData.
	 * 
	 * @return JungData from the Petrinet
	 */
	public JungData getJungData(){
		return jungData;
	}
	
	/**
	 * Gets the Id of PetrinetData.
	 * 
	 * @return an int as id
	 */
	public int getId(){
		return id;
	}
	
}
