package engine.attribute;

/**
 * 
 * This Class holds all Information/Attribute of Arc (which is the weight)
 * 
 */

public class ArcAttribute {

	private int weight;
	
	public ArcAttribute(int weight){
		this.weight = weight;
	}
	
	/**
	 * 
	 * This methods return the value of a Arc.
	 * 
	 * @return the return value (int).
	 * 
	 */
	public int getWeight(){
		return weight;
	}
	
}
