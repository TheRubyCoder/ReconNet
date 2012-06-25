package persistence;

import javax.xml.bind.annotation.XmlElement;

import petrinet.Place;

/**
 * This class represents information about markings
 * @see Place#getMark()
 */
public class InitialMarking {
	
	/**
	 * Marking as String
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
