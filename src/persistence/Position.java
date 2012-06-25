package persistence;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Information about the Position of a node
 *
 */
public class Position {
	
	/**
	 * X double as String
	 */
	String x;
	
	
	/**
	 * Y double as String
	 */
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
	
	public String toString() {
		return "Position(" + x + ", " + y + ")";
	}
}
