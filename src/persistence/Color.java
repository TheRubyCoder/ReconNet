package persistence;

import javax.xml.bind.annotation.XmlAttribute;

public class Color {

	String r;
	String g;
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

	public java.awt.Color toAWTColor() {
		return new java.awt.Color(Integer.valueOf(r), Integer.valueOf(g),
				Integer.valueOf(b));
	}

}
