package persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlList;

public class Page {
	
	String id;
	
	
	Name name;

	
	List<Place> place;
	

	List<Transition> transition;
	
	
	List<Arc> arc;

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