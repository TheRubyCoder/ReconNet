package petrinet.model;

/**
 * The arc of a petrinet. Holding information about its marking as well as
 * references to its source and target nodes.
 */
public final class PreArc implements IArc {
	/**
	 * Maximal weight
	 */
	private int weight;

	/**
	 * Name of the arc
	 */
	private String name;

	/**
	 * source place
	 */
	private Place source;

	/**
	 * target transition
	 */
	private Transition target;

	/**
	 * Unique id
	 */
	private final int id;

	/**
	 * Creates a new arc
	 * 
	 * @param id       Unique id
	 * @param source   source node of arc
	 * @param target   target node of arc
	 * @param name     name of arc
	 * @param weight   arc weight
	 */
	public PreArc(int id, Place source, Transition target, String name, int weight) {		
		this.id       = id;
		this.source   = source;
		this.target   = target;
		this.name     = name;
		this.weight   = weight;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#getWeight()
	 */
	@Override
	public int getWeight() {
		return weight;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#setWeight(int)
	 */
	@Override
	public void setWeight(int weight) {
		if (weight <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.weight = weight;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#getSource()
	 */
	@Override
	public Place getSource() {
		return source;
	}

	public Place getPlace() {
		return source;
	}

	/* (non-Javadoc)
	 * @see petrinet.model.IArc#getTarget()
	 */
	@Override
	public Transition getTarget() {
		return target;
	}

	public Transition getTransition() {
		return target;
	}


	@Override
	public String toString() {
		return "PreArc [weight=" + weight + ", name=" + name + ", place=" + source
				+ ", transition=" + target + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		
		if (!(object instanceof PreArc)) {
			return false;
		}
		
		PreArc arc = (PreArc) object;
		
		return id == arc.getId();
	}	
}
