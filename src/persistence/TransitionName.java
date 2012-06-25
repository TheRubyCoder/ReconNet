package persistence;

import javax.xml.bind.annotation.XmlElement;

import petrinet.Transition;

/**
 * This class represents information about the Label of a Transition
 * @see Transition#getName() 
 */
public class TransitionName {
	
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
