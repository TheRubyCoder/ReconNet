package persistence;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Class that represents the color information about nodes (xml node)
 */
public class Color {

	/**
	 * Red part of the color 0 <= r <= 255
	 */
	String r;
	/**
	 * Green part of the color 0 <= g <= 255
	 */
	String g;
	/**
	 * Blue part of the color 0 <= b <= 255
	 */
	String b;

	@XmlAttribute
	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	@XmlAttribute
	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	@XmlAttribute
	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	/**
	 * Transforms this {@link Color} into the equivalent {@link java.awt.Color} that is used in
	 * the logic and gui
	 * 
	 */
	public java.awt.Color toAWTColor() {
		return new java.awt.Color(Integer.valueOf(r), Integer.valueOf(g),
				Integer.valueOf(b));
	}

}
