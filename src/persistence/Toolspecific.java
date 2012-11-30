package persistence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * This class represents information about toolspecific information about arcs
 * @see IArc
 */
public class Toolspecific {

	/**
	 * XML tool
	 */
	String tool;
	
	/**
	 * XML version
	 */
	String version;
	
	/**
	 * XML id of target node
	 */
	Weight weight;

	@XmlAttribute
	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	@XmlAttribute
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlElement
	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}
}
