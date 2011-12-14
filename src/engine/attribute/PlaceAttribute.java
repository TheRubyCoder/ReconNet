package engine.attribute;

import java.awt.Color;

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
	private Color color;
	
	public PlaceAttribute(int marking, String pname, Color color){
		this.marking = marking;
		this.pname = pname;
		this.color = color;
	}
	
	public int getMarking(){
		return marking;
	}
	
	public String getPname(){
		return pname;
	}
	
	public Color getColor(){
		return color;
	}
	
}
