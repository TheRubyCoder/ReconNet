package persistence;

import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents information of the inscription of a node (xml node) 
 */
public class Inscription {
	
	/**
	 * Inscription content
	 */
	private String text;

	@XmlElement
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
