package persistence;

import javax.xml.bind.annotation.XmlElement;

import petrinet.model.IRenew;
import petrinet.model.Transition;

/**
 * This class represents information about the Label of a Transition
 * 
 * @see Transition#getRnw()
 */
public class TransitionRenew {

	/**
	 * Rebew as String
	 * @see IRenew#toGUIString()
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
