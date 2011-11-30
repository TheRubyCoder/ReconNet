package persistence;

import javax.xml.bind.annotation.XmlAttribute;


public class Position {
	
	String x;
	
	
	String y;

	@XmlAttribute
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}
	@XmlAttribute
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
}
