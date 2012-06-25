package persistence;

import javax.xml.bind.annotation.XmlElement;

/**
 * Representing the name of a place 
 */
public class PlaceName {
	
	String text;

	@XmlElement
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
