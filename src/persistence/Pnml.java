package persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pnml", namespace="http://www.pnml.org/version-2009/grammar/pnml")
/**
 * This class is the root of the XML. 
 */
public class Pnml {
	/**
	 * A List with one or three petrinets (three when this pnml safes a rule)
	 */
	List<Net> net;
	
	/**
	 * The size of nodes in this petrinet/rule
	 */
	double nodeSize;
	
	/**
	 * Indicating whether this is a petrinet or a rule
	 * @see Converter#PETRINET_IDENT
	 * @see Converter#RULE_IDENT
	 */
	String type;
	
	@XmlAttribute
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setNodeSize(double nodeSize) {
		this.nodeSize = nodeSize;
	}
	
	@XmlAttribute
	public double getNodeSize() {
		return nodeSize;
	}
	
	@XmlElements(value = { @XmlElement })
	public List<Net> getNet() {
		return net;
	}


	public void setNet(List<Net> net) {
		this.net = net;
	}
	

}