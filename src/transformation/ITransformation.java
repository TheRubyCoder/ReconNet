package transformation;


/**
 * Interface for accessing transformation component so other components do not need to directly access classes within the component 
 */
public interface ITransformation {

	public Rule createRule();
	
}
