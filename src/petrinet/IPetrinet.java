package petrinet;

import petrinet.model.Petrinet;

/**
 * Interface for accessing petrinet component so other components do not need to
 * directly access classes within the component
 */
public interface IPetrinet {
	public Petrinet createPetrinet();
	public Petrinet getPetrinet(int id);
}
