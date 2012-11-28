package persistence;

import javax.xml.bind.annotation.XmlElement;

import petrinet.model.Transition;

/**
 * This class represents information about the Label of a Transition
 * @see Transition#getTlb() 
 */
public class TransitionLabel {
	
	/**
	 * Label as String
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
