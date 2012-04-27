package persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Arc {


	String id;
	
	
	String source;
	
	
	String target;
	
	
	Graphics graphics;
	
	private Inscription inscription;

	@XmlElement
	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}
	
	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@XmlAttribute
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@XmlAttribute
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	@XmlElement
	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	
	
}
