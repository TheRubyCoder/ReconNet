package persistence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Represents information about a place 
 */
public class Place {
	
	/**
	 * Id of the place
	 */
	String id;
	
	/**
	 * Name of the place
	 */
	PlaceName placeName;
	
	
	/**
	 * Marking of the place
	 */
	InitialMarking initialMarking;
	
	/**
	 * Graphical information about the node
	 */
	Graphics graphics;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@XmlElement
	public PlaceName getPlaceName() {
		return placeName;
	}

	public void setPlaceName(PlaceName placeName) {
		this.placeName = placeName;
	}

	@XmlElement
	public InitialMarking getInitialMarking() {
		return initialMarking;
	}

	public void setInitialMarking(InitialMarking initialMarking) {
		this.initialMarking = initialMarking;
	}

	@XmlElement
	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
}
