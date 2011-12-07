package engine.attribute;

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
