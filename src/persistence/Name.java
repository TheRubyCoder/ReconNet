package persistence;

import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents the name of a node (xml sub node) 
 */
public class Name {

	/**
	 * Name
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
