package engine.attribute;

/**
 * 
 * This Class holds all Information/Attributes of Places.
 * 
 * @author alex (aas772)
 *
 */

public class PlaceAttribute {

	private int marking;
	private String pname;
	
	public PlaceAttribute(int marking, String pname){
		this.marking = marking;
		this.pname = pname;
	}
	
	public int getMarking(){
		return marking;
	}
	
	public String getPname(){
		return pname;
	}
	
}
