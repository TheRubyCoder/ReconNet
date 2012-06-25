package persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
/**
 * This class represents the information about a transition (xml node) 
 */
public class Transition {
	
	/**
	 * Id of Transition
	 */
	String id;
	
	/**	
	 * Position of transition
	 */
	Graphics graphics;
	
	/**
	 * Name
	 */
	TransitionName transitionName;
	
	/**
	 * Label
	 */
	TransitionLabel transitionLabel;
	
	/**
	 * Renew
	 */
	TransitionRenew transitionRenew;

	@XmlAttribute
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement 
	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	@XmlElement 
	public TransitionName getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(TransitionName transitionName) {
		this.transitionName = transitionName;
	}

	@XmlElement 
	public TransitionLabel getTransitionLabel() {
		return transitionLabel;
	}

	public void setTransitionLabel(TransitionLabel transitionLabel) {
		this.transitionLabel = transitionLabel;
	}

	@XmlElement 
	public TransitionRenew getTransitionRenew() {
		return transitionRenew;
	}

	public void setTransitionRenew(TransitionRenew transitionRenew) {
		this.transitionRenew = transitionRenew;
	}

}
