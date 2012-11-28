package engine.data;

import petrinet.model.Petrinet;

/**
 * This Class holds all information: Petrinet and JungData with a Id
 */

final public class PetrinetData extends SessionDataAbstract {
	private Petrinet petrinet;
	private JungData jungData;

	@SuppressWarnings("unused")
	// no default constructor
	private PetrinetData() {
	}

	/**
	 * Constructor for PetrinetData.
	 * 
	 * @param id
	 * @param isSimulation
	 * @param parentId
	 * @param iPetrinet
	 * @param jungData
	 */
	public PetrinetData(int id,
			Petrinet petrinet, JungData jungData) {
		check(id > 0, "id have to be greater than 0");
		check(petrinet instanceof Petrinet, "petrinet not of type Petrinet");
		check(jungData instanceof JungData, "jungData not of type JungData");

		checkContaining(petrinet, jungData);

		this.id = id;
		this.petrinet = petrinet;
		this.jungData = jungData;
	}


	/**
	 * Gets the JungData.
	 * 
	 * @return JungData from the Petrinet
	 */
	public JungData getJungData() {
		return jungData;
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
