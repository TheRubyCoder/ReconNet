package petrinet.model;

/**
 * Defines the type of action performed on a node of a petrinet.
 * Used for {@link IPetrinetListener} 
 */
public enum ActionType {
	ADDED,
	CHANGED,
	REMOVED
}