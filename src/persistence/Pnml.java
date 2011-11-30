package persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pnml", namespace="http://www.pnml.org/version-2009/grammar/pnml")
public class Pnml {
	
	
	
	
	Net net;
	
	@XmlElement
	public Net getNet() {
		return net;
	}


	public void setNet(Net net) {
		this.net = net;
	}
	

}