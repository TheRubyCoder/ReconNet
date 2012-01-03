package engine.data;

import petrinet.Petrinet;

/**
 * This Class holds all information: Petrinet and JungData with a Id
 */

final public class PetrinetData extends SessionDataAbstract {
	private Petrinet petrinet;
	private JungData jungData;
	private boolean  isSimulation;
	private int      parentId;

	private PetrinetData() {}
	
	/**
	 * Constructor for PetrinetData.
	 * @param id
	 * @param isSimulation
	 * @param parentId 
	 * @param iPetrinet
	 * @param jungData
	 */
	public PetrinetData(int id, boolean isSimulation, int parentId, Petrinet petrinet, JungData jungData) {
		check(id > 0, "id have to be greater than 0");
		check(parentId >= 0, "id have to be greater or equal 0");
		check(petrinet instanceof Petrinet, "petrinet not of type Petrinet");
		check(jungData instanceof JungData, "jungData not of type JungData");

		// isSimulation -> (parentId > 0)
		check(!isSimulation || parentId > 0, "a simulation net must have a parentId");

		// (parentId > 0) -> isSimulation 
		check(!(parentId > 0) || isSimulation, "a net with a parentId must be a simulation net");
		
		checkContaining(petrinet, jungData);
		
		this.id           = id;
		this.parentId     = parentId;
		this.petrinet     = petrinet;
		this.jungData     = jungData;
		this.isSimulation = isSimulation;
	}
	
	public boolean isSimulation() {
		return isSimulation;
	}
	
	/**
	 * Gets the JungData.
	 * 
	 * @return JungData from the Petrinet
	 */
	public JungData getJungData() {
		return jungData;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	/**
	 * Gets the Petrinet. 
	 * 
	 * @return IPetrinet
	 */
	public Petrinet getPetrinet() {
		return petrinet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PetrinetData other = (PetrinetData) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
