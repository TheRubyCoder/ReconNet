package persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 * A Page has most information about a petrinet
 */
public class Page {

	/**
	 * ID of the petrinet
	 */
	String id;

	/**
	 * Name of the petrinet
	 */
	Name name;

	/**
	 * Places of the petrinet
	 */
	List<Place> place = new ArrayList<Place>();

	/**
	 * Transitions of the petrinet 
	 */
	List<Transition> transition = new ArrayList<Transition>();

	/**
	 * Arcs of the petrinet 
	 */
	List<Arc> arc = new ArrayList<Arc>();

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@XmlElements(value = { @XmlElement })
	public List<Place> getPlace() {
		return place;
	}

	public void setPlace(List<Place> place) {
		this.place = place;
	}

	@XmlElements(value = { @XmlElement })
	public List<Transition> getTransition() {
		return transition;
	}

	public void setTransition(List<Transition> transition) {
		this.transition = transition;
	}

	@XmlElements(value = { @XmlElement })
	public List<Arc> getArc() {
		return arc;
	}

	public void setArc(List<Arc> arc) {
		this.arc = arc;
	}
}
