package persistence;

import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents the weight of an arc (xml sub node) 
 */
public class Weight {
	/**
	 * weight
	 */
	String text;

	@XmlElement
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
