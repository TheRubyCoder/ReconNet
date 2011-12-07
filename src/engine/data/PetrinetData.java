package engine.data;

import petrinet.IPetrinet;

/**
 * 
 * This Class holds all information: Petrinet and JungData with a Id
 * 
 */
public class PetrinetData {

	private int id;
	private IPetrinet petrinet;
	private JungData jungData;
	
	/**
	 * Constructor for PetrinetData.
	 * @param id
	 * @param iPetrinet
	 * @param jungData
	 */
	public PetrinetData(int id, IPetrinet iPetrinet, JungData jungData){
		this.id = id;
		this.petrinet = iPetrinet;
		this.jungData = jungData;
	}
	
	/**
	 * Gets the Petrinet. 
	 * 
	 * @return IPetrinet
	 */
	public IPetrinet getPetrinet(){
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
